<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!-- 这里的header不能直接调用，必须结合layoutit生成的bootstrap使用。原因是界面本身就只有一个容器，而header就在这个容器中 -->
<div class="row clearfix">
	<div class="col-md-12 column">
		<nav class="navbar navbar-default navbar-fixed-top navbar-inverse"
			role="navigation">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target="#bs-example-navbar-collapse-1">
					<span class="sr-only">导航栏</span><span
						class="icon-bar"></span><span class="icon-bar"></span><span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="#">PASS云</a>
			</div>

			<div class="collapse navbar-collapse" id="menu">
				<ul class="nav navbar-nav">
					<li id="sqlcluster"><a href="#">Mcluster</a></li>
				</ul>
				<form class="navbar-form navbar-left" role="search">
					<div class="form-group">
						<input type="text" class="form-control" />
					</div>
					<button type="submit" class="btn btn-default">搜索</button>
				</form>

				<form id="signin" class="navbar-form form-inline navbar-right"  style="display:none;">
					<input type="text" class="form-control input-small"
						placeholder="Email"> <input type="password"
						class="form-control input-small" placeholder="Password"> <label
						class="checkbox"> <input type="checkbox"> <font
						color="#FFFFFF">记住我</font>
					</label>
					<button id="loginbutton" type="submit" class="btn btn-default">登录</button>
					<button id="headeregisterbutton" class="btn btn-default" data-toggle="button">注册</button>
				</form>

				<ul id="usercenter" class="nav navbar-nav navbar-right"
					style="display: none;">
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown">admin<strong class="caret"></strong></a>
						<ul class="dropdown-menu">
							<li><a href="#">用户中心</a></li>
							<li class="divider"></li>
							<li><a href="#">退出登录</a></li>
						</ul></li>
				</ul>
			</div>
		</nav>
	</div>
</div>