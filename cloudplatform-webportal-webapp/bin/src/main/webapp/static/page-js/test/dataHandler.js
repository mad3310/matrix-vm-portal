/**
 * Created by yaokuo on 2015/2/6.
 */
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
        setIntervalHandler : function(){
            setInterval(abc(),3000);
        },
        setTimeoutHandler: function(){

        }
    }
    function abc(){
        alert("adfadfadf");
    }
});