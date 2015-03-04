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
	//getLoading();
	$.ajax({
		cache:false,
		type:"get",		
		url:"/mcluster/valid",
		dataType:"json",
		success:function(data){
			//removeLoading();
			if(error(data)) return;
			var mclustersInfo = data.data;
			for(var i=0,len=mclustersInfo.length;i<len;i++){
				var option = $("<option value=\""+mclustersInfo[i].id+"\">"+mclustersInfo[i].mclusterName+"</option>");
				$("#mclusterOption").append(option);
				//chosen 组件配置
				chosenConf();
			}
			queryMonitorPoint();
		}
	});	
}

function queryMonitorPoint(){
	//getLoading();
	$.ajax({
		cache:false,
		type:"get",		
		url : "/monitor/index",
		dataType:"json",
		success:function(data){
			//removeLoading();
			if(error(data)) return;
			var monitorPoint = data.data;
			for(var i=0,len=monitorPoint.length;i<len;i++){
				var option = $("<option value=\""+monitorPoint[i].id+"\">"+monitorPoint[i].titleText+"</option>");
				$("#monitorPointOption").append(option);
				//init all charts
				initCharts(monitorPoint[i]);
				/*chosen组件配置*/
				chosenConf();
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
        },
        loading: {
            hideDuration: 10,
            showDuration: 10,
            style: {
            	position: 'absolute',
            	backgroundColor: 'white',
            	opacity: 0.5,
            	textAlign: 'center'
            },
           labelStyle:{
           	 "fontWeight": "bold",
           	 "position": "relative", 
           	 "top": "45%",
           	 "fontSize":"12px"
           }
        }
    });

} 

function setChartData(indexId,chart){
	var mclusterId= $('#mclusterOption').val();
	var queryTime= $('#queryTime').val();
	chart.showLoading();
	$.ajax({
		cache:false,
		type : "get",
		url : "/monitor/"+mclusterId+"/"+indexId+"/"+queryTime,
		dataType : "json", 
		contentType : "application/json; charset=utf-8",
		success:function(data){
			chart.hideLoading();
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
