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
        },
        getFlavorCPUs:function(data){
        	if(!data || !data.data) return;
        	flavorGroupData=data;
        	var flavorCPUs = Object.keys(data.data);//data.data;
           var flavorCPUsContainer = $(".bk-buttontab-flavorCPUs");
           flavorCPUsContainer.children('button').remove();
           var flavorCPUsHtmlArray=[];
           for(var i= 0,len=flavorCPUs.length;i<len;i++){
        	   flavorCPUsHtmlArray.push('<button class="bk-button bk-button-primary bk-button-current" value="'+flavorCPUs[i]+'">'+
											'<div>'+
												'<span>'+flavorCPUs[i]+'核</span>'+
											'</div>'+
										'</button>');
           }
           flavorCPUsContainer.append(flavorCPUsHtmlArray.join(''));
           flavorCPUsContainer.find("button").first().click();
        },
        getFlavorRam:function(CPU){
        	if(!flavorGroupData || !flavorGroupData.data) return;
        	var flavorRams = Object.keys(flavorGroupData.data[CPU]);//data.data;
           var flavorRamsContainer = $(".bk-buttontab-flavorRams");
           flavorRamsContainer.children('button').remove();
           var flavorRamsHtmlArray=[];
           for(var i= 0,len=flavorRams.length;i<len;i++){
        	   flavorRamsHtmlArray.push('<button class="bk-button bk-button-primary bk-button-current" value="'+flavorRams[i]+'">'+
											'<div>'+
												'<span>'+flavorRams[i]+'MB</span>'+
											'</div>'+
										'</button>');
           }
           flavorRamsContainer.append(flavorRamsHtmlArray.join(''));
           flavorRamsContainer.find("button").first().click();
        },
        getFlavorDisk:function(CPU,ram){
        	if(!flavorGroupData || !flavorGroupData.data) return;
        	var flavorDisks = Object.keys(flavorGroupData.data[CPU][ram]);//data.data;
           var flavorDisksContainer = $(".bk-buttontab-flavorDisks");
           flavorDisksContainer.children('button').remove();
           var flavorDisksHtmlArray=[];
           for(var i= 0,len=flavorDisks.length;i<len;i++){
        	   flavorDisksHtmlArray.push('<button class="bk-button bk-button-primary bk-button-current" value="'+flavorDisks[i]+'">'+
											'<div>'+
												'<span>'+flavorDisks[i]+'G</span>'+
											'</div>'+
										'</button>');
           }
           flavorDisksContainer.append(flavorDisksHtmlArray.join(''));
           flavorDisksContainer.find("button").first().click();
        },
        setFlavorId:function(CPU,ram,disk){
        	if(!flavorGroupData || !flavorGroupData.data) return;
        	$('#flavorId').val(flavorGroupData.data[CPU][ram][disk].id);
        },
        getImageOSs:function(data){
        	if(!data || !data.data) return;
        	imageGroupData=data;
        	var imageOSs = Object.keys(data.data);//data.data;
        	var imageOSNameInputRef=$('input[name=vmImageOSName]')
        	var ul = imageOSNameInputRef.parent('div').find('ul');
        	var lis = '';
            for(var i= 0,len=imageOSs.length;i<len;i++){
                var li = "<li class=\"bk-select-option\"><a href=\"javascript:;\" selectid=\""+imageOSs[i]+"\">"+imageOSs[i]+"</a></li>";
                lis=lis+li;
            }
            ul.html(lis);
            imageOSNameInputRef.val('');
            imageOSNameInputRef.trigger('change');
        },
        getImageVersions:function(imageOSName){
        	if(!imageGroupData || !imageGroupData.data) return;
        	var imageVersions = Object.keys(imageGroupData.data[imageOSName]);
        	var imageVersionNameInputRef=$('input[name=vmImageVersionName]');
           var ul = imageVersionNameInputRef.parent('div').find('ul');
           var lis = '';
            for(var i= 0,len=imageVersions.length;i<len;i++){
                var li = "<li class=\"bk-select-option\"><a href=\"javascript:;\" selectid=\""+imageVersions[i]+"\">"+imageVersions[i]+"</a></li>";
                lis=lis+li;
            }
            ul.html(lis);
        },
        setImageId:function(imageOSName,imageVersionName){
        	if(!imageGroupData || !imageGroupData.data) return;
        	var value=(!imageOSName || !imageVersionName) ? '': imageGroupData.data[imageOSName][imageVersionName].id;
        	$('#vmImageId').val(value);
        },
        addDataDisk:function(){
        	domElDict.disAddButton.before($('<div class="bk-form-row-li">'+
													        '<div class="bk-disk">'+
													            '<span class="bk-form-row-li-info bk-disk-type">'+
													                '<span class="bk-black">普通云盘</span>'+
													            '</span>'+
													            '<span class="bk-disk-storage bk-ml2">'+
													                '<input class="bk-disk-input" value="1">'+
													                '<span class="bk-disk-unit">GB</span>'+
													            '</span>'+
													            '<span class="bk-disk-delete">'+
													                '<i class="glyphicon glyphicon-remove"></i>'+
													            '</span>'+
													        '</div>'+
												'</div>'));
        },
        getDataDiskInfos:function(){
        	var diskVolumValues=[];
        	domElDict.diskOperatePanel.find('.bk-disk-input').each(function(index,element){
        		diskVolumValues.push($(element).val());
        	});
        	return diskVolumValues;
        }
    }
    
});