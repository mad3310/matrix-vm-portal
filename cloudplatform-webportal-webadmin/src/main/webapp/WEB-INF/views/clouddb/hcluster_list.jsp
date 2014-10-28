<%@ page language="java" pageEncoding="UTF-8"%>
<!-- /section:settings.box -->
<div class="page-content-area">
	<div class="page-header">
		<h1> 
			物理机集群列表
		</h1>
	</div>
	<!-- /.page-header -->
	<div class="row">
		<div class="widget-box widget-color-blue ui-sortable-handle col-xs-12">
			<div class="widget-header">
				<h5 class="widget-title">物理机集群列表</h5>
				<div class="widget-toolbar no-border">
					<button class="btn btn-white btn-primary btn-xs" data-toggle="modal" data-target="#create-hcluster-modal">
						<i class="ace-icont fa fa-plus"></i>
						 创建物理机集群
					</button>
				</div>
			</div>
		
			<div class="widget-body">
				<div class="widget-main no-padding">
					<table id="hcluster_list" class="table table-striped table-bordered table-hover">
						<thead>
							<tr>
								<th class="center">
									<label class="position-relative">
										<input type="checkbox" class="ace" />
										<span class="lbl"></span>
									</label>
								</th>
								<th>物理机集群名称</th>
								<th>编号</th>
								<th>创建时间 </th>
								<th class="hidden-480">当前状态</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody id="tby">
						</tbody>
					</table>
				</div>
			</div>
		</div>
		<div class="col-xs-3">
			<small><font color="gray">*注：点击编号可查看详情.</font></small>
		</div>
		<div id="pageControlBar" class="col-xs-6">
			<input type="hidden" id="totalPage_input" />
			<ul class="pager">
				<li><a href="javascript:void(0);" id="firstPage">&laquo首页</a></li>
				<li><a href="javascript:void(0);" id="prevPage">上一页</a></li>
				<li><a href="javascript:void(0);" id="nextPage">下一页</a></li>
				<li><a href="javascript:void(0);" id="lastPage">末页&raquo</a></li>
	
				<li>共<lable id="totalPage"></lable>页
				</li>
				<li>第<lable id="currentPage"></lable>页
				</li>
				<li>共<lable id="totalRows"></lable>条记录
				</li>
			</ul>
		</div>
		
		<div class="modal fade" id="create-hcluster-modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="margin-top:157px">
			<div class="modal-dialog">
				<div class="modal-content">
					<form id="create-hcluster-form" name="create-hcluster-form" class="form-horizontal" role="form">
					<div class="col-xs-12">
						<h4 class="lighter">
							<a href="#modal-wizard" data-toggle="modal" class="blue">创建物理机集群 </a>
						</h4>
						<div class="widget-box">
							<div class="widget-body">
								<div class="widget-main">
									<div class="form-group">
										<label class="col-sm-4 control-label" for="hcluster_name">物理机集群名称</label>
										<div class="col-sm-6">
											<input class="form-control" name="hclusterNameAlias" id="hclusterNameAlias" type="text" />
										</div>
										<label class="control-label">
											<a name="popoverHelp" rel="popover" data-container="body" data-toggle="popover" data-placement="right" data-trigger='hover' data-content="集群别名应能概括此集群的信息，可用汉字!" style="cursor:pointer; text-decoration:none;">
												<i class="ace-icon fa fa-question-circle blue bigger-125"></i>
											</a>
										</label>
									</div>
									<div class="form-group">
										<label class="col-sm-4 control-label" for="hcluster_name">编号</label>
										<div class="col-sm-6">
											<input class="form-control" name="hclusterName" id="hclusterName" type="text" />
										</div>
										<label class="control-label">
											<a name="popoverHelp" rel="popover" data-container="body" data-toggle="popover" data-placement="right" data-trigger='hover' data-content="请输入字母数字或'_'." style="cursor:pointer; text-decoration:none;">
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
						<button id="create-hcluster-botton" type="button" class="btn btn-sm disabled btn-primary" onclick="createHcluster()">创建</button>
					</div>
				</form>
				</div>
			</div>
		</div>
		<div id="dialog-confirm" class="hide">
			<div id="dialog-confirm-content" class="alert alert-info bigger-110">
			</div>
			<div class="space-6"></div>
			<p id="dialog-confirm-question" class="bigger-110 bolder center grey">
			</p>
		</div>
	</div>
</div>
<!-- /.page-content-area -->

<link rel="stylesheet" href="${ctx}/static/styles/bootstrap/bootstrapValidator.min.css" />
<script src="${ctx}/static/scripts/bootstrap/bootstrapValidator.min.js"></script>

<script src="${ctx}/static/ace/js/jquery.dataTables.min.js"></script>
<script src="${ctx}/static/ace/js/jquery.dataTables.bootstrap.js"></script>

<script type="text/javascript">
var currentPage = 1; //第几页 
var recordsPerPage = 10; //每页显示条数
var queryBuildStatusrefresh;//刷新handler
	
$(function(){
	//初始化 
	page_init();
	$('[name = "popoverHelp"]').popover();
	$(document).on('click', 'th input:checkbox' , function(){
		var that = this;
		$(this).closest('table').find('tr > td:first-child input:checkbox')
		.each(function(){
			this.checked = that.checked;
			$(this).closest('tr').toggleClass('selected');
		});
	});
});

function queryByPage(currentPage,recordsPerPage) {
	$("#tby tr").remove();
	var mclusterName = $("#nav-search-input").val()?$("#nav-search-input").val():'null';
	$.ajax({
		type : "get",
		url : "${ctx}/hcluster/" + currentPage + "/" + recordsPerPage + "/" + mclusterName,
		dataType : "json", /*这句可用可不用，没有影响*/
		success : function(data) {
			error(data);
			var array = data.data.data;
			var tby = $("#tby");
			var totalPages = data.data.totalPages;
			
			for (var i = 0, len = array.length; i < len; i++) {
				var td1 = $("<td class=\"center\">"
								+"<label class=\"position-relative\">"
								+"<input name=\"hcluster_id\" value= \""+array[i].id+"\" type=\"checkbox\" class=\"ace\"/>"
								+"<span class=\"lbl\"></span>"
								+"</label>"
							+"</td>");
				var td2 = $("<td>"
						+ array[i].hclusterNameAlias
						+ "</td>");
				var td3 = $("<td>"
						+  "<a href=\"${ctx}/detail/hcluster/" + array[i].id+"\">"+array[i].hclusterName+"</a>"
						+ "</td>");
				var td4 = $("<td>"
						+ date('Y-m-d H:i:s',array[i].createTime)
						+ "</td>");
				var td5 = $("<td>"
						+ translateStatus(array[i].status)
						+ "</td>");
				var td6 = $("<td>"
						+"<div class=\"hidden-sm hidden-xs  action-buttons\">"
						+"<a class=\"red\" href=\"#\" onclick=\"deleteHcluster(this)\" data-toggle=\"modal\" data-target=\"#\">"
							+"<i class=\"ace-icon fa fa-trash-o bigger-120\"></i>"
						+"</a>"
						+"</div>"
						+ "</td>"
				);
					
				if(array[i].status == 3){
					var tr = $("<tr class=\"danger\"></tr>");
				}else{
					var tr = $("<tr></tr>");
				}
				
				tr.append(td1).append(td2).append(td3).append(td4).append(td5).append(td6);
				tr.appendTo(tby);
			}//循环json中的数据 
			
			if (totalPages <= 1) {
				$("#pageControlBar").hide();
			} else {
				$("#pageControlBar").show();
				$("#totalPage_input").val(totalPages);
				$("#currentPage").html(currentPage);
				$("#totalRows").html(data.data.totalRecords);
				$("#totalPage").html(totalPages);
			}
		}
	});
   }

function pageControl() {
	// 首页
	$("#firstPage").bind("click", function() {
		currentPage = 1;
		queryByPage(currentPage,recordsPerPage);
	});

	// 上一页
	$("#prevPage").click(function() {
		if (currentPage == 1) {
			$.gritter.add({
				title: '警告',
				text: '已到达首页',
				sticky: false,
				time: '5',
				class_name: 'gritter-warning'
			});
	
			return false;
			
		} else {
			currentPage--;
			queryByPage(currentPage,recordsPerPage);
		}
	});

	// 下一页
	$("#nextPage").click(function() {
		if (currentPage == $("#totalPage_input").val()) {
			$.gritter.add({
				title: '警告',
				text: '已到达末页',
				sticky: false,
				time: '5',
				class_name: 'gritter-warning'
			});
	
			return false;
			
		} else {
			currentPage++;
			queryByPage(currentPage,recordsPerPage);
		}
	});

	// 末页
	$("#lastPage").bind("click", function() {
		currentPage = $("#totalPage_input").val();
		queryByPage(currentPage,recordsPerPage);
	});
}

function searchAction(){
	$('#nav-search-input').bind('keypress',function(event){
        if(event.keyCode == "13")    
        {
        	queryByPage(currentPage, recordsPerPage);
        }
    });
}
	
//创建Container集群表单验证
function formValidate() {
	$("#create-hcluster-form").bootstrapValidator({
	  message: '无效的输入',
         feedbackIcons: {
             valid: 'glyphicon glyphicon-ok',
             invalid: 'glyphicon glyphicon-remove',
             validating: 'glyphicon glyphicon-refresh'
         },
         fields: {
       	  hclusterName: {
                 validMessage: '请按提示输入',
                 validators: {
                     notEmpty: {
                         message: '物理机集群名称不能为空!'
                     },
			          stringLength: {
			              max: 40,
			              message: '物理机集群名过长'
			          },regexp: {
		                  regexp: /^([a-zA-Z_0-9]*)$/,
  		                  message: "请输入字母数字或'_'"
                 	  },
                 	 remote: {
	                        message: '物理机集群名已存在!',
	                        url: "${ctx}/hcluster/validate"
	                    }
	             }
         	}	
         }
     }).on('error.field.bv', function(e, data) {
    	 $('#create-hcluster-botton').addClass("disabled");
     }).on('success.field.bv', function(e, data) {
    	 $('#create-hcluster-botton').removeClass("disabled");
     });
}

function createHcluster(){
	$.ajax({
		type : "post",
		url : "${ctx}/hcluster",
		data :$('#create-hcluster-form').serialize(),
		success:function (data){
			error(data);
			$('#create-hcluster-form').find(":input").not(":button,:submit,:reset,:hidden").val("").removeAttr("checked").removeAttr("selected");
			$('#create-hcluster-form').data('bootstrapValidator').resetForm();
			$('#create-hcluster-botton').addClass('disabled');
			$('#create-hcluster-modal').modal('hide');
			//延时一秒刷新列表
			setTimeout("queryByPage(currentPage, recordsPerPage)",1000);
		}
	});
}

function deleteHcluster(obj){
	var tr = $(obj).parents("tr");
	var hclusterId =tr.find('[name="hcluster_id"]').val();
	$.ajax({
		url:'${ctx}/hcluster/isExistHostOnHcluster/validate',
		type:'post',
		data:{ 'hclusterId' : hclusterId },
		success:function(data){
			if(data.valid){  //data.valid为true时可删除
				function deleteCmd(){
					$.ajax({
						url:'${ctx}/hcluster/'+hclusterId,
						type:'delete',
						success:function(data){
							error(data);
							queryByPage(currentPage, recordsPerPage);
						}
					});
				}
				confirmframe("删除物理机集群","删除物理机集群后将不能恢复!","您确定要删除?",deleteCmd);
			}else{
				warn("该集群中含有物理机,删除完物理机后,才能执行此操作!",3000);
			}
		}
	});
}

function page_init(){
	queryByPage(currentPage, recordsPerPage);
	searchAction();
	formValidate();
	pageControl();
}
</script>