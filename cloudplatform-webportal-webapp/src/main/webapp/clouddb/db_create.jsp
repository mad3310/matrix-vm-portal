<%@ page language="java" pageEncoding="UTF-8"%>
<%@include file="/common/common.jsp"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>创建数据库</title>

</head>

<body>
	<div class="container">
	<%@include file="header.jsp"%>
		<div id="wrap">
		<div class="row">
			<div class="col-md-12 column">
				<h3 class="text-left">创建DB</h3>

					<div class="progress">
						<div id="db_create_progress"
							class="progress-bar progress-bar-striped active"
							role="progressbar" aria-valuenow="45" aria-valuemin="0"
							aria-valuemax="100" style="width: 60%">
							<span class="sr-only">45% Complete</span>
						</div>
					</div>

			</div>
			<hr
				style="FILTER: alpha(opacity = 0, finishopacity = 100, style = 1)"
				width="100%" color=#987cb9 SIZE=3></hr>
		</div>

		<div class="row clearfix">
			<div class="col-md-3 column">
				<h2>注意：</h2>
				<p>只有申请创建数据库通过后，才能创建，如果没有申请，请先申请数据库</p>
				<p>
					<a class="btn" href="#">查看详细使用教程 »</a>
				</p>
			</div>
			<div class="col-md-9 column">

				<form class="form-horizontal" role="form">
					<!--has-success has-feedback 输入框显示输入成功，边框变绿 -->
					<div class="form-group has-success has-feedback">
					
					
						<label class="col-sm-2 control-label" for="inputEmail">申请通过</label>
						<div class="col-sm-4">
							<select class="form-control">
								<option>mcluster_cloud_db</option>
								<option>phone_app_db</option>
							</select>
						</div>
					</div>

					<div class="form-group">
						<div class="col-sm-offset-2 col-sm-1">
							<button id="db_create_button" type="submit"
								class="btn btn-primary">创建</button>
						</div>
						<div class="col-sm-2">
							<button id="db_create_button" type="submit"
								class="btn btn-primary">无可创建数据库,去申请</button>
						</div>
					</div>
				</form>

			</div>
		</div>
	</div>
	<%@include file="footer.jsp"%>
</div>
</body>

</html>
