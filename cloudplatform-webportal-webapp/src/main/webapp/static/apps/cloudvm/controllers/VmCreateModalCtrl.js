/**
 * Created by jiangfei on 2015/8/12.
 */
define(['controllers/app.controller'], function (controllerModule) {

  controllerModule.controller('VmCreateModalCtrl', function (Config, HttpService,WidgetService, $scope, $modalInstance, items, region) {

    $scope.activeFlow = 1;
    $scope.vmName = '';
    $scope.vmImageList = [];
    $scope.selectedVmImage = null;
    $scope.vmCpuList = [];
    $scope.selectedVmCpu = null;
    $scope.vmRamList = [];
    $scope.selectedVmRam = null;
    $scope.vmDiskTypeList = [];
    $scope.selectedVmDiskType = null;
    $scope.dataDiskVolume = 50;
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
    $scope.selectVmDiskType = function (vmDiskType) {
      $scope.selectedVmDiskType = vmDiskType;
    };
    $scope.isSelectedVmDiskType = function (vmDiskType) {
      return $scope.selectedVmDiskType === vmDiskType;
    };
    $scope.createVm = function () {
      var data = {
        region:region,
        name: $scope.vmName,
        imageId: $scope.selectedVmImage.id,
        flavorId: selectedVmFlavor.id,
        volumeTypeId:$scope.selectedVmDiskType.id,
        volumeSize: $scope.dataDiskVolume,
        adminPass: $scope.vmSecurityPassword,
        bindFloatingIp: $scope.vmNetworkPublicIpModel === 'now',
        sharedNetworkId:selectedVmSharedNetwork.id,
        bandWidth:1,
        keyPairName:'',
        count:1,
        privateSubnetId:'',
        snapshotId:'',
      };
      HttpService.doPost(Config.urls.vm_create, data).success(function (data, status, headers, config) {
        if(data.result===1){
          $modalInstance.close(data);
          WidgetService.notifySuccess('创建云主机成功');
        }
        else{
          WidgetService.notifyError(data.msgs[0]||'创建云主机失败');
        }
      });
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
      selectedVmFlavor = null,
      selectedVmSharedNetwork=null;
    var initComponents = function () {
        initVmImageSelector();
        initVmCpuSelector();
        initVmDiskTypeSelector();
        setSelectedVmSharedNetworkId();
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
      },
      initVmDiskTypeSelector = function () {
        HttpService.doGet(Config.urls.vm_disk_type,{region:region}).success(function (data, status, headers, config) {
            $scope.vmDiskTypeList=data.data;
          $scope.selectedVmDiskType = $scope.vmDiskTypeList[0];
        });
      },
      setSelectedVmSharedNetworkId = function () {
        HttpService.doGet(Config.urls.vm_network_shared_list,{region:region}).success(function (data, status, headers, config) {
          selectedVmSharedNetwork=data.data[0];
        });
      };
    ;
    initComponents();
  });

});
