<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="page-content-area">
	<div class="page-header">
		<h3>数据库用户列表</h3>
	    <div class="input-group pull-right">
		<form class="form-inline">
			<!-- <div class="form-group">
				<select class="form-control">
					<option value="0">请选择查询条件</option>
					<option value="1">按用户名查询</option>
					<option value="2">按所属数据库查询</option>
					<option value="3">按当前状态查询</option>
				</select>
			</div> -->
			<div class="form-group">
				<input type="text" class="form-control" placeholder="请输入关键字">
			</div>
			<div class="form-group">
				<input type="date" class="form-control" placeholder="yyyy-MM-dd">
			</div>
			<button class="btn btn-sm btn-default" type="button"><i class="ace-icon fa fa-search"></i>搜索</button>
			<button class="btn btn-sm btn-info" type="button" id="dbuseradvancedSearch">高级搜索</button>
		</form>
	</div>
	</div>
		<!-- /.page-header -->
		<div id="dbuseradvancedSearch-div" style="display:none;overflow:hidden;">
		<form class="form-horizontal" role="form">
            					<div class="form-group col-md-4 col-sm-6 col-xs-12">
            						<lable class="col-md-6 control-label" for="userName"><b>用户名</b> <i class="ace-icon fa fa-user blue bigger-125"></i></lable>
            						<div class="col-md-6">
            							<input type="text" class="form-control" id="userName" placeholder="用户名">
            						</div>
            					
            					</div>
            					<div class="form-group col-md-4 col-sm-6 col-xs-12">
            						<lable class="col-md-6 control-label" for="userDb"><b>所属数据库</b> <i class="ace-icon fa fa-database blue bigger-125"></i></lable>
            						<div class="col-md-6">
            							<input type="text" class="form-control" id="userDb" placeholder="所属数据库">
            						</div>
            						
            					</div>
            					<div class="form-group col-md-4 col-sm-6 col-xs-12">
            						<lable class="col-md-6 control-label" for="userAuthority"><b>用户权限</b> <i class="ace-icon fa fa-cog blue bigger-125"></i></lable>
            						<div class="col-md-6">
            							<!-- <input type="text" class="form-control" id="userAuthority" placeholder="所属Mcluster"> -->
            							<select class="form-control" id="userAuthority">
            								<option value="">管理员</option>
            								<option value="">读写</option>
            								<option value="">。。。</option>
            							</select>
            						</div>
            					
            					</div>
            					<div class="form-group col-md-4 col-sm-6 col-xs-12">
            						<lable class="col-md-6 control-label" for="userIp"><b>ip地址</b> <i class="ace-icon fa fa-info-circle blue bigger-125"></i></lable>
            						<div class="col-md-6">
            							<input type="text" class="form-control" id="userIp" placeholder="ip地址">
            						</div>
            						
            					</div>
            					<div class="form-group col-md-4 col-sm-6 col-xs-12">
            						<lable class="col-md-6 control-label" for="userLimit"><b>频次限制</b> <i class="ace-icon fa fa-cog blue bigger-125"></i></lable>
            						<div class="col-md-6">
            							<select class="form-control" id="userLimit">
            								<option value="">50</option>
            								<option value="">100</option>
            								<option value="">。。。</option>
            							</select>
            						</div>
            					
            					</div>
            					
            					
            					<div class="form-group col-md-4 col-sm-6 col-xs-12">
            						<lable class="col-md-6 control-label" for="PhyMechineRunState"><b>当前状态</b> <i class="ace-icon fa fa-cogs blue bigger-125"></i></lable>
            						<div class="col-md-6">
            							<select class="form-control" id="PhyMechineRunState">
            								<option value="">创建失败</option>
            								<option value="">未审核</option>
            								<option value="">。。。</option>
            							</select>
            						</div>         
            					</div>
            					<div class="form-group">
            					<div class="col-sm-offset-2 col-sm-10">
    							<button class="btn btn-sm btn-info pull-right" type="button" style="margin-left:5px;"><i class="ace-icon fa fa-search"></i>搜索</button>
    							<button class="btn btn-sm btn-default pull-right" type="reset"><i class="ace-icon fa fa-refresh"></i>清空</button>
    							
    							</div>
    							</div>
    						
            				</form>
	</div>
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
<!-- /.page-content-area -->
<script type="text/javascript">
var currentPage = 1; //第几页 
var recordsPerPage = 15; //每页显示条数
var currentSelectedLineDbName = 1;
	
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
 
function buildUser() {
	var ids = $("[name='db_user_id']:checked");
	var str="";
	var flag = 0; //0:无数据 -1:错误
	ids.each(function(){
		if($(this).closest("tr").children().last().html() == "正常"){
			$.gritter.add({
				title: '警告',
				text: '创建用户只能选择待审核数据!',
				sticky: false,
				time: '3000',
				class_name: 'gritter-warning'
			});
			flag = -1;
			return false;
		}else{
			str +=($(this).val())+",";
 			flag += 1;
		}
	});
	
	if(flag > 0) {
		$.ajax({ 
			type : "post",
			url : "${ctx}/dbUser",
			dataType : "json",
			data : {'dbUserId':str},
			success : function(data) {
				if(error(data)) return;
				if(data.result == 1) {
					$("#titleCheckbox").attr("checked", false);
					queryByPage(currentPage,recordsPerPage);
				}
			}
		});
	} else if (flag == 0){
		$.gritter.add({
		title: '警告',
		text: '请选择数据！',
		sticky: false,
		time: '5',
		class_name: 'gritter-warning'
	});

	return false;
	}else{
		return false;
	}
}
 
	function queryByPage(currentPage,recordsPerPage) {
		$("#tby tr").remove();
		var dbName = $("#nav-search-input").val()?$("#nav-search-input").val():'null';
		$.ajax({ 
			type : "get",
			url : "${ctx}/dbUser/" + currentPage + "/" + recordsPerPage+"/" + dbName,
			dataType : "json", /*这句可用可不用，没有影响*/
			contentType : "application/json; charset=utf-8",
			success : function(data) {
				if(error(data)) return;
				var array = data.data.data;
				var tby = $("#tby");
				var totalPages = data.data.totalPages;
				
				for (var i = 0, len = array.length; i < len; i++) {
					var td1 = $("<td class=\"center\">"
									+"<label class=\"position-relative\">"
									+"<input name=\"db_user_id\" value= \""+array[i].id+"\" type=\"checkbox\" class=\"ace\"/>"
									+"<span class=\"lbl\"></span>"
									+"</label>"
								+"</td>");
					var td2 = $("<td>"
							+ array[i].username
							+ "</td>");
					var td3 = $("<td>"
							+ "<a href=\"${ctx}/detail/db/" + array[i].dbId+"\">"+array[i].db.dbName+"</a>"
							+ "</td>");
					if(array[i].type == "3")
					{
						var td4 = $("<td>读写</td>");
					}else{
						var td4 = $("<td>管理员</td>");
					}
					var td5 = $("<td>"
							+ array[i].acceptIp
							+ "</td>");
					var td6 = $("<td>"
							+ array[i].maxConcurrency
							+ "</td>");
					var td7 = $("<td><a>"
							+ translateStatus(array[i].status)
							+ "</a></td>"); 
						
					if(array[i].status == 0 ||array[i].status == 5||array[i].status == 13){
						var tr = $("<tr class=\"warning\"></tr>");
					}else if(array[i].status == 3 ||array[i].status == 4||array[i].status == 14){
						var tr = $("<tr class=\"default-danger\"></tr>");
						
					}else{
						var tr = $("<tr></tr>");
					}
					tr.append(td1).append(td2).append(td3).append(td4).append(td5).append(td6).append(td7);
					tr.appendTo(tby);
				}
				
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
	
	function page_init(){
		searchAction();
		queryByPage(currentPage,recordsPerPage);
		pageControl();
	}
</script>
