/**
 * Created by yaokuo on 2014/12/14.
 */
define(function(require,exports,module){
    var $ = require('jquery');
    var common = require('../common');
    var cn = new common();

    var DataHandler = function(){
    };

    module.exports = DataHandler;

    DataHandler.prototype = {
    		DbInfoHandler : function(data){
                var dbInfo = data.data;

                function GetNetAddr(containers){ //获取container IP地址,并拼成字符串返回
                    var ips='';
                    for(var i= 0,len=containers.length;i<len;i++){
                        if(dbInfo.containers[i].type == "mclustervip"){
                            ips = ips+dbInfo.containers[i].ipAddr+'(vip)'+' ';
                        }else{
                            ips = ips+dbInfo.containers[i].ipAddr+' ';
                        }
                    }
                    return ips;
                }

    			$("#db_info_db_id").html("<span class=\"text-muted pd-r8\">数据库ID:</span><span>"+dbInfo.dbName+"</span>");
    			$("#db_info_db_name").html("<span class=\"text-muted pd-r8\">名称:</span><span text-length=\"26\">"+dbInfo.dbName+"</span>&nbsp;<a class=\"hide btn btn-default btn-xs glyphicon glyphicon-pencil\" href=\"#\"></a>");
    			$("#db_info_net_addr").html("<span class=\"text-muted pd-r8\">内网地址:</span><span text-length=\"26\">"+GetNetAddr(dbInfo.containers)+"</span>");
                $("#db_info_available_region").html("<span class=\"text-muted pd-r8\">可用区:</span>"+"TEST_15X接口待完善");
                $("#db_info_db_port").html(" <span class=\"text-muted pd-r8\">端口:</span>3306");

                $("#db_info_running_state").html("<span class=\"text-muted pd-r8\">运行状态:</span>"+cn.TranslateStatus(dbInfo.status));
        }
    }
});