<%@ page import="java.util.Enumeration"%>
<%@ page language="java"  contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jstl/core"  prefix="c"%>
<body>
<div>
<table style="width: 500px" align="center">
	<tr>
	<td valign="top">
		<div class="easyui-panel" title="站点"  align="center" style="width: 150px; height: 400px;">
		<ul style="width: 100%"  id="conpubtest_site"></ul>
		</div>
	</td>
	<td>
		<div  class="easyui-panel" title="频道" align="center" style="width: 150px; height: 400px;">
		<ul style="width: 100%" id="conpubtest_channel" class="easyui-datalist"></ul>
		</div>
	</td>
	<td>
		<div  class="easyui-panel" title="栏目" align="center" style="width: 150px; height: 400px;">
		<ul  style="width:100%" id="conpubtest_column"class="easyui-datalist"></ul>
		</div>
	</td>
	<td>
		<div title="文章列表" align="center" style="width: 350px; height: 400px;">
		<ul  style="width:100%" id="conpubtest_contenthaspass">
			
		</ul>
		</div>
	</td>
	</tr>
</table>
</div>
<script type="text/javascript">
//加载站点
$('#conpubtest_site').datalist({
    url: '${pageContext.request.contextPath}/sitemgr/findAllSite.do',
	width:'100%',
	height:'100%',
    lines: true,
	valueField:'siteId',
	textField:'siteName',
	onSelect: function (index,record) {//index参数不能省略
		var siteId = record.siteId;
		addChannleForconpubtest(siteId);
		addColumnForconpubtest(0);
	}
});
//加载频道
function addChannleForconpubtest(siteId) {
	$('#conpubtest_channel').datalist({//频道
	    url: '${pageContext.request.contextPath}/chmgr/getChannelByApprPass.do?siteId='+siteId,
		width:'100%',
		height:'100%',
	    lines: true,
		valueField:'channelId',
		textField:'channelName',
		onSelect: function (index,record) {
			var channelId = record.channelId;
			addColumnForconpubtest(channelId);
		}
	});
}
//加载栏目
function addColumnForconpubtest(channelId) {
	$('#conpubtest_column').datalist({//栏目
		url :'${pageContext.request.contextPath}/itemmgr/getColumnByApprPass.do?channelId='+channelId,
		width:'100%',
		height:'100%',
	    lines: true,
		valueField:'columnId',
		textField:'columnName',
		onSelect: function (index,record) {
			var columnId = record.columnId;
			addConForconpubtest(columnId);
		}
	});
}
//加载内容
function addConForconpubtest(columnId){
	var siterows = $("#conpubtest_site").datalist('getChecked');
	var channelrows = $("#conpubtest_channel").datalist('getChecked');
	var siteId =siterows[0].siteId;var channelId =channelrows[0].channelId;
	$("#conpubtest_contenthaspass li").remove();
 	$.ajax({
		url:'${pageContext.request.contextPath}/conpubtest/getcontentForConpubtestController.do',
		type:'post',
		data:{"siteId":siteId,"channelId":channelId,"columnId":columnId},
		dataType:'json',
		success:function(data){
			if(data.length !=0){
				var str='';
				for(var i=0;i<data.length;i++){
					str+='<li><a href="'+data[i].contentPubUrl+'">'+data[i].contentTitle+'</a></li>';
				}
				$("#conpubtest_contenthaspass").append(str);
			}
		}
	});
}
</script>
</body>