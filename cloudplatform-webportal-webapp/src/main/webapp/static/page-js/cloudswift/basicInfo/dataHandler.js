/**
 * Created by yaokuo on 2014/12/14.
 */
define(function(require,exports,module){
    var jQuery = $ = require('jquery');
    require('zclip');
    var common = require('../../common');
    var cn = new common();
   
    var DataHandler = function(){
    };

    module.exports = DataHandler;

    DataHandler.prototype = {
    		DbInfoHandler : function(data){
                var swift = data.data;

    			$("#oss_info_db_id").html(swift.name);
    			if(swift.hcluster) {
    				$("#oss_info_available_region").html(swift.hcluster.hclusterNameAlias);
    			}
    			if(swift.hosts) {
    				$("#oss_info_url").html(swift.hosts[0].hostIp);
    			}
                $("#oss_info_running_state").html(cn.TranslateStatus(swift.status));
                $("#oss_info_create_time").html(cn.TransDate('Y-m-d H:i:s',swift.createTime));
                $("#oss_info_remain_days").html(cn.RemainAvailableTime(swift.createTime));
                $("#oss_info_sotre_size").html(swift.storeSize + "GB");
                $("#oss_info_visibilityLevel").html(swift.visibilityLevel);
        }
        
    }
});