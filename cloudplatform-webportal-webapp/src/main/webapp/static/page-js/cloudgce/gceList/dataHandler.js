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
        GceListHandler : function(data){
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
                            + "<input type=\"checkbox\" name=\"gcecluster_id\" value= \""+array[i].id+"\">"
                            + "</td>");
                    var gceName = "";
                    if(cn.Displayable(array[i].status)){
                        gceName = "<a href=\"/detail/gce/"+array[i].id+"\">" + array[i].gceName + "</a>"
                    }else{
                        gceName = "<span class=\"text-explode font-disabled\">"+array[i].gceName +"</span>"
                    }
                    var td2 = $("<td class=\"padding-left-32\">"
                            + gceName
                            +"</td>");
                    var td3 = $("<td><span>"+cn.TranslateStatus(array[i].status)+"</span></td>");
                    var td4 = $("<td><span>"+array[i].type+"</span></td>");
                    var td5 = $("<td class=\"padding-left-32\">"
                            + "<span>"+getAccpetAddr(array[i].gceContainers)+"</span>"
                            +"</td>");
                    var td7="<td></td>";
                    if(array[i].hcluster != undefined && array[i].hcluster != null){
                        var td7 = $("<td>"
                        + "<span>"+array[i].hcluster.hclusterNameAlias+"</span></td>"
                        + "</td>");
                    }
                    
                    var td8 = $("<td><span><span>包年  </span><span class=\"text-success\">"+cn.RemainAvailableTime(array[i].createTime)+"</span><span>天后到期</span></span></td>");
                    var td9 = $("<td></td>");
                    if(array[i].status == 6){
                    	td9 = $("<td class=\"text-right\"><a href=\"/detail/gce/"+array[i].id+"\">管理</a>"
                        		+ "<span class=\"text-explode font-disabled\">|</span>"
                    			+ "<a href=\"javascript:void(0)\"><span class=\"text-explode gce-stop\" >停止</span></a>"
                    			+ "<span class=\"text-explode font-disabled\">|</span>"
                    			+ "<a href=\"javascript:void(0)\"><span class=\"text-explode gce-restart\">重启</span></a>"
                    			+ "<span class=\"text-explode font-disabled\">|续费|升级</span></td>");
                    }else if(array[i].status == 9){
                    	td9 = $("<td class=\"text-right\"><span class=\"text-explode font-disabled\">管理</span>"
                    		+ "<span class=\"text-explode font-disabled\">|</span>"
                			+ "<a href=\"javascript:void(0)\"><span class=\"text-explode gce-start\">启动</span></a>"
                			+ "<span class=\"text-explode font-disabled\">|续费|升级</span></td>");
                    }else if(array[i].status == 5){
                    	td9 = $("<td class=\"text-right\"><span class=\"text-explode font-disabled\">管理</span>"
                    		+ "<span class=\"text-explode font-disabled\">|</span>"
                			+ "<a href=\"javascript:void(0)\"><span class=\"text-explode gce-restart\">重启</span></a>"
                			+ "<span class=\"text-explode font-disabled\">|续费|升级</span></td>");
                    }else{
                    	td9 = $("<td class=\"text-right\"><span class=\"text-explode font-disabled\">管理|续费|升级</span></td>");
                    }
                     var tr = $("<tr class='data-tr'></tr>");
                    tr.append(td1).append(td2).append(td3).append(td4).append(td5).append(td7).append(td8).append(td9);
                    tr.appendTo($tby);
	            }
	             $(".gce-start").click(function(){
	            		var data = {id : $(this).closest("tr").find("[name=gcecluster_id]").val()};
	            		var url = "/gce/start";
	            		console.log(data.id);
	            		cn.PostData(url,data,function(){
	            			window.location.href = "/list/gce";
	            		});
	            	});
	            	$(".gce-restart").click(function(){
	            		var data = {id : $(this).closest("tr").find("[name=gcecluster_id]").val()};
	            		var url = "/gce/restart";
	            		cn.PostData(url,data,function(){
	            			window.location.href = "/list/gce";
	            		});
	            	});
	            	$(".gce-stop").click(function(){
	            		var data = {id : $(this).closest("tr").find("[name=gcecluster_id]").val()};
	            		var url = "/gce/stop";
	            		cn.PostData(url,data,function(){
	            			window.location.href = "/list/gce";
	            		});
	            	});
	       
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
	        }
	    }
    }
    function getAccpetAddr(data){
        if(data == null || data.length == 0){
            return "-";
        }
        
        var ret="";
        for(var i= 0,len=data.length;i<len;i++){
        	var hostPortArr = data[i].bingHostPort.split(',');
        	var containerPortArr = data[i].bindContainerPort.split(',');

        	var servicePort;
        	for(var j = 0,len = hostPortArr.length;j<len;j++){
        		if(containerPortArr[j] == "8080" ||containerPortArr[j] == "8001"){
        			servicePort = {
        					hostPort:hostPortArr[j],
        					containerPort:containerPortArr[j]
        			};
        		}else{
        			continue;
        		}
        	}
            ret = ret
            +"<span class=\"text-success\">"+data[i].hostIp+"</span>"
            +"<span class=\"text-success\">:</span>"
            +"<span class=\"text-success\">"+servicePort.hostPort+"</span><br>"
        }
        return ret;
    }
});