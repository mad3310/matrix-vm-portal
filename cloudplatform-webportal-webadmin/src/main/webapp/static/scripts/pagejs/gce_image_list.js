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
			'recordsPerPage':recordsPerPage
		}
	
	$("#tby tr").remove();
	getLoading();
	$.ajax({
		cache:false,
		type : "get",
		url : queryUrlBuilder("/gce/image",queryCondition),
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
							+"<input name=\"gce_image_id\" value= \""+array[i].id+"\" type=\"checkbox\" class=\"ace\"/>"
							+"<span class=\"lbl\"></span>"
							+"</label>"
							+"</td>");
				var td2 = $("<td>"
						+array[i].name
						+ "</td>");
				var td3 = $("<td>"
						+ array[i].type
						+ "</td>");
				var td4 = $("<td>"
						+ array[i].tag
						+ "</td>");
				var td5 = $("<td> - </td>");
				if(array[i].createUserModel != undefined && array[i].createUserModel != null ){
					td5.html(array[i].createUserModel.userName);
				}
				var td6 = $("<td>"
						+ array[i].status
						+ "</td>");
				var td7 = $("<td>"
						+ array[i].url
						+ "</td>");
				var td8 = $("<td>"
						+ array[i].descn
						+ "</td>");
				var td9 = $("<td>"
						+"<a class=\"red\" href=\"#\" onclick=\"delGceImage(this)\" style=\"cursor:pointer\" onfocus=\"this.blur();\"  title=\"删除\" data-toggle=\"tooltip\" data-placement=\"right\">"
						+"<i class=\"ace-icon fa fa-trash-o bigger-120\"></i>"
						+"</a>"
						+"</td>");
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
	$("#add-gce-image-form").bootstrapValidator({
	  message: '无效的输入',
         feedbackIcons: {
             valid: 'glyphicon glyphicon-ok',
             invalid: 'glyphicon glyphicon-remove',
             validating: 'glyphicon glyphicon-refresh'
         },
         fields: {
         }
     }).on('success.form.bv', function(e) {
    	 e.preventDefault();
    	$.ajax({
    		cache:false,
    		type : "post",
    		url : "/gce/image",
    		data: {
    			name:$('#name').val(),
    			type:$('#type').val(),
    			descn:$('#descn').val(),
    			tag:$('#tag').val(),
    			url:$('#url').val(),
    			status:$('#status').val(),
    			owner:$('#owner').val()
    		},
    		success : function(data) {
    			location.href = "/list/gce/image";
    		}
    	})
     });
}
function getUser(){
	var select = $("#owner");
	$.ajax({
    		cache:false,
    		type : "get",
    		url : "/gce/user",
    		success : function(data) {
    			var users = data.data;
    			for (var i=0,len = users.length;i < len; i++){
    				var option =$("<option value=\""+users[i].id+"\">"+users[i].userName+"</option>");
    				option.appendTo(select);
    			}
    			initSelect();
    		}
    	})
}
function initSelect(){
	var options = {
		allow_single_deselect:true,
		search_contains:true,
		no_results_text:"未找到匹配数据",
		disable_search:true,	
		width:'272px'
	}
	$('.chosen-select').chosen(options)
}

function delGceImage(obj){
		var gceImageId = $(obj).closest("tr").find("input").val();
		function delCmd(){
			$.ajax({
				cache:false,
				type : "delete",
				url : "/gce/image/"+gceImageId,
				success : function(){
					location.href = "/list/gce/image";
				}
			})
		}
		confirmframe("删除镜像","删除"+$(obj).closest("tr").find("td:eq(1)").html()+"后可重新添加","您确定要删除?",delCmd);
}
function page_init(){
	$('[name = "popoverHelp"]').popover();
	queryByPage();
	formValidate();
	pageControl();
	 getUser();
}
