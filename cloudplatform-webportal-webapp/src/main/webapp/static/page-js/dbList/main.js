/**
 * Created by yaokuo on 2014/12/12.
 */
define(function(require){
    var common = require('../common');
    var cn = new common();

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
	$('#paginator').bootstrapPaginator({
		size:"small",
    	alignment:'right',
		bootstrapMajorVersion:3,
		numberOfPages: 5,
		onPageClicked: function(e,originalEvent,type,page){
        	asyncData(page);
        }
	});
    function asyncData(page) {
        var dbName = $("#dbName").val(),location = $("#location").val();
    	if(!page) page = cn.currentPage;
    	var url = "/db?currentPage=" + page +"&&recordsPerPage=" + cn.recordsPerPage + "&&dbName=" + dbName + "&&location=" + location;
    	cn.GetData(url,dbListHandler.DbListHandler);
    }
});
