<%@ page language="java" pageEncoding="UTF-8"%>
<!-- <div class="content-right" data-spm="2"> -->
    <div class="bread-crumb">
        <span class="item"><a href="#" data-spm-anchor-id="5176.788314868.2.1">云引擎GCE</a></span>&nbsp;&gt;&nbsp;<span class="data-spm-click" data-id="5-2-2">GCE的多个访问地址含义</span>
    </div>
    <h2 style="text-align: center;color: #333;font-size: 18px;">GCE的多个访问地址含义</h2>
    <div class="article-content">
        <p><span>使用云引擎GCE时，会发现多处都提及<span style="line-height:1.5;font-family:微软雅黑,Microsoft YaHei;font-size:10.5pt;">【访问地址】</span>的概念，如下图，</p>
        <p><img src="${ctx}/static/img/help/gce-access1.jpg"></p>
        <p><span>列表中给出的访问地址，是运行nginx的container的Ip，基本信息页中的访问地址，是运行容器的container的Ip，对应上图中的例子，列表中<span style="line-height:1.5;font-family:微软雅黑,Microsoft YaHei;font-size:10.5pt;">【172.17.0.163:8001&nbsp;&nbsp;172.17.2.192:8001】</span>是运行nginx的container的Ip。基本信息页中给出的IP<span style="line-height:1.5;font-family:微软雅黑,Microsoft YaHei;font-size:10.5pt;">【172.17.0.157:8080&nbsp;&nbsp;172.17.2.191:8080 】</span>，是运行容器的container的Ip。可以参考<a>GCE服务架构</a>，更清晰的理解GCE的整个服务架构</p>
    </div>
<!-- </div> -->
<script>
    var target=$('.data-spm-click').attr('data-id');
    $('.vnavbar').find('a').removeClass('current');
    $('.vnavbar').find('li[data-id='+target+']').children('a').addClass('current');
    $('li[data-id='+target+']').closest('ul').removeClass('hidden');
    $('li[data-id='+target+']').closest('ul').prev().find('span').removeClass('arrow-down').addClass('arrow-up');
</script>