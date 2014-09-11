<%@ page language="java" pageEncoding="UTF-8"%>
<div class="page-content-area">
	<div id="page-header-id" class="page-header">
		<h1> 
			数据库列表
		</h1>
	</div>
	<!-- /.page-header -->
</div>
<!-- /.page-content-area -->
<div class="row">
	<div class="col-xs-12">
		<div class="row">
			<div class="widget-box widget-color-blue ui-sortable-handle col-xs-12">
				<div class="widget-header">
					<h5 class="widget-title">数据库列表</h5>
					<div class="widget-toolbar no-border">
						<button class="btn btn-xs btn-success bigger" data-toggle="modal" data-target="#apply-form">
							<i class="ace-icont fa fa-plus"></i>
							 创建数据库
						</button>
					</div>
				</div>
			
				<div class="widget-body">
					<div>
						<table id="mcluster_list" class="table table-striped table-bordered table-hover">
							<thead>
								<tr>
									<th>DB名称</th>
									<th>
										<i class="ace-icon fa fa-clock-o bigger-110 hidden-480"></i>
										创建时间 
									</th>
									<th class="hidden-480">当前状态</th>
									<th></th>
								</tr>
							</thead>
							<tbody id="tby">							
							</tbody>
						</table>
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
				</div>
			</div>
			<div class="modal fade" id="apply-form" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
				<div class="modal-dialog">
					<div class="modal-content">
						<form id="db_apply_form" name="db_apply_form" class="form-horizontal" role="form" action="${ctx}/db/save" method="post">
						<div class="col-xs-12">
							<h4 class="lighter">
								<i class="ace-icon fa fa-hand-o-right icon-animated-hand-pointer blue"></i>
								<a href="#modal-wizard" data-toggle="modal" class="pink"> 创建数据库 </a>
							</h4>
							<div class="hr hr-18 hr-double dotted"></div>
							<div class="widget-box">
								<div class="widget-body">
									<div class="widget-main">
										<hr />
												<div class="form-group">
													<label class="col-sm-2 control-label" for="db_name">数据库名</label>
													<div class="col-sm-8">
														<input class="form-control" name="applyCode" id="applyCode" type="text" />
													</div>
												</div>
												<div class="form-group">
														<label class="col-sm-2 control-label" for="disk_engine">存储引擎</label>
														<div class="col-sm-4">
															<select class="form-control" name="engineType" id="engineType">
																<option>InnoDB</option>
															</select>
														</div>
												</div>
												<div class="form-group">
													<label class="col-sm-2 control-label" for="connection_type">链接类型</label>
													<div class="col-sm-4">
														<select class="form-control" name="linkType" id="linkType">
															<option>长链接</option>
															<option>短链接</option>
														</select>
													</div>
												</div>
												<div class="form-group">
													<label class="col-sm-2 control-label" for="email_notification">邮件通知</label>
													<div class="col-sm-6">
														<label class="inline" style="margin-top: 3px;">
															<input name="isEmailNotice" id="isEmailNotice" checked="checked" type="checkbox" class="ace ace-switch ace-switch-5">
															<span class="lbl middle"></span>
														</label>
													</div>
												</div>
										<hr />
									</div>
								</div>
							</div>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
							<button type="submit" class="btn btn-primary" onclick="">创建</button>
						</div>
					</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<link rel="stylesheet" href="${ctx}/static/styles/bootstrap/bootstrapValidator.min.css" />
<script src="${ctx}/static/scripts/bootstrap/bootstrapValidator.min.js"></script>

<script src="${ctx}/static/ace/js/jquery.dataTables.min.js"></script>
<script src="${ctx}/static/ace/js/jquery.dataTables.bootstrap.js"></script>
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
});	
	function queryByPage(currentPage,recordsPerPage) {
		$("#tby tr").remove();
		var dbName = $("#nav-search-input").val()?$("#nav-search-input").val():'null';
		$.ajax({ 
			type : "get",
			url : "${ctx}/db/list/"+ currentPage+ "/"+ recordsPerPage+ "/"+ dbName,
			dataType : "json", /*这句可用可不用，没有影响*/
			success : function(data) {
				var array = data.data.data;
				var tby = $("#tby");
				var totalPages = data.data.totalPages;
				
				function translateStatus(status){
					if(status == 0){
						return "需审核";
					}else if(status  == 1 ||status  == 2){
						return "审核通过";
					}else if(status  == -1){
						return "审核未通过";
					}
				}
				
				for (var i = 0, len = array.length; i < len; i++) {
					var td2;
					if(array[i].status == 1 || array[i].status == 2 || array[i].status == -1 ){
						td2 = $("<td>"
								+ "<a href=\"${ctx}/db/detail/" + array[i].id+"\">"+array[i].dbName+"</a>"
								+ "</td>");
					}else{	
						td2 = $("<td>"
								+ "<a href=\"#\">"+array[i].dbName+"</a>"
								+ "</td>");
					}
					var td3 = $("<td>"
							+ array[i].createTime
							+ "</td>");
					var td4 = $("<td>"
							+ translateStatus(array[i].status)
							+ "</td>");
					var td5 = $("<td>"
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
						);	
						
					if(array[i].status == 0){
						var tr = $("<tr class=\"danger\"></tr>");
					}else{
						var tr = $("<tr></tr>");
					}
					
					tr.append(td2).append(td3).append(td4).append(td5);
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
				$('#pageMessage').html("<p class=\"bg-warning\" style=\"color:red;font-size:16px;\"><strong>警告!</strong>"+errorThrown+"</p>").show().fadeOut(3000);
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
				$('#pageMessage').html(pageMessage("warning","已经到达首页")).show().fadeOut(3000);
				
			} else {
				currentPage--;
				queryByPage(currentPage,recordsPerPage);
			}
		});

		// 下一页
		$("#nextPage").click(function() {
			if (currentPage == $("#totalPage_input").val()) {
				$('#pageMessage').html(pageMessage("warning","已经到达末页")).show().fadeOut(3000);
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
		//搜索
		$("#searchButton").click(function() {
			queryByPage(currentPage,recordsPerPage);
		});
	}
	
	function pageAction() {
		$("#searchButton").click(function() {
			queryByPage(currentPage,recordsPerPage);
		});
	}
	function formValidate() {
		$("#db_apply_form").bootstrapValidator({
		  message: 'This value is not valid',
          feedbackIcons: {
              valid: 'glyphicon glyphicon-ok',
              invalid: 'glyphicon glyphicon-remove',
              validating: 'glyphicon glyphicon-refresh'
          },
          fields: {
              applyCode: {
                  validMessage: 'The project_name looks great',
                  validators: {
                      notEmpty: {
                          message: '上线项目名称不能为空!'
                      },
			          stringLength: {
			              max: 40,
			              message: '项目名过长'
			          }
                  }
              }
          }
      });
	}
	
	function page_init(){
		formValidate();
		queryByPage(currentPage, recordsPerPage);
		pageAction();
		pageControl();
	}
</script>
