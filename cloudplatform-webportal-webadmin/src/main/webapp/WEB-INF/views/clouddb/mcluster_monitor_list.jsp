<%@ page language="java" pageEncoding="UTF-8"%>
<div class="page-content-area">
	<div class="page-header">
		<h1> 
			container集群监控列表
		</h1>
	</div>
	<!-- /.page-header -->
	<div class="row">
		<div class="widget-box widget-color-blue ui-sortable-handle col-xs-12">
			<div class="widget-header">
				<h5 class="widget-title">container集群监控列表</h5>
			</div>
			<div class="widget-body">
				<div class="widget-main no-padding">
					<table id="mcluster_list" class="table table-striped table-bordered table-hover">
					<thead>
						<tr>
							<th>container集群名称</th>
							<th>VIP节点地址</th>
							<th>所属物理机集群</th>
							<th class="hidden-480">当前状态</th>
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
<!-- /.page-content-area -->

<script type="text/javascript">
 $(function(){
	//初始化
	page_init();
});	
function queryMclusterMonitor() {
	$("#tby tr").remove();
	$.ajax({ 
		type : "get",
		url : "${ctx}/monitor/mcluster",
		dataType : "json", /*这句可用可不用，没有影响*/
		contentType : "application/json; charset=utf-8",
		success : function(data) {
			error(data);
			var array = data.data;
			var tby = $("#tby");
			
			for (var i = 0, len = array.length; i < len; i++) {
				var td1 = $("<td>"
							+ "<a href=\"${ctx}/detail/mcluster/" +array[i].ip +"/monitor\">"+array[i].mclusterName+"</a>"
							+ "</td>");
				var td2 = $("<td>"
							+ array[i].ip
							+ "</td>");

			if(array[i].hclusterName){
					var td3 = $("<td>"
 							+ array[i].hclusterName
							+ "</td>");
				}else{
					var td3 = $("<td>"
 							+ "-"
							+ "</td>");
				}
				var td4 = $("<td>"
							+ translateStatus(array[i].status)
							+ "</td>");
				

				if(array[i].status == 0 ||array[i].status == 5||array[i].status == 13){
					var tr = $("<tr class=\"warning\"></tr>");
				}else if(array[i].status == 3 ||array[i].status == 4||array[i].status == 14){
					var tr = $("<tr class=\"danger\"></tr>");
				}else{
					var tr = $("<tr></tr>");
				}
				
				tr.append(td1).append(td2).append(td3).append(td4);
				tr.appendTo(tby);
				
			}//循环json中的数据 
		}
	});
}
function page_init(){
 queryMclusterMonitor();
}
</script>
