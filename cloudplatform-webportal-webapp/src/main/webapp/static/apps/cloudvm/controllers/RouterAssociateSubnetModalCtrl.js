/**
 * Created by jiangfei on 2015/8/12.
 */
define(['controllers/app.controller'], function (controllerModule) {

  controllerModule.controller('AssociateSubnetModalCtrl', function (Config, HttpService,WidgetService,Utility,ModelService, $scope, $modalInstance,$timeout,$window, routerInfo) {
    $scope.associateRouterName = routerInfo.routerName;


    $scope.closeModal=function(){
      $modalInstance.dismiss('cancel');
    };
    $scope.associateSubnet = function () {
      var data = {
        region:routerInfo.region,
        routerId:routerInfo.routerId,
        subnetId:$scope.selectedSubnet.value
      };
      HttpService.doPost(Config.urls.subnet_associate, data).success(function (data, status, headers, config) {
        if(data.result===1){
          $modalInstance.close({result:1});
          WidgetService.notifySuccess(data.msgs[0]||'子网关联成功');
        }
        else{
          $modalInstance.dismiss('cancel');
          WidgetService.notifyError(data.msgs[0]||'子网关联失败');
        }
      });
    };

    var initComponents = function () {
      initSubnetSelector();
    },initSubnetSelector = function () {
      HttpService.doGet(Config.urls.subnet_list.replace('{region}',routerInfo.region),{name: '', currentPage:'', recordsPerPage: ''}).success(function (data, status, headers, config) {
        $scope.subnetList = data.data.data;
        $scope.subnetListSelectorData=$scope.subnetList.map(function(subnet){
          return new ModelService.SelectModel(subnet.name,subnet.id);
        });
        $scope.selectedSubnet=$scope.subnetListSelectorData[0];
        console.log($scope.selectedSubnet);
      });
    };
    initComponents();
  });

  controllerModule.controller('RemoveSubnetModalCtrl', function (Config, HttpService,WidgetService,Utility,ModelService, $scope, $modalInstance,$timeout,$window, routerInfo) {
    $scope.routerName = routerInfo.routerName;


    $scope.closeModal=function(){
      $modalInstance.dismiss('cancel');
    };
    $scope.removeSubnet = function () {
      var data = {
        region:routerInfo.region,
        routerId:routerInfo.routerId,
        subnetId:$scope.selectedSubnet.value
      };
      HttpService.doPost(Config.urls.subnet_remove, data).success(function (data, status, headers, config) {
        if(data.result===1){
          $modalInstance.close({result:1});
          WidgetService.notifySuccess(data.msgs[0]||'子网关联成功');
        }
        else{
          $modalInstance.dismiss('cancel');
          WidgetService.notifyError(data.msgs[0]||'子网关联失败');
        }
      });
    };

    var initComponents = function () {
      initSubnetSelector();
    },initSubnetSelector = function () {
      HttpService.doGet(Config.urls.subnet_list.replace('{region}',routerInfo.region),{name: '', currentPage:'', recordsPerPage: ''}).success(function (data, status, headers, config) {
        $scope.subnetList = data.data.data;
        $scope.subnetListSelectorData=$scope.subnetList.map(function(subnet){
          return new ModelService.SelectModel(subnet.name,subnet.id);
        });
        $scope.selectedSubnet=$scope.subnetListSelectorData[0];
        console.log($scope.selectedSubnet);
      });
    };
    initComponents();
  });

});
