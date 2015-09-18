/**
 * Created by jiangfei on 2015/8/19.
 */
define(['services/app.service'],function (serviceModule) {
  serviceModule.factory('Utility', ['$timeout',
    function ($timeout) {
      var service = {};
      service.getRzSliderHack=function(scope){
         return function(){
           $timeout(function() {
             scope.$broadcast('reCalcViewDimensions');
           });
         };
      };

      return service;
    }]);
});