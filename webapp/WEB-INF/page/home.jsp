<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>oneZ</title>
    <!-- Set contextPath variable -->
	<c:set var="contextPath" value="${pageContext.request.contextPath}" />
    <link rel="stylesheet" type="text/css"
		href="${pageContext.request.contextPath}/css/home.css" />
    <script src="https://kit.fontawesome.com/91fb88d05c.js" crossorigin="anonymous"></script>
    <link href="https://cdn.lineicons.com/5.0/lineicons.css" rel="stylesheet" />
    <script src="${pageContext.request.contextPath}/js/slider.js"></script>
</head>

<body>
   <jsp:include page="header.jsp" />
   
   <!-- Display error message if available -->
	<c:if test="${not empty error}">
		<p class="error-message">${error}</p>
	</c:if>

	<!-- Display success message if available -->
	<c:if test="${not empty success}">
		<p class="success-message">${success}</p>
	</c:if>

    <!-- slider section Begins -->
    <section class="slider">
        <div class="slide">
            <img src="${contextPath}/resources/slider/image 1.png" alt="slider image">
            <img src="${contextPath}/resources/slider/image 2.jpg" alt="slider image">
            <img src="${contextPath}/resources/slider/image 3.jpg" alt="slider image">
            <img src="${contextPath}/resources/slider/image 4.jpg" alt="slider image">
        </div>
        <div>
            <h2>Flat 10%</h2>
            <h2>OFF</h2>
        </div>
    </section>
    <!-- slider section ends -->

    <!-- browse_by_category section -->
  
    <section class="browse_by_category">
        <h3>Browse By Category</h3>
        <div class="category">
        	<a href="${contextPath}/viewCategory">
                <div><i class="fa-solid fa-globe"></i></div>
            </a>
            <a href="#">
                <div><i class="fa-solid fa-gamepad"></i></div>
            </a>
            <a href="#">
                <div><i class="fa-solid fa-keyboard"></i></div>
            </a>
            <a href="#">
                <div><i class="fa-solid fa-computer-mouse"></i></div>
            </a>
            <a href="#">
                <div><i class="fa-solid fa-headphones-simple"></i></div>
            </a>
            <a href="#">
                <div><i class="fa-solid fa-desktop"></i></div>
            </a>
            <a href="#">
                <div><i class="fa-solid fa-microchip"></i></div>
            </a>
            <a href="#">
                <div><i class="fa-solid fa-hard-drive"></i></div>
            </a>
            <a href="#">
                <div><i class="fa-solid fa-microphone"></i></div>
            </a>
            <a href="#">
                <div><i class="fa-solid fa-ellipsis"></i></div>
            </a>
        </div>
    </section>
    <!-- browse_by_category section -->
    <!-- new_arrival_section -->
    <section class="new_arrival">
        <div class="wrapper">
            <div>
                <h3>Shop New Arrival</h3>
                <a href="#"><button type="button">Shop</button></a>
            </div>
            <div><img src="${contextPath}/resources/products/iqunix 68 he.jpg" alt="Keybaords"></div>
        </div>
    </section>
    <!-- new_arrival_section ends -->

    <h3 class="trnd ">Trending Products</h3>

    <!-- product containers -->
    	<section class="products">
    <div class="product-box">
        <c:forEach var="product" items="${products}">
            <div class="product-container">
                <div class="product-image">
                    <a href="${contextPath}/viewProduct?productId=${product.productId}">
                        <img src="${contextPath}/resources/products/${product.productImage}" alt="${product.productName}">
                    </a>
                </div>
                <div class="product-info">
                    <h4>${product.productName}</h4>
                    <p>Category: ${product.category}</p>
                    <h5>Rs.${product.price}</h5>
                    <a href="${contextPath}/viewProduct?productId=${product.productId}">
                        <button type="button"><i class="fa-solid fa-eye"></i></button>
                    </a>
                </div>
            </div>
        </c:forEach>
    </div>
</section>
    <!-- product containers ends -->
	<jsp:include page="footer.jsp" />

</body>

</html>