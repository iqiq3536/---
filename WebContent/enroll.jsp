<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="zh-CN" class="bootstrap-admin-vertical-centered">
<head>
    <meta charset="UTF-8">
    <title>注册</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap-theme.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap-admin-theme.css">
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/login.js"></script>
</head>

<body class="bootstrap-admin-without-padding">
<div class="container">
    <div class="row">
        <div class="col-lg-12">
            <h1 class="text-center">注册</h1>
            <form class="bootstrap-admin-login-form" method="post" action="${pageContext.request.contextPath}/EnrollServlet">
                <div class="form-group">
                    <label class="control-label" for="username">用户名</label>
                    <input type="text" class="form-control" id="username" name="username" required="required" placeholder="用户名"/>
                </div>
                <div class="form-group">
                    <label class="control-label" for="password">密码</label>
                    <input type="text" class="form-control" id="password" name="password" required="required" placeholder="密码"/>
                </div>
                <input type="submit" class="btn btn-lg btn-primary" value="注&nbsp;&nbsp;&nbsp;&nbsp;册"/>
            </form>
        </div>
    </div>
</div>
</body>
</html>
