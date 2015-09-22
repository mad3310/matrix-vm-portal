/**
 * Created by jiangfei on 2015/8/12.
 */
define(['controllers/app.controller'], function (controllerModule) {
  controllerModule.controller('CloudDiskCrtl', ['$scope','$interval', 'Config', 'HttpService','WidgetService','CurrentContext',
    function ($scope,$interval, Config, HttpService,WidgetService,CurrentContext) {

      $scope.diskList = [];

      $scope.currentPage = 1;
      $scope.totalItems = 0;
      $scope.pageSize = 3;
      $scope.onPageChange = function () {
        refreshDiskList();
      };



      $scope.isAllVmChecked=function(){
        var unCheckedVms=$scope.diskList.filter(function(vm){
          return vm.checked===false || vm.checked===undefined;
        });
        return unCheckedVms.length==0;
      };
      $scope.checkAllVm=function(){
        if($scope.isAllVmChecked()){
          $scope.diskList.forEach(function(vm){
            vm.checked=false;
          });
        }
        else{
          $scope.diskList.forEach(function(vm){
            vm.checked=true;
          });
        }

      };
      $scope.checkVm = function (vm) {
        vm.checked = vm.checked === true ? false : true;
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
        getCheckedVm=function(){
          return $scope.diskList.filter(function(item){
            return item.checked===true;
          });
        };

      refreshDiskList();

    }
  ]);

});
