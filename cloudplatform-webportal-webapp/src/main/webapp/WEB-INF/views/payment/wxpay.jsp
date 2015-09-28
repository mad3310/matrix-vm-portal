<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!--引入头部header-->
<!DOCTYPE html>
<!--[if lte IE 8 ]>
<html class="ielow" lang="zh-CN">
<![endif]-->
<!--[if (gt IE 8)|!(IE)]><!-->
<!--<![endif]-->
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="renderer" content="webkit">
    <title>乐视云视频-最专业的视频开放平台，提供一站式视频解决方案</title>
    <meta name="Keywords" content="乐视云视频，免费视频空间，视频存储，免费空间，企业视频，播客，视频建站，视频营销，在线视频，开放平台"/>
    <meta name="Description" content="乐视云视频是乐视网旗下视频开放平台，专注于为客户提供视频上传、存储、转码、调取、个性化定制、统计等一站式视频解决方案"/>
    <link rel="shortcut icon" href="http://static.uc.letvcloud.com/images/favicon.ico"/>
    <link type="text/css" rel="stylesheet" href="/assets/css/main/usercenterRecharge.css"/>
    <style type="text/css">
        #WeChat_Box{position: relative;}
        #WeChatRefresh{  position: absolute;top: 26px;left: 50%;margin-left: -74px;width: 148px;height: 148px;cursor: pointer;}
        .WeChat_mask{height: 100%; background: #000; opacity: 0.7; filter:alpha(opacity=70);}
        .WeChat_wrap{position: absolute; top: 0; left: 0; width: 100%; padding-top: 44px; text-align: center; color: #fff; line-height: 26px;}
    </style>
    <!--[if lt IE 9]>
    <script src="http://cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="http://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    <!--[if IE 8]>
    <div class="ielow-div">
        <div class="ielow-content">亲，您正在使用过时的浏览器，提升体验您可以：<img src="http://static.uc.letvcloud.com/images/ie6_ie.png"><a
                href="http://windows.microsoft.com/zh-cn/internet-explorer/download-ie">【升级IE浏览器】</a>或下载<img
                src="images/ie6_360.png" style="padding-left: 8px"><a href="http://se.360.cn/">【360安全浏览器】</a><img
                src="images/ie6_chrome.png" style="padding-left: 8px"><a href="http://www.google.cn/intl/zh-CN/chrome/">【chrome浏览器】</a>
        </div>
    </div>
    <![endif]-->
    <!--[if lte IE 7]>
    <script src="http://static.uc.letvcloud.com/js/min/lowBowerNoie.js"></script>
    <![endif]-->
</head>
<body>
<!--中间部分/S-->
<section class="le-section-user clearfix">
    <div class="WeChatQR_Top">
        乐视云计算支付
        <hr/>
    </div>
    <div class="WeChatQR_Buttom" style="border:none">
        <div class="WeChatPay">
            <img src="http://static.uc.letvcloud.com/images/WeChatPay_Right.png"/><span>微信支付</span>
        </div>
        <div class="WeChat_Line"><img src="http://static.uc.letvcloud.com/images/WeChat_Line.png"/></div>
        <div class="WeChat_Money">
            ￥ <span>${money}</span>
        </div>
        <div id="WeChat_Box">
            <img alt="二维码" id="qrcodeImage" src="${ctx}/pay/qrcodeImage/${orderNum}?pattern=2"/>
            <div id="WeChatRefresh" style="display:none;">
                <div class="WeChat_mask"></div>
                <div class="WeChat_wrap"><p>二维码已失效</b><br/>请点击刷新</p></div>
            </div>
        </div>
        <input type="hidden" id="corderId" value="${orderNum}" />
        <div style="width:220px;height:2px;margin:0 auto;" id="code"></div>
        <div class="WeChat_Line WeChat_Line_MarginTop">
            <img src="http://static.uc.letvcloud.com/images/WeChat_Line.png"/>
        </div>
        <div class="WeChat_PlayCode">
            <img src="http://static.uc.letvcloud.com/images/WeChat_Scan.png"/><span>请使用微信扫码支付</span>
        </div>
    </div>
</section>
<script type="text/javascript" src="http://static.uc.letvcloud.com/js/min/jquery-1.7.2.min.js"></script>
<script type="text/javascript">
   var _timer = null;
   var INTERVAL = 50000;
   var repeatCount = 0;
   function fStartPoll(fFuc){
       _timer = setInterval(function() {
           fFuc && fFuc();
       }, INTERVAL);
   }

   function fClearPoll(){
       clearInterval(_timer);
       _timer = null;
   }
   function fFuc(){
       fClearPoll();
       $("#WeChatRefresh")[0].style.display = 'block';
       $("#WeChatRefresh")[0].onclick=function(){
           $("#qrcodeImage").attr("src","${ctx}/pay/qrcodeImage/${orderNum}?pattern=2&"+Math.random());
           $("#WeChatRefresh")[0].style.display = 'none';
           repeatCount = 0;
           fStartPoll(fFuc);
       }

   }
    $(function () {
        fStartPoll(fFuc);
    });
</script>
</body>
</html>