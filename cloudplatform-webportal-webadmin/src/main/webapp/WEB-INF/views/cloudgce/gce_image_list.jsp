<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="page-content-area">
	<div class="row">
		<div class="widget-box widget-color-blue ui-sortable-handle col-xs-12">
			<div class="widget-header">
				<h5 class="widget-title">GCE镜像列表</h5>
				<div class="widget-toolbar no-border">
					<button id="create_job_stream" class="btn btn-white btn-primary btn-xs" data-toggle="modal" data-target="#create-job-unit-modal">
						<i class="ace-icont fa fa-plus"></i>
						 添加镜像
					</button>
				</div>
			</div>
			<div class="widget-body">
				<div class="widget-main no-padding">
					<table class="table table-bordered" id="db_detail_table" >
						<thead>
							<tr>
								<th class="center">
									<label class="position-relative">
										<input type="checkbox" id="titleCheckbox" class="ace" />
										<span class="lbl"></span>
									</label>
								</th>
								<th>镜像名称</th>
								<th>类型</th>
								<th>版本</th>
								<th>所属用户</th>
								<th>可用性</th>
								<th>下载地址</th>
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
		<div id="pageControlBar">
			<input type="hidden" id="totalPage_input" />
			<ul class="pager">
				<li><a href="javascript:void(0);" id="firstPage">&laquo首页</a></li>
				<li><a href="javascript:void(0);" id="prevPage">上一页</a></li>
				<li><a href="javascript:void(0);" id="nextPage">下一页</a></li>
				<li><a href="javascript:void(0);" id="lastPage">末页&raquo</a></li>
	
				<li><a>共<lable id="totalPage"></lable>页</a>
				</li>
				<li><a>第<lable id="currentPage"></lable>页</a>
				</li>
				<li><a>共<lable id="totalRows"></lable>条记录</a>
				</li>
			</ul>
		</div>
		<div class="modal fade" id="create-job-unit-modal" tabindex="-1" aria-labelledby="myModalLabel" style="margin-top:157px">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
           				<button type="button" class="close" data-dismiss="modal">
           					<span aria-hidden="true"><i class="ace-icon fa fa-times-circle"></i></span>
           					<span class="sr-only">关闭</span>
           				</button>
           				<h4 class="modal-title">添加镜像 </h4>
            		</div>
					<form id="add-gce-image-form" name="add-gce-image-form" class="form-horizontal" role="form">
						<div class="modal-body">            				
            				<div class="form-group">
								<label class="col-sm-4 control-label" for="taskUnitName">镜像名称</label>
								<div class="col-sm-6">
									<input class="form-control" name="name" id="name" type="text" />
								</div>
								<label class="control-label">
									<a name="popoverHelp" rel="popover" data-container="body" data-toggle="popover" data-placement="right" data-trigger='hover' data-content="镜像名称应与下载镜像名相同" style="cursor:pointer; text-decoration:none;">
										<i class="ace-icon fa fa-question-circle blue bigger-125"></i>
									</a>
								</label>
							</div>
							<div class="form-group">
								<label class="col-sm-4 control-label" for="type">业务类型</label>
								<div class="col-sm-6">
									<select class="chosen-select" name="type" id="type" data-placeholder="请选择类型...">
										<!-- <option value="">  </option> -->
										<!-- <option value="xx">消息中间件</option> -->
										<option value="jetty">jetty</option>
										<option value="nginx">nginx</option>
									</select>
								</div>								
								<label class="control-label">
									<a name="popoverHelp" rel="popover" data-container="body" data-toggle="popover" data-placement="right" data-trigger='hover' data-content="所属业务类型" style="cursor:pointer; text-decoration:none;">
										<i class="ace-icon fa fa-question-circle blue bigger-125"></i>
									</a>
								</label>
							</div>
							<div class="form-group">
								<label class="col-sm-4 control-label" for="beanName">镜像版本</label>
								<div class="col-sm-6">
									<input class="form-control" name="tag" id="tag" type="text" />
								</div>								
								<label class="control-label">
									<a name="popoverHelp" rel="popover" data-container="body" data-toggle="popover" data-placement="right" data-trigger='hover' data-content="上传镜像的版本号tag" style="cursor:pointer; text-decoration:none;">
										<i class="ace-icon fa fa-question-circle blue bigger-125"></i>
									</a>
								</label>
							</div>
							<div class="form-group">
								<label class="col-sm-4 control-label" for="url">下载地址</label>
								<div class="col-sm-6">
									<input class="form-control" name="url" id="url" type="text" />
								</div>								
								<label class="control-label">
									<a name="popoverHelp" rel="popover" data-container="body" data-toggle="popover" data-placement="right" data-trigger='hover' data-content="下载此镜像的地址" style="cursor:pointer; text-decoration:none;">
										<i class="ace-icon fa fa-question-circle blue bigger-125"></i>
									</a>
								</label>
							</div>
							<div class="form-group">
								<label class="col-sm-4 control-label" for="beanName">可用性</label>
								<div class="col-sm-6">
									<select class="chosen-select" name="status" id="status" data-placeholder="该镜像是否可用">
										<option value="AVAILABLE">AVAILABLE</option>
										<option value="NOTAVAILABLE">NOTAVAILABLE</option>
									</select>
								</div>							
								<label class="control-label">
									<a name="popoverHelp" rel="popover" data-container="body" data-toggle="popover" data-placement="right" data-trigger='hover' data-content="此功能可用暂时禁用镜像" style="cursor:pointer; text-decoration:none;">
										<i class="ace-icon fa fa-question-circle blue bigger-125"></i>
									</a>
								</label>
							</div>
							<div class="form-group">
								<label class="col-sm-4 control-label" for="retry">所属用户</label>
								<div class="col-sm-6">
									<select class="chosen-select" name="owner" id="owner" data-placeholder="请选择所属用户">
										<option value="2">liuhao1</option>
										<option value="1">yaouo</option>
										<option value="3">gaomin</option>
										<option value="4">zhangzeng</option>
										<option value="5">zhangxiang</option>
									</select>
								</div>
								<label class="control-label">
									<a name="popoverHelp" rel="popover" data-container="body" data-toggle="popover" data-placement="right" data-trigger='hover' data-content="只有所属用户才能使用此镜像" style="cursor:pointer; text-decoration:none;">
										<i class="ace-icon fa fa-question-circle blue bigger-125"></i>
									</a>
								</label>
							</div>
							<div class="form-group">
								<label class="col-sm-4 control-label" for="descn">描述</label>
								<div class="col-sm-6">
									<textarea id="descn" name="descn" class="form-control" rows="4"></textarea>
								</div>
								<label class="control-label">
									<a name="popoverHelp" rel="popover" data-container="body" data-toggle="popover" data-placement="right" data-trigger='hover' data-content="本镜像的功能描述，可输入字母、数字或_,最多100字符!" style="cursor:pointer; text-decoration:none;">
										<i class="ace-icon fa fa-question-circle blue bigger-125"></i>
									</a>
								</label>
							</div>
            			</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-sm btn-default" data-dismiss="modal">关闭</button>
						<button type="submit" class="btn btn-sm btn-primary">创建</button>
					</div>
				</form>
				</div>
			</div>
		</div>
	</div>
</div>
<link rel="stylesheet" href="${ctx}/static/styles/bootstrap/bootstrapValidator.min.css" />
<script src="${ctx}/static/scripts/bootstrap/bootstrapValidator.min.js"></script>
<script src="${ctx}/static/scripts/pagejs/gce_image_list.js"></script>