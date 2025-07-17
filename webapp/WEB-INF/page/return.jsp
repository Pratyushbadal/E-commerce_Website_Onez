<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/return.css" />
    <title>Return Policy</title>
  </head>
  <body>
  <jsp:include page="header.jsp" />
  <div class="main">
    <div>
      <h1>Return Policy</h1>
      <div class="return-sidebar">
        <h3>RETURN POLICY OF ONEZ</h3>
      </div>
    </div>
    <div class="return-container">
      <div class="return-section">
        <h2>Eligibility for Returns</h2>
        <ul>
          <li>Items must be returned within 30 days of purchase.</li>
          <li>Product must be unused and in original packaging.</li>
          <li>Proof of purchase is required for all returns.</li>
        </ul>
      </div>

      <div class="return-section">
        <h2>Non-returnable Items</h2>
        <p>
          Certain items such as perishable goods, custom products, and digital
          downloads are not eligible for return.
        </p>
      </div>

      <div class="return-section">
        <h2>Refunds</h2>
        <p>
          Once we receive your item, we will inspect it and notify you of the
          status. Approved returns will be refunded via the original payment
          method.
        </p>
      </div>
    </div>
    </div>
    <jsp:include page="footer.jsp" />
  </body>
</html>
