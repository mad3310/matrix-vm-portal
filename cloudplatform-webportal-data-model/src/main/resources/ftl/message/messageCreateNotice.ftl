<link type="text/css" rel="stylesheet" href="/assets/css/main/usercenterRechargeApp.css"/>
<p style="font-size: 14px;">${introduce}</p>
<div class="table-luzhi-wraper">
	<table class="table table-responsive table-luzhi" style="font-size: 14px;width:calc(100% - 4em);margin-left: 2em;">
		<thead>
			<tr>
				<th>地域</th>
				<th>资源类型</th>
				<th>资源ID</th>
				<th>资源名称</th>
				<#if isVm>
				<th>用户名</th>
				<th>密码</th>
				</#if>
			</tr>
		<thead>
		<tbody>
			<#list resList as res>
			<tr>
				<td>${res.region}</td>
				<td>${res.type}</td>
				<td>${res.id}</td>
				<td>${res.name}</td>
				<#if isVm>
				<td>${res.userName}</td>
				<td>${res.password}</td>
				</#if>
			</tr>
			</#list>
		</tbody>
	</table>
</div>
<p style="font-size: 14px;color: red;">${warn}</p>