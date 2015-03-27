var currentPage = 1; //第几页 
var recordsPerPage = 200; //每页显示条数
var refresh = null;
	
$(function(){
	//初始化 
	page_init();
});	
function queryByPage() {
	var queryCondition = {
			'currentPage':currentPage,
			'recordsPerPage':recordsPerPage,
		}
	$("#tby tr").remove();
	$.ajax({
		cache:false,
		type : "get",
		url : queryUrlBuilder("/task/monitor",queryCondition),
		dataType : "json", /*这句可用可不用，没有影响*/
		success : function(data) {
			$("#menu-tby tr").remove();
			if(error(data)) return;
			var array = data.data.data;
			var tby = $("#menu-tby");
			var totalPages = data.data.totalPages;
			
			for (var i = 0, len = array.length; i < len; i++) {
				var td1=$("<td>-</td>")
				if(array[i].templateTask != undefined && array[i].templateTask!= null){
					td1 = $("<td>"
						+array[i].templateTask.name
						+ "</td>");
				}
				var td2 = $("<td width=\"80px\">"
						+ array[i].status
						+ "</td>");
				var td3 = $("<input type=\"hidden\" value=\""+array[i].id+"\"/>");
				var tr = $("<tr></tr>");
				
				tr.append(td1).append(td2).append(td3);
				tr.appendTo(tby);
			}//循环json中的数据 
			initMonitorListClick();//初始化点击事件
			if (totalPages <= 1) {
				$("#pageControlBar").hide();
			} else {
				$("#pageControlBar").show();
				$("#totalPage_input").val(totalPages);
				$("#currentPage").html(currentPage);
				$("#totalRows").html(data.data.totalRecords);
				$("#totalPage").html(totalPages);
			}
		}
	});
	}
   

function pageControl() {
	// 首页
	$("#firstPage").bind("click", function() {
		currentPage = 1;
		queryByPage();
	});

	// 上一页
	$("#prevPage").click(function() {
		if (currentPage == 1) {
			$.gritter.add({
				title: '警告',
				text: '已到达首页',
				sticky: false,
				time: '5',
				class_name: 'gritter-warning'
			});
	
			return false;
			
		} else {
			currentPage--;
			queryByPage();
		}
	});

	// 下一页
	$("#nextPage").click(function() {
		if (currentPage == $("#totalPage_input").val()) {
			$.gritter.add({
				title: '警告',
				text: '已到达末页',
				sticky: false,
				time: '5',
				class_name: 'gritter-warning'
			});
	
			return false;
			
		} else {
			currentPage++;
			queryByPage();
		}
	});

	// 末页
	$("#lastPage").bind("click", function() {
		currentPage = $("#totalPage_input").val();
		queryByPage();
	});
}

function initMonitorListClick(){
	var trs = $("#menu-tby tr");
	trs.each(function(){
		$(this).click(function(){
			trs.removeClass("selected");
			$(this).addClass("selected");
			
			var taskId = $(this).find("input").val();
			queryTaskDetail(taskId,"new");
			
			function updateTaskDetail(){
				queryTaskDetail(taskId,"update");
			}
			/*定时刷新状态*/
			if(refresh != null){
				clearInterval(refresh);
			}
			refresh = setInterval(updateTaskDetail,5000);
		})
	})
}

function queryTaskDetail(taskId,type){	//type 为 new|update
	$.ajax({
		cache:false,
		type : "get",
		url : "/task/monitor/detail/"+taskId,
		dataType : "json", /*这句可用可不用，没有影响*/
		success : function(data) {
			if(type == "new"){
				$("#tby tr").remove();
			}
			if(error(data)) return;
			var array = data.data;
			var tby = $("#tby");
			
			for (var i = 0, len = array.length; i < len; i++) {
				var td1= $("<td>-</td>");
				if(array[i].templateTaskDetail != undefined && array[i].templateTaskDetail != null){
					td1 = $("<td>"
						+ array[i].templateTaskDetail.name
						+ "</td>");
				};
				var td2 = $("<td>"
						+ date('Y-m-d H:i:s',array[i].startTime)
						+ "</td>");
				var td3 = $("<td>"
						+ date('Y-m-d H:i:s',array[i].endTime)
						+ "</td>");
				var td4 = $("<td>"
						+ array[i].retry
						+ "</td>");
				var td5 = $("<td>"
						+ array[i].result
						+ "</td>");
				var td6 = $("<td>"
						+ array[i].status
						+ "</td>");
				var td7 = $("<td></td>");
				
				var tr = $("<tr></tr>");
				if(array[i].status == "FAILED"){
					td7.html("<span class=\"ace-icon fa fa-repeat bigger-120\" style=\"cursor:pointer\"></span>");
					tr.addClass("danger");
				}
				
				tr.append(td1).append(td2).append(td3).append(td4).append(td5).append(td6).append(td7);
				if(type == "new"){
					tr.appendTo(tby);
				}else{
					tby.find("tr:eq("+i+")").html(tr.html());
				}
			}
		},
		error : function(XMLHttpRequest,textStatus, errorThrown) {
			error(XMLHttpRequest);
			return false;
		}
	});
}

function page_init(){
	queryByPage();
	pageControl();
}
