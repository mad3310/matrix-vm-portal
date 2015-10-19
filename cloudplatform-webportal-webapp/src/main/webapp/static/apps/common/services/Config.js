/**
 * Created by jiangfei on 2015/8/19.
 */
define(['./common.service'], function (serviceModule) {
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
      disk_calculate_price:'/billing/calculate/price/3',
      floatip_calculate_price:'/billing/calculate/price/4',
      route_calculate_price:'/billing/calculate/price/5',
      vm_detail:'/ecs/region/{region}/vm/{vmId}',
      disk_list:'/osv/region/{region}',
      disk_create:'/osv/region/{region}/volume-create',
      disk_delete:'/osv/region/{region}/volume-delete',
      disk_attach:'/ecs/region/{region}/vm-attach-volume',
      disk_detach:'/ecs/region/{region}/vm-detach-volume',
      subnet_list:'/osn/subnet/private/list',
      subnet_create:'/osn/subnet/private/create',
      subnet_delete:'/osn/subnet/private/delete',
      subnet_edit:'/osn/subnet/private/edit',
      vpc_list:'/osn/network/private/list',
      vpc_delete:'/osn/network/private/delete',
      vpc_create:'/osn/network/private/create',
      vpc_edit:'/osn/network/private/edit',
      router_list:'/osn/router/list',
      network_public_list:'/osn/network/public/list',
      available_for_router_subnet_list:'/osn/network/private/available_for_router_interface/list',
      subnet_associate:'/osn/router/subnet/associate',
      subnet_remove:'/osn/router/subnet/separate',
      router_create:'/osn/router/create',
      router_edit:'/osn/router/edit',
      router_delete:'/osn/router/delete',
      floatIP_list:'/osn/floatingip/list',
      floatIP_create:'/osn/floatingip/create',
      floatIp_delete:'/osn/floatingip/delete',
      floatIP_edit:'/osn/floatingip/edit',
      floatIp_bindVm:'/ecs/vm/floatingip/bind',
      floatIp_unbindVm:'/ecs/vm/floatingip/unbind',
      snapshot_disk_list:'/osv/volume/snapshot/list',
      snapshot_disk_create:'/osv/volume/snapshot/create',
      snapshot_disk_delete:'/osv/volume/snapshot/delete',
      snapshot_vm_list:'/ecs/vm/snapshot/list',
      snapshot_vm_create:'/ecs/vm/snapshot/create',
      snapshot_vm_delete:'/ecs/vm/snapshot/delete'
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
      {"text": "已删除", "value": "deleted"},
      {"text": "解挂中", "value": "detaching"},
    ];
    config.vmVpcStatuses = [
      {"text": "活跃", "value": "ACTIVE"},
      {"text": "可用", "value": "DOWN"},
      {"text": "创建中", "value": "BUILD"},
      {"text": "不可用", "value": "ERROR"},
      {"text": "不可用", "value": "UNRECOGNIZED"},
    ];
    config.vmFloatIpStatuses={
      'AVAILABLE':'可用的',
      'BINDED':'已绑定'
    };
    config.allBuyPeriods = [1,2,3,4,5,6,7,8,9,12,24,36];
    return config;
  }]);
});