<%@ page language="java" pageEncoding="UTF-8"%>
<div class="page-content-area">
	<div class="page-header">
		<h1> 
			host列表
			<!-- <small> 
				<i class="ace-icon fa fa-angle-double-right"></i> 
				overview &amp; stats
			</small> -->
		</h1>
	</div>
	<!-- /.page-header -->
	<div class="row">
		<div class="widget-box widget-color-blue ui-sortable-handle col-xs-12">
			<div class="widget-header">
				<h5 class="widget-title">host列表</h5>
				<div class="widget-toolbar no-border">
					<button class="btn btn-xs btn-success bigger" data-toggle="modal" data-target="#create-mcluster-modal">
						<i class="ace-icont fa fa-plus"></i>
						 创建host
					</button>
					<button class="btn btn-xs btn-success bigger" onclick="deleteHost()" >
						<i class="ace-icont fa fa-plus"></i>
						 删除host
					</button>				
					<button class="btn btn-xs btn-success bigger" onclick="updateHostBefore()" data-toggle="modal" data-target="#create-mcluster-update">
						<i class="ace-icont fa fa-plus"></i>
						 修改host
					</button>
				</div>
				
			</div>
			<div class="widget-body">
				<div class="widget-main no-padding">
					<table id="mcluster_list" class="table table-striped table-bordered table-hover">
					<thead>
						<tr>
							<th class="center">
								<label class="position-relative">
									<input type="checkbox" class="ace" />
									<span class="lbl"></span>
								</label>
							</th>
							<th>host名称</th>
							<th>
								hostIP
							</th>
							<th>
								创建时间 
							</th>
							<th class="hidden-480">当前状态</th>
							<!-- <th></th> -->
						</tr>
					</thead>
						<tbody id="tby">							
						</tbody>
					</table>
				</div>
			</div>
		</div>
		<div class="modal fade" id="create-mcluster-modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="margin-top:157px">
			<div class="modal-dialog">
				<div class="modal-content">
					<form id="create-mcluster-form" name="create-mcluster-form" class="form-horizontal" role="form" action="${ctx}/host/" method="post">
					<div class="col-xs-12">
						<h4 class="lighter">
							<a href="#modal-wizard" data-toggle="modal" class="blue"> 创建host </a>
						</h4>
						<div class="widget-box">
							<div class="widget-body">
								<div class="widget-main">
									<div class="form-group">
										<label class="col-sm-2 control-label" for="mcluster_name">host名称</label>
										<div class="col-sm-8">
											<input class="form-control" name="hostName" id="hostName" type="text" />
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label" for="mcluster_name">hostIp</label>
										<div class="col-sm-8">
											<input class="form-control" name="hostIp" id="hostIp" type="text" />
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-sm btn-default" data-dismiss="modal">关闭</button>
						<button id="create-mcluster-botton" type="button" class="btn btn-sm btn-primary" onclick="createMcluster()">创建</button>
					</div>
				</form>
				</div>
			</div>
		</div>
		
				<div class="modal fade" id="create-mcluster-update" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="margin-top:157px">
			<div class="modal-dialog">
				<div class="modal-content">
					<form id="create-mcluster-updateForm" name="create-mcluster-updateForm" class="form-horizontal" role="form">
					<div class="col-xs-12">
						<h4 class="lighter">
							<a href="#modal-wizard" data-toggle="modal" class="blue"> 修改host </a>
						</h4>
						<div class="widget-box">
							<div class="widget-body">
								<div class="widget-main">
									<div class="form-group">
										<label class="col-sm-2 control-label" for="mcluster_name">hostId</label>
										<div class="col-sm-8">
											<input class="form-control" name="id" id="id" type="text" />
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label" for="mcluster_name">hostName</label>
										<div class="col-sm-8">
											<input class="form-control" name="hostNameUpdate" id="hostNameUpdate" type="text" />
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-sm btn-default" data-dismiss="modal">关闭</button>
						<button id="create-mcluster-botton" type="button" class="btn btn-sm btn-primary" onclick="updateHost()">修改</button>
					</div>
				</form>
				</div>
			</div>
		</div>
		
		<div class="modal fade bs-example-modal-lg"  id="create-mcluster-status-modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		  <div class="modal-dialog modal-lg">
		    <div class="modal-content">
		      <div class="modal-header">
		        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
		        <h4 class="modal-title" id="buildStatusHeader">
		        	<i class="ace-icon fa fa-spinner fa-spin green bigger-125"></i>
		        	创建中...
		        </h4>
		      </div>
		      <div class="modal-body">
		        <table id="mcluster_list" class="table">
						<thead>
							<tr class="info">
								<th width="3%">#</th>
								<th width="30%">操作</th>
								<th width="15%">开始时间</th>
								<th width="15%">结束时间</th>
								<th>信息</th>
								<th width="8%">结果  </th>
							</tr>
						</thead>
						<tbody id="build_status_tby">
						</tbody>
					</table>
		      </div>
		    </div><!-- /.modal-content -->
		  </div><!-- /.modal-dialog -->
		</div><!-- /.modal -->
	</div>
</div>
<!-- /.page-content-area -->

<script src="${ctx}/static/scripts/date-transform.js"></script>
<script type="text/javascript">
var currentPage = 1; //第几页 
var recordsPerPage = 10; //每页显示条数
var currentSelectedLineDbName = 1;
	
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
	
	//modal显示创建进度
	$(document).on('click', "[name='buildStatusBoxLink']" , function(){
		var mclusterId = $(this).closest('tr').find('input').val();
		if($(this).html().indexOf("正常")>=0){
			$('#buildStatusHeader').html("创建成功");
		}else if($(this).html().indexOf("创建中")>=0){
			$('#buildStatusHeader').html("<i class=\"ace-icon fa fa-spinner fa-spin green bigger-125\"></i>创建中...");
		}else if($(this).html().indexOf("创建失败")>=0){
			$('#buildStatusHeader').html("<font color=\"red\">创建失败</font>");
		}
		queryBuildStatus(mclusterId);
		/* $('body').everyTime('5s','A',function(){
			alert("dfadfa");
		}); */
	});
});	
function queryByPage(currentPage,recordsPerPage) {
	$("#tby tr").remove();
	var hostName = $("#nav-search-input").val()?$("#nav-search-input").val():'null';
	$.ajax({ 
		type : "get",
		url : "${ctx}/host/" + currentPage + "/" + recordsPerPage+"/" + hostName,
		dataType : "json", /*这句可用可不用，没有影响*/
		contentType : "application/json; charset=utf-8",
		success : function(data) {
			var array = data.data.data;
			var tby = $("#tby");
			var totalPages = data.data.totalPages;
			
			function translateStatus(status){
				if(status == 0){
					return "待审核";
				}else if(status  == 1){
					return "正常";
				}else if(status  == 2){
					return "创建中...";
				}else if(status  == 4){
					return "未通过";
				}else{
					return "创建失败";
				}
			}
			
			for (var i = 0, len = array.length; i < len; i++) {
				var td0 = $("<input class=\"hidden\" type=\"text\" value=\""+array[i].id+"\"\> ");
				var td1 = $("<td class=\"center\">"
						+"<label class=\"position-relative\">"
						+"<input name=\"host_id\" value= \""+array[i].id+"\" type=\"checkbox\" class=\"ace\"/>"
						+"<span class=\"lbl\"></span>"
						+"</label>"
					+"</td>");
				var td2;
				if(array[i].status == 1){
					td2 = $("<td>"
							+ "<a href=\"${ctx}/db/detail/"+array[i].id+"\">"+array[i].hostName+"</a>"
							+ "</td>");
				}else if(array[i].status == 0 ||array[i].status == 3){	
					td2 = $("<td>"
							+ "<a href=\"${ctx}/db/audit/"+array[i].id+"\">"+array[i].hostName+"</a>"
							+ "</td>");
				}else{
					td2 = $("<td>"
							+ "<a style=\"text-decoration:none;\">"+array[i].hostName+"</a>"
							+ "</td>");
				}
				if(array[i].hostIp){
					var td3 = $("<td>"
							+ array[i].hostIp
							+ "</td>");
				} else {
					var td3 = $("<td> </td>");
				}
				var td4 = $("<td>"
						+ array[i].createTime
						+ "</td>");
				if(array[i].status == 4){
					var td5 = $("<td>"
							+"<a href=\"#\" name=\"dbRefuseStatus\" rel=\"popover\" data-container=\"body\" data-toggle=\"popover\" data-placement=\"top\" data-trigger='hover' data-content=\""+ array[i].auditInfo + "\" style=\"cursor:pointer; text-decoration:none;\">"
							+ translateStatus(array[i].status)
							+"</a>"
							+ "</td>");
				}else if(array[i].status == 2){
					var td5 = $("<td>"
							+"<a name=\"buildStatusBoxLink\" data-toggle=\"modal\" data-target=\"#create-mcluster-status-modal\" style=\"cursor:pointer; text-decoration:none;\">"
							+"<i class=\"ace-icon fa fa-spinner fa-spin green bigger-125\" />"
							+"创建中...</a>"
							+ "</td>");
				}else{
					var td5 = $("<td>"
							+ translateStatus(array[i].status)
							+ "</td>");
				}
				
				/* var td6 = $("<td>"
							+"<div class=\"hidden-sm hidden-xs action-buttons\">"
								+"<a class=\"blue\" href=\"#\">"
									+"<i class=\"ace-icon fa fa-play-circle-o bigger-130\"></i>"
								+"</a>"
								+"<a class=\"green\" href=\"#\">"
									+"<i class=\"ace-icon fa  fa-power-off bigger-130\"></i>"
								+"</a>"
								+"<a class=\"red\" href=\"#\">"
									+"<i class=\"ace-icon fa fa-trash-o bigger-130\"></i>"
								+"</a>"
							+"</div>"
							+"<div class=\"hidden-md hidden-lg\">"
								+"<div class=\"inline position-relative\">"
									+"<button class=\"btn btn-minier btn-yellow dropdown-toggle\" data-toggle=\"dropdown\" data-position=\"auto\">"
										+"<i class=\"ace-icon fa fa-caret-down icon-only bigger-120\"></i>"
									+"</button>"
									+"<ul class=\"dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close\">"
										+"<li>"
											+"<a href=\"#\" class=\"tooltip-info\" data-rel=\"tooltip\" title=\"View\">"
												+"<span class=\"blue\">"
													+"<i class=\"ace-icon fa fa-search-plus bigger-120\"></i>"
												+"</span>"
											+"</a>"
										+"</li>"
										+"<li>"
											+"<a href=\"#\" class=\"tooltip-success\" data-rel=\"tooltip\" title=\"Edit\">"
												+"<span class=\"green\">"
													+"<i class=\"ace-icon fa fa-pencil-square-o bigger-120\"></i>"
												+"</span>"
											+"</a>"
										+"</li>"
										+"<li>"
											+"<a href=\"#\" class=\"tooltip-error\" data-rel=\"tooltip\" title=\"Delete\">"
												+"<span class=\"red\">"
													+"<i class=\"ace-icon fa fa-trash-o bigger-120\"></i>"
												+"</span>"
											+"</a>"
										+"</li>"
									+"</ul>"
								+"</div>"
							+"</div>"
						+"</td>"
					);	 */
					
				if(array[i].status == 0){
					var tr = $("<tr class=\"warning\"></tr>");
				}else if(array[i].status == 3 || array[i].status == 4){
					var tr = $("<tr class=\"danger\"></tr>");
					
				}else{
					var tr = $("<tr></tr>");
				}
				
				tr.append(td0).append(td1).append(td2).append(td3).append(td4).append(td5);//.append(td6);
				tr.appendTo(tby);
				
				$('[name = "dbRefuseStatus"]').popover();
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
		},
		error : function(XMLHttpRequest,textStatus, errorThrown) {
			$.gritter.add({
				title: '警告',
				text: errorThrown,
				sticky: false,
				time: '5',
				class_name: 'gritter-warning'
			});
	
			return false;
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
//查询db创建过程
function queryBuildStatus(mclusterId) {
	$("#build_status_tby tr").remove();
	$.ajax({
		type : "get",
		url : "${ctx}/mcluster/build/status/"+mclusterId,
		dataType : "json", /*这句可用可不用，没有影响*/
		success : function(data) {
			var array = data.data;
			var build_status_tby = $("#build_status_tby");
			
			for (var i = 0, len = array.length; i < len; i++) {
				var td1 = $("<td>"
						+ array[i].step
						+"</td>");
				var td2 = $("<td>"
						+ array[i].stepMsg
						+"</td>");
				if(array[i].startTime != null )
				{
					var td3 = $("<td>"
							+ date('Y-m-d H:i:s',array[i].startTime)
							+ "</td>");
				}else{
					var td3 = $("<td>\-</td>");
				}
				if(array[i].endTime != null)
				{
					var td4 = $("<td>"
							+ date('Y-m-d H:i:s',array[i].endTime)
							+ "</td>");
				}else{
					var td4 = $("<td>\-</td>");
				}
				if(array[i].msg != null)
				{
					var td5 = $("<td>"
							+ array[i].msg
							+ "</td>");
				}else{
					var td5 = $("<td>\-</td>");
				}
				
				if(array[i].status == "success"){
					var td6 = $("<td>"
							+"<a class=\"green\"><i class=\"ace-icon fa fa-check bigger-120\">成功</a>"
							+ "</td>");
				}else if(array[i].status == "fail"){
					var td6 = $("<td>"
							+"<a class=\"red\"><i class=\"ace-icon fa fa-times red bigger-120\">失败</a>"
							+ "</td>");
				}else if(array[i].status == "building"){
					var td6 = $("<td>"
							+"<a style=\"text-decoration:none;\" class=\"green\"><h5><i class=\"ace-icon fa fa-spinner fa-spin green bigger-120\"/>运行中</h5></a>"
							+ "</td>");
				}else{
					var td6 = $("<td>"
							+"<a class=\"orange\"><i class=\"ace-icon fa fa-coffee orange bigger-120\">等待</a>"
							+ "</td>");
				}
					
				if(array[i].status == "FAIL"){
					var tr = $("<tr class=\"danger\"></tr>");
				}else{
					var tr = $("<tr></tr>");
				}
				
				tr.append(td1).append(td2).append(td3).append(td4).append(td5).append(td6);
				tr.appendTo(build_status_tby);
			}
		},
		error : function(XMLHttpRequest,textStatus, errorThrown) {
			$.gritter.add({
				title: '警告',
				text: errorThrown,
				sticky: false,
				time: '5',
				class_name: 'gritter-warning'
			});
			return false;
		}
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

function createMcluster(){
	$.ajax({
		type : "post",
		url : "${ctx}/host/",
		data :$('#create-mcluster-form').serialize()
	});

	$('#create-mcluster-botton').addClass('disabled');
	$('#create-mcluster-modal').modal('hide');
	//延时一秒刷新列表
	setTimeout("queryByPage(currentPage, recordsPerPage)",1000);
}
function updateHostBefore(){
	    var ids = $("[name='host_id']:checked");
	    var str="";
		ids.each(function(){
				str +=($(this).val());
	 			flag += 1;			
		});
		
}


function updateHost(){
	var id = $("#id").val();
	var hostName = $("#hostNameUpdate").val();
	var data=id+"/"+hostName;
	$.ajax({
		type : "put",
		url : "${ctx}/host/"+data,
	});

	$('#create-mcluster-botton').addClass('disabled');
	$('#create-mcluster-update').modal('hide');
	//延时一秒刷新列表
	setTimeout("queryByPage(currentPage, recordsPerPage)",1000);
}
function deleteHostName(str){
	$.ajax({
	    url: '/host/' + str ,
	    type: 'DELETE',
	    dataType: 'HTML',
	    success: function(data) {

	    }
	});
}
$.ajax({
    url: '/host/' + str ,
    type: 'DELETE',
    dataType: 'HTML',
    success: function(data) {

    }
});
function deleteHost() {
		var ids = $("[name='host_id']:checked");
		var str="";
		var flag = 0; //0:无数据 -1:错误
 		ids.each(function(){
 				str +=($(this).val());
 	 			flag += 1;			
 		});
		if(flag > 0) {
			deleteHostName(str);
			setTimeout("queryByPage(currentPage, recordsPerPage)",500);
		} else if (flag == 0){
			$.gritter.add({
			title: '警告',
			text: '请选择数据！',
			sticky: false,
			time: '5',
			class_name: 'gritter-warning'
		});

		return false;
		}else{
			return false;
		}
	}
function page_init(){
	queryByPage(currentPage, recordsPerPage);
	searchAction();
	pageControl();
}
</script>
