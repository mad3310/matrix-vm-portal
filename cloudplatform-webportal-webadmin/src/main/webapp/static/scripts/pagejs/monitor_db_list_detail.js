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
		//url :"/monitor/" + ip + "/db/status",
		url:"/static/scripts/pagejs/nodedata.json",
		dataType : "json", 
		success : function(data) {
			removeLoading();
			if(error(data)) return;
			if( data.data.response != null){
				var monitorDbInfo = data.data.response;
				var dbuser = monitorDbInfo.db.dbuser;
				var backup = monitorDbInfo.db.backup;
				var existed_db_anti_item = monitorDbInfo.db.existed_db_anti_item;
				var write_read_avaliable = monitorDbInfo.db.write_read_avaliable;
				var wsrep_status = monitorDbInfo.db.wsrep_status;
				var cur_conns = monitorDbInfo.db.cur_conns;
				console.log(typeof(dbuser.error_record));
				console.log(typeof(dbuser.message));
				for(var key in dbuser){
					var tr = $("<tr></tr>");
					var tdStr = $("<td>"+ key + "</td>");
					var tdInfo = $("<td>"+ JSON.stringify(dbuser[key]) + "</td>");
					tr.append(tdStr).append(tdInfo);
					$(".dbuser").after(tr);
				}
				for(var key in backup){
					var tr = $("<tr></tr>");
					var tdStr = $("<td>"+ key + "</td>");
					var tdInfo = $("<td>"+ JSON.stringify(backup[key])+ "</td>");
					tr.append(tdStr).append(tdInfo);
					//console.log(key+" : "+ dbuser[key]);
					$(".backup").after(tr);
				}
				for(var key in existed_db_anti_item){
					var tr = $("<tr></tr>");
					var tdStr = $("<td>"+ key + "</td>");
					var tdInfo = $("<td>"+ JSON.stringify(existed_db_anti_item[key]) + "</td>");
					tr.append(tdStr).append(tdInfo);
					//console.log(key+" : "+ dbuser[key]);
					$(".existed_db_anti_item").after(tr);
				}
				
				for(var key in write_read_avaliable){
					var tr = $("<tr></tr>");
					var tdStr = $("<td>"+ key + "</td>");
					var tdInfo = $("<td>"+ JSON.stringify(write_read_avaliable[key]) + "</td>");
					tr.append(tdStr).append(tdInfo);
					//console.log(key+" : "+ dbuser[key]);
					$(".write_read_avaliable").after(tr);
				}
				for(var key in wsrep_status){
					var tr = $("<tr></tr>");
					var tdStr = $("<td>"+ key + "</td>");
					var tdInfo = $("<td>"+ JSON.stringify(wsrep_status[key]) + "</td>");
					tr.append(tdStr).append(tdInfo);
					//console.log(key+" : "+ dbuser[key]);
					$(".wsrep_status").after(tr);
				}
				for(var key in cur_conns){
					var tr = $("<tr></tr>");
					var tdStr = $("<td>"+ key + "</td>");
					var tdInfo = $("<td>"+ JSON.stringify(cur_conns[key]) + "</td>");
					tr.append(tdStr).append(tdInfo);
					//console.log(key+" : "+ dbuser[key]);
					$(".cur_conns").after(tr);
				}
				/*var td1 = $("<td>"
						+ "<span> 总体信息:" + monitorDbInfo.db.dbuser.message + "</span><br/>"
						+ "<span> 警告:" + monitorDbInfo.db.dbuser.alarm + "</span><br/>"
						+ "<span> 创建时间:" + monitorDbInfo.db.dbuser.ctime + "</span><br/>"
						+ "<span> 错误记录:" + monitorDbInfo.db.dbuser.error_record + "</span><br/>"
						+"</td>");
				var td2 = $("<td>"
						+ "<span> message:" + monitorDbInfo.db.dbuser.message + "</span><br/>"
						+ "<span> alarm:" + monitorDbInfo.db.dbuser.alarm + "</span><br/>"
						+ "<span> ctime:" + monitorDbInfo.db.dbuser.ctime + "</span><br/>"
						+ "<span> error_record:" + monitorDbInfo.db.dbuser.error_record + "</span><br/>"
						+"</td>");
				var td3 = $("<td>"
						+ "<span> message:" + monitorDbInfo.db.dbuser.message + "</span><br/>"
						+ "<span> alarm:" + monitorDbInfo.db.dbuser.alarm + "</span><br/>"
						+ "<span> ctime:" + monitorDbInfo.db.dbuser.ctime + "</span><br/>"
						+ "<span> error_record:" + monitorDbInfo.db.dbuser.error_record + "</span><br/>"
						+"</td>");
				var td4 = $("<td>"
						+ "<span> message:" + monitorDbInfo.db.dbuser.message + "</span><br/>"
						+ "<span> alarm:" + monitorDbInfo.db.dbuser.alarm + "</span><br/>"
						+ "<span> ctime:" + monitorDbInfo.db.dbuser.ctime + "</span><br/>"
						+ "<span> error_record:" + monitorDbInfo.db.dbuser.error_record + "</span><br/>"
						+"</td>");
				var td5 = $("<td>"
						+ "<span> message:" + monitorDbInfo.db.dbuser.message + "</span><br/>"
						+ "<span> alarm:" + monitorDbInfo.db.dbuser.alarm + "</span><br/>"
						+ "<span> ctime:" + monitorDbInfo.db.dbuser.ctime + "</span><br/>"
						+ "<span> error_record:" + monitorDbInfo.db.dbuser.error_record + "</span><br/>"
						+"</td>");
				var td6 = $("<td>"
						+ "<span> message:" + monitorDbInfo.db.dbuser.message + "</span><br/>"
						+ "<span> alarm:" + monitorDbInfo.db.dbuser.alarm + "</span><br/>"
						+ "<span> ctime:" + monitorDbInfo.db.dbuser.ctime + "</span><br/>"
						+ "<span> error_record:" + monitorDbInfo.db.dbuser.error_record + "</span><br/>"
						+"</td>");
				var tr = $("<tr></tr>");
				
				tr.append(td1).append(td2).append(td3).append(td4).append(td5).append(td6);
				$("#db_detail_table").append(tr);
				*/
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

