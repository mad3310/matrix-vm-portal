/**
 * Created by jiangfei on 2015/8/12.
 */
define(['controllers/app.controller'], function (controllerModule) {

  controllerModule.controller('VmDiskCreateModalCtrl', function (Config, HttpService,WidgetService,Utility,CurrentContext,ModelService, $scope, $modalInstance,$timeout,$window, region,diskSnapshot) {

    Utility.getRzSliderHack($scope)();
    $scope.diskName = '';
    $scope.diskTypeList = [];
    $scope.selectedDiskType = null;
    $scope.snapshotList = [];
    $scope.snapshotListSelectorData = [];
    $scope.selectedSnapshot = null;
    $scope.diskVolume = 10;
    $scope.diskCount = 1;
    $scope.allDiskBuyPeriods = Config.allBuyPeriods;
    $scope.diskBuyPeriod = $scope.allDiskBuyPeriods[0];
    $scope.totalPrice='';

    $scope.closeModal=function(){
      $modalInstance.dismiss('cancel');
    };
    $scope.isSelectedDiskType = function (diskType) {
      return $scope.selectedDiskType === diskType;
    };
    $scope.selectDiskType = function (diskType) {
      $scope.selectedDiskType = diskType;
    };
    $scope.createDisk = function () {
      var data = {
        region:region,
        name: $scope.diskName,
        description:'',
        volumeTypeId:$scope.selectedDiskType.id,
        size: $scope.diskVolume,
        count:$scope.diskCount,
        order_time: $scope.diskBuyPeriod.toString()
      };
      $scope.isOrderSubmiting=true;
      HttpService.doPost(Config.urls.disk_buy, {paramsData:JSON.stringify(data),displayData:buildDisplayData()}).success(function (data, status, headers, config) {
        if(data.result===1){
          $modalInstance.close(data);
          $window.location.href = '/payment/'+data.data;
        }
        else{
          WidgetService.notifyError(data.msgs[0]||'创建云硬盘失败');
          $scope.isOrderSubmiting=false;
        }
      });
    };

    $scope.$watch('diskVolume', function (value) {
      if (value != null) {
        delaySliderModelHandlerOfDiskVolume(value,updateDiskVolumeOfCalculatePrice);
      }
    });

    $scope.$watch(function(){
      return [
        ($scope.selectedDiskType &&  $scope.selectedDiskType.name) || '',
        $scope.diskCount,
        diskVolumeOfCalculatePrice,
        $scope.diskBuyPeriod].join('_');
    }, function (value) {
      if ($scope.selectedDiskType && $scope.diskVolume && $scope.diskCount && $scope.diskBuyPeriod) {
        setDiskPrice();
      }
    });

    var diskVolumeOfCalculatePrice= 0,
      delaySliderModelHandlerOfDiskVolume=Utility.delaySliderModel();
    var initComponents = function () {
        initDiskTypeSelector();
        initSnapshotTypeSelector();
      },
      initDiskTypeSelector = function () {
        HttpService.doGet(Config.urls.vm_disk_type,{region:region}).success(function (data, status, headers, config) {
          $scope.diskTypeList=data.data;
          $scope.selectedDiskType = $scope.diskTypeList[1];
        });
      },
      initSnapshotTypeSelector=function(){
        if(diskSnapshot){
          $scope.snapshotListSelectorData=[new ModelService.SelectModel(diskSnapshot.name,diskSnapshot.id)];
          $scope.selectedSnapshot=$scope.snapshotListSelectorData[0];
        }
        else{
          HttpService.doGet(Config.urls.snapshot_disk_list,{region:region,name: '', currentPage: '', recordsPerPage: ''}).success(function (data, status, headers, config) {
            $scope.snapshotList = data.data.data;
            $scope.snapshotListSelectorData=$scope.snapshotList.map(function(snapshot){
              return new ModelService.SelectModel(snapshot.name,snapshot.id);
            });
            $scope.snapshotListSelectorData.unshift(new ModelService.SelectModel('请选择快照',''));
            $scope.selectedSnapshot=$scope.snapshotListSelectorData[0];
          });
        }
      },
      setDiskPrice=function(){
        var data={
          region: region,
          order_time: $scope.diskBuyPeriod.toString(),
          order_num: $scope.diskCount.toString(),
          volumeType: $scope.selectedDiskType.name,
          volumeSize: $scope.diskVolume.toString(),
        };
        HttpService.doPost(Config.urls.disk_calculate_price,data).success(function (data, status, headers, config) {
          if(data.result===1){
            $scope.totalPrice=data.data;
          }
          else{
            WidgetService.notifyError(data.msgs[0]||'计算总价失败');
          }
        });
      },
      buildDisplayData=function(){
        var data=[];
        data.push(['类型',$scope.selectedDiskType.name].join('/:'));
        data.push(['容量',$scope.diskVolume+'Mbps'].join('/:'));
        return data.join('/;');
      },
      updateDiskVolumeOfCalculatePrice=function(value) {
        diskVolumeOfCalculatePrice = value;
      };

    initComponents();

  });

});
