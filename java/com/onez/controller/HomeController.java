package com.onez.controller;

import java.io.IOException;
import java.util.List;

import com.onez.model.ProductModel; 
import com.onez.service.ProductService; 

import com.onez.util.RedirectionUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(asyncSupported = true, urlPatterns = { "/home", "/" })
public class HomeController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // ✅ Declare and initialize ProductService
    private ProductService productService = new ProductService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        // ✅ Get all products from service
        List<ProductModel> productList = productService.getAllProducts();

        // ✅ Set in request scope for JSP
        request.setAttribute("products", productList);

        // ✅ Forward to home.jsp
        request.getRequestDispatcher(RedirectionUtil.homeUrl).forward(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	// TODO Auto-generated method stub
    	super.doPost(req, resp);
    }
}