package com.onez.controller;

import java.io.IOException;

import com.onez.model.UserModel;
import com.onez.service.LoginService;
import com.onez.util.CookieUtil;
import com.onez.util.RedirectionUtil;
import com.onez.util.SessionUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * LoginController is responsible for handling login requests. It interacts with
 * the LoginService to authenticate users.
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/login" })
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private final LoginService loginService;

	/**
	 * Constructor initializes the LoginService.
	 */
	public LoginController() {
		this.loginService = new LoginService();
	}
 
	/**
	 * Handles GET requests to the login page.
	 *
	 * @param request  HttpServletRequest object
	 * @param response HttpServletResponse object
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException      if an I/O error occurs
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher(RedirectionUtil.loginUrl).forward(request, response);
	}

	/**
	 * Handles POST requests for user login.
	 *
	 * @param request  HttpServletRequest object
	 * @param response HttpServletResponse object
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException      if an I/O error occurs
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	    String username = req.getParameter("username");
	    String password = req.getParameter("password");    

	    UserModel userModel = loginService.loginUser(username, password);

	    if (userModel != null) {
	        SessionUtil.setAttribute(req, "username", userModel.getUserName());
	        SessionUtil.setAttribute(req, "id", userModel.getId());
	        SessionUtil.setAttribute(req, "user", userModel); 

	        if ("admin".equals(userModel.getUserRole())) {
	            CookieUtil.addCookie(resp, "role", "admin", 5 * 30);
	            resp.sendRedirect(req.getContextPath() + "/adminDashboard");
	        } else {
	            CookieUtil.addCookie(resp, "role", "customer", 5 * 30);
	            resp.sendRedirect(req.getContextPath() + "/home");
	        }
	    } else {
	        handleLoginFailure(req, resp, false);
	    }
	}

	/**
	 * Handles login failures by setting attributes and forwarding to the login
	 * page.
	 *
	 * @param req         HttpServletRequest object
	 * @param resp        HttpServletResponse object
	 * @param loginStatus Boolean indicating the login status
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException      if an I/O error occurs
	 */
	private void handleLoginFailure(HttpServletRequest req, HttpServletResponse resp, Boolean loginStatus)
			throws ServletException, IOException {
		String errorMessage;
		if (loginStatus == null) {
			errorMessage = "Our server is under maintenance. Please try again later!";
		} else {
			errorMessage = "User credential mismatch. Please try again!";
		}
		req.setAttribute("error", errorMessage);
		req.getRequestDispatcher("/WEB-INF/page/login.jsp").forward(req, resp);
	}

}