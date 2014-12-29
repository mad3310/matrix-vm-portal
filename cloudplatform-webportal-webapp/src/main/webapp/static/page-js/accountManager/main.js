/**
 * Created by yaokuo on 2014/12/12.
 */
define(function(require){
    var common = require('../common');
    var cn = new common();
    var $ = require("jquery");
    require("bootstrapValidator")($);

    /*页面按钮初始化 --start*/
    $(".toCreateAccount").click(function () {           //切换到创建账户
        $("#accountList").addClass("mc-hide");
        $("#ipListTab").addClass("mc-hide");
        $("#modifyAccountTab").addClass("mc-hide");
        $("#newAccountTab").removeClass("mc-hide");
        asyncDbUserIpData();
    })
    $(".toAccountList").click(function () {             //切换到创建账户
        $("#newAccountTab").addClass("mc-hide");
        $("#ipListTab").addClass("mc-hide");
        $("#modifyAccountTab").addClass("mc-hide");
        $("#accountList").removeClass("mc-hide");
    })
    $(".toIpList").click(function () {                   //切换到IP列表
        $("#newAccountTab").addClass("mc-hide");
        $("#accountList").addClass("mc-hide");
        $("#modifyAccountTab").addClass("mc-hide");
        $("#ipListTab").removeClass("mc-hide");
    })
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
        asyncData();
    });
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
            },
            newPwd1: {
                validators: {
                    notEmpty: {
                        message:'密码不能为空'
                    },
                    identical: {
                        field: 'newPwd2',
                        message: '两次输入密码不同'
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

    /*加载数据*/
    var dataHandler = require('./dataHandler');
    var dbUser = new dataHandler();

    asyncData();
    asyncDbUserIpData();
    asyncModifyIpData();

    function asyncData() {
        var dbUserListUrl = "/dbUser/"+$("#dbId").val();
    	cn.GetData(dbUserListUrl,dbUser.DbUserListHandler);
    }
    function asyncDbUserIpData(){
        cn.GetData("/dbIp/"+$("#dbId").val()+"/null",dbUser.DbUserIpHandler);   //创建用户加载IP
    }
    function asyncModifyIpData(){
        window.setTimeout(function () {
            cn.GetData("/dbIp/"+$("#dbId").val(),dbUser.DbUserIpListHandler);   //获取IP列表信息
        },1000);
    }

    /*创建dbuser*/
    $("#submitCreateUserForm").click(function () {
        if(!$("#submitCreateUserForm").hasClass("disabled")){
            $("#submitCreateUserForm").addClass("disabled");
            var createUserData = dbUser.GetCreateDbUserData();
            var url = "/dbUser";
            cn.PostData(url,createUserData);
            /*后期改为刷新iframe --begin*/
            $("#newAccountTab").addClass("mc-hide");
            $("#accountList").addClass("mc-hide");
            $("#modifyAccountTab").addClass("mc-hide");
            $("#ipListTab").removeClass("mc-hide");
            asyncData();
            /*后期改为刷新iframe --end*/
        }
    })

    /*修改dbuser权限*/
    $("#submitModifyUserForm").click(function () {
        if(!$("#submitModifyUserForm").hasClass("disabled")){
            $("#submitModifyUserForm").addClass("disabled");
            var modifyUserData = dbUser.GetModifyDbUserData();
            var url = "/dbUser/authority/"+$("#modifyFormDbUsername").html();

            cn.PostData(url,modifyUserData);

            /*后期改为刷新iframe --begin*/
            $("#newAccountTab").addClass("mc-hide");
            $("#modifyAccountTab").addClass("mc-hide");
            $("#ipListTab").addClass("mc-hide");
            $("#accountList").removeClass("mc-hide");
            asyncData();
            /*后期改为刷新iframe --end*/
        }
    })

   /* $('#showDbuserIpPrivilege').modal({
        backdrop:false,
        show:true
    });*/
})
