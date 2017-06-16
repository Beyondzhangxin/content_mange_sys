<%@page import="java.util.Enumeration"%>
<%@ 
	page language="java" 
	contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    isELIgnored="false"
%>
<%@taglib uri="http://java.sun.com/jstl/core"  prefix="c"%>
<body>
<style>
	.active{
		background:#E0ECFF;
		border-color:#95b8e7;
	}
	#column_dg{
		display:none;
	}
</style>
<table id="column_dg_failure" style="width:90%;height:100%" class="name"></table>
<!-- 审核通过 -->
<table id="column_dg" style="width:90%;height:100%"></table>
<!-- 操作区 -->
<div id="column_tt">
<table style="width:100%;padding:2px 5px">
	<tr>
		<td width="80%" align="left">
			<a class="easyui-linkbutton active" data-options="iconCls:'icon-notThrougn'" href="javascript:column_appr_event1()" >审核未通过</a>
			<a class="easyui-linkbutton " data-options="iconCls:'icon-through'" href="javascript:column_appr_event2()" >审核通过</a>		
			<a class="easyui-linkbutton " data-options="iconCls:'icon-Unaudited'" href="javascript:column_appr_event3()" >未审核</a>
			<input id="search_column_edit" class="easyui-combobox" data-options="
					prompt:'请选择站点',
					valueField:'siteId',
					textField:'siteName',
        			url:'${pageContext.request.contextPath}/sitemgr/findAllSite.do',
        			onSelect:function(rec){
        				$('#search_column_channel').combobox('clear');
        				var siteId = rec.siteId;
		        		var url = '${pageContext.request.contextPath}/chmgr/getChannelByApprPass.do?siteId='+siteId;
		        		$('#search_column_channel').combobox('reload', url);
        			}">
        	<input id="search_column_channel" class="easyui-combobox" data-options="prompt:'请选择频道',valueField:'channelId',textField:'channelName'">
			<input style="width:120px;" id="column_name_id" class="easyui-textbox" data-options="prompt:'请输入名称'">
			<!-- 创建搜索按钮 -->			
			<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="javascript:searchForColumnTree()">搜索</a>
		</td>
		<td width="20%" align="right">
		<a href="#" onclick="javascript:open_add_column()" class="easyui-linkbutton" iconCls="icon-add" plain="true"></a>
		<a href="#" onclick="javascript:open_edit_column()" class="easyui-linkbutton" iconCls="icon-edit" plain="true"></a>
		<a href="#" onclick="javascript:delect_column()" class="easyui-linkbutton" iconCls="icon-cancel" plain="true" ></a>
		</td>
	</tr>
</table>
</div>

<!--添加窗口-->
<div id="column_w" class="easyui-window" title="添加栏目" data-options="modal:true,closed:true,iconCls:'icon-save'" style="width:400px;height:420px;padding:20px 40px">
	<form method="post"  id="column_add_message">
		<div>
			<span  style="width:88px;display:inline-block;text-align:right;margin-bottom:10px;">栏目名称：</span>
			<input id="add_column_name" name="columnName"  class="easyui-textbox" data-options="required:true,validType:'illegalChar'" >
		</div>
		<div>
			<span  style="width:88px;display:inline-block;text-align:right;margin-bottom:10px;">站点名称：</span> 
			<input id="add_column_site_id" name="siteId"  class="easyui-combobox siteId">
		</div>
		<div>
			<span  style="width:88px;display:inline-block;text-align:right;margin-bottom:10px;">频道名称：</span> 
			<input id="add_column_channel_id" name="channelId" class="easyui-combobox channelId">
		</div>
		<div>
			<span  style="width:88px;display:inline-block;text-align:right;margin-bottom:10px;">上级栏目：</span>
			<input id="add_column_parent_id" name="parentId" class="easyui-combobox parentId">
		</div>
		<div>
			<span  style="width:88px;display:inline-block;text-align:right;margin-bottom:10px;">栏目模板：</span>
			<input id="add_column_templateId_id" name="templateId" class="easyui-combobox template">
	   </div>   
	   <div>
		   <span  style="width:88px;display:inline-block;text-align:right;margin-bottom:10px;">浏览权限：</span>
			   <select name="readPower" class="easyui-combobox" style="width:100px">
			   <option value="1">公开</option>
			   <option value="0">内部</option>
		   </select>
	   </div>
	   <div>
		   <a href="javascript:void(0)" class="easyui-linkbutton" onclick="add_column_message()" style="align:right;margin: 30px 0px 0 95px; padding: 1px 17px;">提交</a>
		   <a href="javascript:void(0)" class="easyui-linkbutton" style="align:right;margin:30px 0 0 35px;padding:0 15px;" onclick="$('#column_w').window('close')">取消</a>
	   </div>
	</form>
 </div>
 
 <!--编辑窗口-->
<div id="column_e" class="easyui-window" title="修改栏目" data-options="modal:true,closed:true,iconCls:'icon-save'" style="width:400px;height:420px;padding:20px 40px">
    <form method="post" id="column_edit_message">
       <div>
			<span  style="width:88px;display:inline-block;text-align:right;margin-bottom:10px;">栏目名称：</span>
			<input id="edit_column_name" name="columnName"  class="easyui-textbox" data-options="required:true,validType:'illegalChar'" >
		</div>
		<div>
			<span  style="width:88px;display:inline-block;text-align:right;margin-bottom:10px;">站点名称：</span> 
			<input id="edit_column_site_id" name="siteId"  class="easyui-combobox siteId">
		</div>
		<div>
			<span  style="width:88px;display:inline-block;text-align:right;margin-bottom:10px;">频道名称：</span> 
			<input id="edit_column_channel_id" name="channelId" class="easyui-combobox channelId">
		</div>
		<div>
			<span  style="width:88px;display:inline-block;text-align:right;margin-bottom:10px;">上级栏目：</span>
			<input id="edit_column_parent_id" name="parentId" class="easyui-combobox parentId">
		</div>
		<div>
			<span  style="width:88px;display:inline-block;text-align:right;margin-bottom:10px;">栏目模板：</span>
			<input id="edit_column_templateId_id" name="templateId" class="easyui-combobox template">
	   </div>   
	   <div>
		   <span  style="width:88px;display:inline-block;text-align:right;margin-bottom:10px;">浏览权限：</span>
			   <select name="readPower" class="easyui-combobox" style="width:100px">
			   <option value="1">公开</option>
			   <option value="0">内部</option>
		   </select>
	   </div>
        <div class="buttonBox">
             <a href="javascript:void(0)" class="easyui-linkbutton" onclick="edit_column_message()" style="align:right;margin: 30px 0px 0 95px;padding: 1px 17px;">提交</a>
             <a href="javascript:void(0)" class="easyui-linkbutton" style="align:right;margin:30px 0 0 35px;padding:0 15px;" onclick="$('#column_e').window('close')">取消</a>
        </div>
    </form>
</div>

<!-- 为三个审核状态绑定单击事件 -->
 <script type="text/javascript">
	$("#column_tt a").click(function(){
		$(this).addClass("active").siblings().removeClass("active");
	})
	/* 审核未通过列表*/
	$("#column_dg_failure").datagrid({
		url:'${pageContext.request.contextPath}/itemmgr/findColumnByColumnAppr.do?columnAppr=2',
		singleSelect:true,
        checkOnSelect:true,
        fit:true,
        singleSelect:true,
        striped:true,
        pagination:true,
		pagePosition:'bottom',
		pageNumber:1,
		pageList:[10,15,20,25,30],
		pageSize:10,
		toolbar:'#column_tt',
		   columns:[[
			   { field:'ck',checkbox:true },
               {field:'columnName',title:'栏目名称',width:150},
               {field:'noPassReason',title:'审核未通过原因',width:150},
               {field:'siteName',title:'站点名称',width:150},
               {field:'channelName',title:'频道名称',width:150},
               {field:'parentName',title:'上级栏目',width:140},
               {field:'userName',title:'创建者',width:120},
               {field:'columnCreateTime',title:'创建时间',width:120},
               {field:'columnFileName',title:'文件夹名称',width:120}
           ]]
	})
 	function column_appr_event1(){
 		var columnAppr = 2;
 		checkdataByColumnAppr(columnAppr);
 		$("#column_dg_failure").addClass("name");
 		$("#column_dg").removeClass("name");
 		$("#column_dg").parent().parent().parent().hide(); 
        $("#column_dg_failure").show();
 		$("#column_dg_failure").datagrid({
 			url:'${pageContext.request.contextPath}/itemmgr/findColumnByColumnAppr.do?columnAppr=2',
 			singleSelect:true,
 	        checkOnSelect:true,
 	        fit:true,
 	       pagination:true,
			pagePosition:'bottom',
			pageNumber:1,
			pageList:[10,15,20,25,30],
			pageSize:10,
 	        singleSelect:true,
 	        striped:true,
 			toolbar:'#column_tt',
 			   columns:[[
 				   { field:'ck',checkbox:true },
 	               {field:'columnName',title:'栏目名称',width:150},
 	               {field:'noPassReason',title:'审核未通过原因',width:150},
 	               {field:'siteName',title:'站点名称',width:150},
 	               {field:'channelName',title:'频道名称',width:150},
 	               {field:'parentName',title:'上级栏目',width:140},
 	               {field:'userName',title:'创建者',width:120},
 	               {field:'columnCreateTime',title:'创建时间',width:120},
 	               {field:'columnFileName',title:'文件夹名称',width:120}
 	           ]]
 		})
 	}
 	function column_appr_event2(){
 		var columnAppr = 1;
 		checkdataByColumnAppr(columnAppr);
		$("#column_dg").addClass("name");
 		$("#column_dg_failure").removeClass("name");
 		$("#column_dg_failure").parent().parent().parent().hide(); 
         $("#column_dg").show();
     	$('#column_dg').treegrid({
                 url:'${pageContext.request.contextPath}/itemmgr/findColumnByColumnAppr.do?columnAppr=1',
                 toolbar:'#column_tt',
                 treeField:'columnName',
                 idField:'columnId',
                 singleSelect:true,
                 checkOnSelect:true,
                 fit:true,
                 singleSelect:true,
                 striped:true,
                 animate:true,
                 fitColumns:false,
                 columns:[[
                	 { field:'ck',checkbox:true },
                     {field:'columnName',title:'栏目名称',width:320},
                     {field:'siteName',title:'站点名称',width:160},
                     {field:'channelName',title:'频道名称',width:160},
                     {field:'userName',title:'创建者',width:160},
                     {field:'columnCreateTime',title:'创建时间',width:150},
                     {field:'columnFileName',title:'文件夹名称',width:150}
                 ]]
         })  
 	}
 	function column_appr_event3(){
 		var columnAppr = 0;
 		checkdataByColumnAppr(columnAppr);
 		$("#column_dg_failure").addClass("name");
 		$("#column_dg").removeClass("name");
 		$("#column_dg").parent().parent().parent().hide(); 
        $("#column_dg_failure").show();
 		$("#column_dg_failure").datagrid({
 			url:'${pageContext.request.contextPath}/itemmgr/findColumnByColumnAppr.do?columnAppr=0',
 			singleSelect:true,
 	        checkOnSelect:true,
 	        fit:true,
 	       pagination:true,
			pagePosition:'bottom',
			pageNumber:1,
			pageList:[10,15,20,25,30],
			pageSize:10,
 	        singleSelect:true,
 	        striped:true,
 			toolbar:'#column_tt',
 			   columns:[[
 				   { field:'ck',checkbox:true },
 	               {field:'columnName',title:'栏目名称',width:150},
 	               {field:'noPassReason',title:'审核未通过原因',width:150},
 	               {field:'siteName',title:'站点名称',width:150},
 	               {field:'channelName',title:'频道名称',width:150},
 	               {field:'parentName',title:'上级栏目',width:140},
 	               {field:'userName',title:'创建者',width:120},
 	               {field:'columnCreateTime',title:'创建时间',width:120},
 	               {field:'columnFileName',title:'文件夹名称',width:120}
 	           ]]
 		})
 	}
 	function checkdataByColumnAppr(columnAppr){
 		$("#search_column_edit").combobox("clear");
 		$('#search_column_channel').combobox('clear');
		$.ajax({
			url:'${pageContext.request.contextPath}/itemmgr/findColumnByColumnAppr.do',
			type:"post",
			data:{columnAppr:columnAppr},
			error:function(){
				$.messager.alert('提示','通过栏目审核状态获取数据失败!','error');
			},
			success:function(data){
				$('#column_dg').treegrid("loadData",data);
			}
		});
 	}
 </script>

<!-- 搜索栏目 -->
<script type="text/javascript">
	function searchForColumnTree() {
		var siteId = $('#search_column_edit').combobox('getValue');
		var channelId = $('#search_column_channel').combobox('getValue');
		var columnName=$("#column_name_id").val();
		if(siteId==''&&channelId==''&&columnName==''){
			$.messager.alert('提示','请输入搜索信息!','info');
		}else{
			$.ajax({
				url:"${pageContext.request.contextPath}/itemmgr/addColumnSearchCriteria.do",
				type:"post",
				data:{siteId:siteId,channelId:channelId,columnName:columnName},
				dataType:"json",
				success:function(data){
					console.log(data);
					if(data.rows.length == 0){
						$.messager.alert('提示','查询结果为空!','info');
					}else{
						$('#column_dg').treegrid('reload');  
					}
				},
				error:function(){
					$.messager.alert('提示','搜索操作失败!','info');
					$('#column_dg').treegrid('reload');
				}
			});
		}
	
	}
</script>

<!-- 删除栏目 -->
<script type="text/javascript">
	function delect_column(){
		var table_name=$(".name");
		var rows = $(table_name).datagrid('getChecked');
		if(rows.length == 0){
			$.messager.alert('提示','请选择需要删除的栏目!','info');
		}else{
			$.messager.confirm('提示', '你确定要删除该栏目?', function(r){
				if (r){
					var columnId = rows[0].columnId;
					$.ajax({
						url:'${pageContext.request.contextPath}/itemmgr/deleteColumnByColumnId.do',
						type:'post',
						data:{columnId:columnId},
						dataType:"json",
						success:function(data){
							$.messager.alert('提示',data.message,'info');
							
							$(table_name).datagrid('reload');
							$(table_name).treegrid('reload');
						},
						error:function(){
							$.messager.alert('提示','删除操作失败！','info');
							$(table_name).datagrid('reload');
							$(table_name).treegrid('reload');
						}
					});
				}
			});
		}
	}
</script>

<!-- 编辑栏目 -->
<script type="text/javascript">
	//弹出栏目编辑窗口
	function open_edit_column() {
		var table_name=$(".name");
		var rows = $(table_name).datagrid('getChecked');
		if(rows.length == 0){
			$.messager.alert('提示','请选择需要编辑的栏目!','info');
		}else{
			//判断该栏目是否是审核通过的
			if(rows[0].columnAppr ==1){
				$.messager.alert('提示','审核通过后的栏目，不能被编辑!','info');
			}else{
				$("#column_e").window('open');
				$('#edit_column_name').textbox('clear');
				$("#edit_column_name").textbox('setValue',rows[0].columnName);
				 //加载站点下拉菜单
				 $('#edit_column_site_id').combobox({
					    url:'${pageContext.request.contextPath}/sitemgr/findAllSite.do',
					    valueField:'siteId',
					    textField:'siteName',
					    prompt:'请选择站点',
					    onSelect: function(rec){
					    	$('#edit_column_channel_id').combobox('clear');
					    	var siteId = rec.siteId;
					        var url = '${pageContext.request.contextPath}/chmgr/getChannelByApprPass.do?siteId='+siteId;
					        $('#edit_column_channel_id').combobox('reload', url);
					    }
				 });
				 //加载频道下拉菜单
				 $('#edit_column_channel_id').combobox({
					    valueField:'channelId',
					    textField:'channelName',
					    prompt:'请选择频道',
					    onSelect: function(rec){
					    	$('#edit_column_parent_id').combobox('clear');
					    	var channelId = rec.channelId;
					        var url = '${pageContext.request.contextPath}/itemmgr/getColumnByApprPass.do?channelId='+channelId;
					        $('#edit_column_parent_id').combobox('reload', url);
					    }
				 });
				 //加载上级栏目下拉菜单
				 $('#edit_column_parent_id').combobox({
					    valueField:'columnId',
					    textField:'columnName',
					    prompt:'请选择上级栏目',
				 });
				 //加载栏目模板下拉菜单
				 $("#edit_column_templateId_id").combobox({
					url:'${pageContext.request.contextPath}/modmgr/findModByModclass.do?templateClass=2',
					method:'post',
					textField:'templateName',
				    valueField:'templateId',
				    prompt:'请选择栏目模板',
				})
			}
		}
	}
//为编辑栏目的提交按钮绑定单击事件
function edit_column_message(){
	var table_name=$(".name");
	var rows = $(table_name).datagrid('getChecked');
	$("#column_edit_message").form('submit',{
        url:'${pageContext.request.contextPath}/itemmgr/editColumn.do',
        onSubmit: function(param){
        	param.columnId=rows[0].columnId;
        	return $(this).form('validate');
        },
        success: function(result){
        	var data = eval("("+result+")");
            if (data.success){
            	$.messager.alert('提示',data.message,'info');
                $('#column_e').dialog('close');  
                $(table_name).treegrid('reload');
                $(table_name).treegrid('clearSelections');
                $(table_name).datagrid('reload');
                $(table_name).datagrid('clearSelections');
            } else {
             	$.messager.alert('提示',data.message,'info');
            }
        }
    });
}
</script>
 
<!-- 添加栏目 -->
<script type="text/javascript">
 //弹出栏目添加窗口
 function open_add_column(){
	 $("#column_w").window('open');
	 $('#add_column_name').textbox('clear');
	 //加载站点下拉菜单
	 $('#add_column_site_id').combobox({
		    url:'${pageContext.request.contextPath}/sitemgr/findAllSite.do',
		    valueField:'siteId',
		    textField:'siteName',
		    prompt:'请选择站点',
		    onSelect: function(rec){
		    	$('#add_column_channel_id').combobox('clear');
		    	var siteId = rec.siteId;
		        var url = '${pageContext.request.contextPath}/chmgr/getChannelByApprPass.do?siteId='+siteId;
		        $('#add_column_channel_id').combobox('reload', url);
		    }
	 });
	 //加载频道下拉菜单
	 $('#add_column_channel_id').combobox({
		    valueField:'channelId',
		    textField:'channelName',
		    prompt:'请选择频道',
		    onSelect: function(rec){
		    	$('#add_column_parent_id').combobox('clear');
		    	var channelId = rec.channelId;
		        var url = '${pageContext.request.contextPath}/itemmgr/getColumnByApprPass.do?channelId='+channelId;
		        $('#add_column_parent_id').combobox('reload', url);
		    }
	 });
	 //加载上级栏目下拉菜单
	 $('#add_column_parent_id').combobox({
		    valueField:'columnId',
		    textField:'columnName',
		    prompt:'请选择上级栏目',
	 });
	 //加载栏目模板下拉菜单
	 $("#add_column_templateId_id").combobox({
		url:'${pageContext.request.contextPath}/modmgr/findModByModclass.do?templateClass=2',
		method:'post',
		textField:'templateName',
	    valueField:'templateId',
	    prompt:'请选择栏目模板',
	})
}
 //为提交按钮绑定单击事件
 function add_column_message(){
	 $("#column_add_message").form('submit',{
		 url:'${pageContext.request.contextPath}/itemmgr/addColumn.do',
		 onSubmit: function(){
			 return $(this).form('validate');	                
	     },
	     success: function(result){
	    	 var data = eval("("+result+")");
	            if (data.success){
	             	$.messager.alert('提示',data.message,'info');
	             	$('#column_w').window('close');
	                $('#column_dg').treegrid('reload');  
	            } else {
	            	$.messager.alert('提示',data.message,'info'); 
	            }
	        }
	 });
 }
 </script>
 
 <script>
 $.extend($.fn.validatebox.defaults.rules, {
		illegalChar: {
	        validator: function(value){
	            var rex=/[`~!@#\$%\^\&\*\(\)_\+<>\?:"\{\},\.\\\/;'\[\]]/im;
	            if(rex.test(value)) {return false;}else{return true;}
	        },
	        message: '不能包含特殊字符'
	    }
	});
$('#column_dg').treegrid({
	onLoadSuccess:function(){
		$("#column_tt").next(".datagrid-view").addClass("column_dgs");
	}
})
</script>
</body>
