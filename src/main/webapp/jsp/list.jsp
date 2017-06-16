<%--
  Created by IntelliJ IDEA.
  User: Zhangx
  Date: 2017/4/21
  Time: 10:12
  To change this template use File | Settings | File Templates.
--%>
<%@page import="java.util.Enumeration" %>
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<html>
<body>
<table style="width: 500px;" align="center">
    <tr>
        <td valign="top">
            <ul id="usertorole_dl" style="width: 150px; height: 400px;" title="用户"></ul>
        </td>
        <td>
            <ul id="usertorole_left_dl" style="width: 150px;height: 400px" title="未选中角色"></ul>
        </td>
        <td align="center" valign="middle" style="width: 100%">
            <a id="role_to_right" class="easyui-linkbutton" data-options="iconCls:'icon-toright',disabled:true"></a>
            <br /> <br>
            <a id="role_to_toright" class="easyui-linkbutton" data-options="iconCls:'icon-totoright'"></a>
            <br /><br>
            <a id="role_to_left" class="easyui-linkbutton"
               data-options="iconCls:'icon-toleft',disabled:true"></a><br><br>
            <a id="role_to_toleft" class="easyui-linkbutton"
               data-options="iconCls:'icon-totoleft'"></a><br /><br>
        </td>
        <td>
            <ul id="usertorole_right_dl" style="width: 150px;height: 400px;" title="已选中角色"></ul>
        </td>
    </tr>
    <tr>
        <td align="center" style="padding-top: 20px">
            <a class="easyui-linkbutton usertoroleSave" data-options="iconCls:'icon-ok'" style="width: 80px;display: inline-block">确定</a>
        </td>
    </tr>
</table>


</body>
</html>
