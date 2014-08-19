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
		<%@include file="/common/header.jsp"%>
		<div id="wrap">
			<div class="row">
				<div class="col-md-12">
					<h3 class="text-left">申请中DB</h3>
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
						<div class="form-group">
							<label class="col-sm-2 control-label" for="inputEmail">正在申请</label>
							<div class="col-sm-4">
								<select class="form-control">
									<option>mcluster_cloud_db_apply</option>
									<option>phone_app_db_apply</option>
								</select>
							</div>
						</div>
					</form>
					<div class="col-sm-10">
					<table class="table table-bordered">
						<tr>
							<td>项目名称</td>
							<td>Mcluster-webportal-开发团队</td>
						</tr>
						<tr>
							<td>存储引擎</td>
							<td>InnoDB</td>
						</tr>
						<tr>
							<td>需求人</td>
							<td>贾跃亭</td>
						</tr>
						<tr>
							<td>原数据IP</td>
							<td>192.168.30.49</td>
						</tr>
						<tr>
							<td>原数据库port</td>
							<td>3306</td>
						</tr>
						<tr>
							<td>原数据库名</td>
							<td>Mcluster_db</td>
						</tr>
						<tr>
							<td>开发语言</td>
							<td>java</td>
						</tr>
					</table>

					</div>
					<div class="col-sm-10">
						<button id="db_apply_modify" type="submit" class="btn btn-default">修改</button>
					</div>
				</div>
			</div>
		</div>
		<%@include file="/common/footer.jsp"%>
	</div>
</body>
<script type="text/javascript">
	$('#db_apply_modify').on('click', function() {
		$(this).button('loading')
	location.href = "./db_applyform.jsp";
</script>
</html>
