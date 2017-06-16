<%@page import="java.util.Enumeration"%>
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jstl/core"  prefix="c"%>
<html>
<body>
<table id="cont_pubplan" class="easyui-datagrid" style="width:100%;height:100%"
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
		toolbar:'#content_pubplan_tool',
		">
</table>
<!-- 创建数据网格（datagrid）面板的头部工具栏。-->
<div id="content_pubplan_tool">
<table style="width:100%;padding:2px 5px">
	<tr>
		<td width="80%" align="left">
			<!-- 创建普通的文本框 -->
			<input style="width:120px;" id="pub_plan_content_title" class="easyui-textbox" data-options="prompt:'请输入内容名称'">
			<input style="width:120px;" id="pub_plan_user_name" class="easyui-textbox" data-options="prompt:'请输入用户名'">
			<!-- 创建搜素按钮 -->			
			<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="javascript:searchForContPubplan()" style="width:70px">搜索</a>
		</td>
		<td width="50%" align="right">
			<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add'" onclick="javascript:pubplanForConpub()" style="width:70px,margin-top:6px">定时发布</a>
		</td>
	</tr>
</table>
</div>

<!-- 创建时间选择窗口 -->
<div id="pubplan_win" class="easyui-window" title="内容定时发布" style="width:400px;height:420px;padding:20px 40px;" data-options="iconCls:'icon-save',modal:true,closed:true">
<div>
	<span  style="width:100px;display:inline-block;text-align:right;margin-bottom:10px;">站点名称：</span>
	<input id="content_pubplan_site_id"  class="easyui-combobox" data-options="
	prompt:'请选择站点',
	valueField: 'siteId',
	textField: 'siteName',
	url: '${pageContext.request.contextPath}/sitemgr/findAllSite.do',
	onSelect:function(rec){
		$('#content_pubplan_channel_id').combobox('clear');
		$('#content_pubplan_column_id').combobox('clear');
		var siteId = rec.siteId;
		var url = '${pageContext.request.contextPath}/chmgr/getChannelByApprPass.do?siteId='+siteId;
		$('#content_pubplan_channel_id').combobox('reload', url);
		}">
</div>
 <div>
	<span style="width:100px;display:inline-block;text-align:right;margin-bottom:10px;">频道名称：</span>
	<input id="content_pubplan_channel_id"  class="easyui-combobox" data-options="
		prompt:'请选择频道',
		valueField: 'channelId',
		textField: 'channelName',
		onSelect:function(rec){
		$('#content_pubplan_column_id').combobox('clear');
			var channelId = rec.channelId;
			var url = '${pageContext.request.contextPath}/itemmgr/getColumnByApprPass.do?channelId='+channelId;
			$('#content_pubplan_column_id').combobox('reload', url);
			}">
</div>
<div>
 	<span style="width:100px;display:inline-block;text-align:right;margin-bottom:10px;">栏目名称：</span>
	<input id="content_pubplan_column_id"  class="easyui-combobox" data-options="prompt:'请选择栏目',valueField: 'columnId',textField: 'columnName'">
</div>
<!-- 选择需要定时发布的时间 -->
<div>
	<span style="width:100px;display:inline-block;text-align:right;margin-bottom:10px;">定时发布的日期：</span>
	<input id="content_pubplan_date" type="text" class="easyui-datebox" required="required">
</div>
<!-- 时间微调器 -->
<div>
	<span style="width:100px;display:inline-block;text-align:right;margin-bottom:10px;">定时发布的时间：</span>
	<input id="content_pubplan_time" class="easyui-timespinner" style="width:170px;"required="required" data-options="min:'08:30',showSeconds:true">
</div>
<div style="width:185px;margin:60px auto 0 100px">
	<a  class="easyui-linkbutton" style="width:75px;margin-left:5px" data-options="iconCls:'icon-ok'" onclick="javascript:sureForConpubplan()">确定</a>
	<a  class="easyui-linkbutton" style="width:75px;margin-left:10px" data-options="iconCls:'icon-cancel'" onclick="$('#pubplan_win').window('close')">取消</a>
 </div>
</div>
<!-- 为搜索按钮绑定单击事件 -->
<script type="text/javascript">
	function searchForContPubplan(){
		var contentTitle =$("#pub_plan_content_title").val();
		var contentCreateUserName = $("#pub_plan_user_name").val();
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
						$("#cont_pubplan").datagrid("loadData",data);
					}else{
						$.messager.alert('提示','对不起,没有搜索记录!','warning');
					}
				}
			});
		}
	 
	}
</script>
<!-- 为【定时发布】按钮绑定单击事件 -->
<script type="text/javascript">
	function pubplanForConpub(){
		var rows = $('#cont_pubplan').datagrid('getChecked');
		if(rows.length == 0){
			$.messager.alert('提示','请选择需要定时发布的内容!','warning');
		}else{
			$('#pubplan_win').window('open');
		}
	}
</script>
<!-- 为确定定时发布绑定单击事件 -->
<script type="text/javascript">
	function sureForConpubplan() {
		var rows = $('#cont_pubplan').datagrid('getChecked');
		var contentId = rows[0].contentId;
		var siteId = $('#content_pubplan_site_id').combobox('getValue');
		if(siteId == ""){
			$.messager.alert('提示','请选择站点!','warning');
		}
		var channelId = $('#content_pubplan_channel_id').combobox('getValue');
		if(channelId==""){
			$.messager.alert('提示','请选择频道!','warning');
		}
		var columnId = $('#content_pubplan_column_id').combobox('getValue');
		if(columnId==""){
			$.messager.alert('提示','请选择栏目!','warning');
		}
		var planDate = $('#content_pubplan_date').datebox('getValue');
		if(planDate ==""){
			$.messager.alert('提示','请选择定时发布的日期!','warning');
		}
		var planTime = $('#content_pubplan_time').timespinner('getValue');
		if(planTime ==""){
			$.messager.alert('提示','请选择定时发布的时间!','warning');
		}
		$.ajax({
			url:"${pageContext.request.contextPath}/conpub/savePubplanTime.do",
			type:"post",
			data:{contentId:contentId,siteId:siteId,channelId:channelId,columnId:columnId,planDate:planDate,planTime:planTime},
			dataType:'json',
			success:function(data){
				if(data.success){
					$.messager.alert('定时发布',data.message,'warning');
					$('#pubplan_win').window('close');
					$("#cont_pubplan").datagrid("reload");
				}else{
					$.messager.alert('定时发布',data.message,'warning');
				}
			}
		});
	}
</script>
</body>
</html>
