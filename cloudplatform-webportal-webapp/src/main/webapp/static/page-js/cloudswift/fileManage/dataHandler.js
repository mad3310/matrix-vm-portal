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
        fileListHandler : function(data){
        	$(".data-tr").remove();
        	
            var $tby = $('#tby');
            var array = data.data;
            if(array.length == 0){
            	cn.emptyBlock($tby);
            }else{
                for(var i= 0, len= array.length;i<len;i++){
                    if(array[i].name){
                      var td1 = $("<td width=\"10\">"
                            + "<input type=\"checkbox\" name=\"mcluster_id\" value= \""+array[i].name+"\">"
                            + "</td>");
                      var td2 = $("<td class=\"padding-left-32\">"
                              + array[i].name
                              +"</td>");
                      var td3 = '';
                      if(array[i].content_type=='application/directory') {//目录
                         td3 = $("<td>文件夹</td>");
                      }else{
                          var filetypeindex=array[i].name;
                          var filetype=filetypeindex.substring(filetypeindex.indexOf('.')+1);
                          td3 = $("<td>"+filetype+"</td>");
                      }
                      var td4 = $("<td>"
                            + "<span>"+array[i].last_modified+"</span>"
                            + "</td>");
                      var td5 = $("<td><span>"+array[i].bytes+"</span></td>");
                      var td6 = $("<td><span >未设置</span></td>");
                      var td7 = $("<td><span>删除</span></td>");
                      var tr = $("<tr class='data-tr'></tr>");
                      tr.append(td1).append(td2).append(td3).append(td4).append(td5).append(td6).append(td7);
                      tr.appendTo($tby);
                    }            
                    // var td4 = $("<td>"
                    //         + "<span>"+array[i].last_modified+"</span>"
                    //         + "</td>");
                    // var td5 = $("<td><span>"+array[i].bytes+"</span></td>");
                    // var td6 = $("<td><span >未设置</span></td>");
                    // var td7 = $("<td><span>删除</span></td>");
                    // var tr = $("<tr class='data-tr'></tr>");
                    // tr.append(td1).append(td2).append(td3).append(td4).append(td5).append(td6).append(td7);
                    // tr.appendTo($tby);
                 }
            }
       
            /*
             * 设置分页数据
             */
         //    $("#totalRecords").html(data.data.totalRecords);
         //    $("#recordsPerPage").html(data.data.recordsPerPage);
            
         //    if(data.data.totalPages < 1){
        	// 	data.data.totalPages = 1;
        	// };
        	
         //    $('#paginator').bootstrapPaginator({
         //        currentPage: data.data.currentPage,
         //        totalPages:data.data.totalPages
         //    });
        }
    }
});