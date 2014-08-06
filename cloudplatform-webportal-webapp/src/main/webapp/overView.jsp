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
			<div class="row clearfix">
				<div class="col-md-2 column">
					<h2>通告：</h2>
					<p>关于数据库使用的通知、帮助和注意事项。</p>
					<p>
						<a class="btn" href="#">查看详细使用教程 »</a>
					</p>
				</div>
				<div class="col-md-10 column">
					<div class="modal fade" id="modal-container-22341" role="dialog"
						aria-labelledby="smallModal" aria-hidden="true">
						<div class="modal-dialog">
							<div class="modal-content">
								<div class="modal-header">
									<button type="button" class="close" data-dismiss="modal"
										aria-hidden="true">×</button>
									<h4 class="modal-title" id="myModalLabel">添加Mcluster</h4>
								</div>
								<div class="modal-body row">
									<form class="form-horizontal">
										<div class="col-md-offset-1 col-md-5">
											<div class="control-group">
												<label class="control-label" for="input">选择存储引擎</label>
												<div class="controls">
													<input type="text" id="inputEmail" class="email"
														placeholder="初级">
												</div>
											</div>
											<div class="control-group">
												<label class="control-label" for="inputEmail">选择内存大小</label>
												<div class="controls">
													<input type="text" id="inputEmail" class="email"
														placeholder="1G">
												</div>
											</div>
											<div class="control-group">
												<label class="control-label" for="inputPassword">选择存储大小</label>
												<div class="controls">
													<input type="password" id="inputPassword" class="password"
														placeholder="10G">
												</div>
											</div>
											

										</div>
										<div class="col-md-offset-1 col-md-5">
										<div class="control-group">
												<label for="name">备份周期</label>
												<div class="controls">
													<select>
														<option>1小时</option>
														<option>3小时</option>
														<option>12小时</option>
														<option>一天</option>
														<option>一周</option>
													</select>
												</div>
											</div>
											<div class="control-group">
												<label for="name">邮件通知</label>
												<div>
													<input id="emailmessage" type="checkbox" checked />
												</div>
											</div>
										</div>
									</form>
								</div>
								<div class="modal-footer">
									<button type="button" class="btn btn-default"
										data-dismiss="modal">取消</button>
									<button type="button" class="btn btn-primary">申请添加</button>
								</div>
							</div>

						</div>

					</div>

					<button type="button" href="#modal-container-22341"
						class="btn btn-success btn-lg" data-toggle="modal">添加Mcluster</button>
					<table id="userdata"
						class="table table-striped table-hover table-responsive">
						<thead>
							<tr>
								<th>ID</th>
								<th>数据库名</th>
								<th>创建时间</th>
								<th>当前状态</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>1</td>
								<td>blogsql</td>
								<td>01/04/2014</td>
								<td>正常</td>
							</tr>
							<tr>
								<td>2</td>
								<td>shopsql</td>
								<td>07/04/2014</td>
								<td>异常</td>
							</tr>
							<tr>
								<td>3</td>
								<td>mysql</td>
								<td>07/04/2014</td>
								<td>异常</td>
							</tr>

						</tbody>
					</table>


				</div>
			</div>

		</div>
		<div style="text-align:center;">
			<ul class="pagination pagination-sm">
				<li><a href="#">Prev</a>
				</li>
				<li><a href="#">1</a>
				</li>
				<li><a href="#">2</a>
				</li>
				<li><a href="#">3</a>
				</li>
				<li><a href="#">4</a>
				</li>
				<li><a href="#">5</a>
				</li>
				<li><a href="#">Next</a>
				</li>
			</ul>
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
