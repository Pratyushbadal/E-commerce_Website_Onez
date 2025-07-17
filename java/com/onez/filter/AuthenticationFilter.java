package com.onez.filter;

import java.io.IOException;

import com.onez.util.SessionUtil;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebFilter(asyncSupported = true, urlPatterns = { "/*" })
public class AuthenticationFilter implements Filter {

    private static final String LOGIN = "/login";
    private static final String REGISTER = "/register";
    private static final String HOME = "/home";
    private static final String ROOT = "/";
    
    // Add patterns for common static resources
    private static final String[] STATIC_RESOURCES = {
        "/css/", "/js/", "/images/", "/fonts/", 
        ".css", ".js", ".png", ".jpg", ".jpeg", 
        ".gif", ".ico", ".svg", ".woff", ".woff2", 
        ".ttf", ".eot", ".map"
    };

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String uri = req.getRequestURI();
        String contextPath = req.getContextPath();

        // Check if the request is for static resources
        if (isStaticResource(uri) || uri.equals(contextPath + HOME) || uri.equals(contextPath + ROOT)) {
            chain.doFilter(request, response);
            return;
        }

        boolean isLoggedIn = SessionUtil.getAttribute(req, "username") != null;

        if (!isLoggedIn) {
            if (uri.equals(contextPath + LOGIN) || uri.equals(contextPath + REGISTER)) {
                chain.doFilter(request, response);
            } else {
                res.sendRedirect(contextPath + LOGIN);
            }
        } else {
            if (uri.equals(contextPath + LOGIN) || uri.equals(contextPath + REGISTER)) {
                res.sendRedirect(contextPath + HOME);
            } else {
                chain.doFilter(request, response);
            }
        }
    }

    // Helper method to check if URI points to a static resource
    private boolean isStaticResource(String uri) {
        for (String resource : STATIC_RESOURCES) {
            if (uri.contains(resource)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}