 	var currentPage = 1; //第几页 
	var recordsPerPage = 15; //每页显示条数
 $(function(){
	//初始化
	page_init();
	/*查询功能*/
	$("#monitorDbSearch").click(function(){
		var iw=document.body.clientWidth;
		if(iw>767){//md&&lg
		}else{
			$('.queryOption').addClass('collapsed').find('.widget-body').attr('style', 'dispaly:none;');
			$('.queryOption').find('.widget-header').find('i').attr('class', 'ace-icon fa fa-chevron-down');
			var qryStr='';
			var qryStr1=$('#hostIp').val();var qryStr2=$('#hostTag').val();var qryStr3=$("#connectCount").find('option:selected').text();var qryStr4=$("#activityCount").find('option:selected').text();
			if(qryStr1){
				qryStr+='<span class="label label-success arrowed">'+qryStr1+'<span class="queryBadge" data-rely-id="hostIp"><i class="ace-icon fa fa-times-circle"></i></span></span>&nbsp;'
			}
			if(qryStr2){
				qryStr+='<span class="label label-warning arrowed">'+qryStr2+'<span class="queryBadge" data-rely-id="hostTag"><i class="ace-icon fa fa-times-circle"></i></span></span>&nbsp;'
			}
			if(qryStr){
				$('.queryOption').find('.widget-title').html(qryStr);
				$('.queryBadge').click(function(event) {
					var id=$(this).attr('data-rely-id');
					$('#'+id).val('');
					$(this).parent().remove();
					queryResourceData();
					if($('.queryBadge').length<=0){
						$('.queryOption').find('.widget-title').html('rds健康监控条件');
					}
					return;
				});
			}else{
				$('.queryOption').find('.widget-title').html('rds健康监控条件');
			}

		}
		queryResourceData();
	});
	$("#monitorDbClear").click(function(){
		var clearList = ["hostTag","hostIp"];
		clearSearch(clearList);
	});
	enterKeydown($(".page-header > .input-group input"),queryResourceData);
});
function queryResourceData(){
	clearSortBy();
	updateResourceData();
}
function updateResourceData(order,orderArg) {
	var hostIp=$('#hostIp').val();
	var hostTag=$('#hostTag').val();
	var queryCondition = {
			'orderArg':'DESC',//默认降序
			'currentPage':currentPage,
			'recordsPerPage':recordsPerPage,
			'hostIp':hostIp?hostIp:'',
			'hostTag':hostTag?hostTag:''
		}
		if(order){
			queryCondition.order=order;
		}
		if(orderArg){
			queryCondition.orderArg=orderArg;
		}
	$("#tby tr").remove();
	getLoading();
	$.ajax({ 
		cache:false,
		type : "get",
		//url : "/monitor/mcluster/list",
		url : queryUrlBuilder("/monitor/rds/node/db/space/list",queryCondition),
		dataType : "json", /*这句可用可不用，没有影响*/
		contentType : "application/json; charset=utf-8",
		success : function(data) {
			removeLoading();
			if(error(data)) return;
			var array = data.data.data;
			var tby = $("#tby");
			var totalPages = data.data.totalPages;
			currentPage = data.data.currentPage;
			
			var trs = [];
			for (var i = 0, len = array.length; i < len; i++) {
				var td=[];
				td.push("<td><a class=\"link\"  href=\"/list/rds/node/tableSpace/"+array[i].id+"\">"+array[i].hostIp+"</td>");
				td.push("<td>"+array[i].hostTag+"</td>");
				td.push("<td>"+array[i].name+"</td>");
				td.push("<td>"+array[i].size+"</td>");
				var tr ="<tr>"+td.join('')+"</tr>";
				trs.push(tr);
			}//循环json中的数据 
			tby.html(trs);
			if (totalPages <= 1) {
				$("#pageControlBar").hide();
			} else {
				$("#pageControlBar").show();
				$("#totalPage_input").val(totalPages);
				$("#currentPage").html(currentPage);
				$("#totalRows").html(data.data.totalRecords);
				$("#totalPage").html(totalPages);
			}
		}
	});
}
function pageControl() {
	// 首页
	$("#firstPage").bind("click", function() {
		currentPage = 1;
		queryResourceData();
	});

	// 上一页
	$("#prevPage").click(function() {
		if (currentPage == 1) {
			$.gritter.add({
				title: '警告',
				text: '已到达首页',
				sticky: false,
				time: '5',
				class_name: 'gritter-warning'
			});
	
			return false;
			
		} else {
			currentPage--;
			queryResourceData();
		}
	});

	// 下一页
	$("#nextPage").click(function() {
		if (currentPage == $("#totalPage_input").val()) {
			$.gritter.add({
				title: '警告',
				text: '已到达末页',
				sticky: false,
				time: '5',
				class_name: 'gritter-warning'
			});
	
			return false;
			
		} else {
			currentPage++;
			queryResourceData();
		}
	});

	// 末页
	$("#lastPage").bind("click", function() {
		currentPage = $("#totalPage_input").val();
		queryResourceData();
	});
}

function tableSortInit(){	//在鼠标放到head上时，显示排序箭头，离开时隐藏，点击箭头，按箭头命令排序。
	var icon = "<a class='sort-down'><i class='fa fa-sort-down'></i></a><a class='sort-up'><i class='fa fa-sort-up'></i></a>";
	$(".table-head-sort").append(icon);
	$(".monitor-table-headr-group .header-two").mouseover(function(){
	    $(".table-head-sort a").css('visibility', 'visible');
	  }).mouseout(function(){
	    $(".table-head-sort a").css('visibility', 'hidden');
	  }).click(function(e){
	  	e = e? e:window.event;//兼容windows
		var _target = e.target || e.srcElement;
		var targetClassList = _target.classList;
		var $target = $(_target);
		if(targetClassList.contains("fa-sort-down")){
				setSortBy($target);
				updateBySort($target,'DESC');
			}else if(targetClassList.contains("fa-sort-up")){
				setSortBy($target);
				updateBySort($target,'ASC');
		}
	  })
}
function setSortBy($target){ //target 为<i class='fa fa-sort-down'></i>或<i class='fa fa-sort-up'></i>
	$(".table-head-sort").find(".sort-by").removeClass("sort-by");
	$target.addClass("sort-by");
}
function clearSortBy(){
	$(".table-head-sort").find(".sort-by").removeClass("sort-by");
}
function updateBySort(target,orderArg){
	currentPage = 1;//重新排序，当前页重置为第一页。 
	var order = target.closest('th').attr('target-data');
	updateResourceData(order,orderArg);
}
function page_init(){
	$('#nav-search').addClass("hidden");
	pageControl();
	tableSortInit();
	queryResourceData();
	$("#updateMonitorData").click(function(){
		queryResourceData();
	});
}
