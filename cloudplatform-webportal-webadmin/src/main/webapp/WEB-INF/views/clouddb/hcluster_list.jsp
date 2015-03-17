<%@ page language="java" pageEncoding="UTF-8"%>
<!-- /section:settings.box -->
<div class="page-content-area">
	<div class="page-header">
		<!-- <h3> 物理机集群列表 </h3> -->
		<div class="input-group pull-right">
			<form class="form-inline">
				<div class="form-group">
					<input type="text" class="form-control" id="hclusterName"
						placeholder="物理机集群名称">
				</div>
				<div class="form-group">
					<input type="text" class="form-control" id="hclusterIndex"
						placeholder="编号">
				</div>
				<!-- <div class="form-group">
					<input type="date" class="form-control" id="PhyMechineDate"
						placeholder="创建时间">
				</div> -->
				<div class="form-group">
					<select class="form-control" id="hclusterStatus">
					    <option value="">请选择状态</option>
					</select>
				</div>
				<button class="btn btn-sm btn-primary btn-search" id="hclusterSearch" type="button"><i class="ace-icon fa fa-search"></i>搜索</button>
				<button class="btn btn-sm " type="button" id="hclusterSearchClear">清空</button>
			</form>
		</div>
	</div>
	<!-- /.page-header -->
    <!-- <div class="modal fade" id="hclusteradvancedSearch">
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
    						<lable class="col-sm-4 control-label" for="PhysicalMechine"><b>物理集群名称</b></lable>
    						<div class="col-sm-7">
    							<input type="text" class="form-control" id="PhysicalMechine" placeholder="物理集群名称">
    						</div>
    						<label class="control-label"><i class="ace-icon fa fa-info-circle blue bigger-125"></i></label>
    					</div>
    					<div class="form-group">
    						<lable class="col-sm-4 control-label" for="PhyMechineNum"><b>编号</b></lable>
    						<div class="col-sm-7">
    							<input type="text" class="form-control" id="PhyMechineNum" placeholder="编号">
    						</div>
    						<label class="control-label"><i class="ace-icon fa fa-tag blue bigger-125"></i></label>
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
    								<option value="">运行中</option>
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
				<h5 class="widget-title">物理机集群列表</h5>
				<div class="widget-toolbar no-border">
					<button class="btn btn-white btn-primary btn-xs" data-toggle="modal" data-target="#create-hcluster-modal">
						<i class="ace-icont fa fa-plus"></i>
						 创建物理机集群
					</button>
				</div>
			</div>
			<div class="widget-body">
				<div class="widget-main no-padding">
					<table id="hcluster_list" class="table table-striped table-bordered table-hover">
						<thead>
							<tr>
								<th class="center">
									<label class="position-relative">
										<input type="checkbox" class="ace" />
										<span class="lbl"></span>
									</label>
								</th>
								<th width="19%">物理机集群名称</th>
								<th width="19%">编号</th>
								<th width="19%">创建时间 </th>
								<th width="19%" class="hidden-480">当前状态</th>
								<th width="19%">操作</th>
							</tr>
						</thead>
						<tbody id="tby">
						</tbody>
					</table>
				</div>
			</div>
		</div>
		<div class="col-xs-3">
			<small><font color="gray">*注：点击编号可查看详情.</font></small>
		</div>
		<div id="pageControlBar" class="col-xs-6">
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
		
		<div class="modal fade" id="create-hcluster-modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="margin-top:157px">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
            				<button type="button" class="close" data-dismiss="modal">
            					<span aria-hidden="true"><i class="ace-icon fa fa-times-circle"></i></span>
            					<span class="sr-only">关闭</span>
            				</button>
            				<h4 class="modal-title">创建物理机集群 </h4>
            		</div>
					<form id="create-hcluster-form" name="create-hcluster-form" class="form-horizontal" role="form">
						<div class="modal-body">            				
            				<div class="form-group">
								<label class="col-sm-4 control-label" for="hcluster_name">物理机集群名称</label>
								<div class="col-sm-6">
									<input class="form-control" name="hclusterNameAlias" id="hclusterNameAlias" type="text" />
								</div>
								<label class="control-label">
									<a name="popoverHelp" rel="popover" data-container="body" data-toggle="popover" data-placement="right" data-trigger='hover' data-content="集群名称应能概括此集群的信息，可用汉字!" style="cursor:pointer; text-decoration:none;">
										<i class="ace-icon fa fa-question-circle blue bigger-125"></i>
									</a>
								</label>
							</div>
							<div class="form-group">
								<label class="col-sm-4 control-label" for="hcluster_name">编号</label>
								<div class="col-sm-6">
									<input class="form-control" name="hclusterName" id="hclusterName" type="text" />
								</div>
								<label class="control-label">
									<a name="popoverHelp" rel="popover" data-container="body" data-toggle="popover" data-placement="right" data-trigger='hover' data-content="请输入字母数字或'_'." style="cursor:pointer; text-decoration:none;">
										<i class="ace-icon fa fa-question-circle blue bigger-125"></i>
									</a>
								</label>
							</div>
            			</div>
					<!-- <div class="col-xs-12">
						<h4 class="lighter">
							<a href="#modal-wizard" data-toggle="modal" class="blue">创建物理机集群 </a>
						</h4>
						<div class="widget-box">
							<div class="widget-body">
								<div class="widget-main">
									<div class="form-group">
										<label class="col-sm-4 control-label" for="hcluster_name">物理机集群名称</label>
										<div class="col-sm-6">
											<input class="form-control" name="hclusterNameAlias" id="hclusterNameAlias" type="text" />
										</div>
										<label class="control-label">
											<a name="popoverHelp" rel="popover" data-container="body" data-toggle="popover" data-placement="right" data-trigger='hover' data-content="集群名称应能概括此集群的信息，可用汉字!" style="cursor:pointer; text-decoration:none;">
												<i class="ace-icon fa fa-question-circle blue bigger-125"></i>
											</a>
										</label>
									</div>
									<div class="form-group">
										<label class="col-sm-4 control-label" for="hcluster_name">编号</label>
										<div class="col-sm-6">
											<input class="form-control" name="hclusterName" id="hclusterName" type="text" />
										</div>
										<label class="control-label">
											<a name="popoverHelp" rel="popover" data-container="body" data-toggle="popover" data-placement="right" data-trigger='hover' data-content="请输入字母数字或'_'." style="cursor:pointer; text-decoration:none;">
												<i class="ace-icon fa fa-question-circle blue bigger-125"></i>
											</a>
										</label>
									</div>
								</div>
							</div>
						</div>
					</div> -->
					<div class="modal-footer">
						<button type="button" class="btn btn-sm btn-default" data-dismiss="modal">关闭</button>
						<button id="create-hcluster-botton" type="button" class="btn btn-sm disabled btn-primary" onclick="createHcluster()">创建</button>
					</div>
				</form>
				</div>
			</div>
		</div>
		<div id="dialog-confirm" class="hide">
			<div id="dialog-confirm-content" class="alert alert-info bigger-110"></div>
			<div class="space-6"></div>
			<p id="dialog-confirm-question" class="bigger-110 bolder center grey"></p>
		</div>
	</div>
</div>
<!-- /.page-content-area -->

<link rel="stylesheet" href="${ctx}/static/styles/bootstrap/bootstrapValidator.min.css" />
<script src="${ctx}/static/scripts/bootstrap/bootstrapValidator.min.js"></script>

<script src="${ctx}/static/ace/js/jquery.dataTables.min.js"></script>
<script src="${ctx}/static/ace/js/jquery.dataTables.bootstrap.js"></script>

<script src="${ctx}/static/scripts/pagejs/hcluster_list.js"></script>