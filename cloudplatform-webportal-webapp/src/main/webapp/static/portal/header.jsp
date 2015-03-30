<%@ page language="java" pageEncoding="UTF-8"%>  
<script>

</script>
<style>
  .oauth_header{background:rgba(254,254,254,1);border-top:1px solid #ddd;border-bottom:1px solid #ddd;overflow:hidden;width:100%;max-width:1920px;height:85px;padding:12px 12.9% 0;border-top:0;}
  .oauth_container{overflow:hidden;width:100%;height:auto;margin:0 auto;}
  .oauth_container i{font-size:1.8rem;}
  .ul_horizon{list-style: none;display: inline-block;padding:0px;}
  .ul_horizon li{display:inline-block;_zoom:1;_display:inline;overflow:hidden;margin-right:5px;}
  .ul_horizon li a{padding:5px 10px;/*color:#97a6b6;*/color:#4A4F54;}
  .letv-logo>img{padding:5px;}
</style>
<div class="oauth_header">
  <div class="oauth_container">
    <ul class="ul_horizon pull-left">
      <li><a class="letv-logo" href="http://www.letvcloud.com/" target="_blank"><img src="img/letv-logo5.png" style="height:66px;width:232px;"></a></li>
    </ul>
    <ul class="ul_horizon pull-right" style="margin-top:30px;font-size:14px;">
      <li><a class="index_a" href="" ><i class="fa fa-home"></i> 首页</a></li>
      <li><a  class="index_a" href="" ><i class="fa fa-key"></i> 登录</a></li>
      <li><a class="reg_a" href="#"><i class="fa fa-tag"></i> 注册</a></li>
    </ul>
  </div>
</div>
<script>
  function urlParameter(parameter){
    var reg = new RegExp("(^|&)" + parameter + "=([^&]*)(&|$)"); //构造含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg);  
    if (r != null) return unescape(r[2]); return null;     
  }
  var redirect_uri=urlParameter('redirect_uri');
  var index_a="index.jsp?redirect_uri="+redirect_uri;
  var reg_a="reg.jsp?redirect_uri="+redirect_uri;
  $('.index_a').attr('href', index_a);
  $('.reg_a').attr('href',reg_a);
</script>