/**
 * Created by jiangfei on 2015/8/12.
 */
define(['controllers/app.controller'], function (controllerModule) {
  controllerModule.controller('VmFloatIpCrtl', ['$scope','$interval','Config','$modal','HttpService','WidgetService','CurrentContext',
    function ($scope,$interval, Config,$modal,HttpService,WidgetService,CurrentContext) {
      $scope.floatIpList = [];
      $scope.currentPage = 1;
      $scope.totalItems = 0;
      $scope.pageSize = 10;
      $scope.onPageChange = function () {
        refreshFloatIpList();
      };
      $scope.isAllFipChecked=function(){
        var unCheckedFips=$scope.floatIpList.filter(function(fIp){
          return fIp.checked===false || fIp.checked===undefined;
        });
        return unCheckedFips.length==0;
      };
      $scope.checkAllFip=function(){
        if($scope.isAllVpcChecked()){
          $scope.floatIpList.forEach(function(fIp){
            fIp.checked=false;
          });
        }
        else{
          $scope.floatIpList.forEach(function(fIp){
            fIp.checked=true;
          });
        }

      };
      $scope.checkFip = function (fIp) {
        fIp.checked = fIp.checked === true ? false : true;
      };
      $scope.openIPcreateModal=function(size){
        var modalInstance = $modal.open({
          animation: $scope.animationsEnabled,
          templateUrl: 'vmIPcreateModalTpl',
          controller: 'VmIPcreateModalCtrl',
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
          if(resultData &&resultData.result===1){
            //refreshFloatIpList();
          }
        }, function () {
        });
      };
      $scope.bindVm=function(size){
        var checkedIps=getCheckedIp();
        if(checkedIps.length !==1){
          WidgetService.notifyWarning('请选中一个公网Ip');
          return;
        }else{
          if(checkedIps[0].bindResource){
            WidgetService.notifyError('已绑定云主机');
            return;
          }else{
            //open bindmodal
            var modalInstance = $modal.open({
              animation: $scope.animationsEnabled,
              templateUrl: 'VmIPbindVmMOdalTpl',
              controller: 'VmIpBindVmModalCtrl',
              size: size,
              backdrop: 'static',
              keyboard: false,
              resolve: {
                region: function () {
                  return CurrentContext.regionId;
                }
              }
            });
          }
        }
        
      }
      $scope.deleteIp=function(size){
        var checkedIps=getCheckedIp();
        if(checkedIps.length !==1){
          WidgetService.notifyWarning('请选中一个公网Ip');
          return;
        }
        var data={
          region:checkedIps[0].region,
          floatingIpId: checkedIps[0].id
        };
        console.log(data)
        var modalInstance = WidgetService.openConfirmModal('删除公网IP','确定要删除公网IP（'+checkedIps[0].name+'）吗？');
        modalInstance.result.then(function (resultData) {
          if(!resultData) return resultData;
          WidgetService.notifyInfo('公网Ip删除执行中...');
          checkedIps[0].status='DELETEING';
          HttpService.doPost(Config.urls.floatIp_delete,data).success(function (data, status, headers, config) {
            if(data.result===1){
              checkedIps[0].status='DELETED';
              modalInstance.close(data);
              WidgetService.notifySuccess('删除公网Ip成功');
              refreshFloatIpList();
            }
            else{
              WidgetService.notifyError(data.msgs[0]||'删除公网Ip失败');
            }
          });
        }, function () {
        });
      };
      var refreshFloatIpList = function () {
          var queryParams = {
            region:CurrentContext.regionId,
            name:'',
            currentPage: $scope.currentPage,
            recordsPerPage: $scope.pageSize
          };
          WidgetService.showSpin();
          HttpService.doGet(Config.urls.floatIP_list, queryParams).success(function (data, status, headers, config) {
            WidgetService.hideSpin();
            $scope.floatIpList = data.data.data;
            $scope.totalItems = data.data.totalRecords;

          });
        };
      getCheckedIp=function(){
        return $scope.floatIpList.filter(function(item){
          return item.checked===true;
        });
      };
      refreshFloatIpList();
    }
  ]);

});
