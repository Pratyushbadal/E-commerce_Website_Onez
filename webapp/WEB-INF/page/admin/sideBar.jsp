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

<!-- Sidebar Navigation -->
      <nav class="sidebar">
      	<div>	
      		<div><img src="${contextPath}/resources/logo/logoWhite.png" alt="ONEZ Logo" class="logo"/></div>
      	</div>
        <a href="${contextPath}/adminDashboard" class="no-style">
        <div>
          <div><p>Dashboard</p></div>
          <i class="fa-solid fa-table-columns"></i>
        </div> </a>
        <a href="#" class="no-style">
        <div>
          <div><p>Orders</p></div>
          <i class="fa-solid fa-boxes-stacked"></i>
        </div></a>
        <a href="${contextPath}/modifyUsers" class="no-style">
        <div>
          <div><p>Customer Details</p></div>
          <i class="fa-solid fa-gear"></i>
        </div></a>
        <a href="${contextPath}/products" class="no-style">
        <div>
          <div><p>Manage Products</p></div>
          <i class="fa-solid fa-boxes-stacked"></i>
        </div></a>
        <div>
          <div><p><form action="${contextPath}/logout" method="post">
					<input type="submit" class="nav-button" value="Logout" />
				</form>
				</p></div>
          <i class="fa-solid fa-right-from-bracket"></i>
        </div>
      </nav>