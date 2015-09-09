/**
 * Created by jiangfei on 2015/7/21.
 */
define(['angular', 'angular-animate', 'angular-route', 'ui-select', 'ui-bootstrap', 'ng-toaster','ng-rap', 'controllers/controllers', 'services/services', 'directives/directives', 'filters/filters'], function (angular) {
  var app = angular.module('myApp', [
    //angular
    'ngAnimate',
    'ngRoute',
    'ui.select',
    'ui.bootstrap',
    'toaster',
    'ngRap',
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
      if (route) {
        var subRoutes = route.subRoutes;
        for (var i = 0, leng = subRoutes.length; i < leng; i++) {
          if ($location.path().indexOf(subRoutes[i].url) > -1) {
            subRoutes[i].isOpen = true;
            break;
          }
        }
      }
    });
  }]);

  return app;
});
