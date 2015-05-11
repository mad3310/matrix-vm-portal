var currentPage = 1; //第几页 
var recordsPerPage = 15; //每页显示条数
var queryBuildStatusrefresh;//刷新handler
	
$(function(){
	//初始化 
	page_init();
	
	/*动态加载界面下拉列表值*/
	var sltArray = [1,2,3,5,7,8,9,10,11,12,13,14];
	addSltOpt(sltArray,$("#containerStatus"));
	
	$(document).on('click', 'th input:checkbox' , function(){
		var that = this;
		$(this).closest('table').find('tr > td:first-child input:checkbox')
		.each(function(){
			this.checked = that.checked;
			$(this).closest('tr').toggleClass('selected');
		});
	});
	var mclusterId;
	var status;
	//modal显示创建进度
	
	$('#create-mcluster-status-modal').on('shown.bs.modal', function(){
		if(status == "2") {
			queryBuildStatusrefresh = setInterval(function() {  
				queryBuildStatus(mclusterId,"update");
			},5000);
		}
	}).on('hidden.bs.modal', function (e) {
		queryBuildStatusrefresh = window.clearInterval(queryBuildStatusrefresh);
		location.reload();
	});
	
	$("#mclusterSearch").click(function(){
		queryByPage();
	});
	$("#mclusterClearSearch").click(function(){
		var clearList = ["containerName","Physicalcluster","containeruser","containerStatus"];
		clearSearch(clearList);
	});
	
	enterKeydown($(".page-header > .input-group input"),queryByPage);
});

function queryByPage() {
	var mclusterName = $("#containerName").val()?$("#containerName").val():'';
	var hclusterName = $("#Physicalcluster").val()?$("#Physicalcluster").val():'';
	var userName = $("#containeruser").val()?$("#containeruser").val():'';
	var status = $("#containerStatus").val()?$("#containerStatus").val():'';
	var queryCondition = {
			'currentPage':currentPage,
			'recordsPerPage':recordsPerPage,
			'mclusterName':mclusterName,
			'hclusterName':hclusterName,
			'userName':userName,
			/*'createTime':createTime,*/
			'status':status
		}
	
	$("#tby tr").remove();
	getLoading();
	$.ajax({
		cache:false,
		type : "get",
		url : queryUrlBuilder("/container",queryCondition),
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
						+  "<a class=\"link\" target=\"_blank\" href=\"/detail/mcluster/" + array[i].id+"\">"+array[i].mclusterName+"</a>"
						+ "</td>");
				if(array[i].hcluster){
					var td3 = $("<td>"
							+ "<a class=\"link\" target=\"_blank\" href=\"/detail/hcluster/" + array[i].hclusterId+"\">"+array[i].hcluster.hclusterNameAlias+"</a>"
							+ "</td>");
				} else {
					var td3 = $("<td> </td>");
				} 
				var type = "";
				if(array[i].type) {
					type="后台创建";
				} else {
					type = "系统创建";
				}
				var td4 = $("<td>"
						+ type
						+ "</td>");
				
				var userName='system';
				if(array[i].createUserModel) {
					userName = array[i].createUserModel.userName;
				}
				var td5 = $("<td>"
						+ userName
						+ "</td>");
				var td6 = $("<td>"
						+ date('Y-m-d H:i:s',array[i].createTime)
						+ "</td>");
				if(array[i].status == 2){
					var td7 = $("<td>"
							+"<a name=\"buildStatusBoxLink\" data-toggle=\"modal\" data-target=\"#create-mcluster-status-modal\" style=\"cursor:pointer; text-decoration:none;\">"
							+"<i class=\"ace-icon fa fa-spinner fa-spin green bigger-125\"/>"
							+"创建中...</a>"
							+ "</td>");
				}else if(array[i].status == 3){
					var td7 = $("<td>"
							+"<a name=\"buildStatusBoxLink\" data-toggle=\"modal\" data-target=\"#create-mcluster-status-modal\" style=\"cursor:pointer; text-decoration:none;\">"
							+translateStatus(array[i].status)
							+"</a>"
							+ "</td>");
				}else{
					var td7 = $("<td>"
							+translateStatus(array[i].status)
							+ "</td>");
					
				}
				
				if(array[i].status == 3){
					var td8 = $("<td>"
							+"<div class=\"hidden-sm hidden-xs  action-buttons\">"
							+"<a class=\"green\" href=\"#\" onclick=\"startMcluster(this)\" onfocus=\"this.blur();\" title=\"启动\" data-toggle=\"tooltip\" data-placement=\"right\">"
							+"<i class=\"ace-icon fa fa-play-circle-o bigger-130\"></i>"
							+"</a>"
							+"<a class=\"blue\" href=\"#\" onclick=\"stopMcluster(this)\" onfocus=\"this.blur();\" title=\"停止\" data-toggle=\"tooltip\" data-placement=\"right\">"
								+"<i class=\"ace-icon fa fa-power-off bigger-120\"></i>"
							+"</a>"
							+"<a class=\"red\" href=\"#\" onclick=\"deleteMcluster(this);\" onfocus=\"this.blur();\"  title=\"删除\" data-toggle=\"tooltip\" data-placement=\"right\">"
							+"<i class=\"ace-icon fa fa-trash-o bigger-120\"></i>"
							+"</a>"
							+"</div>"
							+ "</td>"
					);
				}else{
					var td8 = $("<td>"
							+"<div class=\"hidden-sm hidden-xs  action-buttons\">"
							+"<a class=\"green\" href=\"#\" onclick=\"startMcluster(this)\" onfocus=\"this.blur();\" title=\"启动\" data-toggle=\"tooltip\" data-placement=\"right\">"
							+"<i class=\"ace-icon fa fa-play-circle-o bigger-130\"></i>"
							+"</a>"
							+"<a class=\"blue\" href=\"#\" onclick=\"stopMcluster(this)\" onfocus=\"this.blur();\" title=\"停止\" data-toggle=\"tooltip\" data-placement=\"right\">"
							+"<i class=\"ace-icon fa fa-power-off bigger-120\"></i>"
							+"</a>"
							/*+"<a class=\"red\" href=\"#\" onclick=\"deleteMcluster(this);\" onfocus=\"this.blur();\"  title=\"删除\" data-toggle=\"tooltip\" data-placement=\"right\">"
							+"<i class=\"ace-icon fa fa-trash-o bigger-120\"></i>"
							+"</a>"*/
							+"</div>"
							+ "</td>"
					);
				}
				
					
				if(array[i].status == 3||array[i].status == 4||array[i].status == 14){
					var tr = $("<tr class=\"default-danger\"></tr>");
				}else if(array[i].status == 5||array[i].status == 13){
					var tr = $("<tr class=\"warning\"></tr>");
				}else{
					var tr = $("<tr></tr>");
				}
				
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

function page_init(){
	queryByPage();
	pageControl();
	$('[name = "popoverHelp"]').popover();
}
