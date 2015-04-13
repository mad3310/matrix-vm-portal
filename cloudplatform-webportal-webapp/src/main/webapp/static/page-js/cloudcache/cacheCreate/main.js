define(function(require){
	var $ = require("jquery");
	var Common=require('../../common');var cn=new Common();
	var Handler=require('./dataHandler');var hdler=new Handler();
	cn.divselect();
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
	// function chgeBuyNmu(){
	// 	var _taiNum=$('.tai-num');
	// 	var val=_taiNum.val();
	// 	val=parseInt(val);
	// 	if(val<=1){
	// 		val=1;
	// 		_downT.addClass('bk-number-disabled');
	// 	}else if(val>=99){
	// 		val=99;
	// 		_upT.addClass('bk-number-disabled');
	// 	}else{
	// 		//合法范围
	// 		_upT.removeClass('bk-number-disabled');
	// 		_downT.removeClass('bk-number-disabled');
	// 	}
	// 	_taiNum.val(val);
	// }
});