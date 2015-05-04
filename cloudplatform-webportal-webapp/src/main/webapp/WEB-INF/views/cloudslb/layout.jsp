
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<meta charset="utf-8" />
<meta http-equiv="X-UA-compatible" content="IE=edge,chrome=1" />
<meta name="viewpoint" content="width=device-width,initial-scale=1" />
<!-- bootstrap css -->
<link type="text/css" rel="stylesheet" href="${ctx}/static/css/bootstrap.min.css" />
<!-- fontawesome css -->
<link type="text/css" rel="stylesheet" href="${ctx}/static/css/font-awesome.min.css" />
<!-- ui-css -->
<link type="text/css" rel="stylesheet" href="${ctx}/static/css/ui-css/common.css" />
<title>SLB管理控制台</title>
</head>
<body>
	<!-- 全局参数 start -->
	<input class="hidden" value="${slbId}" name="slbId" id="slbId" type="text" />
	<!-- 全局参数 end -->
	<!-- top bar begin -->
	<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation" style="min-height: 40px; height: 40px;">
		<div class="container-fluid">
			<div class="navbar-header">
				<a class="navbar-brand color" href="${ctx}/dashboard" style="padding-top: 2px; height: 40px !important;">
					<img src="${ctx}/static/img/logo.png" />
				</a>
				<a class="navbar-brand color top-bar-btn" href="${ctx}/dashboard" style="white-space: nowrap; font-size: 13px">
					<i class="fa fa-home text-20"></i>
				</a>
				<a class="navbar-brand color" href="${ctx}/list/slb" style="margin-left: 10px; height: 40px !important; font-size: 15px">
					<i class="fa fa-database text-10">
					</i>&nbsp;负载均衡SLB
				</a>
			</div>
			<div id="navbar" class="navbar-collapse collapse pull-right">
				<ul class="nav navbar-nav">
					<li><a href="javascript:void(0)" class="hlight"><span class="glyphicon glyphicon-bell"></span></a></li>
					<li class="dropdown"><a href="javascript:void(0)" class="dropdown-toggle hlight" data-toggle="dropdown">${sessionScope.userSession.userName}<span class="caret"></span></a>
						<ul class="dropdown-menu" role="menu">
							<li><a href="javascript:void(0)">用户中心</a></li>
							<li><a href="javascript:void(0)">我的订单</a></li>
							<li><a href="javascript:void(0)">账户管理</a></li>
							<li class="divider"></li>
							<li><a href="${ctx}/account/logout">退出</a></li>
						</ul></li>
					<li><a href="javascript:void(0)" class="hlight"><span class="glyphicon glyphicon-lock"></span></a></li>
					<li><a href="javascript:void(0)" class="hlight"><span class="glyphicon glyphicon-pencil"></span></a></li>
				</ul>
			</div>
			<!--/.nav-collapse -->
		</div>
	</nav>
	<!-- top bar end -->

	<!-- navbar begin -->
	<div class="navbar navbar-default mt40" style="margin-bottom: 0px !important;">
		<div class="container-fluid">
			<div class="navbar-header">
				<a class="navbar-brand" href="javascript:void(0)">负载均衡<font color="#FF9C17">SLB</font></a>
				<ul class="nav navbar-nav">
					<li class="active"><a class="navbar-brand" href="javascript:void(0)">实例</a></li>
				</ul>
			</div>
		</div>
	</div>

	<!-- main-content begin-->
	<div class="container-fluid">
		<div class="row main-header">
			<!-- main-content-header begin -->
			<div class="col-sm-12 col-md-6">
				<div class="pull-left">
					<h3>
						<span class="fa  fa-cubes"></span>
						<span id="slbName"></span>
						<span style="display: inline-block;vertical-align:super;">
							<small id="slbStatus" class="text-success text-xs"></small>
						</span>
							<a class="btn btn-default btn-xs" href="${ctx}/list/slb">
						<span class="glyphicon glyphicon-step-backward"></span>
							返回实例列表
						</a>
					</h3>
				</div>
			</div>
			<div class="col-sm-12 col-md-6" style="margin-top: 35px;">
				<div class="pull-right">
					<span class="glyphicon glyphicon-paperclip font-disabled"></span><span class="font-disabled">使用限制和注意事项</span>
				</div>
			</div>
		</div>
		<!-- main-content-header end-->

		<div class="row">
			<!-- main-content-center-begin -->
			<nav class="col-sm-2 col-md-2">
				<div class="sidebar sidebar-line sidebar-selector">
					<ul class="nav nav-sidebar li-underline">
						<li class="active"><a class="text-sm" src="${ctx}/detail/slbBaseInfo/${slbId}" href="javascript:void(0)">实例详情</a></li>
						<li><a class="text-sm" src="${ctx}/detail/slbConfig/${slbId}" href="javascript:void(0)">服务监听配置</a></li>
						<li><a class="text-sm" src="${ctx}/detail/backend/server/${slbId}" href="javascript:void(0)">后端服务配置</a></li>
                        <li><a  class="text-sm" href="javascript:void(0)"><span class="glyphicon glyphicon glyphicon-chevron-right"></span>系统资源监控</a>
	                         <ul class="nav hide">
	                            <li><a  class="text-sm" src="${ctx}/monitor/slb/cpu/${slbId}" href="javascript:void(0)">cpu使用率</a></li>
	                            <li><a  class="text-sm" src="${ctx}/monitor/slb/disk/${slbId}" href="javascript:void(0)">磁盘</a></li>
	                            <li><a  class="text-sm" src="${ctx}/monitor/slb/memory/${slbId}" href="javascript:void(0)">内存</a></li>
	                            <li><a  class="text-sm" src="${ctx}/monitor/slb/network/${slbId}" href="javascript:void(0)">网络</a></li>
	                        </ul>
	                    </li>
					</ul>
				</div>
			</nav>
			<div class="embed-responsive embed-responsive-16by9 col-sm-10">
				<iframe class="embed-responsive-item" id="frame-content" src="${ctx}/detail/slbBaseInfo/${slbId}"></iframe>
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
    "bootstrap": "bootstrap/bootstrap/3.3.0/bootstrap.js"
}
});
seajs.use("${ctx}/static/page-js/cloudslb/layout/main");
</script>

</html>