package com.onez.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import com.onez.model.ProductModel;
import com.onez.service.ProductService;
import com.onez.util.RedirectionUtil;

/**
 * Servlet implementation class ViewCategory
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/viewCategory" })
public class ViewCategoryController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 // ✅ Declare and initialize ProductService
    private ProductService productService = new ProductService();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 // ✅ Get all products from service
        List<ProductModel> productList = productService.getAllProducts();

        // ✅ Set in request scope for JSP
        request.setAttribute("products", productList);
		// TODO Auto-generated method stub
		request.getRequestDispatcher(RedirectionUtil.viewCategoryUrl).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
