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
    		GceInfoHandler : function(data){
                var gceInfo = data.data;
    			$("#gce_info_gce_name").html(gceInfo.gceName);
    			$("#gce_info_gce_image").html(gceInfo.gceImageName);
                $("#gceServerIp").html(gceInfo.ip);
                $("#gce_info_port_forward").html(gceInfo.portForward);
    			$("#gce_create_time").html(cn.TransDate('Y-m-d H:i:s',gceInfo.createTime));
                if(gceInfo.hcluster != undefined && gceInfo.hcluster != null) {
                    $("#gce_info_available_region").html(gceInfo.hcluster.hclusterNameAlias);
                }
            }
    }
});