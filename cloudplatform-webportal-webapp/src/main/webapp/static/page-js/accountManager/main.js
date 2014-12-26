/**
 * Created by yaokuo on 2014/12/12.
 */
define(function(require){
    var common = require('../common');
    var cn = new common();
    var $ = require("jquery");
    require("bootstrapValidator")($);
    /*加载数据*/
    var dataHandler = require('./dataHandler');
    var dbUser = new dataHandler();

    /*页面按钮初始化 --start*/
    $(".toCreateAccount").click(function () {           //切换到创建账户
        $("#accountList").addClass("mc-hide");
        $("#ipListTab").addClass("mc-hide");
        $("#newAccountTab").removeClass("mc-hide");
    })

    $(".toAccountList").click(function () {             //切换到创建账户
        $("#newAccountTab").addClass("mc-hide");
        $("#ipListTab").addClass("mc-hide");
        $("#accountList").removeClass("mc-hide");
    })

    $(".toIpList").click(function () {                   //切换到IP列表
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
    /*页面按钮初始化 --end*/


    $('#db_user_create_form').bootstrapValidator({
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            username: {
                validators: {
                    notEmpty: {
                        message: '用户名不能为空!'
                    },
                    stringLength: {
                        max: 16,
                        message: '用户名过长!'
                    }, regexp: {
                        regexp: /^([a-zA-Z_]+[a-zA-Z_0-9]*)$/,
                        message: "请输入字母数字或'_',用户名不能以数字开头."
                    }
                }
            },
            readWriterRate: {
                validMessage: '请按提示输入',
                validators: {
                    notEmpty: {
                        message: '读写比例不能为空!'
                    },
                    regexp: {
                        regexp: /^((\d|\d\d|\d\d\d)(\:(\d|\d\d|\d\d\d))){1}$/,
                        message: '请按提示输入'
                    }
                }
            },
            maxConcurrency: {
                validMessage: '请按提示输入',
                validators: {
                    notEmpty: {
                        message: '最大并发量不能为空!'
                    },integer: {
                        message: '请输入数字'
                    }
                }
            }
        }
    });
  /*  $('#db_user_edit_form').bootstrapValidator({
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            readWriterRate: {
                validMessage: '请按提示输入',
                validators: {
                    notEmpty: {
                        message: '读写比例不能为空!'
                    },
                    regexp: {
                        regexp: /^((\d|\d\d|\d\d\d)(\:(\d|\d\d|\d\d\d))){1}$/,
                        message: '请按提示输入'
                    }
                }
            },
            maxConcurrency: {
                validMessage: '请按提示输入',
                validators: {
                    notEmpty: {
                        message: '最大并发量不能为空!'
                    },integer: {
                        message: '请输入数字'
                    }
                }
            },
        acceptIp: {
            validators: {
                notEmpty: {
                    message: '地址不能为空'
                },
                regexp: {
                    regexp: /^(\d|\d\d|1\d\d|2[0-4]\d|25[0-5])((\.(\d|\d\d|1\d\d|2[0-4]\d|25[0-5]))|(\.\%)){3}$/,
                    message: '请按提示格式输入'
                },
                remote: {
                    url: '${ctx}/dbUser/validate' ,
                    data: function(validator) {
                        return {
                            username: validator.getFieldElements('username').val(),
                            dbId: validator.getFieldElements('dbId').val()
                        };
                    },
                    message: '该用户名此IP已存在!'
                }
            }
        }
        }
    }).on('error.field.bv', function(e, data) {
        $('#edit-dbUser-botton').addClass("disabled");
    }).on('success.field.bv', function(e, data) {
        $('#edit-dbUser-botton').removeClass("disabled");
    });*/

    asyncData();
	$("#refresh").click(function() {
		asyncData();
	});
    function asyncData() {
    	cn.GetData("/dbUser/"+$("#dbId").val(),dbUser.DbUserListHandler);
    }
    cn.GetData("/static/page-js/accountManager/analogData/ipdata.json",dbUser.DbUserIpHandler);
})
