$(function () {
	getOverview();
	initPieChart();
	$('#nav-search').addClass("hidden");
});

function initPieChart(){
    $('#pie-chart-container').highcharts({
        chart: {
            type: 'pie',
            options3d: {
                enabled: true,
                alpha: 45,
                beta: 0
            }
        },
        title: {
            text: ''
        },
        tooltip: {
	        formatter: function() {
	            return '<b>'+ this.point.name +'</b>: '+ this.point.y ;
	        }
        },
        plotOptions: {
            pie: {
                allowPointSelect: true,
                cursor: 'pointer',
                depth: 35,
                dataLabels: {
                    enabled: true,
                    format: '{point.name}'
                }
            },
            series: {
                cursor: 'pointer'
               /*  events: {
                    click: function(e) {
		                location.href = e.point.url;
					}
				} */
            }
        },
        credits:{
        	enabled: false
        }
    });
    setPieChartData($('#pie-chart-container').highcharts());
}

function setPieChartData(chart){
	chart.showLoading();
	$.ajax({ 
		type : "get",
		url : "/dashboard/monitor/mcluster",
		dataType : "json", 
		contentType : "application/json; charset=utf-8",
		success : function(data) {
			if(error(data)) return;
			var status = data.data;
			var pieChartData=[{
				data: []
	           }];
			if(status.nothing != 0){
				pieChartData[0].data.push({name:'正常',color:'green',y:status.nothing,url:'/list/mcluster/monitor'});
				$('#nothing').html(status.nothing);
			};
			if(status.general != 0){
				pieChartData[0].data.push({name:'危险',color:'#FDC43E',y:status.general,url:'/list/mcluster/monitor'});
				$('#general').html(status.general);
			};
			if(status.serious != 0){
				pieChartData[0].data.push({name:'严重危险',color:'#D15A06',y:status.serious,url:'/list/mcluster/monitor'});
				$('#serious').html(status.serious);
			};
			if(status.crash != 0){
				pieChartData[0].data.push({name:'宕机',color:'red',y:status.crash,url:'/list/mcluster/monitor'});
				$('#crash').html(status.crash);
			}
			
			if(chart.series.length != 0){
				chart.series[0].remove(false);
			}
			chart.hideLoading();
			chart.addSeries(pieChartData[0],false);
			chart.redraw();
		}
	});
}


function updateMclusterChart(){
	setPieChartData($('#pie-chart-container').highcharts());
}

function getOverview(){
	$.ajax({
		type : "get",
		url : "/dashboard/statistics",
		contentType : "application/json; charset=utf-8",
		success : function(data) {
			if(error(data)) return;
			var view = data.data;
			$('#dbSum').html(view.db);
			$('#dbUserSum').html(view.dbUser);
			$('#mclusterSum').html(view.mcluster);
			$('#unauditeDbSum').html(view.dbAudit);
			$('#unauditeDbUserSum').html(view.dbUserAudit);
			$('#hclusterSum').html(view.hcluster);
			$('#hostSum').html(view.host);
			$('#onCreateDbSum').html(view.dbBuilding);
			$('#createFailDbSum').html(view.dbFaild);
			$('#createFailDbUserSum').html(view.dbUserFaild);
		}
	});
}
