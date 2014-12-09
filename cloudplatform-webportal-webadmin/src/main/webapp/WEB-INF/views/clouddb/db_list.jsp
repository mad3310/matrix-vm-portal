<%@ page language="java" pageEncoding="UTF-8"%>
<div class="page-content-area">
	<div class="page-header">
		<h3>数据库列表	</h3>
	    <div class="input-group pull-right">
		<form class="form-inline">
			<!-- <div class="form-group">
				<select class="form-control">
					<option value="0">请选择查询条件</option>
					<option value="1">按数据库名称查询</option>
					<option value="2">按所属物理集群查询</option>
					<option value="3">按当前状态查询</option>
				</select>
			</div> -->
			<div class="form-group">
				<input type="text" class="form-control" placeholder="请输入关键字">
			</div>
			<div class="form-group">
				<input type="date" class="form-control" placeholder="yyyy-MM-dd">
			</div>
			<button class="btn btn-sm btn-default" type="button"><i class="ace-icon fa fa-search"></i>搜索</button>
			<button class="btn btn-sm btn-info" type="button" id="dbadvancedSearch">高级搜索</button>
		</form>
	</div>	
	</div>
    <!-- /.page-header -->
    <div id="dbadvancedSearch-div" style="display:none;overflow:hidden;">
		<form class="form-horizontal" role="form">
            					<div class="form-group col-md-4 col-sm-6 col-xs-12">
            						<lable class="col-md-6 control-label" for="dbName"><b>数据库名称</b> <i class="ace-icon fa fa-database blue bigger-125"></i></lable>
            						<div class="col-md-6">
            							<input type="text" class="form-control" id="dbName" placeholder="数据库名称">
            						</div>
            					</div>
            					<div class="form-group col-md-4 col-sm-6 col-xs-12">
            						<lable class="col-md-6 control-label" for="dbMcluster"><b>所属Mcluster</b> <i class="ace-icon fa fa-info-circle blue bigger-125"></i></lable>
            						<div class="col-md-6">
            							<input type="text" class="form-control" id="dbMcluster" placeholder="所属Mcluster">
            						</div>
            					</div>
            					<div class="form-group col-md-4 col-sm-6 col-xs-12">
            						<lable class="col-md-6 control-label" for="dbPhyMcluster"><b>所属物理机集群</b> <i class="ace-icon fa fa-info-circle blue bigger-125"></i></lable>
            						<div class="col-md-6">
            							<input type="text" class="form-control" id="dbPhyMcluster" placeholder="所属物理机集群">
            						</div>
            					</div>
            					<div class="form-group col-md-4 col-sm-6 col-xs-12">
            						<lable class="col-md-6 control-label" for="dbuser"><b>所属用户</b> <i class="ace-icon fa fa-user blue bigger-125"></i></lable>
            						<div class="col-md-6">
            							<input type="text" class="form-control" id="dbuser" placeholder="所属用户">
            						</div>
            						
            					</div>
            					
            					<div class="form-group col-md-4 col-sm-6 col-xs-12">
            						<lable class="col-md-6 control-label" for="PhyMechineDate"><b>创建时间</b> <i class="ace-icon fa fa-calendar blue bigger-125"></i></lable>
            						<div class="col-md-6">
            							<input type="date" class="form-control" id="PhyMechineDate" placeholder="创建时间">
            						</div>
            						
            					</div>
            					<div class="form-group col-md-4 col-sm-6 col-xs-12">
            						<lable class="col-md-6 control-label" for="PhyMechineRunState"><b>运行状态</b> <i class="ace-icon fa fa-cog blue bigger-125"></i></lable>
            						<div class="col-md-6">
            							<select class="form-control" id="PhyMechineRunState">
            								<option value="">创建失败</option>
            								<option value="">未审核</option>
            								<option value="">。。。</option>
            							</select>
            						</div>
            					</div>
            					<div class="form-group">
    						<div class="col-sm-offset-2 col-sm-10">
    							<button class="btn btn-sm btn-info pull-right" type="button" style="margin-left:5px;"><i class="ace-icon fa fa-search"></i>搜索</button>
    							<button class="btn btn-sm btn-default pull-right" type="reset"><i class="ace-icon fa fa-refresh"></i>清空</button>
    							
    						</div>
    					</div>
            				</form>
	</div>
	<script type="text/javascript">
		$(function(){
			bt_toggle('dbadvancedSearch');
		})
	</script>
            <!-- <div class="modal fade" id="dbadvancedSearch">
            	<div class="modal-dialog">
            		<div class="modal-content">
            			<div class="modal-header">
            				<button type="button" class="close" data-dismiss="modal">
            					<span aria-hidden="true"><i class="ace-icon fa fa-times-circle"></i></span>
            					<span class="sr-only">关闭</span>
            				</button>
            				<h4 class="modal-title">高级搜索</h4>
            			</div>
            			<div class="modal-body">
            				<form class="form-horizontal" role="form">
            					
            					<div class="form-group">
            						<lable class="col-sm-4 control-label" for="dbName"><b>数据库名称</b></lable>
            						<div class="col-sm-7">
            							<input type="text" class="form-control" id="dbName" placeholder="数据库名称">
            						</div>
            						<label class="control-label"><i class="ace-icon fa fa-database blue bigger-125"></i></label>
            					</div>
            					<div class="form-group">
            						<lable class="col-sm-4 control-label" for="dbMcluster"><b>所属Mcluster</b></lable>
            						<div class="col-sm-7">
            							<input type="text" class="form-control" id="dbMcluster" placeholder="所属Mcluster">
            						</div>
            						<label class="control-label"><i class="ace-icon fa fa-info-circle blue bigger-125"></i></label>
            					</div>
            					<div class="form-group">
            						<lable class="col-sm-4 control-label" for="dbPhyMcluster"><b>所属物理机集群</b></lable>
            						<div class="col-sm-7">
            							<input type="text" class="form-control" id="dbPhyMcluster" placeholder="所属物理机集群">
            						</div>
            						<label class="control-label"><i class="ace-icon fa fa-info-circle blue bigger-125"></i></label>
            					</div>
            					<div class="form-group">
            						<lable class="col-sm-4 control-label" for="dbuser"><b>所属用户</b></lable>
            						<div class="col-sm-7">
            							<input type="text" class="form-control" id="dbuser" placeholder="所属用户">
            						</div>
            						<label class="control-label"><i class="ace-icon fa fa-user blue bigger-125"></i></label>
            					</div>
            					
            					<div class="form-group">
            						<lable class="col-sm-4 control-label" for="PhyMechineDate"><b>创建时间</b></lable>
            						<div class="col-sm-7">
            							<input type="date" class="form-control" id="PhyMechineDate" placeholder="创建时间">
            						</div>
            						<label class="control-label"><i class="ace-icon fa fa-calendar blue bigger-125"></i></label>
            					</div>
            					<div class="form-group">
            						<lable class="col-sm-4 control-label" for="PhyMechineRunState"><b>运行状态</b></lable>
            						<div class="col-sm-7">
            							<select class="form-control" id="PhyMechineRunState">
            								<option value="">创建失败</option>
            								<option value="">未审核</option>
            								<option value="">。。。</option>
            							</select>
            						</div>
            						<label class="control-label"><i class="ace-icon fa fa-cog blue bigger-125"></i></label>
            					</div>
            				</form>
            			</div>
            			<div class="modal-footer">
            			<button type="button" class="btn btn-sm btn-default" data-dismiss="modal">取 消 </button>
            			<button type="button" class="btn btn-sm btn-info">搜索</button>
            			</div>
            		</div>
            	</div>
            </div> -->
	<div class="row">
		<div class="widget-box widget-color-blue ui-sortable-handle col-xs-12">
			<div class="widget-header">
				<h5 class="widget-title">数据库列表</h5>
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
							<th>数据库名称</th>
							<th>所属Mcluster</th>
							<th>所属物理机集群</th>
							<th>所属用户</th>
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
		<div id="pageControlBar">
			<input type="hidden" id="totalPage_input" />
			<ul class="pager">
				<li><a href="javascript:void(0);" id="firstPage">&laquo首页</a></li>
				<li><a href="javascript:void(0);" id="prevPage">上一页</a></li>
				<li><a href="javascript:void(0);" id="nextPage">下一页</a></li>
				<li><a href="javascript:void(0);" id="lastPage">末页&raquo</a></li>
	
				<li><a>共<lable id="totalPage"></lable>页</a>
				</li>
				<li><a>第<lable id="currentPage"></lable>页</a>
				</li>
				<li><a>共<lable id="totalRows"></lable>条记录</a>
				</li>
			</ul>
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
								<th width="18%">操作</th>
								<th width="15%">开始时间</th>
								<th width="15%">结束时间</th>
								<th>信息</th>
								<th width="10%">结果  </th>
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
	var mclusterId;
	$(document).on('click', "[name='buildStatusBoxLink']" , function(){
		mclusterId = $(this).closest('tr').find('input').val();
		if($(this).html().indexOf("正常")>=0){
			$('#buildStatusHeader').html("创建成功");
			status = "1";
		}else if($(this).html().indexOf("创建中")>=0){
			$('#buildStatusHeader').html("<i class=\"ace-icon fa fa-spinner fa-spin green bigger-125\"></i>创建中...");
			status = "2";
		}else if($(this).html().indexOf("创建失败")>=0){
			$('#buildStatusHeader').html("创建失败");
			status = "3";
		}
		queryBuildStatus(mclusterId,"new");
	});
	
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
});	
function queryByPage(currentPage,recordsPerPage) {
	$("#tby tr").remove();
	var dbName = $("#nav-search-input").val()?$("#nav-search-input").val():'null';
	$.ajax({ 
		type : "get",
		url : "${ctx}/db/" + currentPage + "/" + recordsPerPage+"/" + dbName,
		dataType : "json", /*这句可用可不用，没有影响*/
		contentType : "application/json; charset=utf-8",
		success : function(data) {
			error(data);
			var array = data.data.data;
			var tby = $("#tby");
			var totalPages = data.data.totalPages;
			
			for (var i = 0, len = array.length; i < len; i++) {
				var td0 = $("<input class=\"hidden\" type=\"text\" value=\""+array[i].mclusterId+"\"\> ");
				var td1 = $("<td class=\"center\">"
								+"<label class=\"position-relative\">"
								+"<input type=\"checkbox\" class=\"ace\"/>"
								+"<span class=\"lbl\"></span>"
								+"</label>"
							+"</td>");
				var td2;
				if(array[i].status == 6){
					td2 = $("<td>"
							+ "<a href=\"${ctx}/detail/db/"+array[i].id+"\">"+array[i].dbName+"</a>"
							+ "</td>");
				}else if(array[i].status == 0 ||array[i].status == 3){	
					td2 = $("<td>"
							+ "<a  class=\"danger\" href=\"${ctx}/audit/db/"+array[i].id+"\">"+array[i].dbName+"</a>"
							+ "</td>");
				}else{
					td2 = $("<td>"
							+ "<a style=\"text-decoration:none;\">"+array[i].dbName+"</a>"
							+ "</td>");
				}
				if(array[i].mcluster){
					var td3 = $("<td>"
 							+ array[i].mcluster.mclusterName
							+ "</td>");
				} else {
					var td3 = $("<td> </td>");
				}
				if(array[i].hcluster){
					var td4 = $("<td>"
 							+ array[i].hcluster.hclusterNameAlias
							+ "</td>");
				} else {
					var td4 = $("<td> </td>");
				}
				var td5 = $("<td>"
						+ array[i].user.userName
						+ "</td>");
				var td6 = $("<td>"
						+ date('Y-m-d H:i:s',array[i].createTime)
						+ "</td>");
				if(array[i].status == 4){
					var td7 = $("<td>"
							+"<a href=\"#\" name=\"dbRefuseStatus\" rel=\"popover\" data-container=\"body\" data-toggle=\"popover\" data-placement=\"top\" data-trigger='hover' data-content=\""+ array[i].auditInfo + "\" style=\"cursor:pointer; text-decoration:none;\">"
							+ translateStatus(array[i].status)
							+"</a>"
							+ "</td>");
				}else if(array[i].status == 2){
					var td7 = $("<td>"
							+"<a name=\"buildStatusBoxLink\" data-toggle=\"modal\" data-target=\"#create-mcluster-status-modal\" style=\"cursor:pointer; text-decoration:none;\">"
							+"<i class=\"ace-icon fa fa-spinner fa-spin green bigger-125\" />"
							+"创建中...</a>"
							+ "</td>");
				}else{
					var td7 = $("<td> <a>"
							+ translateStatus(array[i].status)
							+ "</a></td>");
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
					
				if(array[i].status == 0 ||array[i].status == 5||array[i].status == 13){
					var tr = $("<tr class=\"warning\"></tr>");
					
				}else if(array[i].status == 3 ||array[i].status == 4||array[i].status == 14){
					var tr = $("<tr class=\"default-danger\"></tr>");
					
				}else{
					var tr = $("<tr></tr>");
				}
				
				tr.append(td0).append(td1).append(td2).append(td3).append(td4).append(td5).append(td6).append(td7);
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
//查询Container集群创建过程
function queryBuildStatus(mclusterId,type) {	//type(update或new)
	if(type == "new"){
		$("#build_status_tby tr").remove();
	}
	$.ajax({
		type : "get",
		url : "${ctx}/build/mcluster/"+mclusterId,
		dataType : "json", /*这句可用可不用，没有影响*/
		success : function(data) {
			error(data);
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
				
				if(array[i].status == 1){
					var td6 = $("<td>"
							+"<a class=\"green\"><i class=\"ace-icon fa fa-check bigger-120\">成功</a>"
							+ "</td>");
				}else if(array[i].status == 0){
					var td6 = $("<td>"
							+"<a class=\"red\"><i class=\"ace-icon fa fa-times red bigger-120\">失败</a>"
							+ "</td>");
				}else if(array[i].status == 2){
					var td6 = $("<td>"
							+"<a style=\"text-decoration:none;\" class=\"green\"><h5><i class=\"ace-icon fa fa-spinner fa-spin green bigger-120\"/>运行中</h5></a>"
							+ "</td>");
				}else{
					var td6 = $("<td>"
							+"<a class=\"orange\"><i class=\"ace-icon fa fa-coffee orange bigger-120\">等待</a>"
							+ "</td>");
				}
					
				if(array[i].status == 0){
					var tr = $("<tr class=\"danger\"></tr>");
				}else{
					var tr = $("<tr></tr>");
				}
				
				tr.append(td1).append(td2).append(td3).append(td4).append(td5).append(td6);
				if(type == "new"){
					tr.appendTo(build_status_tby);
				}else{
					build_status_tby.find("tr:eq("+i+")").html(tr.html());
				}
			}
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
        	queryByPage(currentPage, recordsPerPage);
        }
    });
}
function page_init(){
	queryByPage(currentPage, recordsPerPage);
	searchAction();
	pageControl();
}
</script>
