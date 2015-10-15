/**
 * Created by jiangfei on 2015/8/12.
 */
define(['controllers/app.controller'], function (controllerModule) {

  controllerModule.controller('SubnetCreateModalCtrl', function (Config, HttpService,WidgetService,Utility,ModelService,CurrentContext, $scope, $modalInstance,$timeout,$window, subnetInfo) {

    $scope.subnetCreate = {
      name:''
    };

    $scope.closeModal=function(){
      $modalInstance.dismiss('cancel');
    };
    $scope.createSubnet = function () {
      if (!$scope.subnet_create_form.$valid) return;
      var data = {
      };
      HttpService.doPost(Config.urls.subnet_create, data).success(function (data, status, headers, config) {
        if(data.result===1){
          $modalInstance.close({result:1});
          WidgetService.notifySuccess(data.msgs[0]||'创建子网完成');
        }
        else{
          $modalInstance.dismiss('cancel');
          WidgetService.notifyError(data.msgs[0]||'创建子网失败');
        }
      });
    };
    var initComponents = function () {
          initVpcSelector();
        },
        initVpcSelector = function () {
          HttpService.doGet(Config.urls.vpc_list,{region:subnetInfo.region}).success(function (data, status, headers, config) {
            $scope.vpcList = data.data.data;
            $scope.vpcListSelectorData=$scope.vpcList.map(function(vpc){
              return new ModelService.SelectModel(vpc.name,vpc.id);
            });
            $scope.selectedVpc=$scope.vpcListSelectorData[0];
            console.log($scope.selectedVpc);
          });
        };
        initSelector = function () {
          HttpService.doGet(Config.urls.vpc_list,{region:subnetInfo.region}).success(function (data, status, headers, config) {
            $scope.vpcList = data.data.data;
            $scope.vpcListSelectorData=$scope.vpcList.map(function(vpc){
              return new ModelService.SelectModel(vpc.name,vpc.id);
            });
            $scope.selectedVpc=$scope.vpcListSelectorData[0];
            console.log($scope.selectedVpc);
          });
        };

    initComponents();
  });

});
