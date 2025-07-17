package com.onez.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.onez.config.DbConfig;
import com.onez.model.UserModel;
import com.onez.util.PasswordUtil;

/**
 * Service class for handling login operations. Connects to the database,
 * verifies user credentials, and returns login status.
 */
public class LoginService {

	private Connection dbConn;
	private boolean isConnectionError = false;

	/**
	 * Constructor initializes the database connection. Sets the connection error
	 * flag if the connection fails.
	 */
	public LoginService() {
		try {
			dbConn = DbConfig.getDbConnection();
		} catch (SQLException | ClassNotFoundException ex) {
			ex.printStackTrace();
			isConnectionError = true;
		}
	}

	/**
	 * Validates the user credentials against the database records.
	 *
	 * @param userModel the UserModel object containing user credentials
	 * @return true if the user credentials are valid, false otherwise; null if a
	 *         connection error occurs
	 */
	/**
     * Retrieves the user from the database and validates the password.
     */
	public UserModel loginUser(String username, String password) {
	    if (isConnectionError) {
	        System.out.println("Connection Error!");
	        return null;
	    }

	    String query = "SELECT * FROM user WHERE username = ?";
	    try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
	        stmt.setString(1, username);
	        ResultSet result = stmt.executeQuery();

	        if (result.next()) {
	            String dbPassword = result.getString("password");

	            // Decrypt and compare
	            if (PasswordUtil.decrypt(dbPassword, username).equals(password)) {
	                UserModel user = new UserModel();
	                user.setUserName(result.getString("username"));
	                user.setPassword(dbPassword);
	                user.setUserRole(result.getString("userRole"));
	                user.setId(result.getInt("user_id"));
	                // ✅ Set the image URL
	                user.setImageUrl(result.getString("profilePic"));

	                return user;
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return null;
	}
	

	

}