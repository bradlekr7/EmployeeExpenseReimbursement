package servlets.expense;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.RequestDao;
import dao.RequestDaoImpl;
import servlets.Users;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;

public class ExpenseServlet extends HttpServlet {
    public ExpenseServlet() {
        super();

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if ((Users.isManagerNull() && !Users.isEmployee())) {
            RequestDao rd = new RequestDaoImpl();
            ObjectMapper om = new ObjectMapper();
            PrintWriter pw = response.getWriter();
            BigDecimal amount = rd.getSumByType("Expense");
            String requestString = om.writeValueAsString(amount);
            requestString = "{\"requests\":" + requestString + "}";
            pw.print(requestString);
        } else {
            request.getRequestDispatcher("html_files/index.jsp").forward(request, response);
        }
    }
}