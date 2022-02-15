package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.HTMLBuilder;
import ru.akirakozov.sd.refactoring.Model;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;

/**
 * @author akirakozov
 */
public class QueryServlet extends HttpServlet {
    public QueryServlet(Model model) {
        this.model = model;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String command = request.getParameter("command");

        if ("max".equals(command)) {
            try {
                ResultSet rs = model.getProductsOrderedByPlace(true);
                HTMLBuilder.buildHeader(response, "Product with max price: ", true);
                while (rs.next()) {
                    String name = rs.getString("name");
                    int price = rs.getInt("price");
                    HTMLBuilder.addLine(response, name + "\t" + price);
                }

                rs.close();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else if ("min".equals(command)) {
            try {
                ResultSet rs = model.getProductsOrderedByPlace(true);

                HTMLBuilder.buildHeader(response, "Product with min price: ", true);

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
        } else if ("sum".equals(command)) {
            try {
                ResultSet rs = model.getSum();
                HTMLBuilder.buildHeader(response, "Summary price: ", false);

                if (rs.next()) {
                    HTMLBuilder.addLine(response, String.valueOf(rs.getInt(1)));
                }
                HTMLBuilder.buildFooter(response);

                rs.close();

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else if ("count".equals(command)) {
            try {
                ResultSet rs = model.getCount();
                HTMLBuilder.buildHeader(response, "Number of products: ", false);

                if (rs.next()) {
                    HTMLBuilder.addLine(response, String.valueOf(rs.getInt(1)));
                }
                HTMLBuilder.buildFooter(response);

                rs.close();
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
