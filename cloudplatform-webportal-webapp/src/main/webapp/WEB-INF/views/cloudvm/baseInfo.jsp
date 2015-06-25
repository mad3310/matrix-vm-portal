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

	<title>云主机基本信息</title>
</head>

<body> 
<!-- 全局参数 start -->
	<input class="hidden" value="${vmId}" name="vmId" id="vmId" type="text" />
	<input class="hidden" value="${region}" name="region_id" id="region_id" type="text" />
	<div class="panel-group pd10" id="accordion" role="tablist" aria-multiselectable="true">
	    <div class="panel panel-default panel-table ">
	        <div class="panel-heading bdl-list overHidden panel-heading-mcluster" role="tab" id="headingOne" >
		        <span class="panel-title">
		          		基本信息
		        </span>						
				<!-- <div class="pull-right table-viewer-topbar-content">
					<a class="disabled btn btn-xs btn-primary" target="_blank" href="javascript:void(0)">只读实例文档</a>
					<a class="disabled btn btn-xs btn-primary" target="_blank" href="javascript:void(0)">购买只读实例</a>
				</div> -->
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
		        			<span class="text-muted pd-r8">云主机ID:</span>
		        			<span id="vm_info_vm_id"></span>
		        		</td>
		        		<td width="50%">
		        			<span class="text-muted pd-r8">区域:</span>
		        			<span id="vm_info_vm_region"></span>
		        		</td>
		        	</tr> 
		        	<tr>
			        	<td width="50%">
			        		<span class="text-muted pd-r8">名称:</span>
		        			<span id="vm_info_vm_name"></span>
		        			<a class="hide btn btn-default btn-xs glyphicon glyphicon-pencil" href="javascript:void(0)"></a>
			        	</td> 
			        	<td width="50%">
			        		<span class="text-muted pd-r8">镜像:</span>
		        			<span id="vm_info_vm_image"></span>
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
			        		<span id="vm_info_running_state">正常</span>
		        		</td>
		        		<td width="50%">
		        		</td>
		        	</tr> 
		        	</tbody>
		        </table>
		      </div>
		    </div>
		    
	  	</div>
		<div class="panel panel-default panel-table">
	        <div class="panel-heading bdl-list panel-heading-mcluster" role="tab" id="headingThree">
		        <span class="panel-title">
		          		配置
				</span>
				<a class="collapse-selector" data-toggle="collapse" href="#collapseThree"  aria-expanded="true" aria-controls="headingThree">					
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
		        		<td width="50%">
			        		<span class="text-muted pd-r8">规格:</span>
			        		<span id="vm_info_config_flavor"></span>
		        		</td>
		        		<td width="50%">
		        			<span class="text-muted pd-r8">内存:</span>
			        		<span id="vm_info_config_ram"></span>
		        		</td>
		        	</tr> 
		        	<tr>
		        		<td width="50%">
			        		<span class="text-muted pd-r8">虚拟内核:</span>
			        		<span id="vm_info_config_vcpu"></span>
		        		</td>
		        		<td width="50%">
		        			<span class="text-muted pd-r8">磁盘:</span>
			        		<span id="vm_info_config_disk"></span>
		        		</td>
		        	</tr> 
		        	</tbody>
		        </table>
		      </div>
		    </div>		   
		    
	  	</div>
		
		<div class="panel panel-default panel-table">
	        <div class="panel-heading bdl-list panel-heading-mcluster" role="tab" id="headingFour">
		        <span class="panel-title">
		          		网络
				</span>
				<div class="pull-right table-viewer-topbar-content">
					<a class="btn btn-xs btn-primary" id="getPublicIp" href="javascript:void(0)">获取公网IP</a>
				</div>
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
			        		<span class="text-muted pd-r8">私网ip:</span>
			        		<span id="vm_info_network_privateip"></span>
		        		</td>
		        		<td width="50%">	
		        			<span class="text-muted pd-r8">公网ip:</span>
			        		<span id="vm_info_network_publicip"></span>	        			
		        		</td>
		        	</tr> 
		        	<tr>
		        		<td width="50%">
			        		<span class="text-muted pd-r8">公司内网ip:</span>
			        		<span id="vm_info_network_sharedip"></span>
		        		</td>
		        		<td width="50%">	        			
		        		</td>
		        	</tr> 
		        	</tbody>
		        </table>
		      </div>
		    </div>
	  	</div>
	</div>
		<!-- main-content end-->
	<script type="text/javascript" src="${ctx}/static/modules/seajs/2.3.0/sea.js"></script>
	<script type="text/javascript">
		seajs.config({
			base: "${ctx}/static/modules/",
			alias: {
				"jquery": "jquery/2.0.3/jquery.min.js",
				"bootstrap": "bootstrap/bootstrap/3.3.0/bootstrap.js",
				"bootstrapValidator": "bootstrap/bootstrapValidator/0.5.3/bootstrapValidator.js"
			}
		});
		seajs.use("${ctx}/static/page-js/cloudvm/baseInfo/main");
	</script>
</body>

</html>
