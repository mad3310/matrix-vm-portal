/**
 * Created by yaokuo on 2014/12/12.
 */
define(function(require){
    var common = require('../../common');
    var cn = new common();

    /*初始化工具提示*/
    cn.Tooltip();
    /*初始化navbar-header-menu*/
    cn.initNavbarMenu([{
						name : "Le云控制台首页",
						herf : "/dashboard"
					}]);

    /*加载数据*/
    var dataHandler = require('./dataHandler');
    var basicInfoHandler = new dataHandler();

    /*
     * 加载资源数量：rds数
     */
    
    cn.GetData("/dashboard/statistics",basicInfoHandler.resCountHandler).then(function(data){
        cn.GetData("/ecs/is-authority",basicInfoHandler.InitVmModule);
    });
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
    $('.list-title .glyphicon').click(function(event) {
        var _target=$(this).closest('.product-list').children('ul');
        if($(this).hasClass('glyphicon-chevron-down')){
            $(this).removeClass('glyphicon-chevron-down').addClass('glyphicon-chevron-up');
            _target.fadeIn('400', function() {_target.css('display', 'block');});
        }else{
            $(this).removeClass('glyphicon-chevron-up').addClass('glyphicon-chevron-down');
            _target.fadeOut('400', function() {_target.css('display', 'none');});
        }
    });
});
