package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.HTMLBuilder;
import ru.akirakozov.sd.refactoring.Model;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author akirakozov
 */
public class AddProductServlet extends HttpServlet {
    public AddProductServlet(Model model) {
        this.model = model;
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = request.getParameter("name");
        long price = Long.parseLong(request.getParameter("price"));

        model.addProduct(name, price);

        HTMLBuilder.addLine(response, "OK");
        HTMLBuilder.finishBuild(response);
    }

    private final Model model;
}
