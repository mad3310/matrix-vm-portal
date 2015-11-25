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

      service.delayQueueModel = function () {
        var delayQueue = [],
          timeoutPromise = null;
        return function (value, onTimeout) {
          if (delayQueue.length == 0) {
            timeoutPromise = $timeout(function () {
              onTimeout(delayQueue[delayQueue.length - 1]);
              delayQueue.splice(0, delayQueue.length);
            }, 1000);
          }
          delayQueue.push(value);
        };

      };
      service.setOperationBtns=function($scope,objList,productInfo,operationArry,Config){
          var type=productInfo.type;
          var state=productInfo.state;
          var otheraffect=productInfo.other;
          var operaArraytemp=productInfo.operations;
          var operationArraycopy=[];
          var othertemp=1,statetemp='';//暂存数据
          for(var i in objList){
            operationArry[i]=[];
            if(objList[i].checked){
              var objtemp=objList[i];
              statetemp=objtemp[state]?objtemp[state]:'default';
              for(var j in operaArraytemp){
                if(otheraffect.length>0){//其他影响因素
                  for(var k in otheraffect){
                    var otheraffecttemp=objtemp[otheraffect[k]]
                    if(otheraffecttemp instanceof Array){
                      if(otheraffecttemp.length>0){
                        operationArry[i][j]=Config.statusOperations[type][statetemp][operaArraytemp[j]]*Config.statusOperations[type][otheraffect[k]][operaArraytemp[j]];
                      }else{
                        operationArry[i][j]=Config.statusOperations[type][statetemp][operaArraytemp[j]]*Config.statusOperations[type][otheraffect[k]+'null'][operaArraytemp[j]];
                      }
                    }else{
                      if(otheraffecttemp){
                        operationArry[i][j]=Config.statusOperations[type][statetemp][operaArraytemp[j]]*Config.statusOperations[type][otheraffect[k]][operaArraytemp[j]];
                      }else{
                        operationArry[i][j]=Config.statusOperations[type][statetemp][operaArraytemp[j]]*Config.statusOperations[type][otheraffect[k]+'null'][operaArraytemp[j]];
                      }
                    }    
                  }
                }else{//无其他因素影响
                  operationArry[i][j]=Config.statusOperations[type][statetemp][operaArraytemp[j]];
                }
                // operationArry[i][j]=Config.statusOperations[type][objList[i][state]][operaArraytemp[j]];
                operationArraycopy[j]=1;
              }
            }else{
              operationArry[i]=[1,1,1,1,1,1,1,1,1,1,1,1]
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
