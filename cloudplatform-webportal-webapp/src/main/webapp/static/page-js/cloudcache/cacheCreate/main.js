define(function(require) {
	var $ = require("jquery");
	require("bootstrapValidator")($);
	var Common = require('../../common');
	var cn = new Common();
	var options = {// 拖动条初始化参数 全局变量
		'stepSize' : 1,// 步长
		'lev1' : 206,// 拖动条第一块长度==css width
		'lev2' : 309,// 拖动条第二块长度==css width
		'lev3' : 412,// 拖动条第三块长度==css width
		'min':1,
		'grade1' : 15,// 三段设置
		'grade2' : 20,
		'grade3' : 30,
		'unit' : 'GB'
	};
	cn.divselect();
	// drag bar
	cn.dragBarInite(options);
	cn.barClickDrag(options);
	cn.barDrag(options);
	$('.self-dragBar').closest('.bk-form-row-li').next().text('最小值为'+options.min+options.unit+'；步长为'+options.stepSize+options.unit);
	/* 防止误操作退出创建页面 */
	cn.AddBeforeunloadListener();
	if (document.getElementById("monthPurchaseBotton").form == null) { // 兼容IE
																		// form提交
		$("#monthPurchaseBotton").click(function() {
			$("#monthPurchaseForm").submit();
		})
	}
	/* 按钮组件封装 --begin */
	$(".bk-button-primary").click(
			function() {
				if (!$(this).hasClass("disabled")) {
					$(this).parent().find(".bk-button-primary").removeClass(
							"bk-button-current");
					$(this).addClass("bk-button-current");
					if ($(this).parent().find(".hide").length > 0) {
						var val = $(this).val();
						$(this).parent().find(".hide").val(val);
						// if (val == 1) {// 非持久化
						// 	options = {
						// 		'stepSize' : 1,
						// 		'lev1' : 206,
						// 		'lev2' : 309,
						// 		'lev3' : 412,
						// 		'grade1' : 75,
						// 		'grade2' : 150,
						// 		'grade3' : 300,
						// 		'unit' : 'MB'
						// 	};
						// 	cn.dragBarUpdate(options);
						// 	cn.barClickDrag(options);
						// 	cn.barDrag(options);
						// } else if (val == 0) {// 持久化
						// 	options = {
						// 		'stepSize' : 100,
						// 		'lev1' : 206,
						// 		'lev2' : 309,
						// 		'lev3' : 412,
						// 		'grade1' : 2500,
						// 		'grade2' : 5000,
						// 		'grade3' : 10000,
						// 		'unit' : 'MB'
						// 	};
						// 	cn.dragBarUpdate(options);
						// 	cn.barClickDrag(options);
						// 	cn.barDrag(options);
						// }
					}
				}
			});

	$("#monthPurchaseForm").bootstrapValidator({
		message : 'This value is not valid',
		feedbackIcons : {
			valid : 'glyphicon glyphicon-ok',
			invalid : 'glyphicon glyphicon-remove',
			validating : 'glyphicon glyphicon-refresh'
		},
		fields : {
			bucketName : {
				validMessage : '请按提示输入',
				validators : {
					notEmpty : {
						message : '实例名称不能为空!'
					},
					stringLength : {
						max : 16,
						min : 4,
						message : '实例名称长度4-16位!'
					},
					regexp : {
						regexp : /^((?!^monitor$)([a-zA-Z_]+[a-zA-Z_0-9]*))$/,
						message : "请输入字母数字或'_',实例名称不能以数字开头且数据库名称不能命名为monitor."
					},
					remote : {
						message : '缓存实例已存在!',
						url : '/cache/validate'
					}
				}
			}
		}
	}).on('success.form.bv', function(e) {
		e.preventDefault();
		var bucketName = $("[name = 'bucketName']").val();
		var hclusterId = $("[name = 'hclusterId']").val();
		var ramQuotaMB = $("[name='ramQuotaMB']").val()*1024;
		var bucketType = $("[name = 'bucketType']").val();
		var formData = {
			"bucketName" : bucketName,
			"hclusterId" : hclusterId,
			"ramQuotaMB" : ramQuotaMB,
			"bucketType" : bucketType
		};
		CreateCache(formData);
	});
	/* 表单验证 --end */
	var _up = $('.mem-num-up');
	var _down = $('.mem-num-down');
	var _upT = $('.tai-num-up');
	var _downT = $('.tai-num-down');
	_up.click(function(event) {
		var _memSize = $('.memSize');
		var val = _memSize.val();
		var temp = parseInt(val) + options.stepSize;
		if (temp >= options.grade3) {
			_up.addClass('bk-number-disabled');
		} else {
			_up.removeClass('bk-number-disabled');
			if (temp > options.stepSize) {
				_down.removeClass('bk-number-disabled');
			} else {
				_down.addClass('bk-number-disabled');
			}
		}
		_memSize.val(temp);
		cn.inputChge(options);
	});
	_down.click(function(event) {
		var _memSize = $('.memSize');
		var val = _memSize.val();
		var temp = parseInt(val) - options.stepSize;
		if (temp <= options.stepSize) {
			_down.addClass('bk-number-disabled');
		} else {
			_down.removeClass('bk-number-disabled');
			if (temp < options.grade3) {
				_up.removeClass('bk-number-disabled');
			} else {
				_up.addClass('bk-number-disabled');
			}
		}
		_memSize.val(temp);
		cn.inputChge(options);
	});

	// _upT.click(function(event) {
	// 	var _taiNum = $('.tai-num');
	// 	var val = _taiNum.val();
	// 	val = parseInt(val);
	// 	val = val + 1;
	// 	if (val <= 1) {
	// 		val = 1;
	// 		$(this).next().addClass('bk-number-disabled');
	// 	} else if (val >= 99) {
	// 		$(this).addClass('bk-number-disabled');
	// 		val = 99;
	// 	} else {
	// 		// 合法范围
	// 		$(this).removeClass('bk-number-disabled').next().removeClass(
	// 				'bk-number-disabled');
	// 	}
	// 	_taiNum.val(val);
	// });
	// _downT.click(function(event) {
	// 	var _taiNum = $('.tai-num');
	// 	var val = _taiNum.val();
	// 	val = parseInt(val);
	// 	val = val - 1;
	// 	if (val <= 1) {
	// 		val = 1;
	// 		$(this).addClass('bk-number-disabled')
	// 	} else if (val >= 99) {
	// 		val = 99;
	// 		$(this).prev().addClass('bk-number-disabled')
	// 	} else {
	// 		// 合法范围
	// 		$(this).removeClass('bk-number-disabled').prev().removeClass(
	// 				'bk-number-disabled');
	// 	}
	// 	_taiNum.val(val);
	// });
	$('.memSize').change(function(event) {
		cn.inputChge(options);
	});
	$('.tai-num').change(function(event) {
		var _taiNum = $('.tai-num');
		var val = _taiNum.val();
		cn.chgeBuyNmu();
	});

	/* 加载数据 */
	var dataHandler = require('./dataHandler');
	var createCacheHandler = new dataHandler();
	GetHcluster();
	function GetHcluster() {
		var url = "/hcluster/cbase";
		cn.GetData(url, createCacheHandler.GetHclusterHandler);
	}
	/* 创建cache */
	function CreateCache(data) {
		cn.RemoveBeforeunloadListener();
		console.log(data)
		var url = "/cache";
		cn.PostData(url, data, function() {
			location.href = "/list/cache";
		});
	}
});