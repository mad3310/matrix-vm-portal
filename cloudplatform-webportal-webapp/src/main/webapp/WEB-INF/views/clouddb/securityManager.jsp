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
	<title>RDS安全控制</title>
</head>
<body>
	<div class="panel-group pd10"  role="tablist" aria-multiselectable="true">
	    <div class="se-heading" id="headingOne" >
	        <div class="pull-left">
	        	<h5>
	        	安全控制
		        </h5>
	        </div>				      
		    <div class="pull-right">
		       	<button id="refresh" class="btn btn-default">
		       	<span class="glyphicon glyphicon-refresh"></span>				      
		       	刷新
		        </button>
		    </div>
	    </div>
	    <ul class="nav nav-tabs" role="tablist" id="setab">
	    	<li id="whitelist-tab" role="presentation" class="active">
	    	<a data-toggle="tab" href="#whitelist">白名单设置</a></li>
                  <li id="sqlInject-tab" role="presentation">
                  <a data-toggle="tab" href="#sqlInject">SQL注入警告</a></li> 	
	    </ul>
		<!-- <div class="panel-body pd0" id="whitelist"> -->
		<div class="tab-content">				
			<div id="whitelist" role="tabpanel" class="tab-pane fade active in"  aria-labelledby="whitelist-tab">
			        <table class="table table-hover">
			        	<thead>				        	
			        	<tr>
			        		<th colspan="4">
			        			允许访问IP名单
			        		</th>				        		
			        	</tr>
			        	</thead>
			        	<tbody>
			        		<tr >				        			
			        			<td width="25%" >10.23.12.24/22
			        			</td>				        			
			        			<td width="25%" >0.0.0.0/0
			        			</td>				        			
			        			<td width="25%" >33.22.11.44
			        			</td>
			        			
			        			<td width="25%" >10.23.12.24/21
			        			</td>
			        			
			        		</tr>
			        		<tr>				        			
			        			<td width="25%" >10.23.12.24/22
			        			</td>				        			
			        			<td width="25%" >0.0.0.0/0
			        			</td>				        			
			        			<td width="25%" >33.22.11.44
			        			</td>	
			        			<td width="25%" >
			        			</td>	
			        		</tr>
			        	</tbody> 				        	
			        </table>			        			  	
		  		<div class="has-warning help-block">
		  			您已添加<span class="">7</span>个IP，还能添加<span class="ng-binding">93</span>个。
		  		</div>
		  		<div class="" style="margin-bottom: 40px">
		  			<button class="btn btn-primary">手动修改</button> 
		  			<span style="padding:8px">或</span> 
		  			<button class="btn btn-primary">加载ECS内网IP添加
		  			</button> 
		  			<button class="btn btn-primary">将白名单复制至其他实例
		  			</button>
		  		</div>
		  	</div>			
		    <div id="sqlInject" role="tabpanel" class="tab-pane fade" aria-labelledby="sqlInject-tab">
		    	<div class="time-range-unit-header">
		    		<span class="time-range-title">选择时间范围：</span>
		    		<div class="date-unit">
		    			<input type="date" class="form-control date-picker-unit" value="2014/12/15">
		    		</div>
		    		<span class="date-step-span">至</span>
		    		<div class="date-unit">
		    		     <input type="date" class="form-control date-picker-unit" value="2014/12/15">
		    	    </div>		    
		    	     <button class="btn btn-primary btn-search">查询</button>	
		    	</div>	
		    	<table class="table table-hover">
		    			<thead>
		    				<tr>
		    					<th>时间</th>
		    					<th>执行帐号</th>
		    					<th>sql语句</th>
		    				</tr>
		    			</thead>
		    	</table>			    		    		    	
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

seajs.use("${ctx}/static/page-js/securityManager/main");
</script>
</html>