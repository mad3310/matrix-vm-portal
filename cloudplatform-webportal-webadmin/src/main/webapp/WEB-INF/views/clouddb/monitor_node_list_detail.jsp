<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="page-content-area">
	<div id="page-header-id" class="page-header">
		<h1>
			<a href="${ctx}/list/mcluster">返回Container集群列表<i class="ace-icon fa fa-reply icon-only"></i></a>	
		</h1>
	</div>
	<input class="hidden" value="${ip}" name="vipaddr" id="vipaddr" type="text" />
	<!-- /.page-header -->
	<div class="row">
		<div class="col-sm-12 col-md-12">
			<div  class="col-sm-6 col-md-9" style="margin-top: 10px;">
			<table class="table table-bordered table-striped table-hover">
			   	<thead>
			       	<tr>
						<th colspan="4" style="text-align: center">db监控信息</th>
					</tr>
				</thead>
			   <tbody id="db_detail_table">
					<tr class="logWarning">
						<td rowspan="5" style="width: 20%;">log_warning</td>
						<input type="text" id="logWarnFailNum" class="hidden" />
					</tr>
					<tr class="logHealth">
						<td rowspan="5" style="width: 20%;">log_health</td>
						<input type="text" id="logHealthFailNum" class="hidden" />
					</tr>
					<tr class="logError">
						<td rowspan="5" style="width: 20%;">log_error</td>
						<input type="text" id="logErrFailNum" class="hidden" />						
					</tr>
					<tr class="nodeStart">
						<td rowspan="5" style="width: 20%;">started</td>
						<input type="text" id="startFailNum" class="hidden" />
					</tr>
				</tbody>
			</table>
			</div>
		</div>
	</div>
</div>

<script src="${ctx}/static/scripts/pagejs/monitor_node_list_detail.js"></script>