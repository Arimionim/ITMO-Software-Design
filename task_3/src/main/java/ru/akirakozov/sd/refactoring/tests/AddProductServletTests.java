package ru.akirakozov.sd.refactoring.tests;

import org.junit.jupiter.api.Test;
import ru.akirakozov.sd.refactoring.servlet.AddProductServlet;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class AddProductServletTests extends ServletTests {
    protected static final String DB_FILE = "DatabaseTest.db";

    @Test
    void shouldCreateProduct() throws IOException {
        AddProductServlet servlet = new AddProductServlet(model);

        when(getRequest().getParameter("name")).thenReturn("bread");
        when(getRequest().getParameter("price")).thenReturn("123");

        servlet.doGet(getRequest(), getResponse());

        assertEquals("OK</br>\n", getResponseText());
    }
}
