<%@ page language="java" pageEncoding="UTF-8"%>
<!-- <div class="content-right" data-spm="2"> -->
    <div class="bread-crumb">
        <span class="item"><a href="#" data-spm-anchor-id="5176.788314868.2.1">负载均衡SLB</a></span>&nbsp;&gt;&nbsp;<span class="data-spm-click" data-id="6-3-2">SLB实例列表字段说明</span>
    </div>
    <h2 style="text-align: center;color: #333;font-size: 18px;">SLB实例列表字段说明</h2>
    <div class="article-content">
        <p><span>1. GCE实例列表中，有<span style="line-height:1.5;font-family:微软雅黑,Microsoft YaHei;font-size:10.5pt;">【实例名称】【服务地址】【服务端口】【所在可用区】</span>等的字段，下文会对其中几个字段给出解释，如下图，</p>
        <p><img src="${ctx}/static/img/help/slb-list1.jpg"></p>
        <br>
        <p>2. <span style="line-height:1.5;font-family:微软雅黑,Microsoft YaHei;font-size:10.5pt;">【实例名称】</span>指slb实例的名称</p>
        <p>3. <span style="line-height:1.5;font-family:微软雅黑,Microsoft YaHei;font-size:10.5pt;">【服务地址】</span>指请求服务的地址，同时也是container集群的Ip。此处的container集群指运行slb(gbalancer)的container集群的Ip地址。</p>
        <p>4. <span style="line-height:1.5;font-family:微软雅黑,Microsoft YaHei;font-size:10.5pt;">【服务端口】</span>，监听服务的端口，该端口可在<span style="line-height:1.5;font-family:微软雅黑,Microsoft YaHei;font-size:10.5pt;">【服务监听配置】</span>中修改配置。</p>
        <p><span>5. <span style="line-height:1.5;font-family:微软雅黑,Microsoft YaHei;font-size:10.5pt;">【所在可用区】</span>，是可用的物理机集群。</p>
    </div>
<!-- </div> -->
<script>
    var target=$('.data-spm-click').attr('data-id');
    $('.vnavbar').find('a').removeClass('current');
    $('.vnavbar').find('li[data-id='+target+']').children('a').addClass('current');
    $('li[data-id='+target+']').closest('ul').removeClass('hidden');
    $('li[data-id='+target+']').closest('ul').prev().find('span').removeClass('arrow-down').addClass('arrow-up');
</script>