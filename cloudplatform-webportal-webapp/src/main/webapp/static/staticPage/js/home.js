(function init(){
	toTop();
	customerToolInit();
})()
function toTop() {
	var scrollEle = clientEle = document.documentElement, toTopBtn = document.getElementById("toTop"), compatMode = document.compatMode, isChrome = window.navigator.userAgent.indexOf("Chrome") === -1 ? false : true;
	//不同渲染模式以及Chrome的预处理
	if (compatMode === "BackCompat" || isChrome) {
		scrollEle = document.body;
	}
	if (compatMode === "BackCompat") {
		clientEle = document.body;
	}
	//返回顶部按钮的点击响应（注册函数），时间间隔和高度缩减率可以调节
	toTopBtn.onclick = function() {
		var moveInterval = setInterval(moveScroll, 10);
		function moveScroll() {
			setScrollTop(getScrollTop() / 1.2);
			if (getScrollTop() === 0) {
				clearInterval(moveInterval);
			}
		}
	}
	//滚动时判断是否显示返回顶部按钮（注册函数）
	window.onscroll = function() {
		var display = toTopBtn.style.display;
		if (getScrollTop() > getClientHeight()) {
			if (display === "none" || display === "") {
				toTopBtn.style.display = "block";
			}
		} else {
			if (display === "block" || display === "") {
				toTopBtn.style.display = "none";
			}
		}
	}
	//获取和设置scrollTop
	function getScrollTop() {
		return scrollEle.scrollTop;
	}
	function setScrollTop(value) {
		scrollEle.scrollTop = value;
	}
	//获取当前网页的展示高度（第一屏高度），此处Chrome正常
	function getClientHeight() {
		return clientEle.clientHeight;
	}
}; 
// common:top-nav-hover
function topNavHover(){
	var _navUl=$('.nav-uls');var time='';
	$('.item-product').unbind('hover').hover(function(){ //top-nav 滑过效果
		time=setTimeout(function(){
			_navUl.slideDown('400', function() {
				if(_navUl.is(':visible')){
					$('.item-product').children('div').removeClass('hide');
				}
			});
		},200);
	},function(){
		clearTimeout(time);
		$('.item-product').children('div').addClass('hide');
		_navUl.hide();
	});
}
//common:nav-more
function navMore(){
	$('.more').unbind('hover').hover(function() {
		$(this).css({
			color: '#088ac2',
			background: '#fff'
		});
		$(this).find('a').css('color', '#088ac2');
	}, function() {
		$(this).css({
			color: '#fff',
			background: 'transparent'
		});
		$(this).find('a').css('color', '#fff');
	});
}
// index:carousels
function carousels(){
	var timeInterval='';
	if(timeInterval){
	}else{
		timeInterval=setInterval(function(){
			var _targetParent=$('.carousels');
			var _target=_targetParent.children(':first');
			_target.addClass('hide').find('.carousel-content .col-md-5').removeClass('fadeIn');
			_target.find('.carousel-content .col-md-7').removeClass('slideR');
			//carousel-control change
			var preIndex=_target.attr('self-carousel-id');
			$('.carousel-control img:eq('+preIndex+')').addClass('control-short');
			_targetParent.append(_target);
			var nxtIndex=_targetParent.children(':first').attr('self-carousel-id');
			$('.carousel-control img:eq('+nxtIndex+')').removeClass('control-short');
			_targetParent.children(':first').removeClass('hide').find('.carousel-content .col-md-5').addClass('fadeIn')
			_targetParent.children(':first').find('.carousel-content .col-md-7').addClass('slideR');
		},3000);
	}
	//轮播control
	$('.carousel-control').unbind('click').click(function(event) {
		var _eTarget=event.target||event.srcElemnet;//ff && ie
		var _img='';
		if(_eTarget){
			_img=$(_eTarget)
		}else{
			_img=$(_eTarget).closest('img');
		}
		var index=_img.attr('self-carousel-id');
		var _targetParent=$('.carousels');
		var _target=_targetParent.children('.top-carousel[self-carousel-id='+index+']');
		var _targetBehindIndex=$('.top-carousel').index(_target);
		var _targetBehind=_targetParent.children('.top-carousel:gt('+_targetBehindIndex+')');
		_target.addClass('hide').find('.carousel-content .col-md-5').addClass('fadeIn');
		_target.find('.carousel-content .col-md-7').addClass('slideR');
		$('.carousel-control img:eq('+index+')').removeClass('control-short').siblings().addClass('control-short');
		_target.removeClass('hide').siblings().addClass('hide');
		_targetParent.prepend(_targetBehind);
		_targetParent.prepend(_target);
		if(timeInterval){
			clearInterval(timeInterval);
		}
		timeInterval=setInterval(function(){
			var _targetF=_targetParent.children(':first');
			_targetF.addClass('hide').find('.carousel-content .col-md-5').removeClass('fadeIn');
			_targetF.find('.carousel-content .col-md-7').removeClass('slideR');
			//carousel-control change
			var preIndex2=_targetF.attr('self-carousel-id');
			$('.carousel-control img:eq('+preIndex2+')').addClass('control-short');
			_targetParent.append(_targetF);
			var nxtIndex=_targetParent.children(':first').attr('self-carousel-id');
			$('.carousel-control img:eq('+nxtIndex+')').removeClass('control-short');
			_targetParent.children(':first').removeClass('hide').find('.carousel-content .col-md-5').addClass('fadeIn')
			_targetParent.children(':first').find('.carousel-content .col-md-7').addClass('slideR');
		},3000);
	});
}
// products:scroll-nav
function scrollNav(){
	$(window).scroll(function(){  
        var vtop=$(document).scrollTop();
        var _target=$('.tab-fixed')
        if(vtop>=645){
        	$('.tab-layout').addClass('hide');
        	var _tabClone=$('.tab-layout').children().clone();
        	if(_target.children().length>0){
        	}else{
        		_target.append(_tabClone)
        	}
        	_target.removeClass('hide');
        	if(vtop>=3055){
        		_target.children().children('div:eq(3)').addClass('active-item').siblings().removeClass('active-item');
        	}else if(vtop>=2077){
				_target.children().children('div:eq(2)').addClass('active-item').siblings().removeClass('active-item');
        	}else if(vtop>=1097){
        		_target.children().children('div:eq(1)').addClass('active-item').siblings().removeClass('active-item');
        	}else{
        		_target.children().children('div:eq(0)').addClass('active-item').siblings().removeClass('active-item');
        	}
        }else{
        	var _tabClone='';
			if(_target.children().length>0){
				_tabClone=_target.children().clone();
				$('.tab-layout').html(_tabClone);
			}
        	$('.tab-layout').removeClass('hide');
        	_target.addClass('hide');
        	_target.children().remove();
        }
	});
}
//products:tab-click
function tabClick(){
	$('.top-product-tab').unbind('click').click(function(event) {
		event.preventDefault();
		var _etarget=event.target||event.srcElement;
		var _target=$(_etarget).closest('.tab-item');
		_target.addClass('active-item').siblings().removeClass('active-item');
		var _tabClone=$(this).children().clone();
		if($('.tab-fixed').children().length>0){
		}else{
			$('.tab-fixed').append(_tabClone);
		}
		var _anchor=_target.children('a').attr('href');
		var _top=$(_anchor).offset();
		if(_top){
			// $('html, body').animate({
			// 	scrollTop:$(_anchor).offset().top
			// },500);
			$('html, body').scrollTop($(_anchor).offset().top)
			return false;
		}else{
			window.location.hash='#anchor-andvantage';
			// $('body').animate({
			// 	scrollTop:720
			// }, 500)
			$('html, body').scrollTop(720)
		}
	});
}
//home&product logged
function iflogged(){
	var _target=$('.nav-more');
	$.ajax({
		url:'/user',
		type:'get',
		cache:false,
		success:function(data){
			if(data.result==0){//未登录
				_target.removeClass('hide');
				$('.logged').addClass('hide');
			}else{
				var name=data.data.contacts;
				_target.addClass('hide');
				$('.logged').removeClass('hide').find('.logged-name').text(name);
			}
		}
	});
}
function customerToolInit() {
	var brands={
		'lantv':'中国蓝提供广电云服务，助力浙广向新媒体转型/;乐视云计算为中国蓝TV提供一体化新媒体服务，提高工作效率，节省带宽成本。',
		'huashutv':'致力于打造新媒体平台/;CDN解决方案，为华数提供流畅、稳定、快速的网络加速保障以及安全保障，使用户无论在何时何地都能观看流畅高清视频。乐视云以稳定充足的带宽保障，快速有效运维保障，提高了服务质量，节省了各类维护成本。',
		'a':'b'
	};
	(function tooltip(){
		$(".customer-logos div").click(function() {
			$(".customer-logos div").removeClass('logo-focus');
			$('.customer-hoverTip').addClass('logo-focus');
			$(this).addClass("logo-focus");
			var img=$(this).find('img').attr('src');
			$('.tipImg').find('img').attr('src',img);
			var brand=$(this).attr('self-customer-brand');
		});
	})();
	(function tabinit(){
		$(".customer-tab div").click(function() {
			$(".customer-tab div").removeClass('active');
			$(this).addClass("active");
			var customerType=$(this).attr('self-customer-tab');
			$('.customer-logos[self-customer-type='+customerType+']').removeClass('hide').siblings('.customer-logos').addClass('hide')
		});
	})();
}
