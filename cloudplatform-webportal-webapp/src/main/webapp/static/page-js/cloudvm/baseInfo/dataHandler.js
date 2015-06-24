/**
 * Created by yaokuo on 2014/12/14.
 */
define(function(require,exports,module){
    var jQuery = $ = require('jquery');
    var common = require('../../common');
    var cn = new common();
   
    var DataHandler = function(){
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
                evaluateField('vm_info_vm_region',vmInfo.region,'');
                evaluateField('vm_info_vm_name',vmInfo && vmInfo.name  ,'');
                evaluateField('vm_info_vm_image',vmInfo.image && vmInfo.image.name,'');
                evaluateField('vm_info_running_state',cn.TranslateStatus(vmInfo.status),'');
                evaluateField('vm_info_config_flavor',vmInfo.flavor && vmInfo.flavor.name,'');
                evaluateField('vm_info_config_ram',vmInfo.flavor && vmInfo.flavor.ram,'MB');
                evaluateField('vm_info_config_vcpu',vmInfo.flavor && vmInfo.flavor.vcpus,'虚拟内核');                
                evaluateField('vm_info_config_disk',vmInfo.flavor && vmInfo.flavor.disk,'GB');
                evaluateField('vm_info_network_ip',vmInfo.ipAddresses.join(','),'');
                               
        }        
    }
});