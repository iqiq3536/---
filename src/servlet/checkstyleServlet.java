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
import java.io.BufferedReader;  
import java.io.IOException;  
import java.io.InputStreamReader; 
import java.io.*;
/**
 * Servlet implementation class checkstyleServlet
 */
@WebServlet("/checkstyleServlet")
@MultipartConfig(fileSizeThreshold = 1024 * 1024, // 1 MB 
				maxFileSize = 1024 * 1024 * 10, // 10 MB 
				maxRequestSize = 1024 * 1024 * 50) // 50 MB
public class checkstyleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public checkstyleServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String directory = "D:\\workspace\\save\\checkstyletest";  
		// 创建保存文件的目录（如果不存在）
		File save = new File(directory);
        if (!save.exists()) {
        	save.mkdirs();
        }
        
        String[] filesToKeep = {
	            "checkstyle-8.17-all.jar",
	            "checkstyle.xml",
	        };
        
        File del = new File(directory);
        if (del.exists() && del.isDirectory()) {
            deleteFilesExcept(del, filesToKeep);
        }
        
        String testname=null;  
        for (Part part : request.getParts()) {
            String fileName = extractFileName(part); // 提取文件名
            testname=fileName;
            if (fileName != null && !fileName.isEmpty()) {
                part.write(directory  + File.separator+ fileName); // 保存文件到指定路径
            }       
        }
        
        String command = "java -Duser.language=en -Duser.country=US -jar checkstyle-8.17-all.jar -c checkstyle.xml " + testname;  
        
        try {
        ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/c",  command);
        File outputLogFile = new File("D:\\workspace\\save\\checkstyletest\\temp.txt");
        processBuilder.directory(new File("D:\\workspace\\save\\checkstyletest"));
        processBuilder.redirectErrorStream(true);  
        
        // 将输出重定向到文件
        processBuilder.redirectOutput(outputLogFile);

        Process process = processBuilder.start();  
        
        // 等待进程执行完成
        int exitCode = process.waitFor();
        }catch (IOException | InterruptedException e) {  
            e.printStackTrace();  
        }
        
        String filePath = "D:\\workspace\\save\\checkstyletest\\temp.txt";
        StringBuilder contentBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                contentBuilder.append(line).append("\n");
            }
        } catch (IOException e) {
            // 处理文件读取异常
            e.printStackTrace();
        }
        String fileContent = contentBuilder.toString();
        request.setAttribute("javafileContent", fileContent);
        request.getRequestDispatcher("Checkstyletest.jsp").forward(request, response);

	}
	
	private String extractFileName(Part part) {
	    // 获取content-disposition头部信息
	    String contentDisposition = part.getHeader("content-disposition");
	    
	    // 切分header中的各项信息
	    String[] items = contentDisposition.split(";");
	    
	    // 遍历各项信息找到包含filename的项
	    for (String item : items) {
	        item = item.trim();
	        // 找到以filename开头的项
	        if (item.startsWith("filename")) {
	            // 获取文件名部分（可能包含路径）
	            String fileName = item.substring(item.indexOf("=") + 2);
	            
	            // 去掉可能包含的路径，仅保留文件名部分
	            return fileName.substring(fileName.lastIndexOf("\\") + 1, fileName.length() - 1);
	        }
	    }
	    return null;
	}
	
	private static void deleteFilesExcept(File directory, String[] filesToKeep) {
        File[] allFiles = directory.listFiles();
        if (allFiles != null) {
            for (File file : allFiles) {
                if (file.isFile() && !shouldKeep(file.getName(), filesToKeep)) {
                    if (file.delete()) {
                        System.out.println("Deleted file: " + file.getAbsolutePath());
                    } else {
                        System.err.println("Failed to delete file: " + file.getAbsolutePath());
                    }
                } else if (file.isDirectory()) {
                    deleteFilesExcept(file, filesToKeep); // Recursively delete files in subdirectories
                }
            }
        }
    }
	
	private static boolean shouldKeep(String fileName, String[] filesToKeep) {
        for (String keepFileName : filesToKeep) {
            if (fileName.equals(keepFileName)) {
                return true;
            }
        }
        return false;
    }
}
