/**
 * Created by jiangfei on 2015/8/12.
 */
define(['controllers/app.controller'], function (controllerModule) {

  controllerModule.controller('VmKeypairCreateModalCtrl', function (Config, HttpService,WidgetService,Utility,ModelService, $scope, $modalInstance, region) {

    $scope.keypairName='';

    $scope.closeModal=function(){
      $modalInstance.dismiss('cancel');
    };

    $scope.createKeypair = function () {
      var createKeypairData = {
        region:region,
        name: $scope.keypairName
      };
      $scope.isFormSubmiting=true;
      HttpService.doGet(Config.urls.keypair_check, createKeypairData).then(function (data, status, headers, config) {
        if(data.result===1){
          $modalInstance.close(createKeypairData);
        }
        else{
          WidgetService.notifyError(data.msgs[0]||'密钥名称验证失败');
          $scope.isFormSubmiting=false;
        }
      });


    };

  });

});
