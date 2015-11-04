/**
 * Created by jiangfei on 2015/8/19.
 */
define(['./common.service'],function (serviceModule) {
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

      service.delaySliderModel = function () {
        var delayQueue = [],
          timeoutPromise = null;
        return function (value, onTimeout) {
          if (delayQueue.length == 0) {
            timeoutPromise = $timeout(function () {
              onTimeout(delayQueue[delayQueue.length - 1]);
              delayQueue.splice(0, delayQueue.length);
            }, 1500);
          }
          delayQueue.push(value);
        };

      };

      return service;
    }]);
});