<%@ page language="java" pageEncoding="UTF-8"%>
<!-- <div class="content-right" data-spm="2"> -->
    <div class="bread-crumb">
        <span class="item"><a href="#" data-spm-anchor-id="5176.788314868.2.1">云数据库RDS</a></span>&nbsp;&gt;&nbsp;<span class="data-spm-click" data-id="2-1-4">mcluster数据库架构</span>
    </div>
    <h2 style="text-align: center;color: #333;font-size: 18px;">mcluster数据库架构</h2>
    <div class="article-content">
        <p>
            <span></span>
            <span style="font-family:microsoft yahei;">后端mcluster数据库架构采用的是galera组件,可提供三台mysql同时提供读写，相关网址：http://galeracluster.com/ ,如下图:</span>
        </p>
        <p><img title="" src="${ctx}/static/img/help/21.jpg"></p>
        <p><span>相关特性:<br>1、同步复制<br>2、真正的multi-master，即所有节点可以同时读写数据库<br>3、自动的节点成员控制，失效节点自动被清除<br>4、新节点加入数据自动复制<br>5、真正的并行复制，行级<br>6、用户可以直接连接集群，使用感受上与MySQL完全一致</p>
    </div>
<!-- </div> -->
<script>
    var target=$('.data-spm-click').attr('data-id');
    $('.vnavbar').find('a').removeClass('current');
    $('.vnavbar').find('li[data-id='+target+']').children('a').addClass('current');
    $('li[data-id='+target+']').closest('ul').removeClass('hidden');
    $('li[data-id='+target+']').closest('ul').prev().find('span').removeClass('arrow-down').addClass('arrow-up');
</script>