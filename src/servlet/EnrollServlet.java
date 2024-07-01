package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/EnrollServlet")
public class EnrollServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public EnrollServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().append("Served at: ").append(request.getContextPath());
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        int status = 2;

        UserDao userDao = new UserDao();
        boolean result = userDao.insertUser(username, password, status);

        if (result) {
            System.out.println("User registered successfully");
            response.sendRedirect(request.getContextPath() + "/register.jsp");
        } else {
            System.out.println("User registration failed");
            response.sendRedirect(request.getContextPath() + "/enroll.jsp");
        }
    }
}
