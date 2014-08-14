<%@ page language="java" pageEncoding="UTF-8"%>
<%@include file="/common/common.jsp"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Bootstrap 101 Template</title>
</head>
<body>
	<div class="container"">
		<%@include file="header.jsp"%>
		<div id="wrap">
			<div class="row">
				<div class="col-md-12">
					<h3 class="text-left">DB管理</h3>
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

					<button id="db_apply" type="button" class="btn btn-default"
						data-toggle="modal">申请DB</button>
					<button id="db_on_apply" type="button" class="btn btn-default"
						data-toggle="modal">申请中DB</button>
					<button id="db_create" type="button" class="btn btn-default"
						data-toggle="modal">创建DB</button>

					<table id="userdata"
						class="table table-striped table-hover table-responsive">
						<thead>
							<tr>
								<th>DB名称</th>
								<th>创建时间</th>
								<th>当前状态</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>phone_app_db</td>
								<td>01/04/2014</td>
								<td>正常</td>
							</tr>
							<tr>
								<td>phone_tv_db</td>
								<td>07/04/2014</td>
								<td>异常</td>
							</tr>
						</tbody>
					</table>
				</div>

			</div>

		</div>

		<%@include file="footer.jsp"%>
	</div>
</body>
<script type="text/javascript">
	$(document).ready(function() {
		$("#userdata tr").click(function() {
			location.href = "./db_info.jsp";
		});
	});

	$('#db_apply').on('click', function() {
		$(this).button('loading')
		location.href = "./db_applyform.jsp";
	});
	$('#db_create').on('click', function() {
		$(this).button('loading')
		location.href = "./db_create.jsp";
	});
	$('#db_on_apply').on('click', function() {
		$(this).button('loading')
		location.href = "./db_on_apply.jsp";
	});
</script>
</html>
