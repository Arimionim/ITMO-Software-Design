package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.Model;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
            response.getWriter().println("<html><body>");

            while (rs.next()) {
                String name = rs.getString("name");
                int price = rs.getInt("price");
                response.getWriter().println(name + "\t" + price + "</br>");
            }
            response.getWriter().println("</body></html>");

            rs.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }

    private final Model model;
}
