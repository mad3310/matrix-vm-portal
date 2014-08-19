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

	<div class="container">
		<%@include file="/common/header.jsp"%>
		<div id="wrap">
			<div class="row">
				<div class="col-md-12">
					<h3 class="text-left">管理员审核</h3>
				</div>
				<hr
					style="FILTER: alpha(opacity = 0, finishopacity = 100, style = 1)"
					width="100%" color=#987cb9 SIZE=3></hr>
			</div>

			<div class="row clearfix">
				<div class="col-md-3 column">

					<ul class="nav nav-list">
						<li class="nav-header">审核创建DB</li>
						<li class="active"><a href="#"><i
								class="icon-white icon-home"></i>手机端应用数据库</a></li>
						<li><a href="#"><i class="icon-book"></i>webportal专用数据库</a></li>
						<li><a href="#"><i class="icon-pencil"></i>老贾测试数据库</a></li>
						<li class="nav-header">审核修改DB</li>
						<li><a href="#"><i class="icon-user"></i>云paas数据库</a></li>
						<li><a href="#"><i class="icon-cog"></i> 小苹果数据库</a></li>
						<li class="divider"></li>
						<li><a href="#"><i class="icon-flag"></i>gameDB</a></li>
					</ul>

				</div>
				<div class="col-md-9 column">

					<table class="table table-bordered">
						<caption>申请详细信息</caption>
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
							<td>手机号</td>
							<td>15888888888</td>
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

					<button id="db_create" type="button" class="btn btn-default"
						data-toggle="modal">通过</button>
					<button type="button" href="#modal-container-22341"
						class="btn btn-default" data-toggle="modal">不通过</button>
				</div>
			</div>
		</div>
		<%@include file="/common/footer.jsp"%>
	</div>
	<div class="modal fade" id="modal-container-22341" role="dialog"
		aria-labelledby="smallModal" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">×</button>
					<h3 class="modal-title" id="myModalLabel">不通过</h3>
				</div>
				<div class="modal-body row">
					<form class="form-horizontal" role="form">
						<div class="form-group">
							<label for="name">原因和建议</label>
							<textarea class="form-control" rows="3"></textarea>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
					<button type="button" class="btn btn-primary">确定</button>
				</div>
			</div>

		</div>

	</div>
</body>
<script type="text/javascript">
	$('#db_apply').on('click', function() {
		$(this).button('loading')
		location.href = "./db_applyform.jsp";
	})
	$('#db_create').on('click', function() {
		$(this).button('loading')
		location.href = "./db_create.jsp";
	})

	$(function() {
		$('#collapseFour').collapse({
			toggle : false
		})
	});
	$(function() {
		$('#collapseTwo').collapse('show')
	});
	$(function() {
		$('#collapseThree').collapse('toggle')
	});
	$(function() {
		$('#collapseOne').collapse('hide')
	});
</script>
</html>
