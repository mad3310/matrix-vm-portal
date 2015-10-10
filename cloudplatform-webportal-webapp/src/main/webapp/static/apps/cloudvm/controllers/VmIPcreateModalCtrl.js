/**
 * Created by jiangfei on 2015/8/12.
 */
define(['controllers/app.controller'], function (controllerModule) {

  controllerModule.controller('VmIPcreateModalCtrl', function (Config, HttpService,WidgetService,Utility,CurrentContext, $scope, $modalInstance,$timeout,$window, region) {
    $scope.networkBandWidth=2;
    $scope.ipName = '';
    $scope.ipCount = 1;
    $scope.carrierList='';
    $scope.selectedCarrier = null;

    $scope.diskTypeList = [];
    $scope.selectedDiskType = null;
    $scope.diskVolume = 10;

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
    $scope.isSelectedDiskType = function (diskType) {
      return $scope.selectedDiskType === diskType;
    };
    $scope.selectDiskType = function (diskType) {
      $scope.selectedDiskType = diskType;
    };
    $scope.createIP = function () {
      var data = {
        'region':region,
        name: $scope.ipName,
        publicNetworkId:$scope.selectedCarrier.id,
        bandWidth: $scope.networkBandWidth,
        count:$scope.ipCount
      };
      HttpService.doPost(Config.urls.floatIP_create,data).success(function (data, status, headers, config) {
        if(data.result===1){
          $modalInstance.close(data);
          WidgetService.notifySuccess('创建公网IP成功');
          // $window.location.href = '/payment/'+data.data;
          
        }
        else{
          WidgetService.notifyError(data.msgs[0]||'创建公网IP失败');
        }
      });
    };

    var initComponents = function () {
        initDiskTypeSelector();
      },
      initDiskTypeSelector = function () {
        HttpService.doGet(Config.urls.vm_disk_type,{region:region}).success(function (data, status, headers, config) {
          $scope.diskTypeList=data.data;
          $scope.selectedDiskType = $scope.diskTypeList[0];
        });
      };

    initComponents();

  });

});
