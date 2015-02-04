<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="zh">
<head>
	<meta charset="utf-8"/>
	<meta http-equiv="X-UA-compatible" content="IE=edge,chrome=1"/>
	<meta name="viewpoint" content="width=device-width,initial-scale=1"/>
	<!-- bootstrap css -->
	<link type="text/css" rel="stylesheet" href="${ctx}/static/css/bootstrap.min.css"/>
	<!-- fontawesome css -->
	<link type="text/css" rel="stylesheet" href="${ctx}/static/css/font-awesome.min.css"/>
	<!-- ui-css -->
	<link type="text/css" rel="stylesheet" href="${ctx}/static/css/ui-css/common.css"/>
	<title>RDS管理控制台</title>
</head>
<body>
<!-- top bar begin -->
	<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation" style="min-height:40px;height:40px;">
      <div class="container-fluid">
        <div class="navbar-header">
          <a class="navbar-brand color" href="${ctx}/dashboard" style="padding-top:2px;height:40px !important;"><img src="${ctx}/static/img/logo.png"/></a>
	      <a class="navbar-brand color top-bar-btn" href="${ctx}/dashboard" style="white-space:nowrap; font-size:13px"><i class="fa fa-home text-20"></i></a>
          <a class="navbar-brand color" href="${ctx}/list/db" style="margin-left:10px;height:40px !important; font-size:15px"><i class="fa fa-database text-10"></i>&nbsp;关系型数据库RDS</a>
        </div>
        <div id="navbar" class="navbar-collapse collapse pull-right">
            <ul class="nav navbar-nav">
	            <li><a href="javascript:void(0)" class="hlight"><span class="glyphicon glyphicon-bell"></span></a></li>
	            <li class="dropdown">
	              <a href="javascript:void(0)" class="dropdown-toggle hlight" data-toggle="dropdown">${sessionScope.userSession.userName}<span class="caret"></span></a>
	              <ul class="dropdown-menu" role="menu">
	                <li><a href="javascript:void(0)">用户中心</a></li>
	                <li><a href="javascript:void(0)">我的订单</a></li>
	                <li><a href="javascript:void(0)">账户管理</a></li>
	                <li class="divider"></li>
	                <li><a href="${ctx}/account/logout">退出</a></li>
	              </ul>
	            </li>
	            <li><a href="javascript:void(0)" class="hlight"><span class="glyphicon glyphicon-lock"></span></a></li>
	            <li><a href="javascript:void(0)" class="hlight"><span class="glyphicon glyphicon-pencil"></span></a></li>
          </ul>
        </div><!--/.nav-collapse -->
      </div>
    </nav>
<!-- top bar end -->

<!-- navbar begin -->
<div class="navbar navbar-default mt40" style="margin-bottom: 0px !important;">  
  <div class="container-fluid">
    <div class="navbar-header">
      <a class="navbar-brand" href="javascript:void(0)">关系型数据库<font color="#FF9C17">RDS</font></a>
    </div>
  </div>
</div>
	

<!-- navbar end -->
<!-- main-content begin-->
<div class="container-fluid">
	<div class="row main-header overHidden"> <!-- main-content-header begin -->
		<div class="col-sm-12 col-md-6">
			<div class="pull-left">
				<h5>
				<span>关系型数据库管理</span>
				<button class="btn btn-success btn-md btn-region-display">全部</button>
				<button class="btn btn-default btn-md btn-region-display">北京</button>
				</h5> 
			</div>
		</div>
		<div class="col-sm-12 col-md-6">
			<div class="pull-right">
				<h5 class="bdl-0">
				<button class="btn-default btn btn-md" id="refresh"><span class="glyphicon glyphicon-refresh"></span>刷新</button>
				<button class="btn-primary btn btn-md" onclick="window.open('${ctx}/detail/dbCreate')">新建数据库</button>
				</h5>
			</div>
		</div>
		<div class="col-sm-12 col-md-12">
			<div class="pull-left">
				<form class="form-inline" role="form">
					<div class="form-group">
						<input  id="dbName" type="text" class="form-control" size="48" placeholder="请输入实例名称进行搜索">
					</div>
					<button id="search" type="button" class="btn btn-default">搜索</button>
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
						<th>运行状态</th>
						<th>实例类型</th>
						<th>数据库类型</th>
						<th>可用区类型
							<a data-toggle="tooltip" data-placement="top" title="单可用区指数据库集群位于同一个域,多可用区指数据库集群位于多个域" data-content="dfadfadfads">
								<span class="glyphicon glyphicon-question-sign text-muted" ></span>
							</a>
						</th>
						<th>所在可用区</th>
						<th>付费类型</th>
						<th class="text-right">操作</th>
					</tr>
				</thead>
				<tbody id="tby">
				</tbody>
				
				<tfoot>
					<tr class="tfoot" >
						<td width="10">
							<input type="checkbox">
						</td>
						<td colspan=" 8">
							<div class="pull-left">
									<div pagination-info="paginationInfo">
										<div class="pull-left">
											<button class="btn btn-default" disabled="disabled" style="height:30px;font-size:12px;">批量续费</button>
										</div>
									</div>
							</div>
							<div class="pull-right">
								<div class="pagination-info">
									<span class="ng-binding">共有<span id="totalRecords"></span>条</span>， 
									<span class="ng-binding">每页显示：<span id="recordsPerPage"></span>条</span>&nbsp;
								    <ul id='paginator'></ul>
								</div>
							</div>
						</td>
					</tr>
				</tfoot>
			</table>
		    
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

seajs.use("${ctx}/static/page-js/dbList/main");

</script>
</html>
