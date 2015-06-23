/**
 * Created by yaokuo on 2014/12/14.
 */
define(function(require,exports,module){
    var jQuery = $ = require('jquery');
    require('zclip');
    var common = require('../../common');
    var cn = new common();
   
    var DataHandler = function(){
    };

    module.exports = DataHandler;

    DataHandler.prototype = {
    		VmInfoHandler : function(data){
                var vmInfo = data.data;
                var evaluateField=function(fieldId,fieldValue){
        			$("#"+fieldId).html(fieldValue);
                };
                evaluateField('vm_info_vm_id',vmInfo.id);
                evaluateField('vm_info_vm_region',vmInfo.region);
                evaluateField('vm_info_vm_name',vmInfo.name);
                evaluateField('vm_info_vm_image',vmInfo.image.name);
                evaluateField('vm_info_running_state',cn.TranslateStatus(vmInfo.status));
                evaluateField('vm_info_config_flavor',vmInfo.flavor.name);
                evaluateField('vm_info_config_ram',vmInfo.flavor.ram+'MB');
                evaluateField('vm_info_config_vcpu',vmInfo.flavor.vcpus+'虚拟内核');
                
                evaluateField('vm_info_config_disk',vmInfo.flavor.disk+'GB');
                evaluateField('vm_info_network_ip',vmInfo.ipAddresses.join(','));
                               
        }        
    }
});