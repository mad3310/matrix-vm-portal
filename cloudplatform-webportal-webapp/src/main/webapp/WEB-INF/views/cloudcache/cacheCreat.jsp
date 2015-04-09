<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="zh">
<%@include file='main.jsp' %>
<body>
<style>
.bk-slider .bk-slider-range{padding-right:0;}
.bk-slider .bk-slider-container{padding:0;}
.bk-slider .bk-slider-l1 {margin-left:-3px;}
.awCursor {position: absolute;width: 0;height: 0;border-color: transparent;border-style: solid;top: 21px;left:0;margin-left: -15px;border-width: 0 15px 15px;border-bottom-color:#428bca;
}
</style>
	<!-- top bar begin -->
	<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation"
		style="min-height: 40px; height: 40px;">
		<div class="container-fluid">
			<div class="navbar-header">
				<a class="navbar-brand color" href="${ctx}/dashboard"
					style="padding-top: 2px; height: 40px !important;"><img
					src="${ctx}/static/img/logo.png" /></a> 
					<a class="navbar-brand color"
					href="${ctx}/dashboard"
					style="margin-left: 10px; height: 40px !important;"><i
					class="fa fa-home text-20"></i></a>
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
						<span>创建缓存实例</span>
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
										<form id="monthPurchaseForm">
											<div class="form-group bk-form-row col-sm-12">
												<label class="bk-form-row-name col-sm-2" style="padding-left: 0px;">实例名称：</label>
												<div class="col-sm-4 row">
													<input id="dbName" class="form-control" name="dbName" type="text">
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
																<span>酒仙桥机房</span>
																<ul class="hide">
																<li class="bk-select-option"><a href="javascript:;" selectid="14">酒仙桥机房</a></li></ul>
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
														<div class="bk-slider">
															<div class="bk-slider-range" id="bar0">
																<div id="flag"></div>
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
																<span class="bk-slider-block bk-slider-l1" style="margin-left:-4px;">
																	<span class="bk-slider-block-box bk-slider-block-box-last bk-select-action">
																		<span class="bk-slider-txt">1000GB</span>
																	</span>
																</span>
																<span class="bk-slider-container bk-slider-transition" id="layer2" style="width:4.12px;">
																	<span class="bk-slider-current">
																		<span class="bk-slider-unit bk-slider-l2">
																			<span class="bk-slider-unit-box">
																				<span class="bk-slider-txt">250GB</span>
																			</span>
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
																<!-- <button id="btn2"></button> -->
																<!-- <span class="bk-slider-drag" id="btn0"> <i></i> <i></i>
																	<span class="bk-tip-arrow"></span>
																</span> -->
																<span class="awCursor" style="left: 4.12px;"></span>
															</div>
														</div>
														<span class="bk-number bk-ml2">
															<input type="text" class="bk-number-input memSize" id="value2" value="5" onchange="inite()">
															<span class="bk-number-unit">GB</span>
															<span class="bk-number-control">
																<span class="bk-number-up mem-num-up"> <i class="bk-number-arrow"></i> </span> 
																<span class="bk-number-down mem-num-down bk-number-disabled"> <i class="bk-number-arrow"></i></span> 
															</span>
														</span>
												</div>
													<!-- <div class="bk-form-row-li clearfix">
														<div class="pull-left" style="height: 36px;">
															<span class="sleBG"> <span class="sleHid"> <select class="form-control w217 wcolor">
																		<option>10G</option>
																</select>
															</span>
															</span> <span class="bk-select-arrow"></span>
														</div>
													</div> -->
												</div>
											</div>
											<div class="bk-form-row">
												<label class="bk-form-row-name">实例类型：</label>
												<div class="bk-form-row-cell">
													<div class="bk-form-row-li">
														<div class="bk-buttontab">
															<input name="linkType" type="text" class="hide" value="0">
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
											</div>
										</div>
									</dd>
								</dl>
								<dl class="bk-group">
									<dt class="bk-group-title">购买量</dt>
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
																<span>1月</span>
																<ul class="hide">
																	<li class="bk-select-option"><a href="javascript:;">1月</a></li>
																	<li class="bk-select-option"><a href="javascript:;">2月</a></li>
																</ul>
															</div>
														</span>
														</span> <span class="bk-select-arrow"></span>
													</div>
												</div>
												<!-- <div class="bk-form-row-txt">此功能暂时不开放</div> -->
											</div>
										</div>
										<div class="bk-form-row">
											<label class="bk-form-row-name">购买数量：</label>
											<div class="bk-form-row-cell">
												<div class="bk-form-row-li">
													<span class="bk-number">
														<input type="text" class="bk-number-input tai-num" value="3" onchange="taiChange()">
														<span class="bk-number-unit">台</span>
														<span class="bk-number-control"> 
															<span class="bk-number-up"> <i class="bk-number-arrow"></i></span>
															<span class="bk-number-down"> <i class="bk-number-arrow"></i>
														</span> 
													</span>
													</span>
												</div>
											</div>
										</div>
										<!-- <div class="bk-form-row">
											<label class="bk-form-row-name">费用合计：</label>
											<label class="bk-form-row-name">****元</label>
										</div> -->
									</dd>
								</dl>
							</div>
						</div>
						<div class="col-sm-12 col-md-3">
							<div class="bk-orders-menu  bk-mb4">
								<span class="bk-orders-menu-name">购买清单</span> <span class="bk-orders-menu-quantity bk-pale">3台</span>
							</div>
							<div class="bk-scope bk-items">
								<div class="bk-items-title">当前配置</div>
								<div>
									<div class="bk-items-list">
										<ul>
											<li><span class="bk-items-item-name">地域：</span> <span class="bk-items-item-value">北京</span></li>
											<li><span class="bk-items-item-name">配置：</span> <span class="bk-items-item-value">10GB存储空间、2G内存、MySQL 5.5</span></li>
											<li><span class="bk-items-item-name">购买量：</span> <span class="bk-items-item-value">1年x 3台</span></li>
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
								</div>
								<div class="bk-pb4"></div>
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
		"bootstrap": "bootstrap/bootstrap/3.3.0/bootstrap.js"
	}
});

/*self define*/
selecAwClick();
var _up=$('.mem-num-up');
var _down=$('.mem-num-down');
var _upT=$('.bk-number-up');
var _downT=$('.bk-number-down');
_up.click(function(event) {
	var _memSize=$('.memSize');
	var val=_memSize.val();
		var temp=parseInt(val)+5;
	if(temp>=1000){
		_up.addClass('bk-number-disabled');
	}else{
		_up.removeClass('bk-number-disabled');
		if(temp>5){
			_down.removeClass('bk-number-disabled');
		}else{
			_down.addClass('bk-number-disabled');
		}
	}
	_memSize.val(temp);
	changeDrag(temp);
});
_down.click(function(event) {
	var _memSize=$('.memSize');
	var val=_memSize.val();
	var temp=parseInt(val)-5;
	if(temp<=5){
		_down.addClass('bk-number-disabled');
	}else{
		_down.removeClass('bk-number-disabled');
		if(temp<1000){
			_up.removeClass('bk-number-disabled');
		}else{
			_up.addClass('bk-number-disabled');
		}
	}
	_memSize.val(temp);
	changeDrag(temp);
});
sliderClick();
awDrag();
_upT.click(function(event) {
	var _taiNum=$('.tai-num');
	var val=_taiNum.val();
	val=parseInt(val);
	val=val+1;
	if(val<=1){
		val=1;
		_downT.addClass('bk-number-disabled');
	}else if(val>=99){
		_upT.addClass('bk-number-disabled');
		val=99;
	}else{
		//合法范围
		_upT.removeClass('bk-number-disabled');
		_downT.removeClass('bk-number-disabled');
		
	}
	_taiNum.val(val);
});
_downT.click(function(event) {
	var _taiNum=$('.tai-num');
	var val=_taiNum.val();
	val=parseInt(val);
	val=val-1;
	if(val<=1){
		val=1;
		_downT.addClass('bk-number-disabled');
	}else if(val>=99){
		val=99;
		_upT.addClass('bk-number-disabled');
	}else{
		//合法范围
		_upT.removeClass('bk-number-disabled');
		_downT.removeClass('bk-number-disabled');
	}
	_taiNum.val(val);
});
// permentClick();
primaryClick();
function inite(){
	var _memSize=$('.memSize');
	var val=_memSize.val();
	changeDrag(val);	
}
function taiChange(){
	var _taiNum=$('.tai-num');
	var val=_taiNum.val();
	chgeBuyNmu();
}
function sliderClick(){
	var _slider=$('.bk-slider');
	_slider.click(function(event) {
		var left=event.pageX;
		var left2=_slider.offset().left;
		var relaLen=left-left2;
		drag(relaLen)
		
	});
}
function awDrag(){
	var _awCursor=$('.awCursor');
	var _slider=$('.bk-slider');
	var dgFlag=false;
	var temp;
	_awCursor.mousedown(function(event) {
		dgFlag=true;
	});
	$('body').mousemove(function(event) {
		if(dgFlag){
			//可以拖拽
			var left=event.pageX;
			var left2=_slider.offset().left;
			temp=parseInt(left)-parseInt(left2);
			if(temp>414){
				//拖拽过最右界
				temp=412;
			}else{
				 if(temp<0){
				 	//拖拽过最左界
					temp=5;
				 }else{
				 	//允许范围之内
				}				
			}
			drag(temp);
		}else{
			//不能拖拽
		}
	});
	$('body').mouseup(function(event) {
		dgFlag=false;
		console.log(temp+"up");
	});
}
function primaryClick(){
	var _current=$('.bk-button-primary');
	_current.click(function(event) {
		var _this=$(this);
		if(_this.hasClass('bk-button-current')){

		}else{
			_this.removeClass('disabled').addClass('bk-button-current');
			_this.siblings().removeClass('bk-button-current');
		}
	});
}
function changeDrag(val){
	var _memSize=$('.memSize');
	var _awCursor=$('.awCursor');
	var _layer2=$('#layer2');
	var lenPerc;
	var i=Math.ceil(val/5);
	val=i*5;
	_memSize.val(val);
	if(val<5){
		_memSize.val(5);
		lenPerc=_memSize.val()*206/250;
		_awCursor.css({
			left:lenPerc
		});
	}else{
		if(val<=250){
			lenPerc=_memSize.val()*206/250;
			_awCursor.css({
				left:lenPerc
			});
		}else if(val<=500){
			lenPerc=(_memSize.val()-250)*103/250;
			lenPerc+=206;
			_awCursor.css({
				left:lenPerc
			});
		}else{
			if(val<=1000){
				lenPerc=(_memSize.val()-500)*103/500;
				lenPerc+=309;
				_awCursor.css({
					left:lenPerc
				});
			}else{
				_memSize.val(1000);
				lenPerc=414;
				_awCursor.css({
					left:lenPerc
				});
			}
		}
	}
	_awCursor.css({
		left:lenPerc
	});
	_layer2.css({
		width:lenPerc
	});
}
function chgeBuyNmu(){
	var _taiNum=$('.tai-num');
	var val=_taiNum.val();
	val=parseInt(val);
	if(val<=1){
		val=1;
		_downT.addClass('bk-number-disabled');
	}else if(val>=99){
		val=99;
		_upT.addClass('bk-number-disabled');
	}else{
		//合法范围
		_upT.removeClass('bk-number-disabled');
		_downT.removeClass('bk-number-disabled');
	}
	_taiNum.val(val);
}
function drag(relaLen){
	var _memSize=$('.memSize');
	var _awCursor=$('.awCursor');
	var _layer2=$('#layer2');
	if(relaLen>414){
		//不合适
		_up.addClass('bk-number-disabled')
	}else{
		_up.removeClass('bk-number-disabled');
		_down.removeClass('bk-number-disabled');
		if(relaLen>309){
			//500-1000档
			var tempL=parseInt(relaLen)-206-103;
			var temp=tempL*500/103;
			var i=Math.ceil(temp);
			// temp=i*5;
			temp=i+500;
			_memSize.val(temp);
			_layer2.css({
				width:relaLen
			});
			_awCursor.css({
				left:relaLen
			});
		}else if(relaLen>206){
			//250-500
			var tempL=parseInt(relaLen)-206;
			var temp=tempL*250/103;
			var i=Math.ceil(temp);
			// temp=i*5;
			temp=i+250;
			_memSize.val(temp);
			_layer2.css({
				width:relaLen
			});
			_awCursor.css({
				left:relaLen
			});
		}else if(relaLen>=5){
			//5-250
			var tempL=parseInt(relaLen);
			var i=Math.ceil(tempL/5);
			tempL=i*5;
			_memSize.val(tempL);
			_layer2.css({
				width:relaLen
			});
			_awCursor.css({
				left:relaLen
			});
		}else{
			//<5 不合适
			_down.addClass('bk-number-disabled')
		}
	}
}
function selecAwClick(){
	var _target=$('.divselect');
	_target.click(function(event) {
		var _this=$(this);
		_this.parent().parent().next().toggleClass('turn-self');
		_this.find('ul').toggleClass('hide');
	});
	var _li=$('.bk-select-option');
	_li.click(function(event) {
		var _this=$(this);
		console.log(_this.text());
		_this.parent().prev().text(_this.text());
	});
}
</script>
</html>