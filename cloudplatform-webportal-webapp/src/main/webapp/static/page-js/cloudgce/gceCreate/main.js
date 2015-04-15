/**
 * Created by yaokuo on 2014/12/12.
 */
define(function(require){
    var common = require('../../common');
    var cn = new common();
    var $ = require("jquery");
    require("bootstrapValidator")($);

    /*禁用退格键退回网页*/
    window.onload=cn.DisableBackspaceEnter();

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
        	gceName:{
				validMessage: '请按提示输入',
				validators: {
					notEmpty: {
						message: '云应用名不能为空!'
					}, stringLength: {
						max: 16,
						message: '云应用名过长!'
					}, regexp: {
						regexp: /^[a-zA-Z_]+[a-zA-Z_0-9]*$/,
						message: "请输入字母数字或'_',云应用名不能以数字开头."
					}
				}
			}
        }
    }).on('success.form.bv', function(e) {
    	e.preventDefault();
		var createGceData = {
			gceName : $("[name = gceName]").val(),
			hclusterId : $("[name = 'hclusterId']").val(),
			gceImageName:$("[name = gceImageName]").val(),
            type:$("[name = type]").val()
		}
		var url = "/gce";
		cn.PostData(url, createGceData, function () {
			location.href = "/list/gce";
		});
    });
    /*表单验证 --end*/

    /*加载数据*/
    var dataHandler = require('./dataHandler');
    var createDbHandler = new dataHandler();
    GetHcluster();
    GetImageByType();
    $("[name = type]").change(function(){
    		setTimeout(GetImageByType,100);
  	})
    
    function GetHcluster(){
        var url="/hcluster";
        cn.GetData(url,createDbHandler.GetHclusterHandler);
    }
    function GetImageByType(){
    	var type = $("[name = type]").val();
    	if(type == null){
    		return;
    	}
        var url="/gce/image/list/"+type;
        cn.GetData(url,createDbHandler.GetImageHandler);
    }
});
