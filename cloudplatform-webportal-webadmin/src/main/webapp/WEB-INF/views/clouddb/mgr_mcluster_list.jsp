<%@ page language="java" pageEncoding="UTF-8"%>
<!-- /section:settings.box -->
<div class="page-content-area">
	<div class="page-header">
		<h1> 
			集群列表
			<!-- <small> 
				<i class="ace-icon fa fa-angle-double-right"></i> 
				overview &amp; stats
			</small> -->
		</h1>
	</div>
	<!-- /.page-header -->
</div>
<!-- /.page-content-area -->
<div class="row">
	<div class="widget-box widget-color-blue ui-sortable-handle col-xs-12">
		<div class="widget-header">
			<h5 class="widget-title">集群列表</h5>
			<div class="widget-toolbar no-border">
				<button class="btn btn-xs btn-success bigger" data-toggle="modal" data-target="#create-mcluster-modal">
					<i class="ace-icont fa fa-plus"></i>
					 创建集群
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
							<th>集群名称</th>
							<th>集群所属用户</th>
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
				<form id="create-mcluster-form" name="create-mcluster-form" class="form-horizontal" role="form" action="${ctx}/mcluster/build" method="post">
				<div class="col-xs-12">
					<h4 class="lighter">
						<a href="#modal-wizard" data-toggle="modal" class="blue"> 创建集群 </a>
					</h4>
					<div class="widget-box">
						<div class="widget-body">
							<div class="widget-main">
								<div class="form-group">
									<label class="col-sm-2 control-label" for="mcluster_name">集群名称</label>
									<div class="col-sm-8">
										<input class="form-control" name="mclusterName" id="mclusterName" type="text" />
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-sm btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-sm btn-primary" onclick="createMcluster()">创建</button>
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
							<th width="5%">结果  </th>
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
<link rel="stylesheet" href="${ctx}/static/styles/bootstrap/bootstrapValidator.min.css" />
<script src="${ctx}/static/scripts/bootstrap/bootstrapValidator.min.js"></script>

<script src="${ctx}/static/ace/js/jquery.dataTables.min.js"></script>
<script src="${ctx}/static/ace/js/jquery.dataTables.bootstrap.js"></script>
<script type="text/javascript">
var currentPage = 1; //第几页 
var recordsPerPage = 10; //每页显示条数
	
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
	$(document).on('click', "[name='buildStatusBoxLink']" , function(){
		var mclusterId = $(this).closest('tr').find('td:first input').val();
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
	var mclusterName = $("#nav-search-input").val()?$("#nav-search-input").val():'null';
	$.ajax({
		type : "get",
		url : "${ctx}/mcluster/list/" + currentPage + "/" + recordsPerPage + "/" + mclusterName,
		dataType : "json", /*这句可用可不用，没有影响*/
		success : function(data) {
			var array = data.data.data;
			var tby = $("#tby");
			var totalPages = data.data.totalPages;
			
			function translateStatus(status){
				var statuStr;
				if(status == 1){
					return "正常";
				}else{
					return "创建失败";
				}
			}
			
			for (var i = 0, len = array.length; i < len; i++) {
				var td1 = $("<td class=\"center\">"
								+"<label class=\"position-relative\">"
								+"<input value= \""+array[i].id+"\" type=\"checkbox\" class=\"ace\"/>"
								+"<span class=\"lbl\"></span>"
								+"</label>"
							+"</td>");
				var td2 = $("<td>"
						+  "<a href=\"${ctx}/mcluster/detail/" + array[i].id+"\">"+array[i].mclusterName+"</a>"
						+ "</td>");
				var td3 = $("<td>"
						+ array[i].createUser
						+ "</td>");
				var td4 = $("<td>"
						+ array[i].createTime
						+ "</td>");
				if(array[i].status == 2){
					var td5 = $("<td>"
							+"<a name=\"buildStatusBoxLink\" data-toggle=\"modal\" data-target=\"#create-mcluster-status-modal\" style=\"cursor:pointer; text-decoration:none;\">"
							+"<i class=\"ace-icon fa fa-spinner fa-spin green bigger-125\" />"
							+"创建中</a>"
							+ "</td>");
				}else{
					var td5 = $("<td>"
							+"<a name=\"buildStatusBoxLink\" data-toggle=\"modal\" data-target=\"#create-mcluster-status-modal\" style=\"cursor:pointer; text-decoration:none;\">"
							+translateStatus(array[i].status)
							+"</a>"
							+ "</td>");
				}
					
				/* var td6 = $("<td>"
						+"<div class=\"hidden-sm hidden-xs btn-group\">"
						+"<button class=\"btn disabled btn-xs btn-success\">"
						+"<i class=\"ace-icon fa fa-play-circle-o bigger-120\"></i>"
						+"</button>"
						+"<button class=\"btn disabled btn-xs btn-info\">"
							+"<i class=\"ace-icon fa fa-power-off bigger-120\"></i>"
						+"</button>"
						+"<button class=\"btn disabled btn-xs btn-danger\">"
							+"<i class=\"ace-icon fa fa-trash-o bigger-120\"></i>"
						+"</button>"
						+"</div>"
						+ "</td>"
				); */
					
				if(array[i].status == 3){
					var tr = $("<tr class=\"danger\"></tr>");
				}else{
					var tr = $("<tr></tr>");
				}
				
				tr.append(td1).append(td2).append(td3).append(td4).append(td5);
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

	function searchAction(){
		$('#nav-search-input').bind('keypress',function(event){
	        if(event.keyCode == "13")    
	        {
	        	queryByPage(currentPage, recordsPerPage);
	        }
	    });
	}
	

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
                         message: '集群名称不能为空!'
                     },
			          stringLength: {
			              max: 40,
			              message: '集群名过长'
			          }
	             }
         	}	
         }
     });
}
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
				var td3 = $("<td>"
						+   array[i].startTime
						+ "</td>");
				var td4 = $("<td>"
						+ array[i].endTime
						+ "</td>");
				var td5 = $("<td>"
						+ array[i].msg
						+ "</td>");
				var td6 = $("<td>"
						+ array[i].status
						+ "</td>");
					
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

/* $("buildStatusBoxLink"){alert($(this).closest("tr").val())}; */

function createMcluster(){
	$.gritter.add({
		title: '警告',
		text: "后台API调用测试中，暂不可用",
		sticky: false,
		time: '5',
		class_name: 'gritter-warning'
	});
}
function page_init(){
	queryByPage(currentPage, recordsPerPage);
	queryBuildStatus("845769fd-5df0-4bdd-8bec-ac9aa0d3706f");
	searchAction();
	formValidate();
	pageControl();
}
</script>
