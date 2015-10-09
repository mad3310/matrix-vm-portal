/**
 * Created by jiangfei on 2015/8/12.
 */
define(['controllers/app.controller'], function (controllerModule) {

  controllerModule.controller('VmDiskCreateModalCtrl', function (Config, HttpService,WidgetService,Utility,CurrentContext, $scope, $modalInstance,$timeout,$window, region) {

    $scope.diskName = '';
    $scope.diskTypeList = [];
    $scope.selectedDiskType = null;
    $scope.diskVolume = 10;
    $scope.diskCount = 1;

    $scope.closeModal=function(){
      $modalInstance.dismiss('cancel');
    };
    $scope.isSelectedDiskType = function (diskType) {
      return $scope.selectedDiskType === diskType;
    };
    $scope.selectDiskType = function (diskType) {
      $scope.selectedDiskType = diskType;
    };
    $scope.createDisk = function () {
      var data = {
        name: $scope.diskName,
        description:'',
        size: $scope.diskVolume,
        count:$scope.diskCount
      };
      HttpService.doPost(Config.urls.disk_create.replace('{region}',region), data).success(function (data, status, headers, config) {
        if(data.result===1){
          $modalInstance.close(data);
          $window.location.href = '/payment/'+data.data;
        }
        else{
          WidgetService.notifyError(data.msgs[0]||'创建云硬盘失败');
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
