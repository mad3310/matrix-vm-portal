/**
 * Created by jiangfei on 2015/8/19.
 */
define(['./common.service'], function (serviceModule) {
  serviceModule.factory('HttpService', ['$http','$q','$window','WidgetService',
    function ($http,$q,$window,WidgetService) {
      var service = {};
      service.doGet = function (url, data, option) {
        var deferred = $q.defer();
        $http.get(url, angular.extend({params: data}, option)).success(function (data, status, headers, config) {
          if (data.result === 1) {
            deferred.resolve(data);
          } else if (data.result === 2) {
            $window.location.reload();
          }
          else {
            deferred.reject(data);
            if(!option || !option.disableGetGlobalNotify){
              WidgetService.notifyError(data.msgs[0] || '获取数据失败');
            }
          }
        });
        return deferred.promise;
      };
      service.doPost = function (url, data, option) {
        return $http.post(url, data, option);
      };
      return service;
    }]);
});