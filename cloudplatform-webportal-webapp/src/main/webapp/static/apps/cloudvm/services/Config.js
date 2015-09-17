/**
 * Created by jiangfei on 2015/8/19.
 */
define(['services/app.service'], function (serviceModule) {
  serviceModule.factory('Config', [function () {
    var config = {};
    config.urls = {
      vm_regions: '/ecs/regions/',
      vm_list: '/ecs/region/{region}',
      image_list: '/osi/region/{region}',
      flavor_group_data: '/osf/region/{region}/group',
      vm_create_old: '/ecs/region/{region}/vm-create',
      vm_create: '/ecs/vm/create',
      vm_start: '/ecs/region/{region}/vm-start',
      vm_stop: '/ecs/region/{region}/vm-stop',
      vm_delete: '/ecs/region/{region}/vm-delete',
      vm_disk_type:'/osv/volume/type/list',
      vm_network_shared_list:'/osn/network/shared/list',
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