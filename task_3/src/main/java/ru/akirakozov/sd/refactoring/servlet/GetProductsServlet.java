package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.HTMLBuilder;
import ru.akirakozov.sd.refactoring.Model;
import ru.akirakozov.sd.refactoring.Product;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author akirakozov
 */
public class GetProductsServlet extends HttpServlet {
    public GetProductsServlet(Model model) {
        this.model = model;
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            List<Product> products = model.getProducts();
            HTMLBuilder.buildHeader(response);

            for (Product pr : products) {
                HTMLBuilder.addLine(response, pr.getName() + "\t" + pr.getPrice());
            }

            HTMLBuilder.buildFooter(response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        HTMLBuilder.finishBuild(response);
    }

    private final Model model;
}
