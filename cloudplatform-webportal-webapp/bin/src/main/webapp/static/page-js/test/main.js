/**
 * Created by yaokuo on 2015/2/6.
 */
/**
 * Created by yaokuo on 2014/12/12.
 */
define(function(require){
    var common = require('../../common');
    var cn = new common();

    /*加载数据*/
    var dataHandler = require('./dataHandler');
    var testHandler = new dataHandler();

    testHandler.setIntervalHandler();
});
