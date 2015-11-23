<%@ page language="java" pageEncoding="UTF-8"%>
<!-- <div class="content-right" data-spm="2"> -->
<div class="bread-crumb">
	<span class="item"><a href="#">开放缓存OCS</a></span>&nbsp;&gt;&nbsp;<span
		class="data-spm-click" data-id="4-1-1">OCS产品介绍</span>
</div>
<h2 style="text-align: center; color: #333; font-size: 18px;">OCS产品介绍</h2>
<div class="info-wrapper">
	<div class="introduce" data-spm="4" data-spm-max-idx="3">
		<h1 class="">
			<i class="icons-64 icons-ace icons-64-ace "></i>开放缓存OCS
		</h1>
		<div class="intro">开放缓存服务( Open Cache Service，简称OCS）是在线缓存服务，为热点数据的访问提供高速响应</div>
	</div>
	<div class="approve" data-spm="5">
		<a class="" id="Approve" name="Approve" hidefocus="">&nbsp;</a>
		<div class="product-title">
			<h1>我们的优势</h1>
		</div>
		<div class="approve-detail y-clear">
            <div class="dispaly-4div">
                <h3 class="title">便捷</h3>
                <img class="img" src="${ctx}/static/img/help/ocs-intro1.jpg" width="190" height="60">
                <ul class="list">
                    <li>服务开箱即用</li>
                    <li>容量弹性伸缩</li>
                    <li>配置变更不中断服务</li>
                </ul>
            </div>
            <div class="dispaly-4div">
                <h3 class="title">可靠</h3>
                <img class="img" src="${ctx}/static/img/help/ocs-intro2.jpg" width="190" height="60">
                <ul class="list">
                    <li>分布式集群及负载均衡设计</li>
                    <li>单点故障不影响服务</li>
                    <li>硬件故障自动恢复</li>
                </ul>
            </div>
            <div class="dispaly-4div">
                <h3 class="title">快速</h3>
                <img class="img" src="${ctx}/static/img/help/ocs-intro3.jpg" width="190" height="60">
                <ul class="list">
                    <li>远高于磁盘的响应速度</li>
                    <li>配合数据库使用优势尽显</li>
                </ul>
            </div>
    	</div>
	</div>
	<div class="function" data-spm="6">
		<div class="product-title">
			<h1>产品功能</h1>
		</div>
		<h1 class="head-title">提升应用和网站性能的利器：乐视云OCS</h1>
		<h2 class="sub-head-title">加速你的世界！</h2>
		<div class="approve-detail y-clear">
			<div class="dispaly-4div">
				<h3 class="">热点数据访问</h3>
				<p class="">实现热点数据的高速缓存，与数据库搭配能大幅提高应用的响应速度，极大缓解后端存储的压力</p>
			</div>
			<div class="dispaly-4div">
				<h3 class="">兼容常用协议</h3>
				<p class="">
					支持Key-Value的数据结构，兼容Memcached协议的客户端都可使用OCS服务。</p>
			</div>
			<div class="dispaly-4div">
				<h3 class="">安全机制</h3>
				<p class="">提供用户身份认证及IP地址白名单双重安全控制，限定乐视云平台内网访问使得数据更安全。</p>
			</div>
			<div class="dispaly-4div">
				<h3>监控与调整</h3>
				<p>提供实时监控与历史监控多项数据统计；支持线上调整缓存容量，升降配置瞬间完成。</p>
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
					<li><a class="grey-font" href="${ctx}/helpCenter/helpCenter.jsp?container=help-OCSuse" target="_blank" >OCS快速创建指南</a></li>
					<li><a class="grey-font" href="${ctx}/helpCenter/helpCenter.jsp?container=" target="_blank" >常见问题</a></li>
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