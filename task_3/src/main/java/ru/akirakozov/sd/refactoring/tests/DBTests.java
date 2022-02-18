package ru.akirakozov.sd.refactoring.tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.akirakozov.sd.refactoring.Model;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class DBTests {
    protected static final String DB_FILE = "DatabaseTest.db";
    private Model model;

    @BeforeAll
    static void cleanup() {
        File dbFile = new File(DB_FILE);
        if (dbFile.exists()) {
            assertTrue(dbFile.delete());
        }
    }

    @AfterEach
    void cleanupAfterTest() {
        cleanup();
    }

    @BeforeEach
    void initialize() {
        this.model = new Model(DB_FILE);
    }

    @Test
    void shouldCreateDatabase() {
        File dbFile = new File(DB_FILE);
        assertTrue(dbFile.exists());
    }

    @Test
    void shouldInitialize() throws SQLException {
        model.init();
        assertEquals(0, model.execQuerySQL("SELECT COUNT(*) FROM product").getInt(1));
    }

    @Test
    void shouldUpdate() throws SQLException {
        model.init();
        model.execUpdateSQL("INSERT INTO product (name, price) VALUES ('name', 123)");
        ResultSet rs = model.execQuerySQL("SELECT * FROM product");
        String name = rs.getString("name");
        int price = rs.getInt("price");
        assertEquals("name", name);
        assertEquals(123, price);
    }
}
