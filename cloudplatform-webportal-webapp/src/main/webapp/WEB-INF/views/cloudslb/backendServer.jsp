<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="zh">

<head>
	<meta charset="utf-8"/>
	<meta http-equiv="X-UA-compatible" content="IE=edge,chrome=1"/>
	<meta name="viewpoint" content="width=device-width,initial-scale=1"/>
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
	<div id="accountList" class="" role="tablist" aria-multiselectable="true">
		<div class="se-heading" id="headingOne">
			<div class="pull-left">
				<h5>负载均衡服务器池</h5>
				<span class="ng-binding">所在地域：北京</span>
			</div>
			<div class="pull-right">
				<button id="restart" class="btn btn-default">
					<span>重启服务</span>
				</button>
			</div>
		</div>
		<ul class="nav nav-tabs" role="tablist" id="setab">
			<li id="whitelist-tab" role="presentation" class="active">
				<a data-toggle="tab" href="#addedServer">已添加的服务配置</a></li>
			<!-- <li id="sqlInject-tab" role="presentation">
				<a data-toggle="tab" href="#sqlInject">未添加的服务器</a></li> -->
		</ul>
		<!-- <div class="panel-body pd0" id="whitelist"> -->
		<div class="tab-content">
			<div id="addedServer" role="tabpanel" class="tab-pane fade active in"  aria-labelledby="whitelist-tab">
				<table class="table table-hover table-se">
					<thead>
					<tr>
						<th>云服务器ID/名称</th>
						<th>公网/内网IP地址</th>
						<th>服务端口</th>
						<th>SLB协议端口</th>
						<th>状态</th>
						<th>网络类型</th>
						<th>付款方式</th>
						<th>健康检查状态</th>
						<th>权重</th>
						<th class="text-right" width="200px">
							<span style="padding-left:8px">操作</span>
						</th>
					</tr>
					</thead>
					<tbody id="tby">
					</tbody>
				</table>
			</div>
			<div id="sqlInject" role="tabpanel" class="tab-pane fade" aria-labelledby="sqlInject-tab">
				<div class="col-sm-12 col-md-12" style="margin: 10px 0px 10px 0px; padding-left: 0px">
					<div class="pull-left">
						<form class="form-inline" role="form">
							<div class="form-group">
								<select class="form-control">
									<option value="0" selected="selected">常规实例</option>
								</select>
							</div>
							<div class="form-group">
								<input type="text" class="form-control" size="48" placeholder="请输入实例名称或实例ID进行搜索">
							</div>
							<button type="submit" class="btn btn-default">搜索</button>
						</form>
					</div>
				</div>
				<table class="table table-hover table-se">
					<thead>
					<tr>
						<th>云服务器ID/名称</th>
						<th>公网/内网IP地址</th>
						<th>状态</th>
						<th>网络类型</th>
						<th>付款方式</th>
						<th class="text-right" width="200px">
							<span style="padding-left:8px">操作</span>
						</th>
					</tr>
					</thead>
					<tbody>
					<tr>
						<td>h-28x6n5ua98</td>
						<td>166.144.31.142(公网)</td>
						<td><span class="text-success">运行中</span></td>
						<td>经典网络</td>
						<td>包年包月</td>
						<td class="text-right">
							<a class="j_slb_health_drop1 btn-text">添加</a>
						</td>
					</tr>
					</tbody>
				</table>
			</div>
			<div style="height: 40px; margin-top: 20px;">
				<a class="btn btn-success" data-backdrop="false" data-toggle="modal" data-target="#myModal">添加</a>
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
