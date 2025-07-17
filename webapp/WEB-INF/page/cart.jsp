<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="jakarta.servlet.http.HttpSession"%>
<%@ page import="jakarta.servlet.http.HttpServletRequest"%>

<%
HttpSession userSession = request.getSession(false);
String currentUser = (String) (userSession != null ? userSession.getAttribute("username") : null);
pageContext.setAttribute("currentUser", currentUser);
%>

<!DOCTYPE html>
<html>
<head>
    <title>Your Cart - Onez</title>
    <c:set var="contextPath" value="${pageContext.request.contextPath}" />
    <link rel="stylesheet" type="text/css" href="${contextPath}/css/cart.css">
    <script src="https://kit.fontawesome.com/91fb88d05c.js" crossorigin="anonymous"></script>
    <link href="https://cdn.lineicons.com/5.0/lineicons.css" rel="stylesheet" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>
    <jsp:include page="header.jsp"/>
    
    <main class="main-content">
        <div class="cart-container">
            <h1><i class="fas fa-shopping-cart"></i> Your Shopping Cart</h1>

            <c:if test="${not empty error}">
                <div class="error-message">
                    <i class="fas fa-exclamation-circle"></i> ${error}
                </div>
            </c:if>
            <c:if test="${not empty success}">
                <div class="success-message">
                    <i class="fas fa-check-circle"></i> ${success}
                </div>
            </c:if>
			<h1>${user.firstName}</h1>
            <c:if test="${empty cart.items}">
                <div class="empty-cart">
                    <i class="fas fa-shopping-basket"></i>
                    <p>Your cart is empty.</p>
                    <a href="${contextPath}/home" class="continue-shopping">Continue Shopping</a>
                </div>
            </c:if>

            <c:if test="${not empty cart.items}">
                <div class="cart-table-container">
                    <table class="cart-table">
                        <thead>
                            <tr>
                                <th>Product</th>
                                <th>Price</th>
                                <th>Quantity</th>
                                <th>Subtotal</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="item" items="${cart.items}">
                                <tr>
                                    <td class="product-name">
                                        <div class="product-info">
                                            <img src="${contextPath}/resources/product/${item.product.productImage}" 
                                                 alt="${item.product.productName}" 
                                                 class="product-image">
                                            <span>${item.product.productName}</span>
                                        </div>
                                    </td>
                                    <td class="product-price">
                                        <fmt:formatNumber value="${item.product.price}" type="currency" />
                                    </td>
                                    <td class="product-quantity">
                                        <form action="cart/update" method="post" class="quantity-form">
                                            <input type="hidden" name="productId" value="${item.product.productId}" />
                                            <button type="button" class="quantity-btn minus" onclick="changeQuantity(this, -1)">-</button>
                                            <input type="number" name="newQuantity" value="${item.quantity}" 
                                                   min="1" max="${item.product.quantity}" 
                                                   class="quantity-input" onchange="validateQuantity(this)">
                                            <button type="button" class="quantity-btn plus" onclick="changeQuantity(this, 1)">+</button>
                                        </form>
                                    </td>
                                    <td class="product-subtotal">
                                        <fmt:formatNumber value="${item.quantity * item.product.price}" type="currency" />
                                    </td>
                                    <td class="product-actions">
                                        <form action="cart/remove" method="post">
                                            <input type="hidden" name="productId" value="${item.product.productId}" />
                                            <button type="submit" class="remove-btn">
                                                <i class="fas fa-trash-alt"></i> Remove
                                            </button>
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>

                <div class="cart-summary">
                    <div class="summary-details">
                        <h3>Order Summary</h3>
                        <div class="summary-row">
                            <span>Total Items:</span>
                            <span>${cart.totalItems}</span>
                        </div>
                        <div class="summary-row total">
                            <span>Total Price:</span>
                            <span><fmt:formatNumber value="${cart.totalPrice}" type="currency" /></span>
                        </div>
                        <form action="checkout" method="get">
                            <button type="submit" class="checkout-btn">
                                <i class="fas fa-credit-card"></i> Proceed to Checkout
                            </button>
                        </form>
                        <a href="${contextPath}/home" class="continue-shopping">
                            <i class="fas fa-arrow-left"></i> Continue Shopping
                        </a>
                    </div>
                </div>
            </c:if>
        </div>
    </main>

    <jsp:include page="footer.jsp"/>

    <script>
        function changeQuantity(button, change) {
            const form = button.closest('.quantity-form');
            const input = form.querySelector('.quantity-input');
            let newValue = parseInt(input.value) + change;
            
            if (newValue < 1) newValue = 1;
            if (newValue > parseInt(input.max)) newValue = parseInt(input.max);
            
            input.value = newValue;
            form.submit();
        }
        
        function validateQuantity(input) {
            if (input.value < 1) input.value = 1;
            if (input.value > parseInt(input.max)) input.value = parseInt(input.max);
            input.closest('form').submit();
        }
    </script>
</body>
</html>