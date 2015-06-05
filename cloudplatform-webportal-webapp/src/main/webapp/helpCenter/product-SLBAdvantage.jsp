<%@ page language="java" pageEncoding="UTF-8"%>
<!-- <div class="content-right" data-spm="2"> -->
<div class="bread-crumb">
	<span class="item"><a href="#">负载均衡SLB</a></span>&nbsp;&gt;&nbsp;<span
		class="data-spm-click" data-id="6-1-3">优势</span>
</div>
<h2 style="text-align: center; color: #333; font-size: 18px;">SLB优势</h2>
<div class="article-content">
	<div class="markdown-body ng-scope" ui-view="doc"
		style="margin: 0 10px; margin-bottom: 25px;">
		<div id="doc" doc-preloader="isload"
			style="padding-top: 10px; min-height: 400px; font-size: 14px; line-height: 26px;"
			class="ng-scope doc-preloader-hide">
			<div>
				<ul>
					<li><p>高可用</p>
						<p>采用全冗余设计，无单点，可用性高达99.99%。</p>
						<p>根据应用负载进行弹性扩容，在流量波动情况下不中断对外服务。</p></li>
					<li><p>低成本</p> %，私网类型实例免费使用，无需一次性采购昂贵的负载均衡设备，无需运维投入。
						</p>
						</li>
					<li><p>安全 </p>
					<p>结合云盾提供防DDoS攻击能力，包括：CC、SYN flood等DDoS攻击方式。(敬请期待...)</p>
						</li>
				</ul>
			</div>
	</div>
</div>
<!-- </div> -->
<script>
	var target = $('.data-spm-click').attr('data-id');
	$('.vnavbar').find('a').removeClass('current');
	$('.vnavbar').find('li[data-id=' + target + ']').children('a').addClass(
			'current');
	$('li[data-id=' + target + ']').closest('ul').removeClass('hidden');
	$('li[data-id=' + target + ']').closest('ul').prev().find('span')
			.removeClass('arrow-down').addClass('arrow-up');
</script>