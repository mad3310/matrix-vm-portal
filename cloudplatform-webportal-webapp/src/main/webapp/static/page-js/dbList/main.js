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
	asynData();
	pageControl();
	$("#search").click(function() {
		asynData();
	});
	$("#refresh").click(function() {
		asynData();
	});
	
    function asynData() {
        var dbName = $("#dbName").val(),location = $("#location").val(),currentPage = $("#currentPage").val();
    	if(!currentPage) currentPage = cn.currentPage;
    	var url = "/db?currentPage=" + currentPage +"&&recordsPerPage=" + cn.recordsPerPage + "&&dbName=" + dbName + "&&location=" + location;
    	cn.GetData(url,dbListHandler.DbListHandler);
    }
    

    function pageControl() {
    	// 首页
    	$("#firstPage").bind("click", function() {
    		currentPage = 1;
    		asynData(currentPage,recordsPerPage);
    	});

    	// 上一页
    	$("#prevPage").click(function() {
    		if (currentPage == 1) {
    			$.gritter.add({
    				title: '警告',
    				text: '已到达首页',
    				sticky: false,
    				time: '5',
    				class_name: 'gritter-warning'
    			});
    	
    			return false;
    			
    		} else {
    			currentPage--;
    			asynData(currentPage,recordsPerPage);
    		}
    	});

    	// 下一页
    	$("#nextPage").click(function() {
    		if (currentPage == $("#totalPage_input").val()) {
    			$.gritter.add({
    				title: '警告',
    				text: '已到达末页',
    				sticky: false,
    				time: '5',
    				class_name: 'gritter-warning'
    			});
    	
    			return false;
    			
    		} else {
    			currentPage++;
    			asynData(currentPage,recordsPerPage);
    		}
    	});

    	// 末页
    	$("#lastPage").bind("click", function() {
    		currentPage = $("#totalPage_input").val();
    		asynData(currentPage,recordsPerPage);
    	});
    }
});
