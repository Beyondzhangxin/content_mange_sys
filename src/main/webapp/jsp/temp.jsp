<%--
  Created by IntelliJ IDEA.
  User: Jason
  Date: 2017/3/27
  Time: 16:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html" language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<html>
<head>
    <tite>权限管理</tite>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.easyui.min.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/themes/icon.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/themes/default/easyui.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/themes/demo.css">
</head>
<body>
<input class="easyui-switchbutton" data-options="onText:'是',offText:'否',value:1" name="isSys">
</body>
</html>
