<%@ page language="java" pageEncoding="UTF-8"%>
<!-- <div class="content-right" data-spm="2"> -->
    <div class="bread-crumb">
        <span class="item"><a href="#" data-spm-anchor-id="5176.788314868.2.1">云数据库RDS</a></span>&nbsp;&gt;&nbsp;<span class="data-spm-click" data-id="2-3-5">gbalancer连接数据库</span>
    </div>
    <h2 style="text-align: center;color: #333;font-size: 18px;">gbalancer连接数据库</h2>
    <div class="article-content">
        <p>
            <span style="font-family:microsoft yahei;">客户端安装gbalancer代理服务，后端其中一台或者两台mysql服务故障后，前端依然可以连接数据库,如下图:</span>
        </p>
        <p><img title="" src="${ctx}/static/img/help/22.jpg"></p>
    </div>
<!-- </div> -->
<script>
    var target=$('.data-spm-click').attr('data-id');
    $('.vnavbar').find('a').removeClass('current');
    $('.vnavbar').find('li[data-id='+target+']').children('a').addClass('current');
    $('li[data-id='+target+']').closest('ul').removeClass('hidden');
    $('li[data-id='+target+']').closest('ul').prev().find('span').removeClass('arrow-down').addClass('arrow-up');
</script>