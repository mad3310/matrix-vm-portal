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
				<div class="widget-toolbar no-border">
					<button class="btn btn-white btn-primary btn-xs" data-toggle="modal" data-target="#add-timing-task-modal">
						<i class="ace-icont fa fa-plus"></i>
						 添加定时任务
					</button>
				</div>
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
								<th>名称</th>
								<th>任务uuid</th>
								<th>调度方式</th>
								<th>执行接口</th>
								<th>接口调用方式</th>
								<th>调度规则</th>
								<th>描述</th>
								<th>操作</th>
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
	<div class="modal fade" id="add-timing-task-modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="margin-top:157px">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
            				<button type="button" class="close" data-dismiss="modal">
            					<span aria-hidden="true"><i class="ace-icon fa fa-times-circle"></i></span>
            					<span class="sr-only">关闭</span>
            				</button>
            				<h4 class="modal-title">添加定时任务</h4>
            		</div>
					<form id="add-timing-task-form" name="add-timing-task-form" class="form-horizontal" role="form">
					<div class="modal-body">            				
            					<div class="form-group">
										<label class="col-sm-4 control-label" for="mcluster_name">名称</label>
										<div class="col-sm-6">
											<input class="form-control" name="addTimingTaskName" id="addTimingTaskName" type="text" />
										</div>
										<label class="control-label">
											<a name="popoverHelp" rel="popover" data-container="body" data-toggle="popover" data-placement="right" data-trigger='hover' data-content="请输入字母数字或'_'." style="cursor:pointer; text-decoration:none;">
												<i class="ace-icon fa fa-question-circle blue bigger-125"></i>
											</a>
										</label>
									</div>
									<div class="form-group">
										<label class="col-sm-4 control-label" for="hcluster">调度方式</label>
										<div class="col-sm-3">
											<select class="form-control" name="timingTaskExecType" id="timingTaskExecType">
												<option>PYTHON</option>
											</select>
										</div>
										<label class="control-label" for="hcluster">
											<a id="hclusterHelp" name="popoverHelp" rel="popover" data-container="body" data-toggle="popover" data-placement="right" data-trigger='hover' data-content="选择负责调度的选项" style="cursor:pointer; text-decoration:none;">
												<i class="ace-icon fa fa-question-circle blue bigger-125"></i>
											</a>
										</label>
									</div>
									<div class="form-group">
										<label class="col-sm-4 control-label" for="hcluster">执行接口</label>
										<div class="col-sm-6">
											<input class="form-control" name="timingTaskUrl" id="timingTaskUrl" type="text" />
										</div>
										<label class="control-label" for="hcluster">
											<a id="hclusterHelp" name="popoverHelp" rel="popover" data-container="body" data-toggle="popover" data-placement="right" data-trigger='hover' data-content="需要定时调用url" style="cursor:pointer; text-decoration:none;">
												<i class="ace-icon fa fa-question-circle blue bigger-125"></i>
											</a>
										</label>
									</div>
									<div class="form-group">
										<label class="col-sm-4 control-label" for="hcluster">接口调用方式</label>
										<div class="col-sm-3">
											<select class="form-control" name="httpMethod" id="httpMethod">
												<option>get</option>
												<option>post</option>
												<option>delete</option>
												<option>put</option>
												<option>input</option>
											</select>
										</div>
										<label class="control-label" for="hcluster">
											<a id="hclusterHelp" name="popoverHelp" rel="popover" data-container="body" data-toggle="popover" data-placement="right" data-trigger='hover' data-content="url调用方式" style="cursor:pointer; text-decoration:none;">
												<i class="ace-icon fa fa-question-circle blue bigger-125"></i>
											</a>
										</label>
									</div>
									<div class="form-group">
										<label class="col-sm-4 control-label" for="hcluster">调度规则</label>
										<div class="col-sm-6 timing-default">
											<button class="btn btn-xs btn-primary" type="button" id="timing-task-cron-btn">定点执行</button>
											<button class="btn btn-xs btn-default" type="button" id="timing-task-interval-btn">周期执行</button>
											<div class="timing-set" id="timing-task-btn-input">
												<span id="timing-task-span-start">每</span>
												<input class="timing-input" type="text" id="timing-hour" name="timingHour"/><span>时</span>
												<input class="timing-input"  type="text" id="timing-min" name="timingMin"/><span>分</span>
												<input class="timing-input"  type="text" id="timing-second" name="timingSecond"/><span>秒</span>
												<span id="timing-task-span-end">时运行</span>
											</div>
										</div>
										
										<label class="control-label" for="hcluster">
											<a id="hclusterHelp" name="popoverHelp" rel="popover" data-container="body" data-toggle="popover" data-placement="right" data-trigger='hover' data-content="url调用规则,如：（1）每天0时0分0秒执行一次接口，（2）每隔0时0分5秒执行一次接口" style="cursor:pointer; text-decoration:none;">
												<i class="ace-icon fa fa-question-circle blue bigger-125"></i>
											</a>
										</label>
									</div>
									<div class="form-group">
										<label class="col-sm-4 control-label" for="hcluster">任务描述</label>
										<div class="col-sm-6">
											<textarea class="form-control" name="addTimingTaskDescn" id="addTimingTaskDescn" type="text" ></textarea>
										</div>
										<label class="control-label" for="hcluster">
											<a id="hclusterHelp" name="popoverHelp" rel="popover" data-container="body" data-toggle="popover" data-placement="right" data-trigger='hover' data-content="请保证您的应用与数据库在同一地域,以保证连接速度." style="cursor:pointer; text-decoration:none;">
												<i class="ace-icon fa fa-question-circle blue bigger-125"></i>
											</a>
										</label>
									</div>
            			</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-sm btn-default" data-dismiss="modal">关闭</button>
						<button id="add-timing-task-botton" type="submit" class="btn btn-sm btn-primary">创建</button>
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
<!-- /.page-content-area -->

<link rel="stylesheet" href="${ctx}/static/styles/bootstrap/bootstrapValidator.min.css" />
<script src="${ctx}/static/scripts/bootstrap/bootstrapValidator.min.js"></script>

<script src="${ctx}/static/ace/js/jquery.dataTables.min.js"></script>
<script src="${ctx}/static/ace/js/jquery.dataTables.bootstrap.js"></script>

<script src="${ctx}/static/scripts/pagejs/timing_task_list.js"></script>