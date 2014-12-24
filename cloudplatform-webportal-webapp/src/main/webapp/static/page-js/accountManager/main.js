/**
 * Created by yaokuo on 2014/12/12.
 */
define(function(require){
    var common = require('../common');
    var cn = new common();

    var $ = require("jquery");
    require('bootstrapValidator')($);

    /*初始化侧边栏菜单*/
    var index = [1,0];
    cn.Sidebar(index);//index为菜单中的排序(1-12)
    
    /*加载数据*/
    var dataHandler = require('./dataHandler');
    var dbUser = new dataHandler();

    cn.GetData("/dbUser/"+$("#dbId").val(),dbUser.DbUserListHandler);
    cn.GetData("/static/page-js/accountManager/analogData/ipdata.json",dbUser.DbUserIpHandler);

    $('#db_user_apply_form').bootstrapValidator({
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
    $('#db_user_edit_form').bootstrapValidator({
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
            }
        }
    }).on('error.field.bv', function(e, data) {
        $('#edit-dbUser-botton').addClass("disabled");
    }).on('success.field.bv', function(e, data) {
        $('#edit-dbUser-botton').removeClass("disabled");
    });

})