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

	<title>app-dashboard</title>
</head>
<body> 
<!-- 全局参数 start -->
	<input class="hidden" value="${dbId}" name="dbId" id="dbId" type="text" />
<!-- 全局参数 end -->
<!-- top bar begin -->
	<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
      <div class="container-fluid">
        <div class="navbar-header">
          <a class="navbar-brand" href="${ctx}/dashboard"><img src="${ctx}/static/img/cloud.ico"/></a>
        </div>
        <div class="navbar-header">
          <a class="navbar-brand active" href="${ctx}/dashboard"><span class="glyphicon glyphicon-home"></span></a>
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
	              <a href="#" class="dropdown-toggle" data-toggle="dropdown">${sessionScope.userSession.userName}<span class="caret"></span></a>
	              <ul class="dropdown-menu" role="menu">
	                <li><a href="#">用户中心</a></li>
	                <li><a href="#">我的订单</a></li>
	                <li><a href="#">账户管理</a></li>
	                <li class="divider"></li>
	                <li class="dropdown-header"><a href="${ctx}/account/logout">退出</a></li>
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
      <a class="navbar-brand" href="${ctx}/dashboard">Le云控制台首页</a>
    </div>
    <div id="navbar" class="navbar-collapse collapse pull-right">
      <ul class="nav navbar-nav hide">
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
				<a class="btn btn-default btn-xs" href="${ctx}/list/db"><span class="glyphicon glyphicon-step-backward"></span> 返回实例列表</a>
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
		<nav class="col-sm-2 col-md-2">
			<div class="sidebar sidebar-line sidebar-selector">
				<ul class="nav nav-sidebar li-underline">
		            <li><a class="text-sm" href="${ctx}/detail/baseInfo/${dbId}">基本信息</a></li>
		            <li><a  class="text-sm" href="${ctx}/detail/account/${dbId}">账号管理</a></li>
		            <li><a  class="text-sm" href="#">
							<span class="glyphicon glyphicon glyphicon-chevron-right"></span>
							系统资源监控
						</a>
						<ul class="nav hide">
							<li><a  class="text-sm" href="${ctx}/detail/monitor/${dbId}">磁盘空间</a></li>
							<li><a  class="text-sm" href="#">IOPS</a></li>
							<li><a  class="text-sm" href="#">连接数</a></li>
							<li><a  class="text-sm" href="#">CPU利用率</a></li>
							<li><a  class="text-sm" href="#">网络流量</a></li>
							<li><a  class="text-sm" href="#">QPS/TPS</a></li>
							<li><a  class="text-sm" href="#">InnoDB缓冲池</a></li>
							<li><a  class="text-sm" href="#">InnoDB读写量</a></li>
							<li><a  class="text-sm" href="#">InnoDB读写次数</a></li>
							<li><a  class="text-sm" href="#">InnoDB日志</a></li>
							<li><a  class="text-sm" href="#">临时表</a></li>
							<li><a  class="text-sm" href="#">MyISAM key Buffer</a></li>
							<li><a  class="text-sm" href="#">MyISAM读写次数</a></li>
							<li><a  class="text-sm" href="#">COMDML</a></li>
							<li><a  class="text-sm" href="#">ROWDML</a></li>
						</ul>
					</li>
		            <li><a  class="text-sm" href="#">备份与恢复</a></li>
		            <li><a  class="text-sm" href="#">参数设置</a></li>
		            <li><a class="text-sm" href="#">日志管理</a></li>
		            <li><a  class="text-sm" href="#">性能优化</a></li>
		            <li><a class="text-sm" href="#">阈值报警</a></li>
		            <li><a class="text-sm" href="${ctx}/detail/security/${dbId}">安全控制</a></li>
		        </ul>
			</div>
		</nav>
		<div class="col-sm-10 col-md-10">
			<div class="panel-group pd10" id="accordion" role="tablist" aria-multiselectable="true">
			    <div class="panel panel-default panel-table ">
			        <div class="panel-heading bdl-list overHidden panel-heading-mcluster" role="tab" id="headingOne" >
				        <span class="panel-title">
				          		基本信息
				        </span>						
						<div class="pull-right table-viewer-topbar-content">
							<a class="btn btn-xs btn-primary" target="_blank" href="#">只读实例文档</a>
							<a class="btn btn-xs btn-primary" target="_blank" href="#" data-toggle="modal" data-target="#myModalbuyCase">购买只读实例</a>
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
					        	<td width="50%">
					        		<span class="text-muted pd-r8">可用区:</span>可用区A<a class="margin-left-5" data-toggle="modal" data-target="#myModalMoveAvaiable">[迁移可用区]</a><a href="#" target="_blank" class="glyphicon glyphicon-question-sign text-muted margin-left-5"></a>
					        	</td>
				        	</tr>
				        	<tr>
					        	<td id="db_info_net_addr" width="50%"></td> 
					        	<td width="50%">
					        		<span class="text-muted pd-r8">端口:</span>3306<a class="margin-left-5">如何连接RDS</a><a href="#" target="_blank" class=" margin-left-5">如何设置白名单</a>
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
		</div>
	</div><!-- main-content-center-end -->
</div>

<!-- modal 购买实例 begin-->
<div class="modal fade" id="myModalbuyCase">
	<div class="modal-dialog">
	    <div class="modal-content">
	      	<div class="modal-header">
	        	<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
	        	<h4 class="modal-title">数据库版本升级</h4>
	        </div>
	        <div class="modal-body overHidden">
	        	<div class="col-sm-12">
					 	<p>您目前的版本为: MySQL5.5</p>
				</div>
		        <form class="form-horizontal" role="form">
					<div class="form-group">
					    <span for="inputEmail3" class="col-sm-3 control-label text-muted">将升级至:</span>
					    <div class="col-sm-4">
					      <select class="form-control inline-block margin-left-1"><option value="0" selected="selected">MySQL5.6</option></select>
					    </div>				
					</div>
				</form>
				<div class="col-sm-12">
					 	<p class="text-danger">注:购买只读实例必须将实例升级至MySQL5.6；
     建议先购买MySQL5.6实例测试兼容性后再升级版本</p>
				</div>
				<div class="col-sm-12">
					<a href="#" target="_blank">只读实例为什么必须用MySQL5.6版本&gt;&gt;</a>
					<a class="margin-left-5" href="#" target="_blank">MySQL5.6版本特性&gt;&gt;</a>
				</div>
				
	        </div>
	        <div class="modal-footer">
		        <button type="button" class="btn btn-primary">确定</button>
		        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
	        </div>
	    </div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
</div><!-- /.modal -->
<!--modal 购买实例 end  -->

<!-- modal  迁移可用区  begin-->
<div class="modal fade" id="myModalMoveAvaiable">
	<div class="modal-dialog">
	    <div class="modal-content">
	      	<div class="modal-header">
	        	<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
	        	<h4 class="modal-title">将实例迁移至其他可用区</h4>
	        </div>
	        <div class="modal-body overHidden">
	        	<div class="col-sm-12">
				 	<p><span class="text-muted">您在北京节点的实例:</span><span>rdsenn6ryenn6ry</span></p>
				 	<p><span class="text-muted">当前的可用区:</span><span>可用区A</span></p>
				 	<p><span class="text-danger">该实例所在的物理位置仅有一个可用区，不支持迁移</span></p>
				</div>
	        </div>
	        <div class="modal-footer">
	        	<div class="col-sm-6 pull-left">
					<p class="text-left"><span class="text-danger">可用区迁移过程中会有30s的闪断</span></p>
					<p class="text-left"><span class="text-danger">应用程序需要具有数据库重连机制</span></p>
				</div>
		        <button type="button" class="btn btn-primary disabled">确定</button>
		        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
	        </div>
	    </div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
</div><!-- /.modal -->
<!--modal 迁移可用区 end  -->

<!-- modal 内外网切换 begin-->
<div class="modal fade" id="myModalNetchange">
	<div class="modal-dialog">
	    <div class="modal-content">
	      	<div class="modal-header">
	        	<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
	        	<h4 class="modal-title">连接地址修改（切换到外网）</h4>
	        </div>
	        <div class="modal-body overHidden">
		        <form class="form-horizontal" role="form">
					<div class="form-group">
					    <label for="inputEmail3" class="col-sm-3 control-label text-muted">*新的连接地址：</label>
					    <div class="col-sm-4">
					      <input type="text" class="form-control" placeholder="***" required="required">
					    </div>
					    <label class="control-label">.mysql.rds.aliyuncs.com</label>
					    <div class="col-sm-9 col-sm-offset-3"><span class="text-muted">由字母，数字组成，小写字母开头，8-30个字符</span></div>					
					</div>
					<div class="form-group">
					    <label for="inputPassword3" class="col-sm-3 control-label text-muted">端口号：</label>
					    <div class="col-sm-3">
					      <input type="number" class="form-control" placeholder="3306" min="3200" max="3999" required="required">
					    </div>
					    <div class="col-sm-9 col-sm-offset-3"><span class="text-muted">端口范围：3200~3999</span></div>
					</div>	
				</form>
				<div class="col-sm-12">
					 	<p>1.切换内外网之后，请及时到<span class="text-danger">"安全控制=&gt;白名单设置"</span>页面更新配置，避免您的程序无法连接到RDS.</p>
					 	<p>2.需替换代码中的数据库连接地址，并重启应用程序且过去24小时可以切换20次，剩<span class="text-danger">20</span>次.</p>
				</div>
	        </div>
	        <div class="modal-footer">
		        <button type="button" class="btn btn-primary disabled">确定</button>
		        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
	        </div>
	    </div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
</div><!-- /.modal -->
<!--modal 内外网切换 end  -->

<!-- modal restart 实例  begin-->
<div class="modal fade" id="myModalCaseRestart">
	<div class="modal-dialog">
	    <div class="modal-content">
	      	<div class="modal-header">
	        	<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
	        	<h4 class="modal-title">重启实例</h4>
	        </div>
	        <div class="modal-body overHidden">
	        	<div class="row">
	        		<div class=" col-sm-1"><span class="text-size-32 glyphicon glyphicon-info-sign  text-danger"> </span></div>
	        		<div class="col-sm-11"><p class="pd-tb-10">您确定要立即重启此实例吗？</p></div>
	        	</div>     
	        </div>
	        <div class="modal-footer">
		        <button type="button" class="btn btn-primary">确定</button>
		        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
	        </div>
	    </div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
</div><!-- /.modal -->
<!--modal restart 实例 end  -->

<!-- modal 备份实例 begin-->
<div class="modal fade" id="myModalBackCase">
	<div class="modal-dialog">
	    <div class="modal-content">
	      	<div class="modal-header">
	        	<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
	        	<h4 class="modal-title">备份实例</h4>
	        </div>
	        <div class="modal-body overHidden">
		        <form class="form-horizontal" role="form">
					<div class="form-group">
					    <span for="inputEmail3" class="col-sm-3 control-label text-muted">选择备份方式：</span>
					    <div class="col-sm-4">
					      <select class="form-control inline-block margin-left-1"><option value="0" selected="selected">物理备份</option><option value="1">逻辑备份</option></select>
					    </div>				
					</div>
				</form>
				<div class="col-sm-12">
					 	<p>您确定要立即备份此实例吗？（备份任务将会在1分钟左右开始启动）</p>
					 	<p class="text-danger">注：逻辑备份是导出SQL语句，物理备份是直接备份数据库的物理文件。</p>
				</div>
	        </div>
	        <div class="modal-footer">
		        <button type="button" class="btn btn-primary">确定</button>
		        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
	        </div>
	    </div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
</div><!-- /.modal -->
<!--modal 备份实例 end  -->

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