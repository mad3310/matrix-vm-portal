define(function(require,exports,module){
    var $ = require('jquery');
    /*
	 * 引入相关js，使用分页组件
	 */
	require('bootstrap');
	require('paginator')($);
	
    var common = require('../../common');
    var cn = new common();
   
    
    var DataHandler = function(){
    };

    module.exports = DataHandler;

    DataHandler.prototype = {
        CacheListHandler : function(data){
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
                            + "<input type=\"checkbox\" name=\"cache_id\" value= \""+array[i].id+"\">"
                            + "</td>");
                    var cacheName = "";
                    if(cn.Displayable(array[i].status)){
                        cacheName = "<a href=\"/detail/cache/"+array[i].id+"\">" + array[i].bucketName + "</a>"
                    }else{
                        cacheName = "<span class=\"text-explode font-disabled\">"+array[i].bucketName+"</span>"
                    }
                    var td2 = $("<td class=\"padding-left-32\">"
                            + cacheName
                            +"</td>");
                    
                    var td3 = '';                
                    if(array[i].status == 2){
                    	td3 = $("<td>"
                    			+ "<div class=\"progress\" id= \"prg"+ array[i].id + "\">"
                    			+ "<div class=\"progress-bar\" role=\"progressbar\" aria-valuenow=\"60\" aria-valuemin=\"0\" aria-valuemax=\"100\" style=\"width: 0;\">"
                    			+ "</div>"
                    			+ "</div>"
                    			+ "<span class=\"progress-info\"></span>"
                                + "<input class=\"hide\" type=\"text\" name=\"progress_db_id\" id= \""+ array[i].id + "\" value= \""+ array[i].id + "\" >"
                                + "</td>");
                    }else{
                    	td3 = $("<td>"
                                + cn.TranslateStatus(array[i].status)
                                +"</td>");
                    }
                    var td4=$("<td>"
                            + "<span>实例类型</span>"
                            + "</td>");
                    if(array[i].bucketType==0){
                      td4=$("<td>"
                            + "<span>持久化</span>"
                            + "</td>");
                    }else if(array[i].bucketType==1){
                      td4=$("<td>"
                            + "<span>非持久化</span>"
                            + "</td>");
                    }
                    // var td5 = $('<td><span><div class="progress" style="margin-bottom:0;"><div class="progress-bar progress-bar-info progress-bar-striped" role="progressbar" aria-valuenow="20" aria-valuemin="0" aria-valuemax="100" style="width: 40%"><span class="sr-only">40%</span></div></div></span></td>');
                    var td5=$("<td><span>"+array[i].ramQuotaMB/1024+"GB</span></td>");
                    var td6 = $("<td><span >北京</span></td>");
                    var td7 = $("<td><span>"+array[i].hcluster.hclusterNameAlias+"</span></td>");
                    if(array[i].cbaseCluster == null){//服务集群array[i].hcluster.***
                    	var td8 = $("<td></td>");
                    }else{
                    	var td8 = $("<td><span>"+cn.FilterNull(array[i].cbaseCluster.cbaseClusterName)+"</span></td>")
                    }
                    var td9 = $("<td><span>"+cn.TransDate('Y-m-d H:i:s',array[i].createTime)+"</span></td>")
                    var td10 = $("<td><span><span>包年  </span><span class=\"text-success\">"+cn.RemainAvailableTime(array[i].createTime)+"</span><span>天后到期</span></span></td>");
                    if(cn.Displayable(array[i].status)){
                    	var td11 = $("<td><a href=\"/detail/cache/"+array[i].id+"\">管理</a><span class=\"text-explode font-disabled\">|扩容|续费</span></td>");
                    }else{
                    	var td11 = $("<td><span class=\"text-explode font-disabled\">管理|扩容|续费</span></td>");
                    }
                    var tr = $("<tr class='data-tr'></tr>");
                    
                    tr.append(td1).append(td2).append(td3).append(td4).append(td5).append(td6).append(td7).append(td8).append(td9).append(td10).append(td11);
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
        },
        /*进度条进度控制*/
	    progress : function(cacheId,data,asyncData){
	    	var data = data.data;
        // console.log(data)	    	
   	        var unitLen = Math.ceil(100 / 10);
   	        var $obj = $("#prg" + cacheId);
   	        var $prg = $obj.find(".progress-bar");
   	       	var pWidth = unitLen * data;
           	if( data == 1){
           		$prg.css({"width": pWidth + '%'});
           		$prg.html( pWidth + "%");
           		$obj.next().html("正在准备环境...");
           	}else if (data > 1 && data <= 2){
           		$prg.css({"width": pWidth + '%'});
           		$prg.html( pWidth + "%");
           		$obj.next().html("正在检查环境...");
           	}else if (data > 2 && data <= 8){
           		$prg.css({"width": pWidth + '%'});
           		$prg.html( pWidth + "%");
           		$obj.next().html("正在初始化缓存服务...");
           	}else if (data > 8 && data <= 9){
           		$prg.css({"width": pWidth + '%'});
           		$prg.html( pWidth + "%");
           		$obj.next().html("正在创建缓存实例...");
           	}else if (data == 0){
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
	   	}
    }
});