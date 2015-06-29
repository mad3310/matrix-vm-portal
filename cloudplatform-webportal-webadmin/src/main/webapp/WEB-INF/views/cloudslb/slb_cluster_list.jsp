<%@ page language="java" pageEncoding="UTF-8"%>
<!-- /section:settings.box -->
<div class="page-content-area">
<div class="row">
<div class="widget-box widget-color-blue ui-sortable-handle queryOption collapsed">
	<script>
		$(window).load(function() {
			var iw=document.body.clientWidth;
			if(iw>991){//md&&lg
				$('.queryOption').removeClass('collapsed');
			}
		});
		$(window).resize(function(event) {
			var iw=document.body.clientWidth;
			if(iw>991){//md&&lg
				$('.queryOption').removeClass('collapsed');
			}
		});
	</script>
		<div class="widget-header hidden-md hidden-lg">
			<h5 class="widget-title">Container集群查询条件</h5>
			<div class="widget-toolbar">
				<a href="#" data-action="collapse">
					<i class="ace-icon fa fa-chevron-down"></i>
				</a>
			</div>
		</div>
		<div class="widget-body">
		<div class="page-header col-sm-12 col-xs-12 col-md-12">
				<!-- <h3>Container集群列表</h3> -->
			</div>
		</div>
	</div>
	
		<div class="widget-box widget-color-blue ui-sortable-handle col-xs-12">
			<div class="widget-header">
				<h5 class="widget-title">Container集群列表</h5>
				<div class="widget-toolbar no-border">
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
								<!-- <th width="13%">Container集群名称</th>
								<th width="16%">所属物理机集群</th>
								<th width="13%">类型</th>
								<th width="13%">所属用户</th>
								<th width="13%">创建时间 </th>
								<th width="13%" class="hidden-480">当前状态</th>
								<th width="13%">操作</th> -->
								<th>Container集群名称</th>
								<th class="hidden-480">所属物理机集群</th>
								<th class="hidden-480">类型</th>
								<th>所属用户</th>
								<th class="hidden-480">创建时间 </th>
								<th>当前状态</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody id="tby">
						</tbody>
					</table>
				</div>
			</div>
		</div>
		<div class="col-xs-12 col-sm-12">
			<small><font color="gray">*注：点击Container集群名可查看详情.</font></small>
		</div>
		<div id="pageControlBar" class="col-xs-12 col-sm-12">
			<input type="hidden" id="totalPage_input" />
			<ul class="pager">
				<li><a href="javascript:void(0);" id="firstPage">&laquo&nbsp;首页</a></li>
				<li><a href="javascript:void(0);" id="prevPage" >上一页</a></li>
				<li><a href="javascript:void(0);" id="nextPage">下一页</a></li>
				<li><a href="javascript:void(0);" id="lastPage">末页&nbsp;&raquo</a></li>
				<li class="hidden-480"><a>共<lable id="totalPage"></lable>页</a></li>
				<li class="hidden-480"><a>第<lable id="currentPage"></lable>页</a></li>
				<li class="hidden-480"><a>共<lable id="totalRows"></lable>条记录</a></li>
			</ul>
		</div>
	</div>
</div>
<!-- /.page-content-area -->

<link rel="stylesheet" href="${ctx}/static/styles/bootstrap/bootstrapValidator.min.css" />
<script src="${ctx}/static/scripts/bootstrap/bootstrapValidator.min.js"></script>

<script src="${ctx}/static/ace/js/jquery.dataTables.min.js"></script>
<script src="${ctx}/static/ace/js/jquery.dataTables.bootstrap.js"></script>

<script src="${ctx}/static/scripts/pagejs/slb/slb_cluster_list.js"></script>