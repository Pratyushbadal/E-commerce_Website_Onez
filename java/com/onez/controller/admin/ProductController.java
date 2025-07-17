package com.onez.controller.admin;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import com.onez.model.ProductModel;
import com.onez.service.ProductService;
import com.onez.util.ImageUtil;
import com.onez.util.RedirectionUtil;
import com.onez.util.ValidationUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

@WebServlet(asyncSupported = true, urlPatterns = { "/products" })
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50) // 50MB
public class ProductController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String UPLOAD_DIR = "resources/product";

    private final ImageUtil imageUtil = new ImageUtil();
    private final ProductService productService = new ProductService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        
        // Check for success message in session
        String success = (String) req.getSession().getAttribute("success");
        if (success != null) {
            req.setAttribute("success", success);
            req.getSession().removeAttribute("success");
        }
        
        String action = req.getParameter("action");
        
        if ("edit".equals(action)) {
            int productId = Integer.parseInt(req.getParameter("id"));
            ProductModel product = productService.getProductById(productId);
            if (product == null) {
                handleError(req, resp, "Product not found", "/products", null);
                return;
            }
            req.setAttribute("productToEdit", product);
            req.setAttribute("showForm", true);
            req.setAttribute("isEditMode", true);
            req.setAttribute("action", "update");
        } else if ("delete".equals(action)) {
            int productId = Integer.parseInt(req.getParameter("id"));
            boolean isDeleted = productService.deleteProduct(productId);
            if (isDeleted) {
                handleSuccess(req, resp, "Product deleted successfully!", "/products");
            } else {
                handleError(req, resp, "Failed to delete product", "/products", null);
            }
            return;
        }
        
        req.setAttribute("products", productService.getAllProducts());
        req.getRequestDispatcher(RedirectionUtil.productsUrl).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        try {
            String action = req.getParameter("action");
            
            if ("add".equals(action) || "update".equals(action)) {
                String validationMessage = validateProductForm(req);
                if (validationMessage != null) {
                    handleError(req, resp, validationMessage, "/products", action);
                    return;
                }

                ProductModel productModel = extractProductModel(req);
                boolean result;
                
                if ("add".equals(action)) {
                    result = productService.addProduct(productModel);
                } else {
                    result = productService.updateProduct(productModel);
                }

                if (result) {
                    if (uploadImage(req)) {
                        handleSuccess(req, resp, 
                            "Product " + ("add".equals(action) ? "added" : "updated") + " successfully!", 
                            "/products");
                    } else {
                        handleError(req, resp, 
                            "Product saved but image upload failed", 
                            "/products", action);
                    }
                } else {
                    handleError(req, resp, "Failed to save product", "/products", action);
                }
            }
        } catch (Exception e) {
            handleError(req, resp, "An unexpected error occurred: " + e.getMessage(), 
                "/products", req.getParameter("action"));
            e.printStackTrace();
        }
    }

    private String validateProductForm(HttpServletRequest req) {
        String productName = req.getParameter("productName");
        String description = req.getParameter("description");
        String priceStr = req.getParameter("price");
        String quantityStr = req.getParameter("quantity");
        String category = req.getParameter("category");

        // Basic validation
        if (ValidationUtil.isNullOrEmpty(productName))
            return "Product name is required.";
        if (ValidationUtil.isNullOrEmpty(description))
            return "Description is required.";
        if (ValidationUtil.isNullOrEmpty(priceStr))
            return "Price is required.";
        if (ValidationUtil.isNullOrEmpty(quantityStr))
            return "Quantity is required.";
        if (ValidationUtil.isNullOrEmpty(category))
            return "Category is required.";

        try {
            double price = Double.parseDouble(priceStr);
            int quantity = Integer.parseInt(quantityStr);
            
            if (price <= 0) return "Price must be positive.";
            if (quantity < 0) return "Quantity cannot be negative.";
        } catch (NumberFormatException e) {
            return "Invalid number format for price or quantity.";
        }

        try {
            Part image = req.getPart("productImage");
            if (image != null && image.getSize() > 0) {
                if (!ValidationUtil.isValidImageExtension(image)) {
                    return "Invalid image format. Only jpg, jpeg, png, and gif are allowed.";
                }
            }
        } catch (IOException | ServletException e) {
            return "Error processing image upload.";
        }

        return null;
    }
    
    private ProductModel extractProductModel(HttpServletRequest req) throws Exception {
        int productId = 0;
        if (req.getParameter("productId") != null && !req.getParameter("productId").isEmpty()) {
            productId = Integer.parseInt(req.getParameter("productId"));
        }
        
        String productName = req.getParameter("productName");
        String description = req.getParameter("description");
        double price = Double.parseDouble(req.getParameter("price"));
        int quantity = Integer.parseInt(req.getParameter("quantity"));
        String category = req.getParameter("category");

        Part imagePart = req.getPart("productImage");
        String imageUrl = null;
        
        if (imagePart != null && imagePart.getSize() > 0) {
            imageUrl = imageUtil.getImageNameFromPart(imagePart);
        } else if (productId > 0) {
            ProductModel existing = productService.getProductById(productId);
            if (existing != null) {
                imageUrl = existing.getProductImage();
            }
        }

        return new ProductModel(productId, productName, description, price, quantity, category, imageUrl);
    }

    private boolean uploadImage(HttpServletRequest req) throws IOException, ServletException {
		Part image = req.getPart("productImage");
		return imageUtil.uploadImage(image, req.getServletContext().getRealPath("/"), "product");
	}
    
    private void handleSuccess(HttpServletRequest req, HttpServletResponse resp, 
            String message, String redirectUrl) throws IOException {
        req.getSession().setAttribute("success", message);
        resp.sendRedirect(req.getContextPath() + redirectUrl);
    }

    private void handleError(HttpServletRequest req, HttpServletResponse resp, 
            String message, String redirectUrl, String action) throws ServletException, IOException {
        req.setAttribute("error", message);
        req.setAttribute("productName", req.getParameter("productName"));
        req.setAttribute("description", req.getParameter("description"));
        req.setAttribute("price", req.getParameter("price"));
        req.setAttribute("quantity", req.getParameter("quantity"));
        req.setAttribute("category", req.getParameter("category"));
        
        req.setAttribute("showForm", true);
        req.setAttribute("isEditMode", "update".equals(action));
        
        if ("update".equals(action)) {
            req.setAttribute("productToEdit", new ProductModel(
                Integer.parseInt(req.getParameter("productId")),
                req.getParameter("productName"),
                req.getParameter("description"),
                Double.parseDouble(req.getParameter("price")),
                Integer.parseInt(req.getParameter("quantity")),
                req.getParameter("category"),
                null
            ));
        }
        
        req.setAttribute("products", productService.getAllProducts());
        req.getRequestDispatcher(RedirectionUtil.productsUrl).forward(req, resp);
    }
}