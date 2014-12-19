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
	<div class="container-fluid bodycolor"><!-- main-content begin-->
		<div class="home">
			<div class="home-content">
				<div class="user-profile row"><!-- begin user-profile -->
					<div class="col-xs-6 col-md-6"><!-- begin userinfo left-->
						<div class="info clearfix">
							<div class="user-info pull-left">
								<p class="text-muted user-name ellipsis">Hi,<a href="#" target="_blank" class="home-orange" text-length="8">姚阔</a></p>
								<p class="user-email ellipsis">yaokuo1209</p>
							</div>
							<div class="account pull-left">
								<div class="mlt-4 pull-left">
									<p class="balance-title">账户余额:</p>
									<p class="balance-num ellipsis" >
										<span>500</span>
										<span class="balance-after-point">.00</span>
										<span class="balance-unit">元</span>
									</p>
								</div>
								<div class="account-opt pull-left">
									<div class="account-opt-row">
										<a href="#" target="_blank" class="btn btn-default btn-sm">充值</a>
										<a href="#" target="_blank" class="btn btn-default btn-sm">提现</a>
										<a href="#" target="_blank" class="btn btn-default btn-sm">索取发票</a>
									</div>
									<div>
										<a href="#" target="_blank" class="btn btn-default btn-sm">订单管理</a>
										<a target="_blank" class="withholding" id="home-cash" href="#" data-toggle="tooltip" data-placement="top" title="您还未签约支付宝代扣服务，点击查看详情">
											<span class="glyphicon glyphicon-shopping-cart text-warning"></span> <span >未签约</span>
										</a>
									</div>
								</div>
							</div> 
						</div>
					</div><!-- end userinfo left-->
					<div class="col-xs-6 col-md-6 yundun-wrap"><!-- begin cloud right-->
						<div class="yundun-bg  yundun-bg-notopen"></div>
						<div class="yundun">
							<div class="yundun-inner clearfix">
								<div class="pull-left">
									<a class="product-icons-48 product-icons-yundun-grey" href="#"></a>
								</div>
								<div class="yundun-content pull-left">
									<div class="yundun-title">
										<a href="#">云盾</a>
										<div class="yundun-title-tips">
											<span class="glyphicon glyphicon-info-sign text-warning"></span>
											<span class="text-danger">您尚未购买云服务器或负载均衡,在您购买后会自动开启云盾服务</span>
										</div>
									</div>
									<div>
										<div class="yundun-actions clearfix">
											<div class="clearfix">
												<div class="yundun-action yundun-action-status">
													<a href="#"><span class="glyphicon glyphicon-cloud text-muted"></span> <span class="text-muted">未开通</span>
													</a>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div><!-- end cloud right-->
				</div><!-- end user-profile -->
				<div class="product-list product-list-opened"><!-- 已开通产品 begin -->
					<div class="list-title">
						<span class="list-title-em">已开通</span>的产品与服务:
					</div>
					<ul class="row">
						<li class="product-item col-xs-3 col-sm-3 col-md-3 col-lg-3">
							<div class="item-profile clearfix">
								<a href="#" class="pull-left">
									<span class="item-icon product-icons-48 product-icons-rds"></span>
								</a>
								<p class="item-title">
									<a href="${ctx}/list/db">关系型数据库&nbsp;<span class="item-title-short">RDS</span>
									</a>
								</p>
							</div>
							<div class="item-record">
								<span class="item-record-num">
									<!-- <span size="14" class="item-record-loading hide"></span>  -->
									<!-- <a class="glyphicon glyphicon-refresh item-record-reload"></a>  -->
									<a class="item-record-num-count" href="${ctx}/list/db"><span>1</span></a>
								</span>
								<span class="item-record-unit">个</span> 
								<span class="glyphicon glyphicon-question-sign text-muted"></span>
							</div>
						</li>
						<li class="product-item col-xs-3 col-sm-3 col-md-3 col-lg-3">
							<div class="item-profile clearfix">
								<a href="#" class="pull-left">
									<span class="item-icon product-icons-48 product-icons-jiankong"></span>
								</a>
								<p class="item-title">
									<a href="#">云监控&nbsp;<span class="item-title-short"></span></a>
								</p>
							</div>
							<div class="item-record">
								<span class="item-record-num">
									<a class="item-record-num-count" href="#"><span>0</span></a>
								</span> 
								<span class="item-record-unit">个</span> 
								<span class="glyphicon glyphicon-question-sign text-muted"></span>
							</div>
						</li>
						<li class="product-item col-xs-3 col-sm-3 col-md-3 col-lg-3">
							<div class="item-profile clearfix">
								<a href="#" class="pull-left">
									<span class="item-icon product-icons-48 product-icons-toolsimage"></span>
								</a>
								<p class="item-title">
									<a href="#">工具与镜像&nbsp;<span class="item-title-short"></span></a>
								</p>
							</div>
						</li>
					</ul>
				</div><!-- 已开通产品 end -->
				<div class="product-list product-list-notopen"><!-- 未开通产品 begin -->
					<div class="list-title">
						<span class="list-title-em">未开通</span>的产品与服务:
					</div>
					<ul class="row">
						<li class="col-xs-3 col-sm-3 col-md-3 col-lg-3 product-col">
							<div class="product-category">
								<p class="category-title ng-binding">弹性计算</p>
								<ul>
									<li class="clearfix">
										<div class="pull-left">
											<span class="glyphicon glyphicon-th-list text-muted"></span> 
											<span>云服务器</span>
											<span>ECS</span>
										</div>
										<div class="pull-right clearfix">
											<div class="pull-left product-opt-wrap">
												<a href="#" target="_blank" class="product-opt">
													<span class="glyphicon glyphicon-shopping-cart product-opt-icon"></span>
												</a>
											</div>
											<div class="pull-left product-opt-wrap">
												<a href="#" target="_blank" class="product-opt">
													<span class="glyphicon glyphicon-collapse-up product-opt-icon"></span>
												</a>
											</div>
											<div class="pull-left product-opt-wrap">
												<a href="#" target="_blank" class="product-opt">
													<span class="glyphicon glyphicon-tags product-opt-icon"></span>
												</a>
											</div>
										</div>
									</li>
									<li class="clearfix">
										<div class="pull-left">
											<span class="glyphicon glyphicon-tasks text-muted"></span> 
											<span>负载均衡</span>
											<span>SLB</span>
										</div>
										<div class="pull-right clearfix">
											<div class="pull-left product-opt-wrap">
												<a href="#" target="_blank" class="product-opt">
													<span class="glyphicon glyphicon-shopping-cart product-opt-icon"></span>
												</a>
											</div>
											<div class="pull-left product-opt-wrap">
												<a href="#" target="_blank" class="product-opt">
													<span class="glyphicon glyphicon-collapse-up product-opt-icon"></span>
												</a>
											</div>
										</div>
									</li>
									<li class="clearfix">
										<div class="pull-left">
											<span class="glyphicon glyphicon-random text-muted"></span> 
											<span>弹性伸缩服务</span>
											<span>ESS</span>
										</div>
										<div class="pull-right clearfix">
											<div class="pull-left product-opt-wrap">
												<a href="#" target="_blank" class="product-opt">
													<span class="glyphicon glyphicon-lock product-opt-icon"></span>
												</a>
											</div>
											<div class="pull-left product-opt-wrap">
												<a href="#" target="_blank" class="product-opt">
													<span class="glyphicon glyphicon-collapse-up product-opt-icon"></span>
												</a>
											</div>
										</div>
									</li>
								</ul>
							</div>
							<div class="product-category">
								<p class="category-title ng-binding">数据库</p>
								<ul>
									<li class="clearfix">
										<div class="pull-left">
											<span class="glyphicon glyphicon-tower text-muted"></span> 
											<span>开放结构化数据服务</span>
											<span>OTS</span>
										</div>
										<div class="pull-right clearfix">
											<div class="pull-left product-opt-wrap">
												<a href="#" target="_blank" class="product-opt">
													<span class="glyphicon glyphicon-lock product-opt-icon"></span>
												</a>
											</div>
											<div class="pull-left product-opt-wrap">
												<a href="#" target="_blank" class="product-opt">
													<span class="glyphicon glyphicon-collapse-up product-opt-icon"></span>
												</a>
											</div>
										</div>
									</li>
									<li class="clearfix">
										<div class="pull-left">
											<span class="glyphicon glyphicon-leaf text-muted"></span> 
											<span>开放缓存服务</span>
											<span>OCS</span>
										</div>
										<div class="pull-right clearfix">
											<div class="pull-left product-opt-wrap">
												<a href="#" target="_blank" class="product-opt">
													<span class="glyphicon glyphicon-shopping-cart product-opt-icon"></span>
												</a>
											</div>
											<div class="pull-left product-opt-wrap">
												<a href="#" target="_blank" class="product-opt">
													<span class="glyphicon glyphicon-collapse-up product-opt-icon"></span>
												</a>
											</div>
										</div>
									</li>
									<li class="clearfix">
										<div class="pull-left">
											<span class="glyphicon glyphicon-fullscreen text-muted"></span> 
											<span>分布式关系型数据库</span>
											<span>DRDS</span>
										</div>
										<div class="pull-right clearfix">
											<div class="pull-left product-opt-wrap">
												<a href="#" target="_blank" class="product-opt">
													<span class="glyphicon glyphicon-lock product-opt-icon"></span>
												</a>
											</div>
											<div class="pull-left product-opt-wrap">
												<a href="#" target="_blank" class="product-opt">
													<span class="glyphicon glyphicon-collapse-up product-opt-icon"></span>
												</a>
											</div>
										</div>
									</li>
								</ul>
							</div>
						</li>
						<li class="col-xs-3 col-sm-3 col-md-3 col-lg-3 product-col">
							<div class="product-category">
								<p class="category-title ng-binding">存储与CDN</p>
								<ul>
									<li class="clearfix">
										<div class="pull-left">
											<span class="glyphicon glyphicon-cloud-upload text-muted"></span> 
											<span>开放存储服务</span>
											<span>OSS</span>
										</div>
										<div class="pull-right clearfix">
											<div class="pull-left product-opt-wrap">
												<a href="#" target="_blank" class="product-opt">
													<span class="glyphicon glyphicon-shopping-cart product-opt-icon"></span>
												</a>
											</div>
											<div class="pull-left product-opt-wrap">
												<a href="#" target="_blank" class="product-opt">
													<span class="glyphicon glyphicon-collapse-up product-opt-icon"></span>
												</a>
											</div>
										</div>
									</li>
									<li class="clearfix">
										<div class="pull-left">
											<span class="glyphicon glyphicon-qrcode text-muted"></span> 
											<span>内容分发网络</span>
											<span>CDN</span>
										</div>
										<div class="pull-right clearfix">
											<div class="pull-left product-opt-wrap">
												<a href="#" target="_blank" class="product-opt">
													<span class="glyphicon glyphicon-lock product-opt-icon"></span>
												</a>
											</div>
											<div class="pull-left product-opt-wrap">
												<a href="#" target="_blank" class="product-opt">
													<span class="glyphicon glyphicon-collapse-up product-opt-icon"></span>
												</a>
											</div>
										</div>
									</li>
									<li class="clearfix">
										<div class="pull-left">
											<span class="glyphicon glyphicon-retweet text-muted"></span> 
											<span>开放归档服务</span>
											<span>OAS</span>
										</div>
										<div class="pull-right clearfix">
											<div class="pull-left product-opt-wrap">
												<a href="#" target="_blank" class="product-opt">
													<span class="glyphicon glyphicon-collapse-up product-opt-icon"></span>
												</a>
											</div>
										</div>
									</li>
								</ul>
							</div>
							<div class="product-category">
								<p class="category-title ng-binding">大规模计算</p>
								<ul>
									<li class="clearfix">
										<div class="pull-left">
											<span class="glyphicon glyphicon-road text-muted"></span> 
											<span>开放数据处理服务</span>
											<span>ODPS</span>
										</div>
										<div class="pull-right clearfix">
											<div class="pull-left product-opt-wrap">
												<a href="#" target="_blank" class="product-opt">
													<span class="glyphicon glyphicon-lock product-opt-icon"></span>
												</a>
											</div>
											<div class="pull-left product-opt-wrap">
												<a href="#" target="_blank" class="product-opt">
													<span class="glyphicon glyphicon-collapse-up product-opt-icon"></span>
												</a>
											</div>
										</div>
									</li>
									<li class="clearfix">
										<div class="pull-left">
											<span class="glyphicon glyphicon-cloud-download text-muted"></span> 
											<span>采云间</span>
											<span>DPC</span>
										</div>
										<div class="pull-right clearfix">
											<div class="pull-left product-opt-wrap">
												<a href="#" target="_blank" class="product-opt">
													<span class="glyphicon glyphicon-lock product-opt-icon"></span>
												</a>
											</div>
											<div class="pull-left product-opt-wrap">
												<a href="#" target="_blank" class="product-opt">
													<span class="glyphicon glyphicon-collapse-up product-opt-icon"></span>
												</a>
											</div>
										</div>
									</li>
									<li class="clearfix">
										<div class="pull-left">
											<span class="glyphicon glyphicon-compressed text-muted"></span> 
											<span>分析数据库服务</span>
											<span>ADS</span>
										</div>
										<div class="pull-right clearfix">
											<div class="pull-left product-opt-wrap">
												<a href="#" target="_blank" class="product-opt">
													<span class="glyphicon glyphicon-lock product-opt-icon"></span>
												</a>
											</div>
											<div class="pull-left product-opt-wrap">
												<a href="#" target="_blank" class="product-opt">
													<span class="glyphicon glyphicon-collapse-up product-opt-icon"></span>
												</a>
											</div>
										</div>
									</li>
								</ul>
							</div>
						</li>
						<li class="col-xs-3 col-sm-3 col-md-3 col-lg-3 product-col">
							<div class="product-category">
								<p class="category-title ng-binding">应用服务</p>
								<ul>
									<li class="clearfix">
										<div class="pull-left">
											<span class="glyphicon glyphicon-gift text-muted"></span> 
											<span>云引擎</span>
											<span>ACE</span>
										</div>
										<div class="pull-right clearfix">
											<div class="pull-left product-opt-wrap">
												<a href="#" target="_blank" class="product-opt">
													<span class="glyphicon glyphicon-envelope product-opt-icon"></span>
												</a>
											</div>
											<div class="pull-left product-opt-wrap">
												<a href="#" target="_blank" class="product-opt">
													<span class="glyphicon glyphicon-collapse-up product-opt-icon"></span>
												</a>
											</div>
										</div>
									</li>
									<li class="clearfix">
										<div class="pull-left">
											<span class="glyphicon glyphicon-inbox text-muted"></span> 
											<span>简单日志服务</span>
											<span>SLS</span>
										</div>
										<div class="pull-right clearfix">
											<div class="pull-left product-opt-wrap">
												<a href="#" target="_blank" class="product-opt">
													<span class="glyphicon glyphicon-envelope product-opt-icon"></span>
												</a>
											</div>
											<div class="pull-left product-opt-wrap">
												<a href="#" target="_blank" class="product-opt">
													<span class="glyphicon glyphicon-collapse-up product-opt-icon"></span>
												</a>
											</div>
										</div>
									</li>
									<li class="clearfix">
										<div class="pull-left">
											<span class="glyphicon glyphicon-import text-muted"></span> 
											<span>消息队列服务</span>
											<span>MQS</span>
										</div>
										<div class="pull-right clearfix">
											<div class="pull-left product-opt-wrap">
												<a href="#" target="_blank" class="product-opt">
													<span class="glyphicon glyphicon-lock product-opt-icon"></span>
												</a>
											</div>
											<div class="pull-left product-opt-wrap">
												<a href="#" target="_blank" class="product-opt">
													<span class="glyphicon glyphicon-collapse-up product-opt-icon"></span>
												</a>
											</div>
										</div>
									</li>
									<li class="clearfix">
										<div class="pull-left">
											<span class="glyphicon glyphicon-search text-muted"></span> 
											<span>开放搜索服务</span>
										</div>
										<div class="pull-right clearfix">
											<div class="pull-left product-opt-wrap">
												<a href="#" target="_blank" class="product-opt">
													<span class="glyphicon glyphicon-lock product-opt-icon"></span>
												</a>
											</div>
											<div class="pull-left product-opt-wrap">
												<a href="#" target="_blank" class="product-opt">
													<span class="glyphicon glyphicon-collapse-up product-opt-icon"></span>
												</a>
											</div>
										</div>
									</li>
									<li class="clearfix">
										<div class="pull-left">
											<span class="glyphicon glyphicon-sound-dolby text-muted"></span> 
											<span>性能测试服务 PTS</span>
										</div>
										<div class="pull-right clearfix">
											<div class="pull-left product-opt-wrap">
												<a href="#" target="_blank" class="product-opt">
													<span class="glyphicon glyphicon-lock product-opt-icon"></span>
												</a>
											</div>
											<div class="pull-left product-opt-wrap">
												<a href="#" target="_blank" class="product-opt">
													<span class="glyphicon glyphicon-collapse-up product-opt-icon"></span>
												</a>
											</div>
										</div>
									</li>
									<li class="clearfix">
										<div class="pull-left">
											<span class="glyphicon glyphicon-list-alt text-muted"></span> 
											<span>开放消息服务 ONS</span>
										</div>
										<div class="pull-right clearfix">
											<div class="pull-left product-opt-wrap">
												<a href="#" target="_blank" class="product-opt">
													<span class="glyphicon glyphicon-lock product-opt-icon"></span>
												</a>
											</div>
											<div class="pull-left product-opt-wrap">
												<a href="#" target="_blank" class="product-opt">
													<span class="glyphicon glyphicon-collapse-up product-opt-icon"></span>
												</a>
											</div>
										</div>
									</li>
								</ul>
							</div>
						</li>
					</ul>
				</div>
			</div>
		</div>
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

seajs.use("${ctx}/static/page-js/dashboard/main");
</script>
</html>