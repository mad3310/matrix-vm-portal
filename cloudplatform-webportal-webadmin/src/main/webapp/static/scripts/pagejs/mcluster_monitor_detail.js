$(function(){
	initPage();
})
function addDataToTable(data,tby,type){
	function createTd(tdData){
		if(tdData != ""){
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
		var td5 = createTd(data[i].alarm);
		if(data[i].alarm == "nothing"){
			var tr = $("<tr></tr>");
		}else{
			var tr = $("<tr class=\"danger\"></tr>");
		}
		if(type != "cluster"){
			var	td3 = createTd(data[i].errorRecord);
			var	td4 = createTd(data[i].ctime);
			tr.append(td1).append(td2).append(td3).append(td4).append(td5);
		}else{
			tr.append(td1).append(td2).append(td5);
		}
		tr.appendTo(tby);
	}
}

function queryNodeMonitorDetail(){
	getLoading();
	$.ajax({
		cache:false,
		type : "get",
		url : "/monitor/"+$("#ip").val()+"/mcluster/nodeAndDb",
		dataType : "json",
		contentType : "application/json; charset=utf-8",
		success : function(data) {
			removeLoading();
			if(error(data)) return;
			var array = data.data;
			if($('#header_mcluster_name').html().indexOf(array.mclusterName) < 0){
				$("#header_mcluster_name").append(array.mclusterName);
			}

			$("#tby-node-info tr").remove();
			$("#tby-db-info tr").remove();
			addDataToTable(array.nodeMoList,$("#tby-node-info"),"node");
			addDataToTable(array.dbMoList,$("#tby-db-info"),"db");
		}
	});
}

function queryClusterMonitorDetail(){
	getLoading();
	$.ajax({
		cache:false,
		type : "get",
		url : "/monitor/"+$("#ip").val()+"/mcluster/cluster",
		dataType : "json",
		contentType : "application/json; charset=utf-8",
		success : function(data) {
			removeLoading();
			if(error(data)) return;
			var array = data.data;

			$("#tby-cluster-info tr").remove();
			addDataToTable(array.clMoList,$("#tby-cluster-info"),"cluster");
			addDataToTable(array.nodeMoList,$("#tby-cluster-info"),"cluster");
		}
	});
}
function initPage(){
	$('#nav-search').addClass("hidden");
 	queryNodeMonitorDetail();
 	queryClusterMonitorDetail();
}
