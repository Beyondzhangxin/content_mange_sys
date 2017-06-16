<%@page import="java.util.Enumeration"%>
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@taglib uri="http://java.sun.com/jstl/core"  prefix="c"%>
<html>
<body>
	<table id="siteAppr_table_id"></table>
	<!-- 数据网格头部工具导航 -->
	<div id="siteApp_toolbar">
		<table style="width:100%;padding:2px 5px">
			<tr>
				<!-- 搜索区 -->
				<td width="80%" align="left">
					<!-- 创建普通的文本框 -->
					<input style="width:120px;" id="siteAppr_siteName_id" class="easyui-textbox" data-options="prompt:'请输入站点名称'">
					<!-- 创建搜素按钮 -->			
					<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="javascript:searchForSiteAppr()" style="width:70px">搜索</a>
				</td>
				<!-- 操作区 -->
				<td width="50%" align="right">
					<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add'" onclick="javascript:apprSite()" style="width:70px,margin-top:6px">审核</a>
				</td>
			</tr>
		</table>
	</div>
	<!-- 站点审核 -->
	<div  id="siteappr" class="easyui-window" title="站点审核" style="width:400px;height:420px;padding:20px 40px;" data-options="modal:true,closed:true,iconCls:'icon-save'">
		<div style="width:200px;margin:40px auto">
			<label for="siteappr_pass">
            	<input type="radio" value="1" id="siteappr_pass" name="review"  checked="checked" onclick="siteappr_examine()">审核通过
	        </label>
	        <label for="siteappr_fail">
	            <input type="radio"  value="2" id="siteappr_fail" name="review"   onclick="siteappr_examine()">审核未通过
	        </label>
		</div>
		<div style="display:none" class="siteappr_textarea">
			<textarea id="siteappr_review_fail_content" class="easyui-textbox" name="contentDescribe" data-options="prompt:'请输入审核未通过原因',multiline:true" style="width:300px;height:100px"></textarea>
		</div>
		<div style="width:185px;margin:20px auto 0 auto">
            <a  class="easyui-linkbutton" style="width:70px" data-options="iconCls:'icon-ok'" onclick="javascript:siteAppr()">确定</a>
            <a  class="easyui-linkbutton" style="width:70px;margin-left:5px" data-options="iconCls:'icon-cancel'" onclick="$('#siteappr').window('close')">取消</a>
        </div>
	</div>
	<script type="text/javascript">
	function siteappr_examine() {
	   	var review_text = $('#siteappr input:radio[value=2]:checked').val();
	    if(review_text == 2) {$('#siteappr .siteappr_textarea').show();}
	    else {$('#siteappr .siteappr_textarea').hide();}
	}
	function apprSite() {
		var rows = $("#siteAppr_table_id").datagrid('getChecked');
		if(rows.length==0){
			$.messager.alert('站点审核','请选择需要审核的站点!','warning');
		}else{
			$('#siteappr').window('open');
			$("#siteappr_pass").prop("checked","checked");
			$('#siteappr .siteappr_textarea').hide();
		}
	}
	function  siteAppr(){
		var rows = $("#siteAppr_table_id").datagrid('getChecked');
		var siteId=rows[0].siteId;
		var radio =($(":radio:checked").val());
		var textarea_text = ($("#siteappr_review_fail_content").val());
		$.ajax({
			url:'${pageContext.request.contextPath}/siteappr/saveSiteAppr.do',
			type:'post',
			data:{
				"siteId":siteId,
				"siteAppr":radio,
				"apprReason":textarea_text
			},
			error:function(){
				$.messager.alert('频道审核','保存频道审核结果失败!','error');
				$('#siteappr').window('close');
				$("#siteAppr_table_id").datagrid("reload");
			},
			success:function(data){
				if(data.success){
					$.messager.alert('站点审核',data.message,'info');
					$('#siteappr').window('close');
					$("#siteAppr_table_id").datagrid("reload");
				}
			}
		})
	}
		$("#siteAppr_table_id").datagrid({
			width:'100%',
			height:"100%",
			fitColumns:true,
			pagination:true,
			pagePosition:'bottom',
			pageNumber:1,
			pageList:[10,15,20,25,30],
			pageSize:10,
			url:'${pageContext.request.contextPath}/siteappr/findSiteForSiteAppr.do',
			method:'post',
			columns:[[
				{field:'ck',checkbox:true},
				{field:'siteName',width:120,align:'center',title:'站点名称'},
				{field:'siteUrl',width:120,align:'center',title:'站点地址'},
				{field:'userRealname',width:120,align:'center',title:'站点创建者'},
				{field:'createTime',width:120,align:'center',title:'创建时间'},
				{field:'adminEmail',width:120,align:'center',title:'管理员邮箱'},
				
			]],
			rownumbers:true,
			striped:true,
			singleSelect:true,
			toolbar:'#siteApp_toolbar',
		});
	</script>
	<!-- 搜索单击函数 -->
	<script type="text/javascript">
		function searchForSiteAppr() {
			var siteName = $("#siteAppr_siteName_id").val();
			console.log(siteName)
			if(siteName==''){
				$.messager.alert('提示','请输入搜索信息!','info');
			}else{
				$.ajax({
					url:'${pageContext.request.contextPath}/siteappr/addSiteNameForSiteAppr.do',
					type:'post',
					data:{siteName:siteName},
					error:function(){
						$.messager.alert('站点审核','搜索站点信息失败!','error');
					},
					success:function(data){
						if(data.total==0){
							$.messager.alert('站点审核','对不起，搜索的站点不存在!','warning');
						}else{
							$("#siteAppr_table_id").datagrid("loadData",data);
						}
					}
				});
			}
		
		}
	</script>
</body>
</html>






































