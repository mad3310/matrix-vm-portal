/**
 * Created by yaokuo on 2014/12/12.
 * baseInfo page
 */
define(function(require){
	var $ = require('jquery');
    var common = require('../../common');
    var cn = new common();

/*手风琴收放效果箭头变化*/
    cn.Collapse();
    
/*加载数据*/
    var dataHandler = require('./dataHandler');
    var vmInfoHandler = new dataHandler();

    cn.GetData('/ecs/region/'+$("#region_id").val()+'/vm/'+$("#vmId").val(),vmInfoHandler.VmInfoHandler);
});
