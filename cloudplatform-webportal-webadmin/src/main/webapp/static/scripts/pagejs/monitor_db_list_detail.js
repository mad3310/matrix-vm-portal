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
			var monitorDbInfo = data.data.response;
			if( monitorDbInfo != null){
				var dbuser = monitorDbInfo.db.dbuser;
				var backup = monitorDbInfo.db.backup;
				var existed_db_anti_item = monitorDbInfo.db.existed_db_anti_item;
				var write_read_avaliable = monitorDbInfo.db.write_read_avaliable;
				var wsrep_status = monitorDbInfo.db.wsrep_status;
				var cur_conns = monitorDbInfo.db.cur_conns;
				
				/*dbuser监控信息处理*/				
				dataAppend(dbuser,$(".dbuser"),$("#dbuserFailNum"));
				
				/*backup监控信息处理*/
				dataAppend(backup,$(".backup"),$("#backupFailNum"));
				
				dataAppend(existed_db_anti_item,$(".existed_db_anti_item"),$("#antiItemFailNum"));
				dataAppend(existed_db_anti_item,$(".write_read_avaliable"),$("#wravailFailNum"));
				dataAppend(existed_db_anti_item,$(".wsrep_status"),$("#wsrepFailNum"));
				dataAppend(existed_db_anti_item,$(".cur_conns"),$("#curFailNum"));
			}else{
				$("#db_detail_table").html("没有查询到详细信息。")
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

function strToJson(str){
	var jsonArr = {};
	strArr = str.split(",")
	for(var i=0;i<strArr.length;i++){
		strArrC = strArr[i].split("=");
		if(strArrC.length == 2){
			jsonArr[strArrC[0]] = strArrC[1];
		}else{
			console.log("strToJson function error.");
		}
	}
	
	return jsonArr;
}

function msgFormat(json,obj,failedObj){
	for (var key in json){
		var span = $("<span>" + key + "  :  " + json[key] + "</span>" + "<br/>");
		if( key.indexOf("failed") >= 0 && json[key] > 0){
			failedObj.val(json[key]);
			span.eq(0).addClass("monitor-text-danger");
		}/*else if(key.indexOf("success") >= 0 && json[key] > 0){
			span.eq(0).addClass("monitor-text-success");
		}*/		
		obj.append(span);
	}
}

function alarmFormat(str,obj,failedObj){
	var span = $("<span>" + str + "</span>" + "<br/>");
	var failNum = failedObj.val()
	if(failNum == "0"){
		span.eq(0).addClass("monitor-text-success");
	}else if(failNum == "1"){
		span.eq(0).addClass("monitor-text-danger");
	}else if(failNum == "2"){
		span.eq(0).addClass("monitor-text-serious");
	}
	
	obj.append(span);
}

function errorRecordFormat(json,obj){
	if(!$.isEmptyObject(json)){
		for (var key in json){
			if(typeof(json[key] == "object")){
				var span = $("<span>"+ key + "  :   " + JSON.stringify(json[key]) + "</span>" + "<br/>")
				obj.append(span);
			}else{
				var span = $("<span>"+ key + "  :   " + json[key] + "</span>" + "<br/>")
				obj.append(span);
			}
		}		
	}
}

function dataAppend(data,tableFlag,failedObj){
	for(var key in data){
		var tr = $("<tr></tr>");
		var tdStr = $("<td>"+ key + "</td>");
		var tdInfo = $("<td></td>");
		if( key == "message" ){
			var msgJson = strToJson(data[key]);
			if(!$.isEmptyObject(msgJson)){
				msgFormat(msgJson,tdInfo,failedObj)
			}
		}else if(key == "alarm"){
			alarmFormat(data[key],tdInfo,failedObj);
		}else if(key == "error_record"){
			errorRecordFormat(data[key],tdInfo)
		}else{
			var tdInfo = $("<td>"+ JSON.stringify(data[key]) + "</td>");
		}
		
		tr.append(tdStr).append(tdInfo);
		tableFlag.after(tr);
	}
}
