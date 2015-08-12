define(function(require) {
	var $ = require("jquery");
	//require("bootstrapValidator")($);
	var Common = require('../../common');
	var cn = new Common();
	var options = {// 拖动条初始化参数 全局变量
		'stepSize' : 100,// 步长
		'lev1' : 206,// 拖动条第一块长度==css width
		'lev2' : 309,// 拖动条第二块长度==css width
		'lev3' : 412,// 拖动条第三块长度==css width
		'min':100,
		'grade1' : 2500,// 三段设置，倍数关系2
		'grade2' : 5000,
		'grade3' : 10000,
		'unit' : 'MB'
	};
	cn.divselect();
	// drag bar
	cn.dragBarInite(options);
	cn.barClickDrag(options);
	cn.barDrag(options);
	
	var _up = $('.mem-num-up');
	var _down = $('.mem-num-down');
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
	$('.memSize').change(function(event) {
		cn.inputChge(options);
	});

	/* 加载数据 */
	var dataHandler = require('./dataHandler');
//	var createCacheHandler = new dataHandler();
//	GetHcluster();
//	function GetHcluster() {
//		var url = "/hcluster";
//		cn.GetData(url, createCacheHandler.GetHclusterHandler);
//	}
	/* 创建cache */
//	function CreateCache(data) {
//		console.log(data)
//		var url = "/cache";
//		cn.PostData(url, data, function() {
//			location.href = "/list/cache";
//		});
//	}
});