<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="page-content-area">
	<div id="page-header-id" class="page-header">
		<h1>
			<a href="${ctx}/list/mcluster/monitor/3">返回db监控列表<i class="ace-icon fa fa-reply icon-only"></i></a>	
		</h1>
	</div>
	<input class="hidden" value="${ip}" name="vipaddr" id="vipaddr" type="text" />
	<!-- /.page-header -->
	<div class="row">
		<div class="col-sm-12 col-md-12">
			<div style="margin-top: 10px;">
			<table class="table table-bordered table-striped table-hover">
			   	<thead>
			       	<tr>
						<th colspan="6" style="text-align: center">db监控信息</th>
					</tr>
				</thead>
			    <tbody id="db_detail_table">
			    </tbody>
			</table>
			</div>
		</div>
	</div>
</div>

<script src="${ctx}/static/scripts/pagejs/monitor_db_list_detail.js"></script>