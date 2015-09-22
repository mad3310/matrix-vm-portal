function rollup(){
	$('.title-rollup').unbind('click').click(function(){
		var _target=$('.ordertable');
		var _targetI=$('.icon-arrow01');
		var _targetTxt=$('.rollup-text');
		if(_target.hasClass('opacity')){
			_target.removeClass('opacity')
			_target.css({
				opacity: '0',
				transition: 'opacity .2s ease-in'
			});
			_targetI.css({
				transform:'rotate(0deg)',
				transition:'transform .2s ease-in'
			});
			_targetTxt.text('展开');
		}else{
			_target.css({
				opacity: '1',
				transition: 'opacity .2s ease-in'
			});
			_target.addClass('opacity');
			_targetI.css({
				transform:'rotate(180deg)',
				transition:'transform .2s ease-in'
			});
			_targetTxt.text('收起');
		}
	});
}