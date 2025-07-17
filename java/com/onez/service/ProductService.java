package com.onez.service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.onez.config.DbConfig;
import com.onez.model.ProductModel;

public class ProductService {
    
    private Connection dbConn;

    public ProductService() {
        try {
            this.dbConn = DbConfig.getDbConnection();
        } catch (SQLException | ClassNotFoundException ex) {
            System.err.println("Database connection error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public boolean addProduct(ProductModel product) {
        String sql = "INSERT INTO product (productName, description, price, quantity, category, productImage) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = dbConn.prepareStatement(sql)) {
            stmt.setString(1, product.getProductName());
            stmt.setString(2, product.getDescription());
            stmt.setDouble(3, product.getPrice());
            stmt.setInt(4, product.getQuantity());
            stmt.setString(5, product.getCategory());
            stmt.setString(6, product.getProductImage());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error adding product: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateProduct(ProductModel product) {
        String sql = "UPDATE product SET productName = ?, description = ?, price = ?, quantity = ?, category = ?, productImage = ? WHERE product_id = ?";

        try (PreparedStatement stmt = dbConn.prepareStatement(sql)) {
            stmt.setString(1, product.getProductName());
            stmt.setString(2, product.getDescription());
            stmt.setDouble(3, product.getPrice());
            stmt.setInt(4, product.getQuantity());
            stmt.setString(5, product.getCategory());
            stmt.setString(6, product.getProductImage());
            stmt.setInt(7, product.getProductId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating product: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteProduct(int productId) {
        String sql = "DELETE FROM product WHERE product_id = ?";

        try (PreparedStatement stmt = dbConn.prepareStatement(sql)) {
            stmt.setInt(1, productId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting product: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public ProductModel getProductById(int productId) {
        String sql = "SELECT * FROM product WHERE product_id = ?";
        try (PreparedStatement stmt = dbConn.prepareStatement(sql)) {
            stmt.setInt(1, productId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new ProductModel(
                    rs.getInt("product_id"),
                    rs.getString("productName"),
                    rs.getString("description"),
                    rs.getInt("price"),
                    rs.getInt("quantity"),
                    rs.getString("category"),
                    rs.getString("productImage")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving product: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public List<ProductModel> getAllProducts() {
        List<ProductModel> products = new ArrayList<>();
        String sql = "SELECT * FROM product";

        try (Statement stmt = dbConn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                ProductModel product = new ProductModel(
                    rs.getInt("product_id"),
                    rs.getString("productName"),
                    rs.getString("description"),
                    rs.getInt("price"),
                    rs.getInt("quantity"),
                    rs.getString("category"),
                    rs.getString("productImage")
                );
                products.add(product);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving products: " + e.getMessage());
            e.printStackTrace();
        }

        return products;
    }
}
