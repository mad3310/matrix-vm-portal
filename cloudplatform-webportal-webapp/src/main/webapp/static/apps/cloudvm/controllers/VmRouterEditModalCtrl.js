/**
 * Created by jiangfei on 2015/8/12.
 */
define(['controllers/app.controller'], function (controllerModule) {

  controllerModule.controller('VmRouterEditModalCtrl', function (Config, HttpService,WidgetService,Utility,CurrentContext, $scope, $modalInstance,$timeout,$window, routerInfo) {
    $scope.editRouterName = routerInfo.routerName;
    $scope.editEnablePublicNetworkGateway = routerInfo.publicNetworkGatewayEnable;

    if(routerInfo.publicNetworkGatewayEnable===false){
      getPublicNetworkId();
    }

    $scope.closeModal=function(){
      $modalInstance.dismiss('cancel');
    };
    $scope.editRouter = function () {
      if (!$scope.vm_router_edit_form.$valid) return;
      var data = {
        region:routerInfo.region,
        routerId:routerInfo.routerId,
        name: $scope.editRouterName,
        enablePublicNetworkGateway: $scope.editEnablePublicNetworkGateway,
        publicNetworkId:routerInfo.publicNetworkId
      };
      HttpService.doPost(Config.urls.router_edit, data).success(function (data, status, headers, config) {
        if(data.result===1){
          $modalInstance.close({result:1});
          WidgetService.notifySuccess(data.msgs[0]||'修改路由器完成');
        }
        else{
          $modalInstance.dismiss('cancel');
          WidgetService.notifyError(data.msgs[0]||'修改路由器失败');
        }
      });
    };

    function getPublicNetworkId() {
      HttpService.doGet(Config.urls.network_public_list,{region:routerInfo.region}).success(function (data, status, headers, config) {
        routerInfo.publicNetworkId = data.data[0].id;
      });
    };


  });

});
