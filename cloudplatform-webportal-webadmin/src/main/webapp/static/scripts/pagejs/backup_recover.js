/*
 * backup_recover page js. 2015/01/23
 */

var currentPage = 1; //第几页 
var recordsPerPage = 5; //每页显示条数
var currentSelectedLineDbName = 1;
$(function(){
	//初始化
	page_init();
});	
//页面查询功能
$("#bksearch").click(function() {
	queryByPage(currentPage, recordsPerPage);
});

function queryByPage(currentPage, recordsPerPage) {
	
	$("#backupTbody tr").remove();
	var startTime = $("#startTime").val();
	var endTime = $("#endTime").val();
	var mclusterName = $("#mclusterName").val();
	var dbName = $("#dbName").val();
	
	var backupStatus = $("#backupStatus").val();
	if  (backupStatus == 0){
		var status = "SUCCESS";
	}else if(backupStatus == 1){
		var status = "FAILD";
	}else if(backupStatus == 2){
		var status = "BUILDING";
	}
	
	$.ajax({ 
		type : "get",
		url : "/backup?" + "&&startTime=" + startTime + "&&endTime=" + endTime + "&&currentPage=" + currentPage + "&&recordsPerPage=" + recordsPerPage + "&&dbName=" + dbName +"&&mclusterName=" + mclusterName +'&&status=' + status,
		dataType : "json", /*这句可用可不用，没有影响*/
		contentType : "application/json; charset=utf-8",
		success : function(data) {
			error(data);
			var array = data.data.data;
			var $backupTbody = $("#backupTbody");
			var totalPages = data.data.totalPages;
	        for(var i= 0, len= array.length;i<len;i++){
	                var td1 = $("<td>"
	                		+ FilterNull(array[i].mcluster.mclusterName)
	                		+"</td>");
	                var td2 = $("<td>"
	                		+ FilterNull(array[i].db.dbName)
	                		+"</td>");
	                var td3 = $("<td>"
	                        + date('Y-m-d H:i:s',array[i].startTime)
	                        + "</td>");
	                var td4 = $("<td>"
                            + date('Y-m-d H:i:s',array[i].endTime)
	                        + "</td>");
	                var td5 = $("<td><span>"
	                		+ translateStatus(array[i].status)
	                		+ "</span></td>");
	                var td6 = $("<td>"
	                        + array[i].resultDetail
	                        + "</td>");
	                if(array[i].status == 'FAILD'){
						var tr = $("<tr class=\"data-tr warning\"></tr>");
					}else if(array[i].status == 'SUCCESS'){
						var tr = $("<tr class=\" data-tr default-danger\"></tr>");
					}else{
						var tr = $("<tr class='data-tr'></tr>");
					}
	                tr.append(td1).append(td2).append(td3).append(td4).append(td5).append(td6);
	                tr.appendTo($backupTbody);
				   //$('[name = "dbRefuseStatus"]').popover();
			}//循环json中的数据 
			
			if (totalPages <= 1) {
				debugger
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

function pageControl() {
	// 首页
	$("#firstPage").bind("click", function() {
		currentPage = 1;
		queryByPage(currentPage,recordsPerPage);
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
			queryByPage(currentPage,recordsPerPage);
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
			queryByPage(currentPage,recordsPerPage);
		}
	});

	// 末页
	$("#lastPage").bind("click", function() {
		currentPage = $("#totalPage_input").val();
		queryByPage(currentPage,recordsPerPage);
	});
}

function searchAction(){
	$('#nav-search-input').bind('keypress',function(event){
        if(event.keyCode == "13")    
        {
        	queryByPage(currentPage, recordsPerPage);
        }
    });
}
function page_init(){
	$('#nav-search').addClass("hidden");
	queryByPage(currentPage, recordsPerPage);
	searchAction();
	pageControl();
}
