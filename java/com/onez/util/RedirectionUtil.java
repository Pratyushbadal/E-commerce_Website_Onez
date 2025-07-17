package com.onez.util;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * @author Madan Shrestha
 */
public class RedirectionUtil {

	private static final String baseUrl = "/WEB-INF/page/";
	public static final String registerUrl = baseUrl + "register.jsp";
	public static final String loginUrl = baseUrl + "login.jsp";
	public static final String homeUrl = baseUrl + "home.jsp";
	public static final String userDashboardUrl = baseUrl + "userDashboard.jsp";
	public static final String viewCategoryUrl = baseUrl + "viewCategory.jsp";
	public static final String adminDashboardUrl = baseUrl + "admin/adminDashboard.jsp";
	public static final String productsUrl = baseUrl + "admin/products.jsp";
	public static final String cartUrl = baseUrl + "cart.jsp";
	public static final String privacyUrl = baseUrl + "privacy.jsp";
	public static final String termsUrl = baseUrl + "terms.jsp";
	public static final String contactUrl = baseUrl + "contact.jsp";
	public static final String returnUrl = baseUrl + "return.jsp";
	public static final String warrantyUrl = baseUrl + "warranty.jsp";
	public static final String wishlistUrl = baseUrl + "wishlist.jsp";
	public static final String aboutUsUrl = baseUrl + "aboutus.jsp";


	

	public void setMsgAttribute(HttpServletRequest req, String msgType, String msg) {
		req.setAttribute(msgType, msg);
	}

	public void redirectToPage(HttpServletRequest req, HttpServletResponse resp, String page)
			throws ServletException, IOException {
		req.getRequestDispatcher(page).forward(req, resp);
	}

	public void setMsgAndRedirect(HttpServletRequest req, HttpServletResponse resp, String msgType, String msg,
			String page) throws ServletException, IOException {
		setMsgAttribute(req, msgType, msg);
		redirectToPage(req, resp, page);
	}

}