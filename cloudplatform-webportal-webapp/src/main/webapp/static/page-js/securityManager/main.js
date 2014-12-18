/**
 * Created by yaokuo on 2014/12/12.
 */
define(function(require){
    var common = require('../common');
    var cn = new common();
    
    /*初始化侧边栏菜单*/
    var index = [8,0];
    cn.Sidebar(index);//index为菜单中的排序(1-12)
   
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
