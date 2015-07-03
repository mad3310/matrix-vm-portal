<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<meta charset="utf-8" />
<meta http-equiv="X-UA-compatible" content="IE=edge,chrome=1" />
<meta name="viewpoint" content="width=device-width,initial-scale=1, maximum-scale=1, user-scalable=no"/>
<!-- bootstrap css -->
<link type="text/css" rel="stylesheet"
	href="${ctx}/static/css/bootstrap.min.css" />
<!-- fontawesome css -->
<link type="text/css" rel="stylesheet"
	href="${ctx}/static/css/font-awesome.min.css" />
<!-- ui-css -->
<link type="text/css" rel="stylesheet"
	href="${ctx}/static/css/ui-css/common.css" />
<link type="text/css" rel="stylesheet"
	href="${ctx}/static/css/multiple-select/multiple-select.css" />
<title>Le云控制台首页</title>
</head>
<body>
	<!-- top bar begin -->
	<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation"
		style="min-height: 40px; height: 40px;">
		<div class="container-fluid">
			<div class="navbar-header">
				<a class="navbar-brand color" href="${ctx}/dashboard"
					style="padding-top: 2px; height: 40px !important;"><img
					src="${ctx}/static/img/logo.png" /></a> 
					<%-- <a class="navbar-brand color"
					href="${ctx}/dashboard"
					style="margin-left: 10px; height: 40px !important;"><i
					class="fa fa-home text-20"></i></a> --%>
			</div>
			<div id="navbar" class="navbar-collapse collapse pull-right">
				<ul class="nav navbar-nav">
					<li><a href="javascript:void(0)" class="hlight"><span
							class="glyphicon glyphicon-bell"></span></a></li>
					<li class="dropdown"><a href="javascript:void(0)"
						class="dropdown-toggle hlight" data-toggle="dropdown">${sessionScope.userSession.userName}<span
							class="caret"></span></a>
						<ul class="dropdown-menu" role="menu">
							<li><a href="javascript:void(0)">用户中心</a></li>
							<li><a href="javascript:void(0)">我的订单</a></li>
							<li><a href="javascript:void(0)">账户管理</a></li>
							<li class="divider"></li>
							<li><a href="${ctx}/account/logout">退出</a></li>
						</ul></li>
					<li><a href="javascript:void(0)" class="hlight"><span
							class="glyphicon glyphicon-lock"></span></a></li>
					<li><a href="javascript:void(0)" class="hlight"><span
							class="glyphicon glyphicon-pencil"></span></a></li>
				</ul>
			</div>
			<!--/.nav-collapse -->
		</div>
	</nav>
	<!-- top bar end -->

	<!-- navbar begin -->
	<div class="navbar navbar-default mt40"
		style="margin-bottom: 0px !important;">
		<div class="container-fluid">
			<div class="navbar-header">
				<a class="navbar-brand" href="${ctx}/dashboard">Le云控制台首页</a>
			</div>
		</div>
	</div>
	<!-- navbar end -->
	<div class="container-fluid">
		<!-- main-content begin-->
		<div class="row main-header">
			<!-- main-content-header begin -->
			<div class="col-sm-12 col-md-6">
				<div class="pull-left">
					<h4>
						<span>云主机</span>
					</h4>
				</div>
			</div>
		</div>
		<!-- main-content-header end-->

		<div class="row">
			<!-- main-content-center-begin -->
			<div class="col-sm-12 col-md-12">
				<ul class="nav nav-tabs RDSCreateUl" role="tablist" id="RDSCreateTab">
					<li role="presentation" class="active"><a href="#year" role="tab" data-toggle="tab" style="">包年包月</a></li>
					<!--<li role="presentation"><a href="#dosage" role="tab" data-toggle="tab">按量付费</a></li>-->
				</ul>
				<div class="tab-content mt20">
					<form id="monthPurchaseForm">
						<div role="tabpanel" class="tab-pane fade active in" id="year">
							<div class="col-sm-12 col-md-9">
								<dl class="bk-group">
									<dt class="bk-group-title">基本配置</dt>
									<dd class="bk-group-detail">
										<div class="bk-group-control"></div>
										<div>
											<div class="bk-form-row form-group col-sm-12">
													<label class="bk-form-row-name col-sm-2" style="padding-left: 0px;">云主机名称：</label>
													<div class="col-sm-4 row">
														<input id="vmName" class="form-control" name="vmName" type="text">
													</div>
											</div>
											<div class="bk-form-row col-xs-12 col-sm-12">
												<label class="bk-form-row-name">地域：</label>
												<div class="col-xs-12 col-sm-10 row">
													<div class="bk-form-row-li">
														<div class="bk-buttontab bk-buttontab-regioncitys">
															<input type="text" class="hide" value="">															
														</div>
													</div>
												</div>
											</div>
											<div class="bk-form-row">
												<label class="bk-form-row-name">可用区：</label>
												<div class="bk-form-row-cell">
													<div class="bk-form-row-li clearfix">
														<div class="pull-left">
															<span class="sleBG"> <span class="sleHid">
																	<div class="divselect">
																		<span>选择可用区</span>
																		<ul style="display: none;">
																		</ul>
																		<input name="regionName" type="hidden" value="">
																	</div>
															</span>
															</span> <span class="bk-select-arrow"></span>
														</div>
													</div>
												</div>
											</div>
											<div class="bk-form-row col-xs-12 col-sm-12">
												<label class="bk-form-row-name">CPU：</label>
												<div class="col-xs-12 col-sm-10 row">
													<div class="bk-form-row-li">
														<div class="bk-buttontab bk-buttontab-flavorCPUs">	
															<input type="text" class="hide" value="">													
														</div>
													</div>
												</div>
											</div>
											<div class="bk-form-row col-xs-12 col-sm-12">
												<label class="bk-form-row-name">内存：</label>
												<div class="col-xs-12 col-sm-10 row">
													<div class="bk-form-row-li">
														<div class="bk-buttontab bk-buttontab-flavorRams">	
															<input type="text" class="hide" value="">													
														</div>
													</div>
												</div>
											</div>
											<div class="bk-form-row col-xs-12 col-sm-12">
												<label class="bk-form-row-name">硬盘：</label>
												<div class="col-xs-12 col-sm-10 row">
													<div class="bk-form-row-li">
														<div class="bk-buttontab bk-buttontab-flavorDisks">
															<input type="text" class="hide" value="">															
														</div>
													</div>
												</div>
											</div>
											<input id="flavorId" type="hidden" value="">
										</div>
									</dd>
								</dl>
								<dl class="bk-group">
									<dt class="bk-group-title">网络</dt>
									<dd class="bk-group-detail">
										<div class="bk-group-control"></div>
										<div>
											<div class="bk-form-row col-xs-12 col-sm-12">
												<label class="bk-form-row-name">公网IP：</label>
												<div class="col-xs-12 col-sm-10 row">
													<div class="bk-form-row-li">
														<div class="bk-buttontab">
															<input name="isCreatePublicIP" type="text" class="hide" value="0">
															<button class="bk-button bk-button-primary bk-button-current" value="0">
																<div>
																	<span>创建后设置</span>
																</div>
															</button>
															<button class="bk-button bk-button-primary" value="1">
																<div>
																	<span>立即设置</span>
																</div>
															</button>
														</div>
													</div>
												</div>
											</div>
										</div>
									</dd>
								</dl>
								<dl class="bk-group">
									<dt class="bk-group-title">镜像</dt>
									<dd class="bk-group-detail">
										<div class="bk-group-control"></div>
										<div>
											<div class="bk-form-row">
												<label class="bk-form-row-name">镜像名称：</label>
												<div class="bk-form-row-cell">
													<div class="bk-form-row-li clearfix">
														<div class="pull-left image-os-selector divselect-unselected">
															<span class="sleBG"> <span class="sleHid">
																	<div class="divselect">
																		<span>选择操作系统类别</span>
																		<ul class="bk-select-options">
																		</ul>
																		<input id="vmImageOSName" name="vmImageOSName" type="hidden" />
																	</div>
															</span>
															</span> <span class="bk-select-arrow"></span>
														</div>
														<div class="pull-left image-version-selector divselect-disabled">
															<span class="sleBG"> <span class="sleHid">
																	<div class="divselect">
																		<span>选择版本</span>
																		<ul class="bk-select-options">
																		</ul>
																		<input name="vmImageVersionName" type="hidden" value="" />
																	</div>
															</span>
															</span> <span class="bk-select-arrow"></span>
														</div>
														<span class="image-invalid-tip"></span>
														<input id="vmImageId" type="hidden" value="" />
													</div>
												</div>
											</div>
										</div>
									</dd>
								</dl>
								<dl class="bk-group">
									<dt class="bk-group-title">安全</dt>
									<dd class="bk-group-detail">
										<div class="bk-group-control"></div>
										<div>
											<!--<div class="bk-form-row form-group col-xs-12 col-sm-12 hidden-xs">
												<label class="bk-form-row-name col-xs-12 col-sm-2" style="padding-left: 0px;">设置密码：</label>
												<div class="col-xs-12 col-sm-10 row">
													<div class="bk-form-row-li">
														<div class="bk-buttontab bk-buttontab-password">
															<input name="isCreatePassword" type="text" class="hide" value="1">
															<button class=" bk-button bk-button-primary bk-button-current" value="1">
																<div>
																	<span>立即设置</span>
																</div>
															</button>
															<button class="bk-button bk-button-primary" value="0">
																<div>
																	<span>创建后设置</span>
																</div>
															</button>
														</div>
													</div>
												</div>
											</div>-->
											<div class="bk-form-row form-group col-sm-12">
													<label class="bk-form-row-name col-sm-2" style="padding-left: 0px;">管理员密码：</label>
													<div class="col-sm-2 row">
														<input id="vmpw1" class="form-control" name="vmpw1" type="password">
													</div>
											</div>																						
											<div class="bk-form-row form-group col-sm-12">
													<label class="bk-form-row-name col-sm-2" style="padding-left: 0px;">确认密码：</label>
													<div class="col-sm-2 row">
														<input id="vmpw2" class="form-control" name="vmpw2" type="password">
													</div>
											</div>	
										</div>
									</dd>
								</dl>
																
							</div>
						</div>
						</form>
						<div class="col-sm-12 col-md-3">
							<div class="bk-scope bk-items bk-mb4">
								<div class="bk-items-title">当前配置</div>
								<div>
									<div class="bk-items-list">
										<ul>
											<li><span class="bk-items-item-name">区域：</span> <span class="bk-items-item-value" id="buy-region">--</span></li>
											<li><span class="bk-items-item-name">配置：</span> <span class="bk-items-item-value" id="buy-flavor">--</span></li>
											<li><span class="bk-items-item-name">镜像：</span> <span class="bk-items-item-value" id= "buy-image">--</span></li>
											<!-- <li><span class="bk-items-item-name">网络：</span> <span class="bk-items-item-value">网络1</span></li> -->
										</ul>
									</div>
								</div>
								<div class="bk-items-price" style="padding-top: 0">
									<div class="bk-items-price-title bk-pale">配置费用：</div>
									<div class="bk-items-price-settle">
										<div>
											<span class="bk-cny">¥</span> <span class="bk-items-price-money">0.00</span> <span class="bk-items-price-unit"></span>
										</div>
									</div>
								</div>
								<div class="bk-items-price">
									<div class="bk-items-price-title bk-pale">公网流量费用：</div>
									<div class="bk-items-price-settle">
										<span class="bk-cny">¥</span> <span class="bk-items-price-money">0.00</span> <span class="bk-items-price-unit">/GB</span>
									</div>
								</div>
								<div class="bk-items-price-control">
									<button form="monthPurchaseForm" type="submit" id="monthPurchaseBotton" class="bk-button">
										<div>
											<span class="ng-scope">立即购买</span>
										</div>
									</button>
									<button class=" hide bk-button bk-button-default">
										<div>
											<span class="ng-scope">加入清单</span>
										</div>
									</button>
									<!-- <div class="bk-form-row-txt notice-block">您购买的云主机创建大约需要2分钟,请耐心等待...</div> -->
								</div>
								<div class="bk-pb4"></div>
							</div>
							<div class="bk-scope bk-items">
								<div class="bk-items-title">服务专员信息</div>
							</div>
							<div class="bk-scope bk-items">
									<div class="bk-items-list">
											<ul>
												<li><span class="bk-items-item-name">姓名：</span> <span class="bk-items-item-value">周秉政</span></li>
												<li><span class="bk-items-item-name">邮箱：</span> <span class="bk-items-item-value">zhoubingzheng@letv.com</span></li>
												<li><span class="bk-items-item-name">电话：</span> <span class="bk-items-item-value">15901422865</span></li>
												<li><span class="bk-items-item-name">Q  Q：</span> <span class="bk-items-item-value">473877850</span></li>
											</ul>
								</div>
							</div>
						</div>
				</div>
			
			</div>
			<!-- main-content-center-end -->
		</div>
	</div>
	<!-- main-content end-->
	<script type="text/javascript" src="${ctx}/static/modules/seajs/2.3.0/sea.js"></script>
	<script type="text/javascript">
		seajs.config({
			base: "${ctx}/static/modules/",
			alias: {
				"jquery": "jquery/2.0.3/jquery.min.js",
				"jquery.multiple": "multiple-select/jquery.multiple.select.js",
				"bootstrap": "bootstrap/bootstrap/3.3.0/bootstrap.js",
				"bootstrapValidator": "bootstrap/bootstrapValidator/0.5.3/bootstrapValidator.js"
			}
		});
		seajs.use("${ctx}/static/page-js/cloudvm/vmCreate/main");
	</script>
</body>
</html>
