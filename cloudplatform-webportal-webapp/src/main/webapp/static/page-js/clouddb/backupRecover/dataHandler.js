/**
 * Created by jinglinlin@letv.com on 2015/01/22.
 * backup page js
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
        DbListHandler : function(data){
        	$(".data-tr").remove();
            var $tby = $('#backupTbody');
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
                     var td1 = $("<td class='hidden-xs'>"
                             + cn.TransDate('Y-m-d H:i:s',array[i].startTime) 
                             + "/"
                             + cn.TransDate('Y-m-d H:i:s',array[i].endTime)
                             + "</td><td class='hidden-sm hidden-md hidden-lg'>"
                             + cn.TransDate('Y-m-d H:i:s',array[i].startTime) 
                             + "<br>"
                             + cn.TransDate('Y-m-d H:i:s',array[i].endTime)+"</td>");
                     var td2 = $("<td class=\"padding-left-32 hidden-xs\">"
                             + array[i].backupProxy
                             +"</td>");
                     var td3 = $("<td class='hidden-xs'>"
                     		+ "---"
                             //+ cn.FilterNull(array[i].size)
                             +"</td>");
                     var td4 = $("<td class='hidden-xs'>"
                             + array[i].backupMethod
                             + "</td>");
                     var td5 = $("<td class='hidden-xs'>"
                     		+ array[i].backupType
                     		+"</td>");
                     var td6 = $("<td class='hidden-xs'>"
                     		+ array[i].workType
                     		+ "</td>");
                     var td7 = $("<td><span>"
                     		+ cn.TranslateStatus(array[i].status)
                     		+ "</span></td>");
                     var td8 = $("<td class=\"text-right\"> <div class='hidden-xs'>"
                             + "<span class=\"text-explode font-disabled\" href=\"javascript:void(0);\">下载</span><span class=\"text-explode\">"
                             + "|</span><span class=\"text-explode font-disabled\" href=\"javascript:void(0);\">创建临时实例</span><span class=\"text-explode\">"
                             + "|</span><span class=\"text-explode font-disabled\"  href=\"javascript:void(0);\">恢复</span><span class=\"text-explode\">"
                             + "</div>"
                             +"<div class='hidden-sm hidden-md hidden-lg pull-right'>"                 
                             +"<span class=\"text-explode font-disabled\" href=\"javascript:void(0);\"><i class='fa fa-download'></i></span>&nbsp;&nbsp;"
                             +"<span class=\"text-explode font-disabled\" href=\"javascript:void(0);\"><i class='fa fa-plus-square'></i></span>&nbsp;&nbsp;"
                             +"<span class=\"text-explode font-disabled\"  href=\"javascript:void(0);\"><i class='fa fa-power-off'></i></span></div></td>");
                             // +"<div class='hidden-sm hidden-md hidden-lg pull-right m-tgbtn dropdown'><button type='button' class='dropdown-toggle' data-toggle='dropdown'><i class='fa fa-navicon'></i></button>"                 
                             // +"<ul class='dropdown-menu m-dropdown-caret'><li><span class=\"text-explode font-disabled\" href=\"javascript:void(0);\"><i class='fa fa-download'></i></span></li>"
                             // +"<li><span class=\"text-explode font-disabled\" href=\"javascript:void(0);\"><i class='fa fa-plus-square'></i></span></li>"
                             // +"<li><span class=\"text-explode font-disabled\"  href=\"javascript:void(0);\"><i class='fa fa-power-off'></i></span></li></ul></div></td>");
                     var tr = $("<tr class='data-tr'></tr>");
                     tr.append(td1).append(td2).append(td3).append(td4).append(td5).append(td6).append(td7).append(td8);
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
        }
    }
});