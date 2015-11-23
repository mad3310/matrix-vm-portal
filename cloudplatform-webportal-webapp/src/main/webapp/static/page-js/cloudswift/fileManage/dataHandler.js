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
                      var prefix=$('#dirName').val();
                      var fileName='';
                      if(prefix){
                        fileName=array[i].name;
                        fileName=fileName.substring(fileName.lastIndexOf('/')+1);
                      }else{
                        fileName=array[i].name;
                      }
                      var td1 = $("<td width=\"10\">"
                            + "<input type=\"checkbox\" name=\"mcluster_id\" value= \""+array[i].name+"\">"
                            + "</td>");
                      var td2='';
                      var td3 = '';
                      var td5='';
                      var td6='';
                      var td7='';
                      if(array[i].content_type=='application/directory') {//目录
                        // td2 = $("<td class=\"padding-left-32\"><a href='javascript:void(0)'><img src='/static/img/folder.png'/> "
                        //           + fileName
                        //           +"</a></td>");
                        td2 = $("<td class=\"padding-left-32\"><a class='dir-a' href='javascript:void(0)'><i class='fa fa-folder text-warning'></i> "
                                  + fileName
                                  +"</a></td>");
                        td3 = $("<td>文件夹</td>");
                        td5=$("<td class='hidden-xs'></td>");
                        td6=$("<td class='hidden-xs'>-</td>");
                        td7=$("<td class=\"text-right\"><a href=\"javascript:void(0)\"><span class=\"text-explode\" file-path=\""+array[i].name+"\" file-type=\""+array[i].content_type+"\">删除</span></a></td>");
                      }else{
                          var filetypeindex=fileName;
                          var filetype=filetypeindex.substring(filetypeindex.indexOf('.')+1);
                          td2 = $("<td class=\"padding-left-32\"><i class='text-muted "+iconSort(filetype.toLowerCase())+"'></i> "
                                  + fileName
                                  +"</td>");
                          td3 = $("<td>"+filetype+"</td>");
                          if(array[i].bytes/1073741824>=1){//GB 1024*1024*1024=1073741824
                            td5 = $("<td class='hidden-xs'><span>"+(array[i].bytes/1073741824).toFixed(2)+"GB</span></td>");
                          }else if(array[i].bytes/1048576>=1){//MB 1024*1024=1048576
                            td5 = $("<td class='hidden-xs'><span>"+(array[i].bytes/1048576).toFixed(2)+"MB</span></td>");
                          }else if(array[i].bytes/1024>=1){//KB
                            td5 = $("<td class='hidden-xs'><span>"+(array[i].bytes/1024).toFixed(2)+"KB</span></td>");
                          }else{//B
                            td5 = $("<td class='hidden-xs'><span>"+array[i].bytes+"B</span></td>");
                          }
                          var hrefstr=$('#baseLocation').val()+array[i].name;
                          td6= $("<td class='hidden-xs'><a href='"+hrefstr+"' target='_blank'><span>"+hrefstr+"</span></a></td>");
                          td7=$("<td class=\"text-right\"><a href=\"javascript:void(0)\"><span class=\"text-explode\"  file-path=\""+array[i].name+"\" file-type=\""+array[i].content_type+"\">删除</span></a></td>");
                          
                      }
                      var lasttime=array[i].last_modified;var indexT=lasttime.indexOf('T');var indexpoint=lasttime.indexOf('.');
                      var td4 = $("<td class='hidden-xs'>"
                            + "<span>"+lasttime.substring(0,indexT)+" "+lasttime.substring(indexT+1,indexpoint)+"</span>"
                            + "</td>");
                      
                      // var td6 = $("<td><span >未设置</span></td>");
                      // var td7 = $("<td><span class='text-explode font-disabled'>删除</span></td>");
                      var tr = $("<tr class='data-tr'></tr>");
                    }else{//子目录
                      
                    }            
                    tr.append(td1).append(td2).append(td3).append(td4).append(td5).append(td6).append(td7);
                    tr.appendTo($tby);
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
    function iconSort(type){
      var iconclass='fa fa-file';
      var icon={
        'exe':'fa fa-cog',

        'pdf':'fa fa-file-pdf-o',
        'txt':'fa fa-file-text',
        'docx':'fa fa-file-word-o',
        'doc':'fa fa-file-word-o',
        'xlsx':'fa fa-file-excel-o',
        'xls':'fa fa-file-excel-o',
        'pptx':'fa fa-file-powerpoint-o',
        'ppt':'fa fa-file-powerpoint-o',
      
        'png':'fa fa-file-photo-o',
        'jpg':'fa fa-file-photo-o',
        'gif':'fa fa-file-photo-o',
        'bmp':'fa fa-file-photo-o',

        'rar':'fa fa-file-zip-o',
        'zip':'fa fa-file-zip-o',
        'tar':'fa fa-file-zip-o',
        'jar':'fa fa-file-zip-o',
        'z':'fa fa-file-zip-o',
        'gz':'fa fa-file-zip-o',

        'avi':'fa fa-file-movie-o',
        'mov':'fa fa-file-movie-o',
        'wmv':'fa fa-file-movie-o',
        '3gp':'fa fa-file-movie-o',
        'flv':'fa fa-file-movie-o',

        'mp3':'fa fa-file-audio-o',
        'rm':'fa fa-file-audio-o',
        'swf':'fa fa-file-audio-o',
        'wma':'fa fa-file-audio-o',
        'wav':'fa fa-file-audio-o',
        'mp3pro':'fa fa-file-audio-o'
      }
      if(icon[type]){
        iconclass=icon[type];
      }
      return iconclass;
    }
});