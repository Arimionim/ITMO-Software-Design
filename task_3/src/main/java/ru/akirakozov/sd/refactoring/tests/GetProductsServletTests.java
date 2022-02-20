package ru.akirakozov.sd.refactoring.tests;

import org.junit.jupiter.api.Test;
import ru.akirakozov.sd.refactoring.Product;
import ru.akirakozov.sd.refactoring.servlet.GetProductsServlet;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class GetProductsServletTests extends ServletTests {
    @Test
    void shouldFormatProducts() throws SQLException {
        GetProductsServlet servlet = new GetProductsServlet(model);

        List<Product> products = Arrays.asList(
                Product.getRandomProduct(),
                Product.getRandomProduct()
        );

        when(model.getProducts()).thenReturn(products);
        servlet.doGet(getRequest(), getResponse());

        assertEquals(
                "<html><body>\n" +
                        products.get(0).getName() + "\t" + products.get(0).getPrice() + "</br>\n" +
                        products.get(1).getName() + "\t" + products.get(1).getPrice() + "</br>\n" +
                        "</body></html>\n",
                getResponseText()
        );
    }
}
