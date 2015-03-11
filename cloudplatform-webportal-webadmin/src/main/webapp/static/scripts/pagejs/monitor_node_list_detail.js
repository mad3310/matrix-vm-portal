$(function(){
	//隐藏搜索框
	$('#nav-search').addClass("hidden");
	queryMonitorNodeInfo();
});

function queryMonitorNodeInfo(){
	var ip = $("#vipaddr").val();
	getLoading();
	$.ajax({ 
		cache:false,
		type : "get",
		url :"/monitor/" + ip + "/node/status",
		//url:"/static/scripts/pagejs/nodedata.json",
		dataType : "json", 
		success : function(data) {
			removeLoading();
			if(error(data)) return;
			var monitorNodeInfo = data.data.response;
			if( monitorNodeInfo != null){
				var logWarning = monitorNodeInfo.node.log_warning;
				var logHealth = monitorNodeInfo.node.log_health;
				var logError = monitorNodeInfo.node.log_error;
				var started = monitorNodeInfo.node.started;

				/*添加表格数据	*/
				dataAppend(logWarning,$(".logWarning"),$("#logWarnFailNum"));				
				dataAppend(logHealth,$(".logHealth"),$("#logHealthFailNum"));				
				dataAppend(logError,$(".logError"),$("#logErrFailNum"));
				dataAppend(started,$(".nodeStart"),$("#startFailNum"));
				
			}else{
				$("#node_detail_table").html("<tr><td>没有查询到数据信息</td></tr>")
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