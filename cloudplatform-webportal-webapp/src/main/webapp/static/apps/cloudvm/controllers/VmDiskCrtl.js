/**
 * Created by jiangfei on 2015/8/12.
 */
define(['controllers/app.controller'], function (controllerModule) {
  controllerModule.controller('VmDiskCrtl', ['$scope','$interval', 'Config', 'HttpService','WidgetService','CurrentContext',
    function ($scope,$interval, Config, HttpService,WidgetService,CurrentContext) {

      $scope.diskList = [];

      $scope.currentPage = 1;
      $scope.totalItems = 0;
      $scope.pageSize = 3;
      $scope.onPageChange = function () {
        refreshDiskList();
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
        };

      refreshDiskList();

    }
  ]);

});
