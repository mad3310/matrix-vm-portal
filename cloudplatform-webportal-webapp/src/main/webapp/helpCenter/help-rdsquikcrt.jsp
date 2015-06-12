<%@ page language="java" pageEncoding="UTF-8"%>
<!-- <div class="content-right" data-spm="2"> -->
    <div class="bread-crumb">
        <span class="item"><a href="#" data-spm-anchor-id="5176.788314868.2.1">云数据库RDS</a></span>&nbsp;&gt;&nbsp;<span class="data-spm-click" data-id="2-2">快速创建</span>
    </div>
    <h2 style="text-align: center;color: #333;font-size: 18px;">快速创建</h2>
    <div class="article-content">
        <p><span>1. 登录后，若为新用户，使用集群服务，首先要开通集群服务，请选择左下角，【<span style="line-height:1.5;font-family:微软雅黑,Microsoft YaHei;font-size:10.5pt;">购物车图标】</span>如下图：</p>
        <p><img title="" src="${ctx}/static/img/help/09.jpg"></p>
        <p><span>2. 【<span style="line-height:1.5;font-family:微软雅黑,Microsoft YaHei;font-size:10.5pt;">创建数据库实例】</span>，正确填写集群名称，选择可用区，点击购买，创建自己的数据库实例，如下图：</p>
        <p><img title="" src="${ctx}/static/img/help/10.jpg"></p>
        <p><span>3. 若为老用户，新建数据库实例，可通过点击自己创建的RDS服务进入集群详细列表页，点击页面右上，【<span style="line-height:1.5;font-family:微软雅黑,Microsoft YaHei;font-size:10.5pt;">新建数据库】</span>如下图：</p>
        <p><img title="" src="${ctx}/static/img/help/11.jpg"></p>
        <p><span>4. 数据库集群创建完成后可以在邮件中收到创建成功邮件信息，另外会有一个gbalancer配置文件信息请妥善保存，如下图：</p>
        <p><img title="" src="${ctx}/static/img/help/12.jpg"></p>
        <p>5. 点选进入一个实例，可查看或配置以下几项：<span style="line-height:1.5;font-family:微软雅黑,Microsoft YaHei;font-size:10.5pt;">【基本详情】【账号管理】【系统资源监控】【备份与恢复】【安全控制】</span></span></p>
        <p><img title="" src="${ctx}/static/img/help/18.jpg"></p>
        <p><span style="line-height:1.5;font-family:微软雅黑,Microsoft YaHei;font-size:10.5pt;">【基本详情】</span>您可以查看相应实例的基本信息和付费信息
        </p>
        <br>
        <p><span style="line-height:1.5;font-family:微软雅黑,Microsoft YaHei;font-size:10.5pt;">【账号管理】</span><span>可对数据库添加用户，具体的流程请您参考<a href class="help-slblisten" data-spm-click="help-createUser.jsp">创建数据库用户</a></span></p>
        <br>
        <p><span style="line-height:1.5;font-family:微软雅黑,Microsoft YaHei;font-size:10.5pt;">【系统资源监控】</span><span>查看【cpu使用率】【磁盘】【内存】【网络】</span></p>
        <br>
        <p><span style="line-height:1.5;font-family:微软雅黑,Microsoft YaHei;font-size:10.5pt;">【备份与恢复】</span><span>数据库备份信息查看，配置备份策略</span></p>
        <br>
        <p><span style="line-height:1.5;font-family:微软雅黑,Microsoft YaHei;font-size:10.5pt;">【安全控制】</span><span>添加ip白名单</span></p>
    </div>
<!-- </div> -->
<script>
    var target=$('.data-spm-click').attr('data-id');
    $('.vnavbar').find('a').removeClass('current');
    $('.vnavbar').find('li[data-id='+target+']').children('a').addClass('current');
    $('li[data-id='+target+']').closest('ul').removeClass('hidden');
    $('li[data-id='+target+']').closest('ul').prev().find('span').removeClass('arrow-down').addClass('arrow-up');
</script>