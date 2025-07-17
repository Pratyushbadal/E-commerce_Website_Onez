<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="jakarta.servlet.http.HttpSession"%>
<%@ page import="jakarta.servlet.http.HttpServletRequest"%>

<%
// Initialize necessary objects and variables
HttpSession userSession = request.getSession(false);
String currentUser = (String) (userSession != null ? userSession.getAttribute("username") : null);
// need to add data in attribute to select it in JSP code using JSTL core tag
pageContext.setAttribute("currentUser", currentUser);
%>
<html>
<head>
    <title>Product Management</title>
     <c:set var="contextPath" value="${pageContext.request.contextPath}" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/adminDashboard.css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/products.css" />
</head>
<body>
    <div class="container">
        <!-- Sidebar jsp file connection -->
        <jsp:include page="sideBar.jsp"/>
        
        <!-- Main Content -->
        <main class="dashboard" style="padding-left: 50px;">
            <h1>Product Management</h1>
            <c:if test="${not empty error}">
                <div class="error">${error}</div>
            </c:if>
            <c:if test="${not empty success}">
                <div class="success">${success}</div>
            </c:if>
            
            <!-- Toggle button for add product form -->
            <div class="toggle-form">
                <button onclick="toggleAddForm()">
                    <i class="fas fa-plus"></i> Add New Product
                </button>
            </div>

            <!-- Add Product Form (initially hidden) -->
            <div id="addProductForm" class="add-product-form" style="display: ${(not empty error or not empty productToEdit) ? 'block' : 'none'};">
		    <h2>${not empty productToEdit ? 'Edit' : 'Add'} Product Details</h2>
		    <form action="${contextPath}/products" method="post" enctype="multipart/form-data">
		        <input type="hidden" name="action" value="${not empty productToEdit ? 'update' : 'add'}">
		        <c:if test="${not empty productToEdit}">
		            <input type="hidden" name="productId" value="${productToEdit.productId}">
		        </c:if>
		        
		        <div class="form-row">
		            <div class="form-group">
		                <label for="productName">Product Name:</label>
		                <input type="text" id="productName" name="productName" 
		                    value="${not empty productToEdit ? productToEdit.productName : productName}" required>
		            </div>
		            
		            <div class="form-group">
		                <label for="category">Category:</label>
		                <select id="category" name="category" required>
		                    <option value="">Select Category</option>
		                    <option value="Keyboard" ${(not empty productToEdit && productToEdit.category == 'Keyboard') || category == 'Keyboard' ? 'selected' : ''}>Keyboard</option>
		                    <option value="Mouse" ${(not empty productToEdit && productToEdit.category == 'Mouse') || category == 'Mouse' ? 'selected' : ''}>Mouse</option>
		                    <option value="Headphone" ${(not empty productToEdit && productToEdit.category == 'Headphone') || category == 'Headphone' ? 'selected' : ''}>Headphone</option>
		                    <option value="CPU" ${(not empty productToEdit && productToEdit.category == 'CPU') || category == 'CPU' ? 'selected' : ''}>CPU</option>
		                    <option value="GPU" ${(not empty productToEdit && productToEdit.category == 'GPU') || category == 'GPU' ? 'selected' : ''}>GPU</option>
		                	<option value="Monitor" ${(not empty productToEdit && productToEdit.category == 'Monitor') || category == 'Monitor' ? 'selected' : ''}>Monitor</option>
		                    <option value="Mousepad" ${(not empty productToEdit && productToEdit.category == 'Mousepad') || category == 'Mousepad' ? 'selected' : ''}>Mousepad</option>
		                    <option value="Other" ${(not empty productToEdit && productToEdit.category == 'Other') || category == 'Other' ? 'selected' : ''}>Other</option>
		                </select>
		            </div>
		        </div>
		        
		        <!-- Similar updates for other fields -->
		        <div class="form-row">
		            <div class="form-group">
		                <label for="price">Price (Rs.):</label>
		                <input type="number" id="price" name="price" step="5" min="0" 
		                    value="${not empty productToEdit ? productToEdit.price : price}" required>
		            </div>
		            
		            <div class="form-group">
		                <label for="quantity">Quantity:</label>
		                <input type="number" id="quantity" name="quantity" min="0" 
		                    value="${not empty productToEdit ? productToEdit.quantity : quantity}" required>
		            </div>
		        </div>
		        
		        <div class="form-group full-width">
		            <label for="description">Description:</label>
		            <textarea id="description" name="description" rows="3" required>${not empty productToEdit ? productToEdit.description : description}</textarea>
		        </div>
		        
		        <div class="form-group full-width">
		            <label for="productImage">Product Image:</label>
		            <input type="file" id="productImage" name="productImage" ${empty productToEdit ? 'required' : ''}>
		            <c:if test="${not empty productToEdit}">
		                <div class="current-image">
		                    <p>Current Image:</p>
		                    <img src="${contextPath}/resources/product/${productToEdit.productImage}" width="100" onerror="this.src='${contextPath}/resources/logo/onez.svg'">
		                </div>
		            </c:if>
		        </div>
		        
		        <div class="form-actions">
		            <button type="submit">
		                <i class="fas fa-save"></i> ${not empty productToEdit ? 'Update' : 'Add'} Product
		            </button>
		            <button type="button" onclick="toggleAddForm()">
		                <i class="fas fa-times"></i> Cancel
		            </button>
		        </div>
		    </form>
		</div>

            <!-- Products Table -->
            <table class="cart-table">
                <thead>
                    <tr>
                        <th>Image</th>
                        <th>Name</th>
                        <th>Description</th>
                        <th>Price</th>
                        <th>Quantity</th>
                        <th>Category</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="product" items="${products}">
                        <tr>
                            <td><img src="${contextPath}/resources/product/${product.productImage}" width="60" height="60" onerror="this.src='${contextPath}/resources/logo/onez.svg'"></td>
                            <td>${product.productName}</td>
                            <td class="description-cell">${product.description}</td>
                            <td>$${product.price}</td>
                            <td>${product.quantity}</td>
                            <td>${product.category}</td>
                            <td>
                                <div class="action-buttons">
                                    <a href="${contextPath}/products?action=edit&id=${product.productId}">
                                        <button class="edit-btn">
                                            <i class="fas fa-edit"></i> Edit
                                        </button>
                                    </a>
                                    <a href="${contextPath}/products?action=delete&id=${product.productId}" onclick="return confirm('Are you sure you want to delete this product?');">
                                        <button class="delete-btn">
                                            <i class="fas fa-trash"></i> Delete
                                        </button>
                                    </a>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </main>
    </div>

    <script>
    function toggleAddForm() {
        const form = document.getElementById('addProductForm');
        form.style.display = form.style.display === 'none' ? 'block' : 'none';
        
        if (form.style.display === 'block') {
            form.scrollIntoView({ behavior: 'smooth' });
        }
    }

    // Show form if in edit mode or if there are errors
    <c:if test="${not empty error or not empty productToEdit}">
        document.getElementById('addProductForm').style.display = 'block';
        document.getElementById('addProductForm').scrollIntoView({ behavior: 'smooth' });
    </c:if>
    </script>
</body>
</html>