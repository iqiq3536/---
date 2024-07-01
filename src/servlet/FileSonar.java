package servlet;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;



/**
 * Servlet implementation class FileSonar
 */
@WebServlet("/File/FileSonar")
@MultipartConfig(maxFileSize = 16177215) // 15MB
public class FileSonar extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public FileSonar() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // This method can be left empty or used for GET requests
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Part filePart = request.getPart("file");
        if (filePart != null) {
        	try {
                // 定义要执行的命令
                String[] command = {"cmd", "/c", "sonar-scanner"};
                
                // 指定命令运行的目录
                File workingDirectory = new File("E:\\sonarqube\\python-mini-projects-master\\python-mini-projects-master");
                
                // 创建ProcessBuilder并设置命令和工作目录
                ProcessBuilder builder = new ProcessBuilder(command);
                builder.directory(workingDirectory);
                
                // 启动进程
                Process process = builder.start();
                
                // 等待命令执行完成
                int exitCode = process.waitFor();
                System.out.println("Command executed with exit code: " + exitCode);
                
                // 如果命令执行成功，重定向到指定URL
                if (exitCode == 0) {
                    response.sendRedirect("http://localhost:9000/");
                } else {
                    // 如果命令执行失败，可能需要记录错误或采取其他措施
                    System.err.println("Sonar Scanner execution failed with exit code: " + exitCode);
                }
            } catch (IOException | InterruptedException e) {
                // 处理异常情况
                e.printStackTrace();
            }
        }

        }
    
    
    private String extractFileName(Part part) {
        String contentDisposition = part.getHeader("content-disposition");
        String[] items = contentDisposition.split(";");
        for (String item : items) {
            if (item.trim().startsWith("filename")) {
                return item.substring(item.indexOf("=") + 2, item.length() - 1);
            }
        }
        return "";
    }
    

 
}
