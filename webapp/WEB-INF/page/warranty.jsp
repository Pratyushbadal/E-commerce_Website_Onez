<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/warranty.css" />
    <title>Warranty Policy</title>
  </head>
  <body>
  <jsp:include page="header.jsp" />
  <div class="main">
    <div>
      <h1>Warranty Policy</h1>
      <div class="warranty-sidebar">
        <h3>WARRANTY POLICY OF ONEZ</h3>
      </div>
    </div>
    <div class="warranty-container">
      <div class="warranty-section">
        <h2>Coverage</h2>
        <ul>
          <li>All products are covered by a 1-year limited warranty.</li>
          <li>Warranty covers manufacturing defects only.</li>
        </ul>
      </div>

      <div class="warranty-section">
        <h2>Exclusions</h2>
        <p>
          The warranty does not cover damage due to misuse, accidents,
          unauthorized modifications, or normal wear and tear.
        </p>
      </div>

      <div class="warranty-section">
        <h2>Claims Process</h2>
        <p>
          To file a claim, contact our support team with proof of purchase and a
          detailed description of the issue. We will provide instructions on how
          to proceed.
        </p>
      </div>
    </div>
    </div>
    <jsp:include page="footer.jsp" />
  </body>
</html>
