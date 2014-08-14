<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html lang="en">
<head>
<meta charset="utf-8">
<title>PAAS云</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">

<!--link rel="stylesheet/less" href="less/bootstrap.less" type="text/css" /-->
<!--link rel="stylesheet/less" href="less/responsive.less" type="text/css" /-->
<!--script src="js/less-1.3.3.min.js"></script-->
<!--append ‘#!watch’ to the browser URL, then refresh the page. -->

<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/bootstrap-switch.css" rel="stylesheet">
<link href="css/style.css" rel="stylesheet">

<!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
    <script src="js/html5shiv.js"></script>
  <![endif]-->

<!-- Fav and touch icons -->
<link rel="apple-touch-icon-precomposed" sizes="144x144"
	href="img/apple-touch-icon-144-precomposed.png">
<link rel="apple-touch-icon-precomposed" sizes="114x114"
	href="img/apple-touch-icon-114-precomposed.png">
<link rel="apple-touch-icon-precomposed" sizes="72x72"
	href="img/apple-touch-icon-72-precomposed.png">
<link rel="apple-touch-icon-precomposed"
	href="img/apple-touch-icon-57-precomposed.png">
<link rel="shortcut icon" href="img/favicon.png">

<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/bootstrap.min.js"></script>
<script type="text/javascript" src="js/bootstrap-switch.min.js"></script>
<script type="text/javascript" src="js/scripts.js"></script>
</head>

<body>
	<div class="container">
		<div id="wrap">
			<%@include file="header.jsp"%>
			<div class="row">
				<div class="col-md-12">
					<h3 class="text-left">申请DB</h3>
				</div>
				<hr
					style="FILTER: alpha(opacity = 0, finishopacity = 100, style = 1)"
					width="100%" color=#987cb9 SIZE=3></hr>
			</div>


			<div class="row clearfix">
				<div class="col-md-3 column">
					<h2>通告：</h2>
					<p>关于数据库使用的通知、帮助和注意事项。</p>
					<p>
						<a class="btn" href="#">查看详细使用教程 »</a>
					</p>
				</div>
				<div class="col-md-9 column">
				

					<form class="form-horizontal" role="form">
						<div class="control-group">
							<label class="col-offset-2  col-sm-2 control-label" for="inputEmail">上线项目名称</label>
							<div class="col-sm-5">
								<input id="inputEmail" type="text" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="inputPassword">业务描述</label>
							<div class="controls">
								<input id="inputPassword" type="" />
							</div>  
						</div>
						<div class="control-group">
							<label class="control-label" for="inputPassword">需求人</label>
							<div class="controls">
								<input id="inputPassword" type="" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="inputPassword">原数据库IP</label>
							<div class="controls">
								<input id="inputPassword" type="" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="inputPassword">原数据库port</label>
							<div class="controls">
								<input id="inputPassword" type="" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="inputPassword">原数据库名</label>
							<div class="controls">
								<input id="inputPassword" type="" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="inputPassword">最大并发量</label>
							<div class="controls">
								<input id="inputPassword" type="" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="inputPassword">存储引擎</label>
							<div class="controls">
								<input id="inputPassword" type="" />
							</div>
						</div>
						
						<div class="control-group">
							<label class="control-label" for="inputPassword">访问IP地址范围</label>
							<div class="controls">
								<input id="inputPassword" type="" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="inputPassword">数据库管理IP</label>
							<div class="controls">
								<input id="inputPassword" type="" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="inputPassword">应用特点</label>
							<div class="controls">
								<input id="inputPassword" type="" />
							</div>
						</div>
						
						<div class="control-group">
							<label class="control-label" for="inputPassword">开发语言</label>
							<div class="controls">
								<input id="inputPassword" type="" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="inputPassword">链接类型</label>
							<div class="controls">
								<input id="inputPassword" type="" />
							</div>
						</div>
					</form>
					<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
					<button type="button" class="btn btn-primary">创建</button>
				</div>
			</div>
		</div>

		<%@ include file="footer.jsp"%>
	</div>
</body>
<script type="text/javascript">
	$("#signin>#loginbutton").click(function() {
		$("#signin").hide();
		$("#usercenter").show();
	});

	$("#headeregisterbutton").click(function() {
		window.location.href = './register.jsp';

	});

	$(document).ready(function() {
		$("#emailmessage").bootstrapSwitch();

		$("#signin").show();
		$("#sqlcluster").addClass("active");

		$("#userdata tr").click(function() {
			alert("hello");
		});
	});
</script>

</html>
