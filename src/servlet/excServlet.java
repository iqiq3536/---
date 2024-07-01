package servlet;

import java.util.ArrayList;
import java.util.List;
import java.io.*;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.servlet.annotation.MultipartConfig;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
/**
 * Servlet implementation class excServlet
 */

@WebServlet("/excServlet") 
@MultipartConfig(fileSizeThreshold = 1024 * 1024, // 1 MB 
				maxFileSize = 1024 * 1024 * 10, // 10 MB 
				maxRequestSize = 1024 * 1024 * 50) // 50 MB
public class excServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public excServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String[] saveplace = {
	            "D:\\workspace\\save\\java",
	            "D:\\workspace\\save\\class",
	            "D:\\workspace\\save\\jar",
	            "D:\\workspace\\save\\jacocoParser",
	            "D:\\workspace\\save\\jacocoParser",
	        };
		String[] filesToKeep = {
	            "jacocoagent.jar",
	            "jacococli.jar",
	            "Main.java",
	            "Main.class",
	            "JaCoCoReportParser.java",
	            "JaCoCoReportParser.class",
	            "CoverageData.class"
	        };
		//ɾ��ԭ���ļ�
		for (String path : saveplace) {
            File directory = new File(path);
            if (directory.exists() && directory.isDirectory()) {
                deleteFilesExcept(directory, filesToKeep);
            }
        }
        System.out.println("Deletion process completed.");
        
		String savePath = "D:/workspace/save"; // �����ļ���·��
		String[] allpath=new String[3];
		String[] name=new String[3];
		int i=0;

        // ���������ļ���Ŀ¼����������ڣ�
        File directory = new File(savePath);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        
        // ����ÿ���ļ��ϴ�
        for (Part part : request.getParts()) {
            String fileName = extractFileName(part); // ��ȡ�ļ���
            name[i]=fileName;
            if (fileName != null && !fileName.isEmpty()) {
            	if(part.getName().equals("java")) {
            		part.write(saveplace[0]  + File.separator+ fileName); // �����ļ���ָ��·��       
            		part.write(saveplace[4]  + File.separator+ fileName); // �����ļ���ָ��·��       
            	}	
            	else if(part.getName().equals("class")) part.write(saveplace[1]  + File.separator+ fileName); // �����ļ���ָ��·��
            	else if(part.getName().equals("jar")) part.write(saveplace[2]  + File.separator+ fileName); // �����ļ���ָ��·��
            	else if(part.getName().equals("need")) part.write(saveplace[3]  + File.separator+ "testin.txt"); // �����ļ���ָ��·��
            	else if(part.getName().equals("want")) part.write(saveplace[4]  + File.separator+ "testout.txt"); // �����ļ���ָ��·��
                i++;
            }       
        }
        
        String command = "cmd.exe /c java Main";
        executeCommand(command,saveplace[3]);
        /*String command = "java Main";

    	ProcessBuilder runProcessBuilder1 = new ProcessBuilder("cmd.exe", "/k",command);
            runProcessBuilder1.directory(new File("D:\\workspace\\save\\jacocoParser"));         
            runProcessBuilder1.redirectErrorStream(true);
    	try{
    		Process runProcess = runProcessBuilder1.start();
    int exitCode = runProcess.waitFor();
                if (exitCode == 0) {
                    System.out.println("111");
                } else {
                    System.out.println("222" + exitCode);
                }
    	}
            catch(IOException | InterruptedException e ){
    		e.printStackTrace();
    	}*/

        
        if(i!=5) {
        	String workspacejava = "D:\\workspace\\save\\java";
            String workspaceclass = "D:\\workspace\\save\\class";
            String jarOutputDir = "D:\\workspace\\save\\jar";  
            String mainClassName = name[0]; // ����main.java�ж�����public class test 	 
            String jarName = "test.jar";  //������Ҫtest.jar,����test
            name[2]=jarName;
            // 1. ����Manifest.txt��д������  
            String mainNamewithoutjava = mainClassName.substring(0, mainClassName.lastIndexOf(".")); // ȥ��.java��չ��
            File manifestFile = new File(jarOutputDir + "\\Manifest.txt");  
            try (PrintWriter out = new PrintWriter(manifestFile)) {  
                out.println("Main-Class: " + mainNamewithoutjava);  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
      
            // 2. ����main.java������javac��PATH�У�  
            try {  
                ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/c","javac" , mainClassName );  
                pb.directory(new File(workspacejava));  
                Process p = pb.start();  
                p.waitFor(); // �ȴ��������  
            } catch (IOException | InterruptedException e) {  
                e.printStackTrace();  
            }  
            //�ƶ�class
            String sourceFile = workspacejava+"\\"+mainNamewithoutjava+".class";
            String targetDirectory = "D:\\workspace\\save\\class";
            try {
                Path sourcePath = Paths.get(sourceFile);
                Path targetPath = Paths.get(targetDirectory, sourcePath.getFileName().toString());

                // Perform the move operation
                Files.move(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);

                System.out.println("File moved successfully.");
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("Failed to move file.");
            }
      
            // 3. ����JAR��  
            File classFile = new File(workspaceclass + "\\" + mainNamewithoutjava + ".class");  
            if (classFile.exists()) { // ȷ��.class�ļ�����  
                try {  
                    ProcessBuilder pb = new ProcessBuilder(  
                        "jar", "cvfm", jarOutputDir + "\\" +  jarName ,  
                        jarOutputDir + "\\Manifest.txt", "-C", workspaceclass , "." // "."  
                    );  
                    pb.directory(new File(jarOutputDir)); // ��jarOutputDirĿ¼��ִ��jar����  
                    Process p = pb.start();  
                    p.waitFor(); // �ȴ�JAR�������  
                } catch (IOException | InterruptedException e) {  
                    e.printStackTrace();  
                }  
            } else {  
                System.out.println("Class file does not exist. Compilation might have failed.");  
            }  
        }  
        //��ȡ���뼯����������
        String fileName = "D:\\workspace\\save\\jacocoParser\\testin.txt";
        List<List<String>> dataList = new ArrayList<>();
        int rows=0;
        int cols=0;
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));

            // Read the file line by line
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.trim().split("\\s+");
                List<String> row = new ArrayList<>();
                for (String value : values) {
                    row.add(value);
                }
                dataList.add(row);
            }

            // Close the BufferedReader
            br.close();

            // Determine dimensions
            rows = dataList.size();
            cols = dataList.isEmpty() ? 0 : dataList.get(0).size();

            // Print the contents (optional)
            System.out.println("Number of rows: " + rows);
            System.out.println("Number of columns: " + cols);

        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Invalid number format in the file: " + e.getMessage());
        }
        
        // ��һ�� CMD ����
        String command1 = "java -javaagent:jacocoagent.jar=includes=*,output=tcpserver,port=6300,address=localhost,append=true -jar "+name[2];

        // �ڶ��͵����� CMD ����ϲ���һ�������У�
        String temp="report";
        String end=".xml --encoding=utf-8";
        String command2And3 = "java -jar jacococli.jar dump --address 127.0.0.1 --port 6300 --destfile jacoco-demo.exec && " 
        						+"java -jar jacococli.jar report jacoco-demo.exec --classfiles "
        						+"D:\\workspace\\save\\class"
        						+" --sourcefiles "
        						+"D:\\workspace\\save\\java "
        						+" --html html-report --xml "
        						+"D:\\workspace\\save\\jacocoParser\\";
        
        for(int r=0;r<rows;r++) { 
        	String input="";
        	String newcommand2And3=command2And3;
        	for(int c=0;c<cols;c++) {
        		input=input+dataList.get(r).get(c)+"\n";
        		System.out.println(dataList.get(r).get(c));
        	}
        	// ������һ������
        	ProcessBuilder runProcessBuilder = new ProcessBuilder("cmd.exe", "/c",command1);
        	runProcessBuilder.directory(new File(saveplace[2])); // ���� CMD ��ʼ·��Ϊ 
        	runProcessBuilder.redirectErrorStream(true);
            Process runProcess = runProcessBuilder.start();
            // ��ȡ��������������
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(runProcess.getOutputStream()));
            // ����������
            writer.write(input); // �����������֣�ÿ�����ֺ���Ҫ���ϻ��з� \n
            writer.flush();
            writer.close();
            try {
    			Thread.sleep(50);
    		} catch (InterruptedException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
            newcommand2And3=newcommand2And3+temp+String.valueOf(r+1)+end;
         // �����ڶ��͵����������ͬһ�� CMD ����������ִ�У�
            executeCommand(newcommand2And3,saveplace[2]);
            
            try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
           
        // �����ɵ� HTML ����
        //openHtmlReport("html-report/index.html");
        String filePath = "D:\\workspace\\save\\jacocoParser\\suspiciousness_report.txt";

        try {
            File file = new File(filePath);

            // ����ļ������ڣ��򴴽����ļ�
            if (file.createNewFile()) {
                System.out.println("File created: " + file.getAbsolutePath());
            } else {
                System.out.println("File already exists.");
            }

        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        String jacoco = "java JaCoCoReportParser";
        executeCommand(jacoco,saveplace[3]);
        
        
        String savefilePath = "D:\\workspace\\save\\jacocoParser\\suspiciousness_report.txt";
        StringBuilder contentBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(savefilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                contentBuilder.append(line).append("\n");
            }
        } catch (IOException e) {
            // �����ļ���ȡ�쳣
            e.printStackTrace();
        }
        String fileContent = contentBuilder.toString();
        request.setAttribute("javafileContent", fileContent);
        request.getRequestDispatcher("MCDCtest.jsp").forward(request, response);
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
	
	private static void executeCommand(String command,String path) throws IOException {
        ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", command);
        builder.directory(new File(path)); // ���� CMD ��ʼ·��Ϊ 
        builder.redirectErrorStream(true);
        Process process = builder.start();

        // �ȴ�����ִ�����
        try {
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                System.out.println("����ִ�гɹ�.");
            } else {
                System.out.println("����ִ��ʧ�ܣ�222�����룺" + exitCode);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void openHtmlReport(String path) throws IOException {
        File defaultHtmlFile = new File("D:\\workspace\\save\\jar\\html-report\\index.html");
        if (defaultHtmlFile.exists()) {
            Runtime.getRuntime().exec("cmd /c start " + defaultHtmlFile.getAbsolutePath());
        } else {
            System.err.println("·����δ�ҵ� HTML ���档");
        }
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

