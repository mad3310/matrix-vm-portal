<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="zh">
<%@include file='main.jsp' %>
<link type="text/css" rel="stylesheet" href="${ctx}/static/css/ui-css/cbase.css"/>
<body>
	<!-- 全局参数 start -->
    <input class="hidden" value="${swiftId}" name="swiftId" id="swiftId" type="text" />
    <!-- <input class="hidden" value="702" name="cacheId" id="cacheId" type="text" /> -->
    <!-- 全局参数 end -->
    <!-- top bar begin -->
    <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation" style="min-height:40px;height:40px;">
      <div class="container-fluid">
        <div class="navbar-header">
    <a class="navbar-brand color" href="${ctx}/dashboard" style="padding-top:2px;height:40px !important;"><img src="${ctx}/static/img/logo.png"/></a>
     <a class="navbar-brand color top-bar-btn" href="${ctx}/dashboard" style="white-space:nowrap; font-size:13px"><i class="fa fa-home text-20"></i></a>
    <a class="navbar-brand color" href="${ctx}/list/oss" style="margin-left:0px;height:40px !important; font-size:13px"><i class="fa fa-database text-10"></i>&nbsp;开放存储服务OSS</a>
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
      <a class="navbar-brand" href="javascript:void(0)">开放存储服务<font color="#FF9C17">OSS</font></a>
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
                    <span id="ossName"></span>
                    <span style="display: inline-block;vertical-align:super;">
                        <small id="ossStatus" class="text-success text-xs"></small>
                    </span>
                    <a class="btn btn-default btn-xs" href="${ctx}/list/oss">
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
                            <span class="font-disabled">功能指南</span>
                            <button class="btn btn-default btn-xs disabled">
                                <span class="glyphicon glyphicon-eject" id="rds-icon-guide"></span>
                            </button>
                        </span>
                    </small>
                     <small>
                        <span>
                            <button type="button" class="btn btn-success btn-md" data-toggle='modal' data-target='#myModal'>配  置</button>
                        </span>
                    </small>
                  <!--   <small>
                        <span>
                            <button class="btn-default btn btn-sm glyphicon glyphicon-list disabled"></button>
                        </span>
                    </small> -->
                </h3>
            </div>
        </div>
         <div class="col-md-offset-4 col-md-4 alertool" id="alertool">
          </div>
    </div>
    <!-- main-content-header end-->
    <div class="row">
        <!-- main-content-center-begin -->
        <nav class="col-sm-2 col-md-2">
            <div class="sidebar sidebar-line sidebar-selector">
                <ul class="nav nav-sidebar li-underline">
                    <li class="active"><a class="text-sm" src="${ctx}/detail/ossBaseInfo/${swiftId}" href="javascript:void(0)">基本信息</a></li>
                    <%-- <li><a class="text-sm" src="${ctx}/detail/authorityInfo/${swiftId}" href="javascript:void(0)">权限管理</a></li> --%>
                  <!--  <li><a  class="text-sm" href="javascript:void(0)">权限管理 <p class="pull-right home-orange">敬请期待...</p></a></li> -->
                    <li><a  class="text-sm" href="javascript:void(0)" src="${ctx}/detail/file/${swiftId}">文件管理</a></li>
                    <li><a  class="text-sm" href="javascript:void(0)"><span class="glyphicon glyphicon glyphicon-chevron-right"></span>系统资源监控</a>
                        <ul class="nav hide">
                            <li><a  class="text-sm"  href="javascript:void(0)" src="${ctx}/monitor/oss/cpu/${ossId}">请求数</a></li>
                            <li><a  class="text-sm"  href="javascript:void(0)" src="${ctx}/monitor/oss/throughput/${ossId}">吞吐量</a></li>
                        </ul>
                    </li>
                </ul>
            </div>
        </nav>
        <div class="embed-responsive embed-responsive-16by9 col-sm-10">
          <!-- <iframe class="embed-responsive-item" id = "frame-content" src="${ctx}/detail/baseInfo/${dbId}"></iframe> -->
          <iframe class="embed-responsive-item" id = "frame-content" src="${ctx}/detail/ossBaseInfo/${swiftId}"></iframe>
        </div>
    </div>
</div>
<!-- Modal -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="height:500px;">
  <div class="modal-dialog modal-lg">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
        <h4 class="modal-title" id="myModalLabel">配置 </h4>
      </div>
      <div class="modal-body">
      	<form>
	        <div class="mt20 padding-left-32">
	    		<!-- <div class="form-group clearfix">
	    			<label class="col-sm-3 text-muted" style="font-weight:normal">用户权限:</label>
	    			<div class="col-sm-9 clearfix">
	    				<div class="pull-left"><input type="checkbox" value="0"><label>只读</label></div>
	    				<div class="pull-left padding-left-32"><input type="checkbox" value="1"><label>读写</label></div>
	    			</div>
	    		</div> -->
	    		<div class="form-group clearfix">
	    			<label class="col-sm-3 control-label text-muted" style="font-weight:normal">公开（匿名只读）:</label>
	    			<div class="col-sm-9 clearfix">
	    				<div class="pull-left"><input id="level-public" name="visibilityLevel" type="radio" value="PUBLIC"><label>PUBLIC</label></div>
	    				<div class="pull-left padding-left-32"><input id="level-private" name="visibilityLevel" type="radio" value="PRIVATE"><label>PRIVATE</label></div>
	    			</div>
	    		</div>
	    		<div class="form-group clearfix">
	    			<label class="col-sm-3 control-label text-muted" style="font-weight:normal">配额 :</label>
	    			<div class="col-sm-9 clearfix">
	    				<div class="self-dragBar"></div><!-- 拖动条生成容器 -->	
	    			</div>
	    		</div>
	    		<!-- <div class="form-group clearfix">
	    			<label class="col-sm-3 text-muted" style="font-weight:normal">启停服务:</label>
	    			<div class="col-sm-9 clearfix">
	    				<div class="pull-left"><input type="checkbox" value="0"><label>启动</label></div>
	    				<div class="pull-left padding-left-32"><input type="checkbox" value="1"><label>停止</label></div>
	    			</div>
	    		</div>	 -->
			</div>
		</form>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-primary" id="modifyCommit">确定</button>
        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
      </div>
    </div>
  </div>
</div><!-- modal end -->
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
seajs.use("${ctx}/static/page-js/cloudswift/layout/main");
</script>

</html>