/**
 * Created by jiangfei on 2015/7/22.
 */
define(['./common.directive'],function (directiveModule) {

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

    directiveModule.directive('textRestrict', function() {
        var TEXT_REGEX= /[a-zA-Z_0-9]{1,15}/g;
        return {
            require: 'ngModel',
            link: function(scope, elm, attrs, ctrl) {
                ctrl.$validators.textRestrict = function(modelValue, viewValue) {
                    if (viewValue && viewValue.length>0 && viewValue.length<15) {
                        return true;
                    }
                    return false;
                };
            }
        };
    });

    directiveModule.directive('numericInput', function () {
        return {
            restrict: 'AE',
            scope: {
                model: '=numericModel'
            },
            link: function (scope, element, attrs) {
                scope.up=function(){
                    scope.model++;
                };
                scope.down=function(){
                    scope.model--;
                };
            },
            templateUrl: '/static/apps/cloudvm/directives/numeric-input/template.html'
        };
    });
});