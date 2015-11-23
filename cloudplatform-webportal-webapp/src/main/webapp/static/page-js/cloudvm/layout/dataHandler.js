/**
 * Created by yaokuo on 2014/12/14.
 */
define(function(require,exports,module){
    var $ = require('jquery');
    var common = require('../../common');
    var cn = new common();
    var vmStatus = '';
    
    var DataHandler = function(){
    };

    module.exports = DataHandler;

    DataHandler.prototype = {
    	/*
    	 * 相关资源量赋值
    	 */
        resCountHandler : function(data){
                $("#vmName").html(data.data.name);
                $("#vmStatus").html("("+cn.TranslateStatus(data.data.status)+")");
                vmStatus = data.data.status;
        },
		getVmStatus:function(){
			return vmStatus;
		}
    }
});