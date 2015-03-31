var currentPage = 1; //第几页 
var recordsPerPage = 15; //每页显示条数
	
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
		url : "/task/detail/"+$("#jobStreamId").val(),
		dataType : "json", /*这句可用可不用，没有影响*/
		success : function(data) {
			removeLoading();
			if(error(data)) return;
			var array = data.data.templateTaskDetail;
			var tby = $("#tby");
			
			for (var i = 0, len = array.length; i < len; i++) {
				var td1 = $("<td class=\"center\">"
							+"<label class=\"position-relative\">"
							+"<input name=\"chainId\" value= \""+array[i].id+"\" type=\"checkbox\" class=\"ace\"/>"
							+"<span class=\"lbl\"></span>"
							+"</label>"
							+"</td>");
				var td2 = $("<td>-</td>");
				var td3 = $("<td>-</td>");
				var td4 = $("<td>"
						+ array[i].retry
						+ "</td>");
				var td5 = $("<td>-</td>");
				if(array[i].templateTaskDetail != undefined && array[i].templateTaskDetail != null){
					td2 = $("<td>"
							+array[i].templateTaskDetail.name
							+ "</td>");
					td5 = $("<td>"
							+ array[i].templateTaskDetail.descn
							+ "</td>");
				}
				if(array[i].templateTask != undefined && array[i].templateTask != null){
					td3 = $("<td>"
						+ array[i].templateTask.taskType
						+ "</td>");
				}
				var td6 = $("<td>"
						+ array[i].executeOrder
						+ "</td>");
				var td7 = $("<td>"
						+ "<span onclick=\"delTaskUnit(this)\" style=\"cursor:pointer\">删除</span>"
						+"</td>"
				);
				
				var tr = $("<tr></tr>");
				
				tr.append(td1).append(td2).append(td3).append(td4).append(td5).append(td6).append(td7);
				tr.appendTo(tby);
			}//循环json中的数据 
			
			/*初始化tooltip*/
			$('[data-toggle = "tooltip"]').tooltip();
			
			init_task_option("/task/unit/"+data.data.templateTask.taskType)//根据任务流类型初始化任务单元选项
		},
		error : function(XMLHttpRequest,textStatus, errorThrown) {
			error(XMLHttpRequest);
			return false;
		}
	});
	}
   


	function searchAction(){
		$('#nav-search-input').bind('keypress',function(event){
	        if(event.keyCode == "13")    
	        {
	        	queryByPage();
	        }
	    });
	}
function addTaskUnit(){
	$("#add-task-unit").click(function(){
		$("#add-task-unit").addClass("diabled");
		var data={
				taskDetailId : $("#taskDetailId").val(),
				retry : $("#retry").val(),
				taskId : $("#jobStreamId").val(),
				executeOrder : $("#executeOrder").val(),
		}
		$.ajax({
			cache:false,
			type : "post",
			url : "/task/stream",
			data : data,
			success : function(){
				location.href = "/detail/job/stream/"+data.taskId;
			}
		})
	})
}

function delTaskUnit(obj){
		var chainId = $(obj).closest("tr").find("input").val();
		function delCmd(){
			$.ajax({
				cache:false,
				type : "delete",
				url : "/task/stream/"+chainId,
				success : function(){
					location.href = "/detail/job/stream/"+$("#jobStreamId").val();
				}
			})
		}
		confirmframe("删除任务单元","删除"+$(obj).closest("tr").find("td:eq(1)").html()+"后可重新添加","您确定要删除?",delCmd);
}
function init_task_option(url){
	var select = $("#taskDetailId");
	$.ajax({
		cache:false,
		type : "get",
		url : url,
		dataType : "json", /*这句可用可不用，没有影响*/
		success : function(data){
			var array = data.data;
			for(var i=0,len=array.length;i<len;i++){
				var option = $("<option value=\""+array[i].id+"\">"+array[i].name+"</option>");
				option.appendTo(select);
			}
		}
	})
}

function page_init(){
	
	$('[name = "popoverHelp"]').popover();
	queryByPage();
	//formValidate();
	addTaskUnit();
}