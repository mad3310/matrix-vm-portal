var currentPage = 1; //第几页 
var recordsPerPage = 15; //每页显示条数
var queryBuildStatusrefresh;//刷新handler
	
$(function(){
	//初始化 
	page_init();
	$('[name = "popoverHelp"]').popover();
	$(document).on('click', 'th input:checkbox' , function(){
		var that = this;
		$(this).closest('table').find('tr > td:first-child input:checkbox')
		.each(function(){
			this.checked = that.checked;
			$(this).closest('tr').toggleClass('selected');
		});
	});
});

function queryByPage(currentPage,recordsPerPage) {
	$("#tby tr").remove();
	var mclusterName = $("#nav-search-input").val()?$("#nav-search-input").val():'null';
	getLoading();
	$.ajax({
		cache:false,
		type : "get",
		url : "/hcluster/" + currentPage + "/" + recordsPerPage + "/" + mclusterName,
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
								+"<input name=\"hcluster_id\" value= \""+array[i].id+"\" type=\"checkbox\" class=\"ace\"/>"
								+"<span class=\"lbl\"></span>"
								+"</label>"
							+"</td>");
				var td2 = $("<td>"
						+ array[i].hclusterNameAlias
						+ "</td>");
				var td3 = $("<td>"
						+  "<a href=\"/detail/hcluster/" + array[i].id+"\">"+array[i].hclusterName+"</a>"
						+ "</td>");
				var td4 = $("<td>"
						+ date('Y-m-d H:i:s',array[i].createTime)
						+ "</td>");
				var td5 = $("<td><a>"
						+ translateStatus(array[i].status)
						+ "</a></td>");
				var td6 = $("<td>"
						+"<div class=\"hidden-sm hidden-xs  action-buttons\">"
						+"<a class=\"red\" href=\"#\" onclick=\"deleteHcluster(this)\" data-toggle=\"modal\" data-target=\"#\">"
							+"<i class=\"ace-icon fa fa-trash-o bigger-120\"></i>"
						+"</a>"
						+"</div>"
						+ "</td>"
				);
					
				if(array[i].status == 3){
					var tr = $("<tr class=\"default-danger\"></tr>");
				}else{
					var tr = $("<tr></tr>");
				}
				
				tr.append(td1).append(td2).append(td3).append(td4).append(td5).append(td6);
				tr.appendTo(tby);
			}//循环json中的数据 
			
			if (totalPages <= 1) {
				$("#pageControlBar").hide();
			} else {
				$("#pageControlBar").show();
				$("#totalPage_input").val(totalPages);
				$("#currentPage").html(currentPage);
				$("#totalRows").html(data.data.totalRecords);
				$("#totalPage").html(totalPages);
			}
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
	
//创建Container集群表单验证
function formValidate() {
	$("#create-hcluster-form").bootstrapValidator({
	  message: '无效的输入',
         feedbackIcons: {
             valid: 'glyphicon glyphicon-ok',
             invalid: 'glyphicon glyphicon-remove',
             validating: 'glyphicon glyphicon-refresh'
         },
         fields: {
       	  hclusterName: {
                 validMessage: '请按提示输入',
                 validators: {
                     notEmpty: {
                         message: '物理机集群名称不能为空!'
                     },
			          stringLength: {
			              max: 40,
			              message: '物理机集群名过长'
			          },regexp: {
		                  regexp: /^([a-zA-Z_0-9]*)$/,
  		                  message: "请输入字母数字或'_'"
                 	  },
                 	 remote: {
	                        message: '物理机集群名已存在!',
	                        url: "/hcluster/validate"
	                    }
	             }
         	}	
         }
     }).on('error.field.bv', function(e, data) {
    	 $('#create-hcluster-botton').addClass("disabled");
     }).on('success.field.bv', function(e, data) {
    	 $('#create-hcluster-botton').removeClass("disabled");
     });
}

function createHcluster(){
	$.ajax({
		cache:false,
		type : "post",
		url : "/hcluster",
		data :$('#create-hcluster-form').serialize(),
		success:function (data){
			if(error(data)) return;
			$('#create-hcluster-form').find(":input").not(":button,:submit,:reset,:hidden").val("").removeAttr("checked").removeAttr("selected");
			$('#create-hcluster-form').data('bootstrapValidator').resetForm();
			$('#create-hcluster-botton').addClass('disabled');
			$('#create-hcluster-modal').modal('hide');
			//延时一秒刷新列表
			setTimeout("queryByPage(currentPage, recordsPerPage)",1000);
		}
	});
}

function deleteHcluster(obj){
	var tr = $(obj).parents("tr");
	var hclusterId =tr.find('[name="hcluster_id"]').val();
	$.ajax({
		cache:false,
		url:'/hcluster/isExistHostOnHcluster/validate',
		type:'post',
		data:{ 'hclusterId' : hclusterId },
		success:function(data){
			if(data.valid){  //data.valid为true时可删除
				function deleteCmd(){
					$.ajax({
						cache:false,
						url:'/hcluster/'+hclusterId,
						type:'delete',
						success:function(data){
							if(error(data)) return;
							queryByPage(currentPage, recordsPerPage);
						}
					});
				}
				confirmframe("删除物理机集群","删除物理机集群后将不能恢复!","您确定要删除?",deleteCmd);
			}else{
				warn("该集群中含有物理机,删除完物理机后,才能执行此操作!",3000);
			}
		}
	});
}

function page_init(){
	queryByPage(currentPage, recordsPerPage);
	searchAction();
	formValidate();
	pageControl();
}
