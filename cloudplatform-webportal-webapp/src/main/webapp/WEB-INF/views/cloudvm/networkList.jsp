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
<div class="container-fluid vm-network">
				<div class="col-md-offset-4 col-md-4 alertool" id="alertool">
      		</div>
	<div class="row main-header"> <!-- main-content-header begin -->
		<div class="col-xs-12 col-sm-12 col-md-12">
			<div class="pull-left">
				<h5>	
				  <ul class="nav nav-tabs" role="tablist">
				    <li role="presentation" class="active"><a href="#vpc" aria-controls="vpc" role="tab" data-toggle="tab">私有网络</a></li>
				    <li role="presentation"><a href="#subnet" aria-controls="subnet" role="tab" data-toggle="tab">子网</a></li>
				  </ul>	
				  <div class="region-city-list">
						<input id="city_region_selected" type="hidden" value="All" />
				  </div>		
				</h5> 
			</div>
			<div class="pull-right search-box">
				<form class="form-inline" role="form">
					<div class="form-group col-xs-9 col-sm-10">
						<input  id="networkName" type="text" class="form-control" size="48" placeholder="请输入实例名称进行搜索">
					</div>
					<div class="col-xs-3 col-sm-2">
						<button id="search" type="button" class="btn btn-default"><span class='hidden-xs'>搜索</span><span class='glyphicon glyphicon-search hidden-sm hidden-md hidden-lg'></span></button>
					</div>
				</form>
			</div>
		</div>
	</div><!-- main-content-header end-->

	<div class="row"><!-- main-content-center-begin -->
		<div class="col-sm-12 col-md-12 tab-content">
			<div role="tabpanel" class="tab-pane active" id="vpc">
				<div class="pull-right">
					<h5 class="bdl-0">
					<button class="btn-default btn btn-md" id="refresh"><span class="glyphicon glyphicon-refresh"></span>刷新</button>
					<button class="btn-primary btn btn-md" id="show_vpc_modal_button">创建网络</button>
					</h5>
				</div>
				<table class="table table-hover table-se table-vpc">
					<thead>
						<tr>
							<th width="10">
								<input type="checkbox">
							</th>
							<th class="padding-left-32">网络ID</th>
							<th class="hidden-xs">子网</th>
							<th class="hidden-xs">状态</th>
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
			</div>
			<div role="tabpanel" class="tab-pane" id="subnet">
				<div class="pull-right">
					<h5 class="bdl-0">
					<button class="btn-default btn btn-md" id="refresh"><span class="glyphicon glyphicon-refresh"></span>刷新</button>
					<button class="btn-primary btn btn-md" id="show_subnet_modal_button">创建子网</button>
					</h5>
				</div>
				<table class="table table-hover table-se table-subnet">
					<thead>
						<tr>
							<th width="10">
								<input type="checkbox">
							</th>
							<th class="padding-left-32">网络ID</th>
							<th class="hidden-xs">子网</th>
							<th class="hidden-xs">状态</th>
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
			</div>
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
<!--创建vpc-->
<div class="modal fade" id="vpc_modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">创建私有网络</h4>
      </div>
      <div class="modal-body">
        <form class="form-horizontal">
          <div class="form-group">
		    <label for="vpc_region" class="col-sm-3 control-label">区域</label>
		    <div class="col-sm-9">
		      <select class="form-control" id="vpc_region_selector">
		      	<option value="">请选择地域</option>
		      </select>
		    </div>
		  </div>
		  <div class="form-group">
		    <label for="vpc_name" class="	col-sm-3 control-label">私有网络名称</label>
		    <div class="col-sm-9">
		      <input type="text" class="form-control" id="vpc_name" placeholder="私有网络名称">
		    </div>
		  </div>
		</form>
      </div>
      <div class="modal-footer">
        <button type="button" id="vpc_create_button" class="btn btn-primary">创建</button>
        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
      </div>
    </div>
  </div>
</div>
<!--创建vpc-->
<!--创建subnet-->
<div class="modal fade" id="subnet_modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">创建子网</h4>
      </div>
      <div class="modal-body">
        <form class="form-horizontal">
          <div class="form-group">
		    <label for="subnet_region" class="col-sm-3 control-label">区域</label>
		    <div class="col-sm-9">
		      <select class="form-control" id="subnet_region_selector">
		      	 <option value="">请选择地域</option>
		      </select>
		    </div>
		  </div>
		  <div class="form-group">
		    <label for="subnet_region" class="col-sm-3 control-label">私有网络</label>
		    <div class="col-sm-9">
		      <select class="form-control" id="subnet_vpc_selector">
		      	<option value="">请选择私有网络</option>
		      </select>
		    </div>
		  </div>
		  <div class="form-group">
		    <label for="subnet_name" class="col-sm-3 control-label">子网名称</label>
		    <div class="col-sm-9">
		      <input type="text" class="form-control" id="subnet_name" placeholder="子网名称">
		    </div>
		  </div>
		  <div class="form-group">
		    <label for="subnet_cidr" class="col-sm-3 control-label">网段</label>
		    <div class="col-sm-9">
		      <input type="text" class="form-control" id="subnet_cidr" placeholder="网段">
		    </div>
		  </div>
		  <div class="form-group">
		    <label for="subnet_gateway_ip" class="col-sm-3 control-label">网关</label>
		    <div class="col-sm-9">
		      <input type="text" class="form-control" id="subnet_gateway_ip" placeholder="网关">
		    </div>
		  </div>
		  <div class="form-group">
		    <label for="subnet_enable_dhcp" class="col-sm-3 control-label">启动DHCP</label>
		    <div class="col-sm-9">
		      <div class="checkbox">
			        <label>
			          <input type="checkbox" id="subnet_enable_dhcp"> 是否启动DHCP
			        </label>
			  </div>
		    </div>
		  </div>
		</form>
      </div>
      <div class="modal-footer">
        <button type="button" id="subnet_create_button" class="btn btn-primary">创建</button>
        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
      </div>
    </div>
  </div>
</div>
<!--创建subnet-->
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

seajs.use("${ctx}/static/page-js/cloudvm/networkList/main");

</script>
</body>
</html>
