/**
 * Created by yaokuo on 2014/12/14.
 */
define(function(require,exports,module){
    var $ = require('jquery');

    var DataHandler = function(){
    };

    module.exports = DataHandler;

    DataHandler.prototype = {
        GetHclusterHandler : function(data){
            var hcluster = data.data;
            var select = $("[name='hclusterId']");
            for(var i= 0,len=hcluster.length;i<len;i++){
                var option = $("<option value=\""+hcluster[i].id+"\">"+hcluster[i].hclusterNameAlias+"</option>");
                option.appendTo(select);
            }
        }
    }
});