<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.sql.*" %>
<%@ page import="servlet.*" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <title>任务管理</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
        }
        .header {
            display: flex;
            justify-content: center;
            align-items: center;
            background-color: #f4f4f4;
            padding: 10px 20px;
            border-bottom: 1px solid #ccc;
            position: relative;
        }
        .header h1 {
            margin: 0;
            font-size: 24px;
        }
        .logout-btn {
            padding: 10px 20px;
            background-color: #ff4c4c;
            border: none;
            color: white;
            cursor: pointer;
            border-radius: 5px;
            position: absolute;
            right: 20px;
            bottom:5px;
        }
       .navbar {
            background-color: #333;
            overflow: hidden;
            display: flex;
            justify-content: center;
        }
        .navbar a {
            float: left;
            display: block;
            color: #f2f2f2;
            text-align: center;
            padding: 14px 16px;
            text-decoration: none;
        }
        .navbar a:hover {
            background-color: #ddd;
            color: black;
        }
        .container {
            padding: 20px;
            display: flex;
            flex-direction: column;
            align-items: center;
        }
        .form-group {
            display: flex;
            flex-direction: column;
            align-items: flex-start;
            width: 100%;
            max-width: 600px;
        }
        .form-group label {
            margin-bottom: 5px;
        }
        .form-group input {
            margin-bottom: 10px;
            width: calc(100% - 22px);
            padding: 5px;
            border: 1px solid black;
        }
        .form-group input[type="file"] {
            width: calc(100% - 12px);
        }
        .submit-btn {
            margin-top: 20px;
            padding: 10px 20px;
            border: 1px solid black;
            background-color: white;
            cursor: pointer;
            border-radius: 5px;
        }
        
        .modal {
		    position: fixed;
		    top: 50%;
		    left: 50%;
		    transform: translate(-50%, -50%);
		    background-color: blue;
		    border: 1px solid #888;
		    z-index: 1000;
		    padding: 20px;
		    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

	.modal-content {
    	text-align: center;
	}
    </style>
</head>

<body>
	<% User user=(User)request.getSession().getAttribute("user"); 
		if(user==null){
			response.sendRedirect("register.jsp");
		}
	%>
	
    <div class="header">
        <h1>Jacoco</h1>
        <button class="logout-btn" onclick="window.location.href='register.jsp'">退出</button>
    </div>

    <div class="navbar">
    	<a href="details.jsp?section=Details">Details</a>
        <a href="MCDCtest.jsp?section=MCDC">Jacoco</a>
        <a href="Pythontest.jsp?section=Python">Coverage</a>
        <a href="Checkstyletest.jsp?section=Checkstyle">CheckStyle</a>
        <a href="upload.jsp?section=upload">Sonarqube</a>
    </div>

    <div class="container">
        <form id="uploadForm" method="post" enctype="multipart/form-data">
            <div class="form-group">
                <label for="java">.java:</label>
                <input type="file" id="java" name="java" accept=".java">
                <br>
                <label for="class">.class（可选）:</label>
                <input type="file" id="class" name="class" accept=".class">
                <br>
                <label for="jar">.jar(可选):</label>
                <input type="file" id="jar" name="jar" accept=".jar">
                <br>
                <label for="need">测试集:</label>
                <input type="file" id="need" name="need" accept=".txt">
                <br>
                <label for="want">期望值:</label>
                <input type="file" id="want" name="want" accept=".txt">
            </div>
            <button type="submit" class="submit-btn" formaction="excServlet" id="submitBtn">测试</button>
        </form>
    </div>
    
    <h2>test out Content:</h2>
    <pre>
    <% String content=(String)request.getAttribute("javafileContent"); %>
        <%= content %>
    </pre>
    
    <div id="loadingModal" class="modal" style="display: none;">
    <div class="modal-content">
        <p>请耐心等待，正在进行测试...</p>
    </div>
</div>
</body>
</html>

<script>
    document.addEventListener('DOMContentLoaded', function() {
        var javaInput = document.getElementById('java');
        var classInput = document.getElementById('class');
        var jarInput = document.getElementById('jar');
        var needInput = document.getElementById('need');
        var wantInput = document.getElementById('want');
        var submitBtn = document.getElementById('submitBtn');

        // 初始状态，禁用按钮
        submitBtn.disabled = true;

        // 监听文件输入框的变化
        javaInput.addEventListener('change', checkInputs);
        classInput.addEventListener('change', checkInputs);
        jarInput.addEventListener('change', checkInputs);
        needInput.addEventListener('change', checkInputs);
        wantInput.addEventListener('change', checkInputs);

        function checkInputs() {
            // 检查条件
            var javaSelected = javaInput.files.length > 0;
            var needSelected = needInput.files.length > 0;
            var wantSelected = wantInput.files.length > 0;
            var classSelected = classInput.files.length > 0;
            var jarSelected = jarInput.files.length > 0;

            // 根据条件启用或禁用按钮，并给出相应提示
            if (javaSelected && needSelected && wantSelected) {
                if (classSelected && jarSelected) {
                    submitBtn.disabled = false;
                    // 可以选择添加提示，例如：submitBtn.innerText = '可以提交';
                } else if (!classSelected && !jarSelected) {
                    submitBtn.disabled = false;
                    // 可以选择添加提示，例如：submitBtn.innerText = '可以提交';
                } else {
                    submitBtn.disabled = true;
                    // 可以选择添加提示，例如：submitBtn.innerText = '不可提交，.class和.jar须同时上传';
                }
            } else {
                submitBtn.disabled = true;
                // 可以选择添加提示，例如：submitBtn.innerText = '不可提交，.java, 测试集, 期望值须同时上传';
            }
        }
    });
 

</script>