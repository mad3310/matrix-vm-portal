<%@ page language="java" pageEncoding="UTF-8"%>
<!-- /section:settings.box -->
<div class="page-content-area">
	<div class="page-header">
		<h1> 
			Container集群列表
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
				<h5 class="widget-title">Container集群列表</h5>
				<div class="widget-toolbar no-border">
					<button class="btn btn-white btn-primary btn-xs" data-toggle="modal" onclick="queryHcluster()" data-target="#create-mcluster-modal">
						<i class="ace-icont fa fa-plus"></i>
						 创建Container集群
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
								<th>Container集群名称</th>
								<th>所属物理机集群</th>
								<th>类型</th>
								<th>所属用户</th>
								<th>创建时间 </th>
								<th class="hidden-480">当前状态</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody id="tby">
						</tbody>
					</table>
				</div>
			</div>
		</div>
		<div class="col-xs-3">
			<small><font color="gray">*注：点击Container集群名可查看详情.</font></small>
		</div>
		<div id="pageControlBar" class="col-xs-6">
			<input type="hidden" id="totalPage_input" />
			<ul class="pager">
				<li><a href="javascript:void(0);" id="firstPage">&laquo首页</a></li>
				<li><a href="javascript:void(0);" id="prevPage">上一页</a></li>
				<li><a href="javascript:void(0);" id="nextPage">下一页</a></li>
				<li><a href="javascript:void(0);" id="lastPage">末页&raquo</a></li>
	
				<li>共<lable id="totalPage"></lable>页
				</li>
				<li>第<lable id="currentPage"></lable>页
				</li>
				<li>共<lable id="totalRows"></lable>条记录
				</li>
			</ul>
		</div>
		
		<div class="modal fade" id="create-mcluster-modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="margin-top:157px">
			<div class="modal-dialog">
				<div class="modal-content">
					<form id="create-mcluster-form" name="create-mcluster-form" class="form-horizontal" role="form">
					<div class="col-xs-12">
						<h4 class="lighter">
							<a href="#modal-wizard" data-toggle="modal" class="blue"> 创建Container集群 </a>
						</h4>
						<div class="widget-box">
							<div class="widget-body">
								<div class="widget-main">
									<div class="form-group">
										<label class="col-sm-4 control-label" for="mcluster_name">Container集群名称</label>
										<div class="col-sm-6">
											<input class="form-control" name="mclusterName" id="mclusterName" type="text" />
										</div>
										<label class="control-label">
											<a name="popoverHelp" rel="popover" data-container="body" data-toggle="popover" data-placement="right" data-trigger='hover' data-content="请输入字母数字或'_'." style="cursor:pointer; text-decoration:none;">
												<i class="ace-icon fa fa-question-circle blue bigger-125"></i>
											</a>
										</label>
									</div>
									<div class="form-group">
										<label class="col-sm-4 control-label" for="hcluster">物理机集群</label>
										<div class="col-sm-6">
											<select class="form-control" name="hclusterId" id="hcluster_select">
											</select>
										</div>
										<label class="control-label" for="hcluster">
											<a id="hclusterHelp" name="popoverHelp" rel="popover" data-container="body" data-toggle="popover" data-placement="right" data-trigger='hover' data-content="请保证您的应用与数据库在同一地域,以保证连接速度." style="cursor:pointer; text-decoration:none;">
												<i class="ace-icon fa fa-question-circle blue bigger-125"></i>
											</a>
										</label>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-sm btn-default" data-dismiss="modal">关闭</button>
						<button id="create-mcluster-botton" type="button" class="btn btn-sm btn-primary disabled" onclick="createMcluster()">创建</button>
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
		<div id="dialog-confirm" class="hide">
			<div id="dialog-confirm-content" class="alert alert-info bigger-110">
				删除container集群将不能恢复！
			</div>
			<div class="space-6"></div>
			<p id="dialog-confirm-question" class="bigger-110 bolder center grey">
				您确定要删除?
			</p>
		</div>
	</div>
</div>
<!-- /.page-content-area -->

<link rel="stylesheet" href="${ctx}/static/styles/bootstrap/bootstrapValidator.min.css" />
<script src="${ctx}/static/scripts/bootstrap/bootstrapValidator.min.js"></script>

<script src="${ctx}/static/ace/js/jquery.dataTables.min.js"></script>
<script src="${ctx}/static/ace/js/jquery.dataTables.bootstrap.js"></script>

<script type="text/javascript">
var currentPage = 1; //第几页 
var recordsPerPage = 10; //每页显示条数
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
	var mclusterId;
	var status;
	//modal显示创建进度
	$(document).on('click', "[name='buildStatusBoxLink']" , function(){
		mclusterId = $(this).closest('tr').find('td:first input').val();
		
		if($(this).html().indexOf("正常")>=0){
			$('#buildStatusHeader').html("创建成功");
			status = "1";
		}else if($(this).html().indexOf("创建中")>=0){
			$('#buildStatusHeader').html("<i class=\"ace-icon fa fa-spinner fa-spin green bigger-125\"></i>创建中...");
			status = "2";
		}else if($(this).html().indexOf("创建失败")>=0){
			$('#buildStatusHeader').html("<font color=\"red\">创建失败</font>");
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
	var mclusterName = $("#nav-search-input").val()?$("#nav-search-input").val():'null';
	$.ajax({
		type : "get",
		url : "${ctx}/mcluster/" + currentPage + "/" + recordsPerPage + "/" + mclusterName,
		dataType : "json", /*这句可用可不用，没有影响*/
		success : function(data) {
			error(data);
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
						+  "<a href=\"${ctx}/detail/mcluster/" + array[i].id+"\">"+array[i].mclusterName+"</a>"
						+ "</td>");
				if(array[i].hcluster){
					var td3 = $("<td>"
 							+ array[i].hcluster.hclusterNameAlias
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
				if(array[i].createUser) {
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
				}else if(array[i].status == 1||array[i].status == 3||array[i].status == 6){
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
					
				var td8 = $("<td>"
						+"<div class=\"hidden-sm hidden-xs  action-buttons\">"
						+"<a class=\"green\" href=\"#\" onclick=\"startMcluster(this)\">"
						+"<i class=\"ace-icon fa fa-play-circle-o bigger-130\"></i>"
						+"</a>"
						+"<a class=\"blue\" href=\"#\" onclick=\"stopMcluster(this)\" data-toggle=\"modal\" data-target=\"#\">"
							+"<i class=\"ace-icon fa fa-power-off bigger-120\"></i>"
						+"</a>"
						+"<a class=\"red\" href=\"#\" onclick=\"deleteMcluster(this)\" data-toggle=\"modal\" data-target=\"#\">"
							+"<i class=\"ace-icon fa fa-trash-o bigger-120\"></i>"
						+"</a>"
						+"</div>"
						+ "</td>"
				);
					
				if(array[i].status == 3||array[i].status == 4||array[i].status == 14){
					var tr = $("<tr class=\"danger\"></tr>");
				}else if(array[i].status == 5||array[i].status == 13){
					var tr = $("<tr class=\"warning\"></tr>");
				}else{
					var tr = $("<tr></tr>");
				}
				
				tr.append(td1).append(td2).append(td3).append(td4).append(td5).append(td6).append(td7).append(td8);
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
	$("#create-mcluster-form").bootstrapValidator({
	  message: '无效的输入',
         feedbackIcons: {
             valid: 'glyphicon glyphicon-ok',
             invalid: 'glyphicon glyphicon-remove',
             validating: 'glyphicon glyphicon-refresh'
         },
         fields: {
       	  mclusterName: {
                 validMessage: '请按提示输入',
                 validators: {
                     notEmpty: {
                         message: 'Container集群名称不能为空!'
                     },
			          stringLength: {
			              max: 40,
			              message: 'Container集群名过长'
			          },regexp: {
		                  regexp: /^([a-zA-Z_0-9]*)$/,
  		                  message: "请输入字母数字或'_'"
                 	  },
                 	 remote: {
	                        message: 'Container集群名已存在!',
	                        url: "${ctx}/mcluster/validate"
	                    }
	             }
         	}	
         }
     }).on('error.field.bv', function(e, data) {
    	 $('#create-mcluster-botton').addClass("disabled");
     }).on('success.field.bv', function(e, data) {
    	 $('#create-mcluster-botton').removeClass("disabled");
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

function createMcluster(){
	$.ajax({
		type : "post",
		url : "${ctx}/mcluster",
		data :$('#create-mcluster-form').serialize(),
		success:function (data){
			error(data);
			$('#create-mcluster-form').find(":input").not(":button,:submit,:reset,:hidden").val("").removeAttr("checked").removeAttr("selected");
			$('#create-mcluster-form').data('bootstrapValidator').resetForm();
			$('#create-mcluster-botton').addClass('disabled');
			$('#create-mcluster-modal').modal('hide');
			//延时一秒刷新列表
			setTimeout("queryByPage(currentPage, recordsPerPage)",1000);
		}
	});
}
function startMcluster(obj){
	var tr = $(obj).parents("tr").html();
	if (tr.indexOf("已停止") < 0){
		warn("当前状态无法执行启动操作!",3000);
		return 0;
	}
	function startCmd(){
		var mclusterId =$(obj).parents("tr").find('[name="mcluster_id"]').val();
		$.ajax({
			url:'${ctx}/mcluster/start',
			type:'post',
			data:{mclusterId : mclusterId},
			success:function(data){
				error(data);
				queryByPage(currentPage, recordsPerPage);
			}
		});
	}
	confirmframe("启动container集群","启动集群大概需要几分钟时间!","请耐心等待...",startCmd);
}
function stopMcluster(obj){
	var tr = $(obj).parents("tr").html();
	if (tr.indexOf("运行中") < 0 &&tr.indexOf("异常") < 0&&tr.indexOf("危险") < 0&&tr.indexOf("严重危险") < 0 ){
		warn("当前状态无法执行关闭操作!",3000);
		return 0;
	}
	function stopCmd(){
		var mclusterId =$(obj).parents("tr").find('[name="mcluster_id"]').val();
		$.ajax({
			url:'${ctx}/mcluster/stop',
			type:'post',
			data:{mclusterId : mclusterId},
			success:function(data){
				error(data);
				queryByPage(currentPage, recordsPerPage);
			}
		});
	}
	confirmframe("关闭container集群","关闭container集群将不能提供服务,再次启动需要十几分钟!","您确定要关闭?",stopCmd);
}
function deleteMcluster(obj){
	var tr = $(obj).parents("tr").html();
	if (tr.indexOf("删除中") >= 0){
		warn("正在删除集群,请耐心等待...",3000);
		return 0;
	}
	function deleteCmd(){
		var mclusterId =$(obj).parents("tr").find('[name="mcluster_id"]').val();
		$.ajax({
			url:'${ctx}/mcluster/'+mclusterId,
			type:'delete',
			success:function(data){
				error(data);
				queryByPage(currentPage, recordsPerPage);
			}
		});
	}
	confirmframe("删除container集群","删除container集群后将不能恢复!","您确定要删除?",deleteCmd);
}

function queryHcluster(){
	var options = $('#hcluster_select');
	$.ajax({
		url:'${ctx}/hcluster',
		type:'get',
		dataType:'json',
		success:function(data){
			var array = data.data;
			for(var i = 0, len = array.length; i < len; i++){
				
				var option = $("<option value=\""+array[i].id+"\">"
								+array[i].hclusterNameAlias
								+"</option>");
				options.append(option);
			}
		}
	});
}

function page_init(){
	queryByPage(currentPage, recordsPerPage);
	searchAction();
	formValidate();
	pageControl();
	$('[name = "popoverHelp"]').popover();
}
</script>
