/**
 * Created by jiangfei on 2015/8/12.
 */
define(['controllers/app.controller'], function (controllerModule) {
  controllerModule.controller('VirtualMachineCrtl', ['$scope', 'Config', 'HttpService',
    function ($scope, Config, HttpService) {
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

      var refreshVmList = function () {
          var region = $scope.selectedRegion.selected,
            queryParams = {
              name: $scope.searchVmName,
              currentPage: $scope.currentPage,
              recordsPerPage: $scope.pageSize
            };
          HttpService.doGet(Config.urls.vm_list + '/' + region, {
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
  ])
  ;
})
;
