 $(function(){
	//初始化
	page_init();
});	
function queryMclusterMonitor() {
	$("#tby tr").remove();
	$.ajax({ 
		cache:false,
		type : "get",
		url : "/monitor/mcluster/list",
		dataType : "json", /*这句可用可不用，没有影响*/
		contentType : "application/json; charset=utf-8",
		success : function(data) {
			if(error(data)) return;
			var array = data.data;
			var tby = $("#tby");
			
			for (var i = 0, len = array.length; i < len; i++) {
				var mclusterName = '';
        		if(array[i].mcluster) {
        			mclusterName = array[i].mcluster.mclusterName;
        		}
        		var td0 = "<input name=\"mclusterId\" type=\"hidden\" value=\""+array[i].mclusterId+"\"/>"
				var td1 = $("<td>"
							+ mclusterName
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
				var td5 = $("<td>"
						+ "<span><a href=\"/monitor/"+array[i].ipAddr+"/db/status\" target=\"_blank\">查看详情</a></span>"
						+ "<span class=\"text-explode\">|</span>" 
						+ "<a data-toggle=\"tooltip\" data-placement=\"top\" title=\"拉起\" style=\"cursor:pointer\" onclick=\"restartMclusterServer(this)\">"
							+"<i class=\"ace-icon fa fa-repeat bigger-130\"></i>"
						+"</a>"
					+ "</td>");
				if(array[i].status == 0 ||array[i].status == 5||array[i].status == 13){
					var tr = $("<tr class=\"warning\"></tr>");
				}else if(array[i].status == 3 ||array[i].status == 4||array[i].status == 14){
					var tr = $("<tr class=\"default-danger\"></tr>");
				}else{
					var tr = $("<tr></tr>");
				}
				
				tr.append(td0).append(td1).append(td2).append(td3).append(td4).append(td5);
				tr.appendTo(tby);
				
			}//循环json中的数据 
			updateMclusterStatus();//查询集群状态
			$('[data-toggle = "tooltip"]').tooltip();
		}
	});
}
function getMclusterStatus(ip,obj) {
	$.ajax({ 
		cache:false,
		type : "get",
		url : "/monitor/"+ip+"/db/status",
		dataType : "json", 
		contentType : "application/json; charset=utf-8",
		success : function(data) {
			if(error(data)) return;
			var result = data.data.result;
			if(result == "0"){
				$(obj).removeClass();
				$(obj).find('[name="mclusterStatus"]').html("<a>正常</a>");
			}else if(result == "1"){
				$(obj).removeClass();
				$(obj).find('[name="mclusterStatus"]').html("<a>危险</a>");
				$(obj).addClass("default-danger");		
			}else if(result == "2"){
				$(obj).removeClass();
				$(obj).find('[name="mclusterStatus"]').html("<a>严重危险</a>");
				$(obj).addClass("default-danger");		
			}else if(result == "3"){
				$(obj).removeClass();
				$(obj).find('[name="mclusterStatus"]').html("<a>集群不可用</a>");
				$(obj).addClass("default-danger");		
			}else if(result == "4"){
				$(obj).removeClass();
				$(obj).find('[name="mclusterStatus"]').html("<a>获取数据超时</a>");
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

function restartMclusterServer(obj){
	$.ajax({ 
		cache:false,
		type : "post",
		url : "/mcluster/restart",
		dataType : "json", 
		data : {
			mclusterId:$(obj).closest("tr").find('[name = "mclusterId"]').val()
		},
		success : function(data) {
			if(error(data)) return;
			success($(obj).closest("tr").find('td').first().html()+"拉起命令已发出，请等待...",3000);
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
