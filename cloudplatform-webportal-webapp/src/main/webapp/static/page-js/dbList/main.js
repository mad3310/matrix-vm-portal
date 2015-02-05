/**
 * Created by yaokuo on 2014/12/12.
 * dblist page
 */
define(function(require){
    var common = require('../common');
    var cn = new common();
    var  $ = require('jquery');
    require('bootstrap')($);

    cn.Tooltip();
    

	/*禁用退格键退回网页*/
	window.onload=cn.DisableBackspaceEnter();

    /*加载数据*/
    var dataHandler = require('./dataHandler');
    var dbListHandler = new dataHandler();
    /*
     * 初始化数据
     */
	asyncData();
	/*定时刷新
	if ($(".progress").length == 0){
		setInterval(asyncData,cn.dbListRefreshTime);
	}else{
		setInterval(asyncProgressData,2000);
	}*/
	setInterval(asyncData,cn.dbListRefreshTime);
	setInterval(asyncProgressData,1000);
	
	$("#search").click(function() {
		cn.currentPage = 1;
		asyncData();
	});
	$("#refresh").click(function() {		
		asyncData();
	});
	$("#dbName").keydown(function(e){
		if(e.keyCode==13){
			cn.currentPage = 1;
			asyncData();
		}
	});
	
	/*初始化按钮*/
	$(".btn-region-display").click(function(){
		$(".btn-region-display").removeClass("btn-success").addClass("btn-default");
		$(this).removeClass("btn-default").addClass("btn-success");
		$("#dbName").val("");
		asyncData();
	})
	
	/*
	 * 可封装公共方法 begin
	 */
	//初始化分页组件
	$('#paginator').bootstrapPaginator({
		size:"small",
    	alignment:'right',
		bootstrapMajorVersion:3,
		numberOfPages: 5,
		onPageClicked: function(e,originalEvent,type,page){
			cn.currentPage = page;
        	asyncData(page);
        }
	});
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
	function asyncData(page) {
		var dbName = $("#dbName").val(),location = $("#location").val();
		if(!page) page = cn.currentPage;
		var url = "/db?currentPage=" + page +"&&recordsPerPage=" + cn.recordsPerPage + "&&dbName=" + dbName + "&&location=" + location;
		cn.GetSyncData(url,dbListHandler.DbListHandler);
		cn.Tooltip();
	}
	
	 /*进度条数据刷新*/
	function asyncProgressData(){
		var progressArr = $("input[name = progress_db_id]");
		console.log(progressArr.length);
		for(var i= 0, len = progressArr.length;i < len;i++){
			var db_id = $(progressArr[i]).val();
			var url = "/build/db/" + db_id;
			progress(url,db_id);
		}
	}
	
	 /*进度条进度控制*/
	function progress(url,db_id){
		$.get(url,function(data){
			var data = data.data;
			//var data = db_id - 10 
	        var unitLen = 100 / 16;
	        var $obj = $("#prg" + db_id);
	        var $prg = $obj.find(".progress-bar");
	       
	        /*控制进度条状态*/
	       	var pWidth = unitLen * data;
        	if( data > 1 && data <= 4){
        		$prg.css({"width": pWidth + 'px'});
        		$prg.html( pWidth + "%");
        		$obj.tooltip({
    			    title: '环境准备'
    			});
        	}else if (data > 4 && data <= 8){
        		$prg.css({"width": pWidth + 'px'});
        		$prg.html( pWidth + "%");
        		$obj.tooltip({
    			    title: '软件安装'
    			});
        	}else if (data > 8 && data <= 12){
        		$prg.css({"width": pWidth + 'px'});
        		$prg.html( pWidth + "%");
        		$obj.tooltip({
    			    title: '服务初始化'
    			});
        	}else if (data > 8 && data <= 12){
        		$prg.css({"width": pWidth + 'px'});
        		$prg.html( pWidth + "%");
        		$obj.tooltip({
    			    title: '数据初始化'
    			});
        	}else if (data == 0){
        		$prg.css({"width": "100%"});
        		$prg.html("100%");
        		$obj.tooltip({
    			    title: '创建完成'
    			});
        		asyncData;
        	}else{
        		$prg.css({"width": "100%"});
        		$prg.html("100%");
        		$obj.tooltip({
    			    title: '创建失败'
    			});
        		asyncData;
        	}
		});
	}
});
