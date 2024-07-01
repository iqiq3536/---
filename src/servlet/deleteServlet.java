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
 * Servlet implementation class deleteServlet
 */
@WebServlet("/deleteServlet")
public class deleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public deleteServlet() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");

        // 执行删除用户的操作，这里只是示例，你可以根据具体业务逻辑实现
		UserDao userDao = new UserDao();
		
        boolean deleteSuccessful = userDao.deleteUser(username);

        if (deleteSuccessful) {
            // 删除成功，可以重定向回用户列表页面或者做其他处理
        	ArrayList<User> users=userDao.get_ListInfo();
        	HttpSession session = request.getSession();
        	session.setAttribute("usersList", users);
        	request.getRequestDispatcher("control.jsp").forward(request, response);
        } else {
            // 删除失败，可以返回错误信息或者做其他处理
            response.getWriter().println("删除用户失败");
            request.getRequestDispatcher("control.jsp").forward(request, response);
        }
    }

}
