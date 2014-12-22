/**
 * Created by yaokuo on 2014/12/12.
 */
define(function(require){
    var common = require('../common');
    var cn = new common();

    /*初始化工具提示*/
    cn.Tooltip('#home-cash');

    /*加载数据*/
    var dataHandler = require('./dataHandler');
    var basicInfoHandler = new dataHandler();

   // cn.GetData("/static/page-js/basicInfo/analogData/dblist.json",basicInfoHandler.BasicInfoHandler);
});
