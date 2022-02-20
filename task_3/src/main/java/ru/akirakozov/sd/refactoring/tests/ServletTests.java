package ru.akirakozov.sd.refactoring.tests;

import org.junit.jupiter.api.BeforeEach;
import ru.akirakozov.sd.refactoring.Model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public abstract class ServletTests {
    private HttpServletRequest request;
    private HttpServletResponse response;
    private StringWriter stringWriter;
    private PrintWriter printWriter;
    protected static final String DB_FILE = "DatabaseTest.db";
    Model model;

    @BeforeEach
    public void prepareMocks() throws IOException {

        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        stringWriter = new StringWriter();
        printWriter = new PrintWriter(stringWriter);
        model = mock(Model.class);
        when(response.getWriter()).thenReturn(printWriter);
    }


    protected HttpServletRequest getRequest() {
        return request;
    }

    protected HttpServletResponse getResponse() {
        return response;
    }

    protected String getResponseText() {
        printWriter.flush();
        return stringWriter.toString();
    }
}
