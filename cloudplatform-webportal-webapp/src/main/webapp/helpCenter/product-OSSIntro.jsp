<%@ page language="java" pageEncoding="UTF-8"%>
<!-- <div class="content-right" data-spm="2"> -->
<div class="bread-crumb">
	<span class="item"><a href="#">开放存储OSS</a></span>&nbsp;&gt;&nbsp;<span
		class="data-spm-click" data-id="3-1-1">OSS产品介绍</span>
</div>
<h2 style="text-align: center; color: #333; font-size: 18px;">OSS产品介绍</h2>
<div class="info-wrapper">
	<div class="introduce" data-spm="4" data-spm-max-idx="3">
		<h1 class="">
			<i class="icons-64 icons-ace icons-64-ace "></i>开放存储OSS
		</h1>
		<div class="intro">开放存储服务（Open Storage Service，OSS），是乐视云平台对外提供的海量、安全和高可靠的云存储服务。RESTFul API的平台无关性，容量和处理能力的弹性扩展，按实际容量付费真正使您专注于核心业务。</div>
	</div>
	<div class="approve" data-spm="5">
		<a class="" id="Approve" name="Approve" hidefocus="">&nbsp;</a>
		<div class="product-title">
			<h1>我们的优势</h1>
		</div>
		<div class="approve-detail y-clear">
            <div class="dispaly-4div">
                <h3 class="title">稳定</h3>
                <img class="img" src="${ctx}/static/img/help/oss-intro1.jpg" width="190" height="60">
                <ul class="list">
                    <li>服务可用性高达99.9%</li>
                    <li>系统规模自动扩展，不影响对外服务</li>
                    <li>数据三重备份，可靠性达到99.99999999%</li>
                </ul>
            </div>
            <div class="dispaly-4div">
                <h3 class="title">安全</h3>
                <img class="img" src="${ctx}/static/img/help/oss-intro2.jpg" width="190" height="60">
                <ul class="list">
                    <li>多层次安全防护和防DDoS攻击</li>
                    <li>多用户隔离机制</li>
                    <li>提供访问日志有助于追查非法访问</li>
                </ul>
            </div>
            <div class="dispaly-4div">
                <h3 class="title">大规模、高性能</h3>
                <img class="img" src="${ctx}/static/img/help/oss-intro3.png" width="190" height="60">
                <ul class="list">
                    <li>存储容量无限扩展</li>
                    <li>请求处理能力弹性增加</li>
                    <li>多线BGP网络确保全国各地访问流畅</li>
                </ul>
            </div>
    	</div>
	</div>
	<div class="function" data-spm="6">
		<div class="product-title">
			<h1>产品功能</h1>
		</div>
		<h1 class="head-title">OSS帮您轻松应对海量数据的存储和访问</h1>
		<h2 class="sub-head-title">将存储的难题交给OSS解决</h2>
		<div class="approve-detail y-clear">
			<div class="dispaly-4div">
				<h3 class="">弹性扩展</h3>
				<p class="">海量的存储空间，随用户使用量的增加，空间弹性增长，无需担心数据容量的限制。</p>
			</div>
			<div class="dispaly-4div">
				<h3 class="">大规模</h3>
				<p class="">
					能支持同时间内高并发、大流量的读写访问。</p>
			</div>
			<div class="dispaly-4div">
				<h3 class="">图片处理</h3>
				<p class="">对存储在OSS上的图片，支持缩略、裁剪、水印、压缩和格式转换等图片处理功能。</p>
			</div>
			<div class="dispaly-4div">
				<h3>按需付费</h3>
				<p>对存储空间、网络流量、请求次数，按照用户实际使用量进行计费，节省用户的成本。</p>
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
					<li><a class="grey-font" href="${ctx}/helpCenter/helpCenter.jsp?container=help-ossquikcrt" target="_blank" >OSS快速创建指南</a></li>
					<li><a class="grey-font" href="${ctx}/helpCenter/helpCenter.jsp?container=help-OSSuse" target="_blank" >常见问题</a></li>
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