/**
 * Created by jiangfei on 2015/8/13.
 */
define(['jquery'],function($){
  /*设置页面的最低高度*/
  var viewHeight = $(window).height() - 70;
  $('.content').css('min-height', viewHeight);

  /*配置页面的左侧菜单*/
  var sideMenuData = [
      {url: '/profile', title: '概览', icon: 'fa fa-tachometer'},
      {url: '/cvm/#/vm', title: '云主机', icon: 'fa fa-tachometer'},
      {url: '/cvm/#/image', title: '镜像', icon: 'fa fa-tachometer'},
      {url: '/cvm/#/disk', title: '云硬盘', icon: 'fa fa-tachometer'},
      {url: '/rds', title: '关系型数据库', icon: 'fa fa-tachometer'}
    ],
    sideMenuItemEl = null,
    sideMenuItemEls = [],
    sideMenuEl = $('.side-menu'),
    currentUrl = window.location.pathname + window.location.hash;
  for (var i = 0, leng = sideMenuData.length; i < leng; i++) {
    sideMenuItemEl = '<li class="menu-item' + (sideMenuData[i].url === currentUrl ? ' active' : '') + '">' +
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

});
