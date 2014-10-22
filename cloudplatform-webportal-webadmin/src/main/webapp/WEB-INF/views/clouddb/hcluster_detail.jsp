<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="page-content-area">
	<div id="page-header-id" class="page-header">
		<h1> 
			<a href="${ctx}/list/mcluster">物理机集群列表</a>
			<small id="headerHostName"> 
				<i class="ace-icon fa fa-angle-double-right"></i> 
			</small>
		</h1>
	</div>
	<input class="hidden" value="${hclusterId}" name="hclusterId" id="hclusterId" type="text" />
	<!-- /.page-header -->
	<div class="row">
		<div class="col-xs-12">
			<div class=" pull-right">
				<button type="button" class="btn btn-primary btn-xs" data-toggle="modal" data-target="#add-host-form">
					<i class="ace-icont fa fa-plus"></i>添加物理机
				</button>
			</div>
		</div>
		<div class="col-xs-12">
			<div style="margin-top: 8px;">
				<table class="table table-bordered" id="Mcluster_detail_table">
					<thead>
				      <tr style="background-image:none;background-color:#307ECC;color:#FFFFFF;">
				         <th>物理机名称</th>
				         <th>类型</th>
				         <th>ip</th>
				         <th>状态</th>
				         <th>操作</th>
				      </tr>
				   </thead>
				   <tbody id="tby">
					</tbody>
				</table>
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
	<div class="modal fade" id="add-host-form" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<form id="add_host_form" name="add_host_form" class="form-horizontal" role="form">
					<div class="col-xs-12">
						<h4 class="lighter">
							<a href="#modal-wizard-create-db-user" data-toggle="modal" class="blue"> 创建数据库用户 </a>
						</h4>
						<div class="widget-box">
							<div class="widget-body">
								<div class="widget-main">
									<div class="form-group">
										<input class="hidden" value="${hclusterId}" name="hclusterId" id="hclusterId" type="text" />
										<input class="hidden" value="0" name="status" id="status" type="text" />
										<label class="col-sm-offset-1 col-sm-2 control-label" for="username">物理机名</label>
										<div class="col-sm-5">
											<input class="form-control" name="username" id="username" type="text" />
										</div>
										<label class="control-label" for="maximum_concurrency">
											<a id="maxConcurrencyHelp" name="popoverHelp" rel="popover" data-container="body" data-toggle="popover" data-placement="right" data-trigger='hover' data-content="请输入字母数字或'_'" style="cursor:pointer; text-decoration:none;">
												<i class="ace-icon fa fa-question-circle blue bigger-125"></i>
											</a>
										</label>
									</div>
									<div class="form-group">
										<label class="col-sm-offset-1 col-sm-2 control-label" for="connection_type">物理机类型</label>
										<div class="col-sm-5">
											<select class="form-control" name="type" id="type">
												<option value="1">从机</option>
												<option value="0">主机</option>
											</select>
										</div>
										<label class="control-label" for="maximum_concurrency">
											<a id="maxConcurrencyHelp" name="popoverHelp" rel="popover" data-container="body" data-toggle="popover" data-placement="right" data-trigger='hover' data-content="主机是收集所有从机信息的物理机." style="cursor:pointer; text-decoration:none;">
												<i class="ace-icon fa fa-question-circle blue bigger-125"></i>
											</a>
										</label>
									</div>
									 <div class="form-group" name="dynamic-ip-input-mod">
								        <label class="col-sm-offset-1 col-sm-2 control-label">IP地址</label>
								        <div class="col-sm-5">
								            <input type="text" class="form-control" name="hostIp" />
								        </div>
								        <label class="control-label" for="maximum_concurrency">
											<a id="maxConcurrencyHelp" name="popoverHelp" rel="popover" data-container="body" data-toggle="popover" data-placement="right" data-trigger='hover' data-content="请输入物理机ip示例:192.168.33.12" style="cursor:pointer; text-decoration:none;">
												<i class="ace-icon fa fa-question-circle blue bigger-125"></i>
											</a>
										</label>
								    </div>
								   <!--  <div class="form-group">
								    	<label class="col-sm-offset-1 col-sm-2 control-label">描述</label>
									  	<div class="col-sm-5">
									      <textarea  id="descn" name="descn" class="form-control" rows="3" placeholder=""></textarea>
									  	</div>
								        <label class="control-label" for="maximum_concurrency">
											<a id="maxConcurrencyHelp" name="popoverHelp" rel="popover" data-container="body" data-toggle="popover" data-placement="right" data-trigger='hover' data-content="关于物理机信息、状态等描述." style="cursor:pointer; text-decoration:none;">
												<i class="ace-icon fa fa-question-circle blue bigger-125"></i>
											</a>
										</label>
								  </div> -->
								</div>
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-sm btn-default" data-dismiss="modal">关闭</button>
						<button id="add_host_botton" type="button" class="btn btn-sm disabled btn-primary" onclick="addHost()">添加</button>
					</div>
				</form>
				</div>
			</div>
		</div>
</div>
<script type="text/javascript">
$(function(){
	//隐藏搜索框
	$('#nav-search').addClass("hidden");
	$('[name = "popoverHelp"]').popover();
	queryHost();
})

function queryHost(){
	$("#tby tr").remove();
	$.ajax({ 
		type : "get",
		url : "${ctx}/host/"+$("#hclusterId").val(),
		dataType : "json", 
		success : function(data) {
			error(data);
			var array = data.data;
			var tby = $("#tby");
 			$("#headerHostName").append(array[0].hcluster.hclusterName);
			for (var i = 0, len = array.length; i < len; i++) {
				var td0 = $("<input name=\"host_id\" value= \""+array[i].id+"\" type=\"hidden\"/>");
				var td1 = $("<td>"
					    + array[i].containerName
				        + "</td>");
				var	td2 = $("<td>"
						+ array[i].type
						+ "</td>");
				var	td3 = $("<td>"
						+ array[i].hostId
						+ "</td>");
				var	td4 = $("<td>"
						+ array[i].ipAddr
						+ "</td>");
				var	td5 = $("<td>"
						+ array[i].mountDir
						+ "</td>");
				var	td6 = $("<td>"
						+ array[i].zookeeperId
						+ "</td>");
				var	td7 = $("<td>"
						+ "正常"
						+ "</td>");
				var td8 = $("<td>"
						+"<div class=\"hidden-sm hidden-xs action-buttons\">"
						+"<a class=\"blue\" href=\"#\" onclick=\"deleteHost(this)\" data-toggle=\"modal\" data-target=\"#\">"
							+"<i class=\"ace-icon fa fa-power-off bigger-120\"></i>"
						+"</a>"
						+"</div>"
						+ "</td>"
				);
				var tr = $("<tr></tr>");;				
				tr.append(td0).append(td1).append(td2).append(td3).append(td4).append(td5).append(td6).append(td7).append(td8);
				tr.appendTo(tby);
			}
		}
	});
}

function confirmframe(title,content,question,ok,cancle){
	$.widget("ui.dialog", $.extend({}, $.ui.dialog.prototype, {
		_title : function(title) {
			var $title = this.options.title || '&nbsp;'
			if (("title_html" in this.options)
					&& this.options.title_html == true)
				title.html($title);
			else
				title.text($title);
		}
	}));
	
	$('#dialog-confirm').removeClass('hide').dialog({
		resizable : false,
		modal : true,
		title : "<div class='widget-header'><h4 class='smaller'>"+title,
		title_html : true,
		buttons : [
				{
					html : "确定",
					"class" : "btn btn-primary btn-xs",
					click : function() {
						ok();
						$(this).dialog("close");
					}
				},
				{
					html : "取消",
					"class" : "btn btn-xs",
					click : function() {
						$(this).dialog("close");
					}
				} ]
	});
	$('#dialog-confirm-content').html(content);
	$('#dialog-confirm-question').html(question);
}
function deleteHost(obj){
	function deleteCmd(){
		var hostId =$(obj).parents("tr").find('[name="host_id"]').val();
		$.ajax({
			url:'${ctx}/host'+hostId,
			type:'delete',
			success:function(data){
				error(data);
				queryHost();
			}
		});
	}
	confirmframe("删除物理机","","请耐心等待...",startCmd);
}
function addHost(){
	$.ajax({
		url: '${ctx}/host',
        type: 'post',
        dataType: 'text',
        data: $("#add_host_form").serialize(),
        success: function (data) {
        	error(data);
        	$("#create-dbuser-form").modal("hide");
        	queryHost();
			$('#add_host_form').find(":input").not(":button,:submit,:reset,:hidden").val("").removeAttr("checked").removeAttr("selected");
			$('#add_host_form').data('bootstrapValidator').resetForm();
			$('#type').val(3);
			$('#add_host_botton').addClass('disabled');
        }
	});
}
</script>
