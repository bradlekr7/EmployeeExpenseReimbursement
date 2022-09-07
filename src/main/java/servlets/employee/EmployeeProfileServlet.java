package servlets.employee;

import servlets.Users;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EmployeeProfileServlet extends HttpServlet {


    public EmployeeProfileServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        RequestDispatcher rq;
        if (Users.isEmployeeNull()) {
            rq = request.getRequestDispatcher("html_files/EmployeeProfile.html");
        } else {
            rq = request.getRequestDispatcher("html_files/index.jsp");
        }
        rq.forward(request, response);
    }

}
