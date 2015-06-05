<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="zh">
<%@include file='main.jsp' %>
<body>
	<!-- 全局参数 start -->
    <input class="hidden" value="${cacheId}" name="cacheId" id="cacheId" type="text" />
    <!-- 全局参数 end -->
    <%@ include file="../../layouts/header.jsp"%>

<!-- main-content begin-->
<div class="container-fluid">
    <div class="row main-header">
        <!-- main-content-header begin -->
        <div class="col-xs-12 col-sm-6 col-md-6">
            <div class="pull-left">
                <h3>
                    <span class="fa  fa-cubes"></span>
                    <span id="ocsName"></span>
                    <span style="display: inline-block;vertical-align:super;">
                        <small id="ocsStatus" class="text-success text-xs"></small>
                    </span>
                    <a class="btn btn-default btn-xs" href="${ctx}/list/cache">
                        <span class="glyphicon glyphicon-step-backward"></span>
                        返回实例列表
                    </a>
                </h3>
            </div>
        </div>
        <div class="col-xs-12 col-sm-6 col-md-6 hidden-xs">
            <div class="pull-right">
                <h3>
                    <small>
                        <span class="pd-r8">
                            <span class="font-disabled">功能指南</span>
                            <button class="btn btn-default btn-xs disabled">
                                <span class="glyphicon glyphicon-eject" id="rds-icon-guide"></span>
                            </button>
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
        <nav id="sidebar" class="col-sm-2 col-md-2 nav-sidebar-div">
            <div class="sidebar sidebar-line sidebar-selector">
                <ul class="nav nav-sidebar li-underline">
                    <li class="active"><a class="text-sm" src="${ctx}/detail/cacheBaseInfo/${cacheId}" href="javascript:void(0)">基本信息</a></li>
                    <li><a class="text-sm" href="javascript:void(0)" <%-- src="${ctx}/monitor/cache/${cacheId}" --%>>监控信息<p class="pull-right home-orange">敬请期待...</p></a></li>
                    <li><a class="text-sm" href="javascript:void(0)" <%-- src="${ctx}/detail/data/${cacheId}" --%>>数据管理<p class="pull-right home-orange">敬请期待...</p></a></li>
                    <li><a class="text-sm" href="javascript:void(0)">参数设置 <p class="pull-right home-orange">敬请期待...</p></a></li>
                    <li><a class="text-sm" href="javascript:void(0)">日志管理<p class="pull-right home-orange">敬请期待...</p></a></li>
                    <li><a class="text-sm" href="javascript:void(0)">阈值报警<p class="pull-right home-orange">敬请期待...</p></a></li>
                </ul>
            </div>
        </nav>
        <div class="embed-responsive embed-responsive-16by9 col-sm-10 col-xm-12" id="frame-content-div">
          <iframe class="embed-responsive-item" id = "frame-content" src="${ctx}/detail/cacheBaseInfo/${cacheId}" frameBorder=0 scrolling="no"></iframe>
        </div>
    </div>
</div>
    <%@ include file="../../layouts/rToolbar.jsp"%>
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
seajs.use("${ctx}/static/page-js/cloudcache/layout/main");
</script>

</html>