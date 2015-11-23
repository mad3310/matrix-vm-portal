define(function(require){
	var $ = require('jquery');
    var Common = require('../../common');
    var cn = new Common();

/*初始化工具提示*/
    cn.Tooltip('#serviceName');

/*手风琴收放效果箭头变化*/
    cn.Collapse();

/*modal提示框居中*/
    cn.center();
    
/*加载数据*/
    var dataHandler = require('./dataHandler');
    var cacheInfoHandler = new dataHandler();
    cn.GetData("/cache/"+$("#cacheId").val(),cacheInfoHandler.cacheInfoHandler);
});
