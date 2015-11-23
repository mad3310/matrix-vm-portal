/**
 * Created by jinglinlin@letv.com on 2015/01/22.
 * backup page js
 */
define(function(require){
	var common = require('../../common');
	var cn = new common();

	/*初始化时间输入框*/
	 require.async(['moment', 'jquery','datetimepicker'], function() {  
		 $("#startTime").datetimepicker({
				viewMode: 'days',
				format:'L',
				locale: 'zh-cn'
			});
			$("#endTime").datetimepicker({
				viewMode: 'days',
				format: 'L',
				locale: 'zh-cn'
			});
	    });  
	
	

    /*加载数据*/
    var dataHandler = require('./dataHandler');
    var backupRecover = new dataHandler();
    var flag = false; //flag 标记是否已点击查询，true已点击，false未点击

    /*
     * 初始化数据
     */
	asyncData();
	
	/*设置备份时间*/
    $("#backupTime").html(cn.getBackupDate()+ ' '+'4:00 AM');
	$("#bksearch").click(function() {
		cn.currentPage = 1;
		flag = true;
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
        	asyncData(page);
        }
	});
	//加载列表数据
	function asyncData(page) {
		if(!page) page = cn.currentPage;
		if(perpage != "undefined") { var perpage = cn.recordsPerPage};
		if(flag == true){
			var startTime = $("#startTime").val();
			var endTime = $("#endTime").val();
		}else{
			var startTime = '';
			var endTime = '';
		}
		var url = "/backup?dbId=" + $("#dbId").val() + "&&startTime=" + startTime + "&&endTime=" + endTime + "&&currentPage=" + page + "&&recordsPerPage=" + perpage;
		cn.GetData(url,backupRecover.DbListHandler);
	}
});
