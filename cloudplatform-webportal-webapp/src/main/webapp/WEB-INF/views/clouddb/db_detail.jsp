<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="page-content-area">
	<div id="page-header-id" class="page-header">
		<h1> 
			<a href="${ctx}/list/db">数据库列表</a>
			<small id="headerDbName"> 
				<i class="ace-icon fa fa-angle-double-right"></i> 
			</small>
		</h1>
	</div>
	<!-- /.page-header -->
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
						<div id="db-detail-info" class="tab-pane active">
							<div id="db-detail-table" class="col-xs-6">
								<table class="table table-bordered" id="db_detail_table">
									<tr>
										<td width="50%">数据库名</td>
										<td width="50%" id="db_detail_table_dbname"></td>
									</tr>
									<tr>
										<td>所属用户</td>
										<td id="db_detail_table_belongUser"></td>
									</tr>
									<tr>
										<td>创建时间</td>
										<td id="db_detail_table_createtime"></td>
									</tr>
								</table>
								<small><font color="gray">*注:请用高亮IP连接数据库.</font></small>
							</div>
						</div>
						<div id="db-detail-user-mgr" class="tab-pane">
							<div class="col-xs-10">
								<div class=" pull-right">
									<button type="button" class="btn btn-primary btn-xs" data-toggle="modal" data-target="#create-dbuser-form">
										<i class="ace-icont fa fa-plus"></i>创建用户
									</button>
								</div>
							</div>
							<div class="col-xs-10" style="margin-top:8px">
								<table class="table table-bordered" id="db_detail_table" >
								<thead>
									<tr style="background-color: #307ECC;color:#FFFFFF;">
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
										<th>频次限制</th>
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
				</div>
			</div>
		</div>
		<div class="modal fade" id="create-dbuser-form" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<form id="db_user_apply_form" name="db_user_apply_form" class="form-horizontal" role="form">
					<div class="col-xs-12">
						<h4 class="lighter">
							<a href="#modal-wizard-create-db-user" data-toggle="modal" class="blue"> 创建数据库用户 </a>
						</h4>
						<div class="widget-box">
							<div class="widget-body">
								<div class="widget-main">
									<div class="form-group">
										<input class="hidden" value="${dbId}" name="dbId" id="dbId" type="text" />
										<input class="hidden" value="${mclusterId}" name="mclusterId" id="mclusterId" type="text" />
										<input class="hidden" value="0" name="status" id="status" type="text" />
										<label class="col-sm-offset-1 col-sm-2 control-label" for="username">用户名</label>
										<div class="col-sm-5">
											<input class="form-control" name="username" id="username" type="text" />
										</div>
										<label class="control-label" for="maximum_concurrency">
											<a id="maxConcurrencyHelp" name="popoverHelp" rel="popover" data-container="body" data-toggle="popover" data-placement="right" data-trigger='hover' data-content="请输入字母数字或'_',用户名不能以数字开头." style="cursor:pointer; text-decoration:none;">
												<i class="ace-icon fa fa-question-circle blue bigger-125"></i>
											</a>
										</label>
									</div>
									<div class="form-group">
										<label class="col-sm-offset-1 col-sm-2 control-label" for="connection_type">用户类型</label>
										<div class="col-sm-5">
											<select class="form-control" name="type" id="type">
												<option value="3">读写用户</option>
												<option value="1">管理员</option>
											</select>
										</div>
										<label class="control-label" for="maximum_concurrency">
											<a id="maxConcurrencyHelp" name="popoverHelp" rel="popover" data-container="body" data-toggle="popover" data-placement="right" data-trigger='hover' data-content="请选择创建的数据库用户类型." style="cursor:pointer; text-decoration:none;">
												<i class="ace-icon fa fa-question-circle blue bigger-125"></i>
											</a>
										</label>
									</div>
									 <div class="form-group" name="dynamic-ip-input-mod">
								        <label class="col-sm-offset-1 col-sm-2 control-label">IP地址</label>
								        <div class="col-sm-5">
								            <input type="text" class="form-control" name="acceptIp" />
								        </div>
								        <div class="col-sm-2">
								            <button type="button" class="btn btn-white btn-primary addButton">
								                <i class="fa fa-plus"></i>
								            </button>
								        </div>
								        <label class="control-label" for="maximum_concurrency">
											<a id="maxConcurrencyHelp" name="popoverHelp" rel="popover" data-container="body" data-toggle="popover" data-placement="right" data-trigger='hover' data-content="请输入数据库用户ip示例:192.168.33.12或192.168.33.%" style="cursor:pointer; text-decoration:none;">
												<i class="ace-icon fa fa-question-circle blue bigger-125"></i>
											</a>
										</label>
								    </div>
								    <div class="form-group hide" id="optionTemplate">
								        <div class="col-sm-offset-3 col-sm-5">
								            <input type="text" class="form-control" name="acceptIp" />
								        </div>
								        <div class="col-sm-2">
								            <button type="button" class="btn btn-white btn-primary removeButton">
								                <i class="fa fa-minus"></i>
								            </button>
								        </div>
								    </div>
									<div class="form-group">
										<label class="col-sm-offset-1 col-sm-2 control-label" for="read_write_ratio">读写比例</label>
										<div class="col-sm-5">
											<input class="form-control" name="readWriterRate" id="readWriterRate" type="text" value="2:1" />
										</div>
										<label class="control-label" for="maximum_concurrency">
											<a id="readWriterRateHelp" name="popoverHelp" rel="popover" data-container="body" data-toggle="popover" data-placement="right" data-trigger='hover' data-content="请输入读写比例，建议值'2:1'" style="cursor:pointer; text-decoration:none;">
												<i class="ace-icon fa fa-question-circle blue bigger-125"></i>
											</a>
										</label>
									</div>
									<div class="form-group">
										<label class="col-sm-offset-1 col-sm-2 control-label" for="maximum_concurrency">最大并发量</label>
										<div class="col-sm-5">
											<input class="form-control" name="maxConcurrency" id="maxConcurrency" type="text" value="50"/>
										</div>
										<label class="control-label" for="maximum_concurrency">
											<a id="maxConcurrencyHelp" name="popoverHelp" rel="popover" data-container="body" data-toggle="popover" data-placement="right" data-trigger='hover' data-content="请输入每秒最大并发量.建议值'50'" style="cursor:pointer; text-decoration:none;">
												<i class="ace-icon fa fa-question-circle blue bigger-125"></i>
											</a>
										</label>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-sm btn-default" data-dismiss="modal">关闭</button>
						<button id="create-dbUser-botton" type="button" class="btn btn-sm disabled btn-primary" onclick="createDbUser()">创建</button>
					</div>
				</form>
				</div>
			</div>
		</div>
		<div class="modal fade" id="edit-dbuser-form" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<form id="db_user_edit_form" name="db_user_edit_form" class="form-horizontal" role="form">
					<div class="col-xs-12">
						<h4 class="lighter">
							<a href="#modal-wizard-edit-db-user" data-toggle="modal" class="blue">编辑数据库用户 </a>
						</h4>
						<div class="widget-box">
							<div class="widget-body">
								<div class="widget-main">
									<div class="form-group">
										<input class="hidden" value="${dbId}" name="dbId" id="editDbId" type="text" />
										<input class="hidden" value="${id}" name="id" id="dbUserId" type="text" />
										<label class="col-sm-offset-1 col-sm-2 control-label" for="username">用户名</label>
										<div class="col-sm-5">
											<input class="form-control" name="username" id="editUsername" type="text" readonly="readonly"/>
										</div>
										<label class="control-label" for="maximum_concurrency">
											<a id="maxConcurrencyHelp" name="popoverHelp" rel="popover" data-container="body" data-toggle="popover" data-placement="right" data-trigger='hover' data-content="请输入字母数字或'_',用户名不能以数字开头." style="cursor:pointer; text-decoration:none;">
												<i class="ace-icon fa fa-question-circle blue bigger-125"></i>
											</a>
										</label>
									</div>
									<div class="form-group">
										<label class="col-sm-offset-1 col-sm-2 control-label" for="connection_type">用户类型</label>
										<div class="col-sm-5">
											<fieldset disabled>
												<select class="form-control" name="type" id="editType">
													<option value="3">读写用户</option>
													<option value="1">管理员</option>
												</select>
											</fieldset>
											<input class="hidden" name="type" id="editTypeInput" type="text"/>
										</div>
										<label class="control-label" for="maximum_concurrency">
											<a id="maxConcurrencyHelp" name="popoverHelp" rel="popover" data-container="body" data-toggle="popover" data-placement="right" data-trigger='hover' data-content="请选择创建的数据库用户类型." style="cursor:pointer; text-decoration:none;">
												<i class="ace-icon fa fa-question-circle blue bigger-125"></i>
											</a>
										</label>
									</div>
									 <div class="form-group">
								        <label class="col-sm-offset-1 col-sm-2 control-label">IP地址</label>
								        <div class="col-sm-5">
								            <input type="text" class="form-control" name="acceptIp" id="editAcceptIp" type="text" readonly="readonly"/>
								        </div>
								        <label class="control-label" for="maximum_concurrency">
											<a id="maxConcurrencyHelp" name="popoverHelp" rel="popover" data-container="body" data-toggle="popover" data-placement="right" data-trigger='hover' data-content="请输入数据库用户ip示例:192.168.33.12或192.168.33.%" style="cursor:pointer; text-decoration:none;">
												<i class="ace-icon fa fa-question-circle blue bigger-125"></i>
											</a>
										</label>
								    </div>
								    <div class="form-group">
										<label class="col-sm-offset-1 col-sm-2 control-label" for="read_write_ratio">读写比例</label>
										<div class="col-sm-5">
											<input class="form-control" name="readWriterRate" id="editReadWriterRate"/>
										</div>
										<label class="control-label" for="maximum_concurrency">
											<a id="readWriterRateHelp" name="popoverHelp" rel="popover" data-container="body" data-toggle="popover" data-placement="right" data-trigger='hover' data-content="请输入读写比例，建议值'2:1'" style="cursor:pointer; text-decoration:none;">
												<i class="ace-icon fa fa-question-circle blue bigger-125"></i>
											</a>
										</label>
									</div>
									<div class="form-group">
										<label class="col-sm-offset-1 col-sm-2 control-label" for="maximum_concurrency">最大并发量</label>
										<div class="col-sm-5">
											<input class="form-control" name="maxConcurrency" id="editMaxConcurrency" type="text"/>
										</div>
										<label class="control-label" for="maximum_concurrency">
											<a id="maxConcurrencyHelp" name="popoverHelp" rel="popover" data-container="body" data-toggle="popover" data-placement="right" data-trigger='hover' data-content="请输入每秒最大并发量.建议值'50'" style="cursor:pointer; text-decoration:none;">
												<i class="ace-icon fa fa-question-circle blue bigger-125"></i>
											</a>
										</label>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-sm btn-default" data-dismiss="modal">取消</button>
						<button id="edit-dbUser-botton" type="button" class="btn btn-sm disabled btn-primary" onclick="editDbUserCmd()">保存</button>
					</div>
				</form>
				</div>
			</div>
		</div>
		<div class="modal fade" id="delete-dbuser" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog modal-sm">
				<div class="modal-content">
					<div class="col-xs-12">
						<h7 class="lighter">
							<a href="#modal-wizard-edit-db-user" data-toggle="modal" class="blue">&nbsp</a>
						</h7>
						<div class="widget-box">
							<div class="widget-body">
								<div class="widget-main" >
									<h4 class="lighter" align="center">
										确定要删除此用户?
									</h4>
								</div>
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-sm btn-default" data-dismiss="modal">取消</button>
						<button id="edit-dbUser-botton" type="button" onclick="deleteDbUserCmd()" class="btn btn-sm btn-danger" >删除</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<!-- /.page-content-area -->
<link rel="stylesheet" href="${ctx}/static/styles/bootstrap/bootstrapValidator.min.css" />
<script src="${ctx}/static/scripts/bootstrap/bootstrapValidator.js"></script>
<script src="${ctx}/static/scripts/date-transform.js"></script>

<script type="text/javascript">
$(function(){
	//隐藏搜索框
	$('#nav-search').addClass("hidden");
	
	pageinit();
	$('[name = "popoverHelp"]').popover();
	
	 var MAX_OPTIONS = 10;
	    $('#db_user_apply_form').bootstrapValidator({
	            feedbackIcons: {
	                valid: 'glyphicon glyphicon-ok',
	                invalid: 'glyphicon glyphicon-remove',
	                validating: 'glyphicon glyphicon-refresh'
	            },
	            fields: {
	            	username: {
	                    validators: {
	                        notEmpty: {
	                            message: '用户名不能为空!'
	                        },
	  			          stringLength: {
				              max: 16,
				              message: '用户名过长!'
				          }, regexp: {
			                  regexp: /^([a-zA-Z_]+[a-zA-Z_0-9]*)$/,
	  		                  message: "请输入字母数字或'_',用户名不能以数字开头."
	                 	  }
	                    }
	                },
	                readWriterRate: {
	                    validMessage: '请按提示输入',
	                    validators: {
	                        notEmpty: {
	                            message: '读写比例不能为空!'
	                        },
	                        regexp: {
	  		                  regexp: /^((\d|\d\d|\d\d\d)(\:(\d|\d\d|\d\d\d))){1}$/,
	  		                  message: '请按提示输入'
	  		              }
	                    }
	                },
	                maxConcurrency: {
	                    validMessage: '请按提示输入',
	                    validators: {
	                        notEmpty: {
	                            message: '最大并发量不能为空!'
	                        },integer: {
	                            message: '请输入数字'
	                        }
	                    }
	                },
	                acceptIp: {
	                    validators: {
	                        notEmpty: {
	                            message: '地址不能为空'
	                        },
	  		                regexp: {
			                  regexp: /^(\d|\d\d|1\d\d|2[0-4]\d|25[0-5])((\.(\d|\d\d|1\d\d|2[0-4]\d|25[0-5]))|(\.\%)){3}$/,
			                  message: '请按提示格式输入'
			              	}, 
			                remote: {
		                        url: '${ctx}/dbUser/validate' ,
		                        data: function(validator) {
		                            return {
		                                username: validator.getFieldElements('username').val(),
		                                dbId: validator.getFieldElements('dbId').val()
		                            };
		                        },
		                        message: '该用户名此IP已存在!'
		                    }
	                    }
	                }
	            }
	        }).on('click', '.addButton', function() {
	            var $template = $('#optionTemplate'),
	                $clone    = $template
	                                .clone()
	                                .removeClass('hide')
	                                .removeAttr('id')
	                                .attr('name','optionTemplate')
	                                .insertBefore($template),
	                $option = $clone.find('[name="acceptIp"]');
	            $('#db_user_apply_form').bootstrapValidator('addField', $option);
	        }).on('click', '.removeButton', function() {
	            var $row    = $(this).parents('.form-group'),
	                $option = $row.find('[name="acceptIp"]');
	            $row.remove();
	            $('#db_user_apply_form').bootstrapValidator('removeField', $option);
	        }).on('added.field.bv', function(e, data) {
	            if (data.field === 'acceptIp') {
	                if ($('#db_user_apply_form').find(':visible[name="acceptIp"]').length >= MAX_OPTIONS) {
	                    $('#db_user_apply_form').find('.addButton').attr('disabled', 'disabled');
	                }
	            }
	        }).on('removed.field.bv', function(e, data) {
	           if (data.field === 'acceptIp') {
	                if ($('#db_user_apply_form').find(':visible[name="acceptIp"]').length < MAX_OPTIONS) {
	                    $('#db_user_apply_form').find('.addButton').removeAttr('disabled');
	                }
	            }
	        }).on('keyup', '[name="username"]', function() {
	             $('#db_user_apply_form').bootstrapValidator('revalidateField', 'acceptIp');
	        }).on('error.field.bv', function(e, data) {
	        	 $('#create-dbUser-botton').addClass("disabled");
	        }).on('success.field.bv', function(e, data) {
	       	 	$('#create-dbUser-botton').removeClass("disabled");
	        });
	    $('#db_user_edit_form').bootstrapValidator({
	            feedbackIcons: {
	                valid: 'glyphicon glyphicon-ok',
	                invalid: 'glyphicon glyphicon-remove',
	                validating: 'glyphicon glyphicon-refresh'
	            },
	            fields: {
	                readWriterRate: {
	                    validMessage: '请按提示输入',
	                    validators: {
	                        notEmpty: {
	                            message: '读写比例不能为空!'
	                        },
	                        regexp: {
	  		                  regexp: /^((\d|\d\d|\d\d\d)(\:(\d|\d\d|\d\d\d))){1}$/,
	  		                  message: '请按提示输入'
	  		              }
	                    }
	                },
	                maxConcurrency: {
	                    validMessage: '请按提示输入',
	                    validators: {
	                        notEmpty: {
	                            message: '最大并发量不能为空!'
	                        },integer: {
	                            message: '请输入数字'
	                        }
	                    }
	                },
	            }
	        }).on('error.field.bv', function(e, data) {
	        	$('#edit-dbUser-botton').addClass("disabled");
	        }).on('success.field.bv', function(e, data) {
	       	 	$('#edit-dbUser-botton').removeClass("disabled");
	        });
});
function translateStatus(status){
	if(status == 0 || status == 2){
		return "未审核";
	}else if(status  == 1){
		return "正常";
	}else if(status  == 4){
		return "未通过";
	}else{
		return "创建失败";
	}
}
function queryDbUser(){
	$("#tby tr").remove();
	$.ajax({ 
		type : "get",
		url : "${ctx}/dbUser/"+$("#dbId").val(),
		dataType : "json", /*这句可用可不用，没有影响*/
		success : function(data) {
			error(data);
			var array = data.data;
			var tby = $("#tby");
			for (var i = 0, len = array.length; i < len; i++) {
				var td1 = $("<td class=\"center\">"
						    + "<label class=\"position-relative\">"
						    + "<input name=\"db_user_id\" value= \""+array[i].id+"\" type=\"checkbox\" class=\"ace\"/>"
						    + "<span class=\"lbl\"></span>"
						    + "</label>"
					        + "</td>");
				var	td2 = $("<td name=\"db_user_name\">"
							+ array[i].username
							+ "</td>");
				var td3;
				if(array[i].type == 3){
					var td3 = $("<td name=\"db_user_type\">"
							    + "管理员"
							    + "</td>");
				}else{
					var td3 = $("<td name=\"db_user_type\">"
							    + "读写用户"
							    + "</td>");
				}
				
				var td4 = $("<td name=\"db_user_accept_ip\">"
							+array[i].acceptIp
							+ "</td>");
				var td5 = $("<td name=\"db_user_read_writer\">"
							+array[i].readWriterRate
							+ "</td>");
				var td6 = $("<td name=\"db_user_max_concurrency\">"
							+array[i].maxConcurrency
							+ "</td>");
				var td7 = $("<td>"
							+translateStatus(array[i].status)
							+ "</td>");
				var td8 = $("<td>"
							+"<div class=\"hidden-sm hidden-xs action-buttons\">"
							+"<a class=\"green\" href=\"#\" onclick=\"editDbUserForm(this)\" data-toggle=\"modal\" data-target=\"#edit-dbuser-form\">"
								+"<i class=\"ace-icon fa fa-pencil bigger-120\"></i>"
							+"</a>"
							+"<a class=\"red\" href=\"#\" onclick=\"deleteDbUser(this)\" data-toggle=\"modal\" data-target=\"#delete-dbuser\">"
								+"<i class=\"ace-icon fa fa-trash-o bigger-130\"></i>"
							+"</a>"
							+"</div>"
							+ "</td>");
					
				if(array[i].status == 0 ||array[i].status == 2){
					var tr = $("<tr class=\"warning\"></tr>");
				}else if(array[i].status == 3 ||array[i].status == 4){
					var tr = $("<tr class=\"danger\"></tr>");
				}else{
					var tr = $("<tr></tr>");
				}
				
				tr.append(td1).append(td2).append(td3).append(td4).append(td5).append(td6).append(td7).append(td8);
				tr.appendTo(tby);
			}//循环json中的数据 
		}
	});
}
function queryDbInfo(){
	$.ajax({ 
		type : "get",
		url : "${ctx}/db/"+$("#dbId").val(),
		dataType : "json", 
		success : function(data) {
			error(data);
			var dbInfo = data.data;
			$("#headerDbName").append(dbInfo.dbName);
			$("#db_detail_table_dbname").text(dbInfo.dbName);
			$("#db_detail_table_belongUser").text(dbInfo.user.userName);
			$("#db_detail_table_createtime").text(date('Y-m-d H:i:s',dbInfo.createTime));
			for(var i=0,len = dbInfo.containers.length; i < len; i++)
			{
				var td1,td2;
				if(dbInfo.containers[i].type == "mclusternode")
				{
					td1 = $("<td>"
							+ "节点-"+i
							+"</td>");
					td2 =$("<td>"
						+ "<b><font color=\"green\">"
						+ dbInfo.containers[i].ipAddr
						+ "</font></b>"
						+"</td>");
				}else{
					td1 = $("<td>"
							+ "VIP"
							+"</td>");
					td2 =$("<td>"
						+ dbInfo.containers[i].ipAddr
						+"</td>");
				}
				var tr = $("<tr></tr>");
				tr.append(td1).append(td2);
				$("#db_detail_table").append(tr);
			}
		}
	});
}
function queryUser(userId){
	var userInfo;
	$.ajax({ 
		type : "get",
		url : "${ctx}/user/"+ userId,
		dataType : "json", 
		success : function(data) {
			error(data);
			$("#db_detail_table").find('tr:eq(1) td:eq(1)').text(data.data.userName);
		}
	});
}

function checkboxControl(){
	$('th input:checkbox').click(function(){
		var that = this;
		$(this).closest('table').find('tr > td:first-child input:checkbox')
		.each(function(){
			this.checked = that.checked;
			$(this).closest('tr').toggleClass('selected');
		});
	});
}

function createDbUser(){
	$.ajax({
		url: '${ctx}/dbUser',
        type: 'post',
        dataType: 'text',
        data: $("#db_user_apply_form").serialize(),
        success: function (data) {
        	error(data);
        	$("#create-dbuser-form").modal("hide");
        	queryDbUser();
			$('#db_user_apply_form').find(":input").not(":button,:submit,:reset,:hidden").val("").removeAttr("checked").removeAttr("selected");
			$('#db_user_apply_form').find('[name="optionTemplate"]').remove();
			$('#db_user_apply_form').data('bootstrapValidator').resetForm();
			$('#type').val(3);
			$('#readWriterRate').val('2:1');
			$('#maxConcurrency').val('50');
			$('#create-dbUser-botton').addClass('disabled');
        }
	});
}
function editDbUserForm(obj){
	var dbUserTr = $(obj).parents("tr");
	$('#editUsername').val(dbUserTr.children('[name="db_user_name"]').html());
	$('#dbUserId').val(dbUserTr.find('[name="db_user_id"]').val());
	$('#editAcceptIp').val(dbUserTr.children('[name="db_user_accept_ip"]').html());
	$('#editReadWriterRate').val(dbUserTr.children('[name="db_user_read_writer"]').html());
	$('#editMaxConcurrency').val(dbUserTr.children('[name="db_user_max_concurrency"]').html());
	if(dbUserTr.children('[name="db_user_type"]').html() == "管理员"){
		$('#editType').val('1');
		$('#editTypeInput').val('1');
	}else{
		$('#editType').val('3');
		$('#editTypeInput').val('3');
	}
}

function editDbUserCmd(){
	$.ajax({
		url:'${ctx}/dbUser/'+$('#dbUserId').val(),
		type:'post',
		data:$("#db_user_edit_form").serialize(),
		success:function(data){
        	error(data);
			$("#edit-dbuser-form").modal("hide");
			queryDbUser();
		}
	});
}
function deleteDbUser(obj){
	var dbUserId =$(obj).parents("tr").find('[name="db_user_id"]').val();
	$('#dbUserId').val(dbUserId);
}
function deleteDbUserCmd(){
	$.ajax({
		url:'${ctx}/dbUser/'+$('#dbUserId').val(),
		type:'delete',
		success:function(data){
			$("#delete-dbuser").modal("hide");
			queryDbUser();
		}
	});
}

function pageinit(){
	checkboxControl();
	queryDbUser();
	queryDbInfo();
}
</script>
