<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="zh">
<head>
	<meta charset="utf-8"/>
	<meta http-equiv="X-UA-compatible" content="IE=edge,chrome=1"/>
	<meta name="viewport" content="width=device-width,initial-scale=1"/>
	<!-- bootstrap css -->
	<link type="text/css" rel="stylesheet" href="${ctx}/static/css/bootstrap.min.css"/>
	<link type="text/css" rel="stylesheet" href="${ctx}/static/css/bootstrap-datetimepicker.min.css"/>
	<link type="text/css" rel="stylesheet" href="${ctx}/static/css/font-awesome.min.css" />
	<!-- ui-css -->
	<link type="text/css" rel="stylesheet" href="${ctx}/static/css/ui-css/common.css"/>
	<title>备份与恢复</title>
</head>
<body>
    <!-- 全局参数 start -->
	<input class="hidden" value="${dbId}" name="dbId" id="dbId" type="text" />
	<!-- Modal -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
        <h4 class="modal-title" id="myModalLabel">备份设置 </h4>
      </div>
      <div class="modal-body">
      	<form>
	        <div class="mt20 padding-left-32">
	    		<div class="form-group clearfix">
	    			<label class="col-sm-3 text-muted" style="font-weight:normal">备份周期:</label>
	    			<div class="col-sm-9">
	    				<div class="checkbox checkbox-inline" style="margin-top:0px;margin-left: 10px;">
	    					<label><input type="checkbox">星期一</label>
	    				</div>
	    				<div class="checkbox checkbox-inline">
	    					<label><input type="checkbox" checked="true">星期二</label>
	    				</div>
	    				<div class="checkbox checkbox-inline">
	    					<label><input type="checkbox">星期三</label>
	    				</div>
	    				<div class="checkbox checkbox-inline">
	    					<label><input type="checkbox" checked="true">星期四</label>
	    				</div>
	    				<div class="checkbox checkbox-inline">
	    					<label><input type="checkbox">星期五</label>
	    				</div>
	    				<div class="checkbox checkbox-inline">
	    					<label><input type="checkbox" checked="true">星期六</label>
	    				</div>
	    				<div class="checkbox checkbox-inline">
	    					<label><input type="checkbox">星期日</label>
	    				</div>
	    			</div>
	    		</div>
	    		<div class="form-group clearfix">
	    			<label class="col-sm-3 control-label text-muted" style="font-weight:normal">备份时间:</label>
	    			<div class="col-sm-7">
	    				<select class="form-control">
	    					<option value="0">00 - 01时</option>
	    					<option value="1">01 - 02时</option>
	    					<option value="2">02 - 03时</option>
	    					<option value="3">03 - 04时</option>
	    					<option value="4">04 - 05时</option>
	    					<option value="5">05 - 06时</option>
	    					<option value="6">06 - 07时</option>
	    					<option value="7">07 - 08时</option>
	    					<option value="8">08 - 09时</option>
	    					<option value="9">09 - 10时</option>
	    					<option value="10">10 - 11时</option>
	    					<option value="11">11 - 12时</option>
	    					<option value="12">12 - 13时</option>
	    					<option value="13">13 - 14时</option>
	    					<option value="14">14 - 15时</option>
	    					<option value="15">15 - 16时</option>
	    					<option value="16">16 - 17时</option>
	    					<option value="17">17 - 18时</option>
	    					<option value="18">18 - 19时</option>
	    					<option value="19">19 - 20时</option>
	    					<option value="20" selected="selected">20 - 21时</option>
	    					<option value="21">21 - 22时</option>
	    					<option value="22">22 - 23时</option>
	    					<option value="23">23 - 24时</option>
	    				</select>
	    			</div>
	    		</div>	
			</div>
		</form>
		<div class="alert alert-warning"> 注：由于备份任务会执行一段时间，备份开始时间会在您选择的备份时间段内执行。</div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-primary">确定</button>
        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
      </div>
    </div>
  </div>
</div><!-- modal end -->

<!-- Modal back_download-->
<div class="modal fade" id="back_download" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog" style="width:650px;">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
        <h4 class="modal-title" id="myModalLabel">实例备份文件下载</h4>
      </div>
      <div class="modal-body">
      	<div class="alert">
      		<p>目前下载备份文件暂时免费，以后下载备份文件将收取相应的流量费用</p>
      		<p>ECS与RDS地域相同时，ECS上使用内网下载地址，下载速度和安全性更高</p>
      		<p>请在linux系统上执行tar vizxf XXXXXX.tar.gz 进行解压</p>
      		<p><a href="#">如何使用备份文件恢复到自建数据库</a></p>
      	</div>
		<div class="alert alert-warning"> 请注意：如果您未安装Flash插件或版本过低，“复制下载地址”功能将无法使用。</div>
      </div>
      <div class="modal-footer">
      	<span class="alert alert-success pull-left" style="padding:6px !important;margin-right:10px;">复制内网下载地址成功。</span><!-- 此处文本可变 -->
      	<span class="pull-left clearfix">
      		<a class="btn btn-primary">我了解，要下载</a>
      		<a class="btn btn-default">复制下载地址</a>
      		<a class="btn btn-default">复制内网下载地址</a>
      		<a class="btn btn-default" data-dismiss="modal">取消</a>
      	</span>
      </div>
    </div>
  </div>
</div><!-- modal end -->

<!-- Modal back_creat-->
<div class="modal fade" id="back_creat" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
        <h4 class="modal-title" id="myModalLabel">创建临时实例</h4>
      </div>
      <div class="modal-body">
      	<div class="alert">
      		<h5><i class="fa fa-exclamation-circle text-warning text-size-32"></i> 请注意</h5>
      		<ol>
      			<li>创建临时实例，并不影响当前的生产实例，而是提供一个临时实例，供数据访问。</li>
      			<li>临时实例会继承备份点的帐号和密码、但沿用当前实例的网络类型。</li><li>同一时间仅可生成一个临时实例，若要创建新临时实例，需先删除当前临时实例。</li><li>临时实例48小时内有效。</li>
      		</ol>
      	</div>
      </div>
      <div class="modal-footer">
      	<span>
      		<a class="btn btn-primary">确定</a>
      		<a class="btn btn-default" data-dismiss="modal">取消</a>
      	</span>
      </div>
    </div>
  </div>
</div><!-- modal end -->
<!-- Modal recover_back-->
<div class="modal fade" id="recover_back" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog" style="width:650px;">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
        <h4 class="modal-title" id="myModalLabel">恢复备份的实例</h4>
      </div>
      <div class="modal-body">
		<div class="alert alert-warning"> 请注意：此功能将完全覆盖RDS实例的数据,请谨慎操作。</div>
		<form>
			<div class="form-group clearfix">
				<label class="col-sm-3 control-label text-muted" style="font-weight:normal">手机号:</label>
				<div class="col-sm-7">
					<div class="form-control-static pd0">131****7657 <a href="#" class="btn">获取验证码</a></div>
					
				</div>
			</div>
			<div class="form-group clearfix">
				<label class="col-sm-3 control-label text-muted" style="font-weight:normal">手机验证码：</label>
				<div class="col-sm-7">
					<input type="text" class="form-control col-sm-5">
				</div>
			</div>
		</form>
      </div>
      <div class="modal-footer">
      	<span>
      		<a class="btn btn-primary">确定</a>
      		<a class="btn btn-default" data-dismiss="modal">取消</a>
      	</span>
      </div>
    </div>
  </div>
</div><!-- modal end -->
<!-- Modal back_recover-->
<div class="modal fade" id="back_recover" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
        <h4 class="modal-title" id="myModalLabel">恢复备份的实例</h4>
      </div>
      <div class="modal-body">
      	<div class="alert overHidden">
      		<p class="col-sm-2"><i class="fa fa-exclamation-circle text-warning text-size-32"></i></p>
      		<ul class="col-sm-10">
      			<li>RDS回滚操作将覆盖数据，回滚后将无法恢复。</li>
      			<li>建议您创建临时实例，确定数据无误后将临时实例的数据迁移至RDS实例。</li>
      			<li>临时实例免费，提供读写权限，两天后系统将自动删除。</li>
      		</ul>
      	</div>
      </div>
      <div class="modal-footer">
      	<span>
      		<a class="btn btn-primary" data-dismiss="modal" data-toggle="modal" href="#back_creat">好，创建临时实例</a>
      		<a class="btn btn-warning" data-dismiss="modal" data-toggle="modal" href="#recover_back">不，覆盖RDS实例数据</a>
      	</span>
      </div>
    </div>
  </div>
</div><!-- modal end -->
<!-- Modal cleanBinlog-->
<div class="modal fade" id="cleanBinlog" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
        <h4 class="modal-title" id="myModalLabel">一键清除Binlog提示</h4>
      </div>
      <div class="modal-body">
      	<div class="alert overHidden">
      		<p class="col-sm-2"><i class="fa fa-exclamation-circle text-warning text-size-32"></i></p>
      		<ul class="col-sm-10" style="list-style: none;">
      			<li>将清除RDS上所有Binlog文件，请确认。</li>
      			<li>(Binlog全部清除完成需要些时间，请知晓。)</li>
      		</ul>
      	</div>
      </div>
      <div class="modal-footer">
      	<span>
      		<a class="btn btn-primary">确定</a>
      		<a class="btn btn-warning" data-dismiss="modal">取消</a>
      	</span>
      </div>
    </div>
  </div>
</div><!-- modal end -->
	<div class="panel-group m-pr10"  role="tablist" aria-multiselectable="true">
	    <div class="se-heading" id="headingOne" >
		        <div class="pull-left">
		        	<h5>
		        	备份与恢复
		        	<!-- <a id="back_a" data-toggle="tooltip" data-placement="top" title="在同一时间只能有一个临时实例，若需回滚到另一个临时实例，请先删除当前临时实例。">
						<i class="fa fa-question-circle text-muted"></i>
					</a> -->
			        </h5>
		        </div>
	        <div class="hidden-xs">				      
			    <div class="pull-right">
			       	<button id="refresh" disabled="true" class="btn btn-primary" data-toggle="modal" data-target="#cleanBinlog">
			       	一键清除Binlog
			        </button>
			    </div>
		    </div>
	    </div>
	    <ul class="nav nav-tabs" role="tablist" id="setab">
	    	<li id="backlist-tab" role="presentation" class="active">
	    		<a data-toggle="tab" href="#backlist">备份列表</a></li>
			<li id="backsetting-tab" role="presentation">
				<a data-toggle="tab" href="#backsetting">备份设置</a></li> 	
	    </ul>
		<!-- <div class="panel-body pd0" id="backlist"> -->
		<div class="tab-content">				
			<div id="backlist" role="tabpanel" class="tab tab-pane fade active in"  aria-labelledby="backlist-tab">
			<div class="row" style="margin-right:0;"> 
				<div class="input-group">
					<div class="time-range-unit-header form-group col-sm-12 col-md-12">
			    		<span class="time-range-title hidden-xs">选择时间范围：</span>
			    		<span class="time-range-title hidden-sm hidden-md hidden-lg">开始时间：</span>
			    		<div class="date-unit">
	            			<input type='text' class="form-control datetimepicker" id='startTime' />
			    		</div>
			    		<br class="hidden-sm hidden-md hidden-lg">
			    		<span class="date-step-span hidden-xs">至</span>
			    		<span class="time-range-title hidden-sm hidden-md hidden-lg">结束时间：</span>
			    		<div class="date-unit">
			    		     <input type='text' class="form-control datetimepicker" id='endTime' />
			    	    </div>
			    	    <!-- <select class="form-control margin-left-5 inline-block" style="width:160px">
			    	    	<option value="0" selected="selected">备份在OSS上的备份集</option>
			    	    </select>	 -->
			    	    <button id="bksearch" class="btn btn-primary btn-search">查询</button>
			    	</div>
				</div>
				<div class="col-sm-12 col-md-12">
			        <table class="table table-hover table-se " style="margin-top:10px;">
			        	<thead>
			        		<tr class="text-muted">
			        			<th>备份开始/结束时间</th>
			        			<th class="hidden-xs">备份策略</th>
			        			<th class="hidden-xs">备份大小</th>
			        			<th class="hidden-xs">备份方法</th>
			        			<th class="hidden-xs">备份类型</th>
			        			<th class="hidden-xs">工作模式</th>
			        			<th>状态</th>
			        			<th class="text-right">操作</th>
			        		</tr>
			        	</thead>
			        	<tbody id="backupTbody">
			        		<!-- <tr>
			        			<td>2015-01-06 20:49/2015-01-06 20:52</td>
			        			<td>实例备份</td>
			        			<td class="text-success">0.39M</td>
			        			<td>物理备份</td>
			        			<td>全量</td>
			        			<td>常规任务</td>
			        			<td>完成备份</td>
			        			<td class="text-right">
			        				<span class="inline-block"><a class="btn btn-xs" href="#back_download" data-toggle="modal">下载</a></span>
			        				<span class="inline-block"><a class="btn btn-xs" href="#back_creat" data-toggle="modal">创建临时实例</a></span>
			        				<span class="inline-block"><a class="btn btn-xs" href="#back_recover" data-toggle="modal">恢复</a></span>	
			        			</td>
			        		</tr> -->
			        	</tbody>
			        </table>
			       <!--  <div class="help-block hidden" id="noData">没有记录</div> -->
				    <div class="tfoot" id="paginatorBlock">
						<div class="pull-right">
							<div class="pagination-info">
								<span>共有<span id="totalRecords">3</span>条</span>， 
								<span>每页显示：<span id="recordsPerPage">30</span>条</span>&nbsp;
							    <ul id="paginator" class="pagination pagination-sm">
							    	<li class="">
							    		<a href="#">«</a>
							    	</li>
							    	<li class="disabled">
							    		<a href="#" title="Go to previous page">‹</a>
							    	</li>
							    	<li class="active">
							    		<a href="#">1</a>
							    	</li>
							    	<li class="">
							    		<a href="#">›</a>
							    	</li>
							    	<li class="">
							    		<a href="#">»</a>
							    	</li>
							    </ul>
							</div>
						</div>
					</div>   
				</div>
			</div>
				
		    	
		  	</div>			
			<div id="backsetting" role="tabpanel" class="tab-pane fade"
				aria-labelledby="backsetting-tab">
				<div class="pull-left col-sm-10 mt20 padding-left-32">
					<div class="form-group clearfix">
						<label class="col-sm-2 text-muted" style="font-weight: normal">保留天数:</label>
						<div class="col-sm-8">
							<div class="form-control-static pd0">15天</div>
						</div>
					</div>
					<div class="form-group clearfix">
						<label class="col-sm-2 control-label text-muted"
							style="font-weight: normal">备份周期:</label>
						<div class="col-sm-8">
							<div class="form-control-static pd0">星期一,星期二,星期三,星期四,星期五,星期六,星期日</div>
						</div>
					</div>
					<div class="form-group clearfix">
						<label class="col-sm-2 control-label text-muted"
							style="font-weight: normal">备份时间:</label>
						<div class="col-sm-8">
							<div class="form-control-static pd0">4:00 AM</div>
						</div>
					</div>
					<div class="form-group clearfix">
						<label class="col-sm-2 control-label text-muted"
							style="font-weight: normal">预计下次备份时间:</label>
						<div class="col-sm-8">
							<div id="backupTime" class="form-control-static pd0"></div>
						</div>
					</div>
				</div>
				<div class="pull-left col-sm-10">
					<div class="form-group">
						<label class="col-sm-2 control-label"></label>
						<div class="col-sm-8">
							<button type="button" class="btn btn-primary btn-sm"
								disabled="true" data-toggle='modal' data-target='#myModal'>修改</button>
							<p style="display: inline-block">(暂不提供)</p>
						</div>
					</div>
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
		"moment": "moment/2.9.0/moment-with-locales.js",
		"bootstrap": "bootstrap/bootstrap/3.3.0/bootstrap.js",
		"paginator": "bootstrap/paginator/bootstrap-paginator.js",
		"datetimepicker":"bootstrap/datetimepicker/bootstrap-datetimepicker.js"
	}
});
seajs.use("${ctx}/static/page-js/clouddb/backupRecover/main");
</script>
</html>
