<%--
  Created by IntelliJ IDEA.
  User: Zhangx
  Date: 2017/4/1
  Time: 15:48
  To change this template use File | Settings | File Templates.
--%>
<%@
        page language="java"
             contentType="text/html; charset=UTF-8"
             pageEncoding="UTF-8"
             isELIgnored="false"
%>
<%@taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<html>
<head>

    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache" />
    <meta http-equiv="expires" content="0" />
    <script type="text/javascript" src="../js/jquery.min.js"></script>
    <script type="text/javascript" src="../js/jquery.easyui.min.js"></script>
    <link rel="stylesheet" type="text/css" href="../themes/icon.css" />
    <link rel="stylesheet" type="text/css" href="../themes/deflault/easyui.css" />
    <script type="text/javascript" src="../js/easyui-lang-zh_CN.js"></script>
    <title>角色权限管理</title>
</head>
<body>


<div id="role_to_auth">

    <table style="width: 500px" align="center">
        <tr>
            <td valign="top" style="padding-right:50px;">
                <div id="role_panel  " class="easyui-panel" title="角色" align="center"
                     style="width: 150px; height: 400px;">
                    <ul id="role_dl" style="width: 100%" border="0"></ul>
                </div>

            </td>
            <td>
                <div id="left_panel" class="easyui-panel" title="未选中权限" align="center"
                     style="width: 150px; height: 400px">
                    <ul id="auth_left_dl" style="width: 100%" border="0"></ul>

                </div>
            </td>
            <td align="center" valign="middle" style="width:100%;">

                <a id="auth_to_right" class="easyui-linkbutton" data-options="iconCls:'icon-toright',disabled:true"></a>
                <br /> <br>
                <a id="auth_to_toright" class="easyui-linkbutton" data-options="iconCls:'icon-totoright'"></a>
                <br /><br>
                <a id="auth_to_left" class="easyui-linkbutton"
                   data-options="iconCls:'icon-toleft',disabled:true"></a><br><br>
                <a id="auth_to_toleft" class="easyui-linkbutton"
                   data-options="iconCls:'icon-totoleft'"></a><br /><br>
            </td>
            <td>
                <div id="right_panel" class="easyui-panel" title="已选中权限" align="center"
                     style="width: 150px; height: 400px;">
                    <ul id="auth_right_dl" style="width:100%;" border="0"></ul>
                </div>
            </td>
        </tr>
        <tr align="center">
            <td align="center">

            </td>
            <td align="center" style="padding-top:20px;">
                <a class="easyui-linkbutton roletoauthSave"
                   data-options="iconCls:'icon-ok'" href="javascript:void(0)"
                   style="width: 80px; display: inline-block">确定</a>
            </td>
            <td align="center">

            </td>
        </tr>
    </table>
</div>

<script>
    $('#role_dl').datalist({
        url: '${pageContext.request.contextPath}/rolemgr/listdata.do',
        valueField: 'roleId',

        textField: 'roleName',
        onSelect: function (index, record) {
            $('#auth_right_dl').datalist({
                url: '${pageContext.request.contextPath}/roletoauthmgr/authlistbyroleid.do?id=' + record.roleId,
                valueField: 'authorityId',
                textField: 'authorityName',
                singleSelect: false,
                onSelect: function () {
                    $('#auth_to_left').linkbutton('enable');
                    $('#auth_to_right').linkbutton('disable');
                    $('#auth_left_dl').datalist('unselectAll');
                },
                onUnselectAll: function () {
                    $('#auth_to_left').linkbutton('disable');
                }
            });
            $('#auth_left_dl').datalist({
                url: '${pageContext.request.contextPath}/roletoauthmgr/unselectedauthlist.do?id=' + record.roleId,
                valueField: 'authorityId',
                textField: 'authorityName',
                singleSelect: false,
                onSelect: function () {
                    $('#auth_to_right').linkbutton('enable');
                    $('#auth_to_left').linkbutton('disable');
                    $('#auth_right_dl').datalist('unselectAll');
                },
                onUnselectAll: function () {
                    $('#auth_to_right').linkbutton('disable');
                }
            });
        }
    });

    $('#auth_to_right').click(function () {
        var leftRows = $('#auth_left_dl').datalist('getSelections');
        for (var i = 0; i < leftRows.length; i++) {
            var obj = leftRows[i];
            var index = $('#auth_left_dl').datalist('getRowIndex', obj);
            $('#auth_left_dl').datalist('deleteRow', index);
            $('#auth_right_dl').datalist('appendRow', {
                authorityId: obj.authorityId,
                authorityName: obj.authorityName
            });
        }
    });
    $('#auth_to_toright').click(function () {
        $('#auth_left_dl').datalist('loadData', {
            total: 0,
            rows: []
        });
        $('#auth_right_dl').datalist({
            url: '${pageContext.request.contextPath}/authmgr/getall.do'
        });
    });
    $('#auth_to_left').click(function () {
        var rightRows = $('#auth_right_dl').datalist('getSelections');
        for (var i = 0; i < rightRows.length; i++) {
            var obj = rightRows[i];
            var index = $('#auth_right_dl').datalist('getRowIndex', obj);
            $('#auth_right_dl').datalist('deleteRow', index);
            $('#auth_left_dl').datalist('appendRow', {
                authorityId: obj.authorityId,
                authorityName: obj.authorityName
            });
        }
    });
    $('#auth_to_toleft').click(function () {
        $('#auth_right_dl').datalist('loadData', {
            total: 0,
            rows: []
        });
        $('#auth_left_dl').datalist({
            url: '${pageContext.request.contextPath}/authmgr/getall.do'
        });
    });


    $('.roletoauthSave').click(
        function () {
            var postData = $('#auth_right_dl').datalist('getData');
            var data = JSON.stringify(postData);
            var roleId = $('#role_dl').datalist('getSelected').roleId;
            $.post('${pageContext.request.contextPath}/roletoauthmgr/save.do', {
                "authlist": data,
                "roleId": roleId
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

