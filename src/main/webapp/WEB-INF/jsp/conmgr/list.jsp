<%@ page import="java.util.Enumeration"%>
<%@ page language="java"  contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"  isELIgnored="false"%>
<%@taglib uri="http://java.sun.com/jstl/core"  prefix="c"%>

<style>
	.recycleBin_list_button{
		display:none;
	}
</style>
<table id="content_dg" class="easyui-datagrid" style="width:90%;height:100%"
		data-options="
		rownumbers:true,
		pageSize:10,
		singleSelect:false,
		pagination:true,
		rownumbers:true,
		fit:true,
		toolbar:'#content_tt',
    	striped:true,
    	fitColumns:false,
		url:'${pageContext.request.contextPath}/conmgr/findContentForlistdata.do',
		method:'post'">
	<thead>
		<tr>
				<th data-options="field:'ck',checkbox:true"></th>
				<th data-options="field:'contentTitle',align:'center'" style="width:25%">内容名称</th>
				<th data-options="field:'keyword',align:'center'" style="width:15%">关键字</th>
				<th data-options="field:'review',align:'center'" style="width:10%">审核状态</th>
				<th data-options="field:'contentCreateUserName',align:'center'" style="width:15%">创建者</th>
				<th data-options="field:'contentCreateTime',align:'center'" style="width:10%">创建时间</th>
				<th data-options="field:'contentDescribe',align:'center'" style="width:24%">内容描述</th>
		</tr>
	</thead>
</table>
<!-- 操作区 -->
<div id="content_tt" style="padding:2px 5px;">
<table style="width:100%">
	<tr>
		<td width="70%" align="left">
				<a id="btn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-list'" onclick="javascript:contentList()">内容列表</a>
				<a id="btn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-text'" onclick="javascript:content_add()">内容编辑</a>
				<a id="btn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-recycle'" onclick="javascript:recycleBin()">回收站</a>
				<div style="margin-top:10px">
					<select id="check_content_review_id" name="isReview" class="easyui-combobox" style="width:120px;">
						<option value="">请选择审核状态</option>
						<option value="2">未审核</option>
						<option value="3">审核通过</option>
						<option value="4">审核未通过</option>
						<option value="1">不需要审核</option>
					</select>
					<input style="width:120px;" class="easyui-textbox title_name" data-options="prompt:'请输入内容名称'">
					<input style="width:120px;" class="easyui-textbox user_name" data-options="prompt:'请输入用户名'">			
					<a style="width:70px" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="javascript:contentdoSearch()">搜索</a>
				</div>
		</td>
		<td width="50%" align="right">
			<span class="content_list_button">
				<a href="#" onclick="javascript:content_edit()" class="easyui-linkbutton" iconCls="icon-edit" plain="true"></a>
				<a href="#" onclick="javascript:content_delect()" class="easyui-linkbutton" iconCls="icon-cancel" plain="true"></a>
			</span>
			<span class="recycleBin_list_button">
				<a id="btn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-reload'" onclick="javascript:recovery()">恢复文章</a>
				<a id="btn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" onclick="javascript:delect_contentList()">彻底删除</a>
			</span>	
		</td>
	</tr>
</table>
</div>

<!-- 添加内容页 -->
<div id="content_w" class="easyui-window" title="编辑文章内容" data-options="modal:true,closed:true,iconCls:'icon-save'" style="width:100%;height:100%;overflow:hidden; scroll='no'" >
	<form method="post" id="content_add_message">
		<div style="height:25%">
			<div style="padding-top:20px">
				<span style="width:88px;display:inline-block;text-align:right;margin-bottom:10px;">文章标题：</span>
				<input class="easyui-textbox" data-options="required:true,validType:'illegalChar'"  name="contentTitle" style="width:320px">
				<span style="width:88px;display:inline-block;text-align:right;margin-bottom:10px;">副标题：</span>
				<input class="easyui-textbox"  name="contentSmallTitle" data-options="validType:'illegalChar'" >
			</div>
			<div>
				<span style="width:88px;display:inline-block;text-align:right;margin-bottom:10px;">优先级：</span>
				<select name="attribute" class="easyui-combobox" style="width:70px;">
					<option>一般</option>
					<option>重要</option>
					<option>紧急</option>
				</select>
				<span style="width:80px;display:inline-block;text-align:right;margin-bottom:10px;">是否审核：</span>
				<select name="isReview" class="easyui-combobox" style="width:70px;">
					<option value="2">是</option>
					<option value="1">否</option>
				</select>
				<span style="width:90px;display:inline-block;text-align:right;margin-bottom:10px;">是否置顶：</span>
				<select name="isTop" class="easyui-combobox" style="width:70px;">
					<option value="2">是</option>
					<option value="1">否</option>
				</select>
				<span style="width:80px;display:inline-block;text-align:right;margin-bottom:10px;">是否签收：</span>
				<select name="isSign" class="easyui-combobox" style="width:70px;">
					<option value="2">是</option>
					<option value="1">否</option>
				</select>
			</div>
			<div>
				<span style="width:88px;display:inline-block;text-align:right;margin-bottom:10px;">关键字：</span>
				<input class="easyui-textbox"  name="keyword" data-options="validType:'illegalChar'" >
				<span style="width:60px;display:inline-block;text-align:right;margin-bottom:10px;">模板：</span>
				<input id="add_content_template_id" name="templateId" style="width:100px;">
			</div>
			
			<div style="margin-bottom:10px;">
				<span style="width:88px;display:inline-block;text-align:right;margin-bottom:10px;">内容描述：</span>
				<input name="contentDescribe" data-options="required:true,multiline:true,validType:'illegalChar'" style="width:590px" class="easyui-textbox">
			</div>
			</div>
			<!--发布内容-->
			<div  style="height:75%">
				<div>
					<textarea data-options="required:true" name="contentFileUrl"  class="Content_textarea easyui-validatebox" id="add_content_textarea_id" style="width:100%;height:400px">
					
					</textarea>
				</div>
				<div style="width:159px;height:10%;margin:2px auto 0 auto">
					<a  class="easyui-linkbutton content_button" style="width:70px" id="content_add_message" onclick="javascript:add_content_message()">确定发布</a>
					<a  class="easyui-linkbutton content_button" style="width:70px" onclick="$('#content_w').window('close')">取消</a>
				</div>
		</div>
	</form>
</div>
<!-- 编辑内容页 -->
<div id="content_e" class="easyui-window" title="修改内容" data-options="modal:true,closed:true,iconCls:'icon-save'" style="width:100%;height:100%;overflow:hidden; scroll='no'" >
	<form method="post" id="content_edit_message">
	<div style="height:25%">
		<div style="padding-top:20px;">
			<span style="width:88px;display:inline-block;text-align:right;margin-bottom:10px;">文章标题：</span>
			<input class="easyui-textbox title" data-options="" name="contentTitle" style="width:320px">
			<span style="width:88px;display:inline-block;text-align:right;margin-bottom:10px;">副标题：</span>
			<input class="easyui-textbox smallTitle" data-options="required:true," name="contentSmallTitle">
		</div>
		<div>
			<span style="width:88px;display:inline-block;text-align:right;margin-bottom:10px;">优先级：</span>
			<select name="attribute" class="easyui-combobox" style="width:70px;">
				<option>一般</option>
				<option>重要</option>
				<option>紧急</option>
			</select>
			<span style="width:80px;display:inline-block;text-align:right;margin-bottom:10px;">是否审核：</span>
			<select name="isReview" class="easyui-combobox" style="width:70px;">
				<option value="2">是</option>
				<option value="1">否</option>
			</select>
			<span style="width:90px;display:inline-block;text-align:right;margin-bottom:10px;">是否置顶：</span>
			<select name="isTop" class="easyui-combobox" style="width:70px;">
				<option value="2">是</option>
				<option value="1">否</option>
			</select>
			<span style="width:80px;display:inline-block;text-align:right;margin-bottom:10px;">是否签收：</span>
			<select name="isSign" class="easyui-combobox" style="width:70px;">
				<option value="2">是</option>
				<option value="1">否</option>
			</select> 
		</div>
		<div>
			<span style="width:88px;display:inline-block;text-align:right;margin-bottom:10px;">关键字：</span>
			<input class="easyui-textbox keyword" data-options="required:true,missingMessage:'不能为空'" name="keyword">
			<span style="width:60px;display:inline-block;text-align:right;margin-bottom:10px;">模板：</span>
			<input id="edit_content_template_id" name="templateId" style="width:100px;">
		</div>
		<div style="margin-bottom:10px;">
			<span style="width:88px;display:inline-block;text-align:right;margin-bottom:10px;">内容描述：</span>
			<input name="contentDescribe" data-options="multiline:true,required:true,missingMessage:'不能为空'" style="width:590px;" class="easyui-textbox contentDescribe">
		</div>
		</div>
		<!--发布内容-->
		<div  style="height:75%" >
			<div>
			<textarea name="contentFileUrl" cols="80" class="edit_Content_textarea" id="edit_Content_textarea_id" style="width:98%;height:400px" >
			
			</textarea>
			</div>
			<div style="width:159px;margin:2px auto 0 auto;height:10%">
			<a  class="easyui-linkbutton content_button" style="width:70px" id="content_add_message" onclick="javascript:edit_content_message()">确定修改</a>
			<a  class="easyui-linkbutton content_button" style="width:70px" onclick="$('#content_e').window('close')">取消</a>
			</div>
		</div>
	</div>
	</form>
</div>
 <!-- ===========================================================以下部分为操作函数============================================================ -->
 <!-- 编辑内容的相关函数 -->
<script type="text/javascript">
 function content_edit(){
	var rows = $('#content_dg').datagrid('getChecked');
	if(rows.length == 0||rows.length>1){
		$.messager.alert('提示','请选择一篇文章进行编辑!','info');
	}else{
		var edit_editor = KindEditor.create('.edit_Content_textarea', {
			cssPath : '<%=request.getContextPath() %>/kindeditor-4.1.7/plugins/code/prettify.css',
			uploadJson : '<%=request.getContextPath() %>/kindeditor-4.1.7/jsp/upload_json.jsp',
			fileManagerJson : '<%=request.getContextPath() %>/kindeditor-4.1.7/jsp/file_manager_json.jsp',
			allowFileManager : true,
			afterBlur: function(){this.sync();},
			afterCreate : function() {
	            var self = this;
	            KindEditor.ctrl(document, 13, function() {
	                self.sync();
	                document.forms['example'].submit();
	            });
	            KindEditor.ctrl(self.edit.doc, 13, function() {
	                self.sync();
	                document.forms['example'].submit();
	            });
	        },
	        items:['source','undo','redo','justifyleft', 'justifycenter', 'justifyright','justifyfull',
	        		'insertorderedlist', 'insertunorderedlist', 'indent', 'outdent', 'subscript',
	        		'superscript','quickformat', 'fullscreen','formatblock', 'fontname','/',
	        		'fontsize','forecolor', 'hilitecolor', 'bold','italic', 'underline', 
	        		'strikethrough', 'lineheight','removeformat', 'image', 'insertfile',
	        		'table', 'hr', 'emoticons','pagebreak','print',],
		});
		//弹出编辑窗口
		$('#content_e').window('open');
		 //加载模板下拉选菜单
		 $("#edit_content_template_id").combobox({
			 url:'${pageContext.request.contextPath}/conmgr/getContentModForContent.do',
			 method:'post',
			 textField:'templateName',
			 valueField:'templateId',
			 prompt:'请选择模板',
			 onSelect:function(data){
				 var templateId=data.templateId;
				 $.ajax({
					url:'${pageContext.request.contextPath}/conmgr/getModContent.do?templateId='+templateId,
		        	type:'post',
		        	success:function(data){        			
		        		edit_editor.html(data.ModContent);
		        	}
				 })
			 }
		 });
		//添加已有内容
		var contentTitle=rows[0].contentTitle;
		$(".title").textbox('setValue',contentTitle);
		var contentSmallTitle=rows[0].contentSmallTitle;
		$(".smallTitle").textbox('setValue',contentSmallTitle);
		var keyword=rows[0].keyword;
		$('.keyword').textbox('setValue',keyword);
		var contentDescribe=rows[0].contentDescribe;
		$('.contentDescribe').textbox('setValue',contentDescribe);
	}
}
//为【确定修改】按钮绑定单击事件--------------------------表单提交回掉处理
function edit_content_message() {
	var rows = $('#content_dg').datagrid('getChecked');
	var contentId = rows[0].contentId;
	var contentFilePath = rows[0].contentFilePath;
	$("#content_edit_message").form('submit',{
        url:'${pageContext.request.contextPath}/conmgr/editContent.do',
        onSubmit: function(param){
        	param.contentId = contentId;
        	param.contentFilePath = contentFilePath;
        	return $(this).form('validate');
        },
        success: function(result){
        	var data = eval("("+result+")");
            if (data.success){
            	$.messager.alert('提示',data.msg,'info');
                $('#content_e').dialog('close');  
                $('#content_dg').datagrid('reload');
                $('#content_edit_message').form('clear');//清除表单中的内容
            } else {
            	$.messager.alert('提示',data.msg,'info'); 
            }
        }
    });
}


</script>
 <!-- ======================================================================================================================= -->
<!-- 操作区函数 -->
<script type="text/javascript">
	 //为搜索绑定单击事件
	 function contentdoSearch(){
			var isReview = $("#check_content_review_id").combobox('getValue');
			var contentTitle=$(".title_name").val();
			var contentCreateUserName=$(".user_name").val();
			if(isReview==''&&contentTitle==''&&contentCreateUserName==''){
				$.messager.alert('提示','请输入搜索信息!','info');
			}else{
				$.ajax({
					url:'${pageContext.request.contextPath}/conmgr/addQueryCriteria.do',
					type:'post',
					data:{isReview:isReview,contentTitle:contentTitle,contentCreateUserName:contentCreateUserName},
					success:function(data){ 	
						$("#content_dg").datagrid("loadData",data);		
			        },
					error:function(){
						$.messager.alert('提示','数据读取失败!','info');
					}
				});
			}
		
		}
</script>

<!-- 添加内容的相关函数-->
<script type="text/javascript">
$.extend($.fn.validatebox.defaults.rules, {
	illegalChar: {
        validator: function(value){
            var rex=/[`~!@#\$%\^\&\*\(\)_\+<>\?:"\{\},\.\\\/;'\[\]]/im;
            if(rex.test(value)) {return false;}else{return true;}
        },
        message: '不能包含特殊字符'
    }
});
//弹出添加页面
function content_add(){
	var editor = KindEditor.create('.Content_textarea', {
		cssPath : '../kindeditor-4.1.7/plugins/code/prettify.css',
		//uploadJson : '${pageContext.request.contextPath}/upload/image.do',
		uploadJson : '<%=request.getContextPath() %>/kindeditor-4.1.7/jsp/upload_json.jsp',
		fileManagerJson : '../kindeditor-4.1.7/jsp/file_manager_json.jsp',
        allowFileManager : true,
        afterBlur: function(){this.sync();},
        afterCreate : function() {
            var self = this;
            KindEditor.ctrl(document, 13, function() {
                self.sync();document.forms['example'].submit();
            });
            KindEditor.ctrl(self.edit.doc, 13, function() {
                self.sync();
                document.forms['example'].submit();
            });
        },
        items:['source','undo','redo','justifyleft', 'justifycenter', 'justifyright','justifyfull',
        		'insertorderedlist', 'insertunorderedlist', 'indent', 'outdent', 'subscript',
        		'superscript','quickformat', 'fullscreen','formatblock', 'fontname','/',
        		'fontsize','forecolor', 'hilitecolor', 'bold','italic', 'underline', 
        		'strikethrough', 'lineheight','removeformat', 'image', 'insertfile',
        		'table', 'hr', 'emoticons','pagebreak','print',],
	});
	$('#content_w').window('open');
	 //加载模板下拉选菜单
	 $("#add_content_template_id").combobox({
		 url:'${pageContext.request.contextPath}/conmgr/getContentModForContent.do',
		 method:'post',
		 textField:'templateName',
		 valueField:'templateId',
		 prompt:'请选择模板',
		 onSelect:function(data){
			 var templateId=data.templateId;
			 $.ajax({
				url:'${pageContext.request.contextPath}/conmgr/getModContent.do?templateId='+templateId,
	        	type:'post',
	        	success:function(data){
	        		console.log(data.ModContent);
	        		editor.html(data.ModContent);
	        	}
			 })
		 }
	 });
}

//为内容发布按钮绑定单击事件
function add_content_message(){
	$("#content_add_message").form('submit',{
        url:'${pageContext.request.contextPath}/conmgr/addContent.do',
        onSubmit: function(){
            return $(this).form('validate');	                
        },
        success: function(result){
 	     	 data = eval("(" + result + ")");// 将JSON字符串转换成对象
        	  if (data.success){
              	$.messager.alert('提示',data.msg,'info');
                  $('#content_w').dialog('close');  
                  $('#content_dg').datagrid('reload');
                  $('#content_add_message').form('clear');//清除表单中的内容
              } else {
              	$.messager.alert('提示',data.msg,'info');
              }
        }
    });
}
</script>

<!-- 为【内容列表】绑定单击事件 -->
<script type="text/javascript">
function contentList(){
	$('#check_content_review_id').combobox('clear');//清空审核状态下拉选
	$('.title_name').textbox('clear');//清空内容标题输入框
	$(".user_name").textbox('clear');//清空用户名输入框
	$('#content_dg').datagrid({
		url:"${pageContext.request.contextPath}/conmgr/findContentForlistdata2.do?"
	});
	$(".recycleBin_list_button").hide();
	$(".content_list_button").show();
}
</script>

<!-- 为【回收站】按钮绑定单击事件 -->
<script type="text/javascript">
function recycleBin(){
	$('#check_content_review_id').combobox('clear');//清空审核状态下拉选
	$('.title_name').textbox('clear');//清空内容标题输入框
	$(".user_name").textbox('clear');//清空用户名输入框
	$('#content_dg').datagrid({
		url:"${pageContext.request.contextPath}/conmgr/findContentForRecycle.do"
	});
	$(".recycleBin_list_button").show();
	$(".content_list_button").hide();
}
</script>

<!-- 为【删除】按钮绑定单击事件 -->
<script type="text/javascript">
function content_delect(){
	var rows = $('#content_dg').datagrid('getChecked');
	if(rows.length == 0){
		$.messager.alert('提示','请选择要删除的文章!','info');
	}else{
		$.messager.confirm('提示', '确定删除所选中的内容?', function(r){
			if (r){
				var parm = []; 
				for (var i = 0; i < rows.length; i++) {
					 var code = rows[i].contentId;
		             parm.push(code);
		             $.ajax({
		                 url:"${pageContext.request.contextPath}/conmgr/moveToRecycle.do",
		                 type:"post",
		                 dataType:"json",
		                 data:JSON.stringify(parm),
		                 contentType:"application/json",
		                 success:function(data){
		                 	$.messager.alert('提示',data.message,'info');
		                 	$('#content_dg').datagrid('reload'); 
		                 },
		                 error:function(){
		                 	$.messager.alert('提示','删除失败!','info');
		                 }
		            })
				}
			}
		});
	}
}
</script>

<!-- 为【恢复文章】按钮绑定单击事件 -->
<script type="text/javascript">
function recovery(){
	var rows = $('#content_dg').datagrid('getChecked');
	if(rows.length == 0){
		$.messager.alert('提示','请选择需要恢复的文章!','info');
	}else{
		$.messager.confirm('提示', '确定恢复所选中的文章?', function(r){
			if(r){
				 var parm = [];
				 for (var i = 0; i < rows.length; i++) {
					 var code = rows[i].contentId;
					 parm.push(code);
				 }
				 $.ajax({
					 url:"${pageContext.request.contextPath}/conmgr/return2ContentList.do",
					 type:"post",
					 dataType:"json",
					 data:JSON.stringify(parm),
					 contentType:"application/json",
					 success:function(data){
						 $.messager.alert('提示',data.message,'info');
						 $('#content_dg').datagrid('reload'); 
					},
					error:function(){
	                	$.messager.alert('提示','恢复文章失败!','info');
	                }
				 });
			}
		})
	}
}
</script>

<!-- 为【彻底删除】按钮绑定单击事件 -->
<script type="text/javascript">
function delect_contentList(){
	var rows = $('#content_dg').datagrid('getChecked');
	if(rows.length != 1 ){
		$.messager.alert('提示','请选择一篇需要彻底删除的文章！删除后不可恢复！','info');
	}else{
		$.messager.confirm('提示', '确定删除所选中的文章?删除后不可恢复！', function(r){
			if(r){
				 var contentId = rows[0].contentId;
	            $.ajax({
	                url:"${pageContext.request.contextPath}/conmgr/deleteContent.do",
	                type:"post",
	                data:{contentId:contentId},
	                dataType:"json",
	                success:function(data){
	                	$.messager.alert('提示',data.message,'info');
	                	$('#content_dg').datagrid('reload'); 
	                },
	                error:function(){
	                	$.messager.alert('提示','删除失败!','info');
	                }
	           })
			}
		});
	}
}
</script>

