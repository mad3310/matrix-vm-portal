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
	<title>account</title>
</head>

<body>
	<!-- 全局参数 start -->
	<input class="hidden" value="${dbId}" name="dbId" id="dbId" type="text" />
	<!-- 全局参数 end -->
	<!-- top bar begin -->
	<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
      <div class="container-fluid">
        <div class="navbar-header">
          <a class="navbar-brand" href="${ctx}/dashboard"><img src="${ctx}/static/img/cloud.ico"/></a>
        </div>
        <div class="navbar-header">
          <a class="navbar-brand active" href="${ctx}/dashboard"><span class="glyphicon glyphicon-home"></span></a>
        </div>
        <div class="navbar-header">
          <a class="navbar-brand active" href="${ctx}/list/db"><span class="glyphicon glyphicon-th-large">关系型数据库 RDS</span></a>
        </div>
        <div id="navbar" class="navbar-collapse collapse pull-right">
            <ul class="nav navbar-nav">
	            <li><a href="#"><span class="glyphicon glyphicon-bell"></span></a></li>
	            <li class="dropdown">
	              <a href="#" class="dropdown-toggle" data-toggle="dropdown">${sessionScope.userSession.userName}<span class="caret"></span></a>
	              <ul class="dropdown-menu" role="menu">
	                <li><a href="#">用户中心</a></li>
	                <li><a href="#">我的订单</a></li>
	                <li><a href="#">账户管理</a></li>
	                <li class="divider"></li>
	                <li><a href="${ctx}/account/logout">退出</a></li>
	              </ul>
	            </li>
	            <li><a href="#"><span class="glyphicon glyphicon-lock"></span></a></li>
	            <li><a href="#"><span class="glyphicon glyphicon-pencil"></span></a></li>
          </ul>
        </div><!--/.nav-collapse -->
      </div>
    </nav>
	<!-- top bar end -->

	<!-- navbar begin -->
	<div class="navbar navbar-default mt50"> 
	  <div class="container-fluid">
	    <div class="navbar-header">
	      <a class="navbar-brand" href="#">关系型数据库<font color="#FF9C17">RDS</font></a>
	    </div>
	  </div>
	</div>
	<!-- navbar end -->
	
	<!-- main-content begin-->
	<div class="container-fluid">
		<div class="row main-header">
			<!-- main-content-header begin -->
			<div class="col-sm-12 col-md-6">
				<div class="pull-left">
					<h3>
						<span class="text-success glyphicon glyphicon-phone"></span>
						<span id="serviceName" data-toggle="tooltip" data-placement="bottom" title="rdsenn6ryenn6r">rdsenn6ryenn6r...</span>
						<span style="display: inline-block;vertical-align:super;">
							<small class="text-success text-xs">(运行中...)</small>
						</span>
						<a class="btn btn-default btn-xs" href="${ctx}/list/db">
							<span class="glyphicon glyphicon-step-backward"></span>
							返回实例列表
						</a>
					</h3>
				</div>
			</div>
			<div class="col-sm-12 col-md-6">
				<div class="pull-right">
					<h3>
						<small>
							<span>
								<a>功能指南</a>
								<button class="btn btn-default btn-xs">
									<span class="glyphicon glyphicon-eject" id="rds-icon-guide"></span>
								</button>
							</span>
						</small>
						<small>
							<span>
								<button class="btn-warning btn btn-sm" data-toggle="modal" data-target="#myModalNetchange">内外网切换</button>
							</span>
						</small>
						<small>
							<span>
								<button class="btn-danger btn btn-sm" data-toggle="modal" data-target="#myModalCaseRestart">重启实例</button>
							</span>
						</small>
						<small>
							<span>
								<button class="btn-default btn btn-sm" data-toggle="modal" data-target="#myModalBackCase">备份实例</button>
							</span>
						</small>
						<small>
							<span>
								<button class="btn-default btn btn-sm glyphicon glyphicon-list"></button>
							</span>
						</small>
					</h3>
				</div>
			</div>
		</div>
		<!-- main-content-header end-->
		<div class="row">
			<!-- main-content-center-begin -->
			<nav class="col-sm-2 col-md-2">
				<div class="sidebar sidebar-line sidebar-selector">
					<ul class="nav nav-sidebar li-underline">
			            <li class="active"><a class="text-sm" src="${ctx}/detail/baseInfo/${dbId}">基本信息</a></li>
			            <li><a  class="text-sm" src="${ctx}/detail/account/${dbId}">账号管理</a></li>
			            <li><a  class="text-sm" href="#"><span class="glyphicon glyphicon glyphicon-chevron-right"></span>系统资源监控</a>
							<ul class="nav hide">
								<li><a  class="text-sm" src="${ctx}/detail/monitor/${dbId}">磁盘空间</a></li>
								<li><a  class="text-sm" href="#">IOPS</a></li>
								<li><a  class="text-sm" href="#">连接数</a></li>
								<li><a  class="text-sm" href="#">CPU利用率</a></li>
								<li><a  class="text-sm" href="#">网络流量</a></li>
								<li><a  class="text-sm" href="#">QPS/TPS</a></li>
								<li><a  class="text-sm" href="#">InnoDB缓冲池</a></li>
								<li><a  class="text-sm" href="#">InnoDB读写量</a></li>
								<li><a  class="text-sm" href="#">InnoDB读写次数</a></li>
								<li><a  class="text-sm" href="#">InnoDB日志</a></li>
								<li><a  class="text-sm" href="#">临时表</a></li>
								<li><a  class="text-sm" href="#">MyISAM key Buffer</a></li>
								<li><a  class="text-sm" href="#">MyISAM读写次数</a></li>
								<li><a  class="text-sm" href="#">COMDML</a></li>
								<li><a  class="text-sm" href="#">ROWDML</a></li>
							</ul>
						</li>
			            <li><a  class="text-sm" href="#">备份与恢复</a></li>
			            <li><a  class="text-sm" href="#">参数设置</a></li>
			            <li><a class="text-sm" href="#">日志管理</a></li>
			            <li><a  class="text-sm" href="#">性能优化</a></li>
			            <li><a class="text-sm" href="#">阈值报警</a></li>
			            <li><a class="text-sm" src="${ctx}/detail/security/${dbId}">安全控制</a></li>
			        </ul>
				</div>
			</nav>
			<div class="col-sm-10 col-md-10">
				<div class="embed-responsive embed-responsive-16by9">
				  <iframe class="embed-responsive-item" id = "frame-content" src="${ctx}/detail/baseInfo/${dbId}"></iframe>
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
		"bootstrap": "bootstrap/bootstrap/3.3.0/bootstrap.js"
	}
});

seajs.use("${ctx}/static/page-js/accountManager/main");
</script>

</html>