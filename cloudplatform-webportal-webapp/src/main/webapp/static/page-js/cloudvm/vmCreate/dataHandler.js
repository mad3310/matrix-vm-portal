/**
 * Created by yaokuo on 2014/12/14.
 */
define(function(require,exports,module){
    var $=jQuery = require('jquery');
    var common = require('../../common');
    var cn = new common();

    var DataHandler = function(){
    };

    module.exports = DataHandler;

    DataHandler.prototype = {
        getRegion : function(data){
        	var region = data.data;
            var ul = $("[name='regionName']").parent('div').find('ul');
            for(var i= 0,len=region.length;i<len;i++){
                var li = $("<li class=\"bk-select-option\"><a href=\"javascript:;\" selectid=\""+region[i]+"\">"+region[i]+"</a></li>");
                li.appendTo(ul);
            }
            ul.click().find("li").first().click();
        },
        getVmType:function(data){
        	var type = data.data;
            var ul = $("[name='vmType']").parent('div').find('ul');
            var lis='';
            for(var i= 0,len=type.length;i<len;i++){
                var li = "<li class=\"bk-select-option\"><a href=\"javascript:;\" selectid=\""+type[i].id+"\">"+type[i].name+"</a></li>";
                lis = lis+li;
            }
            ul.html(lis);
             ul.click().find("li").first().click();
        },
        getImage:function(data){
        	var images = data.data;
           var ul = $("[name='vmImageName']").parent('div').find('ul');
           var lis = '';
            for(var i= 0,len=images.length;i<len;i++){
                var li = "<li class=\"bk-select-option\"><a href=\"javascript:;\" selectid=\""+images[i].id+"\">"+images[i].name+"</a></li>";
                lis=lis+li;
            }
            ul.html(lis);
             ul.click().find("li").first().click();
        },
        getNetwork:function(data){
        	var network = data.data;
           var select = $("#networkSelecter");
           var options = "";
            for(var i= 0,len=network.length;i<len;i++){
            	if(network[i])
                var option = "<option value=\""+network[i].id+"\">"+network[i].name+"</option>";
                options = options + option;
            }
            select.html(options);
	    	
            select.multipleSelect({ //初始化多选框
	            placeholder: "请选择",
	            selectAll: false
	        });
        }
    }
    
});