/**
 * Created by jiangfei on 2015/8/12.
 */
define(['controllers/app.controller'], function (controllerModule) {

  controllerModule.controller('VmCreateModalCtrl', function (Config, HttpService,WidgetService,Utility,CurrentContext,ModelService, $scope, $modalInstance,$timeout,$window, region,vmSnapshot) {

    $scope.isDesignatedVmSnapshot = vmSnapshot?true:false;
    $scope.activeFlow = 1;
    $scope.vmName = '';
    $scope.imageActiveTab = $scope.isDesignatedVmSnapshot? 'snapshot': 'image';
    $scope.vmImageList = [];
    $scope.selectedVmImage = null;
    $scope.vmSnapshotList = [];
    $scope.selectedVmSnapshot = null;
    $scope.vmCpuList = [];
    $scope.selectedVmCpu = null;
    $scope.vmRamList = [];
    $scope.selectedVmRam = null;
    $scope.vmDiskTypeList = [];
    $scope.selectedVmDiskType = null;
    $scope.dataDiskVolume = 10;
    $scope.vmNetworkType = 'primary';
    $scope.vmNetworkPublicIpModel = 'now';
    $scope.networkBandWidth = 2;
    $scope.vmNetworkSubnetList = [];
    $scope.vmNetworkSubnetSelectorData=[];
    $scope.selectedVmNetworkSubnet = null;
    $scope.vmSecurityType = 'key';
    $scope.vmSecurityKey = 'key1';
    $scope.vmSecurityPassword = '';
    //$scope.vmSecurityPasswordConfirm = '';
    $scope.allVmBuyPeriods = Config.allBuyPeriods;
    $scope.vmBuyPeriod = $scope.allVmBuyPeriods[0];
    $scope.vmCount = 1;
    $scope.vmTotalPrice = '';

    $scope.closeModal=function(){
      $modalInstance.dismiss('cancel');
    };
    $scope.hackRzSlider= Utility.getRzSliderHack($scope);
    $scope.selectVmImage = function (vmImage) {
      $scope.selectedVmImage = vmImage;
    };
    $scope.isSelectedVmImage = function (vmImage) {
      return $scope.selectedVmImage === vmImage;
    };
    $scope.selectVmSnapshot = function (vmImage) {
      $scope.selectedVmSnapshot = vmImage;
    };
    $scope.isSelectedVmSnapshot = function (vmImage) {
      return $scope.selectedVmSnapshot === vmImage;
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
    $scope.selectVmBuyPeriod = function (vmBuyPeriod) {
      $scope.vmBuyPeriod = vmBuyPeriod;
    };
    $scope.isSelectedVmBuyPeriod = function (vmBuyPeriod) {
      return $scope.vmBuyPeriod === vmBuyPeriod;
    };
    $scope.createVm = function () {
      var data = {
        region:region,
        name: $scope.vmName,
        imageId:$scope.imageActiveTab === 'image'? $scope.selectedVmImage.id:'',
        flavorId: selectedVmFlavor.id,
        volumeTypeId:$scope.selectedVmDiskType.id,
        volumeSize: $scope.dataDiskVolume,
        adminPass: $scope.vmSecurityPassword,
        bindFloatingIp: $scope.vmNetworkPublicIpModel === 'now',
        sharedNetworkId:$scope.vmNetworkType=='primary'? selectedVmSharedNetwork.id:'',
        bandWidth:$scope.networkBandWidth,
        keyPairName:'',
        count:$scope.vmCount,
        privateSubnetId:$scope.vmNetworkType=='private'? $scope.selectedVmNetworkSubnet.value:'',//privateSubnetId和sharedNetworkId是否为空来标识选择的基础网络还是私有网络
        snapshotId:$scope.imageActiveTab === 'snapshot'? $scope.selectedVmSnapshot.id:'',
        order_time: $scope.vmBuyPeriod.toString(),
      };
      $scope.isOrderSubmiting=true;
      HttpService.doPost(Config.urls.vm_buy, {paramsData:JSON.stringify(data),displayData:buildDisplayData()}).success(function (data, status, headers, config) {
        if(data.result===1){
          $modalInstance.close(data);
          $window.location.href = '/payment/'+data.data;
        }
        else{
          WidgetService.notifyError(data.msgs[0]||'创建云主机失败');
          $scope.isOrderSubmiting=false;
        }
      });
    };
    $scope.closeModal = function () {
      $modalInstance.dismiss('cancel');
    };

    $scope.$watch('vmName', function (value) {
      if (value && value.length>15) {
        $scope.vmName='';
      }
    });
    $scope.$watch('vmSecurityPassword', function (value) {
      if (value && value.length>15) {
        $scope.vmSecurityPassword='';
      }
    });
    $scope.$watch('selectedVmCpu', function (value) {
      if (value != null) {
        initVmRamSelector();
      }
    });
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

    $scope.$watch(function(){
      return [$scope.vmNetworkType,$scope.vmNetworkPublicIpModel].join('_');
    }, function () {
        if($scope.vmNetworkType == 'primary' && $scope.vmNetworkPublicIpModel == 'now'){
          $scope.networkBandWidth = 2;
        }
        else{
          $scope.networkBandWidth = 0;
        }
    });

    $scope.$watch(function(){
      return [$scope.selectedVmCpu,
        $scope.selectedVmRam,
        ($scope.selectedVmDiskType &&  $scope.selectedVmDiskType.name) || '',
        $scope.dataDiskVolume,
        $scope.networkBandWidth,
        $scope.vmCount,
        $scope.vmBuyPeriod].join('_');
    }, function (value) {
      if ($scope.selectedVmCpu &&$scope.selectedVmRam && $scope.selectedVmDiskType && $scope.dataDiskVolume) {
        setVmPrice();
      }
    });

    var flavorGroupData = null,
      selectedVmFlavor = null,
      selectedVmSharedNetwork=null,
      calculatePriceData= null;
    var initComponents = function () {
        initVmImageSelector();
        initVmSnapshotSelector();
        initVmCpuSelector();
        initVmDiskTypeSelector();
        setSelectedVmSharedNetworkId();
        initVmNetworkSubnetSelector();
      },
      initVmImageSelector = function () {
        if($scope.isDesignatedVmSnapshot) return;
        HttpService.doGet(Config.urls.image_list, {region:region, name: '', currentPage: '', recordsPerPage: ''}).success(function (data, status, headers, config) {
          $scope.vmImageList = data.data.data;
          $scope.selectedVmImage = $scope.vmImageList[0];
        });
      },
      initVmSnapshotSelector = function () {
        if($scope.isDesignatedVmSnapshot){
          $scope.vmSnapshotList.push(vmSnapshot);
          $scope.selectedVmSnapshot = $scope.vmSnapshotList[0];
        }
        else{
          HttpService.doGet(Config.urls.snapshot_vm_list,{region:region, name: '', currentPage: '', recordsPerPage: ''}).success(function (data, status, headers, config) {
            $scope.vmSnapshotList = data.data.data;
            $scope.selectedVmSnapshot = $scope.vmSnapshotList[0];
          });
        }
      },
      initVmNetworkSubnetSelector = function () {
          HttpService.doGet(Config.urls.subnet_list,{region:region, name: '', currentPage: '', recordsPerPage: ''}).success(function (data, status, headers, config) {
            $scope.vmNetworkSubnetList = data.data.data;
            $scope.vmNetworkSubnetSelectorData=$scope.vmNetworkSubnetList.map(function(subnet){
              return new ModelService.SelectModel(subnet.name,subnet.id);
            });
            $scope.selectedVmNetworkSubnet=$scope.vmNetworkSubnetSelectorData[0];
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
      },
      setVmPrice=function(){
        var data={
          region: region,
          order_time: $scope.vmBuyPeriod.toString(),
          order_num: $scope.vmCount.toString(),
          os_broadband: $scope.networkBandWidth.toString(),
          volumeType: $scope.selectedVmDiskType.name,
          volumeSize:$scope.dataDiskVolume.toString(),
          cpu_ram: $scope.selectedVmCpu + '_' + $scope.selectedVmRam,
        };
        calculatePriceData=data;
        HttpService.doPost(Config.urls.vm_calculate_price,data).success(function (data, status, headers, config) {
          if(data.result===1){
            $scope.vmTotalPrice=data.data;
          }
          else{
            WidgetService.notifyError(data.msgs[0]||'计算总价失败');
          }
        });
      },
      buildDisplayData=function(){
        var data=[];
        data.push(['配置',selectedVmFlavor.vcpus+'核, '+selectedVmFlavor.ram/1024+'G内存, '+ $scope.dataDiskVolume+'G数据盘'].join('/:'));
        data.push(['带宽',$scope.networkBandWidth+'Mbps'].join('/:'));
        if($scope.imageActiveTab === 'snapshot'){
          data.push(['快照',$scope.selectedVmSnapshot.name].join('/:'));
        }
        else{
          data.push(['镜像',$scope.selectedVmImage.name].join('/:'));
        }
        data.push(['地域',CurrentContext.allRegionData.filter(function(regionData){return regionData.id==region;})[0].name].join('/:'));
        data.push(['网络类型',$scope.vmNetworkType == 'primary'?'基础网络':'私有网络'].join('/:'));
        return data.join('/;');
      };
    initComponents();
  });

});
