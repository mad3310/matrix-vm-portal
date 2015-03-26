var currentPage = 1; //第几页 
var recordsPerPage = 15; //每页显示条数
	
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
	getLoading();
	$.ajax({
		cache:false,
		type : "get",
		url : queryUrlBuilder("/task/monitor",queryCondition),
		dataType : "json", /*这句可用可不用，没有影响*/
		success : function(data) {
			$("#menu-tby tr").remove();
			removeLoading();
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
				var td2 = $("<td width=\"100px\">"
						+ array[i].status
						+ "</td>");
				var tr = $("<tr></tr>");
				
				tr.append(td1).append(td2);
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
		},
		error : function(XMLHttpRequest,textStatus, errorThrown) {
			error(XMLHttpRequest);
			return false;
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
		})
	})
}

function page_init(){
	queryByPage();
	pageControl();
}
