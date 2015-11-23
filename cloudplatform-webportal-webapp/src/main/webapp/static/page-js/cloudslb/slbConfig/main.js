/**
 * Created by yaokuo on 2014/12/12.
 */
define(function(require){
	var $ = require('jquery');
    require("bootstrapValidator")($);
    var common = require('../../common');
    var cn = new common();


    /*加载数据*/
    var dataHandler = require('./dataHandler');
    var slbConfigHandler = new dataHandler();

    asyncData();
    function asyncData() {
        url = "/slbConfig?currentPage=1&&recordsPerPage=150&&slbId="+$("#slbId").val();
        cn.GetData(url,slbConfigHandler.SlbConfigHandler);
    }

    $('#healthBase').bootstrapValidator({
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            frontendPort:{
                validMessage: '请按提示输入',
                validators: {
                    notEmpty: {
                        message: '端口不能为空!'
                    },integer: {
                        message: '请输入整数'
                    },between:{
                        min:0,
                        max:65535,
                        message:'端口号为：0-65535'
                    }
                }
            }
        }
    }).on('success.form.bv', function(e) {
        e.preventDefault();
        var createConfigData = {
            slbId: $("#slbId").val(),
            agentType:$("[name = agentType]").val()
        }
        var url = "/slbConfig";
        cn.PostData(url, createConfigData, function () {
            /*刷新本身ifame*/
            cn.RefreshIfame();
        });
    })
    
     $('#modifyHealthBase').bootstrapValidator({
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            frontendPort:{
                validMessage: '请按提示输入',
                validators: {
                    notEmpty: {
                        message: '端口不能为空!'
                    },integer: {
                        message: '请输入整数'
                    },between:{
                        min:0,
                        max:65535,
                        message:'端口号为：0-65535'
                    }
                }
            }
        }
    }).on('success.form.bv', function(e) {
        e.preventDefault();
        var createConfigData = {
            id: $("#id").val(),
            agentType:$("[name = modifyAgentType]").val()
        }
        var url = "/slbConfig/modify";
        cn.PostData(url, createConfigData, function () {
            cn.RefreshIfame();
        });
    })
});
