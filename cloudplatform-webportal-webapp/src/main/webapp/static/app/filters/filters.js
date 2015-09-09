/**
 * Created by jiangfei on 2015/8/18.
 */
define(['filters/app.filter'],function (filterModule) {

  filterModule.filter('propsFilter', function () {
    return function (items, props) {
      var out = [];

      if (angular.isArray(items)) {
        items.forEach(function (item) {
          var itemMatches = false;

          var keys = Object.keys(props);
          for (var i = 0; i < keys.length; i++) {
            var prop = keys[i];
            var text = props[prop].toLowerCase();
            if (item[prop].toString().toLowerCase().indexOf(text) !== -1) {
              itemMatches = true;
              break;
            }
          }

          if (itemMatches) {
            out.push(item);
          }
        });
      } else {
        // Let the output be the input untouched
        out = items;
      }

      return out;
    }
  });

  filterModule.filter('mclusterTypeFilter', function () {
    return function (input) {
      var out = '';
      if (input) {
        out = '后台创建';
      } else {
        out = '系统创建';
      }
      return out;
    }
  });

  filterModule.filter('mclusterStatusFilter', ['Config', function (Config) {
    var allStatuses = Config.mclusterStatuses;
    return function (input) {
      var out = '';
      for (var i = 0, leng = allStatuses.length; i < leng; i++) {
        if (allStatuses[i].value == input) {
          out = allStatuses[i].text;
          break;
        }
      }
      return out || '未知';
    }
  }]);

  filterModule.filter('sideMenuUrlFilter', [function () {
    return function (route) {
      var out = '';
      if (route.abstract) {
        out = 'javascript:void(0);';
      }
      else {
        out = route.isSpaUrl ? '#' + route.url : route.url;
      }
      return out || '未知';
    }
  }]);

});