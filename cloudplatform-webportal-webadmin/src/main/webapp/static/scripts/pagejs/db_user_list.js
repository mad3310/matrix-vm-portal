var currentPage = 1; //第几页 
var recordsPerPage = 15; //每页显示条数
var currentSelectedLineDbName = 1;
	
 $(function(){
	//初始化
	page_init();

	$(document).on('click', 'th input:checkbox' , function(){
		var that = this;
		$(this).closest('table').find('tr > td:first-child input:checkbox')
		.each(function(){
			this.checked = that.checked;
			$(this).closest('tr').toggleClass('selected');
		});
	});
});	
 
function buildUser() {
	var ids = $("[name='db_user_id']:checked");
	var str="";
	var flag = 0; //0:无数据 -1:错误
	ids.each(function(){
		if($(this).closest("tr").children().last().html() == "正常"){
			$.gritter.add({
				title: '警告',
				text: '创建用户只能选择待审核数据!',
				sticky: false,
				time: '3000',
				class_name: 'gritter-warning'
			});
			flag = -1;
			return false;
		}else{
			str +=($(this).val())+",";
 			flag += 1;
		}
	});
	
	if(flag > 0) {
		getLoading();
		$.ajax({ 
			cache:false,
			type : "post",
			url : "/dbUser",
			dataType : "json",
			data : {'dbUserId':str},
			success : function(data) {
				removeLoading();
				if(error(data)) return;
				if(data.result == 1) {
					$("#titleCheckbox").attr("checked", false);
					queryByPage(currentPage,recordsPerPage);
				}
			}
		});
	} else if (flag == 0){
		$.gritter.add({
		title: '警告',
		text: '请选择数据！',
		sticky: false,
		time: '5',
		class_name: 'gritter-warning'
	});

	return false;
	}else{
		return false;
	}
}
 
	function queryByPage(currentPage,recordsPerPage) {
		$("#tby tr").remove();
		var dbName = $("#nav-search-input").val()?$("#nav-search-input").val():'null';
		getLoading();
		$.ajax({ 
			cache:false,
			type : "get",
			url : "/dbUser/" + currentPage + "/" + recordsPerPage+"/" + dbName,
			dataType : "json", /*这句可用可不用，没有影响*/
			contentType : "application/json; charset=utf-8",
			success : function(data) {
				removeLoading();
				if(error(data)) return;
				var array = data.data.data;
				var tby = $("#tby");
				var totalPages = data.data.totalPages;
				
				for (var i = 0, len = array.length; i < len; i++) {
					if(array[i].db == undefined || array[i].db == null) continue;
					var td1 = $("<td class=\"center\">"
									+"<label class=\"position-relative\">"
									+"<input name=\"db_user_id\" value= \""+array[i].id+"\" type=\"checkbox\" class=\"ace\"/>"
									+"<span class=\"lbl\"></span>"
									+"</label>"
								+"</td>");
					var td2 = $("<td>"
							+ array[i].username
							+ "</td>");
					var td3 = $("<td>"
							+ "<a href=\"/detail/db/" + array[i].dbId+"\">"+array[i].db.dbName+"</a>"
							+ "</td>");
					if(array[i].type == 1){
						var td4 = $("<td>"
								    + "管理员"
								    + "</td>");
					}else if(array[i].type == 2){
						var td4 = $("<td>"
							    + "只读用户"
							    + "</td>");
					}else{
						var td4 = $("<td>"
								    + "读写用户"
								    + "</td>");
					}
					var td5 = $("<td>"
							+ array[i].acceptIp
							+ "</td>");
					var td6 = $("<td>"
							+ array[i].maxConcurrency
							+ "</td>");
					var td7 = $("<td><a>"
							+ translateStatus(array[i].status)
							+ "</a></td>"); 
						
					if(array[i].status == 0 ||array[i].status == 5||array[i].status == 13){
						var tr = $("<tr class=\"warning\"></tr>");
					}else if(array[i].status == 3 ||array[i].status == 4||array[i].status == 14){
						var tr = $("<tr class=\"default-danger\"></tr>");
						
					}else{
						var tr = $("<tr></tr>");
					}
					tr.append(td1).append(td2).append(td3).append(td4).append(td5).append(td6).append(td7);
					tr.appendTo(tby);
				}
				
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
		searchAction();
		queryByPage(currentPage,recordsPerPage);
		pageControl();
	}
