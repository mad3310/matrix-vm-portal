define(function(require){
	var $ = require("jquery");
	require("bootstrapValidator")($);
	var Common=require('../../common');var cn=new Common();
	
	cn.divselect();
	/*禁用退格键退回网页*/
    window.onload=cn.DisableBackspaceEnter();
    if(document.getElementById("monthPurchaseBotton").form == null){    //兼容IE form提交
        $("#monthPurchaseBotton").click(function(){
            $("#monthPurchaseForm").submit();
        })
    }
    $("#monthPurchaseForm").bootstrapValidator({
        message: 'This value is not valid',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            cacheName: {
                validMessage: '请按提示输入',
                validators: {
                    notEmpty: {
                        message: '实例名称不能为空!'
                    },
                    stringLength: {
                        max: 16,
                        message: '实例名称过长!'
                    }, regexp: {
                        regexp: /^((?!^monitor$)([a-zA-Z_]+[a-zA-Z_0-9]*))$/,
                        message: "请输入字母数字或'_',实例名称不能以数字开头且数据库名称不能命名为monitor."
                    }/*,
                    remote: {
                        message: '数据库名已存在!',
                        url: '/db/validate'
                    }*/
                }
            }
        }
    }).on('success.form.bv', function(e) {
        e.preventDefault();
        var cacheName = $("[name = 'cacheName']").val();
        var cacheId = $("[name = 'cacheId']").val();
        var engineType = $("[name = 'engineType']").val();
        var linkType = $("[name = 'linkType']").val();
        var isCreateAdmin = $("[name = 'isCreateAdmin']").val();
        var formData = {"cacheName":cacheName,"linkType":linkType,"engineType":engineType,"cacheId":cacheId,"isCreateAdmin":isCreateAdmin};
        CreateCache(formData);
    });
    /*表单验证 --end*/
	var _up=$('.mem-num-up');var _down=$('.mem-num-down');var _upT=$('.tai-num-up');var _downT=$('.tai-num-down');
	var options={
		'stepSize':5,
		'lev1':206,
		'lev2':309,
		'lev3':412
	};
	_up.click(function(event) {
		var _memSize=$('.memSize');
		var val=_memSize.val();
		var temp=parseInt(val)+options.stepSize;
		if(temp>=1000){
			_up.addClass('bk-number-disabled');
		}else{
			_up.removeClass('bk-number-disabled');
			if(temp>options.stepSize){
				_down.removeClass('bk-number-disabled');
			}else{
				_down.addClass('bk-number-disabled');
			}
		}
		_memSize.val(temp);
		cn.inputChge(options);
	});
	_down.click(function(event) {
		var _memSize=$('.memSize');
		var val=_memSize.val();
		var temp=parseInt(val)-options.stepSize;
		if(temp<=options.stepSize){
			_down.addClass('bk-number-disabled');
		}else{
			_down.removeClass('bk-number-disabled');
			if(temp<1000){
				_up.removeClass('bk-number-disabled');
			}else{
				_up.addClass('bk-number-disabled');
			}
		}
		_memSize.val(temp);
		cn.inputChge(options);
	});
	cn.barClickDrag(options);
	cn.barDrag(options);
	cn.btnPrimaryClick();
	_upT.click(function(event) {
		var _taiNum=$('.tai-num');
		var val=_taiNum.val();
		val=parseInt(val);
		val=val+1;
		if(val<=1){
			val=1;
			$(this).next().addClass('bk-number-disabled');
		}else if(val>=99){
			$(this).addClass('bk-number-disabled');
			val=99;
		}else{
			//合法范围
			$(this).removeClass('bk-number-disabled')
					.next().removeClass('bk-number-disabled');	
		}
		_taiNum.val(val);
	});
	_downT.click(function(event) {
		var _taiNum=$('.tai-num');
		var val=_taiNum.val();
		val=parseInt(val);
		val=val-1;
		if(val<=1){
			val=1;
			$(this).addClass('bk-number-disabled')
		}else if(val>=99){
			val=99;
			$(this).prev().addClass('bk-number-disabled')
		}else{
			//合法范围
			$(this).removeClass('bk-number-disabled')
					.prev().removeClass('bk-number-disabled');
		}
		_taiNum.val(val);
	});
	$('.memSize').change(function(event) {
		cn.inputChge(options);
	});
	$('.tai-num').change(function(event) {
		var _taiNum=$('.tai-num');
		var val=_taiNum.val();
		cn.chgeBuyNmu();
	});
	
    /*加载数据*/
    var dataHandler = require('./dataHandler');
    var createDbHandler = new dataHandler();
    GetHcluster();
    function GetHcluster(){
        var url="/hcluster";
        cn.GetData(url,createDbHandler.GetHclusterHandler);
    }

	function CreateCache (data) {
		var url="/cache";
        cn.PostData(url,data, function () {
            location.href = "/list/cache";
        });
	}
});