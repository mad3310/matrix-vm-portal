<%@ page language="java" pageEncoding="UTF-8"%>
<!-- <div class="content-right" data-spm="2"> -->
    <div class="bread-crumb">
        <span class="item"><a href="#" data-spm-anchor-id="5176.788314868.2.1">云数据库RDS</a></span>&nbsp;&gt;&nbsp;<span class="data-spm-click" data-id="2-1-2">创建数据库用户</span>
    </div>
    <h2 style="text-align: center;color: #333;font-size: 18px;">创建数据库用户</h2>
    <div class="article-content">
        <p><span>1. 登录后，进入集群服务，未开通此服务的，通过购买数据库，开通该服务。在数据库详细列表中选择一个创建成功的数据库，进入【<span style="line-height:1.5;font-family:微软雅黑,Microsoft YaHei;font-size:10.5pt;">数据库管理页】。注意，创建数据库用户首先进行ip配置，即进入<span> 【<span style="line-height:1.5;font-family:微软雅黑,Microsoft YaHei;font-size:10.5pt;">安全控制】</span>，添加此集群中业务ip地址，请注意ip添加规则：两个IP地址之间"逗号"应为英文输入法"逗号"，如下图：</p>
       
        <p><img title="" src="${ctx}/static/img/help/15.jpg"></p>
        <p><span>2. 页面左menu菜单，【<span style="line-height:1.5;font-family:微软雅黑,Microsoft YaHei;font-size:10.5pt;">账号管理】</span>，在账号详细信息列表右上，【<span style="line-height:1.5;font-family:微软雅黑,Microsoft YaHei;font-size:10.5pt;">创建账号】</span>，如下图：</p>
        <p><img title="" src="${ctx}/static/img/help/16.jpg"></p>
        <p><span>3. 正确填写账号信息，数据库账号：最好与业务相关，比如读写用户sync_wr,只读用户sync_ro,管理用户sync；<br>授权IP： 这里面的ip列表是由前端"安全控制"中添加的IP列表，选择业务IP 点击“授权”，然后选择这些ip地址用户权限;<br>并发量取值范围：1~2000，默认值为50为例：<br>max_queries_per_hour=50x2x60x60<br>max_updates_per_hour=50x60x60<br>max_connections_per_hour=50x2x60x60<br>max_user_connections=50如下图：</p>
        <p><img title="" src="${ctx}/static/img/help/17.jpg"></p>
        <p><span>4.账号创建成功，如下图：</p>
        <p><img title="" src="${ctx}/static/img/help/14.jpg"></p>
        <p><span>【<span style="line-height:1.5;font-family:微软雅黑,Microsoft YaHei;font-size:10.5pt;">注意事项】</span>：<br>
1.设置业务使用中最好不要对管理用户进行并发数设置，管理用户不受并发数值影响，只读用户max_updates_per_hou不受并发量参数值影响<br>
2.在添加业务IP地址时，如果一台机器上有内网地址和外网地址，应添加内网地址；如果只有外网地址就添加外网地址<br>
3.业务程序中只能使用读写用户或者只读用户，不能使用管理用户，因为管理用户的max_updates和max_query连接数比较小，对于管理用户调整并发数没有用，这个是由底层api接口已经固定；如果读写用户需要有truncate权限时，请联系我们这边帮忙添加用户。<br>
创建完用户还需要在业务机器上安装gbalancer,wiki地址：<a href="http://wiki.letv.cn/pages/viewpage.action?pageId=34900154">monit 监控gbalancer</a>；业务程序机器安装好gbalancer后，业务机器程序直接连接本地的127.0.0.1 3306端口(如果此端口被占用，可换成如3307端口)</p>
    </div>
<!-- </div> -->
<script>
    var target=$('.data-spm-click').attr('data-id');
    $('.vnavbar').find('a').removeClass('current');
    $('.vnavbar').find('li[data-id='+target+']').children('a').addClass('current');
    $('li[data-id='+target+']').closest('ul').removeClass('hidden');
    $('li[data-id='+target+']').closest('ul').prev().find('span').removeClass('arrow-down').addClass('arrow-up');
</script>