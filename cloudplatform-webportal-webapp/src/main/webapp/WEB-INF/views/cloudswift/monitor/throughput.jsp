<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<meta charset="utf-8" />
<meta http-equiv="X-UA-compatible" content="IE=edge,chrome=1" />
<meta name="viewpoint" content="width=device-width,initial-scale=1" />
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
	<input class="hidden" value="${swiftId}" name="swiftId" id="swiftId" type="text" />
	<div class="se-heading">
		<div class="pull-left">
			<h5 class="">吞吐量</h5>
		</div>
		<div class="pull-right">
			<input class="hidden" value="2" name="strategy" id="strategy" type="text" />
			<span class="monitor-date-block">
				<ul class="nav nav-pills">
					<li class="time-block active" value="2">
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
			<div id="chart-container" class="chart-container">							
			</div>
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
seajs.use("${ctx}/static/page-js/cloudswift/monitor/throughput/main");
// self define
</script>
</html>