package com.onez.service;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import com.onez.config.DbConfig;
import com.onez.model.UserModel;

/**
 * Service class for interacting with the database to retrieve and update
 * user profile information.
 */
public class UserDashboardService {

    private Connection dbConn;
    private boolean isConnectionError = false;

    public UserDashboardService() {
        try {
            dbConn = DbConfig.getDbConnection();
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            isConnectionError = true;
        }
    }

    /**
     * Retrieves user information for the specified user ID.
     * 
     * @param userId The ID of the user to retrieve
     * @return UserModel containing user information, or null if not found
     */
    public UserModel getUserInfo(int userId) {
        if (isConnectionError) {
            System.out.println("Connection Error!");
            return null;
        }

        String query = "SELECT user_id, first_name, last_name, email, number, dob " +
                      "FROM user WHERE user_id = ?";
        
        try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet result = stmt.executeQuery();

            if (result.next()) {
                UserModel user = new UserModel();
                user.setId(result.getInt("user_id"));
                user.setFirstName(result.getString("first_name"));
                user.setLastName(result.getString("last_name"));
                user.setEmail(result.getString("email"));
                user.setNumber(result.getString("number"));
                
                Date dob = result.getDate("dob");
                if (dob != null) {
                    user.setDob(dob.toLocalDate());
                }
                
                return user;
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Updates user information in the database.
     * 
     * @param user The UserModel containing updated information
     * @return true if update was successful, false otherwise
     */
    public boolean updateUserInfo(UserModel user) {
    	if (isConnectionError || dbConn == null) {
            System.err.println("Database connection error!");
            return false;
        }


        String updateQuery = "UPDATE user SET first_name = ?, last_name = ?, " +
                            "email = ?, number = ?, dob = ? WHERE user_id = ?";
        
        try (PreparedStatement stmt = dbConn.prepareStatement(updateQuery)) {
            stmt.setString(1, user.getFirstName());
            stmt.setString(2, user.getLastName());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getNumber());
            
            if (user.getDob() != null) {
                stmt.setDate(5, Date.valueOf(user.getDob()));
            } else {
                stmt.setNull(5, java.sql.Types.DATE);
            }
            
            stmt.setInt(6, user.getId());

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}