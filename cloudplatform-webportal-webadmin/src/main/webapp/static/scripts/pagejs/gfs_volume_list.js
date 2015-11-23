var currentPage = 1; //第几页 
var recordsPerPage = 15; //每页显示条数
	
$(function(){
	//初始化 
	page_init();
	var options = {
		allow_single_deselect:true,
		search_contains:true,
		no_results_text:"未找到匹配数据",
		disable_search:true,	
		width:'272px'
	}
	
	$('.chosen-select').chosen(options)

	$(document).on('click', 'th input:checkbox' , function(){
		var that = this;
		$(this).closest('table').find('tr > td:first-child input:checkbox')
		.each(function(){
			this.checked = that.checked;
			$(this).closest('tr').toggleClass('selected');
		});
	});
});	

function queryGfsVolume() {
	$("#tby tr").remove();
	getLoading();
	$.ajax({
		cache : false,
		type : "get",
		url : "/gfs/volume",
		dataType : "json", /*这句可用可不用，没有影响*/
		success : function(data) {
			removeLoading();
			if (error(data))
				return;
			var array = data.data.data;
			var tby = $("#tby");
			var trs= [];
			var totalPages = data.data.totalPages;

			for (var i = 0, len = array.length; i < len; i++) {
				var td1 = "<td class=\"center\">"
						+ "<label class=\"position-relative\">"
						+ "<input name=\"volName\" value= \"" + array[i].name
						+ "\" type=\"checkbox\" class=\"ace\"/>"
						+ "<span class=\"lbl\"></span>" + "</label>" + "</td>";
				var td2 = "<td><a class=\"link\" href=\"/detail/gfs/volume/"+array[i].name+"\">" + array[i].name + "</a></td>";
				var td3 = "<td class='hidden-480'>" + array[i].uuid + "</td>";
				var td4 = "<td class='hidden-480'>" + array[i].transport + "</td>";
				var td5 = "<td class='hidden-480'>" + array[i].type + "</td>";
				var td6 = "<td class='hidden-480'>" + array[i].num_bricks + "</td>";
				var td7 = "<td>" + array[i].status
						+ "</td>";
				var td8 = "<td>"
						+ "<div class=\"action-buttons\">"
						+ "<a class=\"action-start green\" href=\"#\" style=\"cursor:pointer\" onfocus=\"this.blur();\"  title=\"启动\" data-toggle=\"tooltip\" data-placement=\"right\">"
						+ "<i class=\"ace-icon fa fa-play-circle-o bigger-120\"></i>"
						+ "</a>"
						+ "<a class=\"action-stop red\" href=\"#\" style=\"cursor:pointer\" onfocus=\"this.blur();\"  title=\"停止\" data-toggle=\"tooltip\" data-placement=\"right\">"
						+ "<i class=\"ace-icon fa fa-power-off bigger-120\"></i>"
						+ "</a>"
						+ "</div>"
						+ "</td>";
				var trStart = "<tr>";
				var trEnd = "</tr>";

				trs.push(trStart+td1+td2+td3+td4+td5+td6+td7+td8+trEnd);
			}//循环json中的数据 
				tby.html(trs.join(""));
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			error(XMLHttpRequest);
			return false;
		}
	});
}
function operationBtnInit(){
	$("#tby").click(function(e){
		e = e? e:window.event;
		var _target = e.target || e.srcElement;
		var aTarget = $(_target).parent()[0];
		var aTargetClassList = aTarget.classList;
		if(aTargetClassList.contains("action-start")){
			volStart(aTarget);
		}else if(aTargetClassList.contains("action-stop")){
			volStop(aTarget);
		}
	});
}

function volStart(target){
	var  volName= $(target).closest("tr").find("input").val();
	if(volName != undefined && volName != null ){
		function startCmd() {
			getLoading();
			$.ajax({
						cache : false,
						type : "post",
						url : "/gfs/volume/start",
						data:{"name":volName},
						success : function() {
							removeLoading();
							location.href = "/list/gfs/volume";
						}
					})
		}
		confirmframe("启动"+volName,"启动"+volName+"可能需要几秒时间！","您确定要启动?",startCmd);
	}else{
		$.gritter.add({
			title: '错误',
			text: "当前状态不允许执行此动作！",
			sticky: false,
			time: 1000,
			class_name: 'gritter-error'
		});
	}
}
function volStop(target){
	var volName = $(target).closest("tr").find("input").val();
	if (volName != undefined && volName != null) {
		function startCmd() {
			getLoading();
			$.ajax({
						cache : false,
						type : "post",
						url : "/gfs/volume/stop",
						data:{"name":volName},
						success : function() {
							removeLoading();
							location.href = "/list/gfs/volume";
						}
					})
		}
		confirmframe("停止"+volName,"停止"+volName+"后，将不能提供服务！","您确定要停止?",startCmd);
	}else{
		$.gritter.add({
			title: '错误',
			text: "当前状态不允许执行此动作！",
			sticky: false,
			time: 1000,
			class_name: 'gritter-error'
		});
	}
}

function page_init(){
	queryGfsVolume();
	operationBtnInit();
}
