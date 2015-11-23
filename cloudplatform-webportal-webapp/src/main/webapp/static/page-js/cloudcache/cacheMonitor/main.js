define(function(require,exports,module){
	var $=require('jquery');
    require('highcharts')($);
    var Common=require('../../common');var cn=new Common();
    cn.divselect();
    $(".monitor-date-block").find("li").click(function () {
        var $brothers = $(".monitor-date-block").find("li");
        $brothers.removeClass("active");
        $(this).addClass("active");
        $("#strategy").val($(this).val());
        //UpdateChartData();
    })
    function UpdateChartData(){
        var url = "/monitor/"+$("#cacheId").val()+"/23/"+$("#strategy").val()+"/true";
        cn.GetData(url,monitor.SetChartData); 
    }
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
});