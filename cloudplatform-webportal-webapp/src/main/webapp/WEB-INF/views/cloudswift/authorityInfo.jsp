<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="zh">
<head>
	<meta charset="utf-8"/>
	<meta http-equiv="X-UA-compatible" content="IE=edge,chrome=1"/>
	<meta name="viewpoint" content="width=device-width,initial-scale=1, maximum-scale=1, user-scalable=no"/>
	<!-- bootstrap css -->
	<link type="text/css" rel="stylesheet" href="${ctx}/static/css/bootstrap.min.css"/>
	<link type="text/css" rel="stylesheet" href="${ctx}/static/css/bootstrap-datetimepicker.min.css"/>
	<link type="text/css" rel="stylesheet" href="${ctx}/static/css/font-awesome.min.css" />
	<!-- ui-css -->
	<link type="text/css" rel="stylesheet" href="${ctx}/static/css/ui-css/common.css"/>
	<link type="text/css" rel="stylesheet" href="${ctx}/static/css/ui-css/cbase.css"/>
	<title>权限管理</title>
</head>
<body>
    <!-- 全局参数 start -->
	<input class="hidden" value="${dbId}" name="dbId" id="dbId" type="text" />
	<!-- Modal -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="height:500px;">
  <div class="modal-dialog modal-lg">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
        <h4 class="modal-title" id="myModalLabel">权限设置 </h4>
      </div>
      <div class="modal-body">
      	<form>
	        <div class="mt20 padding-left-32">
	    		<div class="form-group clearfix">
	    			<label class="col-sm-3 text-muted" style="font-weight:normal">用户权限:</label>
	    			<div class="col-sm-9 clearfix">
	    				<div class="pull-left"><input type="checkbox" value="0"><label>只读</label></div>
	    				<div class="pull-left padding-left-32"><input type="checkbox" value="1"><label>读写</label></div>
	    			</div>
	    		</div>
	    		<div class="form-group clearfix">
	    			<label class="col-sm-3 control-label text-muted" style="font-weight:normal">公开（匿名只读）:</label>
	    			<div class="col-sm-9 clearfix">
	    				<div class="pull-left"><input type="checkbox" value="0"><label>公开</label></div>
	    				<div class="pull-left padding-left-32"><input type="checkbox" value="1"><label>不公开</label></div>
	    			</div>
	    		</div>
	    		<div class="form-group clearfix">
	    			<label class="col-sm-3 control-label text-muted" style="font-weight:normal">配额 :</label>
	    			<div class="col-sm-9 clearfix">
	    				<div class="self-dragBar"></div><!-- 拖动条生成容器 -->	
	    			</div>
	    		</div>
	    		<div class="form-group clearfix">
	    			<label class="col-sm-3 text-muted" style="font-weight:normal">启停服务:</label>
	    			<div class="col-sm-9 clearfix">
	    				<div class="pull-left"><input type="checkbox" value="0"><label>启动</label></div>
	    				<div class="pull-left padding-left-32"><input type="checkbox" value="1"><label>停止</label></div>
	    			</div>
	    		</div>	
			</div>
		</form>
		<div class="alert alert-warning"> 注：权限设置需注意问题。</div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-primary">确定</button>
        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
      </div>
    </div>
  </div>
</div><!-- modal end -->

	<div class="panel-group pd10"  role="tablist" aria-multiselectable="true">
	    <div class="se-heading" id="headingOne" >
	        <div class="pull-left clearfix">
	        	<h5>
	        	权限设置
	        	<!-- <a id="back_a" data-toggle="tooltip" data-placement="top" title="在同一时间只能有一个临时实例，若需回滚到另一个临时实例，请先删除当前临时实例。">
					<i class="fa fa-question-circle text-muted"></i>
				</a> -->
		        </h5>
	        </div>
	    </div>
	    <div>
	    	<div class="pull-left col-sm-10 mt20 padding-left-32">
				<div class="form-group clearfix">
					<label class="col-sm-2 text-muted" style="font-weight: normal">用户权限:</label>
					<div class="col-sm-8">
						<div class="form-control-static pd0">读写</div>
					</div>
				</div>
				<div class="form-group clearfix">
					<label class="col-sm-2 control-label text-muted"
						style="font-weight: normal">公开（匿名只读）:</label>
					<div class="col-sm-8">
						<div class="form-control-static pd0">不公开</div>
					</div>
				</div>
				<div class="form-group clearfix">
					<label class="col-sm-2 control-label text-muted"
						style="font-weight: normal">配额:</label>
					<div class="col-sm-8">
						<div class="form-control-static pd0">4 MB</div>
					</div>
				</div>
				<div class="form-group clearfix">
					<label class="col-sm-2 control-label text-muted"
						style="font-weight: normal">启停服务:</label>
					<div class="col-sm-8">
						<!-- <div id="backupTime" class="form-control-static pd0"></div> -->
						<div class="form-control-static pd0">停止</div>
					</div>
				</div>
			</div>
			<div class="pull-left col-sm-10 padding-left-32">
				<div class="form-group">
					<label class="col-sm-2 control-label"></label>
					<button type="button" class="btn btn-primary btn-sm" data-toggle='modal' data-target='#myModal'>修改权限配置</button>
				</div>
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
		// "moment": "moment/2.9.0/moment-with-locales.js",
		"bootstrap": "bootstrap/bootstrap/3.3.0/bootstrap.js",
		// "paginator": "bootstrap/paginator/bootstrap-paginator.js",
		// "datetimepicker":"bootstrap/datetimepicker/bootstrap-datetimepicker.js"
	}
});
seajs.use("${ctx}/static/page-js/cloudswift/authorityInfo/main");
</script>
</html>
