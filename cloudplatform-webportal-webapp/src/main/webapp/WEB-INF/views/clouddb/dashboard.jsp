<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<link rel="stylesheet" href="${ctx}/static/styles/ui-css/style.css"/>
<div class="page-content-area">
	<div class="page-header">
		<h3>
			Dashboard
		</h3>
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
	getDbStatistics();
	initBarChart();
	initPieChart();
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

function initBarChart(chart){
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

function initPieChart(chart){
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
	    credits:{
        	enabled: false
        },
	    series: [{
	        name: 'total',
	        size: '60%',
	        dataLabels: {
	            color: 'white',
	            distance: -30
	        }
	    }, {
	        name: 'detail',
	        size: '80%',
	        innerSize: '60%',
	        dataLabels: {
	            formatter: function() {
	                return this.y > 1 ? '<b>'+ this.point.name +':</b> '+ this.y : null;
	            }
	        }
	    }]
	});
	setPieChartData($('#column-container').highcharts());
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

function setPieChartData(chart){
    var connectTotal = [];
    var connectDetail = [];
	$.ajax({
		type : "get",
		url : "${ctx}/dashboard/monitor/db/connect",
		contentType : "application/json; charset=utf-8",
		success : function(data) {
			if(error(data)) return;
			var view = data.data;
			var colors = ['#6fb3e0', '#8bbc21', '#1aadce', '#492970', '#f28f43', '#77a1e5', '#c42525', '#a6c96a','#0d233a', '#910000',];
			for(var i=0,len=view.length;i<len;i++){
				connectTotal.push({
					name: view[i].dbName,
		            y: view[i].total,
		            color: colors[i],
				});
				for (var j = 0,len2= view[i].value.length; j < len2; j++) {
		            var brightness = 0.2 - (j / view[i].value.length) / 5 ;
		            connectDetail.push({
		                name: view[i].value[j].detailName,
		                y: view[i].value[j].detailValue ,
		                color: Highcharts.Color(colors[i]).brighten(brightness).get()
		            });
		        }
			}
			chart.series[0].setData(connectTotal);
			chart.series[1].setData(connectDetail);
		}
	});
}

</script>
