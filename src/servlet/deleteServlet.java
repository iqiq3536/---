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

        // ִ��ɾ���û��Ĳ���������ֻ��ʾ��������Ը��ݾ���ҵ���߼�ʵ��
		UserDao userDao = new UserDao();
		
        boolean deleteSuccessful = userDao.deleteUser(username);

        if (deleteSuccessful) {
            // ɾ���ɹ��������ض�����û��б�ҳ���������������
        	ArrayList<User> users=userDao.get_ListInfo();
        	HttpSession session = request.getSession();
        	session.setAttribute("usersList", users);
        	request.getRequestDispatcher("control.jsp").forward(request, response);
        } else {
            // ɾ��ʧ�ܣ����Է��ش�����Ϣ��������������
            response.getWriter().println("ɾ���û�ʧ��");
            request.getRequestDispatcher("control.jsp").forward(request, response);
        }
    }

}
