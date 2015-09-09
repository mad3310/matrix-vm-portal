/**
 * Created by jiangfei on 2015/8/19.
 */
define(['services/app.service'],function (serviceModule) {
  serviceModule.factory('Config', [function () {
    var config = {};
    config.url = {
      mcluster_list: '/mcluster/list',
      user_list: '/user',
      hcluster_list: '/hcluster',
      mcluster_start: '/mcluster/start',
      mcluster_stop: '/mcluster/stop',
    };
    config.mclusterStatuses = [
      {"text": "请选择状态", "value": ""},
      {"text": "运行中", "value": "1"},
      {"text": "创建中", "value": "2"},
      {"text": "创建失败", "value": "3"},
      {"text": "异常", "value": "5"},
      {"text": "启动中", "value": "7"},
      {"text": "停止中", "value": "8"},
      {"text": "已停止", "value": "9"},
      {"text": "删除中", "value": "10"},
      {"text": "危险", "value": "13"},
      {"text": "严重危险", "value": "14"}
    ];
    return config;
  }]);
});