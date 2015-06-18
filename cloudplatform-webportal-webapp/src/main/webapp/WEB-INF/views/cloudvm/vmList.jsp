<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="zh">
<head>
	<meta charset="utf-8"/>
	<meta http-equiv="X-UA-compatible" content="IE=edge,chrome=1"/>
	<meta name="viewport" content="width=device-width,initial-scale=1, maximum-scale=1, user-scalable=no"/>
	<!-- bootstrap css -->
	<link type="text/css" rel="stylesheet" href="${ctx}/static/css/bootstrap.min.css"/>
	<!-- fontawesome css -->
	<link type="text/css" rel="stylesheet" href="${ctx}/static/css/font-awesome.min.css"/>
	<!-- ui-css -->
	<link type="text/css" rel="stylesheet" href="${ctx}/static/css/ui-css/common.css"/>
	<title>云主机管理控制台</title>
</head>
<body>
<%@ include file="../../layouts/header.jsp"%>
<!-- main-content begin-->
<div class="container-fluid">
	<div class="row main-header overHidden"> <!-- main-content-header begin -->
		<div class="col-xs-12 col-sm-12 col-md-6">
			<div class="pull-left">
				<h5>
				<span>云主机管理</span>&nbsp;&nbsp;
				<select>
				  <option>全部</option>
				  <option>酒仙桥电信通</option>
				  <option>惠普DC</option>
				</select>				
				</h5> 
			</div>
		</div>
		<div class="col-xs-12 col-sm-12 col-md-6 hidden-xs">
			<div class="pull-right">
				<h5 class="bdl-0">
				<button class="btn-default btn btn-md" id="refresh"><span class="glyphicon glyphicon-refresh"></span>刷新</button>
				<button class="btn-primary btn btn-md" onclick="window.open('${ctx}/detail/vmCreate')">购买云主机</button>
				</h5>
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
						<th class="padding-left-32">云主机名称</th>
						<th class="hidden-xs">镜像名称</th>
						<th class="hidden-xs">IP 地址</th>
						<th class="hidden-xs">配置</th>						
						<th class="hidden-xs">状态</th>
						<th class="hidden-xs">区域</th>
						<th class="text-right">操作</th>
					</tr>
				</thead>
				<tbody id="tby">
				<tr class="data-tr">
					<td width="10"><input type="checkbox" name="mcluster_id" value="789"></td>
					<td class="hidden-xs"><a href="/detail/vm/789">matrix-test</a></td>
					<td class="hidden-xs"><span>cirros-0.3.2-x86_64</span></td>
					<td class="hidden-xs"><span>172.16.0.6</span></td>
					<td class="hidden-xs"><span>nano | 64MB 内存| 1 虚拟内核 | 1.0GB 盘</span></td>
					<td class="hidden-xs"><span>Active</span></td>
					<td class="hidden-xs"><span>惠普DC</span></td>
					<td class="text-right hidden-xs"><a href="/detail/db/619">管理</a>&nbsp;<a href="/detail/db/619">删除</a></td>
				</tr>
				</tbody>				
			</table>
		    <!-- <div class="help-block hidden" id="noData">没有记录</div> -->
		</div>
	</div><!-- main-content-center-end -->
</div>
<script type="text/javascript" src="${ctx}/static/modules/jquery/2.0.3/jquery.min.js"></script>
	<script type="text/javascript">
		$('#navbar-menu .navbar-header').append('<a class="navbar-brand m-brand" href="/list/vm">云主机</a>');
	</script>
</body>
</html>
