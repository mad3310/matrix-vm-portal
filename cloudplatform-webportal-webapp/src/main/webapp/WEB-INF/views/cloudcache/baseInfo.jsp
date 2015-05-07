<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="zh">

<%@include file='main.jsp'%>

<body> 
<!-- 全局参数 start -->
	<input class="hidden" value="${cacheId}" name="cacheId" id="cacheId" type="text" />
	<!-- <input class="hidden" value="702" name="cacheId" id="cacheId" type="text" /> -->
	<div class="panel-group pd10" id="accordion" role="tablist" aria-multiselectable="true">
	    <div class="panel panel-default panel-table ">
	        <div class="panel-heading bdl-list overHidden panel-heading-mcluster" role="tab" id="headingOne" >
		        <span class="panel-title">
		          		基本信息
		        </span>						
				<a class="collapse-selector" data-toggle="collapse" href="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
				<span class="toggle-drop-down-icon" toggle-show="toggleShow">
					<span class="glyphicon glyphicon-chevron-down table-viewer-dropdown "></span>
				</span>
				</a>
	    	</div>
		    <div id="collapseOne" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="headingOne">
		      <div class="panel-body pd0">
		        <table class="table table-bordered table-bi">
						<tbody>
						<tr>
							<td width="50%"><span class="text-muted pd-r8">实例名称:</span><span id="cache_info_cache_name"></span></td>
							<td width="50%"><span class="text-muted" style="padding-right:8px">可用区:</span><span id="cache_info_available_region"></span></td>
						</tr>
						<tr>
							<td width="50%">
								<span class="text-muted pd-r8">服务集群:</span><span text-length="26" id="cache-cbaseCluster"></span>
							</td>
							<td width="50%">
								<span class="text-muted pd-r8">地域:</span>北京
							</td>
						</tr>
						<tr>
							<td width="50%">
								<span class="text-muted pd-r8">集群地址:</span><span text-length="26" id="cache_info_net_addr"></span>
							</td>
							<td width="50%">
								<span class="text-muted pd-r8">状态:</span><span text-length="26" id="cache_info_running_state"></span>
							</td>
						</tr>
						</tbody>
					</table>
		      </div>
		    </div>
	  	</div>
	  	<div class="panel panel-default panel-table">
	        <div class="panel-heading bdl-list panel-heading-mcluster" role="tab" id="headingTwo">
		        <span class="panel-title">
		          		配置信息
				</span>
				<div class="pull-right table-viewer-topbar-content">
					<a class="disabled btn btn-xs btn-primary" target="_blank" href="javascript:void(0)">续费</a>
					<a class="disabled btn btn-xs btn-primary" target="_blank" href="javascript:void(0)">变更配置</a>
					<a class="btn btn-xs btn-primary" id="showConfigInfo" href="javascript:void(0)">配置信息</a>
				</div>
				<a class="collapse-selector" data-toggle="collapse" href="#collapseThree"  aria-expanded="true" aria-controls="collapseThree">
				<span class="toggle-drop-down-icon" toggle-show="toggleShow">
					<span class="glyphicon glyphicon-chevron-down table-viewer-dropdown "></span>
				</span>
				</a>
	    	</div>
		    <div id="collapseThree" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="headingThree">
		      <div class="panel-body pd0">
		        <table class="table table-bordered table-bi">
		        	<tbody>
		        	<tr>
			        	<td width="50%">
			        		<span class="text-muted pd-r8">实例类型:</span><span text-length="26" id="cache_type"></span>
			        	</td>
			        	<td width="50%">
							<span class="text-muted pd-r8">缓存空间大小:</span><span text-length="26" id="cache_ramQuotaMB"></span>
						</td>
		        	</tr>
		        	</tbody>
		        </table>
		      </div>
		    </div>
	  	</div>
	  	<div class="panel panel-default panel-table">
	        <div class="panel-heading bdl-list panel-heading-mcluster" role="tab" id="headingTwo">
		        <span class="panel-title">
		          		付费信息
				</span>
				<a class="collapse-selector" data-toggle="collapse" href="#collapseFour"  aria-expanded="true" aria-controls="collapseFour">
				<span class="toggle-drop-down-icon" toggle-show="toggleShow">
					<span class="glyphicon glyphicon-chevron-down table-viewer-dropdown "></span>
				</span>
				</a>
	    	</div>
		    <div id="collapseFour" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="headingFour">
		      <div class="panel-body pd0">
		        <table class="table table-bordered table-bi">
		        	<tbody>
		        	<tr>
			        	<td width="50%">
			        		<span class="text-muted pd-r8">创建时间:</span><span id="cache_info_create_time"></span>
			        	</td>
			        	<td width="50%">
			        		<span class="text-muted pd-r8">付费类型:</span><span>包年  <span id="cache_info_remain_days"></span>天后到期</span>
			        	</td>
		        	</tr>
		        	</tbody>
		        </table>
		      </div>
		    </div>
	  	</div>
	  	<!-- <div class="panel panel-default panel-table">
	        <div class="panel-heading bdl-list panel-heading-mcluster" role="tab" id="headingTwo">
		        <span class="panel-title">
					可维护的时间段
				</span>
				<a class="collapse-selector" data-toggle="collapse" href="#collapseFive"  aria-expanded="true" aria-controls="collapseFive">
				<span class="toggle-drop-down-icon" toggle-show="toggleShow">
					<span class="glyphicon glyphicon-chevron-down table-viewer-dropdown "></span>
				</span>
				</a>
	    	</div>
		    <div id="collapseFive" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="headingFive">
		      <div class="panel-body pd0">
		        <table class="table table-bordered table-bi">
		        	<tbody>
		        	<tr>
			        	<td width="50%">
			        		<span class="text-muted pd-r8">可维护的时间段:</span>
			        		<span class="pd-r8">未设置</span>
			        		<span class="font-disabled pd-r8">设置</span>
			        	</td>
		        	</tr>
		        	</tbody>
		        </table>
		      </div>
		    </div>
	  	</div> -->
	    <!-- /.modal config-->
		<div class="modal" id="cacheConfigModal" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog modal-sm">
				<div class="modal-content" style="width:500px;">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">&times;</button>
						<h4 class="modal-title" id="cacheConfigModalLabel"></h4>
					</div>
					<div class="modal-body clearfix" id="cacheConfigInfoWrap" >
						<div class="zero-clipboard">
							<a id= "zclipCopy" class="btn-clipboard">复制</a>
						</div>
						<pre>
							<code id="cacheConfigInfo" class="language-html"></code>
						</pre>
						<div id="zeroclipboardTooltip" data-toggle="tooltip" title="" style="position: absolute; right: 50px; top:10px; z-index: 999999999;">
						</div>
					</div>
				</div>
				<!-- /.modal-content -->
			</div>
			<!-- /.modal -->
		</div>
	</div>
</body>


<!-- js -->
<script type="text/javascript" src="${ctx}/static/modules/seajs/2.3.0/sea.js"></script>
<script type="text/javascript">
	// Set configuration
	seajs.config({
		base : "${ctx}/static/modules/",
		alias : {
			"jquery" : "jquery/2.0.3/jquery.min.js",
			"bootstrap" : "bootstrap/bootstrap/3.3.0/bootstrap.js",
			"zclip" : "jquery/zclip/jquery.zclip.min.js"
		}
	});
	seajs.use("${ctx}/static/page-js/cloudcache/basicInfo/main");
</script>
</html>
