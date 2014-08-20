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
<%@include file="/common/common.jsp"%>
</head>

<body>
	<div class="container"">
		<div id="wrap">
			<%@include file="header.jsp"%>

			<div class="row">
				<div class="col-md-12">
					<h3 class="text-left">Mcluster管理</h3>
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

					<button type="button" href="#modal-container-22341"
						class="btn btn-primary" data-toggle="modal">添加Mcluster</button>
						
						
					<table id="userdata"
						class="table table-striped table-hover table-responsive">
						<thead>
							<tr>
								<th>Mcluster名称</th>
								<th>DB个数</th>
								<th>node_个数</th>
								<th>创建时间</th>
								<th>当前状态</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>移动</td>
								<td>1</td>
								<td>3</td>
								<td>01/04/2014</td>
								<td>正常</td>
							</tr>
							<tr>
								<td>视频</td>
								<td>2</td>
								<td>3</td>
								<td>07/04/2014</td>
								<td>异常</td>
							</tr>
							<tr>
								<td>云计算</td>
								<td>1</td>
								<td>3</td>
								<td>07/04/2014</td>
								<td>异常</td>
							</tr>

						</tbody>
					</table>


				</div>
			</div>
		</div>
		<div style="text-align: center;">
			<ul class="pagination pagination-sm">
				<li><a href="#">Prev</a></li>
				<li><a href="#">1</a></li>
				<li><a href="#">2</a></li>
				<li><a href="#">3</a></li>
				<li><a href="#">4</a></li>
				<li><a href="#">5</a></li>
				<li><a href="#">Next</a></li>
			</ul>
		</div>
		<%@ include file="footer.jsp"%>
	</div>
	
				<%@include file="applycluster1.jsp"%>
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
			location.href = "./mclusterdetail.jsp";
		});
	});
</script>

</html>
