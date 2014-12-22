/**
 * Created by yaokuo on 2014/12/12.
 */
define(function(require,exports,module){
    var $ = require('jquery');
    require('bootstrap')($);
    var Common = function (){
    };
    module.exports = Common;

    Common.prototype = {
        Tooltip : function (id){
            $(id).hover(function() {
                $(this).tooltip('show');
            }, function() {
                $(this).tooltip('hide');
            });
        },
        
        Collapse : function(id){
            $(id).click(function(){
            	if($(this).find(".glyphicon-chevron-down").length>0){
            		$(this).find(".glyphicon-chevron-down").removeClass("glyphicon-chevron-down").addClass("glyphicon-chevron-up");
            	}else{
            		$(this).find(".glyphicon-chevron-up").removeClass("glyphicon-chevron-up").addClass("glyphicon-chevron-down");
            	}
            });
        },
        TranslateStatus : function (status){
        	if (status == 0){
        		return "未审核";
        	}else if(status == 1){
        		return "运行中";
        	}else if(status == 2){
        		return "<i class=\"ace-icon fa fa-spinner fa-spin green bigger-125\"></i>创建中...";
        	}else if(status == 3){
        		return "创建失败";
        	}else if(status == 4){
        		return "<font color=\"red\">审核失败</font>";
        	}else if(status == 5){
        		return "<font color=\"orange\">异常</font>";
        	}else if(status == 6){
        		return "<span class=\"text-success\">运行中<span>";
        	}else if(status == 7){
        		return "<i class=\"ace-icon fa fa-spinner fa-spin green bigger-125\"></i>启动中...";
        	}else if(status == 8){
        		return "<i class=\"ace-icon fa fa-spinner fa-spin green bigger-125\"></i>停止中...";
        	}else if(status == 9){
        		return "已停止";
        	}else if(status == 10){
        		return "<i class=\"ace-icon fa fa-spinner fa-spin green bigger-125\"></i>删除中...";
        	}else if(status == 11){
        		return "已删除";
        	}else if(status == 12){
        		return "不存在";
        	}else if(status == 13){
        		return "<font color=\"orange\">危险</font>";
        	}else if(status == 14){
        		return "<font color=\"red\">严重危险</font>";
        	}else if(status == 15){
        		return "禁用";
        	}
        },
        
        Sidebar : function(index){
            var extended = function(obj){   //obj为有二级菜单的li
                $(obj).removeClass("active");
                $(obj).find("ul").removeClass("hide");
                $(obj).find("span").removeClass("glyphicon glyphicon-chevron-right");
                $(obj).find("span").addClass("glyphicon glyphicon-chevron-down");
            }

            var stacked = function(obj){
                if($(obj).find("li").hasClass("active")){ //如果二级目录含有active
                    $(obj).addClass("active");
                }
                $(obj).find("ul").addClass("hide");
                $(obj).find("span").removeClass("glyphicon glyphicon-chevron-down");
                $(obj).find("span").addClass("glyphicon glyphicon-chevron-right");
            }

            /*二级菜单点击事件处理*/
            $('.sidebar-selector').find("ul ul").closest("li").click(function(){
                if( $(this).find("ul").hasClass("hide")){
                    extended(this);
               }else{
                    stacked(this);
               }
            });

            /*跳转界面处理*/
            $('.sidebar-selector').find(".active").removeClass("active");
            var $obj = $('.sidebar-selector').children("ul").children("li:eq("+index[0]+")");
            if($obj.find("ul").length > 0){
                extended($obj);
                $obj.find("li:eq("+index[1]+")").addClass("active");
            }else{
                $obj.addClass("active");
            }
        },

        GetData : function(url,handler){  //异步获取数据,将数据交给handler处理
            $.ajax({
                url:url,
                type:"get",
                dataType:'json',
                success:function(data){
                    /*添加当handler为空时的异常处理*/
                    handler(data);
                }
            });
        },

        PostData : function (url,data,handler){ //异步提交数据,将返回数据交给handler处理
            $.ajax({
                url:url,
                type:"post",
                dataType:'json',
                data:data,
                success:function(data){
                    /*添加当handler为空时的异常处理*/
                    handler(data);
                }
            });
        },
        
       Charts : function(cType,cTitle,cSubtitle,cxAxis,cyAxis,seriesData){
    	   //legend颜色控制
    	   Highcharts.setOptions({ 
    		    colors: ['#ff66cc','#66ff66','#66ffff'] 
    		});
    		    $('#chart-container').highcharts({
    		        chart: {
    		            type: cType ,          
    		            zoomType: 'x',
    		            spacingRight: 20            
    		        },        
    		        title: {
    		            text: cTitle
    		        },
    		        subtitle: {
    		            text: cSubtitle
    		        },
    		        xAxis: {
    		            type: 'datetime',
    		            maxZoom: 14 * 24 * 3600000, 
    		            title: {
    		                text: null
    		            }
    		        },        
    		        yAxis: {
    		            title: {
    		                text: 'Exchange rate'
    		            }
    		        },
    		        credits: {
    		            enabled: false
    		        },
    		        legend :{
    		            borderColor: '#000000',
    		            backgroundColor: '#f9f9f9',
    		            /*shadow: true,*/
    		            /*itemwidth: 300,*/
    		            /*margin:30,*/
    		            symbolRadius: '2px',
    		            borderRadius: '5px',
    		            /*itemHiddenStyle: {
    		                Color: '#333333'
    		                },*/
    		            itemHoverStyle: {
    		                Color: '#000000'
    		                }
    		        },
    		        tooltip: {
    		                    pointFormat: '<span style="color:{series.color}">{series.name}</span>: <b>{point.percentage:.1f}%</b> ({point.y:,.0f} millions)<br/>',
    		                    shared: true
    		                },  
    		        plotOptions: {
    		            area: {                
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
    		            }
    		        },
    		        series:  seriesData
    		    });
        }
        /*add new common function*/
    }
});