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
      not_in_any_network_vm_list:'/ecs/vm/notInAnyNetwork/list',
      could_attach_subnet_list:'/ecs/vm/couldAttachSubnet/list',
      vm_attach_subnet_list:"/ecs/vm/attached/subnet/list",
      image_list: '/osi/image/list',
      flavor_group_data: '/osf/region/{region}/group',
      vm_create_old: '/ecs/region/{region}/vm-create',
      vm_create: '/ecs/vm/create',
      vm_buy: '/billing/buy/2',
      disk_buy: '/billing/buy/3',
      floatip_buy: '/billing/buy/4',
      router_buy: '/billing/buy/5',
      vm_start: '/ecs/region/{region}/vm-start',
      vm_stop: '/ecs/region/{region}/vm-stop',
      vm_reboot: '/ecs/vm/reboot',
      vm_delete: '/ecs/region/{region}/vm-delete',
      vm_password_change: '/ecs/vm/changeAdminPass',
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
      disk_edit:'/osv/volume/edit',
      subnet_list:'/osn/subnet/private/list',
      subnet_create:'/osn/subnet/private/create',
      subnet_delete:'/osn/subnet/private/delete',
      subnet_edit:'/osn/subnet/private/edit',
      subnet_attach_vm:"/ecs/vm/attach/subnet",
      subnet_detach_vm:"/ecs/vm/detach/subnet",
      vpc_list:'/osn/network/private/list',
      vpc_delete:'/osn/network/private/delete',
      vpc_create:'/osn/network/private/create',
      vpc_subnet_create:'/osn/network/subnet/private/create',
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
      snapshot_vm_delete:'/ecs/vm/snapshot/delete',
      vm_vnc:'/ecs/region/{region}/vm-open-console',
      keypair_list:'/ecs/keypair/list',
      keypair_create:'/ecs/keypair/create',
      keypair_delete:'/ecs/keypair/delete',
      keypair_check:'/ecs/keypair/create/check',
    };
    config.REGEX= {
      NAME: /^[a-zA-Z\u4e00-\u9fa5][^\s"@\/:=<>{\[\]}]{1,127}$/,
      NAME_NO_ZH: /^[a-zA-Z][^\s"@\/:=<>{\[\]}\u4e00-\u9fa5]{1,127}$/,
      PASSWORD: /^(?=.*[0-9].*)(?=.*[A-Z].*)(?=.*[a-z].*)[a-zA-Z0-9]{8,30}$/,
    };
    config.vmStatuses = {
      'active':'活跃',
      'building':'创建中',
      'paused':'已暂停',
      'suspended':'已挂起',
      'stopped':'已停止',
      'error':'异常',
      //前端添加
      'deleted':'已删除',
      'arrearaged':'欠费停机'
  };
    config.vmTaskStatuses = {
      'building':'创建中',
      'deleting':'删除中',
      'stopping':'停止中',
      'starting':'启动中',
      'rebooting':'重启中',
      'spawning':'创建中'
    };
    config.vmDiskStatuses = [
      {"text": "创建中", "value": "creating"},
      {"text": "可用的", "value": "available"},
      {"text": "挂载中", "value": "attaching"},
      {"text": "使用中", "value": "in-use"},
      {"text": "删除中", "value": "deleting"},
      {"text": "异常", "value": "error"},
      //前端自定义
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
    config.vmImageStatuses=[
      {"text": "未识别", "value": "UNRECOGNIZED"},
      {"text": "活跃", "value": "ACTIVE"},
      {"text": "上传中", "value": "SAVING"},
      {"text": "等待上传", "value": "QUEUED"},
      {"text": "停止", "value": "KILLED"},
      {"text": "删除等待", "value": "PENDING_DELETE"},
      {"text": "删除", "value": "DELETED"},
      {"text": "无", "value": "NIL"},
    ];
    config.allBuyPeriods = [1,2,3,4,5,6,7,8,9,12,24,36];
    config.statusOperations={
      "virtualMachine":{
        "active":{"create":1,"start":0,"stop":1,"restart":1,"createsnap":1,"changeconfig":0,"attachdisk":1,"detachdisk":1,"bindfloatIp":1,"unbindfloatIp":1,
                  "joinnet":1,"bindalarm":1,"vnc":1,"editssh":1,"editpass":1,"delete":1},
        "stopped":{"create":1,"start":1,"stop":0,"restart":0,"createsnap":1,"changeconfig":0,"attachdisk":1,"detachdisk":1,"bindfloatIp":1,"unbindfloatIp":1,
                  "joinnet":1,"bindalarm":0,"vnc":0,"editssh":0,"editpass":0,"delete":1},
        "building":{"create":0,"start":0,"stop":0,"restart":0,"createsnap":0,"changeconfig":0,"attachdisk":0,"detachdisk":0,"bindfloatIp":0,"unbindfloatIp":0,
                  "joinnet":0,"bindalarm":0,"vnc":0,"editssh":0,"editpass":0,"delete":0},
        "deleting":{"create":0,"start":0,"stop":0,"restart":0,"createsnap":0,"changeconfig":0,"attachdisk":0,"detachdisk":0,"bindfloatIp":0,"unbindfloatIp":0,
                  "joinnet":0,"bindalarm":0,"vnc":0,"editssh":0,"editpass":0,"delete":0},
        "stopping":{"create":0,"start":0,"stop":0,"restart":0,"createsnap":0,"changeconfig":0,"attachdisk":0,"detachdisk":0,"bindfloatIp":0,"unbindfloatIp":0,
                  "joinnet":0,"bindalarm":0,"vnc":0,"editssh":0,"editpass":0,"delete":0},
        "starting":{"create":0,"start":0,"stop":0,"restart":0,"createsnap":0,"changeconfig":0,"attachdisk":0,"detachdisk":0,"bindfloatIp":0,"unbindfloatIp":0,
                  "joinnet":0,"bindalarm":0,"vnc":0,"editssh":0,"editpass":0,"delete":0},
        "rebooting":{"create":0,"start":0,"stop":0,"restart":0,"createsnap":0,"changeconfig":0,"attachdisk":0,"detachdisk":0,"bindfloatIp":0,"unbindfloatIp":0,
                  "joinnet":0,"bindalarm":0,"vnc":0,"editssh":0,"editpass":0,"delete":0},
        "spawning":{"create":0,"start":0,"stop":0,"restart":0,"createsnap":0,"changeconfig":0,"attachdisk":0,"detachdisk":0,"bindfloatIp":0,"unbindfloatIp":0,
                  "joinnet":0,"bindalarm":0,"vnc":0,"editssh":0,"editpass":0,"delete":0}, 
        "deleted":{"create":1,"start":0,"stop":0,"restart":0,"createsnap":0,"changeconfig":0,"attachdisk":0,"detachdisk":0,"bindfloatIp":0,"unbindfloatIp":0,
                  "joinnet":0,"bindalarm":0,"vnc":0,"editssh":0,"editpass":0,"delete":0},           
      },
      "disk":{

      }
    }

    return config;
  }]);
});