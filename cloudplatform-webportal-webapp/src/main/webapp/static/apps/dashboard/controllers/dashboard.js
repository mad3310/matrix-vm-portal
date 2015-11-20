define(['controllers/app.controller'], function (controllerModule) {
	controllerModule.controller('DashboardCtrl', ['$scope', '$modal', 'Config', 'HttpService','$http','WidgetService','CurrentContext',
	     function ($scope, $modal, Config, HttpService,$http,WidgetService,CurrentContext) {
			$scope.username = "";
			$scope.userimg='';
			$scope.message='';
			$scope.mobileStatus='';
			$scope.emailStatus='';
			$scope.userStatus='';
			$scope.remain='';
			$scope.billMes='';
			$scope.expander='';		
			$scope.expander.layout= "normal";
			$scope.ecsconsume="";
			$scope.diskconsume="";
			$scope.floatipconsume="";
			$scope.routeconsume="";
			$scope.service={}
			$scope.quotas={};
			$scope.expanderToggle = function(element){
				var _target=element.target||element.srcElement;
				var _items=$(_target).parent().parent().find('.operation-items');
				if($(_target).parent().parent().hasClass('zhankai')){
					$(_target).parent().parent().removeClass('zhankai');
					$(_target).text('更多');
				}else{
					$(_target).parent().parent().addClass('zhankai');
					$(_target).text('收起');
					_items.scrollTop();
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
				var serviceurl="/service?region="+CurrentContext.regionId;
				var consumeurl="/billing/product/daily/consume";
				var quotaurl="/quota?region="+CurrentContext.regionId;
				userinfo(usinfourl);
				unreadMes(messageurl);
				remain(remainurl);
				billing(billMesurl);
				operation(operationurl);
				// service(serviceurl);
				consume(consumeurl);
				quotas(quotaurl,serviceurl)
			}
			function userinfo(usinfourl){
				HttpService.doGet(usinfourl).success(function(data){
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
				})
			}
			function unreadMes(messageurl){
				HttpService.doGet(messageurl).success(function(data){
					if(data.result==0){//error
						WidgetService.notifyError('获取消息失败！')
					}else{
						$scope.message=data.data;
					}
				});
			}
			function remain(remainurl){
				HttpService.doGet(remainurl).success(function(data){
					if(data.result==0){//error
						WidgetService.notifyError('获取账户余额失败！')
					}else{
						$scope.remain=data.data;
					}
				});
			}
			function billing(billMesurl){
				HttpService.doGet(billMesurl).success(function(data){
					if(data.result==0){//error
						WidgetService.notifyError('获取账单信息失败！')
					}else{
						$scope.billMes=data.data;
					}
				});
			}
			function operation(operationurl){
				HttpService.doGet(operationurl).success(function(data){
					if(data.result==0){//error
						WidgetService.notifyError('获取操作信息失败！')
					}else{
						$scope.expander=data.data;
					}
				});
			}
			function service(serviceurl){
				return HttpService.doGet(serviceurl).success(function(data){
					if(data.result==0){//error
						WidgetService.notifyError('获取服务信息失败！')
					}else{
						$scope.service=data.data;
					}
				});
			}
			function consume(consumeurl){
				HttpService.doGet(consumeurl).success(function(data){
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
			function quotas(quotaurl,serviceurl){
				var serviceTemp=service(serviceurl);
				// console.log()
				serviceTemp.then(function(data){
					HttpService.doGet(quotaurl).success(function(data){
						if(data.result==0){//error
							WidgetService.notifyError('获取配额信息失败！')
						}else{
							$scope.quotas.CLOUDVM_BAND_WIDTH=data.data.CLOUDVM_BAND_WIDTH;
							$scope.quotas.bandwidthStyle={width:$scope.service.bandWidth/$scope.quotas.CLOUDVM_BAND_WIDTH*100+"%"}
							$scope.quotas.CLOUDVM_CPU=data.data.CLOUDVM_CPU;
							$scope.quotas.cpuStyle={width:$scope.service.cpu/$scope.quotas.CLOUDVM_CPU*100+"%"}
							$scope.quotas.CLOUDVM_FLOATING_IP=data.data.CLOUDVM_FLOATING_IP;
							$scope.quotas.floatIpStyle={width:$scope.service.floatingIp/$scope.quotas.CLOUDVM_FLOATING_IP*100+"%"}
							$scope.quotas.CLOUDVM_KEY_PAIR=data.data.CLOUDVM_KEY_PAIR;
							$scope.quotas.keyPairStyle={width:$scope.service.keyPair/$scope.quotas.CLOUDVM_KEY_PAIR*100+"%"}
							$scope.quotas.CLOUDVM_MEMORY=data.data.CLOUDVM_MEMORY;
							$scope.quotas.memoryStyle={width:$scope.service.memory/1024/$scope.quotas.CLOUDVM_MEMORY*100+"%"}
							$scope.quotas.CLOUDVM_NETWORK=data.data.CLOUDVM_NETWORK;
							$scope.quotas.vpcStyle={width:$scope.service.privateNetwork/$scope.quotas.CLOUDVM_NETWORK*100+"%"}
							$scope.quotas.CLOUDVM_ROUTER=data.data.CLOUDVM_ROUTER;
							$scope.quotas.routerStyle={width:$scope.service.router/$scope.quotas.CLOUDVM_ROUTER*100+"%"}
							$scope.quotas.CLOUDVM_SUBNET=data.data.CLOUDVM_SUBNET;
							$scope.quotas.subNetStyle={width:$scope.service.privateSubnet/$scope.quotas.CLOUDVM_SUBNET*100+"%"}
							$scope.quotas.CLOUDVM_VM=data.data.CLOUDVM_VM;
							$scope.quotas.ecsStyle={width:$scope.service.ecs/$scope.quotas.CLOUDVM_VM*100+"%"}
							$scope.quotas.CLOUDVM_VM_SNAPSHOT=data.data.CLOUDVM_VM_SNAPSHOT;
							$scope.quotas.vmSnapshotStyle={width:$scope.service.vmSnapshot/$scope.quotas.CLOUDVM_VM_SNAPSHOT*100+"%"}
							$scope.quotas.CLOUDVM_VOLUME=data.data.CLOUDVM_VOLUME;
							$scope.quotas.diskStyle={width:$scope.service.disk/$scope.quotas.CLOUDVM_VOLUME*100+"%"}
							$scope.quotas.CLOUDVM_VOLUME_SIZE=data.data.CLOUDVM_VOLUME_SIZE;
							$scope.quotas.volumeSizeStyle={width:$scope.service.volumeSize/$scope.quotas.CLOUDVM_VOLUME_SIZE*100+"%"}
							$scope.quotas.CLOUDVM_VOLUME_SNAPSHOT=data.data.CLOUDVM_VOLUME_SNAPSHOT;
							$scope.quotas.volumeSnapshotStyle={width:$scope.service.volumeSnapshot/$scope.quotas.CLOUDVM_VOLUME_SNAPSHOT*100+"%"}
						}
					});
				});
			}
			inite();
		}]);
});