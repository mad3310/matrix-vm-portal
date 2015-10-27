/**
 * Created by jiangfei on 2015/8/13.
 */
define(['jquery'],function($){
  //浏览器版本监测
  var client = function() {
    var engine = {
      ie: 0,
      gecko: 0,
      webkit: 0,
      khtml: 0,
      opera: 0,

      // 具体的版本
      ver: null
  };

    var browser = {
      ie: 0,
      firefox: 0,
      safari: 0,
      konq: 0,
      opera: 0,
      chrome: 0,

      // 具体的版本
      ver: null
    };

    var system = {
      win: false,
      mac: false,
      xll: false,

      // 移动设备
      iphone: false,
      ipod: false,
      ipad: false,
      ios: false,
      android: false,
      nokiaN: false,
      winMobile: false,

      // 游戏系统
      wii: false,
      ps: false
   }

    var ua = window.navigator.userAgent,

  p = window.navigator.platform;
  system.win = p.indexOf('Win') == 0;
  system.mac = p.indexOf('Mac') == 0;
  system.xll = (p.indexOf('Linux') == 0 || p.indexOf('Xll') == 0);

  system.iphone = ua.indexOf('iPhone') > -1;
  system.ipod = ua.indexOf('iPod') > -1;
  system.ipad = ua.indexOf('iPad') > -1;

  // ios
  if (system.mac && ua.indexOf('Mobile') > -1) {
    if (/CPU (?:iPhone )?OS (\d+_\d+)/.test(ua)) {
      system.ios = parseFloat(RegExp.$1.replace('_', '.'));
    } else {
        system.ios = 2;
    }
  }
    // android
    if (/Android (\d+\.\d+)/.test(ua)) {
      system.android = parseFloat(RegExp.$1);
    }
    // nokia
    system.nokiaN = ua.indexOf('NokiaN') > -1;

    // windows mobile
    if (system.win == 'CE') {
      system.winMobile = system.win;
    } else if (system.win == 'Ph') {
      if (/Windows Phone OS (\d+.\d+)/.test(ua)) {
          system.win = 'Phone';
          system.winMobile = parseFloat(RegExp['$1']);
      }
    }

    // game system
    system.wii = ua.indexOf('Wii') > -1;
    system.ps = /playstation/i.test(ua);
  if(window.opera){
      engine.ver = browser.ver = window.opera.version();
      engine.opera = browser.opera = parseFloat(engine.ver);
  }else if(/AppleWebKit\/(\S+)/i.test(ua)) {
      engine.ver = browser.ver = RegExp['$1'];
      engine.webkit = parseFloat(engine.ver);

      // 确定是chrome 还是 safari
      if(/Chrome\/(\S+)/i.test(ua)) {
          browser.chrome = parseFloat(engine.ver);
      }else if(/Version\/(\S+)/i.test(ua)) {
        browser.safari = parseFloat(engine.ver);
      }else{
          // 近似的确认版本号，早期版本的safari版本中userAgent没有Version
          var safariVersion = 1;
          if(engine.webkit<100){
            safariVersion=1;
          }else if(engine.webkit<312){
            safariVersion=1.2;
          }else if(engine.webkit<412){                     
            safariVersion=1.3;
          }else{                     
            safariVersion=2;
          }
          browser.safari=browser.ver=safariVersion;
      }
    }else if(/KHTML\/(\S+)/i.test(ua)||/Konqueror\/([^;]+)/i.test(ua)){
      engine.ver = browser.ver = RegExp['$1'];
      engine.khtml = browser.konq = parseFloat(engine.ver);
    }else if(/rv:([^\)]+)\) Gecko\/\d{8}/i.test(ua)){
      engine.ver = RegExp['$1'];
      engine.gecko = parseFloat(engine.ver);

      // 确定是不是Firefox浏览器
      if(/Firefox\/(\S+)/i.test(ua)) {
        browser.ver = RegExp['$1'];
        browser.firefox = parseFloat(browser.ver);
      }
    }else if(/MSIE\s([^;]+)/i.test(ua)) {
      engine.ver = browser.ver = RegExp['$1'];
      engine.ie = browser.ie = parseFloat(engine.ver);
    }else if (/trident.*rv:([^)]+)/i.test(ua)) {
      engine.ver = browser.ver = RegExp['$1'];
      engine.ie = browser.ie = parseFloat(engine.ver);
  }
    if (system.win){
      if (/Win(?:dows )?([^do]{2})\s?(\d+\.\d+)?/.test(ua)){
          if (RegExp['$1'] == 'NT'){
            switch(RegExp['$2']) {
                case '5.0': system.win = '2000'; break;
                case '5.1': system.win = 'XP'; break;
                case '6.0': system.win = 'Vista'; break;
                case '6.1': system.win = '7'; break;
                case '6.2': system.win = '8'; break;
                default: system.win = 'NT'; break;
            }
          }else if(RegExp['$1'] == '9x'){
            // 检测windows ME
            system.win = 'ME';
          }else {
            // 检测windows 95、windows 98
            system.win = RegExp['$1'];
          }
      }
    }
    return {
      engine: engine,
      browser: browser,
      system: system
    }
}();
// // common:监测浏览器版本
function browerversion(){
  var _browser = client.browser;
  if(_browser.ie&&_browser.ver<9.0){
    window.location.replace="/browserError";
  }else if(_browser.firefox&&_browser.ver< 5.0){
    window.location.replace="/browserError";
  }else if(_browser.chrome&&_browser.ver< 7.0){
    window.location.replace="/browserError";
  }else if(_browser.safari&&_browser.ver<4.0){
    window.location.replace="/browserError";
  }
}
  browerversion(); //浏览器检测初始化
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
      {url: '/cvm/#/vm-image',title:'镜像',icon:'iconfont icon-shearicon'},
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
});
