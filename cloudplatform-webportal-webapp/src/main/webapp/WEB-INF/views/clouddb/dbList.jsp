<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="zh">
<head>
	<meta charset="utf-8"/>
	<meta http-equiv="X-UA-compatible" content="IE=edge,chrome=1"/>
	<meta name="viewport" content="width=device-width,initial-scale=1"/>
	<!-- bootstrap css -->
	<link type="text/css" rel="stylesheet" href="${ctx}/static/css/bootstrap.min.css"/>
	<!-- fontawesome css -->
	<link type="text/css" rel="stylesheet" href="${ctx}/static/css/font-awesome.min.css"/>
	<!-- ui-css -->
	<link type="text/css" rel="stylesheet" href="${ctx}/static/css/ui-css/common.css"/>
	<title>RDS管理控制台</title>
</head>
<body>
<%@ include file="../../layouts/header.jsp"%>
<!-- main-content begin-->
<div class="container-fluid">
	<div class="row main-header overHidden"> <!-- main-content-header begin -->
		<div class="col-xs-12 col-sm-12 col-md-6">
			<div class="pull-left">
				<h5>
				<span>关系型数据库管理</span>
				<button class="btn btn-success btn-md btn-region-display hidden-xs">全部</button>
				<button class="btn btn-default btn-md btn-region-display hidden-xs">北京</button>
				<button class="btn btn-success btn-sm btn-region-display hidden-sm hidden-md hidden-lg">全部</button>
				<button class="btn btn-default btn-sm btn-region-display hidden-sm hidden-md hidden-lg">北京</button>
				</h5> 
			</div>
		</div>
		<div class="col-xs-12 col-sm-12 col-md-6 hidden-xs">
			<div class="pull-right">
				<h5 class="bdl-0">
				<button class="btn-default btn btn-md" id="refresh"><span class="glyphicon glyphicon-refresh"></span>刷新</button>
				<button class="btn-primary btn btn-md" onclick="window.open('${ctx}/detail/dbCreate')">购买数据库</button>
				</h5>
			</div>
		</div>
		<div class="">
			<div class="pull-left">
				<form class="form-inline" role="form">
					<div class="form-group col-xs-9 col-sm-10">
						<input  id="dbName" type="text" class="form-control" size="48" placeholder="请输入实例名称进行搜索">
					</div>
					<div class="col-xs-3 col-sm-2">
						<button id="search" type="button" class="btn btn-default"><span class='hidden-xs'>搜索</span><span class='glyphicon glyphicon-search hidden-sm hidden-md hidden-lg'></span></button>
					</div>
				</form>
			</div>
		</div>
	</div><!-- main-content-header end-->

	<div class="row"><!-- main-content-center-begin -->
		<div class="col-sm-12 col-md-12">
			<table class="table table-hover table-se">
				<thead>
					<tr>
						<th width="10">
							<input type="checkbox">
						</th>
						<th class="padding-left-32">实例名称</th>
						<th>状态</th>
						<th class="hidden-xs">实例类型</th>
						<th class="hidden-xs">数据库类型</th>
						<th class="hidden-xs">可用区类型
							<a data-toggle="tooltip" data-placement="top" title="单可用区指数据库集群位于同一个域,多可用区指数据库集群位于多个域">
								<span class="glyphicon glyphicon-question-sign text-muted" ></span>
							</a>
						</th>
						<th class="hidden-xs">所在可用区</th>
						<th class="hidden-xs">所在服务集群</th>
						<th class="hidden-xs">付费类型</th>
						<th class="text-right">操作</th>
					</tr>
				</thead>
				<tbody id="tby">
				</tbody>
				<tfoot id="paginatorBlock">
					<!-- <tr class="tfoot" > -->
						<td width="10">
							<input type="checkbox">
						</td>
						<td colspan="9" class="row">
							<div class="col-xs-4 col-sm-6" style="margin:2px 0;padding:0;">
							<div class="pull-left">
									<div pagination-info="paginationInfo">
										<div class="pull-left">
											<button class="btn btn-default hidden-xs" disabled="disabled" style="height:30px;font-size:12px;">批量续费</button>
											<button class="btn btn-default btn-sm hidden-sm hidden-md hidden-lg" disabled="disabled"><span class='glyphicon glyphicon-shopping-cart'></span> 续费</button>
										</div>
									</div>
							</div>
							</div>
							<div class="col-xs-8 col-sm-6" style="margin:2px 0;padding:0;">
							<div class="pull-right m-fltPage">
								<div class="pagination-info hidden-xs">
									<span class="ng-binding">共有<span id="totalRecords"></span>条</span>， 
									<span class="ng-binding">每页显示：<span id="recordsPerPage"></span>条</span>&nbsp;
								</div>
								<ul id='paginator'></ul>
							</div>
							</div>
							<div class="clearfix"></div>
						</td>
					<!-- </tr> -->
				</tfoot>
			</table>
		    <!-- <div class="help-block hidden" id="noData">没有记录</div> -->
		</div>
	</div><!-- main-content-center-end -->
</div>

</body>
<!-- js -->
<script type="text/javascript" src="${ctx}/static/modules/seajs/2.3.0/sea.js"></script>
<script type="text/javascript">

// Set configuration
seajs.config({
	base: "${ctx}/static/modules/",
	alias: {
		"jquery": "jquery/2.0.3/jquery.min.js",
		"bootstrap": "bootstrap/bootstrap/3.3.0/bootstrap.js",
		"paginator": "bootstrap/paginator/bootstrap-paginator.js"
	}
});

seajs.use("${ctx}/static/page-js/clouddb/dbList/main");

</script>
</html>
