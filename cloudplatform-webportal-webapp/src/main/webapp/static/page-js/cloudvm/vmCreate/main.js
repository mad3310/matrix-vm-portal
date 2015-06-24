/**
 * Created by yaokuo on 2014/12/12.
 */
define(function(require){
    var common = require('../../common');
    var cn = new common();
    var $ = jQuery  = require("jquery");
    require("jquery.multiple")(jQuery); 
    require("bootstrapValidator")($);
    
    cn.divSelect();//初始化创建页select功能

    /*禁用退格键退回网页*/
    window.onload=cn.DisableBackspaceEnter();

    if(document.getElementById("monthPurchaseBotton").form == null){    //兼容IE form提交
        $("#monthPurchaseBotton").click(function(){
            $("#monthPurchaseForm").submit();
        })
    }

    /*表单验证 --begin*/
    $("#monthPurchaseForm").bootstrapValidator({
        message: 'This value is not valid',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            vmName: {
                validMessage: '请按提示输入',
                validators: {
                    notEmpty: {
                        message: '虚拟机名称不能为空!'
                    },
                    stringLength: {
                        max: 16,
                        message: '虚拟机名过长!'
                    }, regexp: {
                        regexp: /^([a-zA-Z_]+[a-zA-Z_0-9]*)$/,
                        message: "请输入字母数字或'_',虚拟机名不能以数字开头."
                    }
                }
            },
            vmpw1:{
                validators: {
                    notEmpty: {
                        message:'密码不能为空'
                    },different: {
                        field: 'vmName',
                        message: '密码不能与账户名相同'
                    },stringLength: {
                        min:6,
                        max: 32,
                        message: '密码长度为6-32之间!'
                    },regexp: {
                        regexp: /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z_-]{6,32}$/,
                        message: "由字母、数字、中划线或下划线组成,要求6-32位，必须要包含数字，大小写字母"
                    }
                }
            },
            vmpw2:{
                validators: {
                    notEmpty: {
                        message:'密码不能为空'
                    },identical: {
                        field: 'vmpw1',
                        message: '两次输入密码不同'
                    }
                }
            }
        }
    }).on('success.form.bv', function(e) {
        e.preventDefault();
        var regionName = $("[name = regionName]").val();
        var url = '/ecs/region/'+regionName+'/vm-create';
    	var data =  {
						        name: $("#vmName").val(),
						        imageId: $("[name = vmImageName]").val(),
						        flavorId: $("[name = vmType]").val(),
						       // networkIds: $("#networkSelecter").val(),
						        adminPass:$("#vmpw1").val()
						    }
		cn.PostData(url, data, function (data) {
               location.href = "/list/vm";
         });
    }).on('keyup', '[name="vmpw1"]', function () {
        if($("[name = 'vmpw2']").val() != ''){
            $('#monthPurchaseForm').bootstrapValidator('revalidateField', 'vmpw2');
        }
    });
    /*表单验证 --end*/

    /*加载数据*/
    var DataHandler = require('./dataHandler');
    var dataHandler = new DataHandler(require);
    function getRegion(){
    	var url = "/ecs/regions";
    	cn.GetData(url,dataHandler.getRegion);
    }
    $("[name='regionName']").change(function (){
    	var regionName= $(this).val();
    	getVmType(regionName);
    	getImages(regionName);
    	getNetwork(regionName);
    })
    function getVmType(region){
    	var url = "/osf/region/"+ region;
    	cn.GetData(url,dataHandler.getVmType);
    }
    function getImages(region){
    	var url="/osi/region/"+region;
    	cn.GetData(url,dataHandler.getImage);
    }
     function getNetwork(region){
    	var url="/osn/region/"+region;
    	cn.GetData(url,dataHandler.getNetwork);
    }
    
     getRegion();//获取可用区
});
