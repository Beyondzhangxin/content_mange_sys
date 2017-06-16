<%@page import="java.util.Enumeration"%>
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@taglib uri="http://java.sun.com/jstl/core"  prefix="c"%>
<html>
<body>
	<table id="columnAppr_table_id"></table>
	<div id="columnappr_toolbar">
		<table style="width:100%;padding:2px 5px">
			<tr>
				<td width="80%" align="left">
					<input id="columnAppr_siteName" class="easyui-combobox" data-options="
					prompt:'请选择站点',
					valueField:'siteId',
					textField:'siteName',
        			url:'${pageContext.request.contextPath}/sitemgr/findAllSite.do',
        			onSelect:function(rec){
        				$('#columnAppr_channelId').combobox('clear');
        				var siteId = rec.siteId;
		        		var url = '${pageContext.request.contextPath}/chmgr/getChannelByApprPass.do?siteId='+siteId;
		        		$('#columnAppr_channelId').combobox('reload', url);
        			}">
        	<input id="columnAppr_channelId" class="easyui-combobox" data-options="prompt:'请选择频道',valueField:'channelId',textField:'channelName'">
					<input width='120px' id="columnAppr_columnName" class="easyui-textbox" data-options="prompt:'请输入栏目名称'">		
					<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="javascript:searchForColumnAppr()" style="width:70px">搜索</a>
				</td>
				<td width="20%" align="right">
					<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add'" onclick="javascript:apprColumn()" style="width:70px,margin-top:6px">审核</a>
				</td>
			</tr>
		</table>
	</div>
	<div  id="columnappr" class="easyui-window" title="栏目审核" style="width:400px;height:420px;padding:20px 40px;" data-options="modal:true,closed:true,iconCls:'icon-save'">
		<div style="width:200px;margin:40px auto">
			<label for="columnappr_pass">
            	<input type="radio" value="1"  id="columnappr_pass" name="review" checked="checked" onclick="columnappr_examine()">审核通过
	        </label>
	        <label for="columnappr_fail">
	            <input type="radio"  value="2"  id="columnappr_fail" name="review" onclick="columnappr_examine()">审核未通过
	        </label>
		</div>
		<div style="display:none" class="columnappr_textarea">
			<textarea id="columnappr_review_fail_content" class="easyui-textbox" name="contentDescribe" data-options="prompt:'请输入审核未通过原因',multiline:true" style="width:300px;height:100px"></textarea>
		</div>
		<div style="width:185px;margin:20px auto 0 auto">
            <a  class="easyui-linkbutton" style="width:70px" data-options="iconCls:'icon-ok'" onclick="javascript:columnAppr()">确定</a>
            <a  class="easyui-linkbutton" style="width:70px;margin-left:5px" data-options="iconCls:'icon-cancel'" onclick="$('#columnappr').window('close')">取消</a>
        </div>
	</div>
	<script type="text/javascript">
	function columnappr_examine() {
	   	var review_text = $('input:radio[value=2]:checked').val();
	    if(review_text == 2) {$('.columnappr_textarea').show();}
	    else {$('.columnappr_textarea').hide();}
	}
	function columnAppr(){
		var rows = $("#columnAppr_table_id").datagrid('getChecked');
		var columnId=rows[0].columnId;
		var radio =($(":radio:checked").val());
		var textarea_text = ($("#columnappr_review_fail_content").val());
		$.ajax({
			url:'${pageContext.request.contextPath}/columnappr/columnAppr.do',
			type:'post',
			data:{
				"columnId":columnId,
				"columnAppr":radio,
				"noPassReason":textarea_text
			},
			error:function(){
				$.messager.alert('栏目审核','保存栏目审核结果失败!','error');
				$('#columnappr').window('close');
				$("#columnAppr_table_id").datagrid("reload");
			},
			success:function(data){
				if(data.success){
					$.messager.alert('栏目审核',data.message,'info');
					$('#columnappr').window('close');
					$("#columnAppr_table_id").datagrid("reload");
				}
			}
		})
	}
		$("#columnAppr_table_id").datagrid({
			width:'100%',
			height:"100%",
			fitColumns:true,
			pagination:true,
			pagePosition:'bottom',
			pageNumber:1,
			pageList:[10,15,20,25,30],
			pageSize:10,
			url:'${pageContext.request.contextPath}/columnappr/pageForColumnAppr.do',
			method:'post',
			columns:[[
				{field:'ck',checkbox:true},
				{field:'columnName',width:120,align:'center',title:'栏目名称'},
				{field:'siteName',width:120,align:'center',title:'站点名称'},
				{field:'channelName',width:120,align:'center',title:'频道名称'},
				{field:'userName',width:80,align:'center',title:'创建者'},	
				{field:'userName',width:80,align:'center',title:'创建时间'},	
				{field:'userName',width:80,align:'center',title:'上级栏目'},	
			]],
			rownumbers:true,
			striped:true,
			singleSelect:true,
			toolbar:'#columnappr_toolbar',
		});
	</script>
	<!-- 搜索单击函数 -->
	<script type="text/javascript">
		function searchForColumnAppr() {
			var siteId = $('#columnAppr_siteName').combobox('getValue');
			var channelId=$('#columnAppr_channelId').combobox('getValue');
			var columnName=$("#columnAppr_columnName").val();
			if(siteId==''&&channelId==''&&columnName==''){
				$.messager.alert('提示','请输入搜索信息!','info');
			}else{
				$.ajax({
					url:'${pageContext.request.contextPath}/columnappr/addSearchCriteriaForColumnAppr.do',
					type:'post',
					data:{siteId:siteId,channelId:channelId,columnName:columnName},
					error:function(){
						$.messager.alert('栏目审核','搜索栏目信息失败!','error');
					},
					success:function(data){
						if(data.total==0){
							$.messager.alert('栏目审核','对不起，搜索的栏目不存在!','warning');
						}else{
							$("#columnAppr_table_id").datagrid("loadData",data);
						}
					}
				});		
			}
		
		}
	</script>
	
	<!-- 为审核按钮绑定单击事件 -->
	<script type="text/javascript">
		function apprColumn() {
			var rows = $("#columnAppr_table_id").datagrid('getChecked');
			console.log(rows);
			if(rows.length==0){
				$.messager.alert('站点审核','请选择需要审核的频道!','warning');
			}else{
				$('#columnappr').window('open');
				$("#columnappr_pass").prop("checked","checked");
				$('#columnappr .columnappr_textarea').hide();
			}
		}
		
	</script>
</body>
</html>






































