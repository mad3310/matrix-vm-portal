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

<<<<<<< HEAD
    /*页面按钮初始化*/
    $(".toCreateAccount").click(function () {         //切换到创建账户
        $("#accountList").addClass("mc-hide");
        $("#ipListTab").addClass("mc-hide");
        $("#newAccountTab").removeClass("mc-hide");
    })

    $(".toAccountList").click(function () {         //切换到创建账户
        $("#newAccountTab").addClass("mc-hide");
        $("#ipListTab").addClass("mc-hide");
        $("#accountList").removeClass("mc-hide");
    })

    $(".toIpList").click(function () {         //切换到IP列表
        $("#newAccountTab").addClass("mc-hide");
        $("#accountList").addClass("mc-hide");
        $("#ipListTab").removeClass("mc-hide");
    })

    $("#modifyIpList").click(function () {
        $("#ipList").addClass("hide");
        $("#ipForm").removeClass("hide");
    })

    $(".ipFromBotton").click(function () {
        $("#ipForm").addClass("hide");
        $("#ipList").removeClass("hide");
    })


    cn.GetData("/dbUser/"+$("#dbId").val(),dbUser.DbUserListHandler);
    cn.GetData("/static/page-js/accountManager/analogData/ipdata.json",dbUser.DbUserIpHandler);
=======
    asyncData();
    
	$("#refresh").click(function() {
		asyncData();
	});
    function asyncData() {
    	cn.GetData("/dbUser/"+$("#dbId").val(),dbUser.DbUserListHandler);
    }
    //cn.GetData("/static/page-js/accountManager/analogData/ipdata.json",dbUser.DbUserIpHandler);
>>>>>>> branch 'develop' of git@git.letv.cn:liuhao1/letv_mcluster_webportal.git
})
