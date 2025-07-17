<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>${product.productName}</title>
    <!-- Set contextPath variable -->
	<c:set var="contextPath" value="${pageContext.request.contextPath}" />
	<link rel="stylesheet" type="text/css"
		href="${pageContext.request.contextPath}/css/header.css" />
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/css/viewProduct.css" />
          <link rel="stylesheet" type="text/css"
		href="${pageContext.request.contextPath}/css/footer.css" />
    <script src="https://kit.fontawesome.com/91fb88d05c.js" crossorigin="anonymous"></script>
    <link href="https://cdn.lineicons.com/5.0/lineicons.css" rel="stylesheet" />
</head>
<body>
<jsp:include page="header.jsp" />

<main class="main-content">
	<div class="view-container">
	    <div class="view-img">
	        <img src="${contextPath}/resources/product/${product.productImage}" 
	             alt="${product.productName}" />
	    </div>
	    <div class="view-details">
	        <h2>${product.productName}</h2>
	        <div class="price">Rs.${product.price}</div>
	        <div class="desc">${product.description}</div>
	        <p><strong>Category:</strong> ${product.category}</p>
	        <p><strong>Available Quantity:</strong> ${product.quantity}</p>
	
	        <!-- Quantity and Buttons -->
	        <form action="${contextPath}/cart/add" method="post" class="product-actions">
	            <input type="hidden" name="productId" value="${product.productId}" />
	
	            <div class="quantity-control">
	                <label for="quantity">Quantity:</label>
	                <button type="button" onclick="decreaseQty()">-</button>
	                <input type="number" id="quantity" name="quantity" value="1" min="1" max="${product.quantity}" />
	                <button type="button" onclick="increaseQty()">+</button>
	            </div>
	
	            <div class="product-actions">
	                <button type="submit" class="add-to-cart">Add to Cart</button>
	                <button type="submit" class="buy-now" formaction="${contextPath}/buyNow">Buy Now</button>
	            </div>
	        </form>
	    </div>
	</div>

</main>

<jsp:include page="footer.jsp" />

<script>
    function increaseQty() {
        const input = document.getElementById("quantity");
        const max = parseInt(input.max);
        let current = parseInt(input.value);
        if (current < max) input.value = current + 1;
    }

    function decreaseQty() {
        const input = document.getElementById("quantity");
        let current = parseInt(input.value);
        if (current > 1) input.value = current - 1;
    }
</script>

</body>
</html>
