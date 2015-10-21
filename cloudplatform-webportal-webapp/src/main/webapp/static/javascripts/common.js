/**
 * Created by jiangfei on 2015/8/13.
 */
define(['jquery'],function($){
  /*设置页面的最低高度*/
  var viewHeight = $(window).height() - 70;
  $('.content').css('min-height', viewHeight);

  /*配置页面的左侧菜单*/
  var sideMenuData = [
      {url: '/profile/#/dashboard', title: '概览', icon: 'iconfont icon-blockicon',isSubmenuFisrt:true},
      {url: '/cvm/#/vm', title: '云主机', icon: 'iconfont icon-yunzhuji',isSubmenuFisrt:true},
      {url: '/cvm/#/vm-disk', title: '云硬盘', icon:  'iconfont icon-cloudstorageicon'},
      {url: '/cvm/#/vm-vpc', title: '私有网络', icon:  'iconfont icon-neticon'},
      {url: '/cvm/#/vm-floatIP',title:'公网IP',icon:'iconfont icon-ipicon'},
      {url: '/cvm/#/vm-router',title:'路由器',icon:'iconfont icon-routeicon'},
      {url: '/cvm/#/vm-snapshot',title:'快照',icon:'iconfont icon-snapshoticon'},
      {url: '/rds', title: '关系型数据库', icon:  'iconfont icon-rds',isSubmenuFisrt:true}
    ],
    sideMenuItemEl = null,
    sideMenuItemEls = [],
    sideMenuEl = $('.side-menu'),
    path=window.location.pathname,
    hash=window.location.hash,
    currentUrl = path + hash;

  var isMenuActive=function(sideMenuItem){
    var result=false;
    if(!hash){
      result=sideMenuItem.url.indexOf(path)>-1 && sideMenuItem.isSubmenuFisrt;
    }
    else {
      result = sideMenuData[i].url === currentUrl;
    }
    return result;
  };
  for (var i = 0, leng = sideMenuData.length; i < leng; i++) {
    sideMenuItemEl = '<li class="menu-item' + (isMenuActive(sideMenuData[i]) ? ' active' : '') + '">' +
      '<a href="' + sideMenuData[i].url + '">' +
      '<i class="' + sideMenuData[i].icon + '"></i>' +
      '<span>' + sideMenuData[i].title + '</span>' +
      '</a>' +
      '</li>';
    sideMenuItemEls.push(sideMenuItemEl);
  }
  sideMenuEl.html(sideMenuItemEls.join(''));
  sideMenuEl.on('click', '.menu-item a', function (e) {
    sideMenuEl.children('.menu-item').removeClass('active');
    $(e.target).closest('li').addClass('active');
  });
  // 用户头像修改
  var userid=$('#userId').val();
  var usinfourl="/user/"+userid;
  $.ajax({
    url:usinfourl,
    type: 'get',
    success:function(data){
      if(data.result==0){//error
      }else{
        var _data=data.data;
        if(_data.userAvatar){
          $('.account-icon').attr('src',_data.userAvatar);
        }
      }
    }
  });
  var browserInfo = function(){			
		var bagent = navigator.userAgent.toLowerCase();
		var regStr_ie = /msie [\d.]+;/gi ;
		var regStr_ff = /firefox\/[\d.]+/gi
		var regStr_chrome = /chrome\/[\d.]+/gi ;
		var regStr_saf = /safari\/[\d.]+/gi ;
		//IE
		if(bagent.indexOf("msie") > 0){
			return bagent.match(regStr_ie) ;
		}
		//firefox
		if(bagent.indexOf("firefox") > 0){
			return bagent.match(regStr_ff) ;
		}
		//Chrome
		if(bagent.indexOf("chrome") > 0){
			return bagent.match(regStr_chrome) ;
		}
		//Safari
		if(bagent.indexOf("safari") > 0 && bagent.indexOf("chrome") < 0){
			return bagent.match(regStr_saf) ;
		}
	}
	var browserVersion = function(){
		  var _browser = browserInfo().toString().toLowerCase();
		  var verinfo = (_browser+"").replace(/[^0-9.]/ig,"");    	 
		  if(_browser.indexOf("msie") >=0 && (verinfo < 9.0)){
			  window.location.replace="/browserError";
		  }else if(_browser.indexOf("firefox") >=0 && verinfo < 5.0){
			  window.location.replace="/browserError";
		  }else if(_browser.indexOf("chrome") >=0 && verinfo < 7.0){
			  window.location.replace="/browserError";
		  }else if(_browser.indexOf("safari") >=0 && verinfo < 4.0){
			  window.location.replace="/browserError";
		  }
	}
	browserVersion(); //浏览器检测初始化
});
