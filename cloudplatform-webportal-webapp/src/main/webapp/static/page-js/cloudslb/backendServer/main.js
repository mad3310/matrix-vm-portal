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
    var slbServerHandler = new dataHandler();
    
    $("#refresh").click(function(){
    	asyncData();
    });
    
    $("#restart").click(function(){
    	var url = "/slb/restart";
    	var restartData = {
                id:$("#slbId").val()
            }
        cn.PostData(url, restartData, function () {
        	window.top.location.href = "/list/slb";
        });
    });

    getConfig();
    function getConfig(){
        var url="/slbConfig?currentPage=1&&recordsPerPage=150&&slbId="+$("#slbId").val();
        cn.GetData(url,slbServerHandler.ConfigHandler);
    }

    asyncData();
    function asyncData() {
        var url = "/slbBackend?currentPage=1&&recordsPerPage=150&&slbId="+$("#slbId").val();
        cn.GetData(url,slbServerHandler.ServerHandler);
    }
    $("#inner-server-list-tab").click(function(){
   		 asyncInnerServerData();
    })
    $("#add-inner-server").click(function(){
    	 $('.nav-tabs a:last').tab('show');
    	  asyncInnerServerData();
    });
    function asyncInnerServerData() {
        var  url= "/gce?currentPage=1&&recordsPerPage=150";
        cn.GetData(url,slbServerHandler.InnerServerHandler);
    }

    $('#addBackendServer').bootstrapValidator({
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            backendServerName:{
                validMessage: '请按提示输入',
                validators: {
                    notEmpty: {
                        message: '服务器名不能为空!'
                    }, stringLength: {
                        max: 16,
                        message: '服务器名过长!'
                    }, regexp: {
                        regexp: /^[a-zA-Z_]+[a-zA-Z_0-9]*$/,
                        message: "请输入字母数字或'_',服务器名不能以数字开头."
                    }
                }
            },
            backendServerIp: {
                validMessage: '请按提示输入',
                validators: {
                    notEmpty: {
                        message: '服务器IP不能为空！'
                    }, regexp: {
                        regexp: /^((\d|\d\d|1\d\d|2[0-4]\d|25[0-5])(\.(\d|\d\d|1\d\d|2[0-4]\d|25[0-5])){3})$/,
                        message:"IP格式不正确"
                    }
                }
            },
            backendPort: {
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
            serverName :  $("[name = backendServerName]").val(),
            serverIp : $("[name = backendServerIp]").val(),
            port : $("[name = backendPort]").val(),
            type : "CUSTOM",
            configId : $("[name = frontPort]").val()
        }
        var url = "/slbBackend";
        cn.PostData(url, createConfigData, function () {
            /*刷新本身ifame*/
            cn.RefreshIfame();
        });
    })
});
