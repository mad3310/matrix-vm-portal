/**
 * Created by yaokuo on 2014/12/12.
 */
define(function(require){
	require("bootstrapValidator")($);
	
    var common = require('../../common'),
    	DataHandler = require('./dataHandler'),
    	cn = new common(),
    	dataHandler = new DataHandler(require);
    
    var initRegionCityNames = function (){
    	var url='/osv/regions/group';
    	cn.GetData(url,dataHandler.getRegionCityname);
    };    

    /*按钮组件封装 --begin*/
	$(document).on('click', '.bk-buttontab .bk-button-primary' , function(e){
        if(!$(this).hasClass("disabled")){
            $(this).parent().find(".bk-button-primary").removeClass("bk-button-current");
            $(this).addClass("bk-button-current");
            if($(this).parent().find(".hide").length > 0 ){
                var val = $(this).val();
                var hiddenInput=$(this).parent().find(".hide");
                if(val!==hiddenInput.val()){
                    hiddenInput.val(val);
                    hiddenInput.trigger('change');
                }	
            }
        }
        return false;//阻止表单的自动提交
	});
	/*按钮组件封装 --end*/    

    if(document.getElementById("monthPurchaseBotton").form == null){    //兼容IE form提交
        $("#monthPurchaseBotton").click(function(){
            $("#monthPurchaseForm").submit();
        })
    }

    /*加载数据*/
    $(".bk-buttontab-regioncitys input").change(function (){
    	var regionCityName= $(this).val();
    	dataHandler.getRegion(regionCityName);
    });
    $("input[name='regionName']").change(function (){
    	var regionName= $(this).val();
    	dataHandler.setBuyRegionName(regionName);
    });    
    
    //初始化创建页select功能
    cn.divSelect();
    //禁用退格键退回网页
    window.onload=cn.DisableBackspaceEnter();

    /*表单验证 --begin*/
	$("#monthPurchaseForm").bootstrapValidator({
        message: 'This value is not valid',
        feedbackIcons: {
            valid: null,
            invalid: null,
            validating: null
        },
        fields: {
        	routerName: {
                validMessage: '请按提示输入',
                validators: {
                    notEmpty: {
                        message: '路由名称不能为空!'
                    }
                }
            }
        }
    }).on('success.form.bv', function(e) {
        e.preventDefault();

        var regionName = $("[name = regionName]").val();
        var url = '/osn/router/create';
    	var data =  {
    			region:$('input[name=regionName]').val(),
    	        name:$('#routerName').val(),
    	        enablePublicNetworkGateway:$('input[name=enablePublicNetworkGateway]').val(),
    	        }
		cn.PostData(url, data, function (data) {
			if(data.result===1){
	               location.href = "/list/vm/router";	
			}
			else{
				$('#submitResult').text(data.msgs[0]||'创建路由失败！');
			}
         });
    }); 
    /*表单验证 --end*/
    initRegionCityNames();
});
