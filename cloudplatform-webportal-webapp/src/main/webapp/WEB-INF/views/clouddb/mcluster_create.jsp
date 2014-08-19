<%@ page language="java" pageEncoding="UTF-8"%>
<%@include file="/common/common.jsp"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>申请数据库</title>

</head>
<body>
	<div class="container">
		<%@include file="/common/header.jsp"%>
		<div id="wrap">
			<div class="row">
				<div class="col-md-12">
					<h3 class="text-left">Mcluster创建</h3>
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
					<form class="form-horizontal" role="form" action="${ctx}/mcluster/save">
						<!--has-success has-feedback 输入框显示输入成功，边框变绿 -->
						<div class="form-group has-success has-feedback">
							<label class="col-sm-2 control-label" for="inputEmail">Mcluster名称</label>
							<div class="col-sm-4">
								<input class="form-control" id="mclusterName" name="mclusterName" type="text" />
								<!--再输入框添加绿色对勾 -->
								<span class="glyphicon glyphicon-ok form-control-feedback"></span>
							</div>
						</div>

						<div class="form-group">
							<div class="col-sm-offset-2 col-sm-10">
								<button id="formSave" type="submit" class="btn btn-primary">创建</button>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
		<%@include file="/common/footer.jsp"%>
	</div>
</body>
</html>
