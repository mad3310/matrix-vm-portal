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
	<title>RDSCreate</title>
</head>
<body>
	<!-- top bar begin -->
	<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
		<div class="container-fluid">
			<div class="navbar-header">
				<a class="navbar-brand" href="${ctx}/dashboard"><img
					src="${ctx}/static/img/cloud.ico" /></a>
			</div>
			<div class="navbar-header">
				<a class="navbar-brand active" href="${ctx}/dashboard"><span
					class="glyphicon glyphicon-home"></span></a>
			</div>
			<div id="navbar" class="navbar-collapse collapse pull-right">
				<form class="navbar-form navbar-right pull-left" role="form">
					<div class="form-group">
						<input type="text" placeholder="Search" class="form-control">
					</div>
					<button type="submit" class="btn btn-success">
						<span class="glyphicon glyphicon-search"></span>
					</button>
				</form>
				<ul class="nav navbar-nav">
					<li><a href="#"><span class="glyphicon glyphicon-bell"></span></a></li>
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown">${sessionScope.userSession.userName}<span
							class="caret"></span></a>
						<ul class="dropdown-menu" role="menu">
							<li><a href="#">用户中心</a></li>
							<li><a href="#">我的订单</a></li>
							<li><a href="#">账户管理</a></li>
							<li class="divider"></li>
							<li class="dropdown-header"><a href="${ctx}/account/logout">退出</a></li>
						</ul></li>
					<li><a href="#"><span class="glyphicon glyphicon-lock"></span></a></li>
					<li><a href="#"><span class="glyphicon glyphicon-pencil"></span></a></li>
				</ul>
			</div>
			<!--/.nav-collapse -->
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
<div class="container-fluid"><!-- main-content begin-->
	<div class="row main-header"> <!-- main-content-header begin -->
		<div class="col-sm-12 col-md-6">
			<div class="pull-left">
				<h4><span>关系型数据库RDS</span></h4> 
			</div>
		</div>
		<div class="col-sm-12 col-md-6">
			<div class="pull-right creatHR">
				<span>关联产品：<span><a href="#" class="bk-link">云服务器ECS</a></span></span>
				<span data-toggle="dropdown" id="dropdownMenu1">其他产品 <span class="caret"></span></span>
				<ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu1">
    				<li role="presentation"><a role="menuitem" tabindex="-1" href="#">开放存储服务OSS</a></li>
    				<li role="presentation"><a role="menuitem" tabindex="-1" href="#">开放缓存服务OCS</a></li>
  				</ul>
			</div>
		</div>
	</div><!-- main-content-header end-->

	<div class="row"><!-- main-content-center-begin -->
		<div class="col-sm-12 col-md-12">
			<ul class="nav nav-tabs RDSCreateUl" role="tablist" id="RDSCreateTab">
  				<li role="presentation" ><a href="#year" role="tab" data-toggle="tab" style="">包年包月</a></li>
  				<li role="presentation"><a href="#dosage" role="tab" data-toggle="tab">按量付费</a></li>
			</ul>
			<div class="tab-content mt20">
  				<div role="tabpanel" class="tab-pane" id="year">
  					<div class="col-sm-12 col-md-9">
	  					<dl class="bk-group">
	  						<dt class="bk-group-title">基本配置</dt>
	  						<dd class="bk-group-detail">
	  							<div class="bk-group-control"></div>
	  							<div>
	  								<div class="bk-form-row">
	  									<label class="bk-form-row-name">地域：</label>
	  									<div class="bk-form-row-cell">
	  										<div class="bk-form-row-li">
	  											<div class="bk-buttontab">
	  												<button class=" bk-button bk-button-primary bk-button-current">
	  												<div><span>青岛</span></div>
	  												</button>
	  												<button class="bk-button bk-button-primary">
	  												<div><span>杭州</span></div>
	  												</button>
	  												<button class="bk-button bk-button-primary">
	  												<div><span>北京</span></div>
	  												</button>
	  												<button class="bk-button bk-button-primary">
	  												<div><span>香港</span></div>
	  												</button>
	  												<button class="bk-button bk-button-primary">
	  												<div><span>深圳</span></div>
	  												</button>
	  											</div>
	  											<span><a class="bk-lnk bk-ml2">查看我的产品地域</a></span>
	  										</div>
	  										<div class="bk-form-row-txt">
	  											不同地域之间的产品内网不互通；订购后不支持更换地域，请谨慎选择 <a href="#" target="_blank" class="bk-lnk">教我选择&gt;&gt;</a>
	  										</div>
	  									</div>
	  								</div>
	  								<div class="bk-form-row">
	  									<label class="bk-form-row-name">可用区：</label>
	  									<div class="bk-form-row-cell">
	  										<div class="bk-form-row-li clearfix">
	  											<div class="dropdown pull-left">
		  											<button class="btn btn-default dropdown-toggle w217 wcolor mgl-r" type="button" id="singleUseable" data-toggle="dropdown">单可用区<span class="caret"></span>
													</button>
												    <ul class="dropdown-menu w217" role="menu" aria-labelledby="singleUseable">
												    	<li role="presentation"><a role="menuitem" tabindex="-1" href="#">单可用区</a></li>
												    </ul>
												</div>
												<div class="dropdown pull-left">
		  											<button class="btn btn-default dropdown-toggle w326 wcolor" type="button" id="UseableB" data-toggle="dropdown">可用区B<span class="caret"></span>
													</button>
												    <ul class="dropdown-menu w326" role="menu" aria-labelledby="UseableB">
												    	<li role="presentation"><a role="menuitem" tabindex="-1" href="#">可用区B <span class="text-muted text-xs">0台ECS</span></a></li>
												    </ul>
												</div>
												<span class="glyphicon glyphicon-question-sign margin-left-5" style="margin-top:8px;"></span>
	  										</div>
	  									</div>
	  								</div>
	  								<div class="bk-form-row">
	  									<label class="bk-form-row-name">数据库类型：</label>
	  									<div class="bk-form-row-cell">
	  										<div class="bk-form-row-li clearfix">
	  											<div class="dropdown pull-left">
		  											<button class="btn btn-default dropdown-toggle w217 wcolor" type="button" id="singleUseable" data-toggle="dropdown">MySQL<span class="caret"></span>
													</button>
												    <ul class="dropdown-menu w217" role="menu" aria-labelledby="singleUseable">
												    	<li role="presentation"><a role="menuitem" tabindex="-1" href="#">MySQL</a></li>
												    	<li role="presentation"><a role="menuitem" tabindex="-1" href="#">MS SQLServer</a></li>
												    </ul>
												</div>
	  										</div>
	  									</div>
	  								</div>
	  								<div class="bk-form-row">
	  									<label class="bk-form-row-name">版本：</label>
	  									<div class="bk-form-row-cell">
	  										<div class="bk-form-row-li">
	  											<div class="bk-buttontab">
	  												<button class=" bk-button bk-button-primary bk-button-current">
	  												<div><span>5.5</span></div>
	  												</button>
	  												<button class="bk-button bk-button-primary">
	  												<div><span>5.6</span></div>
	  												</button>
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
	  							<div>
	  								<div class="bk-form-row">
	  									<label class="bk-form-row-name">存储空间：</label>
	  									<div class="bk-form-row-cell">
	  										<div class="bk-form-row-li">
	  											<div class="bk-slider">
													<span class="bk-slider-range">
														<span class="bk-slider-block bk-slider-l2">
															<span class="bk-slider-block-box">
																<span class="bk-slider-txt">250GB</span>
															</span>
														</span>
														<span class="bk-slider-block bk-slider-l1">
															<span class="bk-slider-block-box">
																<span class="bk-slider-txt">500GB</span>
															</span>
														</span>
														<span class="bk-slider-block bk-slider-l1">
															<span class="bk-slider-block-box bk-slider-block-box-last bk-select-action">
																<span class="bk-slider-txt">1000GB</span>
															</span>
														</span>
														<span class="bk-slider-container bk-slider-transition" style="width: 11.88px;">
															<span class="bk-slider-current">
																<span class="bk-slider-unit bk-slider-l2">
																	<span class="bk-slider-unit-box bk-select-action">
																		<span class="bk-slider-txt">250GB</span></span>
																	</span>
																	<span class="bk-slider-unit bk-slider-l1">
																		<span class="bk-slider-unit-box">
																			<span class="bk-slider-txt">500GB</span>
																		</span>
																	</span>
																	<span class="bk-slider-unit bk-slider-l1">
																		<span class="bk-slider-unit-box bk-slider-unit-box-last">
																			<span class="bk-slider-txt">1000GB</span>
																		</span>
																	</span>
																</span>
															</span>
															<span class="bk-slider-drag bk-slider-transition">
																<i></i> <i></i>
																<div style="position: relative">
																	<div class="bk-tip-gray hide" style="top: -38px;left: -15px">
																		<span>15</span> 
																		<span class="bk-tip-arrow"></span>
																	</div>
																</div>
															</span>
													</span>
												</div>
	  											<span class="bk-number bk-ml2">
	  												<input type="text" class="bk-number-input" value="5"> 
	  												<span class="bk-number-unit">GB</span><span class="bk-number-control">
	  													<span class="bk-number-up bk-number-disabled hide">
	  														<i class="bk-number-arrow"></i>
	  													</span> 
	  													<span class="bk-number-up">
	  														<i class="bk-number-arrow"></i>
	  													</span> 
	  													<span class="bk-number-down bk-number-disabled">
	  														<i class="bk-number-arrow"></i>
	  													</span> 
	  													<span class="bk-number-down hide">
	  														<i class="bk-number-arrow"></i>
	  													</span>
	  												</span>
	  											</span>
	  										</div>
	  										<div class="bk-form-row-txt">
	  											步长为5GB
	  										</div>
	  									</div>
	  								</div>
	  								<div class="bk-form-row">
	  									<label class="bk-form-row-name">内存：</label>
	  									<div class="bk-form-row-cell">
	  										<div class="bk-form-row-li">
												<div class="dropdown">
		  											<button class="btn btn-default dropdown-toggle w217 wcolor" type="button" id="UseableB" data-toggle="dropdown">240MB<span class="caret"></span>
													</button>
												    <ul class="dropdown-menu w217 h216" role="menu" aria-labelledby="UseableB">
												    	<li role="presentation"><a role="menuitem" tabindex="-1" href="#">240MB</a></li>
												    	<li role="presentation"><a role="menuitem" tabindex="-1" href="#">600MB</a></li>
												    	<li role="presentation"><a role="menuitem" tabindex="-1" href="#">1200MB</a></li>
												    </ul>
												</div>
	  										</div>
	  										<div class="bk-form-row-txt">
	  											最大连接数:60 IOPS:150
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
	  									<label class="bk-form-row-name">购买时长：</label>
	  									<div class="bk-form-row-cell">
	  										<div class="bk-form-row-li">
	  											<div class="bk-slider bk-slider-duration bk-slider">
	  												<span class="bk-slider-range">
	  													<span class="bk-slider-block bk-slider-mm">
	  														<span class="bk-slider-block-box bk-select-action">
	  															<span class="bk-slider-txt">1</span>
	  														</span>
	  													</span>
	  													<span class="bk-slider-block bk-slider-mm">
	  														<span class="bk-slider-block-box">
	  															<span class="bk-slider-txt">2</span>
	  														</span>
	  													</span>
	  													<span class="bk-slider-block bk-slider-mm">
	  														<span class="bk-slider-block-box">
	  															<span class="bk-slider-txt">3</span>
	  														</span>
	  													</span>
	  													<span class="bk-slider-block bk-slider-mm">
	  														<span class="bk-slider-block-box">
	  															<span class="bk-slider-txt">4</span>
	  														</span>
	  													</span>
	  													<span class="bk-slider-block bk-slider-mm">
	  														<span class="bk-slider-block-box">
	  															<span class="bk-slider-txt">5</span>
	  														</span>
	  													</span>
	  													<span class="bk-slider-block bk-slider-mm">
	  														<span class="bk-slider-block-box">
	  															<span class="bk-slider-txt">6</span>
	  														</span>
	  													</span>
	  													<span class="bk-slider-block bk-slider-mm">
	  														<span class="bk-slider-block-box">
	  															<span class="bk-slider-txt">7</span>
	  														</span>
	  													</span>
	  													<span class="bk-slider-block bk-slider-mm">
	  														<span class="bk-slider-block-box">
	  															<span class="bk-slider-txt">8</span>
	  														</span>
	  													</span>
	  													<span class="bk-slider-block bk-slider-mm">
	  														<span class="bk-slider-block-box">
	  															<span class="bk-slider-txt">9</span>
	  														</span>
	  													</span>
	  													<span class="bk-slider-block bk-slider-yy">
	  														<span class="bk-slider-block-box">
	  															<span class="bk-slider-txt glyphicon glyphicon-gift">1年</span>
	  														</span>
	  													</span>
	  													<span class="bk-slider-block bk-slider-yy">
	  														<span class="bk-slider-block-box">
	  															<span class="bk-slider-txt glyphicon glyphicon-gift">2年</span>
	  														</span>
	  													</span>
	  													<span class="bk-slider-block bk-slider-yy">
	  														<span class="bk-slider-block-box bk-slider-block-box-last" >
	  															<span class="bk-slider-txt glyphicon glyphicon-gift">3年</span>
	  														</span>
	  													</span>
	  													<span class="bk-slider-container bk-slider-transition" style="width: 40px;">
	  														<span class="bk-slider-current">
	  															<span class="bk-slider-unit bk-slider-mm">
	  																<span class="bk-slider-unit-box bk-select-action">
	  																	<span class="bk-slider-txt">1</span>
	  																</span>
	  															</span>
	  															<span class="bk-slider-unit bk-slider-mm">
	  																<span class="bk-slider-unit-box">
	  																	<span class="bk-slider-txt">2</span>
	  																</span>
	  															</span>
	  															<span class="bk-slider-unit bk-slider-mm">
	  																<span class="bk-slider-unit-box">
	  																	<span class="bk-slider-txt">3</span>
	  																</span>
	  															</span>
	  															<span class="bk-slider-unit bk-slider-mm">
	  																<span class="bk-slider-unit-box">
	  																	<span class="bk-slider-txt">4</span>
	  																</span>
	  															</span>
	  															<span class="bk-slider-unit bk-slider-mm">
	  																<span class="bk-slider-unit-box">
	  																	<span class="bk-slider-txt">5</span>
	  																</span>
	  															</span>
	  															<span class="bk-slider-unit bk-slider-mm">
	  																<span class="bk-slider-unit-box">
	  																	<span class="bk-slider-txt">6</span>
	  																</span>
	  															</span>
	  															<span class="bk-slider-unit bk-slider-mm">
	  																<span class="bk-slider-unit-box">
	  																	<span class="bk-slider-txt">7</span>
	  																</span>
	  															</span>
	  															<span class="bk-slider-unit bk-slider-mm">
	  																<span class="bk-slider-unit-box">
	  																	<span class="bk-slider-txt">8</span>
	  																</span>
	  															</span>
	  															<span class="bk-slider-unit bk-slider-mm">
	  																<span class="bk-slider-unit-box">
	  																	<span class="bk-slider-txt">9</span>
	  																</span>
	  															</span>
	  															<span class="bk-slider-unit bk-slider-yy">
	  																<span class="bk-slider-unit-box">
	  																	<span class="bk-slider-txt">1年</span>
	  																</span>
	  															</span>
	  															<span class="bk-slider-unit bk-slider-yy">
	  																<span class="bk-slider-unit-box">
	  																	<span class="bk-slider-txt">2年</span>
	  																</span>
	  															</span>
	  															<span class="bk-slider-unit bk-slider-yy">
	  																<span class="bk-slider-unit-box">
	  																	<span class="bk-slider-txt ">3年</span>
	  																</span>
	  															</span>
	  														</span>
	  													</span> 
	  													<span class="bk-slider-drag bk-slider-transition" style="left: 40px;"><i></i> <i></i>
	  													</span>
	  												</span>
	  											</div>	
	  										</div>	
	  									</div>
	  								</div>
	  								<div class="bk-form-row">
	  									<label class="bk-form-row-name">数量：</label>
	  									<div class="bk-form-row-cell">
	  										<div class="bk-form-row-li">
												<span class="bk-number">
													<input type="text" class="bk-number-input" value="1"> 
													<span class="bk-number-unit">台</span><span class="bk-number-control">
														<span class="bk-number-up bk-number-disabled hide">
															<i class="bk-number-arrow"></i>
														</span> 
														<span class="bk-number-up">
															<i class="bk-number-arrow"></i>
														</span> 
														<span class="bk-number-down bk-number-disabled">
															<i class="bk-number-arrow"></i>
														</span> 
														<span class="bk-number-down hide">
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
  					<div class="col-sm-12 col-md-3">
  						<div class="bk-orders-menu  bk-mb4">
  							<span class="bk-orders-menu-name">购买清单</span> 
  							<span class="bk-orders-menu-quantity bk-pale">0台</span>
  						</div>
  						<div class="bk-scope bk-items">
  							<div class="bk-items-title">当前配置</div>
  							<div>
  								<div class="bk-items-list">
  									<ul>
  										<li>
  											<span class="bk-items-item-name">地域：</span> 
  											<span class="bk-items-item-value">青岛（可用区B）</span>
  										</li>
  										<li>
  											<span class="bk-items-item-name">配置：</span>
  											<span class="bk-items-item-value">5GB存储空间、240MB内存、MySQL 5.5</span>
  										</li>
  										<li>
  											<span class="bk-items-item-name">购买量：</span> 
  											<span class="bk-items-item-value">1个月 x 1台</span>
  										</li>
  									</ul>
  								</div>
  							</div>
  							<div class="bk-items-price" style="padding-top: 0">
  								<div class="bk-items-price-title bk-pale">配置费用：</div>
  								<div class="bk-items-price-settle">
  									<div>
  										<span class="bk-cny">¥</span>
  										<span class="bk-items-price-money">40.80</span>
  										<span class="bk-items-price-unit"></span>
  									</div>
  								</div>
  							</div>
  							<div class="bk-items-price">
  								<div class="bk-items-price-title bk-pale">公网流量费用：</div>
  								<div class="bk-items-price-settle">
  									<span class="bk-cny">¥</span>
  									<span class="bk-items-price-money">1.00</span>
  									<span class="bk-items-price-unit">/GB</span>
  								</div>
  							</div>
  							<div class="bk-items-price-control">
  								<button class="bk-button bk-button-primary">
  									<div><span class="ng-scope">立即购买</span></div>
  								</button>
  								<button class="bk-button bk-button-default">
  									<div ng-transclude=""><span class="ng-scope">加入清单</span></div>
  								</button>
  							</div>
  							<div class="bk-pb4"></div>
  						</div>
  					</div>
  				</div>
  				
  				<div role="tabpanel" class="tab-pane" id="dosage">
  					<div class="col-sm-12 col-md-9">
	  					<dl class="bk-group">
	  						<dt class="bk-group-title">基本配置</dt>
	  						<dd class="bk-group-detail">
	  							<div class="bk-group-control"></div>
	  							<div>
	  								<div class="bk-form-row">
	  									<label class="bk-form-row-name">地域：</label>
	  									<div class="bk-form-row-cell">
	  										<div class="bk-form-row-li">
	  											<div class="bk-buttontab">
	  												<button class=" bk-button bk-button-primary bk-button-current">
	  												<div><span>青岛</span></div>
	  												</button>
	  												<button class="bk-button bk-button-primary">
	  												<div><span>杭州</span></div>
	  												</button>
	  												<button class="bk-button bk-button-primary">
	  												<div><span>北京</span></div>
	  												</button>
	  												<button class="bk-button bk-button-primary">
	  												<div><span>香港</span></div>
	  												</button>
	  												<button class="bk-button bk-button-primary">
	  												<div><span>深圳</span></div>
	  												</button>
	  											</div>
	  											<span><a class="bk-lnk bk-ml2">查看我的产品地域</a></span>
	  										</div>
	  										<div class="bk-form-row-txt">
	  											不同地域之间的产品内网不互通；订购后不支持更换地域，请谨慎选择 <a href="#" target="_blank" class="bk-lnk">教我选择&gt;&gt;</a>
	  										</div>
	  									</div>
	  								</div>
	  								<div class="bk-form-row">
	  									<label class="bk-form-row-name">可用区：</label>
	  									<div class="bk-form-row-cell">
	  										<div class="bk-form-row-li clearfix">
	  											<div class="dropdown pull-left">
		  											<button class="btn btn-default dropdown-toggle w217 wcolor mgl-r" type="button" id="singleUseable" data-toggle="dropdown">单可用区<span class="caret"></span>
													</button>
												    <ul class="dropdown-menu w217" role="menu" aria-labelledby="singleUseable">
												    	<li role="presentation"><a role="menuitem" tabindex="-1" href="#">单可用区</a></li>
												    </ul>
												</div>
												<div class="dropdown pull-left">
		  											<button class="btn btn-default dropdown-toggle w326 wcolor" type="button" id="UseableB" data-toggle="dropdown">可用区B<span class="caret"></span>
													</button>
												    <ul class="dropdown-menu w326" role="menu" aria-labelledby="UseableB">
												    	<li role="presentation"><a role="menuitem" tabindex="-1" href="#">可用区B <span class="text-muted text-xs">0台ECS</span></a></li>
												    </ul>
												</div>
												<span class="glyphicon glyphicon-question-sign margin-left-5" style="margin-top:8px;"></span>
	  										</div>
	  									</div>
	  								</div>
	  								<div class="bk-form-row">
	  									<label class="bk-form-row-name">数据库类型：</label>
	  									<div class="bk-form-row-cell">
	  										<div class="bk-form-row-li clearfix">
	  											<div class="dropdown pull-left">
		  											<button class="btn btn-default dropdown-toggle w217 wcolor" type="button" id="singleUseable" data-toggle="dropdown">MySQL<span class="caret"></span>
													</button>
												    <ul class="dropdown-menu w217" role="menu" aria-labelledby="singleUseable">
												    	<li role="presentation"><a role="menuitem" tabindex="-1" href="#">MySQL</a></li>
												    	<li role="presentation"><a role="menuitem" tabindex="-1" href="#">MS SQLServer</a></li>
												    </ul>
												</div>
	  										</div>
	  									</div>
	  								</div>
	  								<div class="bk-form-row">
	  									<label class="bk-form-row-name">版本：</label>
	  									<div class="bk-form-row-cell">
	  										<div class="bk-form-row-li">
	  											<div class="bk-buttontab">
	  												<button class=" bk-button bk-button-primary bk-button-current">
	  												<div><span>5.5</span></div>
	  												</button>
	  												<button class="bk-button bk-button-primary">
	  												<div><span>5.6</span></div>
	  												</button>
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
	  							<div>
	  								<div class="bk-form-row">
	  									<label class="bk-form-row-name">存储空间：</label>
	  									<div class="bk-form-row-cell">
	  										<div class="bk-form-row-li">
	  											<div class="bk-slider">
													<span class="bk-slider-range">
														<span class="bk-slider-block bk-slider-l2">
															<span class="bk-slider-block-box">
																<span class="bk-slider-txt">250GB</span>
															</span>
														</span>
														<span class="bk-slider-block bk-slider-l1">
															<span class="bk-slider-block-box">
																<span class="bk-slider-txt">500GB</span>
															</span>
														</span>
														<span class="bk-slider-block bk-slider-l1">
															<span class="bk-slider-block-box bk-slider-block-box-last bk-select-action">
																<span class="bk-slider-txt">1000GB</span>
															</span>
														</span>
														<span class="bk-slider-container bk-slider-transition" style="width: 11.88px;">
															<span class="bk-slider-current">
																<span class="bk-slider-unit bk-slider-l2">
																	<span class="bk-slider-unit-box bk-select-action">
																		<span class="bk-slider-txt">250GB</span></span>
																	</span>
																	<span class="bk-slider-unit bk-slider-l1">
																		<span class="bk-slider-unit-box">
																			<span class="bk-slider-txt">500GB</span>
																		</span>
																	</span>
																	<span class="bk-slider-unit bk-slider-l1">
																		<span class="bk-slider-unit-box bk-slider-unit-box-last">
																			<span class="bk-slider-txt">1000GB</span>
																		</span>
																	</span>
																</span>
															</span>
															<span class="bk-slider-drag bk-slider-transition">
																<i></i> <i></i>
																<div style="position: relative">
																	<div class="bk-tip-gray hide" style="top: -38px;left: -15px">
																		<span>15</span> 
																		<span class="bk-tip-arrow"></span>
																	</div>
																</div>
															</span>
													</span>
												</div>
	  											<span class="bk-number bk-ml2">
	  												<input type="text" class="bk-number-input" value="5"> 
	  												<span class="bk-number-unit">GB</span><span class="bk-number-control">
	  													<span class="bk-number-up bk-number-disabled hide">
	  														<i class="bk-number-arrow"></i>
	  													</span> 
	  													<span class="bk-number-up">
	  														<i class="bk-number-arrow"></i>
	  													</span> 
	  													<span class="bk-number-down bk-number-disabled">
	  														<i class="bk-number-arrow"></i>
	  													</span> 
	  													<span class="bk-number-down hide">
	  														<i class="bk-number-arrow"></i>
	  													</span>
	  												</span>
	  											</span>
	  										</div>
	  										<div class="bk-form-row-txt">
	  											步长为5GB
	  										</div>
	  									</div>
	  								</div>
	  								<div class="bk-form-row">
	  									<label class="bk-form-row-name">内存：</label>
	  									<div class="bk-form-row-cell">
	  										<div class="bk-form-row-li">
												<div class="dropdown">
		  											<button class="btn btn-default dropdown-toggle w217 wcolor" type="button" id="UseableB" data-toggle="dropdown">240MB<span class="caret"></span>
													</button>
												    <ul class="dropdown-menu w217 h216" role="menu" aria-labelledby="UseableB">
												    	<li role="presentation"><a role="menuitem" tabindex="-1" href="#">240MB</a></li>
												    	<li role="presentation"><a role="menuitem" tabindex="-1" href="#">600MB</a></li>
												    	<li role="presentation"><a role="menuitem" tabindex="-1" href="#">1200MB</a></li>
												    </ul>
												</div>
	  										</div>
	  										<div class="bk-form-row-txt">
	  											最大连接数:60 IOPS:150
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
	  									<div class="bk-form-row-cell">
	  										<div class="bk-form-row-li">
												<span class="bk-number">
													<input type="text" class="bk-number-input" value="1"> 
													<span class="bk-number-unit">台</span><span class="bk-number-control">
														<span class="bk-number-up bk-number-disabled hide">
															<i class="bk-number-arrow"></i>
														</span> 
														<span class="bk-number-up">
															<i class="bk-number-arrow"></i>
														</span> 
														<span class="bk-number-down bk-number-disabled">
															<i class="bk-number-arrow"></i>
														</span> 
														<span class="bk-number-down hide">
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
  					<div class="col-sm-12 col-md-3">
  						<div class="bk-orders-menu  bk-mb4">
  							<span class="bk-orders-menu-name">购买清单</span> 
  							<span class="bk-orders-menu-quantity bk-pale">0台</span>
  						</div>
  						<div class="bk-scope bk-items">
  							<div class="bk-items-title">当前配置</div>
  							<div>
  								<div class="bk-items-list">
  									<ul>
  										<li>
  											<span class="bk-items-item-name">地域：</span> 
  											<span class="bk-items-item-value">青岛（可用区B）</span>
  										</li>
  										<li>
  											<span class="bk-items-item-name">配置：</span>
  											<span class="bk-items-item-value">5GB存储空间、240MB内存、MySQL 5.5</span>
  										</li>
  										<li>
  											<span class="bk-items-item-name">购买量：</span> 
  											<span class="bk-items-item-value">1个月 x 1台</span>
  										</li>
  									</ul>
  								</div>
  							</div>
  							<div class="bk-items-price" style="padding-top: 0">
  								<div class="bk-items-price-title bk-pale">配置费用：</div>
  								<div class="bk-items-price-settle">
  									<div>
  										<span class="bk-cny">¥</span>
  										<span class="bk-items-price-money">40.80</span>
  										<span class="bk-items-price-unit"></span>
  									</div>
  								</div>
  							</div>
  							<div class="bk-items-price">
  								<div class="bk-items-price-title bk-pale">公网流量费用：</div>
  								<div class="bk-items-price-settle">
  									<span class="bk-cny">¥</span>
  									<span class="bk-items-price-money">1.00</span>
  									<span class="bk-items-price-unit">/GB</span>
  								</div>
  							</div>
  							<div class="bk-items-price-control">
  								<button class="bk-button bk-button-primary">
  									<div><span class="ng-scope">立即购买</span></div>
  								</button>
  								<button class="bk-button bk-button-default">
  									<div ng-transclude=""><span class="ng-scope">加入清单</span></div>
  								</button>
  							</div>
  							<div class="bk-pb4"></div>
  						</div>
  					</div>
  				</div>
			</div>
		</div>
	</div><!-- main-content-center-end -->
</div><!-- main-content end-->
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
seajs.use("${ctx}/static/page-js/dbCreate/main");
</script>
</html>