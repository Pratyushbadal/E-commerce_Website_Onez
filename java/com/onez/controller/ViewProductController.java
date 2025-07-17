package com.onez.controller;

import com.onez.model.ProductModel;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.onez.service.ProductService;

/**
 * Servlet implementation class ViewProduct
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/viewProduct" })
public class ViewProductController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private ProductService productService = new ProductService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int productId = Integer.parseInt(request.getParameter("productId"));
        ProductModel product = productService.getProductById(productId);

        if (product != null) {
            request.setAttribute("product", product);
            request.getRequestDispatcher("/WEB-INF/page/viewProduct.jsp").forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/home?error=ProductNotFound");
        }
    }
}
