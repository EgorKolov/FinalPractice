package com.company;

import org.sqlite.JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DatabaseManager {
    private static DatabaseManager instance;
    private final Connection connection;
    
    private DatabaseManager(String pathToDB) throws SQLException {
        DriverManager.registerDriver(new JDBC());
        connection = DriverManager.getConnection("jdbc:sqlite:" + pathToDB);
    }
    
    public static synchronized DatabaseManager connect(String pathToDB) throws SQLException {
        if (instance == null)
            instance = new DatabaseManager(pathToDB);
        return instance;
    }
    
    public synchronized void disconnect() throws SQLException {
        if (connection != null) {
            connection.close();
            instance = null;
        }
    }
    
    public ArrayList<Product> selectTopFromProducts(int top, String conditions) {
        var query = "SELECT * FROM Products "
                    + sanitizeConditions(conditions)
                    + (top > 0 ? " LIMIT " + top : "");
        var result = new ArrayList<Product>();
        
        try (ResultSet response = connection.createStatement().executeQuery(query)) {
            while (response.next()) {
                result.add(new Product(response.getString("Name"),
                        response.getString("Article"),
                        response.getDouble("Price"),
                        response.getInt("InStock")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return result;
    }
    
    public ArrayList<Product> selectAllProducts() {
        return selectAllFromProducts(null);
    }
    
    public ArrayList<Product> selectAllFromProducts(String conditions) {
        return selectTopFromProducts(-1, conditions);
    }
    
    public void addProducts(ArrayList<Product> products) {
        var asStrings = products.stream().map(Product::toString).toArray(String[]::new);
        var query = "INSERT INTO Products VALUES " + String.join(", ", asStrings);
        
        try {
            connection.createStatement().executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private String sanitizeConditions(String conditions) {
        if (conditions == null)
            return "";
        var statementEnd = conditions.indexOf(';');
        return conditions.substring(0, statementEnd > -1 ? statementEnd : conditions.length());
    }
}
