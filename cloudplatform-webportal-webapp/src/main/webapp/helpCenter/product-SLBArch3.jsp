<%@ page language="java" pageEncoding="UTF-8"%>
<!-- <div class="content-right" data-spm="2"> -->
<div class="bread-crumb">
	<span class="item"><a href="#">负载均衡SLB</a></span>&nbsp;&gt;&nbsp;<span
		class="data-spm-click" data-id="6-1-2">SLB基础架构</span>
</div>
<h2 style="text-align: center; color: #333; font-size: 18px;">SLB基础架构</h2>
<div class="markdown-body ng-scope" ui-view="doc"
	style="margin: 0 10px; margin-bottom: 25px;">
	<div id="doc" doc-preloader="isload"
		style="padding-top: 10px; min-height: 400px; font-size: 14px; line-height: 26px;"
		class="ng-scope doc-preloader-hide">
		<url class="doc-menu">
		<li style="color: #ccc; padding-top: 10px; margin-left: 0px;"><a
			class="menu-a doc-top-title" menuid="menu0" style="color: #333;">SLB功能介绍</a></li>
		<li style="color: #ccc; padding-top: 10px; margin-left: 15px;"><a
			class="menu-a doc-top-title" menuid="menu1" style="color: #333;">SLB实例属性配置</a></li>
		<li style="color: #ccc; padding-top: 10px; margin-left: 30px;"><a
			class="menu-a doc-top-title" menuid="menu2" style="color: #333;">SLB名称</a></li>
		<li style="color: #ccc; padding-top: 10px; margin-left: 30px;"><a
			class="menu-a doc-top-title" menuid="menu3" style="color: #333;">SLB类型</a></li>
		<li style="color: #ccc; padding-top: 10px; margin-left: 30px;"><a
			class="menu-a doc-top-title" menuid="menu4" style="color: #333;">SLB服务地址</a></li>
		<li style="color: #ccc; padding-top: 10px; margin-left: 15px;"><a
			class="menu-a doc-top-title" menuid="menu5" style="color: #333;">SLB服务监听配置</a></li>
		<li style="color: #ccc; padding-top: 10px; margin-left: 30px;"><a
			class="menu-a doc-top-title" menuid="menu6" style="color: #333;">SLB协议/端口</a></li>
		<li style="color: #ccc; padding-top: 10px; margin-left: 30px;"><a
			class="menu-a doc-top-title" menuid="menu7" style="color: #333;">后端协议/端口</a></li>
		<li style="color: #ccc; padding-top: 10px; margin-left: 30px;"><a
			class="menu-a doc-top-title" menuid="menu8" style="color: #333;">转发规则</a></li>
		<li style="color: #ccc; padding-top: 10px; margin-left: 30px;"><a
			class="menu-a doc-top-title" menuid="menu9" style="color: #333;">获取来访者真实IP</a></li>
		<li style="color: #ccc; padding-top: 10px; margin-left: 30px;"><a
			class="menu-a doc-top-title" menuid="menu10" style="color: #333;">会话保持</a></li>
		<li style="color: #ccc; padding-top: 10px; margin-left: 30px;"><a
			class="menu-a doc-top-title" menuid="menu11" style="color: #333;">健康检查</a></li>
		<li style="color: #ccc; padding-top: 10px; margin-left: 30px;"><a
			class="menu-a doc-top-title" menuid="menu12" style="color: #333;">带宽峰值</a></li>
		<li style="color: #ccc; padding-top: 10px; margin-left: 15px;"><a
			class="menu-a doc-top-title" menuid="menu13" style="color: #333;">SLB后端ECS配置</a></li>
		<li style="color: #ccc; padding-top: 10px; margin-left: 30px;"><a
			class="menu-a doc-top-title" menuid="menu14" style="color: #333;">后端ECS权重</a></li>
		</url>
		<h1 id="menu0">SLB功能介绍</h1>
		<p>配置和管理一个SLB实例，主要涉及3部分的功能操作，包括：SLB实例属性配置、SLB服务监听配置和SLB后端ECS配置。通过实例属性配置来定义一个SLB实例的类型，通过服务监听配置来定义一个SLB实例的各项策略和转发规则，通过后端ECS配置来定义一个SLB实例后端用来处理用户请求的多个ECS实例。</p>
		<h2 id="menu1">SLB实例属性配置</h2>
		<h3 id="menu2">SLB名称</h3>
		<p>用户可以为其创建的SLB实例指定一个易于识别的名称，如果用户不指定，那么系统将以该SLB实例的LoadBalancerId作为名称进行展示，LoadBalancerId是识别用户SLB实例的唯一标识，无法修改。</p>
		<h3 id="menu3">SLB类型</h3>
		<p>当前提供公网和私网2种类型的SLB供用户选择，用户可根据其业务场景来选择配置对外公开或对内私有的负载均衡服务，系统根据用户的选择分配公网或私网服务地址（IP）。</p>
		<h3 id="menu4">SLB服务地址</h3>
		<p>根据用户选择的SLB类型不同，系统会分配不同的服务地址给用户。针对需要通过域名对外提供服务的应用，需要将域名解析到相应的公网服务地址上生效后即可通过域名访问。</p>
		<h2 id="menu5">SLB服务监听配置</h2>
		<h3 id="menu6">SLB协议/端口</h3>
		<ul>
			<li>协议：当前提供4层（TCP协议）和7层（HTTP和HTTPS协议）的负载均衡服务。</li>
			<li>端口：用户SLB实例对外或对内提供服务时用来接收请求并向后端服务器进行请求转发的SLB系统前端端口，在同一个SLB实例内不可重复。</li>
		</ul>
		<h3 id="menu7">后端协议/端口</h3>
		<ul>
			<li>协议：当前提供4层（TCP协议）和7层（HTTP和HTTPS协议）的负载均衡服务。</li>
			<li>端口：用户SLB实例后端添加的一组用来处理外部或内部请求的ECS上开放的用来接收请求的后端端口，在同一个SLB实例内可重复。针对同一组后端ECS上部署多个应用服务的情况，当前SLB最多支持50个监听配置规则。</li>
		</ul>
		<h3 id="menu8">转发规则</h3>
		<p>当前SLB支持轮询和最小连接数2种模式的转发规则。“轮询模式”会将外部和内部的访问请求依序分发给后端ECS进行处理，而“最小连接数模式”会将外部和内部的访问请求分发给当前连接数最小的一台后端ECS进行处理。</p>
		<h3 id="menu9">获取来访者真实IP</h3>
		<ul>
			<li>针对7层（HTTP协议）服务，由于采取替换HTTP头文件IP地址的方式来进行请求转发，所以后端云服务器看到的访问IP是SLB系统的本地IP而不是实际来访者的真实IP。所以系统支持用户采用X-Forwarded-For的方式获取访问者真实IP，前提是用户必须在配置7层（HTTP协议）服务监听时开启了“获取真实访问IP”功能。针对常用的应用服务器的配置指引<a
				href="#/pub/slb/best-practice/get-real-ipaddress">点击这里</a>查看。
			</li>
			<li>针对4层（TCP协议）服务，后端云服务器将直接获得来访者的真实IP，所以无需采用其他手段获取。</li>
		</ul>
		<h3 id="menu10">会话保持</h3>
		<p>用户开启会话保持功能后，SLB会把来自同一客户端的访问请求分发到同一台后端ECS上进行处理。
			针对7层（HTTP协议）服务，SLB系统是基于cookie的会话保持。SLB系统提供了两种cookie处理方式：</p>
		<ul>
			<li>cookie植入，表示直接由SLB系统来分配和管理对客户端进行的cookie植入操作，用户在进行配置时需要指定会话保持的超时时间；</li>
			<li>cookie重写，表示SLB系统会根据用户自定义cookie名称来分配和管理对客户端进行的cookie植入操作，便于用户识别和区分自定义的cookie名称，从而有选择的针对后端应用服务器上的不同应用设定会话保持规则，用户在进行配置时需要指定相应的cookie名称。针对多个域名配置不同会话保持规则的实现方法<a
				href="#/pub/slb/best-practice/config-session-sticky">点击这里</a>查看。
			</li>
		</ul>
		<p>针对4层（TCP协议）服务，SLB系统是基于IP地址的会话保持。SLB会来自将同一IP地址的访问请求转发到同一台后端云服务器进行处理。</p>
		<h3 id="menu11">健康检查</h3>
		<ul>
			<li>用户开启健康检查功能后，当后端某个ECS健康检查出现问题时会将请求转发到其他健康检查正常的ECS上，而当该ECS恢复正常运行时，SLB会将其自动恢复到对外或对内的服务中。</li>
			<li>针对7层（HTTP协议）服务，SLB系统的健康检查机制为：默认由SLB系统通过后端ECS内网IP地址来向该服务器应用服务器配置的缺省首页发起http
				head请求（缺省通过在服务监听配置中指定的后端ECS端口进行访问），返回200
				OK后将视为后端ECS运行正常，否则视为后端ECS运行异常。如果用户用来进行健康检查的页面并不是应用服务器的缺省首页，那么需要用户指定相应的URI。如果用户对http
				head请求限定了host字段的参数，那么需要用户指定相应的URL。用户也可以通过设定健康检查的频率、健康阈值和不健康阈值来更好的控制健康检查功能。</li>
			<li>针对4层（TCP协议）服务，SLB系统的健康检查机制为：默认由SLB系统通过在服务监听配置中指定的后端ECS端口发起访问请求，如果端口访问正常则视为后端ECS运行正常，否则视为后端ECS运行异常。</li>
			<li>针对可能引起健康检查异常的排查思路<a
				href="#/pub/slb/best-practice/health-check-debug">点击这里</a>查看。
			</li>
		</ul>
		<p>
			<strong>关于健康检查的参数配置，提供如下参考建议：</strong>
		</p>
		<pre>
			<code>响应超时时间：5秒
健康检查间隔：2秒
不健康阈值：3
健康阈值：3

在此配置下有利于用户服务及应用状态的尽快收敛：
ECS健康检查失败响应时间：2×3+5=11秒
如果用户有更高要求，可以适当地降低响应超时时间值，但必须先保证自己服务在正常状态下的处理时间小于这个值。
ECS健康检查成功响应时间：2×3=6秒
</code>
		</pre>
		<h3 id="menu12">带宽峰值</h3>
		<p>用户可以针对监听设定不同的带宽峰值来限定后端ECS上的不同应用所能对外提供的服务能力。</p>
		<p>带宽峰值的设定规则：</p>
		<ul>
			<li>一个SLB实例最多对应10个监听，每个监听可独立设定限定规则；</li>
			<li>单个监听可限定5-1000Mbps范围的带宽峰值；</li>
			<li>当单个监听上限无法满足用户业务需求时，可以选择不限带宽峰值。</li>
		</ul>
		<h2 id="menu13">SLB后端ECS配置</h2>
		<p>对于添加到SLB实例后端的ECS，原则上不需要进行特别的配置。如果针对关联到SLB
			4层（TCP协议）服务的Linux系统的ECS，如果发现无法正常访问，需要确保系统配置文件/etc/sysctl.conf的以下三项为0：</p>
		<pre>
			<code>net.ipv4.conf.default.rp_filter = 0
net.ipv4.conf.all.rp_filter = 0
net.ipv4.conf.eth0.rp_filter = 0
</code>
		</pre>
		<p>如果部署在同一内网网段下的ECS之间有通信需求，且发现有无法通信的情况存在，那么需要检查如下参数的配置是否正确：</p>
		<pre>
			<code>net.ipv4.conf.default.arp_announce =2
net.ipv4.conf.all.arp_announce =2
</code>
		</pre>
		<p>并使用sysctl –p更新配置。</p>
		<h3 id="menu14">后端ECS权重</h3>
		<p>用户可以指定后端服务器池内各ECS的转发权重，权重越高的ECS将被分配到更多的访问请求，用户可以根据后端ECS的对外服务能力和情况来区别设定。</p>
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