<%@ page language="java" pageEncoding="UTF-8"%>
<%@include file="/common/common.jsp"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Bootstrap 101 Template</title>
<script src="${ctx}/js/pageControl.js"></script>
</head>
<body>
	<div class="container">
		<%@include file="header.jsp"%>
		<div id="wrap">
			<div class="row">
				<div class="col-md-6">
					<h3 class="text-left">Mcluster管理</h3>

				</div>
				<div class="col-md-6">
					<form class="navbar-form navbar-right" role="search">
						<div class="form-group">
							<input id="mclusterName" type="text" class="form-control" />
						</div>
						<button type="submit" class="btn btn-default">搜索</button>
					</form>
				</div>
				<hr
					style="FILTER: alpha(opacity = 0, finishopacity = 100, style = 1)"
					width="100%" color=#987cb9 SIZE=3></hr>
			</div>

			<div class="row clearfix">
				<div class="col-md-3 column">
					<h2>通告：</h2>
					<p>关于数据库使用的通知、帮助和注意事项。</p>
					<p>
						<a class="btn" href="#">查看详细使用教程 »</a>
					</p>
				</div>
				<div class="col-md-9 column">

					<button id="create_mcluster" type="button" class="btn btn-primary"
						data-toggle="modal">添加Mcluster</button>


					<table id="userdata"
						class="table table-striped table-hover table-responsive">
						<thead>
							<tr>
								<th>Mcluster名称</th>
								<th>创建时间</th>
								<th>当前状态</th>
							</tr>
						</thead>
						<tbody id="tby"></tbody>
					</table>
					<input type="hidden" id="totalPage_input" />
					<ul class="pager">
						<li><a href="javascript:void(0);" id="firstPage">&laquo首页</a></li>
						<li><a href="javascript:void(0);" id="prevPage">上一页</a></li>
						<li><a href="javascript:void(0);" id="nextPage">上一页</a></li>
						<li><a href="javascript:void(0);" id="lastPage">末页&raquo</a></li>

						<li>共<lable id="totalPage"></lable>页
						</li>
						<li>第<lable id="currentPage"></lable>页
						</li>
						<li>共<lable id="totalRows"></lable>条记录
						</li>
					</ul>
				</div>

			</div>

		</div>
		<%@include file="footer.jsp"%>
	</div>
</body>

<script type="text/javascript">
	/**
	 * V1.0
	 */
	$(document).ready(function() {

						var currentPage = 1; //第几页 
						var recordsPerPage = 10; //每页显示条数
						$("#signin").show();//显示header中登录框
						$("#sqlcluster").addClass("active");//高亮显示页面名

						//分页查询  
						var queryByPage = function() {
							$("#tby tr").remove();
							$.ajax({
										type : "post",
										url : "${ctx}/mcluster/list?currentPage="
												+ currentPage
												+ "&recordsPerPage="
												+ recordsPerPage
												+ "&mclusterName="
												+ $("#mclusterName").text,
										dataType : "json", /*这句可用可不用，没有影响*/
										contentType : "application/json; charset=utf-8",
										success:function(data) {
											var array = data.data.data;
											var tby = $("#tby");
											var totalPages = data.totalPages;
											$("#totalPage_input").val(totalPages);
											$("#currentPage").html(currentPage);
											$("#totalRows").html(data.totalRecords);
											$("#totalPage").html(totalPages);
											//循环json中的数据 
											for (var i = 0, len = array.length; i < len; i++) {
												var td1 = $("<td>"
														+ array[i].mclusterName
														+ "</td>");
												var td2 = $("<td>"
														+ array[i].createTime
														+ "</td>");
												var td3 = $("<td>"
														+ array[i].status
														+ "</td>");
												var tr = $("<tr></tr>");
												tr.append(td1).append(td2).append(td3);
												tr.appendTo(tby);
											}
										},
										error:function(XMLHttpRequest,
												textStatus, errorThrown) {
											alert(errorThrown);
										}
									});
						}
						//初始化列表 
						queryByPage(currentPage, recordsPerPage);
					});
</script>

</html>
