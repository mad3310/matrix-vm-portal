/**
 * Created by yaokuo on 2014/12/14.
 */
define(function(require,exports,module){
    var $ = require('jquery');

    var DataHandler = function(){
    };

    module.exports = DataHandler;

    DataHandler.prototype = {
        DbUserIpListHandler: function(data){
        var $tby = $("#ipList-tby");
        $tby.find("tr").remove();
        var array = data.data;

        var rank = 0;
        var ips = '';
        var tr =$("<tr></tr>");
        for(var i= 0, len= array.length;i<len;i++){
            var td = $("<td width=\"25%\">"+array[i]+"</td>");
            td.appendTo(tr);
            ips = ips+array[i]+",";
            if(rank < 3){
                rank++;
            }else{
                tr.appendTo($tby);
                tr =$("<tr></tr>");
                rank = 0;
            }
        }
        if(rank > 0){                    //填充空余行
            for(var i=rank;i<4;i++){
                var td = $("<td width=\"25%\"></td>");
                td.appendTo(tr);
            }
            tr.appendTo($tby);
        }

        $("#iplist-textarea").val(ips);
    }
    }
});