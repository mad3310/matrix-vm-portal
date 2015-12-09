function ClientInfor() {
  this.engine = {ie: 0, gecko: 0, webkit: 0, khtml: 0, opera: 0, ver: null}, this.browser = {
    ie: 0,
    firefox: 0,
    safari: 0,
    konq: 0,
    opera: 0,
    chrome: 0,
    ver: null
  }, this.system = {
    win: !1,
    mac: !1,
    xll: !1,
    iphone: !1,
    ipod: !1,
    ipad: !1,
    ios: !1,
    android: !1,
    nokiaN: !1,
    winMobile: !1,
    wii: !1,
    ps: !1
  }, this.ua = window.navigator.userAgent, this.p = window.navigator.platform, this.getSystemInfor = function () {
    if (this.system.win = 0 == this.p.indexOf("Win"), this.system.mac = 0 == this.p.indexOf("Mac"), this.system.xll = 0 == this.p.indexOf("Linux") || 0 == this.p.indexOf("Xll"), this.system.iphone = this.ua.indexOf("iPhone") > -1, this.system.ipod = this.ua.indexOf("iPod") > -1, this.system.ipad = this.ua.indexOf("iPad") > -1, this.system.mac && this.ua.indexOf("Mobile") > -1 && (/CPU (?:iPhone )?OS (\d+_\d+)/.test(this.ua) ? this.system.ios = parseFloat(RegExp.$1.replace("_", ".")) : this.system.ios = 2), /Android (\d+\.\d+)/.test(this.ua) && (this.system.android = parseFloat(RegExp.$1)), this.system.nokiaN = this.ua.indexOf("NokiaN") > -1, "CE" == this.system.win ? this.system.winMobile = this.system.win : "Ph" == this.system.win && /Windows Phone OS (\d+.\d+)/.test(this.ua) && (this.system.win = "Phone", this.system.winMobile = parseFloat(RegExp.$1)), this.system.wii = this.ua.indexOf("Wii") > -1, this.system.ps = /playstation/i.test(this.ua), this.system.win && /Win(?:dows )?([^do]{2})\s?(\d+\.\d+)?/.test(this.ua))if ("NT" == RegExp.$1)switch (RegExp.$2) {
      case"5.0":
        this.system.win = "2000";
        break;
      case"5.1":
        this.system.win = "XP";
        break;
      case"6.0":
        this.system.win = "Vista";
        break;
      case"6.1":
        this.system.win = "7";
        break;
      case"6.2":
        this.system.win = "8";
        break;
      default:
        this.system.win = "NT"
    } else"9x" == RegExp.$1 ? this.system.win = "ME" : this.system.win = RegExp.$1;
    return this.system
  }, this.getBrowserInfor = function () {
    if (window.opera)this.engine.ver = this.browser.ver = window.opera.version(), this.engine.opera = this.browser.opera = parseFloat(this.engine.ver); else if (/AppleWebKit\/(\S+)/i.test(this.ua))if (this.engine.ver = this.browser.ver = RegExp.$1, this.engine.webkit = parseFloat(this.engine.ver), /Chrome\/(\S+)/i.test(this.ua))this.browser.chrome = parseFloat(this.engine.ver); else if (/Version\/(\S+)/i.test(this.ua))this.browser.safari = parseFloat(this.engine.ver); else {
      var safariVersion = 1;
      safariVersion = this.engine.webkit < 100 ? 1 : this.engine.webkit < 312 ? 1.2 : this.engine.webkit < 412 ? 1.3 : 2, this.browser.safari = this.browser.ver = safariVersion
    } else/KHTML\/(\S+)/i.test(this.ua) || /Konqueror\/([^;]+)/i.test(this.ua) ? (this.engine.ver = this.browser.ver = RegExp.$1, this.engine.khtml = this.browser.konq = parseFloat(this.engine.ver)) : /rv:([^\)]+)\) Gecko\/\d{8}/i.test(this.ua) ? (this.engine.ver = RegExp.$1, this.engine.gecko = parseFloat(this.engine.ver), /Firefox\/(\S+)/i.test(this.ua) && (this.browser.ver = RegExp.$1, this.browser.firefox = parseFloat(this.browser.ver))) : /MSIE\s([^;]+)/i.test(this.ua) ? (this.engine.ver = this.browser.ver = RegExp.$1, this.engine.ie = this.browser.ie = parseFloat(this.engine.ver)) : /trident.*rv:([^)]+)/i.test(this.ua) && (this.engine.ver = this.browser.ver = RegExp.$1, this.engine.ie = this.browser.ie = parseFloat(this.engine.ver));
    return this.browser
  }, this.getEngineInfor = function () {
    if (window.opera)this.engine.ver = this.browser.ver = window.opera.version(), this.engine.opera = this.browser.opera = parseFloat(this.engine.ver); else if (/AppleWebKit\/(\S+)/i.test(this.ua))if (this.engine.ver = this.browser.ver = RegExp.$1, this.engine.webkit = parseFloat(this.engine.ver), /Chrome\/(\S+)/i.test(this.ua))this.browser.chrome = parseFloat(this.engine.ver); else if (/Version\/(\S+)/i.test(this.ua))this.browser.safari = parseFloat(this.engine.ver); else {
      var safariVersion = 1;
      safariVersion = this.engine.webkit < 100 ? 1 : this.engine.webkit < 312 ? 1.2 : this.engine.webkit < 412 ? 1.3 : 2, this.browser.safari = this.browser.ver = safariVersion
    } else/KHTML\/(\S+)/i.test(this.ua) || /Konqueror\/([^;]+)/i.test(this.ua) ? (this.engine.ver = this.browser.ver = RegExp.$1, this.engine.khtml = this.browser.konq = parseFloat(this.engine.ver)) : /rv:([^\)]+)\) Gecko\/\d{8}/i.test(this.ua) ? (this.engine.ver = RegExp.$1, this.engine.gecko = parseFloat(this.engine.ver), /Firefox\/(\S+)/i.test(this.ua) && (this.browser.ver = RegExp.$1, this.browser.firefox = parseFloat(this.browser.ver))) : /MSIE\s([^;]+)/i.test(this.ua) ? (this.engine.ver = this.browser.ver = RegExp.$1, this.engine.ie = this.browser.ie = parseFloat(this.engine.ver)) : /trident.*rv:([^)]+)/i.test(this.ua) && (this.engine.ver = this.browser.ver = RegExp.$1, this.engine.ie = this.browser.ie = parseFloat(this.engine.ver));
    return this.engine
  }
}
define("browserCheck", ["jquery"], function (global) {
  return function () {
    var ret;
    return ret || global.browserCheck
  }
}(this)), define("common", ["jquery", "browserCheck"], function ($) {
  function browerversion(obj) {
    var _browser = obj.getBrowserInfor();
    _browser.ie && _browser.ver < 9 ? window.location.replace = "/browserError" : _browser.firefox && _browser.ver < 5 ? window.location.replace = "/browserError" : _browser.chrome && _browser.ver < 7 ? window.location.replace = "/browserError" : _browser.safari && _browser.ver < 4 && (window.location.replace = "/browserError")
  }

  function getCookie(name) {
    for (var arr = document.cookie.split("; "), i = 0; i < arr.length; i++) {
      var arr2 = arr[i].split("=");
      if (arr2[0] == name)return arr2[1]
    }
    return ""
  }

  var client = new ClientInfor;
  browerversion(client);
  var viewHeight = $(window).height() - 70;
  $(".content").css("min-height", viewHeight);
  for (var sideMenuData = [{
    url: "/profile/#/dashboard",
    title: "概览",
    icon: "iconfont icon-clouddashboard",
    isSubmenuFisrt: !0
  }, {url: "/cvm/#/vm", title: "云主机", icon: "iconfont icon-cloudhost", isSubmenuFisrt: !0}, {
    url: "/cvm/#/vm-disk",
    title: "云硬盘",
    icon: "iconfont icon-clouddisk"
  }, {url: "/cvm/#/vm-vpc", title: "私有网络", icon: "iconfont icon-cloudnet"}, {
    url: "/cvm/#/vm-floatIP",
    title: "公网IP",
    icon: "iconfont icon-cloudfloatip"
  }, {url: "/cvm/#/vm-router", title: "路由器", icon: "iconfont icon-cloudroute"}, {
    url: "/cvm/#/vm-snapshot",
    title: "快照",
    icon: "iconfont icon-cloudsnap"
  }, {url: "/cvm/#/vm-image", title: "镜像", icon: "iconfont icon-cloudimage"}, {
    url: "/cvm/#/vm-keypair",
    title: "密钥",
    icon: "iconfont icon-cloudkeypair"
  }], sideMenuItemEl = null, sideMenuItemEls = [], sideMenuEl = $(".side-menu"), path = window.location.pathname, hash = window.location.hash, currentUrl = path + hash, isMenuActive = function (sideMenuItem) {
    var result = !1;
    return result = hash ? sideMenuData[i].url === currentUrl : sideMenuItem.url.indexOf(path) > -1 && sideMenuItem.isSubmenuFisrt
  }, i = 0, leng = sideMenuData.length; leng > i; i++)sideMenuItemEl = '<li class="menu-item' + (isMenuActive(sideMenuData[i]) ? " active" : "") + '"><a href="' + sideMenuData[i].url + '"><i class="' + sideMenuData[i].icon + '"></i><span>' + sideMenuData[i].title + "</span></a></li>", sideMenuItemEls.push(sideMenuItemEl);
  sideMenuEl.html(sideMenuItemEls.join("")), sideMenuEl.on("click", ".menu-item a", function (e) {
    sideMenuEl.children(".menu-item").removeClass("active"), $(e.target).closest("li").addClass("active")
  });
  var userid = getCookie("userId"), usinfourl = "/user/" + userid;
  return $.ajax({
    url: usinfourl, type: "get", success: function (data) {
      if (0 == data.result); else {
        var _data = data.data;
        _data && ($(".header-username").text(_data.contacts), _data.userAvatar && $(".account-icon").attr("src", _data.userAvatar))
      }
    }
  }), {}
}), define("controllers/app.controller", ["angular"], function (angular) {
  return angular.module("app.controller", [])
}), define("controllers/VirtualMachine", ["controllers/app.controller"], function (controllerModule) {
  controllerModule.controller("VirtualMachineCrtl", ["$scope", "$interval", "$window", "$modal", "Config", "Utility", "HttpService", "WidgetService", "CurrentContext", function ($scope, $interval, $window, $modal, Config, Utility, HttpService, WidgetService, CurrentContext) {
    $scope.searchVmName = "", $scope.vmTaskStatuses = Config.vmTaskStatuses, $scope.vmList = [], $scope.currentPage = 1, $scope.totalItems = 0, $scope.pageSize = 10, $scope.operationBtn = {};
    var operationArry = [];
    $scope.onPageChange = function () {
      refreshVmList()
    }, $scope.doSearch = function () {
      refreshVmList()
    }, $scope.startVm = function (size) {
      var checkedVms = getCheckedVm(), originalVmState = "", originalTaskState = "";
      if (1 !== checkedVms.length)return void WidgetService.notifyWarning("请选中一个云主机");
      if (originalVmState = checkedVms[0].vmState, originalTaskState = checkedVms[0].taskState, originalTaskState || "stopped" !== originalVmState)return void WidgetService.notifyWarning("云主机当前状态不可启动");
      var data = {vmId: checkedVms[0].id}, modalInstance = WidgetService.openConfirmModal("启动云主机", "确定要云主机（" + checkedVms[0].name + "）启动吗？");
      modalInstance.result.then(function (resultData) {
        return resultData ? (WidgetService.notifyInfo("云主机启动执行中..."), checkedVms[0].vmState = null, checkedVms[0].taskState = "starting", void HttpService.doPost(Config.urls.vm_start.replace("{region}", CurrentContext.regionId), data).success(function (data, status, headers, config) {
          1 === data.result ? (checkedVms[0].vmState = "active", checkedVms[0].taskState = null, modalInstance.close(data), WidgetService.notifySuccess("启动云主机成功"), refreshVmList()) : (checkedVms[0].vmState = originalVmState, checkedVms[0].taskState = originalTaskState, WidgetService.notifyError(data.msgs[0] || "启动云主机失败"))
        })) : resultData
      }, function () {
      })
    }, $scope.stopVm = function (size) {
      var checkedVms = getCheckedVm(), originalVmState = "", originalTaskState = "";
      if (1 !== checkedVms.length)return void WidgetService.notifyWarning("请选中一个云主机");
      if (originalVmState = checkedVms[0].vmState, originalTaskState = checkedVms[0].taskState, originalTaskState || "active" !== originalVmState)return void WidgetService.notifyWarning("云主机当前状态不可关机");
      var data = {vmId: checkedVms[0].id}, modalInstance = WidgetService.openConfirmModal("云主机关机", "确定要云主机（" + checkedVms[0].name + "）关机吗？");
      modalInstance.result.then(function (resultData) {
        return resultData ? (WidgetService.notifyInfo("云主机关机执行中..."), checkedVms[0].vmState = null, checkedVms[0].taskState = "stopping", void HttpService.doPost(Config.urls.vm_stop.replace("{region}", CurrentContext.regionId), data).success(function (data, status, headers, config) {
          1 === data.result ? (checkedVms[0].vmState = "stopped", checkedVms[0].taskState = null, modalInstance.close(data), WidgetService.notifySuccess("云主机关机成功"), refreshVmList()) : (checkedVms[0].vmState = originalVmState, checkedVms[0].taskState = originalTaskState, WidgetService.notifyError(data.msgs[0] || "云主机关机失败"))
        })) : resultData
      }, function () {
      })
    }, $scope.deleteVm = function (size) {
      var checkedVms = getCheckedVm(), originalVmState = "", originalTaskState = "";
      if (1 !== checkedVms.length)return void WidgetService.notifyWarning("请选中一个云主机");
      if (originalVmState = checkedVms[0].vmState, originalTaskState = checkedVms[0].taskState, originalTaskState || "active" !== originalVmState && "stopped" !== originalVmState && "error" != originalVmState)return void WidgetService.notifyWarning("云主机当前状态不可删除");
      var data = {vmId: checkedVms[0].id}, modalInstance = WidgetService.openConfirmModal("删除云主机", "确定要删除云主机（" + checkedVms[0].name + "）吗？");
      modalInstance.result.then(function (resultData) {
        return resultData ? (WidgetService.notifyInfo("云主机删除执行中..."), checkedVms[0].vmState = null, checkedVms[0].taskState = "deleting", void HttpService.doPost(Config.urls.vm_delete.replace("{region}", checkedVms[0].region), data).success(function (data, status, headers, config) {
          1 === data.result ? (checkedVms[0].vmState = "deleted", checkedVms[0].taskState = null, modalInstance.close(data), WidgetService.notifySuccess("删除云主机成功"), refreshVmList()) : (checkedVms[0].vmState = originalVmState, checkedVms[0].taskState = originalTaskState, WidgetService.notifyError(data.msgs[0] || "删除云主机失败"))
        })) : resultData
      }, function () {
      })
    }, $scope.rebootVm = function (size) {
      var checkedVms = getCheckedVm(), originalVmState = "", originalTaskState = "";
      if (1 !== checkedVms.length)return void WidgetService.notifyWarning("请选中一个云主机");
      if (originalVmState = checkedVms[0].vmState, originalTaskState = checkedVms[0].taskState, originalTaskState || "active" !== originalVmState)return void WidgetService.notifyWarning("云主机当前状态不可重启");
      var data = {
        vmId: checkedVms[0].id,
        region: checkedVms[0].region
      }, modalInstance = WidgetService.openConfirmModal("重启云主机", "确定要重启云主机（" + checkedVms[0].name + "）吗？");
      modalInstance.result.then(function (resultData) {
        return resultData ? (WidgetService.notifyInfo("云主机重启执行中..."), checkedVms[0].vmState = null, checkedVms[0].taskState = "rebooting", void HttpService.doPost(Config.urls.vm_reboot, data).success(function (data, status, headers, config) {
          1 === data.result ? (modalInstance.close(data), checkedVms[0].vmState = "active", checkedVms[0].taskState = null, WidgetService.notifySuccess("重启云主机成功"), refreshVmList()) : (checkedVms[0].vmState = originalVmState, checkedVms[0].taskState = originalTaskState, WidgetService.notifyError(data.msgs[0] || "重启云主机失败"))
        })) : resultData
      }, function () {
      })
    }, $scope.isAllVmChecked = function () {
      var unCheckedVms = $scope.vmList.filter(function (vm) {
        return vm.checked === !1 || void 0 === vm.checked
      });
      return 0 == unCheckedVms.length
    }, $scope.checkAllVm = function () {
      $scope.isAllVmChecked() ? $scope.vmList.forEach(function (vm) {
        vm.checked = !1
      }) : $scope.vmList.forEach(function (vm) {
        vm.checked = !0
      })
    }, $scope.checkVm = function (vm, index) {
      vm.checked = vm.checked === !0 ? !1 : !0
    }, $scope.openDiskEditModal = function (size) {
      var checkedVms = getCheckedVm();
      if (1 !== checkedVms.length)return void WidgetService.notifyWarning("请选中一个云主机");
      var modalInstance = $modal.open({
        animation: $scope.animationsEnabled,
        templateUrl: "VmEditModalTpl",
        controller: "VmEditModalCtrl",
        size: size,
        backdrop: "static",
        keyboard: !1,
        resolve: {
          region: function () {
            return CurrentContext.regionId
          }, vm: function () {
            return checkedVms[0]
          }
        }
      });
      modalInstance.result.then(function (resultData) {
        resultData && 1 === resultData.result && refreshVmList()
      }, function () {
      })
    }, $scope.openDiskAttachModal = function (size) {
      var checkedVms = getCheckedVm();
      if (1 !== checkedVms.length)return void WidgetService.notifyWarning("请选中一个云主机");
      if (checkedVms[0].taskState || "active" !== checkedVms[0].vmState && "stopped" !== checkedVms[0].vmState)return void WidgetService.notifyWarning("云主机当前状态不可挂载云硬盘");
      var modalInstance = $modal.open({
        animation: $scope.animationsEnabled,
        templateUrl: "DiskAttachModalTpl",
        controller: "DiskAttachModalCtrl",
        size: size,
        backdrop: "static",
        keyboard: !1,
        resolve: {
          region: function () {
            return CurrentContext.regionId
          }, vm: function () {
            return checkedVms[0]
          }
        }
      });
      modalInstance.result.then(function (resultData) {
        resultData && 1 === resultData.result && refreshVmList()
      }, function () {
      })
    }, $scope.openDiskDetachModal = function (size) {
      var checkedVms = getCheckedVm();
      if (1 !== checkedVms.length)return void WidgetService.notifyWarning("请选中一个云主机");
      if (checkedVms[0].taskState || "active" !== checkedVms[0].vmState && "stopped" !== checkedVms[0].vmState)return void WidgetService.notifyWarning("云主机当前状态不可卸载云硬盘");
      if (!checkedVms[0].volumes || !checkedVms[0].volumes.length)return void WidgetService.notifyWarning("云主机未绑定云硬盘");
      var modalInstance = $modal.open({
        animation: $scope.animationsEnabled,
        templateUrl: "DiskDetachModalTpl",
        controller: "DiskDetachModalCtrl",
        size: size,
        backdrop: "static",
        keyboard: !1,
        resolve: {
          region: function () {
            return CurrentContext.regionId
          }, vm: function () {
            return checkedVms[0]
          }
        }
      });
      modalInstance.result.then(function (resultData) {
        resultData && 1 === resultData.result && refreshVmList()
      }, function () {
      })
    }, $scope.openFloatingIpBindModal = function (size) {
      var checkedVms = getCheckedVm();
      if (1 !== checkedVms.length)return void WidgetService.notifyWarning("请选中一个云主机");
      if (checkedVms[0].taskState || "active" !== checkedVms[0].vmState && "stopped" !== checkedVms[0].vmState)return void WidgetService.notifyWarning("云主机当前状态不可绑定公网IP");
      var modalInstance = $modal.open({
        animation: $scope.animationsEnabled,
        templateUrl: "FloatingIpBindModalTpl",
        controller: "FloatingIpBindModalCtrl",
        size: size,
        backdrop: "static",
        keyboard: !1,
        resolve: {
          region: function () {
            return CurrentContext.regionId
          }, vm: function () {
            return checkedVms[0]
          }
        }
      });
      modalInstance.result.then(function (resultData) {
        resultData && 1 === resultData.result && refreshVmList()
      }, function () {
      })
    }, $scope.openVmCreateModal = function (size) {
      var modalInstance = $modal.open({
        animation: $scope.animationsEnabled,
        templateUrl: "/static/apps/cloudvm/templates/vm-create-modal.html",
        controller: "VmCreateModalCtrl",
        size: size,
        backdrop: "static",
        keyboard: !1,
        resolve: {
          region: function () {
            return CurrentContext.regionId
          }, vmSnapshot: function () {
            return void 0
          }, loadAllRegionData: function ($q, CurrentContext) {
            if (CurrentContext.allRegionData)return !0;
            var deferred = $q.defer();
            return HttpService.doGet(Config.urls.region_list).then(function (data, status, headers, config) {
              CurrentContext.allRegionData = data.data, deferred.resolve(!0)
            }), deferred.promise
          }
        }
      });
      modalInstance.result.then(function (resultData) {
        resultData && 1 === resultData.result && refreshVmList()
      }, function () {
      })
    }, $scope.openVmSnapshotCreateModal = function (size) {
      var checkedVms = getCheckedVm();
      if (1 !== checkedVms.length)return void WidgetService.notifyWarning("请选中一个云主机");
      if (checkedVms[0].taskState || "active" !== checkedVms[0].vmState && "stopped" !== checkedVms[0].vmState)return void WidgetService.notifyWarning("云主机当前状态不可创建快照");
      var modalInstance = $modal.open({
        animation: $scope.animationsEnabled,
        templateUrl: "VmSnapshotCreateModalTpl",
        controller: "VmSnapshotCreateModalCtrl",
        size: size,
        backdrop: "static",
        keyboard: !1,
        resolve: {
          region: function () {
            return CurrentContext.regionId
          }, vm: function () {
            return checkedVms[0]
          }
        }
      });
      modalInstance.result.then(function (resultData) {
        resultData && 1 === resultData.result
      }, function () {
      })
    }, $scope.openVmPasswordChangeModal = function (size) {
      var checkedVms = getCheckedVm();
      if (1 !== checkedVms.length)return void WidgetService.notifyWarning("请选中一个云主机");
      if (checkedVms[0].taskState || "active" !== checkedVms[0].vmState)return void WidgetService.notifyWarning("云主机当前状态不可修改密码");
      var modalInstance = $modal.open({
        animation: $scope.animationsEnabled,
        templateUrl: "VmPasswordChangeModalTpl",
        controller: "VmPasswordChangeModalCtrl",
        size: size,
        backdrop: "static",
        keyboard: !1,
        resolve: {
          region: function () {
            return CurrentContext.regionId
          }, vm: function () {
            return checkedVms[0]
          }
        }
      });
      modalInstance.result.then(function (resultData) {
        resultData && 1 === resultData.result
      }, function () {
      })
    }, $scope.navigateToVNC = function (vm) {
      return vm.taskState || "active" !== vm.vmState ? void WidgetService.notifyWarning("云主机当前状态不可启动VNC") : void HttpService.doPost(Config.urls.vm_vnc.replace("{region}", CurrentContext.regionId), {vmId: vm.id}).success(function (data, status, headers, config) {
        1 === data.result ? $window.open(data.data) : WidgetService.notifyError(data.msgs[0] || "获取云主机VNC连接失败")
      })
    };
    var refreshVmList = function () {
      operationArry = [];
      var queryParams = {name: $scope.searchVmName, currentPage: $scope.currentPage, recordsPerPage: $scope.pageSize};
      $scope.isListLoading = !0, HttpService.doGet(Config.urls.vm_list.replace("{region}", CurrentContext.regionId), queryParams).then(function (data, status, headers, config) {
        $scope.isListLoading = !1, $scope.vmList = data.data.data, $scope.totalItems = data.data.totalRecords, $scope.vmList.filter(function (vm) {
          return !isVmCreated(vm)
        }).forEach(function (vm) {
          var vmDetailUrl = Config.urls.vm_detail.replace("{region}", CurrentContext.regionId).replace("{vmId}", vm.id), buildStatusInterval = $interval(function () {
            HttpService.doGet(vmDetailUrl).then(function (data) {
              isVmCreated(data.data) && (vm.vmState = data.data.vmState, vm.taskState = data.data.taskState, $interval.cancel(buildStatusInterval), refreshVmList())
            })
          }, 5e3)
        })
      })
    }, getCheckedVm = function () {
      return $scope.vmList.filter(function (item) {
        return item.checked === !0
      })
    }, isVmCreated = function (vm) {
      return !("building" === vm.vmState || vm.taskState || vm.volumes && vm.volumes.length && vm.volumes.filter(function (volume) {
        return "attaching" === volume.status
      }).length)
    }, watchStateChange = function () {
      var productInfo = {
        type: "virtualMachine",
        state: "vmState",
        other: ["volumes", "ipAddresses.public"],
        operations: ["create", "start", "stop", "delete", "restart", "createsnap", "attachdisk", "detachdisk", "bindfloatIp", "editpass"]
      };
      $scope.$watch(function () {
        return $scope.vmList.map(function (vm) {
          return vm.checked
        }).join(";")
      }, function () {
        var operationArraycopy = Utility.setOperationBtns($scope, $scope.vmList, productInfo, operationArry, Config), operaArraytemp = productInfo.operations;
        for (var k in operaArraytemp)$scope.operationBtn[operaArraytemp[k]] = operationArraycopy[k]
      })
    };
    refreshVmList(), watchStateChange()
  }]), controllerModule.controller("DiskAttachModalCtrl", function (Config, HttpService, WidgetService, Utility, ModelService, $scope, $modalInstance, region, vm) {
    $scope.diskList = [], $scope.diskListSelectorData = [], $scope.selectedDisk = null, $scope.closeModal = function () {
      $modalInstance.dismiss("cancel")
    }, $scope.attachDisk = function () {
      var data = {vmId: vm.id, volumeId: $scope.selectedDisk.value};
      $scope.isFormSubmiting = !0, HttpService.doPost(Config.urls.disk_attach.replace("{region}", region), data).success(function (data, status, headers, config) {
        1 === data.result ? ($modalInstance.close(data), WidgetService.notifySuccess("云硬盘挂载成功")) : ($scope.isFormSubmiting = !1, WidgetService.notifyError(data.msgs[0] || "云硬盘挂载失败"), $scope.isFormSubmiting = !1)
      })
    };
    var initComponents = function () {
      initDiskSelector()
    }, initDiskSelector = function () {
      HttpService.doGet(Config.urls.disk_list.replace("{region}", region), {
        name: "",
        currentPage: "",
        recordsPerPage: ""
      }).then(function (data, status, headers, config) {
        $scope.diskList = data.data.data, $scope.diskListSelectorData = $scope.diskList.filter(function (disk) {
          return disk.name && "available" === disk.status
        }).map(function (disk) {
          return new ModelService.SelectModel(disk.name, disk.id)
        }), $scope.selectedDisk = $scope.diskListSelectorData[0]
      })
    };
    initComponents()
  }), controllerModule.controller("DiskDetachModalCtrl", function (Config, HttpService, WidgetService, Utility, ModelService, $scope, $modalInstance, region, vm) {
    $scope.diskList = [], $scope.diskListSelectorData = [], $scope.selectedDisk = null, $scope.closeModal = function () {
      $modalInstance.dismiss("cancel")
    }, $scope.detachDisk = function () {
      var data = {vmId: vm.id, volumeId: $scope.selectedDisk.value};
      $scope.isFormSubmiting = !0, HttpService.doPost(Config.urls.disk_detach.replace("{region}", region), data).success(function (data, status, headers, config) {
        1 === data.result ? ($modalInstance.close(data), WidgetService.notifySuccess("云硬盘卸载成功")) : ($scope.isFormSubmiting = !1, WidgetService.notifyError(data.msgs[0] || "云硬盘卸载失败"), $scope.isFormSubmiting = !1)
      })
    };
    var initComponents = function () {
      initDiskSelector()
    }, initDiskSelector = function () {
      $scope.diskList = vm.volumes, $scope.diskListSelectorData = $scope.diskList.filter(function (disk) {
        return disk.name
      }).map(function (disk) {
        return new ModelService.SelectModel(disk.name, disk.id)
      }), $scope.selectedDisk = $scope.diskListSelectorData[0]
    };
    initComponents()
  }), controllerModule.controller("FloatingIpBindModalCtrl", function (Config, HttpService, WidgetService, Utility, ModelService, $scope, $modalInstance, region, vm) {
    $scope.floatingIpList = [], $scope.floatingIpListSelectorData = [], $scope.selectedFloatingIp = null, $scope.closeModal = function () {
      $modalInstance.dismiss("cancel")
    }, $scope.bindFloatingIp = function () {
      var data = {region: region, vmId: vm.id, floatingIpId: $scope.selectedFloatingIp.value};
      $scope.isFormSubmiting = !0, HttpService.doPost(Config.urls.floatIp_bindVm, data).success(function (data, status, headers, config) {
        1 === data.result ? ($modalInstance.close(data), WidgetService.notifySuccess("绑定公网IP成功")) : ($scope.isFormSubmiting = !1, WidgetService.notifyError(data.msgs[0] || "绑定公网IP失败"), $scope.isFormSubmiting = !1)
      })
    };
    var initComponents = function () {
      initFloatingIpSelector()
    }, initFloatingIpSelector = function () {
      HttpService.doGet(Config.urls.floatIP_list, {
        region: region,
        name: "",
        currentPage: "",
        recordsPerPage: ""
      }).then(function (data, status, headers, config) {
        $scope.floatingIpList = data.data.data, $scope.floatingIpListSelectorData = $scope.floatingIpList.filter(function (floatingIp) {
          return "AVAILABLE" === floatingIp.status
        }).map(function (floatingIp) {
          return new ModelService.SelectModel(floatingIp.name, floatingIp.id)
        }), $scope.selectedFloatingIp = $scope.floatingIpListSelectorData[0]
      })
    };
    initComponents()
  }), controllerModule.controller("VmSnapshotCreateModalCtrl", function (Config, HttpService, WidgetService, Utility, ModelService, $scope, $modalInstance, region, vm) {
    $scope.vmSnapshotName = "", $scope.closeModal = function () {
      $modalInstance.dismiss("cancel")
    }, $scope.createVmSnapshot = function () {
      var data = {region: region, vmId: vm.id, name: $scope.vmSnapshotName};
      $scope.isFormSubmiting = !0, HttpService.doPost(Config.urls.snapshot_vm_create, data).success(function (data, status, headers, config) {
        1 === data.result ? ($modalInstance.close(data), WidgetService.notifySuccess("云主机快照创建成功")) : (WidgetService.notifyError(data.msgs[0] || "云主机快照创建失败"), $scope.isFormSubmiting = !1)
      })
    }
  }), controllerModule.controller("VmPasswordChangeModalCtrl", function (Config, HttpService, WidgetService, Utility, ModelService, $scope, $modalInstance, region, vm) {
    $scope.newPassword = "", $scope.confirmPassword = "", $scope.closeModal = function () {
      $modalInstance.dismiss("cancel")
    }, $scope.changeVmPassword = function () {
      var data = {region: region, vmId: vm.id, adminPass: $scope.confirmPassword};
      $scope.isFormSubmiting = !0, HttpService.doPost(Config.urls.vm_password_change, data).success(function (data, status, headers, config) {
        1 === data.result ? ($modalInstance.close(data), WidgetService.notifySuccess("修改云主机密码成功")) : (WidgetService.notifyError(data.msgs[0] || "修改云主机密码失败"), $scope.isFormSubmiting = !1)
      })
    }
  }), controllerModule.controller("VmEditModalCtrl", function (Config, HttpService, WidgetService, Utility, ModelService, $scope, $modalInstance, region, vm) {
    $scope.vmName = vm.name, $scope.closeModal = function () {
      $modalInstance.dismiss("cancel")
    }, $scope.editVm = function () {
      var data = {region: region, vmId: vm.id, name: $scope.vmName};
      $scope.isFormSubmiting = !0, HttpService.doPost(Config.urls.vm_rename, data).success(function (data, status, headers, config) {
        1 === data.result ? ($modalInstance.close(data), WidgetService.notifySuccess("云主机编辑成功")) : (WidgetService.notifyError(data.msgs[0] || "云主机编辑失败"), $scope.isFormSubmiting = !1)
      })
    }
  })
}), define("controllers/VmCreateModalCtrl", ["controllers/app.controller"], function (controllerModule) {
  controllerModule.controller("VmCreateModalCtrl", function (Config, HttpService, WidgetService, Utility, CurrentContext, ModelService, $scope, $modalInstance, $timeout, $window, $sce, $httpParamSerializerJQLike, $modal, region, vmSnapshot) {
    $scope.isCalculatingPrice = !0, $scope.isDesignatedVmSnapshot = vmSnapshot ? !0 : !1, $scope.activeFlow = 1, $scope.vmName = "", $scope.imageActiveTab = $scope.isDesignatedVmSnapshot ? "snapshot" : "image", $scope.vmImageList = [], $scope.selectedVmImage = null, $scope.vmSnapshotList = [], $scope.selectedVmSnapshot = null, $scope.vmCpuList = [], $scope.selectedVmCpu = null, $scope.vmRamList = [], $scope.selectedVmRam = null, $scope.vmDiskTypeList = [], $scope.selectedVmDiskType = null, $scope.dataDiskVolume = 10, $scope.vmNetworkType = "primary", $scope.vmNetworkPublicIpModel = "now", $scope.networkBandWidth = 2, $scope.vmNetworkSubnetList = [], $scope.vmNetworkSubnetSelectorData = [], $scope.selectedVmNetworkSubnet = null, $scope.vmSecurityType = "keypair", $scope.vmSecurityKeypairList = [], $scope.vmSecurityKeypairSelectorData = [], $scope.selectedVmSecurityKeypair = null, $scope.vmSecurityPassword = {value: ""}, $scope.allVmBuyPeriods = Config.allBuyPeriods, $scope.vmBuyPeriod = $scope.allVmBuyPeriods[0], $scope.vmCount = 1, $scope.vmTotalPrice = "", $scope.firstStepToNextTab = function (event) {
      if (event.preventDefault(), $scope.vm_create_form.vm_name.$valid) {
        if ("snapshot" === $scope.imageActiveTab && !$scope.vmSnapshotList.length)return void WidgetService.notifyWarning("你还没有快照，请去创建或者选择系统镜像。");
        $scope.activeFlow = 2, $scope.hackRzSlider()
      }
    }, $scope.thirdStepToNextTab = function (event) {
      return event.preventDefault(), "private" != $scope.vmNetworkType || $scope.vmNetworkSubnetList.length ? ($scope.activeFlow = 4, void $scope.hackRzSlider()) : void WidgetService.notifyWarning("你还没有子网，请去私有网络中创建。")
    }, $scope.closeModal = function () {
      $modalInstance.dismiss("cancel")
    }, $scope.hackRzSlider = Utility.getRzSliderHack($scope), $scope.selectVmImage = function (vmImage) {
      $scope.selectedVmImage = vmImage
    }, $scope.isSelectedVmImage = function (vmImage) {
      return $scope.selectedVmImage === vmImage
    }, $scope.selectVmSnapshot = function (vmImage) {
      $scope.selectedVmSnapshot = vmImage
    }, $scope.isSelectedVmSnapshot = function (vmImage) {
      return $scope.selectedVmSnapshot === vmImage
    }, $scope.selectVmCpu = function (vmCpu) {
      $scope.selectedVmCpu = vmCpu
    }, $scope.isSelectedVmCpu = function (vmCpu) {
      return $scope.selectedVmCpu === vmCpu
    }, $scope.selectVmRam = function (vmRam) {
      $scope.selectedVmRam = vmRam
    }, $scope.isSelectedVmRam = function (vmRam) {
      return $scope.selectedVmRam === vmRam
    }, $scope.selectVmDiskType = function (vmDiskType) {
      vmDiskType.enable && ($scope.selectedVmDiskType = vmDiskType)
    }, $scope.isSelectedVmDiskType = function (vmDiskType) {
      return $scope.selectedVmDiskType === vmDiskType
    }, $scope.selectVmBuyPeriod = function (vmBuyPeriod) {
      $scope.vmBuyPeriod = vmBuyPeriod
    }, $scope.isSelectedVmBuyPeriod = function (vmBuyPeriod) {
      return $scope.vmBuyPeriod === vmBuyPeriod
    }, $scope.createVm = function () {
      var data = {
        region: region,
        name: $scope.vmName,
        imageId: "image" === $scope.imageActiveTab ? $scope.selectedVmImage.id : "",
        flavorId: selectedVmFlavor.id,
        volumeTypeId: $scope.selectedVmDiskType.id,
        volumeSize: $scope.dataDiskVolume,
        bindFloatingIp: "now" === $scope.vmNetworkPublicIpModel,
        sharedNetworkId: "primary" == $scope.vmNetworkType ? selectedVmSharedNetwork.id : "",
        bandWidth: $scope.networkBandWidth,
        adminPass: "password" == $scope.vmSecurityType ? $scope.vmSecurityPassword.value : "",
        keyPairName: "keypair" == $scope.vmSecurityType ? $scope.selectedVmSecurityKeypair.value : "",
        count: $scope.vmCount,
        privateSubnetId: "private" == $scope.vmNetworkType ? $scope.selectedVmNetworkSubnet.value : "",
        snapshotId: "snapshot" === $scope.imageActiveTab ? $scope.selectedVmSnapshot.id : "",
        order_time: $scope.vmBuyPeriod.toString()
      };
      $scope.isFormSubmiting = !0, HttpService.doPost(Config.urls.vm_buy, {
        paramsData: JSON.stringify(data),
        displayData: buildDisplayData()
      }).success(function (data, status, headers, config) {
        1 === data.result ? ($modalInstance.close(data), $window.location.href = "/payment/" + data.data + "/2") : (WidgetService.notifyError(data.msgs[0] || "创建云主机失败"), $scope.isFormSubmiting = !1)
      })
    }, $scope.openVmKeypairCreateModal = function (size) {
      var modalInstance = $modal.open({
        animation: $scope.animationsEnabled,
        templateUrl: "/static/apps/cloudvm/templates/vm-keypair-create-modal.html",
        controller: "VmKeypairCreateModalCtrl",
        size: size,
        backdrop: "static",
        keyboard: !1,
        resolve: {
          region: function () {
            return CurrentContext.regionId
          }
        }
      });
      modalInstance.result.then(function (resultData) {
        resultData && ($scope.keypairDownloadUrl = $sce.trustAsResourceUrl(Config.urls.keypair_create + "?" + $httpParamSerializerJQLike(resultData)), $timeout(function () {
          WidgetService.notifySuccess("密钥创建成功"), initVmSecurityKeypairSelector()
        }, 2e3))
      }, function () {
      })
    }, $scope.$watch("selectedVmCpu", function (value) {
      null != value && initVmRamSelector()
    }), $scope.$watch("selectedVmRam", function (value) {
      null != value && setSelectedVmFlavor()
    }), $scope.$watch(function () {
      return [$scope.vmNetworkType, $scope.vmNetworkPublicIpModel].join("_")
    }, function () {
      "primary" == $scope.vmNetworkType && "now" == $scope.vmNetworkPublicIpModel ? $scope.networkBandWidth = 2 : $scope.networkBandWidth = 0
    }), $scope.$watch(function () {
      return [$scope.selectedVmCpu, $scope.selectedVmRam, $scope.selectedVmDiskType && $scope.selectedVmDiskType.name || "", $scope.dataDiskVolume, $scope.networkBandWidth, $scope.vmCount, $scope.vmBuyPeriod].join("_")
    }, function (value) {
      $scope.selectedVmCpu && $scope.selectedVmRam && $scope.selectedVmDiskType && setVmPrice()
    });
    var flavorGroupData = null, selectedVmFlavor = null, selectedVmSharedNetwork = null, calculatePriceData = null, initComponents = function () {
      initVmImageSelector(), initVmSnapshotSelector(), initVmCpuSelector(), initVmDiskTypeSelector(), setSelectedVmSharedNetworkId(), initVmNetworkSubnetSelector(), initVmSecurityKeypairSelector()
    }, initVmImageSelector = function () {
      $scope.isDesignatedVmSnapshot || HttpService.doGet(Config.urls.image_list, {
        region: region,
        name: "",
        currentPage: "",
        recordsPerPage: ""
      }).then(function (data, status, headers, config) {
        $scope.vmImageList = data.data.data, $scope.selectedVmImage = $scope.vmImageList[0]
      })
    }, initVmSnapshotSelector = function () {
      $scope.isDesignatedVmSnapshot ? ($scope.vmSnapshotList.push(vmSnapshot), $scope.selectedVmSnapshot = $scope.vmSnapshotList[0]) : HttpService.doGet(Config.urls.snapshot_vm_list, {
        region: region,
        name: "",
        currentPage: "",
        recordsPerPage: ""
      }).then(function (data, status, headers, config) {
        $scope.vmSnapshotList = data.data.data, $scope.selectedVmSnapshot = $scope.vmSnapshotList[0]
      })
    }, initVmNetworkSubnetSelector = function () {
      HttpService.doGet(Config.urls.subnet_list, {
        region: region,
        name: "",
        currentPage: "",
        recordsPerPage: ""
      }).then(function (data, status, headers, config) {
        $scope.vmNetworkSubnetList = data.data.data, $scope.vmNetworkSubnetSelectorData = $scope.vmNetworkSubnetList.map(function (subnet) {
          return new ModelService.SelectModel(subnet.name + "(" + subnet.network.name + ")", subnet.id)
        }), $scope.selectedVmNetworkSubnet = $scope.vmNetworkSubnetSelectorData[0]
      })
    }, initVmSecurityKeypairSelector = function () {
      HttpService.doGet(Config.urls.keypair_list, {
        region: region,
        name: "",
        currentPage: "",
        recordsPerPage: ""
      }).then(function (data, status, headers, config) {
        data.data.data && data.data.data.length && ($scope.vmSecurityKeypairList = data.data.data,
          $scope.vmSecurityKeypairSelectorData.push(new ModelService.SelectModel("请选择密钥", "")), $scope.vmSecurityKeypairList.forEach(function (subnet) {
          $scope.vmSecurityKeypairSelectorData.push(new ModelService.SelectModel(subnet.name, subnet.name))
        }), $scope.selectedVmSecurityKeypair = $scope.vmSecurityKeypairSelectorData[0])
      })
    }, initVmCpuSelector = function () {
      HttpService.doGet(Config.urls.flavor_group_data.replace("{region}", region)).then(function (data, status, headers, config) {
        flavorGroupData = data.data;
        for (var cpu in flavorGroupData)$scope.vmCpuList.push(cpu), $scope.vmCpuList.sort(function (a, b) {
          return Number(a) - Number(b)
        });
        $scope.selectedVmCpu = $scope.vmCpuList[0]
      })
    }, initVmRamSelector = function () {
      $scope.vmRamList.splice(0, $scope.vmRamList.length);
      for (var ram in flavorGroupData[$scope.selectedVmCpu])$scope.vmRamList.push(ram), $scope.vmRamList.sort(function (a, b) {
        return Number(a) - Number(b)
      });
      $scope.selectedVmRam = $scope.vmRamList[0]
    }, setSelectedVmFlavor = function () {
      for (var disk in flavorGroupData[$scope.selectedVmCpu][$scope.selectedVmRam]) {
        selectedVmFlavor = flavorGroupData[$scope.selectedVmCpu][$scope.selectedVmRam][disk];
        break
      }
    }, initVmDiskTypeSelector = function () {
      HttpService.doGet(Config.urls.vm_disk_type, {region: region}).then(function (data, status, headers, config) {
        $scope.vmDiskTypeList = data.data, $scope.selectedVmDiskType = $scope.vmDiskTypeList[0]
      })
    }, setSelectedVmSharedNetworkId = function () {
      HttpService.doGet(Config.urls.vm_network_shared_list, {region: region}).then(function (data, status, headers, config) {
        selectedVmSharedNetwork = data.data[0]
      })
    }, setVmPrice = function () {
      var data = {
        region: region,
        order_time: $scope.vmBuyPeriod.toString(),
        order_num: $scope.vmCount.toString(),
        os_broadband: $scope.networkBandWidth.toString(),
        volumeType: $scope.selectedVmDiskType.name,
        volumeSize: $scope.dataDiskVolume.toString(),
        cpu_ram: $scope.selectedVmCpu + "_" + $scope.selectedVmRam
      };
      calculatePriceData = data, $scope.isCalculatingPrice = !0, HttpService.doPost(Config.urls.vm_calculate_price, data).success(function (data, status, headers, config) {
        $scope.isCalculatingPrice = !1, 1 === data.result ? $scope.vmTotalPrice = data.data : WidgetService.notifyError(data.msgs[0] || "计算总价失败")
      })
    }, buildDisplayData = function () {
      var part1 = [], part2 = [], part3 = [];
      return part1.push("配置/:" + selectedVmFlavor.vcpus + "核, " + selectedVmFlavor.ram / 1024 + "G内存"), "snapshot" === $scope.imageActiveTab ? part1.push("快照/:" + $scope.selectedVmSnapshot.name) : part1.push("镜像/:" + $scope.selectedVmImage.name), part1.push("地域/:" + CurrentContext.allRegionData.filter(function (regionData) {
          return regionData.id == region
        })[0].name), part1.push("网络类型/:" + ("primary" == $scope.vmNetworkType ? "基础网络" : "私有网络")), part2.push("类型/:" + $scope.selectedVmDiskType.name), part2.push("容量/:" + $scope.dataDiskVolume + "G数据盘"), "now" === $scope.vmNetworkPublicIpModel && "primary" === $scope.vmNetworkType && part3.push("带宽/:" + $scope.networkBandWidth + "Mbps"), [part1.join("/;"), part2.join("/;"), part3.join("/;")].join(";;")
    };
    initComponents()
  })
}), define("controllers/ConfirmModalCtrl", ["controllers/app.controller"], function (controllerModule) {
  controllerModule.controller("ConfirmModalCtrl", function ($scope, $modalInstance, message, title) {
    $scope.confirmMessage = message, $scope.title = title, $scope.closeModal = function () {
      $modalInstance.dismiss("cancel")
    }, $scope.ok = function () {
      $modalInstance.close(!0)
    }, $scope.cancel = function () {
      $modalInstance.dismiss("cancel")
    }
  })
}), define("controllers/VmDiskCrtl", ["controllers/app.controller"], function (controllerModule) {
  controllerModule.controller("VmDiskCrtl", ["$scope", "$interval", "$modal", "Config", "Utility", "HttpService", "WidgetService", "CurrentContext", function ($scope, $interval, $modal, Config, Utility, HttpService, WidgetService, CurrentContext) {
    $scope.searchName = "", $scope.diskList = [], $scope.currentPage = 1, $scope.totalItems = 0, $scope.pageSize = 10, $scope.operationBtn = {};
    var operationArry = new Array(6);
    $scope.onPageChange = function () {
      refreshDiskList()
    }, $scope.doSearch = function () {
      refreshDiskList()
    }, $scope.openVmDiskCreateModal = function (size) {
      var modalInstance = $modal.open({
        animation: $scope.animationsEnabled,
        templateUrl: "/static/apps/cloudvm/templates/vm-disk-create-modal.html",
        controller: "VmDiskCreateModalCtrl",
        size: size,
        backdrop: "static",
        keyboard: !1,
        resolve: {
          region: function () {
            return CurrentContext.regionId
          }, diskSnapshot: function () {
            return void 0
          }
        }
      });
      modalInstance.result.then(function (resultData) {
        resultData && 1 === resultData.result && refreshDiskList()
      }, function () {
      })
    }, $scope.openVmDiskAttachModal = function (size) {
      var checkedDisks = getCheckedDisk();
      if (1 !== checkedDisks.length)return void WidgetService.notifyWarning("请选中一个云硬盘");
      if ("available" !== checkedDisks[0].status)return void WidgetService.notifyWarning("云硬盘当前状态不可挂载到云主机");
      var modalInstance = $modal.open({
        animation: $scope.animationsEnabled,
        templateUrl: "VmDiskAttachModalTpl",
        controller: "VmDiskAttachModalCtrl",
        size: size,
        backdrop: "static",
        keyboard: !1,
        resolve: {
          region: function () {
            return CurrentContext.regionId
          }, disk: function () {
            return checkedDisks[0]
          }
        }
      });
      modalInstance.result.then(function (resultData) {
        resultData && 1 === resultData.result && refreshDiskList()
      }, function () {
      })
    }, $scope.detachDisk = function () {
      var checkedDisks = getCheckedDisk(), originalStatus = "";
      if (1 !== checkedDisks.length)return void WidgetService.notifyWarning("请选中一个云硬盘");
      if (originalStatus = checkedDisks[0].status, "in-use" !== checkedDisks[0].status)return void WidgetService.notifyWarning("云硬盘当前状态不可卸载");
      var data = {
        vmId: checkedDisks[0].attachments[0].vmId,
        volumeId: checkedDisks[0].id
      }, modalInstance = WidgetService.openConfirmModal("卸载云硬盘", "确定要从云主机（" + (checkedDisks[0].attachments[0] && checkedDisks[0].attachments[0].vmName) + "）卸载云硬盘（" + checkedDisks[0].name + "）吗？");
      modalInstance.result.then(function (resultData) {
        return resultData ? (WidgetService.notifyInfo("云硬盘卸载执行中..."), checkedDisks[0].status = "detaching", void HttpService.doPost(Config.urls.disk_detach.replace("{region}", checkedDisks[0].region), data).success(function (data, status, headers, config) {
          1 === data.result ? (modalInstance.close(data), checkedDisks[0].status = "available", WidgetService.notifySuccess("卸载云硬盘成功"), refreshDiskList()) : (checkedDisks[0].status = originalStatus, WidgetService.notifyError(data.msgs[0] || "卸载云硬盘失败"))
        })) : resultData
      }, function () {
      })
    }, $scope.deleteDisk = function () {
      var checkedDisks = getCheckedDisk(), originalStatus = "";
      if (1 !== checkedDisks.length)return void WidgetService.notifyWarning("请选中一个云硬盘");
      if (originalStatus = checkedDisks[0].status, "available" !== checkedDisks[0].status && "error" !== checkedDisks[0].status)return void WidgetService.notifyWarning("卸载云硬盘后执行删除操作");
      var data = {volumeId: checkedDisks[0].id}, modalInstance = WidgetService.openConfirmModal("删除云硬盘", "确定要删除云硬盘（" + checkedDisks[0].name + "）吗？");
      modalInstance.result.then(function (resultData) {
        return resultData ? (WidgetService.notifyInfo("云硬盘删除执行中..."), checkedDisks[0].status = "deleting", void HttpService.doPost(Config.urls.disk_delete.replace("{region}", checkedDisks[0].region), data).success(function (data, status, headers, config) {
          1 === data.result ? (checkedDisks[0].status = "deleted", modalInstance.close(data), WidgetService.notifySuccess("删除云硬盘成功"), refreshDiskList()) : (checkedDisks[0].status = originalStatus, WidgetService.notifyError(data.msgs[0] || "删除云硬盘失败"))
        })) : resultData
      }, function () {
      })
    }, $scope.openVmDiskEditModal = function (size) {
      var checkedDisks = getCheckedDisk();
      if (1 !== checkedDisks.length)return void WidgetService.notifyWarning("请选中一个云硬盘");
      if ("available" !== checkedDisks[0].status && "in-use" !== checkedDisks[0].status)return void WidgetService.notifyWarning("云硬盘当前状态不可编辑");
      var modalInstance = $modal.open({
        animation: $scope.animationsEnabled,
        templateUrl: "VmDiskEditModalTpl",
        controller: "VmDiskEditModalCtrl",
        size: size,
        backdrop: "static",
        keyboard: !1,
        resolve: {
          region: function () {
            return CurrentContext.regionId
          }, disk: function () {
            return checkedDisks[0]
          }
        }
      });
      modalInstance.result.then(function (resultData) {
        resultData && 1 === resultData.result && refreshDiskList()
      }, function () {
      })
    }, $scope.openVmDiskSnapshotCreateModal = function (size) {
      var checkedDisks = getCheckedDisk();
      if (1 !== checkedDisks.length)return void WidgetService.notifyWarning("请选中一个云硬盘");
      if ("available" !== checkedDisks[0].status && "in-use" !== checkedDisks[0].status)return void WidgetService.notifyWarning("云硬盘当前状态不可创建快照");
      var modalInstance = $modal.open({
        animation: $scope.animationsEnabled,
        templateUrl: "VmDiskSnapshotCreateModalTpl",
        controller: "VmDiskSnapshotCreateModalCtrl",
        size: size,
        backdrop: "static",
        keyboard: !1,
        resolve: {
          region: function () {
            return CurrentContext.regionId
          }, disk: function () {
            return checkedDisks[0]
          }
        }
      });
      modalInstance.result.then(function (resultData) {
        resultData && 1 === resultData.result
      }, function () {
      })
    }, $scope.isAllDiskChecked = function () {
      var unCheckedDisks = $scope.diskList.filter(function (disk) {
        return disk.checked === !1 || void 0 === disk.checked
      });
      return 0 == unCheckedDisks.length
    }, $scope.checkAllDisk = function () {
      $scope.isAllDiskChecked() ? $scope.diskList.forEach(function (disk) {
        disk.checked = !1
      }) : $scope.diskList.forEach(function (disk) {
        disk.checked = !0
      })
    }, $scope.checkDisk = function (disk) {
      disk.checked = disk.checked === !0 ? !1 : !0
    };
    var refreshDiskList = function () {
      operationArry = [];
      var queryParams = {name: $scope.searchName, currentPage: $scope.currentPage, recordsPerPage: $scope.pageSize};
      $scope.isListLoading = !0, HttpService.doGet(Config.urls.disk_list.replace("{region}", CurrentContext.regionId), queryParams).then(function (data, status, headers, config) {
        $scope.isListLoading = !1, $scope.diskList = data.data.data, $scope.totalItems = data.data.totalRecords, $scope.diskList.filter(function (disk) {
          return "creating" == disk.status
        }).forEach(function (disk) {
          var diskDetailUrl = Config.urls.disk_detail.replace("{region}", CurrentContext.regionId).replace("{volumeId}", disk.id), buildStatusInterval = $interval(function () {
            HttpService.doGet(diskDetailUrl).then(function (data, status, headers, config) {
              1 === data.result && "creating" != data.data.status && (disk.status = data.data.status, $interval.cancel(buildStatusInterval), refreshDiskList())
            })
          }, 5e3)
        })
      })
    }, getCheckedDisk = function () {
      return $scope.diskList.filter(function (item) {
        return item.checked === !0
      })
    }, watchStateChange = function () {
      var productInfo = {
        type: "disk",
        state: "status",
        other: [],
        operations: ["create", "createsnap", "attachdisk", "edit", "detachdisk", "delete"]
      };
      $scope.$watch(function () {
        return $scope.diskList.map(function (obj) {
          return obj.checked
        }).join(";")
      }, function () {
        var operationArraycopy = Utility.setOperationBtns($scope, $scope.diskList, productInfo, operationArry, Config), operaArraytemp = productInfo.operations;
        for (var k in operaArraytemp)$scope.operationBtn[operaArraytemp[k]] = operationArraycopy[k]
      })
    };
    refreshDiskList(), watchStateChange()
  }]), controllerModule.controller("VmDiskAttachModalCtrl", function (Config, HttpService, WidgetService, Utility, ModelService, $scope, $modalInstance, region, disk) {
    $scope.vmList = [], $scope.vmListSelectorData = [], $scope.selectedVm = null, $scope.closeModal = function () {
      $modalInstance.dismiss("cancel")
    }, $scope.attachDisk = function () {
      var data = {vmId: $scope.selectedVm.value, volumeId: disk.id}, originalStatus = disk.status;
      disk.status = "attaching", $scope.isFormSubmiting = !0, HttpService.doPost(Config.urls.disk_attach.replace("{region}", region), data).success(function (data, status, headers, config) {
        1 === data.result ? ($modalInstance.close(data), disk.status = "in-use", WidgetService.notifySuccess("云硬盘挂载成功")) : (WidgetService.notifyError(data.msgs[0] || "云硬盘挂载失败"), disk.status = originalStatus, $scope.isFormSubmiting = !1)
      })
    };
    var initComponents = function () {
      initVmSelector()
    }, initVmSelector = function () {
      HttpService.doGet(Config.urls.vm_list.replace("{region}", region), {
        name: "",
        currentPage: "",
        recordsPerPage: ""
      }).then(function (data, status, headers, config) {
        $scope.vmList = data.data.data, $scope.vmListSelectorData = $scope.vmList.map(function (vm) {
          return new ModelService.SelectModel(vm.name, vm.id)
        }), $scope.selectedVm = $scope.vmListSelectorData[0]
      })
    };
    initComponents()
  }), controllerModule.controller("VmDiskEditModalCtrl", function (Config, HttpService, WidgetService, Utility, ModelService, $scope, $modalInstance, region, disk) {
    $scope.diskName = disk.name, $scope.closeModal = function () {
      $modalInstance.dismiss("cancel")
    }, $scope.editDisk = function () {
      var data = {region: region, volumeId: disk.id, name: $scope.diskName, description: ""};
      $scope.isFormSubmiting = !0, HttpService.doPost(Config.urls.disk_edit, data).success(function (data, status, headers, config) {
        1 === data.result ? ($modalInstance.close(data), WidgetService.notifySuccess("云硬盘编辑成功")) : (WidgetService.notifyError(data.msgs[0] || "云硬盘编辑失败"), $scope.isFormSubmiting = !1)
      })
    }
  }), controllerModule.controller("VmDiskSnapshotCreateModalCtrl", function (Config, HttpService, WidgetService, Utility, ModelService, $scope, $modalInstance, region, disk) {
    $scope.diskSnapshotName = "", $scope.closeModal = function () {
      $modalInstance.dismiss("cancel")
    }, $scope.createDiskSnapshot = function () {
      var data = {region: region, volumeId: disk.id, name: $scope.diskSnapshotName};
      $scope.isFormSubmiting = !0, HttpService.doPost(Config.urls.snapshot_disk_create, data).success(function (data, status, headers, config) {
        1 === data.result ? ($modalInstance.close(data), WidgetService.notifySuccess("云硬盘快照创建成功，请前往快照页面查看。")) : (WidgetService.notifyError(data.msgs[0] || "云硬盘快照创建失败"), $scope.isFormSubmiting = !1)
      })
    }
  })
}), define("controllers/VmDiskCreateModalCtrl", ["controllers/app.controller"], function (controllerModule) {
  controllerModule.controller("VmDiskCreateModalCtrl", function (Config, HttpService, WidgetService, Utility, CurrentContext, ModelService, $scope, $modalInstance, $timeout, $window, region, diskSnapshot) {
    var DEFAULT_MIN_VOLUME = 10, initComponents = function () {
      initDiskTypeSelector(), initSnapshotTypeSelector()
    }, initDiskTypeSelector = function () {
      HttpService.doGet(Config.urls.vm_disk_type, {region: region}).then(function (data, status, headers, config) {
        $scope.diskTypeList = data.data, $scope.selectedDiskType = $scope.diskTypeList[0]
      })
    }, initSnapshotTypeSelector = function () {
      diskSnapshot ? ($scope.snapshotList.push(diskSnapshot), $scope.snapshotListSelectorData = [new ModelService.SelectModel(diskSnapshot.name, diskSnapshot.id)], $scope.selectedSnapshot = $scope.snapshotListSelectorData[0]) : ($scope.snapshotListSelectorData.push(new ModelService.SelectModel("请选择快照", "")), $scope.selectedSnapshot = $scope.snapshotListSelectorData[0], HttpService.doGet(Config.urls.snapshot_disk_list, {
        region: region,
        name: "",
        currentPage: "",
        recordsPerPage: ""
      }).then(function (data, status, headers, config) {
        $scope.snapshotList = data.data.data, $scope.snapshotList.forEach(function (snapshot) {
          $scope.snapshotListSelectorData.push(new ModelService.SelectModel(snapshot.name, snapshot.id))
        })
      }))
    }, setDiskPrice = function () {
      var data = {
        region: region,
        order_time: $scope.diskBuyPeriod.toString(),
        order_num: $scope.diskCount.toString(),
        volumeType: $scope.selectedDiskType.name,
        volumeSize: $scope.diskVolume.toString()
      };
      $scope.isCalculatingPrice = !0, HttpService.doPost(Config.urls.disk_calculate_price, data).success(function (data, status, headers, config) {
        $scope.isCalculatingPrice = !1, 1 === data.result ? $scope.totalPrice = data.data : WidgetService.notifyError(data.msgs[0] || "计算总价失败")
      })
    }, buildDisplayData = function () {
      var data = [];
      return data.push(["类型", $scope.selectedDiskType.name].join("/:")), data.push(["容量", $scope.diskVolume + "GB"].join("/:")), data.join("/;")
    };
    Utility.getRzSliderHack($scope)(), $scope.diskName = "", $scope.diskTypeList = [], $scope.selectedDiskType = null, $scope.snapshotList = [], $scope.snapshotListSelectorData = [], $scope.selectedSnapshot = null, $scope.diskVolume = $scope.diskMinVolume = DEFAULT_MIN_VOLUME, $scope.diskCount = 1, $scope.allDiskBuyPeriods = Config.allBuyPeriods, $scope.diskBuyPeriod = $scope.allDiskBuyPeriods[0], $scope.totalPrice = "", $scope.closeModal = function () {
      $modalInstance.dismiss("cancel")
    }, $scope.isSelectedDiskType = function (diskType) {
      return $scope.selectedDiskType === diskType
    }, $scope.selectDiskType = function (diskType) {
      diskType.enable && ($scope.selectedDiskType = diskType)
    }, $scope.createDisk = function () {
      var data = {
        region: region,
        name: $scope.diskName,
        description: "",
        volumeTypeId: $scope.selectedDiskType.id,
        size: $scope.diskVolume,
        volumeSnapshotId: $scope.selectedSnapshot.value,
        count: $scope.diskCount,
        order_time: $scope.diskBuyPeriod.toString()
      };
      $scope.isFormSubmiting = !0, HttpService.doPost(Config.urls.disk_buy, {
        paramsData: JSON.stringify(data),
        displayData: buildDisplayData()
      }).success(function (data, status, headers, config) {
        1 === data.result ? ($modalInstance.close(data), $window.location.href = "/payment/" + data.data + "/3") : (WidgetService.notifyError(data.msgs[0] || "创建云硬盘失败"), $scope.isFormSubmiting = !1)
      })
    }, $scope.$watch("selectedSnapshot", function (value) {
      if (value)if (value.value) {
        var snapshotItem = $scope.snapshotList.filter(function (snapshot) {
          return snapshot.id === value.value
        })[0];
        snapshotItem.size > DEFAULT_MIN_VOLUME ? $scope.diskMinVolume = snapshotItem.size : $scope.diskMinVolume = DEFAULT_MIN_VOLUME
      } else $scope.diskMinVolume = DEFAULT_MIN_VOLUME
    }), $scope.$watch(function () {
      return [$scope.selectedDiskType && $scope.selectedDiskType.name || "", $scope.diskCount, $scope.diskVolume, $scope.diskBuyPeriod].join("_")
    }, function (value) {
      $scope.selectedDiskType && $scope.diskVolume && $scope.diskCount && $scope.diskBuyPeriod && setDiskPrice()
    }), initComponents()
  })
}), define("controllers/vpcController/SubnetCreateModalCtrl", ["controllers/app.controller"], function (controllerModule) {
  controllerModule.controller("SubnetCreateModalCtrl", function (Config, HttpService, WidgetService, Utility, ModelService, CurrentContext, $scope, $modalInstance, $timeout, $window, subnetInfo) {
    $scope.subnetCreate = {name: ""}, $scope.closeModal = function () {
      $modalInstance.dismiss("cancel")
    }, $scope.createSubnet = function () {
      if ($scope.subnet_create_form.$valid) {
        var data = {
          region: subnetInfo.region,
          networkId: $scope.selectedVpc.value,
          name: $scope.subnetCreate.name,
          cidr: $scope.selectedCidr.value,
          autoGatewayIp: "false",
          enableDhcp: "true",
          gatewayIp: $scope.selectedCidr.relatedOption.gatewayIp
        };
        $scope.isFormSubmiting = !0, HttpService.doPost(Config.urls.subnet_create, data).success(function (data, status, headers, config) {
          1 === data.result ? ($modalInstance.close({result: 1}), WidgetService.notifySuccess(data.msgs[0] || "创建子网完成")) : ($scope.isFormSubmiting = !1, WidgetService.notifyError(data.msgs[0] || "创建子网失败"))
        })
      }
    };
    var initComponents = function () {
      initVpcSelector(), initSelector()
    }, initVpcSelector = function () {
      HttpService.doGet(Config.urls.vpc_list, {region: subnetInfo.region}).then(function (data, status, headers, config) {
        $scope.vpcList = data.data.data, $scope.vpcListSelectorData = $scope.vpcList.map(function (vpc) {
          return new ModelService.SelectModel(vpc.name, vpc.id)
        }), $scope.selectedVpc = $scope.vpcListSelectorData[0]
      })
    }, initSelector = function () {
      HttpService.doGet(Config.urls.subnet_option_list).then(function (data, status, headers, config) {
        $scope.cidrs = data.data, $scope.cidrListSelectorData = $scope.cidrs.map(function (cidr) {
          return new ModelService.SelectModel(cidr.cidr, cidr.cidr, {gatewayIp: cidr.gatewayIp})
        }), $scope.selectedCidr = $scope.cidrListSelectorData[0]
      })
    };
    initComponents()
  }), controllerModule.controller("SubnetCreateWithVpcModalCtrl", function (Config, HttpService, WidgetService, Utility, ModelService, CurrentContext, $scope, $modalInstance, $timeout, $window, subnetInfo) {
    $scope.subnetCreate = {name: "", vpcForSubnet: subnetInfo.vpcForSubnet}, $scope.closeModal = function () {
      $modalInstance.dismiss("cancel")
    }, $scope.createSubnet = function () {
      if ($scope.subnet_create_with_vpc_form.$valid) {
        var data = {
          region: subnetInfo.region,
          networkId: subnetInfo.vpcForSubnet.vpcId,
          name: $scope.subnetCreate.name,
          cidr: $scope.selectedCidr.value,
          autoGatewayIp: "false",
          enableDhcp: "true",
          gatewayIp: $scope.selectedCidr.relatedOption.gatewayIp
        };
        $scope.isFormSubmiting = !0, HttpService.doPost(Config.urls.subnet_create, data).success(function (data, status, headers, config) {
          1 === data.result ? ($modalInstance.close({result: 1}), WidgetService.notifySuccess(data.msgs[0] || "创建子网完成")) : ($scope.isFormSubmiting = !1, WidgetService.notifyError(data.msgs[0] || "创建子网失败"))
        })
      }
    };
    var initComponents = function () {
      initSelector()
    }, initSelector = function () {
      HttpService.doGet(Config.urls.subnet_option_list).then(function (data, status, headers, config) {
        $scope.cidrs = data.data, $scope.cidrListSelectorData = $scope.cidrs.map(function (cidr) {
          return new ModelService.SelectModel(cidr.cidr, cidr.cidr, {gatewayIp: cidr.gatewayIp})
        }), $scope.selectedCidr = $scope.cidrListSelectorData[0]
      })
    };
    initComponents()
  })
}), define("controllers/vpcController/SubnetAssociateRouterModalCtrl", ["controllers/app.controller"], function (controllerModule) {
  controllerModule.controller("AssociateRouterModalCtrl", function (Config, HttpService, WidgetService, Utility, ModelService, $scope, $modalInstance, $timeout, $window, subnetInfo) {
    $scope.associateSubnetName = subnetInfo.subnetName, $scope.closeModal = function () {
      $modalInstance.dismiss("cancel")
    }, $scope.associateRouter = function () {
      var data = {region: subnetInfo.region, subnetId: subnetInfo.subnetId, routerId: $scope.selectedRouter.value};
      $scope.isFormSubmiting = !0, HttpService.doPost(Config.urls.subnet_associate, data).success(function (data, status, headers, config) {
        1 === data.result ? ($modalInstance.close({result: 1}), WidgetService.notifySuccess(data.msgs[0] || "路由绑定成功")) : ($scope.isFormSubmiting = !1, $modalInstance.dismiss("cancel"), WidgetService.notifyError(data.msgs[0] || "路由绑定失败"))
      })
    };
    var initComponents = function () {
      initRouterSelector()
    }, initRouterSelector = function () {
      HttpService.doGet(Config.urls.router_list, {region: subnetInfo.region}).then(function (data, status, headers, config) {
        $scope.routerList = data.data.data, $scope.routerListSelectorData = $scope.routerList.map(function (router) {
          return new ModelService.SelectModel(router.name, router.id)
        }), $scope.selectedRouter = $scope.routerListSelectorData[0]
      })
    };
    initComponents()
  })
}), define("controllers/vpcController/SubnetEditModalCtrl", ["controllers/app.controller"], function (controllerModule) {
  controllerModule.controller("SubnetEditModalCtrl", function (Config, HttpService, WidgetService, Utility, CurrentContext, $scope, $modalInstance, $timeout, $window, subnetInfo) {
    $scope.subnetEdit = {name: subnetInfo.name}, $scope.closeModal = function () {
      $modalInstance.dismiss("cancel")
    }, $scope.editSubnet = function () {
      if ($scope.subnet_edit_form.$valid) {
        var data = {
          region: subnetInfo.region,
          subnetId: subnetInfo.subnetId,
          name: $scope.subnetEdit.name,
          gatewayIp: subnetInfo.gatewayIp,
          enableDhcp: !1
        };
        $scope.isFormSubmiting = !0, HttpService.doPost(Config.urls.subnet_edit, data).success(function (data, status, headers, config) {
          1 === data.result ? ($modalInstance.close({result: 1}), WidgetService.notifySuccess(data.msgs[0] || "编辑子网完成")) : ($scope.isFormSubmiting = !1, $modalInstance.dismiss("cancel"), WidgetService.notifyError(data.msgs[0] || "编辑子网失败"))
        })
      }
    }
  })
}), define("controllers/vpcController/VmVpcCtrl", ["controllers/app.controller"], function (controllerModule) {
  controllerModule.controller("VmVpcCtrl", ["$scope", "$interval", "$modal", "Config", "Utility", "HttpService", "WidgetService", "CurrentContext", function ($scope, $interval, $modal, Config, Utility, HttpService, WidgetService, CurrentContext) {
    $scope.tabShow = "vpc", $scope.vpcList = [], $scope.subnetList = [], $scope.vpc = $scope.subnet = {
      currentPage: 1,
      totalItems: 0,
      pageSize: 10
    }, $scope.operationBtn = {};
    var operationArry = [];
    $scope.vpc.onPageChange = function () {
      refreshVpcList()
    }, $scope.subnet.onPageChange = function () {
      refreshSubnetList()
    }, $scope.openVpcCreateModal = function (size) {
      var modalInstance = $modal.open({
        animation: $scope.animationsEnabled,
        templateUrl: "VpcCreateModalTpl",
        controller: "VpcCreateModalCtrl",
        size: size,
        backdrop: "static",
        keyboard: !1,
        resolve: {
          region: function () {
            return CurrentContext.regionId
          }
        }
      });
      modalInstance.result.then(function (resultData) {
        resultData && 1 === resultData.result && refreshVpcList()
      }, function () {
      })
    }, $scope.openSubnetCreateModal = function (size) {
      var modalInstance = $modal.open({
        animation: $scope.animationsEnabled,
        templateUrl: "SubnetCreateModalTpl",
        controller: "SubnetCreateModalCtrl",
        size: size,
        backdrop: "static",
        keyboard: !1,
        resolve: {
          subnetInfo: function () {
            return {region: CurrentContext.regionId}
          }
        }
      });
      modalInstance.result.then(function (resultData) {
        resultData && 1 === resultData.result && refreshSubnetList()
      }, function () {
      })
    }, $scope.subnetCreateWithVpc = function () {
      var checkedVpcs = getCheckedVpc();
      return 1 !== checkedVpcs.length ? void WidgetService.notifyWarning("请选中一个VPC") : void openSubnetCreateWithVpcModal("500", {
        region: checkedVpcs[0].region,
        vpcForSubnet: {vpcId: checkedVpcs[0].id, name: checkedVpcs[0].name}
      })
    }, $scope.editVpc = function () {
      var checkedVpcs = getCheckedVpc();
      return 1 !== checkedVpcs.length ? void WidgetService.notifyWarning("请选中一个VPC") : void openVmVpcEditModal("500", {
        region: checkedVpcs[0].region,
        vpcId: checkedVpcs[0].id,
        name: checkedVpcs[0].name
      })
    }, $scope.editSubnet = function () {
      var checkedSubnets = getCheckedSubnet();
      return 1 !== checkedSubnets.length ? void WidgetService.notifyWarning("请选中一个子网") : void openVmSubnetEditModal("500", {
        region: checkedSubnets[0].region,
        subnetId: checkedSubnets[0].id,
        name: checkedSubnets[0].name,
        gatewayIp: checkedSubnets[0].gatewayIp
      })
    }, $scope.associateRouter = function () {
      var checkedSubnets = getCheckedSubnet();
      return 1 !== checkedSubnets.length ? void WidgetService.notifyWarning("请选中一个子网") : null !== checkedSubnets[0].router ? void WidgetService.notifyWarning("当前子网已绑定路由器") : void associateRouterModal("500", {
        region: checkedSubnets[0].region,
        subnetId: checkedSubnets[0].id,
        subnetName: checkedSubnets[0].name
      })
    }, $scope.deleteVpc = function () {
      var checkedVpcs = getCheckedVpc();
      if (1 !== checkedVpcs.length)return void WidgetService.notifyWarning("请选中一个VPC");
      var data = {
        region: checkedVpcs[0].region,
        networkId: checkedVpcs[0].id
      }, modalInstance = WidgetService.openConfirmModal("删除VPC", "确定要删除VPC（" + checkedVpcs[0].name + "）吗？");
      modalInstance.result.then(function (resultData) {
        return resultData ? (WidgetService.notifyInfo("VPC删除执行中..."), void HttpService.doPost(Config.urls.vpc_delete, data).success(function (data, status, headers, config) {
          1 === data.result ? (modalInstance.close(data), WidgetService.notifySuccess("删除VPC成功"), refreshVpcList()) : WidgetService.notifyError(data.msgs[0] || "删除VPC失败")
        })) : resultData
      }, function () {
      })
    }, $scope.deleteSubnet = function () {
      var checkedSubnets = getCheckedSubnet();
      if (1 !== checkedSubnets.length)return void WidgetService.notifyWarning("请选中一个子网");
      var data = {
        region: checkedSubnets[0].region,
        subnetId: checkedSubnets[0].id
      }, modalInstance = WidgetService.openConfirmModal("删除子网", "确定要删除子网（" + checkedSubnets[0].name + "）吗？");
      modalInstance.result.then(function (resultData) {
        return resultData ? (WidgetService.notifyInfo("子网删除执行中..."), checkedSubnets[0].status = "DELETEING", void HttpService.doPost(Config.urls.subnet_delete, data).success(function (data, status, headers, config) {
          1 === data.result ? (checkedSubnets[0].status = "DELETED", modalInstance.close(data), WidgetService.notifySuccess("删除子网成功"), refreshSubnetList()) : WidgetService.notifyError(data.msgs[0] || "删除子网失败")
        })) : resultData
      }, function () {
      })
    }, $scope.unbundRouter = function () {
      var checkedSubnets = getCheckedSubnet();
      if (1 !== checkedSubnets.length)return void WidgetService.notifyWarning("请选中一个子网");
      if (null === checkedSubnets[0].router)return void WidgetService.notifyWarning("当前子网未绑定路由器");
      var data = {
        region: checkedSubnets[0].region,
        subnetId: checkedSubnets[0].id,
        routerId: checkedSubnets[0].router.id
      }, modalInstance = WidgetService.openConfirmModal("解绑路由器", "确定要对子网（" + checkedSubnets[0].name + "）路由器解绑吗？");
      modalInstance.result.then(function (resultData) {
        return resultData ? (WidgetService.notifyInfo("子网路由解绑执行中..."), checkedSubnets[0].status = "UNBUNDLING", void HttpService.doPost(Config.urls.subnet_remove, data).success(function (data, status, headers, config) {
          1 === data.result ? (checkedSubnets[0].status = "UNBUNDED", modalInstance.close(data), WidgetService.notifySuccess("子网路由解绑成功"), refreshSubnetList()) : WidgetService.notifyError(data.msgs[0] || "子网路由解绑失败")
        })) : resultData
      }, function () {
      })
    }, $scope.isAllVpcChecked = function () {
      var unCheckedVpcs = $scope.vpcList.filter(function (vpc) {
        return vpc.checked === !1 || void 0 === vpc.checked
      });
      return 0 == unCheckedVpcs.length
    }, $scope.checkAllVpc = function () {
      $scope.isAllVpcChecked() ? $scope.vpcList.forEach(function (vpc) {
        vpc.checked = !1
      }) : $scope.vpcList.forEach(function (vpc) {
        vpc.checked = !0
      })
    }, $scope.checkVpc = function (vpc) {
      vpc.checked = vpc.checked === !0 ? !1 : !0
    }, $scope.isAllSubnetChecked = function () {
      var unCheckedSubnets = $scope.subnetList.filter(function (subnet) {
        return subnet.checked === !1 || void 0 === subnet.checked
      });
      return 0 == unCheckedSubnets.length
    }, $scope.checkAllSubnet = function () {
      $scope.isAllSubnetChecked() ? $scope.subnetList.forEach(function (subnet) {
        subnet.checked = !1
      }) : $scope.subnetList.forEach(function (subnet) {
        subnet.checked = !0
      })
    }, $scope.checkSubnet = function (subnet) {
      subnet.checked = subnet.checked === !0 ? !1 : !0
    }, $scope.switchTabToSubnet = function () {
      $scope.tabShow = "subnet", refreshSubnetList(), watchStateChange()
    }, $scope.switchTabToVpc = function () {
      $scope.tabShow = "vpc", refreshVpcList()
    }, $scope.openSubnetAssociateVmModal = function (size) {
      var checkedSubnets = getCheckedSubnet();
      if (1 !== checkedSubnets.length)return void WidgetService.notifyWarning("请选中一个子网");
      var modalInstance = $modal.open({
        animation: $scope.animationsEnabled,
        templateUrl: "SubnetAssociateVmModalTpl",
        controller: "SubnetAssociateVmModalCtrl",
        size: size,
        backdrop: "static",
        keyboard: !1,
        resolve: {
          subnetInfo: function () {
            return checkedSubnets[0]
          }, region: function () {
            return CurrentContext.regionId
          }
        }
      });
      modalInstance.result.then(function (resultData) {
        resultData && 1 === resultData.result && refreshSubnetList()
      }, function () {
      })
    }, $scope.openSubnetDetachVmModal = function (size) {
      var checkedSubnets = getCheckedSubnet();
      if (1 !== checkedSubnets.length)return void WidgetService.notifyWarning("请选中一个子网");
      var modalInstance = $modal.open({
        animation: $scope.animationsEnabled,
        templateUrl: "SubnetDetachVmModalTpl",
        controller: "SubnetDetachVmModalCtrl",
        size: size,
        backdrop: "static",
        keyboard: !1,
        resolve: {
          subnetInfo: function () {
            return checkedSubnets[0]
          }, region: function () {
            return CurrentContext.regionId
          }
        }
      });
      modalInstance.result.then(function (resultData) {
        resultData && 1 === resultData.result && refreshSubnetList()
      }, function () {
      })
    };
    var refreshVpcList = function () {
      var queryParams = {
        region: CurrentContext.regionId,
        name: "",
        currentPage: $scope.vpc.currentPage,
        recordsPerPage: $scope.vpc.pageSize
      };
      $scope.isListLoading = !0, HttpService.doGet(Config.urls.vpc_list, queryParams).then(function (data, status, headers, config) {
        $scope.isListLoading = !1, $scope.vpcList = data.data.data, $scope.vpc.totalItems = data.data.totalRecords
      })
    }, refreshSubnetList = function () {
      operationArry = [];
      var queryParams = {
        region: CurrentContext.regionId,
        name: "",
        currentPage: $scope.subnet.currentPage,
        recordsPerPage: $scope.subnet.pageSize
      };
      $scope.isListLoading = !0, HttpService.doGet(Config.urls.subnet_list, queryParams).then(function (data, status, headers, config) {
        $scope.isListLoading = !1, $scope.subnetList = data.data.data, $scope.subnet.totalItems = data.data.totalRecords
      })
    }, getCheckedVpc = function () {
      return $scope.vpcList.filter(function (item) {
        return item.checked === !0
      })
    }, getCheckedSubnet = function () {
      return $scope.subnetList.filter(function (item) {
        return item.checked === !0
      })
    }, openVmVpcEditModal = function (size, data) {
      var modalInstance = $modal.open({
        animation: $scope.animationsEnabled,
        templateUrl: "VpcEditModalTpl",
        controller: "VpcEditModalCtrl",
        size: size,
        backdrop: "static",
        keyboard: !1,
        resolve: {
          vpcInfo: function () {
            return data
          }
        }
      });
      modalInstance.result.then(function (resultData) {
        resultData && 1 === resultData.result && refreshVpcList()
      }, function () {
      })
    }, openSubnetCreateWithVpcModal = function (size, data) {
      var modalInstance = $modal.open({
        animation: $scope.animationsEnabled,
        templateUrl: "SubnetCreateWithVpcModalTpl",
        controller: "SubnetCreateWithVpcModalCtrl",
        size: size,
        backdrop: "static",
        keyboard: !1,
        resolve: {
          subnetInfo: function () {
            return data
          }
        }
      });
      modalInstance.result.then(function (resultData) {
        resultData && 1 === resultData.result && refreshVpcList()
      }, function () {
      })
    }, associateRouterModal = function (size, data) {
      var modalInstance = $modal.open({
        animation: $scope.animationsEnabled,
        templateUrl: "AssociateRouterModalTpl",
        controller: "AssociateRouterModalCtrl",
        size: size,
        backdrop: "static",
        keyboard: !1,
        resolve: {
          subnetInfo: function () {
            return data
          }
        }
      });
      modalInstance.result.then(function (resultData) {
        resultData && 1 === resultData.result && refreshSubnetList()
      }, function () {
      })
    }, openVmSubnetEditModal = function (size, data) {
      var modalInstance = $modal.open({
        animation: $scope.animationsEnabled,
        templateUrl: "SubnetEditModalTpl",
        controller: "SubnetEditModalCtrl",
        size: size,
        backdrop: "static",
        keyboard: !1,
        resolve: {
          subnetInfo: function () {
            return data
          }
        }
      });
      modalInstance.result.then(function (resultData) {
        resultData && 1 === resultData.result && refreshSubnetList()
      }, function () {
      })
    }, watchStateChange = function () {
      var productInfo = {
        type: "subnet",
        state: "default",
        other: ["router"],
        operations: ["create", "bindvm", "bindrouter", "unbindvm", "unbindrouter", "edit", "delete"]
      };
      $scope.$watch(function () {
        return $scope.subnetList.map(function (obj) {
          return obj.checked;
        }).join(";")
      }, function () {
        var operationArraycopy = Utility.setOperationBtns($scope, $scope.subnetList, productInfo, operationArry, Config), operaArraytemp = productInfo.operations;
        for (var k in operaArraytemp)$scope.operationBtn[operaArraytemp[k]] = operationArraycopy[k]
      })
    };
    refreshVpcList()
  }]), controllerModule.controller("SubnetAssociateVmModalCtrl", function (Config, HttpService, WidgetService, Utility, CurrentContext, $scope, $modalInstance, $timeout, $window, region, subnetInfo) {
    $scope.associatedVmList = [], $scope.selectedAssociatedVm = [], $scope.subnetAssociateVm = {
      subnetName: subnetInfo.name,
      subnetId: subnetInfo.id
    }, $scope.closeModal = function () {
      $modalInstance.dismiss("cancel")
    }, $scope.selectAssociatedVmImage = function (vm) {
      -1 === $scope.selectedAssociatedVm.indexOf(vm) ? $scope.selectedAssociatedVm.push(vm) : $scope.selectedAssociatedVm.splice($scope.selectedAssociatedVm.indexOf(vm))
    }, $scope.isSelectedAssociatedVmImage = function (vm) {
      return -1 !== $scope.selectedAssociatedVm.indexOf(vm)
    }, $scope.associateVm = function () {
      if (0 === $scope.selectedAssociatedVm.length)return void WidgetService.notifyError("至少选择一个要添加的云主机");
      for (var data = {
        vmIds: "",
        region: region,
        subnetId: subnetInfo.id
      }, vmIds = [], i = 0, len = $scope.selectedAssociatedVm.length; len > i; i++)vmIds.push($scope.selectedAssociatedVm[i].id);
      data.vmIds = JSON.stringify(vmIds), $scope.isFormSubmiting = !0, HttpService.doPost(Config.urls.subnet_attach_vm, data).success(function (data, status, headers, config) {
        1 === data.result ? ($modalInstance.close({result: 1}), WidgetService.notifySuccess(data.msgs[0] || "添加云主机成功")) : ($scope.isFormSubmiting = !1, WidgetService.notifyError(data.msgs[0] || "添加云主机失败"))
      })
    };
    var initComponents = function () {
      initAssociatedVmList()
    }, initAssociatedVmList = function () {
      HttpService.doGet(Config.urls.could_attach_subnet_list, {
        region: region,
        subnetId: subnetInfo.id
      }).then(function (data, status, headers, config) {
        $scope.associatedVmList = data.data
      })
    };
    initComponents()
  }), controllerModule.controller("SubnetDetachVmModalCtrl", function (Config, HttpService, WidgetService, Utility, CurrentContext, $scope, $modalInstance, $timeout, $window, region, subnetInfo) {
    $scope.detachVmList = [], $scope.selectedDetachVm = [], $scope.subnetDetachVm = {
      subnetName: subnetInfo.name,
      subnetId: subnetInfo.id
    }, $scope.closeModal = function () {
      $modalInstance.dismiss("cancel")
    }, $scope.selectDetachVm = function (vm) {
      -1 === $scope.selectedDetachVm.indexOf(vm) ? $scope.selectedDetachVm.push(vm) : $scope.selectedDetachVm.splice($scope.selectedDetachVm.indexOf(vm))
    }, $scope.isSelectedDetachVmImage = function (vm) {
      return -1 !== $scope.selectedDetachVm.indexOf(vm)
    }, $scope.detachVm = function () {
      if (0 === $scope.selectedDetachVm.length)return void WidgetService.notifyError("至少选择一个要移除的云主机");
      for (var data = {
        vmIds: "",
        region: region,
        subnetId: subnetInfo.id
      }, vmIds = [], i = 0, len = $scope.selectedDetachVm.length; len > i; i++)vmIds.push($scope.selectedDetachVm[i].id);
      data.vmIds = JSON.stringify(vmIds), $scope.isFormSubmiting = !0, HttpService.doPost(Config.urls.subnet_detach_vm, data).success(function (data, status, headers, config) {
        1 === data.result ? ($modalInstance.close({result: 1}), WidgetService.notifySuccess(data.msgs[0] || "移除云主机成功")) : ($scope.isFormSubmiting = !1, WidgetService.notifyError(data.msgs[0] || "移除云主机失败"))
      })
    };
    var initComponents = function () {
      initDetachVmList()
    }, initDetachVmList = function () {
      HttpService.doGet(Config.urls.vm_attach_subnet_list, {
        region: region,
        subnetId: subnetInfo.id
      }).then(function (data, status, headers, config) {
        $scope.detachVmList = data.data
      })
    };
    initComponents()
  })
}), define("controllers/vpcController/VpcCreateModalCtrl", ["controllers/app.controller"], function (controllerModule) {
  controllerModule.controller("VpcCreateModalCtrl", function (Config, HttpService, WidgetService, Utility, ModelService, CurrentContext, $scope, $modalInstance, $timeout, $window, region) {
    $scope.vpcCreate = {name: "", isCreateSubnet: "false", subnet: {name: ""}}, $scope.closeModal = function () {
      $modalInstance.dismiss("cancel")
    }, $scope.createVpc = function () {
      if ($scope.vpc_create_form.$valid) {
        var vpcCreateUrl = "";
        if ("false" === $scope.vpcCreate.isCreateSubnet) {
          vpcCreateUrl = Config.urls.vpc_create;
          var data = {region: region, name: $scope.vpcCreate.name}
        } else {
          vpcCreateUrl = Config.urls.vpc_subnet_create;
          var data = {
            region: region,
            networkName: $scope.vpcCreate.name,
            subnetName: $scope.vpcCreate.subnet.name,
            cidr: $scope.vpcCreate.subnet.selectedCidr.value,
            autoGatewayIp: !1,
            gatewayIp: $scope.vpcCreate.subnet.selectedCidr.relatedOption.gatewayIp
          }
        }
        $scope.isFormSubmiting = !0, HttpService.doPost(vpcCreateUrl, data).success(function (data, status, headers, config) {
          1 === data.result ? ($modalInstance.close({result: 1}), WidgetService.notifySuccess(data.msgs[0] || "创建VPC完成")) : ($scope.isFormSubmiting = !1, WidgetService.notifyError(data.msgs[0] || "创建VPC失败"))
        })
      }
    };
    var initComponents = function () {
      initSelector()
    }, initSelector = function () {
      HttpService.doGet(Config.urls.subnet_option_list).then(function (data, status, headers, config) {
        $scope.vpcCreate.subnet.cidrs = data.data, $scope.vpcCreate.subnet.cidrListSelectorData = $scope.vpcCreate.subnet.cidrs.map(function (cidr) {
          return new ModelService.SelectModel(cidr.cidr, cidr.cidr, {gatewayIp: cidr.gatewayIp})
        }), $scope.vpcCreate.subnet.selectedCidr = $scope.vpcCreate.subnet.cidrListSelectorData[0]
      })
    };
    initComponents()
  })
}), define("controllers/vpcController/VpcEditModalCtrl", ["controllers/app.controller"], function (controllerModule) {
  controllerModule.controller("VpcEditModalCtrl", function (Config, HttpService, WidgetService, Utility, CurrentContext, $scope, $modalInstance, $timeout, $window, vpcInfo) {
    $scope.vpcEdit = {name: vpcInfo.name}, $scope.closeModal = function () {
      $modalInstance.dismiss("cancel")
    }, $scope.editVpc = function () {
      if ($scope.vpc_edit_form.$valid) {
        var data = {region: vpcInfo.region, networkId: vpcInfo.vpcId, name: $scope.vpcEdit.name};
        $scope.isFormSubmiting = !0, HttpService.doPost(Config.urls.vpc_edit, data).success(function (data, status, headers, config) {
          1 === data.result ? ($modalInstance.close({result: 1}), WidgetService.notifySuccess(data.msgs[0] || "编辑VPC完成")) : ($scope.isFormSubmiting = !1, $modalInstance.dismiss("cancel"), WidgetService.notifyError(data.msgs[0] || "编辑VPC失败"))
        })
      }
    }
  })
}), define("controllers/VmFloatIpCrtl", ["controllers/app.controller"], function (controllerModule) {
  controllerModule.controller("VmFloatIpCrtl", ["$scope", "$interval", "Config", "Utility", "$modal", "HttpService", "WidgetService", "CurrentContext", function ($scope, $interval, Config, Utility, $modal, HttpService, WidgetService, CurrentContext) {
    $scope.searchIpName = "", $scope.floatIpList = [], $scope.currentPage = 1, $scope.totalItems = 0, $scope.pageSize = 10, $scope.operationBtn = {};
    var operationArry = [];
    $scope.onPageChange = function () {
      refreshFloatIpList()
    }, $scope.doSearch = function () {
      refreshFloatIpList()
    }, $scope.isAllFipChecked = function () {
      var unCheckedFips = $scope.floatIpList.filter(function (fIp) {
        return fIp.checked === !1 || void 0 === fIp.checked
      });
      return 0 == unCheckedFips.length
    }, $scope.checkAllFip = function () {
      $scope.isAllFipChecked() ? $scope.floatIpList.forEach(function (fIp) {
        fIp.checked = !1
      }) : $scope.floatIpList.forEach(function (fIp) {
        fIp.checked = !0
      })
    }, $scope.checkFip = function (fIp) {
      fIp.checked = fIp.checked === !0 ? !1 : !0
    }, $scope.openIPcreateModal = function (size) {
      var modalInstance = $modal.open({
        animation: $scope.animationsEnabled,
        templateUrl: "/static/apps/cloudvm/templates/vm-floatip-create-modal.html",
        controller: "VmIPcreateModalCtrl",
        size: size,
        backdrop: "static",
        keyboard: !1,
        resolve: {
          region: function () {
            return CurrentContext.regionId
          }
        }
      });
      modalInstance.result.then(function (resultData) {
        resultData && 1 === resultData.result && refreshFloatIpList()
      }, function () {
      })
    }, $scope.bindVm = function (size) {
      var checkedIps = getCheckedIp();
      return 1 !== checkedIps.length ? void WidgetService.notifyWarning("请选中一个公网IP") : checkedIps[0].bindResource ? void WidgetService.notifyError("已绑定云主机") : void HttpService.doGet("/ecs/vm/unbindedfloatingip/list", {region: CurrentContext.regionId}).then(function (data) {
        if (1 == data.result)if (data.data.length > 0) {
          var modalInstance = $modal.open({
            animation: $scope.animationsEnabled,
            templateUrl: "VmIPbindVmMOdalTpl",
            controller: "VmIpBindVmModalCtrl",
            size: size,
            backdrop: "static",
            keyboard: !1,
            resolve: {
              region: function () {
                return CurrentContext.regionId
              }, bindfloatIp: function () {
                return checkedIps[0]
              }, avalibleVm: function () {
                return data.data
              }
            }
          });
          modalInstance.result.then(function (resultData) {
            resultData && 1 === resultData.result && refreshFloatIpList()
          }, function () {
          })
        } else WidgetService.notifyWarning("无可用云主机"); else WidgetService.notifyError(data.msgs[0] || "请求出错")
      })
    }, $scope.editIp = function (size) {
      var checkedIps = getCheckedIp();
      if (1 !== checkedIps.length)return void WidgetService.notifyWarning("请选中一个公网IP");
      var modalInstance = $modal.open({
        animation: $scope.animationsEnabled,
        templateUrl: "VmIpEditMOdalTpl",
        controller: "VmIpEditModalCtrl",
        size: size,
        backdrop: "static",
        keyboard: !1,
        resolve: {
          region: function () {
            return CurrentContext.regionId
          }, floatIp: function () {
            return checkedIps[0]
          }
        }
      });
      modalInstance.result.then(function (resultData) {
        resultData && 1 === resultData.result && refreshFloatIpList()
      }, function () {
      })
    }, $scope.unbindVm = function (size) {
      var checkedIps = getCheckedIp();
      if (1 !== checkedIps.length)return void WidgetService.notifyWarning("请选中一个公网IP");
      if (!checkedIps[0].bindResource)return void WidgetService.notifyWarning("未绑定云主机");
      var data = {
        region: checkedIps[0].region,
        vmId: checkedIps[0].bindResource.id,
        floatingIpId: checkedIps[0].id
      }, modalInstance = WidgetService.openConfirmModal("解绑云主机", "确定要（" + checkedIps[0].name + "）解绑云主机吗？");
      modalInstance.result.then(function (resultData) {
        return resultData ? (WidgetService.notifyInfo("解绑云主机执行中..."), checkedIps[0].status = "AVAILABLE", void HttpService.doPost(Config.urls.floatIp_unbindVm, data).success(function (data, status, headers, config) {
          1 === data.result ? (checkedIps[0].status = "AVAILABLE", modalInstance.close(data), WidgetService.notifySuccess("解绑云主机成功"), refreshFloatIpList()) : WidgetService.notifyError(data.msgs[0] || "解绑云主机失败")
        })) : resultData
      }, function () {
      })
    }, $scope.deleteIp = function (size) {
      var checkedIps = getCheckedIp();
      if (1 !== checkedIps.length)return void WidgetService.notifyWarning("请选中一个公网IP");
      var data = {
        region: checkedIps[0].region,
        floatingIpId: checkedIps[0].id
      }, modalInstance = WidgetService.openConfirmModal("删除公网IP", "确定要删除公网IP（" + checkedIps[0].name + "）吗？");
      modalInstance.result.then(function (resultData) {
        return resultData ? (WidgetService.notifyInfo("公网IP删除执行中..."), checkedIps[0].status = "DELETEING", void HttpService.doPost(Config.urls.floatIp_delete, data).success(function (data, status, headers, config) {
          1 === data.result ? (checkedIps[0].status = "DELETED", modalInstance.close(data), WidgetService.notifySuccess("删除公网IP成功"), refreshFloatIpList()) : WidgetService.notifyError(data.msgs[0] || "删除公网IP失败")
        })) : resultData
      }, function () {
      })
    };
    var refreshFloatIpList = function () {
      operationArry = [];
      var queryParams = {
        region: CurrentContext.regionId,
        name: $scope.searchIpName,
        currentPage: $scope.currentPage,
        recordsPerPage: $scope.pageSize
      };
      $scope.isListLoading = !0, HttpService.doGet(Config.urls.floatIP_list, queryParams).then(function (data, status, headers, config) {
        $scope.isListLoading = !1, $scope.floatIpList = data.data.data, $scope.totalItems = data.data.totalRecords
      })
    };
    getCheckedIp = function () {
      return $scope.floatIpList.filter(function (item) {
        return item.checked === !0
      })
    };
    var watchStateChange = function () {
      var productInfo = {
        type: "floatIp",
        state: "status",
        other: [],
        operations: ["bindVm", "edit", "detach", "delete"]
      };
      $scope.$watch(function () {
        return $scope.floatIpList.map(function (obj) {
          return obj.checked
        }).join(";")
      }, function () {
        var operationArraycopy = Utility.setOperationBtns($scope, $scope.floatIpList, productInfo, operationArry, Config), operaArraytemp = productInfo.operations;
        for (var k in operaArraytemp)$scope.operationBtn[operaArraytemp[k]] = operationArraycopy[k]
      })
    };
    refreshFloatIpList(), watchStateChange()
  }]), controllerModule.controller("VmIpBindVmModalCtrl", function (Config, HttpService, WidgetService, ModelService, Utility, CurrentContext, $scope, $modalInstance, $timeout, $window, region, bindfloatIp, avalibleVm) {
    $scope.floatIpName = bindfloatIp.name, $scope.floatIp = bindfloatIp.ipAddress, $scope.vmListSelectorData = avalibleVm.map(function (vm) {
      return new ModelService.SelectModel(vm.name, vm.id)
    }), $scope.selectedVm = $scope.vmListSelectorData[0], $scope.closeModal = function () {
      $modalInstance.dismiss("cancel")
    }, $scope.IpbingVm = function () {
      var data = {region: region, vmId: $scope.selectedVm.value, floatingIpId: bindfloatIp.id};
      $scope.isFormSubmiting = !0, HttpService.doPost(Config.urls.floatIp_bindVm, data).success(function (data) {
        0 == data.result ? ($scope.isFormSubmiting = !1, WidgetService.notifyError(data.msgs[0] || "绑定云主机出错")) : ($modalInstance.close(data), WidgetService.notifySuccess("绑定云主机成功"))
      })
    }
  }), controllerModule.controller("VmIpEditModalCtrl", function (Config, HttpService, WidgetService, Utility, CurrentContext, $scope, $modalInstance, $timeout, $window, region, floatIp) {
    Utility.getRzSliderHack($scope)(), $scope.ipName = floatIp.name, $scope.closeModal = function () {
      $modalInstance.dismiss("cancel")
    }, $scope.editIP = function () {
      if ($scope.vm_ip_edit_form.$valid) {
        var data = {region: region, floatingIpId: floatIp.id, name: $scope.ipName};
        $scope.isFormSubmiting = !0, HttpService.doPost(Config.urls.floatIP_edit, data).success(function (data, status, headers, config) {
          1 === data.result ? ($modalInstance.close(data), WidgetService.notifySuccess("编辑公网IP成功")) : ($scope.isFormSubmiting = !1, WidgetService.notifyError(data.msgs[0] || "编辑公网IP失败"))
        })
      }
    }
  })
}), define("controllers/routerController/VmRouterCtrl", ["controllers/app.controller"], function (controllerModule) {
  controllerModule.controller("VmRouterCtrl", ["$scope", "$interval", "$modal", "Config", "Utility", "HttpService", "WidgetService", "CurrentContext", function ($scope, $interval, $modal, Config, Utility, HttpService, WidgetService, CurrentContext) {
    $scope.routerList = [], $scope.currentPage = 1, $scope.totalItems = 0, $scope.pageSize = 10, $scope.operationBtn = {};
    var operationArry = [];
    $scope.onPageChange = function () {
      refreshRouterList()
    }, $scope.doSearch = function () {
      refreshRouterList()
    }, $scope.openVmRouterCreateModal = function (size) {
      var modalInstance = $modal.open({
        animation: $scope.animationsEnabled,
        templateUrl: "VmRouterCreateModalTpl",
        controller: "VmRouterCreateModalCtrl",
        size: size,
        backdrop: "static",
        keyboard: !1,
        resolve: {
          region: function () {
            return CurrentContext.regionId
          }
        }
      });
      modalInstance.result.then(function (resultData) {
        resultData && 1 === resultData.result && refreshRouterList()
      }, function () {
      })
    }, $scope.isAllRouterChecked = function () {
      var unCheckedRouters = $scope.routerList.filter(function (router) {
        return router.checked === !1 || void 0 === router.checked
      });
      return 0 == unCheckedRouters.length
    }, $scope.checkAllRouter = function () {
      $scope.isAllRouterChecked() ? $scope.routerList.forEach(function (router) {
        router.checked = !1
      }) : $scope.routerList.forEach(function (router) {
        router.checked = !0
      })
    }, $scope.checkRouter = function (router) {
      router.checked = router.checked === !0 ? !1 : !0
    }, $scope.editRouter = function () {
      var checkedRouters = getCheckedRouter();
      return 1 !== checkedRouters.length ? void WidgetService.notifyWarning("请选中一个路由器") : void openVmRouterEditModal("500", {
        region: checkedRouters[0].region,
        routerId: checkedRouters[0].id,
        routerName: checkedRouters[0].name,
        publicNetworkGatewayEnable: checkedRouters[0].publicNetworkGatewayEnable,
        publicNetworkId: checkedRouters[0].carrier ? checkedRouters[0].carrier.id : null
      })
    }, $scope.associateSubnet = function () {
      var checkedRouters = getCheckedRouter();
      return 1 !== checkedRouters.length ? void WidgetService.notifyWarning("请选中一个路由器") : void associateSubnetModal("500", {
        region: checkedRouters[0].region,
        routerId: checkedRouters[0].id,
        routerName: checkedRouters[0].name
      })
    }, $scope.removeSubnet = function () {
      var checkedRouters = getCheckedRouter();
      return 1 !== checkedRouters.length ? void WidgetService.notifyWarning("请选中一个路由器") : (console.log(checkedRouters[0].subnets), 0 === checkedRouters[0].subnets.length ? void WidgetService.notifyWarning("选择的路由未关联子网") : void removeSubnetModal("500", {
        region: checkedRouters[0].region,
        routerId: checkedRouters[0].id,
        routerName: checkedRouters[0].name,
        subnets: checkedRouters[0].subnets
      }))
    }, $scope.deleteRouter = function () {
      var checkedRouters = getCheckedRouter();
      if (1 !== checkedRouters.length)return void WidgetService.notifyWarning("请选中一个路由器");
      var data = {
        region: checkedRouters[0].region,
        routerId: checkedRouters[0].id
      }, modalInstance = WidgetService.openConfirmModal("删除路由器", "确定要删除路由器（" + checkedRouters[0].name + "）吗？");
      modalInstance.result.then(function (resultData) {
        return resultData ? (WidgetService.notifyInfo("路由器删除执行中..."), checkedRouters[0].status = "DELETEING", void HttpService.doPost(Config.urls.router_delete, data).success(function (data, status, headers, config) {
          1 === data.result ? (checkedRouters[0].status = "DELETED", modalInstance.close(data), WidgetService.notifySuccess("删除路由器成功"), refreshRouterList()) : WidgetService.notifyError(data.msgs[0] || "删除路由器失败")
        })) : resultData
      }, function () {
      })
    }, $scope.functionDisable = function () {
      WidgetService.notifyWarning("此功能尚未开放,敬请期待...")
    };
    var refreshRouterList = function () {
      operationArry = [];
      var queryParams = {
        name: $scope.searchRouterName,
        currentPage: $scope.currentPage,
        recordsPerPage: $scope.pageSize
      };
      $scope.isListLoading = !0, HttpService.doGet(Config.urls.router_list, queryParams).then(function (data, status, headers, config) {
        $scope.isListLoading = !1, $scope.routerList = data.data.data, $scope.totalItems = data.data.totalRecords
      })
    }, getCheckedRouter = function () {
      return $scope.routerList.filter(function (item) {
        return item.checked === !0
      })
    }, openVmRouterEditModal = function (size, data) {
      var modalInstance = $modal.open({
        animation: $scope.animationsEnabled,
        templateUrl: "VmRouterEditModalTpl",
        controller: "VmRouterEditModalCtrl",
        size: size,
        backdrop: "static",
        keyboard: !1,
        resolve: {
          routerInfo: function () {
            return data
          }
        }
      });
      modalInstance.result.then(function (resultData) {
        resultData && 1 === resultData.result && refreshRouterList()
      }, function () {
      })
    }, associateSubnetModal = function (size, data) {
      var modalInstance = $modal.open({
        animation: $scope.animationsEnabled,
        templateUrl: "AssociateSubnetModalTpl",
        controller: "AssociateSubnetModalCtrl",
        size: size,
        backdrop: "static",
        keyboard: !1,
        resolve: {
          routerInfo: function () {
            return data
          }
        }
      });
      modalInstance.result.then(function (resultData) {
        resultData && 1 === resultData.result && refreshRouterList()
      }, function () {
      })
    }, removeSubnetModal = function (size, data) {
      var modalInstance = $modal.open({
        animation: $scope.animationsEnabled,
        templateUrl: "RemoveSubnetModalTpl",
        controller: "RemoveSubnetModalCtrl",
        size: size,
        backdrop: "static",
        keyboard: !1,
        resolve: {
          routerInfo: function () {
            return data
          }
        }
      });
      modalInstance.result.then(function (resultData) {
        resultData && 1 === resultData.result && refreshRouterList()
      }, function () {
      })
    }, watchStateChange = function () {
      var productInfo = {
        type: "router",
        state: "status",
        other: ["subnets"],
        operations: ["create", "bindsubnet", "edit", "removesubnet", "delete"]
      };
      $scope.$watch(function () {
        return $scope.routerList.map(function (obj) {
          return obj.checked
        }).join(";")
      }, function () {
        var operationArraycopy = Utility.setOperationBtns($scope, $scope.routerList, productInfo, operationArry, Config), operaArraytemp = productInfo.operations;
        for (var k in operaArraytemp)$scope.operationBtn[operaArraytemp[k]] = operationArraycopy[k]
      })
    };
    refreshRouterList(), watchStateChange()
  }])
}), define("controllers/routerController/VmRouterCreateModalCtrl", ["controllers/app.controller"], function (controllerModule) {
  controllerModule.controller("VmRouterCreateModalCtrl", function (Config, HttpService, WidgetService, Utility, CurrentContext, $scope, $modalInstance, $timeout, $window, region) {
    $scope.routerName = "", $scope.enablePublicNetworkGateway = "true", $scope.publicNetworkId = "", $scope.allRouteBuyPeriods = Config.allBuyPeriods, $scope.routeBuyPeriod = $scope.allRouteBuyPeriods[0], $scope.closeModal = function () {
      $modalInstance.dismiss("cancel")
    }, $scope.createRouter = function () {
      var data = {
        region: region,
        name: $scope.routerName,
        enablePublicNetworkGateway: $scope.enablePublicNetworkGateway,
        publicNetworkId: $scope.publicNetworkId,
        count: 1,
        order_time: $scope.routeBuyPeriod.toString()
      };
      $scope.isFormSubmiting = !0, HttpService.doPost(Config.urls.router_buy, {
        paramsData: JSON.stringify(data),
        displayData: buildDisplayData()
      }).success(function (data, status, headers, config) {
        1 === data.result ? ($modalInstance.close(data), $window.location.href = "/payment/" + data.data + "/5") : (WidgetService.notifyError(data.msgs[0] || "创建路由器失败"), $scope.isFormSubmiting = !1)
      })
    }, $scope.$watch("routeBuyPeriod", function (value) {
      value && setRoutePrice()
    });
    var initComponents = function () {
      initRouterTypeSelector()
    }, initRouterTypeSelector = function () {
      HttpService.doGet(Config.urls.network_public_list, {region: region}).then(function (data, status, headers, config) {
        $scope.publicNetworkId = data.data[0].id
      })
    }, setRoutePrice = function () {
      var data = {region: region, order_time: $scope.routeBuyPeriod.toString(), order_num: "1", os_router: "router"};
      $scope.isCalculatingPrice = !0, HttpService.doPost(Config.urls.route_calculate_price, data).success(function (data, status, headers, config) {
        $scope.isCalculatingPrice = !1, 1 === data.result ? $scope.totalPrice = data.data : WidgetService.notifyError(data.msgs[0] || "计算总价失败")
      })
    }, buildDisplayData = function () {
      var data = [];
      return data.push(["公网网关", "true" === $scope.enablePublicNetworkGateway ? "开启" : "关闭"].join("/:")), data.join("/;")
    };
    initComponents()
  })
}), define("controllers/routerController/VmRouterEditModalCtrl", ["controllers/app.controller"], function (controllerModule) {
  controllerModule.controller("VmRouterEditModalCtrl", function (Config, HttpService, WidgetService, Utility, CurrentContext, $scope, $modalInstance, $timeout, $window, routerInfo) {
    function getPublicNetworkId() {
      HttpService.doGet(Config.urls.network_public_list, {region: routerInfo.region}).then(function (data, status, headers, config) {
        routerInfo.publicNetworkId = data.data[0].id
      })
    }

    $scope.editRouterName = routerInfo.routerName, $scope.editEnablePublicNetworkGateway = routerInfo.publicNetworkGatewayEnable, routerInfo.publicNetworkGatewayEnable === !1 && getPublicNetworkId(), $scope.closeModal = function () {
      $modalInstance.dismiss("cancel")
    }, $scope.editRouter = function () {
      if ($scope.vm_router_edit_form.$valid) {
        var data = {
          region: routerInfo.region,
          routerId: routerInfo.routerId,
          name: $scope.editRouterName,
          enablePublicNetworkGateway: $scope.editEnablePublicNetworkGateway,
          publicNetworkId: routerInfo.publicNetworkId
        };
        $scope.isFormSubmiting = !0, HttpService.doPost(Config.urls.router_edit, data).success(function (data, status, headers, config) {
          1 === data.result ? ($modalInstance.close({result: 1}), WidgetService.notifySuccess(data.msgs[0] || "编辑路由器完成")) : ($scope.isFormSubmiting = !1, $modalInstance.dismiss("cancel"), WidgetService.notifyError(data.msgs[0] || "编辑路由器失败"))
        })
      }
    }
  })
}), define("controllers/routerController/RouterAssociateSubnetModalCtrl", ["controllers/app.controller"], function (controllerModule) {
  controllerModule.controller("AssociateSubnetModalCtrl", function (Config, HttpService, WidgetService, Utility, ModelService, $scope, $modalInstance, $timeout, $window, routerInfo) {
    $scope.associateRouterName = routerInfo.routerName, $scope.closeModal = function () {
      $modalInstance.dismiss("cancel")
    }, $scope.associateSubnet = function () {
      var data = {region: routerInfo.region, routerId: routerInfo.routerId, subnetId: $scope.selectedSubnet.value};
      $scope.isFormSubmiting = !0, HttpService.doPost(Config.urls.subnet_associate, data).success(function (data, status, headers, config) {
        1 === data.result ? ($modalInstance.close({result: 1}), WidgetService.notifySuccess(data.msgs[0] || "子网关联成功")) : ($scope.isFormSubmiting = !1, $modalInstance.dismiss("cancel"), WidgetService.notifyError(data.msgs[0] || "子网关联失败"))
      })
    };
    var initComponents = function () {
      initSubnetSelector()
    }, initSubnetSelector = function () {
      HttpService.doGet(Config.urls.available_for_router_subnet_list, {region: routerInfo.region}).then(function (data, status, headers, config) {
        var vpcList = data.data;
        $scope.subnetList = [];
        for (var i = 0, len = vpcList.length; len > i; i++)!function (vpc) {
          for (var subnets = vpc.subnets, i = 0, len = subnets.length; len > i; i++)subnets[i].nameWithVpc = subnets[i].name + "(" + vpc.name + ")", $scope.subnetList.push(subnets[i])
        }(vpcList[i]);
        $scope.subnetListSelectorData = $scope.subnetList.map(function (subnet) {
          return new ModelService.SelectModel(subnet.nameWithVpc, subnet.id)
        }), $scope.selectedSubnet = $scope.subnetListSelectorData[0]
      })
    };
    initComponents()
  }), controllerModule.controller("RemoveSubnetModalCtrl", function (Config, HttpService, WidgetService, Utility, ModelService, $scope, $modalInstance, $timeout, $window, routerInfo) {
    $scope.routerName = routerInfo.routerName, $scope.subnetList = routerInfo.subnets, $scope.subnetListSelectorData = $scope.subnetList.map(function (subnet) {
      return new ModelService.SelectModel(subnet.name, subnet.id)
    }), $scope.selectedSubnet = $scope.subnetListSelectorData[0], $scope.closeModal = function () {
      $modalInstance.dismiss("cancel")
    }, $scope.removeSubnet = function () {
      var data = {region: routerInfo.region, routerId: routerInfo.routerId, subnetId: $scope.selectedSubnet.value};
      $scope.isFormSubmiting = !0, HttpService.doPost(Config.urls.subnet_remove, data).success(function (data, status, headers, config) {
        1 === data.result ? ($modalInstance.close({result: 1}), WidgetService.notifySuccess(data.msgs[0] || "解除关联子网成功")) : ($scope.isFormSubmiting = !1, $modalInstance.dismiss("cancel"), WidgetService.notifyError(data.msgs[0] || "解除关联子网失败"))
      })
    }
  })
}), define("controllers/VmIPcreateModalCtrl", ["controllers/app.controller"], function (controllerModule) {
  controllerModule.controller("VmIPcreateModalCtrl", function (Config, HttpService, WidgetService, Utility, CurrentContext, $scope, $modalInstance, $timeout, $window, region) {
    Utility.getRzSliderHack($scope)(), $scope.networkBandWidth = 2, $scope.ipName = "", $scope.ipCount = 1, $scope.carrierList = "", $scope.selectedCarrier = null, $scope.allFloatipBuyPeriods = Config.allBuyPeriods, $scope.floatipBuyPeriod = $scope.allFloatipBuyPeriods[0], $scope.totalPrice = "", HttpService.doGet("/osn/network/public/list", {region: region}).then(function (data) {
      $scope.carrierList = data.data
    }), $scope.closeModal = function () {
      $modalInstance.dismiss("cancel")
    }, $scope.selectCarrier = function (carrier) {
      $scope.selectedCarrier = carrier
    }, $scope.isSelectedCarrier = function (carrier) {
      return $scope.selectedCarrier = carrier
    }, $scope.createIP = function () {
      var data = {
        region: region,
        name: $scope.ipName,
        publicNetworkId: $scope.selectedCarrier.id,
        bandWidth: $scope.networkBandWidth,
        count: $scope.ipCount,
        order_time: $scope.floatipBuyPeriod.toString()
      };
      $scope.isFormSubmiting = !0, HttpService.doPost(Config.urls.floatip_buy, {
        paramsData: JSON.stringify(data),
        displayData: buildDisplayData()
      }).success(function (data, status, headers, config) {
        1 === data.result ? ($modalInstance.close(data), $window.location.href = "/payment/" + data.data + "/4") : (WidgetService.notifyError(data.msgs[0] || "创建公网IP失败"), $scope.isFormSubmiting = !1)
      })
    }, $scope.$watch(function () {
      return [$scope.ipCount, $scope.networkBandWidth, $scope.floatipBuyPeriod].join("_")
    }, function (value) {
      $scope.ipCount && $scope.networkBandWidth && $scope.floatipBuyPeriod && setFloatipPrice()
    });
    var setFloatipPrice = function () {
      var data = {
        region: region,
        order_time: $scope.floatipBuyPeriod.toString(),
        order_num: $scope.ipCount.toString(),
        os_broadband: $scope.networkBandWidth.toString()
      };
      $scope.isCalculatingPrice = !0, HttpService.doPost(Config.urls.floatip_calculate_price, data).success(function (data, status, headers, config) {
        $scope.isCalculatingPrice = !1, 1 === data.result ? $scope.totalPrice = data.data : WidgetService.notifyError(data.msgs[0] || "计算总价失败")
      })
    }, buildDisplayData = function () {
      var data = [];
      return data.push(["带宽", $scope.networkBandWidth + "Mbps"].join("/:")), data.join("/;")
    }
  })
}), define("controllers/VmSnapshotCrtl", ["controllers/app.controller"], function (controllerModule) {
  controllerModule.controller("VmSnapshotCrtl", ["$scope", "$interval", "$modal", "Config", "HttpService", "WidgetService", "CurrentContext", function ($scope, $interval, $modal, Config, HttpService, WidgetService, CurrentContext) {
    var refreshSnapshotList = function () {
      var isVmTabActive = isVmSnapshotTabActive(), queryParams = {
        region: CurrentContext.regionId,
        name: "",
        currentPage: isVmTabActive ? $scope.vmCurrentPage : $scope.diskCurrentPage,
        recordsPerPage: $scope.pageSize
      }, url = isVmTabActive ? Config.urls.snapshot_vm_list : Config.urls.snapshot_disk_list;
      $scope.isListLoading = !0, HttpService.doGet(url, queryParams).then(function (data, status, headers, config) {
        $scope.isListLoading = !1, isVmTabActive ? ($scope.vmSnapshotList = data.data.data, $scope.vmTotalItems = data.data.totalRecords, $scope.vmSnapshotList.filter(function (vmSnapshot) {
          return "QUEUED" == vmSnapshot.status
        }).forEach(function (vmSnapshot) {
          var vmSnapshotDetailUrl = Config.urls.snapshot_vm_detail, queryData = {
            region: CurrentContext.regionId,
            vmSnapshotId: vmSnapshot.id
          }, pendingStatusInterval = $interval(function () {
            HttpService.doGet(vmSnapshotDetailUrl, queryData).then(function (data, status, headers, config) {
              1 === data.result && "QUEUED" != data.data.status && (vmSnapshot.status = data.data.status, $interval.cancel(pendingStatusInterval), refreshSnapshotList())
            })
          }, 5e3)
        })) : ($scope.diskSnapshotList = data.data.data, $scope.diskTotalItems = data.data.totalRecords)
      })
    }, getCheckedSnapshot = function () {
      var snapshotList = getCurrentSnapshotList();
      return snapshotList.filter(function (item) {
        return item.checked === !0
      })
    }, getCurrentSnapshotList = function () {
      return isVmSnapshotTabActive() ? $scope.vmSnapshotList : $scope.diskSnapshotList
    }, isVmSnapshotTabActive = function () {
      return "VmSnapshot" === $scope.tabShow
    };
    $scope.tabShow = "VmSnapshot", $scope.vmSnapshotList = [], $scope.diskSnapshotList = [], $scope.vmCurrentPage = 1, $scope.vmTotalItems = 0, $scope.diskCurrentPage = 1, $scope.diskTotalItems = 0, $scope.pageSize = 10, $scope.refreshSnapshotList = refreshSnapshotList, $scope.onPageChange = function () {
      refreshSnapshotList()
    }, $scope.openVmDiskCreateModal = function (size) {
      var checkedSnapshots = getCheckedSnapshot();
      if (1 !== checkedSnapshots.length)return void WidgetService.notifyWarning("请选中一个云硬盘快照");
      if ("available" !== checkedSnapshots[0].status)return void WidgetService.notifyWarning("快照当前状态不可创建云硬盘");
      var modalInstance = $modal.open({
        animation: $scope.animationsEnabled,
        templateUrl: "/static/apps/cloudvm/templates/vm-disk-create-modal.html",
        controller: "VmDiskCreateModalCtrl",
        size: size,
        backdrop: "static",
        keyboard: !1,
        resolve: {
          region: function () {
            return CurrentContext.regionId
          }, diskSnapshot: function () {
            return checkedSnapshots[0]
          }
        }
      });
      modalInstance.result.then(function (resultData) {
        resultData && 1 === resultData.result
      }, function () {
      })
    }, $scope.openVmCreateModal = function (size) {
      var checkedSnapshots = getCheckedSnapshot();
      if (1 !== checkedSnapshots.length)return void WidgetService.notifyWarning("请选中一个云主机快照");
      if ("ACTIVE" !== checkedSnapshots[0].status)return void WidgetService.notifyWarning("快照当前状态不可创建云主机");
      var modalInstance = $modal.open({
        animation: $scope.animationsEnabled,
        templateUrl: "/static/apps/cloudvm/templates/vm-create-modal.html",
        controller: "VmCreateModalCtrl",
        size: size,
        backdrop: "static",
        keyboard: !1,
        resolve: {
          region: function () {
            return CurrentContext.regionId
          }, vmSnapshot: function () {
            return checkedSnapshots[0]
          }, loadAllRegionData: function ($q, CurrentContext) {
            if (CurrentContext.allRegionData)return !0;
            var deferred = $q.defer();
            return HttpService.doGet(Config.urls.region_list).then(function (data, status, headers, config) {
              CurrentContext.allRegionData = data.data, deferred.resolve(!0)
            }), deferred.promise
          }
        }
      });
      modalInstance.result.then(function (resultData) {
        resultData && 1 === resultData.result
      }, function () {
      })
    }, $scope.deleteDiskSnapshot = function () {
      var checkedSnapshots = getCheckedSnapshot(), originalStatus = "";
      if (1 !== checkedSnapshots.length)return void WidgetService.notifyWarning("请选中一个云硬盘快照");
      if (originalStatus = checkedSnapshots[0].status, "available" !== checkedSnapshots[0].status && "error" !== checkedSnapshots[0].status)return void WidgetService.notifyWarning("云硬盘快照当前状态不可删除");
      var data = {
        region: checkedSnapshots[0].region,
        snapshotId: checkedSnapshots[0].id
      }, modalInstance = WidgetService.openConfirmModal("删除云硬盘快照", "确定要删除云硬盘快照（" + checkedSnapshots[0].name + "）吗？");
      modalInstance.result.then(function (resultData) {
        return resultData ? (WidgetService.notifyInfo("云硬盘快照删除执行中..."), checkedSnapshots[0].status = "deleting", void HttpService.doPost(Config.urls.snapshot_disk_delete, data).success(function (data, status, headers, config) {
          1 === data.result ? (checkedSnapshots[0].status = "deleted", modalInstance.close(data), WidgetService.notifySuccess("删除云硬盘快照成功"), refreshSnapshotList()) : (checkedSnapshots[0].status = originalStatus, WidgetService.notifyError(data.msgs[0] || "删除云硬盘快照失败"))
        })) : resultData
      }, function () {
      })
    }, $scope.deleteVmSnapshot = function () {
      var checkedSnapshots = getCheckedSnapshot(), originalStatus = "";
      if (1 !== checkedSnapshots.length)return void WidgetService.notifyWarning("请选中一个云主机快照");
      if (originalStatus = checkedSnapshots[0].status, "ACTIVE" !== originalStatus && "KILLED" !== originalStatus)return void WidgetService.notifyWarning("云主机快照当前状态不可删除");
      var data = {
        region: checkedSnapshots[0].region,
        vmSnapshotId: checkedSnapshots[0].id
      }, modalInstance = WidgetService.openConfirmModal("删除云主机快照", "确定要删除云主机快照（" + checkedSnapshots[0].name + "）吗？");
      modalInstance.result.then(function (resultData) {
        return resultData ? (WidgetService.notifyInfo("云主机快照删除执行中..."), checkedSnapshots[0].status = "PENDING_DELETE", void HttpService.doPost(Config.urls.snapshot_vm_delete, data).success(function (data, status, headers, config) {
          1 === data.result ? (checkedSnapshots[0].status = "DELETED", modalInstance.close(data), WidgetService.notifySuccess("删除云主机快照成功"), refreshSnapshotList()) : (checkedSnapshots[0].status = originalStatus, WidgetService.notifyError(data.msgs[0] || "删除云主机快照失败"))
        })) : resultData
      }, function () {
      })
    }, $scope.isAllSnapshotChecked = function () {
      var snapshotList = getCurrentSnapshotList(), unCheckedSnapshots = snapshotList.filter(function (snapshot) {
        return snapshot.checked === !1 || void 0 === snapshot.checked
      });
      return 0 == unCheckedSnapshots.length
    }, $scope.checkAllSnapshot = function () {
      var snapshotList = getCurrentSnapshotList();
      $scope.isAllSnapshotChecked() ? snapshotList.forEach(function (snapshot) {
        snapshot.checked = !1
      }) : snapshotList.forEach(function (snapshot) {
        snapshot.checked = !0
      })
    }, $scope.checkSnapshot = function (snapshot) {
      snapshot.checked = snapshot.checked === !0 ? !1 : !0
    }, refreshSnapshotList()
  }])
}), define("controllers/VmImageCrtl", ["controllers/app.controller"], function (controllerModule) {
  controllerModule.controller("VmImageCrtl", ["$scope", "$interval", "Config", "$modal", "HttpService", "WidgetService", "CurrentContext", function ($scope, $interval, Config, $modal, HttpService, WidgetService, CurrentContext) {
    $scope.searchName = "", $scope.imageList = [], $scope.currentPage = 1, $scope.totalItems = 0, $scope.pageSize = 10, $scope.onPageChange = function () {
      refreshimageList()
    }, $scope.doSearch = function () {
      refreshimageList()
    }, $scope.isAllImgChecked = function () {
      var unCheckedImgs = $scope.imageList.filter(function (img) {
        return img.checked === !1 || void 0 === img.checked
      });
      return 0 == unCheckedImgs.length
    }, $scope.checkAllimg = function () {
      $scope.isAllImgChecked() ? $scope.imageList.forEach(function (img) {
        img.checked = !1
      }) : $scope.imageList.forEach(function (img) {
        img.checked = !0
      })
    }, $scope.checkimg = function (img) {
      img.checked = img.checked === !0 ? !1 : !0
    };
    var refreshimageList = function () {
      var queryParams = {
        region: CurrentContext.regionId,
        name: $scope.searchName,
        currentPage: $scope.currentPage,
        recordsPerPage: $scope.pageSize
      };
      $scope.isListLoading = !0, HttpService.doGet(Config.urls.image_list, queryParams).then(function (data, status, headers, config) {
        $scope.isListLoading = !1, $scope.imageList = data.data.data, $scope.totalItems = data.data.totalRecords
      })
    };
    getCheckedImg = function () {
      return $scope.imageList.filter(function (item) {
        return item.checked === !0
      })
    }, refreshimageList()
  }])
}), define("controllers/VmKeypairCrtl", ["controllers/app.controller"], function (controllerModule) {
  controllerModule.controller("VmKeypairCrtl", ["$scope", "$interval", "$modal", "$timeout", "$httpParamSerializerJQLike", "$sce", "Config", "HttpService", "WidgetService", "CurrentContext", function ($scope, $interval, $modal, $timeout, $httpParamSerializerJQLike, $sce, Config, HttpService, WidgetService, CurrentContext) {
    $scope.searchName = "", $scope.keypairList = [], $scope.keypairDownloadUrl = "", $scope.currentPage = 1, $scope.totalItems = 0, $scope.pageSize = 10, $scope.onPageChange = function () {
      refreshKeypairList()
    }, $scope.doSearch = function () {
      refreshKeypairList()
    }, $scope.openVmKeypairCreateModal = function (size) {
      var modalInstance = $modal.open({
        animation: $scope.animationsEnabled,
        templateUrl: "/static/apps/cloudvm/templates/vm-keypair-create-modal.html",
        controller: "VmKeypairCreateModalCtrl",
        size: size,
        backdrop: "static",
        keyboard: !1,
        resolve: {
          region: function () {
            return CurrentContext.regionId
          }
        }
      });
      modalInstance.result.then(function (resultData) {
        resultData && ($scope.keypairDownloadUrl = $sce.trustAsResourceUrl(Config.urls.keypair_create + "?" + $httpParamSerializerJQLike(resultData)), $timeout(function () {
          WidgetService.notifySuccess("密钥创建成功"), refreshKeypairList()
        }, 2e3))
      }, function () {
      })
    }, $scope.deleteKeypair = function () {
      var checkedKeypairs = getCheckedKeypair();
      if (1 !== checkedKeypairs.length)return void WidgetService.notifyWarning("请选中一个密钥");
      var data = {
        region: checkedKeypairs[0].region,
        name: checkedKeypairs[0].name
      }, modalInstance = WidgetService.openConfirmModal("删除密钥", "确定要删除密钥（" + checkedKeypairs[0].name + "）吗？");
      modalInstance.result.then(function (resultData) {
        return resultData ? (WidgetService.notifyInfo("密钥删除执行中..."), void HttpService.doPost(Config.urls.keypair_delete, data).success(function (data, status, headers, config) {
          1 === data.result ? (modalInstance.close(data), WidgetService.notifySuccess("删除密钥成功"), refreshKeypairList()) : WidgetService.notifyError(data.msgs[0] || "删除密钥失败")
        })) : resultData
      }, function () {
      })
    }, $scope.isAllKeypairChecked = function () {
      var unCheckedKeypairs = $scope.keypairList.filter(function (keypair) {
        return keypair.checked === !1 || void 0 === keypair.checked
      });
      return 0 == unCheckedKeypairs.length
    }, $scope.checkAllKeypair = function () {
      $scope.isAllKeypairChecked() ? $scope.keypairList.forEach(function (keypair) {
        keypair.checked = !1
      }) : $scope.keypairList.forEach(function (keypair) {
        keypair.checked = !0
      })
    }, $scope.checkKeypair = function (keypair) {
      keypair.checked = keypair.checked === !0 ? !1 : !0
    };
    var refreshKeypairList = function () {
      var queryParams = {
        region: CurrentContext.regionId,
        name: $scope.searchName,
        currentPage: $scope.currentPage,
        recordsPerPage: $scope.pageSize
      };
      $scope.isListLoading = !0, HttpService.doGet(Config.urls.keypair_list, queryParams).then(function (data, status, headers, config) {
        $scope.isListLoading = !1, $scope.keypairList = data.data.data, $scope.totalItems = data.data.totalRecords
      })
    }, getCheckedKeypair = function () {
      return $scope.keypairList.filter(function (item) {
        return item.checked === !0
      })
    };
    refreshKeypairList()
  }])
}), define("controllers/VmKeypairCreateModalCtrl", ["controllers/app.controller"], function (controllerModule) {
  controllerModule.controller("VmKeypairCreateModalCtrl", function (Config, HttpService, WidgetService, Utility, ModelService, $scope, $modalInstance, region) {
    $scope.keypairName = "", $scope.closeModal = function () {
      $modalInstance.dismiss("cancel")
    }, $scope.createKeypair = function () {
      var createKeypairData = {region: region, name: $scope.keypairName};
      $scope.isFormSubmiting = !0, HttpService.doGet(Config.urls.keypair_check, createKeypairData, {disableGetGlobalNotify: !0}).then(function (data) {
        $modalInstance.close(createKeypairData)
      }, function (data) {
        0 === data.result && (WidgetService.notifyError(data.msgs[0] || "密钥名称验证失败"), $scope.isFormSubmiting = !1)
      })
    }
  })
}), define("controllers/controllers", ["controllers/app.controller", "controllers/VirtualMachine", "controllers/VmCreateModalCtrl", "controllers/ConfirmModalCtrl", "controllers/VmDiskCrtl", "controllers/VmDiskCreateModalCtrl", "controllers/vpcController/SubnetCreateModalCtrl", "controllers/vpcController/SubnetAssociateRouterModalCtrl", "controllers/vpcController/SubnetEditModalCtrl", "controllers/vpcController/VmVpcCtrl", "controllers/vpcController/VpcCreateModalCtrl", "controllers/vpcController/VpcEditModalCtrl", "controllers/VmFloatIpCrtl", "controllers/routerController/VmRouterCtrl", "controllers/routerController/VmRouterCreateModalCtrl", "controllers/routerController/VmRouterEditModalCtrl", "controllers/routerController/RouterAssociateSubnetModalCtrl", "controllers/VmIPcreateModalCtrl", "controllers/VmSnapshotCrtl", "controllers/VmImageCrtl", "controllers/VmKeypairCrtl", "controllers/VmKeypairCreateModalCtrl"], function (controllerModule) {
}), define("../common/services/common.service", ["angular"], function (angular) {
  return angular.module("common.service", [])
}), define("../common/services/Config", ["./common.service"], function (serviceModule) {
  serviceModule.factory("Config", [function () {
    var config = {};
    return config.urls = {
      vm_regions: "/ecs/regions/",
      region_list: "/ecs/region/list",
      vm_list: "/ecs/region/{region}",
      vm_detail: "/ecs/region/{region}/vm/{vmId}",
      not_in_any_network_vm_list: "/ecs/vm/notInAnyNetwork/list",
      could_attach_subnet_list: "/ecs/vm/couldAttachSubnet/list",
      vm_attach_subnet_list: "/ecs/vm/attached/subnet/list",
      image_list: "/osi/image/list",
      flavor_group_data: "/osf/region/{region}/group",
      vm_create_old: "/ecs/region/{region}/vm-create",
      vm_create: "/ecs/vm/create",
      vm_buy: "/billing/buy/2",
      disk_buy: "/billing/buy/3",
      floatip_buy: "/billing/buy/4",
      router_buy: "/billing/buy/5",
      vm_rename: "/ecs/vm/rename",
      vm_start: "/ecs/region/{region}/vm-start",
      vm_stop: "/ecs/region/{region}/vm-stop",
      vm_reboot: "/ecs/vm/reboot",
      vm_delete: "/ecs/region/{region}/vm-delete",
      vm_password_change: "/ecs/vm/changeAdminPass",
      vm_disk_type: "/osv/volume/type/list",
      vm_network_shared_list: "/osn/network/shared/list",
      vm_calculate_price: "/billing/calculate/price/2",
      disk_calculate_price: "/billing/calculate/price/3",
      floatip_calculate_price: "/billing/calculate/price/4",
      route_calculate_price: "/billing/calculate/price/5",
      disk_list: "/osv/region/{region}",
      disk_detail: "/osv/region/{region}/volume/{volumeId}",
      disk_create: "/osv/region/{region}/volume-create",
      disk_delete: "/osv/region/{region}/volume-delete",
      disk_attach: "/ecs/region/{region}/vm-attach-volume",
      disk_detach: "/ecs/region/{region}/vm-detach-volume",
      disk_edit: "/osv/volume/edit",
      subnet_list: "/osn/subnet/private/list",
      subnet_option_list: "/osn/subnet/option/list",
      subnet_create: "/osn/subnet/private/create",
      subnet_delete: "/osn/subnet/private/delete",
      subnet_edit: "/osn/subnet/private/edit",
      subnet_attach_vm: "/ecs/vm/attach/subnet",
      subnet_detach_vm: "/ecs/vm/detach/subnet",
      vpc_list: "/osn/network/private/list",
      vpc_delete: "/osn/network/private/delete",
      vpc_create: "/osn/network/private/create",
      vpc_subnet_create: "/osn/network/subnet/private/create",
      vpc_edit: "/osn/network/private/edit",
      router_list: "/osn/router/list",
      network_public_list: "/osn/network/public/list",
      available_for_router_subnet_list: "/osn/network/private/available_for_router_interface/list",
      subnet_associate: "/osn/router/subnet/associate",
      subnet_remove: "/osn/router/subnet/separate",
      router_create: "/osn/router/create",
      router_edit: "/osn/router/edit",
      router_delete: "/osn/router/delete",
      floatIP_list: "/osn/floatingip/list",
      floatIP_create: "/osn/floatingip/create",
      floatIp_delete: "/osn/floatingip/delete",
      floatIP_edit: "/osn/floatingip/edit",
      floatIp_bindVm: "/ecs/vm/floatingip/bind",
      floatIp_unbindVm: "/ecs/vm/floatingip/unbind",
      snapshot_disk_list: "/osv/volume/snapshot/list",
      snapshot_disk_create: "/osv/volume/snapshot/create",
      snapshot_disk_delete: "/osv/volume/snapshot/delete",
      snapshot_vm_list: "/ecs/vm/snapshot/list",
      snapshot_vm_detail: "/ecs/vm/snapshot/detail",
      snapshot_vm_create: "/ecs/vm/snapshot/create",
      snapshot_vm_delete: "/ecs/vm/snapshot/delete",
      vm_vnc: "/ecs/region/{region}/vm-open-console",
      keypair_list: "/ecs/keypair/list",
      keypair_create: "/ecs/keypair/create",
      keypair_delete: "/ecs/keypair/delete",
      keypair_check: "/ecs/keypair/create/check"
    }, config.REGEX = {
      NAME: /^[a-zA-Z\u4e00-\u9fa5][a-zA-Z0-9\u4e00-\u9fa5\(（_\-\)）]{1,127}$/,
      NAME_KEYPAIR: /^[a-zA-Z][a-zA-Z0-9_\-]{1,127}$/,
      PASSWORD: /^(?=.*[0-9].*)(?=.*[A-Z].*)(?=.*[a-z].*)[a-zA-Z0-9]{8,30}$/
    }, config.REGEX_MESSAGE = {
      NAME: "名称须为2-128个字符，支持大小写字母数字或中文以及()（）_-，以大小写字母或中文开头",
      NAME_KEYPAIR: "名称须为2-128个字符，支持大小写字母数字以及_-，以大小写字母开头",
      PASSWORD: "8-30个字符，同时包含大小写字母和数字，不支持特殊符号"
    }, config.vmStatuses = {
      active: "活跃",
      building: "创建中",
      paused: "已暂停",
      suspended: "已挂起",
      stopped: "已关机",
      error: "异常",
      deleted: "已删除",
      arrearaged: "欠费停机"
    }, config.vmTaskStatuses = {
      building: "创建中",
      deleting: "删除中",
      stopping: "关机中",
      starting: "启动中",
      rebooting: "重启中",
      scheduling: "创建中",
      block_device_mapping: "创建中",
      spawning: "创建中",
      image_snapshotting: "快照创建中",
      image_uploading: "快照上传中",
      updating_password: "密码修改中",
      suspending: "挂起中",
      resuming: "恢复中",
      networking: "网络挂载中"
    }, config.vmDiskStatuses = [{text: "创建中", value: "creating"}, {text: "可用的", value: "available"}, {
      text: "挂载中",
      value: "attaching"
    }, {text: "使用中", value: "in-use"}, {text: "删除中", value: "deleting"}, {text: "异常", value: "error"}, {
      text: "已删除",
      value: "deleted"
    }, {text: "卸载中", value: "detaching"}], config.vmVpcStatuses = [{text: "活跃", value: "ACTIVE"}, {
      text: "可用",
      value: "DOWN"
    }, {text: "创建中", value: "BUILD"}, {text: "不可用", value: "ERROR"}, {
      text: "不可用",
      value: "UNRECOGNIZED"
    }], config.vmFloatIpStatuses = {AVAILABLE: "可用的", BINDED: "已绑定"}, config.vmImageStatuses = [{
      text: "未识别",
      value: "UNRECOGNIZED"
    }, {text: "活跃", value: "ACTIVE"}, {text: "上传中", value: "SAVING"}, {text: "等待上传", value: "QUEUED"}, {
      text: "停止",
      value: "KILLED"
    }, {text: "删除等待", value: "PENDING_DELETE"}, {text: "删除", value: "DELETED"}, {
      text: "无",
      value: "NIL"
    }], config.allBuyPeriods = [1, 2, 3, 4, 5, 6, 7, 8, 9, 12, 24, 36], config.serverName = {
      uc: "uc.letvcloud.com",
      matrix: "matrix.letvclond.com"
    }, config.EMPTY_TEXT = "--", config.statusOperations = {
      virtualMachine: {
        active: {
          create: 1,
          start: 0,
          stop: 1,
          restart: 1,
          createsnap: 1,
          changeconfig: 0,
          attachdisk: 1,
          detachdisk: 1,
          bindfloatIp: 1,
          unbindfloatIp: 1,
          joinnet: 1,
          bindalarm: 1,
          vnc: 1,
          editssh: 1,
          editpass: 1,
          "delete": 1
        },
        stopped: {
          create: 1,
          start: 1,
          stop: 0,
          restart: 0,
          createsnap: 1,
          changeconfig: 0,
          attachdisk: 1,
          detachdisk: 1,
          bindfloatIp: 1,
          unbindfloatIp: 1,
          joinnet: 1,
          bindalarm: 0,
          vnc: 0,
          editssh: 0,
          editpass: 0,
          "delete": 1
        },
        building: {
          create: 0,
          start: 0,
          stop: 0,
          restart: 0,
          createsnap: 0,
          changeconfig: 0,
          attachdisk: 0,
          detachdisk: 0,
          bindfloatIp: 0,
          unbindfloatIp: 0,
          joinnet: 0,
          bindalarm: 0,
          vnc: 0,
          editssh: 0,
          editpass: 0,
          "delete": 0
        },
        deleting: {
          create: 0,
          start: 0,
          stop: 0,
          restart: 0,
          createsnap: 0,
          changeconfig: 0,
          attachdisk: 0,
          detachdisk: 0,
          bindfloatIp: 0,
          unbindfloatIp: 0,
          joinnet: 0,
          bindalarm: 0,
          vnc: 0,
          editssh: 0,
          editpass: 0,
          "delete": 0
        },
        stopping: {
          create: 0,
          start: 0,
          stop: 0,
          restart: 0,
          createsnap: 0,
          changeconfig: 0,
          attachdisk: 0,
          detachdisk: 0,
          bindfloatIp: 0,
          unbindfloatIp: 0,
          joinnet: 0,
          bindalarm: 0,
          vnc: 0,
          editssh: 0,
          editpass: 0,
          "delete": 0
        },
        starting: {
          create: 0,
          start: 0,
          stop: 0,
          restart: 0,
          createsnap: 0,
          changeconfig: 0,
          attachdisk: 0,
          detachdisk: 0,
          bindfloatIp: 0,
          unbindfloatIp: 0,
          joinnet: 0,
          bindalarm: 0,
          vnc: 0,
          editssh: 0,
          editpass: 0,
          "delete": 0
        },
        rebooting: {
          create: 0,
          start: 0,
          stop: 0,
          restart: 0,
          createsnap: 0,
          changeconfig: 0,
          attachdisk: 0,
          detachdisk: 0,
          bindfloatIp: 0,
          unbindfloatIp: 0,
          joinnet: 0,
          bindalarm: 0,
          vnc: 0,
          editssh: 0,
          editpass: 0,
          "delete": 0
        },
        spawning: {
          create: 0,
          start: 0,
          stop: 0,
          restart: 0,
          createsnap: 0,
          changeconfig: 0,
          attachdisk: 0,
          detachdisk: 0,
          bindfloatIp: 0,
          unbindfloatIp: 0,
          joinnet: 0,
          bindalarm: 0,
          vnc: 0,
          editssh: 0,
          editpass: 0,
          "delete": 0
        },
        deleted: {
          create: 1,
          start: 0,
          stop: 0,
          restart: 0,
          createsnap: 0,
          changeconfig: 0,
          attachdisk: 0,
          detachdisk: 0,
          bindfloatIp: 0,
          unbindfloatIp: 0,
          joinnet: 0,
          bindalarm: 0,
          vnc: 0,
          editssh: 0,
          editpass: 0,
          "delete": 0
        },
        error: {
          create: 1,
          start: 0,
          stop: 0,
          restart: 0,
          createsnap: 0,
          changeconfig: 0,
          attachdisk: 0,
          detachdisk: 0,
          bindfloatIp: 0,
          unbindfloatIp: 0,
          joinnet: 0,
          bindalarm: 0,
          vnc: 0,
          editssh: 0,
          editpass: 0,
          "delete": 1
        },
        "default": {
          create: 1,
          start: 1,
          stop: 1,
          restart: 1,
          createsnap: 1,
          changeconfig: 1,
          attachdisk: 1,
          detachdisk: 1,
          bindfloatIp: 1,
          unbindfloatIp: 1,
          joinnet: 1,
          bindalarm: 1,
          vnc: 1,
          editssh: 1,
          editpass: 1,
          "delete": 1
        },
        volumes: {
          create: 1,
          start: 1,
          stop: 1,
          restart: 1,
          createsnap: 1,
          changeconfig: 1,
          attachdisk: 1,
          detachdisk: 1,
          bindfloatIp: 1,
          unbindfloatIp: 1,
          joinnet: 1,
          bindalarm: 1,
          vnc: 1,
          editssh: 1,
          editpass: 1,
          "delete": 1
        },
        volumesnull: {
          create: 1,
          start: 1,
          stop: 1,
          restart: 1,
          createsnap: 1,
          changeconfig: 1,
          attachdisk: 1,
          detachdisk: 0,
          bindfloatIp: 1,
          unbindfloatIp: 1,
          joinnet: 1,
          bindalarm: 1,
          vnc: 1,
          editssh: 1,
          editpass: 1,
          "delete": 1
        },
        "public": {
          create: 1,
          start: 1,
          stop: 1,
          restart: 1,
          createsnap: 1,
          changeconfig: 1,
          attachdisk: 1,
          detachdisk: 1,
          bindfloatIp: 0,
          unbindfloatIp: 1,
          joinnet: 1,
          bindalarm: 1,
          vnc: 1,
          editssh: 1,
          editpass: 1,
          "delete": 1
        },
        publicnull: {
          create: 1,
          start: 1,
          stop: 1,
          restart: 1,
          createsnap: 1,
          changeconfig: 1,
          attachdisk: 1,
          detachdisk: 1,
          bindfloatIp: 1,
          unbindfloatIp: 0,
          joinnet: 1,
          bindalarm: 1,
          vnc: 1,
          editssh: 1,
          editpass: 1,
          "delete": 1
        }
      },
      disk: {
        creating: {create: 0, createsnap: 0, attachdisk: 0, detachdisk: 0, expandVolume: 0, "delete": 0, edit: 0},
        available: {create: 1, createsnap: 1, attachdisk: 1, detachdisk: 0, expandVolume: 1, "delete": 1, edit: 1},
        attaching: {create: 0, createsnap: 0, attachdisk: 0, detachdisk: 0, expandVolume: 0, "delete": 0, edit: 0},
        "in-use": {create: 1, createsnap: 1, attachdisk: 0, detachdisk: 1, expandVolume: 0, "delete": 0, edit: 1},
        deleting: {create: 0, createsnap: 0, attachdisk: 0, detachdisk: 0, expandVolume: 0, "delete": 0, edit: 0},
        error: {create: 1, createsnap: 0, attachdisk: 0, detachdisk: 0, expandVolume: 0, "delete": 0, edit: 0},
        deleted: {create: 1, createsnap: 0, attachdisk: 0, detachdisk: 0, expandVolume: 0, "delete": 0, edit: 0},
        detaching: {create: 0, createsnap: 0, attachdisk: 0, detachdisk: 0, expandVolume: 0, "delete": 0, edit: 0},
        "default": {create: 1, createsnap: 1, attachdisk: 1, detachdisk: 1, expandVolume: 1, "delete": 1, edit: 1}
      },
      floatIp: {
        AVAILABLE: {
          bindVm: 1,
          bindRouter: 1,
          bindBalance: 1,
          changeBandwidth: 1,
          detach: 0,
          bindalarm: 1,
          edit: 1,
          "delete": 1
        },
        BINDED: {
          bindVm: 0,
          bindRouter: 0,
          bindBalance: 0,
          changeBandwidth: 0,
          detach: 1,
          bindalarm: 1,
          edit: 1,
          "delete": 0
        },
        "default": {
          bindVm: 1,
          bindRouter: 1,
          bindBalance: 1,
          changeBandwidth: 1,
          detach: 1,
          bindalarm: 1,
          edit: 1,
          "delete": 1
        }
      },
      router: {
        ACTIVE: {
          create: 1,
          bindIp: 1,
          unbindIp: 1,
          bindsubnet: 1,
          removesubnet: 1,
          edit: 1,
          bindalarm: 1,
          "delete": 1
        },
        DOWN: {create: 1, bindIp: 0, unbindIp: 1, bindsubnet: 0, removesubnet: 1, edit: 1, bindalarm: 1, "delete": 1},
        BUILD: {create: 0, bindIp: 0, unbindIp: 0, bindsubnet: 0, removesubnet: 0, edit: 0, bindalarm: 0, "delete": 0},
        ERROR: {create: 1, bindIp: 0, unbindIp: 0, bindsubnet: 0, removesubnet: 0, edit: 0, bindalarm: 0, "delete": 0},
        UNRECOGNIZED: {
          create: 1,
          bindIp: 0,
          unbindIp: 0,
          bindsubnet: 0,
          removesubnet: 0,
          edit: 0,
          bindalarm: 0,
          "delete": 0
        },
        subnets: {
          create: 1,
          bindIp: 1,
          unbindIp: 1,
          bindsubnet: 1,
          removesubnet: 1,
          edit: 1,
          bindalarm: 1,
          "delete": 0
        },
        subnetsnull: {
          create: 1,
          bindIp: 1,
          unbindIp: 1,
          bindsubnet: 1,
          removesubnet: 0,
          edit: 1,
          bindalarm: 1,
          "delete": 1
        },
        "default": {
          create: 1,
          bindIp: 1,
          unbindIp: 1,
          bindsubnet: 1,
          removesubnet: 1,
          edit: 1,
          bindalarm: 1,
          "delete": 1
        }
      },
      subnet: {
        vm: {create: 1, bindvm: 1, unbindvm: 1, bindrouter: 1, unbindrouter: 1, edit: 1, "delete": 1},
        vmnull: {create: 1, bindvm: 1, unbindvm: 0, bindrouter: 1, unbindrouter: 1, edit: 1, "delete": 1},
        router: {create: 1, bindvm: 1, unbindvm: 1, bindrouter: 0, unbindrouter: 1, edit: 1, "delete": 1},
        routernull: {create: 1, bindvm: 1, unbindvm: 1, bindrouter: 1, unbindrouter: 0, edit: 1, "delete": 1},
        "default": {create: 1, bindvm: 1, unbindvm: 1, bindrouter: 1, unbindrouter: 1, edit: 1, "delete": 1}
      }
    }, config
  }])
}), define("../common/services/HttpService", ["./common.service"], function (serviceModule) {
  serviceModule.factory("HttpService", ["$http", "$q", "$window", "WidgetService", function ($http, $q, $window, WidgetService) {
    var service = {};
    return service.doGet = function (url, data, option) {
      var deferred = $q.defer();
      return $http.get(url, angular.extend({params: data}, option)).success(function (data, status, headers, config) {
        1 === data.result ? deferred.resolve(data) : 2 === data.result ? $window.location.reload() : (deferred.reject(data), option && option.disableGetGlobalNotify || WidgetService.notifyError(data.msgs[0] || "获取数据失败"))
      }), deferred.promise
    }, service.doPost = function (url, data, option) {
      return $http.post(url, data, option)
    }, service
  }])
}), define("../common/services/WidgetService", ["./common.service"], function (serviceModule) {
  serviceModule.factory("WidgetService", ["$modal", "toaster", function ($modal, toaster) {
    var service = {};
    return service.openConfirmModal = function (title, message) {
      return $modal.open({
        templateUrl: "/static/apps/cloudvm/templates/confirm-modal.html",
        controller: "ConfirmModalCtrl",
        size: "small",
        resolve: {
          message: function () {
            return message
          }, title: function () {
            return title
          }
        }
      })
    }, service.notifyError = function (message) {
      toaster.pop("error", null, message, 2e3, "trustedHtml")
    }, service.notifyInfo = function (message) {
      toaster.pop("info", null, message, 2e3, "trustedHtml")
    }, service.notifySuccess = function (message) {
      toaster.pop("success", null, message, 2e3, "trustedHtml")
    }, service.notifyWarning = function (message) {
      toaster.pop("warning", null, message, 2e3, "trustedHtml")
    }, service.showSpin = function (message) {
      $("body").append('<div class="spin"></div>'), $("body").append('<div class="far-spin"></div>')
    }, service.hideSpin = function (message) {
      $("body").find(".spin").remove(), $("body").find(".far-spin").remove()
    }, service
  }])
}), define("../common/services/CurrentContext", ["./common.service", "jquery"], function (serviceModule, $) {
  serviceModule.factory("CurrentContext", [function () {
    var service = {};
    return service.regionId = $("#region_id").val(), service.allRegionData = null, service
  }])
}), define("../common/services/Utility", ["./common.service"], function (serviceModule) {
  serviceModule.factory("Utility", ["$timeout", function ($timeout) {
    var service = {};
    service.getRzSliderHack = function (scope) {
      return function () {
        $timeout(function () {
          scope.$broadcast("rzSliderForceRender")
        }, 50)
      }
    }, service.delayQueueModel = function () {
      var delayQueue = [], timeoutPromise = null;
      return function (value, onTimeout) {
        0 == delayQueue.length && (timeoutPromise = $timeout(function () {
          onTimeout(delayQueue[delayQueue.length - 1]), delayQueue.splice(0, delayQueue.length)
        }, 1e3)), delayQueue.push(value)
      }
    };
    var jsonTrans = function (obj, path) {
      var patharry = [], _targetobj = "", pathtemp = "", flag = !1;
      if (path.indexOf(".") > 0) {
        patharry = path.split(".");
        var objtemp = "";
        for (var i in patharry)objtemp = objtemp ? objtemp : obj, objtemp = objtemp[patharry[i]], pathtemp = patharry[i];
        _targetobj = objtemp
      } else pathtemp = path, _targetobj = obj[path];
      return _targetobj instanceof Array ? _targetobj.length > 0 && (flag = !0) : _targetobj && (flag = !0), {
        flag: flag,
        pathvalue: pathtemp
      }
    };
    return service.setOperationBtns = function ($scope, objList, productInfo, operationArry, Config) {
      var type = productInfo.type, state = productInfo.state, otheraffect = productInfo.other, operaArraytemp = productInfo.operations, operationArraycopy = [], statetemp = "";
      for (var i in objList)if (operationArry[i] = [], objList[i].checked) {
        var objtemp = objList[i];
        statetemp = objtemp[state] ? objtemp[state] : "default";
        for (var j in operaArraytemp) {
          if (operationArry[i][j] = operationArry[i][j] ? operationArry[i][j] : Config.statusOperations[type][statetemp][operaArraytemp[j]], otheraffect.length > 0)for (var k in otheraffect) {
            var flag = jsonTrans(objtemp, otheraffect[k]).flag, pathvalue = jsonTrans(objtemp, otheraffect[k]).pathvalue;
            flag ? operationArry[i][j] = operationArry[i][j] * Config.statusOperations[type][pathvalue][operaArraytemp[j]] : operationArry[i][j] = operationArry[i][j] * Config.statusOperations[type][pathvalue + "null"][operaArraytemp[j]]
          } else operationArry[i][j] = Config.statusOperations[type][statetemp][operaArraytemp[j]];
          operationArraycopy[j] = 1
        }
      } else operationArry[i] = [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1];
      for (var i in operationArry)for (var j in operationArry[i])operationArraycopy[j] = operationArraycopy[j] * operationArry[i][j];
      return operationArraycopy
    }, service.isInt = function (input) {
      return Number(input) % 1 === 0
    }, service
  }])
}), define("../common/services/ModelService", ["./common.service"], function (serviceModule) {
  serviceModule.factory("ModelService", ["$modal", "toaster", function ($modal, toaster) {
    var service = {};
    return service.SelectModel = function (text, value, option) {
      this.text = text, this.value = value, this.relatedOption = option || {}
    }, service
  }])
}), define("../common/services/services", ["./common.service", "./Config", "./HttpService", "./WidgetService", "./CurrentContext", "./Utility", "./ModelService"], function (serviceModule) {
}), define("../common/directives/common.directive", ["angular"], function (angular) {
  return angular.module("common.directive", [])
}), define("../common/directives/directives", ["./common.directive"], function (directiveModule) {
  directiveModule.directive("passwordVerify", function () {
    return {
      require: "ngModel", scope: {passwordVerify: "="}, link: function (scope, element, attrs, ctrl) {
        var isInit = !0;
        scope.$watch(function () {
          var combined;
          return (scope.passwordVerify || ctrl.$viewValue) && (combined = scope.passwordVerify + "_" + ctrl.$viewValue), combined
        }, function (value) {
          return isInit ? (isInit = !1, ctrl.$setValidity("passwordVerify", !0), !0) : void(value && ctrl.$parsers.unshift(function (viewValue) {
            var origin = scope.passwordVerify;
            return origin !== viewValue ? void ctrl.$setValidity("passwordVerify", !1) : (ctrl.$setValidity("passwordVerify", !0), viewValue)
          }))
        })
      }
    }
  }), directiveModule.directive("nameInputRestrict", function (Config) {
    return {
      require: "ngModel", link: function (scope, elm, attrs, ctrl) {
        ctrl.$validators.nameInputRestrict = function (modelValue, viewValue) {
          return viewValue && Config.REGEX.NAME.test(viewValue) ? !0 : !1
        }
      }
    }
  }), directiveModule.directive("nameInputKeypairRestrict", function (Config) {
    return {
      require: "ngModel", link: function (scope, elm, attrs, ctrl) {
        ctrl.$validators.nameInputKeypairRestrict = function (modelValue, viewValue) {
          return viewValue && Config.REGEX.NAME_KEYPAIR.test(viewValue) ? !0 : !1
        }
      }
    }
  }), directiveModule.directive("passwordRestrict", function (Config) {
    return {
      require: "ngModel", link: function (scope, elm, attrs, ctrl) {
        ctrl.$validators.passwordRestrict = function (modelValue, viewValue) {
          return viewValue && Config.REGEX.PASSWORD.test(viewValue) ? !0 : !1
        }
      }
    }
  }), directiveModule.directive("passwordConfirm", function (Config) {
    return {
      require: "ngModel", scope: {passwordModel: "=passwordModel"}, link: function (scope, elm, attrs, ctrl) {
        var isInit = !0;
        scope.$watch(function () {
          var combined;
          return (scope.passwordModel || ctrl.$viewValue) && (combined = scope.passwordModel + "_" + ctrl.$viewValue), combined
        }, function (value) {
          isInit && (isInit = !1, ctrl.$setValidity("passwordConfirm", !0)), value && (scope.passwordModel !== ctrl.$viewValue ? ctrl.$setValidity("passwordConfirm", !1) : ctrl.$setValidity("passwordConfirm", !0))
        })
      }
    }
  }), directiveModule.directive("vmCreateKeypairValidator", function (Config) {
    return {
      require: "ngModel", link: function (scope, elm, attrs, ctrl) {
        ctrl.$validators.keypairIsNullRestrict = function (modelValue, viewValue) {
          return viewValue ? !0 : !1
        }, ctrl.$validators.keypairValueRestrict = function (modelValue, viewValue) {
          return viewValue && viewValue.value ? !0 : !1
        }
      }
    }
  }), directiveModule.directive("numericInput", function () {
    var MAX = 10, MIN = 1;
    return {
      restrict: "AE",
      scope: {externalModel: "=numericModel", max: "=numericMax", min: "=numericMin"},
      link: function (scope, element, attrs) {
        scope.model = scope.externalModel, void 0 === scope.max && (scope.max = MAX), void 0 === scope.min && (scope.min = MIN), scope.up = function () {
          ++scope.model > scope.max && --scope.model
        }, scope.down = function () {
          --scope.model < scope.min && ++scope.model
        }, scope.$watch("model", function (value) {
          if (isNaN(value))scope.model = scope.min; else {
            var validatedValue = Number(value);
            validatedValue > scope.max ? scope.model = scope.max : validatedValue < scope.min ? scope.model = scope.min : scope.model = validatedValue
          }
          scope.externalModel = scope.model
        })
      },
      templateUrl: "/static/apps/common/directives/numeric-input/template.html"
    }
  }), directiveModule.directive("leSelect", function ($document) {
    return {
      restrict: "AE",
      scope: {model: "=selectModel", options: "=selectOptions", externalEmptyText: "@selectEmptyText"},
      link: function (scope, element, attrs) {
        scope.emptyText = scope.externalEmptyText ? scope.externalEmptyText : "暂无数据", scope.toggleSelect = function (e) {
          scope.isOpen || (scope.isOpen = !0, e.stopPropagation(), $document.bind("click", closeDropdown))
        }, scope.selectOption = function (selectedOption) {
          scope.model = selectedOption
        };
        var closeDropdown = function (evt) {
          scope.isOpen && ($document.unbind("click", closeDropdown), scope.isOpen = !1, scope.$$phase || scope.$apply())
        }
      },
      templateUrl: "/static/apps/common/directives/le-select/template.html"
    }
  }), directiveModule.directive("leSlider", function (Utility) {
    return {
      restrict: "AE",
      scope: {
        externalModel: "=leSliderModel",
        step: "@leSliderStep",
        min: "=leSliderMin",
        isMinChangeable: "@isSliderMinChangeable",
        max: "@leSliderMax",
        unit: "@leSliderUnit"
      },
      link: function (scope, element, attrs) {
        var delayQueueModelHandler = Utility.delayQueueModel(), max = Number(scope.max), min = Number(scope.min), isExternalModelChangedInDirective = !1;
        "true" === scope.isMinChangeable && scope.$watch("min", function (newValue) {
          min = Number(newValue), scope.model = min
        }), scope.$watch("externalModel", function (newValue) {
          return isExternalModelChangedInDirective ? void(isExternalModelChangedInDirective = !1) : void(scope.model = newValue)
        }), scope.$watch("model", function (newValue) {
          null === newValue || isNaN(newValue) ? scope.model = min : (Utility.isInt(newValue) || (newValue = Math.floor(newValue)), max >= newValue && newValue >= min ? (newValue % scope.step !== 0 && (newValue = Math.ceil(newValue / scope.step) * scope.step), delayQueueModelHandler(newValue, function (value) {
            scope.model = Number(value), isExternalModelChangedInDirective = !0, scope.externalModel = scope.model
          })) : newValue > max ? scope.model = max : min > newValue && (scope.model = min))
        })
      },
      templateUrl: "/static/apps/common/directives/le-slider/template.html"
    }
  }), directiveModule.directive("buyPeriodSelector", function () {
    return {
      restrict: "AE",
      scope: {selectedBuyPeriod: "=buyPeriodModel", allBuyPeriods: "=buyPeriodOptions"},
      link: function (scope, element, attrs) {
        scope.selectBuyPeriod = function (buyPeriod) {
          scope.selectedBuyPeriod = buyPeriod
        }, scope.isSelectedBuyPeriod = function (buyPeriod) {
          return scope.selectedBuyPeriod === buyPeriod
        }
      },
      templateUrl: "/static/apps/common/directives/buy-period-selector/template.html"
    }
  }), directiveModule.directive("leAutoFocus", function ($timeout) {
    return {
      restrict: "AE", link: function (scope, element, attrs) {
        element && element[0] && $timeout(function () {
          element[0].focus()
        })
      }
    }
  }), directiveModule.directive("inputValidationTooltip", function ($timeout) {
    return {
      restrict: "AE", scope: {message: "@validationMessage"}, link: function (scope, element, attrs) {
      }, templateUrl: "/static/apps/common/directives/input-validation-tooltip/template.html"
    }
  })
}), define("../common/filters/common.filter", ["angular"], function (angular) {
  return angular.module("common.filter", [])
}), define("../common/filters/filters", ["./common.filter"], function (filterModule) {
  filterModule.filter("propsFilter", function () {
    return function (items, props) {
      var out = [];
      return angular.isArray(items) ? items.forEach(function (item) {
        for (var itemMatches = !1, keys = Object.keys(props), i = 0; i < keys.length; i++) {
          var prop = keys[i], text = props[prop].toLowerCase();
          if (-1 !== item[prop].toString().toLowerCase().indexOf(text)) {
            itemMatches = !0;
            break
          }
        }
        itemMatches && out.push(item)
      }) : out = items, out
    }
  }), filterModule.filter("mclusterTypeFilter", function () {
    return function (input) {
      var out = "";
      return out = input ? "后台创建" : "系统创建"
    }
  }), filterModule.filter("vmStatusFilter", ["Config", function (Config) {
    var vmStatuses = Config.vmStatuses, vmTaskStatuses = Config.vmTaskStatuses;
    return function (input) {
      var out = "", vmState = input.vmState, taskState = input.taskState;
      return out = taskState ? vmTaskStatuses[taskState] ? vmTaskStatuses[taskState] : taskState : vmStatuses[vmState] ? vmStatuses[vmState] : vmState
    }
  }]), filterModule.filter("diskStatusFilter", ["Config", function (Config) {
    var allDiskStatuses = Config.vmDiskStatuses;
    return function (input) {
      for (var out = "", i = 0, leng = allDiskStatuses.length; leng > i; i++)if (allDiskStatuses[i].value == input) {
        out = allDiskStatuses[i].text;
        break
      }
      return out || "未知"
    }
  }]), filterModule.filter("vpcStatusFilter", ["Config", function (Config) {
    var allStatuses = Config.vmVpcStatuses;
    return function (input) {
      for (var out = "", i = 0, leng = allStatuses.length; leng > i; i++)if (allStatuses[i].value == input) {
        out = allStatuses[i].text;
        break
      }
      return out || "未知"
    }
  }]), filterModule.filter("vpcSubnetsFilter", ["Config", function (Config) {
    return function (input) {
      return input.map(function (subnet) {
          return subnet.name
        }).join(", ") || Config.EMPTY_TEXT
    }
  }]), filterModule.filter("buyPeriodFilter", [function () {
    return function (period, isSelected) {
      var out = null;
      return out = 9 >= period ? isSelected ? period + "个月" : period : parseInt(period / 12) + "年"
    }
  }]), filterModule.filter("publicNetworkGatewayFilter", [function () {
    return function (input) {
      var out = "";
      return out = "true" == input ? "开启" : "关闭"
    }
  }]), filterModule.filter("vmFlavorFilter", [function () {
    return function (vm) {
      var totalVolumeSize = 0;
      return vm.volumes && vm.volumes.length && (totalVolumeSize = vm.volumes.length > 1 ? vm.volumes.map(function (volume) {
        return volume.size
      }).reduce(function (x, y) {
        return x + y
      }) : vm.volumes[0].size), [vm.flavor.vcpus + "核", Math.ceil(vm.flavor.ram / 1024) + "G", totalVolumeSize + "G"].join("/")
    }
  }]), filterModule.filter("sideMenuUrlFilter", [function () {
    return function (route) {
      var out = "";
      return out = route["abstract"] ? "javascript:void(0);" : route.isSpaUrl ? "#" + route.url : route.url, out || "未知"
    }
  }]), filterModule.filter("vmFloatIpFilter", ["Config", function (Config) {
    var allIpStatuses = Config.vmFloatIpStatuses;
    return function (input) {
      var out = "";
      return out = allIpStatuses[input], out || "未知"
    }
  }]), filterModule.filter("routerSubnetFilter", [function () {
    return function (input) {
      var out = "";
      if (null == input[0])return "--";
      for (var i = 0, len = input.length; len > i; i++)out = "" !== out ? out + "," + input[i].name : input[i].name;
      return out
    }
  }]), filterModule.filter("routerGatewayFilter", [function () {
    return function (input) {
      return input === !0 ? "开启" : "关闭"
    }
  }]), filterModule.filter("routerStatusFilter", [function () {
    return function (input) {
      var out = "";
      return out = "ACTIVE" === input ? "活跃" : "DOWN" === input ? "关闭" : "BUILD" === input ? "构建中" : "ERROR" === input ? "错误" : "UNRECOGNIZED" === input ? "未识别" : "未知"
    }
  }]), filterModule.filter("vmImageStatusFilter", ["Config", function (Config) {
    var allStatuses = Config.vmImageStatuses;
    return function (input) {
      for (var out = "", i = 0, leng = allStatuses.length; leng > i; i++)if (allStatuses[i].value == input) {
        out = allStatuses[i].text;
        break
      }
      return out || "未知"
    }
  }]), filterModule.filter("vmImageSizeFilter", [function () {
    return function (input) {
      var out = "";
      return out = input / 1073741824 >= 1 ? (input / 1073741824).toFixed(2) + "GB" : input / 1048576 >= 1 ? (input / 1048576).toFixed(2) + "MB" : input / 1024 >= 1 ? (input / 1024).toFixed(2) + "KB" : input + "B";
    }
  }])
}), define("app", ["angular", "controllers/controllers", "../common/services/services", "../common/directives/directives", "../common/filters/filters"], function (angular) {
  var app = angular.module("myApp", ["ngAnimate", "ngRoute", "ui.bootstrap", "toaster", "rzModule", "app.controller", "common.service", "common.directive", "common.filter"]);
  return app.config(["$httpProvider", function ($httpProvider) {
    $httpProvider.defaults.headers.post["Content-Type"] = "application/x-www-form-urlencoded; charset=UTF-8", $httpProvider.defaults.headers.post.Accept = "application/json, text/javascript, */*; q=0.01", $httpProvider.defaults.headers.post["Accept-Language"] = "zh-CN,zh;q=0.8,en;q=0.6", $httpProvider.defaults.headers.post["X-Requested-With"] = "XMLHttpRequest", $httpProvider.defaults.headers.get["X-Requested-With"] = "XMLHttpRequest", $httpProvider.defaults.headers.get["If-Modified-Since"] = "Mon, 26 Jul 1997 05:00:00 GMT", $httpProvider.defaults.headers.get["Cache-Control"] = "no-cache", $httpProvider.defaults.headers.get.Pragma = "no-cache", $httpProvider.defaults.transformRequest = function (data) {
      return void 0 === data ? data : $.param(data)
    }
  }]), app.run(["$route", "$rootScope", "$http", "$location", "routes", "Config", function ($route, $rootScope, $http, $location, routes, Config) {
    $rootScope.REGEX_MESSAGE = Config.REGEX_MESSAGE, $rootScope.EMPTY_TEXT = Config.EMPTY_TEXT
  }]), app
}), define("app.router", ["app"], function (app) {
  "use strict";
  var routeConfigurator = function ($routeProvider, routes) {
    var setRoute = function (routeConfig) {
      $routeProvider.when(routeConfig.url, routeConfig.config)
    };
    angular.forEach(routes, function (value, key) {
      setRoute(value)
    }), $routeProvider.otherwise({redirectTo: "/vm"})
  }, getRoutes = function () {
    return [{
      url: "/vm",
      title: "云主机",
      config: {templateUrl: "/static/apps/cloudvm/views/virtual-machine.html"}
    }, {
      url: "/vm-disk",
      title: "云硬盘",
      config: {templateUrl: "/static/apps/cloudvm/views/vm-disk.html"}
    }, {
      url: "/vm-vpc",
      title: "私有网络",
      config: {templateUrl: "/static/apps/cloudvm/views/vm-vpc.html"}
    }, {
      url: "/vm-router",
      title: "路由器",
      config: {templateUrl: "/static/apps/cloudvm/views/vm-router.html"}
    }, {
      url: "/vm-floatIP",
      title: "公网IP",
      config: {templateUrl: "/static/apps/cloudvm/views/vm-floatIP.html"}
    }, {
      url: "/vm-snapshot",
      title: "快照",
      config: {templateUrl: "/static/apps/cloudvm/views/vm-snapshot.html"}
    }, {
      url: "/vm-image",
      title: "镜像",
      config: {templateUrl: "/static/apps/cloudvm/views/vm-image.html"}
    }, {url: "/vm-keypair", title: "密钥", config: {templateUrl: "/static/apps/cloudvm/views/vm-keypair.html"}}]
  };
  app.constant("routes", getRoutes()), app.config(["$routeProvider", "routes", routeConfigurator])
}), require.config({
  paths: {
    jquery: "../../javascripts/dist/jquery.min",
    bootstrap: "../../javascripts/dist/bootstrap.min",
    angular: "../../javascripts/dist/angular-package.min",
    common: "../../javascripts/common",
    browserCheck: "../../staticPage/js/browserCheck",
    app: "./app-build",
    "app.router": "./app.route"
  },
  shim: {
    browserCheck: {deps: ["jquery"], exports: "browserCheck"},
    bootstrap: {deps: ["jquery"], exports: "bootstrap"},
    angular: {deps: ["common"], exports: "angular"}
  }
}), require(["jquery", "angular", "common", "bootstrap", "app", "app.router"], function (jquery, angular) {
  angular.bootstrap(document, ["myApp"])
}), define("main-build", function () {
});