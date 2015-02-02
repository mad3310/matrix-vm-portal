<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-- 全局参数 start -->
<input class="hidden" value="${dbId}" name="dbId" id="dbId" type="text" />

<div class="page-content-area">
		<!-- <h3>备份恢复列表</h3> -->
		<div class="input-group pull-right">
			<div class="form-inline">
				<div class="form-group time-range-unit-header">
					<span class="time-range-title">选择时间范围：</span>
					<div class="date-unit">
						<input type='text' class="form-control datetimepicker" id='startTime' />
					</div>
					<span class="date-step-span">至</span>
					<div class="date-unit">
						<input type='text' class="form-control datetimepicker" id='endTime' />
					</div>
				</div>
				<script type="text/javascript">
				$(function () {
	                $('#startTime').datetimepicker();
	                $('#endTime').datetimepicker();
	            });
				</script>
				<div class="form-group">
					<input id="dbName" type="input" class="form-control margin-left-5"
						placeholder="请输入数据库名称">
				</div>
				<div class="form-group ">
					<input id="mclusterName" type="input"
						class="form-control margin-left-5" placeholder="请输入集群名称">
				</div>
				<div class="form-group margin-left-5">
					<select id="backupStatus" class="form-control">
					    <option value="4"  selected="selected">全部</option>
						<option value="0">备份成功</option>
						<option value="1">备份失败</option>
						<option value="2">备份中...</option>
					</select>
				</div>
				<button id="bksearch" class="btn btn-primary btn-sm btn-search">查询</button>
			</div>
		</div>
	</div>
		<div class="row">
			<div class="widget-box widget-color-blue ui-sortable-handle col-xs-12">
				<div class="widget-header">
					<h5 class="widget-title">备份恢复列表</h5>
				</div>
				<div class="widget-body">
					<div class="widget-main no-padding">
						<table class="table table-hover table-striped">
							<thead>
								<tr class="text-muted">
									<td style="width: 15%">container集群名称</td>
									<td style="width: 15%">数据库名称</td>
									<td style="width: 20%">开始时间</td>
									<td style="width: 20%">结束时间</td>
									<td style="width: 10%">状态</td>
									<td style="width: 20%">详情</td>
								</tr> 
							</thead>
							<tbody id="backupTbody">
							</tbody>
						</table>
					</div>
				</div>
			</div>
			<div id="noData" class="col-xs-12 col-md-12 hidden">
			      <small><font color="gray">没有符合条件的记录。</font></small>
		    </div>
			<div id="pageControlBar">
				<input type="hidden" id="totalPage_input" ></input>
				<ul class="pager">
					<li><a href="javascript:void(0);" id="firstPage">&laquo首页</a></li>
					<li><a href="javascript:void(0);" id="prevPage">上一页</a></li>
					<li><a href="javascript:void(0);" id="nextPage">下一页</a></li>
					<li><a href="javascript:void(0);" id="lastPage">末页&raquo</a></li>
                    <li><a>共<lable id="totalPage"></lable>页
					</a></li>
					<li><a>第<lable id="currentPage"></lable>页
					</a></li>
					<li><a>共<lable id="totalRows"></lable>条记录
					</a></li>
				</ul>
	        </div>
		</div>
</div>

<!-- js -->
<<script src="${ctx}/static/scripts/moment/2.9.0/moment-with-locales.min.js"></script>
<script src="${ctx}/static/scripts/bootstrap/datetimepicker/bootstrap-datetimepicker.js"></script>
<script src="${ctx}/static/scripts/pagejs/backup_recover.js"></script>
