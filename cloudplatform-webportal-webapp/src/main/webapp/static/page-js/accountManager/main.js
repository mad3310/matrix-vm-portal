/**
 * Created by yaokuo on 2014/12/12.
 */
define(function(require){
    var common = require('../common');
    var cn = new common();
    var $ = require("jquery");
    require("bootstrapValidator")($);
    var dataHandler = require('./dataHandler');
    var dbUser = new dataHandler();

    $(".glyphicon-pencil").click(function(){
        cn.EditBoxInit(this);
    })
    /*禁用退格键退回网页*/
    window.onload=cn.DisableBackspaceEnter();

    /*页面按钮初始化 --start*/
    $(".toCreateAccount").click(function () {           //切换到创建账户
        $("#accountList").addClass("mc-hide");
        $("#modifyAccountTab").addClass("mc-hide");
        $("#newAccountTab").removeClass("mc-hide");
        asyncDbUserIpData();
    })
    $(".toAccountList").click(function () {             //切换到账户列表
        $("#newAccountTab").addClass("mc-hide");
        $("#modifyAccountTab").addClass("mc-hide");
        $("#accountList").removeClass("mc-hide");
    })

    $("#refresh").click(function() {
        asyncData();
    });
    $("#manager-ip-list").click(function () {
        cn.RefreshIfame("/detail/security/"+$("#dbId").val());
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
                    },different: {
                        field: 'username',
                        message: '密码不能与账户名相同'
                    },stringLength: {
                    	min: 6,
                        max: 32,
                        message: '密码长度6-32位'
                    }
                    ,regexp: {
                        regexp: /^[a-zA-Z0-9_-]+$/,
                        message: "由字母、数字、中划线或下划线组成，长度6~32位"
                    }
                }
            },
            newPwd2: {
                validators: {
                    notEmpty: {
                        message:'密码不能为空'
                    },identical: {
                        field: 'newPwd1',
                        message: '两次输入密码不同'
                    },stringLength: {
                    	min: 6,
                        max: 32,
                        message: '密码长度6-32位'
                    },regexp: {
                        regexp: /^[a-zA-Z0-9_-]+$/,
                        message: "由字母、数字、中划线或下划线组成，长度6~32位"
                    }
                }
            }
        }
    }).on('success.form.bv', function(e) {
        e.preventDefault();

        var createUserData = dbUser.GetCreateDbUserData();
        var url = "/dbUser";
        cn.PostData(url, createUserData, function () {
            /*刷新本身ifame*/
            var $iframe = $("body", parent.document).find("iframe");
            $iframe.attr("src", $iframe.attr("src"));
        });
    })

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
                    },stringLength: {
                    	min: 6,
                        max: 32,
                        message: '密码长度6-32位'
                    },regexp: {
                        regexp: /^[a-zA-Z0-9_-]+$/,
                        message: "由字母、数字、中划线或下划线组成，长度6~32位"
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
                    },stringLength: {
                    	min: 6,
                        max: 32,
                        message: '密码长度6-32位'
                    },regexp: {
                        regexp: /^[a-zA-Z0-9_-]+$/,
                        message: "由字母、数字、中划线或下划线组成，长度6~32位"
                    }
                }
            }
        }
    }).on('success.form.bv', function(e) {
        e.preventDefault();

        var modifyUserData = dbUser.GetModifyDbUserData();
        var url = "/dbUser/authority/"+$("#modifyFormDbUsername").html();

        cn.PostData(url,modifyUserData,function(){
            /*刷新本身ifame*/
            var $iframe = $("body",parent.document).find("iframe");
            $iframe.attr("src",$iframe.attr("src"));
        });
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
                    },stringLength: {
                    	min: 6,
                        max: 32,
                        message: '密码长度6-32位'
                    },regexp: {
                        regexp: /^[a-zA-Z0-9_-]+$/,
                        message: "由字母、数字、中划线或下划线组成，长度6~32位"
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
                    },stringLength: {
                    	min: 6,
                        max: 32,
                        message: '密码长度6-32位'
                    },regexp: {
                        regexp: /^[a-zA-Z0-9_-]+$/,
                        message: "由字母、数字、中划线或下划线组成，长度6~32位"
                    }
                }
            }
        }
    }).on('success.form.bv', function(e) {
        e.preventDefault();

        var data = {
            "username":$("#reset-password-username").val(),
            "password":$("[name = 'reset-password']").val(),
            "dbId":$("#dbId").val()
        }
        console.log(data);
        var url = "/dbUser/security";
        cn.PostData(url,data, function () {
            /*刷新本身ifame*/
            var $iframe = $("body",parent.document).find("iframe");
            $iframe.attr("src",$iframe.attr("src"));
        });
    });

    /*加载数据*/
    asyncData();

    function asyncData() {
        var dbUserListUrl = "/dbUser/"+$("#dbId").val();
    	cn.GetData(dbUserListUrl,dbUser.DbUserListHandler);
    }
    function asyncDbUserIpData(){
        cn.GetData("/dbIp/"+$("#dbId").val()+"/null",dbUser.DbUserIpHandler);   //创建用户加载IP
    }
})
