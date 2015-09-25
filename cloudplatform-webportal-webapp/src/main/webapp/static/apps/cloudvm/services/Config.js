/**
 * Created by jiangfei on 2015/8/19.
 */
define(['services/app.service'], function (serviceModule) {
  serviceModule.factory('Config', [function () {
    var config = {};
    config.urls = {
      vm_regions: '/ecs/regions/',
      region_list:'/ecs/region/list',
      vm_list: '/ecs/region/{region}',
      image_list: '/osi/region/{region}',
      flavor_group_data: '/osf/region/{region}/group',
      vm_create_old: '/ecs/region/{region}/vm-create',
      vm_create: '/ecs/vm/create',
      vm_buy: '/billing/buy/2',
      vm_start: '/ecs/region/{region}/vm-start',
      vm_stop: '/ecs/region/{region}/vm-stop',
      vm_delete: '/ecs/region/{region}/vm-delete',
      vm_disk_type:'/osv/volume/type/list',
      vm_network_shared_list:'/osn/network/shared/list',
      vm_calculate_price:'/billing/calculate/price/2',
      vm_detail:'/ecs/region/{region}/vm/{vmId}',
      disk_list:'/osv/region/{region}',
      vpc_list:'/osn/network/private/list',
    };
    config.vmStatuses = [
      {"text": "活跃", "value": "ACTIVE"},
      {"text": "创建中", "value": "BUILD"},
      {"text": "已暂停", "value": "PAUSED"},
      {"text": "已挂起", "value": "SUSPENDED"},
      {"text": "已删除", "value": "DELETED"},
      {"text": "已停止", "value": "SHUTOFF"},
      {"text": "启动中", "value": "STARTING"},
      {"text": "停止中", "value": "STOPING"},
      {"text": "删除中", "value": "DELETEING"},
    ];
    config.vmDiskStatuses = [
      {"text": "创建中", "value": "creating"},
      {"text": "可用的", "value": "available"},
      {"text": "挂载中", "value": "attaching"},
      {"text": "使用中", "value": "in-use"},
      {"text": "删除中", "value": "deleting"},
      {"text": "异常", "value": "error"},
    ];
    config.vmVpcStatuses = [
      {"text": "活跃", "value": "ACTIVE"},
      {"text": "可用", "value": "DOWN"},
      {"text": "创建中", "value": "BUILD"},
      {"text": "不可用", "value": "ERROR"},
      {"text": "不可用", "value": "UNRECOGNIZED"},
    ];
    config.allVmBuyPeriods = [1,2,3,4,5,6,7,8,9,12,24,36];
    return config;
  }]);
});