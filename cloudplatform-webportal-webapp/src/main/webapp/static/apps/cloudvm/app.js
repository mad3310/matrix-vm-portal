/**
 * Created by jiangfei on 2015/7/21.
 */
define(['angular', 'angular-animate', 'angular-route', 'ui-select', 'ui-bootstrap', 'ng-toaster','ng-rzslider', 'controllers/controllers', 'services/services', 'directives/directives', 'filters/filters'], function (angular) {
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

  app.run(['$route', '$rootScope', '$location', 'routes', function ($route, $rootScope, $location, routes) {

  }]);

  return app;
});
