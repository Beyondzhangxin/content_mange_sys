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
    <link rel="stylesheet" type="text/css" href="../themes/default/easyui.css" />
    <script type="text/javascript" src="../js/easyui-lang-zh_CN.js"></script>
    <title>资源权限管理</title>
</head>
<body>


<div id="res_to_auth">

    <table style="width: 500px" align="center">
        <tr>
            <td valign="top" style="padding-right:50px;">
                <div id="res_panel  " class="easyui-panel" title="权限"  align="center"
                     style="width: 150px; height: 400px;">
                    <ul id="res_dl" style="width: 100%" border="0"></ul>

                </div>


            </td>
            <td>
                <div id="left_panel" class="easyui-panel" title="未选中资源" align="center"
                     style="width: 150px; height: 400px;">
                    <ul id="res_left_dl" style="width: 100%" border="0"></ul>

                </div>
            </td>
            <td align="center" valign="middle" style="width:100%;">

                <a id="res_to_right" class="easyui-linkbutton" data-options="iconCls:'icon-toright',disabled:true"></a>
                <br /> <br>
                <a id="res_to_toright" class="easyui-linkbutton" data-options="iconCls:'icon-totoright'"></a>
                <br /><br>
                <a id="res_to_left" class="easyui-linkbutton"
                   data-options="iconCls:'icon-toleft',disabled:true"></a><br><br>
                <a id="res_to_toleft" class="easyui-linkbutton"
                   data-options="iconCls:'icon-totoleft'"></a><br /><br>
            </td>
            <td>
                <div id="right_panel" class="easyui-panel" title="已选中资源" align="center"
                     style="width: 150px; height: 400px;">
                    <ul id="res_right_dl" style="width:100%;" border="0"></ul>
                </div>
            </td>
        </tr>
        <tr align="center">
            <td align="center">

            </td>
            <td align="center" style="padding-top:20px;">
                <a class="easyui-linkbutton restoauthSave"
                   data-options="iconCls:'icon-ok'" href="javascript:void(0)"
                   style="width: 80px; display: inline-block">确定</a>
            </td>
            <td align="center">

            </td>
        </tr>
    </table>
</div>

<script>
    $('#res_dl').datalist({

        url: '${pageContext.request.contextPath}/authmgr/getall.do',

        valueField: 'authorityId',
        textField: 'authorityName',
        onSelect: function (index, record) {
            $('#res_right_dl').datalist({
                url: '${pageContext.request.contextPath}/restoauthmgr/getreslistbyauthid.do?id=' + record.authorityId,
                valueField: 'resourceId',
                textField: 'resourceName',
                singleSelect: false,
                onSelect: function () {
                    $('#res_to_left').linkbutton('enable');
                    $('#res_to_right').linkbutton('disable');
                    $('#res_left_dl').datalist('unselectAll');
                },
                onUnselectAll: function () {
                    $('#res_to_left').linkbutton('disable');
                }
            });
            $('#res_left_dl').datalist({
                url: '${pageContext.request.contextPath}/restoauthmgr/getunselectedreslistbyauthid.do?id=' + record.authorityId,
                valueField: 'resourceId',
                textField: 'resourceName',
                singleSelect: false,
                onSelect: function () {
                    $('#res_to_right').linkbutton('enable');
                    $('#res_to_left').linkbutton('disable');
                    $('#res_right_dl').datalist('unselectAll');
                },
                onUnselectAll: function () {
                    $('#res_to_right').linkbutton('disable');
                }
            });
        }

    });


    $('#res_to_right').click(function () {
        var leftRows = $('#res_left_dl').datalist('getSelections');
        for (var i = 0; i<leftRows.length; i++) {
            var obj = leftRows[i];
            var index = $('#res_left_dl').datalist('getRowIndex', obj);
            $('#res_left_dl').datalist('deleteRow', index);
            $('#res_right_dl').datalist('appendRow', {
                resourceId: obj.resourceId,
                resourceName: obj.resourceName
            });
        }
    });
    $('#res_to_toright').click(function () {
        $('#res_left_dl').datalist('loadData', {
            total: 0,
            rows: []
        });
        $('#res_right_dl').datalist({
            url: '${pageContext.request.contextPath}/resmgr/listall.do'
        });
    });
    $('#res_to_left').click(function () {
        var rightRows = $('#res_right_dl').datalist('getSelections');
        for (var i = 0; i < rightRows.length; i++) {
            var obj = rightRows[i];
            var index = $('#res_right_dl').datalist('getRowIndex', obj);
            $('#res_right_dl').datalist('deleteRow', index);
            $('#res_left_dl').datalist('appendRow', {
                resourceId: obj.resourceId,
                resourceName: obj.resourceName
            });
        }
    });
    $('#res_to_toleft').click(function () {
        $('#res_right_dl').datalist('loadData', {
            total: 0,
            rows: []
        });
        $('#res_left_dl').datalist({
            url: '${pageContext.request.contextPath}/resmgr/listall.do'
        });
    });

    $('.restoauthSave').click(
        function restoauth_save() {
            var postData = $('#res_right_dl').datalist('getData');
            var data = JSON.stringify(postData);
            var authId = $('#res_dl').datalist('getSelected').authorityId;
            $.post('${pageContext.request.contextPath}/restoauthmgr/save.do', {
                "reslist": data,
                "authId": authId
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

