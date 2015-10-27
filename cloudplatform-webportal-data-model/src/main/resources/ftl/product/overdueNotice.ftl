<html>
<head>
<style type="text/css">
table.gridtable {
	font-family: verdana, arial, sans-serif;
	font-size: 11px;
	color: #333333;
	border-width: 1px;
	border-color: #666666;
	border-collapse: collapse;
}

table.gridtable th {
	border-width: 1px;
	padding: 8px;
	border-style: solid;
	border-color: #666666;
	background-color: #dedede;
}

table.gridtable td {
	border-width: 1px;
	padding: 8px;
	border-style: solid;
	border-color: #666666;
	background-color: #ffffff;
	text-align: center; 
}
p {
	margin: 20px
}
</style>
</head>
<body>
<!-- Table goes in the document BODY -->

<h3>尊敬的${userName}用户,</h3>
<p>您的以下资源因欠费已延期使用，请尽快充值：</p>
<table class="gridtable" style="margin: 20px">
	<tr>
        <th width="100px">地域</th>
		<th width="100px">资源类型</th>
        <th width="100px">资源ID</th>
        <th width="100px">资源名称</th>
        <th width="100px">预计释放时间</th>
		<th width="100px">延期天数（/天）</th>
	</tr>
	<#list resList as res>
	<tr>
		<td>${res.region}</td>
		<td>${res.type}</td>
		<td>${res.id}</td>
		<td>${res.name}</td>
		<td>${res.deleteTime}</td>
        <td>${res.day}</td>
	</tr>
	</#list>
</table>
<br/>
<p>注意：如不及时续费，到期后资源将被释放，无法恢复；如果您不再使用这些资源, 可以主动销毁, 避免再次收到提醒。</p>
<hr/>
<p>乐视云计算公司</p>
</body>
</html>