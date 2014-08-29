<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<div id="header" class="row clearfix">
	<div class="col-md-12 column">
		<nav class="navbar navbar-default navbar-fixed-top navbar-inverse"
			role="navigation">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
					<span class="sr-only">导航栏</span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="#">PASS云</a>
			</div>

			<div class="collapse navbar-collapse" id="menu">
				<ul class="nav navbar-nav" id="headNavList" name="headNavList">
					<li id="mclusterMgr">
						<a href="${ctx}/mcluster/mgrList">Mcluster管理</a>
					</li>
					<li id="dbMgr">
						<a href="${ctx}/db/mgrList">DB管理</a>
					</li>
				</ul>
				<ul id="usercenter" class="nav navbar-nav navbar-right">
					<li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown">${sessionScope.loginName}<strong class="caret"></strong></a>
						<ul class="dropdown-menu">
							<li><a href="#">用户中心</a></li>
							<li class="divider"></li>
							<li><a href="#">退出登录</a></li>
						</ul>
					</li>
				</ul>
			</div>
		</nav>
	</div>
</div> 
