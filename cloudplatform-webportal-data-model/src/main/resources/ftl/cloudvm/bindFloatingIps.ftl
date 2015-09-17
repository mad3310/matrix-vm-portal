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

<h3>${userName}您好：</h3>
<p>您的虚拟机已经绑定公网IP成功。以下为相关网络信息：</p>
<table class="gridtable" style="margin: 20px">
	<tr>
        <th width="100px">地域</th>
        <th width="200px">虚拟机的ID</th>
        <th width="100px">虚拟机的名称</th>
        <th width="100px">IP地址</th>
        <th width="100px">端口号</th>
        <th width="400px">绑定时间</th>
	</tr>
    #foreach($vm in $vmList)
	<tr>
        <td>${vm.region}</td>
        <td>${vm.vmId}</td>
        <td>${vm.vmName}</td>
        <td>${vm.ip}</td>
        <td>${vm.port}</td>
        <td>${vm.bindTime}</td>
	</tr>
    #end
</table>
<br/>
<p>本邮件由系统发出，请勿回复。<br/>如有问题，联系系统管理员。</p>
<hr/>
<p>乐视云计算公司<br/>
PAAS云开发团队</p>
</body>
</html>