(function init() {
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
function topNavHover() {
	$('.item-product').unbind('hover').hover(function() {//top-nav 滑过效果
		var _navUl = $('.nav-uls');
		_navUl.slideDown('fast', function() {
			$('.item-product').children('div').removeClass('hide');
		});
	}, function() {
		$('.item-product').children('div').addClass('hide');
		var _navUl = $('.nav-uls');
		_navUl.hide();
	});
}

// index:carousels
function carousels() {
	var timeInterval = '';
	if (timeInterval) {
	} else {
		timeInterval = setInterval(function() {
			var _targetParent = $('.carousels');
			var _target = _targetParent.children(':first');
			_target.addClass('hide').find('.carousel-content .col-md-5').removeClass('fadeIn');
			_target.find('.carousel-content .col-md-7').removeClass('slideR');
			//carousel-control change
			var preIndex = _target.attr('self-carousel-id');
			$('.carousel-control img:eq(' + preIndex + ')').addClass('control-short');
			_targetParent.append(_target);
			var nxtIndex = _targetParent.children(':first').attr('self-carousel-id');
			$('.carousel-control img:eq(' + nxtIndex + ')').removeClass('control-short');
			_targetParent.children(':first').removeClass('hide').find('.carousel-content .col-md-5').addClass('fadeIn')
			_targetParent.children(':first').find('.carousel-content .col-md-7').addClass('slideR');
		}, 3000);
	}
	//轮播control
	$('.carousel-control').unbind('click').click(function(event) {
		var _eTarget = event.target || event.srcElemnet;
		//ff && ie
		var _img = '';
		if (_eTarget) {
			_img = $(_eTarget)
		} else {
			_img = $(_eTarget).closest('img');
		}
		var index = _img.attr('self-carousel-id');
		var _targetParent = $('.carousels');
		var _target = _targetParent.children('.top-carousel[self-carousel-id=' + index + ']');
		var _targetBehindIndex = $('.top-carousel').index(_target);
		var _targetBehind = _targetParent.children('.top-carousel:gt(' + _targetBehindIndex + ')');
		_target.addClass('hide').find('.carousel-content .col-md-5').addClass('fadeIn');
		_target.find('.carousel-content .col-md-7').addClass('slideR');
		$('.carousel-control img:eq(' + index + ')').removeClass('control-short').siblings().addClass('control-short');
		_target.removeClass('hide').siblings().addClass('hide');
		_targetParent.prepend(_targetBehind);
		_targetParent.prepend(_target);
		if (timeInterval) {
			clearInterval(timeInterval);
		}
		timeInterval = setInterval(function() {
			var _targetF = _targetParent.children(':first');
			_targetF.addClass('hide').find('.carousel-content .col-md-5').removeClass('fadeIn');
			_targetF.find('.carousel-content .col-md-7').removeClass('slideR');
			//carousel-control change
			var preIndex2 = _targetF.attr('self-carousel-id');
			$('.carousel-control img:eq(' + preIndex2 + ')').addClass('control-short');
			_targetParent.append(_targetF);
			var nxtIndex = _targetParent.children(':first').attr('self-carousel-id');
			$('.carousel-control img:eq(' + nxtIndex + ')').removeClass('control-short');
			_targetParent.children(':first').removeClass('hide').find('.carousel-content .col-md-5').addClass('fadeIn')
			_targetParent.children(':first').find('.carousel-content .col-md-7').addClass('slideR');
		}, 3000);
	});
}

// products:scroll-nav
function scrollNav() {
	$(window).scroll(function() {
		var vtop = $(document).scrollTop();
		var _target = $('.tab-fixed')
		if (vtop >= 645) {
			$('.tab-layout').addClass('hide');
			_target.removeClass('hide');
		} else {
			$('.tab-layout').removeClass('hide')
			_target.addClass('hide')
		}
	});
}

function customerToolInit() {
	(function tooltip(){
		$(".customer-logos div").click(function() {
			$(".customer-logos div").removeClass('logo-focus');
			$(this).addClass("logo-focus");
		});
	})();
	(function tabinit(){
		$(".customer-tab div").click(function() {
			$(".customer-tab div").removeClass('active');
			$(this).addClass("active");
		});
	})();
}
