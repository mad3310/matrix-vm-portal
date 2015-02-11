$(function(){
	//隐藏搜索框
	$('#nav-search').addClass("hidden");
	queryContainer();
})
function queryContainer(){
	$("#tby tr").remove();
	getLoading();
	$.ajax({ 
		cache:false,
		type : "get",
		url : "/container/"+$("#mclusterId").val(),
		dataType : "json", 
		success : function(data) {
			removeLoading();
			if(error(data)) return;
			var array = data.data;
			var tby = $("#tby");
 			$("#headerContainerName").append(array[0].mcluster.mclusterName);
			for (var i = 0, len = array.length; i < len; i++) {
				var td0 = $("<input name=\"container_id\" value= \""+array[i].id+"\" type=\"hidden\"/>");
				var td1 = $("<td>"
					    + array[i].containerName
				        + "</td>");
				var	td2 = $("<td>"
						+ array[i].type
						+ "</td>");
				var	td3 = $("<td>"
						+ array[i].hostIp
						+ "</td>");
				var	td4 = $("<td>"
						+ array[i].ipAddr
						+ "</td>");
				if(array[i].mountDir != null){
					var	td5 = $("<td>"
							+ array[i].mountDir
							+ "</td>");
				}else{
					var	td5 = $("<td>"
							+ '-'
							+ "</td>");
				}
				if(array[i].zookeeperId != null){
					var	td6 = $("<td>"
							+ array[i].zookeeperId
							+ "</td>");
				}else{
					var	td6 = $("<td>"
							+ '-'
							+ "</td>");
				}
				var	td7 = $("<td>"
						+ translateStatus(array[i].status)
						+ "</td>");
				var td8 = $("<td>"
						+"<div class=\"hidden-sm hidden-xs action-buttons\">"
						+"<a class=\"green\" href=\"#\" onclick=\"startContainer(this)\" data-toggle=\"modal\" data-target=\"#\">"
						+"<i class=\"ace-icon fa fa-play-circle-o bigger-130\"></i>"
						+"</a>"
						+"<a class=\"blue\" href=\"#\" onclick=\"stopContainer(this)\" data-toggle=\"modal\" data-target=\"#\">"
							+"<i class=\"ace-icon fa fa-power-off bigger-120\"></i>"
						+"</a>"
						+"</div>"
						+ "</td>"
				);
				var tr = $("<tr></tr>");;				
				tr.append(td0).append(td1).append(td2).append(td3).append(td4).append(td5).append(td6).append(td7).append(td8);
				tr.appendTo(tby);
			}
		}
	});
}

function startContainer(obj){
	var tr = $(obj).parents("tr").html();
	if (tr.indexOf("已停止") < 0){
		warn("当前状态无法执行启动操作!",3000);
		return 0;
	}
	function startCmd(){
		var containerId =$(obj).parents("tr").find('[name="container_id"]').val();
		getLoading();
		$.ajax({
			cache:false,
			url:'/container/start',
			type:'post',
			data:{containerId : containerId},
			success:function(data){
				removeLoading();
				if(error(data)) return;
				queryContainer();
			}
		});
	}
	confirmframe("启动container","启动container大概需要几分钟时间!","请耐心等待...",startCmd);
}
function stopContainer(obj){
	var tr = $(obj).parents("tr").html();
	if (tr.indexOf("运行中") < 0 && tr.indexOf("异常") < 0){
		warn("当前状态无法执行关闭操作!",3000);
		return 0;
	}
	function stopCmd(){
		var containerId =$(obj).parents("tr").find('[name="container_id"]').val();
		getLoading();
		$.ajax({
			cache:false,
			url:'/container/stop',
			type:'post',
			data:{containerId : containerId},
			success:function(data){
				removeLoading();
				if(error(data)) return;
				queryContainer();
			}
		});
	}
	confirmframe("关闭container","关闭container将不能提供服务,再次启动需要十几分钟!","您确定要关闭?",stopCmd);
}
