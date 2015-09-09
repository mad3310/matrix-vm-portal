define(['app'], function (app) {
  app.config(['$httpProvider', 'ngRapProvider', function (httpProvider, ngRapProvider) {
    // replace nnn with your project id
    ngRapProvider.script = 'http://10.154.28.164:17070/rap.plugin.js?projectId=4';
    ngRapProvider.enable({
      mode: 3
    });
    httpProvider.interceptors.push('rapMockInterceptor');
  }]);
});


