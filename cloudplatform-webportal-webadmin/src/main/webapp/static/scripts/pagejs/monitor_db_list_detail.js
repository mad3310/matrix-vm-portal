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
		//url:"/static/scripts/pagejs/nodedata.json",
		dataType : "json", 
		success : function(data) {
			removeLoading();
			if(error(data)) return;
			var monitorDbInfo = data.data.response;
			if( monitorDbInfo != null){
				var dbuser = monitorDbInfo.db.dbuser;
				var backup = monitorDbInfo.db.backup;
				var existed_db_anti_item = monitorDbInfo.db.existed_db_anti_item;
				var write_read_avaliable = monitorDbInfo.db.write_read_avaliable;
				var wsrep_status = monitorDbInfo.db.wsrep_status;
				var cur_conns = monitorDbInfo.db.cur_conns;
				
				/*表格数据添加*/	
				dataAppend(dbuser,$(".dbuser"),$("#dbuserFailNum"));
				dataAppend(backup,$(".backup"),$("#backupFailNum"));				
				dataAppend(existed_db_anti_item,$(".existed_db_anti_item"),$("#antiItemFailNum"));
				dataAppend(existed_db_anti_item,$(".write_read_avaliable"),$("#wravailFailNum"));
				dataAppend(existed_db_anti_item,$(".wsrep_status"),$("#wsrepFailNum"));
				dataAppend(existed_db_anti_item,$(".cur_conns"),$("#curFailNum"));
			}else{
				$("#db_detail_table").html("<tr><td>没有查询到数据信息</td></tr>");
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