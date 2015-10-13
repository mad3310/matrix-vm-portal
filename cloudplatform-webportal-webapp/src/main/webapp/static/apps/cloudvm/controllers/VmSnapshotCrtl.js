/**
 * Created by jiangfei on 2015/8/12.
 */
define(['controllers/app.controller'], function (controllerModule) {
  controllerModule.controller('VmSnapshotCrtl', ['$scope','$interval','$modal', 'Config', 'HttpService','WidgetService','CurrentContext',
    function ($scope,$interval,$modal, Config, HttpService,WidgetService,CurrentContext) {

      $scope.snapshotList = [];

      $scope.currentPage = 1;
      $scope.totalItems = 0;
      $scope.pageSize = 10;
      $scope.onPageChange = function () {
        refreshSnapshotList();
      };

      $scope.deleteSnapshot=function(){
        var checkedSnapshots=getCheckedSnapshot();
        if(checkedSnapshots.length !==1){
          WidgetService.notifyWarning('请选中一个云硬盘');
          return;
        }
        if(checkedSnapshots[0].status!=='available' && checkedSnapshots[0].status!=='error'){
          WidgetService.notifyWarning('云硬盘快照当前状态不可删除');
          return;
        }
        var data={
          region:checkedSnapshots[0].region,
          snapshotId: checkedSnapshots[0].id
        };
        var modalInstance = WidgetService.openConfirmModal('删除云硬盘快照','确定要删除云硬盘快照（'+checkedSnapshots[0].name+'）吗？');
        modalInstance.result.then(function (resultData) {
          if(!resultData) return resultData;
          WidgetService.notifyInfo('云硬盘快照删除执行中...');
          checkedSnapshots[0].status='deleting';
          HttpService.doPost(Config.urls.snapshot_disk_delete, data).success(function (data, status, headers, config) {
            if(data.result===1){
              checkedSnapshots[0].status='deleted';
              modalInstance.close(data);
              WidgetService.notifySuccess('删除云硬盘快照成功');
              refreshSnapshotList();
            }
            else{
              WidgetService.notifyError(data.msgs[0]||'删除云硬盘快照失败');
            }
          });
        }, function () {
        });
      };

      $scope.isAllSnapshotChecked=function(){
        var unCheckedSnapshots=$scope.snapshotList.filter(function(snapshot){
          return snapshot.checked===false || snapshot.checked===undefined;
        });
        return unCheckedSnapshots.length==0;
      };
      $scope.checkAllSnapshot=function(){
        if($scope.isAllSnapshotChecked()){
          $scope.snapshotList.forEach(function(snapshot){
            snapshot.checked=false;
          });
        }
        else{
          $scope.snapshotList.forEach(function(snapshot){
            snapshot.checked=true;
          });
        }

      };
      $scope.checkSnapshot = function (snapshot) {
        snapshot.checked = snapshot.checked === true ? false : true;
      };

      var refreshSnapshotList = function () {
          var queryParams = {
            region:CurrentContext.regionId,
            name: '',
            currentPage: $scope.currentPage,
            recordsPerPage: $scope.pageSize
          };
          WidgetService.showSpin();
          HttpService.doGet(Config.urls.snapshot_disk_list, queryParams).success(function (data, status, headers, config) {
            WidgetService.hideSpin();
            $scope.snapshotList = data.data.data;
            $scope.totalItems = data.data.totalRecords;

          });
      },
        getCheckedSnapshot=function(){
          return $scope.snapshotList.filter(function(item){
            return item.checked===true;
          });
        };

      refreshSnapshotList();

    }
  ]);

});
