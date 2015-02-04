/**
 * Created by yaokuo on 2014/12/12.
 * dblist page
 */
define(function(require){
    var common = require('../common');
    var cn = new common();

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
	//setInterval(asyncData,cn.dbListRefreshTime);
	setInterval(asyncProgressData,1000);
	//asyncProgressData();
	
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
		//var url = "/static/page-js/dbList/jsondata.json";
		cn.GetSyncData(url,dbListHandler.DbListHandler);
	}
	 /*进度条部分*/
	function asyncProgressData(){
		var progressArr = $("input[name = progress_db_id]");
		//alert(progressArr.length);
		//console.log(progressArr.length);
		for(var i= 0, len= progressArr.length;i<len;i++){
			var db_id = $(progressArr[i]).val();
			//console.log(db_id);
			var url = "/build/db/" + db_id;
			progress(url,db_id);
		}
	}
	
	function progress(url,db_id){
		$.get(url,function(data){
			//var data = data.data;
			var data = db_id - 10 
	        var unitLen = 100 / 16;
	        var $obj = $("#Grise" + db_id);
	        var $prg = $obj.find(".charge");
	        var $load = $obj.find(".load > p");
	        $obj.removeClass("hide");
	    	if(data == 1){
	         	$prg.css({"width": unitLen + 'px'});
	         	$load.html("环境准备");
	         }else if(data == 2){
	         	$prg.css({"width": (unitLen * 2) + 'px'});
	         	$load.html("环境准备");
	         }else if(data == 3){
	         	$prg.css({"width": (unitLen * 3) + 'px'});
	         	$load.html("环境准备");
	         }else if(data == 4){
	         	$prg.css({"width": (unitLen * 4) + 'px'});
	         	$load.html("环境准备");
	         }else if(data == 5){
	         	$prg.css({"width": (unitLen * 5) + 'px'});
	         	$load.html("软件安装");
	         }else if(data == 6){
	         	$prg.css({"width": (unitLen * 6) + 'px'});
	         	$load.html("软件安装");
	         }else if(data == 7){
	         	$prg.css({"width": (unitLen * 7) + 'px'});
	         	$load.html("软件安装");
	         }else if(data == 8){
	         	$prg.css({"width": (unitLen * 8) + 'px'});
	         	$load.html("软件安装");
	         }else if(data == 9){
	         	$prg.css({"width": (unitLen * 9) + 'px'});
	         	$load.html("服务初始化");
	         }else if(data == 10){
	         	$prg.css({"width": (unitLen * 10) + 'px'});
	         	$load.html("服务初始化");
	         }else if(data == 11){
	         	$prg.css({"width": (unitLen * 11) + 'px'});
	         	$load.html("服务初始化");
	         }else if(data == 12){
	         	$prg.css({"width": (unitLen * 12) + 'px'});
	         	$load.html("服务初始化");
	         }else if(data == 13){
	         	$prg.css({"width": (unitLen * 13) + 'px'});
	         	$load.html("数据初始化");
	         }else if(data == 14){
	         	$prg.css({"width": (unitLen * 14) + 'px'});
	         	$load.html("数据初始化");
	         }else if(data == 15){
	         	$prg.css({"width": (unitLen * 15) + 'px'});
	         	$load.html("数据初始化");
	         }else if(data == 16){
	         	$prg.css({"width": (unitLen * 15) + 'px'});
	         	$load.html("数据初始化");
	         }else if(data == 0){
	         	$prg.css({"width": "100%"});
	         	$load.html("创建完成");
	         }else{
	         	$prg.css({"width": "100%"});
	         	$load.html("创建失败");
	         } 
	    	
	    	/*创建失败或者创建成功隐藏进度条*/
	    	var pValue = $load.html();
	    	
	    	if( pValue == "创建完成" ){
	    		if($obj.closest("tr").find("td:eq(2) span").length < 1){
	        		var html = "<span class=\"text-success\">正常</span>"
	    	        //console.log($obj);
	    	        $obj.parent().append(html);
	    	        $obj.hide();
	        	}
	    	}
	    	if(pValue == "创建失败"){
	    		if($obj.closest("tr").find("td:eq(2) span").length < 1){
	        		var html = "<span>创建失败</span>"
	    	        //console.log($obj);
	    	        $obj.parent().append(html);
	    	        $obj.hide();
	        	}
	    	}
	    	
		});
	}
});
