<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/contact.css" />
    <title>Contact Us</title>
  </head>
  <body>
  
  <jsp:include page="header.jsp" />
  
  <div class="main">
    <div>
      <h1>Contact Us</h1>
      <div class="contact-sidebar">
        <h3>CONTACT ONEZ</h3>
      </div>
    </div>
    <div class="contact-container">
      <div class="contact-section">
        <h2>How to Reach Us</h2>
        <p>
          For inquiries, support, or feedback, feel free to get in touch via the
          following channels:
        </p>
        <ul>
          <li>Email: oneZsupport@gmail.com</li>
          <li>Phone: +977-9813747090</li>
        </ul>
      </div>

      <div class="contact-section">
        <h2>Support Hours</h2>
        <p>Sunday to Friday: 8 am - 5 pm</p>
      </div>

      <div class="contact-section">
        <h2>Feedback</h2>
        <p>
          Please contact us if you have any inconveniences and share your
          thoughts and suggestions to help us improve.
        </p>
      </div>
    </div>
    </div>
    <jsp:include page="footer.jsp" />
  </body>
</html>
