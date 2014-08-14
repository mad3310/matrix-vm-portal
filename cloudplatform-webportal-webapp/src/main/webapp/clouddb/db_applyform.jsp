<%@ page language="java" pageEncoding="UTF-8"%>
<%@include file="/common/common.jsp"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>申请数据库</title>
<!-- bootstrap switch -->
<link href="../static/styles/bootstrap/css/bootstrap-switch.min.css"
	rel="stylesheet">
<script src="../static/scripts/bootstrap/bootstrap-switch.min.js"></script>
</head>
<body>
	<div class="container">
	<%@include file="header.jsp"%>
		<div id="wrap">
		<div class="row">
			<div class="col-md-12">
				<h3 class="text-left">DB申请</h3>
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
					<!--has-success has-feedback 输入框显示输入成功，边框变绿 -->
					<div class="form-group">
						<label class="col-sm-2 control-label" for="inputEmail">上线项目名称</label>
						<div class="col-sm-4">
							<input class="form-control" id="inputEmail" type="text" />
							<!--再输入框添加绿色对勾 -->
							<!-- <span class="glyphicon glyphicon-ok form-control-feedback"></span> -->
						</div>
					</div>
					<!--has-warning has-feedback 输入框显示输入警告，边框变黄 -->
					<div class="form-group">
						<label class="col-sm-2 control-label" for="inputEmail">业务描述</label>
						<div class="col-sm-8">
							<textarea class="form-control" rows="3"></textarea>
							<!--再输入框添加黄色叹号警告 -->
							<!--  <span class="glyphicon glyphicon-warning-sign form-control-feedback"></span>-->
						</div>
					</div>
					<!--输入框显示输入失败，边框变红 -->
					<div class="form-group">
						<label class="col-sm-2 control-label" for="inputEmail">原数据库IP</label>
						<div class="col-sm-4">
							<input class="form-control" id="inputEmail" type="text"
								placeholder="127.0.0.1" />
							<!--再输入框添加红色叉-->
						<!--	<span class="glyphicon glyphicon-remove form-control-feedback"></span>-->
						</div>
					</div>

					<div class="form-group">
						<label class="col-sm-2 control-label" for="inputEmail">原数据库port</label>
						<div class="col-sm-2">
							<input class="form-control" id="inputEmail" type="text"
								placeholder="3306" />
						</div>
					</div>

					<div class="form-group">
						<label class="col-sm-2 control-label" for="inputEmail">原数据库名</label>
						<div class="col-sm-4">
							<input class="form-control" id="inputEmail" type="text" />
						</div>
					</div>

					<div class="form-group">
						<!-- fieldset disabled 将输入框置灰，不可输入 -->
						<fieldset disabled>
							<label class="col-sm-2 control-label" for="disabledSelect">存储引擎</label>
							<div class="col-sm-4">
								<select class="form-control">
									<option>InnoDB</option>
									<option>MyISAM</option>
									<option>MEMORY</option>
									<option>MERGE</option>

								</select>
							</div>
						</fieldset>
					</div>

					<div class="form-group">
						<label class="col-sm-2 control-label" for="inputEmail">访问数据库IP列表</label>
						<div class="col-sm-6">
							<input class="form-control" id="inputEmail" type="text"
								placeholder="192.168.1.102-192.168.105;192.168.1.107" />
						</div>

					</div>

					<div class="form-group">
						<label class="col-sm-2 control-label" for="inputEmail">数据库管理IP</label>
						<div class="col-sm-6">
							<input class="form-control" id="inputEmail" type="text"
								placeholder="192.168.1.102-192.168.105;192.168.1.107" />
						</div>
					</div>

					<div class="form-group">
						<label class="col-sm-2 control-label" for="inputEmail">读写比例</label>
						<div class="col-sm-2">
							<input class="form-control" id="inputEmail" type="text"
								placeholder="1:1" />
						</div>
					</div>

					<div class="form-group">
						<label class="col-sm-2 control-label" for="inputEmail">最大并发量</label>
						<div class="col-sm-2">
							<input class="form-control" id="inputEmail" type="text"
								placeholder="100/s" />
						</div>
					</div>

					<div class="form-group">
						<label class="col-sm-2 control-label" for="inputEmail">开发语言</label>
						<div class="col-sm-4">
							<input class="form-control" id="inputEmail" type="text"
								placeholder="python，java" />
						</div>
					</div>

					<div class="form-group">
						<label class="col-sm-2 control-label" for="inputEmail">链接类型</label>
						<div class="col-sm-4">
							<select class="form-control">
								<option>长链接</option>
								<option>短链接</option>
							</select>
						</div>
					</div>


					<div class="form-group">
						<label class="col-sm-2 control-label" for="inputEmail">邮件通知</label>
						<div class="col-sm-3">
							<div class="switch switch-small">
								<input id="emailmessage_switch" type="checkbox" checked />
							</div>
						</div>
					</div>


					<div class="form-group">
						<div class="col-sm-offset-2 col-sm-10">
							<button type="submit" class="btn btn-primary">申请</button>
						</div>
					</div>
				</form>

			</div>
		</div>
	</div>
	<%@include file="footer.jsp"%>
</div>

</body>
<script type="text/javascript">
	$(document).ready(function() {
		$("#emailmessage_switch").bootstrapSwitch();
	});
</script>

</html>

