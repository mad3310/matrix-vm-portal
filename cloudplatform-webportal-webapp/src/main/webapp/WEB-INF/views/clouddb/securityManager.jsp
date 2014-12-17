<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="zh">
<head>
	<meta charset="utf-8"/>
	<meta http-equiv="X-UA-compatible" content="IE=edge,chrome=1"/>
	<meta name="viewpoint" content="width=device-width,initial-scale=1"/>
	<!-- bootstrap css -->
	<link type="text/css" rel="stylesheet" href="css/bootstrap.min.css"/>
	<!-- ui-css -->
	<link type="text/css" rel="stylesheet" href="css/ui-css/common.css"/>
	<!-- js -->
	<script type="text/javascript" src="js/jquery-2.0.3.min.js"></script>
	<script type="text/javascript" src="js/bootstrap.min.js"></script>
	<script type="text/javascript" src="js/ui-js/common.js"></script>
	<title>app-dashboard</title>
</head>
<body>
<!-- top bar begin -->

	<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
      <div class="container-fluid">
        <div class="navbar-header">
          <a class="navbar-brand" href="#"><img src="img/cloud.ico"/></a>
        </div>
        <div class="navbar-header">
          <a class="navbar-brand active" href="#"><span class="glyphicon glyphicon-home"></span></a>
        </div>
        <div id="navbar" class="navbar-collapse collapse pull-right">
        	<form class="navbar-form navbar-right pull-left" role="form">
	            <div class="form-group">
	              <input type="text" placeholder="Search" class="form-control">
	            </div>
	            <button type="submit" class="btn btn-success"><span class="glyphicon glyphicon-search"></span></button>
	        </form>
            <ul class="nav navbar-nav">
	            <li><a href="#"><span class="glyphicon glyphicon-bell"></span></a></li>
	            <li class="dropdown">
	              <a href="#" class="dropdown-toggle" data-toggle="dropdown">userName <span class="caret"></span></a>
	              <ul class="dropdown-menu" role="menu">
	                <li><a href="#">用户中心</a></li>
	                <li><a href="#">我的订单</a></li>
	                <li><a href="#">账户管理</a></li>
	                <li class="divider"></li>
	                <li class="dropdown-header"><a href="#">退出</a></li>
	              </ul>
	            </li>
	            <li><a href="#"><span class="glyphicon glyphicon-lock"></span></a></li>
	            <li><a href="#"><span class="glyphicon glyphicon-pencil"></span></a></li>
          </ul>
        </div><!--/.nav-collapse -->
      </div>
    </nav>
<!-- top bar end -->

<!-- navbar begin -->
<div class="navbar navbar-default mt50"> 
        <div class="container-fluid">
          <div class="navbar-header">
            <a class="navbar-brand" href="#">Le云控制台首页</a>
          </div>
          <div id="navbar" class="navbar-collapse collapse pull-right">
            <ul class="nav navbar-nav">
              <li class="active"><a href="#"><span class="glyphicon glyphicon-phone"></span> 扫描二维码</a></li>
            </ul>
          </div>
        </div>
      </div>
	

<!-- navbar end -->
<!-- main-content begin-->
<div class="container-fluid">
	<div class="row main-header"> <!-- main-content-header begin -->
		<div class="col-sm-12 col-md-6">
			<div class="pull-left">
				<h3>
				<span class="text-success glyphicon glyphicon-phone"></span>
				<span id="serviceName" data-toggle="tooltip" data-placement="bottom" title="rdsenn6ryenn6r">rdsenn6ryenn6r...</span>
				<span style="display: inline-block;vertical-align:super;">
				<small class="text-success text-xs">(运行中...)</small></span>
				<a class="btn btn-default btn-xs" href="RDSlist.html"><span class="glyphicon glyphicon-step-backward"></span> 返回实例列表</a>
				</h3> 
			</div>
		</div>
		<div class="col-sm-12 col-md-6">
			<div class="pull-right">
				<h3>
				<small><span><a>功能指南</a> <button class="btn btn-default btn-xs"><span class="glyphicon glyphicon-eject" id="rds-icon-guide"></span></button></span></small>
				<small><span><button class="btn-warning btn btn-sm" data-toggle="modal" data-target="#myModalNetchange">内外网切换</button></span></small>
				<small><span><button class="btn-danger btn btn-sm" data-toggle="modal" data-target="#myModalCaseRestart">重启实例</button></span></small>
				<small><span><button class="btn-default btn btn-sm" data-toggle="modal" data-target="#myModalBackCase">备份实例</button></span></small>
				<small><span><button class="btn-default btn btn-sm glyphicon glyphicon-list"></button></span></small>
				</h3>
			</div>
		</div>
	</div><!-- main-content-header end-->

	<div class="row"><!-- main-content-center-begin -->
		<div class="col-sm-2 col-md-2">
			<div class="sidebar sidebar-line">
				<ul class="nav nav-sidebar nav-space li-underline">
		            <li class="active"><a class="text-sm" href="#">基本信息</a></li>
		            <li><a  class="text-sm" href="#">账号管理</a></li>
		            <li><a  class="text-sm" href="#">数据库管理</a></li>
		            <li><a  class="text-sm" href="#">系统资源监控</a></li>
		            <li><a  class="text-sm" href="#">备份与恢复</a></li>
		            <li><a  class="text-sm" href="#">参数设置</a></li>
		            <li><a class="text-sm" href="#">日志管理</a></li>
		            <li><a  class="text-sm" href="#">性能优化</a></li>
		            <li><a class="text-sm" href="#">阈值报警</a></li>
		            <li><a class="text-sm" href="#">安全控制</a></li>
		        </ul>
			</div>
		</div>
		<div class="col-sm-10 col-md-10">
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
				<div id="whitelist" role="tabpanel" class="tab-pane fade active"  aria-labelledby="whitelist-tab">
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
			<script>
					$('#setab a').click(function (e) {
  					e.preventDefault()
  					$(this).tab('show')
					})
			</script>
			<script>
			       $("#sqlInject-tab").click(function() {
                      $("#refresh").hide();
					}); 
			       $("#whitelist-tab").click(function() {
                      $("#refresh").show();
					}); 
			</script>
		    </div>				
	    </div>
   </div><!-- main-content-center-end -->
</div>
</body>
</html>