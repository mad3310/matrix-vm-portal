<%@ page language="java" pageEncoding="UTF-8"%>
<!-- <div class="content-right" data-spm="2"> -->
    <div class="bread-crumb">
        <span class="item"><a href="#">通用云引擎GCE</a></span>&nbsp;&gt;&nbsp;<span class="data-spm-click" data-id="5-1-3">优势</span>
    </div>
    <h2 style="text-align: center;color: #333;font-size: 18px;">GCE优势</h2>
	<div class="article-content">
		<div class="markdown-body ng-scope" ui-view="doc"
			style="margin: 0 10px; margin-bottom: 25px;">
			<div id="doc" doc-preloader="isload"
				style="padding-top: 10px; min-height: 400px; font-size: 14px; line-height: 26px;"
				class="ng-scope doc-preloader-hide">
				<url class="doc-menu">
				<li style="color: #ccc; padding-top: 10px; margin-left: 0px;"><a
					class="menu-a doc-top-title" menuid="menu0" style="color: #333;">优势</a></li>
				</url>
				<h1 id="menu0">优势</h1>
				<div style="overflow: auto;">
					<table>
						<thead>
							<tr>
								<th>对比项</th>
								<th>GCE</th>
								<th>传统应用</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>运维成本</td>
								<td>自动化统一运维，屏蔽运维细节</td>
								<td>需要有相应的运维人员</td>
							</tr>
							<tr>
								<td>人员投入</td>
								<td>不需要专门的运维人员和DBA等</td>
								<td>需要专门的运维人员和DBA</td>
							</tr>
							<tr>
								<td>可用性</td>
								<td>高可用，故障时自动迁移，不影响用户的访问</td>
								<td>需要自己实现保障可用性的策略，成本较高</td>
							</tr>
							<tr>
								<td>安全性</td>
								<td>专业的安全团队保障托管环境的安全，无需投入对应的人力进行保障</td>
								<td>托管环境的安全需要投入对应的人力进行保障</td>
							</tr>
						</tbody>
					</table>
				</div>
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