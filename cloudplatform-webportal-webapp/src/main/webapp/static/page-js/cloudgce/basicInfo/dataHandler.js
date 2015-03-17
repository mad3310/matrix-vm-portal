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
    		SlbInfoHandler : function(data){
                var slbInfo = data.data;
    			$("#slb_info_slb_id").html(slbInfo.slbName);
    			$("#slb_info_slb_name").html(slbInfo.slbName);
                $("#slbServerIp").html(slbInfo.ip);
    			$("#slb_create_time").html(cn.TransDate('Y-m-d H:i:s',slbInfo.createTime));
                if(slbInfo.hcluster != undefined && slbInfo.hcluster != null) {
                    $("#slb_info_available_region").html(slbInfo.hcluster.hclusterNameAlias);
                }
            }
    }
});