/**
 * Created by jiangfei on 2015/8/12.
 */
define(['controllers/app.controller'],function (controllerModule) {
  controllerModule.controller('rds.cluster-manage.ContainerClusterCrtl', ['$scope', 'Config', 'HttpService',
    function ($scope, Config, HttpService) {
      $scope.searchMclusterName = '';

      $scope.statusList = Config.mclusterStatuses;
      $scope.selectedStatus = {};

      $scope.physicalClusterList = [];
      $scope.selectedPhysicalCluster = {};

      $scope.userList = [];
      $scope.selectedUser = {};

      $scope.mclusterList = [];

      $scope.currentPage = 1;
      $scope.totalItems = 0;
      $scope.pageSize = 15;
      $scope.onPageChange = function(){
        refreshMclusterList();
      };

      $scope.doFilter = function () {
        refreshMclusterList();
      };
      $scope.doClear = function () {
        $scope.searchMclusterName = '';
        $scope.selectedStatus.selected = undefined;
        $scope.selectedPhysicalCluster.selected = undefined;
        $scope.selectedUser.selected = undefined;
        refreshMclusterList();
      };

      $scope.startMcluster = function (mcluster) {
        HttpService.doPost(Config.url.mcluster_start, {mclusterId: mcluster.id}).success(function (data, status, headers, config) {
          if(data.result==1){
            toaster.pop('success', null, '启动成功', 2000, 'trustedHtml');
          }
          else{
            toaster.pop('error', null, '启动失败', 2000, 'trustedHtml');
          }
        });
      };
      $scope.stopMcluster = function (mcluster) {
        HttpService.doPost(Config.url.mcluster_stop, {mclusterId: mcluster.id}).success(function (data, status, headers, config) {
          if(data.result==1){
            toaster.pop('success', null, '停止成功', 2000, 'trustedHtml');
          }
          else{
            toaster.pop('error', null, '停止失败', 2000, 'trustedHtml');
          }
        });
      };

      $scope.deleteMcluster = function (mcluster) {
        return mcluster;
      };

      var refreshMclusterList = function () {
          var queryParams = {};
          queryParams.mclusterName = $scope.searchMclusterName;
          queryParams.currentPage = $scope.currentPage;
          queryParams.recordsPerPage = $scope.pageSize;
          queryParams.hclusterName = $scope.selectedPhysicalCluster.selected === undefined ? '' : $scope.selectedPhysicalCluster.selected.value;
          queryParams.userName = $scope.selectedUser.selected === undefined ? '' : $scope.selectedUser.selected.value;
          queryParams.status = $scope.selectedStatus.selected === undefined ? '' : $scope.selectedStatus.selected.value;
          HttpService.doGet(Config.url.mcluster_list, {params: queryParams}).success(function (data, status, headers, config) {
            $scope.mclusterList = data.data.data;
            $scope.totalItems = data.data.totalRecords;
          });
        },
        initPageComponents = function () {
          HttpService.doGet(Config.url.hcluster_list).success(function (data, status, headers, config) {
            $scope.physicalClusterList = data.data.map(function (hcluster) {
              return {text: hcluster.hclusterNameAlias, value: hcluster.hclusterName};
            });
          });

          HttpService.doGet(Config.url.user_list).success(function (data, status, headers, config) {
            $scope.userList = data.data.map(function (user) {
              return {text: user.userName, value: user.userName};
            });
          });
        };

      initPageComponents();
      refreshMclusterList();

    }]);
});
