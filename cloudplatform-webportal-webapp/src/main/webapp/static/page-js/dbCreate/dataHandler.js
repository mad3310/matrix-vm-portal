/**
 * Created by yaokuo on 2014/12/14.
 */
define(function(require,exports,module){
    var $ = require('jquery');

    var DataHandler = function(){
    };

    module.exports = DataHandler;

    DataHandler.prototype = {
        DbListHandler : function(data){
            var $tby = $('#tby');
            var array = data.data.data;

            for(var i= 0, len= array.length;i<len;i++){
                var td1 = $("<td width=\"10\">"
                        + "<input type=\"checkbox\">"
                        + "</td>");
                var td2 = $("<td class=\"padding-left-32\">"
                        + "<div>"
                        + "<div>"
                        + "<a href=\"/detail/baseInfo/${dbId}/"+array[i].id+"\">" + array[i].dbName + "</a><br>"
                        + "<span text-length=\"26\">" + array[i].dbName + "</span>"
                        + "<a class=\"btn btn-default btn-xs glyphicon glyphicon-pencil\" href=\"#\"></a>"
                        +"</div>"
                        +"</div>"
                        +"</td>");
                var td3 = $("<td>"
                        + "<span tooltip=\"\" class=\"text-success\">"+array[i].status+"</span>"
                        +"</td>");
                var td4 = $("<td>"
                        + "<span>专享</span>"
                        + "</td>");
                var td5 = $("<td><span>"+array[i].engineType+"</span></td>");
                var td6 = $("<td><span >单可用区</span></td>");
                var td7 = $("<td>北京<span>"+array[i].hcluster.hclusterNameAlias+"</span></td>");
                var td8 = $("<td><span><span>包月</span><span class=\"text-success\">365</span><span> 天后到期</span></span></td>");
                var td9 = $("<td class=\"text-right\"> <div><a href=\"/detail/baseInfo/${dbId}\">管理</a><span class=\"text-explode\">|</span><a href=\"#\" target=\"_blank\">续费</a><span class=\"text-explode\">|</span><a href=\"#\">升级</a> </div></td>");
                var tr = $("<tr></tr>");
                tr.append(td1).append(td2).append(td3).append(td4).append(td5).append(td6).append(td7).append(td8).append(td9);
                tr.appendTo($tby);
            }
        }
    }
});