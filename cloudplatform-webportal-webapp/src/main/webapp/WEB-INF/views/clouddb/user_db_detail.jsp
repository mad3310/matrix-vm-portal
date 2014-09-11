<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="page-content-area">
	<div id="page-header-id" class="page-header">
		<h1> 
			<a href="${ctx}/db/list">数据库列表</a>
			<small> 
				<i class="ace-icon fa fa-angle-double-right"></i> 
				${db.dbName}
			</small>
		</h1>
	</div>
	<!-- /.page-header -->
</div>
<!-- /.page-content-area -->
<div class="row">
	<div class="widget-box transparent ui-sortable-handle">
		<div class="widget-header">
			<div class="widget-toolbar no-border pull-left">
				<ul id="db-detail-tabs" class="nav nav-tabs" id="myTab2">
					<li class="active">
						<a data-toggle="tab" href="#db-detail-info">数据库信息</a>
					</li>
					<li class="">
						<a data-toggle="tab" href="#db-detail-user-mgr">用户管理</a>
					</li>
				</ul>
			</div>
		</div>
		<div class="widget-body">
			<div class="widget-main padding-12 no-padding-left no-padding-right">
				<div class="tab-content padding-4">
					<div id="db-detail-info" class="tab-pane active">
						<div id="db-detail-table" class="col-xs-10">
							<div class="widget-box widget-color-blue ui-sortable-handle">
								<div class="widget-header">
									<h5 class="widget-title bigger lighter">
										<i class="ace-icon fa fa-table"></i>数据库信息
									</h5>
								</div>
								<div class="widget-body">
									<div class="widget-main no-padding">
										<table class="table table-striped table-bordered table-hover" id="db_detail_table">
											<tr>
												<td width="50%">数据库名</td>
												<td width="50%">${db.dbName}</td>
											</tr>
											<c:forEach items="${dbUsers}" var="dbUser">
												<tr>
													<td>用户名</td>
													<td>${dbUser.username}</td>
												</tr>
												<tr>
													<td>密码</td>
													<td>${dbUser.password}</td>
												</tr>
											</c:forEach>
											<c:forEach items="${containers}" var="container">
												<tr>
													<td>${container.type}</td>
													<td>${container.ipAddr}</td>
												</tr>
											</c:forEach>
										</table>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div id="db-detail-user-mgr" class="tab-pane">
						<div class="col-xs-10">
							<div class=" pull-right">
								<button type="button" class="btn btn-xs btn-success bigger" data-toggle="modal" data-target="#create-dbuser-form">
									<h5><i class="ace-icont fa fa-plus"></i>&nbsp&nbsp创建&nbsp&nbsp</h5>
								</button>
								<button type="button" class="btn btn-xs btn-danger bigger disabled">
									<h5><i class="ace-icont fa fa-trash-o"></i>&nbsp&nbsp删除&nbsp&nbsp</h5>
								</button>
							</div>
							<div class="widget-box widget-color-blue ui-sortable-handle" style="margin-top: 50px;" >
								<div class="widget-header">
									<div class="widget-toolbar no-border">
									</div>
								</div>
								<div class="widget-body">
									<div class="widget-main no-padding">
										<table class="table table-striped table-bordered table-hover" id="db_detail_table">
										<thead>
											<tr>
												<th class="center">
													<label class="position-relative">
														<input type="checkbox" class="ace" />
														<span class="lbl"></span>
													</label>
												</th>
												<th>
													<i class="ace-icon fa fa-user bigger-110 hidden-480"></i>
													用户名
												</th>
												<th>
													<i class="ace-icon fa fa-key bigger-110 hidden-480"></i>
													用户密码 
												</th>
												<th>用户权限</th>
												<th>ip地址</th>
												<th>频次限制</th>
												<th>
													<i class="ace-icon fa fa-flag-o bigger-110 hidden-480"></i>
													当前状态
												</th>
											</tr>
										</thead>
											<tr>
												<td class="center">
													<label class="position-relative">
														<input type="checkbox" class="ace"/>
														<span class="lbl"></span>
													</label>
												</td>	
												<td >liuhao1</td>
												<td >123456</td>
												<td >管理员+读+写</td>
												<td >192.168.30.49,192.168.30.44</td>
												<td >200/s</td>
												<td >正在审核</td>
											</tr>
											<tr>
												<td class="center">
													<label class="position-relative">
														<input type="checkbox" class="ace"/>
														<span class="lbl"></span>
													</label>
												</td>	
												<td >liuhao1</td>
												<td >123456</td>
												<td >读+写</td>
												<td >192.168.30.49,192.168.30.44,192.168.30.49,192.168.30.44</td>
												<td >200/s</td>
												<td >正在审核</td>
											</tr>
											<tr>
												<td class="center">
													<label class="position-relative">
														<input type="checkbox" class="ace"/>
														<span class="lbl"></span>
													</label>
												</td>	
												<td >liuhao1</td>
												<td >123456</td>
												<td >读+写</td>
												<td >192.168.30.49,192.168.30.44</td>
												<td >200/s</td>
												<td >正在审核</td>
											</tr>
											<tr>
												<td class="center">
													<label class="position-relative">
														<input type="checkbox" class="ace"/>
														<span class="lbl"></span>
													</label>
												</td>	
												<td >liuhao1</td>
												<td >123456</td>
												<td >读+写</td>
												<td >192.168.30.49,192.168.30.44</td>
												<td >200/s</td>
												<td >正在审核</td>
											</tr>
										</table>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="modal fade" id="create-dbuser-form" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<form id="db_apply_form" name="db_apply_form" class="form-horizontal" role="form" action="" method="post">
				<div class="col-xs-12">
					<h4 class="lighter">
						<i class="ace-icon fa fa-hand-o-right icon-animated-hand-pointer blue"></i>
						<a href="#modal-wizard" data-toggle="modal" class="blue"> 创建数据库用户 </a>
					</h4>
					<div class="hr hr-18 hr-double dotted"></div>
					<div class="widget-box">
						<div class="widget-body">
							<div class="widget-main">
								<hr />
									<div class="form-group">
										<label class="col-sm-2 control-label" for="db_name">用户名</label>
										<div class="col-sm-8">
											<input class="form-control" name="applyCode" id="applyCode" type="text" />
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label" for="db_name">密码</label>
										<div class="col-sm-8">
											<input class="form-control" name="applyCode" id="applyCode" type="text" />
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label" for="connection_type">用户类型</label>
										<div class="col-sm-4">
											<select class="form-control" name="linkType" id="linkType">
												<option>管理员</option>
												<option>读写</option>
											</select>
										</div>
									</div>
									 <div class="form-group">
								        <label class="col-sm-2 control-label">IP地址</label>
								        <div class="col-sm-4">
								            <input type="text" class="form-control" name="option[]" />
								        </div>
								        <div class="col-sm-2">
								            <button type="button" class="btn btn-success addButton btn-sm">
								                <i class="fa fa-plus"></i>
								            </button>
								        </div>
								    </div>
								    <div class="form-group hide" id="optionTemplate">
								        <div class="col-sm-offset-2 col-sm-4">
								            <input type="text" class="form-control" name="option[]" />
								        </div>
								        <div class="col-sm-4">
								            <button type="button" class="btn btn-danger btn-sm removeButton">
								                <i class="fa fa-minus"></i>
								            </button>
								        </div>
								    </div>
									<div class="form-group">
										<label class="col-sm-2 control-label" for="read_write_ratio">读写比例</label>
										<div class="col-sm-2">
											<input class="form-control" name="readWriterRate" id="readWriterRate" type="text"
												placeholder="1:1" />
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label" for="maximum_concurrency">最大并发量</label>
										<div class="col-sm-2">
											<input class="form-control" name="maxConcurrency" id="maxConcurrency" type="text"
												placeholder="100"/>
										</div>
										<label class="control-label" for="maximum_concurrency">/s</label>
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
<link rel="stylesheet" href="${ctx}/static/styles/bootstrap/bootstrapValidator.min.css" />
<script src="${ctx}/static/scripts/bootstrap/bootstrapValidator.min.js"></script>
<script type="text/javascript">
$(function(){
	pageinit();
	 var MAX_OPTIONS = 5;

	    $('#db_apply_form').bootstrapValidator({
	            feedbackIcons: {
	                valid: 'glyphicon glyphicon-ok',
	                invalid: 'glyphicon glyphicon-remove',
	                validating: 'glyphicon glyphicon-refresh'
	            },
	            fields: {
	                question: {
	                    validators: {
	                        notEmpty: {
	                            message: 'The question required and cannot be empty'
	                        }
	                    }
	                },
	                'option[]': {
	                    validators: {
	                        notEmpty: {
	                            message: 'The option required and cannot be empty'
	                        },
	                        stringLength: {
	                            max: 100,
	                            message: 'The option must be less than 100 characters long'
	                        }
	                    }
	                }
	            }
	        }).on('click', '.addButton', function() {
	            var $template = $('#optionTemplate'),
	                $clone    = $template
	                                .clone()
	                                .removeClass('hide')
	                                .removeAttr('id')
	                                .insertBefore($template),
	                $option   = $clone.find('[name="option[]"]');

	            // Add new field
	            $('#db_apply_form').bootstrapValidator('addField', $option);
	        }).on('click', '.removeButton', function() {
	            var $row    = $(this).parents('.form-group'),
	                $option = $row.find('[name="option[]"]');
	            $row.remove();
	            $('#db_apply_form').bootstrapValidator('removeField', $option);
	        }).on('added.field.bv', function(e, data) {
	            if (data.field === 'option[]') {
	                if ($('#db_apply_form').find(':visible[name="option[]"]').length >= MAX_OPTIONS) {
	                    $('#db_apply_form').find('.addButton').attr('disabled', 'disabled');
	                }
	            }
	        }).on('removed.field.bv', function(e, data) {
	           if (data.field === 'option[]') {
	                if ($('#db_apply_form').find(':visible[name="option[]"]').length < MAX_OPTIONS) {
	                    $('#db_apply_form').find('.addButton').removeAttr('disabled');
	                }
	            }
	        });
});

function checkboxControl(){
	$('th input:checkbox').click(function(){
		var that = this;
		$(this).closest('table').find('tr > td:first-child input:checkbox')
		.each(function(){
			this.checked = that.checked;
			$(this).closest('tr').toggleClass('selected');
		});
	});
}

function translateStatus(status){
	if(status = 1)
	{
		return "是";
	}else{
		return "否";
	}
}
function pageinit(){
	checkboxControl();
}
</script>
