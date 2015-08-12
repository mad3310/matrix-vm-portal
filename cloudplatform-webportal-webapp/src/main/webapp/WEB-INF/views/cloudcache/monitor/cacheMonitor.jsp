<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<meta charset="utf-8" />
<meta http-equiv="X-UA-compatible" content="IE=edge,chrome=1" />
<meta name="viewpoint" content="width=device-width,initial-scale=1, maximum-scale=1, user-scalable=no"/>
<!-- bootstrap css -->
<link rel="stylesheet" href="${ctx}/static/css/bootstrap.min.css" />
<!-- fontawesome css -->
<link rel="stylesheet" href="${ctx}/static/css/font-awesome.min.css" />
<!-- ui-css -->
<link rel="stylesheet" href="${ctx}/static/css/ui-css/common.css" />
<link rel="stylesheet" href="${ctx}/static/css/ui-css/cbase.css" />
</head>
<style>
 .bk-select-arrow {position: absolute;left:200px;top: -35px;float: right;display: inline;width: 0;height: 0;border: solid 6px transparent;border-top-color: #fff;margin: 15px 0 0 10px;-webkit-transform-origin: 50% 20% 0;-moz-transform-origin: 50% 20% 0;-ms-transform-origin: 50% 20% 0;-o-transform-origin: 50% 20% 0;-webkit-transform: rotate(0deg);-moz-transform: rotate(0deg);-ms-transform: rotate(0deg);-o-transform: rotate(0deg);
}
.btn-search{vertical-align:top;}
.chart{padding:10px;}
</style>
<body>
<!-- 全局参数 start -->
	<input class="hidden" value="${cacheId}" name="cacheId" id="cacheId" type="text" />
	<!-- 全局参数 end -->
	<!-- <div class="se-heading" style="padding:10px;">
		<div class="row main-header">
			<div class="col-sm-12 col-md-6">
				<div class="pull-left">
					<h4>
						<span>缓存实例监控</span>
					</h4>
				</div>
			</div>
		</div>
		<div class="col-sm-12 col-md-12">
			<div class="time-range-unit-header">
				<span class="time-range-title">监控指标:</span>
				<div class="bk-form-row-cell">
					<div class="bk-form-row-li clearfix">
						<div class="pull-left">
							<span class="sleBG"> <span class="sleHid"> 
							<div class="divselect">
								<span>实例使用容量（KB）</span>
								<ul style="display:none;">
								<li class="bk-select-option"><a href="javascript:;" selectid="14">实例使用容量（KB）</a></li></ul>
								<input name="cacheId" type="hidden" value="">
							</div>
							<select name="hclusterId" class="form-control w217 wcolor">
							<option>实例使用容量（KB）</option>
								</select>
							</span>
							</span>
							<span class="" style="position:relative;"><span class="bk-select-arrow"></span></span>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="col-sm-12 col-md-12">
			<div class="time-range-unit-header">
	    		<span class="time-range-title">选择时间范围：</span>
	    		<div class="bk-form-row-cell">
		    		<div class="date-unit">
	        			<input type="date" class="form-control datetimepicker" id="startTime">
		    		</div>
		    		<span class="date-step-span">至</span>
		    		<div class="date-unit">
		    		     <input type="date" class="form-control datetimepicker" id="endTime">
		    	    </div>
		    	    <div class="date-unit"><button id="bksearch" class="btn btn-primary btn-search">查询</button></div>
	    	    </div>
	    	</div>
		</div>
	</div> -->
	<div class="se-heading">
		<div class="pull-left">
			<h5 class="">缓存实例监控</h5>
		</div>
		<div class="pull-right">
			<input class="hidden" value="1" name="strategy" id="strategy" type="text" />
			<span class="monitor-date-block">
				<ul class="nav nav-pills">
					<li class="time-block active" value="1">
						<a>1小时</a>
					</li>
					<li class="time-block" value="2">
						<a>3小时</a>
					</li>
					<li class="time-block" value="3">
						<a>1天</a>
					</li>
					<li class="time-block" value="4">
						<a>7天</a>
					</li>
					<li class="time-block" value="5">
						<a>1个月</a>
					</li>
				</ul>
			</span>
		</div>
	</div>
	<div class="monitor-charts">
		<div class="chart-content" style="width:100%">
			<div class="chart"></div>
		</div>
		<div class="footer" style="width:100%;height:200px;">						
		</div>
	</div>
	<!-- main-content-center-end -->			
</body>
<script type="text/javascript" src="${ctx}/static/modules/seajs/2.3.0/sea.js"></script>
<script>
//Set configuration
seajs.config({
	base: "${ctx}/static/modules/",
	alias: {
		"jquery": "jquery/2.0.3/jquery.min.js",
		"bootstrap": "bootstrap/bootstrap/3.3.0/bootstrap.js",
		"highcharts": "highcharts/highcharts.src.js"
	}
});
seajs.use("${ctx}/static/page-js/cloudcache/cacheMonitor/main");
// self define
</script>
</html>