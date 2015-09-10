/**
 * Created by jiangfei on 2015/7/22.
 */
define(['app'],function (app) {
  'use strict';

  var routeConfigurator = function ($routeProvider, routes) {
      var setRoute = function (routeConfig) {
        if (!routeConfig.abstract) {
          $routeProvider.when(routeConfig.url, routeConfig.config);
        }
      };
      angular.forEach(routes, function (value, key) {
        setRoute(value);
        if (value.subRoutes) {
          angular.forEach(value.subRoutes, function (value, key) {
            setRoute(value);
          });
        }
      });
      $routeProvider.otherwise({redirectTo: '/profile'});
    },
    getRoutes = function () {
      return [
        {//begin Dashboard routes
          url: '/profile',
          title: '概览',
          isSpaUrl:true,
          icon: 'fa fa-tachometer',
          config: {
            template: 'Dashboard'
          }
        },//end Dashboard routes
        {//begin rds routes
          url: '/cloudvm',
          title: '计算与网络',
          icon: 'fa fa-desktop',
          abstract: true,
          subRoutes: [
            {
              url: '/cloudvm/vm',
              title: '主机',
              isSpaUrl:true,
              config: {
                templateUrl: 'static/apps/cloudvm/views/virtual-machine.html'
              }
            }, {
              url: '/cloudvm/disk',
              title: '云硬盘',
              isSpaUrl:true,
              config: {
                template: '云硬盘<a href="/cvm#/cloudvm/vm">url</a>'
              }
            }
          ]
        }
      ];
    };

  app.constant('routes', getRoutes());

  app.config(['$routeProvider', 'routes', routeConfigurator]);

});
