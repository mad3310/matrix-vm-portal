<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="page-content-area">
	<div class="page-header">
		<h1>
			Dashboard
			<small>
				<i class="ace-icon fa fa-angle-double-right"></i>
				概览 
			</small>
		</h1>
	</div><!-- /.page-header -->
	
	<div class="row">
		<div class="col-xs-12">
			<div class="row">
				<div class="space-6"></div>
				<div class="col-sm-7 infobox-container">
					<div class="infobox infobox-green" onmouseover="this.style.cursor='pointer'" onclick="document.location='${ctx}/list/db';">
						<div class="infobox-icon">
							<i class="ace-icon fa fa-database"></i>
						</div>
						<div class="infobox-data">
							<span class="infobox-data-number" id="dbSum">32</span>
							<div class="infobox-content">数据库数</div>
						</div>
					</div>
					<div class="infobox infobox-green"  onmouseover="this.style.cursor='pointer'" onclick="document.location='${ctx}/list/dbUser';">
						<div class="infobox-icon">
							<i class="ace-icon fa fa-users"></i>
						</div>
						<div class="infobox-data">
							<span class="infobox-data-number" id="dbUserSum">32</span>
							<div class="infobox-content">数据库用户数</div>
						</div>
					</div>
					<div class="infobox infobox-green"  onmouseover="this.style.cursor='pointer'" onclick="document.location='${ctx}/list/mcluster';">
						<div class="infobox-icon">
							<i class="ace-icon fa fa-cubes"></i>
						</div>
						<div class="infobox-data">
							<span class="infobox-data-number" id="mclusterSum">32</span>
							<div class="infobox-content">container集群数</div>
						</div>
					</div>	
					<div class="space-6"></div>
					<div class="infobox infobox-blue"  onmouseover="this.style.cursor='pointer'" onclick="document.location='${ctx}/list/hcluster';">
						<div class="infobox-icon">
							<i class="ace-icon fa fa-cubes"></i>
						</div>
						<div class="infobox-data">
							<span class="infobox-data-number" id="hclusterSum">32</span>
							<div class="infobox-content">物理机集群数</div>
						</div>
					</div>
					<div class="infobox infobox-blue"  onmouseover="this.style.cursor='pointer'">
						<div class="infobox-icon">
							<i class="ace-icon fa fa-cube"></i>
						</div>
						<div class="infobox-data">
							<span class="infobox-data-number" id="hostSum">32</span>
							<div class="infobox-content">物理机节点数</div>
						</div>
					</div>
					<div class="space-6"></div>
					<div class="infobox infobox-orange"  onmouseover="this.style.cursor='pointer'" onclick="document.location='${ctx}/list/db';">
						<div class="infobox-icon">
							<i class="ace-icon fa fa-database"></i>
						</div>
						<div class="infobox-data">
							<span class="infobox-data-number" id="unauditeDbSum">32</span>
							<div class="infobox-content">待审核数据库</div>
						</div>
					</div>
	
					<div class="infobox infobox-orange"  onmouseover="this.style.cursor='pointer'" onclick="document.location='${ctx}/list/dbUser';">
						<div class="infobox-icon">
							<i class="ace-icon fa fa-user"></i>
						</div>
						<div class="infobox-data">
							<span class="infobox-data-number" id="unauditeDbUserSum">32</span>
							<div class="infobox-content">待审核数据库用户</div>
						</div>
					</div>

				</div>
	
				<div class="vspace-12-sm"></div>
	
				<div class="col-sm-4">
					<div class="widget-box">
						<div class="widget-header widget-header-flat widget-header-small">
							<h5 class="widget-title">
								container集群监控图
							</h5>
							<div class="widget-toolbar no-border">
								<i class="ace-icon fa fa-refresh icon-on-right bigger-110"></i>
							</div>
						</div>
						<div class="widget-body">
							<div class="widget-main">
								<div id="container" style="height: 400px"></div>	
								<div class="hr hr8 hr-double"></div>
	
								<div class="clearfix">
									<div class="grid4">
										<span class="grey">
											<i class="ace-icon fa fa-thumbs-o-up fa-2x green"></i>
											&nbsp; 健康
										</span>
										<h4 class="bigger pull-right">1,255</h4>
									</div>
	
									<div class="grid4">
										<span class="grey">
											<i class="ace-icon fa fa-warning fa-2x orange"></i>
											&nbsp; 一般
										</span>
										<h4 class="bigger pull-right">941</h4>
									</div>
									<div class="grid4">
										<span class="grey">
											<i class="ace-icon fa  fa-bolt fa-2x red"></i>
											&nbsp; 危险
										</span>
										<h4 class="bigger pull-right">100</h4>
									</div>
									<div class="grid4">
										<span class="grey">
											<i class="ace-icon fa fa-wrench fa-2x red"></i>
											&nbsp; 异常
										</span>
										<h4 class="bigger pull-right">50</h4>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<script src="${ctx}/static/scripts/highcharts/highcharts.js"></script>
<script src="${ctx}/static/scripts/highcharts/highcharts-3d.js"></script>

<script type="text/javascript">
$(function () {
	pieChartData=[
	                ['健康', 1],
	                ['一般', 2],
	                ['危险', 3],
	                ['异常', 4],
	            ];
	initPieChart(pieChartData);
	getMclusterData();
	getOverview();
});

function initPieChart(data){
    $('#container').highcharts({
        chart: {
            type: 'pie',
            options3d: {
                enabled: true,
                alpha: 45,
                beta: 0
            }
        },
        colors:[
        	'green',
        	'#FDC43E',
        	'#D15A06',
        	'red'
        ],
        
        title: {
            text: '当前状态'
        },
        tooltip: {
            pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
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
            }
        },
        credits:{
        	enabled: false
        },
        series: [{
            type: 'pie',
            name: 'container集群',
            data: data
        }]
    });
}


/* updateMclusterChart(){
	
}
 */
function checkMclusterStatus(vips){
	var nothing=0,normal=0,serious=0,crash=0;
	var pieChartData = {};
	for (var i = 0; vips[i] != null; i++){
		/*  $.ajax({ 
			type : "get",
			async:false,
			url : "${ctx}/monitor/"+vips[i]+"/mcluster/status",
			dataType : "json", 
			contentType : "application/json; charset=utf-8",
			success : function(data) {
				if(data.result == 0){
					crash++;
				}else{
					var status = data.data[0].status;
					if(status==0){
						nothing++;
					}else if(status==13){
						normal++;
					}else if(status==14){
						serious++;
					}else{
						crash++;
					}
				}
			}
		}); */
		pieChartData=[
		                ['健康', nothing],
		                ['一般', normal],
		                ['危险', serious],
		                ['异常', crash],
		            ]; 
		/* initPieChart(pieChartData); */
	}
	
		console.log(pieChartData);
				/* updateMclusterChart(); */
}

function getMclusterData(){
	var vips = {};
	$.ajax({ 
		type : "get",
		url : "${ctx}/monitor/mcluster/list",
		contentType : "application/json; charset=utf-8",
		success : function(data) {
			error(data);
			var array = data.data;
			
			for (var i = 0, len = array.length; i < len; i++) {
				vips[i] = array[i].ipAddr;
			}
			checkMclusterStatus(vips);
		}
	});
}

function getOverview(){
	$.ajax({
		type : "get",
		url : "${ctx}/dashboard/statistics",
		contentType : "application/json; charset=utf-8",
		success : function(data) {
			var view = data.data;
			$('#dbSum').html(view.db);
			$('#dbUserSum').html(view.db);
			$('#mclusterSum').html(view.mcluster);
			$('#unauditeDbSum').html(view.dbUserAudit);
			$('#unauditeDbUserSum').html(view.db);
			$('#hclusterSum').html(view.hcluster);
			$('#hostSum').html(view.db);
		}
	});
}
</script>
