<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="zh">

<head>
	<meta charset="utf-8"/>
	<meta http-equiv="X-UA-compatible" content="IE=edge,chrome=1"/>
	<meta name="viewpoint" content="width=device-width,initial-scale=1, maximum-scale=1, user-scalable=no"/>
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
				<h5>负载均衡服务配置</h5>
				<a href="/helpCenter/helpCenter.jsp?container=help-slblisten"  target="_black" data-toggle="tooltip" data-placement="top" title="如何配置">
					<span class="glyphicon glyphicon-question-sign text-muted" ></span>
				</a>
			</div>
		</div>
		<div class="table-responsive">
			<table class="table table-hover table-se">
				<thead>
					<tr>
						<th>SLB协议：端口</th>
						<th>状态</th>
						<th class="hidden-xs">转发规则</th>
						<th class="hidden-xs">获取真实IP</th>
						<th class="hidden-xs">会话保持</th>
						<th class="hidden-xs">健康检查</th>
						<th class="hidden-xs">带宽峰值</th>
						<th class="text-right" width="200px"><span style="padding-left: 8px">操作</span></th>
					</tr>
				</thead>
				<tbody id="tby">
					<tr>
					</tr>
				</tbody>
			</table>
		</div>
		<div id="addButton" class=" hidden" style="height: 40px; margin-top: 20px;">
			<a class="btn btn-success" data-backdrop="false" data-toggle="modal" data-target="#myModal">添加</a>
		</div>
	</div>
	<div class="modal fade" id="myModal" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-sm">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">添加服务监听</h4>
				</div>
				<form id="healthBase" name="healthBase" class="form-horizontal" role="form">
					<div class="modal-body">
						<div class="form-inline form-group">
							<label class="col-sm-4 control-label"> <span class="text-danger">*</span>协议：
							</label>
							<div class="col-sm-8 ng-scope">
								<select name="agentType" class="form-control ng-pristine ng-valid" style="width: 90px">
									<option value="HTTP">HTTP</option>
									<option value="TCP">TCP</option>
									<option value="MYSQL">MYSQL</option>
								</select> 
							</div>
						</div>
						<!-- <div class="form-inline form-group">
							<label class="col-sm-4 control-label"> <span class="text-danger">*</span>SLB端口：
							</label>
							<div class="col-sm-7 ng-scope">
								<input name="frontendPort" class="form-control" size="7" type="text">
							</div>
						</div> -->
						<div class="modal-footer">
							<button class="btn btn-primary" type="submit">添加监听</button>
							<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
	
	<div class="modal fade" id="modifyModal" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-sm">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">修改服务监听</h4>
				</div>
				<form id="modifyHealthBase" name="modifyHealthBase" class="form-horizontal" role="form">
					<div class="modal-body">
						<div class="form-inline form-group">
							<label class="col-xs-3 col-sm-4 control-label m-pdt"> <span class="text-danger">*</span>协议：
							</label>
							<div class="col-xs-9 col-sm-8 ng-scope">
								<select name="modifyAgentType" class="form-control ng-pristine ng-valid" style="width: 90px">
									<option value="HTTP">HTTP</option>
									<option value="TCP">TCP</option>
									<option value="MYSQL">MYSQL</option>
								</select> 
								<input type="hidden" id="id"  value=""/>
							</div>
						</div>
						<!-- <div class="form-inline form-group">
							<label class="col-sm-4 control-label"> <span class="text-danger">*</span>SLB端口：
							</label>
							<div class="col-sm-7 ng-scope">
								<input name="frontendPort" class="form-control" size="7" type="text">
							</div>
						</div> -->
						<div class="modal-footer">
							<button class="btn btn-primary" type="submit">修改</button>
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
seajs.use("${ctx}/static/page-js/cloudslb/slbConfig/main");
</script>

</html>
