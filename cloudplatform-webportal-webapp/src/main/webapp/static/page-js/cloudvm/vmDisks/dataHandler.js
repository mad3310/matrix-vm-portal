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
                    tdList.unshift("<tr class='data-tr'>");
                    tdList.push("</tr>");
                    
                    $tby.append(tdList.join(""));
                 }
            }
       
        },
	   	resetSelectAllCheckbox:function(){
			$('th input:checkbox').prop('checked', false);
			//$('tfoot input:checkbox').prop('checked', false);
	   	}
    }
});
