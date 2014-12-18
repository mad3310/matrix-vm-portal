/**
 * Created by yaokuo on 2014/12/12.
 */
define(function(require){
    var common = require('../common');
    var cn = new common();

/*初始化工具提示*/
    cn.Tooltip('#serviceName');

/*初始化侧边栏菜单*/
    var index = [0,0];
    cn.Sidebar(index);//index为菜单中的排序(1-12)

/*加载数据*/
    var dataHandler = require('./dataHandler');
    var basicInfoHandler = new dataHandler();
    var runStatusHandler = new dataHandler();

    cn.GetData("/static/page-js/basicInfo/analogData/dblist.json",basicInfoHandler.BasicInfoHandler);
    cn.GetData("/static/page-js/basicInfo/analogData/statusAction.json",runStatusHandler.StatusActionHandler);
});
