<%@ page language="java" pageEncoding="UTF-8"%>
<!-- <div class="content-right" data-spm="2"> -->
<div class="bread-crumb">
	<span class="item"><a href="#">云数据库RDS</a></span>&nbsp;&gt;&nbsp;<span
		class="data-spm-click" data-id="2-1-3">优势</span>
</div>
<h2 style="text-align: center; color: #333; font-size: 18px;">RDS优势</h2>
<div class="article-content">
	<div class="markdown-body ng-scope" ui-view="doc"
		style="margin: 0 10px; margin-bottom: 25px;">
		<div id="doc" doc-preloader="isload"
			style="padding-top: 10px; min-height: 400px; font-size: 14px; line-height: 26px;"
			class="ng-scope doc-preloader-hide">
			<div>
				<ul>
					<li><p>多主机</p>
						<p>每台RDS拥有三个物理节点作为主节点提供服务，一个主节点发生故障，无需切换至其他节点
服务可用性高达99.99%</p>
						</li>
					<li><p>安全防护</p> <p>自定义访问IP白名单</p><p>防DDoS攻击,SQL注入告警
多重备份，数据可靠性高达99.9999%
						</p>
						</li>
					<li><p>简单易用 </p>
					<p>一键式数据迁移，可视化管理面板操作</p>
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