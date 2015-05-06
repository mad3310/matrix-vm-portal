<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="zh">
<%@include file='main.jsp' %>
<style>
	table input{margin-top:0;}
</style>
<body>
<%@include file='header.jsp'%>
<!-- navbar begin -->
<div class="navbar navbar-default mt40" style="margin-bottom: 0px !important;">  
  <div class="container-fluid">
    <div class="navbar-header">
      <a class="navbar-brand" href="javascript:void(0)">oss实例详情</a>
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
				<span>实例列表</span>
				<button class="btn btn-success btn-md btn-region-display">全部</button>
				<button class="btn btn-default btn-md btn-region-display">北京</button>
				<!-- <button class="btn btn-default btn-md btn-region-display">杭州</button>
				<button class="btn btn-default btn-md btn-region-display">青岛</button>
				<button class="btn btn-default btn-md btn-region-display">香港</button> -->
				</h5> 
			</div>
		</div>
		<div class="col-sm-12 col-md-6">
			<div class="pull-right">
				<h5 class="bdl-0">
				<button class="btn-default btn btn-md" id="refresh"><span class="glyphicon glyphicon-refresh"></span>刷新</button>
				<button class="btn-primary btn btn-md" onclick="window.open('${ctx}/buy/oss')">新建oss实例</button>
				<!-- <button class="btn-warning btn btn-md" onclick="">删除缓存实例</button> -->
				</h5>
			</div>
		</div>
		<div class="col-sm-12 col-md-12">
			<div class="pull-left">
				<form class="form-inline" role="form">
					<div class="form-group">
						<input onkeyup="value=value.replace(/[\W]/g,'') " 
						onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))" 
						id="cacheName" type="text" class="form-control" size="48" placeholder="请输入实例名称进行搜索">
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
						<th>配额</th>
						<th>地域
							<!-- <a data-toggle="tooltip" data-placement="top" title="单可用区指数据库集群位于同一个域,多可用区指数据库集群位于多个域" data-content="dfadfadfads">
								<span class="glyphicon glyphicon-question-sign text-muted" ></span>
							</a> -->
						</th>
						<th>可用区</th>
						<th>创建时间</th>
						<th>付费类型</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody id="tby">
				</tbody>
				<tfoot id="paginatorBlock">
					<tr class="tfoot">
						<td width="10">
							<input type="checkbox">
						</td>
						<td colspan="10">
							<div class="pull-left">
									<div class="clearfix" pagination-info="paginationInfo">
										<div class="pull-left">
											<button class="btn btn-default" disabled="disabled" style="height:30px;font-size:12px;">批量续费</button>
										</div>
										<div class="pull-left">
											<button class="btn btn-default" disabled="disabled" style="height:30px;margin-left:10px;font-size:12px;">批量删除</button>
										</div>
									</div>
							</div>
							<div class="pull-right">
								<div class="pagination-info">
									<span class="ng-binding">共有<span id="totalRecords">1</span>条</span>， 
									<span class="ng-binding">每页显示：<span id="recordsPerPage">10</span>条</span>&nbsp;
								    <ul id="paginator" class="pagination pagination-sm"><li class="disabled"><a href="javascript:void(0);" title="Go to first page">«</a></li><li class="disabled"><a href="javascript:void(0);" title="Go to previous page">‹</a></li><li class="active"><a href="javascript:void(0);" title="Current page is 1">1</a></li><li class="disabled"><a href="javascript:void(0);" title="Go to next page">›</a></li><li class="disabled"><a href="javascript:void(0);" title="Go to last page">»</a></li></ul>
								</div>
							</div>
							<div class="clearfix"></div>
						</td>
					</tr>
				</tfoot>
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
		"bootstrap": "bootstrap/bootstrap/3.3.0/bootstrap.js",
		"paginator": "bootstrap/paginator/bootstrap-paginator.js"
	}
});
seajs.use("${ctx}/static/page-js/cloudswift/containerList/main");
/*self define*/
</script>
</html>