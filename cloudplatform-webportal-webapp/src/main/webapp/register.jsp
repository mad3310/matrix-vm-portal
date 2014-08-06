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
<script type="text/javascript" src="js/jqBootstrapValidation.js"></script>
<script type="text/javascript" src="js/bootstrap.min.js"></script>
<script type="text/javascript" src="js/scripts.js"></script>
<script>
	$(function() {
		$("input,select,textarea").not("[type=submit]").jqBootstrapValidation();
	});
</script>
</head>
<body>
	<div class="container">
		<div id="wrap">
			<%@include file="header.jsp"%>
			<div class="row clearfix">
			<div class="col-md-4"></div>
				<div class="col-md-4 column ">
					<form role="form">
						<div class="form-group">
							<label for="exampleInputEmail1">邮箱</label><input type="email"
								class="form-control" id="exampleInputEmail1" />
						</div>
						<div class="form-group">
							<label for="exampleInputPassword1">密码</label><input
								type="password" class="form-control" id="exampleInputPassword1" />
						</div>
						<div class="form-group">
							<label for="exampleInputPassword1">确认密码</label><input
								type="password" class="form-control" id="exampleInputPassword1" />
						</div>
						<div class="checkbox">
							<label><input type="checkbox" />同意条款</label>
						</div>
						<div class="pull-right">
						<button class="btn btn-default">取消</button>
						<button type="submit" class="btn btn-primary">注册</button>
						</div>
					</form>
				</div>
				<div class="col-md-4"></div>
			</div>
		</div>

	<%@ include file="footer.jsp"%>
	</div>


</body>

</html>
