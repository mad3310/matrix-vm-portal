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
            		}else if(scope.layout == 'top-shrink'){
            			item.removeClass("operation-expander");
            			item.height(item.height()-415);
            		}
            	});
            }
        };
    });

});