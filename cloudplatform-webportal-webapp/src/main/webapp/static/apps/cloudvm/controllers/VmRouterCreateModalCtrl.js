/**
 * Created by jiangfei on 2015/8/12.
 */
define(['controllers/app.controller'], function (controllerModule) {

  controllerModule.controller('VmRouterCreateModalCtrl', function (Config, HttpService,WidgetService,Utility,CurrentContext, $scope, $modalInstance,$timeout,$window, region) {

    $scope.routerName = '';
    $scope.enablePublicNetworkGateway = 'true';
    $scope.publicNetworkId = '';
    $scope.allRouteBuyPeriods = Config.allBuyPeriods;
    $scope.routeBuyPeriod = $scope.allRouteBuyPeriods[0];

    $scope.closeModal=function(){
      $modalInstance.dismiss('cancel');
    };
    $scope.createRouter = function () {
      if (!$scope.vm_router_create_form.$valid) return;
      var data = {
        region:region,
        name: $scope.routerName,
        enablePublicNetworkGateway: $scope.enablePublicNetworkGateway,
        publicNetworkId:$scope.publicNetworkId
      };
      HttpService.doPost(Config.urls.router_create, data).success(function (data, status, headers, config) {
        if(data.result===1){
          /*$modalInstance.close(data);
          $window.location.href = '/payment/'+data.data;*/
          $modalInstance.close({result:1});
          WidgetService.notifySuccess(data.msgs[0]||'创建路由器完成');
        }
        else{
          $modalInstance.dismiss('cancel');
          WidgetService.notifyError(data.msgs[0]||'创建路由器失败');
        }
      });
    };
    $scope.$watch('routeBuyPeriod', function (value) {
      if (value) {
        setRoutePrice();
      }
    });

    var initComponents = function () {
        initRouterTypeSelector();
      },
      initRouterTypeSelector = function () {
        HttpService.doGet(Config.urls.network_public_list,{region:region}).success(function (data, status, headers, config) {
          $scope.publicNetworkId = data.data[0].id;
        });
      },
      setRoutePrice=function(){
        var data={
          region: region,
          order_time: $scope.routeBuyPeriod.toString(),
          order_num: '1',
          os_router:'router'
        };
        HttpService.doPost(Config.urls.route_calculate_price,data).success(function (data, status, headers, config) {
          if(data.result===1){
            $scope.totalPrice=data.data;
          }
          else{
            WidgetService.notifyError(data.msgs[0]||'计算总价失败');
          }
        });
      };

    initComponents();

  });

});
