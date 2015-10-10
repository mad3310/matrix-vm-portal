define(['controllers/app.controller'], function (controllerModule) {
	controllerModule.controller('VmIpBindVmModalCtrl', function (Config, HttpService,WidgetService,ModelService,Utility,CurrentContext, $scope, $modalInstance,$timeout,$window, region,bindfloatIp,avalibleVm) {
		$scope.floatIpName=bindfloatIp.name;
		$scope.floatIp=bindfloatIp.ipAddress;
		$scope.vmListSelectorData=avalibleVm.map(function(vm){
            return new ModelService.SelectModel(vm.name,vm.id);
        });
		$scope.selectedVm=$scope.vmListSelectorData[0];
		$scope.closeModal=function(){
	      $modalInstance.dismiss('cancel');
	    };
	    $scope.IpbingVm=function(){
	    	var data={
	    		'region':region,
		        vmId:$scope.selectedVm.value,
		        floatingIpId:bindfloatIp.id
	    	}
	    	HttpService.doPost(Config.urls.floatIp_bindVm,data).success(function(data){
	    		if(data.result==0){//error
					WidgetService.notifyError(data.msgs[0]||'绑定云主机出错');
	    		}else{
	    			$modalInstance.close(data);
          			WidgetService.notifySuccess('绑定云主机成功');
	    		}

	    	});
	    }
	});
})