<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="page-content-area">
	<div id="page-header-id" class="page-header">
		<h1> 
			<a href="${ctx}/mcluster">集群列表</a>
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
			         <th>宿主机</th>
			         <th>ip</th>
			         <th>挂载路径</th>
			         <th>zookeepId</th>
			         <th>状态</th>
			      </tr>
			   </thead>
			   <tbody id="tby">
				</tbody>
			</table>
		</div>
	</div>
</div>
<script type="text/javascript">
$(function(){
	//隐藏搜索框
	$('#nav-search').addClass("hidden");
	queryContainer();
})

function queryContainer(){
	$.ajax({ 
		type : "get",
		url : "${ctx}/container/"+$("#mclusterId").val(),
		dataType : "json", 
		success : function(data) {
			var array = data.data;
			var tby = $("#tby");
 			$("#headerContainerName").append(array[0].mcluster.mclusterName);
			for (var i = 0, len = array.length; i < len; i++) {
				var td1 = $("<td>"
					    + array[i].containerName
				        + "</td>");
				var	td2 = $("<td>"
						+ array[i].type
						+ "</td>");
				var	td3 = $("<td>"
						+ array[i].hostId
						+ "</td>");
				var	td4 = $("<td>"
						+ array[i].ipAddr
						+ "</td>");
				var	td5 = $("<td>"
						+ array[i].mountDir
						+ "</td>");
				var	td6 = $("<td>"
						+ array[i].zookeeperId
						+ "</td>");
				var	td7 = $("<td>"
						+ "正常"
						+ "</td>");
				var tr = $("<tr></tr>");;				
				tr.append(td1).append(td2).append(td3).append(td4).append(td5).append(td6).append(td7);
				tr.appendTo(tby);
			}
		}
	});
}
</script>
