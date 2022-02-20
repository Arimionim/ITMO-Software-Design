package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.HTMLBuilder;
import ru.akirakozov.sd.refactoring.Model;
import ru.akirakozov.sd.refactoring.Product;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.List;

/**
 * @author akirakozov
 */
public class QueryServlet extends HttpServlet {
    public QueryServlet(Model model) {
        this.model = model;
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String command = request.getParameter("command");

        if ("max".equals(command)) {
            try {
                List<Product> products = model.getProductsOrderedByPrice(true);
                HTMLBuilder.buildHeader(response, "Product with max price: ", true);
                HTMLBuilder.addLine(response, products.get(0).getName() + "\t" + products.get(0).getPrice());

                HTMLBuilder.buildFooter(response);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else if ("min".equals(command)) {
            try {
                List<Product> products = model.getProductsOrderedByPrice(false);
                HTMLBuilder.buildHeader(response, "Product with min price: ", true);
                HTMLBuilder.addLine(response, products.get(0).getName() + "\t" + products.get(0).getPrice());

                HTMLBuilder.buildFooter(response);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else if ("sum".equals(command)) {
            try {
                int sum = model.getSum();
                HTMLBuilder.buildHeader(response, "Summary price: ", false);

                HTMLBuilder.addLine(response, String.valueOf(sum));
                HTMLBuilder.buildFooter(response);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else if ("count".equals(command)) {
            try {
                HTMLBuilder.buildHeader(response, "Number of products: ", false);
                HTMLBuilder.addLine(response, String.valueOf(model.getCount()));
                HTMLBuilder.buildFooter(response);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            HTMLBuilder.unknownCommand(response, command);
        }

        HTMLBuilder.finishBuild(response);
    }

    private final Model model;
}
