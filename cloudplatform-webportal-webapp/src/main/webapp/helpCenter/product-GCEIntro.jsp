<%@ page language="java" pageEncoding="UTF-8"%>
<!-- <div class="content-right" data-spm="2"> -->
<div class="bread-crumb">
	<span class="item"><a href="#">云引擎GCE</a></span>&nbsp;&gt;&nbsp;<span
		class="data-spm-click" data-id="5-1-1">GCE产品介绍</span>
</div>
<h2 style="text-align: center; color: #333; font-size: 18px;">GCE产品介绍</h2>
<div class="info-wrapper">
	<div class="introduce" data-spm="4" data-spm-max-idx="3">
		<h1 class="">
			<i class="icons-64 icons-ace icons-64-ace "></i>通用云引擎GCE
		</h1>
		<div class="intro">通用云应用引擎（以下简称GCE）是乐视云计算推出一款基于弹性扩展的网络应用托管平台，采用多层沙箱保护提供安全运行环境，并且整合多种软件开发常用的扩展服务，帮助开发者快速开发和部署应用程序，将开发者从系统运维、技术钻研等工作中解放出来，集中于核心业务的开发和运营。目前支持JAVA开发语言，后续会支持PHP、PYTHON、NODEJS等开发语言。</div>
	</div>
	<div class="approve" data-spm="5">
		<a class="" id="Approve" name="Approve" hidefocus="">&nbsp;</a>
		<div class="product-title">
			<h1>我们的优势</h1>
		</div>
		<div class="approve-detail y-clear">
			<div class="dispaly-4div">
				<h3 class="">稳定</h3>
				<img class="img" src="${ctx}/static/img/help/product/1.jpg" width="190" height="60">
				<ul class="list">
					<li>个别实例故障不影响整体服务</li>
					<li>健康检查机制，实时掌握服务状态</li>
					<li>设备故障，应用实例自动漂移(敬请期待...)</li>
				</ul>
			</div>
			<div class="dispaly-4div">
				<h3 class="">高效</h3>
				<img class="img" src="${ctx}/static/img/help/product/2.jpg" width="190" height="60">
				<ul class="list">
					<li>路由优化</li>
					<li>对动、静资源访问分离处理</li>
					<li>对静态资源访问进行了加速处理</li>
				</ul>
			</div>
			<div class="dispaly-4div">
				<h3 class="">安全</h3>
				<img class="img" src="${ctx}/static/img/help/product/3.jpg" width="190" height="60">
				<ul class="list">
					<li>多用户隔离</li>
					<li>防DDoS系统</li>
					<li>防密码破解</li>
				</ul>
			</div>
			<div class="dispaly-4div">
				<h3 class="">丰富的扩展服务</h3>
				<img class="img" src="${ctx}/static/img/help/product/4.jpg" width="190" height="60">
				<ul class="list">
					<li>分布式Session服务</li>
					<li>高可用RDS服务</li>
					<li>开放缓存服务</li>
					<li>开放存储服务</li>
				</ul>
			</div>
		</div>
	</div>
	<div class="function" data-spm="6">
		<div class="product-title">
			<h1>产品功能</h1>
		</div>
		<h1 class="head-title">GCE提供了安全、稳定、高效、经济的full stack应用托管平台</h1>
		<h2 class="sub-head-title">日夜坚守为您的应用保驾护航！</h2>
		<div class="function-panel y-clear">
			<div class="dispaly-4div">
				<h3 class="">应用管理</h3>
				<p class="">Java应用的创建、重启、停止、启动、删除</p>
			</div>
			<div class="dispaly-4div">
				<h3 class="">自动伸缩</h3>
				<p class="">
					应用运行过程中，GCE通过判断负载情况自动伸缩它所使用的资源，伸缩过程不影响应用对外服务，也无需用户干预(敬请期待...)</p>
			</div>
			<div class="dispaly-4div">
				<h3 class="">监控和日志查询</h3>
				<p class="">提供应用的性能分析数据，包括负载的各项参数、网络流量等</p>
			</div>
		</div>
	</div>
	<div class="helps" data-spm="9" data-spm-max-idx="7">
		<a class="" id="Help" name="Help" hidefocus="">&nbsp;</a>
		<div class="product-title">
			<h1>产品帮助</h1>
		</div>
		<div class="helps y-clear">
			<div class="help-cell cell">
				<h4>操作指南</h4>
				<ul class="link-list">
					<li><a class="grey-font" href="${ctx}/helpCenter/helpCenter.jsp?container=help-gcequikcrt" target="_blank" >GCE快速创建指南</a></li>
					<li><a class="grey-font" href="${ctx}/helpCenter/helpCenter.jsp?container=help-createGce" target="_blank" >常见问题</a></li>
				</ul>
			</div>
		</div>
	</div>
</div>
<script>
var target=$('.data-spm-click').attr('data-id');
$('.vnavbar').find('a').removeClass('current');
$('.vnavbar').find('li[data-id='+target+']').children('a').addClass('current');
$('li[data-id='+target+']').closest('ul').removeClass('hidden');
$('li[data-id='+target+']').closest('ul').prev().find('span').removeClass('arrow-down').addClass('arrow-up');
</script>