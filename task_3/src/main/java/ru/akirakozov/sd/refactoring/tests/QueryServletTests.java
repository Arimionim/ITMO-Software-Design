package ru.akirakozov.sd.refactoring.tests;

import org.junit.jupiter.api.Test;
import ru.akirakozov.sd.refactoring.Product;
import ru.akirakozov.sd.refactoring.servlet.QueryServlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class QueryServletTests extends ServletTests {
    @Test
    void shouldReturnMostExpensiveProduct() throws SQLException, IOException {
        QueryServlet servlet = new QueryServlet(model);

        when(getRequest().getParameter("command")).thenReturn("max");
        List<Product> products = Arrays.asList(
                new Product("alex", 456),
                new Product("sasha", 123)
        );

        when(model.getProductsOrderedByPrice(true)).thenReturn(products);

        servlet.doGet(getRequest(), getResponse());

        assertEquals(
                "<html><body>\n" +
                        "<h1>Product with max price: </h1>\n" +
                        products.get(0).getName() + "\t" + products.get(0).getPrice() + "</br>\n" +
                        "</body></html>\n",
                getResponseText()
        );
    }

    @Test
    void shouldReturnCheapestProduct() throws SQLException, IOException {
        QueryServlet servlet = new QueryServlet(model);

        when(getRequest().getParameter("command")).thenReturn("min");
        List<Product> products = Arrays.asList(
                new Product("sasha", 123),
                new Product("alex", 456)
        );

        when(model.getProductsOrderedByPrice(false)).thenReturn(products);
        servlet.doGet(getRequest(), getResponse());

        assertEquals(
                "<html><body>\n" +
                        "<h1>Product with min price: </h1>\n" +
                        products.get(0).getName() + "\t" + products.get(0).getPrice() + "</br>\n" +
                        "</body></html>\n",
                getResponseText()
        );
    }

    @Test
    void shouldReturnPriceSum() throws SQLException, IOException {
        QueryServlet servlet = new QueryServlet(model);

        when(getRequest().getParameter("command")).thenReturn("sum");

        when(model.getSum()).thenReturn(579);
        servlet.doGet(getRequest(), getResponse());

        assertEquals(
                "<html><body>\n" +
                        "Summary price: \n" +
                        "579</br>\n" +
                        "</body></html>\n",
                getResponseText()
        );
    }

    @Test
    void shouldReturnCount() throws SQLException, IOException {
        QueryServlet servlet = new QueryServlet(model);

        when(getRequest().getParameter("command")).thenReturn("count");

        when(model.getCount()).thenReturn(420);

        servlet.doGet(getRequest(), getResponse());

        assertEquals(
                "<html><body>\n" +
                        "Number of products: \n" +
                        "420</br>\n" +
                        "</body></html>\n",
                getResponseText()
        );
    }
}
