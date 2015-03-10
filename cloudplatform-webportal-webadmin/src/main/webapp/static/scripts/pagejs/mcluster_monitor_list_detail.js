$(function(){
	//隐藏搜索框
	$('#nav-search').addClass("hidden");

	queryMonitorClusterInfo();
	
});

function queryMonitorClusterInfo(){
	var ip = $("#vipaddr").val();
	getLoading();
	$.ajax({ 
		cache:false,
		type : "get",
		url :"/monitor/" + ip + "/mcluster/status",
		dataType : "json", 
		success : function(data) {
			removeLoading();
			if(error(data)) return;
			if( data.data.response != null){
				var monitorClusterInfo = data.data.response;
				
				var td1 = $("<td>"
						+ "<span>" + monitorClusterInfo.node.node_size.message + "</span>"
						+"</td>");
				var td2 = $("<td>"
						+ monitorClusterInfo.node.node_size.lost_ip
						+"</td>");
				var td3 = $("<td>"
						+ monitorClusterInfo.node.node_size.alarm
						+"</td>");
				var td4 = $("<td>"
						+ monitorClusterInfo.cluster.cluster_available.message
						+"</td>");
				var td5 = $("<td>"
						+ monitorClusterInfo.cluster.cluster_available.alarm
						+"</td>");
				var trc = $("<tr></tr>");
				var trn = $("<tr></tr>");
				trc.append(td1).append(td2).append(td3);
				trn.append(td4).append(td5);
				$("#cluster_detail_table").append(trc);
				$("#node_detail_table").append(trn);	
			}
		},
		error : function(XMLHttpRequest,textStatus, errorThrown) {
			$.gritter.add({
				title: '警告',
				text: errorThrown,
				sticky: false,
				time: '5',
				class_name: 'gritter-warning'
			});
			return false;
		}
	});
}

