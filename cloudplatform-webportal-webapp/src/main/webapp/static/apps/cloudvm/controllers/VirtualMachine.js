/**
 * Created by jiangfei on 2015/8/12.
 */
define(['controllers/app.controller'], function (controllerModule) {
  controllerModule.controller('VirtualMachineCrtl', ['$scope','$interval', '$modal', 'Config', 'HttpService','WidgetService','CurrentContext',
    function ($scope,$interval, $modal, Config, HttpService,WidgetService,CurrentContext) {
      $scope.searchVmName = '';

      $scope.vmList = [];

      $scope.currentPage = 1;
      $scope.totalItems = 0;
      $scope.pageSize = 10;
      $scope.onPageChange = function () {
        refreshVmList();
      };

      $scope.doSearch = function () {
        refreshVmList();
      };

      $scope.startVm=function(size){
        var checkedVms=getCheckedVm();
        if(checkedVms.length !==1){
          WidgetService.notifyWarning('请选中一个云主机');
          return;
        }
        if(checkedVms[0].status!=='SHUTOFF'){
          WidgetService.notifyWarning('云主机当前状态不可启动');
          return;
        }
        var data={
          vmId: checkedVms[0].id
        };
        var modalInstance = WidgetService.openConfirmModal('启动云主机','确定要启动云主机（'+checkedVms[0].name+'）吗？');

        modalInstance.result.then(function (resultData) {
          if(!resultData) return resultData;
          WidgetService.notifyInfo('云主机启动执行中...');
          checkedVms[0].status='STARTING';
          HttpService.doPost(Config.urls.vm_start.replace('{region}', CurrentContext.regionId), data).success(function (data, status, headers, config) {
            if(data.result===1){
              checkedVms[0].status='ACTIVE';
              modalInstance.close(data);
              WidgetService.notifySuccess('启动云主机成功');
            }
            else{
              WidgetService.notifyError(data.msgs[0]||'启动云主机失败');
            }
          });
        }, function () {
        });
      };

      $scope.stopVm=function(size){
        var checkedVms=getCheckedVm();
        if(checkedVms.length !==1){
          WidgetService.notifyWarning('请选中一个云主机');
          return;
        }
        if(checkedVms[0].status!=='ACTIVE'){
          WidgetService.notifyWarning('云主机当前状态不可停止');
          return;
        }
        var data={
          vmId: checkedVms[0].id
        };
        var modalInstance = WidgetService.openConfirmModal('停止云主机','确定要停止云主机（'+checkedVms[0].name+'）吗？');

        modalInstance.result.then(function (resultData) {
          if(!resultData) return resultData;
          WidgetService.notifyInfo('云主机停止执行中...');
          checkedVms[0].status='STOPING';
          HttpService.doPost(Config.urls.vm_stop.replace('{region}', CurrentContext.regionId), data).success(function (data, status, headers, config) {
            if(data.result===1){
              checkedVms[0].status='SHUTOFF';
              modalInstance.close(data);
              WidgetService.notifySuccess('停止云主机成功');
            }
            else{
              WidgetService.notifyError(data.msgs[0]||'停止云主机失败');
            }
          });
        }, function () {
        });
      };

      $scope.deleteVm=function(size){
        var checkedVms=getCheckedVm();
        if(checkedVms.length !==1){
          WidgetService.notifyWarning('请选中一个云主机');
          return;
        }
        if(checkedVms[0].taskState){
          WidgetService.notifyWarning('云主机当前状态不可删除');
          return;
        }
        var data={
          vmId: checkedVms[0].id
        };
        var modalInstance = WidgetService.openConfirmModal('删除云主机','确定要删除云主机（'+checkedVms[0].name+'）吗？');
        modalInstance.result.then(function (resultData) {
          if(!resultData) return resultData;
          WidgetService.notifyInfo('云主机删除执行中...');
          checkedVms[0].status='DELETEING';
          HttpService.doPost(Config.urls.vm_delete.replace('{region}', checkedVms[0].region), data).success(function (data, status, headers, config) {
            if(data.result===1){
              checkedVms[0].status='DELETED';
              modalInstance.close(data);
              WidgetService.notifySuccess('删除云主机成功');
              refreshVmList();
            }
            else{
              WidgetService.notifyError(data.msgs[0]||'删除云主机失败');
            }
          });
        }, function () {
        });
      };


      $scope.isAllVmChecked=function(){
        var unCheckedVms=$scope.vmList.filter(function(vm){
          return vm.checked===false || vm.checked===undefined;
        });
        return unCheckedVms.length==0;
      };
      $scope.checkAllVm=function(){
        if($scope.isAllVmChecked()){
          $scope.vmList.forEach(function(vm){
            vm.checked=false;
          });
        }
        else{
          $scope.vmList.forEach(function(vm){
            vm.checked=true;
          });
        }

      };
      $scope.checkVm = function (vm) {
        vm.checked = vm.checked === true ? false : true;
      };

      $scope.openDiskAttachModal = function (size) {
        var checkedVms=getCheckedVm();
        if(checkedVms.length !==1){
          WidgetService.notifyWarning('请选中一个云主机');
          return;
        }
        if(checkedVms[0].status !=='ACTIVE'){
          WidgetService.notifyWarning('云主机当前状态不可挂载云硬盘');
          return;
        }
        var modalInstance = $modal.open({
          animation: $scope.animationsEnabled,
          templateUrl: 'DiskAttachModalTpl',
          controller: 'DiskAttachModalCtrl',
          size: size,
          backdrop: 'static',
          keyboard: false,
          resolve: {
            region: function () {
              return CurrentContext.regionId;
            },
            vm: function () {
              return checkedVms[0];
            }
          }
        });

        modalInstance.result.then(function (resultData) {
          if(resultData &&resultData.result===1){
            refreshVmList();
          }
        }, function () {
        });
      };

      $scope.openVmCreateModal = function (size) {
        var modalInstance = $modal.open({
          animation: $scope.animationsEnabled,
          templateUrl: '/static/apps/cloudvm/templates/vm-create-modal.html',
          controller: 'VmCreateModalCtrl',
          size: size,
          backdrop: 'static',
          keyboard: false,
          resolve: {
            region: function () {
              return CurrentContext.regionId;
            },
            vmSnapshot: function () {
              return undefined;
            },
            loadAllRegionData:function($q,CurrentContext){
              if(CurrentContext.allRegionData){
                return true;
              }
              else{
                var deferred = $q.defer();
                HttpService.doGet(Config.urls.region_list).success(function (data, status, headers, config) {
                  CurrentContext.allRegionData=data.data;
                  deferred.resolve(true);
                });
                return deferred.promise;
              }
            }
          }
        });

        modalInstance.result.then(function (resultData) {
          if(resultData &&resultData.result===1){
            refreshVmList();
          }
        }, function () {
        });
      };

      $scope.openVmSnapshotCreateModal=function(size){
        var checkedVms=getCheckedVm();
        if(checkedVms.length !==1){
          WidgetService.notifyWarning('请选中一个云主机');
          return;
        }
        var modalInstance = $modal.open({
          animation: $scope.animationsEnabled,
          templateUrl: 'VmSnapshotCreateModalTpl',
          controller: 'VmSnapshotCreateModalCtrl',
          size: size,
          backdrop: 'static',
          keyboard: false,
          resolve: {
            region: function () {
              return CurrentContext.regionId;
            },
            vm: function () {
              return checkedVms[0];
            }
          }
        });

        modalInstance.result.then(function (resultData) {
          if(resultData &&resultData.result===1){
            //refreshDiskList();
          }
        }, function () {
        });
      };

      var refreshVmList = function () {
          var queryParams = {
            name: $scope.searchVmName,
            currentPage: $scope.currentPage,
            recordsPerPage: $scope.pageSize
          };
          WidgetService.showSpin();
          HttpService.doGet(Config.urls.vm_list.replace('{region}', CurrentContext.regionId), queryParams).success(function (data, status, headers, config) {
            WidgetService.hideSpin();
            $scope.vmList = data.data.data;
            $scope.totalItems = data.data.totalRecords;

            $scope.vmList.filter(function(vm){return vm.status=='BUILD'}).forEach(function(vm) {
              var vmDetailUrl = Config.urls.vm_detail.replace('{region}', CurrentContext.regionId).replace('{vmId}', vm.id);
              var buildStatusInterval = $interval(function () {
                HttpService.doGet(vmDetailUrl).success(function (data, status, headers, config) {
                  if (data.result === 1 && data.data.status != 'BUILD') {
                    vm.status = data.data.status;
                    $interval.cancel(buildStatusInterval);
                  }
                });
              }, 5000);
            });

          });
        },
        getCheckedVm=function(){
          return $scope.vmList.filter(function(item){
            return item.checked===true;
          });
        };

      refreshVmList();

    }
  ]);

  controllerModule.controller('DiskAttachModalCtrl', function (Config, HttpService,WidgetService,Utility,ModelService, $scope, $modalInstance, region,vm) {

    $scope.diskList=[];
    $scope.diskListSelectorData=[];
    $scope.selectedDisk=null;

    $scope.closeModal=function(){
      $modalInstance.dismiss('cancel');
    };

    $scope.attachDisk = function () {
      var data = {
        vmId:vm.id,
        volumeId:$scope.selectedDisk.value
      };
      WidgetService.notifyInfo('云硬盘挂载执行中');
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
        initDiskSelector();
      },
      initDiskSelector = function () {
        HttpService.doGet(Config.urls.disk_list.replace('{region}',region),{name: '', currentPage:'', recordsPerPage: ''}).success(function (data, status, headers, config) {
          $scope.diskList = data.data.data;
          $scope.diskListSelectorData=$scope.diskList.filter(function(disk){
            return disk.name && disk.status==='available';
          }).map(function(disk){
            return new ModelService.SelectModel(disk.name,disk.id);
          });
          $scope.selectedDisk=$scope.diskListSelectorData[0];
        });

      };

    initComponents();

  });

  controllerModule.controller('VmSnapshotCreateModalCtrl', function (Config, HttpService,WidgetService,Utility,ModelService, $scope, $modalInstance, region,vm) {

    $scope.vmSnapshotName='';

    $scope.closeModal=function(){
      $modalInstance.dismiss('cancel');
    };

    $scope.createVmSnapshot = function () {
      var data = {
        region:region,
        vmId:vm.id,
        name:$scope.vmSnapshotName
      };
      $scope.isOrderSubmiting=true;
      HttpService.doPost(Config.urls.snapshot_vm_create, data).success(function (data, status, headers, config) {
        if(data.result===1){
          $modalInstance.close(data);
          WidgetService.notifySuccess('云主机快照创建成功');
        }
        else{
          WidgetService.notifyError(data.msgs[0]||'云主机快照创建失败');
          $scope.isOrderSubmiting=false;
        }
      });
    };

  });

});
