package servlets.employee;


import dao.RequestDao;
import dao.RequestDaoImpl;
import entities.Person;
import entities.Request;
import servlets.Users;


import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;

public class SubmitRequestServlet extends HttpServlet {

    public SubmitRequestServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        if (Users.isEmployeeNull() && !Users.isManager()) {
            double a = Double.parseDouble(request.getParameter("amount"));
            BigDecimal amount = BigDecimal.valueOf(a);
            RequestDao requestDao = new RequestDaoImpl();
            Request request1 = new Request();
            Person employee = (Person)request.getSession().getAttribute("user");

            request1.setDescription(request.getParameter("expensedescription"));
            request1.setIsApproved(null);

            request1.setAmount(amount);
            request1.setEmployee(employee);
            request1.setManager(null);
            requestDao.addRequest(request1);
            response.sendRedirect("employeehome");
        } else {
            response.sendRedirect("login");
        }

    }

}