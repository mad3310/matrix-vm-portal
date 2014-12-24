/**
 * Created by yaokuo on 2014/12/12.
 */
define(function(require){
    var common = require('../common');
    var cn = new common();

    var $ = require("jquery");

    /*加载数据*/
    var dataHandler = require('./dataHandler');
    var dbUser = new dataHandler();

    asyncData();
    
	$("#refresh").click(function() {
		asyncData();
	});
    function asyncData() {
    	cn.GetData("/dbUser/"+$("#dbId").val(),dbUser.DbUserListHandler);
    }
    //cn.GetData("/static/page-js/accountManager/analogData/ipdata.json",dbUser.DbUserIpHandler);
})