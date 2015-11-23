define(function(require){
    var Common = require('../../common');
    var cn = new Common();
    cn.initNavbarMenu([{
        name : "开放缓存服务 OCS",
        herf : "/list/cache"
    }]);
    /*加载数据*/
    var dataHandler = require('./dataHandler');
    var basicInfoHandler = new dataHandler();
    
    /*初始化侧边栏菜单*/
    var index = [1,0];
    cn.Sidebar(index);//index为菜单中的排序(1-12)
    /*
     * 加载cache基础信息
     */
    cn.GetData("/cache/"+$("#cacheId").val(),basicInfoHandler.resCountHandler);
});
