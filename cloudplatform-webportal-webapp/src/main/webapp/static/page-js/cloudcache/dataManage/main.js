/**
 * Created by jinglinlin@letv.com on 2015/01/22.
 * backup page js
 */
define(function(require){
	var common = require('../../common');
	var cn = new common();

	/*禁用退格键退回网页*/
	window.onload=cn.DisableBackspaceEnter();

    /*加载数据*/
    var dataHandler = require('./dataHandler');
    
});
