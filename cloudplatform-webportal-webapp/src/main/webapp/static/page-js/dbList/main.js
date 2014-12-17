/**
 * Created by yaokuo on 2014/12/12.
 */
define(function(require){
    var common = require('../common');
    var cn = new common();

    /*加载数据*/
    var dataHandler = require('./dataHandler');
    var currentPage=1,recordsPerPage=15,searchContent=null;
    var dbListHandler = new dataHandler();

    cn.GetData("/db/"+currentPage+"/"+recordsPerPage+"/"+searchContent,dbListHandler.DbListHandler);
});
