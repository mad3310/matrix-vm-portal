/**
 * Created by yaokuo on 2014/12/14.
 */
define(function(require,exports,module){
    var $ = require('jquery');
    var common = require('../../common');
    var cn = new common();

    var DataHandler = function(){
    };

    module.exports = DataHandler;

    DataHandler.prototype = {
        SlbConfigHandler : function(data){
            $(".data-tr").remove();
            var $tby = $('#tby');
            var array = data.data.data;
            if(array.length == 0){
            	$("#addButton").removeClass("hidden");
            	return
            }
            for(var i= 0, len= array.length;i<len;i++){
                var td1 = $("<td>"
                        + array[i].agentType + " : " + array[i].frontPort
                        + "</td>");
                var td3 = $("<td>"
                        + "<span class=\"text-success\">运行中</span>"
                        + "</td>");
                var td4 = $("<td class='hidden-xs'>"
                        + "<span>轮询模式</span>"
                        + "</td>");
                var td5 = $("<td class='hidden-xs'>"
                        + "<span class=\"text-success\">已启用</span>"
                        + "</td>");
                var td6 = $("<td class='hidden-xs'>"
                        + "<span class=\"text-success\">已启用</span>"
                        + "</td>");
                var td7 = $("<td class='hidden-xs'>"
                        + "<span class=\"text-success\">已启用</span>"
                        + "</td>");
                var td8 = $("<td class='hidden-xs'>"
                        + "<span>100 Mbps</span>"
                        + "</td>");
                var td9 = $("<td class=\"text-right hidden-xs\">"
                		+"<a data-backdrop=\"false\" data-toggle=\"modal\" data-target=\"#modifyModal\" href><span class=\"text-explode slb-config-modify\" configId=\""+array[i].id+"\">编辑</span></a>" 
                		+"<span class=\"text-explode font-disabled\">|删除</span>" 
                		+"</td>"
                        +"<td class=\"text-right hidden-sm hidden-md hidden-lg\">"
                        +"<div class='pull-right'>"                 
                        +"<a data-backdrop=\"false\" data-toggle=\"modal\" data-target=\"#modifyModal\" href><span class='text-explode slb-config-modify glyphicon glyphicon-edit' configId=\""+array[i].id+"\"></span></a>&nbsp;&nbsp;"
                        +"<a><span class='text-explode font-disabled glyphicon glyphicon-trash'></span></a></div></td>"
                        );
                var tr = $("<tr class='data-tr'></tr>");

                tr.append(td1).append(td3).append(td4).append(td5).append(td6).append(td7).append(td8).append(td9);
                tr.appendTo($tby);
            }
            $(".slb-config-modify").click(function(){
        			$("#id").val($(this).attr('configId'));
        	});
        }
    }
});