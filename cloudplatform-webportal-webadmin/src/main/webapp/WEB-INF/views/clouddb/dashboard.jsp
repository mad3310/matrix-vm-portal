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
							<span class="infobox-data-number" id="dbSum">0</span>
							<div class="infobox-content">数据库数</div>
						</div>
					</div>
					<div class="infobox infobox-green"  onmouseover="this.style.cursor='pointer'" onclick="document.location='${ctx}/list/dbUser';">
						<div class="infobox-icon">
							<i class="ace-icon fa fa-users"></i>
						</div>
						<div class="infobox-data">
							<span class="infobox-data-number" id="dbUserSum">0</span>
							<div class="infobox-content">数据库用户数</div>
						</div>
					</div>
					<div class="infobox infobox-green"  onmouseover="this.style.cursor='pointer'" onclick="document.location='${ctx}/list/mcluster';">
						<div class="infobox-icon">
							<i class="ace-icon fa fa-cubes"></i>
						</div>
						<div class="infobox-data">
							<span class="infobox-data-number" id="mclusterSum">0</span>
							<div class="infobox-content">container集群数</div>
						</div>
					</div>	
					<div class="space-6"></div>
					<div class="infobox infobox-blue"  onmouseover="this.style.cursor='pointer'" onclick="document.location='${ctx}/list/hcluster';">
						<div class="infobox-icon">
							<i class="ace-icon fa fa-cubes"></i>
						</div>
						<div class="infobox-data">
							<span class="infobox-data-number" id="hclusterSum">0</span>
							<div class="infobox-content">物理机集群数</div>
						</div>
					</div>
					<div class="infobox infobox-blue"  onmouseover="this.style.cursor='pointer'">
						<div class="infobox-icon">
							<i class="ace-icon fa fa-cube"></i>
						</div>
						<div class="infobox-data">
							<span class="infobox-data-number" id="hostSum">0</span>
							<div class="infobox-content">物理机节点数</div>
						</div>
					</div>
					
					<div class="space-6"></div>
					<div class="infobox infobox-orange infobox-dark"  onmouseover="this.style.cursor='pointer'" onclick="document.location='${ctx}/list/db';">
						<div class="infobox-icon">
							<i class="ace-icon fa fa-database"></i>
						</div>
						<div class="infobox-data">
							<span class="infobox-data-number" id="unauditeDbSum">0</span>
							<div class="infobox-content">待审核数据库</div>
						</div>
					</div>
	
					<div class="infobox infobox-orange infobox-dark"  onmouseover="this.style.cursor='pointer'" onclick="document.location='${ctx}/list/dbUser';">
						<div class="infobox-icon">
							<i class="ace-icon fa fa-user"></i>
						</div>
						<div class="infobox-data">
							<span class="infobox-data-number" id="unauditeDbUserSum">0</span>
							<div class="infobox-content">待审核数据库用户</div>
						</div>
					</div>
					<div class="space-6"></div>
					<div class="infobox infobox-green"  onmouseover="this.style.cursor='pointer'" onclick="document.location='${ctx}/list/db';">
						<div class="infobox-icon">
							<i class="ace-icon fa fa-database"></i>
						</div>
						<div class="infobox-data">
							<span class="infobox-data-number" id="onCreateDbSum">0</span>
							<div class="infobox-content">正在创建数据库</div>
						</div>
					</div>
	
					<div class="infobox infobox-red"  onmouseover="this.style.cursor='pointer'" onclick="document.location='${ctx}/list/db';">
						<div class="infobox-icon">
							<i class="ace-icon fa fa-database"></i>
						</div>
						<div class="infobox-data">
							<span class="infobox-data-number" id="createFailDbSum">0</span>
							<div class="infobox-content">数据库创建失败数</div>
						</div>
					</div>
					<div class="infobox infobox-red"  onmouseover="this.style.cursor='pointer'" onclick="document.location='${ctx}/list/dbUser';">
						<div class="infobox-icon">
							<i class="ace-icon fa fa-user"></i>
						</div>
						<div class="infobox-data">
							<span class="infobox-data-number" id="createFailDbUserSum">0</span>
							<div class="infobox-content">数据库用户创建失败数</div>
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
											&nbsp; 正常
										</span>
										<h4 id="nothing" class="bigger pull-right">0</h4>
									</div>
	
									<div class="grid4">
										<span class="grey">
											<i class="ace-icon fa fa-warning fa-2x orange"></i>
											&nbsp; 一般
										</span>
										<h4 id="general" class="bigger pull-right">0</h4>
									</div>
									<div class="grid4">
										<span class="grey">
											<i class="ace-icon fa  fa-bolt fa-2x red"></i>
											&nbsp; 危险
										</span>
										<h4 id="serious" class="bigger pull-right">0</h4>
									</div>
									<div class="grid4">
										<span class="grey">
											<i class="ace-icon fa fa-wrench fa-2x red"></i>
											&nbsp; 宕机
										</span>
										<h4 id="crash" class="bigger pull-right">0</h4>
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
	getOverview();
	checkMclusterStatus();
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
            },
            series: {
                cursor: 'pointer',
                events: {
                    click: function(e) {
		                location.href = e.point.url;
					}
				}
            }
        },
        credits:{
        	enabled: false
        },
        series: data
    });
}


/* updateMclusterChart(){
	
}
 */
function checkMclusterStatus(){
	$.ajax({ 
		type : "get",
		url : "${ctx}/dashboard/monitor/mcluster",
		dataType : "json", 
		contentType : "application/json; charset=utf-8",
		success : function(data) {
			var status = data.data;
			var pieChartData=[{
				data: []
	           }];
			if(status.nothing != 0){
				pieChartData[0].data.push({name:'正常',y:status.nothing,url:'${ctx}/list/mcluster/monitor'});
				$('#nothing').html(status.nothing);
			};
			if(status.general != 0){
				pieChartData[0].data.push({name:'一般',y:status.general,url:'${ctx}/list/mcluster/monitor'});
				$('#general').html(status.general);
			};
			if(status.serious != 0){
				pieChartData[0].data.push({name:'危险',y:status.serious,url:'${ctx}/list/mcluster/monitor'});
				$('#serious').html(status.serious);
			};
			if(status.crash != 0){
				pieChartData[0].data.push({name:'宕机',y:status.crash,url:'${ctx}/list/mcluster/monitor'});
				$('#crash').html(status.crash);
			}
			initPieChart(pieChartData);
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
</script>
