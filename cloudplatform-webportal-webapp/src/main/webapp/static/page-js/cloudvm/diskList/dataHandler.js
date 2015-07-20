/**
 * Created by yaokuo on 2014/12/14.
 * dblist page 
 */
define(function(require,exports,module){
    var $ = require('jquery');
    /*
	 * 引入相关js，使用分页组件
	 */
	require('bootstrap');
	require('paginator')($);
	
    var common = require('../../common');
    var cn = new common();
   
    
    var DataHandler = function(){
    };
    var regionCitynameData;

    module.exports = DataHandler;

    DataHandler.prototype = {
        DiskListHandler : function(data){
        	$(".data-tr").remove();
        	
            var $tby = $('#tby');
            var array = data.data.data;
            if(array.length == 0){
            	cn.emptyBlock($tby);
            	if($("#paginatorBlock").length > 0){
            		$("#paginatorBlock").hide();
            	}
            }else{
            	 if($("#noData").length > 0){
            		 $("#noData").remove();
            	 }
            	 if($("#paginatorBlock").length > 0){
            		 $("#paginatorBlock").show();
            	 }
                for(var i= 0, len= array.length;i<len;i++){
                	var tdList= [];
                	tdList.push("<td width=\"10\">"+ 
                            		"<input type=\"checkbox\" name=\"disk_id\" value= \""+array[i].id+"\">"+
                            	"</td>");
                    tdList.push("<td class=\"padding-left-32\">"+ (array[i].id||'--')+"</td>");
                    var diskStatus = '';                
                    var diskStatus = 	"<td>"+
                    						"<input type=\"hidden\" name=\"disk_status\" value= \""+ array[i].status + "\" />"+
		                                	"<span class=\"disk-display-status\">"+cn.TranslateStatus(array[i].status)+"</span>"+
		                               "</td>";
                    tdList.push(diskStatus);
                    tdList.push("<td class=\"padding-left-32\">"+
	                    			array[i].size+'GB'+
	                			"</td>");
                    tdList.push("<td class='hidden-xs'>"+
                            		"<span>"+array[i].regionDisplayName+"</span>"+
                            		'<input type="hidden" class="field-region" value="'+array[i].region+'" />'+
                             	"</td>");
                    tdList.push('<td class="text-right hidden-xs">'+
		                    		'<a class="disk-operation disk-remove" href="javascript:void(0);">删除</a>'+
		                    	'</td>');
                    tdList.unshift("<tr class='data-tr'>");
                    tdList.push("</tr>");
                    
                    $tby.append(tdList.join(""));
                 }
            }
            /*
             * 设置分页数据
             */
            $("#totalRecords").html(data.data.totalRecords);
            $("#recordsPerPage").html(data.data.recordsPerPage);
            
            if(data.data.totalPages < 1){
        		data.data.totalPages = 1;
        	};
        	
            $('#paginator').bootstrapPaginator({
                currentPage: data.data.currentPage,
                totalPages:data.data.totalPages
            });
       
        },
        /*进度条进度控制*/
	    progress : function(dbId,data,asyncData){
	    	var data = data.data;	    	
           	if( data.status !== 'BUILD'){
           		asyncData();
           	}
	   	},
	   	goingStatusList:['creating','attaching','deleting'],
	   	initRegionSelectorHandler:function(data){
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
        	var regionCityListEl=$('.region-city-list');
        	regionCityListEl.children('button').remove();
	   		var optionList=[];
	   		optionList.push('<button class="btn btn-default btn-md btn-region-display hidden-xs" value="All">全部</button>');
	   		for(var i=0,len=cityObjects.length;i<len;i++){
	   			optionList.push('<button class="btn btn-default btn-md btn-region-display hidden-xs" value="'+cityObjects[i].cityId+'">'+cityObjects[i].cityName+'</button>');	
	   		}
	   		regionCityListEl.append(optionList.join(''));
	   		regionCityListEl.children('button').first().trigger('click');
	   	},
	   	getSelectedRegion:function(){
	   		return $('.region-city-list #city_region_selected').val();
	   	},
	   	resetSelectAllCheckbox:function(){
			$('th input:checkbox').prop('checked', false);
			//$('tfoot input:checkbox').prop('checked', false);
	   	},
	   	operateDisk:function(diskId,fieldRegion,operationType,asyncData){
	   		var title = "确认",
	   			text = '',
	   			operationUrl='',
	   			operatingTip='',
	   			operationCallback=null,
	   			diskStatusEl=$($('input:checkbox[value='+diskId+']').closest('tr')).find('input:hidden[name=disk_status]'),
	   			diskStatus=diskStatusEl.val();
	   		var doOperation=function(operateStatus){
	                cn.DialogBoxInit(title,text,function(){
        				cn.alertoolSuccess(operatingTip);
	            		cn.PostData(operationUrl,{
	            			volumeId: diskId
	            	    },function(data){
	            	    	operationCallback(data);
	            		});
                    	
	                });
    		};
    		
			if(!(diskStatus =='error'|| diskStatus =='available')){
				cn.alertoolWarnning("云盘当前的状态不可删除。");
				return;
			}
    		switch(operationType){
        		case 'disk-remove':
        			text='您确定要删除该云盘吗？';
        			operatingTip="云盘删除执行中...";
        			operationUrl='/osv/region/'+fieldRegion+'/volume-delete';
        			operationCallback=function(data){
        				asyncData();
            		};
            		doOperation('REMOVEING');
            		break;
            	default:
            		break;
    		}

    		
	   	}
    }
});
