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

    directiveModule.directive('nameInputRestrict', function(Config) {
        return {
            require: 'ngModel',
            link: function(scope, elm, attrs, ctrl) {
                ctrl.$validators.nameInputRestrict = function(modelValue, viewValue) {
                    if (viewValue && Config.REGEX.NAME.test(viewValue)) {
                        return true;
                    }
                    return false;
                };
            }
        };
    });

    directiveModule.directive('passwordRestrict', function(Config) {
        return {
            require: 'ngModel',
            link: function(scope, elm, attrs, ctrl) {
                ctrl.$validators.passwordRestrict = function(modelValue, viewValue) {
                    if (viewValue && Config.REGEX.PASSWORD.test(viewValue)) {
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
                model: '=numericModel',
                max: '=numericMax',
                min: '=numericMin',
            },
            link: function (scope, element, attrs) {
                if(scope.max===undefined){
                    scope.max=10;
                }
                if(scope.min===undefined){
                    scope.min=1;
                }
                scope.up=function(){
                    if(++scope.model>scope.max){
                        --scope.model;
                    }
                };
                scope.down=function(){
                    if(--scope.model<scope.min){
                        ++scope.model;
                    }
                };
            },
            templateUrl: '/static/apps/common/directives/numeric-input/template.html'
        };
    });

    directiveModule.directive('leSelect', function ($document) {
        return {
            restrict: 'AE',
            scope: {
                model: '=selectModel',
                options:'=selectOptions'
            },
            link: function (scope, element, attrs) {
                scope.toggleSelect=function(){
                    scope.isOpen = true;
                    $document.bind('click',closeDropdown);
                };
                scope.selectOption=function(selectedOption){
                    scope.model=selectedOption;
                };
                var toggleElement = element.find('.le-select-toggle');
                var closeDropdown=function( evt ) {
                    if(!scope.isOpen){
                        return;
                    }
                    if ( evt && toggleElement && toggleElement[0].contains(evt.target) ) {
                        return;
                    }

                    $document.unbind('click', closeDropdown);
                    scope.isOpen = false;
                    if (!scope.$$phase) {
                        scope.$apply();
                    }
                };
            },
            templateUrl: '/static/apps/common/directives/le-select/template.html'
        };
    });

    directiveModule.directive('buyPeriodSelector', function () {
        return {
            restrict: 'AE',
            scope: {
                selectedBuyPeriod: '=buyPeriodModel',
                allBuyPeriods:'=buyPeriodOptions'
            },
            link: function (scope, element, attrs) {
                scope.selectBuyPeriod = function (buyPeriod) {
                    scope.selectedBuyPeriod = buyPeriod;
                };
                scope.isSelectedBuyPeriod = function (buyPeriod) {
                    return scope.selectedBuyPeriod === buyPeriod;
                };
            },
            templateUrl: '/static/apps/common/directives/buy-period-selector/template.html'
        };
    });
});