require.config({
  paths: {
    //vendor
    'jquery':'../../javascripts/jquery-1.11.3',
    'bootstrap':'../../javascripts/bootstrap',
    'common': '../../javascripts/common',
    'browserCheck': '../../staticPage/js/browserCheck',
    'angular':'../../javascripts/angular',
    'angular-animate': '../../javascripts/angular-animate',
    'angular-route': '../../javascripts/angular-route',
    'ui-bootstrap': '../../javascripts/ui-bootstrap-tpls-0.13.3',
    'ng-toaster': '../../javascripts/toaster',
    'ng-rzslider': '../../javascripts/rzslider',
    //js文件
    'app': './app',
    'app.router': './app.route'
  },
  shim: {
    'browserCheck':{
      deps: ['jquery'],
      exports:'browserCheck'
    },
    'angular': {
      deps: ['common'],
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