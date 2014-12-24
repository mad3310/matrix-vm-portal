/**
 * Created by yaokuo on 2014/12/12.
 */
define(function(require){
	var $ = require('jquery');
    var common = require('../common');
    var cn = new common();
    
    /*初始化标签页*/
	$('#setab a').click(function (e) {
		e.preventDefault()
		$(this).tab('show')
	})
   $("#sqlInject-tab").click(function() {
      $("#refresh").hide();
	}); 
   $("#whitelist-tab").click(function() {
      $("#refresh").show();
	}); 
   
    /*加载数据*/
    var dataHandler = require('./dataHandler');
    var basicInfoHandler = new dataHandler();

   // cn.GetData("/static/page-js/basicInfo/analogData/dblist.json",basicInfoHandler.BasicInfoHandler);
});
