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
	
    var common = require('../../common');
    var cn = new common();
   
    
    var DataHandler = function(){
    };

    module.exports = DataHandler;

    DataHandler.prototype = {
        DbListHandler : function(data){
        	$(".data-tr").remove();
        	
            var $tby = $('#tby');
            var array = data.data.data;
            if(array.length == 0){
            	cn.emptyBlock($tby);
            	if($("#paginatorBlock").length > 0){
            		$("#paginatorBlock").hide();
            	}
            }else{
            	 if($("#noData").length > 0){
            		 $("#noData").remove();
            	 }
            	 if($("#paginatorBlock").length > 0){
            		 $("#paginatorBlock").show();
            	 }
                for(var i= 0, len= array.length;i<len;i++){
                    var td1 = $("<td width=\"10\">"
                            + "<input type=\"checkbox\" name=\"mcluster_id\" value= \""+array[i].id+"\">"
                            + "</td>");
                    var swiftName = "";
                    if(cn.Displayable(array[i].status)){
                        swiftName = "<a href=\"/detail/oss/"+array[i].id+"\">" + array[i].name + "</a>"
                    }else{
                        swiftName = "<span class=\"text-explode font-disabled\">"+array[i].name+"</span>"
                    }
                    var td2 = $("<td class=\"padding-left-32\">"
                            + swiftName
                            +"</td>");
                    	var td3 = $("<td>"
                                + cn.TranslateStatus(array[i].status)
                                +"</td>");
                    var td4 = $("<td>"
                            + "<span>"+array[i].storeSize+"MB</span>"
                            + "</td>");
                    var td5 = $("<td><span>北京</span></td>");
                    var td6 = $("<td><span>"+array[i].hcluster.hclusterNameAlias+"</span></td>");
                    var td7 = $("<td><span><span></span><span class=\"text-success\">"+cn.TransDate('Y-m-d H:i:s',array[i].createTime)+"</span></span></td>");
                    var td8 = $("<td><span><span>包年  </span><span class=\"text-success\">"+cn.RemainAvailableTime(array[i].createTime)+"</span><span>天后到期</span></span></td>");
                    if(cn.Displayable(array[i].status)){
                    	var td9 = $("<td><a href=\"/detail/oss/"+array[i].id+"\">管理</a><span class=\"text-explode font-disabled\"></span></td>");
                    }else{
                    	var td9 = $("<td><span class=\"text-explode font-disabled\">管理</span></td>");
                    }
                    var tr = $("<tr class='data-tr'></tr>");
                    tr.append(td1).append(td2).append(td3).append(td4).append(td5).append(td6).append(td7).append(td8).append(td9);
                    tr.appendTo($tby);
                 }
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
        }/*,
        进度条进度控制
	    progress : function(dbId,data,asyncData){
	    	var data = data.data;	    	
   	        var unitLen = 100 / 8;
   	        var $obj = $("#prg" + dbId);
   	        var $prg = $obj.find(".progress-bar");
   	       	var pWidth = unitLen * data;
           	if( data == 1){
           		$prg.css({"width": pWidth + '%'});
           		$prg.html( pWidth + "%");
           		$obj.next().html("正在准备安装环境...");
           	}else if (data > 1 && data <= 3){
           		$prg.css({"width": pWidth + '%'});
           		$prg.html( pWidth + "%");
           		$obj.next().html("正在检查安装环境...");
           	}else if (data > 3 && data <= 6){
           		$prg.css({"width": pWidth + '%'});
           		$prg.html( pWidth + "%");
           		$obj.next().html("正在初始化数据库服务...");
           	}else if (data > 6 && data < 8){
           		$prg.css({"width": pWidth + '%'});
           		$prg.html( pWidth + "%");
           		$obj.next().html("正在创建数据库...");
           	}else if (data == 0 || data >= 8){
           		$prg.css({"width": "100%"});
           		$prg.html("100%");
           		$obj.next().html("创建完成");
           		asyncData();
           	}else if(data == -1){
           		$prg.css({"width": "100%"});
           		$prg.html("100%");
           		$obj.next().html("创建失败");
           		asyncData();
           	}else{
           		asyncData();
           	}
	   	}*/
    }
});