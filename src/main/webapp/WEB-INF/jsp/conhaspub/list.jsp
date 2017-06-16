<%@page import="java.util.Enumeration"%>
<%@ 
	page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"
%>
<%@taglib uri="http://java.sun.com/jstl/core"  prefix="c"%>
<html>
<body>
	<table id="content_has" class="easyui-datagrid" style="width:100%;height:100%"
		data-options="
		width:'90%',
		height:'90%',
		fitColumns:true,
		pagination:true,
		pagePosition:'bottom',
		pageNumber:1,
		pageList:[10,15,20,25,30],
		pageSize:10,
		url:'${pageContext.request.contextPath}/conhaspub/getConPubForPage.do',
		method:'post',
		columns:[[
			{field:'ck',checkbox:true},
			{field:'contentTitle',width:110,align:'center',title:'内容标题'},
			{field:'siteName',width:110,align:'center',title:'站点名称'},
			{field:'channelName',width:130,align:'center',title:'频道名称'},
			{field:'columnName',width:140,align:'center',title:'栏目名称'},
			{field:'contentPubUserName',width:140,align:'center',title:'发布者'},
			{field:'contentPubTime',width:140,align:'center',title:'发布时间'}
		]],
		rownumbers:true,
		striped:true,
		singleSelect:true,
		checkOnSelect:true,
		toolbar:'#conhaspub_tool',
		">
	</table>
<div id="conhaspub_tool">
<table style="width:100%;padding:2px 5px">
	<tr>
		<td width="80%" align="left">
			<input id="conhaspub_siteId" class="easyui-combobox" data-options="
					prompt:'请选择站点',
					valueField:'siteId',
					textField:'siteName',
        			url:'${pageContext.request.contextPath}/sitemgr/findAllSite.do',
        			onSelect:function(rec){
        				$('#conhaspub_channelId').combobox('clear');
        				$('#conhaspub_columnName').combobox('clear');
        				var siteId = rec.siteId;
		        		var url = '${pageContext.request.contextPath}/chmgr/getChannelByApprPass.do?siteId='+siteId;
		        		$('#conhaspub_channelId').combobox('reload', url);
        			}">
        	<input id="conhaspub_channelId" class="easyui-combobox" 
        		data-options="prompt:'请选择频道',valueField:'channelId',textField:'channelName',
        		onSelect:function(data){
        			var channelId=data.channelId;
        			var column='${pageContext.request.contextPath}/itemmgr/getColumnByApprPass.do?channelId='+channelId;
        			$('#conhaspub_columnName').combobox('reload', column);
        		}" >
        	<input id="conhaspub_columnName" class="easyui-combobox" data-options="prompt:'请选择栏目',valueField:'columnId',textField:'columnName'
        	">
			<input style="width:120px;" id="conhaspub_contentTitle" class="easyui-textbox" data-options="prompt:'请输入内容标题'">		
			<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="javascript:searchForConhaspub()" style="width:70px">搜索</a>
		</td>
		<td width="20%" align="right">
			<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" onclick="javascript:content_delect_conhaspub()" style="width:70px,margin-top:6px">删除</a>
		</td>
	</tr>
</table> 
</div>
<script>
	function searchForConhaspub(){
		var conhaspub_contentTitle =$("#conhaspub_contentTitle").val();
		var conhaspub_channelId = $('#conhaspub_channelId').combobox('getValue');
		var conhaspub_columnName=$('#conhaspub_columnName').combobox('getValue');
		var conhaspub_siteName=$('#conhaspub_siteId').combobox('getValue');
		if(conhaspub_contentTitle==''&&conhaspub_channelId==''&&conhaspub_columnName==''&&conhaspub_siteName==''){
			$.messager.alert('提示','请输入搜索信息!','info');
		}else{
			$.ajax({
				url:'${pageContext.request.contextPath}/conhaspub/addSearchCriteria.do',
				type:'post',
				data:{"siteId":conhaspub_siteName,"channelId":conhaspub_channelId,"columnId":conhaspub_columnName,"contentTitle":conhaspub_contentTitle},
				error:function(){
					$.messager.alert('内容搜索','搜索内容信息失败!','error');
				},
				success:function(data){
					if(data.total==0){
						$.messager.alert('内容搜索','对不起，搜索的内容不存在!','warning');
					}else{
						$("#content_has").datagrid("loadData",data);
					}
				}
			});
		}
	};
	function content_delect_conhaspub(){
		var rows = $('#content_has').datagrid('getChecked');
		if(rows.length == 0 ){
			$.messager.alert('提示','请选择要删除的选项!','info');
		}else{
			$.messager.confirm('删除内容', '确定删除此内容?', function(t){
				if (t){
					 var contentPubId = rows[0].contentPubId;
			            $.ajax({
			                url:"${pageContext.request.contextPath}/conhaspub/deteleContentPublish.do",
			                type:"post",
			                dataType:"json",
			                data:{"contentPubId":contentPubId},
			                success:function(data){
			                	$.messager.alert('提示',data.message,'info');
			                	$('#content_has').datagrid('reload'); 
			                }
			           })
				}else{
					$('#content_has').datagrid('reload'); 
				}
			});
		}
	}
</script>
</body>
</html>






































