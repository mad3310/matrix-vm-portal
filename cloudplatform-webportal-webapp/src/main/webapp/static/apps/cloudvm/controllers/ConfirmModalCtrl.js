/**
 * Created by jiangfei on 2015/8/12.
 */
define(['controllers/app.controller'], function (controllerModule) {

  controllerModule.controller('ConfirmModalCtrl', function ( $scope, $modalInstance, message,title) {
    $scope.confirmMessage=message;
    $scope.title=title;
    $scope.closeModal=function(){
      $modalInstance.dismiss('cancel');
    };
    $scope.ok = function () {
      $modalInstance.close(true);
    };
    $scope.cancel = function () {
      $modalInstance.dismiss('cancel');
    };
  });
});
