/**
 * Created by yaokuo on 2014/12/12.
 */
define(function(require){
    var common = require('../common');
    var cn = new common();
    var $ = require("jquery");
    require("bootstrapValidator")($);

    $(".glyphicon-pencil").click(function(){
        cn.EditBoxInit(this);
    })
    /*禁用退格键退回网页*/
    window.onload=cn.DisableBackspace();

    /*页面按钮初始化 --start*/
    $(".toCreateAccount").click(function () {           //切换到创建账户
        $("#accountList").addClass("mc-hide");
        $("#modifyAccountTab").addClass("mc-hide");
        $("#newAccountTab").removeClass("mc-hide");
        asyncDbUserIpData();
    })
    $(".toAccountList").click(function () {             //切换到创建账户
        $("#newAccountTab").addClass("mc-hide");
        $("#modifyAccountTab").addClass("mc-hide");
        $("#accountList").removeClass("mc-hide");
    })

    $("#refresh").click(function() {
        asyncData();
    });
    $("#manager-ip-list").click(function () {
        var $iframe = $("body",parent.document).find("iframe");
        $iframe.attr("src","/detail/security/"+$("#dbId").val());
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
            maxConcurrency: {
                validMessage: '请按提示输入',
                validators: {
                    notEmpty: {
                        message: '最大并发量不能为空!'
                    },integer: {
                        message: '请输入数字'
                    },between:{
                        min:1,
                        max:5000,
                        message:'最大并发量1-5000'
                    }
                }
            },
            newPwd1: {
                validators: {
                    notEmpty: {
                        message:'密码不能为空'
                    },
                    different: {
                        field: 'username',
                        message: '密码不能与账户名相同'
                    }
                }
            },
            newPwd2: {
                validators: {
                    notEmpty: {
                        message:'密码不能为空'
                    },
                    identical: {
                        field: 'newPwd1',
                        message: '两次输入密码不同'
                    }
                }
            }
        }
    }).on('error.field.bv', function(e) {
        $("#submitCreateUserForm").addClass("disabled");
    }).on('success.field.bv', function(e) {
        $("#submitCreateUserForm").removeClass("disabled");
    });

    $('#db_user_modify_form').bootstrapValidator({
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            modifyFormNewPwd1: {
                validators: {
                    notEmpty: {
                        message:'密码不能为空'
                    }
                }
            },
            modifyFormNewPwd2: {
                validators: {
                    notEmpty: {
                        message:'密码不能为空'
                    },
                    identical: {
                        field: 'modifyFormNewPwd1',
                        message: '两次输入密码不同'
                    }
                }
            }
        }
    }).on('error.field.bv', function(e, data) {
        $("#submitModifyUserForm").addClass("disabled");
    }).on('success.field.bv', function(e, data) {
        $("#submitModifyUserForm").removeClass("disabled");
    });
    $('#reset-password-form').bootstrapValidator({
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            'reset-password': {
                validators: {
                    notEmpty: {
                        message:'密码不能为空'
                    }
                }
            },
            'reset-password-repeat': {
                validators: {
                    notEmpty: {
                        message:'密码不能为空'
                    },
                    identical: {
                        field: 'reset-password',
                        message: '两次输入密码不同'
                    }
                }
            }
        }
    }).on('error.field.bv', function(e, data) {
        $("#resetPasswordBoxSubmit").addClass("disabled");
    }).on('success.field.bv', function(e, data) {
        $("#resetPasswordBoxSubmit").removeClass("disabled");
    });

    /*加载数据*/
    var dataHandler = require('./dataHandler');
    var dbUser = new dataHandler();

    asyncData();
    asyncDbUserIpData();

    function asyncData() {
        var dbUserListUrl = "/dbUser/"+$("#dbId").val();
    	cn.GetData(dbUserListUrl,dbUser.DbUserListHandler);
    }
    function asyncDbUserIpData(){
        cn.GetData("/dbIp/"+$("#dbId").val()+"/null",dbUser.DbUserIpHandler);   //创建用户加载IP
    }

    /*创建dbuser*/
    $("#submitCreateUserForm").click(function () {
        if(!$("#submitCreateUserForm").hasClass("disabled")){
            $("#submitCreateUserForm").addClass("disabled");
            var createUserData = dbUser.GetCreateDbUserData();
            var url = "/dbUser";
            cn.PostData(url,createUserData, function () {
                /*刷新本身ifame*/
                var $iframe = $("body",parent.document).find("iframe");
                $iframe.attr("src",$iframe.attr("src"));
            });
        }
    })

    /*修改dbuser权限*/
    $("#submitModifyUserForm").click(function () {
        if(!$("#submitModifyUserForm").hasClass("disabled")){
            $("#submitModifyUserForm").addClass("disabled");
            var modifyUserData = dbUser.GetModifyDbUserData();
            var url = "/dbUser/authority/"+$("#modifyFormDbUsername").html();

            cn.PostData(url,modifyUserData,function(){
                /*刷新本身ifame*/
                var $iframe = $("body",parent.document).find("iframe");
                $iframe.attr("src",$iframe.attr("src"));
            });
        }
    })
})
