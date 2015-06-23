$(function(){
	//初始化 
	page_init();
	
	$(document).on('click', 'th input:checkbox' , function(){
		var that = this;
		$(this).closest('table').find('tr > td:first-child input:checkbox')
		.each(function(){
			this.checked = that.checked;
			$(this).closest('tr').toggleClass('selected');
		});
	});
});	

function getVolSpaceInfo() {
	$("#tby tr").remove();
	getLoading();
	$.ajax({
		cache : false,
		type : "get",
		url : "/gfs/volume/capacity/"+$("#volName").val(),
		dataType : "json", /* 这句可用可不用，没有影响 */
		success : function(data) {
			removeLoading();
			if (error(data))
				return;
			var info = data.data.data;
			var tby = $("#space-tby");
			
			var td2 = "<td>" + info.volume + "</td>";
			var td3 = "<td class='hidden-480'>" + parseInt(info.size/1024)+"MB"+ "</td>";
			var td4 = "<td>" + parseInt(info.used/1024)+"MB" + "</td>";
			var td5 = "<td class='hidden-480'>" + parseInt(info.available/1024)+"MB" + "</td>";
			var td6 = "<td>" + info.capacity + "</td>";
			var trStart = "<tr>";
			var trEnd = "</tr>";

			var tr = trStart+td2+td3+td4+td5+td6+trEnd;
			tby.html(tr);
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			error(XMLHttpRequest);
			return false;
		}
	});
}

function getVolProcessInfo() {
	$("#tby tr").remove();
	getLoading();
	$.ajax({
		cache : false,
		type : "get",
		url : "/gfs/volume/process/"+$("#volName").val(),
		dataType : "json", /* 这句可用可不用，没有影响 */
		success : function(data) {
			removeLoading();
			if (error(data))
				return;
			var array = data.data.data[0].processes;
			var tby = $("#process-tby");
			var trs= [];
			for (var i = 0, len = array.length; i < len; i++) {
				var prefix =""; 
				if(array[i].hostname.indexOf("lab")>=0){
					prefix = "Brick "+array[i].hostname+":";
				}else{
					prefix = array[i].hostname+" on ";
				}
				var td2 = "<td>" + prefix +array[i].path + "</td>";
				var td3 = "<td class='hidden-480'>" + array[i].peerid + "</td>";
				var td4 = "<td class=\"used\">" + array[i].pid + "</td>";
				var td5 = "<td class='hidden-480'>" + array[i].port + "</td>";
				
				var stat = "";
				if(array[i].status = 1){
					stat = "Y"
				}else{
					stat = "N"
				}
				var td6 = "<td>" + stat
						+ "</td>";
				var trStart = "<tr>";
				var trEnd= "</tr>";
				trs.push(trStart+td2+td3+td4+td5+td6+trEnd);
			}// 循环json中的数据
			tby.html(trs.join(""));
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			error(XMLHttpRequest);
			return false;
		}
	});
}

function getVolConfigInfo() {
	$("#tby tr").remove();
	getLoading();
	$.ajax({
		cache : false,
		type : "get",
		url : "/gfs/volume/config/"+$("#volName").val(),
		dataType : "json", /* 这句可用可不用，没有影响 */
		success : function(data) {
			removeLoading();
			if (error(data))
				return;
			var options = data.data.data;
			var tby = $("#config-tby");
			var trs = [];
			for(item in  options){
				var trStart = "<tr>";
				var td1="<td>"+item+"</td>";
				var td2="<td>"+options[item].value+"</td>";
				var trEnd = "</tr>";
				trs.push(trStart+td1+td2+trEnd);
			}
			tby.html(trs.join(""));
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			error(XMLHttpRequest);
			return false;
		}
	});
}

function getVolSplitbrainInfo() {
	$("#tby tr").remove();
	getLoading();
	$.ajax({
		cache : false,
		type : "get",
		url : "/gfs/volume/log/"+$("#volName").val()+"/splitbrain",
		dataType : "json", /* 这句可用可不用，没有影响 */
		success : function(data) {
			removeLoading();
			if (error(data))
				return;
			var info = data.data.data;
			$("#split-brain-log").html(info);
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			error(XMLHttpRequest);
			return false;
		}
	});
}

function getHealInfo() {
	$("#tby tr").remove();
	getLoading();
	$.ajax({
		cache : false,
		type : "get",
		url : "/gfs/volume/info/"+$("#volName").val(),
		dataType : "json", /* 这句可用可不用，没有影响 */
		success : function(data) {
			removeLoading();
			if (error(data))
				return;
			var info = data.data.data;
			$("#heal-info").html(info);
			
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			error(XMLHttpRequest);
			return false;
		}
	});
}

function page_init(){
	getVolSpaceInfo();
	$("#db-detail-tabs").click(function(e){
		e = e? e:window.event;
		var target = e.target || e.srcElement;
		switch(target.id){
			case "vol-space":
				getVolSpaceInfo();
				break;
			case "vol-process":
				getVolProcessInfo();
				break;	
			case "vol-config":
				getVolConfigInfo();
				break;
			case "vol-split-brain":
				getVolSplitbrainInfo();
				break;
			case "vol-heal":
				getHealInfo();
				break;
		}
	});
}
