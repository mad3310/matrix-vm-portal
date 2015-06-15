<%@ page language="java" pageEncoding="UTF-8"%>
<!-- <div class="content-right" data-spm="2"> -->
    <div class="bread-crumb">
        <span class="item"><a href="#">开放缓存OCS</a></span>&nbsp;&gt;&nbsp;<span class="data-spm-click" data-id="4-2">OCS服务使用</span>
    </div>
    <h2 style="text-align: center;color: #333;font-size: 18px;">OCS服务使用</h2>
    <div class="article-content">
        <p><span>1. 登录后，若为新用户，使用开放缓存服务，首先要开通服务，请选择左下角，【<span style="line-height:1.5;font-family:微软雅黑,Microsoft YaHei;font-size:10.5pt;">购物车图标】</span>，如下图：</p>
        <p><img title="" src="${ctx}/static/img/help/31.jpg"></p>
        <p><span>2. 【<span style="line-height:1.5;font-family:微软雅黑,Microsoft YaHei;font-size:10.5pt;">创建缓存实例】</span>，正确填写缓存实例名称，选择可用区，实例类型和实例大小，点击购买，创建自己的缓存实例，如下图：</p>
        <p><img title="" src="${ctx}/static/img/help/32.jpg"></p>
        <p><span>3.  若为老用户，新建缓存实例，可通过点击自己创建的OCS服务进入缓存实例列表页，点击页面右上，如下图：</p>
        <p><img title="" src="${ctx}/static/img/help/33.jpg"></p>
        <p><span>4. 缓存实例创建成功后可以点击实例管理查看基本信息和moxi配置信息，如下图：</span></p>
        <p><img title="" src="${ctx}/static/img/help/34.jpg"></p>
        <p><span>4. 缓存实例创建成功后可以在邮件中收到创建成功邮件信息，另外会有一个moxi配置信息，如下图：</span></p>
        <p><img title="" src="${ctx}/static/img/help/35.jpg"></p>
    </div>
<!-- </div> -->
<script>
    var target=$('.data-spm-click').attr('data-id');
    $('.vnavbar').find('a').removeClass('current');
    $('.vnavbar').find('li[data-id='+target+']').children('a').addClass('current');
    $('li[data-id='+target+']').closest('ul').removeClass('hidden');
    $('li[data-id='+target+']').closest('ul').prev().find('span').removeClass('arrow-down').addClass('arrow-up');
</script>