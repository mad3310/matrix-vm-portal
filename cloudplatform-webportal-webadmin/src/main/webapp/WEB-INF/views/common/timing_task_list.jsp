<%@ page language="java" pageEncoding="UTF-8"%>
<!-- /section:settings.box -->
<div class="page-content-area">
	<div class="page-header">
		<!-- <h3>Container集群列表</h3> -->
		<div class="input-group pull-right">
			<form class="form-inline">
				<!-- <div class="form-group">
					<input type="text" class="form-control" id="containerType"
						placeholder="类型">
				</div> -->
				<div class="form-group">
					<input type="text" class="form-control" id="timingTaskName"
						placeholder="任务名称">
				</div>
				<div class="form-group">
					<input type="text" class="form-control" id="timingTaskDescn"
						placeholder="任务描述">
				</div>
				<button class="btn btn-sm btn-primary btn-search" id="mclusterSearch" type="button">
					<i class="ace-icon fa fa-search"></i>搜索
				</button>
				<button class="btn btn-sm" type="button" id="mclusterClearSearch">清空</button>
			</form>
		</div>
	</div>
	<!-- /.page-header -->
	<div class="row">
		<div class="widget-box widget-color-blue ui-sortable-handle col-xs-12">
			<div class="widget-header">
				<h5 class="widget-title">定时任务列表</h5>
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
								<th width="16%">名称</th>
								<th width="16%">任务uuid</th>
								<th width="15%">调度方式</th>
								<th width="15%">执行接口</th>
								<th width="15%">接口调用方式</th>
								<th width="15%" >调度规则</th>
								<th width="15%">描述</th>
							</tr>
						</thead>
						<tbody id="tby">
						</tbody>
					</table>
				</div>
			</div>
		</div>
		<div id="pageControlBar" class="col-xs-12">
			<input type="hidden" id="totalPage_input" />
			<ul class="pager">
				<li><a href="javascript:void(0);" id="firstPage">&laquo&nbsp;首页</a></li>
				<li><a href="javascript:void(0);" id="prevPage" >上一页</a></li>
				<li><a href="javascript:void(0);" id="nextPage">下一页</a></li>
				<li><a href="javascript:void(0);" id="lastPage">末页&nbsp;&raquo</a></li>
				<li><a>共<lable id="totalPage"></lable>页</a></li>
				<li><a>第<lable id="currentPage"></lable>页</a></li>
				<li><a>共<lable id="totalRows"></lable>条记录</a></li>
			</ul>
		</div>
	</div>
</div>
<!-- /.page-content-area -->

<link rel="stylesheet" href="${ctx}/static/styles/bootstrap/bootstrapValidator.min.css" />
<script src="${ctx}/static/scripts/bootstrap/bootstrapValidator.min.js"></script>

<script src="${ctx}/static/ace/js/jquery.dataTables.min.js"></script>
<script src="${ctx}/static/ace/js/jquery.dataTables.bootstrap.js"></script>

<script src="${ctx}/static/scripts/pagejs/timing_task_list.js"></script>