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
	<title>monitor</title>
</head>

<body>
	<!-- top bar begin -->
	<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
      <div class="container-fluid">
        <div class="navbar-header">
          <a class="navbar-brand" href="${ctx}/dashboard"><img src="${ctx}/static/img/cloud.ico"/></a>
        </div>
        <div class="navbar-header">
          <a class="navbar-brand active" href="${ctx}/dashboard"><span class="glyphicon glyphicon-home"></span></a>
        </div>
        <div id="navbar" class="navbar-collapse collapse pull-right">
        	<form class="navbar-form navbar-right pull-left" role="form">
	            <div class="form-group">
	              <input type="text" placeholder="Search" class="form-control">
	            </div>
	            <button type="submit" class="btn btn-success"><span class="glyphicon glyphicon-search"></span></button>
	        </form>
            <ul class="nav navbar-nav">
	            <li><a href="#"><span class="glyphicon glyphicon-bell"></span></a></li>
	            <li class="dropdown">
	              <a href="#" class="dropdown-toggle" data-toggle="dropdown">${sessionScope.userSession.userName}<span class="caret"></span></a>
	              <ul class="dropdown-menu" role="menu">
	                <li><a href="#">用户中心</a></li>
	                <li><a href="#">我的订单</a></li>
	                <li><a href="#">账户管理</a></li>
	                <li class="divider"></li>
	                <li class="dropdown-header"><a href="${ctx}/account/logout">退出</a></li>
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
	      <a class="navbar-brand" href="${ctx}/dashboard">Le云控制台首页</a>
	    </div>
	    <div id="navbar" class="navbar-collapse collapse pull-right">
	      <ul class="nav navbar-nav hide">
	        <li class="active"><a href="#"><span class="glyphicon glyphicon-phone"></span> 扫描二维码</a></li>
	      </ul>
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
						<a class="btn btn-default btn-xs" href="RDSlist.html">
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
		            <li><a class="text-sm" href="${ctx}/detail/baseInfo/${dbId}">基本信息</a></li>
		            <li><a  class="text-sm" href="${ctx}/detail/account/${dbId}">账号管理</a></li>
		            <li><a  class="text-sm" href="#">
							<span class="glyphicon glyphicon glyphicon-chevron-right"></span>
							系统资源监控
						</a>
						<ul class="nav hide">
							<li><a  class="text-sm" href="${ctx}/detail/monitor/${dbId}">磁盘空间</a></li>
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
		            <li><a class="text-sm" href="${ctx}/detail/security/${dbId}">安全控制</a></li>
		        </ul>
			</div>
		</nav>
			<div class="col-sm-10 col-md-10">
				<div class="se-heading">
					<div class="pull-left">
						<h5 class="">Monitor Chart Demo</h5>
					</div>
					<div class="pull-right">
						<span class="monitor-date-block">
							<ul class="nav nav-pills">
								<li class="time-block active" >
									<a>1天</a>
								</li>
								<li class="time-block" >
									<a>7天</a>
								</li>
								<li class="time-block" >
									<a>1个月</a>
								</li>
								<li class="time-block" >
									<a>3个月</a>
								</li>
								<li class="time-block" >
									<a>6个月</a>
								</li>
								<li class="time-block" >
									<a>1年</a>
								</li>
							</ul>
						</span>
					</div>
				</div>
				<div class="monitor-charts">
					<div class="chart-content" style="width:100%">
						<div id="chart-container" class="chart-container">							
						</div>
					</div>
					<div class="footer" style="width:100%;height:200px;">						
					</div>
				</div>
				<!-- main-content-center-end -->			
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
		"highcharts": "highcharts/highcharts.src.js"
	}
});

seajs.use("${ctx}/static/page-js/monitor/main");

</script>
</html>