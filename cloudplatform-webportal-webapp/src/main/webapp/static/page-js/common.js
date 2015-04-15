/**
 * Created by yaokuo on 2014/12/12.
 */
define(function(require,exports,module){
    var  $ = require('jquery');
    require('bootstrap')($);
    var Common = function (){
		this.totalAvailableTime = 365;
		this.dbListRefreshTime = 60000; //单位ms
		this.maxConcurrency = 2000; //数据库最大并发量
		
		TopBtnInit();//初始化顶部菜单按钮
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
        Popover : function (id){
        	if(!id) {
        		id = "[data-toggle='popover']";
        	}
			$(id).hover(function() {
				$(this).popover('show');
			}, function() {
				$(this).popover('hide');
			});
        },
        Collapse : function(){
        	$(".collapse").on('show.bs.collapse', function () {
        			$(this).prev().find(".glyphicon-chevron-up").removeClass("glyphicon-chevron-up").addClass("glyphicon-chevron-down");
        	}).on('hide.bs.collapse', function () {
        			$(this).prev().find(".glyphicon-chevron-down").removeClass("glyphicon-chevron-down").addClass("glyphicon-chevron-up");
        	})
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
        	}else if(status == 'FAILD'){
        		return "<font color=\"red\">备份失败</font>";
        	}else if(status == 'SUCCESS'){
        		return "<span class=\"text-success\">备份成功<span>";
        	}else if(status == 'BUILDING'){
        		return "<i class=\"ace-icon fa fa-spinner fa-spin green bigger-125\"></i>备份中...";
        	}	
        },
		Displayable : function (status) {
			if(status == 6){
				return true;
			}
		},
        TransDate : function (format, timestamp){
        	//var a, jsdate=((timestamp) ? new Date(timestamp) : new Date());
        	var a;
        	if(timestamp == null){
        		return "---";
        	}else{
        		var jsdate=new Date(timestamp);
        	}
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
		RemainAvailableTime:function(ctimestamp){
			var timestamp = Date.parse(new Date());
			var differDays = parseInt((timestamp-ctimestamp)/(1000 * 60 * 60 * 24));
			// console.log("differTime:"+differDays+"  this.totalAvailableTime:"+this.totalAvailableTime);
			var remainDays = this.totalAvailableTime - differDays;
			return remainDays;
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
        GetData : function(url,handler){ //异步获取数据,将数据交给handler处理
			if($('body').find('.sidebar').length==0){	//layout界面不添加loading
			   $('body').append("<div class=\"spin\"></div>");
			   $('body').append("<div class=\"far-spin\"></div>");
			}
			$.ajax({
                url:url,
				cache:false,
                type:"get",
                dataType:'json',
                success:function(data){
                	$('body').find('.spin').remove();
					$('body').find('.far-spin').remove();
                	if(data.result == 0){
                		 window.location.href="/500";
                	}else{
                		/*添加当handler为空时的异常处理*/
                        handler(data);
                	}
                }
            });
        },
        GetLocalData : function(url,handler){ //异步获取数据,将数据交给handler处理
			$.ajax({
                url:url,
				cache:false,
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
				cache:false,
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
		EditBoxInit: function(obj){
			var offset = $(obj).offset();
			var left = offset.left;
			var top = offset.top+$(obj).height()+5;			
			$("#edit-spin").addClass("edit-spin");

			$(".edit-text-box").css('left',left+"px");
			$(".edit-text-box").css('top',top+"px");
			$(".edit-text-box").removeClass("hide");

			$("#editBoxCancel").unbind("click").click(function(){
				$(".edit-text-box").addClass("hide");
				$("#edit-spin").removeClass("edit-spin");
			})
		},
		DialogBoxInit:function(title,text,handler,args){
			$("#dialog-box").find("#dialog-box-title").html(title);
			$("#dialog-box").find("#dialog-box-text").html("<h5 style='text-align: center; '>"+text+"</h5>");
			$("#dialogBoxSubmit").unbind("click").click(function(){
				handler(args);
			})
			$('#dialog-box').modal({
				backdrop:false,
				show:true
			});
			$("#submitCreateUserForm").removeAttr("disabled");
			$("#submitModifyUserForm").removeAttr("disabled");
		},
		DisableBackspaceEnter:function(){
			document.getElementsByTagName("body")[0].onkeydown  = function(event) {
				var target, code,tag;
				if (!event) {
					event = window.event; //针对ie浏览器
					target = event.srcElement;
					code = event.keyCode;
					if (code == 13) {
						tag = target.tagName;
						if (tag == "TEXTAREA") { return true; }
						else { return false; }
					}else if(code == 8){
						tag = target.tagName;
						if(tag!='INPUT' && tag!='TEXTAREA'){
							event.returnValue = false ;
							return ;
						}
						var type_e = target.type.toUpperCase();
						if(tag=='INPUT' && (type_e!='TEXT' && type_e!='TEXTAREA' && type_e!='PASSWORD' && type_e!='FILE')){
							event.returnValue = false ;
							return ;
						}
						if(tag=='INPUT' && (target.readOnly==true || target.disabled ==true)){
							event.returnValue = false ;
							return ;
						}
					}
				}
				else {
					target = event.target; //针对遵循w3c标准的浏览器，如Firefox
					code = event.keyCode;
					if (code == 13) {
						tag = target.tagName;
						if (tag == "INPUT") { return false; }
						else { return true; }
					}else if(code == 8){
						tag = target.tagName;
						if(tag!='INPUT' && tag!='TEXTAREA'){
							event.returnValue = false ;
							return ;
						}
						var type_e = target.type.toUpperCase();
						if(tag=='INPUT' && (type_e!='TEXT' && type_e!='TEXTAREA' && type_e!='PASSWORD' && type_e!='FILE')){
							event.returnValue = false ;
							return ;
						}
						if(tag=='INPUT' && (target.readOnly==true || target.disabled ==true)){
							event.returnValue = false ;
							return ;
						}
					}
				}
			};
		},
		TableFilterNull:function(data){
			if(data == null || data == undefined){
				return '-';
			}else{
				return data;
			}
		},
		FilterNull:function(data){
			if(data == null || data == undefined){
				return '';
			}else{
				return data;
			}
		},
		/*刷新ifame*/
		RefreshIfame:function(url){
			var $iframe = $("body",parent.document).find("iframe");
			if(url == undefined || url == null){
				//刷新dangqianye
				$iframe.attr("src",$("body", parent.document).find("iframe").attr("src"));
			}else{
				$iframe.attr("src",url);
			}
		},
		/*弹出窗口居中*/
		center:function(){
			//获取父窗口对象
			var winobj=window.parent.document.body;
			//获取父窗口对象属性
			var screenWidth = $(winobj).width();
			//var screenHeight = $(winobj).height();
		    //var scrolltop = $(winobj).scrollTop();//获取当前窗口距离页面顶部高度
		    
		    //获取子窗口及对象及子窗口对象的属性
			var iframebody= document.body;
			var iframebodyWidth = $(iframebody).width();
			//var iframebodyHeight = $(iframebody).height();
			
			//获取探矿对象
			var $obj = $(".modal-dialog");
			//alert($obj.offset().top);
			var objLeft = ($obj.offset().left - (screenWidth-iframebodyWidth)/2); 
			//var objTop = ($obj.offset().top  - (screenHeight-iframebodyHeight)/2);
			$obj.css({'left':  objLeft + 'px','display': 'block'});
		},
		divselect: function () {
			$('.divselect').closest('.pull-left').unbind("click").click(function(event){
				event.stopPropagation();
				var ul = $(this).find('ul');
				if(ul.css("display")=="none"){
					$('.divselect').find('ul').hide().closest('.pull-left').find('.bk-select-arrow').attr("style","-webkit-transform:rotate(0deg);-moz-transform:rotate(0deg);-o-transform:rotate(0deg);-ms-transform: rotate(0deg);");//关闭所有select
					ul.show().closest('.pull-left').find('.bk-select-arrow').attr("style","-webkit-transform:rotate(180deg);-moz-transform:rotate(180deg);-o-transform:rotate(180deg);-ms-transform: rotate(180deg);");
				}else{
					ul.hide().closest('.pull-left').find('.bk-select-arrow').attr("style","-webkit-transform:rotate(0deg);-moz-transform:rotate(0deg);-o-transform:rotate(0deg);-ms-transform: rotate(0deg);");
				}
			})
			$(document).click(function () {
				$('.divselect').find('ul').hide().closest('.pull-left').find('.bk-select-arrow').attr("style","-webkit-transform:rotate(0deg);-moz-transform:rotate(0deg);-o-transform:rotate(0deg);-ms-transform: rotate(0deg);");
			})

			$('.divselect').find("ul li").unbind("click").click(function(){
				var txt = $(this).find('a').text();
				$(this).closest('.divselect').find('span').html(txt);
				var value = $(this).find('a').attr("selectid");
				$(this).closest('.divselect').find('input').val(value).change();
			});
			$(".divselect").each(function () {
				if($(this).find('span').html() == ''&&$(this).find('li').length > 0){
					$(this).find('ul li').first().click();
				}
			})
		},
		getBackupDate:function(){
			var myDate = new Date();			
			var timestamp = myDate.valueOf();			
			var hour = myDate.getHours();
			
			if(hour > 4){
				timestamp = timestamp + 86400000;
			}else{
				timestamp = timestamp;
			}
			
			var newDate = new Date(timestamp);
			var year = newDate.getFullYear();
			var month = newDate.getMonth() + 1;
			var day = newDate.getDate();
			
			return year + '年' + month + '月' + day + '日';
		},
		emptyBlock:function(obj){
			if($("#noData").length < 1){
				$(obj).closest("div").after("<div class=\"help-block\" id=\"noData\">没有数据记录</div>");
			}			
		},
		TranslateAgentType:function(data){
			if(data = 1){
				return "http";
			}else{
				return "tcp";
			}
		},
		formatJson:function(json){
				var i           = 0,
			        il          = 0,
			        tab         = "    ",
			        newJson     = "",
			        indentLevel = 0,
			        inString    = false,
			        currentChar = null;
				var cn = new Common;
	
			    for (i = 0, il = json.length; i < il; i += 1) { 
			        currentChar = json.charAt(i);
		
			        switch (currentChar) {
			        case '{': 
			        case '[': 
			            if (!inString) { 
			                newJson += currentChar + "\n" + cn.repeat(tab, indentLevel + 1);
			                indentLevel += 1; 
			            } else { 
			                newJson += currentChar; 
			            }
			            break; 
			        case '}': 
			        case ']': 
			            if (!inString) { 
			                indentLevel -= 1; 
			                newJson += "\n" + cn.repeat(tab, indentLevel) + currentChar; 
			            } else { 
			                newJson += currentChar; 
			            } 
			            break; 
			        case ',': 
			            if (!inString) { 
			                newJson += ",\n" + cn.repeat(tab, indentLevel); 
			            } else { 
			                newJson += currentChar; 
			            } 
			            break; 
			        case ':': 
			            if (!inString) { 
			                newJson += ": "; 
			            } else { 
			                newJson += currentChar; 
			            } 
			            break; 
			        case ' ':
			        case "\n":
			        case "\t":
			            if (inString) {
			                newJson += currentChar;
			            }
			            break;
			        case '"': 
			            if (i > 0 && json.charAt(i - 1) !== '\\') {
			                inString = !inString; 
			            }
			            newJson += currentChar; 
			            break;
			        default: 
			            newJson += currentChar; 
			            break;                    
			        } 
			    } 
		    return newJson; 
		},
		repeat:function(s, count){
			 return new Array(count + 1).join(s);
		},	

		 /*add new common function*/
         //2015-04-13 by gm new add drag及相关
         baseData:function(){
            var _base=$('.bk-slider-l2');
            var level1=_base.width();//0-250:206
            var level2=_base.next().width();//103
            var level3=_base.next().next().width();//103
            var length={
                lev1:level1,
                lev2:level1+level2,
                lev3:level1+level2+level3
            }
            return length;
        },
        btnPrimaryClick:function(){
            var _current=$('.bk-button-primary');
            _current.click(function(event) {
                var _this=$(this);
                if(_this.hasClass('bk-button-current')){

                }else{
                    _this.removeClass('disabled').addClass('bk-button-current');
                    _this.siblings().removeClass('bk-button-current');
                }
            });
        },
        barDrag:function(options){//滑动条拖动
            var _awCursor=$('.awCursor');
            var _slider=$('.bk-slider');
            var dgFlag=false;
            var temp;
            _awCursor.on('mousedown',function(event) {
                event.preventDefault();
                dgFlag=true;
                $(this).prev().removeClass('bk-slider-transition');
            });
            $('body').on('mousemove',function(event) {
                event.preventDefault();
                if(dgFlag){
                    //可以拖拽
                    _awCursor.find('.bk-tip-gray').removeClass('hide');
                    var left=event.pageX;
                    var left2=_slider.offset().left;
                    temp=parseInt(left)-parseInt(left2);
                    if(temp>414){
                        //拖拽过最右界
                        temp=412;
                    }else{
                         if(temp<0){
                            //拖拽过最左界
                            //temp=5;
                            temp=options.stepSize;
                         }else{
                            //允许范围之内
                        }               
                    }
                    drag(temp,options);
                }else{
                    //不能拖拽
                }
            });
            $('body').on('mouseup',function(event) {
                event.preventDefault();
                dgFlag=false;
                _awCursor.find('.bk-tip-gray').addClass('hide');
                _awCursor.prev().addClass('bk-slider-transition');
            });
        },
        barClickDrag:function(options){
            var _slider=$('.bk-slider');
            _slider.on('click',function(event) {
                event.preventDefault();
                var left=event.pageX;
                var left2=_slider.offset().left;
                var relaLen=left-left2;
                drag(relaLen,options);
            });
        },
        inputChge:function(options){
            var _memSize=$('.memSize');
            var val=_memSize.val();
            inputChgeDrag(val,options);
        },
        chgeBuyNmu:function(){
            var _taiNum=$('.tai-num');
            var _upT=$('.tai-num-up');var _downT=$('.tai-num-down');
            var val=_taiNum.val();
            val=parseInt(val);
            if(val<=1){
                val=1;
                _downT.addClass('bk-number-disabled');
            }else if(val>=99){
                val=99;
                _upT.addClass('bk-number-disabled');
            }else{
                //合法范围
                _upT.removeClass('bk-number-disabled');
                _downT.removeClass('bk-number-disabled');
            }
            _taiNum.val(val);
        }
    }
    function drag(relaLen,options){
        var _memSize=$('.memSize');
        var _awCursor=$('.awCursor');
        var _layer2=$('#layer2');
        var _up=$('.mem-num-up');
        var _down=$('.mem-num-down');
        var base=options.stepSize;
        var lev1=options.lev1;//0-250:206
        var lev2=options.lev2;//250-500:206+103
        var lev3=options.lev3;//500-1000:206+103+103
        if(relaLen>lev3){
            //不合适
            _up.addClass('bk-number-disabled')
        }else{
            _up.removeClass('bk-number-disabled');
            _down.removeClass('bk-number-disabled');
            if(relaLen>lev2){
                //500-1000档
                var tempL=parseInt(relaLen)-lev2;
                var temp=tempL*500/(lev2-lev1);
                var i=Math.ceil(temp);
                i=base*Math.ceil(i/base);
                // temp=i*5;
                temp=i+500;
                _memSize.val(temp);
                _awCursor.find('.ng-binding').text(temp);
                _layer2.css({
                    width:relaLen
                });
                _awCursor.css({
                    left:relaLen
                });
            }else if(relaLen>lev1){
                //250-500
                var tempL=parseInt(relaLen)-lev1;
                var temp=tempL*250/(lev2-lev1);
                var i=Math.ceil(temp);
                i=base*Math.ceil(i/base);
                // temp=i*5;
                temp=i+250;
                _memSize.val(temp);
                _awCursor.find('.ng-binding').text(temp);
                _layer2.css({
                    width:relaLen
                });
                _awCursor.css({
                    left:relaLen
                });
            }else if(relaLen>=base){
                //5-250
                var tempL=parseInt(relaLen);
                // var i=Math.ceil(tempL/base);
                // tempL=i*base;
                var temp=250*tempL/lev1;
                var j=Math.ceil(temp/base);
                tempL=j*base;
                _memSize.val(tempL);
                _awCursor.find('.ng-binding').text(tempL);
                _layer2.css({
                    width:relaLen
                });
                _awCursor.css({
                    left:relaLen
                });
            }else{
                //<5 不合适
                _down.addClass('bk-number-disabled')
            }
        }
    }
    function inputChgeDrag(val,options){
        var _memSize=$('.memSize');
        var _awCursor=$('.awCursor');
        var _layer2=$('#layer2');
        var base=options.stepSize;
        var lev1=options.lev1;//0-250:206
        var lev2=options.lev2;//250-500:206+103
        var lev3=options.lev3;//500-1000:206+103+103
        var lenPerc;
        var i=Math.ceil(val/base);
        val=i*base;
        _memSize.val(val);
        if(val<base){
            _memSize.val(base);
            lenPerc=_memSize.val()*lev1/250;
            _awCursor.css({
                left:lenPerc
            });
        }else{
            if(val<=250){
                lenPerc=_memSize.val()*lev1/250;
                _awCursor.css({
                    left:lenPerc
                });
            }else if(val<=500){
                lenPerc=(_memSize.val()-250)*(lev2-lev1)/250;
                lenPerc+=lev1;
                _awCursor.css({
                    left:lenPerc
                });
            }else{
                if(val<=1000){
                    lenPerc=(_memSize.val()-500)*(lev2-lev1)/500;
                    lenPerc+=lev2;
                    _awCursor.css({
                        left:lenPerc
                    });
                }else{
                    _memSize.val(1000);
                    lenPerc=lev3+1;
                    _awCursor.css({
                        left:lenPerc
                    });
                }
            }
        }
        _awCursor.css({
            left:lenPerc
        });
        _layer2.css({
            width:lenPerc
        });
    } 
    //end 2015-04-10 by gm new add drag及相关 
	var TopBtnInit = function(){
		$("body",parent.document).find(".top-bar-btn").mouseenter(function(){
			$(this).width(88);
			$(this).html("管理控制台");
		}).mouseleave(function () {
			$(this).width(20);
			$(this).html("<i class=\"fa fa-home text-20\"></i>");
		})
	}
});




