var currentPage = 1; //第几页 
var recordsPerPage = 15; //每页显示条数
var queryBuildStatusrefresh;//刷新handler
	
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
	$("#mclusterSearch").click(function(){
		queryByPage();
	});
	$("#mclusterClearSearch").click(function(){
		var clearList = ["timingTaskName","timingTaskDescn"];
		clearSearch(clearList);
	});
	enterKeydown($(".page-header > .input-group input"),queryByPage);
});
function queryByPage() {
	var name = $("#timingTaskName").val()?$("#timingTaskName").val():'';
	var  descn= $("#timingTaskDescn").val()?$("#timingTaskDescn").val():'';
	var queryCondition = {
			'currentPage':currentPage,
			'recordsPerPage':recordsPerPage,
			'name':name,
			'descn':descn
		}
	$("#tby tr").remove();
	getLoading();
	$.ajax({
		cache:false,
		type : "get",
		url : queryUrlBuilder("/timingTask",queryCondition),
		dataType : "json", /*这句可用可不用，没有影响*/
		success : function(data) {
			removeLoading();
			if(error(data)) return;
			var array = data.data.data;
			var tby = $("#tby");
			var totalPages = data.data.totalPages;
			for (var i = 0, len = array.length; i < len; i++) {
				var td1 = $("<td class=\"center\">"
								+"<label class=\"position-relative\">"
								+"<input name=\"timing_task_id\" value= \""+array[i].id+"\" type=\"checkbox\" class=\"ace\"/>"
								+"<span class=\"lbl\"></span>"
								+"</label>"
							+"</td>");
				var td2 = $("<td>"
						+array[i].name
						+ "</td>");
				var td3 = $("<td>"
						+array[i].uuid
						+" </td>");
				var td4 = $("<td>"
						+ array[i].execType
						+ "</td>");
				var td5 = $("<td>"
						+ array[i].httpMethod
						+ "</td>");
				var td6 = $("<td>"
						+ array[i].url
						+ "</td>");
				var td7 = $("<td>"
						+ array[i].type+"="+(array[i].timePoint != null?array[i].timePoint : array[i].timeInterval)
						+ "</td>");
						
				var td8 = $("<td>"
						+array[i].descn
						+ "</td>");
				var td9 = $("<td>"
						+"<div class=\"hidden-sm hidden-xs action-buttons\">"
						+"<a class=\"red\" href=\"#\" onclick=\"delTimingTask(this)\" title=\"删除\" data-toggle=\"tooltip\" data-placement=\"right\">"
						+"<i class=\"ace-icon fa fa-trash-o bigger-130\"></i>"
						+"</a>"
						+"</div>"
						+ "</td>"
				);
					var tr = $("<tr></tr>");
				
				tr.append(td1).append(td2).append(td3).append(td4).append(td5).append(td6).append(td7).append(td8).append(td9);
				tr.appendTo(tby);
			}//循环json中的数据 
			
			/*初始化tooltip*/
			$('[data-toggle = "tooltip"]').tooltip();
			
			if (totalPages <= 1) {
				$("#pageControlBar").hide();
			} else {
				$("#pageControlBar").show();
				$("#totalPage_input").val(totalPages);
				$("#currentPage").html(currentPage);
				$("#totalRows").html(data.data.totalRecords);
				$("#totalPage").html(totalPages);timing_task_id
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
function formValidate(){
	$("#add-timing-task-form").bootstrapValidator({
	  message: '无效的输入',
         feedbackIcons: {
             valid: 'glyphicon glyphicon-ok',
             invalid: 'glyphicon glyphicon-remove',
             validating: 'glyphicon glyphicon-refresh'
         },
         fields: {
       	  addTimingTaskName: {
                 validMessage: '请按提示输入',
                 validators: {
                     notEmpty: {
                         message: '名称不能为空!'
                     },
			          stringLength: {
			              max: 40,
			              message: 'Container集群名过长'
			          },regexp: {
		                  regexp: /^([a-zA-Z_0-9]*)$/,
  		                  message: "请输入字母数字或'_'"
                 	  }
	             }
         	},
         	timingTaskUrl: {
                 validMessage: '请按提示输入',
                 validators: {
                     notEmpty: {
                         message: '执行接口不能为空	!'
                     }
	             }
         	},
         	timingTaskRule: {
                 validMessage: '请按提示输入',
                 validators: {
                     notEmpty: {
                         message: '调度规则不能为空!'
                     }
	             }
         	}
         }
     }).on('success.form.bv', function(e) {
        e.preventDefault();
        $.ajax({
    		cache:false,
    		type : "post",
    		url : "/timingTask",
    		data: {
    			name:$('#addTimingTaskName').val(),
    			url:$('#timingTaskUrl').val(),
    			timingRule:$('#timingTaskRule').val(),
    			httpMethod:$('#httpMethod').val(),
    			descn:$('#addTimingTaskDescn').val()
    		},
    		success : function(data) {
    			location.href = "/list/timingTask";
    		}
    	})
    });
}
function delTimingTask(obj){
	function delCmd(){
		var timingTaskId =$(obj).parents("tr").find('[name="timing_task_id"]').val();
		getLoading();
		$.ajax({
			cache:false,
			url:'/timingTask/'+timingTaskId,
			type:'delete',
			success:function(data){
				removeLoading();
				if(error(data)) return;
				location.href = "/list/timingTask";
			}
		});
	}
	confirmframe("删除","您确定要删除此任务","删除后可以重新创建",delCmd);
}
function page_init(){
	queryByPage();
	pageControl();
	formValidate();
	$('[name = "popoverHelp"]').popover();
}
