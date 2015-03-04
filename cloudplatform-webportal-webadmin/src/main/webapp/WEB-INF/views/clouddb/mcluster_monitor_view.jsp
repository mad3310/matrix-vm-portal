<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="page-content-area">
	<div class="page-header">
		<h3> 
			container集群监控视图
		</h3>
	</div>
	<!-- /.page-header -->
	<div class="row">
		<div id="monitor-option" class="col-sm-12"> 
			<div class="clearfix form-actions">
				<form class="form-horizontal" role="form">
					<div class="form-group">
					   <label class="col-sm-2 control-label text-info no-padding-right" for="mclusterOption">选择Container集群</label>
						<div class="col-sm-4">
							<select class="chosen-select" id="mclusterOption" name="mclusterId" data-placeholder="请选择集群...">
							</select>
						</div>
						<label class="col-sm-2 control-label text-info">时间</label>
						<div class="col-sm-3">
							<select id="queryTime" name="queryTime" class="form-control">
								<option value="1">一小时</option>
								<option value="2">三小时</option>
								<option value="3">一天</option>
								<option value="4">一周</option>
							</select>
						</div>						
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label no-padding-right text-info" >监控点</label>
						<div class="col-sm-9">
							<select multiple="multiple" class="chosen-select form-control" name="monitorPoint" id="monitorPointOption" data-placeholder="空为显示所有.">
							</select>	
					 	</div>
					 	<div class="col-sm-1">
							<button id="refresh-data" type="button" onclick="refreshChartForSelect()" class="btn btn-sm btn-info">刷新</button>
						</div>	
					</div>						
				</form>			
			</div>
		<div id="monitor-view" class="row">
		</div>
		<!-- <div class="space-24"></div> -->
			<div id="monitor-view-demo" name="monitor-view" class="col-xs-12 col-sm-6 widget-container-col ui-sortable hide ui-sortable-disabled">
				<div class="widget-box transparent ui-sortable-handle">
					<div class="widget-header">
						<!-- <h4 class="widget-title lighter">监控图</h4> -->
						<div class="widget-toolbar">
							<a href="#" data-action="fullscreen" class="orange2" onclick="updateChartSize($(this))">
								<i class="ace-icon fa fa-expand"></i>
							</a>
							<a href="#" class="orange2" data-action="settings" onclick="changeDraggable($(this))">
								<input type="text" name="draggable" value="0" class="hidden" />
								<i class="ace-icon fa fa-thumb-tack" style="-webkit-transform:rotate(45deg);-moz-transform:rotate(45deg);-o-transform:rotate(45deg);"></i>
							</a>
						</div>
					</div>
					<div class="widget-body">
						<div class="widget-main">
							<div name="data-chart"></div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>


<script src="${ctx}/static/scripts/highcharts/highcharts.js"></script>
<%-- <script src="${ctx}/static/scripts/highcharts/themes/grid.js"></script> --%>
<script src="${ctx}/static/scripts/highcharts/themes/grid-light.js"></script>

<%-- <script src="${ctx}/static/scripts/highcharts/themes/dark-blue.js"></script> --%>
<script src="${ctx}/static/scripts/pagejs/mcluster_monitor_view.js"></script>


