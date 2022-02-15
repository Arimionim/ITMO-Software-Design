package ru.akirakozov.sd.refactoring.servlet;

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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = request.getParameter("name");
        long price = Long.parseLong(request.getParameter("price"));

        model.addProduct(name, price);

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println("OK");
    }

    private final Model model;
}
