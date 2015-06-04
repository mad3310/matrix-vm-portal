<%@ page language="java" pageEncoding="UTF-8"%>
<!-- <div class="content-right" data-spm="2"> -->
<div class="bread-crumb">
	<span class="item"><a href="#">负载均衡SLB</a></span>&nbsp;&gt;&nbsp;<span
		class="data-spm-click" data-id="6-1-1">SLB产品介绍</span>
</div>
<h2 style="text-align: center; color: #333; font-size: 18px;">SLB产品介绍</h2>
<div class="markdown-body ng-scope" ui-view="doc"
	style="margin: 0 10px; margin-bottom: 25px;">
	<div id="doc" doc-preloader="isload"
		style="padding-top: 10px; min-height: 400px; font-size: 14px; line-height: 26px;"
		class="ng-scope doc-preloader-hide">
		<url class="doc-menu">
		<li style="color: #ccc; padding-top: 10px; margin-left: 0px;"><a
			class="menu-a doc-top-title" menuid="menu0" style="color: #333;">SLB概述</a></li>
		<li style="color: #ccc; padding-top: 10px; margin-left: 15px;"><a
			class="menu-a doc-top-title" menuid="menu1" style="color: #333;">SLB概要</a></li>
		<li style="color: #ccc; padding-top: 10px; margin-left: 15px;"><a
			class="menu-a doc-top-title" menuid="menu2" style="color: #333;">SLB基本概念</a></li>
		<li style="color: #ccc; padding-top: 10px; margin-left: 15px;"><a
			class="menu-a doc-top-title" menuid="menu3" style="color: #333;">SLB术语表</a></li>
		<li style="color: #ccc; padding-top: 10px; margin-left: 15px;"><a
			class="menu-a doc-top-title" menuid="menu4" style="color: #333;">SLB功能概述</a></li>
		<li style="color: #ccc; padding-top: 10px; margin-left: 15px;"><a
			class="menu-a doc-top-title" menuid="menu5" style="color: #333;">SLB产品优势</a></li>
		<li style="color: #ccc; padding-top: 10px; margin-left: 15px;"><a
			class="menu-a doc-top-title" menuid="menu6" style="color: #333;">SLB使用场景</a></li>
		<li style="color: #ccc; padding-top: 10px; margin-left: 15px;"><a
			class="menu-a doc-top-title" menuid="menu7" style="color: #333;">SLB使用限制</a></li>
		<li style="color: #ccc; padding-top: 10px; margin-left: 15px;"><a
			class="menu-a doc-top-title" menuid="menu8" style="color: #333;">SLB使用注意事项</a></li>
		</url>
		<h1 id="menu0">SLB概述</h1>
		<h2 id="menu1">SLB概要</h2>
		<ul>
			<li><p>负载均衡（Server Load
					Balancer，简称SLB）是对多台云服务器进行流量分发的负载均衡服务。SLB可以通过流量分发扩展应用系统对外的服务能力，通过消除单点故障提升应用系统的可用性。</p>
			</li>
			<li><p>SLB服务通过设置虚拟服务地址（IP），将位于同一地域（Region）的多台云服务器（Elastic
					Compute
					Service，简称ECS）资源虚拟成一个高性能、高可用的应用服务池；根据应用指定的方式，将来自客户端的网络请求分发到云服务器池中。</p>
			</li>
			<li>SLB服务会检查云服务器池中ECS的健康状态，自动隔离异常状态的ECS，从而解决了单台ECS的单点问题，同时提高了应用的整体服务能力。在标准的负载均衡功能之外，SLB服务还具备TCP与HTTP抗DDoS攻击的特性，增强了应用服务器的防护能力。</li>
			<li>SLB服务是ECS面向多机方案的一个配套服务，需要同ESC结合使用。</li>
		</ul>
		<p>
			<a href="http://slb.aliyun.com">访问官网</a>
		</p>
		<h2 id="menu2">SLB基本概念</h2>
		<ul>
			<li>SLB服务主要由3个基本概念组成。
				<ol>
					<li>LoadBalancer代表一个SLB实例</li>
					<li>Listener代表用户定制的负载均衡策略和转发规则</li>
					<li>BackendServer是后端的一组ECS。</li>
					<li>来自外部的访问请求，通过SLB实例并根据相关的策略和转发规则分发到后端ECS进行处理。</li>
				</ol>
			</li>
		</ul>
		<p>slb核心概念如图示</p>
		<p>
			<img
				src="${ctx}/static/img/help/product/7.jpg"
				alt="">
		</p>
		<h2 id="menu3">SLB术语表</h2>
		<div style="overflow: auto;">
			<table>
				<thead>
					<tr>
						<th>术语</th>
						<th>全称</th>
						<th>中文</th>
						<th>说明</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>SLB</td>
						<td>Server Load Balancer</td>
						<td>负载均衡服务，简称SLB服务。</td>
						<td>阿里云计算提供的一种网络负载均衡服务，可以结合阿里云提供的ECS服务为用户提供基于ECS实例的TCP与HTTP负载均衡服务。</td>
					</tr>
					<tr>
						<td>LoadBalancer</td>
						<td>Load Balancer</td>
						<td>负载均衡服务实例，简称SLB实例。</td>
						<td>SLB实例可以理解为SLB服务的一个运行实例，用户要使用SLB服务，就必须先创建一个SLB实例，LoadBalancerId是识别用户SLB实例的唯一标识。</td>
					</tr>
					<tr>
						<td>Listener</td>
						<td>Listener</td>
						<td>负载均衡服务监听。</td>
						<td>负载均衡服务监听，包括监听端口、负载均衡策略和健康检查配置等，每个监听对应后端的一个应用服务，一个SLB实例最多支持10个监听配置。</td>
					</tr>
					<tr>
						<td>BackendServer</td>
						<td>Backend Server</td>
						<td>后端服务器。</td>
						<td>接受SLB分发请求的一组ECS，SLB服务将外部的访问请求按照用户设定的规则转发到这一组后端ECS上进行处理。</td>
					</tr>
					<tr>
						<td>Address</td>
						<td>Address</td>
						<td>服务地址</td>
						<td>系统分配的服务地址，当前为IP地址。用户可以选择该服务地址是否对外公开，来分别创建公网和私网类型的SLB服务。</td>
					</tr>
					<tr>
						<td>Certificate</td>
						<td>Certificate</td>
						<td>证书</td>
						<td>用于 HTTPS 协议。用户将证书上传到SLB中，在创建https 协议监听的时候绑定证书，提供https服务。</td>
					</tr>
				</tbody>
			</table>
		</div>
		<h2 id="menu4">SLB功能概述</h2>
		<ul>
			<li>当前提供4层（TCP协议）和7层（HTTP和HTTPS协议）的负载均衡服务。</li>
			<li>可以对后端ECS进行健康检查，自动屏蔽异常状态的ECS，待该ECS恢复正常后自动解除屏蔽。</li>
			<li>提供会话保持功能，在Session的生命周期内，可以将同一客户端请求转发到同一台后端ECS上。</li>
			<li>支持加权轮询（WRR），加权最小连接数（WLC）转发方式。WRR的方式将外部请求依序分发到后端ECS上，WLC的方式将外部请求分发到当前连接数最小的后端ECS上，后端ECS权重越高被分发的几率也越大。</li>
			<li>支持针对监听来分配其对应服务所能达到的带宽峰值。</li>
			<li>可以支持公网或私网类型的负载均衡服务。</li>
			<li>提供丰富的监控数据，实时了解SLB运行状态。</li>
			<li>结合云盾，提供WAF及防DDOS攻击能力，包括CC,SYN FLOOD等。</li>
			<li>支持同一地域（REGION）跨数据中心容灾，结合DNS还可以支持跨REGION容灾。</li>
			<li>针对HTTPS协议，提供统一的证书管理服务，证书无需上传后端ECS，解密处理在SLB上进行，降低后端ECS CPU开销。</li>
			<li>提供控制台，API，SDK多种管理方式。</li>
		</ul>
		<h2 id="menu5">SLB产品优势</h2>
		<ul>
			<li><p>高可用</p>
				<p>采用全冗余设计，无单点，支持同城容灾和跨REGION容灾，可用性高达99.99%。</p>
				<p>根据应用负载进行弹性扩容，在流量波动情况下不中断对外服务。</p></li>
			<li><p>低成本
					与传统硬件负载均衡系统高投入相比成本能下降60%，私网类型实例免费使用，无需一次性采购昂贵的负载均衡设备，无需运维投入。</p></li>
			<li><p>安全 结合云盾提供防DDoS攻击能力，包括：CC、SYN flood等DDoS攻击方式。</p></li>
		</ul>
		<h2 id="menu6">SLB使用场景</h2>
		<ul>
			<li>横向扩展应用系统的服务能力，适用于各种web server和app server。</li>
			<li>消除应用系统的单点故障，当其中一部分ECS宕机后，应用系统仍能正常工作。</li>
		</ul>
		<h2 id="menu7">SLB使用限制</h2>
		<ul>
			<li>SLB不支持跨地域（Region）部署，也就是说一个SLB实例后端的ECS必须是属于同一地域（Region）的ECS实例。</li>
			<li>在4层（TCP协议）服务中，当前不支持添加进后端云服务器池的ECS既作为Real
				Server，又作为客户端向所在的SLB实例发送请求。因为，返回的数据包只在云服务器内部转发，不经过SLB，所以通过配置在SLB内的ECS去访问的VIP是不通的。</li>
			<li>SLB不限制通过ECS ping SLB的公网IP。但是针对青岛节点、北京节点和杭州节点中一部分新购ECS无法通过ECS
				ping SLB的私网IP。这一限制并不影响SLB与ECS之间的通信。</li>
			<li>金融云的客户为了满足其安全合规的需求，目前其公网类型的SLB实例端口只能对外开放这些端口：80，443，2800-3300，5000-10000，13000-14000。</li>
		</ul>
		<h2 id="menu8">SLB使用注意事项</h2>
		<ul>
			<li>在通过SLB对外提供服务前，首先要确保已经完成并正确配置了所有SLB后端ECS上的应用服务，且能通过ECS的服务地址正确访问该服务。</li>
			<li>SLB不提供ECS间的数据同步服务，如果部署在SLB后端ECS上的应用服务是无状态的，那么可以通过独立的ECS或RDS服务来存储数据；如果部署在SLB后端ECS上的应用服务是有状态的，那么需要确保这些ECS上的数据是同步的。</li>
			<li>当SLB实例的服务地址（IP）已经解析到正常的域名进行对外服务时，请不要随意删除该SLB实例。删除SLB实例操作会将该SLB实例的服务地址（IP）一同释放掉，从而导致已经对外提供的服务中断。如果创建新的SLB实例，系统会重新分配一个服务地址（IP）。</li>
		</ul>
	</div>
</div>
<script>
	var target = $('.data-spm-click').attr('data-id');
	$('.vnavbar').find('a').removeClass('current');
	$('.vnavbar').find('li[data-id=' + target + ']').children('a').addClass(
			'current');
	$('li[data-id=' + target + ']').closest('ul').removeClass('hidden');
	$('li[data-id=' + target + ']').closest('ul').prev().find('span')
			.removeClass('arrow-down').addClass('arrow-up');
</script>