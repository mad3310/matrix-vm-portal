/**
 * Created by jiangfei on 2015/8/12.
 */
define(['controllers/app.controller'], function (controllerModule) {
    controllerModule.controller('VmVpcCtrl', ['$scope', '$interval', '$modal', 'Config', 'HttpService', 'WidgetService', 'CurrentContext',
        function ($scope, $interval, $modal, Config, HttpService, WidgetService, CurrentContext) {
            $scope.tabShow = 'subnet';
            $scope.vpcList = [];
            $scope.subnetList = [];
            $scope.vpc = $scope.subnet = {
                currentPage: 1,
                totalItems: 0,
                pageSize: 10
            };
            $scope.vpc.onPageChange = function(){
                refreshVpcList();
            }
            $scope.subnet.onPageChange = function(){
                refreshSubnetList();
            }

            $scope.openVpcCreateModal = function (size) {
                var modalInstance = $modal.open({
                    animation: $scope.animationsEnabled,
                    templateUrl: 'VpcCreateModalTpl',
                    controller: 'VpcCreateModalCtrl',
                    size: size,
                    backdrop: 'static',
                    keyboard: false,
                    resolve: {
                        region: function () {
                            return CurrentContext.regionId;
                        }
                    }
                });
                modalInstance.result.then(function (resultData) {
                    if (resultData && resultData.result === 1) {
                        refreshVpcList();
                    }
                }, function () {
                });
            };
            $scope.openSubnetCreateModal = function (size) {
                var modalInstance = $modal.open({
                    animation: $scope.animationsEnabled,
                    templateUrl: 'SubnetCreateModalTpl',
                    controller: 'SubnetCreateModalCtrl',
                    size: size,
                    backdrop: 'static',
                    keyboard: false,
                    resolve: {
                        subnetInfo: function () {
                            return {
                                region:CurrentContext.regionId,

                            };
                        }
                    }
                });
                modalInstance.result.then(function (resultData) {
                    if (resultData && resultData.result === 1) {
                        refreshSubnetList();
                    }
                }, function () {
                });
            };
            $scope.editVpc = function () {
                var checkedVpcs = getCheckedVpc();
                if (checkedVpcs.length !== 1) {
                    WidgetService.notifyWarning('请选中一个VPC');
                    return;
                }
                openVmVpcEditModal('500', {
                    region: checkedVpcs[0].region,
                    vpcId: checkedVpcs[0].id,
                    name: checkedVpcs[0].name
                });
            };

            $scope.deleteVpc = function () {
                var checkedVpcs = getCheckedVpc();
                if (checkedVpcs.length !== 1) {
                    WidgetService.notifyWarning('请选中一个VPC');
                    return;
                }
                console.log(checkedVpcs);
                var data = {
                    region: checkedVpcs[0].region,
                    networkId: checkedVpcs[0].id
                };
                var modalInstance = WidgetService.openConfirmModal('删除VPC', '确定要删除VPC（' + checkedVpcs[0].name + '）吗？');
                modalInstance.result.then(function (resultData) {
                    if (!resultData) return resultData;
                    WidgetService.notifyInfo('VPC删除执行中...');
                    checkedVpcs[0].status = 'DELETEING';
                    HttpService.doPost(Config.urls.vpc_delete, data).success(function (data, status, headers, config) {
                        if (data.result === 1) {
                            checkedVpcs[0].status = 'DELETED';
                            modalInstance.close(data);
                            WidgetService.notifySuccess('删除VPC成功');
                            refreshVpcList();
                        }
                        else {
                            WidgetService.notifyError(data.msgs[0] || '删除VPC失败');
                        }
                    });
                }, function () {
                });
            };

            $scope.isAllVpcChecked = function () {
                var unCheckedVpcs = $scope.vpcList.filter(function (vpc) {
                    return vpc.checked === false || vpc.checked === undefined;
                });
                return unCheckedVpcs.length == 0;
            };
            $scope.checkAllVpc = function () {
                if ($scope.isAllVpcChecked()) {
                    $scope.vpcList.forEach(function (vpc) {
                        vpc.checked = false;
                    });
                }
                else {
                    $scope.vpcList.forEach(function (vpc) {
                        vpc.checked = true;
                    });
                }
            };
            $scope.checkVpc = function (vpc) {
                vpc.checked = vpc.checked === true ? false : true;
            };

            $scope.isAllSubnetChecked = function () {
                var unCheckedSubnets = $scope.subnetList.filter(function (subnet) {
                    return subnet.checked === false || subnet.checked === undefined;
                });
                return unCheckedSubnets.length == 0;
            };
            $scope.checkAllSubnet = function () {
                if ($scope.isAllSubnetChecked()) {
                    $scope.subnetList.forEach(function (subnet) {
                        subnet.checked = false;
                    });
                }
                else {
                    $scope.subnetList.forEach(function (subnet) {
                        subnet.checked = true;
                    });
                }

            };
            $scope.checkSubnet = function (subnet) {
                subnet.checked = subnet.checked === true ? false : true;
            };

            var refreshVpcList = function () {
                    var queryParams = {
                        region: CurrentContext.regionId,
                        name: '',
                        currentPage: $scope.vpc.currentPage,
                        recordsPerPage: $scope.vpc.pageSize
                    };
                    WidgetService.showSpin();
                    HttpService.doGet(Config.urls.vpc_list, queryParams).success(function (data, status, headers, config) {
                        WidgetService.hideSpin();
                        $scope.vpcList = data.data.data;
                        $scope.vpc.totalItems = data.data.totalRecords;

                    });
                },
                refreshSubnetList = function () {
                    var queryParams = {
                        region: CurrentContext.regionId,
                        name: '',
                        currentPage: $scope.subnet.currentPage,
                        recordsPerPage: $scope.subnet.pageSize
                    };
                    WidgetService.showSpin();
                    HttpService.doGet(Config.urls.subnet_list, queryParams).success(function (data, status, headers, config) {
                        WidgetService.hideSpin();
                        $scope.subnetList = data.data.data;
                        $scope.subnet.totalItems = data.data.totalRecords;

                    });
                },
                getCheckedVpc = function () {
                    return $scope.vpcList.filter(function (item) {
                        return item.checked === true;
                    });
                },
                getCheckedSubnet = function () {
                    return $scope.subnetList.filter(function (item) {
                        return item.checked === true;
                    });
                },
                openVmVpcEditModal = function (size, data) {
                    var modalInstance = $modal.open({
                        animation: $scope.animationsEnabled,
                        templateUrl: 'VpcEditModalTpl',
                        controller: 'VpcEditModalCtrl',
                        size: size,
                        backdrop: 'static',
                        keyboard: false,
                        resolve: {
                            vpcInfo: function () {
                                return data;
                            }
                        }
                    });
                    modalInstance.result.then(function (resultData) {
                        if (resultData && resultData.result === 1) {
                            refreshVpcList();
                        }
                    }, function () {
                    });
                };

            refreshVpcList();
            refreshSubnetList();
        }

    ]);
});
