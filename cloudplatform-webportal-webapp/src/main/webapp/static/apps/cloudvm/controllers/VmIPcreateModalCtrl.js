/**
 * Created by jiangfei on 2015/8/12.
 */
define(['controllers/app.controller'], function (controllerModule) {

  controllerModule.controller('VmIPcreateModalCtrl', function (Config, HttpService,WidgetService,Utility,CurrentContext, $scope, $modalInstance,$timeout,$window, region) {
    $scope.networkBandWidth=2;
    $scope.ipName = '';
    $scope.ipCount = 1;
    $scope.carrierList='';

    $scope.diskTypeList = [];
    $scope.selectedDiskType = null;
    $scope.diskVolume = 10;

    HttpService.doGet('/osn/network/public/list',{'region':region}).success(function(data) {
      $scope.carrierList=data.data;
    });
    $scope.closeModal=function(){
      $modalInstance.dismiss('cancel');
    };
    $scope.isSelectedDiskType = function (diskType) {
      return $scope.selectedDiskType === diskType;
    };
    $scope.selectDiskType = function (diskType) {
      $scope.selectedDiskType = diskType;
    };
    $scope.createIP = function () {
      var data = {
        region:$scope.region,
        name: $scope.ipName,
        bandWidth: $scope.bandWidth,
        count:$scope.ipCount
      };
      HttpService.doPost(Config.urls.floatIP_create,data).success(function (data, status, headers, config) {
        if(data.result===1){
          $modalInstance.close(data);
          $window.location.href = '/payment/'+data.data;
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
