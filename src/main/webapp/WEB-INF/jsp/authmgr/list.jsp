<%--
  Created by IntelliJ IDEA.
  User: Jason
  Date: 2017/3/27
  Time: 16:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html" language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<html>

    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.easyui.min.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/themes/icon.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/themes/default/easyui.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/themes/demo.css">

<body>
<%--数据列表--%>
<table id="authdg" style="width:100%;height:95%"></table>

<%--工具栏模块，增加、修改、删除功能--%>
<div id="auth_tb" style="padding: 2px 5px;">
    <table style="width: 100%">
        <tr>
            <td width="50%" align="left"></td>
            <td width="50%" align="right">
                <a onclick="auth_add_save()" class="easyui-linkbutton"
                   iconCls="icon-add" plain="true"></a>
                <a onclick="auth_add_edit()" class="easyui-linkbutton"
                   iconCls="icon-edit" plain="true"></a> <a
                    onclick="auth_del()" class="easyui-linkbutton"
                    iconCls="icon-cancel" plain="true"></a></td>
        </tr>
    </table>
</div>
<%--添加或者修改的弹出窗口--%>
<div id="auth_add_edit" class="easyui-window" title="添加权限" data-options="modal:true,closed:true,iconCls:'icon-save'"
     style="width: 500px; height: 400px; padding: 10px 80px;text-align: center;">
    <form id="auth_add_edit_fm" method="post" style="align-content: space-around;text-align: right;margin:0 auto;display:inline-block;">
        <div style="margin-top: 15px; position: absolute; top: -999em">
            <span class="auth_span">ID: <input type="text" class="easyui-textbox" name="authorityId"></span>
        </div>
        <div style="margin-top: 15px;">
            <span class="auth_span">权限标识: <input type="text" class="easyui-textbox" name="authorityMark"></span>
        </div>
        <div style="margin-top: 15px;">
            <span class="auth_span">权限名称: <input type="text" class="easyui-textbox" name="authorityName"></span>
        </div>
        <div style="margin-top: 15px;">
            <span class="auth_span">描述: <input type="text" class="easyui-textbox" name="authorityDesc"></span>
        </div>
        <div style="margin-top: 15px;text-align: left">
            <span class="auth_span">是否开启:
            <input class="easyui-switchbutton" data-options="checked:true,onText:'是',offText:'否',value:1" name="enable"></span>
        </div>
        <div style="margin-top: 15px;text-align: left;margin-left:-24px;">
            <span class="auth_span">是否系统权限:
                <input class="easyui-switchbutton"
                                                   data-options="checked:true,onText:'是',offText:'否',checked:true,value:1"
                                                   name="isSys"></span>
        </div>
        <div style="margin-top: 15px;">
            <span class="auth_span">模块ID: <input type="text" class="easyui-textbox" name="moduleId"></span>
        </div>
        <div class="auth_button">
            <a href="javascript:void(0)" class="easyui-linkbutton"
               onclick="auth_save()"
               style="align: right; margin: 50px 0px 0 23px; padding: 1px 17px;">提交</a>
            <a class="easyui-linkbutton"
               style="align: right; margin: 50px 0 0 33px; padding: 0 15px;"
               onclick="clearForm()">重置</a>
        </div>
    </form>

</div>
<%--删除弹出窗口--%>
<div id="auth_del_window" class="easyui-window" title="删除权限"
     data-options="modal:true,closed:true,iconCls:'icon-del'"
     style="width: 400px; height: 250px; text-align: center; padding: 30px 0">
    <div data-options="region:'center'" style="padding: 10px;">确定删除该权限？</div>
    <a class="easyui-linkbutton delete_authlist"
       data-options="iconCls:'icon-ok'" href="javascript:void(0)"
       style="width: 80px; display: inline-block; margin: 30px 15px 0 0;">确定</a>
    <a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'"
       href="javascript:void(0)" onclick="$('#auth_del_window').window('close')"
       style="width: 80px; display: inline-block; margin: 30px 0 0 15px;">取消</a>
</div>
<%--完成datagrid的属性设置--%>
<script>

    $("#authdg").datagrid(
        {
            url: '${pageContext.request.contextPath}/authmgr/listall.do',
            idField: 'authorityId',
            fitColumns: true,
            resizeHandle: 'right',
            striped: true,
            loadMsg: 'please wait.......',
            toolbar: '#auth_tb',
            pagination: true,
            total: 10,
            columns: [[
                {
                    field: 'authorityMark',
                    title: '权限标识',
                    align: 'center',
                    width: 80,
                    checkbox: true
                },
                {
                    field: 'authorityName',
                    title: '权限名称',
                    align: 'center',
                    width: 80
                },
                {
                    field: 'authorityDesc',
                    align: 'center',
                    title: '描述',
                    width: 120
                },
                {
                    field: 'enable',
                    align: 'center',
                    title: '是否开启',
                    width: 100,
                    formatter: function (value) {
                        return value == true ? '是' : '否';
                    }
                },
                {
                    field: 'isSys',
                    align: 'center',
                    title: '是否系统权限',
                    width: 80,
                    formatter: function (value) {
                    return value == true ? '是' : '否';
                }
                },
                {
                    field: 'moduleId',
                    align: 'center',
                    title: '模块ID',
                    width: 100
                }
            ]]
        }
    );

    <%--点击添加或者修改后弹出窗口--%>

    function auth_add_save() {
        $("#auth_add_edit").window("open");
        $('#auth_add_edit_fm').form('clear');
    }

    function auth_add_edit() {

        var rows = $('#authdg').datagrid('getSelections');
        if (rows.length == 1) {
            $('#auth_add_edit').window('open');
            $('#auth_add_edit_fm').form('reset');
            $('#auth_add_edit_fm').form('load', {
                authorityId: rows[0].authorityId,
                authorityMark: rows[0].authorityMark,
                authorityName: rows[0].authorityName,
                authorityDesc: rows[0].authorityDesc,
                enable: rows[0].enable,
                isSys: rows[0].isSys,
                moduleId: rows[0].moduleId
            });
        } else {
            $.messager.alert('警告', '只能选择一条进行修改！');
        }
    }
    /*删除窗口点击确定后之行的操作*/
    function auth_del() {
        var rows = $('#authdg').datagrid('getSelections');
        if (rows.length > 0) {
            $('#auth_del_window').window('open');
            $('.delete_authlist').click(
                function () {
                    var param = [];
                    for (var i = 0; i < rows.length; i++) {
                        param.push(rows[i].authorityId);
                    }
                    $.ajax({
                        url: '${pageContext.request.contextPath}/authmgr/deleteAuthlist.do?authorityIds=' + param,
                        dataType: 'json',
                        success: function (data) {
                            $('#auth_del_window').window('close');
                            $('#authdg').datagrid('reload');
                            $('#authdg').datagrid('clearChecked');
                            $.messager.show({
                                title: '结果',
                                msg: data.mssg
                            });
                        }

                    });
                }
            );
        }
    }


    <%--点击添加窗口的提交按钮后的操作--%>
    function auth_save() {
        $('#auth_add_edit_fm').form('submit', {
            url: '${pageContext.request.contextPath}/authmgr/authSave.do',
            onSubmit: function () {
                return $(this).form('validate');
            },
            success: function () {
                $('#auth_add_edit').window('close');
                $('#authdg').datagrid('reload');
                $.messager.show({
                    title: "结果",
                    msg: "保存成功！"
                });
            },
            error: function () {
                $('#auth_add_edit').window('close');
                $.messager.show({
                    title: "结果",
                    msg: "保存失败！"
                });

            }
        });
    }

    //    重置按钮

    function clearForm() {
        iter
        $('#auth_add_edit_fm').form('clear');
        $('#auth_add_edit_fm').form('clear');
    }
</script>

</body>
</html>
