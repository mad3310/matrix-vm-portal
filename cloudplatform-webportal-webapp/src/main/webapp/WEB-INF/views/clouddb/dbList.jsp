<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="zh">
<head>
	<meta charset="utf-8"/>
	<meta http-equiv="X-UA-compatible" content="IE=edge,chrome=1"/>
	<meta name="viewpoint" content="width=device-width,initial-scale=1"/>
	<!-- bootstrap css -->
	<link type="text/css" rel="stylesheet" href="../css/bootstrap.min.css"/>
	<!-- ui-css -->
	<link type="text/css" rel="stylesheet" href="../css/ui-css/common.css"/>
	<!-- js -->
	<script type="text/javascript" src="../js/jquery-2.0.3.min.js"></script>
	<script type="text/javascript" src="../js/bootstrap.min.js"></script>
	<script type="text/javascript" src="../js/ui-js/common.js"></script>
	<title>app-dashboard</title>
</head>
<body>
<!-- top bar begin -->
	<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
      <div class="container-fluid">
        <div class="navbar-header">
          <a class="navbar-brand" href="#"><img src="../img/cloud.ico"/></a>
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
	<div class="row main-header overHidden"> <!-- main-content-header begin -->
		<div class="col-sm-12 col-md-6">
			<div class="pull-left">
				<h5>
				<span>关系型数据库管理</span>
				<button class="btn btn-success btn-md" href="#">全部</button>
				<button class="btn btn-default btn-md" href="#">北京</button>
				</h5> 
			</div>
		</div>
		<div class="col-sm-12 col-md-6">
			<div class="pull-right">
				<h5 class="bdl-0">
				<button class="btn-default btn btn-md">刷新</button>
				<button class="btn-primary btn btn-md" >新建实例</button>
				</h5>
			</div>
		</div>
		<div class="col-sm-12 col-md-12">
			<div class="pull-left">
				<form class="form-inline" role="form">
					<div class="form-group">
						<select class="form-control">
							<option value="0" selected="selected">常规实例</option>
						</select>
					</div>
					<div class="form-group">
						<input type="text" class="form-control" size="48" placeholder="请输入实例名称或实例ID进行搜索">
					</div>
					<button type="submit" class="btn btn-default">搜索</button>
				</form>
			</div>
		</div>
	</div><!-- main-content-header end-->

	<div class="row"><!-- main-content-center-begin -->
		<div class="col-sm-12 col-md-12">
			<table class="table table-hover">
				<thead>
					<tr>
						<th width="10">
							<input type="checkbox">
						</th>
						<th class="padding-left-32">实例名称</th>
						<th>运行状态</th>
						<th>实例类型</th>
						<th>数据库类型</th>
						<th>可用区类型</th>
						<th>所在可用区</th>
						<th>付费类型</th>
						<th class="text-right">操作</th>
					</tr>
				</thead>
				<tbody>
				<tr>
					<td width="10">
						<input type="checkbox">
					</td>
					<td class="padding-left-32">
						<div>
							<div>
								<a href="baseInfo.html">rdsenn6ryenn6ry</a><br>
								<span text-length="26">rdsenn6ryenn6ry</span>
								<a class="btn btn-default btn-xs glyphicon glyphicon-pencil" href="#"></a>
							</div>
						</div>
					</td>
					<td>	
						<span tooltip="" class="text-success ng-scope">运行中</span>
					</td>
					<td><span>专享</span></td>
					<td><span>MySQL5.5</span></td>
					<td><span >单可用区</span></td>
					<td>北京<span>可用区A</span></td>
					<td><span><span>包月</span><span class="margin-left-1 text-success">303</span><span> 天后到期</span></span></td>
					<td class="text-right">
						<div>
							<a href="#">管理</a>
							<span class="text-explode">|</span>
							<a href="#" target="_blank">续费</a>
							<span class="text-explode">|</span>
							<a href="#">升级</a>
						</div>
					</td>
				</tr>
			</tbody>
			<tfoot>
				<tr>
					<td width="10">
						<input type="checkbox">
					</td>
					<td colspan=" 8">
						<div class="pull-left">
							<div pagination-info="paginationInfo">
								<div>
									<button class="btn btn-default" disabled="disabled">批量续费</button> 
								</div>
							</div>
						</div>
					</td>
				</tr>
			</tfoot>
		</table>
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
</html>