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
		//url:"/static/scripts/pagejs/clusterdata.json",
		dataType : "json", 
		success : function(data) {
			removeLoading();
			if(error(data)) return;
			var monitorClusterInfo = data.data.response;
			if( monitorClusterInfo != null){
				var nodeSize = monitorClusterInfo.node.node_size;
				var clusterAvail = monitorClusterInfo.cluster.cluster_available;
				dataAppend(nodeSize,$(".node"),$("#nodeFailNum"));				
				dataAppend(clusterAvail,$(".cluster"),$("#clusterFailNum"));
			}else{
				$("#cluster_detail_table").html("<tr><td>没有查询到数据信息</td></tr>");
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
