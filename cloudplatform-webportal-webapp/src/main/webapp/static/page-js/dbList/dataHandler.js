/**
 * Created by yaokuo on 2014/12/14.
 * dblist page 
 */
define(function(require,exports,module){
    var $ = require('jquery');
    /*
	 * 引入相关js，使用分页组件
	 */
	require('bootstrap');
	require('paginator');
	
    var common = require('../common');
    var cn = new common();
    
    var DataHandler = function(){
    };

    module.exports = DataHandler;

    DataHandler.prototype = {
        DbListHandler : function(data){
        	$(".data-tr").remove();
        	
            var $tby = $('#tby');
            var array = data.data.data;
           
            for(var i= 0, len= array.length;i<len;i++){
                var td1 = $("<td width=\"10\">"
                        + "<input type=\"checkbox\" name=\"mcluster_id\" value= \""+array[i].id+"\">"
                        + "</td>");
                var dbName = "";
                if(cn.Displayable(array[i].status)){
                    dbName = "<a href=\"/detail/db/"+array[i].id+"\">" + array[i].dbName + "</a>"
                }else{
                    dbName = "<span class=\"text-explode font-disabled\">"+array[i].dbName+"</span>"
                }
                var td2 = $("<td class=\"padding-left-32\">"
                        + dbName
                        +"</td>");
                
                var td3 = '';
                if(array[i].status == 6){
                	var td3 = $("<td>"
                            + "<div id=\"Grise\">"
                            + "<div id=\"progress\">"
                            + "<div id=\"charge\"></div>"
                            + "</div>"
                            + "<div id=\"load\">"
                            + "<p></p>"
                            + "</div>"
                            + "</div>"
                            +"</td>");
                	var dbId = array[i].id
                	var url = "/build/db/" + dbId;
                	var dblistHandler = new DataHandler();
                	setInterval(cn.GetLocalData(url,dblistHandler.progress),100);
                	//setInterval(progress(url),1000);
                }else{
                	var td3 = $("<td>"
                            + cn.TranslateStatus(array[i].status)
                            +"</td>");
                }
                var td4 = $("<td>"
                        + "<span>专享</span>"
                        + "</td>");
                var td5 = $("<td><span>MySQL5.5</span></td>");
                var td6 = $("<td><span >单可用区</span></td>");
                var td7 = $("<td><span>"+array[i].hcluster.hclusterNameAlias+"</span></td>");
                var td8 = $("<td><span><span>包年  </span><span class=\"text-success\">"+cn.RemainAvailableTime(array[i].createTime)+"</span><span>天后到期</span></span></td>");
                if(cn.Displayable(array[i].status)){
                	var td9 = $("<td class=\"text-right\"><a href=\"/detail/db/"+array[i].id+"\">管理</a><span class=\"text-explode font-disabled\">|续费|升级</span></td>");
                }else{
                	var td9 = $("<td class=\"text-right\"><span class=\"text-explode font-disabled\">管理|续费|升级</span></td>");
                }
                var tr = $("<tr class='data-tr'></tr>");
                
                tr.append(td1).append(td2).append(td3).append(td4).append(td5).append(td6).append(td7).append(td8).append(td9);
                tr.appendTo($tby);
             }
       
            /*
             * 设置分页数据
             */
            $("#totalRecords").html(data.data.totalRecords);
            $("#recordsPerPage").html(data.data.recordsPerPage);
            
            if(data.data.totalPages < 1){
        		data.data.totalPages = 1;
        	};
            $('#paginator').bootstrapPaginator({
                currentPage: data.data.currentPage,
                totalPages:data.data.totalPages
            });
        },
	    progress : function(data){
	    	var data = data.data;
	        var unitLen = 100 / 16;
	    	if(data == 1){
	         	$("#charge").css({"width": unitLen + 'px'});
	         	$("#load p").html("环境准备");
	         }else if(data == 2){
	         	$("#charge").css({"width": (unitLen * 2) + 'px'});
	         	$("#load p").html("环境准备");
	         }else if(data == 3){
	         	$("#charge").css({"width": (unitLen * 3) + 'px'});
	         	$("#load p").html("环境准备");
	         }else if(data == 4){
	         	$("#charge").css({"width": (unitLen * 4) + 'px'});
	         	$("#load p").html("环境准备");
	         }else if(data == 5){
	         	$("#charge").css({"width": (unitLen * 5) + 'px'});
	         	$("#load p").html("软件安装");
	         }else if(data == 6){
	         	$("#charge").css({"width": (unitLen * 6) + 'px'});
	         	$("#load p").html("软件安装");
	         }else if(data == 7){
	         	$("#charge").css({"width": (unitLen * 7) + 'px'});
	         	$("#load p").html("软件安装");
	         }else if(data == 8){
	         	$("#charge").css({"width": (unitLen * 8) + 'px'});
	         	$("#load p").html("软件安装");
	         }else if(data == 9){
	         	$("#charge").css({"width": (unitLen * 9) + 'px'});
	         	$("#load p").html("服务初始化");
	         }else if(data == 10){
	         	$("#charge").css({"width": (unitLen * 10) + 'px'});
	         	$("#load p").html("服务初始化");
	         }else if(data == 11){
	         	$("#charge").css({"width": (unitLen * 11) + 'px'});
	         	$("#load p").html("服务初始化");
	         }else if(data == 12){
	         	$("#charge").css({"width": (unitLen * 12) + 'px'});
	         	$("#load p").html("服务初始化");
	         }else if(data == 13){
	         	$("#charge").css({"width": (unitLen * 13) + 'px'});
	         	$("#load p").html("数据初始化");
	         }else if(data == 14){
	         	$("#charge").css({"width": (unitLen * 14) + 'px'});
	         	$("#load p").html("数据初始化");
	         }else if(data == 15){
	         	$("#charge").css({"width": (unitLen * 15) + 'px'});
	         	$("#load p").html("数据初始化");
	         }else if(data == 16){
	         	$("#charge").css({"width": (unitLen * 15) + 'px'});
	         	$("#load p").html("数据初始化");
	         }else if(data == 0){
	         	$("#charge").css({"width": "100%"});
	         	$("#load p").html("创建完成");
	         }else{
	         	$("#charge").css({"width": "100%"});
	         	$("#load p").html("创建失败");
	         }
	    }
    }

});