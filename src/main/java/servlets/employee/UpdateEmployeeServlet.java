package servlets.employee;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.PersonDao;
import dao.PersonDaoImpl;
import dao.RequestDao;
import dao.RequestDaoImpl;
import entities.Person;
import entities.Request;
import servlets.Users;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class UpdateEmployeeServlet extends HttpServlet {

    public UpdateEmployeeServlet() {
        super();
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        RequestDispatcher rq;
        {
            Person employee = (Person)request.getSession().getAttribute("user");
            RequestDao rd = new RequestDaoImpl();
            ObjectMapper om = new ObjectMapper();
            PrintWriter pw = response.getWriter();

            String requestString = om.writeValueAsString(employee);
            requestString = "{\"requests\":" + requestString + "}";
            pw.print(requestString);
        }
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Person user = (Person)request.getSession().getAttribute("user");
        if (user !=null) {

//            int employeeId = new PersonDaoImpl().getById();
//            PersonDao rId = new PersonDaoImpl().getById();
            String firstname = request.getParameter("fName");
            String lastname = request.getParameter("lName");
            String username = request.getParameter("uName");
            String password = request.getParameter("password");
            String address = request.getParameter("address");

            PersonDao personDao = new PersonDaoImpl();
            Person employee = new Person();
            employee.setId(user.getId());
            employee.setFirstname(firstname);
            employee.setLastname(lastname);
            employee.setUsername(username);
            employee.setPassword(password);
            employee.setAddress(address);
            employee.setEmail(user.getEmail());

            personDao.updateEmployee(employee);
            request.getSession().setAttribute("user",employee);
            response.sendRedirect("employeeprofile");
        } else {
            response.sendRedirect("login");
        }

    }

}
