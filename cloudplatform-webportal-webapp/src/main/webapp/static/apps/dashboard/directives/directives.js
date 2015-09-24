/**
 * Created by jiangfei on 2015/7/22.
 */
define(['directives/app.directive'],function (directiveModule) {

    directiveModule.directive('leExpander', function () {
        return {
            restrict: 'AE',
            transclude: true,
            replace:true,
            scope: {
            	layout:'@'
            },
            template :'<div ng-transclude></div>',
            link: function (scope, element, attrs) {
            	scope.$watch('layout',function(){
            		var item = element.find(".operation-items");
            		if(scope.layout == 'top-expander'){
            			item.addClass("operation-expander");
            			item.height(item.height()+415);
                        item.css('transition', 'height .5s ease-in-out');
            		}else if(scope.layout == 'top-shrink'){
            			item.removeClass("operation-expander");
            			item.height(item.height()-415);
                        item.css('transition', 'height .5s ease-in');
            		}
            	});
            }
        };
    });
//     directiveModule.directive('dataPicker', ['$filter',function($filter) {
//     var dateFilter = $filter('date');
//     return {
//         require: 'ngModel',
//         link: function(scope, elm, attrs, ctrl) {
//             function formatter(value) {
//                 return dateFilter(value,'yyyy-MM-dd'); //format
//             }
//             function formatterRemote(value) {
//                 return dateFilter(value,'hh::mm'); //format2
//             }
//             function parser() {
//                 return ctrl.$modelValue;
//             }
//             ctrl.$formatters.push(formatter);
//             ctrl.$formatters.push(formatterRemote);
//             ctrl.$parsers.unshift(parser);
//         }
//     };
// }]);
});