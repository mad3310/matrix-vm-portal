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
			<div class="row">
				<div class="col-md-12">
					<h3 class="text-left">DB详情</h3>
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
					<table class="table table-bordered" id="db_detail_table" name="db_detail_table">
					<caption>数据库信息</caption>
					</table>
					<table class="table table-bordered" id="db_apply_table" name="db_apply_table">
					<caption>申请信息</caption>
					</table>
					</div>
				</div>
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
		url : "${ctx}/db/list/dbApplyInfo?belongDb="
				+dbId,
				/* + "&dbName="
				+ $("#dbName").val() */
		dataType : "json", /*这句可用可不用，没有影响*/
		contentType : "application/json; charset=utf-8",
		success : function(data) {
			
			var value = data.data;
			var apply_detail = $("#db_detail_table");
			var apply_table = $("#db_apply_table");
			
			apply_detail.append("<tr><td>数据库名</td><td>"+"hello world"+"</td></tr>");
			apply_detail.append("<tr><td>用户名</td><td>"+"hello"+"</td></tr>");
			apply_detail.append("<tr><td>密码</td><td>"+"*************************************"+"</td></tr>");
			apply_detail.append("<tr><td>VIP</td><td>"+"182.182.182.182"+"</td></tr>");
			apply_detail.append("<tr><td>container_1_ip</td><td>"+"182.182.182.183"+"</td></tr>");
			apply_detail.append("<tr><td>container_2_ip</td><td>"+"182.182.182.184"+"</td></tr>");
			apply_detail.append("<tr><td>container_3_ip</td><td>"+"182.182.182.185"+"</td></tr>");

			apply_table.append("<tr><td>项目名称</td><td>"+value.applyName+"</td></tr>");
			apply_table.append("<tr><td>业务描述</td><td>"+value.descn+"</td></tr>");
			apply_table.append("<tr><td>链接类型</td><td>"+value.linkType+"</td></tr>");
			apply_table.append("<tr><td>最大访问量</td><td>"+value.maxConcurrency+"/s</td></tr>");
			apply_table.append("<tr><td>读写比例</td><td>"+value.readWriterRate+"</td></tr>");
			apply_table.append("<tr><td>开发语言</td><td>"+value.developLanguage+"</td></tr>");
			apply_table.append("<tr><td>IP访问列表</td><td>"+value.dataLimitIpList+"</td></tr>");
			apply_table.append("<tr><td>管理IP访问列表</td><td>"+value.mgrLimitIpList+"</td></tr>");
			apply_table.append("<tr><td>数据库引擎</td><td>"+value.engineType+"</td></tr>");
			apply_table.append("<tr><td>邮件通知</td><td>"+translateStatus(value.isEmailNotice)+"</td></tr>");
			apply_table.append("<tr><td>申请时间</td><td>"+value.createTime+"</td></tr>");
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
		/* location.href = "${ctx}/mcluster/mgrList"; */
	});
}
</script>
</html>
