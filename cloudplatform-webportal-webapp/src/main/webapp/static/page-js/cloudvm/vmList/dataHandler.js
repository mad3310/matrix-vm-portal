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
        VmListHandler : function(data){
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
                	var baseInfoUrl='/detail/vm/'+array[i].id+'?region='+array[i].region;
                	tdList.push("<td width=\"10\">"+ 
                            		"<input type=\"checkbox\" name=\"vm_id\" value= \""+array[i].id+"\">"+
                            	"</td>");
                    var	vmName = "<a href=\""+baseInfoUrl+"\">" + array[i].name + "</a>";
                    tdList.push("<td class=\"padding-left-32\">"+ vmName+"</td>");
                    var vmStatus = '';                
                    if(array[i].status == 'BUILD'){
                    	var vmStatus = "<td class='hidden-xs'>"+	
                    						"<input type=\"hidden\" name=\"vm_status\" value= \""+ array[i].status + "\" />"+
			                    			"<span class=\"vm-building\">创建中...</span>"+
			                    			"<input class=\"hide\" type=\"text\" name=\"progress_vm_id\" id= \""+ array[i].id + "\" value= \""+ array[i].id + "\" >"+
		                                "</td>";
                    }else{
                    	var vmStatus = "<td>"+
                    						"<input type=\"hidden\" name=\"vm_status\" value= \""+ array[i].status + "\" />"+
		                                	cn.TranslateStatus(array[i].status)+
		                               "</td>";
                    }
                    tdList.push(vmStatus);
                    tdList.push("<td class='hidden-xs'>"+
                            		"<span>"+array[i].regionDisplayName+"</span>"+
                            		'<input type="hidden" class="field-region" value="'+array[i].region+'" />'+
                             	"</td>");
                    tdList.push("<td class=\"padding-left-32\">"+ array[i].image.name+"</td>");
                    tdList.push("<td class=\"padding-left-32\">"+
                    				"<span>私网："+(array[i].ipAddresses.private.join(', ') || '--')+"</span><br>"+
                    				"<span>公网："+(array[i].ipAddresses.public.join(', ')|| '--')+"</span><br>"+
                    				"<span>内网："+(array[i].ipAddresses.shared.join(', ')|| '--')+"</span>"+
		                    	 "</td>");
                    tdList.push("<td class=\"padding-left-32\">"+
                    			[array[i].flavor.ram+'M 内存',array[i].flavor.vcpus+' 虚拟内核',array[i].flavor.disk+'G 硬盘',].join('|')+
                    			"</td>");
                    tdList.push('<td class="text-right hidden-xs">'+
		                    		'<a href="'+baseInfoUrl+'">管理</a>|'+
		                    		'<a class="vm-operation vm-start" href="javascript:void(0);">启动</a>|'+
		                    		'<a class="vm-operation vm-stop" href="javascript:void(0);">停止</a>|'+
		                    		'<div class="dropdown vmlist-item-more">'+
								      '<a href="javascript:void(0);" class="dropdown-toggle" data-toggle="dropdown">更多<span class="caret"></span></a>'+
								      '<ul class="dropdown-menu">'+
								        '<li><a class="vm-operation vm-remove" href="javascript:void(0);"><span>删除</span></a></li>'+
								        '<li class="disabled"><a href="javascript:void(0);"><span>连接管理终端</span></a></li>'+
								      '</ul>'+
								    '</div>'+
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
	   	operateVm:function(vmId,fieldRegion,operationType,asyncData){
	   		var title = "确认";
            var text = '';
            var operationUrl='';
            var operationCallback=null;
            var getVmStatusRef=function(){
            	return $($('input:checkbox[value='+vmId+']').closest('tr')).find('input:hidden[name=vm_status]');
            };
            var vmStatus=getVmStatusRef().val();
            var setVmStatus=function(value){
            	getVmStatusRef().val(value);
            };
            var doOperation=function(operateStatus){
                cn.DialogBoxInit(title,text,function(){
        			setVmStatus(operateStatus);
            		cn.PostData(operationUrl,{
            	        vmId: vmId
            	    },function(data){
            	    	operationCallback(data);
            		});
                    	
                });
    		};
    		if(vmStatus=='BUILD'){
    			cn.alertoolWarnning("虚拟机正在创建中，不允许操作。");
    			return;
    		}
			if(vmStatus=='REMOVEING' || vmStatus=='ACTIVEING' || vmStatus=='SHUTOFFING'){
				cn.alertoolWarnning("虚拟机正在操作中，请稍后再试。");
				return;
			}
    		switch(operationType){
        		case 'vm-remove':
        			text='您确定要删除该虚拟机吗？';
        			operationUrl='/ecs/region/'+fieldRegion+'/vm-delete';
        			operationCallback=function(data){
        				cn.alertoolSuccess("虚拟机删除成功。");
        				asyncData();
            		};
            		doOperation('REMOVEING');
            		break;
        		case 'vm-start':
        			if(vmStatus=='ACTIVE'){
        				cn.alertoolWarnning("虚拟机已经是启动状态。");
        				return;
        			}
        			text='您确定要开始该虚拟机吗？';
        			operationUrl='/ecs/region/'+fieldRegion+'/vm-start';
        			operationCallback=function(data){
        				cn.alertoolSuccess("虚拟机已启动");
        				asyncData();
            		};
            		doOperation('ACTIVEING');
            		break;
        		case 'vm-stop':
        			if(vmStatus=='SHUTOFF'){
        				cn.alertoolWarnning("虚拟机已经是关闭状态。");
        				return;
        			}
        			text='您确定要停止该虚拟机吗？';
        			operationUrl='/ecs/region/'+fieldRegion+'/vm-stop';
        			operationCallback=function(data){
        				cn.alertoolSuccess("虚拟机已停止");
        				asyncData();
            		};
            		doOperation('SHUTOFFING');
            		break;
            	default:
            		break;
    		}

    		
	   	}
    }
});
