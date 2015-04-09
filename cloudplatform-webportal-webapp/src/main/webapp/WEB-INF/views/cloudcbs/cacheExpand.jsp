<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="zh">
<%@include file='main.jsp' %>
<style>
.bk-slider .bk-slider-range{padding-right:0;}
.bk-slider .bk-slider-container{padding:0;}
.bk-slider .bk-slider-l1 {margin-left:-3px;}
.awCursor {position: absolute;width: 0;height: 0;border-color: transparent;border-style: solid;top: 21px;left:0;margin-left: -15px;border-width: 0 15px 15px;border-bottom-color:#428bca;
}
</style>
<body>
		<div class="container-fluid">
		<!-- main-content begin-->
		<div class="row main-header">
			<!-- main-content-header begin -->
			<div class="col-sm-12 col-md-6">
				<div class="pull-left">
					<h4>
						<span>缓存实例扩容</span>
					</h4>
				</div>
			</div>
		</div>
		<!-- main-content-header end-->

		<div class="row">
			<!-- main-content-center-begin -->
			<div class="col-sm-12  col-md-7">
				<div class="tab-content mt20">
						<div role="tabpanel" class="tab-pane fade active in" id="year">
							<div class="col-sm-12 col-md-12">
								<dl class="bk-group">
									<dt class="bk-group-title">基本配置</dt>
									<dd class="bk-group-detail">
										<div class="bk-group-control"></div>
										<div class="form-group bk-form-row col-sm-12">
											<label class="bk-form-row-name col-sm-2" style="padding-left: 0px;">实例名称：</label>
											<label class="bk-form-row-name col-sm-10">cyxtest</label>
										</div>
										<div class="form-group bk-form-row col-sm-12">
											<label class="bk-form-row-name col-sm-2" style="padding-left: 0px;">扩容前容量：</label>
											<label class="bk-form-row-name col-sm-10">70GB</label>
										</div>
									</dd>
								</dl>
								<dl class="bk-group">
									<dt class="bk-group-title">扩容配置</dt>
									<dd class="bk-group-detail">
										<div class="bk-group-control"></div>
										<div class="disabled">
											<div class="bk-form-row">
												<label class="bk-form-row-name">扩容大小：</label>
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
																<span class="bk-number-up"> <i class="bk-number-arrow"></i> </span> 
																<span class="bk-number-down bk-number-disabled"> <i class="bk-number-arrow"></i></span> 
															</span>
														</span>
												</div>
												<div class="bk-form-row-txt">步长为5GB</div> 
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
											<div class="form-group bk-form-row col-sm-12">
												<label class="bk-form-row-name col-sm-2" style="padding-left: 0px;">扩容前容量：</label>
												<label class="bk-form-row-name">140GB</label>
											</div>
										</div>
									</dd>
								</dl>
								<dl class="bk-group">
									<dt class="bk-group-title">费用</dt>
									<dd class="bk-group-detail">
										<div class="bk-group-control"></div>
									
										<div class="bk-form-row">
											<label class="bk-form-row-name">费用合计：</label>
											<label class="bk-form-row-name">****元</label>
										</div>
									</dd>
								</dl>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
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
var _up=$('.bk-number-up');
var _down=$('.bk-number-down');
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

function inite(){
	var _memSize=$('.memSize');
	var val=_memSize.val();
	changeDrag(val);	
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
			console.log(temp)
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
</script>
</html>