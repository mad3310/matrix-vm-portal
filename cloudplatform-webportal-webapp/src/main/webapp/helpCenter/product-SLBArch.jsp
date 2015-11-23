<%@ page language="java" pageEncoding="UTF-8"%>
<!-- <div class="content-right" data-spm="2"> -->
    <div class="bread-crumb">
        <span class="item"><a href="#">负载均衡SLB</a></span>&nbsp;&gt;&nbsp;<span class="data-spm-click" data-id="6-1-2">SLB基础架构</span>
    </div>
    <h2 style="text-align: center;color: #333;font-size: 18px;">SLB基础架构</h2>
	<div class="article-content">
		<div class="markdown-body ng-scope" ui-view="doc"
			style="margin: 0 10px; margin-bottom: 25px;">
			<div id="doc" doc-preloader="isload"
				style="padding-top: 10px; min-height: 400px; font-size: 14px; line-height: 26px;"
				class="ng-scope doc-preloader-hide">
				<url class="doc-menu"></url>
				<div class="intro">负载均衡（Server Load Balancer，简称SLB）是对多台云服务器进行流量分发的负载均衡服务。SLB可以通过流量分发扩展应用系统对外的服务能力，通过消除单点故障提升应用系统的可用性。</div>
				<p><img src="${ctx}/static/img/help/product/11.jpg" alt=""></p>
				<div class="intro">SLB服务通过设置虚拟服务地址（IP），将位于同一地域（Region）的多台云服务器（简称GCE）资源虚拟成一个高性能、高可用的应用服务池；根据应用指定的方式，将来自客户端的网络请求分发到云服务器池中。SLB服务会检查云服务器池中GCE的健康状态，自动隔离异常状态的GCE，从而解决了单台GCE的单点问题，同时提高了应用的整体服务能力。在标准的负载均衡功能之外，SLB服务还具备TCP与HTTP抗DDoS攻击的特性，增强了应用服务器的防护能力。</div>
				<p><img src="${ctx}/static/img/help/product/7.jpg"alt=""></p>
				
			</div>
		</div>
	</div>
<!-- </div> -->
<script>
var target=$('.data-spm-click').attr('data-id');
$('.vnavbar').find('a').removeClass('current');
$('.vnavbar').find('li[data-id='+target+']').children('a').addClass('current');
$('li[data-id='+target+']').closest('ul').removeClass('hidden');
$('li[data-id='+target+']').closest('ul').prev().find('span').removeClass('arrow-down').addClass('arrow-up');
</script>