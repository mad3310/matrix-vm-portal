/**
 * Created by jiangfei on 2015/8/12.
 */
define(['controllers/app.controller'], function (controllerModule) {

  controllerModule.controller('VmIPcreateModalCtrl', function (Config, HttpService,WidgetService,Utility,CurrentContext, $scope, $modalInstance,$timeout,$window, region) {
    Utility.getRzSliderHack($scope)();
    $scope.networkBandWidth=2;
    $scope.ipName = '';
    $scope.ipCount = 1;
    $scope.carrierList='';
    $scope.selectedCarrier = null;
    HttpService.doGet('/osn/network/public/list',{'region':region}).success(function(data) {
      $scope.carrierList=data.data;
    });
    $scope.closeModal=function(){
      $modalInstance.dismiss('cancel');
    };
    $scope.selectCarrier = function (carrier) {
      $scope.selectedCarrier = carrier;
    };
    $scope.isSelectedCarrier = function (carrier) {
      return $scope.selectedCarrier = carrier;
    };
    $scope.createIP = function () {
      var data = {
        'region':region,
        name: $scope.ipName,
        publicNetworkId:$scope.selectedCarrier.id,
        bandWidth: $scope.networkBandWidth,
        count:$scope.ipCount
      };
      $scope.isOrderSubmiting=true;
      HttpService.doPost(Config.urls.floatIP_create,data).success(function (data, status, headers, config) {
        if(data.result===1){
          $modalInstance.close(data);
          WidgetService.notifySuccess('创建公网IP成功');
          // $window.location.href = '/payment/'+data.data;
          
        }
        else{
          WidgetService.notifyError(data.msgs[0]||'创建公网IP失败');
          $scope.isOrderSubmiting=false;
        }
      });
    };
  });

});
