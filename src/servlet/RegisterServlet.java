package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public RegisterServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().append("Served at: ").append(request.getContextPath());
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        HttpSession session = request.getSession();
        
        
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        UserDao userDao = new UserDao();
        User user=userDao.getUserInfo(username,password);
        boolean result = userDao.Login_verify(username, password);
        int status = userDao.getUserStatus(username, password); 
        System.out.println(status);
        if (result) {
            // ��¼�ɹ�
            session.setAttribute("username", username);
            session.setAttribute("user", user);
            if(status==2) {
            response.sendRedirect("index.jsp"); // �ض��򵽻�ӭҳ�棬���Ը�����Ҫ�޸�·��
            }
            else {
            	ArrayList<User> users=userDao.get_ListInfo();
            	session.setAttribute("usersList", users);
            	request.getRequestDispatcher("control.jsp").forward(request, response);
            }
        } else {
            // ��¼ʧ��
            PrintWriter out = response.getWriter();
            out.println("<script>alert('��¼ʧ�ܣ������û��������룡');window.location.href='login.jsp';</script>");
        }
    }

       /* if (result) {
            User user = userDao.getUserInfo(username, password);

            if (user.getStatus() == 2) {
                session.setAttribute("uid", "" + user.getAid());
            } else if (user.getStatus() == 0) {
                session.setAttribute("aid", "" + user.getAid());
            } else {
                session.setAttribute("wid", "" + user.getAid());
            }

            session.setMaxInactiveInterval(6000);

            if (user.getStatus() == 1) {
                response.sendRedirect(request.getContextPath() + "MCDCtest.jsp");
            } else if (user.getStatus() == 1) {
                response.sendRedirect(request.getContextPath() + "MCDCtest.jsp");
            } else {
                response.sendRedirect(request.getContextPath() + "MCDCtest.jsp");
            }
        } else {
            session.setAttribute("state", "�˺Ż��������");
            response.sendRedirect(request.getContextPath() + "/register.jsp");
        }
    }*/
}
