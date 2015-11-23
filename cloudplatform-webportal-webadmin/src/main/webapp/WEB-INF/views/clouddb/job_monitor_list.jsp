<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="page-content-area">
	<div class="row">		
		<div class="task-monitor-menu col-xs-12 col-sm-12 col-md-4 dataTables_wrapper form-inline no-footer">
			<div class="table-header" style="margin-bottom:10px;background-color:#333;">
			任务流执行记录
			</div>
			<div class="row">
				<div class="input-group pull-right">
					<form>
						<div class="form-group col-xs-6 col-sm-6">
							<select class="" id="jobstatus" style="height:34px;width:150px;">
								<option value="GCE">GCE</option>
								<option value="RDS">RDS</option>
								<option value="SLB">SLB</option>
								<option value="CBASE">CBASE</option>
								<option value="OSS">OSS</option>
								<option value="LOG">LOG</option>
							</select>
						</div>
						<div class=" form-group col-xs-6 col-sm-6">
							<button class="btn btn-sm btn-primary btn-search pull-right " id="jobSearch"
								type="button">
								<i class="ace-icon fa fa-search"></i>搜索
							</button>
						</div>
					</form>
				</div>
			</div>
			<table class="table task-monitor-list" id="db_detail_table" style="margin-top:10px;">
				 <thead>
					<tr>
						<th>名称</th>
						<th class="hidden-480">起始时间</th>
						<th>应用名称</th>
						<th class="hidden-480">集群名称</th>						
						<th>状态</th>
					</tr>
				</thead>
				<tbody id="menu-tby">
				
				</tbody>
			</table>
			<div id="noData" class="col-xs-12 col-md-12 hidden" style="border-top:1px solid #ccc;padding-top:10px;margin-top:-20px;">
			      <small><font color="gray">没有数据记录</font></small>
		    </div>
			<div id="pageControlBar" class="hidden">
				<input type="hidden" id="totalPage_input" />
				<ul class="pager">
					<li><a href="javascript:void(0);" id="firstPage">&laquo&nbsp;首页</a></li>
					<li><a href="javascript:void(0);" id="prevPage" >上一页</a></li>
					<li>
					   <a>
						   <input class="pageinput" id="currentPage" style="text-align:right"/>/
						   <input class="pageinput" id="totalPage" />
					   </a>
					</li>
					<li><a href="javascript:void(0);" id="nextPage">下一页</a></li>
					<li><a href="javascript:void(0);" id="lastPage">末页&nbsp;&raquo</a></li>
					<!-- <li><a>共<lable id="totalPage"></lable>页</a></li>
					<li><a>第<lable id="currentPage"></lable>页</a></li>
					<li><a>共<lable id="totalRows"></lable>条记录</a></li> -->
				</ul>
			</div>
		</div>
		<div class="widget-box widget-color-blue ui-sortable-handle task-monitor-table col-xs-12 col-sm-12 col-md-8">
			<div class="widget-header">
				<h5 class="widget-title">任务监控</h5>
			</div>
			<div class="widget-body">
				<div class="widget-main no-padding">
					<table class="table table-bordered" id="db_detail_table" >
						<thead>
							<tr>
								<th>任务单元名称</th>
								<th class="hidden-480">起始时间</th>
								<th class="hidden-480">结束时间</th>
								<th class="hidden-480">重试次数</th>
								<th class="hidden-480">执行结果</th>
								<th>当期状态</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody id="tby">
						</tbody>
						<input type="text" id="taskIdTemp" class="hidden"/>
					</table>
				</div>
			</div>
		</div>
	</div>
</div>
<script src="${ctx}/static/scripts/pagejs/job_monitor_list.js"></script>
