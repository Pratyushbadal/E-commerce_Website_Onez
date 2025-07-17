package com.onez.controller;

import com.onez.model.CartModel;
import com.onez.model.ProductModel;
import com.onez.model.UserModel;
import com.onez.service.AdminDashboardService;
import com.onez.service.CartService;
import com.onez.service.ProductService;
import com.onez.util.RedirectionUtil;
import com.onez.util.ValidationUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

/**
 * CartController handles all cart-related operations including:
 * - Viewing the cart
 * - Adding products to cart
 * - Removing products from cart
 * - Updating quantities
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/cart", "/cart/add", "/cart/remove", "/cart/update" })
public class CartController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private final CartService cartService = new CartService();
    private final ProductService productService = new ProductService();
 // Instance of DashboardService for handling business logic
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        UserModel user = (UserModel) session.getAttribute("user");

        if (user == null) {
            handleError(req, resp, "You must be logged in to view your cart");
            return;
        }

        Integer userId = user.getId();  // Get userId from the UserModel

        try {
            CartModel cart = cartService.getCartByUserId(userId);
            req.setAttribute("cart", cart);
            req.getRequestDispatcher(RedirectionUtil.cartUrl).forward(req, resp);
        } catch (Exception e) {
            handleError(req, resp, "An error occurred while loading your cart");
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        UserModel user = (UserModel) session.getAttribute("user");

        if (user == null) {
            handleError(req, resp, "You must be logged in to modify your cart");
            return;
        }

        Integer userId = user.getId();  // Get userId from the UserModel
        String servletPath = req.getServletPath();

        try {
            switch (servletPath) {
                case "/cart/add":
                    handleAddToCart(req, resp, userId);
                    break;
                case "/cart/remove":
                    handleRemoveFromCart(req, resp, userId);
                    break;
                case "/cart/update":
                    handleUpdateCart(req, resp, userId);
                    break;
                default:
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (Exception e) {
            handleError(req, resp, "An error occurred while processing your request");
            e.printStackTrace();
        }
    }
    
    private void handleAddToCart(HttpServletRequest req, HttpServletResponse resp, int userId) 
            throws ServletException, IOException {
        String productIdStr = req.getParameter("productId");
        String quantityStr = req.getParameter("quantity");

        String validationMessage = validateCartRequest(productIdStr, quantityStr);
        if (validationMessage != null) {
            handleError(req, resp, validationMessage);
            return;
        }

        int productId = Integer.parseInt(productIdStr);
        int quantity = Integer.parseInt(quantityStr);
        
        ProductModel product = productService.getProductById(productId);
        if (product == null) {
            handleError(req, resp, "Product not found");
            return;
        }

        if (quantity > product.getQuantity()) {
            handleError(req, resp, "Requested quantity exceeds available stock");
            return;
        }

        CartModel cart = cartService.addProductToCart(userId, productId, quantity);
        req.setAttribute("cart", cart);
        req.setAttribute("success", "Product added to cart successfully");
        req.getRequestDispatcher(RedirectionUtil.cartUrl).forward(req, resp);
    }

    private void handleRemoveFromCart(HttpServletRequest req, HttpServletResponse resp, int userId) 
            throws ServletException, IOException {
        String productIdStr = req.getParameter("productId");
        String quantityStr = req.getParameter("quantity");

        String validationMessage = validateCartRequest(productIdStr, quantityStr);
        if (validationMessage != null) {
            handleError(req, resp, validationMessage);
            return;
        }

        int productId = Integer.parseInt(productIdStr);
        int quantity = Integer.parseInt(quantityStr);

        CartModel cart = cartService.removeProductFromCart(userId, productId, quantity);
        req.setAttribute("cart", cart);
        req.setAttribute("success", "Product removed from cart successfully");
        req.getRequestDispatcher(RedirectionUtil.cartUrl).forward(req, resp);
    }

    private void handleUpdateCart(HttpServletRequest req, HttpServletResponse resp, int userId) 
            throws ServletException, IOException {
        String productIdStr = req.getParameter("productId");
        String newQuantityStr = req.getParameter("newQuantity");

        String validationMessage = validateCartRequest(productIdStr, newQuantityStr);
        if (validationMessage != null) {
            handleError(req, resp, validationMessage);
            return;
        }

        int productId = Integer.parseInt(productIdStr);
        int newQuantity = Integer.parseInt(newQuantityStr);

        ProductModel product = productService.getProductById(productId);
        if (product == null) {
            handleError(req, resp, "Product not found");
            return;
        }

        if (newQuantity > product.getQuantity()) {
            handleError(req, resp, "Requested quantity exceeds available stock");
            return;
        }

        // Get current cart to find current quantity
        CartModel currentCart = cartService.getCartByUserId(userId);
        
        // Calculate difference to update
        int currentQuantity = 0; // You would need to track individual items in CartModel
        // In a real implementation, you'd get the current quantity for this product
        
        int quantityDifference = newQuantity - currentQuantity;
        
        CartModel cart;
        if (quantityDifference > 0) {
            cart = cartService.addProductToCart(userId, productId, quantityDifference);
        } else if (quantityDifference < 0) {
            cart = cartService.removeProductFromCart(userId, productId, -quantityDifference);
        } else {
            cart = currentCart; // No change
        }

        req.setAttribute("cart", cart);
        req.setAttribute("success", "Cart updated successfully");
        req.getRequestDispatcher(RedirectionUtil.cartUrl).forward(req, resp);
    }

    private String validateCartRequest(String productIdStr, String quantityStr) {
        if (ValidationUtil.isNullOrEmpty(productIdStr)) {
            return "Product ID is required";
        }
        if (ValidationUtil.isNullOrEmpty(quantityStr)) {
            return "Quantity is required";
        }

        try {
            int productId = Integer.parseInt(productIdStr);
            if (productId <= 0) {
                return "Invalid product ID";
            }
        } catch (NumberFormatException e) {
            return "Product ID must be a number";
        }

        try {
            int quantity = Integer.parseInt(quantityStr);
            if (quantity <= 0) {
                return "Quantity must be greater than zero";
            }
        } catch (NumberFormatException e) {
            return "Quantity must be a number";
        }

        return null;
    }

    private void handleError(HttpServletRequest req, HttpServletResponse resp, String message) 
            throws ServletException, IOException {
        req.setAttribute("error", message);
        req.getRequestDispatcher(RedirectionUtil.cartUrl).forward(req, resp);
    }
}