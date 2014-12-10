<%@ page language="java" pageEncoding="UTF-8"%>
<div class="page-content-area">
	<div class="page-header">
		<h3>数据库列表	</h3>
	    <div class="input-group pull-right">
		<form class="form-inline">
			<!-- <div class="form-group">
				<select class="form-control">
					<option value="0">请选择查询条件</option>
					<option value="1">按数据库名称查询</option>
					<option value="2">按所属物理集群查询</option>
					<option value="3">按当前状态查询</option>
				</select>
			</div> -->
			<div class="form-group">
				<input type="text" class="form-control" placeholder="请输入关键字">
			</div>
			<div class="form-group">
				<input type="date" class="form-control" placeholder="yyyy-MM-dd">
			</div>
			<button class="btn btn-sm btn-default" type="button"><i class="ace-icon fa fa-search"></i>搜索</button>
			<button class="btn btn-sm btn-info" type="button" id="dbadvancedSearch">高级搜索</button>
		</form>
	</div>	
	</div>
    <!-- /.page-header -->
    <div id="dbadvancedSearch-div" style="display:none;overflow:hidden;">
		<form class="form-horizontal" role="form">
            					<div class="form-group col-md-4 col-sm-6 col-xs-12">
            						<lable class="col-md-6 control-label" for="dbName"><b>数据库名称</b> <i class="ace-icon fa fa-database blue bigger-125"></i></lable>
            						<div class="col-md-6">
            							<input type="text" class="form-control" id="dbName" placeholder="数据库名称">
            						</div>
            					</div>
            					<div class="form-group col-md-4 col-sm-6 col-xs-12">
            						<lable class="col-md-6 control-label" for="dbMcluster"><b>所属Mcluster</b> <i class="ace-icon fa fa-info-circle blue bigger-125"></i></lable>
            						<div class="col-md-6">
            							<input type="text" class="form-control" id="dbMcluster" placeholder="所属Mcluster">
            						</div>
            					</div>
            					<div class="form-group col-md-4 col-sm-6 col-xs-12">
            						<lable class="col-md-6 control-label" for="dbPhyMcluster"><b>所属物理机集群</b> <i class="ace-icon fa fa-info-circle blue bigger-125"></i></lable>
            						<div class="col-md-6">
            							<input type="text" class="form-control" id="dbPhyMcluster" placeholder="所属物理机集群">
            						</div>
            					</div>
            					<div class="form-group col-md-4 col-sm-6 col-xs-12">
            						<lable class="col-md-6 control-label" for="dbuser"><b>所属用户</b> <i class="ace-icon fa fa-user blue bigger-125"></i></lable>
            						<div class="col-md-6">
            							<input type="text" class="form-control" id="dbuser" placeholder="所属用户">
            						</div>
            						
            					</div>
            					
            					<div class="form-group col-md-4 col-sm-6 col-xs-12">
            						<lable class="col-md-6 control-label" for="PhyMechineDate"><b>创建时间</b> <i class="ace-icon fa fa-calendar blue bigger-125"></i></lable>
            						<div class="col-md-6">
            							<input type="date" class="form-control" id="PhyMechineDate" placeholder="创建时间">
            						</div>
            						
            					</div>
            					<div class="form-group col-md-4 col-sm-6 col-xs-12">
            						<lable class="col-md-6 control-label" for="PhyMechineRunState"><b>运行状态</b> <i class="ace-icon fa fa-cog blue bigger-125"></i></lable>
            						<div class="col-md-6">
            							<select class="form-control" id="PhyMechineRunState">
            								<option value="">创建失败</option>
            								<option value="">未审核</option>
            								<option value="">。。。</option>
            							</select>
            						</div>
            					</div>
            					<div class="form-group">
    						<div class="col-sm-offset-2 col-sm-10">
    							<button class="btn btn-sm btn-info pull-right" type="button" style="margin-left:5px;"><i class="ace-icon fa fa-search"></i>搜索</button>
    							<button class="btn btn-sm btn-default pull-right" type="reset"><i class="ace-icon fa fa-refresh"></i>清空</button>
    							
    						</div>
    					</div>
            				</form>
	</div>
<script type="text/javascript">
		$(function(){
			bt_toggle('dbadvancedSearch');
		})
	</script>
            <!-- <div class="modal fade" id="dbadvancedSearch">
            	<div class="modal-dialog">
            		<div class="modal-content">
            			<div class="modal-header">
            				<button type="button" class="close" data-dismiss="modal">
            					<span aria-hidden="true"><i class="ace-icon fa fa-times-circle"></i></span>
            					<span class="sr-only">关闭</span>
            				</button>
            				<h4 class="modal-title">高级搜索</h4>
            			</div>
            			<div class="modal-body">
            				<form class="form-horizontal" role="form">
            					
            					<div class="form-group">
            						<lable class="col-sm-4 control-label" for="dbName"><b>数据库名称</b></lable>
            						<div class="col-sm-7">
            							<input type="text" class="form-control" id="dbName" placeholder="数据库名称">
            						</div>
            						<label class="control-label"><i class="ace-icon fa fa-database blue bigger-125"></i></label>
            					</div>
            					<div class="form-group">
            						<lable class="col-sm-4 control-label" for="dbMcluster"><b>所属Mcluster</b></lable>
            						<div class="col-sm-7">
            							<input type="text" class="form-control" id="dbMcluster" placeholder="所属Mcluster">
            						</div>
            						<label class="control-label"><i class="ace-icon fa fa-info-circle blue bigger-125"></i></label>
            					</div>
            					<div class="form-group">
            						<lable class="col-sm-4 control-label" for="dbPhyMcluster"><b>所属物理机集群</b></lable>
            						<div class="col-sm-7">
            							<input type="text" class="form-control" id="dbPhyMcluster" placeholder="所属物理机集群">
            						</div>
            						<label class="control-label"><i class="ace-icon fa fa-info-circle blue bigger-125"></i></label>
            					</div>
            					<div class="form-group">
            						<lable class="col-sm-4 control-label" for="dbuser"><b>所属用户</b></lable>
            						<div class="col-sm-7">
            							<input type="text" class="form-control" id="dbuser" placeholder="所属用户">
            						</div>
            						<label class="control-label"><i class="ace-icon fa fa-user blue bigger-125"></i></label>
            					</div>
            					
            					<div class="form-group">
            						<lable class="col-sm-4 control-label" for="PhyMechineDate"><b>创建时间</b></lable>
            						<div class="col-sm-7">
            							<input type="date" class="form-control" id="PhyMechineDate" placeholder="创建时间">
            						</div>
            						<label class="control-label"><i class="ace-icon fa fa-calendar blue bigger-125"></i></label>
            					</div>
            					<div class="form-group">
            						<lable class="col-sm-4 control-label" for="PhyMechineRunState"><b>运行状态</b></lable>
            						<div class="col-sm-7">
            							<select class="form-control" id="PhyMechineRunState">
            								<option value="">创建失败</option>
            								<option value="">未审核</option>
            								<option value="">。。。</option>
            							</select>
            						</div>
            						<label class="control-label"><i class="ace-icon fa fa-cog blue bigger-125"></i></label>
            					</div>
            				</form>
            			</div>
            			<div class="modal-footer">
            			<button type="button" class="btn btn-sm btn-default" data-dismiss="modal">取 消 </button>
            			<button type="button" class="btn btn-sm btn-info">搜索</button>
            			</div>
            		</div>
            	</div>
            </div> -->
	<div class="row">
		<div class="widget-box widget-color-blue ui-sortable-handle col-xs-12">
			<div class="widget-header">
				<h5 class="widget-title">数据库列表</h5>
			</div>
			<div class="widget-body">
				<div class="widget-main no-padding">
					<table id="mcluster_list" class="table table-striped table-bordered table-hover">
					<thead>
						<tr>
							<th class="center">
								<label class="position-relative">
									<input type="checkbox" class="ace" />
									<span class="lbl"></span>
								</label>
							</th>
							<th>数据库名称</th>
							<th>所属Mcluster</th>
							<th>所属物理机集群</th>
							<th>所属用户</th>
							<th>
								创建时间 
							</th>
							<th class="hidden-480">当前状态</th>
							<!-- <th></th> -->
						</tr>
					</thead>
						<tbody id="tby">							
						</tbody>
					</table>
				</div>
			</div>
		</div>
		<div id="pageControlBar">
			<input type="hidden" id="totalPage_input" />
			<ul class="pager">
				<li><a href="javascript:void(0);" id="firstPage">&laquo首页</a></li>
				<li><a href="javascript:void(0);" id="prevPage">上一页</a></li>
				<li><a href="javascript:void(0);" id="nextPage">下一页</a></li>
				<li><a href="javascript:void(0);" id="lastPage">末页&raquo</a></li>
	
				<li><a>共<lable id="totalPage"></lable>页</a>
				</li>
				<li><a>第<lable id="currentPage"></lable>页</a>
				</li>
				<li><a>共<lable id="totalRows"></lable>条记录</a>
				</li>
			</ul>
		</div>
		<div class="modal fade bs-example-modal-lg"  id="create-mcluster-status-modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		  <div class="modal-dialog modal-lg">
		    <div class="modal-content">
		      <div class="modal-header">
		        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
		        <h4 class="modal-title" id="buildStatusHeader">
		        	<i class="ace-icon fa fa-spinner fa-spin green bigger-125"></i>
		        	创建中...
		        </h4>
		      </div>
		      <div class="modal-body">
		        <table id="mcluster_list" class="table">
						<thead>
							<tr class="info">
								<th width="3%">#</th>
								<th width="18%">操作</th>
								<th width="15%">开始时间</th>
								<th width="15%">结束时间</th>
								<th>信息</th>
								<th width="10%">结果  </th>
							</tr>
						</thead>
						<tbody id="build_status_tby">
						</tbody>
					</table>
		      </div>
		    </div><!-- /.modal-content -->
		  </div><!-- /.modal-dialog -->
		</div><!-- /.modal -->
	</div>
</div>
<!-- /.page-content-area -->

<script src="${ctx}/static/scripts/pagejs/db_list.js"></script>