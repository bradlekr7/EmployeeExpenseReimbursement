package servlets.manager;

import dao.PersonDaoImpl;
import dao.RequestDao;
import dao.RequestDaoImpl;
import entities.Person;
import entities.Request;



import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;

public class UpdateRequestServlet extends HttpServlet {
    public UpdateRequestServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        HttpSession session = request.getSession();
        Person me = (Person) session.getAttribute("user");

        if ((me != null) && me.getIsManager()) {
            RequestDao requestDao = new RequestDaoImpl();

            int id = Integer.parseInt(request.getParameter("id"));
          //  Request req = requestDao.getRequestById(id);
            String decision = request.getParameter("decision");

           // req.setIsApproved("approve".equals(decision));

          //  req.setManager(me.getId());
            requestDao.updateRequest(id,decision,me.getId());
            response.sendRedirect("managerhome");
        }else {
            response.sendRedirect("login");
        }

    }
}

