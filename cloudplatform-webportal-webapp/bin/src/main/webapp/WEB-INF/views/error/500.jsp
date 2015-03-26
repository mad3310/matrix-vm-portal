<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.letv.common.util.ConfigUtil"%>
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
<!-- center body -->
<div class="container">
	<div class="col-md-12 text-center">
		<div><img src="../../../static/img/error500.png"></div>
		<p class="text-muted"><b>服务器调皮，开小差出游。请稍后重试</b></p>
		<p class="text-muted"><a href="<%=ConfigUtil.getString("webportal.local.http") %>"><span class="text-muted">返回乐视云首页</span></a></p>
	</div>
</div>
<!-- end center body -->
<!-- footer links-->
<footer class="mt50">
	<div class="col-md-12 text-muted text-center">
		<ul class="list-inline">
			<li><a href="http://www.letv.com"><span class="text-muted">乐视网</span></a></li>
			<li><a href="http://www.wangjiu.com"><span class="text-muted">网酒网</span></a></li>
			<li><a href="http://shop.letv.com"><span class="text-muted">乐视商城</span></a></li>
			<li><a href="http://pan.letv.com"><span class="text-muted">乐视云盘</span></a></li>
			<li><a href="http://www.lelife.com"><span class="text-muted">乐生活</span></a></li>
			<li><a href="http://client.pc.letv.com/pc/download"><span class="text-muted">PC客户端</span></a></li>
		</ul>
		<p>网络文化经营许可证 文网文[2009]221号</p>
		<p>Copyright &copy; 2004-2015 乐视网（letv.com）All rights reserved.</p>
	</div>
</footer>
<!-- end footer links -->
</body>
</html>
