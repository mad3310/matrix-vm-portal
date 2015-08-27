<%@ page language="java" pageEncoding="UTF-8"%>
<!-- <div class="content-right" data-spm="2"> -->
    <div class="bread-crumb">
        <span class="item"><a href="#" data-spm-anchor-id="5176.788314868.2.1">开放存储OSS</a></span>&nbsp;&gt;&nbsp;<span class="data-spm-click" data-id="3-2">OSS快速创建</span>
    </div>
    <h2 style="text-align: center;color: #333;font-size: 18px;">OSS快速创建</h2>
    <div class="article-content">
        <p><span>1. 用户登录matrix后，点击“开放存储服务OSS”购买后可创建一个OSS的instance，用户可在该instance下建立文件、目录、并上传/下载文件。购买完成后，用户可以在该实例内部创建文件及目录，如下图：</p>
        <p><img title="" src="${ctx}/static/img/help/24.jpg"></p>
        <p><span>2.点选进入一个实例，可查看或配置以下几项：<span>【基本信息页】【文件管理】【系统资源监控】</span>，访问OSS使用登录matrix的用户名及密码及可，点击上图的“管理”或实例名称后，进入“基本信息”页面，如下图：</p>
        <p><img title="" src="${ctx}/static/img/help/25.jpg"></p>
        <p><span style="line-height:1.5;font-family:微软雅黑,Microsoft YaHei;font-size:10.5pt;">【基本详情】</span>您可以查看相应实例的基本信息和付费信息
        </p>
        <br>
        <p><span style="line-height:1.5;font-family:微软雅黑,Microsoft YaHei;font-size:10.5pt;">【文件管理】</span>您可以对文件进行上传、下载、删除等操作</p>
        <br>
        <p><span style="line-height:1.5;font-family:微软雅黑,Microsoft YaHei;font-size:10.5pt;">【系统资源监控】</span><span>查看【cpu使用率】【磁盘】【内存】【网络】</span></p>
        <br>
        <p><span>使用访问地址：向服务器申请token：
curl -XGET -i https://10.150.110.216:443/auth/v1.0 -H 'x-auth-key:your_matrix_password' -H 'x-auth-user: your_matrix_account_name'
如用户“dboyzhong”会返回一个token，如下图：</p>
        <p><img title="" src="${ctx}/static/img/help/26.jpg"></p>
        <p><span><h4>1) 使用该token来访问刚刚创建的实例：</h4>
curl –XGET –H’X-Auth-Token: 27ab5b67f6605dba51954a8b3b8b8b12’ -i https://10.150.110.216:443/v1/AUTH_your_matrix_account_name/ins333<br>
注意url的格式：ip:port/v1/AUTH_account_name/instance_name。<br>
此时会返回你的帐户的ins333实例下的所有对象。</span></p>
        <p><span><h4>2) 上传文件：</h4>
    curl –XPUT –H’X-Auth-Token: 27ab5b67f6605dba51954a8b3b8b8b12’ -i https://10.150.110.216:443/v1/AUTH_your_matrix_account_name/ins333/text1.txt --data-binary ‘123’
    </span>
    </p>
    <p><span><h4>3) 下载文件：</h4>
    curl –XGET –H’X-Auth-Token: 27ab5b67f6605dba51954a8b3b8b8b12’ -i https://10.150.110.216:443/v1/AUTH_your_matrix_account_name/ins333/text1.txt
    </span>
    </p>
    <p><span><h4>4) 删除文件：</h4>
    curl –XDELETE –H’X-Auth-Token: 27ab5b67f6605dba51954a8b3b8b8b12’ -i https://10.150.110.216:443/v1/AUTH_your_matrix_account_name/ins333/text1.txt
    </span>
    </p>
    </div>
<!-- </div> -->
<script>
    var target=$('.data-spm-click').attr('data-id');
    $('.vnavbar').find('a').removeClass('current');
    $('.vnavbar').find('li[data-id='+target+']').children('a').addClass('current');
    $('li[data-id='+target+']').closest('ul').removeClass('hidden');
    $('li[data-id='+target+']').closest('ul').prev().find('span').removeClass('arrow-down').addClass('arrow-up');
</script>