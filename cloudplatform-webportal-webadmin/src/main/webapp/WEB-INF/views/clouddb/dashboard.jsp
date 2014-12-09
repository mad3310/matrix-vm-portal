<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<link rel="stylesheet" href="${ctx}/static/styles/ui-css/style.css"/>
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
				<div class="col-sm-6 infobox-container">
					<div class="col-md-12 col-xs-12">
						<div class=" logo-label mt10">
							<div class="width-100  center padding-tb-5">
								<a class="dash-a"><h4><b>集群概览</b></h4></a>
							</div>
						</div>
					</div>
					<div id="statistics" class="col-md-12 col-xs-12 mt10">
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
	
				<div class="col-sm-6">
					<div class="col-md-12 col-xs-12">
						<div class=" logo-label mt10">
							<div class="width-100  center padding-tb-5">
								<a class="dash-a"><h4><b>cluster状态</b></h4></a>
							</div>
						</div>
					</div>
					<div id="statistics" class="col-md-12 col-xs-12 mt10">
						<div id="pie-chart-container" style="height: 300px"></div>	
						<div class="hr hr8 hr-double"></div>

						<div class="clearfix">
							<div class="grid5" onmouseover="this.style.cursor='pointer'" onclick="document.location='${ctx}/list/mcluster/monitor';">
								<span class="grey">
									<i class="ace-icon fa fa-thumbs-o-up fa-2x green"></i>
									&nbsp; 正常<br/>
								</span>
								<h4 id="nothing" class="bigger pull-right">0</h4>
							</div>

							<div class="grid5" onmouseover="this.style.cursor='pointer'" onclick="document.location='${ctx}/list/mcluster/monitor';">
								<span class="grey">
									<i class="ace-icon fa fa-warning fa-2x orange"></i>
									&nbsp; 危险<br/>
								</span>
								<h4 id="general" class="bigger pull-right">0</h4>
							</div>
							<div class="grid5" onmouseover="this.style.cursor='pointer'" onclick="document.location='${ctx}/list/mcluster/monitor';">
								<span class="grey">
									<i class="ace-icon fa  fa-bolt fa-2x red"></i>
									&nbsp; 严重危险<br/>
								</span>
								<h4 id="serious" class="bigger pull-right">0</h4>
							</div>
							<div class="grid5" onmouseover="this.style.cursor='pointer'" onclick="document.location='${ctx}/list/mcluster/monitor';">
								<span class="grey">
									<i class="ace-icon fa fa-wrench fa-2x red"></i>
									&nbsp; 集群不可用<br/>
								</span>
								<h4 id="crash" class="bigger pull-right">0</h4>
							</div>
							<div class="grid5" onmouseover="this.style.cursor='pointer'" onclick="document.location='${ctx}/list/mcluster/monitor';">
								<span class="grey">
									<i class="ace-icon fa fa-wrench fa-2x red"></i>
									&nbsp; 获取数据超时<br/>
								</span>
								<h4 id="timeout" class="bigger pull-right">0</h4>
							</div>
						</div>
					</div>
				</div>
				
				
			</div>
<%-- 			<div class="hr hr32 hr-dotted"></div>
			<div class="row">
				<div class="col-sm-6">
					<div class="col-md-12 col-xs-12">
						<div class=" logo-label mt10">
							<div class="width-100  center padding-tb-5">
								<a class="dash-a"><h4><b>node状态</b></h4></a>
							</div>
						</div>
					</div>
					<div id="statistics" class="col-md-12 col-xs-12 mt10">
						<div id="pie-chart-container" style="height: 300px"></div>	
						<div class="hr hr8 hr-double"></div>

						<div class="clearfix">
							<div class="grid5" onmouseover="this.style.cursor='pointer'" onclick="document.location='${ctx}/list/mcluster/monitor';">
								<span class="grey">
									<i class="ace-icon fa fa-thumbs-o-up fa-2x green"></i>
									&nbsp; 正常<br/>
								</span>
								<h4 id="nothing" class="bigger pull-right">0</h4>
							</div>

							<div class="grid5" onmouseover="this.style.cursor='pointer'" onclick="document.location='${ctx}/list/mcluster/monitor';">
								<span class="grey">
									<i class="ace-icon fa fa-warning fa-2x orange"></i>
									&nbsp; 危险<br/>
								</span>
								<h4 id="general" class="bigger pull-right">0</h4>
							</div>
							<div class="grid5" onmouseover="this.style.cursor='pointer'" onclick="document.location='${ctx}/list/mcluster/monitor';">
								<span class="grey">
									<i class="ace-icon fa  fa-bolt fa-2x red"></i>
									&nbsp; 严重危险<br/>
								</span>
								<h4 id="serious" class="bigger pull-right">0</h4>
							</div>
							<div class="grid5" onmouseover="this.style.cursor='pointer'" onclick="document.location='${ctx}/list/mcluster/monitor';">
								<span class="grey">
									<i class="ace-icon fa fa-wrench fa-2x red"></i>
									&nbsp; 集群不可用<br/>
								</span>
								<h4 id="crash" class="bigger pull-right">0</h4>
							</div>
							<div class="grid5" onmouseover="this.style.cursor='pointer'" onclick="document.location='${ctx}/list/mcluster/monitor';">
								<span class="grey">
									<i class="ace-icon fa fa-wrench fa-2x red"></i>
									&nbsp; 获取数据超时<br/>
								</span>
								<h4 id="timeout" class="bigger pull-right">0</h4>
							</div>
						</div>
					</div>
				</div>
				<div class="col-sm-6">
					<div class="col-md-12 col-xs-12">
						<div class=" logo-label mt10">
							<div class="width-100  center padding-tb-5">
								<a class="dash-a"><h4><b>db状态</b></h4></a>
							</div>
						</div>
					</div>
					<div id="statistics" class="col-md-12 col-xs-12 mt10">
						<div id="pie-chart-container" style="height: 300px"></div>	
						<div class="hr hr8 hr-double"></div>

						<div class="clearfix">
							<div class="grid5" onmouseover="this.style.cursor='pointer'" onclick="document.location='${ctx}/list/mcluster/monitor';">
								<span class="grey">
									<i class="ace-icon fa fa-thumbs-o-up fa-2x green"></i>
									&nbsp; 正常<br/>
								</span>
								<h4 id="nothing" class="bigger pull-right">0</h4>
							</div>

							<div class="grid5" onmouseover="this.style.cursor='pointer'" onclick="document.location='${ctx}/list/mcluster/monitor';">
								<span class="grey">
									<i class="ace-icon fa fa-warning fa-2x orange"></i>
									&nbsp; 危险<br/>
								</span>
								<h4 id="general" class="bigger pull-right">0</h4>
							</div>
							<div class="grid5" onmouseover="this.style.cursor='pointer'" onclick="document.location='${ctx}/list/mcluster/monitor';">
								<span class="grey">
									<i class="ace-icon fa  fa-bolt fa-2x red"></i>
									&nbsp; 严重危险<br/>
								</span>
								<h4 id="serious" class="bigger pull-right">0</h4>
							</div>
							<div class="grid5" onmouseover="this.style.cursor='pointer'" onclick="document.location='${ctx}/list/mcluster/monitor';">
								<span class="grey">
									<i class="ace-icon fa fa-wrench fa-2x red"></i>
									&nbsp; 集群不可用<br/>
								</span>
								<h4 id="crash" class="bigger pull-right">0</h4>
							</div>
							<div class="grid5" onmouseover="this.style.cursor='pointer'" onclick="document.location='${ctx}/list/mcluster/monitor';">
								<span class="grey">
									<i class="ace-icon fa fa-wrench fa-2x red"></i>
									&nbsp; 获取数据超时<br/>
								</span>
								<h4 id="timeout" class="bigger pull-right">0</h4>
							</div>
						</div>
					</div>
				</div>
			</div> --%>
		</div>
	</div>
</div>
<script src="${ctx}/static/scripts/highcharts/highcharts.js"></script>
<script src="${ctx}/static/scripts/highcharts/highcharts-3d.js"></script>

<script type="text/javascript">
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
		url : "${ctx}/dashboard/monitor/mcluster",
		dataType : "json", 
		contentType : "application/json; charset=utf-8",
		success : function(data) {
			if(error(data)) return;
			var status = data.data;
			var pieChartData=[{
				data: []
	           }];
			if(status.nothing != 0){
				pieChartData[0].data.push({name:'正常',color:'green',y:status.nothing,url:'${ctx}/list/mcluster/monitor'});
				$('#nothing').html(status.nothing);
			};
			if(status.general != 0){
				pieChartData[0].data.push({name:'危险',color:'#FDC43E',y:status.general,url:'${ctx}/list/mcluster/monitor'});
				$('#general').html(status.general);
			};
			if(status.serious != 0){
				pieChartData[0].data.push({name:'严重危险',color:'#D15A06',y:status.serious,url:'${ctx}/list/mcluster/monitor'});
				$('#serious').html(status.serious);
			};
			if(status.crash != 0){
				pieChartData[0].data.push({name:'宕机',color:'red',y:status.crash,url:'${ctx}/list/mcluster/monitor'});
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
		url : "${ctx}/dashboard/statistics",
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
</script>
