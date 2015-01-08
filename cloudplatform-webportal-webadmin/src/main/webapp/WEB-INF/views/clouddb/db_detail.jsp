<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="page-content-area">
	<div id="page-header-id" class="page-header">
		<h3> 
			<a href="${ctx}/list/db">数据库列表</a>
			<small id="headerDbName"> 
				<i class="ace-icon fa fa-angle-double-right"></i> 
			</small>
		</h3>
	</div>
	<!-- /.page-header -->
	<input class="hidden" value="${dbId}" name="dbId" id="dbId" type="text" />
	<div class="row">
		<div class="widget-box transparent ui-sortable-handle">
			<div class="widget-header">
				<div class="widget-toolbar no-border pull-left">
					<ul id="db-detail-tabs" class="nav nav-tabs" id="myTab2">
						<li class="active">
							<a data-toggle="tab" href="#db-detail-info">数据库信息</a>
						</li>
						<li class="">
							<a data-toggle="tab" href="#db-detail-user-mgr">用户管理</a>
						</li>
					</ul>
				</div>
			</div>
			<div class="widget-body">
				<div class="widget-main padding-12 no-padding-left no-padding-right">
					<div class="tab-content padding-4">
						<div id="db-detail-info" class="tab-pane  active">
							<div id="db-detail-table" class="col-xs-6">
								<table class="table table-bordered table-striped table-hover" id="db_detail_table">
									<tr>
										<th width="50%">数据库名</th>
										<td width="50%" id="db_detail_table_dbname"></td>
									</tr>
									<tr>
										<th>所属用户</th>
										<td id="db_detail_table_belongUser"></td>
									</tr>
									<tr>
										<th>创建时间</th>
										<td id="db_detail_table_createtime"></td>
									</tr>
								</table>
							</div>
						</div>
						<div id="db-detail-user-mgr" class="tab-pane">
							<div class="widget-box widget-color-blue ui-sortable-handle col-xs-10">
								<div class="widget-header">
									<div class="widget-toolbar no-border pull-right">
										<button type="button" class="btn btn-white btn-primary btn-xs hide" data-toggle="modal" data-target="#create-dbuser-form">
											<i class="ace-icont fa fa-plus"></i>创建用户
										</button>
									</div>
								</div>
								<div class="widget-body">
									<div class="widget-main no-padding">
										<table class="table table-bordered  table-striped table-hover" id="db_detail_table" >
									<thead>
										<tr>
											<th class="center">
												<label class="position-relative">
													<input type="checkbox" class="ace" />
													<span class="lbl"></span>
												</label>
											</th>
											<th>用户名</th>
											<th>用户权限</th>
											<th>ip地址</th>
											<th>读写比例</th>
											<th>当前状态</th>
										</tr>
									</thead>
										<tbody id="tby">
										</tbody>
									</table>
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

<script src="${ctx}/static/scripts/pagejs/db_detail.js"></script>