<%@ page language="java" pageEncoding="UTF-8"%>
<!-- <div class="content-right" data-spm="2"> -->
    <div class="bread-crumb">
        <span class="item"><a href="#" data-spm-anchor-id="5176.788314868.2.1">负载均衡SLB</a></span>&nbsp;&gt;&nbsp;<span class="data-spm-click" data-id="6-3-1">负载均衡SLB购买帮助</span>
    </div>
    <h2 style="text-align: center;color: #333;font-size: 18px;">负载均衡SLB购买帮助</h2>
    <div class="article-content">
        <p><span>1. 购买负载均衡SLB，<span style="line-height:1.5;font-family:微软雅黑,Microsoft YaHei;font-size:10.5pt;">【负载均衡名称】【可用区】【数量】</span>为必填字段，下文中会对每个必填项给出填写说明，如下图，红框为必填处：</p>
        <p><img src="${ctx}/static/img/help/slb-crt1.jpg"></p>
        <p><span>2. <span style="line-height:1.5;font-family:微软雅黑,Microsoft YaHei;font-size:10.5pt;">【负载均衡名称】</span>，需填入字母数字或'_',云应用名称不能以数字开头.如图：<img title="" src="${ctx}/static/img/help/slb-crt2.jpg"></p>
        <p><span>3. <span style="line-height:1.5;font-family:微软雅黑,Microsoft YaHei;font-size:10.5pt;">【可用区】</span>，为可用物理集群，如图：<img src="${ctx}/static/img/help/gce-crt3.jpg"></p>
        <p><span>5. <span style="line-height:1.5;font-family:微软雅黑,Microsoft YaHei;font-size:10.5pt;">【数量】</span>，表示负载均衡SLB的数量，目前默认2台，即表示每项应用服务SLB配备2个，支持高可用，如图，<img src="${ctx}/static/img/help/gce-crt6.jpg"></p>
    </div>
<!-- </div> -->
<script>
    var target=$('.data-spm-click').attr('data-id');
    $('.vnavbar').find('a').removeClass('current');
    $('.vnavbar').find('li[data-id='+target+']').children('a').addClass('current');
    $('li[data-id='+target+']').closest('ul').removeClass('hidden');
    $('li[data-id='+target+']').closest('ul').prev().find('span').removeClass('arrow-down').addClass('arrow-up');
</script>