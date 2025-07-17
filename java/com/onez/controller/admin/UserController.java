package com.onez.controller.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

import com.onez.model.AddressModel;
import com.onez.model.UserModel;
import com.onez.service.AdminDashboardService;
import com.onez.util.ValidationUtil;

/**
 * Servlet implementation class userController
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/modifyUsers" })
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
		maxFileSize = 1024 * 1024 * 10, // 10MB
		maxRequestSize = 1024 * 1024 * 50) // 50MB
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// Instance of DashboardService for handling business logic
	private AdminDashboardService dashboardService;

	/**
	 * Default constructor initializes the DashboardService instance.
	 */
	public UserController() {
		this.dashboardService = new AdminDashboardService();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Retrieve all user information from the DashboardService
		request.setAttribute("userList", dashboardService.getAllUsersInfo());

		request.setAttribute("total", dashboardService.getTotalUsers());
		request.setAttribute("Kathmandu", dashboardService.getKathmanduUsers());
		request.setAttribute("Lalitpur", dashboardService.getLalitpurUsers());
		request.setAttribute("Bhaktapur", dashboardService.getBhaktapurUsers());
		// Forward the request to the Users JSP for rendering
		request.getRequestDispatcher("/WEB-INF/page/admin/users.jsp").forward(request, response);
	}

	/**
	 * Handles HTTP POST requests for various actions such as update, delete, or
	 * redirecting to the update form. Processes the request parameters based on the
	 * specified action.
	 * 
	 * @param request  The HttpServletRequest object containing the request data.
	 * @param response The HttpServletResponse object used to return the response.
	 * @throws ServletException If an error occurs during request processing.
	 * @throws IOException      If an input or output error occurs.
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");
		int userId = Integer.parseInt(request.getParameter("id"));

		switch (action) {
		case "updateForm":
			handleUpdateForm(request, response, userId);
			break;

		case "update":
			handleUpdate(request, response, userId);
			break;

		case "delete":
			handleDelete(request, response, userId);
			break;

		default:
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Unknown action: " + action);
		}
	}

	/**
	 * Handles the update form action by setting user data in the session and
	 * redirecting to the update page.
	 * 
	 * @param request   The HttpServletRequest object containing the request data.
	 * @param response  The HttpServletResponse object used to return the response.
	 * @param userId The ID of the user to be updated.
	 * @throws IOException If an input or output error occurs.
	 */
	private void handleUpdateForm(HttpServletRequest request, HttpServletResponse response, int userId)
			throws ServletException, IOException {
		// Retrieve the user information from the service
		UserModel user = dashboardService.getSpecificUserInfo(userId);

		if (user != null) {
			// Store the user object in the session
			request.getSession().setAttribute("user", user);

			// Redirect to the update URL
			response.sendRedirect(request.getContextPath() + "/userUpdate");
		} else {
			// Handle case where user info is not found
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "User not found with ID: " + userId);
		}
	}

	/**
	 * Handles the update action by processing user data and updating it through
	 * the DashboardService. Redirects to the dashboard page upon completion.
	 * 
	 * @param request   The HttpServletRequest object containing the request data.
	 * @param response  The HttpServletResponse object used to return the response.
	 * @param userId The ID of the user to be updated.
	 * @throws ServletException If an error occurs during request processing.
	 * @throws IOException      If an input or output error occurs.
	 */
	private void handleUpdate(HttpServletRequest request, HttpServletResponse response, int userId)
			throws ServletException, IOException {
		// Validate form parameters
		String validationMessage = validateUpdateForm(request);
		if (validationMessage != null) {
			request.setAttribute("error", validationMessage);
			doGet(request, response); // Reload the page with the error message
			return;
		}

		// Retrieve form parameters
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String userName = request.getParameter("username");
		String dobStr = request.getParameter("dob");
		LocalDate dob = LocalDate.parse(dobStr); // Convert date string to LocalDate
		String gender = request.getParameter("gender");
		String email = request.getParameter("email");
		String number = request.getParameter("phoneNumber");
		String addressName = request.getParameter("address");
		String imageUrl = request.getParameter("image"); // Assuming image upload is handled elsewhere

		// Create a AddressModel object
		AddressModel address = new AddressModel();
		address.setName(addressName);

		// Create a userModel object
		UserModel user = new UserModel(userId, firstName, lastName, userName, dob, gender, email, number,
				null, address, imageUrl);

		// Update the user using DashboardService
		boolean success = dashboardService.updateUser(user);

		// Handle the result of the update operation
		if (success) {
			request.setAttribute("success", "User information updated successfully.");
		} else {
			request.setAttribute("error", "Failed to update user information.");
		}

		// Forward to the dashboard to reflect changes
		doGet(request, response);
	}

	/**
	 * Validates the form fields for the update operation.
	 * 
	 * @param request The HttpServletRequest object containing the request data.
	 * @return A validation error message, or null if all validations pass.
	 */
	private String validateUpdateForm(HttpServletRequest request) {
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String username = request.getParameter("username");
		String dobStr = request.getParameter("dob");
		String gender = request.getParameter("gender");
		String email = request.getParameter("email");
		String number = request.getParameter("phoneNumber");
		String address = request.getParameter("address");

		// Check for null or empty fields first
		if (ValidationUtil.isNullOrEmpty(firstName))
			return "First name is required.";
		if (ValidationUtil.isNullOrEmpty(lastName))
			return "Last name is required.";
		if (ValidationUtil.isNullOrEmpty(username))
			return "Username is required.";
		if (ValidationUtil.isNullOrEmpty(dobStr))
			return "Date of birth is required.";
		if (ValidationUtil.isNullOrEmpty(gender))
			return "Gender is required.";
		if (ValidationUtil.isNullOrEmpty(email))
			return "Email is required.";
		if (ValidationUtil.isNullOrEmpty(number))
			return "Phone number is required.";
		if (ValidationUtil.isNullOrEmpty(address))
			return "Address is required.";

		// Convert date of birth
		LocalDate dob;
		try {
			dob = LocalDate.parse(dobStr);
		} catch (Exception e) {
			return "Invalid date format. Please use YYYY-MM-DD.";
		}

		// Validate fields
		if (!ValidationUtil.isAlphanumericStartingWithLetter(username))
			return "Username must start with a letter and contain only letters and numbers.";
		if (!ValidationUtil.isValidGender(gender))
			return "Gender must be 'male' or 'female'.";
		if (!ValidationUtil.isValidEmail(email))
			return "Invalid email format.";
		if (!ValidationUtil.isValidPhoneNumber(number))
			return "Phone number must be 10 digits and start with 98.";

		// Check if the date of birth is at least 16 years before today
		if (!ValidationUtil.isAgeAtLeast16(dob))
			return "You must be at least 16 years old.";

		// Assuming image validation is handled elsewhere
		// return null if all validations pass
		return null;
	}

	/**
	 * Handles the delete action by removing a user from the database and
	 * forwarding to the dashboard page.
	 * 
	 * @param request   The HttpServletRequest object containing the request data.
	 * @param response  The HttpServletResponse object used to return the response.
	 * @param userId The ID of the user to be deleted.
	 * @throws ServletException If an error occurs during request processing.
	 * @throws IOException      If an input or output error occurs.
	 */
	private void handleDelete(HttpServletRequest request, HttpServletResponse response, int userId)
			throws ServletException, IOException {
		boolean success = dashboardService.deleteUser(userId);

		if (success) {
			System.out.println("Deletion successful");
		} else {
			System.out.println("Deletion failed");
		}

		// Forward to the dashboard to reflect changes
		doGet(request, response);
	}
}