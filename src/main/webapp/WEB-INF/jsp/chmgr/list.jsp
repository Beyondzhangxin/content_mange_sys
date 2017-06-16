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
</style>
<table id="channel_dg" style="width:90%;height:100%"  data-options="
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
		toolbar:'#channel_tt',
    	striped:true,
    	fitColumns:false,
		url:'${pageContext.request.contextPath}/chmgr/listdataBypage.do'">
	<thead>
        <tr>
            <th data-options="field:'ck',checkbox:true"></th>
            <th data-options="field:'siteName'" style="width:10%" align="center">站点名称</th>
            <th data-options="field:'channelName',editor:'text'" style="width:10%" align="center">频道名称</th>
            <th data-options="field:'createUserName'" style="width:10%" align="center">频道创建者</th>
            <th data-options="field:'createTime'" style="width:10%" align="center">频道时间</th>
            <th data-options="field:'apprUserName'" style="width:10%" align="center">频道审核人</th>
            <th data-options="field:'noPassReason'" style="width:20%" align="center">审核未通过原因</th>
            <th data-options="field:'channelFileName'" style="width:10%" align="center">文件夹名称</th>
            <th data-options="field:'channelFilePath'" style="width:20%" align="center">文件夹保存路径</th>
        </tr>
	</thead>
</table>
<!-- 操作区 -->
<div id="channel_tt">
	<table style="width:100%;padding:2px 5px">
		<tr>
			<td>	
				<a class="easyui-linkbutton active" data-options="iconCls:'icon-notThrougn'" href="javascript:channel_appr_event1()" >审核未通过</a>
				<a class="easyui-linkbutton " data-options="iconCls:'icon-through'" href="javascript:channel_appr_event2()" >审核通过</a>		
				<a class="easyui-linkbutton " data-options="iconCls:'icon-Unaudited'" href="javascript:channel_appr_event3()" >未审核</a>
			</td>
		</tr>
		<tr>
			<td>	
				<input id="channel_appr_site__id"  data-options="prompt:'请选择站点'" >
        		<!-- 创建普通的文本框 -->
				<input style="width:120px;" id="channel_appr_channel_name" class="easyui-textbox" data-options="prompt:'请输入频道名称'">
				<!-- 创建搜索按钮 -->			
				<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="javascript:searchForChannellist()">搜索</a>
			</td>
			<td width="50%" align="right">
				<a href="#" onclick="javascript:channel_add()" class="easyui-linkbutton" iconCls="icon-add"  plain="true"></a>
				<a href="#" onclick="javascript:channel_edit()" class="easyui-linkbutton" iconCls="icon-edit"  plain="true"></a>
				<a href="#" onclick="javascript:channel_delect()" class="easyui-linkbutton" iconCls="icon-cancel"  plain="true"></a>
			</td>
		</tr>
	</table>
</div>
<!-- 新增频道窗口 -->
<div id="channel_w" class="easyui-window" title="添加频道" data-options="modal:true,closed:true,iconCls:'icon-save'" style="width:400px;height:300px;text-align:center;">
        <form method="post" id="channel_add_message_id">
        <div style="margin-top:60px;">
            <span>站点名称：</span>
            <input id="channel_site" class="easyui-combobox" name="siteId" data-options="prompt:'请选择站点',required:true">
        </div>
        <div style="margin-top:60px;">
            <span>频道名称：</span>
            <input class="easyui-textbox"  name="channelName"  id="channel_name_id" data-options="required:true,validType:'illegalChar'">
        </div>
        <div class="buttonBox">
            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="add_channel_message()" style="align:right;margin: 50px 0px 0 50px;padding: 1px 17px;">提交</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="close_add_window()" style="align:right;margin:50px 0 0 15px;padding:0 15px;">取消</a>
        </div>
        </form>
</div>
<!-- 编辑频道窗口 -->
<div id="channel_e" class="easyui-window" title="编辑频道" data-options="modal:true,closed:true,iconCls:'icon-save'" style="width:400px;height:300px;text-align:center;">
    	<div style="margin-top:60px;">
            <span>站点名称：</span>
                <input id="channel_site_edit" class="combobox_siteName" name="editChannelSite" data-options="prompt:'请选择站点',required:true">
        </div>
        <div style="margin-top:60px;">
            <span>频道名称：</span>
            <input id="edit_channel_name_id" class="easyui-textbox channnelName_edit" name="channelName" data-options="required:true,validType:'illegalChar'" >
        </div>
        <div>
             <a href="javascript:void(0)" class="easyui-linkbutton" onclick="edit_channel_message()" style="align:right;margin: 50px 0px 0 50px;padding: 1px 17px;">提交</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" style="align:right;margin:50px 0 0 15px;padding:0 15px;" onclick="$('#channel_e').window('close')">取消</a>
        </div>
</div>

<script>
$('#channel_dg').datagrid({
	onLoadSuccess:function(){
		$("#channel_tt").next(".datagrid-view").addClass("channel_dgs");
	}
})
$("#channel_appr_site__id").combobox({
	    	url:'${pageContext.request.contextPath}/sitemgr/findAllSite.do',
	    	textField:'siteName',
	        valueField:'siteId'
	    });
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

<!-- 为搜索按钮绑定单击事件 -->
<script type="text/javascript">
	function searchForChannellist() {
		var siteId= $("#channel_appr_site__id").combobox("getValue");
		var channelName = $("#channel_appr_channel_name").val();
		if(siteId==''&&channelName==''){
			$.messager.alert('提示','请输入搜索信息!','info');
		}else{
			$.ajax({
				url:'${pageContext.request.contextPath}/chmgr/addQueryCriteria.do',
				type:"post",
				data:{siteId:siteId,channelName:channelName},
				error:function(){
					$.messager.alert('提示','搜索操作失败!','error');
				},
				success:function(data){
					if(data.total==0){
						$.messager.alert('提示','没有查询到数据!','error');
					}else{
						$("#channel_dg").datagrid("loadData",data);
					}
					
				}
			});
		}
	
	}
</script>

<!-- 为三个审核状态绑定单击事件 -->
 <script type="text/javascript">
	$("#channel_tt a").click(function(){
		$(this).addClass("active").siblings().removeClass("active");
	})
 	function channel_appr_event1(){
 		var channelAppr = 2;
 		checkdataByChannelAppr(channelAppr);
 	}
 	function channel_appr_event2(){
 		var channelAppr = 1;
 		checkdataByChannelAppr(channelAppr);
 	}
 	function channel_appr_event3(){
 		var channelAppr = 0;
 		checkdataByChannelAppr(channelAppr);
 	}
 	function checkdataByChannelAppr(channelAppr){
 		$("#channel_appr_site__id").combobox("clear");
 		$("#channel_appr_channel_name").textbox('clear');
		$.ajax({
			url:'${pageContext.request.contextPath}/chmgr/checkdataByChannelAppr.do',
			type:"post",
			data:{channelAppr:channelAppr},
			error:function(){
				$.messager.alert('提示','通过频道审核状态获取数据失败!','error');
			},
			success:function(data){
				$("#channel_dg").datagrid("loadData",data);
			}
		});
 	}
 </script>

<!-- 删除频道 -->
<script type="text/javascript">
function channel_delect(){
	var rows = $('#channel_dg').datagrid('getChecked');
	if(rows.length == 0){
		$.messager.alert('提示','请选择需要删除的频道!','info');
	}else{
		var channelId =rows[0].channelId;
		$.messager.confirm('提示', '确定删除该频道?', function(r){
			if (r){
				$.ajax({
		            url:"${pageContext.request.contextPath}/chmgr/deleteByChannelId.do",
		            type:"post",
		            data:{channelId:channelId},
		            dataType:"json",
		            success:function(data){
		            	$.messager.alert('提示','删除成功!','info');
		            	$("#channel_dg").datagrid('reload');
		                $('#channel_d').window('close');
		            },
		            error:function(){
		            	$.messager.alert('提示','删除失败!','info');
		            }
		       });
			}else{
				$("#channel_dg").datagrid('reload');
			}
		});
	}
}
</script>

<!-- 编辑频道 -->
<script type="text/javascript">
//打开编辑窗口
function channel_edit(){
	var rows = $('#channel_dg').datagrid('getChecked');
	if(rows.length == 0){
		$.messager.alert('提示','请选择需要编辑的频道!','info');
	}else{
		$('#channel_e').window('open');
        $("#channel_site_edit").combobox({
        	url:'${pageContext.request.contextPath}/sitemgr/findAllSite.do',
        	textField:'siteName',
            valueField:'siteId'
        });
        var channelName=rows[0].channelName;
        $(".channnelName_edit").textbox('setValue',channelName);
	}
}
//提交编辑后的频道信息
function edit_channel_message(){
	var channelName= $("#edit_channel_name_id").val();
	var siteId= $("#channel_site_edit").combobox("getValue");
	var rows = $('#channel_dg').datagrid('getChecked');
	var channelId =rows[0].channelId;
	$.ajax({
		url:'${pageContext.request.contextPath}/chmgr/updateChannel.do',
		type:"post",
		data:{channelName:channelName,siteId:siteId,channelId:channelId},
		dataType:"json",
		success:function(data){
			if(data.success){
				$.messager.alert('提示',data.message,'info');
				$('#channel_dg').datagrid('reload');
				$('#channel_e').window('close');
			}else{
				$.messager.alert('提示',data.message,'info');
			}
		},
		error:function(){
			$.messager.alert('提示',"编辑操作失败！",'info');
		}
	}); 
}
</script>

<!-- 增加频道 -->
<script type="text/javascript">
	//弹出增加窗口
	function channel_add(){
		$('#channel_w').window('open');
		$("#channel_site").combobox({
	    	url:'${pageContext.request.contextPath}/sitemgr/findAllSite.do',
	    	textField:'siteName',
	        valueField:'siteId'
	    });
		$("#channel_name_id").textbox('clear');
	}
	//为取消按钮绑定单击事件
	function close_add_window(){  
		$('#channel_w').window('close');
		$('#channel_dg').datagrid('reload');
	}
	function add_channel_message(){
		$("#channel_add_message_id").form('submit',{
	        url:'${pageContext.request.contextPath}/chmgr/addChannel.do',
	        onSubmit: function(){
	        	return $("#channel_add_message_id").form('validate');
	        },
	        success: function(result){
				var data = eval("("+result+")");
				if(data.success){
					$.messager.alert('提示',data.message,'info');
					$('#add_channel_message_id').form('clear');
					$('#channel_w').window('close');
	                $('#channel_dg').datagrid('reload'); 
				}else{
					$.messager.alert('提示',data.message,'info');
				}
	        }
	    });
	}
</script>
</body>





