<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="page-content-area">
	<div id="page-header-id" class="page-header">
		<h1> 
			<a href="${ctx}/list/db">数据库列表</a>
			<small> 
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
							<a data-toggle="tab" href="#db-detail-user-mgr">用户管理</a>
						</li>
						<li class="">
							<a data-toggle="tab" href="#db-detail-info">数据库信息</a>
						</li>
					</ul>
				</div>
			</div>
			<div class="widget-body">
				<div class="widget-main padding-12 no-padding-left no-padding-right">
					<div class="tab-content padding-4">
						<div id="db-detail-info" class="tab-pane">
							<div id="db-detail-table" class="col-xs-6">
								<table class="table table-bordered" id="db_detail_table">
									<tr>
										<td width="50%">数据库名</td>
										<td width="50%" id="dbName"></td>
									</tr>
									<tr>
										<td>所属用户</td>
										<td></td>
									</tr>
									<tr>
										<td>创建时间</td>
										<td></td>
									</tr>
								</table>
							</div>
						</div>
						<div id="db-detail-user-mgr" class="tab-pane active">
							<div class="col-xs-10">
								<div class=" pull-right">
									<button type="button" class="btn btn-xs btn-success bigger" data-toggle="modal" data-target="#create-dbuser-form">
										<i class="ace-icont fa fa-plus"></i>创建用户
									</button>
									<!-- <button type="button" class="btn btn-xs btn-danger bigger disabled">
										<i class="ace-icont fa fa-trash-o"></i>删除用户
									</button> -->
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
										<th>
											用户名
										</th>
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
								<small><font color="gray">*注:请用高亮IP连接数据库.</font></small>
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
							<a href="#modal-wizard" data-toggle="modal" class="blue"> 创建数据库用户 </a>
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
										<label class="col-sm-offset-1 col-sm-2 control-label" for="db_name">密码</label>
										<div class="col-sm-5">
											<input class="form-control" name="password" id="password" type="password" />
										</div>
										<label class="control-label" for="maximum_concurrency">
											<a id="maxConcurrencyHelp" name="popoverHelp" rel="popover" data-container="body" data-toggle="popover" data-placement="right" data-trigger='hover' data-content="密码请妥善保管!" style="cursor:pointer; text-decoration:none;">
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
									 <div class="form-group">
								        <label class="col-sm-offset-1 col-sm-2 control-label">IP地址</label>
								        <div class="col-sm-5">
								            <input type="text" class="form-control" name="acceptIp" />
								        </div>
								        <div class="col-sm-2">
								            <button type="button" class="btn btn-success addButton btn-sm">
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
								            <button type="button" class="btn btn-default btn-sm removeButton">
								                <i class="fa fa-minus"></i>
								            </button>
								        </div>
								    </div>
									<div class="form-group">
										<label class="col-sm-offset-1 col-sm-2 control-label" for="read_write_ratio">读写比例</label>
										<div class="col-sm-5">
											<input class="form-control" name="readWriterRate" id="readWriterRate" type="text" placeholder="" />
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
											<input class="form-control" name="maxConcurrency" id="maxConcurrency" type="text" placeholder=""/>
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
	                password: {
	                    validators: {
	                        notEmpty: {
	                            message: '密码不能为空!'
	                        },
	  			          stringLength: {
				              max: 20,
				              message: '密码太长了!'
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
		                        message: '该用户名此IP也存在!'
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
	$.ajax({ 
		type : "get",
		url : "${ctx}/dbUser/"+$("#dbId").val(),
		dataType : "json", /*这句可用可不用，没有影响*/
		success : function(data) {
			var array = data.data;
			var tby = $("#tby");
			
			for (var i = 0, len = array.length; i < len; i++) {
				var td1 = $("<td class=\"center\">"
						    + "<label class=\"position-relative\">"
						    + "<input type=\"checkbox\" class=\"ace\"/>"
						    + "<span class=\"lbl\"></span>"
						    + "</label>"
					        + "</td>");
				var	td2 = $("<td>"
							+ array[i].username
							+ "</td>");
				var td3;
				if(array[i].type == 3){
					var td3 = $("<td>"
							    + "管理员"
							    + "</td>");
				}else{
					var td3 = $("<td>"
							    + "读写用户"
							    + "</td>");
					
				}
				
				var td4 = $("<td>"
							+array[i].acceptIp
							+ "</td>");
				var td5 = $("<td>"
							+array[i].maxConcurrency
							+ "</td>");
				var td6 = $("<td>"
							+translateStatus(array[i].status)
							+ "</td>");
					
				if(array[i].status == 0 ||array[i].status == 2){
					var tr = $("<tr class=\"warning\"></tr>");
				}else if(array[i].status == 3 ||array[i].status == 4){
					var tr = $("<tr class=\"danger\"></tr>");
					
				}else{
					var tr = $("<tr></tr>");
				}
				
				tr.append(td1).append(td2).append(td3).append(td4).append(td5).append(td6);
				tr.appendTo(tby);
				
			/* 	var trdata = $("#db_detail_table").find("tr");
				alert(trdata.children().html());
				alert(trdata.next().next().html());
				trdata.next().html()); */
			}//循环json中的数据 
		},
		error : function(XMLHttpRequest,textStatus, errorThrown) {
			$.gritter.add({
				title: '警告',
				text: errorThrown,
				sticky: false,
				time: '5',
				class_name: 'gritter-warning'
			});
	
			return false;
		}
	});
}
function queryDbInfo(){
	$.ajax({ 
		type : "get",
		url : "${ctx}/db/"+$("#dbId").val(),
		dataType : "json", 
		success : function(data) {
			var dbInfo = data.data;
			$("#page-header-id").find('small').append(dbInfo.dbName);
			$("#db_detail_table").find('tr:eq(0) td:eq(1)').text(dbInfo.dbName);
			queryUser(dbInfo.createUser);
			$("#db_detail_table").find('tr:eq(2) td:eq(1)').text(date('Y-m-d H:i:s',dbInfo.createTime));
		},
		error : function(XMLHttpRequest,textStatus, errorThrown) {
			$.gritter.add({
				title: '警告',
				text: errorThrown,
				sticky: false,
				time: '5',
				class_name: 'gritter-warning'
			});
			return false;
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
			$("#db_detail_table").find('tr:eq(1) td:eq(1)').text(data.data.userName);
		},
		error : function(XMLHttpRequest,textStatus, errorThrown) {
			$.gritter.add({
				title: '警告',
				text: errorThrown,
				sticky: false,
				time: '5',
				class_name: 'gritter-warning'
			});
			return false;
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
        	window.location.href='${ctx}/detail/db/'+$("#dbId").val();
        }
	});
}

function pageinit(){
	checkboxControl();
	queryDbUser();
	queryDbInfo();
}
</script>
