/**
 * Created by jiangfei on 2015/8/12.
 */
define(['controllers/app.controller'], function (controllerModule) {
  controllerModule.controller('VirtualMachineCrtl', ['$scope', '$modal', 'Config', 'HttpService','WidgetService','CurrentContext',
    function ($scope, $modal, Config, HttpService,WidgetService,CurrentContext) {
      $scope.searchVmName = '';

      $scope.vmList = [];

      $scope.currentPage = 1;
      $scope.totalItems = 0;
      $scope.pageSize = 3;
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
        var data={
          vmId: checkedVms[0].id
        };
        var modalInstance = $modal.open({
          templateUrl: 'ConfirmModalTpl',
          controller: 'ConfirmModalCtrl',
          size: size,
          resolve: {
            message:function(){
              return  '确定要启动云主机test吗？';
            },
            title:function(){
              return  '启动云主机';
            }
          }
        });

        modalInstance.result.then(function (resultData) {
          if(!resultData) return resultData;
          HttpService.doPost(Config.urls.vm_start.replace('{region}', CurrentContext.regionId), data).success(function (data, status, headers, config) {
            if(data.result===1){
              $modalInstance.close(data);
              WidgetService.notifySuccess('创建云主机成功');
            }
            else{
              WidgetService.notifyError(data.msgs[0]||'创建云主机失败');
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
        var data={
          vmId: checkedVms[0].id
        };
        var modalInstance = WidgetService.openConfirmModal('删除云主机','确定要删除云主机test吗？');
        modalInstance.result.then(function (resultData) {
          if(!resultData) return resultData;
          HttpService.doPost(Config.urls.vm_delete.replace('{region}', checkedVms[0].region), data).success(function (data, status, headers, config) {
            if(data.result===1){
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

      $scope.items = ['item1', 'item2', 'item3'];
      $scope.openVmCreateModal = function (size) {
        var modalInstance = $modal.open({
          animation: $scope.animationsEnabled,
          templateUrl: 'VmCreateModalTpl',
          controller: 'VmCreateModalCtrl',
          size: size,
          resolve: {
            items: function () {
              return $scope.items;
            },
            region: function () {
              return CurrentContext.regionId;
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

      var refreshVmList = function () {
            var queryParams = {
              name: $scope.searchVmName,
              currentPage: $scope.currentPage,
              recordsPerPage: $scope.pageSize
            };
          HttpService.doGet(Config.urls.vm_list.replace('{region}', CurrentContext.regionId), queryParams).success(function (data, status, headers, config) {
            $scope.vmList = data.data.data;
            $scope.totalItems = data.data.totalRecords;
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

});
