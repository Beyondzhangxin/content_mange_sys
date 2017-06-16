<%@page import="java.util.Enumeration"%>
<%@ 
	page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"
%>
<%@taglib uri="http://java.sun.com/jstl/core"  prefix="c"%>
<html>
<body>
	<table id="cont_pub" class="easyui-datagrid" style="width:100%;height:100%"
		data-options="
		width:'90%',
		height:'90%',
		fitColumns:true,
		pagination:true,
		pagePosition:'bottom',
		pageNumber:1,
		pageList:[10,15,20,25,30],
		pageSize:10,
		url:'${pageContext.request.contextPath}/conpub/findContentForConPub.do',
		method:'post',
		columns:[[
			{field:'ck',checkbox:true},
			{field:'contentTitle',width:110,align:'center',title:'内容名称'},
			{field:'keyword',width:110,align:'center',title:'关键字'},
			{field:'contentCreateUserName',width:130,align:'center',title:'创建者'},
			{field:'contentCreateTime',width:140,align:'center',title:'创建时间'},
			{field:'contentDescribe',width:140,align:'center',title:'内容描述'}
		]],
		rownumbers:true,
		striped:true,
		singleSelect:true,
		checkOnSelect:true,
		toolbar:'#conpub_tool',
		">
	</table>
<!-- 创建数据网格（datagrid）面板的头部工具栏。-->
<div id="conpub_tool">
<table style="width:100%;padding:2px 5px">
	<tr>
		<td width="80%" align="left">
			<!-- 创建普通的文本框 -->
			<input style="width:120px;" id="contentTitle" class="easyui-textbox" data-options="prompt:'请输入内容名称'">
			<input style="width:120px;" id="contentCreateUserName" class="easyui-textbox" data-options="prompt:'请输入用户名'">
			<!-- 创建搜素按钮 -->			
			<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="javascript:searchForContPub()" style="width:70px">搜索</a>
		</td>
		<td width="50%" align="right">
			<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add'" onclick="javascript:publicConetent()" style="width:70px,margin-top:6px">发布</a>
		</td>
	</tr>
</table>
</div>
<!-- 发布内容窗口 -->
<div id="content_pub_win_id" class="easyui-window" title="内容发布" style="width:400px;height:420px;padding:20px 40px" data-options="iconCls:'icon-save',modal:true,closed:true">
 	<div>
 		<span  style="width:100px;display:inline-block;text-align:right;margin-bottom:10px;">站点名称：</span>
 		<input id="content_pub_site_id"  class="easyui-combobox" data-options="
 			prompt:'请选择站点',
 			valueField: 'siteId',
    		textField: 'siteName',
    		url: '${pageContext.request.contextPath}/sitemgr/findAllSite.do',
    		onSelect:function(rec){
    			$('#content_pub_channel_id').combobox('clear');
    			$('#content_pub_column_id').combobox('clear');
        		var siteId = rec.siteId;
		        var url = '${pageContext.request.contextPath}/chmgr/getChannelByApprPass.do?siteId='+siteId;
		        $('#content_pub_channel_id').combobox('reload', url);
        	}
 		">
 	</div>
 	<div>
 		<span  style="width:100px;display:inline-block;text-align:right;margin-bottom:10px;">频道名称：</span>
 		<input id="content_pub_channel_id"  class="easyui-combobox" data-options="
 			prompt:'请选择频道',
 			valueField: 'channelId',
    		textField: 'channelName',
    		onSelect:function(rec){
    			$('#content_pub_column_id').combobox('clear');
        		var channelId = rec.channelId;
		        var url = '${pageContext.request.contextPath}/itemmgr/getColumnByApprPass.do?channelId='+channelId;
		        $('#content_pub_column_id').combobox('reload', url);
        	}
 		">
 	</div>
 	 	<div>
 		<span  style="width:100px;display:inline-block;text-align:right;margin-bottom:10px;">栏目名称：</span>
 		<input id="content_pub_column_id"  class="easyui-combobox" data-options="
 			prompt:'请选择栏目',valueField: 'columnId',textField: 'columnName'
 		">
 	</div>
 	<div style="width:185px;margin:30px auto 0 100px">
 		<a href="#" style="width:75px;margin-left:5px" data-options="iconCls:'icon-ok'"  class="easyui-linkbutton" onclick="javascript:content_pub()">确定</a>
		<a href="#" style="width:75px;margin-left:10px"class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" onclick="$('#content_pub_win_id').window('close')">取消</a>
 	</div>
</div>

<!-- 为【搜索】按钮绑定单击事件 -->
<script type="text/javascript">
	function searchForContPub(){
		var contentTitle =$("#contentTitle").val();
		console.log(contentTitle)
		var contentCreateUserName =$("#contentCreateUserName").val();
		if(contentTitle==''&&contentCreateUserName==''){
			$.messager.alert('提示','请输入搜索信息!','info');
		}else{
			$.ajax({
				url:'${pageContext.request.contextPath}/conpub/addQueryForConPub.do',
				type:'post',
				data:{"contentTitle":contentTitle,"contentCreateUserName":contentCreateUserName},
				dataType:'json',
				success:function(data){
					var total = data.total;
					if(total >0){
						$("#cont_pub").datagrid("loadData",data);
					}else{
						$.messager.alert('搜索内容','对不起,没有搜索记录!','warning');
					}
				}
			});
		}
	 
	}
</script>
<!-- 为发布按钮绑定单击事件 -->
<script type="text/javascript">
	function publicConetent() {
		$('#content_pub_site_id').combobox('clear');
		$('#content_pub_channel_id').combobox('clear');
		$('#content_pub_column_id').combobox('clear');
		var rows = $('#cont_pub').datagrid('getChecked');
		if(rows.length == 0){
			$.messager.alert('提示','请选择需要发布的内容!','warning');
		}else{
			$('#content_pub_win_id').window('open');
		}
	}
</script>
<!-- 为【确定发布】按钮绑定单击事件 -->
<script type="text/javascript">
	function content_pub() {
		var rows = $('#cont_pub').datagrid('getChecked');
		var contentId = rows[0].contentId;
		var siteId = $('#content_pub_site_id').combobox('getValue');
		var channelId = $('#content_pub_channel_id').combobox('getValue');
		var columnId = $('#content_pub_column_id').combobox('getValue');
		$.ajax({
			url:'${pageContext.request.contextPath}/conpub/pubContent.do',
            type:"post",
            dataType:"json",
            data:{contentId:contentId,siteId:siteId,channelId:channelId,columnId:columnId},
			success:function(data){
				if(data.success){
					$('#cont_pub').datagrid('reload');
					$.messager.alert('发布内容',data.message,'ok');
					$('#content_pub_win_id').window('close');
				}else{
					$.messager.alert('发布内容',data.message,'warning');
				}
			},
			error:function(data){
				$.messager.alert('发布内容',data.message,'warning');
			}
		});
	}
</script>
</body>
</html>






































