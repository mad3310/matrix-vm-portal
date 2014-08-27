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
				<div class="col-md-3">
					<h3 class="text-left">Mcluster创建</h3>
				</div>
				<div  class="col-md-6">
					<div id="pageMessage"></div>
				</div>
				<div class="col-md-3"></div>
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
					<form id="mclusterCreateForm" data-toggle="validator" class="form-horizontal" role="form" action="${ctx}/mcluster/save">
						<div class="form-group">
							<label class="col-sm-2 control-label">Mcluster名称</label>
							<div class="col-sm-4">
								<input class="form-control" id="mclusterName" name="mclusterName" type="text" />
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
<script type="text/javascript">

$(document).ready(function() {
	$("#mclusterCreateForm").bootstrapValidator({
		  message: 'This value is not valid',
          feedbackIcons: {
              valid: 'glyphicon glyphicon-ok',
              invalid: 'glyphicon glyphicon-remove',
              validating: 'glyphicon glyphicon-refresh'
          },
          fields: {
        	  mclusterName: {
                  validMessage: 'The username looks great',
                  validators: {
                      notEmpty: {
                          message: 'Mcluster名称不能为空!'
                      },
			          stringLength: {
			              max: 40,
			              message: '项目名过长'
			          }
                  }
              }
          }
      });
});
</script>
</html>
