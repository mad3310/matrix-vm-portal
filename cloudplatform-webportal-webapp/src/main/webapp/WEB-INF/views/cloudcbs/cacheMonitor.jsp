<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="zh">
<%@include file='main.jsp' %>
<style>
 .bk-select-arrow {position: absolute;left:200px;top: -35px;float: right;display: inline;width: 0;height: 0;border: solid 6px transparent;border-top-color: #fff;margin: 15px 0 0 10px;-webkit-transform-origin: 50% 20% 0;-moz-transform-origin: 50% 20% 0;-ms-transform-origin: 50% 20% 0;-o-transform-origin: 50% 20% 0;-webkit-transform: rotate(0deg);-moz-transform: rotate(0deg);-ms-transform: rotate(0deg);-o-transform: rotate(0deg);
}
.btn-search{vertical-align:top;}
.chart{padding:10px;}
</style>
<body>
	<div class="se-heading" style="padding:10px;">
		<!-- <div>
			<h5 class="">缓存实例监控</h5>
		</div> -->
		<div class="row main-header">
			<!-- main-content-header begin -->
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
// self define
var _chart=$('.chart');
var unit=[{
            name: 'John',
            data: [3, 4, 3, 5, 4, 10, 12]
        }, {
            name: 'Jane',
            data: [1, 3, 4, 3, 3, 5, 4]
        }];
InitChart(_chart,'实例使用容量','容量（KB）',unit);
	function  InitChart(obj,title,ytitle,unit){
        $(obj).highcharts({
            chart: {
                type: 'areaspline',
                zoomType: 'x',
                spacingRight: 20
            },
            colors: ['#ff66cc','#66ff66','#66ffff','#FFBB33'],
            title: {
                text: title
            },
            legend :{
                borderColor: '#000000',
                backgroundColor: '#f9f9f9',
                symbolRadius: '2px',
                borderRadius: '5px',
                itemHoverStyle: {
                    Color: '#000000'
                }
            },
            xAxis: {
                type: 'datetime',
                tickPixelInterval:150,
                labels:{
                    rotation:0,
                    align:'right'
                },
                dateTimeLabelFormats:{
                    millisecond: '%H:%M:%S.%L',
                    second: '%H:%M:%S',
                    minute: '%H:%M',
                    hour: '%H:%M',
                    day: '%e. %b',
                    week: '%e. %b',
                    month: '%b \'%y',
                    year: '%Y'
                }
            },
            plotOptions: {
            	lineWidth: 0.1,  
                fillOpacity: 0.1,
                areaspline: {
                    marker: {
                        enabled: false,
                        symbol: 'circle',
                        radius: 2,
                        states: {
                            hover: {
                                enabled: true
                            }
                        }
                    }
                },
                series:{
                	lineWidth: 0.5,  
                    fillOpacity: 0.5,
                    states:{
                        hover:{
                            lineWidthPlus:0
                        }
                	}
            	}
            },
            credits:{
                enabled: false
            },
            yAxis: {
                title: {
                    text: ytitle
                }
            },
            tooltip: {
                valueSuffix: "/s",
                shared: true
            },
            series: unit
        });
    }
</script>
</html>