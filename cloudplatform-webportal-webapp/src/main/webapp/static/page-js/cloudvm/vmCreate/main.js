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
    /*按钮组件封装 --begin*/
	$(document).on('click', '.bk-buttontab .bk-button-primary' , function(e){
		e.preventDefault();
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
	});
	/*按钮组件封装 --end*/
    
	$('.disk-operate-panel .bk-disk-add').on('click',function(e){
		dataHandler.addDataDisk();
	});
	$(document).on('click', '.bk-disk .bk-disk-delete' , function(e){
			e.preventDefault();
            $(this).closest('.bk-form-row-li').remove();
	});
	$(document).on('blur', '.bk-disk .bk-disk-input' , function(e){
		var inputEl=$(e.currentTarget);
        var inputValue=inputEl.val();
        
        if(!cn.validateInputNum(inputValue)){
        	inputEl.val('1');
        }
	});
	/*$('.bk-buttontab-password').find('button').on('click',function(e){
    	var buttonValue = $(e.currentTarget).attr('value');
    	if(buttonValue==='1'){
    		$('#vmpw1').closest('.bk-form-row').css('display','block');
    		$('#vmpw2').closest('.bk-form-row').css('display','block');
    	}
    	else{
    		$('#vmpw1').closest('.bk-form-row').css('display','none');
    		$('#vmpw2').closest('.bk-form-row').css('display','none');
    	}
    });*/

    /*表单验证 --begin*/
    $("#monthPurchaseForm").bootstrapValidator({
        message: 'This value is not valid',
        feedbackIcons: {
            valid: null,
            invalid: null,
            validating: null
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
            vmImageOSName: {
                validators: {
                    notEmpty: {
                        message: '请选择镜像操作系统!'
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
                        regexp: /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z]{6,32}$/,
                        message: "由字母、数字组成,要求6-32位，必须要包含数字，大小写字母"
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
        var imageInvalidTipRef=$('.image-invalid-tip');
        imageInvalidTipRef.css('display','none');
        if($('input[name=vmImageOSName]').val()==''){
        	imageInvalidTipRef.css('display','inline');
        	imageInvalidTipRef.text('请选择镜像操作系统！');
        	return;
        }
        if($('input[name=vmImageVersionName]').val()==''){
        	imageInvalidTipRef.css('display','inline');
        	imageInvalidTipRef.text('请选择镜像版本！');
        	return;
        }
        var regionName = $("[name = regionName]").val();
        var url = '/ecs/region/'+regionName+'/vm-create';
        var pwd= $("#vmpw1").val();//$('input[name=isCreatePassword]')==='1'?$("#vmpw1").val():'';
    	var data =  {
						        name: $("#vmName").val(),
						        imageId: $('#vmImageId').val(),
						        flavorId: $('#flavorId').val(),
						        adminPass:pwd,
						        publish: !!parseInt($('input[name=isCreatePublicIP]').val()),
						        volumeSizes: JSON.stringify(dataHandler.getDataDiskInfos())
						    }
		cn.PostData(url, data, function (data) {
			if(data.result===1){
	               location.href = "/list/vm";	
			}
			else{
				$('#submitResult').text(data.msgs[0]||'创建云主机失败！');
			}
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
    var imageOSLastName='';
    $(".bk-buttontab-regioncitys input").change(function (){
    	var regionCityName= $(this).val();
    	dataHandler.getRegion(regionCityName);
    });
    $("input[name='regionName']").change(function (){
    	var regionName= $(this).val();
    	dataHandler.setBuyRegionName(regionName);
    	$(".bk-buttontab-flavorCPUs input").val('');
    	initFlavorCPUs(regionName);
    	initImageOSs(regionName);
    });
    $(".bk-buttontab-flavorCPUs input").change(function (){
    	var flavorCPU= $(this).val();
    	$(".bk-buttontab-flavorRams input").val('');
    	dataHandler.getFlavorRam(flavorCPU);
    });
    $(".bk-buttontab-flavorRams input").change(function (){
    	var flavorCPU= $(".bk-buttontab-flavorCPUs input").val();
    	var flavorRam= $(this).val();
    	$(".bk-buttontab-flavorDisks input").val('');
    	dataHandler.getFlavorDisk(flavorCPU,flavorRam);
    });
    $(".bk-buttontab-flavorDisks input").change(function (){
    	var flavorCPU= $(".bk-buttontab-flavorCPUs input").val();
    	var flavorRam= $(".bk-buttontab-flavorRams input").val();
    	var flavorDisk= $(this).val();
    	var buyFlavorHtmlArray=[];
    	buyFlavorHtmlArray.push(flavorCPU + '核');
    	buyFlavorHtmlArray.push(flavorRam + 'MB');
    	buyFlavorHtmlArray.push(flavorDisk + 'G');
    	$('#buy-flavor').html(buyFlavorHtmlArray.join(' '));
    	dataHandler.setFlavorId(flavorCPU,flavorRam,flavorDisk);
    });
    $("input[name='vmImageOSName']").change(function (){
    	var imageOSName= $(this).val();
    	if(imageOSLastName===imageOSName) return;
    	if(imageOSName===''){
    		$('.image-os-selector').addClass('divselect-unselected');
    		$('.image-version-selector').addClass('divselect-disabled');
    		$(this).parent().find("span").html('选择操作系统');
    	}
    	else{
        	$('.image-os-selector').removeClass('divselect-unselected');
    		$('.image-version-selector').removeClass('divselect-disabled');	
    		dataHandler.getImageVersions(imageOSName);
    	}
		imageOSLastName=imageOSName;
		$('input[name=vmImageVersionName]').val('');
		$('input[name=vmImageVersionName]').trigger('change');
    });
    $("input[name='vmImageVersionName']").change(function (){
    	var imageVersionName= $(this).val();
    	if(!imageVersionName){
    		$('.image-version-selector').addClass('divselect-unselected');
    		$(this).parent().find("span").html('选择版本');
    		$("#buy-image").html('--');
    	}
    	else{
    		$('.image-version-selector').removeClass('divselect-unselected');
        	$("#buy-image").html(imageOSLastName+' ' +imageVersionName);
        	$('.image-invalid-tip').css('display','none');
    	}
    	dataHandler.setImageId(imageOSLastName,imageVersionName);
    });
    function initRegionCityNames(){
    	var url='/ecs/regions/group';
    	cn.GetData(url,dataHandler.getRegionCityname);
    }
    function initFlavorCPUs(region){
    	var url='/osf/region/'+region+'/group';
    	cn.GetData(url,dataHandler.getFlavorCPUs);
    }
    function initImageOSs(region){
    	var url='/osi/region/'+region+'/group';
    	cn.GetData(url,dataHandler.getImageOSs);
    }
     initRegionCityNames();
});
