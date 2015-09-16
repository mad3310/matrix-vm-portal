/**
 * Created by jiangfei on 2015/7/22.
 */
define(['directives/app.directive'],function (directiveModule) {

    directiveModule.directive('leModal', function () {
        return {
            restrict: 'AE',
            scope: {
                option: '=option'
            },
            transclude: true,
            link: function (scope, element, attrs) {
                var modalEl = element.children().first();
                modalEl.modal({
                    show: false,
                    backdrop: false
                });
                scope.$watch('option.isShow', function (newValue, oldValue) {
                    if (newValue) {
                        modalEl.modal('show');
                    }
                    else {
                        modalEl.modal('hide');
                    }
                });
            },
            templateUrl: '/app/directives/le-modal/template.html'
        };
    });
    directiveModule.directive("passwordVerify", function() {
        return {
            require: "ngModel",
            scope: {
                passwordVerify: '='
            },
            link: function(scope, element, attrs, ctrl) {
                var isInit=true
                scope.$watch(function() {
                    var combined;

                    if (scope.passwordVerify || ctrl.$viewValue) {
                        combined = scope.passwordVerify + '_' + ctrl.$viewValue;
                    }
                    return combined;
                }, function(value) {
                    if(isInit){
                        isInit=false;
                        ctrl.$setValidity("passwordVerify", true);
                        return true;
                    }
                    if (value) {
                        ctrl.$parsers.unshift(function(viewValue) {
                            var origin = scope.passwordVerify;
                            if (origin !== viewValue) {
                                ctrl.$setValidity("passwordVerify", false);
                                return undefined;
                            } else {
                                ctrl.$setValidity("passwordVerify", true);
                                return viewValue;
                            }
                        });
                    }
                });
            }
        };
    });
});