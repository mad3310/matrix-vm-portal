/**
 * Created by yaokuo on 2014/12/12.
 */
define(function(require){
    var common = require('../common');
    var cn = new common();

    /*初始化工具提示*/
    cn.Tooltip();

    /*加载数据*/
    var dataHandler = require('./dataHandler');
    var basicInfoHandler = new dataHandler();

    /*初始化按钮*/
    $("#rds-purchase").click(function(){
        location.href = "/detail/dbCreate";
    })

    /*
     * 加载资源数量：rds数
     */
    cn.GetData("/dashboard/statistics",basicInfoHandler.resCountHandler);
});
