$(function(){
	//隐藏搜索框
	$('#nav-search').addClass("hidden");
	queryMonitorDbInfo();
});

function queryMonitorDbInfo(){
	var ip = $("#vipaddr").val();
	getLoading();
	$.ajax({ 
		cache:false,
		type : "get",
		url :"/monitor/" + ip + "/db/status",
		//url:"/static/scripts/pagejs/testjson/dbNormal.json",
		dataType : "json", 
		success : function(data) {
			removeLoading();
			if(error(data)) return;
			var monitorDbInfo = data.data.response;
			
			var $tby = $("#db_detail_table");
			if( monitorDbInfo != null){
				var dbuser = monitorDbInfo.db.dbuser;
				var backup = monitorDbInfo.db.backup;
				var existed_db_anti_item = monitorDbInfo.db.existed_db_anti_item;
				var write_read_avaliable = monitorDbInfo.db.write_read_avaliable;
				var wsrep_status = monitorDbInfo.db.wsrep_status;
				var cur_conns = monitorDbInfo.db.cur_conns;
				
				/*定义表格布局*/
				var dbuserC = getCount(dbuser);
				var backupC = getCount(backup);
				var antiC = getCount(existed_db_anti_item);
				var wrC = getCount(write_read_avaliable);
				var wsrepC = getCount(wsrep_status);
				var curC = getCount(cur_conns);
				
				var td1 = $("<td style=\"width: 20%;\">dbuser" 
						+ "<input type=\"text\" id=\"dbuserFailNum\" class=\"hidden\" />"
						+ "</td>");
				var td2 = $("<td style=\"width: 20%;\">backup" 
						+ "<input type=\"text\" id=\"backupFailNum\" class=\"hidden\" />"
						+ "</td>");
				var td3 = $("<td style=\"width: 20%;\">existed_db_anti_item" 
						+ "<input type=\"text\" id=\"antiItemFailNum\" class=\"hidden\" />"
						+ "</td>");
				var td4 = $("<td style=\"width: 20%;\">write_read_avaliable" 
						+ "<input type=\"text\" id=\"wravailFailNum\" class=\"hidden\" />"
						+ "</td>");
				var td5 = $("<td style=\"width: 20%;\">wsrep_status" 
						+ "<input type=\"text\" id=\"wsrepFailNum\" class=\"hidden\" />"
						+ "</td>");
				var td6 = $("<td style=\"width: 20%;\">cur_conns" 
						+ "<input type=\"text\" id=\"curFailNum\" class=\"hidden\" />"
						+ "</td>");
				
				//定义表格跨行数目
				td1.attr({"rowspan":dbuserC + 1});
				td2.attr({"rowspan":backupC + 1});
				td3.attr({"rowspan":antiC + 1});
				td4.attr({"rowspan":wrC + 1});
				td5.attr({"rowspan":wsrepC + 1});
				td6.attr({"rowspan":curC + 1});
				
				//添加标记，根据关键字添加表格
				var tr1 = $("<tr class=\"dbuser\"></tr>");
				var tr2 = $("<tr class=\"backup\"></tr>");
				var tr3 = $("<tr class=\"existed_db_anti_item\"></tr>");
				var tr4 = $("<tr class=\"write_read_avaliable\"></tr>");
				var tr5 = $("<tr class=\"wsrep_status\"></tr>");
				var tr6 = $("<tr class=\"cur_conns\"></tr>");
				
				//将单元格追加到行上
				tr1.append(td1);
				tr2.append(td2);
				tr3.append(td3);
				tr4.append(td4);
				tr5.append(td5);
				tr6.append(td6);
				
				//将数目添加到表格中				
				$tby.append(tr1).append(tr2).append(tr3).append(tr4).append(tr5).append(tr6);
					
				/*表格数据添加*/	
				dataAppend(dbuser,tr1,"dbuserFailNum");
				dataAppend(backup,tr2,"backupFailNum");				
				dataAppend(existed_db_anti_item,tr3,"antiItemFailNum");
				dataAppend(write_read_avaliable,tr4,"wravailFailNum");
				dataAppend(wsrep_status,tr5,"wsrepFailNum");
				dataAppend(cur_conns,tr6,"curFailNum");
				/*定义表格布局end*/
			}else{
				$tby.html("<tr><td>没有查询到数据信息</td></tr>");
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