<%@ page language="java" pageEncoding="UTF-8"%>
<style>
/*页底footer*/
.index-ft{position:fixed;bottom:5px;left:30px;}
.index-ft,.index-ft a{color:rgba(255,255,255,.5)}
.oauth-footer{margin-top:10px;text-align:center;/*position:absolute;bottom:0;width:100%;z-index: 1000;*/}
.footer_text{color:#DADADA; font-size:1.4rem;letter-spacing:1px;}
.oauth-footer .ul_top li a{color:#666;}
.footer-logo{width:140px;height:120px; margin:0 15px;}
.partners img{-webkit-filter:grayscale(1);}
.gray { -webkit-filter: grayscale(1);-moz-filter: grayscale(1);-ms-filter: grayscale(1);-o-filter: grayscale(1);filter: grayscale(1);filter: url(../img/grayscale.svg#grayscale);filter: gray;}
.partners img:hover{-webkit-filter:grayscale(0);filter:gray;}
.partner{font-size: 16px;width: 60px;height: 60px;border-radius: 60px;overflow: hidden;background-color: #666;color: #FFF;padding:8px 12px;}
.weibo{position:relative;z-index:1000;/*margin-right:30px;*/}
.weibo a,.wechat a{opacity:.5;}
.weibo a:hover,.wechat a:hover{opacity:1;}
.weibo-img{position:absolute;width:150px;height:140px;top:-180;top:-180px\9;left:-60;left:-60px\9;}
.wechat{position:relative;z-index:1000;margin-left:15px;}
.wechat-img{position:absolute;width:150px;height:140px;top:-180;top:-180px\9;left:-60;left:-60px\9;}
.pd30{padding:30px;}
.pd15{padding:15px;}
.mt10{margin-top:10px;}
.mt30{margin-top:30px;}
.ib{display: inline-block;_zoom:1;_display:inline;}
.ul_horizon{list-style: none;display: inline-block;padding:0px;}
.ul_horizon li{display:inline-block;_zoom:1;_display:inline;overflow:hidden;margin-right:5px;}
.ul_horizon li a{padding:5px 10px;/*color:#97a6b6;*/color:#4A4F54;}
</style> 
<div class="oauth-footer" >
    <div class="oauth_container text-left pd30" style="background-color:#fff;color:#666;padding-bottom:0px;"> <!-- background-color:#f8f8f8; -->
    	<div class="ib mt10">
			<ul class="ul_horizon ul_top pd15 text-center partners">
				<li><div class="ib partner">
    				<span>合作</span><br/><span>伙伴</span>
    			</div></li>
	        	<li><a href="http://www.jd.com" target="_blank"><img class="gray" src="${ctx}/static/img/360buy.jpg" /></a></li>
	            <li><a href="http://www.taobao.com" target="_blank"><img class="gray" src="${ctx}/static/img/taobao.jpg"/></a></li>
	            <li><a href="http://www.gome.com.cn" target="_blank"><img class="gray" src="${ctx}/static/img/gome.jpg" /></a></li>
	            <li><a href="http://www.dangdang.com" target="_blank"><img class="gray" src="${ctx}/static/img/dangdang.jpg"/></a></li>
	            <li><a href="http://www.360.cn/" target="_blank"><img class="gray" src="${ctx}/static/img/360.jpg" /></a></li>
	            <li><a href="http://bj.58.com/" target="_blank"><img class="gray" src="${ctx}/static/img/58tc.jpg" /></a></li>
	            <li><a href="http://www.hunantv.com" target="_blank"><img class="gray" src="${ctx}/static/img/mangguotv.png" /></a></li>
	            <li><a href="http://www.zjstv.com" target="_blank"><img class="gray" src="${ctx}/static/img/lantianxia.png" /></a></li>
				<li><a href="http://www.lzwg.com" target="_blank"><img class="gray" src="${ctx}/static/img/jiangsu.jpg" /></a></li>
	            <li><a href="http://www.wasu.cn" target="_blank"><img class="gray" src="${ctx}/static/img/huashutv.png" /></a></li>
	            <li><a href="http://www.dangjian.com" target="_blank"><img class="gray" src="${ctx}/static/img/dangjian.png" /></a></li>
	            <li><a href="http://v.4399pk.com" target="_blank"><img class="gray" src="${ctx}/static/img/4399.jpg" /></a></li>
	           <li><a href="http://www.meilishuo.com/" target="_blank"><img class="gray" src="${ctx}/static/img/meilishuo.png" /></a></li>
	           <li><a href="http://www.dianping.com" target="_blank"><img class="gray" src="${ctx}/static/img/dzdp.png" /></a></li>
	           <li><a href="http://act.vip.com" target="_blank"><img class="gray" src="${ctx}/static/img/wph.png" /></a>
	           <li><a href="http://changba.com/" target="_blank"><img class="gray" src="${ctx}/static/img/cb.png" /></a>	         
	        </ul>
	        <div class="clearfix"></div>
        </div>
    	<div class="pull-left">
	    	<ul class="ul_horizon ul_top mt30">
	    		<li><a><span>公司简介</span></a><span>|</span></li>
	    		<li><a><span>招贤纳士</span></a><span>|</span></li>
	    		<li><a><span>广告服务</span></a><span>|</span></li>
	    		<li><a><span>联系方式</span></a><span>|</span></li>
	    		<li><a><span>版权声明</span></a><span>|</span></li>
	    		<li><a><span>法律顾问</span></a></li>
	    	</ul>
	    	<div class="clearfix"></div>
	        <ul class="ul_horizon ul_top">
	    		<li><a><span><i class="fa fa-qq"></i> 网站客服</span></a></li>
	    		<li><a><span><i class="fa fa-envelope"></i> kefu123@letv.com</span></a></li>
	    		<li><a><span><i class="fa fa-phone"></i> 400-8000-432</span></a></li>
	    		<li><a><span>乐视云计算有限公司</span></a></li>
	    		<li><a><span>Copyright &copy;2015 乐视网 All rights reserved.</span></a></li>
	    	</ul>
	        <div class="clearfix"></div>
        </div>
        <div class="pull-right mt30 pd15" style="margin-right:200px;">
        	<div class="weibo pull-left">
        		<a href="http://weibo.com/leshiyun" target="_blank" class="text-muted"><span><img src="${ctx}/static/img/wb.jpg"/></span></a>
        		<div class="weibo-img pd5 hide" style="background:#fff;">
    				<img src="${ctx}/static/img/wbqcode.png">
    			</div>
        	</div>
        	<div class="wechat pull-right">       		
        		<a class="text-muted"><span><img src="${ctx}/static/img/wx.jpg"/></span></a>
        		<div class="wechat-img pd5 hide" style="background:#fff;">
    				<img src="${ctx}/static/img/wxqcode.png">
    			</div>
        	</div>
        	<div class="clearfix"></div>
        </div>
        <div class="clearfix"></div>
    </div>
</div>
<script>
	$('.partners img').hover(function() {
		if($(this).hasClass('gray')){
			$(this).removeClass('gray');
		}else{}
	}, function() {
		if($(this).hasClass('gray')){
			
		}else{
			$(this).addClass('gray');
		}
	});
	$('.weibo').hover(function() {
		$('.weibo-img').removeClass('hide');
	}, function() {
		$('.weibo-img').addClass('hide');
	});
	$('.wechat').hover(function() {
		$('.wechat-img').removeClass('hide');
	}, function() {
		$('.wechat-img').addClass('hide');
	});
</script>