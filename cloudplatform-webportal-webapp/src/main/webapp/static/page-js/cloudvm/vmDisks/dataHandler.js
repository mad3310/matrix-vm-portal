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
	
    var common = require('../../common');
    var cn = new common();
   
    
    var DataHandler = function(){
    };

    module.exports = DataHandler;

    DataHandler.prototype = {
        DiskListHandler : function(data){
        	$(".data-tr").remove();
        	
            var $tby = $('#tby');
            var array = data.data.volumes;
            if(array.length == 0){
            	cn.emptyBlock($tby);
            	if($("#paginatorBlock").length > 0){
            		$("#paginatorBlock").hide();
            	}
            }else{
            	 if($("#noData").length > 0){
            		 $("#noData").remove();
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
		                                	"<span class\"disk-display-status\">"+cn.TranslateStatus(array[i].status)+"</span>"+
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
                    		'<a class="disk-operation disk-detach" href="javascript:void(0);">解挂</a>'+
                    	'</td>');
                    tdList.unshift("<tr class='data-tr'>");
                    tdList.push("</tr>");
                    
                    $tby.append(tdList.join(""));
                 }
            }
       
        },
	   	resetSelectAllCheckbox:function(){
			$('th input:checkbox').prop('checked', false);
			//$('tfoot input:checkbox').prop('checked', false);
	   	},
	   	operateDisk:function(diskId,vmId,fieldRegion,operationType,asyncData){
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
	            			volumeId: diskId,
	            			vmId:vmId
	            	    },function(data){
	            	    	operationCallback(data);
	            		});
                    	
	                });
    		};
    		
			if(diskStatus !='in-use'){
				cn.alertoolWarnning("云盘当前的状态不可解绑。");
				return;
			}
    		switch(operationType){
        		case 'disk-detach':
        			text='您确定要解挂该云盘吗？';
        			operatingTip="云盘解挂执行中...";
        			operationUrl='/ecs/region/'+fieldRegion+'/vm-detach-volume';
        			operationCallback=function(data){
        				asyncData();
            		};
            		doOperation('detaching');
            		break;
            	default:
            		break;
    		}

    		
	   	}
    }
});
