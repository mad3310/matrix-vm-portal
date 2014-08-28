<%@ page language="java" pageEncoding="UTF-8"%>
<%@include file="/common/common.jsp"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>数据库详情</title>

</head>
<body>
	<div class="container">
		<%@include file="/common/header.jsp"%>
		<div id="wrap">
			<div class="row">
				<div class="col-md-12">
					<h3 class="text-left">Mcluster详情</h3>
				</div>
				<hr
					style="FILTER: alpha(opacity = 0, finishopacity = 100, style = 1)"
					width="100%" color=#987cb9 SIZE=3></hr>
			</div>

			<div class="row clearfix">
				<div class="col-md-3 column">
					<h2>通告：</h2>
					<p>关于数据库使用的通知、帮助和注意事项。</p>
					<p>
						<a class="btn" href="#">查看详细使用教程 »</a>
					</p>
				</div>
				<div class="col-md-9 column">
					<div class="col-sm-10">
					<button id="goBack" name="goBack" type="button" class="btn btn-default">返回</button>
					
					<table class="table table-bordered" id="Mcluster_detail_table" name="Mcluster_detail_table">
						<caption>container</caption>
						<tr><td>名称</td><td>node1</td></tr>
						<tr><td>ip</td><td>192.168.10.11</td></tr>
						<tr><td>网关</td><td>192.168.10.1</td></tr>
						<tr><td>掩码</td><td>24</td></tr>
						<tr><td>挂载路径</td><td>/opt;/mnt/db:/srv/db</td></tr>
						<tr><td>zookeeper</td><td>1</td></tr>
					</table>
					<table class="table table-bordered" id="Mcluster_detail_table" name="Mcluster_detail_table">
						<caption>container</caption>
						<tr><td>名称</td><td>node1</td></tr>
						<tr><td>ip</td><td>192.168.10.11</td></tr>
						<tr><td>网关</td><td>192.168.10.1</td></tr>
						<tr><td>掩码</td><td>24</td></tr>
						<tr><td>挂载路径</td><td>/opt;/mnt/db:/srv/db</td></tr>
						<tr><td>zookeeper</td><td>1</td></tr>
					</table>
					<table class="table table-bordered" id="Mcluster_detail_table" name="Mcluster_detail_table">
						<caption>container</caption>
						<tr><td>名称</td><td>node1</td></tr>
						<tr><td>ip</td><td>192.168.10.11</td></tr>
						<tr><td>网关</td><td>192.168.10.1</td></tr>
						<tr><td>掩码</td><td>24</td></tr>
						<tr><td>挂载路径</td><td>/opt;/mnt/db:/srv/db</td></tr>
						<tr><td>zookeeper</td><td>1</td></tr>
					</table>
					<table class="table table-bordered" id="Mcluster_detail_table" name="Mcluster_detail_table">
						<caption>container</caption>
						<tr><td>名称</td><td>node1</td></tr>
						<tr><td>ip</td><td>192.168.10.11</td></tr>
						<tr><td>网关</td><td>192.168.10.1</td></tr>
						<tr><td>掩码</td><td>24</td></tr>
						<tr><td>挂载路径</td><td>/opt;/mnt/db:/srv/db</td></tr>
						<tr><td>zookeeper</td><td>1</td></tr>
					</table>
<!-- 					<div class="col-sm-10">
						<button id="db_apply_modify" type="submit" class="btn btn-default">修改</button>
					</div> -->
					</div>
				</div>
			</div>
		</div>
		<%@include file="/common/footer.jsp"%>
	</div>
</body>
<script type="text/javascript">
$(function(){
	var dbId = request("belongDb");
	queryByDbId(dbId);
	initPage();
});
function queryByDbId(dbId) {
//	$("#db_detail_table tr").remove();
	$.ajax({ 
		type : "post",
		url : "${ctx}/db/list/mclusterInfo?belongDb="
				+dbId,
				/* + "&dbName="
				+ $("#dbName").val() */
		dataType : "json", /*这句可用可不用，没有影响*/
		contentType : "application/json; charset=utf-8",
		success : function(data) {
			
			var value = data.data;
			var mcluster_detail_table = $("#Mcluster_detail_table");
				mcluster_detail_table.append("<caption>"+container+"</caption>");
				mcluster_detail_table.append("<tr><td>名称</td><td>"+"node1"+"</td></tr>");
				mcluster_detail_table.append("<tr><td>ip</td><td>"+"192.168.10.11"+"</td></tr>");
				mcluster_detail_table.append("<tr><td>网关</td><td>"+"192.168.10.1"+"</td></tr>");
				mcluster_detail_table.append("<tr><td>掩码</td><td>"+"24"+"</td></tr>");
				mcluster_detail_table.append("<tr><td>挂载路径</td><td>"+"/opt;/mnt/db:/srv/db"+"</td></tr>");
				mcluster_detail_table.append("<tr><td>zookeeper</td><td>"+"1"+"</td></tr>");
		},
		error : function(XMLHttpRequest,textStatus, errorThrown) {
			$('#pageMessage').html("<p class=\"bg-warning\" style=\"color:red;font-size:16px;\"><strong>警告!</strong>"+errorThrown+"</p>").show().fadeOut(3000);
		}
	});
}
function request(paras)
{ 
    var url = location.href; 
    var paraString = url.substring(url.indexOf("?")+1,url.length).split("&"); 
    var paraObj = {} 
    for (i=0; j=paraString[i]; i++){ 
    paraObj[j.substring(0,j.indexOf("=")).toLowerCase()] = j.substring(j.indexOf("=")+1,j.length); 
    } 
    var returnValue = paraObj[paras.toLowerCase()]; 
    if(typeof(returnValue)=="undefined"){ 
    return ""; 
    }else{ 
    return returnValue; 
    } 
}

function translateStatus(status){
	if(status = 1)
	{
		return "是";
	}else{
		return "否";
	}
}

function initPage(){
	$("#goBack").click(function() {
		location.href = "${ctx}/mcluster/mgrList";
	});
}
</script>
</html>
