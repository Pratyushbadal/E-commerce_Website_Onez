<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
  <title>Your Wishlist - GameHub</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/wishlist.css" />
</head>
<body>

<jsp:include page="header.jsp" />
  <div class="main-content">
    <div class="wishlist-header">
      <h1>Your Wishlist</h1>
      <p>Items you’re saving for later.</p>
    </div>

    <section class="wishlist-section">
      <div class="wishlist-grid">
        <div class="wishlist-card">
          <img src="${pageContext.request.contextPath}/resources/product/Lamzu Maya.jpg" alt="Product 1">
          <h3>Gaming Mouse</h3>
          <p>$29.99</p>
        </div>
        <div class="wishlist-card">
          <img src="${pageContext.request.contextPath}/resources/product/hyperx cloud ii.jpg" alt="Product 2">
          <h3>RGB Headset</h3>
          <p>$59.99</p>
        </div>
        <div class="wishlist-card">
          <img src="${pageContext.request.contextPath}/resources/product/iqunix 68 he.jpg" alt="Product 3">
          <h3>Mechanical Keyboard</h3>
          <p>$99.99</p>
        </div>
      </div>
    </section>

    
  </div>
<jsp:include page="footer.jsp" />
</body>
</html>
