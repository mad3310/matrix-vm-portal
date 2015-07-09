<%@ page language="java" pageEncoding="UTF-8"%>
<!-- <div class="content-right" data-spm="2"> -->
    <div class="bread-crumb">
        <span class="item"><a href="#" data-spm-anchor-id="5176.788314868.2.1">云引擎GCE</a></span>&nbsp;&gt;&nbsp;<span class="data-spm-click" data-id="5-3-1">云引擎GCE购买帮助</span>
    </div>
    <h2 style="text-align: center;color: #333;font-size: 18px;">云引擎GCE购买帮助</h2>
    <div class="article-content">
        <p><span>1. 购买云引擎GCE，<span style="line-height:1.5;font-family:微软雅黑,Microsoft YaHei;font-size:10.5pt;">【云应用名称】【可用区】【服务类型】【镜像】【数量】</span>为必填字段，下文中会对每个必填项给出填写说明，如下图，红框为必填处：</p>
        <p><img src="${ctx}/static/img/help/gce-crt1.jpg"></p>
        <p><span>2. <span style="line-height:1.5;font-family:微软雅黑,Microsoft YaHei;font-size:10.5pt;">【云应用名称】</span>，需填入字母数字或'_',云应用名称不能以数字开头.如图：<img title="" src="${ctx}/static/img/help/gce-crt2.jpg"></p>
        <p><span>3. <span style="line-height:1.5;font-family:微软雅黑,Microsoft YaHei;font-size:10.5pt;">【可用区】</span>，为可用物理集群，如图：<img src="${ctx}/static/img/help/gce-crt3.jpg"></p>
        <p><span>3. <span style="line-height:1.5;font-family:微软雅黑,Microsoft YaHei;font-size:10.5pt;">【服务类型】</span>，表示云引擎GCE支持的对应该语言的环境，目前服务类型下拉框中默认java，表示GCE服务支持java开发的环境，在其后，会完善该选项，例如支持PHP、NodeJS、 Python等常用语言环境，如图，<img src="${ctx}/static/img/help/gce-crt4.jpg"></p>
        <p><span>4. <span style="line-height:1.5;font-family:微软雅黑,Microsoft YaHei;font-size:10.5pt;">【镜像】</span>，表示云引擎GCE支持的镜像地址，目前镜像下拉框中是默认镜像地址，日后会完善该选项，支持配置好的其他镜像地址，如图：<img src="${ctx}/static/img/help/gce-crt5.jpg"><br>此外，简述下镜像与应用服务程序的关系，一般，用户将应用服务程序打war包，交给portal管理员，目前仅支持portal管理员统一将war包做镜像管理，其后会完善该过程。</p>
        <p><span>5. <span style="line-height:1.5;font-family:微软雅黑,Microsoft YaHei;font-size:10.5pt;">【数量】</span>，表示云引擎GCE配置的nginx数量，目前默认2台，即表示每项应用服务GCE配备2个nginx，支持高可用，如图，<img src="${ctx}/static/img/help/gce-crt6.jpg"></p>
    </div>
<!-- </div> -->
<script>
    var target=$('.data-spm-click').attr('data-id');
    $('.vnavbar').find('a').removeClass('current');
    $('.vnavbar').find('li[data-id='+target+']').children('a').addClass('current');
    $('li[data-id='+target+']').closest('ul').removeClass('hidden');
    $('li[data-id='+target+']').closest('ul').prev().find('span').removeClass('arrow-down').addClass('arrow-up');
</script>