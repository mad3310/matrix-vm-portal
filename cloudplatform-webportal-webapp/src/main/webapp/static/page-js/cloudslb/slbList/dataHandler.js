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
	require('paginator')($);
	
    var common = require('../../common');
    var cn = new common();
   
    
    var DataHandler = function(){
    };

    module.exports = DataHandler;

    DataHandler.prototype = {
        SlbListHandler : function(data){
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
                            + "<input type=\"checkbox\" name=\"slbcluster_id\" value= \""+array[i].id+"\">"
                            + "</td>");
                    var slbName = "";
                    if(cn.Displayable(array[i].status)){
                        slbName = "<a href=\"/detail/slb/"+array[i].id+"\">" + array[i].slbName + "</a>"
                    }else{
                        slbName = "<span class=\"text-explode font-disabled\">"+array[i].slbName +"</span>"
                    }
                    var td2 = $("<td class=\"padding-left-32\">"
                            + slbName
                            +"</td>");
                    var td8 = $("<td class=\"padding-left-32 hidden-xs\">"
                            + "<span>"+cn.TableFilterNull(array[i].ip)+"</span>"
                            +"</td>");
                    var td9=$("<td class='hidden-xs'>-</td>");
                    if(array[i].slbConfigs != undefined || array[i].slbConfigs !=null){
                        td9 = $("<td class='hidden-xs'>"
                            +addPort(array[i].slbConfigs)
                            +"</td>")
                    }
                    var td4="<td class='hidden-xs'></td>";
                    if(array[i].hcluster != undefined && array[i].hcluster != null){
                        var td4 = $("<td class='hidden-xs'>"
                        + "<span>"+array[i].hcluster.hclusterNameAlias+"</span></td>"
                        + "</td>");
                    }
                    var td5 = $("<td><span>"+cn.TranslateStatus(array[i].status)+"</span></td>");
                    var td6 = $("<td class='hidden-xs'><span><span>包年  </span><span class=\"text-success\">"+cn.RemainAvailableTime(array[i].createTime)+"</span><span>天后到期</span></span></td>");
                    var td7 = $("<td></td>");
                    if(array[i].status == 6){
                    	td7 = $("<td class=\"text-right hidden-xs\"><a href=\"/detail/slb/"+array[i].id+"\">管理</a>"
                        		+ "<span class=\"text-explode font-disabled\">|</span>"
                    			+ "<a href=\"javascript:void(0)\"><span class=\"text-explode slb-stop\" >停止</span></a>"
                    			+ "<span class=\"text-explode font-disabled\">|</span>"
                    			+ "<a href=\"javascript:void(0)\"><span class=\"text-explode slb-restart\">重启</span></a>"
                    			+ "<span class=\"text-explode font-disabled\">|续费|升级|删除</span></td>"
                                // +"<td class=\"hidden-sm hidden-md hidden-lg\">"
                                // +"<div class='pull-right'>"                 
                                // +"<a href=\"/detail/slb/"+array[i].id+"\"><span class='text-success'><i class='fa fa-cogs'></i></span></a>&nbsp;&nbsp;"
                                // +"<a><span class=\"text-explode\"><i class='fa fa-dot-circle-o'></i></span></a>&nbsp;&nbsp;"
                                // +"<a><span class='text-explode font-disabled'><i class='fa fa-repeat'></i></span></a>&nbsp;&nbsp;"
                                // +"<a><span class=\"text-explode font-disabled\"><i class='fa fa-shopping-cart'></i></span></a>&nbsp;&nbsp;"
                                // +"<a><span class='text-explode font-disabled'><i class='fa fa-upload'></i></span></a>&nbsp;&nbsp;<a><span class='text-explode font-disabled'><i class='fa fa-trash'></i></span></a></div></td>"

                                +"<td class='text-right hidden-sm hidden-md hidden-lg'><div class='hidden-sm hidden-md hidden-lg pull-right m-tgbtn dropdown'><button type='button' class='dropdown-toggle' data-toggle='dropdown'><i class='fa fa-navicon'></i></button>"                 
                                +"<ul class='dropdown-menu'><li><a href=\"/detail/slb/"+array[i].id+"\"><span class='text-success'><i class='fa fa-cogs'></i></span></a></li>"
                                +"<li><a><span class=\"text-explode slb-stop text-warning\"><i class='fa fa-dot-circle-o'></i></span></a></li>"
                                +"<li><a><span class='text-explode slb-restart text-primary'><i class='fa fa-repeat'></i></span></a></li>"
                                +"<li><a><span class=\"text-explode font-disabled\"><i class='fa fa-shopping-cart'></i></span></a></li>"
                                +"<li><a><span class='text-explode font-disabled'><i class='fa fa-upload'></i></span></a></li><li><a><span class='text-explode font-disabled'><i class='fa fa-trash'></i></span></a></li></ul></div></td>" 
                    			/*+"<a href=\"javascript:void(0)\"><span class=\"text-explode slb-delete\">删除</span></a></td>"*/);
                    }else if(array[i].status == 9){
                    	td7 = $("<td class=\"text-right hidden-xs\"><span class=\"text-explode font-disabled\">管理</span>"
	                    		+ "<span class=\"text-explode font-disabled\">|</span>"
	                			+ "<a href=\"javascript:void(0)\"><span class=\"text-explode slb-start\">启动</span></a>"
	                			+ "<span class=\"text-explode font-disabled\">|续费|升级|删除</span></td>" 
                                // +"<td class=\"hidden-sm hidden-md hidden-lg\">"
                                // +"<div class='pull-right'>"                 
                                // +"<a href=\"/detail/slb/"+array[i].id+"\"><span class='text-success'><i class='fa fa-cogs'></i></span></a>&nbsp;&nbsp;"
                                // +"<a><span class='text-explode slb-start text-success'><i class='fa fa-play-circle'></i></span></a>&nbsp;&nbsp;"
                                // +"<a><span class=\"text-explode font-disabled\"><i class='fa fa-shopping-cart'></i></span></a>&nbsp;&nbsp;"
                                // +"<a><span class='text-explode font-disabled'><i class='fa fa-upload'></i></span></a>&nbsp;&nbsp;<a><span class='text-explode font-disabled'><i class='fa fa-trash'></i></span></a></div></td>"

                                +"<td class='text-right hidden-sm hidden-md hidden-lg'><div class='hidden-sm hidden-md hidden-lg pull-right m-tgbtn dropdown'><button type='button' class='dropdown-toggle' data-toggle='dropdown'><i class='fa fa-navicon'></i></button>"                 
                                +"<ul class='dropdown-menu'><li><a href=\"/detail/slb/"+array[i].id+"\"><span class='text-success'><i class='fa fa-cogs'></i></span></a></li>"
                                +"<li><a><span class='text-explode slb-start text-success'><i class='fa fa-play-circle'></i></span></a></li>"
                                +"<li><a><span class=\"text-explode font-disabled\"><i class='fa fa-shopping-cart'></i></span></a></li>"
                                +"<li><a><span class='text-explode font-disabled'><i class='fa fa-upload'></i></span></a></li><li><a><span class='text-explode font-disabled'><i class='fa fa-trash'></i></span></a></li></ul></div></td>" 
	                			/*+"<a href=\"javascript:void(0)\"><span class=\"text-explode slb-delete\">删除</span></a></td>"*/);
                    }else if(array[i].status == 5){
                    	td7 = $("<td class=\"text-right hidden-xs\"><span class=\"text-explode font-disabled\">管理</span>"
	                    		+ "<span class=\"text-explode font-disabled\">|</span>"
	                			+ "<a href=\"javascript:void(0)\"><span class=\"text-explode slb-restart\">重启</span></a>"
	                			+ "<span class=\"text-explode font-disabled\">|续费|升级|删除</span></td>"
                                // +"<td class=\"hidden-sm hidden-md hidden-lg\">"
                                // +"<div class='pull-right'>"                 
                                // +"<a href=\"/detail/slb/"+array[i].id+"\"><span class='font-disabled'><i class='fa fa-cogs'></i></span></a>&nbsp;&nbsp;"
                                // +"<a><span class='text-explode slb-restart text-success'><i class='fa fa-repeat'></i></span></a>&nbsp;&nbsp;"
                                // +"<a><span class=\"text-explode font-disabled\"><i class='fa fa-shopping-cart'></i></span></a>&nbsp;&nbsp;"
                                // +"<a><span class='text-explode font-disabled'><i class='fa fa-upload'></i></span></a>&nbsp;&nbsp;<a><span class='text-explode font-disabled'><i class='fa fa-trash'></i></span></a></div></td>"

                                +"<td class='text-right hidden-sm hidden-md hidden-lg'><div class='hidden-sm hidden-md hidden-lg pull-right m-tgbtn dropdown'><button type='button' class='dropdown-toggle' data-toggle='dropdown'><i class='fa fa-navicon'></i></button>"                 
                                +"<ul class='dropdown-menu'><li><a href=\"/detail/slb/"+array[i].id+"\"><span class='font-disabled'><i class='fa fa-cogs'></i></span></a></li>"
                                +"<li><a><span class='text-explode slb-restart text-success'><i class='fa fa-repeat'></i></span></a></li>"
                                +"<li><a><span class=\"text-explode font-disabled\"><i class='fa fa-shopping-cart'></i></span></a></li>"
                                +"<li><a><span class='text-explode font-disabled'><i class='fa fa-upload'></i></span></a></li><li><a><span class='text-explode font-disabled'><i class='fa fa-trash'></i></span></a></li></ul></div></td>" 
	                			/*+"<a href=\"javascript:void(0)\"><span class=\"text-explode slb-delete\">删除</span></a></td>"*/);
                    }else{
                    	td7 = $("<td class=\"text-right hidden-xs\"><span class=\"text-explode font-disabled\">管理|续费|升级|删除</span></td>" 
                                // +"<td class=\"hidden-sm hidden-md hidden-lg\">"
                                // +"<div class='pull-right'>"                 
                                // +"<a href=\"/detail/slb/"+array[i].id+"\"><span class='font-disabled'><i class='fa fa-cogs'></i></span></a>&nbsp;&nbsp;"
                                // +"<a><span class='text-explode font-disabled'><i class='fa fa-repeat'></i></span></a>&nbsp;&nbsp;"
                                // +"<a><span class=\"text-explode font-disabled\"><i class='fa fa-shopping-cart'></i></span></a>&nbsp;&nbsp;"
                                // +"<a><span class='text-explode font-disabled'><i class='fa fa-upload'></i></span></a>&nbsp;&nbsp;<a><span class='text-explode font-disabled'><i class='fa fa-trash'></i></span></a></div></td>"

                                +"<td class='text-right hidden-sm hidden-md hidden-lg'><div class='hidden-sm hidden-md hidden-lg pull-right m-tgbtn dropdown'><button type='button' class='dropdown-toggle' data-toggle='dropdown'><i class='fa fa-navicon'></i></button>"                 
                                +"<ul class='dropdown-menu'>"
                                +"<li><a href=\"/detail/slb/"+array[i].id+"\"><span class='font-disabled'><i class='fa fa-cogs'></i></span></a></li>"
                                +"<li><a><span class='text-explode font-disabled'><i class='fa fa-repeat'></i></span></a></li>"
                                +"<li><a><span class=\"text-explode font-disabled\"><i class='fa fa-shopping-cart'></i></span></a></li>"
                                +"<li><a><span class='text-explode font-disabled'><i class='fa fa-upload'></i></span></a></li><li><a><span class='text-explode font-disabled'><i class='fa fa-trash'></i></span></a></li></ul></div></td>" 
                    			/*+"<a href=\"javascript:void(0)\"><span class=\"text-explode slb-delete\">删除</span></a></td></td>"*/);
                    }
                    
                    var tr = $("<tr class='data-tr'></tr>");
                    tr.append(td1).append(td2).append(td8).append(td9).append(td4).append(td5).append(td6).append(td7);
                    tr.appendTo($tby);
                 }
            	$(".slb-start").click(function(){
            		var data = {id : $(this).closest("tr").find("[name=slbcluster_id]").val()};
            		var url = "/slb/start";
            		cn.PostData(url,data,function(){
            			window.location.href = "/list/slb";
            		});
            	});
            	$(".slb-restart").click(function(){
            		var data = {id : $(this).closest("tr").find("[name=slbcluster_id]").val()};
            		var url = "/slb/restart";
            		cn.PostData(url,data,function(){
            			window.location.href = "/list/slb";
            		});
            	});
            	$(".slb-stop").click(function(){
            		var data = {id : $(this).closest("tr").find("[name=slbcluster_id]").val()};
            		var url = "/slb/stop";
            		cn.PostData(url,data,function(){
            			window.location.href = "/list/slb";
            		});
            	});
            	 $(".slb-delete").click(function () {    
            	var id =$(this).closest("tr").find("input").val();
            	var name = $(this).closest("tr").find("td:eq(1)").html();
                var title = "确认";
                var text = "您确定要删除("+name+")此应用";
                cn.DialogBoxInit(title,text,slbDelete,id);
            })
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
        }
    }
    function addPort(data){
        if(data ==null || data.length == 0){
            return "-";
        }
        var ret="";
        for(var i= 0,len=data.length;i<len;i++){
            ret = ret
            +"<span class=\"text-success\">"+data[i].agentType+"</span>"
            +"<span class=\"text-success\">:</span>"
            +"<span class=\"text-success\">"+data[i].frontPort+"</span><br>"
        }
        return ret;
    };
    function slbDelete(id){
    	var url = "/slb/"+id;
            cn.DeleteData(url, function () {
                location.href="/list/slb";
            });
    }
});