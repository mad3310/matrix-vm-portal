/**
 * Created by yaokuo on 2014/12/12.
 */
define(function(require){
    var common = require('../common');
    var cn = new common();

	cn.Tooltip();

	/*禁用退格键退回网页*/
	window.onload=cn.DisableBackspace();

    /*加载数据*/
    var dataHandler = require('./dataHandler');
    var dbListHandler = new dataHandler();
	
    /*
     * 初始化数据
     */
	asyncData();
	$("#search").click(function() {
		asyncData();
	});
	$("#refresh").click(function() {
		asyncData();
	});
	$("#dbName").keydown(function(e){
		if(e.keyCode==13){
			asyncData();
		}
	});
	/*初始化按钮*/


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
		$(this).closest('table').find('tr > td:first-child input:checkbox')
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
    	cn.GetData(url,dbListHandler.DbListHandler);
    }
});
