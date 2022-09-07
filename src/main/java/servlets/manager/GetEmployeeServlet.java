package servlets.manager;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.PersonDao;
import dao.PersonDaoImpl;
import entities.Person;
import servlets.Users;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class GetEmployeeServlet extends HttpServlet {

    public GetEmployeeServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (Users.isManagerNull() && !Users.isEmployee()) {
            PersonDao personDao = new PersonDaoImpl();
            ObjectMapper om = new ObjectMapper();
            PrintWriter pw = response.getWriter();
            List<Person> employees = personDao.getEmployees();
            String requestString = om.writeValueAsString(employees);
            requestString = "{\"requests\":"+requestString+"}";
            pw.print(requestString);
        } else {
            request.getRequestDispatcher("html_files/index.jsp").forward(request, response);
        }
    }

}
