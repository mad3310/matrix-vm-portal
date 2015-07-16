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
	<div class="container">
		<!-- main-content begin-->
		<div class="row main-header">
			<!-- main-content-header begin -->
			<div class="col-sm-12 col-md-6">
				<div class="pull-left">
					<h4>
						<span>云盘</span>
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
											<div class="bk-form-row">
												<label class="bk-form-row-name">云盘：</label>
												<div class="bk-form-row-cell">
													<div class="bk-form-row-li">
														<div class="yundisk-size">
															<input class="yundisk-size-input" type="text" value="1">
															<span class="yundisk-size-unit">GB</span>
														</div>
														<div class="clearfix"></div>
													</div>
												</div>
											</div>
										</div>
									</dd>
								</dl>
								<dl class="bk-group">
									<dt class="bk-group-title">购买量</dt>
									<dd class="bk-group-detail">
										<div class="bk-group-control"></div>
										<div>
											<div class="bk-form-row">
												<label class="bk-form-row-name">数量：</label>
												<div class="bk-form-row-cell disk-operate-panel">
													<div class="bk-form-row-li">
														  <span class="bk-number"> 
														  	<input type="text" class="bk-number-input" value="1">
														  	<span class="bk-number-unit">个</span> 
														  	<span class="bk-number-control"> 
					                                            <span class="bk-number-up"> 
					                                            	<i class="bk-number-arrow"></i>
					                                            </span> 
					                                            <span class="bk-number-down bk-number-disabled"> 
					                                            	<i class="bk-number-arrow"></i>
					                                            </span> 
				                                          	</span>
				                                          </span>
													</div>
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
											<li><span class="bk-items-item-name">大小：</span> <span class="bk-items-item-value" id="buy-disk-size">1GB</span></li>
											<li><span class="bk-items-item-name">数量：</span> <span class="bk-items-item-value" id= "buy-disk-count">1个</span></li>
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
								<div class="bk-items-price-control">
									<button id="monthPurchaseBotton" class="bk-button">
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
								<p id="submitResult"></p>
								<div class="bk-pb4">
								</div>
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
		seajs.use("${ctx}/static/page-js/cloudvm/diskCreate/main");
	</script>
</body>
</html>
