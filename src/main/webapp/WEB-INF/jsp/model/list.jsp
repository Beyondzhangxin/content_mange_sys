<%@page import="java.util.Enumeration"%>
<%@ 
	page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<body>
<table id="model_dg" style="width:90%;height:100%" class="easyui-datagrid"  data-options="
    	url:'${pageContext.request.contextPath}/modmgr/listdataByPage.do',
		rownumbers:true,
		pageSize:10,
		singleSelect:true,
		pagination:true,
		pageNumber:1,
		pageList:[10,15,20,25,30],
		pageSize:10,
		rownumbers:true,
		fit:true,
		nowrap:true,
		autoRowHeight:false,
		toolbar:'#model_toolbar',
    	striped:true,
    	fitColumns:false">
    <thead>
    <tr>
        <th data-options="field:'ck',checkbox:true"></th>
        <th data-options="field:'templateClassName'" width="20%" align="left">模板类型</th>
        <th data-options="field:'templateName'" width="29%" align="left">模板名称</th>
        <th data-options="field:'templateFileUrl'" width="50%" align="left">模板访问路径</th>
    </tr>
    </thead>
</table>
<!-- 操作区 -->
<div id="model_toolbar">
<table style="width:100%;padding:2px 5px">
<tr>
<td width="50%" align="left">
<input class="easyui-searchbox" id="check_template_name_id" data-options="prompt:'请输入模板名字：',searcher:doSearch_mod" style="width:300px"></input>
</td>
<td width="50%" align="right">
<a href="#"></a>
		<sec:authorize url="/modmgr/list.do" >
		<a href="#" onclick="javascript:model_add()" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true"></a>
		</sec:authorize> 
<!-- 		<sec:authorize access="hasAuthority('AUTH_ADMIN')">
		<a href="#" onclick="javascript:model_add()" class="easyui-linkbutton" iconCls="icon-add" plain="true"></a>
		</sec:authorize>  -->
<a href="#" onclick="javascript:model_delect()" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true"></a>
</td>
</tr>
</table>
</div>
<!-- 添加窗口 -->
<div id="model_w" class="easyui-window" title="添加模板" data-options="modal:true,closed:true,iconCls:'icon-save'" style="width:400px;height:300px;padding:20px 40px;">
<form method="post" id="model_add_message" enctype="multipart/form-data">
<div>
<span style="width:88px;display:inline-block;text-align:right;margin-bottom:10px;">模板名称：</span>
<input class="easyui-textbox" name="templateName" id="add_templateName_id" data-options="required:true,validType:'illegalChar'">
</div>
<div>
<span style="width:88px;display:inline-block;text-align:right;margin-bottom:10px;">模板类型：</span>
<input class="easyui-combobox" name="templateClass" id="add_templateClass_id" data-options="prompt:'请选择模板'">
</div>
<div>
<span style="width:88px;display:inline-block;text-align:right;margin-bottom:10px;">上传文件：</span>
<input id="model_files" class="easyui-filebox " name="modfile" data-options="prompt:''" >
</div>
<div>
<a href="javascript:void(0)" class="easyui-linkbutton" onclick="add_model_message()" style="align:right;margin: 30px 0px 0 95px;padding: 1px 17px;">提交</a>
<a href="javascript:void(0)" class="easyui-linkbutton" style="align:right;margin:30px 0 0 35px;padding:0 15px;" onclick="$('#model_w').window('close')">取消</a>
</div>
</form>
</div>
<script>
	function model_delect() {
		var rows = $('#model_dg').datagrid('getChecked');
		if(rows.length == 0 ){
			$.messager.alert('提示','请选择需要删除的模板!','info');
		}else{
			$.messager.confirm('提示', '确定删除该模板?', function(r){
				if (r){
					var templateId = rows[0].templateId;
					$.ajax({
		                url:"${pageContext.request.contextPath}/modmgr/deleteTemplate.do",
		                type:"post",
		                dataType:"json",
		                data:{templateId:templateId},	               
		                success:function(data){
		                	$.messager.alert('提示',data.message,'info');
		                    $("#model_dg").datagrid('reload');
		                    //$('#model_dg').datagrid('unselectAll');
		                },
		                error:function(){
		                	$.messager.alert('提示','删除失败!','info');
		                }
		           })
				}
			});
		}
	}
function model_add(){
	$('#model_w').window('open');
	$('#model_files').filebox({buttonText:'选择'});
	$("#add_templateName_id").textbox("clear");
	$("#model_files").filebox("clear");
	$('#add_templateClass_id').combobox({
		url:'${pageContext.request.contextPath}/modmgr/findModClass.do',
		method:'post',
		textField:'tempaleAbstract',
	    valueField:'templateClass',
	    onLoadSuccess: function (data) {
	    	 $('#set_template').combobox('select', '请选择模板类型：');
	    }
	})
}
function add_model_message(){
	$("#model_add_message").form('submit',{
        url:'${pageContext.request.contextPath}/modmgr/addTemplate.do',
        onSubmit: function(){
            return $(this).form('validate');	                
        },
        success: function(result){
        	var data = eval("("+result+")");
           	  if (data.success){
           		$('#model_w').dialog('close');
            	$.messager.alert('提示',data.message,'info');
                $('#model_dg').datagrid('reload');  
            } else {
            	$.messager.alert('提示',data.message,'info');
            } 
        }
    });
}
function doSearch_mod(templateName){
	$.ajax({
		url:'${pageContext.request.contextPath}/modmgr/addQueryCondition.do',
		type:'post',
		data:{templateName:templateName},
		dataType:"json",
		error:function(){
			$.messager.alert('提示','搜索模板操作失败!','info');
		},
		success:function(data){
			if(data.total == 0 ){
				$.messager.alert('提示','搜索结果为空!','info');
			}else{
				$("#model_dg").datagrid('reload');
			}
		}
	});
}
$.extend($.fn.validatebox.defaults.rules, {
	illegalChar: {
        validator: function(value){
            var rex=/[`~!@#\$%\^\&\*\(\)_\+<>\?:"\{\},\.\\\/;'\[\]]/im;
            if(rex.test(value)) {return false;}else{return true;}
        },
        message: '不能包含特殊字符'
    }
});
</script>
</body>
