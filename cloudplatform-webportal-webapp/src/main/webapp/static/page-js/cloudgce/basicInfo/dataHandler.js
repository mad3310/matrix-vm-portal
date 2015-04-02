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
    			$("#gce_type").html(gceInfo.type);
                $("#gce_server_addr").html(getAccpetAddr(gceInfo.gceContainers));
                $("#gce_info_port_forward").html(getPort(gceInfo.gceContainers));
    			$("#gce_create_time").html(cn.TransDate('Y-m-d H:i:s',gceInfo.createTime));
                if(gceInfo.hcluster != undefined && gceInfo.hcluster != null) {
                    $("#gce_info_available_region").html(gceInfo.hcluster.hclusterNameAlias);
                }
            }
    }
    function getAccpetAddr(data){
        if(data == null && data.length == 0){
            return "-";
        }
        var ret="";
        for(var i= 0,len=data.length;i<len;i++){
            ret = ret
            +"<span class=\"text-success\">"+data[i].hostIp+"</span>"
            +"<span class=\"text-success\">:</span>"
            +"<span class=\"text-success\">"+data[i].bingHostPort+"</span>"
            
            if(i!=len-1){
            	ret = ret + "; "
            }
        }
        return ret;
    }
    function getPort(data){
    	if(data == null && data.length == 0){
    		return "-";
    	}
    	var ret="";
    	for(var i= 0,len=data.length;i<len;i++){
    		ret = ret
    		+"<span class=\"text-success\">"+data[i].bingHostPort+"</span>"
    		+"<span class=\"text-success\">:</span>"
    		+"<span class=\"text-success\">"+data[i].bindContainerPort+"</span>"
    		
    		if(i!=len-1){
    			ret = ret + "; "
    		}
    	}
    	return ret;
    }
});