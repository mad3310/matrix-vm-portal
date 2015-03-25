<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="page-content-area">
	<div id="page-header-id" class="page-header">
		<h1>
			<a href="${ctx}/list/job/stream">返回任务流列表<i class="ace-icon fa fa-reply icon-only"></i></a>	
		</h1>
	</div>
	<input class="hidden" value="${jobStreamId}" name="jobStreamId" id="jobStreamId" type="text" />
	<!-- /.page-header -->
	<div class="row">
		<div class="col-sm-12 col-md-12">
			<div style="margin-top: 10px;">
				<div class="widget-box widget-color-blue ui-sortable-handle col-xs-12">
					<div class="widget-header">
						<h5 class="widget-title">任务流详情</h5>
						<div class="widget-toolbar no-border">
							<button id="create_job_stream" class="btn btn-white btn-primary btn-xs" data-toggle="modal" data-target="#add-job-stream-unit-modal">
								<i class="ace-icont fa fa-plus"></i>
								 添加任务单元
							</button>
						</div>
					</div>
					<div class="widget-body">
						<div class="widget-main no-padding">
							<table class="table table-bordered" id="db_detail_table" >
								<thead>
									<tr>
										<th class="center">
											<label class="position-relative">
												<input type="checkbox" id="titleCheckbox" class="ace" />
												<span class="lbl"></span>
											</label>
										</th>
										<th width=25%>任务单元名称</th>
										<th>业务类型</th>
										<th>失败重试次数</th>
										<th>功能描述</th>
										<th>执行顺序</th>
										<th>操作</th>
									</tr>
								</thead>
								<tbody id="tby">
									<tr><td class="center"><label class="position-relative"><input type="checkbox" class="ace"><span class="lbl"></span></label></td>
										<td>创建cluster</td>
										<td>RDS</td>
										<td>5</td>
										<td>为RDS准备集群</td>
										<td>1</td>
										<td>编辑|删除</td>
									</tr>
									<tr><td class="center"><label class="position-relative"><input type="checkbox" class="ace"><span class="lbl"></span></label></td>
										<td>mysql服务初始化</td>
										<td>RDS</td>
										<td>5</td>
										<td>为RDS准备server</td>
										<td>2</td>
										<td>编辑|删除</td>
									</tr>
									<tr><td class="center"><label class="position-relative"><input type="checkbox" class="ace"><span class="lbl"></span></label></td>
										<td>创建db用户</td>
										<td>RDS</td>
										<td>5</td>
										<td>为RDS用户创建默认账户</td>
										<td>3</td>
										<td>编辑|删除</td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="modal fade" id="add-job-stream-unit-modal" tabindex="-1" aria-labelledby="myModalLabel" style="margin-top:157px">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
           				<button type="button" class="close" data-dismiss="modal">
           					<span aria-hidden="true"><i class="ace-icon fa fa-times-circle"></i></span>
           					<span class="sr-only">关闭</span>
           				</button>
           				<h4 class="modal-title">添加任务单元</h4>
            		</div>
					<form id="create-hcluster-form" name="create-hcluster-form" class="form-horizontal" role="form">
						<div class="modal-body">            				
            				<div class="form-group">
								<label class="col-sm-4 control-label" for="hcluster_name">任务单元</label>
								<div class="col-sm-6">
									<select class="form-control" name="jobType" id="jobType">
										<option value="">初始化cluster</option>
										<option value="">启动sqlServer</option>
										<option value="">创建数据库</option>
									</select>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-4 control-label" for="hcluster_name">失败重试次数</label>
								<div class="col-sm-3">
									<select class="form-control" name="jobType" id="jobType">
										<option value="">1</option>
										<option value="">2</option>
										<option value="">3</option>
										<option value="">4</option>
										<option value="">5</option>
									</select>
								</div>
							</div>
							</div>
            			</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-sm btn-default" data-dismiss="modal">关闭</button>
						<button id="create-hcluster-botton" type="button" class="btn btn-sm disabled btn-primary" onclick="">创建</button>
					</div>
				</form>
				</div>
			</div>
		</div>
	</div>
</div>
<link rel="stylesheet" href="${ctx}/static/styles/bootstrap/bootstrapValidator.min.css" />
<script src="${ctx}/static/scripts/bootstrap/bootstrapValidator.min.js"></script>
<script src="${ctx}/static/scripts/pagejs/job_stream_detail.js"></script>