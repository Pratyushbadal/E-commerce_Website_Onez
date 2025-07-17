<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="jakarta.servlet.http.HttpSession"%>
<%@ page import="jakarta.servlet.http.HttpServletRequest"%>
<%@ page import="com.onez.model.UserModel" %>

<%
    HttpSession userSession = request.getSession(false);
    String currentUser = null;
    UserModel user = null;
    UserModel id = null;

    if (userSession != null) {
        currentUser = (String) userSession.getAttribute("username");
        user = (UserModel) userSession.getAttribute("user"); // assuming user object is stored in session
        
    }

    pageContext.setAttribute("currentUser", currentUser);
    pageContext.setAttribute("user", user); // make it accessible to JSTL
%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<link rel="stylesheet" type="text/css"
		href="${pageContext.request.contextPath}/css/header.css" />
<script src="https://kit.fontawesome.com/91fb88d05c.js" crossorigin="anonymous"></script>
<link href="https://cdn.lineicons.com/5.0/lineicons.css" rel="stylesheet" />
<!-- header Begins -->
<header>
    <div>
        <a href="${contextPath}/home">
            <img src="${contextPath}/resources/logo/onez.svg" alt="Logo">
        </a>
    </div>
    <div>
        <input type="text" placeholder="Search For Products">
        <i class="fa-solid fa-magnifying-glass"></i>
    </div>
    <div>
        <ul>
            <li><a href="${contextPath}/contact"><i class="fa-solid fa-headset fa-xl"></i></a></li>
            <li><a href="${contextPath}/wishlist"><i class="fa-regular fa-heart fa-xl"></i></a></li>
            <li><a href="${contextPath}/cart"><i class="fa-solid fa-cart-shopping fa-xl"></i></a></li>
            <li>
                <c:choose>
                    <c:when test="${not empty currentUser}">
                        <a href="${contextPath}/userDashboard">
                            <img src="${contextPath}/resources/user/${user.imageUrl}" width="30" height="30" style="border-radius: 10px;"
     								onerror="this.src='${contextPath}/resources/logo/onez.svg'">
     								
                        </a>
                    </c:when>
                    <c:otherwise>
                        <a href="${contextPath}/login"><i class="fa-solid fa-user fa-xl"></i></a>
                    </c:otherwise>
                </c:choose>
            </li>
        </ul>
    </div>
</header>
<!-- headers ends -->
