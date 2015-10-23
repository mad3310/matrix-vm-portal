define(['controllers/app.controller'], function (controllerModule) {
	controllerModule.controller('DashboardCtrl', ['$scope', '$modal', 'Config', 'HttpService','$http','WidgetService',
	     function ($scope, $modal, Config, HttpService,$http,WidgetService) {
			$scope.username = "";
			$scope.userimg='';
			$scope.message='';
			$scope.mobileStatus='';
			$scope.emailStatus='';
			$scope.userStatus='';
			$scope.remain='';
			$scope.billMes='';
			$scope.expander='';
			$scope.ecs='';
			$scope.ecs_sleep='';
			$scope.expander.layout= "normal";
			$scope.ecsconsume="";
			$scope.diskconsume="";
			$scope.floatipconsume="";
			$scope.routeconsume="";
			$scope.expanderToggle = function(element){
				var _target=element.target||element.srcElement;
				if($(_target).parent().parent().hasClass('zhankai')){
					$(_target).parent().parent().removeClass('zhankai');
					$(_target).text('更多');
				}else{
					$(_target).parent().parent().addClass('zhankai');
					$(_target).text('收起');
					$('.operation-items').scrollTop();
				}
				$scope.expander.layout === "top-expander"? $scope.expander.layout = "top-shrink":$scope.expander.layout="top-expander";
			}
			var inite=function(){
				var userid=$('#userId').val();
				var usinfourl="/user/"+userid;
				var messageurl="/user/message/un/"+userid;
				var remainurl="/userAccount/balance/"+userid;
				var billMesurl="/userAccount/order/un/"+userid;
				var operationurl="/operate";
				var serviceurl="/service";
				var consumeurl="/billing/product/daily/consume";
				$http.get(usinfourl).success(function(data){
					if(data.result==0){//error
						WidgetService.notifyError('获取用户信息失败！')
					}else{
						var _data=data.data;
						$scope.username=_data.contacts;
						// $scope.userimg=_data.userAvatar;
						if(_data.userAvatar){
							$scope.userimg=_data.userAvatar;
							$('.account-icon').attr('src',_data.userAvatar);
						}else{
							$scope.userimg="../static/images/dashboard/account.png"
						}
						$scope.mobileStatus=_data.mobileStatus;
						$scope.emailStatus=_data.emailStatus;
						$scope.userStatus=_data.examineStatus;
					}
				});
				$http.get(messageurl).success(function(data){
					if(data.result==0){//error
						WidgetService.notifyError('获取消息失败！')
					}else{
						$scope.message=data.data;
					}
				});
				$http.get(remainurl).success(function(data){
					if(data.result==0){//error
						WidgetService.notifyError('获取账户余额失败！')
					}else{
						$scope.remain=data.data;
					}
				});
				$http.get(billMesurl).success(function(data){
					if(data.result==0){//error
						WidgetService.notifyError('获取账单信息失败！')
					}else{
						$scope.billMes=data.data;
					}
				});
				$http.get(operationurl).success(function(data){
					if(data.result==0){//error
						WidgetService.notifyError('获取操作信息失败！')
					}else{
						$scope.expander=data.data;
					}
				});
				$http.get(serviceurl).success(function(data){
					if(data.result==0){//error
						WidgetService.notifyError('获取服务信息失败！')
					}else{
						$scope.ecs=data.data.ecs;
						$scope.ecs_sleep=data.data['ecs-sleep'];
					}
				});
				$http.get(consumeurl).success(function(data){
					if(data.result==0){//error
						WidgetService.notifyError('获取服务信息失败！')
					}else{
						$scope.ecsconsume=data.data["2"];
						$scope.diskconsume=data.data["3"];
						$scope.floatipconsume=data.data["4"];
						$scope.routeconsume=data.data["5"];
					}
				});
			}
			inite();
		}]);
});