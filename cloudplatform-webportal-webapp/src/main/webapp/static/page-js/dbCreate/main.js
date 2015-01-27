/**
 * Created by yaokuo on 2014/12/12.
 */
define(function(require){
    var common = require('../common');
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
  /*  $("#monthPurchaseBotton").click(function () {
        if(!$(this).hasClass("disabled")){
            var dbName = $("[name = 'dbName']").val();
            var hclusterId = $("[name = 'hclusterId']").val();
            var engineType = $("[name = 'engineType']").val();
            var linkType = $("[name = 'linkType']").val();
            var formData = {"dbName":dbName,"linkType":linkType,"engineType":engineType,"hclusterId":hclusterId};
            $(this).addClass("disabled");
            CreateDb(formData);
        }
    })*/
    
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
            dbName: {
                validMessage: '请按提示输入',
                validators: {
                    notEmpty: {
                        message: '数据库名称不能为空!'
                    },
                    stringLength: {
                        max: 16,
                        message: '数据库名名过长!'
                    }, regexp: {
                        regexp: /^([a-zA-Z_]+[a-zA-Z_0-9]*)$/,
                        message: "请输入字母数字或'_',数据库名不能以数字开头."
                    },
                    remote: {
                        message: '数据库名已存在!',
                        url: '/db/validate'
                    }
                }
            }
        }
    }).on('success.form.bv', function(e) {
        e.preventDefault();
        var dbName = $("[name = 'dbName']").val();
        var hclusterId = $("[name = 'hclusterId']").val();
        var engineType = $("[name = 'engineType']").val();
        var linkType = $("[name = 'linkType']").val();
        var formData = {"dbName":dbName,"linkType":linkType,"engineType":engineType,"hclusterId":hclusterId,"isCreateAdmin":1};
        CreateDb(formData);
    });
    /*表单验证 --end*/

    /*加载数据*/
    var dataHandler = require('./dataHandler');
    var createDbHandler = new dataHandler();
    GetHcluster();
    function GetHcluster(){
        var url="/hcluster";
        cn.GetData(url,createDbHandler.GetHclusterHandler);
    }
    /*创建数据库*/
    function CreateDb(data){
        var url="/db";
        cn.PostData(url,data, function () {
            location.href = "/list/db";
        });
    }
});
