<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<%@ page import="servlet.*" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>管理面板</title>
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
        }
        table {
            width: 100%;
            border-collapse: collapse;
        }
        table, th, td {
            border: 1px solid #ccc;
        }
        th, td {
            padding: 12px;
            text-align: left;
        }
        .btn {
            padding: 6px 12px;
            background-color: #4CAF50;
            color: white;
            border: none;
            cursor: pointer;
            border-radius: 5px;
        }
        .btn.delete {
            background-color: #f44336;
        }
        .add-user-btn {
            margin: 20px 0;
            padding: 10px 20px;
            background-color: #4CAF50;
            border: none;
            color: white;
            cursor: pointer;
            border-radius: 5px;
        }
        .modal {
            display: none;
            position: fixed;
            z-index: 1;
            left: 0;
            top: 0;
            width: 100%;
            height: 100%;
            overflow: auto;
            background-color: rgb(0,0,0);
            background-color: rgba(0,0,0,0.4);
            padding-top: 60px;
        }
        .modal-content {
            background-color: #fefefe;
            margin: 5% auto;
            padding: 20px;
            border: 1px solid #888;
            width: 80%;
        }
        .close {
            color: #aaa;
            float: right;
            font-size: 28px;
            font-weight: bold;
        }
        .close:hover,
        .close:focus {
            color: black;
            text-decoration: none;
            cursor: pointer;
        }
    </style>
</head>
<body>
	<% User user1=(User)request.getSession().getAttribute("user"); 
		if(user1==null){
			response.sendRedirect("register.jsp");
		}
	%>
<div class="header">
    <h1>Control</h1>
    <button class="logout-btn" onclick="window.location.href='register.jsp'">退出</button>
</div>

<div class="navbar">
	<a href="details.jsp?section=Details">Details</a>
    <a href="control.jsp?section=control">Control</a>
</div>

<div class="container">
    <h2>用户管理</h2>
    <h2>User List</h2>
    <table>
        <thead>
            <tr>
                <th>Username</th>
                <th>Password</th>
                <th>操作</th>
            </tr>
        </thead>
        <tbody id="userTableBody">
            <% 
            ArrayList<User> usersList = (ArrayList<User>) request.getSession().getAttribute("usersList");
            System.out.println(usersList);
            if (usersList != null) {
                for (User user : usersList) {
            %>
                <tr>
                    <td><%= user.getUsername() %></td>
                    <td><%= user.getPassword() %></td>
                    <td>
                	<form action="${pageContext.request.contextPath}/deleteServlet" method="post">
                    	<input type="hidden" name="username" value="<%= user.getUsername() %>">
                    	<input type="submit" value="删除">
                	</form>
            		</td>
                </tr>
            <% 
                }
            }
            %>
        </tbody>
    </table>
    <button class="add-user-btn" onclick="window.location.href='addUser.jsp'">添加用户</button>
</div>
</body>
</html>