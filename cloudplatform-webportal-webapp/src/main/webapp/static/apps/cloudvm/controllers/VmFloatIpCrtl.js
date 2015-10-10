/**
 * Created by jiangfei on 2015/8/12.
 */
define(['controllers/app.controller'], function (controllerModule) {
  controllerModule.controller('VmFloatIpCrtl', ['$scope','$interval','Config','$modal','HttpService','WidgetService','CurrentContext',
    function ($scope,$interval, Config,$modal,HttpService,WidgetService,CurrentContext) {
      $scope.floatIpList = [];
      $scope.currentPage = 1;
      $scope.totalItems = 0;
      $scope.pageSize = 10;
      $scope.onPageChange = function () {
        refreshFloatIpList();
      };
      $scope.isAllFipChecked=function(){
        var unCheckedFips=$scope.floatIpList.filter(function(fIp){
          return fIp.checked===false || fIp.checked===undefined;
        });
        return unCheckedFips.length==0;
      };
      $scope.checkAllFip=function(){
        if($scope.isAllVpcChecked()){
          $scope.floatIpList.forEach(function(fIp){
            fIp.checked=false;
          });
        }
        else{
          $scope.floatIpList.forEach(function(fIp){
            fIp.checked=true;
          });
        }

      };
      $scope.checkFip = function (fIp) {
        fIp.checked = fIp.checked === true ? false : true;
      };
      $scope.openIPcreateModal=function(size){
        var modalInstance = $modal.open({
          animation: $scope.animationsEnabled,
          templateUrl: 'vmIPcreateModalTpl',
          controller: 'VmIPcreateModalCtrl',
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
      }
      var refreshFloatIpList = function () {
          var queryParams = {
            region:CurrentContext.regionId,
            name:'',
            currentPage: $scope.currentPage,
            recordsPerPage: $scope.pageSize
          };
          WidgetService.showSpin();
          HttpService.doGet(Config.urls.floatIP_list, queryParams).success(function (data, status, headers, config) {
            WidgetService.hideSpin();
            $scope.floatIpList = data.data.data;
            $scope.totalItems = data.data.totalRecords;

          });
        };
      refreshFloatIpList();
    }
  ]);

});
