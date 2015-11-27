/**
 * Created by jiangfei on 2015/8/13.
 */
define(['jquery','browserCheck'],function($){
// // common:监测浏览器版本
function browerversion(obj){
  var _browser=obj.getBrowserInfor();
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
var client=new ClientInfor();
  browerversion(client); //浏览器检测初始化
  /*设置页面的最低高度*/
  var viewHeight = $(window).height() - 70;
  $('.content').css('min-height', viewHeight);

  /*配置页面的左侧菜单*/
  var sideMenuData = [
      {url: '/profile/#/dashboard', title: '概览', icon: 'iconfont icon-clouddashboard',isSubmenuFisrt:true},
      {url: '/cvm/#/vm', title: '云主机', icon: 'iconfont icon-cloudhost',isSubmenuFisrt:true},
      {url: '/cvm/#/vm-disk', title: '云硬盘', icon:  'iconfont icon-clouddisk'},
      {url: '/cvm/#/vm-vpc', title: '私有网络', icon:  'iconfont icon-cloudnet'},
      {url: '/cvm/#/vm-floatIP',title:'公网IP',icon:'iconfont icon-cloudfloatip'},
      {url: '/cvm/#/vm-router',title:'路由器',icon:'iconfont icon-cloudroute'},
      {url: '/cvm/#/vm-snapshot',title:'快照',icon:'iconfont icon-cloudsnap'},
      {url: '/cvm/#/vm-image',title:'镜像',icon:'iconfont icon-cloudimage'},
      {url: '/cvm/#/vm-keypair',title:'密钥',icon:'iconfont icon-cloudkeypair'}/*,
      {url: '/rds', title: '关系型数据库', icon:  'iconfont icon-cloudrds',isSubmenuFisrt:true}*/
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
  return {};
});
