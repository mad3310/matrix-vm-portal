<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="zh">

<head>
	<meta charset="utf-8"/>
	<meta http-equiv="X-UA-compatible" content="IE=edge,chrome=1"/>
	<meta name="viewport" content="width=device-width,initial-scale=1"/>
	<!-- bootstrap css -->
	<link type="text/css" rel="stylesheet" href="${ctx}/static/css/bootstrap.min.css"/>
	<!-- ui-css -->
	<link type="text/css" rel="stylesheet" href="${ctx}/static/css/ui-css/common.css"/>
	<!-- bootstrapValidator-->
	<link type="text/css" rel="stylesheet" href="${ctx}/static/css/bootstrapValidator.css"/>

	<title>SLB配置</title>
</head>

<body>
	<!-- 全局参数 start -->
	<input class="hidden" value="${slbId}" name="slbId" id="slbId" type="text" />
	<!-- 全局参数 end -->
	<!-- 账号管理主界面div -->
	<div id="accountList" class="m-pr10" role="tablist" aria-multiselectable="true">
		<div class="se-heading" id="headingOne">
			<div class="pull-left">
				<h5 class="hidden-xs">负载均衡服务器池</h5>
				<a href="/helpCenter/helpCenter.jsp?container=help-slbadservice"  target="_black" data-toggle="tooltip" data-placement="top" title="如何配置">
					<span class="glyphicon glyphicon-question-sign text-muted" ></span>
				</a>
				<span class="ng-binding hidden-xs">所在地域：北京</span>
				<h5 class="hidden-sm hidden-md hidden-lg">SLB服务器池</h5>
				<span class="ng-binding hidden-sm hidden-md hidden-lg">地域：北京</span>
			</div>
			<div class="pull-right hidden-xs">
				<button id="refresh" class="btn btn-default">
					<span>刷新</span>
				</button>
			</div>
			<div class="hidden-sm hidden-md hidden-lg clearfix"></div>
			<div class="pull-right">
				<button id="restart" class="btn btn-primary">
					<span class='hidden-xs'>重启服务</span>
					<span class='hidden-sm hidden-md hidden-lg glyphicon glyphicon-expand'></span>
				</button>
			</div>
		</div>
		<ul class="nav nav-tabs" role="tablist" id="setab">
			<li id="whitelist-tab" role="presentation" class="active">
				<a data-toggle="tab" href="#addedServer">已添加的服务器</a></li>
			<li id="inner-server-list-tab" role="presentation" class="hidden">
				<a data-toggle="tab" href="#sqlInject">未添加的GCE服务器</a></li>
		</ul>
		<!-- <div class="panel-body pd0" id="whitelist"> -->
		<div class="tab-content">
			<div id="addedServer" role="tabpanel" class="tab-pane fade active in"  aria-labelledby="whitelist-tab">
				<table class="table table-hover table-se">
					<thead>
					<tr>
						<th>云服务器ID/名称</th>
						<th class="hidden-xs">访问地址</th>
						<th class="hidden-xs">SLB协议端口</th>
						<th>状态</th>
						<th class="hidden-xs">网络类型</th>
						<th class="hidden-xs">付款方式</th>
						<th class="hidden-xs">健康检查状态</th>
						<th class="hidden-xs">权重</th>
						<th class="text-right">
							<span style="padding-left:8px">操作</span>
						</th>
					</tr>
					</thead>
					<tbody id="tby">
					</tbody>
				</table>
				<div style="height: 40px; margin-top: 20px;">
					<a class="btn btn-success hidden" id="add-custom-server" data-backdrop="false" data-toggle="modal" data-target="#myModal">添加自定义服务器</a>
					<a class="btn btn-success hidden"  id="add-inner-server">添加GCE服务器</a>
				</div>
			</div>
			<div id="sqlInject" role="tabpanel" class="tab-pane fade" aria-labelledby="sqlInject-tab">
				<table class="table table-hover table-se">
					<thead>
					<tr>
						<th>云服务器ID/名称</th>
						<th class="hidden-xs">访问地址</th>
						<th class="hidden-xs">网络类型</th>
						<th class="hidden-xs">付款方式</th>
						<th>健康检查状态</th>
						<th class="text-right">
							<span style="padding-left:8px">操作</span>
						</th>
					</tr>
					</thead>
					<tbody id="server-tby">
					</tbody>
				</table>
			</div>
		</div>
	</div>
	<div class="modal" id="myModal" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">添加服务器</h4>
				</div>
				<form id="addBackendServer" name="healthBase" class="form-horizontal" role="form">
					<div class="modal-body">
						<div class="form-inline form-group">
							<label class="col-sm-4 control-label"> <span class="text-danger">*</span>服务器名称：
							</label>
							<div class="col-sm-6">
								<input name="backendServerName" class="form-control" size="7" type="text">
							</div>
						</div>
						<div class="form-inline form-group">
							<label class="col-sm-4 control-label"> <span class="text-danger">*</span>服务器IP：
							</label>
							<div class="col-sm-6">
								<input name="backendServerIp" class="form-control" size="7" type="text">
							</div>
						</div>
						<div class="form-inline form-group">
							<label class="col-sm-4 control-label"> <span class="text-danger">*</span>后端端口：
							</label>
							<div class="col-sm-3">
								<input name="backendPort" class="form-control" size="7" type="text">
							</div>
						</div>
						<div class="form-inline form-group">
							<label class="col-sm-4 control-label"> <span class="text-danger">*</span>SLB协议端口：
							</label>
							<div class="col-sm-8 ng-scope">
								<select name="frontPort" class="form-control ng-pristine ng-valid">
								</select>
							</div>
						</div>
						<div class="modal-footer">
							<button class="btn btn-primary" type="submit">添加服务器</button>
							<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
	<!--确认对话框-->
	<div id="dialog-box" class="modal">
		<div class="modal-dialog modal-sm">
			<div class="modal-content">
				<div class="modal-header">
			<!--	<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span><span class="sr-only">关闭</span>
					</button>-->
					<h5 id="dialog-box-title" class="modal-title"></h5ashboard>
				</div>
				<div class="modal-body clearfix">
					<div class="col-xs-2 col-sm-1 col-md-1 warning-sign">
						<span class="glyphicon glyphicon-exclamation-sign"></span>
					</div>
					<div id="dialog-box-text" class="col-xs-10 col-sm-10"></div>
				</div>
				<div class="modal-footer">
					<button id="dialogBoxSubmit" type="button" class="btn btn-primary" data-dismiss="modal">确定</button>
					<button id="dialogBoxCancel" type="button" class="btn btn-default" data-dismiss="modal">取消</button>
				</div>
			</div>
		</div>
	</div>
</body>
	<!-- js -->
<script type="text/javascript" src="${ctx}/static/modules/seajs/2.3.0/sea.js"></script>
<script type="text/javascript">

// Set configuration
seajs.config({
	base: "${ctx}/static/modules/",
	alias: {
		"jquery": "jquery/2.0.3/jquery.min.js",
		"bootstrap": "bootstrap/bootstrap/3.3.0/bootstrap.js",
		"bootstrapValidator": "bootstrap/bootstrapValidator/0.5.3/bootstrapValidator.js"
	}
});
seajs.use("${ctx}/static/page-js/cloudslb/backendServer/main");
</script>

</html>
