<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<link rel="stylesheet" href="${ctx}/static/styles/ui-css/style.css"/>
<div class="page-content-area">
	<div class="page-header">
		<h1>
			Dashboard
		</h1>
	</div>
	
	<div class="row">
		<div class="col-md-6 infobox-container mt10">
			<div class="col-md-12 col-xs-12">
				<div class=" logo-label mt10">
					<div class="width-100  center padding-tb-5">
						<a class="dash-a"><h4><b>数据库概览</b></h4></a>
					</div>
				</div>
			</div>
			<div id="statistics" class="col-md-12 col-xs-12 mt10">
				<div class="infobox infobox-green">
					<div class="infobox-icon">
						<i class="ace-icon fa fa-cloud"></i>
					</div>
		
					<div class="infobox-data">
						<span id="dbSum" class="infobox-data-number">1</span>
						<div class="infobox-content">我的数据库</div>
					</div>
				</div>
				<div class="infobox infobox-blue">
					<div class="infobox-icon">
						<i class="ace-icon fa fa-cubes"></i>
					</div>
		
					<div class="infobox-data">
						<span id="dbAbleSum" class="infobox-data-number">4</span>
						<div class="infobox-content">可创建数据库</div>
					</div>
				</div>
				<div class="infobox infobox-blue2">
					<div class="infobox-icon">
						<i class="ace-icon fa fa-cogs"></i>
					</div>
					<div class="infobox-data">
						<div id="dbUserSum" class="infobox-data-number">5</div>
						<div class="infobox-content">数据库用户</div>
					</div>
				</div>
			</div>
		</div>
		<div class="col-md-6 infobox-container mt10">
			<div class="col-md-12 col-xs-12">
				<div class=" logo-label mt10">
					<div class="width-100  center padding-tb-5">
						<a class="dash-a"><h4><b>数据库存储概况图</b></h4></a>
					</div>
				</div>
				<div class="col-md-12 col-sm-12 col-xs-12" id="con-container">
				</div>
			</div>
		</div>
		<div class="col-md-6 infobox-container mt10">
			<div class="col-md-12 col-xs-12">
				<div class=" logo-label mt10">
					<div class="width-100  center padding-tb-5">
						<a class="dash-a"><h4><b>数据库概况图</b></h4></a>
					</div>
				</div>
				<div id="column-container" class="col-md-12 col-xs-12 mt10"></div>
			</div>
		</div>
	</div>
</div>
<script src="${ctx}/static/scripts/highcharts/highcharts.js"></script>
<script src="${ctx}/static/scripts/highcharts/highcharts-3d.js"></script>

<script type="text/javascript">
$(function () {
	demoData();
	getDbStatistics();
	initBarChart();
});

function setBarChartData(chart){
	var dbName=[],storage=[{data: []}];
	$.ajax({
		type : "get",
		url : "${ctx}/dashboard/monitor/db/storage",
		contentType : "application/json; charset=utf-8",
		success : function(data) {
			if(error(data)) return;
			$.each(data.data, function(key, val) {
				dbName.push(key);
				storage[0].data.push(val);
			});
			chart.xAxis[0].setCategories(dbName,false);
			chart.addSeries(storage[0],false);
			chart.redraw();
		}
	});
}

function initBarChart(){
	$('#con-container').highcharts({
		chart: {
            type: 'bar'
        },
        title: {
            text: '数据库存储使用'
        },
        legend: {
            enabled: false
        },
        yAxis: {
            min: 0,
            max:100,
            
            title: {
                text: '使用百分比'
            }
        },
        xAxis: {
        	tickWidth: 10,
        },
        tooltip: {
            pointFormat: '<span style="color:{series.color}"><b>{point.y}%</b><br>',
            shared: true
        },
        plotOptions: {
            column: {
                stacking: 'percent'
            },
            series:{
            	pointWidth:33
            }
        },
        credits:{
        	enabled: false
        }
    });
	setBarChartData($('#con-container').highcharts());
}

function getDbStatistics(){
	$.ajax({
		type : "get",
		url : "${ctx}/dashboard/statistics",
		contentType : "application/json; charset=utf-8",
		success : function(data) {
			if(error(data)) return;
			var view = data.data;
			$('#dbSum').html(view.db);
			$('#dbAbleSum').html(view.dbFree);
			$('#dbUserSum').html(view.dbUser);
		}
	});
}

function demoData(){
	var colors = Highcharts.getOptions().colors,
    categories = ['db1', 'db2', 'db3', 'db4', 'db5'],
    name = 'Browser brands',
    data = [{
            y: 55.11,
            color: colors[0],
            drilldown: {
                name: 'db1',
                categories: ['db1-admin', 'db1-user1', 'db1-user2', 'db1-user3'],
                data: [10.85, 7.35, 33.06, 2.81],
                color: colors[0]
            }
        }, {
            y: 21.63,
            color: colors[1],
            drilldown: {
                name: 'db2',
                categories: ['db2', 'db2', 'db2', 'db2', 'db2'],
                data: [0.20, 0.83, 1.58, 13.12, 5.43],
                color: colors[1]
            }
        }, {
            y: 11.94,
            color: colors[2],
            drilldown: {
                name: 'db3',
                categories: ['db3', 'db3', 'db3', 'db3', 'db3',
                    'db3', 'db3', 'db3'],
                data: [0.12, 0.19, 0.12, 0.36, 0.32, 9.91, 0.50, 0.22],
                color: colors[2]
            }
        }, {
            y: 7.15,
            color: colors[3],
            drilldown: {
                name: 'db4',
                categories: ['db4', 'db4', 'db4', 'db4', 'db4',
                    'db4', 'db4'],
                data: [4.55, 1.42, 0.23, 0.21, 0.20, 0.19, 0.14],
                color: colors[3]
            }
        }, {
            y: 2.14,
            color: colors[4],
            drilldown: {
                name: 'db5',
                categories: ['db5', 'db5', 'db5'],
                data: [ 0.12, 0.37, 1.65],
                color: colors[4]
            }
        }];


// Build the data arrays
var browserData = [];
var versionsData = [];
for (var i = 0; i < data.length; i++) {

    // add browser data
    browserData.push({
        name: categories[i],
        y: data[i].y,
        color: data[i].color
    });

    // add version data
    for (var j = 0; j < data[i].drilldown.data.length; j++) {
        var brightness = 0.2 - (j / data[i].drilldown.data.length) / 5 ;
        versionsData.push({
            name: data[i].drilldown.categories[j],
            y: data[i].drilldown.data[j],
            color: Highcharts.Color(data[i].color).brighten(brightness).get()
        });
    }
}

// Create the chart
$('#column-container').highcharts({
    chart: {
        type: 'pie'
    },
    title: {
        text: '数据库使用比例'
    },
    yAxis: {
        title: {
            text: 'Total percent market share'
        }
    },
    plotOptions: {
        pie: {
            shadow: false,
            center: ['50%', '50%']
        }
    },
    tooltip: {
	    valueSuffix: '%'
    },
    series: [{
        name: 'Browsers',
        data: browserData,
        size: '60%',
        dataLabels: {
            formatter: function() {
                return this.y > 5 ? this.point.name : null;
            },
            color: 'white',
            distance: -30
        }
    }, {
        name: 'Versions',
        data: versionsData,
        size: '80%',
        innerSize: '60%',
        dataLabels: {
            formatter: function() {
                // display only if larger than 1
                return this.y > 1 ? '<b>'+ this.point.name +':</b> '+ this.y +'%'  : null;
            }
        }
    }]
});

}

</script>
