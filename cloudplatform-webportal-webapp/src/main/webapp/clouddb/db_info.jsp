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
				<div class="col-md-3">
					<h3 class="text-left">DB信息</h3>
				</div>

				<hr
					style="FILTER: alpha(opacity = 0, finishopacity = 100, style = 1)"
					width="100%" color=#987cb9 SIZE=3></hr>
			</div>

			<div class="row clearfix">
				<div class="col-md-3 column">
					<h2>使用方法：</h2>
					<p>请按使用文档使用，如有疑问请call姚发亮同学</p>
					<p>
						<a class="btn" href="#">查看详细使用教程 »</a>
					</p>
				</div>
				<div class="col-md-9 column">

					<button id="db_apply_botton" type="button" class="btn btn-primary"
						data-toggle="modal">返回</button>
					<button id="db_modify_botton" type="button" class="btn btn-default"
						data-toggle="modal">修改数据库信息</button>

					<div class="table-responsive">
						<table id="db_info_table" class="table table-bordered">
							<caption>数据库详情</caption>

							<tr>
								<td>数据库名</td>
								<td>webportal</td>
							</tr>
							<tr>
								<td>数据库用户名</td>
								<td>jiasmd</td>
							</tr>
							<tr>
								<td>nodeIP</td>
								<td>192.168.30.49;192.168.30.50;192.168.30.51</td>
							</tr>
							<tr>
								<td>VIP</td>
								<td>192.168.1.101</td>
							</tr>
														<tr>
								<td>访问ip列表</td>
								<td>192.168.56.49-192.168.30.149;192.168.30.51</td>
							</tr>
							<tr>
								<td>管理ip列表</td>
								<td>192.168.30.52-192.168.30.53;192.168.30.54</td>
							</tr>
							<tr>
								<td>数据库引擎</td>
								<td>innoDB</td>
							</tr>
							<tr>
								<td>最大并发量</td>
								<td>1000000000000000000000/s</td>
							</tr>
							<tr>
								<td>链接类型</td>
								<td>多为长链接</td>
							</tr>
							<tr>
								<td>链接类型</td>
								<td>多为长链接</td>
							</tr>
							<tr>
								<td>邮件通知</td>
								<td>已打开</td>
							</tr>
							<tr>
								<td>备份时间</td>
								<td>天黑闭眼后</td>
							</tr>

						</table>
					</div>



				</div>
			</div>
		</div>
		<%@include file="footer.jsp"%>
	</div>
</body>
<script type="text/javascript">
	$('#db_apply_botton').on('click', function() {
		$(this).button('loading')
		location.href = "./db_list.jsp";
	})
	$('#db_modify_botton').on('click', function() {
		$(this).button('loading')
		location.href = "./db_applyform.jsp";
	})
</script>
</html>
