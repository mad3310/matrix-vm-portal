define(function(require){
	var $=require('jquery');
	var Common=require('../../common');
	var cn=new Common();
	
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
	cn.Tooltip();
    
	/*禁用退格键退回网页*/
	window.onload=cn.DisableBackspaceEnter();

    /*加载数据*/
    var DataHandler = require('./dataHandler');
    var hdler = new DataHandler();
    /*
     * 初始化数据
     */
	asyncData();
	
	$("#search").click(function() {
		cn.currentPage = 1;
		asyncData();
	});
	$("#refresh").click(function() {		
		asyncData();
	});
	$("#cacheName").keydown(function(e){
		if(e.keyCode==13){
			cn.currentPage = 1;
			asyncData();
		}
	});
	 /*初始化按钮*/
	$(".btn-region-display").click(function(){
		$(".btn-region-display").removeClass("btn-success").addClass("btn-default");
		$(this).removeClass("btn-default").addClass("btn-success");
		$("#cacheName").val("");
		asyncData();
	});
	//加载列表数据
	function asyncData(page) {
		var cacheName = $("#cacheName").val(),location = $("#location").val();
		if(!page) page = cn.currentPage;
		var url = "/cache?currentPage=" + page +"&&recordsPerPage=" + cn.recordsPerPage + "&&cacheName=" + dbName + "&&location=" + location;
		cn.GetData(url,refreshCtl);
	}
	function refreshCtl(data) {
		hdler.DbListHandler(data);
		if ($(".progress").length == 0){
			if(pFresh){
				clearInterval(pFresh);
			}
			if(iFresh){
				clearInterval(iFresh);
			}
			iFresh = setInterval(asyncData,cn.dbListRefreshTime);
		}else{
			asyncProgressData();
			if(iFresh){
				clearInterval(iFresh);
			}
			if(pFresh){
				clearInterval(pFresh);
			}
			pFresh = setInterval(asyncProgressData,10000);
		}
	}
	/*进度条数据刷新*/
	function asyncProgressData(){
		$("input[name = progress_db_id]").each(function(){
			var dbId = $(this).val();
			function progress_func(data){
				hdler.progress(dbId,data,asyncData);
			}
			var url = "/build/db/" + dbId;
			cn.GetLocalData(url,progress_func);
		})
	}	
})