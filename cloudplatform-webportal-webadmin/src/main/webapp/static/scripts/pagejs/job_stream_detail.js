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
			var array = data.data;
			var tby = $("#tby");
			
			for (var i = 0, len = array.length; i < len; i++) {
				var td1 = $("<td class=\"center\">"
							+"<label class=\"position-relative\">"
							+"<input name=\"mcluster_id\" value= \""+array[i].id+"\" type=\"checkbox\" class=\"ace\"/>"
							+"<span class=\"lbl\"></span>"
							+"</label>"
							+"</td>");
				var td2 = $("<td>-</td>");
				var td3 = $("<td>-</td>");
				var td5 = $("<td>-</td>");
					if(array[i].templateTaskDetail != undefined && array[i].templateTaskDetail != null){
						td2 = $("<td>"
								+array[i].templateTaskDetail.name
								+ "</td>");
						var td3 = $("<td>"
								+ array[i].templateTaskDetail.taskType
								+ "</td>");
						var td5 = $("<td>"
								+ array[i].templateTaskDetail.descn
								+ "</td>");
					}
				var td4 = $("<td>"
						+ array[i].retry
						+ "</td>");
				var td6 = $("<td>"
						+ array[i].executeOrder
						+ "</td>");
				var td7 = $("<td>"
						+ "<a><span>修改</span><a>"
						+"</td>"
				);
				
				var tr = $("<tr></tr>");
				
				tr.append(td1).append(td2).append(td3).append(td4).append(td5).append(td6).append(td7);
				tr.appendTo(tby);
			}//循环json中的数据 
			
			/*初始化tooltip*/
			$('[data-toggle = "tooltip"]').tooltip();
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
	
//创建Container集群表单验证
function formValidate() {
	$("#create-task-stream-form").bootstrapValidator({
	  message: '无效的输入',
         feedbackIcons: {
             valid: 'glyphicon glyphicon-ok',
             invalid: 'glyphicon glyphicon-remove',
             validating: 'glyphicon glyphicon-refresh'
         },
         fields: {
        	 taskStreamName: {
                 validMessage: '请按提示输入',
                 validators: {
                     notEmpty: {
                         message: '任务流名称不能为空!'
                     },
			          stringLength: {
			              max: 40,
			              message: '任务流名过长'
			          }
	             }
         	},descn:{
         		validMessage: '请按提示输入',
         		validators: {
			          stringLength: {
			              max: 100,
			              message: '描述最长字符为100'
			          }
	             }
         	}	
         }
     }).on('success.form.bv', function(e) {
    	 e.preventDefault();
    	$.ajax({
    		cache:false,
    		type : "post",
    		url : "/task",
    		data: {
    			name:$('#taskStreamName').val(),
    			taskType:$('#taskType').val(),
    			descn:$('#descn').val()
    		},
    		success : function(data) {
    			location.href = "/list/job/stream";
    		}
    	})
     });
}

function page_init(){
	$('[name = "popoverHelp"]').popover();
	queryByPage();
//	formValidate();
}