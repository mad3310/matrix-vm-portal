var currentPage = 1; //第几页 
var recordsPerPage = 15; //每页显示条数
var timingTaskType="CRON";
	
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
				var td7= $("<td>"-"</td>");
				if(array[i].type=="CRON"){
					var td7 = $("<td>"
							+"每"+array[i].timePoint+"时执行"
							+ "</td>");
				}else if(array[i].type=="INTERVAL"){
					var td7 = $("<td>"
							+"每隔"+array[i].timeInterval+"执行一次"
							+ "</td>");
				}
						
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
         	timingHour: {
                 validMessage: '请按提示输入',
                 validators: {
                     integer: {
                        message: '请输入数字'
                    },between:{
                        min:0,
                        max:23,
                        message:'时范围0-23'
                    }
	             }
         	},
         	timingMin: {
                 validMessage: '请按提示输入',
                 validators: {
                     integer: {
                        message: '请输入数字'
                    },between:{
                        min:0,
                        max:59,
                        message:'分范围0-59'
                    }
	             }
         	},
         	timingSecond: {
                 validMessage: '请按提示输入',
                 validators: {
                     integer: {
                        message: '请输入数字'
                    },between:{
                        min:0,
                        max:59,
                        message:'秒范围0-59'
                    },callback: {
                        message: '时分秒不能全为空',
                        callback: function() {
                            if($("#timing-hour").val()!=''||$("#timing-min").val()!=''||$("#timing-second").val()!=''){
	                            return true;
                            }else{
                            	 return false;
                            }
                        }
                    }
	             }
         	}
         }
     }).on('keyup', '[name="timingHour"]', function () {
            $('#add-timing-task-form').bootstrapValidator('revalidateField', 'timingSecond');
    }).on('keyup', '[name="timingMin"]', function () {
            $('#add-timing-task-form').bootstrapValidator('revalidateField', 'timingSecond');
    }).on('success.form.bv', function(e) {
        e.preventDefault();
        var timingHour=$("#timing-hour").val();
        var timingmin=$("#timing-min").val();
        var timingSecond=$("#timing-second").val();
        
        var postData= {
    			name:$('#addTimingTaskName').val(),
    			url:$('#timingTaskUrl').val(),
    			type:timingTaskType,
    			httpMethod:$('#httpMethod').val(),
    			descn:$('#addTimingTaskDescn').val(),
    			execType:$("#timingTaskExecType").val()
    		};
    		if(timingTaskType=="CRON"){
    			postData.timePoint=(timingHour==''? '':timingHour+"hour")+(timingmin==''? '':timingmin+"minute")+(timingSecond==''? '':timingSecond+"second");
    		}else if(timingTaskType=="INTERVAL"){
    			postData.timeInterval=(timingHour==''? '':timingHour+"hours")+(timingmin==''? '':timingmin+"minutes")+(timingSecond==''? '':timingSecond+"seconds");
    		}
        $.ajax({
    		cache:false,
    		type : "post",
    		url : "/timingTask",
    		data:postData,
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
function initTimingTaskSetBtn(){
	$("#timing-task-cron-btn").click(function(){
		$("#timing-task-interval-btn").removeClass("btn-primary").addClass("btn-default");
		$(this).removeClass("btn-default").addClass("btn-primary");
		$("#timing-task-span-start").text("每");
		$("#timing-task-span-end").text("时运行");
		timingTaskType="CRON";
	});
	$("#timing-task-interval-btn").click(function(){
		$("#timing-task-cron-btn").removeClass("btn-primary").addClass("btn-default");
		$(this).removeClass("btn-default").addClass("btn-primary");
		$("#timing-task-span-start").text("每隔");
		$("#timing-task-span-end").text("运行一次");
		timingTaskType="INTERVAL";
	});
	
}
function page_init(){
	queryByPage();
	pageControl();
	formValidate();
	initTimingTaskSetBtn();
	$('[name = "popoverHelp"]').popover();
}
