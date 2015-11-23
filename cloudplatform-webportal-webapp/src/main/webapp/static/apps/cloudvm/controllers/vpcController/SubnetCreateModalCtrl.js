/**
 * Created by jiangfei on 2015/8/12.
 */
define(['controllers/app.controller'], function (controllerModule) {

    controllerModule.controller('SubnetCreateModalCtrl', function (Config, HttpService, WidgetService, Utility, ModelService, CurrentContext, $scope, $modalInstance, $timeout, $window, subnetInfo) {

        $scope.subnetCreate = {
            name: ''
        };

        $scope.closeModal = function () {
            $modalInstance.dismiss('cancel');
        };
        $scope.createSubnet = function () {
            if (!$scope.subnet_create_form.$valid) return;
            var data = {
                region:subnetInfo.region,
                networkId:$scope.selectedVpc.value,
                name:$scope.subnetCreate.name,
                cidr:$scope.selectedCidr.value,
                autoGatewayIp:'false',
                enableDhcp:'true',
                gatewayIp:$scope.selectedCidr.relatedOption.gatewayIp
            };
            $scope.isFormSubmiting=true;
            HttpService.doPost(Config.urls.subnet_create, data).success(function (data, status, headers, config) {
                if (data.result === 1) {
                    $modalInstance.close({result: 1});
                    WidgetService.notifySuccess(data.msgs[0] || '创建子网完成');
                }
                else {
                    $scope.isFormSubmiting=false;
                    WidgetService.notifyError(data.msgs[0] || '创建子网失败');
                }
            });
        };
        var initComponents = function () {
                initVpcSelector();
                initSelector();
            },
            initVpcSelector = function () {
                HttpService.doGet(Config.urls.vpc_list, {region: subnetInfo.region}).success(function (data, status, headers, config) {
                    $scope.vpcList = data.data.data;
                    $scope.vpcListSelectorData = $scope.vpcList.map(function (vpc) {
                        return new ModelService.SelectModel(vpc.name, vpc.id);
                    });
                    $scope.selectedVpc = $scope.vpcListSelectorData[0];
                });
            },
            initSelector = function () {
                $scope.cidrs = [
                    {
                        cidr: '192.168.1.0/24',
                        gatewayIp: '192.168.1.1'
                    },
                    {
                        cidr: '192.168.0.0/24',
                        gatewayIp: '192.168.0.1'
                    }
                ];
                $scope.cidrListSelectorData = $scope.cidrs.map(function (cidr) {
                    return new ModelService.SelectModel(cidr.cidr, cidr.cidr,{gatewayIp: cidr.gatewayIp});
                });
                $scope.selectedCidr = $scope.cidrListSelectorData[0];
            };

        initComponents();
    });
    controllerModule.controller('SubnetCreateWithVpcModalCtrl', function (Config, HttpService, WidgetService, Utility, ModelService, CurrentContext, $scope, $modalInstance, $timeout, $window, subnetInfo) {

        $scope.subnetCreate = {
            name: '',
            vpcForSubnet:subnetInfo.vpcForSubnet
        };

        $scope.closeModal = function () {
            $modalInstance.dismiss('cancel');
        };
        $scope.createSubnet = function () {
            if (!$scope.subnet_create_with_vpc_form.$valid) return;
            var data = {
                region:subnetInfo.region,
                networkId:subnetInfo.vpcForSubnet.vpcId,
                name:$scope.subnetCreate.name,
                cidr:$scope.selectedCidr.value,
                autoGatewayIp:'false',
                enableDhcp:'true',
                gatewayIp:$scope.selectedCidr.relatedOption.gatewayIp
            };
            $scope.isFormSubmiting=true;
            HttpService.doPost(Config.urls.subnet_create, data).success(function (data, status, headers, config) {
                if (data.result === 1) {
                    $modalInstance.close({result: 1});
                    WidgetService.notifySuccess(data.msgs[0] || '创建子网完成');
                }
                else {
                    $scope.isFormSubmiting=false;
                    WidgetService.notifyError(data.msgs[0] || '创建子网失败');
                }
            });
        };
        var initComponents = function () {
                initSelector();
            },
            initSelector = function () {
                $scope.cidrs = [
                    {
                        cidr: '192.168.1.0/24',
                        gatewayIp: '192.168.1.1'
                    },
                    {
                        cidr: '192.168.0.0/24',
                        gatewayIp: '192.168.0.1'
                    }
                ];
                $scope.cidrListSelectorData = $scope.cidrs.map(function (cidr) {
                    return new ModelService.SelectModel(cidr.cidr, cidr.cidr,{gatewayIp: cidr.gatewayIp});
                });
                $scope.selectedCidr = $scope.cidrListSelectorData[0];
            };

        initComponents();
    });

});
