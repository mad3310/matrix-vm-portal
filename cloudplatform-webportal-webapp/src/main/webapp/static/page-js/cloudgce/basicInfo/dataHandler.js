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
    			$("#gce_create_time").html(cn.TransDate('Y-m-d H:i:s',gceInfo.createTime));
                if(gceInfo.hcluster != undefined && gceInfo.hcluster != null) {
                    $("#gce_info_available_region").html(gceInfo.hcluster.hclusterNameAlias);
                }
            }
    }
    function getAccpetAddr(data){
        if(data == null || data.length == 0){
            return "-";
        }
        
        var ret="";
        for(var i= 0,len=data.length;i<len;i++){
        	var hostPortArr = data[i].bingHostPort.split(',');
        	var containerPortArr = data[i].bindContainerPort.split(',');

        	var servicePort;
        	for(var j = 0,len = hostPortArr.length;j<len;j++){
        		if(containerPortArr[j] == "8080" ||containerPortArr[j] == "8001"){
        			servicePort = {
        					hostPort:hostPortArr[j],
        					containerPort:containerPortArr[j]
        			};
        		}else{
        			continue;
        		}
        	}
            ret = ret
            +"<span class=\"text-success\">"+data[i].hostIp+"</span>"
            +"<span class=\"text-success\">:</span>"
            +"<span class=\"text-success\">"+servicePort.hostPort+"</span>"
            
            if(i!=len-1){
            	ret = ret + "; "
            }
        }
        return ret;
    }
});