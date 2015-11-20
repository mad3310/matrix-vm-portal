/**
 * Created by jiangfei on 2015/8/19.
 */
define(['./common.service'], function (serviceModule) {
  serviceModule.factory('HttpService', ['$http','WidgetService','Config',
    function ($http,WidgetService,Config) {
      var service = {};
      service.doGet = function (url, data, option) {
        return $http.get(url, angular.extend({params: data}, option)).success(function(data, status, headers, config){
          if(data.result == 0){
            WidgetService.notifyError(data.msgs[0]||'获取数据失败');
          }else if(data.result == 2){
            $window.location.reload();
          }
          return;
        })
      };
      service.doPost = function (url, data, option) {
        return $http.post(url, data, option);
      };
      return service;
    }]);
});