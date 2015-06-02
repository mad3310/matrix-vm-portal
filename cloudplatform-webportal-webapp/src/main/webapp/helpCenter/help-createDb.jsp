<%@ page language="java" pageEncoding="UTF-8"%>
<!-- <div class="content-right" data-spm="2"> -->
    <div class="bread-crumb">
        <span class="item"><a href="#" data-spm-anchor-id="5176.788314868.2.1">云数据库RDS</a></span>&nbsp;&gt;&nbsp;<span class="data-spm-click" data-id="2-1-1">创建数据库实例</span>
    </div>
    <h2 style="text-align: center;color: #333;font-size: 18px;">创建数据库实例</h2>
    <div class="article-content">
        <p><span>1. 登录后，若为新用户，使用集群服务，首先要开通集群服务，请选择左下角，【<span style="line-height:1.5;font-family:微软雅黑,Microsoft YaHei;font-size:10.5pt;">购物车图标】</span>如下图：</p>
        <p><img title="" src="${ctx}/static/img/help/09.jpg"></p>
        <p><span>2. 【<span style="line-height:1.5;font-family:微软雅黑,Microsoft YaHei;font-size:10.5pt;">创建数据库实例】</span>，正确填写集群名称，选择可用区，点击购买，创建自己的数据库实例，如下图：</p>
        <p><img title="" src="${ctx}/static/img/help/10.jpg"></p>
        <p><span>3. 若为老用户，新建数据库实例，可通过点击自己创建的RDS服务进入集群详细列表页，点击页面右上，【<span style="line-height:1.5;font-family:微软雅黑,Microsoft YaHei;font-size:10.5pt;">新建数据库】</span>如下图：</p>
        <p><img title="" src="${ctx}/static/img/help/11.jpg"></p>
        <p><span>4. 数据库集群创建完成后可以在邮件中收到创建成功邮件信息，另外会有一个gbalancer配置文件信息请妥善保存，如下图：</p>
        <p><img title="" src="${ctx}/static/img/help/12.jpg"></p>
    </div>
<!-- </div> -->
<script>
    var target=$('.data-spm-click').attr('data-id');
    $('.vnavbar').find('a').removeClass('current');
    $('.vnavbar').find('li[data-id='+target+']').children('a').addClass('current');
    $('li[data-id='+target+']').closest('ul').removeClass('hidden');
    $('li[data-id='+target+']').closest('ul').prev().find('span').removeClass('arrow-down').addClass('arrow-up');
</script>