<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Products</title>
    <!-- Set contextPath variable -->
	<c:set var="contextPath" value="${pageContext.request.contextPath}" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/viewCategory.css">
    <link href="https://cdn.lineicons.com/5.0/lineicons.css" rel="stylesheet" />
    <script src="https://kit.fontawesome.com/91fb88d05c.js" crossorigin="anonymous"></script>
</head>

<body>
    <jsp:include page="header.jsp" />

    <div class="main-content">
        <aside class="left-panel">
            <div class="category-title">
                <h3>Category:</h3>
            </div>
            <ul class="category-list">
                <li class="category-items">
                    <input type="checkbox" id="all" name="category" checked>
                    <label for="all">ALL</label>
                </li>
                <li class="category-items">
                    <input type="checkbox" id="consoles" name="category">
                    <label for="consoles">Consoles</label> <!--for="consoles" is used so when press on 
                    text inside label it will activate checkbox with id="consoles" -->
                </li>
                <li class="category-items">
                    <input type="checkbox" id="keyboards" name="category">
                    <label for="keyboards">Keyboards</label>
                </li>
                <li class="category-items">
                    <input type="checkbox" id="mouse" name="category">
                    <label for="mouse">Mouse</label>
                </li>
                <li class="category-items">
                    <input type="checkbox" id="headset" name="category">
                    <label for="headset">Headset</label>
                </li>
                <li class="category-items">
                    <input type="checkbox" id="monitor" name="category">
                    <label for="monitor">Monitor</label>
                </li>
                <li class="category-items">
                    <input type="checkbox" id="cpu-gpu" name="category">
                    <label for="cpu-gpu">CPU/GPU</label>
                </li>
                <li class="category-items">
                    <input type="checkbox" id="storage" name="category">
                    <label for="storage"></label>Storage</label>
                </li>
                <li class="category-items">
                    <input type="checkbox" id="accessories" name="category">
                    <label for="accessories">Accessories</label>
                </li>
            </ul>
        </aside>

        <!-- right panel -->
        <section class="right-panel">
            <div class="select-price">
                <div class="text">
                    <h3>All</h3>
                </div>
                <div class="selection">
                    <label for="sort-by">Sort By:</label>
                    <select name="sort-by" id="sort-by">
                        <option value="default">Default</option>
                        <option value="priceHigh">High-Low</option>
                        <option value="priceLow">Low-High</option>
                    </select>
                </div>
                <div class="category">
                    <div class="category-selection">
                        <label for="category">Category:</label>
                        <select name="sort-by" id="category">
                            <option value="All">All</option>
                            <option value="Consoles">Consoles</option>
                            <option value="Keyboards">Keyboards</option>
                            <option value="Mouse">Mouse</option>
                            <option value="Headset">Headset</option>
                            <option value="Monitor">Monitor</option>
                            <option value="CPU/GPU">CPU/GPU</option>
                            <option value="Storages">Storages</option>
                            <option value="Accessories">Accessories</option>
                        </select>
                    </div>
                </div>
            </div>



            <section>
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
        </section>
    </div>

            <jsp:include page="footer.jsp" />
</body>

</html>