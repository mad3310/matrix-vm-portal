/**
 * Created by jiangfei on 2015/7/22.
 */
define(['app'],function (app) {
  'use strict';

  var routeConfigurator = function ($routeProvider, routes) {
      var setRoute = function (routeConfig) {
          $routeProvider.when(routeConfig.url, routeConfig.config);
      };
      angular.forEach(routes, function (value, key) {
        setRoute(value);
      });
      $routeProvider.otherwise({redirectTo: '/vm'});
    },
    getRoutes = function () {
      return [
        {
          url: '/vm',
          title: '云主机',
          config: {
            templateUrl: '/static/apps/cloudvm/views/virtual-machine.html'
          }
        },
        {
          url: '/vm-disk',
          title: '云硬盘',
          config: {
            templateUrl: '/static/apps/cloudvm/views/vm-disk.html'
          }
        },
        {
          url: '/vm-vpc',
          title: '私有网络',
          config: {
            templateUrl: '/static/apps/cloudvm/views/vm-network.html'
          }
        }];
    };

  app.constant('routes', getRoutes());

  app.config(['$routeProvider', 'routes', routeConfigurator]);

});
