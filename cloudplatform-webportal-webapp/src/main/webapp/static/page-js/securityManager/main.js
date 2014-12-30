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

    /*初始化按钮 --begin*/
    $("#modifyIpList").click(function () {
        $("#ipList").addClass("hide");
        $("#ipForm").removeClass("hide");
    })
    $("[name = 'submitIpForm']").click(function () {
        $("#ipForm").addClass("hide");
        $("#ipList").removeClass("hide");
        var dbId = $("#dbId").val();
        var ips = $("#iplist-textarea").val();
        cn.PostData(
            "/dbIp",
            {
                dbId:dbId,
                ips:ips
            }
        );
        asyncModifyIpData();
    })
    $("[name = 'cancleIpForm']").click(function () {
        $("#ipForm").addClass("hide");
        $("#ipList").removeClass("hide");
    })
    $("#refresh").click(function() {
        asyncModifyIpData();
    });
   /*初始化按钮 --end*/

    /*加载数据*/
    var dataHandler = require('./dataHandler');
    var securityDataHandler = new dataHandler();

    asyncModifyIpData();
    function asyncModifyIpData(){
        window.setTimeout(function () {
            cn.GetData("/dbIp/"+$("#dbId").val(),securityDataHandler.DbUserIpListHandler);   //获取IP列表信息
        },500);
    }
});
