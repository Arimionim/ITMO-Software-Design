package ru.akirakozov.sd.refactoring;

import java.sql.*;

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

    public ResultSet getProductsOrderedByPlace(boolean desc) {
        String sql;
        if (desc) {
            sql = "SELECT * FROM PRODUCT ORDER BY PRICE DESC LIMIT 1";
        } else {
            sql = "SELECT * FROM PRODUCT ORDER BY PRICE LIMIT 1";
        }

        return execQuerySQL(sql);
    }

    public void addProduct(String name, long price) {
        String sql = "INSERT INTO PRODUCT " +
                "(NAME, PRICE) VALUES (\"" + name + "\"," + price + ")";

        execUpdateSQL(sql);
    }

    public ResultSet getSum() {
        return execQuerySQL("SELECT SUM(price) FROM PRODUCT");
    }

    public ResultSet getCount() {
        return execQuerySQL("SELECT COUNT(*) FROM PRODUCT");
    }

    public ResultSet getProducts() {
        return execQuerySQL("SELECT * FROM PRODUCT");
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
