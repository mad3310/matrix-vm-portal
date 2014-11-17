<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="page-content-area">
	<div class="page-header">
		<h1> 
			container集群监控视图
		</h1>
	</div>
	<!-- /.page-header -->
	<div class="row">
		<div id="monitor-option" class="col-sm-12"> 
			<div class="clearfix form-actions">
				<form class="form-horizontal" role="form">
					<div class="form-group">
						<label class="col-sm-1 control-label no-padding-right">Container集群</label>
						<div class="col-sm-1">
							<select id="mclusterOption" name="mclusterId" class="form-control">
							</select>					
						</div>
					<!-- 	<label class="col-sm-1 control-label">监控点</label>
						<div class="col-sm-2">
							<select id="monitorPointOption" name="monitorPoint" class="form-control">
								<option value=""></option>
							</select>
						</div> -->
						<label class="col-sm-1 control-label">监控点</label>
						<div class="col-sm-3">
							<select multiple="" class="chosen-select form-control" name="monitorPoint" id="monitorPointOption" data-placeholder="Choose a State...">
							</select>
						</div>
						
						<label class="col-sm-1 control-label">时间</label>
						<div class="col-sm-1">
							<select id="queryTime" name="queryTime" class="form-control">
								<option value="1">一小时</option>
								<option value="2">三小时</option>
								<option value="3">一天</option>
								<option value="4">一周</option>
							</select>
						</div>
						<div class="col-sm-1">
							<button id="refresh-data" type="button" onclick="refreshChartForSelect()" class="btn btn-sm btn-primary">刷新</button>
						</div>
					</div>
				</form>
			</div>
		<div id="monitor-view" class="row">
		</div>
		<!-- <div class="space-24"></div> -->
			<div id="monitor-view-demo" class="col-xs-12 col-sm-6 widget-container-col ui-sortable hide">
				<div class="widget-box transparent ui-sortable-handle">
					<div class="widget-header">
						<h4 class="widget-title lighter">监控图</h4>
						<div class="widget-toolbar no-border">
							<a href="#" data-action="fullscreen" class="orange2">
								<i class="ace-icon fa fa-expand"></i>
							</a>
							<a href="#" data-action="close">
								<i class="ace-icon fa fa-times"></i>
							</a>
						</div>
					</div>
					<div class="widget-body">
						<div name="monitor-view-demo-data" class="widget-main padding-6 no-padding-left no-padding-right">
						</div>
					</div>
				</div>
			</div>
	</div>
</div>
<script src="${ctx}/static/scripts/highcharts/highcharts.js"></script>
<script src="${ctx}/static/scripts/highcharts/themes/grid.js"></script>

<%-- <script src="${ctx}/static/scripts/highcharts/themes/dark-blue.js"></script> --%>
<script type="text/javascript">
function drawChart(obj,title,ytitle,unit,xdata,ydata){
    $(obj).highcharts({
        title: {
            text: title
      
        },
        xAxis: {
			type: 'datetime',
            categories: xdata,
            labels:{
            	rotation:-90,
            	align:'right',
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
        },
        series: ydata
    });

} 

function addChart(data){
	var viewDemo = $('#monitor-view-demo').clone().removeClass('hide').removeAttr('id').appendTo($('#monitor-view'));
	var div = $("<div name=\"data-chart\" class=\"col-sm-12\" style=\"min-width: 310px; height: 400px\"></div>");
	div.appendTo(viewDemo.find('[name="monitor-view-demo-data"]'));
	drawChart(div,data.title,data.ytitle,data.unit,data.xdata,data.ydata);
	draggable(viewDemo);
}

function queryAllChart(clusterId){
	$('#monitor-view div').remove();
	$.ajax({
		type : "get",
		url : "${ctx}/monitor/index",
		dataType : "json", 
		contentType : "application/json; charset=utf-8",
		success : function(data) {
			error(data);
			var array = data.data;
			for (var i=0,len = array.length;i < len; i ++){
				$.ajax({
					type : "get",
					url : "${ctx}/monitor/"+clusterId+"/"+array[i].id+"/"+$('#queryTime').val(),
					dataType : "json", 
					contentType : "application/json; charset=utf-8",
					success:function(data){
				 		error(data);
				 		addChart(data.data);
					}
				});
			}
		}	
	});
}

function queryMcluster(){
	$.ajax({
		type:"get",		
		url:"${ctx}/mcluster",
		dataType:"json",
		success:function(data){
			error(data);
			var mclustersInfo = data.data;
			for(var i=0,len=mclustersInfo.length;i<len;i++)
			{
				var option = $("<option value=\""+mclustersInfo[i].id+"\">"
								+mclustersInfo[i].mclusterName
								+"</option>");
				$("#mclusterOption").append(option);
			}
			queryAllChart(mclustersInfo[0].id);
		}
	});	
}

function queryMonitorPoint(){
	$.ajax({
		type:"get",		
		url : "${ctx}/monitor/index",
		dataType:"json",
		success:function(data){
			error(data);
			var monitorPoint = data.data;
			for(var i=0,len=monitorPoint.length;i<len;i++)
			{
				var option = $("<option value=\""+monitorPoint[i].id+"\">"
								+monitorPoint[i].titleText
								+"</option>");
				$("#monitorPointOption").append(option);
			}
			
		}
	});	
}

function refreshChartForSelect(){
	var monitorPoint = $('#monitorPointOption').val();
	var mclusterId= $('#mclusterOption').val();
	var queryTime= $('#queryTime').val();
	
	if (monitorPoint != '')
	{
		$('#monitor-view div').remove();
		$.ajax({
			type : "get",
			url : "${ctx}/monitor/"+mclusterId+"/"+monitorPoint+"/"+queryTime,
			dataType : "json", 
			contentType : "application/json; charset=utf-8",
			success:function(data){
		 		error(data);
		 		addChart(data.data);
			}
		});
	}else{
		queryAllChart(mclusterId);
	}
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
			start: function(event, ui) {
				ui.item.parent().css({'min-height':ui.item.height()})
			},
			update: function(event, ui) {
				ui.item.parent({'min-height':''})
			}
	    });
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
$(function(){
	queryMonitorPoint();
	queryMcluster();

});
</script>
