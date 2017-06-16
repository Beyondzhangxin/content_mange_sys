<%--
  Created by IntelliJ IDEA.
  User: Jason
  Date: 2017/3/27
  Time: 16:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<html>
<
<head>
    <title>用户管理</title>
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/themes/icon.css">
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/themes/cms.css">
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/demo/demo.css">
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/all.css">
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/siteAdd.css">
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/js/jquery.easyui.min.js"></script>
</head>
<body>
<script type="text/javascript">
    function sitedoSearch(value, name) {
        console.log(name)
        $
            .ajax({
                url: '${pageContext.request.contextPath}/usermgr/searchList.do?username='
                + value,
                type: 'get',
                success: function (data) {
                    $("#user_dg").datagrid("loadData", data);
                },
                error: function () {
                    alert("ssss")
                }
            });
    }
    $(function () {
        $.extend($.fn.validatebox.defaults.rules, {
            equals: {
                validator: function (value, param) {
                    return value == $(param[0]).val();
                },
                message: "输入密码不一致,请重新输入！"
            }
        });
    });
</script>

<table id="user_dg" style="width: 100%; height: 100%"
>
    <%--<thead>
    <tr>
        <th data-options="field:'ck',checkbox:true"></th>
        <th data-options="field:'username',width:110,align:'center'">用户名</th>
        <th data-options="field:'userRealname',width:110,align:'center'">真实姓名</th>
        &lt;%&ndash; <th data-options="field:'gender',width:110,align:'center',
                formatter:function(value){
                   if(value=='1'){return '<span style=color:green>男</span>';}
                   if(value=='0'){return '<span style=color:red>女</span>';}
                             }">性别</th> &ndash;%&gt;
        <th
                data-options="field:'TDepartment',
						formatter:function(value,rec){
						if(value==null){return '';}else {return value.depName;}
						},
						width:120,align:'center'"
                style="width: 15%">部门
        </th>
        <th data-options="field:'position',width:120,align:'center'">职位</th>
        <th
                data-options="field:'TRole',formatter:function(value,rec){
						if(value==null){return '';}else {return value.roleName;}
						},width:120,align:'center'">角色
        </th>
        <th data-options="field:'mobilePhoneNum',width:110,align:'center'">联系电话</th>
        <th data-options="field:'emailAddr',width:130,align:'center'">电子邮箱</th>
    </tr>
    </thead>--%>
</table>
<script>
    $('#user_dg').datagrid({
        url: '${pageContext.request.contextPath}/usermgr/listdata.do',
        idField: 'userId',
        fitColumns: true,
        loadMsg: '数据加载中...请等待',
        singleSelect: false,
        pagination: true,
        toolbar: '#user_tt',
        striped: true,
        nowrap: true,
        columns: [[
            {
                field: 'ck',
                checkbox: true
            },
            {
                field: 'username',
                title: '用户名',
                width: 110,
                align: 'center'
            },
            {
                field: 'userRealname',
                title: '真实姓名',
                width: 110,
                align: 'center'
            },
            {
                field: 'TDepartment',
                title: '部门',
                width: 120,
                align: 'center',
                formatter: function (value, rec) {
                    if (value == null) {
                        return '';
                    } else {
                        return value.depName;
                    }
                }
            },
            {
                field: 'position',
                title: '职位',
                width: 120,
                align: 'center'
            },
            {
                field: 'TRole',
                title: '角色',
                width: 120,
                align: 'center',
                formatter: function (value, rec) {
                    if (value == null) {
                        return '';
                    } else {
                        return value.roleName;
                    }
                }
            },
            {
                field: 'mobilePhoneNum',
                title: '联系电话',
                width: 110,
                align: 'center'
            },
            {
                field: 'emailAddr',
                title: '电子邮箱',
                width: 130,
                align: 'center'
            }
        ]]
    });

</script>

<!--添加页-->
<div id="add_user" class="easyui-window" title="添加用户"
     data-options="modal:true,closed:true,iconCls:'icon-save'"
     style="width: 600px; height: 500px; padding: 10px 80px; text-align: center;">
    <form id="user_fm" method="post" style="text-align: right;margin:0 auto;display: inline-block;">


        <div style="margin-top: 15px; position: absolute; top: -999em">
				<span class="role_span">ID: <input type="text"
                                                   class="easyui-textbox" name="userId"></span>
        </div>
        <div style="margin-top: 15px;">
				<span class="role_span">用 户 名: <input type="text"
                                                      class="easyui-textbox" name="username"
                                                      data-options="required:true"></span>
        </div>
        <div style="margin-top: 15px;">
				<span class="role_span">真实姓名: <input type="text"
                                                     class="easyui-textbox" name="userRealname"
                                                     data-options="required:true"></span>
        </div>
        <div style="margin-top: 15px;">
				<span class="role_span">密 &nbsp; 码: <input type="text" id="pwd1"
                                                           class="easyui-passwordbox" prompt="password" iconWidth="28"
                                                           name="password" required=true></span>
        </div>
        <div style="margin-top: 15px;">
				<span class="role_span">确认密码: <input type="text"
                                                     class="easyui-passwordbox" prompt="Password" iconWidth="28"
                                                     name="Password" data-options="required:true"
                                                     validType="equals['#pwd1']"></span>
        </div>
        <div style="margin-top: 15px;">
				<span class="role_span">部 &nbsp; 门: <input name="depId"
                                                           class="easyui-combobox" id="dep_combobox">
				</span>
        </div>
        <div style="margin-top: 15px;">
				<span class="role_span">角 &nbsp; 色: <input name="roleId"
                                                           class="easyui-combobox" id="role_combobox">
				</span>
        </div>
        <div style="margin-top: 15px;">
				<span class="role_span">职 &nbsp; 位: <input type="text"
                                                           class="easyui-textbox" name="position"
                ></span>
        </div>
        <div style="margin-top: 15px;">
				<span class="role_span">手 机 号: <input type="text"
                                                      class="easyui-textbox" name="mobilePhoneNum"
                ></span>
        </div>
        <div style="margin-top: 15px;">
				<span class="role_span">电子邮箱: <input type="text"
                                                     class="easyui-textbox" name="emailAddr"></span>
        </div>
        <div class="role_button">
            <a href="javascript:void(0)" class="easyui-linkbutton"
               onclick="saveUser()"
               style="align: right; margin: 50px 0px 0 23px; padding: 1px 17px;">提交</a>
            <a href="javascript:void(0)" class="easyui-linkbutton"
               style="align: right; margin: 50px 0 0 33px; padding: 0 15px;"
               onclick="clearForm()">重置</a>
        </div>
    </form>
</div>


<!--修改-->
<div id="user_e" class="easyui-window" title="修改用户"
     data-options="modal:true,closed:true,iconCls:'icon-save'"
     style="width: 600px; height: 500px; padding: 10px 80px; text-align: center;">
    <form method="post" id="user_ed" style="text-align: right;margin:0 auto;display: inline-block;">

        <div style="margin-top: 15px; position: absolute; top: -999em">
				<span class="role_span">ID: <input type="text"
                                                   class="easyui-textbox" name="userId"
                                                   data-options="required:true"></span>
        </div>
        <div style="margin-top: 15px;">
				<span class="role_span">用 户 名: <input type="text"
                                                      class="easyui-textbox" name="username"
                                                      data-options="required:true"></span>
        </div>
        <div style="margin-top: 15px;">
				<span class="role_span">真实姓名: <input type="text"
                                                     class="easyui-textbox" name="userRealname"
                                                     data-options="required:true"></span>
        </div>
        <div style="margin-top: 15px;">
				<span class="role_span">密 &nbsp; 码: <input type="text" id="pwd2"
                                                           class="easyui-passwordbox" prompt="password" iconWidth="28"
                                                           name="password" data-options="required:true"></span>
        </div>
        <div style="margin-top: 15px;">
				<span class="role_span">确认密码: <input type="text"
                                                     class="easyui-passwordbox" prompt="Password" iconWidth="28"
                                                     name="Password"
                                                     data-options="required:true" validType="equals['#pwd2']"></span>
        </div>
        <div style="margin-top: 15px;">
				<span class="role_span">部 &nbsp; 门: <input name="depId"
                                                           class="easyui-combobox" id="dep2_combobox">
				</span>
        </div>
        <div style="margin-top: 15px;">
				<span class="role_span">角 &nbsp; 色: <input name="roleId"
                                                           class="easyui-combobox" id="role2_combobox">
				</span>
        </div>
        <div style="margin-top: 15px;">
				<span class="role_span">职 &nbsp; 位: <input type="text"
                                                           class="easyui-textbox" name="position"
                ></span>
        </div>
        <div style="margin-top: 15px;">
				<span class="role_span">手 机 号: <input type="text"
                                                      class="easyui-textbox" name="mobilePhoneNum"
                ></span>
        </div>
        <div style="margin-top: 15px;">
				<span class="role_span">电子邮箱: <input type="text"
                                                     class="easyui-textbox" name="emailAddr"></span>
        </div>
        <div class="role_button">
            <a href="javascript:void(0)" class="easyui-linkbutton"
               onclick="editUser()"
               style="align: right; margin: 50px 0px 0 23px; padding: 1px 17px;">提交</a>
            <a href="javascript:void(0)" class="easyui-linkbutton"
               style="align: right; margin: 50px 0 0 33px; padding: 0 15px;"
               onclick="clearForm()">重置</a>
        </div>
    </form>
</div>


<!--删除-->
<div id="user_d" class="easyui-window" title="删除用户"
     data-options="modal:true,closed:true,iconCls:'icon-save'"
     style="width: 400px; height: 250px; text-align: center; padding: 30px 0">
    <div data-options="region:'center'" style="padding: 10px;">确定删除该用户？</div>
    <a class="easyui-linkbutton delete_userList"
       data-options="iconCls:'icon-ok'" href="javascript:void(0)"
       style="width: 80px; display: inline-block; margin: 30px 15px 0 0;">确定</a>
    <a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'"
       href="javascript:void(0)" onclick="$('#user_d').window('close')"
       style="width: 80px; display: inline-block; margin: 30px 0 0 15px;">取消</a>
</div>


<!--按钮-->
<div id="user_tt" style="padding: 2px 5px;">
    <table style="width: 100%">
        <tr>
            <td width="50%" align="left"><input class="easyui-searchbox"
                                                id="userName" name="username" type="text" style="width: 220px;"
                                                searcher="sitedoSearch" prompt="请输入用户信息"></td>
            <td width="50%" align="right">
                <security:authorize access="hasAuthority('AUTH_ADMIN')">
                    <a onclick="javascript:user_add()" class="easyui-linkbutton"
                       iconCls="icon-add" plain="true"></a>
                </security:authorize>
                <a
                        onclick="javascript:user_edit()" class="easyui-linkbutton"
                        iconCls="icon-edit" plain="true"></a> <a
                    onclick="javascript:user_delect()" class="easyui-linkbutton"
                    iconCls="icon-cancel" plain="true"></a></td>
        </tr>
    </table>
</div>
<script>
    var depId = "";
    $("#dep_combobox").combobox({
        prompt: '请选择部门',
        valueField: 'depId',
        textField: 'depName',
        url: '${pageContext.request.contextPath}/deptmgr/listdata.do',
        onSelect: function (data) {
            depId = data.depId;
            $('#ShopType').combobox('setValue', depId);
            console.log(depId)

        }
    });
    $("#role_combobox").combobox({
        prompt: '请选择角色',
        valueField: 'roleId',
        textField: 'roleName',
        url: '${pageContext.request.contextPath}/rolemgr/listdata.do',
        onSelect: function (data) {
            roleId = data.roleId;
            $('#ShopType').combobox('setValue', depId);
            console.log(depId)

        }
    });
    $("#role2_combobox").combobox({
        prompt: '请选择角色',
        valueField: 'roleId',
        textField: 'roleName',
        url: '${pageContext.request.contextPath}/rolemgr/listdata.do',
        onSelect: function (data) {
            roleId = data.roleId;
            $('#ShopType').combobox('setValue', depId);
            console.log(depId)

        }
    });
    $("#dep2_combobox").combobox({
        prompt: '请选择部门',
        valueField: 'depId',
        textField: 'depName',
        url: '${pageContext.request.contextPath}/deptmgr/listdata.do',
        onSelect: function (data) {
            depId = data.depId;
            $('#ShopType').combobox('setValue', depId);
            console.log(depId)

        }
    });
    console.log(depId)

    //重置按钮
    function clearForm() {
        $('#user_fm').form('clear');
        $('#user_ed').form('clear');
    }
    function saveUser() {

        $('#user_fm')
            .form(
                'submit',
                {
                    url: '${pageContext.request.contextPath}/usermgr/saveUpdateUser.do',
                    onSubmit: function () {
                        return $(this).form('validate');
                    },
                    success: function (data) {
                        if (data) {
                            $.messager.show({
                                title: '结果',
                                msg: data
                            });
                            $('#add_user').dialog('close'); // close the dialog
                            $('#user_dg').datagrid('reload'); // reload the user data
                        } else {
                            $.messager.show({
                                title: '结果',
                                msg: "添加失败！"
                            });
                            $('#add_user').dialog('close');
                        }
                    }
                });
    }

    function editUser() {
        $('#user_ed')
            .form(
                'submit',
                {
                    type: 'post',
                    data: $('#user_ed').serialize(),
                    dataType: 'json',
                    url: '${pageContext.request.contextPath}/usermgr/saveUpdateUser.do',
                    onSubmit: function () {
                        return $(this).form('validate');
                    },
                    success: function (result) {
                        $('#user_e').dialog('close'); // close the dialog
                        $('#user_dg').datagrid('reload'); // reload the user data
                        $.messager.show({
                            title: "结果",
                            msg: "修改成功"
                        });
                    },
                    error: function (result) {
                        $.messager.show({
                            title: "结果",
                            msg: "保存失败"
                        });
                    }
                });
    }

    function user_add() {
        $('#add_user').window('open');
        //$('#user_fm').get(0).reset();
        $('#user_fm').form('clear');
    }
    function user_edit() {
        var row = $('#user_dg').datagrid('getSelections');
        if (row.length == 1) {
            $('#user_e').window('open');
            $('#user_ed').form("reset");
            if (row[0].tdepartment != null) {
                $('#user_ed').form('load', {
                    depId: row[0].tdepartment.depId
                });
            }
            if (row[0].trole != null) {
                $('#user_ed').form('load', {
                    roleId: row[0].trole.roleId
                });
            }
            $('#user_ed').form('load', {
                userId: row[0].userId,
                username: row[0].username,
                userRealname: row[0].userRealname,
                password: row[0].password,
                Password: row[0].Password,
                position: row[0].position,
                mobilePhoneNum: row[0].mobilePhoneNum,
                emailAddr: row[0].emailAddr
            });

        } else if (row.length == 0) {
            $.messager.alert('警告', '必须选择一条进行修改！');
        } else {
            $.messager.alert({
                title: '警告',
                msg: '只能选择一条进行修改！'
            });
        }
    }
    function user_delect() {
        var rows = $('#user_dg').datagrid('getSelections');
        if (rows.length > 0) {
            $('#user_d').window('open');
            $(".delete_userList")
                .click(
                    function () {
                        var parm = [];
                        for (var i = 0; i < rows.length; i++) {
                            var code = rows[i].userId;
                            parm.push(code);
                        }
                        console.log(parm);
                        $
                            .ajax({
                                url: "${pageContext.request.contextPath}/usermgr/deleteUser.do?userId="
                                + parm,
                                //type:"post",
                                dataType: "text",
                                //data:{"userId":parm},
                                success: function (data) {
                                    $("#user_d")
                                        .dialog('close');
                                    $('#user_dg').datagrid(
                                        'reload');
                                    $('#user_dg').datagrid(
                                        'clearChecked');
                                },
                            });

                    });
        } else {
            alert("请选择要删除的选项");
        }
    }
</script>
</body>
</html>

