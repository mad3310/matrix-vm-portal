/**
 * Created by yaokuo on 2014/12/14.
 */
define(function(require,exports,module){
    var $=jQuery = require('jquery');
    var common = require('../../common');
    var cn = new common();

    var diskOperatePanel=$('.disk-operate-panel');
    var domElDict={
    		diskOperatePanel:diskOperatePanel,
    		disAddButton:diskOperatePanel.find('.bk-disk-add').parent(),
    };
    var DataHandler = function(){
    };
    var regionCitynameData,flavorGroupData,imageGroupData;

    module.exports = DataHandler;

    DataHandler.prototype = {
        getRegion : function(regionCityName){
        	if(!regionCitynameData || !regionCitynameData.data) return;
        	var regionCountryAndCity=regionCityName.split('-');
        	var currentCityObject = regionCitynameData.data[regionCountryAndCity[0]][regionCountryAndCity[1]];
        	var regions= Object.keys(currentCityObject).map(function(regionNum){
        		return {id:currentCityObject[regionNum].id,name:currentCityObject[regionNum].name};
        	});
            var ul = $("[name='regionName']").parent('div').find('ul');
            ul.empty();
            for(var i= 0,len=regions.length;i<len;i++){
                var li = $("<li class=\"bk-select-option\"><a href=\"javascript:;\" selectid=\""+regions[i].id+"\">"+regions[i].name+"</a></li>");
                li.appendTo(ul);
            }
            ul.click().find("li").first().click();
        },
        getRegionCityname : function(data){
        	if(!data || !data.data) return;
        	regionCitynameData=data;
        	var cityObjects = Object.keys(data.data).reduce(function(x,countryId){
        		var newCityIds=	Object.keys(data.data[countryId]);
        		newCityIds.forEach(function(currentCityId){
        			var currentCityObject= data.data[countryId][currentCityId];
        			var firstRegionNum=Object.keys(currentCityObject)[0];
        			x.push({cityId:countryId+'-'+currentCityId,cityName:currentCityObject[firstRegionNum].city});
        		});
        		return x;
        	},[]);
            var regioncitysRef = $(".bk-buttontab-regioncitys");
            for(var i= 0,len=cityObjects.length;i<len;i++){
                var regionHtml = $('<button class="bk-button bk-button-primary bk-button-current" value="'+cityObjects[i].cityId+'">'+
								'<div>'+
									'<span>'+cityObjects[i].cityName+'</span>'+
								'</div>'+
							'</button>');
                regioncitysRef.append(regionHtml);
            }
            regioncitysRef.find("button").first().click();
        },
        setBuyRegionName:function(regionName){
        	if(!regionCitynameData || !regionCitynameData.data) return;
        	var countryAndCityAndRegionName=regionName.split('-');
        	$('#buy-region').html(regionCitynameData.data[countryAndCityAndRegionName[0]][countryAndCityAndRegionName[1]][countryAndCityAndRegionName[2]].name);        	
        }
        
    }
    
});