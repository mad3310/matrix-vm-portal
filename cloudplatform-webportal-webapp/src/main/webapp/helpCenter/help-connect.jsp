<%@ page language="java" pageEncoding="UTF-8"%>
<!-- <div class="content-right" data-spm="2"> -->
    <div class="bread-crumb">
        <span class="item"><a href="#">云数据库RDS</a></span>&nbsp;&gt;&nbsp;<span class="data-spm-click" data-id="2-1-3">个人办公电脑怎么连接数据库</span>
    </div>
    <h2 style="text-align: center;color: #333;font-size: 18px;">个人办公电脑怎么连接数据库</h2>
    <div class="article-content">
        <p><span style="text-transform:none;text-indent:0px;display:inline !important;font:14px/21px 微软雅黑,Microsoft YaHei;white-space:normal;float:none;letter-spacing:normal;color:rgb(0,0,0);word-spacing:0px;">1. 先选择一个已经创建好的数据库“zhangtest022” -->系统资源监控，可查看到内网地址:10.150.147.203。在这里说明下这个ip地址不能直接用于业务程序连接使用，程序需要连接数据库请参考<a href="" class="help-createUser" data-spm-click="help-createUser.jsp">"创建用户流程"</a>，这个ip地址是专门用于个人办公环境连接数据库地址，把这个ip地址添加到白名单中,如下图：</p>
        <p><img title="" src="${ctx}/static/img/help/18.jpg"></p>
        <p><img title="" src="${ctx}/static/img/help/19.jpg"></p>
        <p><span style="text-transform:none;text-indent:0px;display:inline !important;font:14px/21px 微软雅黑,Microsoft YaHei;white-space:normal;float:none;letter-spacing:normal;color:rgb(0,0,0);word-spacing:0px;">2. 【<span style="line-height:1.5;font-family:微软雅黑,Microsoft YaHei;font-size:10.5pt;">账户管理】</span>,右上“创建数据库用户”，用户可根据自身需要创建：管理员用户、读写用户和只读用户，注意：管理用户具有修改表、添加表权限，但是管理用户max_query和max_update并发量不高，读写用户和只读用户可以在下面设置并发量（最大为2000)。如下图：</p>
        <p><img title="" src="${ctx}/static/img/help/20.jpg"></p>
        <p><span style="text-transform:none;text-indent:0px;display:inline !important;font:14px/21px 微软雅黑,Microsoft YaHei;white-space:normal;float:none;letter-spacing:normal;color:rgb(0,0,0);word-spacing:0px;">用户创建成功后，连接vip地址 10.150.147.203 3306端口：mysql -uzhang_test3 -p -h10.150.147.203</p>
    </div>
<!-- </div> -->
<script>
    var target=$('.data-spm-click').attr('data-id');
    $('.vnavbar').find('a').removeClass('current');
    $('.vnavbar').find('li[data-id='+target+']').children('a').addClass('current');
    $('li[data-id='+target+']').closest('ul').removeClass('hidden');
    $('li[data-id='+target+']').closest('ul').prev().find('span').removeClass('arrow-down').addClass('arrow-up');
    htmlLoad('help-createUser');
function htmlLoad(container){
    $('.'+container).click(function(event) {
        event.preventDefault();
        $('.GeneralQues').parent().removeClass('current');
        $('.serviceCenter').parent().removeClass('current');
        $('.vnavbar').find('.current').removeClass('current');
        //$(this).addClass('current');
        // $(this).addClass('current')
        //         .parent().siblings().find('a').removeClass('current');
        // var _sublevel=$(this).closest('.sub-level');
        // if(_sublevel.length>0){//存在二级目录
        //     console.log(_sublevel.attr('class'))
        //     _sublevel.removeClass('hidden');
        //     _sublevel.prev().children('span').removeClass('arrow-down').addClass('arrow-up');
        // }
        var source=$(this).attr('data-spm-click');
        $('.content-right').html('');
        $('.content-right').load(source);
        
    });
}
</script>