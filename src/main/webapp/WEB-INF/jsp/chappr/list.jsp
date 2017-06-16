<%@page import="java.util.Enumeration"%>
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@taglib uri="http://java.sun.com/jstl/core"  prefix="c"%>
<html>
<body>
	<table id="chraAppr_table_id"></table>
	<div id="chrappr_toolbar">
		<table style="width:100%;padding:2px 5px">
			<tr>
				<td width="80%" align="left">
					<input id="charAppr_siteId" class="easyui-combobox" name="" data-options="prompt:'请选择站点',valueField:'siteId',textField:'siteName',
        		url:'${pageContext.request.contextPath}/sitemgr/findAllSite.do?'">
					<input style="width:120px;" id="chrappr_chraName_id" class="easyui-textbox" data-options="prompt:'请输入频道名称'">		
					<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="javascript:searchForChraAppr()" style="width:70px">搜索</a>
				</td>
				<td width="20%" align="right">
					<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add'" onclick="javascript:apprChra()" style="width:70px,margin-top:6px">审核</a>
				</td>
			</tr>
		</table>
	</div>
	<div  id="chappr" class="easyui-window" title="频道审核" style="width:400px;height:420px;padding:20px 40px;" data-options="modal:true,closed:true,iconCls:'icon-save'">
		<div style="width:200px;margin:40px auto">
			<label for="chappr_pass">
            	<input type="radio" value="1" name="review" id="chappr_pass" checked="checked" onclick="chappr_examine()">审核通过
	        </label>
	        <label for="chappr_fail">
	            <input type="radio"  value="2" name="review" id="chappr_fail" onclick="chappr_examine()">审核未通过
	        </label>
		</div>
		<div style="display:none" class="chappr_textarea">
			<textarea id="chappr_review_fail_content" class="easyui-textbox" name="contentDescribe" data-options="prompt:'请输入审核未通过原因',multiline:true" style="width:300px;height:100px"></textarea>
		</div>
		<div style="width:185px;margin:20px auto 0 auto">
            <a  class="easyui-linkbutton" style="width:70px" data-options="iconCls:'icon-ok'" onclick="javascript:channelAppr()">确定</a>
            <a  class="easyui-linkbutton" style="width:70px;margin-left:5px" data-options="iconCls:'icon-cancel'" onclick="$('#chappr').window('close')">取消</a>
        </div>
	</div>
	<script type="text/javascript">
	function chappr_examine() {
	   	var review_text = $('#chappr input:radio[value=2]:checked').val();
	    if(review_text == 2) {$('#chappr .chappr_textarea').show();}
	    else {$('#chappr .chappr_textarea').hide();
	}
	}
	function channelAppr(){
		var rows = $("#chraAppr_table_id").datagrid('getChecked');
		var channelId=rows[0].channelId;
		var radio =($(":radio:checked").val());
		var textarea_text = ($("#chappr_review_fail_content").val());
		$.ajax({
			url:'${pageContext.request.contextPath}/chrappr/channelAppr.do',
			type:'post',
			data:{
				"channelId":channelId,
				"channelAppr":radio,
				"noPassReason":textarea_text
			},
			error:function(){
				$.messager.alert('频道审核','保存频道审核结果失败!','error');
				$('#chappr').window('close');
				$("#chraAppr_table_id").datagrid("reload");
			},
			success:function(data){
				if(data.success){
					$.messager.alert('频道审核',data.message,'info');
					$('#chappr').window('close');
					$("#chraAppr_table_id").datagrid("reload");
				}
			}
		})
	}
		$("#chraAppr_table_id").datagrid({
			width:'100%',
			height:"100%",
			fitColumns:true,
			pagination:true,
			pagePosition:'bottom',
			pageNumber:1,
			pageList:[10,15,20,25,30],
			pageSize:10,
			url:'${pageContext.request.contextPath}/chrappr/getDataByChannelAppr.do',
			method:'post',
			columns:[[
				{field:'ck',checkbox:true},
				{field:'siteName',width:120,align:'center',title:'站点名称'},
				{field:'channelName',width:120,align:'center',title:'频道名称'},
				{field:'username',width:120,align:'center',title:'创建者'},
				{field:'createTime',width:120,align:'center',title:'创建时间'},			
			]],
			rownumbers:true,
			striped:true,
			singleSelect:true,
			toolbar:'#chrappr_toolbar',
		});
	</script>
	<!-- 搜索单击函数 -->
	<script type="text/javascript">
		function searchForChraAppr() {
			var siteId= $("#charAppr_siteId").combobox("getValue");
			var chraName = $("#chrappr_chraName_id").val();
			if(siteId==''&&chraName==''){
				$.messager.alert('提示','请输入搜索信息!','info');
			}else{
				$.ajax({
					url:'${pageContext.request.contextPath}/chrappr/addSearchCriteria.do',
					type:'post',
					data:{channelName:chraName,siteId:siteId},
					error:function(){
						$.messager.alert('频道审核','搜索频道信息失败!','error');
					},
					success:function(data){
						if(data.total==0){
							$.messager.alert('频道审核','对不起，搜索的频道不存在!','warning');
						}else{
							$("#chraAppr_table_id").datagrid("loadData",data);
						}
					}
				});
			}
			
		}
	</script>
	
	<!-- 为审核按钮绑定单击事件 -->
	<script type="text/javascript">
		function apprChra() {
			var rows = $("#chraAppr_table_id").datagrid('getChecked');
			if(rows.length==0){
				$.messager.alert('站点审核','请选择需要审核的频道!','warning');
			}else{
				$('#chappr').window('open');		
				$("#chappr_pass").prop("checked","checked");
				$('#chappr .chappr_textarea').hide();
			}
		}
		
	</script>
</body>
</html>






































