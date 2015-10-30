<link type="text/css" rel="stylesheet" href="/assets/css/main/usercenterRechargeApp.css"/>
<p style="font-size: 12px;">${introduce}</p>
<div class="table-luzhi-wraper">
	<table class="table table-responsive table-luzhi" style="font-size: 12px;width:calc(100% - 4em);margin-left: 2em;">
		<thead>
			<tr>
				<th>地域</th>
				<th>资源类型</th>
				<th>资源ID</th>
				<th>资源名称</th>
			</tr>
		<thead>
		<tbody>
			<#list resList as res>
			<tr>
				<td>${res.region}</td>
				<td>${res.type}</td>
				<td>${res.id}</td>
				<td>${res.name}</td>
			</tr>
			</#list>
		</tbody>
	</table>
</div>