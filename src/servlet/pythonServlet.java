package servlet;

import java.io.File;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import java.awt.Desktop;  
/**
 * Servlet implementation class pythonServlet
 */
@WebServlet("/pythonServlet")
@MultipartConfig(fileSizeThreshold = 1024 * 1024, // 1 MB 
				maxFileSize = 1024 * 1024 * 10, // 10 MB 
				maxRequestSize = 1024 * 1024 * 50) // 50 MB
public class pythonServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public pythonServlet() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String directory = "D:\\workspace\\save\\pytest";  
		// ���������ļ���Ŀ¼����������ڣ�
		File save = new File(directory);
        if (!save.exists()) {
        	save.mkdirs();
        }
        
        File del = new File(directory);
        if (del.exists() && del.isDirectory()) {
            deleteFilesExcept(del);
        }
        
        String testname=null;  
        for (Part part : request.getParts()) {
            String fileName = extractFileName(part); // ��ȡ�ļ���
            testname=fileName;
            if (fileName != null && !fileName.isEmpty()) {
                part.write(directory  + File.separator+ fileName); // �����ļ���ָ��·��
            }       
        }
        //ȥ��.py
        testname=testname.substring(0, testname.lastIndexOf("."));
        System.out.println(testname);
        String htmlReportDir = directory + File.separator + "htmlcov";  
  
        runCommand(directory, "coverage erase");  
        runCommand(directory, "coverage run -m " + testname);  
        runCommand(directory, "coverage html");  
  
        // �����ɵ�HTML����  
        openHtmlReport(htmlReportDir);  
        
        request.getRequestDispatcher("Pythontest.jsp").forward(request, response);
    }  
  
    private static void runCommand(String directory, String command) {  
        try {           
            ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", command);
            builder.directory(new File("D:\\workspace\\save\\pytest")); // ���� CMD ��ʼ·��Ϊ 
            builder.redirectErrorStream(true);
            Process process = builder.start();
            // �ȴ�����ִ�����  
            int exitCode = process.waitFor();  
            System.out.println("Command executed with exit code: " + exitCode);  
        } catch (IOException | InterruptedException e) {  
            e.printStackTrace();  
        }  
	}

    private static void openHtmlReport(String htmlReportDir) {  
        if (Desktop.isDesktopSupported()) {  
            Desktop desktop = Desktop.getDesktop();  
            try {  
                // ���Դ�htmlReportDirĿ¼�е�index.html�ļ�  
                File htmlFile = new File(htmlReportDir, "index.html");  
                if (htmlFile.exists()) {  
                    desktop.browse(htmlFile.toURI());  
                } else {  
                    System.out.println("HTML report not found at: " + htmlFile.getAbsolutePath());  
                }  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        } else {  
            System.out.println("Desktop is not supported. Cannot open the HTML report.");  
        }  
    }  

    private String extractFileName(Part part) {
	    // ��ȡcontent-dispositionͷ����Ϣ
	    String contentDisposition = part.getHeader("content-disposition");
	    
	    // �з�header�еĸ�����Ϣ
	    String[] items = contentDisposition.split(";");
	    
	    // ����������Ϣ�ҵ�����filename����
	    for (String item : items) {
	        item = item.trim();
	        // �ҵ���filename��ͷ����
	        if (item.startsWith("filename")) {
	            // ��ȡ�ļ������֣����ܰ���·����
	            String fileName = item.substring(item.indexOf("=") + 2);
	            
	            // ȥ�����ܰ�����·�����������ļ�������
	            return fileName.substring(fileName.lastIndexOf("\\") + 1, fileName.length() - 1);
	        }
	    }
	    return null;
	}
    
    private static void deleteFilesExcept(File directory) {
        File[] allFiles = directory.listFiles();
        if (allFiles != null) {
            for (File file : allFiles) {
                if (file.isFile()) {
                    if (file.delete()) {
                        System.out.println("Deleted file: " + file.getAbsolutePath());
                    } else {
                        System.err.println("Failed to delete file: " + file.getAbsolutePath());
                    }
                } else if (file.isDirectory()) {
                    deleteFilesExcept(file); // Recursively delete files in subdirectories
                }
            }
        }
    }
}
