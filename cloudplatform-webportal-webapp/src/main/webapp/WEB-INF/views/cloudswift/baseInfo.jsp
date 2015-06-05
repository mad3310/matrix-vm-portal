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
	<!-- ui-css -->
	<link type="text/css" rel="stylesheet" href="${ctx}/static/css/ui-css/common.css"/>

	<title>OSS实例基本信息</title>
</head>

<body> 
<!-- 全局参数 start -->
	<input class="hidden" value="${swiftId}" name="swiftId" id="swiftId" type="text" />
	<div class="panel-group pd10" id="accordion" role="tablist" aria-multiselectable="true">
	    <div class="panel panel-default panel-table ">
	        <div class="panel-heading bdl-list overHidden panel-heading-mcluster" role="tab" id="headingOne" >
		        <span class="panel-title">
		          		基本信息
		        </span>						
				<a class="collapse-selector" data-toggle="collapse" href="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
				<span class="toggle-drop-down-icon" toggle-show="toggleShow">
					<span class="glyphicon glyphicon-chevron-down table-viewer-dropdown "></span>
				</span>
				</a>
	    	</div>
		    <div id="collapseOne" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="headingOne">
		      <div class="panel-body pd0">
		        <table class="table table-bordered table-bi">
		        	<tbody>
		        	<tr>
		        		<td width="50%">
		        			<span class="text-muted pd-r8">实例名称:</span>
		        			<span id="oss_info_db_id"></span>
		        		</td>
		        		<td width="50%">
		        			<span class="text-muted pd-r8">地域:</span>
		        			<span>北京</span>
		        		</td>
		        	</tr> 
		        	<tr>
			        	<td width="50%">
			        		<span class="text-muted pd-r8">访问地址:</span>
		        			<span id="oss_info_url"></span>
		        			<a class="hide btn btn-default btn-xs glyphicon glyphicon-pencil" href="javascript:void(0)"></a>
			        	</td> 
			        	<td width="50%">
			        		<span class="text-muted pd-r8">可用区:</span>
		        			<span class="pd-r8" id="oss_info_available_region"></span>
			        		<span class="font-disabled pd-r8">[迁移可用区]</span>
			        	</td>
		        	</tr>
		        	</tbody>
		        </table>
		      </div>
		    </div>
	  	</div>
	  	<div class="panel panel-default panel-table">
	        <div class="panel-heading bdl-list panel-heading-mcluster" role="tab" id="headingTwo">
		        <span class="panel-title">
		          		运行状态
				</span>
				<a class="collapse-selector" data-toggle="collapse" href="#collapseTwo"  aria-expanded="true" aria-controls="collapseTwo">					
				<span class="toggle-drop-down-icon" toggle-show="toggleShow">		
					<span class="glyphicon glyphicon-chevron-down table-viewer-dropdown "></span>						  
				</span>
				</a>
	    	</div>
		    <div id="collapseTwo" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="headingTwo">
		      <div class="panel-body pd0">
		        <table class="table table-bordered table-bi">
		        	<tbody>
		        	<tr>
		        		<td width="50%">
			        		<span class="text-muted pd-r8">运行状态:</span>
			        		<span id="oss_info_running_state"></span>
		        		</td>
		        		<td width="50%">
			        		<span class="text-muted pd-r8">公开(匿名访问):</span>
			        		<span id="oss_info_visibilityLevel"></span>
		        		</td>
		        	</tr> 
		        	<tr>
			        	<td width="50%">
			        		<span class="text-muted pd-r8">可用性:</span><span>100.0%</span>
			        	</td> 
			        	<td width="50%">
			        		<span class="text-muted pd-r8">配额:</span><span></span>
			        		<span id="oss_info_sotre_size"></span>
			        	</td>
		        	</tr>
		        	</tbody>
		        </table>
		      </div>
		    </div>
	  	</div>
	  	
	  	<div class="panel panel-default panel-table">
	        <div class="panel-heading bdl-list panel-heading-mcluster" role="tab" id="headingTwo">
		        <span class="panel-title">
		          		运行周期
				</span>
				<a class="collapse-selector" data-toggle="collapse" href="#collapseFour"  aria-expanded="true" aria-controls="collapseFour">
				<span class="toggle-drop-down-icon" toggle-show="toggleShow">
					<span class="glyphicon glyphicon-chevron-down table-viewer-dropdown "></span>
				</span>
				</a>
	    	</div>
		    <div id="collapseFour" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="headingFour">
		      <div class="panel-body pd0">
		        <table class="table table-bordered table-bi">
		        	<tbody>
		        	<tr>
			        	<td width="50%">
			        		<span class="text-muted pd-r8">创建时间:</span><span id="oss_info_create_time"></span>
			        	</td>
			        	<td width="50%">
			        		<span class="text-muted pd-r8">付费类型:</span><span>包年  <span id="oss_info_remain_days"></span>天后到期</span>
			        	</td>
		        	</tr>
		        	</tbody>
		        </table>
		      </div>
		    </div>
	  	</div>
	  	<div class="panel panel-default panel-table">
	        <div class="panel-heading bdl-list panel-heading-mcluster" role="tab" id="headingTwo">
		        <span class="panel-title">
					可维护的时间段
				</span>
				<a class="collapse-selector" data-toggle="collapse" href="#collapseFive"  aria-expanded="true" aria-controls="collapseFive">
				<span class="toggle-drop-down-icon" toggle-show="toggleShow">
					<span class="glyphicon glyphicon-chevron-down table-viewer-dropdown "></span>
				</span>
				</a>
	    	</div>
		    <div id="collapseFive" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="headingFive">
		      <div class="panel-body pd0">
		        <table class="table table-bordered table-bi">
		        	<tbody>
		        	<tr>
			        	<td width="50%">
			        		<span class="text-muted pd-r8">可维护的时间段:</span>
			        		<span class="pd-r8">未设置</span>
			        		<span class="font-disabled pd-r8">设置</span>
			        	</td>
		        	</tr>
		        	</tbody>
		        </table>
		      </div>
		    </div>
	  	</div>
	    <!-- /.modal config-->
		<div class="modal" id="dbConfigModal" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog modal-sm">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">&times;</button>
						<h4 class="modal-title" id="dbConfigModalLabel"></h4>
					</div>
					<div class="modal-body clearfix" id="dbConfigInfoWrap" >
						<div class="zero-clipboard">
							<a id= "zclipCopy" class="btn-clipboard">复制</a>
						</div>
						<pre>
							<code id="dbConfigInfo" class="language-html"></code>
						</pre>
						<div id="zeroclipboardTooltip" data-toggle="tooltip" title="" style="position: absolute; right: 50px; top:10px; z-index: 999999999;">
						</div>
					</div>
					<!-- <div class="modal-footer">
						<button type="button" id="fortest" class="btn btn-default" >测试</button>
					</div> -->
				</div>
				<!-- /.modal-content -->
			</div>
			<!-- /.modal -->
		</div>
	</div>
</body>


<!-- js -->
<script type="text/javascript" src="${ctx}/static/modules/seajs/2.3.0/sea.js"></script>
<script type="text/javascript">
	// Set configuration
	seajs.config({
		base : "${ctx}/static/modules/",
		alias : {
			"jquery" : "jquery/2.0.3/jquery.min.js",
			"bootstrap" : "bootstrap/bootstrap/3.3.0/bootstrap.js",
			"zclip" : "jquery/zclip/jquery.zclip.min.js"
		}
	});
	seajs.use("${ctx}/static/page-js/cloudswift/basicInfo/main");
</script>
</html>
