/**
 * file list page
 */
define(function(require){
	var pFresh,iFresh;
    var common = require('../../common');
    var cn = new common();
    
    cn.Tooltip();
    
	/*禁用退格键退回网页*/
	window.onload=cn.DisableBackspaceEnter();

    /*加载数据*/
    var dataHandler = require('./dataHandler');
    var fileListHandler = new dataHandler();
    /*
     * 初始化数据
     */
	asyncData();
	
	// $("#search").click(function() {
	// 	cn.currentPage = 1;
	// 	asyncData();
	// });
	$("#refresh").click(function() {		
		var dirname=$('#dirName').val();var url;
		if(dirname){
			url="/oss/"+$("#swiftId").val()+"/file?directory="+dirname;
		}else{
			url="/oss/"+$("#swiftId").val()+"/file?directory=root";
		}
		cn.GetData(url,refreshCtl);
	});
	// $("#fileName").keydown(function(e){
	// 	if(e.keyCode==13){
	// 		cn.currentPage = 1;
	// 		asyncData();
	// 	}
	// });
	
	/*初始化按钮*/
	// $(".btn-region-display").click(function(){
	// 	$(".btn-region-display").removeClass("btn-success").addClass("btn-default");
	// 	$(this).removeClass("btn-default").addClass("btn-success");
	// 	$("#fileName").val("");
	// 	asyncData();
	// })
	
	/*
	 * 可封装公共方法 begin
	 */
	//初始化分页组件
	// $('#paginator').bootstrapPaginator({
	// 	size:"small",
 //    	alignment:'right',
	// 	bootstrapMajorVersion:3,
	// 	numberOfPages: 5,
	// 	onPageClicked: function(e,originalEvent,type,page){
	// 		cn.currentPage = page;
 //        	asyncData(page);
 //        }
	// });
	//初始化checkbox
	$(document).on('click', 'th input:checkbox' , function(){
		var that = this;
		$(this).closest('table').find('tr > td:first-child input:checkbox')
		.each(function(){
			this.checked = that.checked;
			$(this).closest('tr').toggleClass('selected');
		});
	});
	$(document).on('click', 'tfoot input:checkbox' , function(){
		var that = this;
		$(this).closest('table').find('tr > td:first-child input:checkbox,th input:checkbox ')
		.each(function(){
			this.checked = that.checked;
			$(this).closest('tr').toggleClass('selected');
		});
	});
	/*
	 * 可封装公共方法 end
	 */
	
	//加载列表数据
	function asyncData() {
		var url = "/oss/"+$("#swiftId").val()+"/file?directory=root";
		cn.GetData(url,refreshCtl);
		
	}
	function refreshCtl(data) {
		fileListHandler.fileListHandler(data);
		dirClick();
		// returnDir();
	}
	function dirClick(){
      var _target=$('table').find('a');
      _target.each(function() {
        $(this).click(function(event) {
	    	var dirname=$(this).parent().prev().children('input').val();
	    	var dirarry=dirname.split('/');
	    	var location='<span class="dirPath" name="root">当前位置：根目录 /</span> ';
	    	for(i in dirarry){
	    		location=location+'<span class="dirPath" name="'+dirarry[i]+'">'+dirarry[i]+' /</span> '
	    	}
          $('#dirName').val(dirname);
          $('[name="dirName"]').html(location);
          var url = "/oss/"+$("#swiftId").val()+"/file?directory="+$('#dirName').val();
          cn.GetData(url,refreshCtl);
        });
      });
      var _location=$('.dirPath');
      _location.click(function(event) {
      	var url,dirname,location;
      	var tempname=$(this).attr('name');var j=tempname.length;
      	var tempdir=$('#dirName').val();var i=tempdir.indexOf(tempname,0)+j;
      	console.log(tempdir.substring(0,i)+"  "+i)
      	$(this).nextAll('.dirPath').addClass('hidden')
      	if(tempdir.substring(0,i)){
      		if(tempdir.substring(0,i)!='dir'){
      			url = "/oss/"+$("#swiftId").val()+"/file?directory="+tempdir.substring(0,i);
      			dirname=tempdir.substring(0,i);
      		}else{
      			url = "/oss/"+$("#swiftId").val()+"/file?directory=root";
      			dirname='';
      		}
      	}else{
      		url="/oss/"+$("#swiftId").val()+"/file?directory=root";

      	}
      	$('#dirName').val(dirname);
        cn.GetData(url,refreshCtl);
      });
    }
});
