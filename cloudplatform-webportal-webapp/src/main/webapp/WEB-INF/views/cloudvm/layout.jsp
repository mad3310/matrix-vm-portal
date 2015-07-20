<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="zh">

<head>
	<meta charset="utf-8"/>
	<meta http-equiv="X-UA-compatible" content="IE=edge,chrome=1"/>
	<meta name="viewport" content="width=device-width,initial-scale=1, maximum-scale=1, user-scalable=no"/>
	<!-- bootstrap css -->
	<link type="text/css" rel="stylesheet" href="${ctx}/static/css/bootstrap.min.css"/>
	<!-- fontawesome css -->
	<link type="text/css" rel="stylesheet" href="${ctx}/static/css/font-awesome.min.css"/>
	<!-- ui-css -->
	<link type="text/css" rel="stylesheet" href="${ctx}/static/css/ui-css/common.css"/>
	<title>云主机</title>
</head>

<body>
	<!-- 全局参数 start -->
	<input class="hidden" value="${vmId}" name="vmId" id="vmId" type="text" />
	<input class="hidden" value="${region}" name="region" id="region" type="text" />
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
                    <span id="vmName"></span>
                    <span style="display: inline-block;vertical-align:super;">
                        <small id="vmStatus" class="text-success text-xs"></small>
                    </span>
                    <a class="btn btn-default btn-xs" href="${ctx}/list/vm">
                        <span class="glyphicon glyphicon-step-backward"></span>
                        	返回实例列表
                    </a>
                </h3>
            </div>
        </div>
        <div class="col-sm-6 col-md-6 hidden-xs">
            <div id="right-head" class="pull-right">
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
                            <button id="start-vm-btn" class="btn-success btn btn-sm">启动实例</button>
                        </span>
                    </small>
                    <small>
                        <span>
                            <button id="stop-vm-btn" class="btn-danger btn btn-sm">停止实例</button>
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
        <div class="col-md-offset-4 col-md-4 alertool" id="alertool">
      	</div>
    </div>
    <!-- main-content-header end-->
    <div class="row">
        <!-- main-content-center-begin -->
        <nav id="sidebar" class="col-sm-2 col-md-2 nav-sidebar-div">
            <div class="sidebar sidebar-line sidebar-selector">
                <ul class="nav nav-sidebar li-underline">
                    <li class="active"><a class="text-sm" src="${ctx}/detail/vmBaseInfo/${vmId}?region=${region}" href="javascript:void(0)">基本信息</a></li>
                    <li><a class="text-sm" src="${ctx}/detail/vmDisks/${vmId}?region=${region}" href="javascript:void(0)">磁盘</a></li>
                </ul>
            </div>
        </nav>
        
        <div class="embed-responsive embed-responsive-16by9 col-sm-10 col-xm-12"  id="frame-content-div">
        <iframe class="embed-responsive-item " id="frame-content" src="${ctx}/detail/vmBaseInfo/${vmId}?region=${region}" frameBorder=0 scrolling="no"></iframe>
        </div> 
    </div>
</div>
<!--确认对话框-->
	<div id="dialog-box" class="modal">
		<div class="modal-dialog modal-sm">
			<div class="modal-content">
				<div class="modal-header">
			<!--	<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span><span class="sr-only">关闭</span>
					</button>-->
					<h5 id="dialog-box-title" class="modal-title"></h5>
				</div>
				<div class="modal-body clearfix">
					<div class="col-xs-1 col-sm-1 col-md-1 warning-sign">
						<span class="glyphicon glyphicon-exclamation-sign"></span>
					</div>
					<div id="dialog-box-text" class="col-xs-11 col-sm-10 "></div>
				</div>
				<div class="modal-footer">
					<button id="dialogBoxSubmit" type="button" class="btn btn-primary" data-dismiss="modal">确定</button>
					<button id="dialogBoxCancel" type="button" class="btn btn-default" data-dismiss="modal">取消</button>
				</div>
			</div>
		</div>
	</div>
<%@ include file="../../layouts/rToolbar.jsp"%>
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
seajs.use("${ctx}/static/page-js/cloudvm/layout/main");
</script>
	
</body>

</html>
