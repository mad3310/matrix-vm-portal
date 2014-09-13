<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="page-content-area">
	<div id="page-header-id" class="page-header">
		<h1> 
			<a href="${ctx}/mcluster/list">集群列表</a>
			<small> 
				<i class="ace-icon fa fa-angle-double-right"></i> 
				${containers[0].clusterNodeName}
			</small>
		</h1>
	</div>
	<!-- /.page-header -->
</div>
<div class="row">
	<div>
		<table class="table table-bordered" id="Mcluster_detail_table" name="Mcluster_detail_table" style="margin-top: 10px;">
			<thead>
		      <tr class="info">
		         <th>container名称</th>
		         <th>类型</th>
		         <th>宿主机</th>
		         <th>ip</th>
		         <th>挂载路径</th>
		         <th>zookeepId</th>
		         <th>状态</th>
		      </tr>
		   </thead>
		   <c:forEach var="container" items="${containers}">
			 <tr>
			 	<td>${container.containerName}</td>
			 	<td>${container.type}</td>
			 	<td>${container.hostId}</td>
			 	<td>${container.ipAddr}</td>
			 	<td>${container.mountDir}</td>
			 	<td>${container.zookeeperId}</td>
			 	<td>正常</td>
			 </tr>
		   </c:forEach>
		</table>
	</div>
</div>
<script type="text/javascript">
$(function(){
	initPage();
});

function initPage(){
}
</script>
