<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="page-content-area">
	<div class="page-header">
		<!-- <h3>数据库用户列表</h3> -->
	    <div class="input-group pull-right">
			<form class="form-inline">
				<div class="form-group">
					<input type="text" class="form-control" id="userName"
						placeholder="用户名">
				</div>
				<div class="form-group">
					<input type="text" class="form-control" id="userDb"
						placeholder="所属数据库">
				</div>
				<div class="form-group">
					<!-- <input type="text" class="form-control" id="userAuthority" placeholder="所属Mcluster"> -->
					<select class="form-control" id="userAuthority">
						<option value="">用户权限</option>					
						<option value="">管理员</option>
						<option value="">读写</option>
						<option value="">。。。</option>
					</select>
				</div>
				<div class="form-group">
					<input type="text" class="form-control" id="userIp"
						placeholder="ip地址">
				</div>
				<div class="form-group">
					<select class="form-control" id="userLimit">
						<option value="">频次限制</option>
						<option value="">50</option>
						<option value="">100</option>
						<option value="">。。。</option>
					</select>
				</div>
				<div class="form-group">
					<select class="form-control" id="PhyMechineRunState">
						<option value="">运行状态</option>
						<option value="">创建失败</option>
						<option value="">未审核</option>
						<option value="">。。。</option>
					</select>
				</div>
				<button class="btn btn-sm btn-primary btn-search" type="button">
					<i class="ace-icon fa fa-search"></i>搜索
				</button>
				<button class="btn btn-sm " type="button"
					id="dbuseClearSearch">清空</button>
			</form>
		</div>
	</div>
<!-- /.page-header -->
            <!-- <div class="modal fade" id="dbuseradvancedSearch">
            	<div class="modal-dialog">
            		<div class="modal-content">
            			<div class="modal-header">
            				<button type="button" class="close" data-dismiss="modal">
            					<span aria-hidden="true"><i class="ace-icon fa fa-times-circle"></i></span>
            					<span class="sr-only">关闭</span>
            				</button>
            				<h4 class="modal-title">高级搜索</h4>
            			</div>
            			<div class="modal-body">
            				<form class="form-horizontal" role="form">
            					<div class="form-group">
            						<lable class="col-sm-4 control-label" for="userName"><b>用户名</b></lable>
            						<div class="col-sm-7">
            							<input type="text" class="form-control" id="userName" placeholder="用户名">
            						</div>
            						<label class="control-label"><i class="ace-icon fa fa-user blue bigger-125"></i></label>
            					</div>
            					<div class="form-group">
            						<lable class="col-sm-4 control-label" for="userDb"><b>所属数据库</b></lable>
            						<div class="col-sm-7">
            							<input type="text" class="form-control" id="userDb" placeholder="所属数据库">
            						</div>
            						<label class="control-label"><i class="ace-icon fa fa-database blue bigger-125"></i></label>
            					</div>
            					<div class="form-group">
            						<lable class="col-sm-4 control-label" for="userAuthority"><b>用户权限</b></lable>
            						<div class="col-sm-7">
            							<input type="text" class="form-control" id="userAuthority" placeholder="所属Mcluster">
            							<select class="form-control" id="userAuthority">
            								<option value="">管理员</option>
            								<option value="">读写</option>
            								<option value="">。。。</option>
            							</select>
            						</div>
            						<label class="control-label"><i class="ace-icon fa fa-cog blue bigger-125"></i></label>
            					</div>
            					<div class="form-group">
            						<lable class="col-sm-4 control-label" for="userIp"><b>ip地址</b></lable>
            						<div class="col-sm-7">
            							<input type="text" class="form-control" id="userIp" placeholder="ip地址">
            						</div>
            						<label class="control-label"><i class="ace-icon fa fa-info-circle blue bigger-125"></i></label>
            					</div>
            					<div class="form-group">
            						<lable class="col-sm-4 control-label" for="userLimit"><b>频次限制</b></lable>
            						<div class="col-sm-7">
            							<input type="text" class="form-control" id="dbuser" placeholder="所属用户">
            							<select class="form-control" id="userLimit">
            								<option value="">50</option>
            								<option value="">100</option>
            								<option value="">。。。</option>
            							</select>
            						</div>
            						<label class="control-label"><i class="ace-icon fa fa-cog blue bigger-125"></i></label>
            					</div>
            					
            					
            					<div class="form-group">
            						<lable class="col-sm-4 control-label" for="PhyMechineRunState"><b>当前状态</b></lable>
            						<div class="col-sm-7">
            							<select class="form-control" id="PhyMechineRunState">
            								<option value="">创建失败</option>
            								<option value="">未审核</option>
            								<option value="">。。。</option>
            							</select>
            						</div>
            						<label class="control-label"><i class="ace-icon fa fa-cogs blue bigger-125"></i></label>
            					</div>
            				</form>
            			</div>
            			<div class="modal-footer">
            			<button type="button" class="btn btn-sm btn-default" data-dismiss="modal">取 消 </button>
            			<button type="button" class="btn btn-sm btn-info">搜索</button>
            			</div>
            		</div>
            	</div>
            </div> -->
	<div class="row">
		<div class="widget-box widget-color-blue ui-sortable-handle col-xs-12">
			<div class="widget-header">
				<h5 class="widget-title">数据库用户列表</h5>
				<div class="widget-toolbar no-border">
					<button id="create_db_user" class="btn btn-white btn-primary btn-xs" type="button" onclick="buildUser()">
						<i class="ace-icont fa fa-plus"></i>
						 创建用户
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
								<th>用户名</th>
								<th>所属数据库</th>
								<th>用户权限</th>
								<th>ip地址</th>
								<th>频次限制</th>
								<th>
									当前状态
								</th>
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
	</div>
</div>

<script src="${ctx}/static/scripts/pagejs/db_user_list.js"></script>