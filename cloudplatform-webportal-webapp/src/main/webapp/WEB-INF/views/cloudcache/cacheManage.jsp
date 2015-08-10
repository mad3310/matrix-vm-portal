<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="zh">
<%@include file='main.jsp' %>
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
      <a class="navbar-brand" href="javascript:void(0)">缓存实例管理</a>
    </div>
  </div>
</div>
	

<!-- navbar end -->
<!-- main-content begin-->
<div class="container-fluid">
	<div class="row main-header overHidden"> <!-- main-content-header begin -->
		<div class="col-sm-12 col-md-12">
			<div class="pull-right">
				<h5 class="bdl-0">
				<button class="btn-primary btn btn-md" onclick="">配置信息</button>
				<button class="btn-default btn btn-md" id="refresh"><span class="glyphicon glyphicon-refresh"></span>刷新</button>				
				</h5>
			</div>
			<div class="clearfix"></div>				
		</div>
	</div><!-- main-content-header end-->

	<div class="row"><!-- main-content-center-begin -->
		<div class="col-sm-12 col-md-12">
			<table class="table table-hover table-se">
				<thead>
					<tr>
						<th class="padding-left-32">实例名称</th>
						<th>运行状态</th>
						<th>实例类型</th>
						<th>已用空间及配额</th>
						<th>访问地址</th>
						<th>访问端口</th>
						<th>地域</th>
						<th>可用区</th>
						<th>创建时间</th>
					</tr>
					<tr style="border-bottom:1px solid #ddd;">
						<td><span>cyxtest</span></td>
						<td><span>正常</span></td>
						<td><span>持久化</span></td>
						<td>
							<!-- <span style="padding:4px 6px; background:#43bfe3"></span>
							<span style="padding:4px 6px; background:#43bfe3"></span>
							<span style="padding:4px 6px; background:#43bfe3"></span>
							<span style="padding:4px 6px; background:#43bfe3"></span>
							<span style="padding:4px 6px; background:#f1f1f1"></span>
							<span style="padding:4px 6px; background:#f1f1f1"></span>
							<span style="padding:4px 6px; background:#f1f1f1"></span>
							<span style="padding:4px 6px; background:#f1f1f1"></span>
							<span style="padding:4px 6px; background:#f1f1f1"></span>
							<span style="padding:4px 6px; background:#f1f1f1"></span> -->
							<div class="progress" style="margin-bottom:0;">
							  <div class="progress-bar progress-bar-info progress-bar-striped" role="progressbar" aria-valuenow="20" aria-valuemin="0" aria-valuemax="100" style="width: 40%">
							    <span class="sr-only">40%</span>
							  </div>
							</div>
						</td>
						<td><span>10.154.156.57</span></td>
						<td><span>11211</span></td>
						<td><span>北京</span></td>
						<td><span>酒仙桥DC</span></td>
						<td><span>2015-03-25</span></td>
					</tr>
				</thead>
				<tbody id="tby">
				</tbody>
			</table>
		    
		</div>
	</div><!-- main-content-center-end -->
</div>
</body>
<script type="text/javascript" src="${ctx}/static/modules/seajs/2.3.0/sea.js"></script>
<script>
	//Set configuration
seajs.config({
	base: "${ctx}/static/modules/",
	alias: {
		"jquery": "jquery/2.0.3/jquery.min.js",
		"bootstrap": "bootstrap/bootstrap/3.3.0/bootstrap.js"
	}
});

/*self define*/
</script>
</html>