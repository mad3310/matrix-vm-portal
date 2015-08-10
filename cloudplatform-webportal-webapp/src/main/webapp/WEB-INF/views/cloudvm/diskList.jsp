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
				<div class="col-md-offset-4 col-md-4 alertool" id="alertool">
      		</div>
	<div class="row main-header overHidden"> <!-- main-content-header begin -->
		<div class="col-xs-12 col-sm-12 col-md-6">
			<div class="pull-left">
				<h5>
				<span>磁盘管理</span>		
				<div class="region-city-list">
					<input id="city_region_selected" type="hidden" value="" />
				</div>			
				</h5> 
			</div>
		</div>
		<div class="col-xs-12 col-sm-12 col-md-6 hidden-xs">
			<div class="pull-right">
				<h5 class="bdl-0">
				<button class="btn-default btn btn-md" id="refresh"><span class="glyphicon glyphicon-refresh"></span>刷新</button>
				<button class="btn-primary btn btn-md" onclick="window.open('${ctx}/detail/diskCreate')">购买云盘</button>
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
						<th class="padding-left-32">云盘ID</th>
						<th class="hidden-xs">状态</th>
						<th class="hidden-xs">大小</th>
						<th class="hidden-xs">区域</th>
						<th class="text-right">操作</th>
					</tr>
				</thead>
				<tbody id="tby">
				</tbody>
				<tfoot id="paginatorBlock">
					<!-- <tr class="tfoot" > -->
						<td width="10">
						</td>
						<td colspan="9" class="row">
							<div class="col-xs-4 col-sm-6" style="margin:2px 0;padding:0;">
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
						</td>
					<!-- </tr> -->
				</tfoot>				
			</table>
		    <!-- <div class="help-block hidden" id="noData">没有记录</div> -->
		</div>
	</div><!-- main-content-center-end -->
</div>
<!--确认对话框-->
	<div id="dialog-box" class="modal">
		<div class="modal-dialog modal-sm">
			<div class="modal-content">
				<div class="modal-header">
			<!--	<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span><span class="sr-only">关闭</span>
					</button>-->
					<h5 id="dialog-box-title" class="modal-title"></h5>
				</div>
				<div class="modal-body clearfix">
					<div class="col-xs-1 col-sm-1 col-md-1 warning-sign">
						<span class="glyphicon glyphicon-exclamation-sign"></span>
					</div>
					<div id="dialog-box-text" class="col-xs-11 col-sm-10 "></div>
				</div>
				<div class="modal-footer">
					<button id="dialogBoxSubmit" type="button" class="btn btn-primary" data-dismiss="modal">确定</button>
					<button id="dialogBoxCancel" type="button" class="btn btn-default" data-dismiss="modal">取消</button>
				</div>
			</div>
		</div>
	</div>
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

seajs.use("${ctx}/static/page-js/cloudvm/diskList/main");

</script>
</body>
</html>
