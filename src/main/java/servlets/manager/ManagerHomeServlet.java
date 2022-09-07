package servlets.manager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ManagerHomeServlet extends HttpServlet {


    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getSession().getAttribute("user") == null) {
            request.getSession().invalidate();
            response.sendRedirect("login");
        } else {
            request.getRequestDispatcher("html_files/ManagerHome.html").forward(request, response);
        }
    }

}