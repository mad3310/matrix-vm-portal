<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="page-content-area">
	<div id="page-header-id" class="page-header">
		<%-- <h3>
			<a href="${ctx}/list/hcluster">物理机集群列表</a> <small id="headerHostName">
				<i class="ace-icon fa fa-angle-double-right"></i>
			</small>
		</h3> --%>
	</div>
	<!-- /.page-header -->
	<div class="row">

		<div class="col-xs-12">
			<div style="margin-top: 8px;">
				<div class="widget-box widget-color-blue ui-sortable-handle col-xs-12">
					<div class="widget-header">
						<h5 id="hclusterTitle" class="widget-title">物理机集群详情</h5>
						<div class="widget-toolbar no-border">
							<button type="button" class="btn btn-info btn-xs"
								data-toggle="modal" data-target="#add-host-form-modal">
								<i class="ace-icont fa fa-plus"></i>添加物理机
							</button>
						</div>
					</div>
					<table class="table table-bordered table-striped table-hover"
						id="Mcluster_detail_table">
						<thead>
							<tr
								style="background-image: none; background-color: #307ECC; color: #FFFFFF;">
								<th>物理机名称</th>
								<th>编号</th>
								<th>类型</th>
								<th>ip</th>
								<th>状态</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody id="tby">
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
	<div id="dialog-confirm" class="hide">
		<div id="dialog-confirm-content" class="alert alert-info bigger-110">
		</div>
		<div class="space-6"></div>
		<p id="dialog-confirm-question" class="bigger-110 bolder center grey">
		</p>
	</div>
	<div class="modal fade" id="add-host-form-modal" tabindex="-1"
		role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h4>添加物理机</h4>
				</div>
				<form id="add_host_form" name="add_host_form"
					class="form-horizontal" role="form">
					<div class="modal-body">
						<div class="form-group">
							<label class="col-sm-offset-1 col-sm-2 control-label"
								for="hostNameAlias">物理机名称</label>
							<div class="col-sm-5">
								<input class="form-control" name="hostNameAlias"
									id="hostNameAlias" type="text" />
							</div>
							<label class="control-label" for="hostNameAlias"> <a
								id="maxConcurrencyHelp" name="popoverHelp" rel="popover"
								data-container="body" data-toggle="popover"
								data-placement="right" data-trigger='hover'
								data-content="此名称应能概括物理机信息，可用汉字!"
								style="cursor: pointer; text-decoration: none;"> <i
									class="ace-icon fa fa-question-circle blue bigger-125"></i>
							</a>
							</label>
						</div>
						<div class="form-group">
							<input class="hidden" value="${hclusterId}" name="hclusterId"
								id="hclusterId" type="text" /> <label
								class="col-sm-offset-1 col-sm-2 control-label" for="hostName">编号</label>
							<div class="col-sm-5">
								<input class="form-control" name="hostName" id="hostName"
									type="text" />
							</div>
							<label class="control-label" for="hostName"> <a
								id="maxConcurrencyHelp" name="popoverHelp" rel="popover"
								data-container="body" data-toggle="popover"
								data-placement="right" data-trigger='hover'
								data-content="请输入字母数字或'_'"
								style="cursor: pointer; text-decoration: none;"> <i
									class="ace-icon fa fa-question-circle blue bigger-125"></i>
							</a>
							</label>
						</div>
						<div class="form-group">
							<label class="col-sm-offset-1 col-sm-2 control-label"
								for="connection_type">物理机类型</label>
							<div class="col-sm-5">
								<select class="form-control" name="type" id="type">
									<option value="1">从机</option>
									<option value="0">主机</option>
								</select>
							</div>
							<label class="control-label" for="maximum_concurrency"> <a
								id="maxConcurrencyHelp" name="popoverHelp" rel="popover"
								data-container="body" data-toggle="popover"
								data-placement="right" data-trigger='hover'
								data-content="主机是收集所有从机信息的物理机,第一个添加物理机必须为主机"
								style="cursor: pointer; text-decoration: none;"> <i
									class="ace-icon fa fa-question-circle blue bigger-125"></i>
							</a>
							</label>
						</div>
						<div class="form-group" name="dynamic-ip-input-mod">
							<label class="col-sm-offset-1 col-sm-2 control-label">IP地址</label>
							<div class="col-sm-5">
								<input type="text" class="form-control" name="hostIp" />
							</div>
							<label class="control-label" for="maximum_concurrency"> <a
								id="maxConcurrencyHelp" name="popoverHelp" rel="popover"
								data-container="body" data-toggle="popover"
								data-placement="right" data-trigger='hover'
								data-content="请输入物理机ip示例:192.168.33.12"
								style="cursor: pointer; text-decoration: none;"> <i
									class="ace-icon fa fa-question-circle blue bigger-125"></i>
							</a>
							</label>
						</div>
						<div class="form-group">
							<label class="col-sm-offset-1 col-sm-2 control-label">描述</label>
							<div class="col-sm-5">
								<textarea id="descn" name="descn" class="form-control" rows="3"
									placeholder=""></textarea>
							</div>
							<label class="control-label" for="maximum_concurrency"> <a
								id="maxConcurrencyHelp" name="popoverHelp" rel="popover"
								data-container="body" data-toggle="popover"
								data-placement="right" data-trigger='hover'
								data-content="关于物理机信息、状态等描述."
								style="cursor: pointer; text-decoration: none;"> <i
									class="ace-icon fa fa-question-circle blue bigger-125"></i>
							</a>
							</label>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-sm btn-default"
							data-dismiss="modal">关闭</button>
						<button id="add_host_botton" type="button"
							class="btn btn-sm disabled btn-primary" onclick="addHost()">添加</button>
					</div>
				</form>
			</div>
		</div>
	</div>
</div>
<link rel="stylesheet"
	href="${ctx}/static/styles/bootstrap/bootstrapValidator.min.css" />
<script src="${ctx}/static/scripts/bootstrap/bootstrapValidator.min.js"></script>

<script src="${ctx}/static/scripts/pagejs/hcluster_detail.js"></script>