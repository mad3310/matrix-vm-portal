define(['controllers/app.controller'], function (controllerModule) {
  controllerModule.controller('VmIpEditModalCtrl', function (Config, HttpService,WidgetService,Utility,CurrentContext, $scope, $modalInstance,$timeout,$window, region,floatIp) {
    $scope.networkBandWidth=2;
    $scope.ipName = '';
    $scope.closeModal=function(){
      $modalInstance.dismiss('cancel');
    };
    $scope.editIP=function () {
      var data = {
        'region':region,
        floatingIpId:floatIp.id,
        name: $scope.ipName,
        bandWidth: $scope.networkBandWidth,
      };
      HttpService.doPost(Config.urls.floatIP_edit,data).success(function (data, status, headers, config) {
        if(data.result===1){
          $modalInstance.close(data);
          WidgetService.notifySuccess('修改公网IP成功');
          // $window.location.href = '/payment/'+data.data;
        }
        else{
          WidgetService.notifyError(data.msgs[0]||'修改公网IP失败');
        }
      });
    };
  });

});
