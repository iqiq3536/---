package servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class addUserServlet
 */
@WebServlet("/addUserServlet")
public class addUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public addUserServlet() {
        super();
        // TODO Auto-generated constructor stub
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
            System.out.println("User add successfully");
            ArrayList<User> users=userDao.get_ListInfo();
        	HttpSession session = request.getSession();
        	session.setAttribute("usersList", users);
        	request.getRequestDispatcher("control.jsp").forward(request, response);
        } else {
            System.out.println("User add failed");
            response.sendRedirect(request.getContextPath() + "/control.jsp");
        }
    }

}
