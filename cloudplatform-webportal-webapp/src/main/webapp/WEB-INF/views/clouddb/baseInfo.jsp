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

	<title>RDS基本信息</title>
</head>
<body> 
<!-- 全局参数 start -->
	<input class="hidden" value="${dbId}" name="dbId" id="dbId" type="text" />
	<div class="panel-group pd10" id="accordion" role="tablist" aria-multiselectable="true">
	    <div class="panel panel-default panel-table ">
	        <div class="panel-heading bdl-list overHidden panel-heading-mcluster" role="tab" id="headingOne" >
		        <span class="panel-title">
		          		基本信息
		        </span>						
				<div class="pull-right table-viewer-topbar-content">
					<a class="hide btn btn-xs btn-primary" target="_blank" href="#">只读实例文档</a>
					<a class="hide btn btn-xs btn-primary" target="_blank" href="#" data-toggle="modal" data-target="#myModalbuyCase">购买只读实例</a>
				</div>
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
		        		<td id="db_info_db_id" width="50%"></td>
		        		<td width="50%"><span class="text-muted" style="padding-right:8px">地域:</span><span>北京</span></td>
		        	</tr> 
		        	<tr>
			        	<td id="db_info_db_name" width="50%"></td> 
			        	<td id="db_info_available_region" width="50%"></td>
		        	</tr>
		        	<tr>
			        	<td id="db_info_net_addr" width="50%"></td> 
			        	<td id="db_info_db_port" width="50%"></td>
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
		        		<td id="db_info_running_state" width="50%"></td>
		        		<td width="50%"><span class="text-muted" style="padding-right:8px">锁定模式:</span><span>正常</span></td>
		        	</tr> 
		        	<tr>
			        	<td width="50%">
			        		<span class="text-muted pd-r8">可用性:</span><span text-length="26">100.0%</span>
			        	</td> 
			        	<td width="50%">
			        		<span class="text-muted pd-r8">已用空间:</span><span>501M</span>
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
		          		配置信息
				</span>
				<a class="collapse-selector" data-toggle="collapse" href="#collapseThree"  aria-expanded="true" aria-controls="collapseThree">
				<span class="toggle-drop-down-icon" toggle-show="toggleShow">
					<span class="glyphicon glyphicon-chevron-down table-viewer-dropdown "></span>
				</span>
				</a>
	    	</div>
		    <div id="collapseThree" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="headingThree">
		      <div class="panel-body pd0">
		        <table class="table table-bordered table-bi">
		        	<tbody>
		        	<tr>
		        		<td width="50%"><span class="text-muted pd-r8">运行状态:</span><span class="text-success">运行中</span></td>
		        		<td width="50%"><span class="text-muted" style="padding-right:8px">锁定模式:</span><span>正常</span></td>
		        	</tr>
		        	<tr>
			        	<td width="50%">
			        		<span class="text-muted pd-r8">可用性:</span><span text-length="26">100.0%</span>
			        	</td>
			        	<td width="50%">
			        		<span class="text-muted pd-r8">已用空间:</span><span>501M</span>
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
		        		<td width="50%"><span class="text-muted pd-r8">运行状态:</span><span class="text-success">运行中</span></td>
		        		<td width="50%"><span class="text-muted" style="padding-right:8px">锁定模式:</span><span>正常</span></td>
		        	</tr>
		        	<tr>
			        	<td width="50%">
			        		<span class="text-muted pd-r8">可用性:</span><span text-length="26">100.0%</span>
			        	</td>
			        	<td width="50%">
			        		<span class="text-muted pd-r8">已用空间:</span><span>501M</span>
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
		        		<td width="50%"><span class="text-muted pd-r8">运行状态:</span><span class="text-success">运行中</span></td>
		        		<td width="50%"><span class="text-muted" style="padding-right:8px">锁定模式:</span><span>正常</span></td>
		        	</tr>
		        	<tr>
			        	<td width="50%">
			        		<span class="text-muted pd-r8">可用性:</span><span text-length="26">100.0%</span>
			        	</td>
			        	<td width="50%">
			        		<span class="text-muted pd-r8">已用空间:</span><span>501M</span>
			        	</td>
		        	</tr>
		        	</tbody>
		        </table>
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
		"bootstrap": "bootstrap/bootstrap/3.3.0/bootstrap.js"
	}
});

seajs.use("${ctx}/static/page-js/basicInfo/main");

</script>
</html>