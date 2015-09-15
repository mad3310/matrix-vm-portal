/**
 * Created by jiangfei on 2015/8/12.
 */
define(['controllers/app.controller'], function (controllerModule) {
  controllerModule.controller('VirtualMachineCrtl', ['$scope', '$modal', 'Config', 'HttpService',
    function ($scope, $modal, Config, HttpService) {
      $scope.searchVmName = '';

      $scope.regionList = [];
      $scope.selectedRegion = {};

      $scope.vmList = [];

      $scope.currentPage = 1;
      $scope.totalItems = 0;
      $scope.pageSize = 15;
      $scope.onPageChange = function () {
        refreshVmList();
      };

      $scope.doSearch = function () {
        refreshVmList();
      };

      $scope.restartVm=function(size){
        var modalInstance = $modal.open({
          templateUrl: 'ConfirmModalTpl',
          controller: 'ConfirmModalCtrl',
          size: size,
          resolve: {
            message:function(){
              return  '确定要启动云主机test吗？';
            },
            title:function(){
              return  '启动云主机';
            }
          }
        });

        modalInstance.result.then(function (selectedItem) {
          $scope.selected = selectedItem;
        }, function () {
        });
      };

      $scope.startMcluster = function (mcluster) {
        HttpService.doPost(Config.url.mcluster_start, {mclusterId: mcluster.id}).success(function (data, status, headers, config) {
          if (data.result == 1) {
            toaster.pop('success', null, '启动d成功', 2000, 'trustedHtml');
          }
          else {
            toaster.pop('error', null, '启动失败', 2000, 'trustedHtml');
          }
        });
      };
      $scope.stopMcluster = function (mcluster) {
        HttpService.doPost(Config.url.mcluster_stop, {mclusterId: mcluster.id}).success(function (data, status, headers, config) {
          if (data.result == 1) {
            toaster.pop('success', null, '停止成功', 2000, 'trustedHtml');
          }
          else {
            toaster.pop('error', null, '停止失败', 2000, 'trustedHtml');
          }
        });
      };

      $scope.deleteMcluster = function (mcluster) {
        return mcluster;
      };
      $scope.checkVm = function (vm) {
        vm.checked = vm.checked === true ? false : true;
      };

      $scope.items = ['item1', 'item2', 'item3'];
      $scope.openVmCreateModal = function (size) {
        var modalInstance = $modal.open({
          animation: $scope.animationsEnabled,
          templateUrl: 'VmCreateModalTpl',
          controller: 'VmCreateModalCtrl',
          size: size,
          resolve: {
            items: function () {
              return $scope.items;
            },
            region: function () {
              return $scope.selectedRegion.selected;
            }
          }
        });

        modalInstance.result.then(function (selectedItem) {
          $scope.selected = selectedItem;
        }, function () {
        });
      };

      var refreshVmList = function () {
          var region = $scope.selectedRegion.selected,
            queryParams = {
              name: $scope.searchVmName,
              currentPage: $scope.currentPage,
              recordsPerPage: $scope.pageSize
            };
          HttpService.doGet(Config.urls.vm_list.replace('{region}', region), {
            currentPage: 1,
            recordsPerPage: 3
          }).success(function (data, status, headers, config) {
            $scope.vmList = data.data.data;
            $scope.totalItems = data.data.totalRecords;
          });
        },
        initPageComponents = function () {
          HttpService.doGet(Config.urls.vm_regions).success(function (data, status, headers, config) {
            $scope.regionList = data.data;
            $scope.selectedRegion.selected = $scope.regionList[0];
            refreshVmList();
          });
        };

      initPageComponents();

    }
  ]);

  controllerModule.controller('VmCreateModalCtrl', function (Config, HttpService, $scope, $modalInstance, items, region) {

    $scope.tabsActive = {1: true, 2: false, 3: false, 4: false};
    $scope.vmName = '';
    $scope.vmImageList = [];
    $scope.selectedVmImage = null;
    $scope.vmCpuList = [];
    $scope.selectedVmCpu = null;
    $scope.vmRamList = [];
    $scope.selectedVmRam = null;
    $scope.dataDiskVolume = 10;
    $scope.vmNetworkType = 'primary';
    $scope.vmNetworkPublicIpModel = 'now';
    $scope.networkBandWidth = 2;
    $scope.vmNetworkSubnet = 'subnet1';
    $scope.vmSecurityType = 'key';
    $scope.vmSecurityKey = 'key1';
    $scope.vmSecurityPassword = '';
    $scope.vmSecurityPasswordConfirm = '';
    $scope.vmCount = '';


    $scope.selectVmImage = function (vmImage) {
      $scope.selectedVmImage = vmImage;
    };
    $scope.isSelectedVmImage = function (vmImage) {
      return $scope.selectedVmImage === vmImage;
    };
    $scope.selectVmCpu = function (vmCpu) {
      $scope.selectedVmCpu = vmCpu;
    };
    $scope.isSelectedVmCpu = function (vmCpu) {
      return $scope.selectedVmCpu === vmCpu;
    };
    $scope.selectVmRam = function (vmRam) {
      $scope.selectedVmRam = vmRam;
    };
    $scope.isSelectedVmRam = function (vmRam) {
      return $scope.selectedVmRam === vmRam;
    };
    $scope.createVm = function () {
      var data = {
        name: $scope.vmName,
        imageId: $scope.selectedVmImage.id,
        flavorId: selectedVmFlavor.id,
        networkIds: null,
        adminPass: $scope.vmSecurityPassword,
        publish: $scope.vmNetworkPublicIpModel === 'now',
        volumeSizes: [$scope.dataDiskVolume]
      };
      HttpService.doPost(Config.urls.vm_create.replace('{region}', region), data).success(function (data, status, headers, config) {
        return data;
      });
      $modalInstance.close($scope.selected.item);
    };
    $scope.closeModal = function () {
      $modalInstance.dismiss('cancel');
    };
    $scope.$watch('selectedVmCpu', function (value) {
      if (value != null) {
        initVmRamSelector();
      }
    });
    $scope.$watch('selectedVmRam', function (value) {
      if (value != null) {
        setSelectedVmFlavor();
      }
    });

    var flavorGroupData = null,
      selectedVmFlavor = null;
    var initComponents = function () {
        initVmImageSelector();
        initVmCpuSelector();
      },
      initVmImageSelector = function () {
        HttpService.doGet(Config.urls.image_list.replace('{region}', region)).success(function (data, status, headers, config) {
          $scope.vmImageList = data.data;
          $scope.selectedVmImage = $scope.vmImageList[0];
        });
      },
      initVmCpuSelector = function () {
        HttpService.doGet(Config.urls.flavor_group_data.replace('{region}', region)).success(function (data, status, headers, config) {
          flavorGroupData = data.data;
          for (var cpu in flavorGroupData) {
            $scope.vmCpuList.push(cpu);
          }
          $scope.selectedVmCpu = $scope.vmCpuList[0];
        });
      },
      initVmRamSelector = function () {
        $scope.vmRamList.splice(0, $scope.vmRamList.length);
        for (var ram in flavorGroupData[$scope.selectedVmCpu]) {
          $scope.vmRamList.push(ram);
        }
        $scope.selectedVmRam = $scope.vmRamList[0];
      },
      setSelectedVmFlavor = function () {
        for (var disk in flavorGroupData[$scope.selectedVmCpu][$scope.selectedVmRam]) {//跟运维规定系统盘都为40G，cpu,ram可唯一确定flavor,默认选择第一个硬盘，
          selectedVmFlavor = flavorGroupData[$scope.selectedVmCpu][$scope.selectedVmRam][disk];
          break;
        }
      };
    ;
    initComponents();
  });

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
