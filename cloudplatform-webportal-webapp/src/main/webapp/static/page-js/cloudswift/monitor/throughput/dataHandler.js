/**
 * Created by yaokuo on 2014/12/14.
 */
define(function(require,exports,module){
    var $ = require('jquery');

    var DataHandler = function(){
    };
    module.exports = DataHandler;

    var monitor = new DataHandler();
    DataHandler.prototype = {
        InitCharts:function (data){
            var div = $('#chart-container');
            
            InitChart(div,data.titleText,data.yAxisText,data.tooltipSuffix);
        },
        SetChartData:function(data){
            var swiftId= $('#swiftId').val();
            var chart = $("#chart-container").highcharts();
            var ydata = data.data;
            for(var i=chart.series.length-1;i>=0;i--){
                chart.series[i].remove(false);
            }
            for(var i=0;i<ydata.length;i++){
            	if(ydata[i].name != undefined && ydata[i].name != null && ydata[i].name == "throughput_put"){
            		ydata[i].name = "PUT请求吞吐量";
            	}else if(ydata[i].name != undefined && ydata[i].name != null && ydata[i].name == "throughput_head"){
            		ydata[i].name = "HEAD请求吞吐量";
            	}else if(ydata[i].name != undefined && ydata[i].name != null && ydata[i].name == "throughput_post"){
            		ydata[i].name = "POST请求吞吐量";
            	}else if(ydata[i].name != undefined && ydata[i].name != null && ydata[i].name == "throughput_delete"){
            		ydata[i].name = "DELETE请求吞吐量";
            	}else if(ydata[i].name != undefined && ydata[i].name != null && ydata[i].name == "throughput_get"){
            		ydata[i].name = "GET请求吞吐量";
            	}
                chart.addSeries(ydata[i],false);
            }
            chart.redraw();
        }
    }
    function  InitChart(obj,title,ytitle,unit){
        $(obj).highcharts({
            chart: {
                // type: 'areaspline',
                type:'line',
                zoomType: 'x',
                spacingRight: 20
            },
            colors: ['#ff66cc','#66ff66','#66ffff','#FFBB33','#C9C'],
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
                // areaspline: {
                //     marker: {
                //         enabled: false,
                //         symbol: 'circle',
                //         radius: 2,
                //         states: {
                //             hover: {
                //                 enabled: true
                //             }
                //         }
                //     }
                // },
                line: {
                    marker: {
                        enabled: false,
                        states: {
                            hover: {
                                enabled: true
                            }
                        }
                    }
                },
                series:{
                	// lineWidth: 0.5,
                    lineWidth:2,  
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
                valueSuffix: unit,
                shared: true
            }
        });
    }
});

