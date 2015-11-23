define(function(require){
	var $=require('jquery');
	var Handler=require('./dataHandler');var hdler=new Handler();
	var Common=require('../../common.js');var cn=new Common();
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
	$('.memSize').change(function(event) {
		cn.inputChge(options);
	});
});