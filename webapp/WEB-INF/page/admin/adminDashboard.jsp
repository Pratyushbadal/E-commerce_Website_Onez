<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />
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
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Admin Dashboard</title>
    <link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/adminDashboard.css" />
    <script src="https://kit.fontawesome.com/91fb88d05c.js" crossorigin="anonymous"
    ></script>
  </head>

  <body>
    <div class="mainparent">
      
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

        <h1>Welcome to Dashboard!</h1>

        <div class="maindash">
          <div class="section1">
            <div class="product">
              <i class="fa-solid fa-box"></i>
              <h2>${empty totalProduct ? 0 : totalProduct}</h2>
              <p>Total Products</p>
            </div>
          </div>

          <div class="section2">
            <div class="product">
              <i class="fa-solid fa-money-bill-trend-up"></i>
              <h2>${empty totalSales ? 0 : totalSales}</h2>
              <p>Total Sales</p>
            </div>
          </div>

          <div class="section3">
            <div class="product">
              <i class="fa-solid fa-user-plus"></i>
              <h2>${empty total ? 0 : total}</h2>
              <p>Total Customer</p>
            </div>
          </div>
        </div>

        <div class="recent-orders">
          <h2>Recent Orders</h2>
          <div class="table-container">
            <table class="table">
              <thead>
                <tr>
                  <th>Name</th>
                  <th>Order Id</th>
                  <th>Status</th>
                  <th>Address</th>
                </tr>
              </thead>
              <tbody>
                <!-- Using JSTL forEach loop to display user data -->
						<c:forEach var="user" items="${userList}">
							<tr>
								<td>${user.id}</td>
								<td>${user.firstName}${user.lastName}</td>
								<td>${user.email}</td>
								<td>${user.address.name}</td>
							</tr>
						</c:forEach>
              </tbody>
            </table>
          </div>
        </div>
      </main>
    </div>
  </body>
</html>
