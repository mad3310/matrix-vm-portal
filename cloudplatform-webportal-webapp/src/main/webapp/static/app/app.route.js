/**
 * Created by jiangfei on 2015/7/22.
 */
define(['app'],function (app) {
  'use strict';

  var routeConfigurator = function ($routeProvider, routes) {
      var setRoute = function (routeConfig) {
        if (!routeConfig.abstract) {
          $routeProvider.when(routeConfig.url, routeConfig.config);
        }
      };
      angular.forEach(routes, function (value, key) {
        setRoute(value);
        if (value.subRoutes) {
          angular.forEach(value.subRoutes, function (value, key) {
            setRoute(value);
            if (value.subSubRoutes) {
              angular.forEach(value.subSubRoutes, function (value, key) {
                setRoute(value);
              });
            }
          });
        }
      });
      $routeProvider.otherwise({redirectTo: '/rds/cluster/container-cluster'});
    },
    getRoutes = function () {
      return [
        {//begin Dashboard routes
          url: '/dashboard',
          title: 'Dashboard',
          icon: 'fa fa-tachometer',
          config: {
            template: 'Dashboard'
          }
        },//end Dashboard routes
        {//begin rds routes
          url: '/rds',
          title: 'RDS管理',
          icon: 'fa fa-database',
          abstract: true,
          subRoutes: [
            {
              url: ' /rds/cluster',
              title: '集群管理',
              abstract: true,
              subSubRoutes: [
                {
                  url: '/rds/cluster/container-cluster',
                  title: 'Container集群列表',
                  isSpaUrl:true,
                  config: {
                    templateUrl: '/app/views/rds/cluster-manage/container-cluster.html'
                  }
                }, {
                  url: '/rds/cluster/container-list',
                  title: 'Container列表',
                  isSpaUrl:true,
                  config: {
                    templateUrl: '/app/views/vm-list.html'
                  }
                }
              ]
            }, {
              url: '/rds/backup',
              title: '备份与恢复',
              isSpaUrl:true,
              config: {
                template: '备份与恢复'
              }
            }, {
              url: '/rds/monitor',
              title: '监控管理',
              abstract: true,
              subSubRoutes: [
                {
                  url: '/rds/monitor/container-cluster',
                  title: 'Container集群监控图',
                  isSpaUrl:true,
                  config: {
                    template: 'Container集群监控图'
                  }
                }, {
                  url: '/rds/monitor/resource',
                  title: 'rds资源监控',
                  isSpaUrl:true,
                  config: {
                    template: 'rds资源监控'
                  }
                }
              ]
            }
          ]
        },//end rds routes
        {//begin gce routes
          url: '/gce',
          title: 'GCE管理',
          icon: 'fa fa-inbox',
          abstract: true,
          subRoutes: [
            {
              url: '/gce/cluster',
              title: '集群管理',
              abstract: true,
              subSubRoutes: [
                {
                  url: '/gce/cluster/container-cluster',
                  title: 'Container集群列表',
                  isSpaUrl:true,
                  config: {
                    template: 'gce Container集群列表'
                  }
                }, {
                  url: '/gce/cluster/container-list',
                  title: 'Container列表',
                  isSpaUrl:true,
                  config: {
                    template: 'gce Container列表'
                  }
                }
              ]
            }, {
              url: '/list/gce/server',
              title: 'GCE列表',
              config: {
                template: 'GCE列表'
              }
            }
          ]
        },//end rds routes
        {//start billing routes
          url: '/billing',
          title: '计费管理',
          icon: 'fa fa-money',
          abstract: true,
          subRoutes: [
            {
              url: '/billing/baseElements',
              title: '基础元素管理',
              abstract: true,
              subSubRoutes: [
                {
                  url: '/billing/baseElements/baseElement',
                  title: '计费元素维护',
                  isSpaUrl:true,
                  config: {
                    templateUrl: '/app/views/billing/baseElement.html'
                  }
                }, {
                  url: '/billing/baseElements/specification',
                  title: '元素规格维护',
                  isSpaUrl:true,
                  config: {
                    templateUrl: '/app/views/billing/ElementSpecification.html'
                  }
                },{
                  url: '/billing/baseElements/region',
                  title: '地域维护',
                  isSpaUrl:true,
                  config: {
                    templateUrl: '/app/views/billing/region.html'
                  }
                }
              ]
            }, {
              url: '/billing/products',
              title: '产品管理',
              abstract: true,
              subSubRoutes: [
                {
                  url: '',
                  title: '产品维护',
                  isSpaUrl:true,
                  config: {
                    templateUrl: ''
                  }
                }, {
                  url: '',
                  title: '产品配置维护',
                  isSpaUrl:true,
                  config: {
                    templateUrl: ''
                  }
                },{
                  url: '',
                  title: '产品价格维护',
                  isSpaUrl:true,
                  config: {
                    templateUrl: ''
                  }
                }
              ]
            }
          ]
        }
      ];
    };

  app.constant('routes', getRoutes());

  app.config(['$routeProvider', 'routes', routeConfigurator]);

});
