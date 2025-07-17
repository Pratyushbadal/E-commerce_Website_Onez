package com.onez.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.onez.model.UserModel;
import com.onez.service.UserDashboardService;
import com.onez.util.RedirectionUtil;

@WebServlet(asyncSupported = true, urlPatterns = { "/userDashboard" })
public class UserDashboardController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private UserDashboardService dashboardService;

    public UserDashboardController() {
        this.dashboardService = new UserDashboardService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Integer userId = (Integer) request.getSession().getAttribute("id");
        
        // If not logged in, redirect to login
        if (userId == null) {
            response.sendRedirect(request.getContextPath() + RedirectionUtil.loginUrl);
            return;
        }

        UserModel user = dashboardService.getUserInfo(userId);
        if (user != null) {
            request.setAttribute("user", user);
            request.getRequestDispatcher(RedirectionUtil.userDashboardUrl).forward(request, response);
        } else {
            // If user not found, set error message and stay on dashboard
            request.setAttribute("error", "User information not found");
            request.getRequestDispatcher(RedirectionUtil.userDashboardUrl).forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Integer userId = (Integer) request.getSession().getAttribute("id");
        
        if (userId == null) {
            response.sendRedirect(request.getContextPath() + RedirectionUtil.loginUrl);
            return;
        }

        try {
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String email = request.getParameter("email");
            String number = request.getParameter("number");
            String dob = request.getParameter("dob");

            UserModel updatedUser = new UserModel();
            updatedUser.setId(userId);
            updatedUser.setFirstName(firstName);
            updatedUser.setLastName(lastName);
            updatedUser.setEmail(email);
            updatedUser.setNumber(number);
            
            if (dob != null && !dob.isEmpty()) {
                updatedUser.setDob(java.time.LocalDate.parse(dob));
            }

            boolean success = dashboardService.updateUserInfo(updatedUser);

            if (success) {
                request.getSession().setAttribute("successMessage", "Profile updated successfully!");
            } else {
                request.getSession().setAttribute("errorMessage", "Failed to update profile. Please try again.");
            }
        } catch (Exception e) {
            request.getSession().setAttribute("errorMessage", "Invalid data format. Please check your inputs.");
        }
        
        response.sendRedirect(request.getContextPath() + RedirectionUtil.userDashboardUrl);
    }
}