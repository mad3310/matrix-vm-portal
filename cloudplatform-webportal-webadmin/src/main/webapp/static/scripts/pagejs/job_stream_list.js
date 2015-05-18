var currentPage = 1; //第几页 
var recordsPerPage = 15; //每页显示条数
	
$(function(){
	//初始化 
	page_init();
	var options = {
			allow_single_deselect:true,
			search_contains:true,
			no_results_text:"未找到匹配数据",
			disable_search:true,	
			width:'272px'
		}
		
	$('.chosen-select').chosen(options)

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
		url : queryUrlBuilder("/task",queryCondition),
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
							+"<input name=\"mcluster_id\" value= \""+array[i].id+"\" type=\"checkbox\" class=\"ace\"/>"
							+"<span class=\"lbl\"></span>"
							+"</label>"
							+"</td>");
				var td2 = $("<td>"
						+"<a class=\"link\" href=\"/detail/job/stream/"+array[i].id+"\">"+array[i].name+"</a>"
						+ "</td>");
				var td3 = $("<td>"
						+ array[i].taskType
						+ "</td>");
				var td4 = $("<td class='hidden-480'>"
						+ date('Y-m-d H:i:s',array[i].createTime)
						+ "</td>");
				var td5 = $("<td class='hidden-480'>"
						+ array[i].descn
						+ "</td>");
				/*var td6 = $("<td>"
						+ "<a><span>修改</span><a>"
						+"</td>"
				);*/
				
				var tr = $("<tr></tr>");
				
				tr.append(td1).append(td2).append(td3).append(td4).append(td5);
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
        	 name: {
                 validMessage: '请按提示输入',
                 validators: {
                     notEmpty: {
                         message: '任务流名称不能为空!'
                     },
			          stringLength: {
			              max: 40,
			              message: '任务流名过长'
			          },remote:{
			        	  message: '任务流名称已存在!',
	                        url: "/task/validate"
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
    		success : function() {
    			location.href = "/list/job/stream";
    		}
    	})
     });
}

function page_init(){
	$('[name = "popoverHelp"]').popover();
	queryByPage();
	formValidate();
	pageControl();
}
