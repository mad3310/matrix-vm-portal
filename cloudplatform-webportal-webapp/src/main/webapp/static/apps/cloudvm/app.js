/**
 * Created by jiangfei on 2015/7/21.
 */
define(['angular', 'angular-animate', 'angular-route', 'ui-select', 'ui-bootstrap', 'ng-toaster', 'controllers/controllers', 'services/services', 'directives/directives', 'filters/filters'], function (angular) {
  var app = angular.module('myApp', [
    //angular
    'ngAnimate',
    'ngRoute',
    'ui.select',
    'ui.bootstrap',
    'toaster',
    //app
    'app.controller',
    'app.service',
    'app.directive',
    'app.filter'
  ]);

  app.run(['$route', '$rootScope', '$location', 'routes', function ($route, $rootScope, $location, routes) {
    $rootScope.$on('$routeChangeSuccess', function (event) {
      var route = null;
      for (var i = 0, leng = routes.length; i < leng; i++) {
        if ($location.path().indexOf(routes[i].url) > -1) {
          routes[i].isOpen = true;
          route = routes[i];
          break;
        }
      }
    });
  }]);

  return app;
});
