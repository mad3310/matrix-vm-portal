/**
 * Created by yaokuo on 2014/12/14.
 */
define(function(require,exports,module){
    var $ = require('jquery');

    var DataHandler = function(){
    };

    module.exports = DataHandler;

    DataHandler.prototype = {
    	/*
    	 * 相关资源量赋值
    	 */
        resCountHandler : function(data){
            if(data.data.db > 0){
                $("#rds-opened").removeClass("hide");
                $("#rds-not-opened").addClass("hide");
                $("#dbCount").html(data.data.db);
            }else{
                $("#rds-opened").addClass("hide");
                $("#rds-not-opened").removeClass("hide");
            }
            if(data.data.slb > 0){
                $("#slb-opened").removeClass("hide");
                $("#slb-not-opened").addClass("hide");
                $("#slbCount").html(data.data.slb);
            }else{
                $("#slb-opened").addClass("hide");
                $("#slb-not-opened").removeClass("hide");
            }
        }
    }
});