 $(function(){
	//初始化
	page_init();
	/*查询功能*/
	$("#monitorDbSearch").click(function(){
		queryMclusterMonitor();
	});
	$("#monitorDbClear").click(function(){
		var clearList = ["monitorContainer","monitorPhyM","VipAddress"]
		clearSearch(clearList);
	})
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
	getLoading();
	$.ajax({ 
		cache:false,
		type : "get",
		//url : "/monitor/mcluster/list",
		url : queryUrlBuilder("/monitor/mcluster/list/",queryCondition),
		dataType : "json", /*这句可用可不用，没有影响*/
		contentType : "application/json; charset=utf-8",
		success : function(data) {
			removeLoading();
			if(error(data)) return;
			var array = data.data;
			var tby = $("#tby");
			
			for (var i = 0, len = array.length; i < len; i++) {
				if(array[i] == null) continue;
				var mclusterName = '';
        		if(array[i].mcluster != undefined && array[i].mcluster != null) {
        			mclusterName = array[i].mcluster.mclusterName;
        		}
        		var tdh1 = "<input name=\"mclusterId\" type=\"hidden\" value=\""+array[i].mclusterId+"\"/>"
        		var tdh2 = "<input name=\"onRestart\" type=\"hidden\" value=\"0\"/>"
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
				var td5 = $("<td name=\"mclusterControl\">"
						//+ "<a href=\"/monitor/"+array[i].ipAddr+"/db/status\" target=\"_blank\">查看详情</a>"
						+ "<a href=\"/detail/mcluster/monitor/list/" + array[i].ipAddr + "/3\">查看详情</a>"
						+ "</td>");
				if(array[i].status == 0 ||array[i].status == 5||array[i].status == 13){
					var tr = $("<tr class=\"warning\"></tr>");
				}else if(array[i].status == 3 ||array[i].status == 4||array[i].status == 14){
					var tr = $("<tr class=\"default-danger\"></tr>");
				}else{
					var tr = $("<tr></tr>");
				}
				
				tr.append(tdh1).append(tdh2).append(td1).append(td2).append(td3).append(td4).append(td5);
				tr.appendTo(tby);
				
			}//循环json中的数据 
			/*增加标记行，控制各种状态的归类显示*/
			var tr1 = $("<tr class=\"default-danger normalTag\"></tr>");
			var tr2 = $("<tr class=\"default-danger lightDangerTag\"></tr>");
			var tr3 = $("<tr class=\"default-danger seriousTag\"></tr>");
			var tr4 = $("<tr class=\"default-danger disableClusterTag\"></tr>");
			var tr5 = $("<tr class=\"default-danger timeoutClusterTag\"></tr>");
			tby.prepend(tr1).prepend(tr2).prepend(tr3).prepend(tr4).prepend(tr5);
			updateMclusterStatus();//查询集群状态
			
		}
	});
}
function getMclusterStatus(ip,obj) {
	var restartButton = $("<span class=\"text-explode\">|</span>" 
							+ "<a data-toggle=\"tooltip\" data-placement=\"top\" title=\"拉起\" style=\"cursor:pointer\" onclick=\"restartMclusterServer(this)\">"
								+"<i class=\"ace-icon fa fa-repeat bigger-120\"></i>"
							+"</a>")
							
	function addNormalButton(){
		$(obj).find('[name="mclusterControl"]').html($(obj).find('[name="mclusterControl"]').find('a').first());
	}
	function addControlButton(){
		$(obj).find('[name="mclusterControl"]').html($(obj).find('[name="mclusterControl"]').find('a').first());
		$(obj).find('[name="mclusterControl"]').append(restartButton);
	}
	
	if($(obj).find('[name = "onRestart"]').val() != 0) return; //如果正在拉起，不更新
	getLoading();
	$.ajax({ 
		cache:false,
		type : "get",
		url : "/monitor/"+ip+"/db/status",
		dataType : "json", 
		contentType : "application/json; charset=utf-8",
		success : function(data) {
			removeLoading();
			if(error(data)) return;
			var result = data.data.result;
			if(result == "0"){
				$(obj).removeClass();
				$(obj).find('[name="mclusterStatus"]').html("<a>正常</a>");
				$(obj).parent().find(".normalTag").after($(obj));
				addNormalButton();
			}else if(result == "1"){
				$(obj).removeClass();
				$(obj).find('[name="mclusterStatus"]').html("<a>危险</a>");
				$(obj).addClass("default-danger lightDanger");
				$(obj).parent().find(".lightDangerTag").after($(obj));	
				addControlButton();
			}else if(result == "2"){
				$(obj).removeClass();
				$(obj).find('[name="mclusterStatus"]').html("<a>严重危险</a>");
				$(obj).addClass("default-danger serious");
				$(obj).parent().find(".seriousTag").after($(obj));
				addControlButton();
			}else if(result == "3"){
				$(obj).removeClass();
				$(obj).find('[name="mclusterStatus"]').html("<a>集群不可用</a>");
				$(obj).addClass("default-danger disableCluster");
				$(obj).parent().find(".disableClusterTag").after($(obj));
				addControlButton();
			}else if(result == "4"){
				$(obj).removeClass();
				$(obj).find('[name="mclusterStatus"]').html("<a>获取数据超时</a>");
				$(obj).addClass("default-danger timeoutCluster");
				$(obj).parent().find(".timeoutClusterTag").after($(obj));
				addControlButton();
			}
			$('[data-toggle = "tooltip"]').tooltip();
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
	if($(obj).closest('tr').find('[name = "onRestart"]').val() != 0){	//如果正在拉起，不在发送命令。
		warn($(obj).closest("tr").find('td').first().html()+"拉起命令已发出，请等待...",3000);
		return
	}else{
		$(obj).closest('tr').find('[name = "onRestart"]').val(1);
	}
	$(obj).closest('tr').find('[name="mclusterControl"] a:eq(1)').attr('title',"正在拉起，请稍等...").find('i').attr('class',"ace-icon fa fa-spinner fa-spin  bigger-120");//改为刷新状态，并提示正在拉起
	getLoading();
	$.ajax({ 
		cache:false,
		type : "post",
		url : "/mcluster/restart",
		dataType : "json", 
		data : {
			mclusterId:$(obj).closest("tr").find('[name = "mclusterId"]').val()
		},
		success : function(data) {
			removeLoading();
			$(obj).closest('tr').find('[name = "onRestart"]').val(0); //恢复可刷新状态
			if(error(data)){
				$(obj).closest('tr').find('[name="mclusterControl"] a:eq(1)').attr('title',"拉起").find('i').attr('class',"ace-icon fa fa-repeat bigger-120");//改为刷新状态，并提示正在拉起
				return;
			}
			var tr = $(obj).closest('tr')
			getMclusterStatus(tr.find('[name="vip"]').html(),tr); //返回成功刷新本行
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
