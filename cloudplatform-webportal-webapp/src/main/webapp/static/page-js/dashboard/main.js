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
    
    /*
     * 加载资源数量：rds数
     */
    cn.GetData("/dashboard/statistics",basicInfoHandler.resCountHandler);
});
