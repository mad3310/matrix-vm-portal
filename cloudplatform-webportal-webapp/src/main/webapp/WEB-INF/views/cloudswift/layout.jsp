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
<%@ include file="../../layouts/header.jsp"%>
<!-- main-content begin-->
<div class="container-fluid">
    <div class="row main-header">
        <!-- main-content-header begin -->
        <div class="col-xs-10 col-sm-6 col-md-6">
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
        <div class="col-xs-2 col-sm-6 col-md-6">
            <div class="pull-right">
                <h3>
                    <small class='hidden-xs'>
                        <span class="pd-r8">
                            <span>功能指南</span>
                            <a href="/helpCenter/helpCenter.jsp?container=product-OSSIntro" target="_black" style="text-decoration:none;">
	                            <button class="btn btn-default btn-xs">
	                                <span class="glyphicon glyphicon-eject" id="rds-icon-guide"></span>
	                            </button>
                            </a>
                        </span>
                    </small>
                     <small>
                        <span>
                            <button type="button" class="btn btn-success btn-md" data-backdrop="false" data-toggle='modal' data-target='#myModal'><span class='hidden-xs'>配  置</span><span class='hidden-sm hidden-md hidden-lg glyphicon glyphicon-cog'></span></button>
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
                    <li class="active"><a class="text-sm" src="${ctx}/detail/ossBaseInfo/${swiftId}" href="javascript:void(0)">基本信息</a></li>
                    <%-- <li><a class="text-sm" src="${ctx}/detail/authorityInfo/${swiftId}" href="javascript:void(0)">权限管理</a></li> --%>
                    <li><a  class="text-sm" href="javascript:void(0)" src="${ctx}/detail/file/${swiftId}">文件管理</a></li>
                    <li><a  class="text-sm" href="javascript:void(0)"><span class="glyphicon glyphicon glyphicon-chevron-right"></span>系统资源监控</a>
                        <ul class="nav hide">
                            <li><a  class="text-sm"  href="javascript:void(0)" src="${ctx}/monitor/reqfreq/${swiftId}">请求数</a></li>
                            <li><a  class="text-sm"  href="javascript:void(0)" src="${ctx}/monitor/throughput/${swiftId}">吞吐量</a></li>
                        </ul>
                    </li>
                </ul>
            </div>
        </nav>
        <div class="embed-responsive embed-responsive-16by9 col-sm-10 col-xm-12" id="frame-content-div">
          <!-- <iframe class="embed-responsive-item" id = "frame-content" src="${ctx}/detail/baseInfo/${dbId}"></iframe> -->
          <iframe class="embed-responsive-item" id="frame-content" src="${ctx}/detail/ossBaseInfo/${swiftId}" frameBorder=0 scrolling="no"></iframe>
        </div>
    </div>
</div>
<!-- Modal -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="false" style="height:500px;">
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
	    			<label class="col-xs-12 col-sm-3 control-label text-muted" style="font-weight:normal">公开（匿名只读）:</label>
	    			<div class="col-xs-12 col-sm-9 clearfix">
	    				<div class="pull-left"><input id="level-public" name="visibilityLevel" type="radio" value="PUBLIC"><label>PUBLIC</label></div>
	    				<div class="pull-left padding-left-32"><input id="level-private" name="visibilityLevel" type="radio" value="PRIVATE"><label>PRIVATE</label></div>
	    			</div>
	    		</div>
	    		<div class="form-group clearfix  hidden-xs">
	    			<label class="col-xs-12 col-sm-3 control-label text-muted" style="font-weight:normal">配额 :</label>
	    			<div class="col-xs-12 col-sm-9 clearfix">
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
seajs.use("${ctx}/static/page-js/cloudswift/layout/main");
</script>

</html>