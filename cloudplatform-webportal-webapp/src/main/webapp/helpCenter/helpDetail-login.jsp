<%@ page language="java" pageEncoding="UTF-8"%>
<!-- <div class="content-right" data-spm="2"> -->
    <div class="bread-crumb">
        <span class="item"><a href="#?spm=5176.788314868.2.1.snDEqL&amp;urlFlag=hot_and_nav&amp;categoryId=8314827" data-spm-anchor-id="5176.788314868.2.1">用户中心</a></span>&nbsp;&gt;&nbsp;<span class="data-spm-click" data-id="1-1">矩阵系统用户登录</span>
    </div>

    <h2 style="text-align: center;color: #333;font-size: 18px;">矩阵系统用户登录</h2>
    <div class="article-content">
        <p><span>1. 矩阵系统是通过oauth用户验证,地址：<a href="http://matrix.lecloud.com/">http://matrix.lecloud.com/</a> (旧域名 <a href="http://rds.et.letv.com">http://rds.et.letv.com</a>)    建议使用chrome和firefox浏览器注册用户，登录分为内网用户登录和普通注册用户登录。【<span style="line-height:1.5;font-family:微软雅黑,Microsoft YaHei;font-size:10.5pt;">内网用户登录】，用户名为公司邮箱前缀，密码为ldap密码。</span>如下图：</span></p>
        <p><img title="" src="${ctx}/static/img/help/04.jpg"></p>
        <p><span>【<span style="line-height:1.5;font-family:微软雅黑,Microsoft YaHei;font-size:10.5pt;">普通用户登录】，用户名为注册时填写的用户名，密码为注册密码。</span>如下图：</span></p>
        <p><img title="" src="${ctx}/static/img/help/05.jpg"></p>
    </div>
<!-- </div> -->
<script>
    var target=$('.data-spm-click').attr('data-id');
    $('.vnavbar').find('a').removeClass('current');
    // $('.sub-level').addClass('hidden').prev().find('span').removeClass('arrow-up').addClass('arrow-down');
    $('.vnavbar').find('li[data-id='+target+']').children('a').addClass('current');
</script>