define(function(require,exports,module){
    var $=require('jquery');
    var common = require('../../common');
    var cn = new common();
    var dataHandler = function(){
    };
    module.exports=dataHandler;
    dataHandler.prototype={
        GetCacheHandler : function(data){
            var cache = data.data;
            var ul = $("[name='cacheId']").parent('div').find('ul');
            for(var i= 0,len=hcluster.length;i<len;i++){
                var li = $("<li class=\"bk-select-option\"><a href=\"javascript:;\" selectid=\""+cache[i].id+"\">"+cache[i].cacheNameAlias+"</a></li>");
                li.appendTo(ul);
            }
            cn.divselect();
        }
    }
    
});