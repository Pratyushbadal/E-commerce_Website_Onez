<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <link rel="stylesheet" href="hello.css" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Register</title>
    <!-- Set contextPath variable for reuse -->
	<c:set var="contextPath" value="${pageContext.request.contextPath}" />
    <link rel="stylesheet" type="text/css"
		href="${contextPath}/css/register.css" />
    <link href="https://cdn.lineicons.com/3.0/lineicons.css" rel="stylesheet" />

  </head>
  <body>
    <div class="register-container">
      <img src="${contextPath}/resources/logo/logo.png" alt="Logo" class="logo" />
      <h3>Register account</h3>
      <!-- Display error message if available -->
		<c:if test="${not empty error}">
			<p class="error-message">${error}</p>
		</c:if>

		<!-- Display success message if available -->
		<c:if test="${not empty success}">
			<p class="success-message">${success}</p>
		</c:if>
      <form class="form" action="${contextPath}/register" method="post"
			enctype="multipart/form-data">
        <div class="form-group-row">
          <div class="form">
            <label>First Name</label>
            <div class="input">
              <input id="firstName" name="firstName" type="text" value="${firstName}" required />
            </div>
          </div>
          <div class="form">
            <label>Last Name</label>
            <div class="input">
              <input id="lastName" name="lastName"type="text" value="${lastName}" required />
            </div>
          </div>
        </div>
        <div class="form-group-row">
          <div class="form">
            <label>Username</label>
            <div class="input">
              <i class="lni lni-user"></i>
              <input id="username" name="username" type="text" value="${username}" required />
            </div>
          </div>
          <div class="form">
            <label>Date of birth</label>
            <div class="input">
              <input id="dob" name="dob" type="date" value="${dob}"required />
            </div>
          </div>
        </div>
        <div class="form-group-row">
          <div class="form">
            <label>Gender</label>
            <div class="input">
              <select id="gender" name="gender" required>
                <option value="">Select Gender</option>
                <option value="male" ${gender == 'male' ? 'selected' : ''}>Male</option>
                <option  value="female" ${gender == 'female' ? 'selected' : ''}>Female</option>
            </select>
            </div>
          </div>
          <div class="form">
            <label>Email</label>
            <div class="input">
              <i class="lni lni-envelope"></i>
              <input id="email" name="email" type="email" value="${email}" required />
            </div>
          </div>
        </div>
        <div class="form-group-row">
          <div class="form">
            <label>Phone number</label>
            <div class="input">
              <i class="lni lni-phone"></i>
              <input id="phoneNumber" name="phoneNumber" type="tel" value="${phoneNumber}" required />
            </div>
          </div>
          <div class="form">
            <label>Address</label>
            <div class="input">
              <i class="lni lni-home"></i>
              <select id="address" name="address" required>
                <option value="">Select Address</option>
                <option value="Kathmandu"
                  ${address == 'Kathmandu' ? 'selected' : ''}>Kathmandu</option>
                <option value="Lalitpur"
                  ${address == 'Lalitpur' ? 'selected' : ''}>Lalitpur</option>
                <option value="Bhaktapur"
                  ${address == 'Bhaktapur' ? 'selected' : ''}>Bhaktapur</option>
              </select>
            </div>
          </div>
        </div>
        <div class="form-group-row">
          <div class="form">
            <label>Password</label>
            <div class="input">
              <i class="lni lni-lock"></i>
              <input id="password" name="password" type="password" required />
            </div>
          </div>
          <div class="form">
            <label>Confirm password</label>
            <div class="input">
              <i class="lni lni-lock"></i>
              <input id="retypePassword" name="retypePassword" type="password" required />
            </div>
          </div>
        </div>
        <div class="form-group-row">
          <div class="form">
            <label>Profile picture:</label>
            <div class="input">
              <input type="file" id="image" name="image" hidden />
              <label for="image" class="upload">Choose File</label>
            </div>
          </div>
        </div>
        <div class="registerbtn">
          <button type="submit">Register</button>
          <p>Already have an account? <a href="${contextPath}/login">Sign in</a></p>
        </div>
      </form>
    </div>
  </body>
</html>
