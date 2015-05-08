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
    	 SwiftConfigHandler:function(data){
        	 if(cn.Displayable(data.data.status)){
                $("#ossName").html(data.data.name);
                $("#ossStatus").html("("+cn.TranslateStatus(data.data.status)+")");
            }else{
                location.href = '/list/oss';
            }
            
        	if(data.data.visibilityLevel == "PUBLIC"){
        		$("#level-public").click();
        	}else{
        		$("#level-private").click();
        	}
        	$(".memSize").val(data.data.storeSize).change();
        }
    }
});