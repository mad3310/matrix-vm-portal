<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="page-content-area">
	<div id="page-header-id" class="page-header">
		<h1> 
			<a href="${ctx}/list/mcluster">Container集群列表</a>
			<small id="headerContainerName"> 
				<i class="ace-icon fa fa-angle-double-right"></i> 
			</small>
		</h1>
	</div>
	<input class="hidden" value="${mclusterId}" name="mclusterId" id="mclusterId" type="text" />
	<!-- /.page-header -->
	<div class="row">
		<div  style="margin-top: 10px;">
			<table class="table table-bordered" id="Mcluster_detail_table">
				<thead>
			      <tr style="background-image:none;background-color:#307ECC;color:#FFFFFF;">
			         <th>container名称</th>
			         <th>类型</th>
			         <th>宿主机ip</th>
			         <th>ip</th>
			         <th>挂载路径</th>
			         <th>zookeepId</th>
			         <th>状态</th>
			         <th>操作</th>
			      </tr>
			   </thead>
			   <tbody id="tby">
				</tbody>
			</table>
		</div>
	</div>
	<div id="dialog-confirm" class="hide">
		<div id="dialog-confirm-content" class="alert alert-info bigger-110">
		</div>
		<div class="space-6"></div>
		<p id="dialog-confirm-question" class="bigger-110 bolder center grey">
		</p>
	</div>
</div>
<script type="text/javascript">
$(function(){
	//隐藏搜索框
	$('#nav-search').addClass("hidden");
	queryContainer();
})
function queryContainer(){
	$("#tby tr").remove();
	$.ajax({ 
		type : "get",
		url : "${ctx}/container/"+$("#mclusterId").val(),
		dataType : "json", 
		success : function(data) {
			if(error(data)) return;
			var array = data.data;
			var tby = $("#tby");
 			$("#headerContainerName").append(array[0].mcluster.mclusterName);
			for (var i = 0, len = array.length; i < len; i++) {
				var td0 = $("<input name=\"container_id\" value= \""+array[i].id+"\" type=\"hidden\"/>");
				var td1 = $("<td>"
					    + array[i].containerName
				        + "</td>");
				var	td2 = $("<td>"
						+ array[i].type
						+ "</td>");
				var	td3 = $("<td>"
						+ array[i].hostIp
						+ "</td>");
				var	td4 = $("<td>"
						+ array[i].ipAddr
						+ "</td>");
				if(array[i].mountDir != null){
					var	td5 = $("<td>"
							+ array[i].mountDir
							+ "</td>");
				}else{
					var	td5 = $("<td>"
							+ '-'
							+ "</td>");
				}
				if(array[i].zookeeperId != null){
					var	td6 = $("<td>"
							+ array[i].zookeeperId
							+ "</td>");
				}else{
					var	td6 = $("<td>"
							+ '-'
							+ "</td>");
				}
				var	td7 = $("<td>"
						+ translateStatus(array[i].status)
						+ "</td>");
				var td8 = $("<td>"
						+"<div class=\"hidden-sm hidden-xs action-buttons\">"
						+"<a class=\"green\" href=\"#\" onclick=\"startContainer(this)\" data-toggle=\"modal\" data-target=\"#\">"
						+"<i class=\"ace-icon fa fa-play-circle-o bigger-130\"></i>"
						+"</a>"
						+"<a class=\"blue\" href=\"#\" onclick=\"stopContainer(this)\" data-toggle=\"modal\" data-target=\"#\">"
							+"<i class=\"ace-icon fa fa-power-off bigger-120\"></i>"
						+"</a>"
						+"</div>"
						+ "</td>"
				);
				var tr = $("<tr></tr>");;				
				tr.append(td0).append(td1).append(td2).append(td3).append(td4).append(td5).append(td6).append(td7).append(td8);
				tr.appendTo(tby);
			}
		}
	});
}

function startContainer(obj){
	var tr = $(obj).parents("tr").html();
	if (tr.indexOf("已停止") < 0){
		warn("当前状态无法执行启动操作!",3000);
		return 0;
	}
	function startCmd(){
		var containerId =$(obj).parents("tr").find('[name="container_id"]').val();
		$.ajax({
			url:'${ctx}/container/start',
			type:'post',
			data:{containerId : containerId},
			success:function(data){
				if(error(data)) return;
				queryContainer();
			}
		});
	}
	confirmframe("启动container","启动container大概需要几分钟时间!","请耐心等待...",startCmd);
}
function stopContainer(obj){
	var tr = $(obj).parents("tr").html();
	if (tr.indexOf("运行中") < 0 && tr.indexOf("异常") < 0){
		warn("当前状态无法执行关闭操作!",3000);
		return 0;
	}
	function stopCmd(){
		var containerId =$(obj).parents("tr").find('[name="container_id"]').val();
		$.ajax({
			url:'${ctx}/container/stop',
			type:'post',
			data:{containerId : containerId},
			success:function(data){
				if(error(data)) return;
				queryContainer();
			}
		});
	}
	confirmframe("关闭container","关闭container将不能提供服务,再次启动需要十几分钟!","您确定要关闭?",stopCmd);
}
</script>
