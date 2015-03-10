$(function(){
	//隐藏搜索框
	$('#nav-search').addClass("hidden");
	queryMonitorDbInfo();
});

function queryMonitorDbInfo(){
	var ip = $("#vipaddr").val();
	console.log(ip);
	getLoading();
	$.ajax({ 
		cache:false,
		type : "get",
		url :"/monitor/" + ip + "/db/status",
		dataType : "json", 
		success : function(data) {
			removeLoading();
			if(error(data)) return;
			if( data.data.response != null){
				var monitorDbInfo = data.data.response;
				
				var td1 = $("<td>"
						+ "<span> message:" + monitorDbInfo.dbuser.message + "</span>"
						+ "<span> alarm:" + monitorDbInfo.dbuser.alarm + "</span>"
						+ "<span> ctime:" + monitorDbInfo.dbuser.ctime + "</span>"
						+ "<span> error_record:" + monitorDbInfo.dbuser.error_record + "</span>"
						+"</td>");
				var td2 = $("<td>"
						+ monitorDbInfo.node.node_size.lost_ip
						+"</td>");
				var td3 = $("<td>"
						+ monitorDbInfo.node.node_size.alarm
						+"</td>");
				var td4 = $("<td>"
						+ monitorDbInfo.cluster.cluster_available.message
						+"</td>");
				var td5 = $("<td>"
						+ monitorDbInfo.cluster.cluster_available.alarm
						+"</td>");
				var tr = $("<tr></tr>");
				
				tr.append(td1).append(td2).append(td3).append(tr4).append(tr5);
				$("#db_detail_table").append(tr);
				
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

