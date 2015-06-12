<%@ page language="java" pageEncoding="UTF-8"%>
<!-- <div class="content-right" data-spm="2"> -->
<div class="bread-crumb">
	<span class="item"><a href="#">云数据库RDS</a></span>&nbsp;&gt;&nbsp;<span
		class="data-spm-click" data-id="2-1-1">RDS产品介绍</span>
</div>
<h2 style="text-align: center; color: #333; font-size: 18px;">RDS产品介绍</h2>
<div class="info-wrapper">
	<div class="introduce" data-spm="4" data-spm-max-idx="3">
		<h1 class="">
			<i class="icons-64 icons-ace icons-64-ace "></i>云数据库RDS
		</h1>
		<div class="intro">云数据库是构建在SSD盘上，完全兼容MySQL，SQLServer，PostgreSQL协议的关系型数据库服务（Relational Database Service，简称RDS）。采取多主架构，具有多重安全防护措施和完善的性能监控体系，并提供专业的数据库备份、恢复及优化方案，使您能专注于应用开发和业务发展。</div>
	</div>
	<div class="approve" data-spm="5">
		<a class="" id="Approve" name="Approve" hidefocus="">&nbsp;</a>
		<div class="product-title">
			<h1>我们的优势</h1>
		</div>
		<div class="approve-detail y-clear">
            <div class="dispaly-4div">
                <h3 class="title">多主架构</h3>
                <img class="img" src="${ctx}/static/img/help/rdsintro1.jpg" width="190" height="60">
                <ul class="list">
                    <li>每台RDS拥有三个物理节点多主架构</li>
                    <li>主节点1发生故障，秒级切换至其他主节点</li>
                    <li>服务可用性高达99.95%</li>
                </ul>
            </div>
            <div class="dispaly-4div">
                <h3 class="title">安全防护</h3>
                <img class="img" src="${ctx}/static/img/help/rdsintro2.jpg" width="190" height="60">
                <ul class="list">
                    <li>自定义访问IP白名单</li>
                    <li>防DDoS攻击，SQL注入告警</li>
                    <li>多重备份，数据可靠性高达99.9999%</li>
                </ul>
            </div>
            <div class="dispaly-4div">
                <h3 class="title">简单易用</h3>
                <img class="img" src="${ctx}/static/img/help/rdsintro3.jpg" width="190" height="60">
                <ul class="list">
                    <li>完全兼容MySQL，SQL Server协议</li>
                    <li>一键式数据迁移</li>
                    <li>可视化管理面板操作</li>
                </ul>
            </div>
    	</div>
	</div>
	<div class="function" data-spm="6">
		<div class="product-title">
			<h1>产品功能</h1>
		</div>
		<h1 class="head-title">RDS为您快速搭建专业数据库服务</h1>
		<h2 class="sub-head-title">我的高可用、高安全、高易用，让你不舍！</h2>
		<div class="approve-detail y-clear">
			<div class="dispaly-4div">
				<h3 class="">专业的数据库管理平台</h3>
				<p class="">iDB Cloud是专为RDS定制的数据库管理平台，使用户通过浏览器即可安全、方便的进行数据库管理和维护。</p>
			</div>
			<div class="dispaly-4div">
				<h3 class="">轻松实现数据回溯</h3>
				<p class="">
					用户可随时进行数据备份，RDS能够根据备份文件将数据库恢复至7日内任意时刻</p>
			</div>
			<div class="dispaly-4div">
				<h3 class="">专业的数据库优化建议</h3>
				<p class="">RDS提供直观的慢SQL分析报告和完整的SQL运行报告，并提供如主键检查、索引检查等多种优化建议。</p>
			</div>
			<div class="dispaly-4div">
				<h3>完善的监控体系</h3>
				<p>RDS展示近20种性能资源监控视图，可对部分资源项设置阈值报警，并提供WEB操作、SQL审计等多种日志。</p>
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
					<li><a class="grey-font" href="${ctx}/helpCenter/helpCenter.jsp?container=help-rdsquikcrt" target="_blank" >RDS快速创建指南</a></li>
					<li><a class="grey-font" href="${ctx}/helpCenter/helpCenter.jsp?container=help-createDb" target="_blank" >常见问题</a></li>
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