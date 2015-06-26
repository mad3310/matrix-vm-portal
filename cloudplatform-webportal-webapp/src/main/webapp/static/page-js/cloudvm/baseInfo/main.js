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
    
    $("#getPublicIp").click(function(){
    	cn.alertoolSuccess("正在获取公网IP...");
    	data = {
    		vmId: $("#vmId").val()
    	}
    	cn.PostData('/ecs/region/'+$("#region_id").val()+'/vm-publish',data,function(){
    		cn.alertoolSuccess("公网IP获取成功，新IP生效可能需要几秒钟。");
    		getBaseInfo();
    	});
    });
    getBaseInfo();
	function getBaseInfo(){
	    cn.GetData('/ecs/region/'+$("#region_id").val()+'/vm/'+$("#vmId").val(),vmInfoHandler.VmInfoHandler);
	};
});
