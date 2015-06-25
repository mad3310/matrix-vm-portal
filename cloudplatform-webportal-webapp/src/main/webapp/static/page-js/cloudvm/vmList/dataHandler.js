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
        VmListHandler : function(data){
        	$(".data-tr").remove();
        	
            var $tby = $('#tby');
            var array = data.data;
            if(array.length == 0){
            	cn.emptyBlock($tby);
            }else{
            	 if($("#noData").length > 0){
            		 $("#noData").remove();
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
			                    			"<span class=\"vm-building\">创建中...</span>"+
			                    			"<input class=\"hide\" type=\"text\" name=\"progress_vm_id\" id= \""+ array[i].id + "\" value= \""+ array[i].id + "\" >"+
		                                "</td>";
                    }else{
                    	var vmStatus = "<td>"+
		                                	cn.TranslateStatus(array[i].status)+
		                               "</td>";
                    }
                    tdList.push(vmStatus);
                    tdList.push("<td class='hidden-xs'>"+
                            		"<span class='field-region'>"+array[i].region+"</span>"+
                             	"</td>");
                    tdList.push("<td class=\"padding-left-32\">"+ array[i].image.name+"</td>");
                    tdList.push("<td class=\"padding-left-32\">"+
                    				"<span>私网："+array[i].ipAddresses.private.join(', ')+"</span><br>"+
                    				"<span>公网："+array[i].ipAddresses.public.join(', ')+"</span><br>"+
                    				"<span>内网："+array[i].ipAddresses.shared.join(', ')+"</span>"+
		                    	 "</td>");
                    tdList.push("<td class=\"padding-left-32\">"+
                    			[array[i].flavor.name,array[i].flavor.ram+' 内存',array[i].flavor.vcpus+' 虚拟内核',array[i].flavor.disk+'G 硬盘',].join('|')+
                    			"</td>");
                    tdList.push('<td class="text-right hidden-xs">'+
		                    		'<a href="'+baseInfoUrl+'">管理</a>|'+
		                    		'<a class="vm-operation vm-start" href="javascript:void(0);">启动</a>|'+
		                    		'<a class="vm-operation vm-stop" href="javascript:void(0);">停止</a>|'+
		                    		'<a class="vm-operation vm-remove" href="javascript:void(0);">删除</a>'+
		                    	'</td>');
                    tdList.unshift("<tr class='data-tr'>");
                    tdList.push("</tr>");
                    
                    $tby.append(tdList.join(""));
                 }
            }
       
        },
        /*进度条进度控制*/
	    progress : function(dbId,data,asyncData){
	    	var data = data.data;	    	
           	if( data.status !== 'BUILD'){
           		asyncData();
           	}
	   	},
	   	initRegionSelectorHandler:function(data){
   			$('#region_selector').empty();
	   		if(data.result==1&&data.data.length){
	   			var optionList=[];
	   			optionList.push('<option value="All" selected="selected">全部</option>');
	   			for(var i=0,len=data.data.length;i<len;i++){
	   				optionList.push('<option value="'+data.data[i]+'">'+data.data[i]+'</option>');	
	   			}
	   			$('#region_selector').append(optionList.join(''));
	   		}
	   	},
	   	getSelectedRegion:function(){
	   		return $('select#region_selector').val();
	   	},
	   	operateVm:function(vmId,fieldRegion,operationType,asyncData){
	   		var title = "确认";
            var text = '';
            var operationUrl='';
            var operationCallback=null;
    		switch(operationType){
        		case 'vm-remove':
        			text='您确定要删除该虚拟机吗？';
        			operationUrl='/ecs/region/'+fieldRegion+'/vm-delete';
        			operationCallback=function(data){
            	    	var refresh =setInterval(function(){
            	    		if($('#tby input:checkbox[value='+vmId+']').length){
                	    		asyncData();
            	    		}else{
            	    			clearInterval(refresh);
            	    		}
            	    	},1000);
            		};
            		doOperation();
            		break;
        		case 'vm-start':
        			text='您确定要开始该虚拟机吗？';
        			operationUrl='/ecs/region/'+fieldRegion+'/vm-start';
        			operationCallback=function(data){
        				cn.alertoolSuccess("虚拟机已启动");
        				asyncData();
            		};
            		doOperation();
            		break;
        		case 'vm-stop':
        			text='您确定要停止该虚拟机吗？';
        			operationUrl='/ecs/region/'+fieldRegion+'/vm-stop';
        			operationCallback=function(data){
        				cn.alertoolSuccess("虚拟机已停止");
        				asyncData();
            		};
            		doOperation();
            		break;
            	default:
            		break;
    		}

    		function doOperation(){
                cn.DialogBoxInit(title,text,function(){
            		cn.PostData(operationUrl,{
            	        vmId: vmId
            	    },function(data){
            	    	operationCallback(data);
            		});
                    	
                });
    		}
	   	}
    }
});