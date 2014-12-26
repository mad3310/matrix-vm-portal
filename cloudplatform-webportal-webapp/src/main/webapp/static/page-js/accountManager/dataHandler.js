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
        	$(".data-tr").remove();
            var $tby = $('#tby');
            var array = data.data;
            for(var i= 0, len= array.length;i<len;i++){
                var td1 = $("<td>"+ array[i].username +"</td>");
                var td2 = $("<td>" + cn.TranslateStatus(array[i].status) +"</td>");
                var td3 = $("<td>"+ array[i].readWriterRate + "</td>");
                var td4 = $("<td><span>"+array[i].maxConcurrency+"</span></td>");
                var td5 = $("<td class=\"text-right\"> <div><a href=\"#\">ip访问权限</a><span class=\"text-explode\">|</span><a href=\"#\">重置密码</a><span class=\"text-explode\">|</span><a href=\"#\">修改权限</a><span class=\"text-explode\">|</span><a href=\"#\">删除</a> </div></td>");
                var tr = $("<tr class='data-tr'></tr>");
                tr.append(td1).append(td2).append(td3).append(td4).append(td5);
                tr.appendTo($tby);
            }
        },
        CreateDbUserIpHandler: function(data){
            InitDoubleFrame(".multi-select",data.data);
        },
        DbUserIpListHandler: function(data){
            var $tby = $("#ipList-tby");
            $tby.find("tr").remove();
            var array = data.data;

            var rank = 0;
            var ips = '';
            var tr =$("<tr></tr>");
            for(var i= 0, len= array.length;i<len;i++){
                var td = $("<td width=\"25%\">"+array[i]+"</td>");
                td.appendTo(tr);
                ips = ips+array[i]+",";
                if(rank < 3){
                    rank++;
                }else{
                    tr.appendTo($tby);
                    tr =$("<tr></tr>");
                    rank = 0;
                }
            }
            if(tr.length>0){
                tr.appendTo($tby);
            }

            $("#iplist-textarea").val(ips);
        }
    }

    /*双选框初始化 --begin*/
    function InitDoubleFrame(id,data){
        var $d = $(id);		                    //双选框div
        $sl = $d.find(".select-list-left");		//左选框ul
        $sr = $d.find(".select-list-right");	//右选框ul
        for(var i= 0,len=data.length;i<len;i++){
            if(data[i].used == 0){
                AddToLeftFrame($sl,data[i]);
            }else{
                AddToRightFrame($sr,data[i]);
            }
        };
        /*初始化选中操作*/
        SelectToggle($d);
        /*双击移动操作*/
        DoubleClickToggle($d);

        /*添加选中内容*/
        $d.find(".btn_db_add").click(function(){
            var selected= [];
            $sl.find(".active").each(function (i,val) {
                selected.push({addr:$(val).html(),id:$(val).val()});
            });
            $sl.find(".active").remove();
            for(var i= 0,len=selected.length;i<len;i++){
                AddToRightFrame($sr,selected[i]);
            }
            SelectToggle($d);
        });
        $d.find(".btn_db_remove").click(function(){
            var selected= [];
            $sr.find(".active").each(function (i,val) {
                selected.push({addr:$(val).find("p:first").html(),id:$(val).val()});
            });
            $sr.find(".active").remove();
            for(var i= 0,len=selected.length;i<len;i++){
                AddToLeftFrame($sl,selected[i]);
            }
            SelectToggle($d);
        });
        /*全选按钮初始化*/
        $d.find(".select-all-rw").click(function(){
            var $sr = $(this).closest(".mcluster-select");
            $sr.find("[type='radio']").attr("checked",false);
            var displayContent = $(this).html();
            if(displayContent == "全部设管理"){
                $(this).html("全部设读写");
                $sr.find(".mgr").prev().attr("checked",true);
            }else if (displayContent == "全部设读写"){
                $(this).html("全部设只读");
                $sr.find(".rw").prev().attr("checked",true);
            }else{
                $(this).html("全部设管理");
                $sr.find(".ro").prev().attr("checked",true);
            }
        });

    };

    function AddToLeftFrame($sl,data){
        var $li = $("<li value=\""+data.id+"\" class=\"select-item\">"+data.addr+"</li>");
        $li.appendTo($sl);
    };
    function AddToRightFrame($sr,data){
        var $li = $("<li value=\""+data.id+"\"  class=\"select-item\"> "
            + "<p class=\"pull-left\">"+data.addr+"</p>"
            + "<p class=\"pull-right\" style=\"margin-right:5px\">"
            + "<span>"
            + "<input type=\"radio\" name=\""+data.addr+"\" value=\"1\" checked=\"checked\">"
            + "<label class=\"mgr\">管理</label>"
            + "</span>"
            + "<span>"
            + "<input type=\"radio\" name=\""+data.addr+"\" value=\"2\">"
            + "<label class=\"ro\">只读</label>"
            + "</span>"
            + "<span>"
            + "<input type=\"radio\" name=\""+data.addr+"\" value=\"3\">"
            + "<label class=\"rw\">读写</label>"
            + "</span>"
            + "</p>"
            + "</li>");
        $li.appendTo($sr);
    };
    function SelectToggle($d){
        $d.find("li").each(function() {
            if ($(this).find("p").length == 0) {
                $(this).unbind("click");
                $(this).click(function () {
                    $(this).toggleClass("active");
                });
            } else {
                $(this).find("p:first").unbind("click");
                $(this).find("p:first").click(function () {
                    $(this).closest("li").toggleClass("active");
                });
            }


        })
    };
    function DoubleClickToggle($d){
        $d.find("li").unbind("dblclick");
        $sl = $d.find(".select-list-left");		    //左选框ul
        $sr = $d.find(".select-list-right");	    //右选框ul
        $sl.find("li").each(function(){
            $(this).dblclick(function(){
                var selected= [];
                selected.push({addr:$(this).html(),id:$(this).val()});
                $(this).remove();
                AddToRightFrame($sr,selected[0]);
                SelectToggle($d);                //重新初始化单机事件
                DoubleClickToggle($d);           //重新初始化双击事件
            })
        });
        $sr.find("li").each(function(){
            $(this).dblclick(function(){
                var selected= [];
                selected.push({addr:$(this).find("p:first").html(),id:$(this).val()});
                $(this).remove();
                AddToLeftFrame($sl,selected[0]);
                SelectToggle($d);               //重新初始化单机事件
                DoubleClickToggle($d);          //重新初始化双击事件
            })
        });
    }
    function GetDoubleValue(id){
        var s = $(id);
    }
    /*双选框初始化 --end*/
});
