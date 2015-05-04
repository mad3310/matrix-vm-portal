/**
 * Created by yaokuo on 2014/12/12.
 */
define(function(require){
    var common = require('../../common');
    var cn = new common();

    /*初始化工具提示*/
    cn.Tooltip();

    /*加载数据*/
    var dataHandler = require('./dataHandler');
    var basicInfoHandler = new dataHandler();

    /*
     * 加载资源数量：rds数
     */
    
    cn.GetData("/dashboard/statistics",basicInfoHandler.resCountHandler);
    //查询缓存是否显示
    if(cn.getCookie('tiptool')){//已读过tip
    }else{
        setTimeout(function(){
            var _tip=$('[data-type=tip]');
            var len2=0;
            _tip.each(function() {
                if($(this).parent('li').is(':hidden')){
                    len2++;
                }
            });
            var relaLen=len2;
            if(relaLen>0){//已读过tip
            }else{
                $('.shade-container').append('<div class="shade"></div>')
                cn.inite();
                cn.validate();
                cn.resizeUpdate();
            }
        },500);
    }
});
