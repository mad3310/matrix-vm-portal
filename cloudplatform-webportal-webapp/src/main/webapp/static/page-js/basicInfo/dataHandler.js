/**
 * Created by yaokuo on 2014/12/14.
 */
define(function(require,exports,module){
    var $ = require('jquery');

    var DataHandler = function(){
    };

    module.exports = DataHandler;

    DataHandler.prototype = {
    		DbInfoHandler : function(data){
    			var dbInfo = data.data;
    			$("#db_info_db_id").html("<span class=\"text-muted pd-r8\">数据库ID:</span><span>"+dbInfo.dbName+"</span>");
    			$("#db_info_db_name").html("<span class=\"text-muted pd-r8\">名称:</span><span text-length=\"26\">"+dbInfo.dbName+"</span>&nbsp;<a class=\"btn btn-default btn-xs glyphicon glyphicon-pencil\" href=\"#\"></a>");
    			$("#db_info_net_addr").html("<span class=\"text-muted pd-r8\">内网地址:</span><span text-length=\"26\">"+dbInfo.containers[0].ipAddr+"</span>");
        }
    }
});