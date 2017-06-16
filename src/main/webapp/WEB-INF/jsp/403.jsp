<%@page import="org.springframework.security.core.authority.SimpleGrantedAuthority"%>
<%@page import="org.springframework.security.core.GrantedAuthority"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Enumeration"%>
<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core' %>
<%@ 
	page language="java" 
	contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    isELIgnored="false"
%>
<%@ page import="org.springframework.security.core.Authentication" %>
<%@ page import="org.springframework.security.core.context.SecurityContextHolder" %>
<html>
<head>

</head>
<body>
	<div>对不起，你没有操作给功能的权限！</div>
</body>
</html>