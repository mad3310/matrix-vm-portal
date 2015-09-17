/**
 * Created by jiangfei on 2015/8/19.
 */
define(['services/app.service'], function (serviceModule) {
  serviceModule.factory('HttpService', ['$http',
    function ($http) {
      var service = {};
      service.doGet = function (url, data, option) {
        return $http.get(url, angular.extend({params: data}, option));
      };
      service.doPost = function (url, data, option) {
        return $http.post(url, data, option);
      };
      return service;
    }]);
});