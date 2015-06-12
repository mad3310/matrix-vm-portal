<%@ page language="java" pageEncoding="UTF-8"%>
<!-- <div class="content-right" data-spm="2"> -->
    <div class="bread-crumb">
        <span class="item"><a href="#">云数据库RDS</a></span>&nbsp;&gt;&nbsp;<span class="data-spm-click" data-id="2-1-2">RDS基础架构</span>
    </div>
    <h2 style="text-align: center;color: #333;font-size: 18px;">RDS基础架构</h2>
	<div class="article-content">
		<div class="markdown-body ng-scope" ui-view="doc"
			style="margin: 0 10px; margin-bottom: 25px;">
			<div id="doc" doc-preloader="isload"
				style="padding-top: 10px; min-height: 400px; font-size: 14px; line-height: 26px;"
				class="ng-scope doc-preloader-hide">
				<div class="intro">云数据库（Relational Database Service，即关系型数据库服务，简称RDS）是乐视云提供的一种稳定可靠、可弹性伸缩的在线数据库服务。RDS采用即开即用方式，并提供了数据库在线扩容、备份回滚、性能监控及分析等功能。</div>
				<h2>RDS基础架构</h2>
				<p><img src="${ctx}/static/img/help/rds-arch1.png" alt=""></p>
				<div class="intro">RDS的目标是将耗时费力的数据库管理任务承担下来，使用户能够专心于应用开发和业务发展。用户可根据业务需求对RDS进行弹性伸缩，RDS承诺99.95%的服务可用性和99.9999%的数据可靠性。</div>
				<div class="intro">RDS目前支持MySQL、SQL Server和PostgreSQL等三种关系型数据库的访问协议。</div>
				<h2>功能特点</h2>
				<ul>
					<li>防DDoS攻击：当RDS为公网访问时，云平台安全体系会自动判断RDS是否正在遭受DDoS攻击，并启动流量清洗的功能，若攻击达到黑洞阈值或清洗失效，将会进行黑洞处理。</li>
					<li>SQL注入告警：RDS会通过解析SQL语句，判断是否遭受SQL注入攻击，并提示修改应用程序。</li>
					<li>IP访问白名单：白名单可以使RDS实例得到最高级的访问安全保护；建议设置访问源IP地址或者IP段，最多设置1000个。</li>
					<li>将数据迁移至RDS：RDS for MySQL提供在线迁移数据的方式，可以不停止原有数据库运行的情况下完成数据迁移操作；RDS for SQL Server提供上传备份文件迁移至RDS的迁移方式，可便捷的完成数据的导入操作。</li>
					<li>实例在线升级：RDS提供的在线升级服务，包括实例配置的升级和数据库版本的升级，升级过程无需用户介入。</li>
					<li>系统性能监控：RDS提供近20个系统性能的监控视图，如磁盘容量、IOPS、连接数、CPU利用率、网络流量等，用户可以轻松查看实例的负载。</li>
					<li>优化建议：RDS提供多种优化建议，如存储引擎检查、主键检查、大表检查、索引偏多、缺失索引等，用户可以根据优化建议并结合自身的应用来对数据库进行优化。</li>
					<li>备份管理：RDS自动提供多重备份，同时RDS支持用户通过RDS管理控制台或OPEN API灵活变更备份的时间。 数据回溯：RDS通过备份和日志，用户可以选择7天内的任意时间点创建一个临时实例，临时实例生成后验证数据无误，即可将数据迁移到RDS实例，从而完成数据回溯操作。</li>
				</ul>
				
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