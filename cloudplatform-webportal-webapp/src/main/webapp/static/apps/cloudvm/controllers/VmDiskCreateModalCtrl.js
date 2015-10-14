/**
 * Created by jiangfei on 2015/8/12.
 */
define(['controllers/app.controller'], function (controllerModule) {

  controllerModule.controller('VmDiskCreateModalCtrl', function (Config, HttpService,WidgetService,Utility,CurrentContext,ModelService, $scope, $modalInstance,$timeout,$window, region,diskSnapshot) {

    Utility.getRzSliderHack($scope)();
    $scope.diskName = '';
    $scope.diskTypeList = [];
    $scope.selectedDiskType = null;
    $scope.snapshotList = [];
    $scope.snapshotListSelectorData = [];
    $scope.selectedSnapshot = null;
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
        volumeTypeId:$scope.selectedDiskType.id,
        size: $scope.diskVolume,
        count:$scope.diskCount
      };
      $scope.isOrderSubmiting=true;
      HttpService.doPost(Config.urls.disk_create.replace('{region}',region), data).success(function (data, status, headers, config) {
        if(data.result===1){
          $modalInstance.close(data);
          WidgetService.notifySuccess('创建云硬盘成功');
        }
        else{
          WidgetService.notifyError(data.msgs[0]||'创建云硬盘失败');
          $scope.isOrderSubmiting=false;
        }
      });
    };

    var initComponents = function () {
        initDiskTypeSelector();
        initSnapshotTypeSelector();
      },
      initDiskTypeSelector = function () {
        HttpService.doGet(Config.urls.vm_disk_type,{region:region}).success(function (data, status, headers, config) {
          $scope.diskTypeList=data.data;
          $scope.selectedDiskType = $scope.diskTypeList[0];
        });
      },
      initSnapshotTypeSelector=function(){
        if(diskSnapshot){
          $scope.snapshotListSelectorData=[new ModelService.SelectModel(diskSnapshot.name,diskSnapshot.id)];
          $scope.selectedSnapshot=$scope.snapshotListSelectorData[0];
        }
        else{
          HttpService.doGet(Config.urls.snapshot_disk_list,{region:region,name: '', currentPage: '1', recordsPerPage: '1000'}).success(function (data, status, headers, config) {
            $scope.snapshotList = data.data.data;
            $scope.snapshotListSelectorData=$scope.snapshotList.map(function(snapshot){
              return new ModelService.SelectModel(snapshot.name,snapshot.id);
            });
            $scope.snapshotListSelectorData.unshift(new ModelService.SelectModel('请选择快照',''));
            $scope.selectedSnapshot=$scope.snapshotListSelectorData[0];
          });
        }
      };

    initComponents();

  });

});
