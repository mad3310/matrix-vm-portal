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
	<!-- fontawesome css -->
	<link type="text/css" rel="stylesheet" href="${ctx}/static/css/font-awesome.min.css"/>
	<!-- ui-css -->
	<link type="text/css" rel="stylesheet" href="${ctx}/static/css/ui-css/common.css"/>
	<title>关系型数据库RDS</title>
</head>

<body>
	<!-- 全局参数 start -->
	<input class="hidden" value="${dbId}" name="dbId" id="dbId" type="text" />
	<!-- 全局参数 end -->
	<!-- top bar begin -->
	<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation" style="min-height:40px;height:40px;">
      <div class="container-fluid">
        <div class="navbar-header">
	<a class="navbar-brand color" href="${ctx}/dashboard" style="padding-top:2px;height:40px !important;"><img src="${ctx}
    /static/img/logo.png"/></a>
     <a class="navbar-brand color top-bar-btn" href="${ctx}/dashboard" style="white-space:nowrap; font-size:13px"><i class="fa fa-home text-20"></i></a>
	<a class="navbar-brand color" href="${ctx}/list/db" style="margin-left:0px;height:40px !important; font-size:13px"><i class="fa fa-database text-10"></i>&nbsp;关系型数据库RDS</a>
    </div>
    <div id="navbar" class="navbar-collapse collapse pull-right">
        <ul class="nav navbar-nav">
            <li><a href="javascript:void(0)" class="hlight"><span class="glyphicon glyphicon-bell"></span></a></li>
            <li class="dropdown">
              <a href="javascript:void(0)" class="dropdown-toggle hlight" data-toggle="dropdown">${sessionScope.userSession.userName}<span class="caret"></span></a>
              <ul class="dropdown-menu" role="menu">
                <li><a href="javascript:void(0)">用户中心</a></li>
                <li><a href="javascript:void(0)">我的订单</a></li>
                <li><a href="javascript:void(0)">账户管理</a></li>
                <li class="divider"></li>
                <li><a href="${ctx}/account/logout">退出</a></li>
              </ul>
            </li>
            <li><a href="javascript:void(0)" class="hlight"><span class="glyphicon glyphicon-lock"></span></a></li>
            <li><a href="javascript:void(0)" class="hlight"><span class="glyphicon glyphicon-pencil"></span></a></li>
      </ul>
    </div><!--/.nav-collapse -->
  </div>
</nav>
<!-- top bar end -->

<!-- navbar begin -->
<div class="navbar navbar-default mt40" style="margin-bottom: 0px !important;">
  <div class="container-fluid">
    <div class="navbar-header">
      <a class="navbar-brand" href="javascript:void(0)">关系型数据库<font color="#FF9C17">RDS</font></a>
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
                    <span class="fa  fa-cubes"></span>
                    <span id="dbName"></span>
                    <span style="display: inline-block;vertical-align:super;">
                        <small id="dbStatus" class="text-success text-xs"></small>
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
                        <span class="pd-r8">
                            <span>功能指南</span>
	                        <a href="/helpCenter/helpCenter.jsp?container=help-connect" target="_black" style="text-decoration:none;">
	                            <button class="btn btn-default btn-xs">
	                                <span class="glyphicon glyphicon-eject" id="rds-icon-guide"></span>
	                            </button>
                            </a>
                        </span>
                    </small>
                    <small>
                        <span>
                            <button class="btn-warning btn btn-sm disabled">内外网切换</button>
                        </span>
                    </small>
                    <small>
                        <span>
                            <button class="btn-danger btn btn-sm disabled">重启实例</button>
                        </span>
                    </small>
                    <small>
                        <span>
                            <button class="btn-default btn btn-sm disabled">备份实例</button>
                        </span>
                    </small>
                    <small>
                        <span>
                            <button class="btn-default btn btn-sm glyphicon glyphicon-list disabled"></button>
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
                    <li class="active"><a class="text-sm" src="${ctx}/detail/baseInfo/${dbId}" href="javascript:void(0)">基本信息</a></li>
                    <li><a  class="text-sm" src="${ctx}/detail/account/${dbId}" href="javascript:void(0)">账号管理</a></li>
                    <li><a  class="text-sm" href="javascript:void(0)"><span class="glyphicon glyphicon glyphicon-chevron-right"></span>系统资源监控</a>
                        <ul class="nav hide">
                            <li><a  class="text-sm" src="${ctx}/monitor/dbLink/${dbId}" href="javascript:void(0)">COMDML</a></li>
                            <li><a  class="text-sm" src="${ctx}/monitor/InnoDB/buffer/${dbId}" href="javascript:void(0)">InnoDB缓冲池</a></li>
                            <li><a  class="text-sm" src="${ctx}/monitor/QPS/TPS/${dbId}" href="javascript:void(0)">QPS/TPS</a></li>
                            <%-- <li><a  class="text-sm" src="${ctx}/monitor/cpu/${dbId}" href="javascript:void(0)">cpu使用率</a></li> --%>
                        <!--<li><a  class="text-sm" href="javascript:void(0)">磁盘空间</a></li>
                            <li><a  class="text-sm" href="javascript:void(0)">IOPS</a></li>
                            <li><a  class="text-sm" href="javascript:void(0)">CPU利用率</a></li>
                            <li><a  class="text-sm" href="javascript:void(0)">网络流量</a></li>
                            <li><a  class="text-sm" href="javascript:void(0)">InnoDB读写量</a></li>
                            <li><a  class="text-sm" href="javascript:void(0)">InnoDB读写次数</a></li>
                            <li><a  class="text-sm" href="javascript:void(0)">InnoDB日志</a></li>
                            <li><a  class="text-sm" href="javascript:void(0)">临时表</a></li>
                            <li><a  class="text-sm" href="javascript:void(0)">MyISAM key Buffer</a></li>
                            <li><a  class="text-sm" href="javascript:void(0)">MyISAM读写次数</a></li>
                            <li><a  class="text-sm" href="javascript:void(0)">COMDML</a></li>
                            <li><a  class="text-sm" href="javascript:void(0)">ROWDML</a></li> -->
                        </ul>
                    </li>
                    <li><a  class="text-sm" href="javascript:void(0)" src="${ctx}/list/backup/${dbId}">备份与恢复</a></li>
                    <li><a  class="text-sm" href="javascript:void(0)">参数设置 <p class="pull-right home-orange">敬请期待...</p></a></li>
                    <li><a class="text-sm" href="javascript:void(0)">日志管理<p class="pull-right home-orange">敬请期待...</p></a></li>
                    <li><a  class="text-sm" href="javascript:void(0)">性能优化<p class="pull-right home-orange">敬请期待...</p></a></li>
                    <li><a class="text-sm" href="javascript:void(0)">阈值报警<p class="pull-right home-orange">敬请期待...</p></a></li>
                    <li><a class="text-sm" src="${ctx}/detail/security/${dbId}" href="javascript:void(0)">安全控制</a></li>
                </ul>
            </div>
        </nav>
        <div class="embed-responsive embed-responsive-16by9 col-sm-10">
          <iframe class="embed-responsive-item" id = "frame-content" src="${ctx}/detail/baseInfo/${dbId}"></iframe>
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
    "bootstrap": "bootstrap/bootstrap/3.3.0/bootstrap.js"
}
});
seajs.use("${ctx}/static/page-js/clouddb/layout/main");
</script>

</html>