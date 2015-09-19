/**
 * Created by jiangfei on 2015/8/19.
 */
define(['services/app.service'],function (serviceModule) {
  serviceModule.factory('WidgetService', ['$modal','toaster',
    function ($modal,toaster) {
      var service = {};

      service.openConfirmModal = function (title, message) {
        return $modal.open({
          templateUrl: 'ConfirmModalTpl',
          controller: 'ConfirmModalCtrl',
          size: '400',
          resolve: {
            message:function(){
              return  message;
            },
            title:function(){
              return  title;
            }
          }
        });
      };

      service.notifyError=function(message){
        toaster.pop('error', null, message, 2000, 'trustedHtml');
      };
      service.notifyInfo=function(message){
        toaster.pop('info', null, message, 2000, 'trustedHtml');
      };
      service.notifySuccess=function(message){
        toaster.pop('success', null, message, 2000, 'trustedHtml');
      };
      service.notifyWarning=function(message){
        toaster.pop('warning', null, message, 2000, 'trustedHtml');
      };

      return service;
    }]);
});