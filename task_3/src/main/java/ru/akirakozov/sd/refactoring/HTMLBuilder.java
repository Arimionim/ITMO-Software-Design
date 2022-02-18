package ru.akirakozov.sd.refactoring;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HTMLBuilder {
    public static void buildHeader(HttpServletResponse response) throws IOException {
        buildHeader(response, "");
    }

    public static void buildHeader(HttpServletResponse response, String name) throws IOException {
        buildHeader(response, name, false);
    }

    public static void buildHeader(HttpServletResponse response, String name, boolean header) throws IOException {
        response.getWriter().println("<html><body>");

        if (!name.isEmpty()) {
            if (header) {
                response.getWriter().println("<h1>" + name + "</h1>");
            } else {
                response.getWriter().println(name);
            }
        }
    }

    public static void addLine(HttpServletResponse response, String line) throws IOException {
        response.getWriter().println(line + "</br>");
    }

    public static void buildFooter(HttpServletResponse response) throws IOException {
        response.getWriter().println("</body></html>");
    }

    public static void unknownCommand(HttpServletResponse response, String command) throws IOException {
        response.getWriter().println("Unknown command: " + command);
    }

    public static void finishBuild(HttpServletResponse response) {
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
