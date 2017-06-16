<%@page import="java.util.Enumeration"%>
<%@ 
	page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"  isELIgnored="false"
%>
<%@taglib uri="http://java.sun.com/jstl/core"  prefix="c"%>
<html>
<body>
	<table id="cont_appr" class="easyui-datagrid" style="width:100%;height:100%"
		data-options="
		width:'90%',
		height:'90%',
		fitColumns:true,
		pagination:true,
		pagePosition:'bottom',
		pageNumber:1,
		pageList:[10,15,20,25,30],
		pageSize:10,
		url:'${pageContext.request.contextPath}/conappr/findContentForConAppr.do',
		method:'post',
		columns:[[
			{field:'ck',checkbox:true},
			{field:'contentTitle',width:110,align:'center',title:'标题'},
			{field:'contentSmallTitle',width:110,align:'center',title:'小标题'},
			{field:'keyword',width:120,align:'center',title:'关键字'},
			{field:'contentCreateUserName',width:120,align:'center',title:'用户名'},
			{field:'contentFileName',width:110,align:'center',title:'内容名称'},
			{field:'contentDescribe',width:130,align:'center',title:'内容描述'},
			{field:'attribute',width:140,align:'center',title:'内容属性'}
		]],
		rownumbers:true,
		striped:true,
		singleSelect:true,
		checkOnSelect:true,
		toolbar:'#conappr_tool',
		">
	
	</table>
	
	
	<!--审核页-->
	<div id="examine" class="easyui-window" title="审核页面" data-options="modal:true,closed:true,iconCls:'icon-save'" style="width:700px;height:500px;padding:20px 40px;">
		<div id="addContentForAppr">
			<!-- <iframe src="http://localhost:8080/contentUpload/mysql.html" scrolling="auto" width="620" height="250" frameborder="0"> 
				
			</iframe> -->
		</div>
		<div style="width:200px;margin:40px auto">
			<label for="t_review_pass">
            	<input type="radio" value="t_review_pass" id="t_review_pass" name="review" checked="checked" onclick="examine()">审核通过
	        </label>
	        <label for="t_review_fail">
	            <input type="radio"  value="t_review_fail" id="t_review_fail" name="review" value="t_review_fail" onclick="examine()">审核未通过
	        </label>
		</div>
		<div style="display:none" class="textarea">
			<textarea id="review_fail_content" class="easyui-textbox" name="contentDescribe" data-options="prompt:'请输入审核未通过原因',multiline:true" style="width:590px;height:100px"></textarea>
		</div>
		<div style="width:185px;margin:20px auto 0 auto">
            <a  class="easyui-linkbutton" style="width:70px" data-options="iconCls:'icon-ok'" onclick="javascript:apprConetent()">确定</a>
            <a  class="easyui-linkbutton" style="width:70px;margin-left:5px" data-options="iconCls:'icon-cancel'" onclick="$('#examine').window('close')">取消</a>
        </div>
	</div>

	<!-- 创建数据网格（datagrid）面板的头部工具栏。-->
	<div id="conappr_tool">
		<table style="width:100%;padding:2px 5px">
			<tr>
				<!-- 搜索区 -->
				<td width="80%" align="left">
		
					<!-- 创建普通的文本框 -->
					<input style="width:120px;" id="title_appr" class="easyui-textbox" data-options="prompt:'请输入内容名称'">
					<input style="width:120px;" id="username_appr" class="easyui-textbox" data-options="prompt:'请输入用户名'">
					<!-- 创建搜素按钮 -->			
					<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="javascript:searchForContAppr()" style="width:70px">搜索</a>
				</td>
				
				<!-- 操作区 -->
				<td width="50%" align="right">
					<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add'" onclick="javascript:apprcontentForContent()" style="width:70px,margin-top:6px">审核</a>
				</td>
			</tr>
		</table>
	</div>

<!-- 为【确定】绑定单击事件 -->
<script type="text/javascript">
	function apprConetent() {
		var rows = $("#cont_appr").datagrid('getChecked');
		var contentId = rows[0].contentId;
		//获取状态：通过/未通过
		var review = $('#examine input[name="review"]:checked ').val();
		var reviewStatus;
		var reason="";
		if(review == "t_review_pass"){
			reviewStatus = 3;//审核通过：3
		}
		if(review == "t_review_fail"){
			var reviewStatus = 4;//审核未通过：4
			reason=$("#review_fail_content").val();
		}
		$.ajax({
			url:'${pageContext.request.contextPath}/conappr/ApprContent.do',
			type:"post",
			data:{"contentId":contentId,"reviewStatus":reviewStatus,"reason":reason},
			dataType:'json',
			success:function(data){
				if(data.success){
					$.messager.alert('搜索内容',data.msg,'warning');
					$('#examine').window('close');
					$("#cont_appr").datagrid('reload');
				}
			},
			error:function(){
				$.messager.alert('搜索内容',data.msg,'warning');
				$('#examine').window('close');
				$("#cont_appr").datagrid('reload');
			}
		});
	}
</script>

<!-- 为搜索按钮绑定单击事件 -->
<script type="text/javascript">
function searchForContAppr(){
	var contentTitle =$("#title_appr").val();
	var contentCreateUserName = $("#username_appr").val();	
	if(contentTitle==''&&contentCreateUserName==''){
		$.messager.alert('提示','请输入搜索信息!','info');
	}else{
	 	$.ajax({
			url:'${pageContext.request.contextPath}/conappr/addQueryForConAppr.do',
			type:'post',
			data:{"contentTitle":contentTitle,"contentCreateUserName":contentCreateUserName},
			dataType:'json',
			success:function(data){
				var total = data.total;
				if(total >0){
					$("#cont_appr").datagrid("loadData",data);
				}else{
					$.messager.alert('搜索内容','对不起,没有搜索记录!','warning');
				}
			}
		});
	}
	

}
</script>

<!-- 为审核【按钮】绑定单击事件 -->
<script type="text/javascript">
function apprcontentForContent(){
	var rows = $("#cont_appr").datagrid('getChecked');
	if(rows == 0){
		$.messager.alert('审核内容','请选择需要审核的内容!','warning');
	}else{
		//获取内容的contentPath
		var contentFileUrl = rows[0].contentFileUrl;
		var str='<iframe src="'+contentFileUrl+'" scrolling="auto" width="620" height="250" frameborder="0"></iframe>';
		var div = $("#addContentForAppr");
		div.empty();//清空后代节点
		div.append(str);//添加子节点
		$('#examine').window('open');
		$("#t_review_pass").prop("checked","checked");
		$('#examine .textarea').hide();
	}
}
</script>

<script type="text/javascript">
function examine() {
   	var review_text = $('input:radio[value=t_review_fail]:checked').val();
    if(review_text == 't_review_fail') {$('.textarea').show();}
    else {$('.textarea').hide();}
}
</script>
</body>
</html>
