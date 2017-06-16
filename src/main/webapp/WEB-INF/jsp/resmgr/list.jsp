<%--
  Created by IntelliJ IDEA.
  User: Zhangx
  Date: 2017/4/1
  Time: 15:48
  To change this template use File | Settings | File Templates.
--%>
<%--<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>--%>
<%--<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page pageEncoding="UTF-8" %>

<%@ page contentType="text/html;charset=utf-8" language="java" isELIgnored="false" %>
<html>
<head>
    <title>资源管理</title>
    <meta http-equiv="content-type" content="text/html;charset=utf-8">
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.easyui.min.js"></script>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/themes/demo.css">
</head>
<body>
<%--table--%>
<table id="res_dg" style="width:100%;height:95%"></table>
<%--toolbar--%>
<div id="res_tb" style="padding: 2px 5px;">
    <table style="width: 100%">
        <tr>
            <td width="50%" align="left"></td>
            <td width="50%" align="right">
                <a onclick="res_save()" class="easyui-linkbutton"
                   iconCls="icon-add" plain="true"></a>
                <a onclick="res_edit()" class="easyui-linkbutton"
                   iconCls="icon-edit" plain="true"></a>
                <a onclick="res_del()" class="easyui-linkbutton"
                   iconCls="icon-cancel" plain="true"></a></td>
        </tr>
    </table>
</div>
<%--add edit popup window--%>
<div id="res_add_edit" class="easyui-window" title="添加资源" data-options="modal:true,closed:true,iconCls:'icon-save'"
     style="width: 500px; height: 500px; padding: 10px 80px;text-align: center;">
    <form id="res_add_edit_fm" method="post"
          style="align-content: space-around;text-align: right;margin:0 auto;display: inline-block;">
        <div style="margin-top: 15px; position: absolute; top: -999em">
            <span class="res_span">ID: <input type="text" class="easyui-textbox" name="resourceId"></span>
        </div>
        <div style="margin-top: 15px;">
            <span class="res_span">资源名称: <input type="text" class="easyui-textbox" name="resourceName"></span>
        </div>
        <div style="margin-top: 15px;text-align:left;">
            <span class="res_span">资源类型: <select
                    class="easyui-combobox" name="resourceType" editable="false" style="width: 100px">
                <option value="url">链接</option>
                <option value="metho">方法</option>
                <option value="menu">菜单</option>
            </select></span>
        </div>
        <div style="margin-top: 15px;">
            <span class="res_span">描述: <input type="text" class="easyui-textbox" name="resourceDesc"></span>
        </div>
        <div style="margin-top: 15px;">
            <span class="res_span">资源路径: <input type="text" class="easyui-textbox" name="resourcePath"></span>
        </div>
        <div style="margin-top: 15px;text-align:left;margin-left:12px;">
            <span class="res_span">优先级: <select
                    class="easyui-combobox" name="priority" editable="false">
                <option value="1">高</option>
                <option value="2">中等</option>
                <option value="3">低</option>
            </select></span>
        </div>
        <div style="margin-top: 15px;text-align:left;">
            <span class="res_span">是否开启: <input  class="easyui-switchbutton"
                                                data-options="checked:true,onText:'是',offText:'否',value:1"
                                                name="enable" ></span>
        </div>
        <div style="margin-top: 15px;text-align:left;margin-left:-24px;">
            <span class="res_span">是否系统资源: <input  class="easyui-switchbutton"
                                                  data-options="checked:true,onText:'是',offText:'否',value:1"
                                                  name="issys"></span>
        </div>
        <div style="margin-top: 15px;">
            <span class="res_span">模块ID: <input type="text" class="easyui-textbox" name="moduleId"></span>
        </div>
        <div class="res_button">
            <a href="javascript:void(0)" class="easyui-linkbutton"
               onclick="res_add_save()"
               style="align: right; margin: 50px 0px 0 23px; padding: 1px 17px;">提交</a>
            <a class="easyui-linkbutton"
               style="align: right; margin: 50px 0 0 33px; padding: 0 15px;"
               onclick="clearForm()">重置</a>
        </div>
    </form>

</div>
<%--del popup window--%>
<div id="res_del_window" class="easyui-window" title="删除权限"
     data-options="modal:true,closed:true,iconCls:'icon-del'"
     style="width: 400px; height: 250px; text-align: center; padding: 30px 0">
    <div data-options="region:'center'" style="padding: 10px;">确定删除该权限？</div>
    <a class="easyui-linkbutton delete_reslist"
       data-options="iconCls:'icon-ok'" href="javascript:void(0)"
       style="width: 80px; display: inline-block; margin: 30px 15px 0 0;">确定</a>
    <a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'"
       href="javascript:void(0)" onclick="$('#res_del_window').window('close')"
       style="width: 80px; display: inline-block; margin: 30px 0 0 15px;">取消</a>
</div>
<script>
    <%--datagrid property setting--%>
    $("#res_dg").datagrid(
        {
            url: '${pageContext.request.contextPath}/resmgr/pagelist.do',
            idField: 'resourceId',
            fitColumns: true,
            striped: true,
            loadMsg: '加载中.......',
            toolbar: '#res_tb',
            pagination: true,
            columns: [[
                {
                    checkbox: true,
                    field: 'ck'
                },
                {
                    field: 'resourceName',
                    title: '资源名称',
                    align: 'center',
                    width: 80
                },
                {
                    field: 'resourceType',
                    title: '资源类型',
                    align: 'center',
                    width: 80
                },
                {
                    field: 'resourceDesc',
                    title: '描述',
                    align: 'center',
                    width: 80
                },
                {
                    field: 'resourcePath',
                    title: '资源路径',
                    align: 'center',
                    width: 80
                },
                {
                    field: 'priority',
                    title: '优先级',
                    align: 'center',
                    width: 80,
                    formatter: function (value) {
                        return value == 1 ? '高' : (value == 2 ? '中等' : '低');
                    }
                },
                {
                    field: 'enable',
                    title: '是否开启',
                    align: 'center',
                    width: 80,
                    formatter: function (value) {
                        return value == 1 ? '是' : '否';
                    }
                },
                {
                    field: 'issys',
                    title: '是否系统资源',
                    align: 'center',
                    width: 80,
                    formatter: function (value) {
                        return value == 1 ? '是' : '否';
                    }
                },
                {
                    field: 'moduleId',
                    title: '模块ID',
                    align: 'center',
                    width: 80
                }
            ]]
        }
    );
    function res_save() {
        $('#res_add_edit').window('open');
        $('#res_add_edit_fm').form('clear');
    }
    function res_add_save() {
        $('#res_add_edit_fm').form('submit', {
            url: '${pageContext.request.contextPath}/resmgr/save.do',
            onSubmit: function () {
                return $(this).form('validate');
            },
            success: function (result) {
                $('#res_add_edit').window('close');
                $('#res_dg').datagrid('reload');
                $.messager.show({
                    title: "结果",
                    msg: "保存成功！"
                });
            },
            error: function () {
                $('#res_add_edit').window('close');
                $.messager.show({
                    title: "结果",
                    msg: "保存失败！"
                });

            }
        });
    }

    function res_edit() {

        var rows = $('#res_dg').datagrid('getSelections');
        if (rows.length == 1) {
            $('#res_add_edit').window('open');
            $('#res_add_edit_fm').form('reset');
            $('#res_add_edit_fm').form('load', {
                resourceName: rows[0].resourceName,
                resourceType: rows[0].resourceType,
                resourcePath: rows[0].resourcePath,
                priority: rows[0].priority,
                enable: rows[0].enable,
                issys: rows[0].issys,
                moduleId: rows[0].moduleId,
            });
        } else {
            $.messager.alert('警告', '选择一条进行修改！');
        }
    }
    function res_del() {
        var rows = $('#res_dg').datagrid('getSelections');
        if (rows.length > 0) {
            $('#res_del_window').window('open');
            $('.delete_reslist').click(
                function () {
                    var param = [];
                    for (var i = 0; i < rows.length; i++) {
                        param.push(rows[i].resourceId);
                    }
                    $.ajax({
                        url: '${pageContext.request.contextPath}/resmgr/delete.do?ids=' + param,
                        dataType: 'json',
                        success: function (data) {
                            $('#res_del_window').window('close');
                            $('#res_dg').datagrid('reload');
                            $('#res_dg').datagrid('clearChecked');
                            $.messager.show({
                                title: '结果',
                                msg: data.msg
                            });
                        }

                    });
                }
            );
        }
    }
</script>


</body>
</html>
