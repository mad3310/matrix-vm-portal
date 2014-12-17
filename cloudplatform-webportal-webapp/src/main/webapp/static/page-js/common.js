/**
 * Created by yaokuo on 2014/12/12.
 */
define(function(require,exports,module){
    var $ = require('jquery');
    require('bootstrap')($);
    var Common = function (){
    };
    module.exports = Common;

    Common.prototype = {
        Tooltip : function (id){
            $(id).hover(function() {
                $(this).tooltip('show');
            }, function() {
                $(this).tooltip('hide');
            });
        },

        Sidebar : function(index){
            var extended = function(obj){   //obj为有二级菜单的li
                $(obj).removeClass("active");
                $(obj).find("ul").removeClass("hide");
                $(obj).find("span").removeClass("glyphicon glyphicon-chevron-right");
                $(obj).find("span").addClass("glyphicon glyphicon-chevron-down");
            }

            var stacked = function(obj){
                if($(obj).find("li").hasClass("active")){ //如果二级目录含有active
                    $(obj).addClass("active");
                }
                $(obj).find("ul").addClass("hide");
                $(obj).find("span").removeClass("glyphicon glyphicon-chevron-down");
                $(obj).find("span").addClass("glyphicon glyphicon-chevron-right");
            }

            /*二级菜单点击事件处理*/
            $('.sidebar-selector').find("ul ul").closest("li").click(function(){
                if( $(this).find("ul").hasClass("hide")){
                    extended(this);
               }else{
                    stacked(this);
               }
            });

            /*跳转界面处理*/
            $('#sidebar').find(".active").removeClass("active");
            var $obj = $('#sidebar').children("ul").children("li:eq("+index[0]+")");
            if($obj.find("ul").length > 0){
                extended($obj);
                $obj.find("li:eq("+index[1]+")").addClass("active");
            }else{
                $obj.addClass("active");
            }
        },

        GetData : function(url,handler){  //异步获取数据,将数据交给handler处理
            $.ajax({
                url:url,
                type:"get",
                dataType:'json',
                success:function(data){
                    /*添加当handler为空时的异常处理*/
                    handler(data);
                }
            });
        },

        PostData : function (url,data,handler){ //异步提交数据,将返回数据交给handler处理
            $.ajax({
                url:url,
                type:"post",
                dataType:'json',
                data:data,
                success:function(data){
                    /*添加当handler为空时的异常处理*/
                    handler(data);
                }
            });
        }

        /*add new common function*/
    }
});