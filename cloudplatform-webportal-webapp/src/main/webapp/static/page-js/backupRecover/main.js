/**
 * Created by yaokuo on 2014/12/12.
 * backup page js
 */
define(function(require){
    var common = require('../common');
    var cn = new common();
	/*禁用退格键退回网页*/
	window.onload=cn.DisableBackspaceEnter();

    /*加载数据*/
    var dataHandler = require('./dataHandler');
    var dbListHandler = new dataHandler();
	
    /*
     * 初始化数据
     */
	asyncData();

	$("#bksearch").click(function() {
		asyncData();
	});
   
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
	//加载列表数据
	function asyncData(page) {
		if(!page) page = cn.currentPage;
		if(perpage != "undefined") { var perpage = cn.recordsPerPage};
		var startTime = $("#startTime").val();
		var endTime = $("#endTime").val();
		var url = "/backup?dbId=" + $("#dbId").val() + "&&startTime=" + startTime + "&&endTime=" + endTime + "&&currentPage=" + page + "&&recordsPerPage=" + perpage;
		cn.GetData(url,dbListHandler.DbListHandler);
	}
});
