<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<script type="text/javascript">
	try {
		ace.settings.check('navbar', 'fixed')
	} catch (e) {}
</script>

<div id="navbar" class="navbar navbar-default">
	<div class="navbar-container" id="navbar-container">
		<div class="navbar-header pull-left">
			<a href="#" class="navbar-brand logo"> 
				<img src="${ctx}/static/image/site.logo.png" alt="paas云管理平台" style="max-width:150px;margin-top:-5px;"/> 
				<b><small>PAAS portal</small></b> 
				<span><small style="font-size: 25%;">beta</small></span>
			</a>
		</div>

		<!-- #section:basics/navbar.dropdown -->
		<div class="navbar-buttons navbar-header pull-right" role="navigation">
			<ul class="nav ace-nav">
				<li class="light-blue">
					<a data-toggle="dropdown" href="#" class="dropdown-toggle">
						<img class="nav-user-photo"
					 		src="${ctx}/static/ace/avatars/user.jpg" alt="Jason's Photo" />
						<span class="user-info"> <small>欢迎会员,</small>${sessionScope.userSession.userName}</span>
						<i class="ace-icon fa fa-caret-down"></i>
					</a>
					<ul class="user-menu dropdown-menu-right dropdown-menu dropdown-blue dropdown-caret dropdown-close">
						<li>
							<a href="${ctx}/account/logout"> 
								<i class="ace-icon fa fa-power-off"></i><span>退出登录</span>
							</a>
						</li>
					</ul>
				</li>
				<!-- <li>
					 &nbsp;&nbsp;<button class="btn btn-dark btn-minier" id="skin-custom" onclick="changeSkin('skin-custom')">
								 <i class="ace-icon fa fa-check  bigger-90 icon-only"></i></button>
					 &nbsp;<button class="btn btn-darkGreen btn-minier" id="skin-darkGreen" onclick="changeSkin('skin-default')">
								 <i class="ace-icon fa fa-check  bigger-90 icon-only"></i></button>
				</li> -->
			</ul>
		</div>	
	</div>
</div>
