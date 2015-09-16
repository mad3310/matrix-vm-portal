define(['controllers/app.controller'], function (controllerModule) {
	controllerModule.controller('DashboardCtrl', ['$scope', '$modal', 'Config', 'HttpService',
	     function ($scope, $modal, Config, HttpService) {
			$scope.username = "小金牛";
			$scope.expander = {};
			$scope.expander.layout= "normal";
			$scope.expanderToggle = function(){
				$scope.expander.layout === "top-expander"? $scope.expander.layout = "top-shrink":$scope.expander.layout="top-expander";
			}
		}]);
});