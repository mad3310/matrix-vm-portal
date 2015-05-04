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
    	/*
    	 * 相关资源量赋值
    	 */
        resCountHandler : function(data){
            if(cn.Displayable(data.data.status)){
                $("#dbName").html(data.data.dbName);
                $("#dbStatus").html("("+cn.TranslateStatus(data.data.status)+")");
            }else{
                location.href = '/list/db';
            }
        }
    }
});