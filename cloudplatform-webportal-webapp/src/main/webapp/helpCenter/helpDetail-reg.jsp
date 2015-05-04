<%@ page language="java" pageEncoding="UTF-8"%>
<!-- <div class="content-right" data-spm="2"> -->
    <div class="bread-crumb">
        <span class="item"><a href="#" data-spm-anchor-id="5176.788314868.2.1">用户中心</a></span>&nbsp;&gt;&nbsp;<span class="data-spm-click" data-id="1-2">矩阵系统用户注册</span>
    </div>
    <h2 style="text-align: center;color: #333;font-size: 18px;">矩阵系统用户注册</h2>
    <div class="article-content">
        <p><span style="text-transform:none;text-indent:0px;display:inline !important;font:14px/21px 微软雅黑,Microsoft YaHei;white-space:normal;float:none;letter-spacing:normal;color:rgb(0,0,0);word-spacing:0px;">1. 矩阵系统是通过oauth用户验证,注册地址：<a href="http://matrix.lecloud.com/">http://matrix.lecloud.com/</a> (旧域名 <a href="http://rds.et.letv.com">http://rds.et.letv.com</a>)  建议使用chrome和firefox浏览器注册用户 ,注册第一步【<span style="line-height:1.5;font-family:微软雅黑,Microsoft YaHei;font-size:10.5pt;">完善资料】</span>邮箱、用户名、密码、重复密码及验证码均为必填项，如下图：</span></p>
        <p><img title="" src="${ctx}/static/img/help/01.jpg"></p>
        <p><span style="font-family:微软雅黑,Microsoft YaHei;font-size:14px;">2. 注册第二步【<span style="line-height:1.5;font-family:微软雅黑,Microsoft YaHei;font-size:10.5pt;">完善资料】</span>可不填或选填</span><span style="font-family:微软雅黑,Microsoft YaHei;font-size:14px;"><span style="line-height:1.5;font-family:微软雅黑,Microsoft YaHei;font-size:10.5pt;">如下图：</span></span></p>
        <p><img title="" src="${ctx}/static/img/help/02.jpg"></p>
        <p><span style="font-family:微软雅黑,Microsoft YaHei;font-size:14px;">3. 注册第三步【<span style="line-height:1.5;font-family:微软雅黑,Microsoft YaHei;font-size:10.5pt;">激活账号】</span>查收激活邮件，进行激活操作后，当前用户才能被激活。操作期间不要关闭当前激活页，若邮箱未收到激活邮件，首先请查看垃圾邮件中是否有目标邮件，若没有，请点击‘重新发送激活邮件’，进行激活操作，若依旧未能成功，请联系客服解决，激活账号页</span><span style="font-family:微软雅黑,Microsoft YaHei;font-size:14px;"><span style="line-height:1.5;font-family:微软雅黑,Microsoft YaHei;font-size:10.5pt;">如下图：</span></span></p>
        <p><img title="" src="${ctx}/static/img/help/03.jpg"></p>
        <p><span style="font-family:微软雅黑,Microsoft YaHei;font-size:14px;">4. 注册第四步【<span style="line-height:1.5;font-family:微软雅黑,Microsoft YaHei;font-size:10.5pt;">完成注册】</span>查收激活邮件，点击邮件中的链接后，跳转到第四步，完成注册</span></span></p>
    </div>
<!-- </div> -->
<script>
    var target=$('.data-spm-click').attr('data-id');
    $('.vnavbar').find('a').removeClass('current');
    // $('.sub-level').addClass('hidden').prev().find('span').removeClass('arrow-up').addClass('arrow-down');
    $('.vnavbar').find('li[data-id='+target+']').children('a').addClass('current');
</script>