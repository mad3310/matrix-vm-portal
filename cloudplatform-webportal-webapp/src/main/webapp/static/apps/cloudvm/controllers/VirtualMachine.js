/**
 * Created by jiangfei on 2015/8/12.
 */
define(['controllers/app.controller'], function (controllerModule) {
  controllerModule.controller('VirtualMachineCrtl', ['$scope','$interval','$window', '$modal', 'Config','Utility', 'HttpService','WidgetService','CurrentContext',
    function ($scope,$interval,$window, $modal, Config,Utility, HttpService,WidgetService,CurrentContext) {
      $scope.searchVmName = '';
      $scope.vmTaskStatuses = Config.vmTaskStatuses;
      $scope.vmList = [];

      $scope.currentPage = 1;
      $scope.totalItems = 0;
      $scope.pageSize = 10;
      $scope.operationBtn={};
      var operationArry=[];
      $scope.onPageChange = function () {
        refreshVmList();
      };

      $scope.doSearch = function () {
        refreshVmList();
      };

      $scope.startVm=function(size){
        var checkedVms=getCheckedVm(),
          originalVmState=checkedVms[0].vmState,
          originalTaskState=checkedVms[0].taskState;
        if(checkedVms.length !==1){
          WidgetService.notifyWarning('请选中一个云主机');
          return;
        }
        if(originalTaskState || originalVmState!=='stopped'){
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
          checkedVms[0].vmState=null;
          checkedVms[0].taskState='starting';
          HttpService.doPost(Config.urls.vm_start.replace('{region}', CurrentContext.regionId), data).success(function (data, status, headers, config) {
            if(data.result===1){
              checkedVms[0].vmState='active';
              checkedVms[0].taskState=null;
              modalInstance.close(data);
              WidgetService.notifySuccess('启动云主机成功');
              refreshVmList();
            }
            else{
              checkedVms[0].vmState=originalVmState;
              checkedVms[0].taskState=originalTaskState;
              WidgetService.notifyError(data.msgs[0]||'启动云主机失败');
            }
          });
        }, function () {
        });
      };

      $scope.stopVm=function(size){
        var checkedVms=getCheckedVm(),
          originalVmState=checkedVms[0].vmState,
          originalTaskState=checkedVms[0].taskState;
        if(checkedVms.length !==1){
          WidgetService.notifyWarning('请选中一个云主机');
          return;
        }
        if(originalTaskState || originalVmState!=='active'){
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
          checkedVms[0].vmState=null;
          checkedVms[0].taskState='stopping';
          HttpService.doPost(Config.urls.vm_stop.replace('{region}', CurrentContext.regionId), data).success(function (data, status, headers, config) {
            if(data.result===1){
              checkedVms[0].vmState='stopped';
              checkedVms[0].taskState=null;
              modalInstance.close(data);
              WidgetService.notifySuccess('停止云主机成功');
              refreshVmList();
            }
            else{
              checkedVms[0].vmState=originalVmState;
              checkedVms[0].taskState=originalTaskState;
              WidgetService.notifyError(data.msgs[0]||'停止云主机失败');
            }
          });
        }, function () {
        });
      };

      $scope.deleteVm=function(size){
        var checkedVms=getCheckedVm(),
          originalVmState=checkedVms[0].vmState,
          originalTaskState=checkedVms[0].taskState;
        if(checkedVms.length !==1){
          WidgetService.notifyWarning('请选中一个云主机');
          return;
        }
        if(originalTaskState || (originalVmState!=='active' && originalVmState !=='stopped')){
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
          checkedVms[0].vmState=null;
          checkedVms[0].taskState='deleting';
          HttpService.doPost(Config.urls.vm_delete.replace('{region}', checkedVms[0].region), data).success(function (data, status, headers, config) {
            if(data.result===1){
              checkedVms[0].vmState='deleted';
              checkedVms[0].taskState=null;
              modalInstance.close(data);
              WidgetService.notifySuccess('删除云主机成功');
              refreshVmList();
            }
            else{
              checkedVms[0].vmState=originalVmState;
              checkedVms[0].taskState=originalTaskState;
              WidgetService.notifyError(data.msgs[0]||'删除云主机失败');
            }
          });
        }, function () {
        });
      };

      $scope.rebootVm=function(size){
        var checkedVms=getCheckedVm(),
          originalVmState=checkedVms[0].vmState,
          originalTaskState=checkedVms[0].taskState;
        if(checkedVms.length !==1){
          WidgetService.notifyWarning('请选中一个云主机');
          return;
        }
        if(originalTaskState || originalVmState!=='active'){
          WidgetService.notifyWarning('云主机当前状态不可重启');
          return;
        }
        var data={
          vmId: checkedVms[0].id,
          region:checkedVms[0].region
        };
        var modalInstance = WidgetService.openConfirmModal('重启云主机','确定要重启云主机（'+checkedVms[0].name+'）吗？');
        modalInstance.result.then(function (resultData) {
          if(!resultData) return resultData;
          WidgetService.notifyInfo('云主机重启执行中...');
          checkedVms[0].vmState=null;
          checkedVms[0].taskState='rebooting';
          HttpService.doPost(Config.urls.vm_reboot,data).success(function (data, status, headers, config) {
            if(data.result===1){
              modalInstance.close(data);
              checkedVms[0].vmState='active';
              checkedVms[0].taskState=null;
              WidgetService.notifySuccess('重启云主机成功');
              refreshVmList();
            }
            else{
              checkedVms[0].vmState=originalVmState;
              checkedVms[0].taskState=originalTaskState;
              WidgetService.notifyError(data.msgs[0]||'重启云主机失败');
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
      $scope.checkVm = function (vm,index) {
        vm.checked = vm.checked === true ? false : true;
      };

      $scope.openDiskAttachModal = function (size) {
        var checkedVms=getCheckedVm();
        if(checkedVms.length !==1){
          WidgetService.notifyWarning('请选中一个云主机');
          return;
        }
        if(checkedVms[0].taskState || (checkedVms[0].vmState!=='active' && checkedVms[0].vmState !=='stopped')){
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

      $scope.openDiskDetachModal = function (size) {
        var checkedVms=getCheckedVm();
        if(checkedVms.length !==1){
          WidgetService.notifyWarning('请选中一个云主机');
          return;
        }
        if(checkedVms[0].taskState || (checkedVms[0].vmState!=='active' && checkedVms[0].vmState !=='stopped')){
          WidgetService.notifyWarning('云主机当前状态不可卸载云硬盘');
          return;
        }
        if(!checkedVms[0].volumes || !checkedVms[0].volumes.length){
          WidgetService.notifyWarning('云主机还没有绑定云硬盘');
          return;
        }
        var modalInstance = $modal.open({
          animation: $scope.animationsEnabled,
          templateUrl: 'DiskDetachModalTpl',
          controller: 'DiskDetachModalCtrl',
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

      $scope.openFloatingIpBindModal = function (size) {
        var checkedVms=getCheckedVm();
        if(checkedVms.length !==1){
          WidgetService.notifyWarning('请选中一个云主机');
          return;
        }
        if(checkedVms[0].taskState || (checkedVms[0].vmState!=='active' && checkedVms[0].vmState!=='stopped')){
          WidgetService.notifyWarning('云主机当前状态不可绑定公网ip');
          return;
        }
        var modalInstance = $modal.open({
          animation: $scope.animationsEnabled,
          templateUrl: 'FloatingIpBindModalTpl',
          controller: 'FloatingIpBindModalCtrl',
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
        if(checkedVms[0].taskState || (checkedVms[0].vmState!=='active' && checkedVms[0].vmState!=='stopped')){
          WidgetService.notifyWarning('云主机当前状态不可创建快照');
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

      $scope.openVmPasswordChangeModal=function(size){
        var checkedVms=getCheckedVm();
        if(checkedVms.length !==1){
          WidgetService.notifyWarning('请选中一个云主机');
          return;
        }
        if(checkedVms[0].taskState || checkedVms[0].vmState!=='active'){
          WidgetService.notifyWarning('云主机当前状态不可修改密码');
          return;
        }
        var modalInstance = $modal.open({
          animation: $scope.animationsEnabled,
          templateUrl: 'VmPasswordChangeModalTpl',
          controller: 'VmPasswordChangeModalCtrl',
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

      $scope.navigateToVNC=function(vm){
        if(vm.taskState || vm.vmState!=='active'){
          WidgetService.notifyWarning('云主机当前状态不可启动VNC');
          return;
        }
        HttpService.doPost(Config.urls.vm_vnc.replace('{region}', CurrentContext.regionId), {vmId:vm.id}).success(function (data, status, headers, config) {
          if(data.result===1){
            $window.open(data.data);
          }
          else{
            WidgetService.notifyError(data.msgs[0]||'获取云主机VNC连接失败');
          }
        });
      };

      var refreshVmList = function () {
          var queryParams = {
            name: $scope.searchVmName,
            currentPage: $scope.currentPage,
            recordsPerPage: $scope.pageSize
          };
          $scope.isListLoading=true;
          HttpService.doGet(Config.urls.vm_list.replace('{region}', CurrentContext.regionId), queryParams).success(function (data, status, headers, config) {
            $scope.isListLoading=false;
            $scope.vmList = data.data.data;
            $scope.totalItems = data.data.totalRecords;

            $scope.vmList.filter(function(vm){return vm.taskState=='spawning'}).forEach(function(vm) {
              var vmDetailUrl = Config.urls.vm_detail.replace('{region}', CurrentContext.regionId).replace('{vmId}', vm.id);
              var buildStatusInterval = $interval(function () {
                HttpService.doGet(vmDetailUrl).success(function (data, status, headers, config) {
                  if (data.result === 1 && data.data.taskState != 'spawning') {
                    vm.vmState = data.data.vmState;
                    vm.taskState = data.data.taskState;
                    $interval.cancel(buildStatusInterval);
                    refreshVmList();
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
      var watchStateChange=function(){
        var productInfo={
          'type':'virtualMachine',
          'state':'vmState',
          'operations':['create','start','stop','delete','restart','createsnap','attachdisk','detachdisk']
        }
        $scope.$watch(function(){
          return $scope.vmList.map(function(vm) {
            return vm.checked;
          }).join(';');
        },function(){
          var operationArraycopy=Utility.setOperationBtns($scope,$scope.vmList,productInfo,operationArry,Config);
          var operaArraytemp=productInfo.operations;
          for(var k in operaArraytemp){
            $scope.operationBtn[operaArraytemp[k]]=operationArraycopy[k]
          }
        });
      } 
      refreshVmList();
      watchStateChange();
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
      $scope.isFormSubmiting=true;
      HttpService.doPost(Config.urls.disk_attach.replace('{region}',region), data).success(function (data, status, headers, config) {
        if(data.result===1){
          $modalInstance.close(data);
          WidgetService.notifySuccess('云硬盘挂载成功');
        }
        else{
          $scope.isFormSubmiting=false;
          WidgetService.notifyError(data.msgs[0]||'云硬盘挂载失败');
          $scope.isFormSubmiting=false;
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

  controllerModule.controller('DiskDetachModalCtrl', function (Config, HttpService,WidgetService,Utility,ModelService, $scope, $modalInstance, region,vm) {

    $scope.diskList=[];
    $scope.diskListSelectorData=[];
    $scope.selectedDisk=null;

    $scope.closeModal=function(){
      $modalInstance.dismiss('cancel');
    };

    $scope.detachDisk = function () {
      var data = {
        vmId:vm.id,
        volumeId:$scope.selectedDisk.value
      };
      $scope.isFormSubmiting=true;
      HttpService.doPost(Config.urls.disk_detach.replace('{region}',region), data).success(function (data, status, headers, config) {
        if(data.result===1){
          $modalInstance.close(data);
          WidgetService.notifySuccess('云硬盘卸载成功');
        }
        else{
          $scope.isFormSubmiting=false;
          WidgetService.notifyError(data.msgs[0]||'云硬盘卸载失败');
          $scope.isFormSubmiting=false;
        }
      });
    };

    var initComponents = function () {
        initDiskSelector();
      },
      initDiskSelector = function () {
          $scope.diskList = vm.volumes;
          $scope.diskListSelectorData=$scope.diskList.filter(function(disk){
            return disk.name;
          }).map(function(disk){
            return new ModelService.SelectModel(disk.name,disk.id);
          });
          $scope.selectedDisk=$scope.diskListSelectorData[0];

      };

    initComponents();

  });

  controllerModule.controller('FloatingIpBindModalCtrl', function (Config, HttpService,WidgetService,Utility,ModelService, $scope, $modalInstance, region,vm) {

    $scope.floatingIpList=[];
    $scope.floatingIpListSelectorData=[];
    $scope.selectedFloatingIp=null;

    $scope.closeModal=function(){
      $modalInstance.dismiss('cancel');
    };

    $scope.bindFloatingIp = function () {
      var data = {
        region:region,
        vmId:vm.id,
        floatingIpId:$scope.selectedFloatingIp.value
      };
      $scope.isFormSubmiting=true;
      HttpService.doPost(Config.urls.floatIp_bindVm, data).success(function (data, status, headers, config) {
        if(data.result===1){
          $modalInstance.close(data);
          WidgetService.notifySuccess('绑定公网ip成功');
        }
        else{
          $scope.isFormSubmiting=false;
          WidgetService.notifyError(data.msgs[0]||'绑定公网ip失败');
          $scope.isFormSubmiting=false;
        }
      });
    };

    var initComponents = function () {
        initFloatingIpSelector();
      },
      initFloatingIpSelector = function () {
        HttpService.doGet(Config.urls.floatIP_list,{region:region,name: '', currentPage:'', recordsPerPage: ''}).success(function (data, status, headers, config) {
          $scope.floatingIpList = data.data.data;
          $scope.floatingIpListSelectorData=$scope.floatingIpList.filter(function(floatingIp){
            return floatingIp.status==='AVAILABLE';
          }).map(function(floatingIp){
            return new ModelService.SelectModel(floatingIp.name,floatingIp.id);
          });
          $scope.selectedFloatingIp=$scope.floatingIpListSelectorData[0];
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
      $scope.isFormSubmiting=true;
      HttpService.doPost(Config.urls.snapshot_vm_create, data).success(function (data, status, headers, config) {
        if(data.result===1){
          $modalInstance.close(data);
          WidgetService.notifySuccess('云主机快照创建成功');
        }
        else{
          WidgetService.notifyError(data.msgs[0]||'云主机快照创建失败');
          $scope.isFormSubmiting=false;
        }
      });
    };

  });

  controllerModule.controller('VmPasswordChangeModalCtrl', function (Config, HttpService,WidgetService,Utility,ModelService, $scope, $modalInstance, region,vm) {

    $scope.newPassword='';
    $scope.confirmPassword='';

    $scope.closeModal=function(){
      $modalInstance.dismiss('cancel');
    };

    $scope.changeVmPassword = function () {
      var data = {
        region:region,
        vmId:vm.id,
        adminPass:$scope.confirmPassword
      };
      $scope.isFormSubmiting=true;
      HttpService.doPost(Config.urls.vm_password_change, data).success(function (data, status, headers, config) {
        if(data.result===1){
          $modalInstance.close(data);
          WidgetService.notifySuccess('修改云主机密码成功');
        }
        else{
          WidgetService.notifyError(data.msgs[0]||'修改云主机密码失败');
          $scope.isFormSubmiting=false;
        }
      });
    };

  });

});
