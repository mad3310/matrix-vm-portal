/**
 * Created by yaokuo on 2014/12/12.
 */
define(function(require){
    var common = require('../../common');
    var cn = new common();
    var $ = require("jquery");
    require("bootstrapValidator")($);

	/* 防止误操作退出创建页面 */
	cn.AddBeforeunloadListener();

    if(document.getElementById("monthPurchaseBotton").form == null){    //兼容IE form提交
        $("#monthPurchaseBotton").click(function(){
            $("#monthPurchaseForm").submit();
        })
    }

    /*按钮组件封装 --begin*/
    $(".bk-button-primary").click(function () {
        if(!$(this).hasClass("disabled")){
            $(this).parent().find(".bk-button-primary").removeClass("bk-button-current");
            $(this).addClass("bk-button-current");
            if($(this).parent().find(".hide").length > 0 ){
                var val = $(this).val();
                $(this).parent().find(".hide").val(val);
            }
        }
    })
    /*按钮组件封装 --end*/

    /*表单验证 --begin*/
    $("#monthPurchaseForm").bootstrapValidator({
        message: 'This value is not valid',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
        	slbName:{
				validMessage: '请按提示输入',
				validators: {
					notEmpty: {
						message: '负载均衡名不能为空!'
					}, stringLength: {
						max: 16,
						message: '负载均衡名过长!'
					}, regexp: {
						regexp: /^[a-zA-Z_]+[a-zA-Z_0-9]*$/,
						message: "请输入字母数字或'_',负载均衡名不能以数字开头."
					}
				}
			}
        }
    }).on('success.form.bv', function(e) {
    	e.preventDefault();
		var createConfigData = {
			slbName : $("[name = slbName]").val(),
			hclusterId : $("[name = 'hclusterId']").val()
		}
		cn.RemoveBeforeunloadListener();
		var url = "/slb";
		cn.PostData(url, createConfigData, function () {
			location.href = "/list/slb";
		});
    });
    /*表单验证 --end*/

    /*加载数据*/
    var dataHandler = require('./dataHandler');
    var createDbHandler = new dataHandler();
    GetHcluster();
    function GetHcluster(){
        var url="/hcluster/slb";
        cn.GetData(url,createDbHandler.GetHclusterHandler);
    }
});
