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
		//url:"/static/scripts/pagejs/testjson/nodeNormal.json",
		dataType : "json", 
		success : function(data) {
			removeLoading();
			if(error(data)) return;
			var monitorNodeInfo = data.data.response;
			var $tby = $("#node_detail_table");
			if( monitorNodeInfo != null){
				var logWarning = monitorNodeInfo.node.log_warning;
				var logHealth = monitorNodeInfo.node.log_health;
				var logError = monitorNodeInfo.node.log_error;
				var started = monitorNodeInfo.node.started;
				
				var warnCount = getCount(logWarning);
				var healthCount = getCount(logHealth);
				var errorCount = getCount(logError);
				var startCount = getCount(started);
				/*定义表格布局*/
				var td1 = $("<td style=\"width: 20%;\">log_warning" 
						+ "<input type=\"text\" id=\"logWarnFailNum\" class=\"hidden\" />"
						+ "</td>");
				var td2 = $("<td style=\"width: 20%;\">log_health" 
						+ "<input type=\"text\" id=\"logHealthFailNum\" class=\"hidden\" />"
						+ "</td>");
				var td3 = $("<td style=\"width: 20%;\">log_error" 
						+ "<input type=\"text\" id=\"logErrFailNum\" class=\"hidden\" />"
						+ "</td>");
				var td4 = $("<td style=\"width: 20%;\">started" 
						+ "<input type=\"text\" id=\"startFailNum\" class=\"hidden\" />"
						+ "</td>");
				
				/*定义表格跨行数目*/
				td1.attr({"rowspan":warnCount + 1});
				td2.attr({"rowspan":healthCount + 1});
				td3.attr({"rowspan":errorCount + 1});
				td4.attr({"rowspan":startCount + 1});
				
				var tr1 = $("<tr class=\"logWarning\"></tr>");
				var tr2 = $("<tr class=\"logHealth\"></tr>");
				var tr3 = $("<tr class=\"logError\"></tr>");
				var tr4 = $("<tr class=\"nodeStart\"></tr>");
				tr1.append(td1);
				tr2.append(td2);
				tr3.append(td3);
				tr4.append(td4);
				
				$tby.append(tr1).append(tr2).append(tr3).append(tr4);
				/*添加表格数据	*/
				dataAppend(logWarning,tr1,"logWarnFailNum");				
				dataAppend(logHealth,tr2,"logHealthFailNum");				
				dataAppend(logError,tr3,"logErrFailNum");
				dataAppend(started,tr4,"startFailNum");
				/*定义表格布局end*/
			}else{
				$tby.html("<tr><td>没有查询到数据信息</td></tr>")
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