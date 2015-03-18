 $(function(){
	page_init();
	
	$("#monitorClusterSearch").click(function(){
		queryMclusterMonitor();
	});
	$("#monitorClusterClear").click(function(){
		var clearList = ["monitorContainer","monitorPhyM","VipAddress"]
		clearSearch(clearList);
	});
	
	enterKeydown($(".page-header > .input-group input"),queryByPage);
});	
function queryMclusterMonitor() {
	var mclusterName = $("#monitorContainer").val()?$("#monitorContainer").val():'';
	var hclusterName = $("#monitorPhyM").val()?$("#monitorPhyM").val():'';
	var vip = $("#VipAddress").val()?$("#VipAddress").val():'';
	var queryCondition = {
			'mclusterName':mclusterName,
			'hclusterName':hclusterName,
			'ipAddr':vip
		}
	$("#tby tr").remove();
	$.ajax({ 
		cache:false,
		type : "get",
		//url : "/monitor/mcluster/list",
		url : queryUrlBuilder("/monitor/mcluster/list/",queryCondition),
		dataType : "json",
		contentType : "application/json; charset=utf-8",
		success : function(data) {
			if(error(data)) return;
			var array = data.data;
			var tby = $("#tby");
			
			for (var i = 0, len = array.length; i < len; i++) {
				if(array[i] == null) continue;
				var mclusterName = '';
        		if(array[i].mcluster != undefined && array[i].mcluster != null) {
        			mclusterName = array[i].mcluster.mclusterName;
        		}
				var td1 = $("<td>"
						+ "<a class=\"link\" target=\"_blank\" href=\"/detail/mcluster/" + array[i].mclusterId+"\">"+ mclusterName +"</a>"
						+ "</td>");
				var td2 = $("<td name=\"vip\">"
						+ array[i].ipAddr
						+ "</td>");

			if(array[i].hcluster.hclusterNameAlias){
					var td3 = $("<td>"
							+ "<a class=\"link\" target=\"_blank\" href=\"/detail/hcluster/" + array[i].host.hclusterId+"\">"+array[i].hcluster.hclusterNameAlias+"</a>"
 							+ "</td>");
				}else{
					var td3 = $("<td>"
 							+ "-"
							+ "</td>");
				}
				var td4 = $("<td name=\"mclusterStatus\">"
							+"<a><i class=\"ace-icon fa fa-spinner fa-spin  bigger-120\"/>获取数据中...</a>"
							+ "</td>");
				var td5 = $("<td>"						
						+ "<a class=\"link\" href=\"/detail/mcluster/monitor/list/" + array[i].ipAddr + "/1\">查看详情</a>"
						//+ "<a href=\"/monitor/"+array[i].ipAddr+"/mcluster/status\" target=\"_blank\"></a>"
						+ "</td>");
				if(array[i].status == 0 ||array[i].status == 5||array[i].status == 13){
					var tr = $("<tr class=\"warning\"></tr>");
				}else if(array[i].status == 3 ||array[i].status == 4||array[i].status == 14){
					var tr = $("<tr class=\"default-danger\"></tr>");
				}else{
					var tr = $("<tr></tr>");
				}
				tr.append(td1).append(td2).append(td3).append(td4).append(td5);
				tr.appendTo(tby);
			}
			var tr1 = $("<tr class=\"default-danger normalTag\"></tr>");
			var tr2 = $("<tr class=\"default-danger lightDangerTag\"></tr>");
			var tr3 = $("<tr class=\"default-danger seriousTag\"></tr>");
			var tr4 = $("<tr class=\"default-danger disableClusterTag\"></tr>");
			var tr5 = $("<tr class=\"default-danger timeoutClusterTag\"></tr>");
			tby.prepend(tr1).prepend(tr2).prepend(tr3).prepend(tr4).prepend(tr5);
			updateMclusterStatus();
		}
	});
}
function getMclusterStatus(ip,obj) {
	$.ajax({ 
		cache:false,
		type : "get",
		url : "/monitor/"+ip+"/mcluster/status",
		dataType : "json", 
		contentType : "application/json; charset=utf-8",
		success : function(data) {
			if(error(data)) return;
			var result = data.data.result;
			if(result == "0"){
				$(obj).removeClass();
				$(obj).find('[name="mclusterStatus"]').html("<a>正常</a>");
				//$(obj).parent().find(".normalTag").after($(obj));
			}else if(result == "1"){
				$(obj).removeClass();
				$(obj).find('[name="mclusterStatus"]').html("<a>危险</a>");
				$(obj).addClass("default-danger lightDanger");
				$(obj).parent().find(".lightDangerTag").after($(obj));
			}else if(result == "2"){
				$(obj).removeClass();
				$(obj).find('[name="mclusterStatus"]').html("<a>严重危险</a>");
				$(obj).addClass("default-danger serious");
				$(obj).parent().find(".seriousTag").after($(obj));
			}else if(result == "3"){
				$(obj).removeClass();
				$(obj).find('[name="mclusterStatus"]').html("<a>集群不可用/a>");
				$(obj).addClass("default-danger disableCluster");
				$(obj).parent().find(".disableClusterTag").after($(obj));
			}else if(result == "4"){
				$(obj).removeClass();
				$(obj).find('[name="mclusterStatus"]').html("<a>获取数据超时</a>");
				$(obj).addClass("default-danger timeoutCluster");
				$(obj).parent().find(".timeoutClusterTag").after($(obj));
			}
		}	
	});
}
function updateMclusterStatus(){
	$("#tby tr").each(function(){
		var ip = $(this).find('[name="vip"]').html();
		if(ip != null){
			var status = getMclusterStatus(ip,$(this));
		}
	});
}
function page_init(){
	$('#nav-search').addClass("hidden");
	queryMclusterMonitor();
	setInterval(function() {
		updateMclusterStatus();
		},60000);
}
