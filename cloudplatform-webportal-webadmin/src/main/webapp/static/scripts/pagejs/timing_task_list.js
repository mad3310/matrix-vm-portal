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
		var iw=document.body.clientWidth;
		if(iw>767){//md&&lg
		}else{
			$('.queryOption').addClass('collapsed').find('.widget-body').attr('style', 'dispaly:none;');
			$('.queryOption').find('.widget-header').find('i').attr('class', 'ace-icon fa fa-chevron-down');
			var qryStr='';
			var qryStr1=$('#timingTaskName').val();var qryStr2=$('#timingTaskDescn').val();
			if(qryStr1){
				qryStr+='<span class="label label-success arrowed">'+qryStr1+'<span class="queryBadge" data-rely-id="timingTaskName"><i class="ace-icon fa fa-times-circle"></i></span></span>&nbsp;'
			}
			if(qryStr2){
				qryStr+='<span class="label label-warning arrowed">'+qryStr2+'<span class="queryBadge" data-rely-id="timingTaskDescn"><i class="ace-icon fa fa-times-circle"></i></span></span>&nbsp;'
			}
			if(qryStr){
				$('.queryOption').find('.widget-title').html(qryStr);
				$('.queryBadge').click(function(event) {
					var id=$(this).attr('data-rely-id');
					$('#'+id).val('');
					$(this).parent().remove();
					queryByPage();
					if($('.queryBadge').length<=0){
						$('.queryOption').find('.widget-title').html('定时任务查询条件');
					}
					return;
				});
			}else{
				$('.queryOption').find('.widget-title').html('定时任务查询条件');
			}

		}
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
				var td3 = $("<td class='hidden-480'>"
						+array[i].uuid
						+" </td>");
				var td4 = $("<td class='hidden-480'>"
						+ array[i].execType
						+ "</td>");
				var td5 = $("<td>"
						+ array[i].httpMethod
						+ "</td>");
				var td6 = $("<td class='hidden-480'>"
						+ array[i].url
						+ "</td>");
				var td7= $("<td>"-"</td>");
				if(array[i].type=="CRON"){
					var td7 = $("<td>"
							+"每天"+array[i].timePoint+"时执行"
							+ "</td>");
				}else if(array[i].type=="INTERVAL"){
					var td7 = $("<td>"
							+"每隔"+array[i].timeInterval+"执行一次"
							+ "</td>");
				}
						
				var td8 = $("<td class='hidden-480'>"
						+array[i].descn
						+ "</td>");
				var td9 = $("<td>"
						+"<div class=\"action-buttons\">"
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
         	timingTaskVal:{
         		validMessage: '请按提示输入',
                 validators: {
                     notEmpty: {
                         message: '执行接口不能为空	!'
                     },integer: {
	                        message: '请输入数字'
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
       $("#add-timing-task-modal").modal("hide");
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
    			postData.timePoint=$("#timingTaskVal").val()+$("#timingTaskValUnit").val();
    		}else if(timingTaskType=="INTERVAL"){
    			postData.timeInterval=$("#timingTaskVal").val()+$("#timingTaskValUnit").val();
    		}
        $.ajax({
    		cache:false,
    		type : "post",
    		url : "/timingTask",
    		data:postData,
    		dataType : "json",
    		success : function(data) {
    			if(error(data)) return;
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
		$("#timingTaskValUnit").html("<option value=\"hour\">时</option>");
		$("#timing-task-span-start").text("每天");
		$("#timing-task-span-end").text("时运行");
		timingTaskType="CRON";
	});
	$("#timing-task-interval-btn").click(function(){
		$("#timing-task-cron-btn").removeClass("btn-primary").addClass("btn-default");
		$(this).removeClass("btn-default").addClass("btn-primary");
		$("#timingTaskValUnit").html("<option value=\"hours\">时</option>"
													+"<option value=\"minutes\">分</option>"
													+"<option value=\"seconds\">秒</option>");
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
