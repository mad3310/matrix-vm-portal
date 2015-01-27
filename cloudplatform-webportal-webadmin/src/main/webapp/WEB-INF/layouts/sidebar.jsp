<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript">
		try {
			ace.settings.check('sidebar', 'fixed')
		} catch (e) {
		}
</script>

<div id="sidebar" class="sidebar responsive">
	<ul id="sidebar-list" class="nav nav-list">
		<li id="sidebar-dashboard" >
			<a href="${ctx}/dashboard">
				<i class="menu-icon fa fa-tachometer"></i> 
				<span class="menu-text">Dashboard</span> 
			</a>
		</li>
		<li id="sidebar-cluster-mgr" >
			<a href="#" class="dropdown-toggle">
				<i class="menu-icon fa fa-cubes"></i> 
				<span class="menu-text"> 集群管理 </span> 
				<b class="arrow fa fa-angle-down"></b>
			</a>
			<b class="arrow"></b>
			<ul class="submenu">
				<li id="sidebar-mcluster-list" >
					<a href="${ctx}/list/mcluster"> 
						<i class="menu-icon fa fa-caret-right"></i>
						Container集群列表
					</a>
					<b class="arrow"></b>
				</li>
				<li id="sidebar-hcluster-list" >
					<a href="${ctx}/list/hcluster"> 
						<i class="menu-icon fa fa-caret-right"></i>
						物理机集群列表
					</a>
					<b class="arrow"></b>
				</li>
			</ul>
		</li>
		<li id="sidebar-db-mgr" >
			<a href="#" class="dropdown-toggle">
				<i class="menu-icon fa fa-database"></i> 
				<span class="menu-text"> 数据库管理 </span> 
				<b class="arrow fa fa-angle-down"></b>
			</a>
			<b class="arrow"></b>
			<ul class="submenu">
				<li id="sidebar-db-list" >
					<a href="${ctx}/list/db"> 
						<i class="menu-icon fa fa-caret-right"></i>
						数据库列表
					</a>
					<b class="arrow"></b>
				</li>
				<li id="sidebar-db-list" >
					<a href="${ctx}/list/dbUser"> 
						<i class="menu-icon fa fa-caret-right"></i>
						数据库用户列表
					</a>
					<b class="arrow"></b>
				</li>
			</ul>
		</li>
		<li id="sidebar-forewarning-monitor-mgr" >
			<a href="#" class="dropdown-toggle">
				<i class="menu-icon fa fa-list"></i> 
				<span class="menu-text"> 预警管理 </span> 
				<b class="arrow fa fa-angle-down"></b>
			</a>
			<b class="arrow"></b>
			<ul class="submenu">
				<li id="sidebar-monitor-list" >
					<a href="${ctx}/list/mcluster/monitor/1"> 
						<i class="menu-icon fa fa-caret-right"></i>
						cluster监控列表
					</a>
					<b class="arrow"></b>
				</li>
				<li id="sidebar-monitor-list" >
					<a href="${ctx}/list/mcluster/monitor/2"> 
						<i class="menu-icon fa fa-caret-right"></i>
						node监控列表
					</a>
					<b class="arrow"></b>
				</li>
				<li id="sidebar-monitor-list" >
					<a href="${ctx}/list/mcluster/monitor/3"> 
						<i class="menu-icon fa fa-caret-right"></i>
						db监控列表
					</a>
					<b class="arrow"></b>
				</li>
			</ul>
		</li>
		<li id="sidebar-monitor-mgr" >
			<a href="#" class="dropdown-toggle">
				<i class="menu-icon fa fa-bar-chart-o"></i> 
				<span class="menu-text"> 监控管理 </span> 
				<b class="arrow fa fa-angle-down"></b>
			</a>
			<b class="arrow"></b>
			<ul class="submenu">
				<li id="sidebar-monitor-view" >
					<a href="${ctx}/view/mcluster/monitor"> 
						<i class="menu-icon fa fa-caret-right"></i>
						container集群监控视图
					</a>
					<b class="arrow"></b>
				</li>
			</ul>
		</li>
		<li id="backupRecover" >
			<a href="${ctx}/list/backup">
				<i class="menu-icon fa  fa-university"></i> 
				<span class="menu-text">备份恢复</span> 
			</a>
		</li>
	</ul>

	<!-- #section:basics/sidebar.layout.minimize -->
	<div class="sidebar-toggle sidebar-collapse" id="sidebar-collapse">
		<i class="ace-icon fa fa-angle-double-left"
			data-icon1="ace-icon fa fa-angle-double-left"
			data-icon2="ace-icon fa fa-angle-double-right"></i>
	</div>
	
	<!-- /section:basics/sidebar.layout.minimize -->
	<script type="text/javascript">
		try {
			ace.settings.check('sidebar', 'collapsed')
		} catch (e) {
		}
	</script>
</div>
