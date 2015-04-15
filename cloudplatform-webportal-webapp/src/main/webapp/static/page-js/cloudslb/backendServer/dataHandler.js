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
        ServerHandler : function(data){
            $(".data-tr").remove();
            var $tby = $('#tby');
            var array = data.data.data;
            for(var i= 0, len= array.length;i<len;i++){
                var td1 = $("<td>"
                + array[i].serverName
                + "</td>");
                var td2 = $("<td>"
                + array[i].serverIp
                + "</td>");
                var td3 = $("<td>"
                + array[i].port
                + "</td>");
                var td10="<td>-</td>"
                if(array[i].slbConfig != undefined && array[i].slbConfig != null){
                    td10="<td>"
                    +"<span class=\"text-success\">"+array[i].slbConfig.agentType+"</span>"
                    +"<span class=\"text-success\">:</span>"
                    +"<span class=\"text-success\">"+array[i].slbConfig.frontPort+"</span><br>"
                    +"</td>";
                }
                var status ="-";
                if(array[i].status==5) status = "未生效";
                if(array[i].status==6) status = "生效";
                var td4 = $("<td>"
                + "<span class=\"text-success\">"+status+"</span>"
                + "</td>");
                var td5 = $("<td>"
                + "<span>经典网络</span>"
                + "</td>");
                var td6 = $("<td>"
                + "<span>包年</span>"
                + "</td>");
                var td7 = $("<td>"
                + "<span class=\"text-success\">正常</span>"
                + "</td>");
                var td8 = $("<td>"
                + "<span>100</span>"
                + "</td>");
                var td9 = $("<td class=\"text-right\"><span class=\"text-explode font-disabled\">移除</span></td>");
                var tr = $("<tr class='data-tr'></tr>");

                tr.append(td1).append(td2).append(td3).append(td10).append(td4).append(td5).append(td6).append(td7).append(td8).append(td9);
                tr.appendTo($tby);
            }
        },
        ConfigHandler : function(data){
            var select = $("[name = frontPort]")
            var array = data.data.data;

            for(var i= 0,len=array.length;i<len;i++) {
                var option = $("<option value=\""+array[i].id+"\">"+addPort(array[i])+"</option>");
                option.appendTo(select);
            }
        }
    }
    function addPort(data){
        if(data ==null || data.length == 0){
            return "-";
        }
        return "<span>"+data.agentType+"</span>"
		        +"<span>:</span>"
		        +"<span>"+data.frontPort+"</span><br>";
    }
});