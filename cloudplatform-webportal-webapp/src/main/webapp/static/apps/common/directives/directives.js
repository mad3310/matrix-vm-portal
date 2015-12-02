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

    directiveModule.directive('nameInputNozhRestrict', function(Config) {
        return {
            require: 'ngModel',
            link: function(scope, elm, attrs, ctrl) {
                ctrl.$validators.nameInputNozhRestrict = function(modelValue, viewValue) {
                    if (viewValue && Config.REGEX.NAME_NO_ZH.test(viewValue)) {
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

    directiveModule.directive('passwordConfirm', function(Config) {
        return {
            require: 'ngModel',
            scope: {
                passwordModel: '=passwordModel'
            },
            link: function(scope, elm, attrs, ctrl) {
                var isInit=true
                scope.$watch(function() {
                    var combined;

                    if (scope.passwordModel || ctrl.$viewValue) {
                        combined = scope.passwordModel + '_' + ctrl.$viewValue;
                    }
                    return combined;
                }, function(value) {
                    if(isInit){
                        isInit=false;
                        ctrl.$setValidity("passwordConfirm", true);
                    }
                    if (value) {
                        if (scope.passwordModel !== ctrl.$viewValue) {
                            ctrl.$setValidity("passwordConfirm", false);
                        } else {
                            ctrl.$setValidity("passwordConfirm", true);
                        }
                    }
                });
            }
        };
    });

    directiveModule.directive('numericInput', function () {
        var MAX=10,
          MIN=1;
        return {
            restrict: 'AE',
            scope: {
                externalModel: '=numericModel',
                max: '=numericMax',
                min: '=numericMin',
            },
            link: function (scope, element, attrs) {
                scope.model=scope.externalModel;
                if(scope.max===undefined){
                    scope.max=MAX;
                }
                if(scope.min===undefined){
                    scope.min=MIN;
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
                scope.$watch('model',function(value) {
                    if (!isNaN(value)) {
                        var validatedValue = Number(value);
                        if (validatedValue > scope.max) {
                            scope.model = scope.max;
                        }
                        else if (validatedValue < scope.min) {
                            scope.model = scope.min;
                        }
                        else{
                            scope.model = validatedValue;
                        }
                    }
                    else {
                        scope.model = scope.min;
                    }
                    scope.externalModel = scope.model;
                });
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
                scope.toggleSelect=function(e){
                    if(!scope.isOpen){
                        scope.isOpen = true;
                        e.stopPropagation();
                        $document.bind('click',closeDropdown);
                    }
                };
                scope.selectOption=function(selectedOption){
                    scope.model=selectedOption;
                };
                var closeDropdown=function( evt ) {
                    if(!scope.isOpen){
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

    directiveModule.directive('leSlider', function (Utility) {
        return {
            restrict: 'AE',
            scope: {
                externalModel: '=leSliderModel',
                step:'@leSliderStep',
                min:'=leSliderMin',
                isMinChangeable:'@isSliderMinChangeable',
                max:'@leSliderMax',
                unit:'@leSliderUnit',
            },
            link: function (scope, element, attrs) {
                var delayQueueModelHandler = Utility.delayQueueModel();
                var max = Number(scope.max),
                  min = Number(scope.min),
                  isExternalModelChangedInDirective=false;
                if(scope.isMinChangeable === 'true'){
                    scope.$watch('min', function (newValue) {
                        min = Number(newValue);
                        scope.model = min;
                    });
                }
                scope.$watch('externalModel', function (newValue) {
                    if(isExternalModelChangedInDirective){
                        isExternalModelChangedInDirective=false;
                        return;
                    }
                    scope.model = newValue;
                });
                scope.$watch('model', function (newValue) {
                    if (newValue !== null && !isNaN(newValue)) {
                        if(!Utility.isInt(newValue)){
                            newValue = Math.floor(newValue);
                        }
                        if (newValue <= max && newValue >= min) {
                            if(newValue % scope.step!==0) {
                                newValue = Math.ceil(newValue/scope.step) * scope.step;
                            }
                            delayQueueModelHandler(newValue, function (value) {
                                scope.model = Number(value);
                                isExternalModelChangedInDirective=true;
                                scope.externalModel = scope.model;
                            });
                        }
                        else if (newValue > max) {
                            scope.model = max;
                        }
                        else if (newValue < min) {
                            scope.model = min;
                        }
                        else {
                        }
                    }
                    else {
                        scope.model = min;
                    }
                });
            },
            templateUrl: '/static/apps/common/directives/le-slider/template.html'
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

    directiveModule.directive('leAutoFocus', function ($timeout) {
        return {
            restrict: 'AE',
            link: function (scope, element, attrs) {
                if (element && element[0]) {
                    $timeout( function () { element[0].focus(); } );
                }
            }
        };
    });

    directiveModule.directive('inputValidationTooltip', function ($timeout) {
        return {
            restrict: 'AE',
            scope: {
                message: '@validationMessage'
            },
            link: function (scope, element, attrs) {
            },
            templateUrl: '/static/apps/common/directives/input-validation-tooltip/template.html'
        };
    });
});