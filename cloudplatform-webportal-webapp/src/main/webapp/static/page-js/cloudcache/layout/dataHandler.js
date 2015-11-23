define(function(require,exports,module){
    var $ = require('jquery');
    var Common = require('../../common');
    var cn = new Common();
    
    var dataHandler = function(){
    };

    module.exports = dataHandler;

    dataHandler.prototype = {
    	/*
    	 * 相关资源量赋值
    	 */
        resCountHandler : function(data){
            if(cn.Displayable(data.data.status)){
                $("#ocsName").html(data.data.bucketName);
                $("#ocsStatus").html("("+cn.TranslateStatus(data.data.status)+")");
            }else{
                location.href = '/list/cache';
            }
        }
    }
});