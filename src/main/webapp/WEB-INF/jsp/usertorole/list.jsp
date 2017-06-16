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
            <ul id="usertorole_left_dl"  class="easyui-datalist" style="width: 150px;height: 400px" title="未选中角色"></ul>
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
            <ul id="usertorole_right_dl" class="easyui-datalist" style="width: 150px;height: 400px;" title="已选中角色"></ul>
        </td>
    </tr>
    <tr>
        <td></td>
        <td align="center" style="padding-top: 20px">
            <a class="easyui-linkbutton usertoroleSave" data-options="iconCls:'icon-ok'" style="width: 80px;display: inline-block">确定</a>
        </td>
        <td></td>
    </tr>
</table>
 <script>
     $('#usertorole_dl').datalist({
         url:'${pageContext.request.contextPath}/usermgr/listall.do',
         valueField:'userId',
         textField:'username',
         onSelect: function (index, record) {
             $('#usertorole_right_dl').datalist({
                 url: '${pageContext.request.contextPath}/usertorole/getrolelistbyuserid.do?id=' + record.userId,
                 valueField: 'roleId',
                 textField: 'roleName',
                 singleSelect: false,
                 onSelect: function () {
                     $('#role_to_left').linkbutton('enable');
                     $('#role_to_right').linkbutton('disable');
                     $('#usertorole_left_dl').datalist('unselectAll');
                 },
                 onUnselectAll: function () {
                     $('#role_to_left').linkbutton('disable');
                 }
             });
             $('#usertorole_left_dl').datalist({
                 url: '${pageContext.request.contextPath}/usertorole/getunselectedrolesbyuserid.do?id=' + record.userId,
                 valueField: 'roleId',
                 textField: 'roleName',
                 singleSelect: false,
                 onSelect: function () {
                     $('#role_to_right').linkbutton('enable');
                     $('#role_to_left').linkbutton('disable');
                     $('#usertorole_right_dl').datalist('unselectAll');
                 },
                 onUnselectAll: function () {
                     $('#role_to_right').linkbutton('disable');
                 }
             });
         }

     });

     $('#role_to_right').click(function () {
         var leftRows = $('#usertorole_left_dl').datalist('getSelections');
         for (var i = 0; i < leftRows.length; i++) {
             var obj = leftRows[i];
             var index = $('#usertorole_left_dl').datalist('getRowIndex', obj);
             $('#usertorole_left_dl').datalist('deleteRow', index);
             $('#usertorole_right_dl').datalist('appendRow', {
                 roleId: obj.roleId,
                 roleName: obj.roleName
             });
         }
     });
     $('#role_to_toright').click(function () {
         $('#usertorole_left_dl').datalist('loadData', {
             total: 0,
             rows: []
         });
         $('#usertorole_right_dl').datalist({
             url: '${pageContext.request.contextPath}/rolemgr/listdata.do'
         });
     });
     $('#role_to_left').click(function () {
         var rightRows = $('#usertorole_right_dl').datalist('getSelections');
         for (var i = 0; i < rightRows.length; i++) {
             var obj = rightRows[i];
             var index = $('#usertorole_right_dl').datalist('getRowIndex', obj);
             $('#usertorole_right_dl').datalist('deleteRow', index);
             $('#usertorole_left_dl').datalist('appendRow', {
                 roleId: obj.roleId,
                 roleName: obj.roleName
             });
         }
     });
     $('#role_to_toleft').click(function () {
         $('#usertorole_right_dl').datalist('loadData', {
             total: 0,
             rows: []
         });
         $('#usertorole_left_dl').datalist({
             url: '${pageContext.request.contextPath}/rolemgr/listdata.do'
         });
     });

     $('.usertoroleSave').click(
         function () {
             var postData = $('#usertorole_right_dl').datalist('getData');
             var data = JSON.stringify(postData);
             var userId = $('#usertorole_dl').datalist('getSelected').userId;
             $.post('${pageContext.request.contextPath}/usertorole/save.do', {
                 "rolelist": data,
                 "userId": userId
             }, function (result) {
                 $.messager.show({
                     title: '结果',
                     msg: result,
                     showType: 'show'
                 });
             }, "text");
         }
     );


 </script>

</body>
</html>
