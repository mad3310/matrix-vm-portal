/**
 * Created by yaokuo on 2014/12/12.
 */
define(function(require){
	var $ = require('jquery');
	require('highcharts')($);
    var common = require('../../common');
    var cn = new common();
    
    /*初始化监控数据*/
    /*加载数据demo --start*/
	var cType = "area";
	var cTitle = " ";
	var cSubtitle = " ";
	var cxAxis = ['1750', '1800', '1850', '1900', '1950', '1999', '2050'];
	var cyAxis = '';
	var seriesData = [{
		type: cType,
		name: "cpu",
		pointInterval: 24 * 3600 * 1000,
		pointStart: Date.UTC(2006, 0, 01),
		data: [0.9999, 0.8445, 0.8444, 0.8451,    0.8418, 0.8264,    0.8258, 0.8232,    0.8233, 0.8258, 0.8283, 0.8278, 0.8256, 0.8292,    0.8239, 0.8239,    0.8245, 0.8265,    0.8261, 0.8269, 0.8273, 0.8244, 0.8244, 0.8172,    0.8139, 0.8146, 0.8164, 0.82,    0.8269, 0.8269 ]
		},
		{
			type: cType,
			name:"memeory",
			pointInterval: 24 * 3600 * 1000,
			pointStart: Date.UTC(2006, 0, 01),
			data:[0.71,0.55,0.06,0.77,0.68,0.6,0.27,0.07,0.89,0.75,0.98,0.87,0.74,0.96,0.64,0.55,0.66,0.94,0.2,0.34,0.28,0.63,0.38,0.46,0.31,0.08,0.88,0.3,0.95,0.68]

		},
		{
			type: cType,
			name:"memeory",
			pointInterval: 24 * 3600 * 1000,
			pointStart: Date.UTC(2006, 0, 01),
			data:[0.27,0.37,0.04,0.23,0.38,0.09,0.22,0.43,0.26,0.42,0.09,0.19,0.17,0.13,0.07,0.49,0.02,0.48,0.19,0.26,0.42,0.09,0.19,0.17,0.13,0.07,0.49,0.02,0.48,0.19]

		}];

	cn.Charts(cType,cTitle,cSubtitle,cxAxis,cyAxis,seriesData);
	/*加载数据demo --end*/

    var dataHandler = require('./dataHandler');
    

    /*function dbMonitorChart(){
    	$.ajax({
    		type : "get",
    		url : "/monitor/index/23",
    		dataType : "json", 
    		contentType : "application/json; charset=utf-8",
    		success:function(data){
    	 		if(error(data)) return;
    	 		initCharts(data.data[0]);
    		}
    	});
    }
    
    function initCharts(data){
    	var viewDemo = $('#monitor-view-demo').clone().removeClass('hide').attr("id","23-monitor-view").appendTo($('#monitor-view'));
    	var div = $(viewDemo).find('[name="data-chart"]');
    	$(div).attr("id","23");
    	//init div to chart
    	initChart(div,data.titleText,data.yAxisText,data.tooltipSuffix);
    	var chart = $(div).highcharts();
    	setChartData(chart);
    }
    
    function setChartData(chart){
    	var dbId= $('#dbId').val();
//    	var strategy= $('#strategy').val();
    	$.ajax({
    		type : "get",
    		url : "/monitor/"+dbId+"/23/1",//+strategy,
    		dataType : "json", 
    		contentType : "application/json; charset=utf-8",
    		success:function(data){
    	 		if(error(data)) return;
    	 		var ydata = data.data;
    	 		for(var i=chart.series.length-1;i>=0;i--){
    	 			chart.series[i].remove(false);
     			}
    	 		for(var i=0;i<ydata.length;i++){
    	 			chart.addSeries(ydata[i],false);
     			}
    	 		chart.redraw();
    		}
    	});
    }

    function updateDbMonitorChart(strategy){
    	if(strategy){
    		$('#strategy').val(strategy);
    	}
    	var chart = $('#23').highcharts();
    	setChartData(chart);
    }
    
    function initChart(obj,title,ytitle,unit){
        $(obj).highcharts({
        	chart:{
        		zoomType: 'x'
        	},
        	colors: ['#4572A7', '#AA4643', '#80699B', '#89A54E'],
            title: {
                text: title
            },
            xAxis: {
    			type: 'datetime',
    			tickPixelInterval:30,
                labels:{
                	rotation:-90,
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
                spline: {  
                    lineWidth: 1.5,  
                    fillOpacity: 0.1,  
                     marker: {  
                        enabled: false,  
                        states: {  
                            hover: {  
                                enabled: true,  
                                radius: 2  
                            }  
                        }  
                    },  
                    shadow: false  
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
                valueSuffix: "/s"
            }
        });
    } */
    
});
