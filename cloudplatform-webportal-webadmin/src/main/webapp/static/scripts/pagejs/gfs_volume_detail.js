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
		url : "/gfs/volume/status/"+$("#volName").val(),
		dataType : "json", /* 这句可用可不用，没有影响 */
		success : function(data) {
			removeLoading();
			if (error(data))
				return;
			var array = data.data.data;
			var tby = $("#tby");
			console.log(array);
			for (var i = 0, len = array.length; i < len; i++) {
				var td1 = $("<td class=\"center\">"
						+ "<label class=\"position-relative\">"
						+ "<input name=\"mcluster_id\" value= \"" + array[i].id
						+ "\" type=\"checkbox\" class=\"ace\"/>"
						+ "<span class=\"lbl\"></span>" + "</label>" + "</td>");
				var td2 = $("<td class='hidden-480'>" + array[i].name + "</td>");
				var td3 = $("<td>" + array[i].ip + "</td>");
				var td4 = $("<td>" + array[i].port + "</td>");
				var td5 = $("<td class=\"used\">" + array[i].used + "</td>");
				var td6 = $("<td class='hidden-480'>" + array[i].status
						+ "</td>");
				var td7 = $("<td class='hidden-480'>" + array[i].descn
						+ "</td>");
				var td8 = $("<td>"
						+ "<a class=\"red\" href=\"#\" onclick=\"delZookeeper(this)\" style=\"cursor:pointer\" onfocus=\"this.blur();\"  title=\"删除\" data-toggle=\"tooltip\" data-placement=\"right\">"
						+ "<i class=\"ace-icon fa fa-trash-o bigger-120\"></i>"
						+ "</a>" + "</td>");

				var tr = $("<tr></tr>");

				tr.append(td1).append(td2).append(td3).append(td4).append(td5)
						.append(td6).append(td7).append(td8);
				tr.appendTo(tby);
			}// 循环json中的数据

			/* 初始化tooltip */
			$('[data-toggle = "tooltip"]').tooltip();

			if (totalPages <= 1) {
				$("#pageControlBar").hide();
			} else {
				$("#pageControlBar").show();
				$("#totalPage_input").val(totalPages);
				$("#currentPage").html(currentPage);
				$("#totalRows").html(data.data.totalRecords);
				$("#totalPage").html(totalPages);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			error(XMLHttpRequest);
			return false;
		}
	});
}

function getVolSpaceInfo() {
	$("#tby tr").remove();
	getLoading();
	$.ajax({
		cache : false,
		type : "get",
		url : "/gfs/volume/status/"+$("#volName").val(),
		dataType : "json", /* 这句可用可不用，没有影响 */
		success : function(data) {
			removeLoading();
			if (error(data))
				return;
			var array = data.data.data;
			var tby = $("#tby");
			console.log(array);
			for (var i = 0, len = array.length; i < len; i++) {
				var td1 = $("<td class=\"center\">"
						+ "<label class=\"position-relative\">"
						+ "<input name=\"mcluster_id\" value= \"" + array[i].id
						+ "\" type=\"checkbox\" class=\"ace\"/>"
						+ "<span class=\"lbl\"></span>" + "</label>" + "</td>");
				var td2 = $("<td class='hidden-480'>" + array[i].name + "</td>");
				var td3 = $("<td>" + array[i].ip + "</td>");
				var td4 = $("<td>" + array[i].port + "</td>");
				var td5 = $("<td class=\"used\">" + array[i].used + "</td>");
				var td6 = $("<td class='hidden-480'>" + array[i].status
						+ "</td>");
				var td7 = $("<td class='hidden-480'>" + array[i].descn
						+ "</td>");
				var td8 = $("<td>"
						+ "<a class=\"red\" href=\"#\" onclick=\"delZookeeper(this)\" style=\"cursor:pointer\" onfocus=\"this.blur();\"  title=\"删除\" data-toggle=\"tooltip\" data-placement=\"right\">"
						+ "<i class=\"ace-icon fa fa-trash-o bigger-120\"></i>"
						+ "</a>" + "</td>");

				var tr = $("<tr></tr>");

				tr.append(td1).append(td2).append(td3).append(td4).append(td5)
						.append(td6).append(td7).append(td8);
				tr.appendTo(tby);
			}// 循环json中的数据

			/* 初始化tooltip */
			$('[data-toggle = "tooltip"]').tooltip();

			if (totalPages <= 1) {
				$("#pageControlBar").hide();
			} else {
				$("#pageControlBar").show();
				$("#totalPage_input").val(totalPages);
				$("#currentPage").html(currentPage);
				$("#totalRows").html(data.data.totalRecords);
				$("#totalPage").html(totalPages);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			error(XMLHttpRequest);
			return false;
		}
	});
}

function getVolSpaceInfo() {
	$("#tby tr").remove();
	getLoading();
	$.ajax({
		cache : false,
		type : "get",
		url : "/gfs/volume/status/"+$("#volName").val(),
		dataType : "json", /* 这句可用可不用，没有影响 */
		success : function(data) {
			removeLoading();
			if (error(data))
				return;
			var array = data.data.data;
			var tby = $("#tby");
			console.log(array);
			for (var i = 0, len = array.length; i < len; i++) {
				var td1 = $("<td class=\"center\">"
						+ "<label class=\"position-relative\">"
						+ "<input name=\"mcluster_id\" value= \"" + array[i].id
						+ "\" type=\"checkbox\" class=\"ace\"/>"
						+ "<span class=\"lbl\"></span>" + "</label>" + "</td>");
				var td2 = $("<td class='hidden-480'>" + array[i].name + "</td>");
				var td3 = $("<td>" + array[i].ip + "</td>");
				var td4 = $("<td>" + array[i].port + "</td>");
				var td5 = $("<td class=\"used\">" + array[i].used + "</td>");
				var td6 = $("<td class='hidden-480'>" + array[i].status
						+ "</td>");
				var td7 = $("<td class='hidden-480'>" + array[i].descn
						+ "</td>");
				var td8 = $("<td>"
						+ "<a class=\"red\" href=\"#\" onclick=\"delZookeeper(this)\" style=\"cursor:pointer\" onfocus=\"this.blur();\"  title=\"删除\" data-toggle=\"tooltip\" data-placement=\"right\">"
						+ "<i class=\"ace-icon fa fa-trash-o bigger-120\"></i>"
						+ "</a>" + "</td>");

				var tr = $("<tr></tr>");

				tr.append(td1).append(td2).append(td3).append(td4).append(td5)
						.append(td6).append(td7).append(td8);
				tr.appendTo(tby);
			}// 循环json中的数据

			/* 初始化tooltip */
			$('[data-toggle = "tooltip"]').tooltip();

			if (totalPages <= 1) {
				$("#pageControlBar").hide();
			} else {
				$("#pageControlBar").show();
				$("#totalPage_input").val(totalPages);
				$("#currentPage").html(currentPage);
				$("#totalRows").html(data.data.totalRecords);
				$("#totalPage").html(totalPages);
			}
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
			console.log(array);
			for (var i = 0, len = array.length; i < len; i++) {
				var td1 = $("<td class='hidden-480'>" + array[i].hostname + "</td>");
				var td2 = $("<td>" + array[i].path + "</td>");
				var td3 = $("<td>" + array[i].peerid + "</td>");
				var td4 = $("<td class=\"used\">" + array[i].pid + "</td>");
				var td5 = $("<td class='hidden-480'>" + array[i].port
						+ "</td>");
				var td6 = $("<td class='hidden-480'>" + array[i].status
						+ "</td>");
				var tr = $("<tr></tr>");

				tr.append(td1).append(td2).append(td3).append(td4).append(td5).append(td6);
				tr.appendTo(tby);
			}// 循环json中的数据
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			error(XMLHttpRequest);
			return false;
		}
	});
}

function page_init(){
	getVolProcessInfo();
}
