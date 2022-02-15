package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.HTMLBuilder;
import ru.akirakozov.sd.refactoring.Model;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.ResultSet;

/**
 * @author akirakozov
 */
public class GetProductsServlet extends HttpServlet {
    public GetProductsServlet(Model model) {
        this.model = model;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            ResultSet rs = model.getProducts();
            HTMLBuilder.buildHeader(response);

            while (rs.next()) {
                String name = rs.getString("name");
                int price = rs.getInt("price");
                HTMLBuilder.addLine(response, name + "\t" + price);
            }
            HTMLBuilder.buildFooter(response);

            rs.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        HTMLBuilder.finishBuild(response);
    }

    private final Model model;
}
