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
	
});

function queryByPage() {
	var containerName = $("#containerName").val()?$("#containerName").val():'';
	var  ipAddr= $("#ipAddr").val()?$("#ipAddr").val():'';
	var status = $("#containerStatus").val()?$("#containerStatus").val():'';
	var queryCondition = {
		'currentPage':currentPage,
		'recordsPerPage':recordsPerPage,
		// 'containerName':containerName,
		// 'ipAddr':ipAddr,
		// /*'createTime':createTime,*/
		// 'status':status
	}
	$("#tby tr").remove();
	getLoading();
	$.ajax({
		cache:false,
		type : "get",
		// url : "/slb/container",
		url : queryUrlBuilder("/slb/container",queryCondition),
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
						+  "<a class=\"link\" href=\"/detail/slb/cluster/" + array[i].id+"\">"+array[i].containerName+"</a>"
						+ "</td>");
				if(array[i].slbCluster){
					var td3 = $("<td class='hidden-480'>"
							+ "<a class=\"link\" href=\"/detail/slb/cluster/" + array[i].slbClusterId+"\">"+array[i].slbCluster.clusterName+"</a>"
							+ "</td>");//href=\"/detail/mcluster/" + array[i].slbClusterId+"\"
				} else {
					var td3 = $("<td class='hidden-480'> </td>");
				} 
				if(array[i].hcluster){
					var td4 = $("<td class='hidden-480'>"
							+ "<a class=\"link\" href=\"/detail/hcluster/" + array[i].slbCluster.hclusterId+"\">"+array[i].hcluster.hclusterNameAlias+"</a>"
							+ "</td>");//href=\"/detail/hcluster/" + array[i].slbCluster.hclusterId+"\"
				} else {
					var td4= $("<td class='hidden-480'> </td>");
				}
				var td5 = $("<td>"
						+ array[i].ipAddr
						+ "</td>");
				var td6 = $("<td>"
						+ array[i].hostIp
						+ "</td>");
				var td9 = $("<td class='hidden-480'>"
						+ date('Y-m-d H:i:s',array[i].createTime)
						+ "</td>");
				var td7 = $("<td>"
						+translateStatus(array[i].status)
						+ "</td>");
					
				if(array[i].status == 3||array[i].status == 4||array[i].status == 14){
					var tr = $("<tr class=\"default-danger\"></tr>");
				}else if(array[i].status == 5||array[i].status == 13){
					var tr = $("<tr class=\"warning\"></tr>");
				}else{
					var tr = $("<tr></tr>");
				}
				
				tr.append(td1).append(td2).append(td3).append(td4).append(td5).append(td6).append(td9).append(td7);
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
