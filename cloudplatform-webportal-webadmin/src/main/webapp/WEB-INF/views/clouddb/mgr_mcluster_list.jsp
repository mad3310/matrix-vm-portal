<%@ page language="java" pageEncoding="UTF-8"%>
<!-- /section:settings.box -->
<div class="page-content-area">
	<div class="page-header">
		<h1> 
			集群列表
			<!-- <small> 
				<i class="ace-icon fa fa-angle-double-right"></i> 
				overview &amp; stats
			</small> -->
		</h1>
	</div>
	<!-- /.page-header -->
</div>
<!-- /.page-content-area -->
<div class="row">
	<div class="widget-box widget-color-blue ui-sortable-handle col-xs-12">
		<div class="widget-header">
			<h5 class="widget-title">集群列表</h5>
			<div class="widget-toolbar no-border">
				<button class="btn btn-xs btn-success bigger" data-toggle="modal" data-target="#create-mcluster-modal">
					<i class="ace-icont fa fa-plus"></i>
					 创建集群
				</button>
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
							<th>集群名称</th>
							<th>集群所属用户</th>
							<th>
								创建时间 
							</th>
							<th class="hidden-480">当前状态</th>
							<!-- <th></th> -->
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

			<li>共<lable id="totalPage"></lable>页
			</li>
			<li>第<lable id="currentPage"></lable>页
			</li>
			<li>共<lable id="totalRows"></lable>条记录
			</li>
		</ul>
	</div>
	<div class="modal fade" id="create-mcluster-modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="margin-top:157px">
		<div class="modal-dialog">
			<div class="modal-content">
				<form id="create-mcluster-form" name="create-mcluster-form" class="form-horizontal" role="form" action="" method="post">
				<div class="col-xs-12">
					<h4 class="lighter">
						<a href="#modal-wizard" data-toggle="modal" class="blue"> 创建集群 </a>
					</h4>
					<div class="widget-box">
						<div class="widget-body">
							<div class="widget-main">
								<div class="form-group">
									<label class="col-sm-2 control-label" for="mcluster_name">集群名称</label>
									<div class="col-sm-8">
										<input class="form-control" name="mclusterName" id="mclusterName" type="text" />
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-sm btn-default" data-dismiss="modal">关闭</button>
					<button type="submit" class="btn btn-sm btn-primary" onclick="">创建</button>
				</div>
			</form>
			</div>
		</div>
	</div>
</div>
<link rel="stylesheet" href="${ctx}/static/styles/bootstrap/bootstrapValidator.min.css" />
<script src="${ctx}/static/scripts/bootstrap/bootstrapValidator.min.js"></script>

<script src="${ctx}/static/ace/js/jquery.dataTables.min.js"></script>
<script src="${ctx}/static/ace/js/jquery.dataTables.bootstrap.js"></script>
<script type="text/javascript">
var currentPage = 1; //第几页 
var recordsPerPage = 10; //每页显示条数
	
$(function(){
	//初始化 
	page_init();
	
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
		url : "${ctx}/mcluster/list/" + currentPage + "/" + recordsPerPage + "/" + mclusterName,
		dataType : "json", /*这句可用可不用，没有影响*/
		success : function(data) {
			var array = data.data.data;
			var tby = $("#tby");
			var totalPages = data.data.totalPages;
			
			function translateStatus(status){
				var statuStr;
				if(status == 0){
					return "启动";
				}else if(status  == 1){
					return "关闭";
				}else{
					return "异常";
				}
			}
			
			for (var i = 0, len = array.length; i < len; i++) {
				var td1 = $("<td class=\"center\">"
								+"<label class=\"position-relative\">"
								+"<input type=\"checkbox\" class=\"ace\"/>"
								+"<span class=\"lbl\"></span>"
								+"</label>"
							+"</td>");
				var td2 = $("<td>"
						+  "<a href=\"${ctx}/mcluster/detail/" + array[i].id+"\">"+array[i].mclusterName+"</a>"
						+ "</td>");
				var td3 = $("<td>"
						+ array[i].createUser
						+ "</td>");
				var td4 = $("<td>"
						+ array[i].createTime
						+ "</td>");
				var td5 = $("<td>"
						+ translateStatus(array[i].status)
						+ "</td>");
					
				/* var td6 = $("<td>"
						+"<div class=\"hidden-sm hidden-xs btn-group\">"
						+"<button class=\"btn disabled btn-xs btn-success\">"
						+"<i class=\"ace-icon fa fa-play-circle-o bigger-120\"></i>"
						+"</button>"
						+"<button class=\"btn disabled btn-xs btn-info\">"
							+"<i class=\"ace-icon fa fa-power-off bigger-120\"></i>"
						+"</button>"
						+"<button class=\"btn disabled btn-xs btn-danger\">"
							+"<i class=\"ace-icon fa fa-trash-o bigger-120\"></i>"
						+"</button>"
						+"</div>"
						+ "</td>"
				); */
					
				if(array[i].status == 3){
					var tr = $("<tr class=\"danger\"></tr>");
				}else{
					var tr = $("<tr></tr>");
				}
				
				tr.append(td1).append(td2).append(td3).append(td4).append(td5);
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
		},
		error : function(XMLHttpRequest,textStatus, errorThrown) {
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
		} else {
			currentPage--;
			queryByPage(currentPage,recordsPerPage);
		}
	});

	// 下一页
	$("#nextPage").click(function() {
		if (currentPage == $("#totalPage_input").val()) {
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
	

function formValidate() {
	$("#create-mcluster-form").bootstrapValidator({
	  message: '无效的输入',
         feedbackIcons: {
             valid: 'glyphicon glyphicon-ok',
             invalid: 'glyphicon glyphicon-remove',
             validating: 'glyphicon glyphicon-refresh'
         },
         fields: {
       	  mclusterName: {
                 validMessage: '请按提示输入',
                 validators: {
                     notEmpty: {
                         message: '集群名称不能为空!'
                     },
		          stringLength: {
		              max: 40,
		              message: '集群名过长'
		          }
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
