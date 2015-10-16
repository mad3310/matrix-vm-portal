/**
 * Created by jiangfei on 2015/8/12.
 */
define(['controllers/app.controller'], function (controllerModule) {

  controllerModule.controller('VpcEditModalCtrl', function (Config, HttpService,WidgetService,Utility,CurrentContext, $scope, $modalInstance,$timeout,$window, vpcInfo) {
    $scope.vpcEdit = {
      name:vpcInfo.name
    };

    $scope.closeModal=function(){
      $modalInstance.dismiss('cancel');
    };
    $scope.editVpc = function () {
      if (!$scope.vpc_edit_form.$valid) return;
      var data = {
        region:vpcInfo.region,
        networkId:vpcInfo.vpcId,
        name: $scope.vpcEdit.name
      };
      HttpService.doPost(Config.urls.vpc_edit, data).success(function (data, status, headers, config) {
        if(data.result===1){
          $modalInstance.close({result:1});
          WidgetService.notifySuccess(data.msgs[0]||'修改VPC完成');
        }
        else{
          $modalInstance.dismiss('cancel');
          WidgetService.notifyError(data.msgs[0]||'修改VPC失败');
        }
      });
    };
  });

});
