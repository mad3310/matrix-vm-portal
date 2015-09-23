/**
 * Created by jiangfei on 2015/8/12.
 */
define(['controllers/app.controller'], function (controllerModule) {
  controllerModule.controller('VmVpcCrtl', ['$scope','$interval', 'Config', 'HttpService','WidgetService','CurrentContext',
    function ($scope,$interval, Config, HttpService,WidgetService,CurrentContext) {

      $scope.vpcList = [];

      $scope.currentPage = 1;
      $scope.totalItems = 0;
      $scope.pageSize = 3;
      $scope.onPageChange = function () {
        refreshVpcList();
      };



      $scope.isAllVpcChecked=function(){
        var unCheckedVpcs=$scope.vpcList.filter(function(vpc){
          return vpc.checked===false || vpc.checked===undefined;
        });
        return unCheckedVpcs.length==0;
      };
      $scope.checkAllVpc=function(){
        if($scope.isAllVpcChecked()){
          $scope.vpcList.forEach(function(vpc){
            vpc.checked=false;
          });
        }
        else{
          $scope.vpcList.forEach(function(vpc){
            vpc.checked=true;
          });
        }

      };
      $scope.checkVpc = function (vpc) {
        vpc.checked = vpc.checked === true ? false : true;
      };

      var refreshVpcList = function () {
          var queryParams = {
            region:CurrentContext.regionId,
            name:'',
            currentPage: $scope.currentPage,
            recordsPerPage: $scope.pageSize
          };
          WidgetService.showSpin();
          HttpService.doGet(Config.urls.vpc_list, queryParams).success(function (data, status, headers, config) {
            WidgetService.hideSpin();
            $scope.vpcList = data.data.data;
            $scope.totalItems = data.data.totalRecords;

          });
        };

      refreshVpcList();

    }
  ]);

});
