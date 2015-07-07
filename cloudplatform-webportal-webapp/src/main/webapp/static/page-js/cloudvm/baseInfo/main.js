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
    
	var region=$("#region_id").val();
	var vmId=$("#vmId").val();
    var getBaseInfo=function(){
	    cn.GetData('/ecs/region/'+region+'/vm/'+vmId,vmInfoHandler.VmInfoHandler);
	};
    $(".btn-publicip").click(function(e){
    	var btnId= $(e.currentTarget).attr('id');
    	var operatingTip,operatedTip,operateUrl;
    	var doOperation=function(){
        	cn.alertoolSuccess(operatingTip);
        	cn.PostData(operateUrl,{vmId: vmId},function(){
        		cn.alertoolSuccess(operatedTip);
        		getBaseInfo();
        	});
    	};
    	switch(btnId){
	    	case 'btn_publicip_bind':
	    		operatingTip="正在绑定公网IP...";
	    		operatedTip="公网IP绑定成功，新IP生效可能需要几秒钟。";
	    		operateUrl='/ecs/region/'+region+'/vm-publish';
	    		doOperation();
	    		break;
	    	case 'btn_publicip_unbind':
	    		operatingTip="正在解绑公网IP...";
	    		operatedTip="公网IP解绑成功。";
	    		operateUrl='/ecs/region/'+region+'/vm-unpublish';
	    		doOperation();
	    		break;
	    	default:break;
    	}

    });
    getBaseInfo();
});
