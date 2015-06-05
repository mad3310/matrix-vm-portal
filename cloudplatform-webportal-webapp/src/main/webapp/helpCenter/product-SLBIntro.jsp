<%@ page language="java" pageEncoding="UTF-8"%>
<!-- <div class="content-right" data-spm="2"> -->
<div class="bread-crumb">
	<span class="item"><a href="#">负载均衡SLB</a></span>&nbsp;&gt;&nbsp;<span
		class="data-spm-click" data-id="6-1-1">SLB产品介绍</span>
</div>
<h2 style="text-align: center; color: #333; font-size: 18px;">SLB产品介绍</h2>
<div class="info-wrapper">
	<div class="introduce" data-spm="4" data-spm-max-idx="3">
		<h1 class="">
			<i class="icons-64 icons-ace icons-64-ace "></i>负载均衡SLB
		</h1>
		<div class="intro">负载均衡（Server Load Balancer，简称SLB）是对多台云服务器进行流量分发的负载均衡服务。SLB可以通过流量分发扩展应用系统对外的服务能力，通过消除单点故障提升应用系统的可用性</div>
	</div>
	<div class="approve" data-spm="5">
		<a class="" id="Approve" name="Approve" hidefocus="">&nbsp;</a>
		<div class="product-title">
			<h1>我们的优势</h1>
		</div>
		<div class="approve-detail y-clear">
            <div class="dispaly-4div">
                <h3 class="title">高可用</h3>
                <img class="img" src="${ctx}/static/img/help/product/8.jpg" width="190" height="60">
                <ul class="list">
                    <li>冗余设计，无单点，可用性达99.99%</li>
                    <li>根据应用负载进行弹性扩容(敬请期待...)</li>
                    <li>流量波动情况下不中断对外服务</li>
                </ul>
            </div>
            <div class="dispaly-4div">
                <h3 class="title">低成本</h3>
                <img class="img" src="${ctx}/static/img/help/product/9.jpg" width="190" height="60">
                <ul class="list">
                    <li>与传统模式相比成本下降60%</li>
                    <li>无需采购昂贵的设备，免运维</li>
                </ul>
            </div>
            <div class="dispaly-4div">
                <h3 class="title">安全</h3>
                <img class="img" src="${ctx}/static/img/help/product/10.jpg" width="190" height="60">
                <ul class="list">
                    <li>结合云盾提供防DDoS攻击(敬请期待...)</li>
                    <li>多用户资源隔离</li>
                </ul>
            </div>
    	</div>
	</div>
	<div class="function" data-spm="6">
		<div class="product-title">
			<h1>产品功能</h1>
		</div>
		<h1 class="head-title">SLB帮您轻松分发网络流量，消除单点故障提升应用系统的可用性。</h1>
		<h2 class="sub-head-title">让高可用变的更简单！</h2>
		<div class="approve-detail y-clear">
			<div class="dispaly-4div">
				<h3 class="">SLB服务类型</h3>
				<p class="">支持公网/私网类型的SLB服务；提供4层（TCP协议）和7层（HTTP和HTTPS协议）的SLB服务</p>
			</div>
			<div class="dispaly-4div">
				<h3 class="">健康检查</h3>
				<p class="">
					对后端云服务器进行健康检查，自动屏蔽异常状态云服务器，恢复正常后自动解除屏蔽。(敬请期待...	)</p>
			</div>
			<div class="dispaly-4div">
				<h3 class="">会话保持</h3>
				<p class="">提供会话保持功能，在Session生命周期内，将同一客户端请求转发到同一台后端云服务器上。</p>
			</div>
			<div class="dispaly-4div">
				<h3>转发及QoS</h3>
				<p>支持加权轮询(WRR)、最小连接数(WLC)转发方式。支持针对监听分配其对应服务的带宽峰值。</p>
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
					<li><a class="grey-font" href="${ctx}/helpCenter/helpCenter.jsp?container=help-slbquikcrt" target="_blank" >SLB快速创建指南</a></li>
					<li><a class="grey-font" href="${ctx}/helpCenter/helpCenter.jsp?container=help-createSlb" target="_blank" >常见问题</a></li>
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