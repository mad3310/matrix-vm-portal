/**
 * Created by yaokuo on 2015/01/22.
 * backup page js
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
        	
            var $tby = $('#backupTbody');
            var array = data.data.data;

            for(var i= 0, len= array.length;i<len;i++){
                var td1 = $("<td>"
                        + cn.TransDate('Y-m-d H:i:s',array[i].startTime) 
                        + "/"
                        + cn.TransDate('Y-m-d H:i:s',array[i].endTime)
                        + "</td>");
                //var dbName = "";
                /*if(cn.Displayable(array[i].status)){
                    dbName = "<a href=\"/detail/db/"+array[i].id+"\">" + array[i].dbName + "</a>"
                }else{
                    dbName = "<span class=\"text-explode font-disabled\">"+array[i].dbName+"</span>"
                }*/
                var td2 = $("<td class=\"padding-left-32\">"
                        + array[i].strategy
                        +"</td>");
                var td3 = $("<td>"
                        + array[i].size
                        +"</td>");
                var td4 = $("<td>"
                        + array[i].method
                        + "</td>");
                var td5 = $("<td>"
                		+ array[i].backupType
                		+"</td>");
                var td6 = $("<td>"
                		+ array[i].pattern
                		+ "</td>");
                var td7 = $("<td><span>"
                		+ array[i].status
                		+ "</span></td>");
                var td8 = $("<td class=\"text-right\"> <div>"
                        + "<a class=\"dbuser-list-ip-privilege\" href=\"javascript:void(0);\">下载</a><span class=\"text-explode\">"
                        + "|</span><a class=\"dbuser-list-reset-password\"  href=\"javascript:void(0);\">创建临时实例</a><span class=\"text-explode\">"
                        + "|</span><a class=\"dbuser-list-modify-privilege\"  href=\"javascript:void(0);\">恢复</a><span class=\"text-explode\">"
                        + "</div></td>");
               /* if(cn.Displayable(array[i].status)){
                	var td9 = $("<td class=\"text-right\"><a href=\"/detail/db/"+array[i].id+"\">管理</a><span class=\"text-explode font-disabled\">|续费|升级</span></td>");
                }else{
                	var td9 = $("<td class=\"text-right\"><span class=\"text-explode font-disabled\">管理|续费|升级</span></td>");
                }*/
                var tr = $("<tr class='data-tr'></tr>");
                tr.append(td1).append(td2).append(td3).append(td4).append(td5).append(td6).append(td7).append(td8);
                tr.appendTo($tby);
            }
            /*
             * 设置分页数据
             */
            $("#totalRecords").html(data.data.totalRecords);
            $("#recordsPerPage").html(data.data.recordsPerPage);
            
            $('#paginator').bootstrapPaginator({
                currentPage: data.data.currentPage,
                totalPages:data.data.totalPages
            });
        }
    }
});