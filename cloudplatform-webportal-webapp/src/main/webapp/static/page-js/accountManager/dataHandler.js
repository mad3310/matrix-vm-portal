/**
 * Created by yaokuo on 2014/12/14.
 */
define(function(require,exports,module){
    var $ = require('jquery');
    var common = require('../common');
    var cn = new common();

    var DataHandler = function(){
    };

    module.exports = DataHandler;

    DataHandler.prototype = {
        DbUserListHandler : function(data){
            var $tby = $('#tby');
            var array = data.data;
            for(var i= 0, len= array.length;i<len;i++){
                var td1 = $("<td>"+ array[i].username +"</td>");
                var td2 = $("<td>" + cn.TranslateStatus(array[i].status) +"</td>");
                var td3 = $("<td>"+ array[i].readWriterRate + "</td>");
                var td4 = $("<td><span>"+array[i].maxConcurrency+"</span></td>");
                var td5 = $("<td class=\"text-right\"> <div><a href=\"#\">ip访问权限</a><span class=\"text-explode\">|</span><a href=\"#\">重置密码</a><span class=\"text-explode\">|</span><a href=\"#\">修改权限</a><span class=\"text-explode\">|</span><a href=\"#\">删除</a> </div></td>");
                var tr = $("<tr></tr>");
                tr.append(td1).append(td2).append(td3).append(td4).append(td5);
                tr.appendTo($tby);
            }
        }
    }
});