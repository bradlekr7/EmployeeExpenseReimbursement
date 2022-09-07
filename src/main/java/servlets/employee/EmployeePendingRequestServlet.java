package servlets.employee;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.RequestDao;
import dao.RequestDaoImpl;
import entities.Person;
import entities.Request;
import servlets.Users;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class EmployeePendingRequestServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Person person  = (Person) request.getSession().getAttribute("user");
        if ((person !=null && !Users.isManager())) {
            RequestDao rd = new RequestDaoImpl();
            ObjectMapper om = new ObjectMapper();
            PrintWriter pw = response.getWriter();
            List<Request> requests = rd.getPendingRequestsByEmployeeId(person.getId());
            String requestString = om.writeValueAsString(requests);
            requestString = "{\"requests\":" + requestString + "}";
            pw.print(requestString);
        } else {
            request.getRequestDispatcher("html_files/index.jsp").forward(request, response);
        }
    }

}
