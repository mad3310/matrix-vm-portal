<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="zh">

<head>
	<meta charset="utf-8"/>
	<meta http-equiv="X-UA-compatible" content="IE=edge,chrome=1"/>
	<meta name="viewpoint" content="width=device-width,initial-scale=1"/>
	<!-- bootstrap css -->
	<link type="text/css" rel="stylesheet" href="${ctx}/static/css/bootstrap.min.css"/>
	<!-- ui-css -->
	<link type="text/css" rel="stylesheet" href="${ctx}/static/css/ui-css/common.css"/>
	<!-- bootstrapValidator-->
	<link type="text/css" rel="stylesheet" href="${ctx}/static/css/bootstrapValidator.css"/>

	<title>account</title>
</head>

<body>
	<!-- 全局参数 start -->
	<input class="hidden" value="${dbId}" name="dbId" id="dbId" type="text" />
	<!-- 全局参数 end -->
	<!-- top bar begin -->
	<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
      <div class="container-fluid">
        <div class="navbar-header">
          <a class="navbar-brand" href="${ctx}/dashboard"><img src="${ctx}/static/img/cloud.ico"/></a>
        </div>
        <div class="navbar-header">
          <a class="navbar-brand active" href="${ctx}/dashboard"><span class="glyphicon glyphicon-home"></span></a>
        </div>
        <div id="navbar" class="navbar-collapse collapse pull-right">
        	<form class="navbar-form navbar-right pull-left" role="form">
	            <div class="form-group">
	              <input type="text" placeholder="Search" class="form-control">
	            </div>
	            <button type="submit" class="btn btn-success"><span class="glyphicon glyphicon-search"></span></button>
	        </form>
            <ul class="nav navbar-nav">
	            <li><a href="#"><span class="glyphicon glyphicon-bell"></span></a></li>
	            <li class="dropdown">
	              <a href="#" class="dropdown-toggle" data-toggle="dropdown">${sessionScope.userSession.userName}<span class="caret"></span></a>
	              <ul class="dropdown-menu" role="menu">
	                <li><a href="#">用户中心</a></li>
	                <li><a href="#">我的订单</a></li>
	                <li><a href="#">账户管理</a></li>
	                <li class="divider"></li>
	                <li class="dropdown-header"><a href="${ctx}/account/logout">退出</a></li>
	              </ul>
	            </li>
	            <li><a href="#"><span class="glyphicon glyphicon-lock"></span></a></li>
	            <li><a href="#"><span class="glyphicon glyphicon-pencil"></span></a></li>
          </ul>
        </div><!--/.nav-collapse -->
      </div>
    </nav>
<!-- top bar end -->

<!-- navbar begin -->
<div class="navbar navbar-default mt50"> 
  <div class="container-fluid">
    <div class="navbar-header">
      <a class="navbar-brand" href="${ctx}/dashboard">Le云控制台首页</a>
    </div>
    <div id="navbar" class="navbar-collapse collapse pull-right">
      <ul class="nav navbar-nav hide">
        <li class="active"><a href="#"><span class="glyphicon glyphicon-phone"></span> 扫描二维码</a></li>
      </ul>
    </div>
  </div>
</div>
	<!-- navbar end -->
	<!-- main-content begin-->
	<div class="container-fluid">
		<div class="row main-header">
			<!-- main-content-header begin -->
			<div class="col-sm-12 col-md-6">
				<div class="pull-left">
					<h3>
						<span class="text-success glyphicon glyphicon-phone"></span>
						<span id="serviceName" data-toggle="tooltip" data-placement="bottom" title="rdsenn6ryenn6r">rdsenn6ryenn6r...</span>
						<span style="display: inline-block;vertical-align:super;">
							<small class="text-success text-xs">(运行中...)</small>
						</span>
						<a class="btn btn-default btn-xs" href="${ctx}/list/db">
							<span class="glyphicon glyphicon-step-backward"></span>
							返回实例列表
						</a>
					</h3>
				</div>
			</div>
			<div class="col-sm-12 col-md-6">
				<div class="pull-right">
					<h3>
						<small>
							<span>
								<a>功能指南</a>
								<button class="btn btn-default btn-xs">
									<span class="glyphicon glyphicon-eject" id="rds-icon-guide"></span>
								</button>
							</span>
						</small>
						<small>
							<span>
								<button class="btn-warning btn btn-sm" data-toggle="modal" data-target="#myModalNetchange">内外网切换</button>
							</span>
						</small>
						<small>
							<span>
								<button class="btn-danger btn btn-sm" data-toggle="modal" data-target="#myModalCaseRestart">重启实例</button>
							</span>
						</small>
						<small>
							<span>
								<button class="btn-default btn btn-sm" data-toggle="modal" data-target="#myModalBackCase">备份实例</button>
							</span>
						</small>
						<small>
							<span>
								<button class="btn-default btn btn-sm glyphicon glyphicon-list"></button>
							</span>
						</small>
					</h3>
				</div>
			</div>
		</div>
		<!-- main-content-header end-->
		<div class="row">
			<!-- main-content-center-begin -->
			<nav class="col-sm-2 col-md-2">
				<div class="sidebar sidebar-line sidebar-selector">
					<ul class="nav nav-sidebar li-underline">
			            <li><a class="text-sm" href="${ctx}/detail/baseInfo/${dbId}">基本信息</a></li>
			            <li><a  class="text-sm" href="${ctx}/detail/account/${dbId}">账号管理</a></li>
			            <li><a  class="text-sm" href="#">
								<span class="glyphicon glyphicon glyphicon-chevron-right"></span>
								系统资源监控
							</a>
							<ul class="nav hide">
								<li><a  class="text-sm" href="${ctx}/detail/monitor/${dbId}">磁盘空间</a></li>
								<li><a  class="text-sm" href="#">IOPS</a></li>
								<li><a  class="text-sm" href="#">连接数</a></li>
								<li><a  class="text-sm" href="#">CPU利用率</a></li>
								<li><a  class="text-sm" href="#">网络流量</a></li>
								<li><a  class="text-sm" href="#">QPS/TPS</a></li>
								<li><a  class="text-sm" href="#">InnoDB缓冲池</a></li>
								<li><a  class="text-sm" href="#">InnoDB读写量</a></li>
								<li><a  class="text-sm" href="#">InnoDB读写次数</a></li>
								<li><a  class="text-sm" href="#">InnoDB日志</a></li>
								<li><a  class="text-sm" href="#">临时表</a></li>
								<li><a  class="text-sm" href="#">MyISAM key Buffer</a></li>
								<li><a  class="text-sm" href="#">MyISAM读写次数</a></li>
								<li><a  class="text-sm" href="#">COMDML</a></li>
								<li><a  class="text-sm" href="#">ROWDML</a></li>
							</ul>
						</li>
			            <li><a  class="text-sm" href="#">备份与恢复</a></li>
			            <li><a  class="text-sm" href="#">参数设置</a></li>
			            <li><a class="text-sm" href="#">日志管理</a></li>
			            <li><a  class="text-sm" href="#">性能优化</a></li>
			            <li><a class="text-sm" href="#">阈值报警</a></li>
			            <li><a class="text-sm" href="${ctx}/detail/security/${dbId}">安全控制</a></li>
			        </ul>
				</div>
			</nav>
			<div class="col-sm-10 col-md-10">
				<!-- 账号管理主界面div -->
				<div id="accountList" class="" role="tablist" aria-multiselectable="true">
					<div class="se-heading" id="headingOne">
						<div class="pull-left">
							<h4>账号管理</h4>
						</div>
						<div class="pull-right">
							<button id="refresh" class="btn btn-default">
								<span class="glyphicon glyphicon-refresh"></span>
								刷新
							</button>
							<button id="createAccount" class="btn btn-primary" onclick="createAccount()">创建帐号</button>
						</div>
					</div>
					<div class="table-responsive">
						<table class="table table-hover table-se">
							<thead>
								<tr>
									<th width="25%">帐号</th>
									<th width="15%">状态</th>
									<th width="15%">读写比例</th>
									<th>频次限制</th>
									<th class="text-right" width="30%">
										<span style="padding-left:8px">操作</span>
									</th>
								</tr>
							</thead>
							<tbody id="tby">
							</tbody>
						</table>
					</div>
				</div>
				<!-- 点击“创建账号”后加载的div 去掉mc-hide既可以显示此div-->
				<div id="newAccountTab" class="mc-hide" role="tablist" aria-multiselectable="true">
					<!-- heading部分 -->
					<div class="se-heading" id="headingOne">
						<div class="pull-left">
							<h4>创建新账号</h4>
						</div>
						<a href="#accountList" class="pull-left">返回帐号管理</a>
					</div>
					<!-- 内容部分，由一个form承载 -->
					<div style="width:auto;height:auto;">
						<form role="form" class="form-horizontal" name="account_modify_form">
							<!-- 数据库账号模块 -->
							<div class="form-group">
								<label class="col-sm-2 control-label">
									<span class="text-danger">*</span>
									数据库帐号：
								</label>
								<div class="col-sm-8 row">
									<!-- ngIf: !newAccount -->
									<div class="col-sm-4">
										<!-- ngIf: newAccount && !isJst -->
										<input required="required" name="accountNumber" class="form-control" type="text"></div>
									<div class="col-sm-12 notice-block">
										<p class="text-correct">由小写字母，数字、下划线组成、字母开头，字母或数字结尾，最长16个字符</p>
									</div>
								</div>
							</div>
							<!-- 数据库账号模块end -->
							<!-- 授权数据库模块 -->
							<div class="form-group multi-select">
								<label class="col-sm-2 control-label" style="height:190px;margin-right:15px">授权IP：</label>
								<div class="inline-block mcluster-select" style="width:180px">
									<div class="select-head clearfix">
										<p class="pull-left">未授权IP</p>
									</div>
									<div class="select">
										<ul class="select-list select-list-left">
										</ul>
										<!-- 没有数据记录时显示暂无数据 -->
										<div class="select-msg mc-hide">
											<span class="text-muted">暂无数据</span>
										</div>
									</div>
								</div>
								<div class="inline-block" style="width: 60px;text-align:center;height:183px;padding-top:60px">
									<div style="margin-bottom:5px">
										<a class="btn_db_add">授权&nbsp;&gt;</a>
									</div>
									<div>
										<a class="btn_db_remove">&lt;&nbsp;移除</a>
									</div>
								</div>
								<div class="inline-block mcluster-select" style="width:380px;height:100%">
									<div class="select-head clearfix">
										<p class="pull-left">已授权IP</p>
										<p class="pull-right">
											<span style="padding-right: 5px;color:#bbb">权限</span>
											<a class="select-all-rw">全部设读写</a>
										</p>
									</div>
									<div class="select">
										<div class="select-wrap">
											<ul class="select-list select-list-right">
											</ul>
											<!--无数据时显示暂无数据 -->
											<div class="select-msg mc-hide">
												<span class="text-muted">暂无数据</span>
											</div>
										</div>
									</div>
								</div>
							</div>
							<!-- 授权数据库模块end -->
							<!-- 密码输入模块 -->
							<div class="form-group">
								<label class="col-sm-2 control-label">
									<span class="text-danger">*</span>
									读写比例：
								</label>
								<div class="col-sm-8 row">
									<!-- 密码输入框 -->
									<div class="col-sm-4">
										<input required="required" name="newPwd" class="form-control" type="password">
									</div>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">
									<span class="text-danger">*</span>
									最大并发量：
								</label>
								<div class="col-sm-8 row">
									<!-- 密码输入框 -->
									<div class="col-sm-4">
										<input required="required" name="newPwd" class="form-control" type="password">
									</div>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">
									<span class="text-danger">*</span>
									密码：
								</label>
								<div class="col-sm-8 row">
									<!-- 密码输入框 -->
									<div class="col-sm-4">
										<input required="required" name="newPwd" class="form-control" type="password">
									</div>
									<!-- 密码规则提示 -->
									<div class="notice-block col-sm-12">
										<p class="">由字母、数字、中划线或下划线组成，长度6~32位</p>
									</div>
								</div>
							</div>
							<!-- 密码输入模块end -->
							<!-- 确认密码模块 -->
							<div class="form-group">
								<label class="col-sm-2 control-label">
									<span class="text-danger">*</span>
									确认密码：
								</label>
								<div class="col-sm-8 row">
								<!-- 密码输入框 -->
									<div class="col-sm-4">
										<input required="required" name="newPwd" class="form-control" type="password">
									</div>
								</div>
							</div>
							<!-- 按钮模块 -->
							<div class="form-group">
								<label class="col-sm-2 control-label"></label>
								<div class="col-sm-4">
									<button disabled="disabled" type="button" class="btn btn-success btn-disable" onclick="newAccountSubmit()">提交</button>
									<button type="button" class="btn btn-default" ui-sref="rdsDetail.account.list()" onclick="newAccountCancel()">返回</button>
								</div>
							</div>
							<!-- 按钮模块end -->
						</form>
					</div>
				</div>
				<!-- main-content-center-end -->
				<script>
				    function createAccount(){				    
				    	$("#accountList").addClass("mc-hide");
				        $("#newAccountTab").removeClass("mc-hide");
				    };
				    function newAccountSubmit(){
				    	$("#accountList").addClass("mc-hide");
				        $("#newAccountTab").removeClass("mc-hide");
				    };
				    function newAccountCancel(){
				    	$("#accountList").removeClass("mc-hide");
				        $("#newAccountTab").addClass("mc-hide");
				    };
				</script>
			</div>
		</div>
	</div>
</body>
<!-- js -->
<script type="text/javascript" src="${ctx}/static/modules/seajs/2.3.0/sea.js"></script>
<script type="text/javascript">

// Set configuration
seajs.config({
	base: "${ctx}/static/modules/",
	alias: {
		"jquery": "jquery/2.0.3/jquery.min.js",
		"bootstrap": "bootstrap/bootstrap/3.3.0/bootstrap.js",
		"bootstrapValidator": "bootstrap/bootstrapValidator/0.5.3/bootstrapValidator.js"
	}
});

seajs.use("${ctx}/static/page-js/accountManager/main");
</script>

</html>