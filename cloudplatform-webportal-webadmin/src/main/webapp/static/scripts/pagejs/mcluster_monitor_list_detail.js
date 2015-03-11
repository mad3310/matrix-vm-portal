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
		//url :"/monitor/" + ip + "/mcluster/status",
		url:"/static/scripts/pagejs/clusterdata.json",
		dataType : "json", 
		success : function(data) {
			removeLoading();
			if(error(data)) return;
			var monitorClusterInfo = data.data.response;
			var $tby = $("#cluster_detail_table");
			if( monitorClusterInfo != null){
				var nodeSize = monitorClusterInfo.node.node_size;
				var clusterAvail = monitorClusterInfo.cluster.cluster_available;
				var nodeCount = getCount(nodeSize);
				var clusterCount = getCount(clusterAvail);
				
				/*定义表格布局begin*/
				var td1 = $("<td style=\"width: 20%;\">node" 
						+ "<input type=\"text\" id=\"nodeFailNum\" class=\"hidden\" />"
						+ "</td>");
				var td2 = $("<td style=\"width: 20%;\">cluster" 
						+ "<input type=\"text\" id=\"clusterFailNum\" class=\"hidden\" />"
						+ "</td>");
				td1.attr({"rowspan":nodeCount + 1});
				td2.attr({"rowspan":clusterCount + 1});
				
				var tr1 = $("<tr class=\"node\"></tr>");
				var tr2 = $("<tr class=\"cluster\"></tr>");
				tr1.append(td1);
				tr2.append(td2);
				$tby.append(tr1).append(tr2);
				/*定义表格布局end*/
				
				/*向表格添加数据*/
				dataAppend(nodeSize,$(".node"),$("#nodeFailNum"));				
				dataAppend(clusterAvail,$(".cluster"),$("#clusterFailNum"));
			}else{
				$tby.html("<tr><td>没有查询到数据信息</td></tr>");
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
