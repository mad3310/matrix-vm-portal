/**
 * file list page
 */
define(function(require){
	var pFresh,iFresh;
    var common = require('../../common');
    var cn = new common();
    var $ = require("jquery");
    require("jquery.form")($);
    require("bootstrapValidator")($);
    cn.Tooltip();
    

	/* $('#form-upload').submit(function(){  
         $(this).ajaxSubmit(); 
         alert("123");
         return false;
     });*/
	(function () {
        $("#form-upload").ajaxForm({
        	success: function (data) {
        		$("#progress").width("101%").delay(200).fadeOut(400, function() {
				    $(this).remove();
				});
				$('#upload').val('');
        		asyncData();
            }
        });
    })();
	
    /*加载数据*/
    var dataHandler = require('./dataHandler');
    var fileListHandler = new dataHandler();
    /*
     * 初始化数据
     */
	asyncData();
	/*
	 * 操作按钮初始化
	 */
	$("#tby").click(function(e){
		e = e? e:window.event;
		var target = e.target || e.srcElement;
		var filePath = $(target).attr("file-path");
		var filetype=$(target).attr('file-type');
		var isfolder;
		if(filetype=='application/directory'){
			isfolder=true;
		}else{isfolder=false;}
		if(filePath != undefined && filePath != null){
			var url = "/oss/"+$("#swiftId").val()+"/file/del";
			var data = {
					file : filePath,
					isFolder:isfolder
			}
			cn.PostData(url,data,asyncData);
		}
	})
	//文件上传
	$('#upload').change(function(event) {
		if(cn.uploadfile(this)){//文件要求后缀和大小均符合
			var file=cn.getFile(this);
			var pathvalue=$('.dirPath:visible').text();var path='root';
			if(pathvalue){
				if(pathvalue!='当前位置：根目录 /'){
					path=$('#dirName').val();
				}
			}
			if ($("#progress").length === 0) {
			    $("body").append($("<div><dt/><dd/></div>").attr("id", "progress"));
			    $("#progress").width((50 + Math.random() * 30) + "%");
			}
            $("#dir").val(path);
            $("#form-upload").submit();
		}
	});
	
	//新建文件夹验证，提交
	$('#createDirform').bootstrapValidator({
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
        	folderName:{
        		validators: {
                    notEmpty: {
                        message: '文件夹名不能为空!'
                    },
                    stringLength: {
                        max: 254,
                        message: '文件夹名过长!'
                    },regexp: {
                        regexp: /^[a-zA-Z0-9\u4e00-\u9fa5_.-]*$/,
                        message: " 只能包含字母，数字，中文，下划线（_）和短横线（-）,小数点（.）"
                    }//重名则覆盖 新建和更新
                }
        	}
        }
    }).on('success.form.bv', function(e) {
    	e.preventDefault();
    	$("#add-dir").html("创建中...");
    	var folderName=$('#floderName').val();
    	var pathvalue=$('.dirPath:visible').text();var path='root';
		if(pathvalue){
			if(pathvalue!='当前位置：根目录 /'){
				path=$('#dirName').val();
			}
		}
		var data={
			'file':folderName,
			'directory':path
		}
		var url='/oss/'+$("#swiftId").val()+'/folder';
		cn.PostData(url,data,function(){
			asyncData();
			$("#addDirModal").modal("hide");
			$("#createDirform").data("bootstrapValidator").resetForm();
			$("#createDirform")[0].reset();
			$("#add-dir").html("创建");
		});
    });

	// $("#search").click(function() {
	// 	cn.currentPage = 1;
	// 	asyncData();
	// });
	$("#refresh").unbind('click').click(function() {		
		refreshFile();
	});
	function refreshFile(){
		var dirname=$('#dirName').val();var url;
		if(dirname){
			url="/oss/"+$("#swiftId").val()+"/file?directory="+dirname;
		}else{
			url="/oss/"+$("#swiftId").val()+"/file?directory=root";
		}
		cn.PostData(url,refreshCtl);
	}
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
		var directory = $('#dirName').val();
		if(!directory){
			directory="root";
		}
		var url = "/oss/"+$("#swiftId").val()+"/file?directory="+directory;
		cn.GetData(url,refreshCtl);
		var url2='/oss/'+$("#swiftId").val()+'/file/prefixUrl';
		cn.GetData(url2,function(data){
			$('#baseLocation').val(data.data);
		});
	}
	function refreshCtl(data) {
		fileListHandler.fileListHandler(data);
		dirClick();
		// returnDir();
	}
	function dirClick(){
      var _target=$('table').find('.dir-a');
      _target.each(function() {
        $(this).unbind('click').click(function(event) {
	    	var dirname=$(this).parent().prev().children('input').val();
	    	var dirarry='';
	    	if(dirname){
	    		dirarry=dirname.split('/');
	    	}
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
      _location.unbind('click').click(function(event) {
      	var url,dirname,location;
      	var tempname=$(this).attr('name');var j=tempname.length;
      	var tempdir=$('#dirName').val();var i=0;
      	i=tempdir.indexOf(tempname,0);
      	if(i>=0){//root
			i=tempdir.indexOf(tempname,0)+j;
      	}else{i=-1}
      	$(this).nextAll('.dirPath').addClass('hidden');
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