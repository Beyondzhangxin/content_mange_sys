<%@page import="java.util.Enumeration"%>
<%@ 
	page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<div class="easyui-layout" data-options="fit:true">
	<div data-options="region:'west',split:true" title="组织结构"
		style="width: 150px;">
		<div class="easyui-panel" style="padding: 5px; border: none;">
			<ul class="easyui-tree"
				data-options="
            url:'${pageContext.request.contextPath}/deptmgr/getDepartmentTree.do',
            method:'get',
            animate:true,
            onClick:function(node){    
                $('.department_t').text(node.text)
                var depId=node.id
                $.ajax({
                	url:'${pageContext.request.contextPath}/usermgr/depUsers.do?depId='+depId,
                	type:'post',
               		dataType:'json',
               		data:{'depId':depId},
               		success:function(data){
               			$('#department_dg').datagrid({
               				url:'${pageContext.request.contextPath}/usermgr/depUsers.do?depId='+depId
               			})
               		},
               		error:function(){
               			alert('加载失败')
               		}
                })
}"></ul>
		</div>
	</div>
	<div data-options="region:'center', tools:'#dep_tt',iconCls:'icon-ok'">
		<div id="dep_tt" style="padding: 2px 5px;">
			<table style="width: 100%">
				<tr>
					<!--  
					<td width="25%" align="left">
						<input class="easyui-textbox" id="fullName" name="fullName"
							type="text" style="width: 220"
							data-options="prompt:'部门名称',iconWidth:38,
								icons:[{
									iconCls:'icon-search'
								}]">
					
					</td>
					-->
					<td width="25%" align="center"
						style="font-size: 10px; text-align: left;"><span>部门名称:<span
							class="department_t"></span></span></td>
					<td width="25%" align="center"
						style="font-size: 10px; text-align: left;"><span>部门描述:<span
							class="department_t"></span></span></td>
					<!-- 
					 <td width="25%" align="right">
					<a href="#" onclick="javascript:dep_add()" class="easyui-linkbutton" iconCls="icon-add" plain="true" style="width:28%;margin:10px 0"></a>
					<a href="#" onclick="javascript:dep_edit()" class="easyui-linkbutton" iconCls="icon-edit" plain="true" style="width:28%;margin:10px 0"></a>
					<a href="#" onclick="javascript:dep_delect()" class="easyui-linkbutton" iconCls="icon-cancel" plain="true" style="width:28%;margin:10px 0"></a>
					</td>
					-->
				</tr>
			</table>
		</div>


		<table id="department_dg" style="height: 95%" class="easyui-datagrid"
			data-options="
                pagination:true,
                url:'${pageContext.request.contextPath}/usermgr/listall.do',
                method:'get',
                fitColumns:true,
                rownumbers:true,
                scrollbarSize:0,
     
                selectOnCheck:false,
                idField: 'id',
				treeField: 'name',
                ">
			<thead>
				<tr>
					<th data-options="field:'username',align:'center'" width="80">用户名</th>
					<th data-options="field:'userRealname',align:'center'" width="100">真实姓名</th>
					<th data-options="field:'mobilePhoneNum',align:'center'" width="80">联系电话</th>
				</tr>
			</thead>
		</table>


	</div>
</div>
<script src="${pageContext.request.contextPath}/js/deptmgr.js"></script>

