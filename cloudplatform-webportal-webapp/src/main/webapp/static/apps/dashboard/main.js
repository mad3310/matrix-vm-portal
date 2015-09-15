require.config({
  paths: {
    //vendor
    'jquery': '/static/javascripts/jquery-1.11.3',
    'bootstrap': '/static/javascripts/bootstrap',
    'common': '/static/javascripts/common',
    'angular': '/static/javascripts/angular',
    'angular-animate': '/static/javascripts/angular-animate',
    'angular-route': '/static/javascripts/angular-route',
    'ui-select': '/static/javascripts/select',
    'ui-bootstrap': '/static/javascripts/ui-bootstrap-tpls-0.13.3',
    'ng-toaster': '/static/javascripts/toaster',
    'ng-rzslider': '/static/javascripts/rzslider',
    //js文件
    'app': '/static/apps/dashboard/app',
    'app.router': '/static/apps/dashboard/app.route'
  },
  shim: {
    'angular': {
      exports: 'angular'
    },
    'angular-animate': {
      deps: ['angular'],
      exports: 'angular-animate'
    },
    'angular-route': {
      deps: ['angular'],
      exports: 'angular-route'
    },
    'ui-select': {
      deps: ['angular'],
      exports: 'ui-select'
    },
    'ui-bootstrap': {
      deps: ['angular'],
      exports: 'ui-bootstrap'
    },
    'ng-toaster': {
      deps: ['angular', 'angular-animate'],
      exports: 'ng-toaster'
    },
    'ng-rzslider': {
      deps: ['angular'],
      exports: 'ng-rzslider'
    },
    'bootstrap': {
      deps: ['jquery'],
      exports: 'bootstrap'
    }
  }
});

require(['jquery', 'angular', 'common', 'bootstrap', 'app', 'app.router'],
  function (jquery, angular) {
    angular.bootstrap(document, ['myApp']);
  }
);