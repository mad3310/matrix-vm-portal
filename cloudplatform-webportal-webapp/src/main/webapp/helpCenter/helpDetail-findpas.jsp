<%@ page language="java" pageEncoding="UTF-8"%>
<!-- <div class="content-right" data-spm="2"> -->
    <div class="bread-crumb">
        <span class="item"><a href="#" data-spm-anchor-id="5176.788314868.2.1">用户中心</a></span>&nbsp;&gt;&nbsp;<span class="data-spm-click" data-id="1-3">矩阵系统用户找回密码</span>
    </div>

    <h2 style="text-align: center;color: #333;font-size: 18px;">矩阵系统用户找回密码</h2>
    <div class="article-content">
        <p><span style="text-transform:none;text-indent:0px;display:inline !important;font:14px/21px 微软雅黑,Microsoft YaHei;white-space:normal;float:none;letter-spacing:normal;color:rgb(0,0,0);word-spacing:0px;">1. 矩阵系统是通过oauth用户验证,地址：<a href="http://matrix.lecloud.com/">http://matrix.lecloud.com/</a> (旧域名 <a href="http://rds.et.letv.com">http://rds.et.letv.com</a>)    建议使用chrome和firefox浏览器注册用户，登录分为内网用户登录和普通注册用户登录。普通用户忘记密码可通过密码找回操作找回密码，【<span style="line-height:1.5;font-family:微软雅黑,Microsoft YaHei;font-size:10.5pt;">普通用户找回密码】，输入注册时填写的邮箱，</span>如下图：</span></p>
        <p><img title="" src="${ctx}/static/img/help/06.jpg"></p>
        <p><span style="text-transform:none;text-indent:0px;display:inline !important;font:14px/21px 微软雅黑,Microsoft YaHei;white-space:normal;float:none;letter-spacing:normal;color:rgb(0,0,0);word-spacing:0px;">【<span style="line-height:1.5;font-family:微软雅黑,Microsoft YaHei;font-size:10.5pt;">验证身份】，邮箱验证身份期间，请不要关闭页面。邮箱里没有找回邮件时，请先到垃圾邮件中寻找，若垃圾邮件中没有，那么可以通过点击【<span style="line-height:1.5;font-family:微软雅黑,Microsoft YaHei;font-size:10.5pt;">重发激活邮件】</span>如下图：</span></p>
        <p><img title="" src="${ctx}/static/img/help/07.jpg"></p>
        <p><span style="text-transform:none;text-indent:0px;display:inline !important;font:14px/21px 微软雅黑,Microsoft YaHei;white-space:normal;float:none;letter-spacing:normal;color:rgb(0,0,0);word-spacing:0px;">【<span style="line-height:1.5;font-family:微软雅黑,Microsoft YaHei;font-size:10.5pt;">重置密码】，重新输入密码，进行重置，如下图：</span></p>
        <p><img title="" src="${ctx}/static/img/help/08.jpg"></p>
    </div>
<!-- </div> -->
<script>
    var target=$('.data-spm-click').attr('data-id');
    $('.vnavbar').find('a').removeClass('current');
    // $('.sub-level').addClass('hidden').prev().find('span').removeClass('arrow-up').addClass('arrow-down');
    $('.vnavbar').find('li[data-id='+target+']').children('a').addClass('current');
</script>