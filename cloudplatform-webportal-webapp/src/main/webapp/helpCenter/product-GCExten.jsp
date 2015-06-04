<%@ page language="java" pageEncoding="UTF-8"%>
<!-- <div class="content-right" data-spm="2"> -->
    <div class="bread-crumb">
        <span class="item"><a href="#">通用云引擎GCE</a></span>&nbsp;&gt;&nbsp;<span class="data-spm-click" data-id="5-1-4">GCE扩展服务</span>
    </div>
    <h2 style="text-align: center;color: #333;font-size: 18px;">GCE扩展服务</h2>
<div class="article-content">
	<div class="markdown-body">
		<div id="doc">
			<h1 id="menu0">扩展服务简介</h1>
			<h2 id="menu1">分布式Session</h2>
			<p>
				基于乐视云开放缓存服务（
				<code>OCS</code>
				）的分布式
				<code>Session</code>
				空间，用以解决集群模式下多个实例的
				<code>Session</code>
				共享问题。开发者使用分布式
				<code>Session</code>
				无需在代码中特别做适配，
				<code>GCE</code>
				在运行环境的容器层做了自动转换。
			</p>
			<h2 id="menu2">数据库（MySQL）</h2>
			<p>
				基于乐视云的关系型数据库服务（
				<code>RDS</code>
				），实例为共享型
				<code>MySQL 5.1</code>
				，限制创建一个数据库，支持最大连接数
				<code>10</code>
				个，存储空间
				<code>1GB</code>
				。
			</p>
			<h2 id="menu3">缓存（Cache）</h2>
			<p>
				基于乐视云开放缓存服务（
				<code>OCS</code>
				），提供分布式缓存功能，用于高效地读写小型数据。是保障集群模式下多个实例之间临时数据共享型和一致性的方案之一。支持
				<code>Memcached</code>
				协议。
			</p>
			<p>
				开发者需要可以在
				<code>GCE</code>
				控制台创建缓存空间，然后在代码中使用
				<code>SDK</code>
				提供的接口进行数据的读写。
			</p>
			<p>
				<code>GCE</code>
				控制台创建的缓存服务不支持管控功能，若您需要完整的缓存服务管控功能，可在登录控制台后，<a
					href="http://matrix.lecloud.com">开通<code>OCS</code></a>，在
				<code>GCE</code>
				中配置使用，详细对应语言的
				<code>SDK</code>
				使用文档。
			</p>
			<h2 id="menu4">存储（Storage）</h2>
			<p>
				基于乐视云的开放存储服务（
				<code>OSS</code>
				），提供分布式文件存储功能，用于持久化存储较大型的文件，提供
				<code>Restful</code>
				风格的访问接口。其存储具有大容量、高可靠、支持高并发的特性。
			</p>
			<p>
				开发者需要先在
				<code>GCE</code>
				控制台创建存储空间，然后在代码中使用
				<code>SDK</code>
				提供的接口进行读、写、删除、拷贝等操作。
			</p>
			<p>
				<code>GCE</code>
				控制台仅提供5个存储空间且不支持管控功能，若您需要完整的存储服务管控功能，可在登录控制台后，<a
					href="http://matrix.lecloud.com">开通<code>OCS</code></a>，在
				<code>GCE</code>
				中配置使用，详细对应语言的
				<code>SDK</code>
				使用文档。
			</p>
			<h2 id="menu5">定时任务（Cron）</h2>
			<p>
				<code>GCE</code>
				提供的扩展服务之一，提供分布式定时触发请求的功能，开发者先定义好触发的URL和时间规则，系统会在符合规则的时间点自动请求指定的
				<code>URL</code>
				。 目前仅支持
				<code>HTTP</code>
				协议的
				<code>GET</code>
				方式的请求，仅支持通过控制台定义定时任务。SLB与GCE的关系，如下图所示：
			</p>
			<br>
			<p><img src="${ctx}/static/img/help/product/7.jpg" alt=""></p>
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