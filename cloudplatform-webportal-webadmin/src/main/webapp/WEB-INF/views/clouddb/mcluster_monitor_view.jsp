<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="page-content-area">
	<div class="page-header">
		<h3> 
			container集群监控视图
		</h3>
	</div>
	<!-- /.page-header -->
	<div class="row">
		<div id="monitor-option" class="col-sm-12"> 
			<div class="clearfix form-actions">
				<form class="form-horizontal" role="form">
					<div class="form-group">
						<label class="col-sm-2 control-label no-padding-right text-info">Container集群</label>
						<div class="col-sm-4">
							<select id="mclusterOption" name="mclusterId" class="form-control">
							</select>
												
						</div>
					<!-- 	<label class="col-sm-1 control-label">监控点</label>
						<div class="col-sm-2">
							<select id="monitorPointOption" name="monitorPoint" class="form-control">
								<option value=""></option>
							</select>
						</div> -->					
						
						<label class="col-sm-2 control-label text-info">时间</label>
						<div class="col-sm-3">
							<select id="queryTime" name="queryTime" class="form-control">
								<option value="1">一小时</option>
								<option value="2">三小时</option>
								<option value="3">一天</option>
								<option value="4">一周</option>
							</select>
						</div>						
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label no-padding-right text-info" >监控点</label>
						<div class="col-sm-9">
							<select multiple="multiple" class="chosen-select form-control" name="monitorPoint" id="monitorPointOption" data-placeholder="空为显示所有.">
							</select>	
					 	</div>
					 	<div class="col-sm-1">
							<button id="refresh-data" type="button" onclick="refreshChartForSelect()" class="btn btn-sm btn-info">刷新</button>
						</div>	
					</div>						
				</form>			
			</div>
		<div id="monitor-view" class="row">
		</div>
		<!-- <div class="space-24"></div> -->
			<div id="monitor-view-demo" name="monitor-view" class="col-xs-12 col-sm-6 widget-container-col ui-sortable hide ui-sortable-disabled">
				<div class="widget-box transparent ui-sortable-handle">
					<div class="widget-header">
						<!-- <h4 class="widget-title lighter">监控图</h4> -->
						<div class="widget-toolbar">
							<a href="#" data-action="fullscreen" class="orange2" onclick="updateChartSize($(this))">
								<i class="ace-icon fa fa-expand"></i>
							</a>
							<a href="#" class="orange2" data-action="settings" onclick="changeDraggable($(this))">
								<input type="text" name="draggable" value="0" class="hidden" />
								<i class="ace-icon fa fa-thumb-tack" style="-webkit-transform:rotate(45deg);-moz-transform:rotate(45deg);-o-transform:rotate(45deg);"></i>
							</a>
						</div>
					</div>
					<div class="widget-body">
						<div class="widget-main">
							<div name="data-chart"></div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<script src="${ctx}/static/scripts/highcharts/highcharts.js"></script>
<%-- <script src="${ctx}/static/scripts/highcharts/themes/grid.js"></script> --%>
<script src="${ctx}/static/scripts/highcharts/themes/grid-light.js"></script>

<%-- <script src="${ctx}/static/scripts/highcharts/themes/dark-blue.js"></script> --%>
<script type="text/javascript">

function refreshChartForSelect(){
	var monitorPoint = $('#monitorPointOption').val();
	$('#monitor-view [name="monitor-view"]').each(function(){
		if (monitorPoint != null){
			for (var i = 0,len = monitorPoint.length; i < len ; i++){
				if($(this).attr('id') == (monitorPoint[i]+"-monitor-view")){
					$(this).removeClass('hide');
					var chart = $("#"+monitorPoint[i]).highcharts();
					setChartData(monitorPoint[i],chart);
					break
				}
				$(this).addClass('hide');
			}
		}else{
			$(this).removeClass('hide');
			monitorPointId=$(this).find('[name="data-chart"]').attr('id');
			var chart = $("#"+monitorPointId).highcharts();
			setChartData(monitorPointId,chart);
		}
	});
}

function queryMcluster(){
	$.ajax({
		type:"get",		
		url:"${ctx}/mcluster",
		dataType:"json",
		success:function(data){
			if(error(data)) return;
			var mclustersInfo = data.data;
			for(var i=0,len=mclustersInfo.length;i<len;i++){
				var option = $("<option value=\""+mclustersInfo[i].id+"\">"+mclustersInfo[i].mclusterName+"</option>");
				$("#mclusterOption").append(option);
			}
			queryMonitorPoint();
		}
	});	
}

function queryMonitorPoint(){
	$.ajax({
		type:"get",		
		url : "${ctx}/monitor/index",
		dataType:"json",
		success:function(data){
			if(error(data)) return;
			var monitorPoint = data.data;
			for(var i=0,len=monitorPoint.length;i<len;i++){
				var option = $("<option value=\""+monitorPoint[i].id+"\">"+monitorPoint[i].titleText+"</option>");
				$("#monitorPointOption").append(option);
				//init all charts
				initCharts(monitorPoint[i]);
			}
			initMultiple();
		/* 	$('.widget-box').each(function(){
				$(this).resize(function(){
					alert($(this).width());
				});
			  }); */
		}
	});	
}

function initCharts(data){
	var viewDemo = $('#monitor-view-demo').clone().removeClass('hide').attr("id",data.id+"-monitor-view").appendTo($('#monitor-view'));
	var div = $(viewDemo).find('[name="data-chart"]');
	$(div).attr("id",data.id);
	//init div to chart
	initChart(div,data.titleText,data.yAxisText,data.tooltipSuffix);
	
	var chart = $(div).highcharts();
	setChartData(data.id,chart);
	
	draggable(viewDemo);
}

function initChart(obj,title,ytitle,unit){
    $(obj).highcharts({
    	chart:{
    		zoomType: 'x'
    	},
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
            valueSuffix: unit
        }
    });

} 

function setChartData(indexId,chart){
	var mclusterId= $('#mclusterOption').val();
	var queryTime= $('#queryTime').val();
	$.ajax({
		type : "get",
		url : "${ctx}/monitor/"+mclusterId+"/"+indexId+"/"+queryTime,
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

function draggable(obj){
	 $(obj).sortable({
	        connectWith: '.widget-container-col',
			items:'> .widget-box',
			handle: ace.vars['touch'] ? '.widget-header' : false,
			cancel: '.fullscreen',
			opacity:0.8,
			revert:true,
			forceHelperSize:true,
			placeholder: 'widget-placeholder',
			forcePlaceholderSize:true,
			tolerance:'pointer',
			disabled:true,
			start: function(event, ui) {
				ui.item.parent().css({'min-height':ui.item.height()})
			},
			update: function(event, ui) {
				ui.item.parent({'min-height':''})
			}
	    });
}

function changeDraggable(obj){
	var dgable = $(obj).find('input').val();
	if(dgable == '1'){
		$(obj).closest('[name="monitor-view"]').sortable('disable');
		$(obj).find('input').val(0);
		$(obj).find('i').attr("style","-webkit-transform:rotate(45deg);-moz-transform:rotate(45deg);-o-transform:rotate(45deg);");
	}else{
		$(obj).closest('[name="monitor-view"]').sortable('enable');
		$(obj).find('input').val(1);
		$(obj).find('i').attr("style","-webkit-transform:rotate(90deg);-moz-transform:rotate(90deg);-o-transform:rotate(90deg);");
	}
}

function initMultiple(){
	$('.chosen-select').chosen({allow_single_deselect:true}); 
	$(window).off('resize.chosen').on('resize.chosen', function() {
		$('.chosen-select').each(function() {
			 var $this = $(this);
			 $this.next().css({'width': $this.parent().width()});
		})
	}).trigger('resize.chosen');
}

function updateChartSize(obj){
	 setTimeout(function () { 
		 $(obj).closest('.widget-box').find('[name="data-chart"]').highcharts().reflow();
	    }, 1);
}

$(function(){
	$('#nav-search').addClass("hidden");
	queryMcluster();
});
</script>
