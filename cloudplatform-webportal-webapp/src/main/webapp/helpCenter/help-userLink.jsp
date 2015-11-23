<%@ page language="java" pageEncoding="UTF-8"%>
<!-- <div class="content-right" data-spm="2"> -->
    <div class="bread-crumb">
        <span class="item"><a href="#" data-spm-anchor-id="5176.788314868.2.1">云数据库RDS</a></span>&nbsp;&gt;&nbsp;<span class="data-spm-click" data-id="2-3-6">用户连接不上数据库</span>
    </div>
    <h2 style="text-align: center;color: #333;font-size: 18px;">用户连接不上数据库</h2>
    <div class="article-content">
        <p>
            <span>1. </span>
            <span style="font-family:microsoft yahei;">确定在<a href="http://matrix.lecloud.com/">http://matrix.lecloud.com/</a> 页面中创建了用户，登录页面后，选择数据库如zhangtest022--->账号管理---选择创建的用户如zhang_ad---->ip访问权限中包含现在业务机器的ip地址，
如果没有此业务机器的ip地址，参考<a href="" class="help-createUser" data-spm-click="help-createUser.jsp">"创建用户流程"</a>；如果个人办公电脑登录问题，请参考<a href="" class="help-connect" data-spm-click="help-connect.jsp">"个人办公电脑怎么连接数据库"</a>相关设置。如下图:</span>
        </p>
        <p><img title="" src="${ctx}/static/img/help/23.jpg"></p>
        <p>
            <span>2. </span>
            <span style="font-family:microsoft yahei;">页面中能够查看到有用户名和业务机器ip，查看gbalancer,如下：
            <code>
            <br>
                ==========================================<br>
                # cat /etc/gbalancer/3306configuration.json <br>
                {<br>
                    &nbsp;&nbsp;"Addr": "0.0.0.0", <br>
                    &nbsp;&nbsp;"Backend": [<br>
                    &nbsp;&nbsp;&nbsp;&nbsp;"10.150.147.251:3306",<br> 
                    &nbsp;&nbsp;&nbsp;&nbsp;"10.150.147.252:3306", <br>
                    &nbsp;&nbsp;&nbsp;&nbsp;"10.150.147.253:3306"<br>
                    &nbsp;&nbsp;], <br>
                    &nbsp;&nbsp;"Pass": "LHHImkJM", <br>
                    &nbsp;&nbsp;"Port": "3306", <br>
                    &nbsp;&nbsp;"User": "monitor"<br>
                }<br>
            </code>
            </span>
        </p>
        <p>
            <span></span>
            <span style="font-family:microsoft yahei;">
            连接其中一个数据库: mysql  -uzhang_test03 -p -h10.150.147.252 ,如果连接失败，请参考<a href="" class="help-createUser" data-spm-click="help-createUser.jsp">"创建用户流程"</a>；<br>
通过nc命令查看数据库端口是否可以连通<br>
# nc -zv 10.150.147.252 3306<br>
Connection to 10.150.147.252 3306 port [tcp/mysql] succeeded!<br>
注意：如果是乐影公司、致新可能连接不上数据库，请联系客服，需要IDC添加访问权限。</span></p><br>
        <p>
            <span>3. </span>连接其中一个数据库连接成功，连接本地127.0.0.1失败（mysql -uzhang_test03 -p -h127.0.0.1）<br>
查看gbalancer进程是否存在：ps -ef|grep gblanacer<br>
若不存在gbalancer进程，可启动gbalancer服务： /etc/init.d/gbalancer start 或者/etc/init.d/3306gbalancer start <br>
再次查看gbalancer进程，可参考wiki文档 <a href="http://wiki.letv.cn/pages/viewpage.action?pageId=34900154">monit 监控gbalancer</a></p>
    </div>
<!-- </div> -->
<script>
    var target=$('.data-spm-click').attr('data-id');
    $('.vnavbar').find('a').removeClass('current');
    $('.vnavbar').find('li[data-id='+target+']').children('a').addClass('current');
    $('li[data-id='+target+']').closest('ul').removeClass('hidden');
    $('li[data-id='+target+']').closest('ul').prev().find('span').removeClass('arrow-down').addClass('arrow-up');
     htmlLoad('help-connect');
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