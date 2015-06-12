<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="page-content-area">
	<input type="hidden" id="volName" name="volName"  value="${volName}" >
	<div id="page-header-id" class="page-header">
	</div>
	<!-- /.page-header -->
	<div class="row">
		<div class="widget-box transparent ui-sortable-handle">
			<div class="widget-header">
				<div class="widget-toolbar no-border pull-left">
					<ul id="db-detail-tabs" class="nav nav-tabs" id="myTab2">
						<li class="active">
							<a id="vol-space" data-toggle="tab" href="#vol-space-info">空间信息</a>
						</li>
						<li class="">
							<a id="vol-process" data-toggle="tab" href="#vol-process-info">进程信息</a>
						</li>
						<li class="">
							<a id="vol-config" data-toggle="tab" href="#vol-config-info">配置信息</a>
						</li>
						<li class="">
							<a id="vol-split-brain" data-toggle="tab" href="#vol-split-brain-info">裂脑文件日志</a>
						</li>
						<li class="">
							<a id="vol-heal" data-toggle="tab" href="#vol-heal-info">受损文件信息</a>
						</li>
					</ul>
				</div>
			</div>
			<div class="widget-body">
				<div class="widget-main padding-12 no-padding-left no-padding-right">
					<div class="tab-content padding-4">
						<div id="vol-space-info" class="tab-pane  active">
							<div class="widget-box widget-color-blue ui-sortable-handle col-xs-12 col-sm-12 col-md-10">
								<div class="widget-header">
									<h5 class="widget-title">${volName}</h5>
								</div>
								<div class="widget-body">
									<div class="widget-main no-padding">
										<table class="table table-bordered  table-striped table-hover" id="vol_status_info_table">
											<thead>
												<tr>
													<th>名称</th>
													<th>空间大小</th>
													<th>已用空间</th>
													<th>可用空间</th>
													<th>使用比例</th>
												</tr>
											</thead>
											<tbody id="space-tby">
											</tbody>
										</table>
									</div>
								</div>
							</div>
						</div>
						<div id="vol-process-info" class="tab-pane">
							<div class="widget-box widget-color-blue ui-sortable-handle col-xs-12 col-sm-12 col-md-10">
								<div class="widget-header">
									<h5 class="widget-title">${volName}</h5>
								</div>
								<div class="widget-body">
									<div class="widget-main no-padding">
										<table class="table table-bordered  table-striped table-hover" id="vol_process_info_talbe">
											<thead>
												<tr>
													<th>主机名</th>
													<th>路径</th>
													<th>节点id</th>
													<th>进程id</th>
													<th>端口</th>
													<th>状态</th>
												</tr>
											</thead>
											<tbody id="process-tby">
											</tbody>
										</table>
									</div>
								</div>
							</div>
						</div>
						<div id="vol-config-info" class="tab-pane">
							<div class="widget-box widget-color-blue ui-sortable-handle col-xs-12 col-sm-12 col-md-6">
								<div class="widget-header">
									<h5 class="widget-title">${volName}</h5>
								</div>
								<div class="widget-body">
									<div class="widget-main no-padding">
										<table class="table table-bordered  table-striped table-hover" id="vol_process_info_talbe">
											<thead>
												<tr>
													<th>配置项</th>
													<th>值</th>
												</tr>
											</thead>
											<tbody id="config-tby">
											</tbody>
										</table>
									</div>
								</div>
							</div>
						</div>
						<div id="vol-split-brain-info" class="tab-pane">
							<div class="widget-box widget-color-blue ui-sortable-handle col-xs-12 col-sm-12 col-md-10">
								<div class="widget-header widget-header-flat">
									<h4 class="smaller">
										<i class="icon-code"></i>裂脑文件日志
									</h4>
								</div>
								<div class="widget-body">
									<div class="widget-main">
										<pre class="prettyprint linenums" id="split-brain-log">
										</pre>
									</div>
								</div>
							</div>
						</div>
						<div id="vol-heal-info" class="tab-pane">
							<div class="widget-box widget-color-blue ui-sortable-handle col-xs-12 col-sm-12 col-md-10">
								<div class="widget-header widget-header-flat">
									<h4 class="smaller">
										<i class="icon-code"></i>受损文件信息
									</h4>
								</div>
								<div class="widget-body">
									<div class="widget-main">
										<pre class="prettyprint linenums" id="heal-info">
										</pre>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<link rel="stylesheet" href="${ctx}/static/styles/bootstrap/bootstrapValidator.min.css" />
<script src="${ctx}/static/scripts/bootstrap/bootstrapValidator.min.js"></script>

<script src="${ctx}/static/scripts/pagejs/gfs_volume_detail.js"></script>