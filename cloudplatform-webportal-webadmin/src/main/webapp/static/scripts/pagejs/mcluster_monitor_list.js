 $(function(){
	//初始化
	page_init();
});	
function queryMclusterMonitor() {
	$("#tby tr").remove();
	$.ajax({ 
		type : "get",
		url : "/monitor/mcluster/list",
		dataType : "json", /*这句可用可不用，没有影响*/
		contentType : "application/json; charset=utf-8",
		success : function(data) {
			if(error(data)) return;
			var array = data.data;
			var tby = $("#tby");
			
			for (var i = 0, len = array.length; i < len; i++) {
				var td1 = $("<td>"
							+ "<a href=\"/detail/mcluster/" +array[i].ipAddr+"/monitor\">"+array[i].mcluster.mclusterName+"</a>"
							+ "</td>");
				var td2 = $("<td name=\"vip\">"
							+ array[i].ipAddr
							+ "</td>");

			if(array[i].hcluster.hclusterNameAlias){
					var td3 = $("<td>"
 							+ array[i].hcluster.hclusterNameAlias
							+ "</td>");
				}else{
					var td3 = $("<td>"
 							+ "-"
							+ "</td>");
				}
				var td4 = $("<td name=\"mclusterStatus\">"
							+"<a><i class=\"ace-icon fa fa-spinner fa-spin  bigger-120\"/>数据抓取中...</a>"
							+ "</td>");

				if(array[i].status == 0 ||array[i].status == 5||array[i].status == 13){
					var tr = $("<tr class=\"warning\"></tr>");
				}else if(array[i].status == 3 ||array[i].status == 4||array[i].status == 14){
					var tr = $("<tr class=\"default-danger\"></tr>");
				}else{
					var tr = $("<tr></tr>");
				}
				
				tr.append(td1).append(td2).append(td3).append(td4);
				tr.appendTo(tby);
				
			}//循环json中的数据 
			updateMclusterStatus();//查询集群状态
		}
	});
}
function getMclusterStatus(ip,obj) {
	$.ajax({ 
		type : "get",
		url : "/monitor/"+ip+"/mcluster/status",
		dataType : "json", 
		contentType : "application/json; charset=utf-8",
		success : function(data) {
			if(error(data)) return;
			var status = data.data[0].status;
			if(status == "6"){
				$(obj).removeClass();
				$(obj).find('[name="mclusterStatus"]').html(translateStatus(status));
			}else if(status == "5"){
				$(obj).removeClass();
				$(obj).find('[name="mclusterStatus"]').html("<font color=\"red\">集群不可用</font>");
				$(obj).addClass("default-danger");		
			}else if(status == "13"){
				$(obj).removeClass();
				$(obj).find('[name="mclusterStatus"]').html(translateStatus(status));
				$(obj).addClass("warning");		
			}else if(status == "14"){
				$(obj).removeClass();
				$(obj).find('[name="mclusterStatus"]').html("<a>"+translateStatus(status)+"</a>");
				$(obj).addClass("default-danger");		
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
