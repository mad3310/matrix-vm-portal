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
    	currentPage:1,
    	recordsPerPage:10,
        Tooltip : function (id){
        	if(!id) {
        		id = "[data-toggle='tooltip']";
        	}
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
		TranslateDbUserType: function(type){
			if(type == 1){
				return "管理";
			}else if(type == 2){
				return "只读"
			}else{
				return "读写"
			}
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
        		return "<span class=\"text-danger\">异常</span>";
        	}else if(status == 6){
        		return "<span class=\"text-success\">正常<span>";
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
        TransDate : function (format, timestamp){
        	var a, jsdate=((timestamp) ? new Date(timestamp) : new Date());
    	    var pad = function(n, c){
    	        if((n = n + "").length < c){
    	            return new Array(++c - n.length).join("0") + n;
    	        } else {
    	            return n;
    	        }
    	    };
    	    var txt_weekdays = ["Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"];
    	    var txt_ordin = {1:"st", 2:"nd", 3:"rd", 21:"st", 22:"nd", 23:"rd", 31:"st"};
    	    var txt_months = ["", "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];
    	    var f = {
    	        // Day
    	        d: function(){return pad(f.j(), 2)},
    	        D: function(){return f.l().substr(0,3)},
    	        j: function(){return jsdate.getDate()},
    	        l: function(){return txt_weekdays[f.w()]},
    	        N: function(){return f.w() + 1},
    	        S: function(){return txt_ordin[f.j()] ? txt_ordin[f.j()] : 'th'},
    	        w: function(){return jsdate.getDay()},
    	        z: function(){return (jsdate - new Date(jsdate.getFullYear() + "/1/1")) / 864e5 >> 0},
    	       
    	        // Week
    	        W: function(){
    	            var a = f.z(), b = 364 + f.L() - a;
    	            var nd2, nd = (new Date(jsdate.getFullYear() + "/1/1").getDay() || 7) - 1;
    	            if(b <= 2 && ((jsdate.getDay() || 7) - 1) <= 2 - b){
    	                return 1;
    	            } else{
    	                if(a <= 2 && nd >= 4 && a >= (6 - nd)){
    	                    nd2 = new Date(jsdate.getFullYear() - 1 + "/12/31");
    	                    return date("W", Math.round(nd2.getTime()/1000));
    	                } else{
    	                    return (1 + (nd <= 3 ? ((a + nd) / 7) : (a - (7 - nd)) / 7) >> 0);
    	                }
    	            }
    	        },
    	       
    	        // Month
    	        F: function(){return txt_months[f.n()]},
    	        m: function(){return pad(f.n(), 2)},
    	        M: function(){return f.F().substr(0,3)},
    	        n: function(){return jsdate.getMonth() + 1},
    	        t: function(){
    	            var n;
    	            if( (n = jsdate.getMonth() + 1) == 2 ){
    	                return 28 + f.L();
    	            } else{
    	                if( n & 1 && n < 8 || !(n & 1) && n > 7 ){
    	                    return 31;
    	                } else{
    	                    return 30;
    	                }
    	            }
    	        },
    	       
    	        // Year
    	        L: function(){var y = f.Y();return (!(y & 3) && (y % 1e2 || !(y % 4e2))) ? 1 : 0},
    	        //o not supported yet
    	        Y: function(){return jsdate.getFullYear()},
    	        y: function(){return (jsdate.getFullYear() + "").slice(2)},
    	       
    	        // Time
    	        a: function(){return jsdate.getHours() > 11 ? "pm" : "am"},
    	        A: function(){return f.a().toUpperCase()},
    	        B: function(){
    	            // peter paul koch:
    	            var off = (jsdate.getTimezoneOffset() + 60)*60;
    	            var theSeconds = (jsdate.getHours() * 3600) + (jsdate.getMinutes() * 60) + jsdate.getSeconds() + off;
    	            var beat = Math.floor(theSeconds/86.4);
    	            if (beat > 1000) beat -= 1000;
    	            if (beat < 0) beat += 1000;
    	            if ((String(beat)).length == 1) beat = "00"+beat;
    	            if ((String(beat)).length == 2) beat = "0"+beat;
    	            return beat;
    	        },
    	        g: function(){return jsdate.getHours() % 12 || 12},
    	        G: function(){return jsdate.getHours()},
    	        h: function(){return pad(f.g(), 2)},
    	        H: function(){return pad(jsdate.getHours(), 2)},
    	        i: function(){return pad(jsdate.getMinutes(), 2)},
    	        s: function(){return pad(jsdate.getSeconds(), 2)},
    	        //u not supported yet
    	       
    	        // Timezone
    	        //e not supported yet
    	        //I not supported yet
    	        O: function(){
    	            var t = pad(Math.abs(jsdate.getTimezoneOffset()/60*100), 4);
    	            if (jsdate.getTimezoneOffset() > 0) t = "-" + t; else t = "+" + t;
    	            return t;
    	        },
    	        P: function(){var O = f.O();return (O.substr(0, 3) + ":" + O.substr(3, 2))},
    	        //T not supported yet
    	        //Z not supported yet
    	       
    	        // Full Date/Time
    	        c: function(){return f.Y() + "-" + f.m() + "-" + f.d() + "T" + f.h() + ":" + f.i() + ":" + f.s() + f.P()},
    	        //r not supported yet
    	        U: function(){return Math.round(jsdate.getTime()/1000)}
    	    };
    	       
    	    return format.replace(/[\\]?([a-zA-Z])/g, function(t, s){
    	        if( t!=s ){
    	            // escaped
    	            ret = s;
    	        } else if( f[s] ){
    	            // a date function exists
    	            ret = f[s]();
    	        } else{
    	            // nothing special
    	            ret = s;
    	        }
    	        return ret;
    	    });
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
            /*菜单点击处理事件*/
            $('.sidebar-selector').find("a").click(function(){
            	var $parent = $(this).closest("li");
            	var str = $(this).attr("src");
        		//判断src是否有值，若有值，则为有业务的菜单，若无值，判断parent是否为父菜单，若为父菜单，执行菜单收缩展开操作
        		if(str) {
	    			$("#frame-content").attr("src",str);
	    			$('.sidebar-selector').find(".active").removeClass("active");
	    			$parent.addClass("active");
        		}else {
        			if($parent.children("ul")){
        				//判断菜单是否展开
		        		if($parent.children("ul").hasClass("hide")){
		        			extended($parent);
		                }else{
		                	stacked($parent);
		                }
	        		}
        		}
        		
            });
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
					if(handler){
						handler(data);
					}
                }
            });
        },
        DeleteData : function (url,handler){ //异步提交数据,将返回数据交给handler处理
            $.ajax({
                url:url,
                type:"delete",
                dataType:'json',
                success:function(data){
                    /*添加当handler为空时的异常处理*/
					if(handler){
						handler(data);
					}
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
        },
		EditBoxInit: function(obj){
			var offset = $(obj).offset();
			var left = offset.left-35;
			var top = offset.top+$(obj).height()+5;

			$(".edit-text-box").css('left',left+"px");
			$(".edit-text-box").css('top',top+"px");
			$(".edit-text-box").removeClass("hide");

			$("#editBoxCancel").unbind("click").click(function(){
				$(".edit-text-box").addClass("hide");
			})
		},
		DialogBoxInit:function(title,text,handler,args){
			$("#dialog-box").find("#dialog-box-title").html(title);
			$("#dialog-box").find("#dialog-box-text").html(text);
			$("#dialogBoxSubmit").unbind("click").click(function(){
				handler(args);
			})
			$('#dialog-box').modal({
				backdrop:false,
				show:true
			});
		}

        /*add new common function*/
    }
});
