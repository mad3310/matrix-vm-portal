<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<%@include file='main.jsp' %>
<link rel="stylesheet" href="${ctx}/static/css/ui-css/cbase.css" />
</head>
<body>
<style>
.bk-slider .bk-slider-range{padding-right:0;}
.bk-slider .bk-slider-container{padding:0;}
.bk-slider .bk-slider-l1 {margin-left:-3px;}
</style>
	<%@include file='header.jsp' %>

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
						<span>开放存储服务OSS</span>
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
						<div role="tabpanel" class="tab-pane fade active in" id="year">
							<div class="col-sm-12 col-md-9">
								<dl class="bk-group">
									<dt class="bk-group-title">基本配置</dt>
									<dd class="bk-group-detail">
										<div class="bk-group-control"></div>
										<div>
										<form id="monthPurchaseForm" class="bv-form">
											<div class="form-group bk-form-row col-sm-12">
												<label class="bk-form-row-name col-sm-2" style="padding-left: 0px;">实例名称：</label>
												<div class="col-sm-4 row">
													<input id="name" class="form-control" name="name" type="text">
												</div>
											</div>
										</form>
										<div class="bk-form-row">
												<label class="bk-form-row-name">地域选择：</label>
												<div class="bk-form-row-cell">
													<div class="bk-form-row-li">
														<div class="bk-buttontab">
															<button class=" bk-button bk-button-primary bk-button-current">
																<div>
																	<span>北京</span>
																</div>
															</button>
															<button class="bk-button bk-button-primary disabled">
																<div>
																	<span>成都</span>
																</div>
															</button>
															<button class="bk-button bk-button-primary disabled">
																<div>
																	<span>青岛</span>
																</div>
															</button>
															<button class="bk-button bk-button-primary disabled">
																<div>
																	<span>香港</span>
																</div>
															</button>
															<button class="bk-button bk-button-primary disabled">
																<div>
																	<span>深圳</span>
																</div>
															</button>
														</div>
														<span class="bk-lnk" style="cursor: text;">暂时只有北京可用,其他地域陆续开通</span>
													</div>
													<div class="bk-form-row-txt">不同地域之间的产品内网不互通；订购后不支持更换地域，请谨慎选择</div>
												</div>
											</div>
										    <div class="bk-form-row">
												<label class="bk-form-row-name">可用区：</label>
												<div class="bk-form-row-cell">
													<div class="bk-form-row-li clearfix">
														<div class="pull-left">
															<span class="sleBG"> 
															<span class="sleHid"> 
															<div class="divselect">
																<span></span>
																<ul>
																</ul>
																<input name="hclusterId" type="hidden" value="" />
															</div>
															</span>
															</span> <span class="bk-select-arrow"></span>
														</div>
													</div>
												</div>
											</div>
										</div>
									</dd>
								</dl>
								<dl class="bk-group">
									<dt class="bk-group-title">存储</dt>
									<dd class="bk-group-detail">
										<div class="bk-group-control"></div>
										<div class="disabled">
											<div class="bk-form-row">
												<label class="bk-form-row-name">实例大小：</label>
												<div class="bk-form-row-cell">
													<!-- 原拖动效果注释 -->
													<div class="bk-form-row-li">
														<div class="self-dragBar"></div><!-- 拖动条生成容器 -->	
													</div>	
												</div>
											</div>
											<!-- <div class="bk-form-row">
												<label class="bk-form-row-name">实例类型：</label>
												<div class="bk-form-row-cell">
													<div class="bk-form-row-li">
														<div class="bk-buttontab">
															<input name="bucketType" type="text" class="hide" value="0">
															<button class="bk-button bk-button-primary bk-button-current bk-perment-bt" value="0">
																<div>
																	<span>持久化</span>
																</div>
															</button>
															<button class="bk-button bk-button-primary" value="1">
																<div>
																	<span>非持久化</span>
																</div>
															</button>
														</div>
													</div>
												</div>
											</div> -->
										</div>
									</dd>
								</dl>
								<dl class="bk-group">
									<dt class="bk-group-title">购买时长</dt>
									<dd class="bk-group-detail">
										<div class="bk-group-control"></div>
										<!-- <div class="disabled"> -->
										<div class="bk-form-row">
											<label class="bk-form-row-name">购买时长：</label>
											<div class="bk-form-row-cell">
												<div class="bk-form-row-li clearfix">
													<div class="pull-left" style="height: 36px;">
														<span class="sleBG">
														<span class="sleHid"> 
														<div class="divselect">
																<span>1年</span>
																<ul style="display:none;">
																	<li class="bk-select-option"><a href="javascript:;">1年</a></li>
																</ul>
															</div>
														</span>
														</span> <span class="bk-select-arrow"></span>
													</div>
												</div>
												<!-- <div class="bk-form-row-txt">此功能暂时不开放</div> -->
											</div>
										</div>
										<!-- <div class="bk-form-row">
											<label class="bk-form-row-name">购买数量：</label>
											<div class="bk-form-row-cell">
												<div class="bk-form-row-li">
													<span class="bk-number">
														<input type="text" class="bk-number-input tai-num" value="1">
														<span class="bk-number-unit">台</span>
														<span class="bk-number-control"> 
															<span class="bk-number-up tai-num-up"> <i class="bk-number-arrow"></i></span>
															<span class="bk-number-down tai-num-down"> <i class="bk-number-arrow"></i>
														</span> 
													</span>
													</span>
												</div>
											</div>
										</div> -->
										<!-- <div class="bk-form-row">
											<label class="bk-form-row-name">费用合计：</label>
											<label class="bk-form-row-name">****元</label>
										</div> -->
									</dd>
								</dl>
							</div>
						</div>
						<div class="col-sm-12 col-md-3">
							<!-- <div class="bk-orders-menu  bk-mb4">
								<span class="bk-orders-menu-name">购买清单</span> <span class="bk-orders-menu-quantity bk-pale">1个</span>
							</div> -->
							<div class="bk-scope bk-items">
								<div class="bk-items-title">当前配置</div>
								<div>
									<div class="bk-items-list">
										<ul>
											<li><span class="bk-items-item-name">地域：</span> <span class="bk-items-item-value">北京</span></li>
											<li><span class="bk-items-item-name">购买量：</span> <span class="bk-items-item-value">1年</span></li>
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
								<!-- <div class="bk-items-price">
									<div class="bk-items-price-title bk-pale">《计费标准说明》</div>
								</div> -->
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
									<div class="bk-form-row-txt notice-block"></div>
								</div>
								<div class="bk-pb4"></div>
							</div>
								<div class="bk-scope bk-items">
								<div class="bk-items-title">服务专员信息</div>
							</div>
							<div class="bk-scope bk-items">
									<div class="bk-items-list">
											<ul>
												<li><span class="bk-items-item-name">姓名：</span> <span class="bk-items-item-value">钟健男</span></li>
												<li><span class="bk-items-item-name">邮箱：</span> <span class="bk-items-item-value">zhongjiannan@letv.com</span></li>
												<li><span class="bk-items-item-name">电话：</span> <span class="bk-items-item-value">15210219856</span></li>
												<li><span class="bk-items-item-name">Q  Q：</span> <span class="bk-items-item-value">123102027</span></li>
											</ul>
								</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- main-content-center-end -->
	</div>
	<!-- main-content end-->
</body>
<script type="text/javascript" src="${ctx}/static/modules/seajs/2.3.0/sea.js"></script>
<script>
//Set configuration
seajs.config({
	base: "${ctx}/static/modules/",
	alias: {
		"jquery": "jquery/2.0.3/jquery.min.js",
		"bootstrap": "bootstrap/bootstrap/3.3.0/bootstrap.js",
		"bootstrapValidator": "bootstrap/bootstrapValidator/0.5.3/bootstrapValidator.js"
	}
});
seajs.use("${ctx}/static/page-js/cloudswift/containerCreate/main");
</script>
</html>
