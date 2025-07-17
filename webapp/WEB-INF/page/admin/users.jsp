<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="jakarta.servlet.http.HttpSession"%>
<%@ page import="jakarta.servlet.http.HttpServletRequest"%>

<%
// Initialize necessary objects and variables
HttpSession userSession = request.getSession(false);
String currentUser = (String) (userSession != null ? userSession.getAttribute("username") : null);
// need to add data in attribute to select it in JSP code using JSTL core tag
pageContext.setAttribute("currentUser", currentUser);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Admin Dashboard</title>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/adminDashboard.css" />
</head>
<body>

	<div class="container">
		
		<!-- Sidebar jsp file connection -->
        <jsp:include page="sideBar.jsp"/>
         <!-- Main Content -->
        <main class="dashboard">
			
			 <header>
	          <div>
	            <img src="${contextPath}/resources/logo/onez.svg" alt="ONEZ Logo" />
	          </div>
	          <div class="add-product">
	
	
	          <div class="admin-header">
	            <img src="${contextPath}/resources/user/${user.imageUrl}" width="30" height="30" style="border-radius: 10px;"
	     								onerror="this.src='${contextPath}/resources/logo/onez.svg'">
	            <p>Admin</p>
	
	          </div>
	
	          </div>
	        </header>
			
			<div class="maindash">
	          
	          <div class="section2">
	            <div class="product">
	              <i class="fa-solid fa-money-bill-trend-up"></i>
	              <h2>${empty Kathmandu ? 0 : Kathmandu}</h2>
	              <p>Kathmandu</p>
	            </div>
	          </div>
	
	          <div class="section3">
	            <div class="product">
	              <i class="fa-solid fa-user-plus"></i>
	              <h2>${empty Lalitpur ? 0 : Lalitpur}</h2>
	              <p>Lalitpur</p>
	            </div>
	          </div>
	          
	          <div class="section4">
	            <div class="product">
	              <i class="fa-solid fa-user-plus"></i>
	              <h2>${empty Bhaktapur ? 0 : Bhaktapur}</h2>
	              <p>Bhaktapur</p>
	            </div>
	          </div>
        </div>

			<div class="table-container">
				<!-- Display error message if available -->
				<c:if test="${not empty error}">
					<p class="error-message">${error}</p>
				</c:if>

				<!-- Display success message if available -->
				<c:if test="${not empty success}">
					<p class="success-message">${success}</p>
				</c:if>
				<h3>User List</h3>
				<table>
					<thead>
						<tr>
							<th>ID</th>
							<th>Name</th>
							<th>Address</th>
							<th>Email</th>
							<th>Number</th>
							<th>Actions</th>
						</tr>
					</thead>
					<tbody>
						<!-- Using JSTL forEach loop to display user data -->
						<c:forEach var="user" items="${userList}">
							<tr>
								<td>${user.id}</td>
								<td>${user.firstName} ${user.lastName}</td>
								<td>${user.address.name}</td>
								<td>${user.email}</td>
								<td>${user.number}</td>
								<td>
									<form action="${contextPath}/modifyUsers" method="post"
										style="display: inline;">
										<input type="hidden" name="userId" value="${user.id}">
										<input type="hidden" name="action" value="updateForm">
										<button class="action-btn" type="submit">Edit</button>
									</form>
									<form action="${contextPath}/users" method="post"
										style="display: inline;">
										<input type="hidden" name="userId" value="${user.id}">
										<input type="hidden" name="action" value="delete">
										<button class="action-btn" type="submit">Delete</button>
									</form>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			</main>
		</div>

</body>
</html>