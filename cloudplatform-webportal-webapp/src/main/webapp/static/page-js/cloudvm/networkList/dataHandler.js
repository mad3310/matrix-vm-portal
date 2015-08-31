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

    module.exports = DataHandler;

    DataHandler.prototype = {
    	NetworkListHandler : function(data){
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
                            		"<input type=\"checkbox\" name=\"vpc_id\" value= \""+array[i].id+"\">"+
                            	"</td>");
                    tdList.push("<td class=\"padding-left-32\">"+ (array[i].name||'--')+"</td>");
                    tdList.push("<td class=\"padding-left-32\">"+
                    		array[i].subnets.map(function(subnet){
                    			return "<span>"+subnet.name+"</span>";
                    		}).join('<br>')
                    		);
                    var diskStatus = '';                
                    var diskStatus = 	"<td>"+
                    						"<input type=\"hidden\" name=\"vpc_status\" value= \""+ array[i].status + "\" />"+
		                                	"<span class=\"vpc-display-status\">"+cn.TranslateStatus(array[i].status)+"</span>"+
		                               "</td>";
                    tdList.push(diskStatus);
                    tdList.push("<td class=\"padding-left-32\">"+ array[i].regionDisplayName+
                    		'<input type="hidden" class="field-region" value="'+array[i].region+'" />'+		
                    "</td>");
                    tdList.push('<td class="text-right hidden-xs">'+
		                    		'<a class="vpc-operation vpc-remove" href="javascript:void(0);">删除</a>'+
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
        	
        	var vpcRegionSelectorEl=$('#vpc_region_selector');
	   		var vpcRegionSelectorOptions=[];
			for(var country in data.data){
				if(data.data.hasOwnProperty(country)){
					for(var city in data.data[country]){
						if(data.data[country].hasOwnProperty(city)){
							for(var region in data.data[country][city]){
								if(data.data[country][city].hasOwnProperty(region)){
									vpcRegionSelectorOptions.push('<option value="'+data.data[country][city][region].id+'">'+data.data[country][city][region].name+'</option>');	
								}
							}
						}
					}
				}
			}
	   		vpcRegionSelectorEl.html(vpcRegionSelectorOptions.join(''));
        	
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
	   	operateDisk:function(vpcId,fieldRegion,operationType,asyncData){
	   		var title = "确认",
	   			text = '',
	   			operationUrl='',
	   			operatingTip='',
	   			operationCallback=null,
	   			vpcStatusEl=$($('input:checkbox[value='+vpcId+']').closest('tr')).find('input:hidden[name=vpc_status]'),
	   			vpcStatus=vpcStatusEl.val();
	   		var doOperation=function(operateStatus){
	                cn.DialogBoxInit(title,text,function(){
        				cn.alertoolSuccess(operatingTip);
	            		cn.PostData(operationUrl,{
	            	        region:fieldRegion,
	            	        networkId:vpcId
	            	    },function(data){
	            	    	operationCallback(data);
	            		});
                    	
	                });
    		};
    		
    		switch(operationType){
        		case 'vpc-remove':
        			text='您确定要删除该私有网络吗？';
        			operatingTip="私有网络删除执行中...";
        			operationUrl='/osn/network/private/delete';
        			operationCallback=function(data){
        				asyncData();
            		};
            		doOperation('REMOVEING');
            		break;
            	default:
            		break;
    		}

    		
	   	},
	   	createVPC:function(){
	   		
	   	}
    }
});
