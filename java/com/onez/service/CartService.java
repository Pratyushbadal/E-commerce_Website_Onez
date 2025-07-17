package com.onez.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.onez.config.DbConfig;
import com.onez.model.CartModel;
import com.onez.model.AddressModel;
import com.onez.model.CartItemModel;
import com.onez.model.ProductModel;
import com.onez.model.UserModel;

/**
 * CartService handles all cart-related database operations including:
 * - Retrieving cart items
 * - Adding products to cart
 * - Removing products from cart
 * - Updating quantities
 * - Calculating cart totals
 */
public class CartService {

    private Connection dbConn;

    /**
     * Constructor initializes the database connection.
     */
    public CartService() {
        try {
            this.dbConn = DbConfig.getDbConnection();
        } catch (SQLException | ClassNotFoundException ex) {
            System.err.println("Database connection error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    /**
     * Retrieves a user's cart with all items.
     *
     * @param userId the ID of the user
     * @return CartModel containing all cart items and totals
     */
    public CartModel getCartByUserId(int userId) {
        if (dbConn == null) {
            System.err.println("Database connection is not available.");
            return null;
        }

        String query = "SELECT c.cart_id, ci.product_id, p.productName, p.price, ci.productQuantity, p.quantity " +
                       "FROM cart c " +
                       "JOIN cartItem ci ON c.cart_id = ci.cart_id " +
                       "JOIN product p ON ci.product_id = p.product_id " +
                       "WHERE c.user_id = ?";

        CartModel cart = new CartModel();
        List<CartItemModel> items = new ArrayList<>();
        int totalItems = 0;
        double totalPrice = 0.0;

        try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int cartId = rs.getInt("cart_id");
                int productId = rs.getInt("product_id");
                String productName = rs.getString("productName");
                double price = rs.getDouble("price");
                int quantity = rs.getInt("productQuantity");
                int stockQuantity = rs.getInt("quantity");

                ProductModel product = new ProductModel();
                product.setProductId(productId);
                product.setProductName(productName);
                product.setPrice(price);
                product.setQuantity(stockQuantity);

                CartItemModel item = new CartItemModel(cartId, product, quantity);
                items.add(item);

                totalItems += quantity;
                totalPrice += (price * quantity);
            }

            cart.setCartId(items.isEmpty() ? 0 : items.get(0).getCartId());
            cart.setItems(items);
            cart.setTotalItems(totalItems);
            cart.setTotalPrice(totalPrice);

        } catch (SQLException e) {
            System.err.println("Error retrieving cart: " + e.getMessage());
            e.printStackTrace();
            return null;
        }

        return cart;
    }

    /**
     * Adds a product to the user's cart or updates quantity if already exists.
     *
     * @param userId the ID of the user
     * @param productId the ID of the product to add
     * @param quantity the quantity to add
     * @return true if successful, false otherwise
     */
 // In CartService.java
    public CartModel addProductToCart(int userId, int productId, int quantity) {
        try {
            int cartId = getOrCreateCartId(userId);
            Integer existingQuantity = getCartItemQuantity(cartId, productId);

            if (existingQuantity != null) {
                updateCartItem(cartId, productId, existingQuantity + quantity);
            } else {
                insertCartItem(cartId, productId, quantity);
            }

            return getCartByUserId(userId);
        } catch (SQLException e) {
            System.err.println("Error adding product to cart: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }


    public CartModel removeProductFromCart(int userId, int productId, int quantity) {
        try {
            int cartId = getCartId(userId);
            if (cartId == 0) return null;

            Integer existingQuantity = getCartItemQuantity(cartId, productId);
            if (existingQuantity == null) return null;

            int newQuantity = existingQuantity - quantity;

            if (newQuantity <= 0) {
                deleteCartItem(cartId, productId);
            } else {
                updateCartItem(cartId, productId, newQuantity);
            }

            return getCartByUserId(userId); // Return the updated cart
        } catch (SQLException e) {
            System.err.println("Error removing product from cart: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Clears all items from the user's cart.
     *
     * @param userId the ID of the user
     * @return true if successful, false otherwise
     */
    public boolean clearCart(int userId) {
        if (dbConn == null) {
            System.err.println("Database connection is not available.");
            return false;
        }

        String query = "DELETE FROM cartItem WHERE cart_id IN (SELECT cart_id FROM cart WHERE user_id = ?)";

        try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error clearing cart: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    private boolean userExists(int userId) throws SQLException {
        String query = "SELECT 1 FROM user WHERE user_id = ?";
        try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            return rs.next(); // true if user exists
        }
    }
    
    // Helper methods

    private int getOrCreateCartId(int userId) throws SQLException {
        if (!userExists(userId)) {
            throw new SQLException("Cannot create cart: User does not exist with userId = " + userId);
        }

        int cartId = getCartId(userId);
        if (cartId == 0) {
            String query = "INSERT INTO cart (user_id, total_items, total_price, createdAt) VALUES (?, ?, ?, ?)";
            try (PreparedStatement stmt = dbConn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
                stmt.setInt(1, userId);
                stmt.setInt(2, 0);
                stmt.setInt(3, 0);
                stmt.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
                stmt.executeUpdate();

                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    cartId = rs.getInt(1);
                }
            }
        }
        return cartId;
    }


    private int getCartId(int userId) throws SQLException {
        String query = "SELECT cart_id FROM cart WHERE user_id = ?";
        try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            return rs.next() ? rs.getInt("cart_id") : 0;
        }
    }

    private Integer getCartItemQuantity(int cartId, int productId) throws SQLException {
        String query = "SELECT productQuantity FROM cartItem WHERE cart_id = ? AND product_id = ?";
        try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
            stmt.setInt(1, cartId);
            stmt.setInt(2, productId);
            ResultSet rs = stmt.executeQuery();
            return rs.next() ? rs.getInt("productQuantity") : null;
        }
    }

    private boolean insertCartItem(int cartId, int productId, int productQuantity) throws SQLException {
        String query = "INSERT INTO cartItem (cart_id, product_id, productQuantity) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
            stmt.setInt(1, cartId);
            stmt.setInt(2, productId);
            stmt.setInt(3, productQuantity);
            return stmt.executeUpdate() > 0;
        }
    }

    private boolean updateCartItem(int cartId, int productId, int productQuantity) throws SQLException {
        String query = "UPDATE cartItem SET productQuantity = ? WHERE cart_id = ? AND product_id = ?";
        try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
            stmt.setInt(1, productQuantity);
            stmt.setInt(2, cartId);
            stmt.setInt(3, productId);
            return stmt.executeUpdate() > 0;
        }
    }

    private boolean deleteCartItem(int cartId, int productId) throws SQLException {
        String query = "DELETE FROM cartItem WHERE cart_id = ? AND product_id = ?";
        try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
            stmt.setInt(1, cartId);
            stmt.setInt(2, productId);
            return stmt.executeUpdate() > 0;
        }
    }
    
}