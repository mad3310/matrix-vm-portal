<%@ page language="java" pageEncoding="UTF-8"%>
<div class="page-content-area">
	<div class="page-header">
		<h3> 
			container集群监控列表
		</h3>
		<div class="input-group pull-right">
		<form class="form-inline">
			<!-- <div class="form-group">
				<select class="form-control">
					<option value="0">请选择查询条件</option>
					<option value="1">按container集群名称查询</option>
					<option value="2">按所属物理集群查询</option>
					<option value="3">按当前状态查询</option>
				</select>
			</div> -->
			<div class="form-group">
				<input type="text" class="form-control" placeholder="请输入关键字">
			</div>
			<div class="form-group">
				<input type="date" class="form-control" placeholder="yyyy-MM-dd">
			</div>
			<button class="btn btn-sm btn-default" type="button"><i class="icon-search"></i>搜索</button>
			<button class="btn btn-sm btn-info" type="button" id="monitorAdvancedSearch">高级搜索</button>
		</form>
		
	</div>
	</div>
	<!-- /.page-header -->
	<div id="monitorAdvancedSearch-div" style="display:none;overflow:hidden;">
		<form class="form-horizontal" role="form">
            					<div class="form-group col-md-4 col-sm-6 col-xs-12">
            						<lable class="col-md-6 control-label" for="monitorContainer"><b>container集群名称</b> <i class="ace-icon fa fa-info-circle blue bigger-125"></i></lable>
            						<div class="col-md-6">
            							<input type="text" class="form-control" id="monitorContainer" placeholder="container集群名称">
            						</div>
            						
            					</div>
            					
            					<div class="form-group col-md-4 col-sm-6 col-xs-12">
            						<lable class="col-md-6 control-label" for="VipAddress"><b>Vip节点地址</b> <i class="ace-icon fa fa-info-circle blue bigger-125"></i></lable>
            						<div class="col-md-6">
            							<input type="text" class="form-control" id="VipAddress" placeholder="Vip节点地址">
            						</div>
            						
            					</div>
            					<div class="form-group col-md-4 col-sm-6 col-xs-12">
            						<lable class="col-md-6 control-label" for="monitorPhyM"><b>所属物理机集群</b> <i class="ace-icon fa fa-question-circle blue bigger-125"></i></lable>
            						<div class="col-md-6">
            							<input type="text" class="form-control" id="monitorPhyM" placeholder="所属物理机集群">
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
            						<lable class="col-sm-4 control-label" for="monitorContainer"><b>container集群名称</b></lable>
            						<div class="col-sm-7">
            							<input type="text" class="form-control" id="monitorContainer" placeholder="container集群名称">
            						</div>
            						<label class="control-label"><i class="ace-icon fa fa-info-circle blue bigger-125"></i></label>
            					</div>
            					
            					<div class="form-group">
            						<lable class="col-sm-4 control-label" for="VipAddress"><b>Vip节点地址</b></lable>
            						<div class="col-sm-7">
            							<input type="text" class="form-control" id="VipAddress" placeholder="Vip节点地址">
            						</div>
            						<label class="control-label"><i class="ace-icon fa fa-info-circle blue bigger-125"></i></label>
            					</div>
            					<div class="form-group">
            						<lable class="col-sm-4 control-label" for="monitorPhyM"><b>所属物理机集群</b></lable>
            						<div class="col-sm-7">
            							<input type="text" class="form-control" id="monitorPhyM" placeholder="所属物理机集群">
            						</div>
            						<label class="control-label"><i class="ace-icon fa fa-question-circle blue bigger-125"></i></label>
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
	<div id="monitorAdvancedSearch-div" style="display:none;overflow:hidden;">
		<form class="form-horizontal" role="form">
            					<div class="form-group col-md-4 col-sm-6 col-xs-12">
            						<lable class="col-md-6 control-label" for="monitorContainer"><b>container集群名称</b> <i class="ace-icon fa fa-info-circle blue bigger-125"></i></lable>
            						<div class="col-md-6">
            							<input type="text" class="form-control" id="monitorContainer" placeholder="container集群名称">
            						</div>
            						
            					</div>
            					
            					<div class="form-group col-md-4 col-sm-6 col-xs-12">
            						<lable class="col-md-6 control-label" for="VipAddress"><b>Vip节点地址</b> <i class="ace-icon fa fa-info-circle blue bigger-125"></i></lable>
            						<div class="col-md-6">
            							<input type="text" class="form-control" id="VipAddress" placeholder="Vip节点地址">
            						</div>
            						
            					</div>
            					<div class="form-group col-md-4 col-sm-6 col-xs-12">
            						<lable class="col-md-6 control-label" for="monitorPhyM"><b>所属物理机集群</b> <i class="ace-icon fa fa-question-circle blue bigger-125"></i></lable>
            						<div class="col-md-6">
            							<input type="text" class="form-control" id="monitorPhyM" placeholder="所属物理机集群">
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
            						<lable class="col-sm-4 control-label" for="monitorContainer"><b>container集群名称</b></lable>
            						<div class="col-sm-7">
            							<input type="text" class="form-control" id="monitorContainer" placeholder="container集群名称">
            						</div>
            						<label class="control-label"><i class="ace-icon fa fa-info-circle blue bigger-125"></i></label>
            					</div>
            					
            					<div class="form-group">
            						<lable class="col-sm-4 control-label" for="VipAddress"><b>Vip节点地址</b></lable>
            						<div class="col-sm-7">
            							<input type="text" class="form-control" id="VipAddress" placeholder="Vip节点地址">
            						</div>
            						<label class="control-label"><i class="ace-icon fa fa-info-circle blue bigger-125"></i></label>
            					</div>
            					<div class="form-group">
            						<lable class="col-sm-4 control-label" for="monitorPhyM"><b>所属物理机集群</b></lable>
            						<div class="col-sm-7">
            							<input type="text" class="form-control" id="monitorPhyM" placeholder="所属物理机集群">
            						</div>
            						<label class="control-label"><i class="ace-icon fa fa-question-circle blue bigger-125"></i></label>
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
				<h5 class="widget-title">container集群监控列表</h5>
			</div>
			<div class="widget-body">
				<div class="widget-main no-padding">
					<table id="mcluster_list" class="table table-striped table-bordered table-hover">
					<thead>
						<tr>
							<th width="25%">container集群名称</th>
							<th width="25%">VIP节点地址</th>
							<th width="25%">所属物理机集群</th>
							<th width="25%" class="hidden-480">当前状态</th>
						</tr>
					</thead>
						<tbody id="tby">
						</tbody>
					</table>
				</div>
			</div>
		</div>
		<div class="col-xs-3">
			<small><font color="gray">*注：监控数据每分钟刷新一次.</font></small>
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
		url : "${ctx}/monitor/mcluster/list",
		dataType : "json", /*这句可用可不用，没有影响*/
		contentType : "application/json; charset=utf-8",
		success : function(data) {
			if(error(data)) return;
			var array = data.data;
			var tby = $("#tby");
			
			for (var i = 0, len = array.length; i < len; i++) {
				var td1 = $("<td>"
							+ "<a href=\"${ctx}/detail/mcluster/" +array[i].ipAddr+"/monitor\">"+array[i].mcluster.mclusterName+"</a>"
							+ "</td>");
				var td2 = $("<td name=\"vip\">"
							+ array[i].ipAddr
							+ "</td>");

			if(array[i].hcluster.hclusterNameAlias){
					var td3 = $("<td>"
 							+ array[i].hcluster.hclusterNameAlias
							+ "</td>");
				}else{
					var td3 = $("<td>"
 							+ "-"
							+ "</td>");
				}
				var td4 = $("<td name=\"mclusterStatus\">"
							+"<a><i class=\"ace-icon fa fa-spinner fa-spin  bigger-120\"/>数据抓取中...</a>"
							+ "</td>");

				if(array[i].status == 0 ||array[i].status == 5||array[i].status == 13){
					var tr = $("<tr class=\"warning\"></tr>");
				}else if(array[i].status == 3 ||array[i].status == 4||array[i].status == 14){
					var tr = $("<tr class=\"default-danger\"></tr>");
				}else{
					var tr = $("<tr></tr>");
				}
				
				tr.append(td1).append(td2).append(td3).append(td4);
				tr.appendTo(tby);
				
			}//循环json中的数据 
			updateMclusterStatus();//查询集群状态
		}
	});
}
function getMclusterStatus(ip,obj) {
	$.ajax({ 
		type : "get",
		url : "${ctx}/monitor/"+ip+"/mcluster/status",
		dataType : "json", 
		contentType : "application/json; charset=utf-8",
		success : function(data) {
			if(error(data)) return;
			var status = data.data[0].status;
			if(status == "6"){
				$(obj).removeClass();
				$(obj).find('[name="mclusterStatus"]').html(translateStatus(status));
			}else if(status == "5"){
				$(obj).removeClass();
				$(obj).find('[name="mclusterStatus"]').html("<font color=\"red\">集群不可用</font>");
				$(obj).addClass("default-danger");		
			}else if(status == "13"){
				$(obj).removeClass();
				$(obj).find('[name="mclusterStatus"]').html(translateStatus(status));
				$(obj).addClass("warning");		
			}else if(status == "14"){
				$(obj).removeClass();
				$(obj).find('[name="mclusterStatus"]').html("<a>"+translateStatus(status)+"</a>");
				$(obj).addClass("default-danger");		
			}
		}	
	});
}
function updateMclusterStatus(){
	$("#tby tr").each(function(){
		var ip = $(this).find('[name="vip"]').html();
		if(ip != null){
			var status = getMclusterStatus(ip,$(this));
		}
	});
}
function page_init(){
	$('#nav-search').addClass("hidden");
	queryMclusterMonitor();
	setInterval(function() {
		updateMclusterStatus();
		},60000);
}
</script>
