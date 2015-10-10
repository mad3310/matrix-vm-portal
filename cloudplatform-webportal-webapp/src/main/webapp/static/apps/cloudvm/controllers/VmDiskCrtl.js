/**
 * Created by jiangfei on 2015/8/12.
 */
define(['controllers/app.controller'], function (controllerModule) {
  controllerModule.controller('VmDiskCrtl', ['$scope','$interval','$modal', 'Config', 'HttpService','WidgetService','CurrentContext',
    function ($scope,$interval,$modal, Config, HttpService,WidgetService,CurrentContext) {

      $scope.diskList = [];

      $scope.currentPage = 1;
      $scope.totalItems = 0;
      $scope.pageSize = 10;
      $scope.onPageChange = function () {
        refreshDiskList();
      };


      $scope.openVmDiskCreateModal = function (size) {
        var modalInstance = $modal.open({
          animation: $scope.animationsEnabled,
          templateUrl: 'VmDiskCreateModalTpl',
          controller: 'VmDiskCreateModalCtrl',
          size: size,
          backdrop: 'static',
          keyboard: false,
          resolve: {
            region: function () {
              return CurrentContext.regionId;
            }
          }
        });

        modalInstance.result.then(function (resultData) {
          if(resultData &&resultData.result===1){
            //refreshVmList();
          }
        }, function () {
        });
      };

      $scope.openVmDiskAttachModal = function (size) {
        var checkedDisks=getCheckedDisk();
        if(checkedDisks.length !==1){
          WidgetService.notifyWarning('请选中一个云硬盘');
          return;
        }
        if(checkedDisks[0].status !=='available'){
          WidgetService.notifyWarning('云硬盘当前状态不可挂载到云主机');
          return;
        }
        var modalInstance = $modal.open({
          animation: $scope.animationsEnabled,
          templateUrl: 'VmDiskAttachModalTpl',
          controller: 'VmDiskAttachModalCtrl',
          size: size,
          backdrop: 'static',
          keyboard: false,
          resolve: {
            region: function () {
              return CurrentContext.regionId;
            },
            disk: function () {
              return checkedDisks[0];
            }
          }
        });

        modalInstance.result.then(function (resultData) {
          if(resultData &&resultData.result===1){
            refreshDiskList();
          }
        }, function () {
        });
      };

      $scope.detachDisk=function(){
        var checkedDisks=getCheckedDisk();
        if(checkedDisks.length !==1){
          WidgetService.notifyWarning('请选中一个云硬盘');
          return;
        }
        if(checkedDisks[0].status !=='in-use'){
          WidgetService.notifyWarning('云硬盘当前状态不可解挂');
          return;
        }
        var data={
          vmId: checkedDisks[0].attachments[0].vmId,
          volumeId: checkedDisks[0].id,
        };
        var modalInstance = WidgetService.openConfirmModal('解挂云硬盘','确定要从云主机（'+checkedDisks[0].attachments[0].name+'）解挂云硬盘（'+checkedDisks[0].name+'）吗？');
        modalInstance.result.then(function (resultData) {
          if(!resultData) return resultData;
          WidgetService.notifyInfo('云硬盘解挂执行中...');
          checkedDisks[0].status='detaching';
          HttpService.doPost(Config.urls.disk_detach.replace('{region}', checkedDisks[0].region), data).success(function (data, status, headers, config) {
            if(data.result===1){
              modalInstance.close(data);
              WidgetService.notifySuccess('解挂云硬盘成功');
              refreshDiskList();
            }
            else{
              WidgetService.notifyError(data.msgs[0]||'解挂云硬盘失败');
            }
          });
        }, function () {
        });
      };

      $scope.deleteDisk=function(){
        var checkedDisks=getCheckedDisk();
        if(checkedDisks.length !==1){
          WidgetService.notifyWarning('请选中一个云硬盘');
          return;
        }
        var data={
          volumeId: checkedDisks[0].id
        };
        var modalInstance = WidgetService.openConfirmModal('删除云硬盘','确定要删除云硬盘（'+checkedDisks[0].name+'）吗？');
        modalInstance.result.then(function (resultData) {
          if(!resultData) return resultData;
          WidgetService.notifyInfo('云硬盘删除执行中...');
          checkedDisks[0].status='deleting';
          HttpService.doPost(Config.urls.disk_delete.replace('{region}', checkedDisks[0].region), data).success(function (data, status, headers, config) {
            if(data.result===1){
              checkedDisks[0].status='deleted';
              modalInstance.close(data);
              WidgetService.notifySuccess('删除云硬盘成功');
              refreshDiskList();
            }
            else{
              WidgetService.notifyError(data.msgs[0]||'删除云硬盘失败');
            }
          });
        }, function () {
        });
      };

      $scope.isAllDiskChecked=function(){
        var unCheckedDisks=$scope.diskList.filter(function(disk){
          return disk.checked===false || disk.checked===undefined;
        });
        return unCheckedDisks.length==0;
      };
      $scope.checkAllDisk=function(){
        if($scope.isAllDiskChecked()){
          $scope.diskList.forEach(function(disk){
            disk.checked=false;
          });
        }
        else{
          $scope.diskList.forEach(function(disk){
            disk.checked=true;
          });
        }

      };
      $scope.checkDisk = function (disk) {
        disk.checked = disk.checked === true ? false : true;
      };

      var refreshDiskList = function () {
          var queryParams = {
            name: '',
            currentPage: $scope.currentPage,
            recordsPerPage: $scope.pageSize
          };
          WidgetService.showSpin();
          HttpService.doGet(Config.urls.disk_list.replace('{region}', CurrentContext.regionId), queryParams).success(function (data, status, headers, config) {
            WidgetService.hideSpin();
            $scope.diskList = data.data.data;
            $scope.totalItems = data.data.totalRecords;

          });
      },
        getCheckedDisk=function(){
          return $scope.diskList.filter(function(item){
            return item.checked===true;
          });
        };

      refreshDiskList();

    }
  ]);

  controllerModule.controller('VmDiskAttachModalCtrl', function (Config, HttpService,WidgetService,Utility,ModelService, $scope, $modalInstance, region,disk) {

    $scope.vmList=[];
    $scope.vmListSelectorData=[];
    $scope.selectedVm=null;

    $scope.closeModal=function(){
      $modalInstance.dismiss('cancel');
    };

    $scope.attachDisk = function () {
      var data = {
        vmId:$scope.selectedVm.value,
        volumeId:disk.id
      };
      disk.status='attaching';
      HttpService.doPost(Config.urls.disk_attach.replace('{region}',region), data).success(function (data, status, headers, config) {
        if(data.result===1){
          $modalInstance.close(data);
          WidgetService.notifySuccess('云硬盘挂载成功');
        }
        else{
          WidgetService.notifyError(data.msgs[0]||'云硬盘挂载失败');
        }
      });
    };

    var initComponents = function () {
        initVmSelector();
      },
      initVmSelector = function () {
        HttpService.doGet(Config.urls.vm_list.replace('{region}',region),{name: '', currentPage:'', recordsPerPage: ''}).success(function (data, status, headers, config) {
          $scope.vmList = data.data.data;
          $scope.vmListSelectorData=$scope.vmList.map(function(vm){
            return new ModelService.SelectModel(vm.name,vm.id);
          });
          $scope.selectedVm=$scope.vmListSelectorData[0];
        });

      };

    initComponents();

  });

});
