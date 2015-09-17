/**
 * Created by jiangfei on 2015/7/21.
 */
define(['angular', 'angular-animate', 'angular-route', 'ui-select', 'ui-bootstrap', 'ng-toaster', 'ng-rzslider', 'controllers/controllers', 'services/services', 'directives/directives', 'filters/filters'], function (angular) {
  var app = angular.module('myApp', [
    //angular
    'ngAnimate',
    'ngRoute',
    'ui.select',
    'ui.bootstrap',
    'toaster',
    'rzModule',
    //app
    'app.controller',
    'app.service',
    'app.directive',
    'app.filter'
  ]);
  app.config(['$httpProvider', function ($httpProvider) {
    $httpProvider.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded; charset=UTF-8';
    $httpProvider.defaults.headers.post['Accept'] ='application/json, text/javascript, */*; q=0.01';
    $httpProvider.defaults.headers.post['Accept-Language'] ='zh-CN,zh;q=0.8,en;q=0.6';
    $httpProvider.defaults.headers.post['X-Requested-With'] ='XMLHttpRequest';
    $httpProvider.defaults.transformRequest = function(data){
      if (data === undefined) {
        return data;
      }
      return $.param(data);
    };
  }]);
  app.run(['$route', '$rootScope', '$http', '$location', 'routes', function ($route, $rootScope, $http, $location, routes) {

  }]);

  return app;
});
