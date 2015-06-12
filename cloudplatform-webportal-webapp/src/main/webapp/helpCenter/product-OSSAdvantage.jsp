<%@ page language="java" pageEncoding="UTF-8"%>
<!-- <div class="content-right" data-spm="2"> -->
<div class="bread-crumb">
	<span class="item"><a href="#">开放存储OSS</a></span>&nbsp;&gt;&nbsp;<span
		class="data-spm-click" data-id="3-1-3">优势</span>
</div>
<h2 style="text-align: center; color: #333; font-size: 18px;">OSS优势</h2>
<div class="article-content">
	<div class="markdown-body ng-scope" ui-view="doc"
		style="margin: 0 10px; margin-bottom: 25px;">
		<div id="doc" doc-preloader="isload"
			style="padding-top: 10px; min-height: 400px; font-size: 14px; line-height: 26px;"
			class="ng-scope doc-preloader-hide">
			<div>
				<ul>
					<li><p>稳定</p>
						<p>服务可用性高达99.9%，系统规模自动扩展，不影响对外服务，数据三重备份，可靠性达到99.99999999%</p>
						</li>
					<li><p>安全防护</p> <p>多层次安全防护和防DDoS攻击</p><p>多用户隔离机制</p><p>提供访问日志有助于追查非法访问</p>
						</li>
					<li><p>简单易用 </p>
					<p>存储容量无限扩展，请求处理能力弹性增加，多线BGP网络确保全国各地访问流畅</p>
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