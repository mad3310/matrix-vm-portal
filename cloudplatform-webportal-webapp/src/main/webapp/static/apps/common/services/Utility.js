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
      service.setOperationBtns=function($scope,objList,productInfo,operationArry,Config){
          var type=productInfo.type;
          var state=productInfo.state;
          var operaArraytemp=productInfo.operations;
          var operationArraycopy=[];
          for(var i in objList){
            operationArry[i]=[];
            if(objList[i].checked){
              for(var j in operaArraytemp){
                operationArry[i][j]=Config.statusOperations[type][objList[i][state]][operaArraytemp[j]];
                operationArraycopy[j]=1;
              }
            }else{
              operationArry[i]=[1,1,1,1,1,1,1,1]
            }   
          }
          for(var i in operationArry){//多记录状态叠加
            for(var j in operationArry[i]){
              operationArraycopy[j]=operationArraycopy[j]*operationArry[i][j];
            }
          }
          return operationArraycopy
      };

      service.isInt=function (input) {
        return Number(input) % 1 === 0;
      };
      return service;
    }]);
});