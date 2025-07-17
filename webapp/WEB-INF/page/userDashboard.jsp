<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
  <title>Edit Profile</title>
  <!-- Set contextPath variable -->
	<c:set var="contextPath" value="${pageContext.request.contextPath}" />
  	<link rel="stylesheet" type="text/css"
		href="${pageContext.request.contextPath}/css/userDashboard.css" />
  	
</head>
<body>
<jsp:include page="header.jsp" />
  <!-- Main Content -->
  <div class="main-content">
    <div class="top-section">
      <h3>Manage My Account</h3>
    </div>
	<div class="alert alert-success">${successMessage}</div>
    <div class="alert alert-danger">${errorMessage}</div>
    
    
    <div class="account-details">
      <div class="profile-icon">
      <img src="${contextPath}/resources/user/${user.imageUrl}" width="30" height="30" style="border-radius: 10px;"
     								onerror="this.src='${contextPath}/resources/logo/onez.svg'"></div>
      <div class="vertical-line"></div>
      <form action="${pageContext.request.contextPath}/userDashboard" method="post">
        <div class="form-group">
          <label for="firstName">First Name</label>
            <input type="text" class="form-control" id="firstName" name="firstName" 
                   value="${user.firstName}" required>
        </div>
        
        <div class="form-group">
          <label for="lastName">Last Name</label>
            <input type="text" class="form-control" id="lastName" name="lastName" 
                   value="${user.lastName}" required>
        </div>

        <div class="form-group">
         <label for="dob">Date of Birth</label>
            <input type="date" class="form-control" id="dob" name="dob" 
                   value="${user.dob}">
        </div>

        <div class="form-group">
          <label for="email">Email</label>
            <input type="email" class="form-control" id="email" name="email" 
                   value="${user.email}" required>
        </div>

        <div class="form-group">
          <label for="number">Phone Number</label>
            <input type="tel" class="form-control" id="number" name="number" 
                   value="${user.number}">
        </div>

        <div class="form-actions">
          <a href="${contextPath}/home"><button type="submit" class="btn btn-primary">Update Profile</button></a>
        </div>
        </form>
    </div>
  </div>
  <!-- Logout Button at Bottom -->
    <div class="logout-bottom">
      <form action="${contextPath}/logout" method="post">
					<input type="submit" class="nav-button" value="Logout" />
				</form>
		</div>
<jsp:include page="footer.jsp" />
</body>
</html>
