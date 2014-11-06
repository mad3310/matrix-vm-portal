<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="page-content-area">
	<div id="page-header-id" class="page-header">
		<h1> 
			<a href="${ctx}/list/mcluster/monitor">container集群监控列表</a>
			<small id="header_mcluster_name"> 
				<i class="ace-icon fa fa-angle-double-right"></i> 
			</small>
		</h1>
	</div>
	<!-- /.page-header -->
	<input class="hidden" value="${ip}" name="ip" id="ip" type="text" />
	<div class="row">
		<div class="widget-box transparent ui-sortable-handle">
			<div class="widget-header">
				<div class="widget-toolbar no-border pull-left">
					<ul id="db-detail-tabs" class="nav nav-tabs" id="myTab2">
						<li class="active">
							<a data-toggle="tab" href="#cluster-info" onclick="queryMonitorDetail()">cluster信息</a>
						</li>
						<li class="">
							<a data-toggle="tab" href="#node-info" onclick="queryMonitorDetail()">node信息</a>
						</li>
						<li class="">
							<a data-toggle="tab" href="#db-info" onclick="queryMonitorDetail()">db信息</a>
						</li>
					</ul>
				</div>
			</div>
			<div class="widget-body">
				<div class="widget-main padding-12 no-padding-left no-padding-right">
					<div class="tab-content padding-4">
						<div id="cluster-info" class="tab-pane  active">
							<div id="db-detail-table" class="col-xs-10">
								<table class="table table-bordered" id="db_detail_table">
									<thead>
										<tr>
											<th></th>
											<th width="30%">message</th>
											<th width="40%">error_record</th>
											<th width="20%">ctime</th>
											<th width="10%">alarm</th>
										</tr>
									</thead>
									<tbody id="tby-cluster-info"></tbody>
								</table>
							</div>
						</div>
						<div id="node-info" class="tab-pane">
							<div id="db-detail-table" class="col-xs-10">
								<table class="table table-bordered" id="db_detail_table">
									<thead>
										<tr>
											<th></th>
											<th width="30%">message</th>
											<th width="40%">error_record</th>
											<th width="20%">ctime</th>
											<th width="10%">alarm</th>
										</tr>
									</thead>
									<tbody id="tby-node-info"></tbody>
								</table>
							</div>
						</div>
						<div id="db-info" class="tab-pane">
							<div id="db-detail-table" class="col-xs-10">
								<table class="table table-bordered" id="db_detail_table">
									<thead>
										<tr>
											<th></th>
											<th width="30%">message</th>
											<th width="40%">error_record</th>
											<th width="20%">ctime</th>
											<th width="10%">alarm</th>
										</tr>
									</thead>
									<tbody id="tby-db-info"></tbody>
								</table>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript">
$(function(){
	initPage();
})
function addDataToTable(data,tby){
	function createTd(tdData){
		if(tdData != null && tdData != ""){
			var td = $("<td>"
						+ tdData
						+ "</td>");
		}else{
			var td = $("<td>"
			            + "-"
			            + "</td>");

		}
			return td;
	};
	for(var i=0,len = data.length; i < len; i++ ){
		var td1 = createTd(data[i].monitorName);
		var td2 = createTd(data[i].message);
		var td3 = createTd(data[i].errorRecord);
		var td4 = createTd(data[i].ctime);
		var td5 = createTd(data[i].alarm);
		if(data[i].alarm == "nothing"){
			var tr = $("<tr></tr>");
		}else{
			var tr = $("<tr class=\"danger\"></tr>");
		}
		tr.append(td1).append(td2).append(td3).append(td4).append(td5);
		tr.appendTo(tby);
	}
}


function queryMonitorDetail(){
	$.ajax({
		type : "get",
		url : "${ctx}/monitor/"+$("#ip").val()+"/mcluster",
		dataType : "json",
		contentType : "application/json; charset=utf-8",
		success : function(data) {
			error(data);
			var array = data.data;
			if($('#header_mcluster_name').html().indexOf(array[0].mclusterName) < 0){
				$("#header_mcluster_name").append(array[0].mclusterName);
			}
			$("#tby-cluster-info tr").remove();
			$("#tby-node-info tr").remove();
			$("#tby-db-info tr").remove();
			addDataToTable(array[0].clMoList,$("#tby-cluster-info"));
			addDataToTable(array[0].nodeMoList,$("#tby-node-info"));
			addDataToTable(array[0].dbMoList,$("#tby-db-info"));
		}
	});
}
function initPage(){
	$('#nav-search').addClass("hidden");
 	queryMonitorDetail();
}
</script>
