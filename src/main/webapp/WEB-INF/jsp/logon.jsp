<%@page import="org.springframework.security.core.authority.SimpleGrantedAuthority"%>
<%@page import="org.springframework.security.core.GrantedAuthority"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Enumeration"%>
<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core' %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
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
	<meta http-equiv="content-type" content="text/html;charset=utf-8">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/themes/cms.css">

	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/easyui-lang-zh_CN.js"></script>
	<link rel="shortcut icon" href="${pageContext.request.contextPath}/themes/icons/casic.ico" type="image/x-icon" />
	<link rel="icon" href="${pageContext.request.contextPath}/themes/icons/casic.ico" type="image/x-icon" />

	<link rel="stylesheet" href="${pageContext.request.contextPath}/kindeditor-4.1.7/themes/default/default.css" />
	<link rel="stylesheet" href="${pageContext.request.contextPath}/kindeditor-4.1.7/plugins/code/prettify.css" />
	<script charset="utf-8" src="${pageContext.request.contextPath}/kindeditor-4.1.7/kindeditor.js"></script>
	<script charset="utf-8" src="${pageContext.request.contextPath}/kindeditor-4.1.7/lang/zh_CN.js"></script>
	<script charset="utf-8" src="${pageContext.request.contextPath}/kindeditor-4.1.7/plugins/code/prettify.js"></script>

	<title>航天智慧内容管理系统</title>
	<script type="text/javascript">
		function buildNav(){
			var username = $("#userName").text();
			$.ajax({
				url:"${pageContext.request.contextPath}/logon/menu.do",
				type:"post",
				data:{username:username},
				dataType:"json",
				success:function(result)
				{
					for(var o in result){
						treeMenuId = "menu_" + result[o].id;
						if(result[o].id!=4){
						$('#menuDiv').accordion('add',{
							id:treeMenuId,
							title:result[o].text,
							iconCls:result[o].iconCls,
							content:'<div style="padding:10px"></div>'
						});
						}else{
							 $('#menuDiv').accordion('add',{
								id:treeMenuId,
								title:result[o].text,
								iconCls:result[o].iconCls,
								content:'<div style="padding:10px"></div>'
							});
						}
						var mid = '#' + treeMenuId;
						$(mid).tree({
							data: result[o].children,
							onClick:function (node) {
								$.ajax({
									url:"${pageContext.request.contextPath}/"+node.funcid+"/" + node.action +".do",
									type:"post",
									complete: function(XMLHttpRequest,textStatus) {
										if(XMLHttpRequest.status==404||XMLHttpRequest.status==403){
											$('#mainTabs').tabs('add',{
												title:node.text,
												closable:true,
												iconCls:node.iconCls,
												href:"${pageContext.request.contextPath}/error_403.html"
											});
										}
										if (node.children)
								        {
								        	if(node.state=='open' && XMLHttpRequest.status==200)
								        		$(mid).tree('collapse',node.target);
								        	else
								        		$(mid).tree('expand',node.target);
								        }else{
								        	if ($("#mainTabs").tabs('exists', node.text)) {
									            $('#mainTabs').tabs('select', node.text);
								        	}else{
								        		$('#mainTabs').tabs('add',{
													title:node.text,
													closable:true,
													iconCls:node.iconCls,
													href:"${pageContext.request.contextPath}/"+node.funcid+"/" + node.action +".do"
												});
								        	}
								        }
									},
								});

						    }
						});
					}
					$('#menuDiv').accordion({
						active:0
					});
				}
			});
		}
	</script>

</head>
<body onload="buildNav();">
<div id="cc" class="easyui-layout" style="width:100%;height:100%;">
		<div data-options="region:'north'" style="height:50px;background: url(${pageContext.request.contextPath}/images/logo.gif)" >
		<p align="right" ><br></p>
		</div>
		<div data-options="region:'south',split:true" style="height:50px"><p align="center" width="100%">Copyright ©1996-2016 航天科工智慧产业发展有限公司</p></div>
		<div data-options="region:'west',split:true,iconCls:'icon-nav'" id="menuDiv" class="easyui-accordion" title="功能导航" style="width:220px;">
		</div>
		<div data-options="region:'center',tools:'#tt'" class="easyui-panel" title="工作区" >
			<div class="easyui-tabs" id="mainTabs" style="width:100%;height:100%" data-options="fit:true,border:false,plain:true">
			</div>
		</div>
	</div>
	<div id="tt">
		<span style="height:30px;font-size:10px" >欢迎：&nbsp;&nbsp;</span>
		<span style="height:30px;font-size:10px" id="userName"><sec:authentication property="principal.username" /></span>
		<a href="<%=request.getContextPath()%>/logout" class="icon-logout" title="退出登录"></a>
	</div>
	</body>

</html>
