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
	<!-- ui-css -->
	<link type="text/css" rel="stylesheet" href="${ctx}/static/css/ui-css/common.css"/>
	<title>app-dashboard</title>
</head>
<body>
<!-- top bar begin -->
	<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
      <div class="container-fluid">
        <div class="navbar-header">
          <a class="navbar-brand" href="${ctx}/dashboard"><img src="${ctx}/static/img/cloud.ico"/></a>
        </div>
        <div class="navbar-header">
          <a class="navbar-brand active" href="${ctx}/dashboard"><span class="glyphicon glyphicon-home"></span></a>
        </div>
        <div id="navbar" class="navbar-collapse collapse pull-right">
        	<form class="navbar-form navbar-right pull-left" role="form">
	            <div class="form-group">
	              <input type="text" placeholder="Search" class="form-control">
	            </div>
	            <button type="submit" class="btn btn-success"><span class="glyphicon glyphicon-search"></span></button>
	        </form>
            <ul class="nav navbar-nav">
	            <li><a href="#"><span class="glyphicon glyphicon-bell"></span></a></li>
	            <li class="dropdown">
	              <a href="#" class="dropdown-toggle" data-toggle="dropdown">${sessionScope.userSession.userName}<span class="caret"></span></a>
	              <ul class="dropdown-menu" role="menu">
	                <li><a href="#">用户中心</a></li>
	                <li><a href="#">我的订单</a></li>
	                <li><a href="#">账户管理</a></li>
	                <li class="divider"></li>
	                <li class="dropdown-header"><a href="${ctx}/account/logout">退出</a></li>
	              </ul>
	            </li>
	            <li><a href="#"><span class="glyphicon glyphicon-lock"></span></a></li>
	            <li><a href="#"><span class="glyphicon glyphicon-pencil"></span></a></li>
          </ul>
        </div><!--/.nav-collapse -->
      </div>
    </nav>
<!-- top bar end -->

<!-- navbar begin -->
<div class="navbar navbar-default mt50"> 
  <div class="container-fluid">
    <div class="navbar-header">
      <a class="navbar-brand" href="${ctx}/dashboard">Le云控制台首页</a>
    </div>
    <div id="navbar" class="navbar-collapse collapse pull-right">
      <ul class="nav navbar-nav hide">
        <li class="active"><a href="#"><span class="glyphicon glyphicon-phone"></span> 扫描二维码</a></li>
      </ul>
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
				<button class="btn btn-success btn-md" href="#">全部</button>
				<button class="btn btn-default btn-md" href="#">北京</button>
				</h5> 
			</div>
		</div>
		<div class="col-sm-12 col-md-6">
			<div class="pull-right">
				<h5 class="bdl-0">
				<button class="btn-default btn btn-md">刷新</button>
				<button class="btn-primary btn btn-md" onclick="location='${ctx}/detail/dbCreate'">新建数据库</button>
				</h5>
			</div>
		</div>
		<div class="col-sm-12 col-md-12">
			<div class="pull-left">
				<form class="form-inline" role="form">
					<div class="form-group">
						<select class="form-control">
							<option value="0" selected="selected">常规实例</option>
						</select>
					</div>
					<div class="form-group">
						<input type="text" class="form-control" size="48" placeholder="请输入实例名称或实例ID进行搜索">
					</div>
					<button type="submit" class="btn btn-default">搜索</button>
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
						<th>可用区类型</th>
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
										<span class="ng-binding">共有1条</span>， <span class="ng-binding">每页显示：20条</span>
									</div>
									<ul class="pagination pagination-sm">
										<li><a href="#">&laquo;</a></li>
										 <li><a href="#">&lt;</a></li>
										<li class="active"><a href="#">1</a></li>	
										<li><a href="#">&gt;</a></li>																		
										<li><a href="#">&raquo;</a></li>
									</ul>

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
		"bootstrap": "bootstrap/bootstrap/3.3.0/bootstrap.js"
	}
});

seajs.use("${ctx}/static/page-js/dbList/main");

</script>
</html>