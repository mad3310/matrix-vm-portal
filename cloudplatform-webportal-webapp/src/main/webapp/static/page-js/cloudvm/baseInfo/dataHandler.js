/**
 * Created by yaokuo on 2014/12/14.
 */
define(function(require,exports,module){
    var jQuery = $ = require('jquery');
    var common = require('../../common');
    var cn = new common();
   
    var DataHandler = function(){
    };
    var operatePublicIpBtnsVisible=function(bindVisible){
        if(!bindVisible){
        	$("#btn_publicip_bind").hide();
        	$("#btn_publicip_unbind").show();
        }
        else{
        	$("#btn_publicip_bind").show();
        	$("#btn_publicip_unbind").hide();
        }
    };

    module.exports = DataHandler;

    DataHandler.prototype = {
    		VmInfoHandler : function(data){
                var vmInfo = data.data;
                if(!vmInfo){
                	return;
                }
                var evaluateField=function(fieldId,fieldValue,fieldSuffix){
                	var value=(fieldValue===null||fieldValue===undefined || fieldValue==='')? '--':fieldValue+fieldSuffix;
        			$("#"+fieldId).html(value);
                };
                evaluateField('vm_info_vm_id',vmInfo.id,'');
                evaluateField('vm_info_vm_region',vmInfo.regionDisplayName,'');
                evaluateField('vm_info_vm_name',vmInfo && vmInfo.name  ,'');
                evaluateField('vm_info_vm_image',vmInfo.image && vmInfo.image.name,'');
                evaluateField('vm_info_running_state',cn.TranslateStatus(vmInfo.status),'');
                evaluateField('vm_info_config_flavor',vmInfo.flavor && vmInfo.flavor.name,'');
                evaluateField('vm_info_config_ram',vmInfo.flavor && vmInfo.flavor.ram,'MB');
                evaluateField('vm_info_config_vcpu',vmInfo.flavor && vmInfo.flavor.vcpus,'内核');                
                evaluateField('vm_info_config_disk',vmInfo.flavor && vmInfo.flavor.disk,'GB');
                evaluateField('vm_info_network_privateip',vmInfo.ipAddresses.private.join(', '),'');
                evaluateField('vm_info_network_publicip',vmInfo.ipAddresses.public.join(', '),'');
                evaluateField('vm_info_network_sharedip',vmInfo.ipAddresses.shared.join(', '),'');
                evaluateField('vm_info_config_volumes',vmInfo.volumes.map(function(element){return element.size+'GB';}).join(', '),'');
                operatePublicIpBtnsVisible(!vmInfo.ipAddresses.public.length);
        },
        operatePublicIpBtnsVisible:operatePublicIpBtnsVisible
    
    }
});