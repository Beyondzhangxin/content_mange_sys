<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<head>
<title>角色管理</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/themes/icon.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/demo/demo.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/all.css">

<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery.easyui.min.js"></script>

<style>
span.role_span {
	margin-top: 30px;
	width: 100%;
	text-align: center;
	display: inline-block;
}

input:focus {
	outline: none;
}
</style>
</head>
<body>
	<table id="role_dg"     class="easyui-datagrid"
		style="width: 100%; height: 100%"
		data-options="
		idField:'roleId',
		rownumbers:true,
		singleSelect:false,
		pagination:true,
		rownumbers:true,
		fit:true,
		toolbar:'#role_tt',
    	striped:true,
    	fitColumns:true,
    
    pagePosition:'bottom',
			pageNumber:1,
			pageList:[10,15,20,25,30],
			pageSize:10,
    	
		url:'${pageContext.request.contextPath}/rolemgr/listdata.do',method:'get',toolbar:'#role_tt'">
		<thead>
			<tr>
				<th data-options="field:'ck',checkbox:true"></th>
				<th data-options="field:'roleId',editor:'text',align:'center'"
					style="width: 49%">角色编码</th>
				<th data-options="field:'roleName',editor:'text',align:'center'"
					style="width: 50%">角色名称</th>
			</tr>
		</thead>
	</table>

	<!--添加页-->
	<div id="role_w" class="easyui-window" title="添加角色"
		data-options="modal:true,closed:true,iconCls:'icon-save'"
		style="width: 400px; height: 300px; text-align: center;">
		<form method="post" id="add_role1">

			<div style="margin-top: 60px;">
				<span class="role_span">角色名称: <input type="text"
					class="easyui-textbox" name="roleName"></span>
			</div>
			<div class="role_button">

				<a href="javascript:void(0)" class="easyui-linkbutton"
					onclick="saveRole()"
					style="align: right; margin: 1px 0px 0 23px; padding: 1px 17px;">提交</a>
				<a href="javascript:void(0)" class="easyui-linkbutton"
					style="align: right; padding: 0 15px; margin: 60px 0 60px 19px;"
					onclick="clearForm()">重置</a>

			</div>
		</form>
	</div>

	<!--修改页-->
	<div id="role_e" class="easyui-window" title="修改名称"
		data-options="modal:true,closed:true,iconCls:'icon-save'"
		style="width: 400px; height: 300px; margin: 0 auto; text-align: center">
		<form method="post" id="add_role2">
		<div style="margin-top:15px;position:absolute;top:-999em">
                    <span class="role_span">ID: <input type="text"  name="roleId" ></span>
                </div>
			<div style="margin-top: 40px;">
				<span class="role_span">角色名称: <input type="text"
					class="easyui-textbox" name="roleName"></span>
			</div>
			<div class="role_button">
				<a  class="easyui-linkbutton"
					onclick="editRole()"
					style="align: right; margin: 1px 0px 0 23px; padding: 1px 17px;">提交</a>
				<a  class="easyui-linkbutton"
					style="align: right; margin: 60px 0 60px 19px; padding: 0 15px;"
					onclick="clearForm()">重置</a>
			</div>
		</form>
	</div>

	<!--删除-->
	<div id="role_d" class="easyui-window" title="删除角色"
		data-options="modal:true,closed:true,iconCls:'icon-save'"
		style="width: 400px; height: 250px; text-align: center; padding: 30px 0">
		<div data-options="region:'center'" style="padding: 10px;">确定删除该角色？</div>
		<a class="easyui-linkbutton delect_roleList"
			data-options="iconCls:'icon-ok'" href="javascript:void(0)"
			style="width: 80px; display: inline-block; margin: 30px 15px 0 0;">确定</a>
		<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'"
			href="javascript:void(0)" onclick="$('#role_d').window('close')"
			style="width: 80px; display: inline-block; margin: 30px 0 0 15px;">取消</a>
	</div>



	<!--按钮-->
	<div id="role_tt" style="padding: 2px 5px;">
		<table style="width: 100%">
			<tr>
				<td width="50%" align="left"><input class="easyui-searchbox"
					id="roleName" name="roleName" type="text" style="width: 220px"
					searcher="sitedoSearch" prompt="请输入角色名称">
				</td>
				<td width="50%" align="right"><a href="#"
					onclick="javascript:role_add()" class="easyui-linkbutton"
					iconCls="icon-add" plain="true"></a> <a href="#"
					onclick="javascript:role_edit()" class="easyui-linkbutton"
					iconCls="icon-edit" plain="true"></a> <a href="#"
					onclick="javascript:role_delect()" class="easyui-linkbutton"
					iconCls="icon-cancel" plain="true"></a></td>
			</tr>
		</table>
	</div>
	<script src="${pageContext.request.contextPath}/js/rolemgr.js"></script>
	<script type="text/javascript">
		function sitedoSearch(value, name) {
			$
					.ajax({
						url : '${pageContext.request.contextPath}/rolemgr/searchRole.do?roleName='
								+ value,
						type : 'get',
						success : function(data) {
							$("#role_dg").datagrid("loadData", data);
						},
						error : function() {
							alert("ssss")
						}
					});
		}
	</script>
	<script type="text/javascript">
		function saveRole() {
			$('#add_role1')
					.form(
							'submit',
							{
								url : '${pageContext.request.contextPath}/rolemgr/saveOrUpdateRole.do',
								onSubmit : function() {
									return $(this).form('validate');
								},
								success : function(result) {

									if (result) {
										$.messager.show({
											title : '结果',
											msg : result
										});
                                        $('#role_w').dialog('close'); // close the dialog
                                        $('#role_dg').datagrid('reload'); // reload the user data
									} else {
                                        $.messager.show({
                                            title: '结果',
                                            msg: '提交失败！'
                                        });
										$('#role_w').dialog('close'); // close the dialog
									}
								}
							});
		}

		function editRole() {
			$('#add_role2')
					.form(
							'submit',
							{
								url : '${pageContext.request.contextPath}/rolemgr/saveOrUpdateRole.do',
								onSubmit : function() {
								return $(this).form('validate');
								},
								success : function(result) {
									if (result) {
										$.messager.show({
											title : 'Error',
											msg : result
										});
									} else {
										$('#role_e').dialog('close'); // close the dialog
										$('#role_dg').datagrid('reload'); // reload the user data
									}
								}
							});
		}

		function role_add() {
			$('#role_w').window('open');
			$('#add_role1').form('reset');
		}
		function role_edit() {
			var rows = $('#role_dg').datagrid('getChecked');
			if (rows.length == 1) {
				$('#role_e').window('open');
				console.info(rows[0]);
				$('#add_role2').form('load', {
					roleName : rows[0].roleName,
					roleId:rows[0].roleId
				});
			} else {
				alert("选择一个内容编辑");
			}
		}
		function role_delect() {
			var rows = $('#role_dg').datagrid('getChecked');
			if (rows.length > 0) {
				$('#role_d').window('open');
				$(".delect_roleList")
						.click(
								function() {
									var parm = [];
									for (var i = 0; i < rows.length; i++) {
										var code = rows[i].roleId;
										parm.push(code);
									}
									$.ajax({
												url : "${pageContext.request.contextPath}/rolemgr/delete.do?roleId="
														+ parm,
												type : "post",
												dataType : "text",
												data : {
													"userId" : parm
												},
												success : function(data) {
													$("#role_dg").datagrid('reload');
													$('#role_d').window('close');
													$('#role_dg').datagrid('unselectAll');	
												},
												error : function() {
													alert("删除失败");
												}
											})
								});
			} else {
				alert("请选择要删除的选项");
			}
		}

		//重置按钮
		function clearForm() {
			$('#add_role1').form('reset');
			$('#add_role2').form('reset');
		}
	</script>


</body>