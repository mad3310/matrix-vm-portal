define(['controllers/app.controller'], function (controllerModule) {
	controllerModule.controller('VmIpBindVmModalCtrl', function (Config, HttpService,WidgetService,Utility,CurrentContext, $scope, $modalInstance,$timeout,$window, region) {
		$scope.floatIp='';
		$scope.vmList=[];
		$scope.closeModal=function(){
	      $modalInstance.dismiss('cancel');
	    };
	});
})