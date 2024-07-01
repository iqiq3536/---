<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="zh-CN" class="bootstrap-admin-vertical-centered">
<head>
    <meta charset="UTF-8">
    <title>登录</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap-theme.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap-admin-theme.css">
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/login.js"></script>
</head>

<style type="text/css">
    .alert {
        margin: 0 auto 20px;
        text-align: center;
    }
    .title{
       width:100%;
       height:50px;
       font-size:38px;
       color:black;
       text-align:center;
       line-height:50px;
       margin-bottom:20px;
    }
</style>

<body class="bootstrap-admin-without-padding">
<div class="container">
    <div class="row">
        <div class="col-lg-12">
           <div class="title">欢 迎 登 录 测 试 系 统!</div>

            <form class="bootstrap-admin-login-form" method="post" action="${pageContext.request.contextPath}/RegisterServlet">
                <%
                    String state = (String) session.getAttribute("state");
                    session.removeAttribute("state");
                    if (state != null) {
                %>
                <label class="control-label" for="username">密码错误</label>
                <%}%>
                <div class="form-group">
                    <label class="control-label" for="username">账&nbsp;号</label>
                    <input type="text" class="form-control" id="username" name="username" required="required"
                           placeholder="账号"/>
                </div>
                <div class="form-group">
                    <label class="control-label" for="password">密&nbsp;码</label>
                    <input type="password" class="form-control" id="password" name="password" required="required"
                           placeholder="密码"/>
                </div>
                <label class="control-label" for="password">没有账号请<a href="${pageContext.request.contextPath}/enroll.jsp" style="color:blue;">注册</a></label>
                <br>
                <input type="submit" class="btn btn-lg btn-primary" value="登&nbsp;&nbsp;&nbsp;&nbsp;录"/>
            </form>
        </div>
    </div>
</div>
</body>
</html>
