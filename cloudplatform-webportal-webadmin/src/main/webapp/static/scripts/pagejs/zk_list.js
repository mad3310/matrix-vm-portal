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
			'recordsPerPage':recordsPerPage
		}
	
	$("#tby tr").remove();
	getLoading();
	$.ajax({
		cache:false,
		type : "get",
		url : queryUrlBuilder("/zk",queryCondition),
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
				var td2 = $("<td class='hidden-480'>"
						+array[i].name
						+ "</td>");
				var td3 = $("<td>"
						+ array[i].ip
						+ "</td>");
				var td4 = $("<td>"
						+ array[i].port
						+ "</td>");
				var td5 = $("<td class=\"used\">"
						+ array[i].used
						+ "</td>");
				var td6 = $("<td class='hidden-480'>"
						+ array[i].status
						+ "</td>");
				var td7 = $("<td class='hidden-480'>"
						+ array[i].descn
						+ "</td>");
				var td8 = $("<td>"
						+"<a class=\"red\" href=\"#\" onclick=\"delZookeeper(this)\" style=\"cursor:pointer\" onfocus=\"this.blur();\"  title=\"删除\" data-toggle=\"tooltip\" data-placement=\"right\">"
						+"<i class=\"ace-icon fa fa-trash-o bigger-120\"></i>"
						+"</a>"
						+"</td>");
				
				var tr = $("<tr></tr>");
				
				tr.append(td1).append(td2).append(td3).append(td4).append(td5).append(td6).append(td7).append(td8);
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
	
function formValidate() {
	$("#add-zk-form").bootstrapValidator({
	  message: '无效的输入',
         feedbackIcons: {
             valid: 'glyphicon glyphicon-ok',
             invalid: 'glyphicon glyphicon-remove',
             validating: 'glyphicon glyphicon-refresh'
         },
	         fields: {
	        	 ip: {
	                 validMessage: '请按提示输入',
	                 validators: {
	                     notEmpty: {
	                         message: 'ip不能为空'
	                     },
	                     regexp:{
	                    	 regexp:/^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/ ,
	                         message: "请按IP格式(xx.xx.xx.xx)输入."
	                     }
		             }
	         	},
	        	 port: {
	                 validMessage: '请按提示输入',
	                 validators: {
	                     notEmpty: {
	                         message: '端口号不能为空'
	                     },
	                     between:{
	                         min:0,
	                         max:65535,
	                         message:'端口号为0-65535之间'
	                     }
		             }
	         	}	
	         }
     }).on('success.form.bv', function(e) {
    	 e.preventDefault();
    	$.ajax({
    		cache:false,
    		type : "post",
    		url : "/zk",
    		data: {
    			name:$('#name').val(),
    			descn:$('#descn').val(),
    			status:$('#status').val(),
    			ip:$('#ip').val(),
    			port:$('#port').val()
    		},
    		success : function(data) {
    			location.href = "/list/zk";
    		}
    	})
     });
}
function delZookeeper(obj){
	if($(obj).closest("tr").find(".used").html() != '0'){
	$.gritter.add({
			title: '错误',
			text: "该zookeeper正在被使用，请移除使用再删除！",
			sticky: false,
			time: 1000,
			class_name: 'gritter-error'
		});
		return;
	}
	
		var zkId = $(obj).closest("tr").find("input").val();
		function delCmd(){
			$.ajax({
				cache:false,
				type : "delete",
				url : "/zk/"+zkId,
				success : function(){
					location.href = "/list/zk";
				}
			})
		}
		confirmframe("删除zookeeper","删除"+$(obj).closest("tr").find("td:eq(1)").html()+"后可重新添加","您确定要删除?",delCmd);
}
function page_init(){
	$('[name = "popoverHelp"]').popover();
	queryByPage();
	formValidate();
	pageControl();
}
