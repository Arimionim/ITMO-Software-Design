package ru.akirakozov.sd.refactoring;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Model {
    Model() {
        this("test.db");
    }

    public Model(String file) {
        try {
            c = DriverManager.getConnection("jdbc:sqlite:" + file);
            init();
        } catch (SQLException e) {
            throw new RuntimeException("Can't connect to database with error: " + e.getMessage());
        }
    }

    public void init() {
        String sql = "CREATE TABLE IF NOT EXISTS PRODUCT" +
                "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                " NAME           TEXT    NOT NULL, " +
                " PRICE          INT     NOT NULL)";

        execUpdateSQL(sql);
    }

    public List<Product> getProductsOrderedByPrice(boolean desc) throws SQLException {
        String sql;
        if (desc) {
            sql = "SELECT * FROM PRODUCT ORDER BY PRICE DESC LIMIT 1";
        } else {
            sql = "SELECT * FROM PRODUCT ORDER BY PRICE LIMIT 1";
        }
        ResultSet rs = execQuerySQL(sql);
        List<Product> products = extractProducts(rs);
        rs.close();
        return products;
    }

    public void addProduct(String name, long price) {
        String sql = "INSERT INTO PRODUCT " +
                "(NAME, PRICE) VALUES (\"" + name + "\"," + price + ")";

        execUpdateSQL(sql);
    }

    public int getSum() throws SQLException {
        return execQuerySQL("SELECT SUM(price) FROM PRODUCT").getInt(0);
    }

    public int getCount() throws SQLException {
        return execQuerySQL("SELECT COUNT(*) FROM PRODUCT").getInt(0);
    }

    public List<Product> getProducts() throws SQLException {
        ResultSet rs = execQuerySQL("SELECT * FROM PRODUCT");
        List<Product> products = extractProducts(rs);
        rs.close();
        return products;
    }

    private static List<Product> extractProducts(ResultSet rs) throws SQLException {
        ArrayList<Product> products = new ArrayList<>();
        while (rs.next()) {
            String name = rs.getString("name");
            int price = rs.getInt("price");
            products.add(new Product(name, price));
        }
        return products;
    }

    public ResultSet execQuerySQL(String sql) {
        try {
            Statement stmt = c.createStatement();
            return stmt.executeQuery(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Can't error occurred while exec SQL query: " + e.getMessage());
        }
    }

    public void execUpdateSQL(String sql) {
        try {
            Statement stmt = c.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException("Can't error occurred while exec SQL update: " + e.getMessage());
        }
    }

    private final Connection c;
}
