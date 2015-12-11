/**
 * Created by jiangfei on 2015/8/19.
 */
define(['./common.service'],function (serviceModule) {
  serviceModule.factory('Utility', ['$timeout','$document',
    function ($timeout,$document) {
      var service = {};
      service.getRzSliderHack=function(scope){
         return function(){
           $timeout(function() {
             scope.$broadcast('rzSliderForceRender');
           },50);
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
      var jsonTrans=function(obj,path){
        var patharry=[],_targetobj='',pathtemp='';
        var flag=false;
        if(path.indexOf('.')>0){
          patharry=path.split('.');
          var objtemp='';
          for(var i in patharry){
            objtemp=objtemp?objtemp:obj;
            objtemp=objtemp[patharry[i]];
            pathtemp=patharry[i];
          }
          _targetobj=objtemp;
        }else{
          pathtemp=path;
          _targetobj=obj[path];
        }
        if(_targetobj instanceof Array){
          if(_targetobj.length>0){
            flag=true;
          }
        }else{
          if(_targetobj){
            flag=true;
          }
        }
        return {
          "flag":flag,
          "pathvalue":pathtemp
        };
      }
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
                operationArry[i][j]=operationArry[i][j]?operationArry[i][j]:Config.statusOperations[type][statetemp][operaArraytemp[j]];
                if(otheraffect.length>0){//其他影响因素
                  for(var k in otheraffect){
                    var flag=jsonTrans(objtemp,otheraffect[k]).flag;
                    var pathvalue=jsonTrans(objtemp,otheraffect[k]).pathvalue;
                    if(flag){
                      operationArry[i][j]=operationArry[i][j]*Config.statusOperations[type][pathvalue][operaArraytemp[j]];
                    }else{
                      operationArry[i][j]=operationArry[i][j]*Config.statusOperations[type][pathvalue+'null'][operaArraytemp[j]];
                    }
                    // var otheraffecttemp=objtemp[otheraffect[k]]
                    // if(otheraffecttemp instanceof Array){
                    //   if(otheraffecttemp.length>0){
                    //     operationArry[i][j]=Config.statusOperations[type][statetemp][operaArraytemp[j]]*Config.statusOperations[type][otheraffect[k]][operaArraytemp[j]];
                    //   }else{
                    //     operationArry[i][j]=Config.statusOperations[type][statetemp][operaArraytemp[j]]*Config.statusOperations[type][otheraffect[k]+'null'][operaArraytemp[j]];
                    //   }
                    // }else{
                    //   if(otheraffecttemp){
                    //     operationArry[i][j]=Config.statusOperations[type][statetemp][operaArraytemp[j]]*Config.statusOperations[type][otheraffect[k]][operaArraytemp[j]];
                    //   }else{
                    //     operationArry[i][j]=Config.statusOperations[type][statetemp][operaArraytemp[j]]*Config.statusOperations[type][otheraffect[k]+'null'][operaArraytemp[j]];
                    //   }
                    // }    
                  }
                }else{//无其他因素影响
                  operationArry[i][j]=Config.statusOperations[type][statetemp][operaArraytemp[j]];
                }
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
      var getCookie=function(name){
        var arr=$document[0].cookie.split(';');
        for(var i=0; i<arr.length; i++){
          var arr2=arr[i].split('=');
          if(arr2[0]==name){
            return arr2[1];
          }
        }
        return '';
      };
      var setCookie=function(name,value){
        $document[0].cookie=name+"="+value + ";";
      }
      service.isServiceReady=function(name){
        var flag=false;
        var serviceStatus=getCookie(name);
        if(serviceStatus){
          if(serviceStatus=="notready"){//service not ready
            setCookie(name,'');
          }else{//service ready
            flag=true;
          }
        }else{//非支付返回
          flag=true;
        }
        return flag;
      }
      service.isInt=function (input) {
        return Number(input) % 1 === 0;
      };
      return service;
    }]);
});
