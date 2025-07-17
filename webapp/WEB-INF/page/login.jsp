<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Login</title>
    <!-- Set contextPath variable for reuse -->
	<c:set var="contextPath" value="${pageContext.request.contextPath}" />
    <link rel="stylesheet" type="text/css"
		href="${contextPath}/css/login.css" />
	<link href="https://cdn.lineicons.com/3.0/lineicons.css" rel="stylesheet" />
  </head>
  <body>
    <div class="login-container">
      <img src="${contextPath}/resources/logo/logo.png" alt="Logo" class="logo" />
      <p>Please enter your details</p>

		<form action="${contextPath}/login" method="post">
		      <div class="form">
		        <label class="username" for="username">Email/Username</label>
		        <div class="input">
		          <i class="lni lni-user"></i>
		          <input type="text" id="username" name="username" required />
		        </div>
		      </div>
		
		      <div class="form">
		        <label class="password" for="password">Password</label>
		        <div class="input">
		          <i class="lni lni-lock"></i>
		          <input type="password" id="password" name="password" required />
		        </div>
		      </div>
		
		      <button type="submit"  class="login-btn">Login</button>
		
		      <div class="signup-link">
		        Don't have an account? <a href="${contextPath}/register">Sign up</a>
		      </div>
      	</form>
      <!-- Display error message if available -->
				<c:if test="${not empty error}">
					<p class="error-message">${error}</p>
				</c:if>
		
				<!-- Display success message if available -->
				<c:if test="${not empty success}">
					<p class="success-message">${success}</p>
				</c:if>
    </div>
  </body>
</html>
