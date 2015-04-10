<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="zh">
<%@include file='main.jsp' %>
<body>
<div class="navbar navbar-default" style="margin-bottom: 0px !important;">  
  <div class="container-fluid">
    <div class="navbar-header">
      <a class="navbar-brand" href="javascript:void(0)">实例数据管理</a>
    </div>
  </div>
</div>
<div class="container-fluid">
	<div class="col-sm-12 col-md-offset-1 col-md-3">
		<div role="tabpanel" style="margin-top:20px;">
		    <ul class="nav nav-tabs" role="tablist">
		      <li role="presentation" class="active"><a href="#get" id="get-tab" role="tab" data-toggle="tab" aria-controls="get" aria-expanded="true">查询(Get)</a></li>
		      <li role="presentation"><a href="#delete" role="tab" id="delete-tab" data-toggle="tab" aria-controls="delete">删除(Delete)</a></li>
		    </ul>
		    <div  class="tab-content" style="padding:15px;padding-top:20px;border:1px solid #ddd;border-top:none;">
		      <div role="tabpanel" class="tab-pane fade in active" id="get" aria-labelledby="get-tab">
		        <form class="form-horizontal">
		        	<div class="form-group">
		                <label class="col-xs-12 col-sm-3 col-md-3 control-label">键(Key)</label>
						<div class="col-xs-12 col-sm-7 col-md-7">
							<input class="form-control" type="text" placeholder="请输入Key值"/>
						</div>
            		</div>
            		<div class="form-group">
	            		<div class="col-xs-12 col-sm-offset-3 col-sm-8 col-md-offset-3 col-md-8">
	            			<a class="btn btn-info btn-md"><i class="fa fa-search"></i> 查询</a>
	            		</div>
		                
            		</div>
		        </form>
		      </div>
		      <div role="tabpanel" class="tab-pane fade" id="delete" aria-labelledby="delete-tab">
		        <form class="form-horizontal">
		        	<div class="form-group">
		                <label class="col-xs-12 col-sm-3 col-md-3 control-label">键(Key)</label>
						<div class="col-xs-12 col-sm-7 col-md-7">
							<input class="form-control" type="text" placeholder="请输入Key值"/>
						</div>
            		</div>
            		<div class="form-group">
	            		<div class="col-xs-12 col-sm-offset-3 col-sm-8 col-md-offset-3 col-md-8">
	            			<a class="btn btn-info btn-md"><i class="fa fa-trash"></i> 删除</a>
	            		</div>
		                
            		</div>
		        </form>
		      </div>
		    </div>
  		</div>
	</div>
	<div class="col-sm-12 col-md-6">
		<div role="tabpanel" style="margin-top:20px;padding:5px;">
		    <ul class="nav nav-tabs" role="tablist">
		      <li role="presentation" class="active"><a href="#add" id="add-tab" role="tab" data-toggle="tab" aria-controls="add" aria-expanded="true">添加(Add)</a></li>
		      <li role="presentation"><a href="#set" role="tab" id="set-tab" data-toggle="tab" aria-controls="set">修改(Set)</a></li>
		      <li role="presentation"><a href="#replace" role="tab" id="replace-tab" data-toggle="tab" aria-controls="replace">替换(Replace)</a></li>
		    </ul>
		    <div  class="tab-content" style="padding:15px;padding-top:20px;border:1px solid #ddd;border-top:none;">
		      <div role="tabpanel" class="tab-pane fade in active" id="add" aria-labelledby="add-tab">
		        <form class="form-horizontal">
		        	<div class="form-group">
		                <label class="col-sm-3 col-md-2 control-label">键(Key)</label>
						<div class="col-sm-7 col-md-7">
							<input class="form-control" type="text" placeholder="请输入Key值"/>
						</div>
            		</div>
            		<div class="form-group">
		                <label class="col-sm-3 col-md-2 control-label">值(Value)</label>
						<div class="col-sm-7 col-md-7">
							<textarea class="form-control"  placeholder="请输入Key值" rows="9" cols="120"></textarea>
						</div>
            		</div>
            		<div class="form-group">
		                <label class="col-sm-3 col-md-2 control-label">设置过期时长</label>
						<div class="col-sm-7 col-md-7">
							<input class="form-control" type="text" placeholder="请输入Key值"/>
						</div><label class="control-label">秒</label>
            		</div>
            		<div class="form-group">
	            		<div class="col-sm-offset-3 col-sm-8 col-md-offset-2 col-md-8">
	            			<a class="btn btn-info btn-md"><i class="fa fa-plus-circle"></i> 添加</a>
	            		</div>
		                
            		</div>
		        </form>
		      </div>
		      <div role="tabpanel" class="tab-pane fade" id="set" aria-labelledby="set-tab">
		        <form class="form-horizontal">
		        	<div class="form-group">
		                <label class="col-sm-3 col-md-2 control-label">键(Key)</label>
						<div class="col-sm-7 col-md-7">
							<input class="form-control" type="text" placeholder="请输入Key值"/>
						</div>
            		</div>
            		<div class="form-group">
		                <label class="col-sm-3 col-md-2 control-label">值(Value)</label>
						<div class="col-sm-7 col-md-7">
							<textarea class="form-control"  placeholder="请输入Key值" rows="9" cols="20"></textarea>
						</div>
            		</div>
            		<div class="form-group">
		                <label class="col-sm-3 col-md-2 control-label">设置过期时长</label>
						<div class="col-sm-7 col-md-7">
							<input class="form-control" type="text" placeholder="请输入Key值"/>
						</div><label class="control-label">秒</label>
            		</div>
            		<div class="form-group">
	            		<div class="col-sm-offset-3 col-sm-8 col-md-offset-2 col-md-8">
	            			<a class="btn btn-info btn-md"><i class="fa fa-edit"></i> 修改</a>
	            		</div>
		                
            		</div>
		        </form>
		      </div>
		      <div role="tabpanel" class="tab-pane fade" id="replace" aria-labelledby="replace-tab">
		        <form class="form-horizontal">
		        	<div class="form-group">
		                <label class="col-sm-3 col-md-2 control-label">键(Key)</label>
						<div class="col-sm-7 col-md-7">
							<input class="form-control" type="text" placeholder="请输入Key值"/>
						</div>
            		</div>
            		<div class="form-group">
		                <label class="col-sm-3 col-md-2 control-label">值(Value)</label>
						<div class="col-sm-7 col-md-7">
							<textarea class="form-control"  placeholder="请输入Key值" rows="9" cols="20"></textarea>
						</div>
            		</div>
            		<div class="form-group">
		                <label class="col-sm-3 col-md-2 control-label">设置过期时长</label>
						<div class="col-sm-7 col-md-7">
							<input class="form-control" type="text" placeholder="请输入Key值"/>
						</div><label class="control-label">秒</label>
            		</div>
            		<div class="form-group">
	            		<div class="col-sm-offset-3 col-sm-8 col-md-offset-2 col-md-8">
	            			<a class="btn btn-info btn-md"><i class="fa fa-exchange"></i> 替换</a>
	            		</div>
		                
            		</div>
		        </form>
		      </div>
		    </div>
  		</div>
	</div>
</div>
<script type="text/javascript" src="${ctx}/static/modules/seajs/2.3.0/sea.js"></script>
<script>
//Set configuration
seajs.config({
	base: "${ctx}/static/modules/",
	alias: {
		"jquery": "jquery/2.0.3/jquery.min.js",
		"bootstrap": "bootstrap/bootstrap/3.3.0/bootstrap.js"
	}
});

/*self define*/
</script>
</body>
</html>