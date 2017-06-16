<%@page import="java.util.Enumeration"%>
<%@ 
	page language="java" 
	contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    isELIgnored="false"
%>
<%@taglib uri="http://java.sun.com/jstl/core"  prefix="c"%>
<style>
	.active{
		background:#E0ECFF;
		border-color:#95b8e7;
	}
</style>
<table id="site_dg" style="width:90%;height:100%" data-options="
		rownumbers:true,
		pageSize:10,
		singleSelect:true,
		pagination:true,
		pageNumber:1,
		pageList:[10,15,20,25,30],
		rownumbers:true,
		fit:true,
		nowrap:true,
		autoRowHeight:false,
		toolbar:'#site_tt',
    	striped:true,
    	fitColumns:false,
		url:'${pageContext.request.contextPath}/sitemgr/listdataBypage.do',method:'post'">
	<thead>
		<tr>
				<th data-options="field:'ck',checkbox:true"></th>
				<th data-options="field:'siteName',width:110,align:'center'" style="width:15%">站点名称</th>
				<th data-options="field:'siteUrl',width:110,align:'center'" style="width:20%">站点Url</th>
				<th data-options="field:'userRealname',width:80,align:'center'" style="width:15%">站点创建者</th>
				<th data-options="field:'createTime',width:110,align:'center'" style="width:10%">创建日期</th>
				<th data-options="field:'apprUsername',width:120,align:'center'" style="width:15%">站点审核人员</th>
				<th data-options="field:'apprReason',width:240,align:'center'" style="width:24%">审核未通过原因</th>
				<th data-options="field:'fileName',width:120,align:'center'" style="width:15%">文件夹名称</th>
		</tr>
	</thead>
</table>
<!-- 操作区 -->
<div id="site_tt" style="padding:2px 5px;">
<table style="width:100%">
	<tr>
		<td width="60%" align="left">	
			<a class="easyui-linkbutton active" data-options="iconCls:'icon-notThrougn'" href="javascript:site_appr_event1()"  style="width:100px">审核未通过</a>
			<a class="easyui-linkbutton " data-options="iconCls:'icon-through'" href="javascript:site_appr_event2()"  style="width:80px">审核通过</a>		
			<a class="easyui-linkbutton " data-options="iconCls:'icon-Unaudited'" href="javascript:site_appr_event3()"  style="width:80px">未审核</a>
			<input class="easyui-searchbox" id="channelName" name="fullName" type="text" style="width: 220" searcher="sitedoSearch" prompt="请输入站点信息">
		</td>
		<td width="40%" align="right">
		<a href="#" onclick="javascript:site_add()" class="easyui-linkbutton" iconCls="icon-add" plain="true"></a>
		<a href="#" onclick="javascript:site_edit()" class="easyui-linkbutton" iconCls="icon-edit" plain="true"></a>
		<a href="#" onclick="javascript:site_delect()" class="easyui-linkbutton" iconCls="icon-cancel" plain="true"></a>
		</td>
	</tr>
</table>
</div>
<!-- 操作窗口 -->
<!--添加窗口-->
<div id="site_w" class="easyui-window" title="添加站点" data-options="modal:true,closed:true,iconCls:'icon-save'" style="width:400px;height:420px;padding:20px 40px;">
	<form method="post" id="site_add_message">
		<div>
			<span style="width:88px;display:inline-block;text-align:right;margin-bottom:10px;">站点名称：</span>
			<input class="easyui-textbox" name="siteName"  data-options="required:true,validType:'illegalChar'"></input>
		</div>
		<div>
			<span style="width:88px;display:inline-block;text-align:right;margin-bottom:10px;">站点地址：</span>
			<input class="easyui-textbox" name="siteUrl"  data-options="required:true,validType:'illegalChar'"></input>
		</div>
		<div>
			<span style="width:88px;display:inline-block;text-align:right;margin-bottom:10px;">上级应用：</span>
			<input class="easyui-textbox" name="upApplication"  data-options="required:true,validType:'illegalChar'"></input>
		</div>
		<div>
			<span style="width:88px;display:inline-block;text-align:right;margin-bottom:10px;">网页编码：</span>
			<input class="easyui-textbox" name="pageCode"  data-options="required:true,validType:'illegalChar'"></input>
		</div>
		<div>
			<span style="width:88px;display:inline-block;text-align:right;margin-bottom:10px;">关键字：</span>
			<input class="easyui-textbox" name="keyWord"  data-options="required:true,validType:'illegalChar'"></input>
		</div>
		<div>
			<span style="width:88px;display:inline-block;text-align:right;margin-bottom:10px;">备案信息：</span>
			<input class="easyui-textbox" name="remarksMsg"  data-options="required:true,validType:'illegalChar'"></input>
		</div>
		<div>
			<span style="width:88px;display:inline-block;text-align:right;margin-bottom:10px;">管理员邮箱：</span>
			<input class="easyui-textbox" name="adminEmail" data-options="required:true,validType:'email'"></input>
		</div>
		<div class="buttonBox">
			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="add_site_message()" style="align:right;margin: 30px 0px 0 95px;padding: 1px 17px;">提交</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="cancel_site_add()"	 	style="align:right;margin:30px 0 0 35px;padding:0 15px;" >取消</a>
		</div>
		</form>
</div>
<!--编辑窗口-->
	<div id="site_e" class="easyui-window" title="编辑站点" data-options="modal:true,closed:true,iconCls:'icon-save'" style="width:400px;height:420px;padding:20px 40px;">
		<form method="post" id="site_edit_message">
			<div>
				<span style="width:88px;display:inline-block;text-align:right;margin-bottom:10px;">站点名称：</span>
				<input class="easyui-textbox e_siteName" data-options="required:true,validType:'illegalChar'" name="siteName">
			</div>
			<div>
				<span style="width:88px;display:inline-block;text-align:right;margin-bottom:10px;">站点地址：</span>
				<input class="easyui-textbox e_siteUrl" name="siteUrl" data-options="required:true,validType:'illegalChar'">
			</div>
			<div>
				<span style="width:88px;display:inline-block;text-align:right;margin-bottom:10px;">上级应用：</span>
				<input class="easyui-textbox e_upApplication" name="upApplication" data-options="required:true,validType:'illegalChar'" >
			</div>
			<div>
				<span style="width:88px;display:inline-block;text-align:right;margin-bottom:10px;">网页编码：</span>
				<input class="easyui-textbox e_pageCode" name="pageCode" data-options="required:true,validType:'illegalChar'" >
			</div>
			<div>
				<span style="width:88px;display:inline-block;text-align:right;margin-bottom:10px;">关键字：</span>
				<input class="easyui-textbox e_keyWord" name="keyWord" data-options="required:true,validType:'illegalChar'">
			</div>
			<div>
				<span style="width:88px;display:inline-block;text-align:right;margin-bottom:10px;">备案信息：</span>
				<input class="easyui-textbox e_remarksMsg" name="remarksMsg" data-options="required:true,validType:'illegalChar'">
			</div>
			<div>
				<span style="width:88px;display:inline-block;text-align:right;margin-bottom:10px;">管理员邮箱：</span>
				<input class="easyui-textbox e_adminEmail" name="adminEmail" data-options="validType:'email',required:true" >
			</div>
			<div class="buttonBox">
				<a href="javascript:void(0)" class="easyui-linkbutton" onclick="edit_site_message()" style="align:right;margin: 30px 0px 0 95px;padding: 1px 17px;">提交</a>
                <a href="javascript:void(0)" class="easyui-linkbutton" style="align:right;margin:30px 0 0 35px;padding:0 15px;" onclick="$('#site_e').window('close')">取消</a>
			</div>
		</form>
	</div>

<script type="text/javascript">
$('#site_dg').datagrid({
	onLoadSuccess:function(){
		$("#site_tt").next(".datagrid-view").addClass("site_dgs");
	}
})
</script>

<!-- 编辑站点 -->
<script type="text/javascript">
//为编辑框注入数据
function site_edit(){
	if($('.site_dgs .datagrid-btable input:checked').length==1){
		$('#site_e').window('open');
		var rows = $('#site_dg').datagrid('getChecked');
		var siteName=rows[0].siteName;
		$(".e_siteName").textbox('setValue',siteName);
		var siteUrl=rows[0].siteUrl;
		$(".e_siteUrl").textbox('setValue',siteUrl);
		var upApplication=rows[0].upApplication; 
		$(".e_upApplication").textbox('setValue',upApplication);
		var pageCode=rows[0].pageCode;
		$(".e_pageCode").textbox('setValue',pageCode);
		var keyWord=rows[0].keyWord;
		$(".e_keyWord").textbox('setValue',keyWord);
		var remarksMsg=rows[0].remarksMsg;
		$(".e_remarksMsg").textbox('setValue',remarksMsg);
		var adminEmail=rows[0].adminEmail;
		$(".e_adminEmail").textbox('setValue',adminEmail);
	}else{
		$.messager.alert('提示','请选择一个内容编辑!','info');
	}
}
//表单提交
function edit_site_message(){
	$("#site_edit_message").form('submit',{
        url:'${pageContext.request.contextPath}/sitemgr/editSite.do',
        onSubmit: function(param){
        	var rows = $('#site_dg').datagrid('getChecked');
        	var siteId_edit=rows[0].siteId;
        	param.siteId=siteId_edit;
        	return $(this).form('validate');	
        },
        success: function(result){
        	var data = eval("("+result+")");
			if(data.success){
				$.messager.alert('提示',data.message,'info');
				$('#site_edit_message').form('clear');//清除表单中的内容
				$('#site_e').dialog('close');  
	            $('#site_dg').datagrid('reload');  
			}else{
				$.messager.alert('提示',data.message,'info');
			}
        }
    }); 
}
</script>

<!-- 添加站点 -->
<script type="text/javascript">
	//为添加按钮绑定单击事件
	function site_add(){
		$('#site_w').window('open');
	}
	//为表单提交按钮绑定单机事件
	function add_site_message(){
		$("#site_add_message").form('submit',{
	        url:'${pageContext.request.contextPath}/sitemgr/addSite.do',
	        onSubmit: function(){
	        	return $("#site_add_message").form('validate');
	        },
	        success: function(result){
				var data = eval("("+result+")");
				if(data.success){
					$.messager.alert('提示',data.message,'info');
					$('#site_add_message').form('clear');//清除表单中的内容
					$('#site_w').window('close');
	                $('#site_dg').datagrid('reload'); 
				}else{
					$.messager.alert('提示',data.message,'info');
				}
	        }
	    });
	}
	//为表单的取消按钮绑定单击事件
	function cancel_site_add() {
		$('#site_add_message').form('clear');//清除表单中的内容
		$('#site_w').window('close');
	}
</script>

<!-- 删除站点 -->
<script type="text/javascript">
function site_delect(){
	var rows = $('#site_dg').datagrid('getChecked');
	if(rows.length == 0 ){
		$.messager.alert('提示','请选择要删除的选项!','info');
	}else{
		$.messager.confirm('删除站点', '确定删除此站点?', function(r){
			if (r){
				 var siteId = rows[0].siteId;
		            $.ajax({
		                url:"${pageContext.request.contextPath}/sitemgr/deleteSite.do",
		                type:"post",
		                dataType:"json",
		                data:{siteId:siteId},
		                success:function(data){
		                	$.messager.alert('提示',data.message,'info');
		                	$('#site_dg').datagrid('reload'); 
		                }
		           })
			}else{
				$('#site_dg').datagrid('reload'); 
			}
		});
	}
}
</script>

<!-- 为搜索绑定单击事件 -->
<script type="text/javascript"> 
$.extend($.fn.validatebox.defaults.rules, {
	illegalChar: {
        validator: function(value){
            var rex=/[`~!@#\$%\^\&\*\(\)_\+<>\?:"\{\},\.\\\/;'\[\]]/im;
            if(rex.test(value)) {return false;}else{return true;}
        },
        message: '不能包含特殊字符'
    }
});
function sitedoSearch(value){
	var condition = value;
	if(condition==''){
		$.messager.alert('提示','请输入搜索信息!','info');
	}else{
		$.ajax({
			url:'${pageContext.request.contextPath}/sitemgr/addQueryCondition.do',
			type:'post',
			data:{condition:condition},
			success:function(data){
				if(data.total==0){
					$.messager.alert('站点审核','对不起，搜索的站点不存在!','warning');
				}
				$("#site_dg").datagrid("loadData",data);
	        }
		});
	}	
}
</script>

<!-- 为审核状态的三个按钮绑定单击事件 -->
<script type="text/javascript">
	$("#site_tt a").click(function(){
		$(this).addClass("active").siblings().removeClass("active");
	})
	function site_appr_event1() {//审核未通过
		var siteAppr =2;
		getSiteDateBySiteAppr(siteAppr);
	}
	function site_appr_event2() {//审核通过
		var siteAppr=1;
		getSiteDateBySiteAppr(siteAppr);
	}
	function site_appr_event3() {//未审核
		var siteAppr=0;
		getSiteDateBySiteAppr(siteAppr);
	}
	//通过审核状态获取数据
	function getSiteDateBySiteAppr(siteAppr){
		$("#channelName").searchbox("clear");
		$.ajax({
			url:'${pageContext.request.contextPath}/sitemgr/addSiteAppr.do',
			type:"post",
			data:{siteAppr:siteAppr},
			error:function(){
				$.messager.alert('站点管理','通过站点审核状态获取数据失败!','error');
			},
			success:function(data){
				$("#site_dg").datagrid("loadData",data);
			}
		});
	}
</script>
