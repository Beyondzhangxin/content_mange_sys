<%@page import="java.util.Enumeration"%>
<%@ 
	page language="java" 
	contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    isELIgnored="false"
%>
<%@taglib uri="http://java.sun.com/jstl/core"  prefix="c"%>
<head>
	<meta http-equiv="content-type" content="text/html;charset=utf-8">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/themes/cms.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/demo/demo.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/all.css">
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/easyui-lang-zh_CN.js"></script>
</head>

<body>
<table id="dg" title="栏目列表" style="width:60%" class="easyui-treegrid"  data-options="url:'treeNode.json',singleSelect:false,
    checkOnSelect:true,
    tools:'#tt',
     idField: 'id',
    treeField: 'name'">
    <thead>
        <tr>
            <th data-options="field:'ck',checkbox:true"></th>
            <th data-options="field:'name'"  style="width:30%" align="left">栏目名称</th>
        </tr>
    </thead>
</table>
<div id="tt">
    <a href="javascript:void(0)" class="icon-add" onclick="javascript:add()"></a>
    <a href="javascript:void(0)" class="icon-edit" onclick="javascript:edit()"></a>
    <a href="javascript:void(0)" class="icon-cancel" onclick="javascript:delect()"></a>
</div>
<!--添加页-->
<div id="w" class="easyui-window" title="添加栏目" data-options="modal:true,closed:true,iconCls:'icon-save'" style="width:400px;height:500px;">
        <form action="${pageContext.request.contextPath}/columnMgr/addColumn.do" method="post">
            <div>
                <span class="siteTitle">栏目名称：</span>
                <input class="easyui-textbox channeName" data-options="prompt:''" name="columnName">
            </div>
            <div>
                <span class="siteTitle">内容模型：</span>
                <select name="template" class="easyui-combobox">
                    <option value="普通文章">普通文章</option>
                    <option value="分局文章">分局文章</option>
                    <option value="分局文章"></option>
                </select>
            </div>
            <div>
                <span class="siteTitle">频道ID：</span>
                <input class="easyui-textbox" name="channelId">
                <tt>由低—>高</tt>
            </div>
            <div>
                <span class="siteTitle">浏览权限：</span>
                <select name="readPower" class="easyui-combobox">
                    <option value="公开">公开</option>
                    <option value="内部">内部</option>
                    <option value="内部"></option>
                </select>
                <tt>仅限制栏目里的文档浏览权限</tt>
            </div>
            <div>
                <span class="siteTitle">栏目频道：</span>
                <input name="channelId" class="easyui-combobox channelId" data-options="prompt:'请选择频道'">
            </div>
            <div>
                <span class="siteTitle">上级栏目：</span>
                <input type="text" name="parentId" class="easyui-combobox parentId" data-options="prompt:'请选择所属栏目'">
            </div>
            <div class="buttonBox">
                <a href="#" class="easyui-linkbutton"><input type="submit" value="确定"></a>
                <a href="#" class="easyui-linkbutton" onclick="$('#w').window('close')"><input type="button" value="取消"></a>
            </div>
        </form>
    </div>
<!--编辑页-->
<div id="e" class="easyui-window" title="修改栏目" data-options="modal:true,closed:true,iconCls:'icon-save'" style="width:400px;height:500px;">
    <form action="${pageContext.request.contextPath}/columnMgr/editColumn.do" method="post">
        <div>
            <span class="siteTitle">栏目名称：</span>
            <input class="easyui-textbox channeName" data-options="prompt:''" name="columnName" id="text">
        </div>
        <div>
            <span class="siteTitle">内容模型：</span>
            <select name="template" class="easyui-combobox">
                <option value="普通文章">普通文章</option>
                <option value="分局文章">分局文章</option>
                <option value="分局文章"></option>
            </select>
        </div>
        <div>
            <span class="siteTitle">排列顺序：</span>
            <input class="easyui-textbox">
            <tt>由低—>高</tt>
        </div>
        <div>
            <span class="siteTitle">浏览权限：</span>
            <select name="readPower" class="easyui-combobox">
                <option value="公开">公开</option>
                <option value="内部">内部</option>
                <option value="内部"></option>
            </select>
            <tt>仅限制栏目里的文档浏览权限</tt>
        </div>
        <div>
            <span class="siteTitle">栏目频道：</span>
            <input name="channelId" class="easyui-combobox channelId" data-options="prompt:'请选择频道'">
        </div>
        <div>
            <span class="siteTitle">上级栏目：</span>
            <input type="text" name="parentId" class="easyui-combobox parentId" data-options="prompt:'请选择所属栏目'">
        </div>
        <div class="buttonBox">
            <a href="#" class="easyui-linkbutton"><input type="submit" value="确定"></a>
            <a href="#" class="easyui-linkbutton" onclick="$('#e').window('close')"><input type="button" value="取消"></a>
        </div>
    </form>
</div>
<!--删除页-->
<div id="d" class="easyui-window" title="删除栏目" data-options="modal:true,closed:true,iconCls:'icon-save'" style="width:400px;height:200px;">
    <div data-options="region:'center'" style="padding:10px;margin-bottom:10px">确定删除该栏目？</div>
        <a class="easyui-linkbutton delect_columnList" data-options="iconCls:'icon-ok'" href="javascript:void(0)"  style="width:80px">确定</a>
        <a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0)" onclick="$('#d').window('close')" style="width:80px">取消</a>
</div>
<script>
    $(".channelId").combobox({
        url:"${pageContext.request.contextPath}/channelMgr/listdata.do",
        method: 'get',
        textField:'channelName',
        valueField:'channelId',
        onSelect:function(data){
            $(".channelId").combobox('getValue');
            var channelId = data.channelId;
            $.ajax({
                url:'${pageContext.request.contextPath}/columnMgr/findColumnByChannelId.do',
                type:"post",
                dataType:"json",
                data:{"channelId":channelId},
                success:function(data){
                    $(".parentId").combobox({
                        url:'${pageContext.request.contextPath}/columnMgr/findColumnByChannelId.do?channelId='+channelId,
                        textField:'columnName',
                        valueField:'channelId'
                    })
                },
                error:function(){
                    console.log("ee");
                }
            })
        }
    })
function add(){
    $("#w").window('open');
}
function edit(){
    if($('.datagrid-btable input:checked').length==1){
        $("#e").window('open');
        var rows = $('#dg').datagrid('getChecked');
        var columnName=rows[0].name;
        $("#_easyui_textbox_input3").val(columnName);

    }else{
        alert("请选择一个栏目编辑")
    }
}
    function delect(){
        if($('.datagrid-btable input:checked').length>0){
            $("#d").window('open');
            $(".delect_columnList").click(function () {
                var parm = [];
                var rows = $('#dg').treegrid('getChecked');
                for (var i = 0; i < rows.length; i++) {
                    var code = rows[i].id;
                    parm.push(code);
                }
                console.log(parm);
                $.ajax({
                    url:"${pageContext.request.contextPath}/itemmgr /deleteColumn.do",
                    type:"post",
                    dataType:"text",
                    data:{"columnId":parm},
                    success:function(data){
                        alert("删除成功");
                        $(".datagrid-btable input:checked").parent().parent().parent().remove();
                    },
                    error:function(){
                        alert("删除失败");
                    }
                })
            });
        }else{
            alert("请选择要删除的选项");
        }
    }
</script>
</body>