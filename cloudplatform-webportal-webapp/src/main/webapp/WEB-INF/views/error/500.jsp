<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh">
<head>
	<meta charset="utf-8"/>
	<meta http-equiv="X-UA-compatible" content="IE=edge,chrome=1"/>
	<meta name="viewpoint" content="width=device-width,initial-scale=1"/>
	<!-- bootstrap css -->
	<link type="text/css" rel="stylesheet" href="../../../static/css/bootstrap.min.css"/>
	<!-- fontawesome css -->
	<link type="text/css" rel="stylesheet" href="../../../static/css/font-awesome.min.css"/>
	<!-- ui-css -->
	<link type="text/css" rel="stylesheet" href="../../../static/css/ui-css/common.css"/>
	<title>500错误</title>
</head>
<body>
<!-- top bar begin -->
	<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation" style="min-height:40px;height:40px;">
      <div class="container-fluid">
        <div class="navbar-header">
          <a class="navbar-brand color" href="#" style="padding-top:2px;height:40px !important;"><img src="../../../static/img/logo.png"/></a>
	      <a class="navbar-brand color top-bar-btn" href="#" style="white-space:nowrap; font-size:13px"><i class="fa fa-home text-20"></i></a>
        </div>
        <div id="navbar" class="navbar-collapse collapse pull-right">
            <ul class="nav navbar-nav">
	            <li><a href="#" class="hlight"><span class="glyphicon glyphicon-bell"></span></a></li>
	            <li class="dropdown">
	              <a href="#" class="dropdown-toggle hlight" data-toggle="dropdown">test-username<span class="caret"></span></a>
	              <ul class="dropdown-menu" role="menu">
	                <li><a href="#">用户中心</a></li>
	                <li><a href="#">我的订单</a></li>
	                <li><a href="#">账户管理</a></li>
	                <li class="divider"></li>
	                <li><a href="${ctx}/account/logout">退出</a></li>
	              </ul>
	            </li>
	            <li><a href="#" class="hlight"><span class="glyphicon glyphicon-lock"></span></a></li>
	            <li><a href="#" class="hlight"><span class="glyphicon glyphicon-pencil"></span></a></li>
          </ul>
        </div><!--/.nav-collapse -->
      </div>
    </nav>
<!-- top bar end -->
<!-- center body -->
<div class="container">
	<div class="col-md-12 text-center">
		<div><img src="../../../static/img/error500.png"></div>
		<p class="text-muted"><b>服务器调皮，开小差出游。请稍后重试</b></p>
		<p class="text-muted"><a><span class="text-muted">返回乐视云首页</span></a></p>
	</div>
</div>
<!-- end center body -->
<!-- footer links-->
<footer class="mt50">
	<div class="col-md-12 text-muted text-center">
		<ul class="list-inline">
			<li><a><span class="text-muted">乐视网</span></a></li>
			<li><a><span class="text-muted">乐视云计算</span></a></li>
			<li><a><span class="text-muted">网酒网</span></a></li>
			<li><a><span class="text-muted">乐视超级电视</span></a></li>
			<li><a><span class="text-muted">乐视云盘</span></a></li>
			<li><a><span class="text-muted">乐视视频PC版</span></a></li>
			<li><a><span class="text-muted">看球</span></a></li>
		</ul>
		<p>网络文化经营许可证 文网文[2009]221号</p>
		<p>Copyright &copy; 2004-2015 乐视网（letv.com）All rights reserved.</p>
	</div>
</footer>
<!-- end footer links -->
</body>
</html>
