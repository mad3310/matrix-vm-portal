var currentPage = 1; //第几页
var recordsPerPage = 15; //每页显示条数
var currentSelectedLineDbName = 1;

$(function(){
  //初始化
  page_init();
  /*动态添加select内容*/
	var sltArray = [0,2,3,5,6,7,8,9,10,13,14];
	addSltOpt(sltArray,$("#dbStatus"));
	
	$(document).on('click', 'th input:checkbox' , function(){
		var that = this;
		$(this).closest('table').find('tr > td:first-child input:checkbox')
		.each(function(){
			this.checked = that.checked;
			$(this).closest('tr').toggleClass('selected');
		});
	});
	
	//modal显示创建进度
	var mclusterId;
	/*$(document).on('click', "[name='buildStatusBoxLink']" , function(){
		mclusterId = $(this).closest('tr').find('input').val();
		if($(this).html().indexOf("正常")>=0){
			$('#buildStatusHeader').html("创建成功");
			status = "1";
		}else if($(this).html().indexOf("创建中")>=0){
			$('#buildStatusHeader').html("<i class=\"ace-icon fa fa-spinner fa-spin green bigger-125\"></i>创建中...");
			status = "2";
		}else if($(this).html().indexOf("创建失败")>=0){
			$('#buildStatusHeader').html("创建失败");
			status = "3";
		}
		queryBuildStatus(mclusterId,"new");
	});*/
	
	$('#create-mcluster-status-modal').on('shown.bs.modal', function(){
		if(status == "2") {
			queryBuildStatusrefresh = setInterval(function() {  
				queryBuildStatus(mclusterId,"update");
			},5000);
		}
	}).on('hidden.bs.modal', function (e) {
		queryBuildStatusrefresh = window.clearInterval(queryBuildStatusrefresh);
		location.reload();
	});
	
	/*查询功能*/
	$("#dbSearch").click(function(){
		var iw=document.body.clientWidth;
		if(iw>767){//md&&lg
		}else{
			$('.queryOption').addClass('collapsed').find('.widget-body').attr('style', 'dispaly:none;');
			$('.queryOption').find('.widget-header').find('i').attr('class', 'ace-icon fa fa-chevron-down');
			var qryStr='';
			var qryStr1=$('#dbName').val();var qryStr2=$('#dbMcluster').val();var qryStr3=$("#dbPhyMcluster").find('option:selected').text();var qryStr4=$("#containeruser").find('option:selected').text();var qryStr5;
			if($('#dbStatus').val()){
				qryStr5=translateStatus($('#dbStatus').val());
			}
			if(qryStr1){
				qryStr+='<span class="label label-success arrowed">'+qryStr1+'<span class="queryBadge" data-rely-id="dbName"><i class="ace-icon fa fa-times-circle"></i></span></span>&nbsp;'
			}
			if(qryStr2){
				qryStr+='<span class="label label-warning arrowed">'+qryStr2+'<span class="queryBadge" data-rely-id="dbMcluster"><i class="ace-icon fa fa-times-circle"></i></span></span>&nbsp;'
			}
			if(qryStr3){
				qryStr+='<span class="label label-purple arrowed">'+qryStr3+'<span class="queryBadge" data-rely-id="dbPhyMcluster"><i class="ace-icon fa fa-times-circle"></i></span></span>&nbsp;'
			}
			if(qryStr4){
				qryStr+='<span class="label label-yellow arrowed">'+qryStr4+'<span class="queryBadge" data-rely-id="containeruser"><i class="ace-icon fa fa-times-circle"></i></span></span>&nbsp;'
			}
			if(qryStr5){
				qryStr+='<span class="label label-pink arrowed">'+qryStr5+'<span class="queryBadge" data-rely-id="dbStatus"><i class="ace-icon fa fa-times-circle"></i></span></span>&nbsp;'
			}
			if(qryStr){
				$('.queryOption').find('.widget-title').html(qryStr);
				$('.queryBadge').click(function(event) {
					var id=$(this).attr('data-rely-id');
					$('#'+id).val('');
					$(this).parent().remove();
					queryByPage();
					if($('.queryBadge').length<=0){
						$('.queryOption').find('.widget-title').html('数据库查询条件');
					}
					return;
				});
			}else{
				$('.queryOption').find('.widget-title').html('数据库查询条件');
			}

		}
		queryByPage();
	});
	$("#dbSearchClear").click(function(){
		//var clearList = ["","","","","",""]
		var clearList = ["dbName","dbMcluster","dbPhyMcluster","containeruser","dbStatus"]
		clearSearch(clearList);
	});
	
	enterKeydown($(".page-header > .input-group input"),queryByPage);
});

  function queryByPage() {
  	var dbName = $("#dbName").val()?$("#dbName").val():'';
	var mclusterName = $("#dbMcluster").val()?$("#dbMcluster").val():'';
	var hclusterName = $("#dbPhyMcluster").find('option:selected').attr('data-hclsName')?$("#dbPhyMcluster").find('option:selected').attr('data-hclsName'):'';
	var userName = $("#containeruser").find('option:selected').text()?$("#containeruser").find('option:selected').text():'';
	/*var createTime = $("#PhyMechineDate").val()?$("#PhyMechineDate").val():'null';*/
	var status = $("#dbStatus").val()?$("#dbStatus").val():'';
	var queryCondition = {
			'currentPage':currentPage,
			'recordsPerPage':recordsPerPage,
			'slbName':dbName,
			'clusterName':mclusterName,
			'hclusterName':hclusterName,
			'userName':userName,
			// /*'createTime':createTime,*/
			'status':status
	}
    $("#tby tr").remove();
    getLoading();
    $.ajax({
      cache:false,
      type : "get",
      // url : '/slb',
      url : queryUrlBuilder("/slb",queryCondition),
      dataType : "json", /*这句可用可不用，没有影响*/
      contentType : "application/json; charset=utf-8",
      success : function(data) {
	removeLoading();
	error(data);
	var array = data.data.data;
	var tby = $("#tby");
	var totalPages = data.data.totalPages;
	for (var i = 0, len = array.length; i < len; i++) {
	  var td0 = $("<input class=\"hidden\" type=\"text\" value=\""+array[i].slbClusterId+"\"\> ");
	  var td1 = $("<td class=\"center\">"
	    +"<label class=\"position-relative\">"
	    +"<input type=\"checkbox\" class=\"ace\"/>"
	    +"<span class=\"lbl\"></span>"
	    +"</label>"
	    +"</td>");
	  var td2;
	  if(array[i].status == 6){
	    td2 = $("<td>"
	      + "<a class=\"link\" href='#'>"+array[i].slbName+"</a>"
	      + "</td>");//href=\"/detail/db/"+array[i].id+"\"
	  }else if(array[i].status == 0 ||array[i].status == 3){
	    td2 = $("<td>"
	      + "<a class=\"link\" class=\"danger\" href='#'>"+array[i].slbName+"</a>"
	      + "</td>");//href=\"/audit/db/"+array[i].id+"\"
	  }else{
	    td2 = $("<td>"
	      + "<a class=\"link\" style=\"text-decoration:none;\">"+array[i].slbName+"</a>"
	      + "</td>");
	  }
	  if(array[i].slbCluster){
	    var td3 = $("<td class='hidden-480'>"
	      + "<a class=\"link\" href=\"/detail/slb/cluster/" + array[i].slbClusterId+"\">"+array[i].slbCluster.clusterName+"</a>"
	      + "</td>");//href=\"/detail/mcluster/" + array[i].mclusterId+"\"
	  } else {
	    var td3 = $("<td class='hidden-480'> -</td>");
	  }
	  if(array[i].hcluster){
	    var td4 = $("<td class='hidden-480'>"
	      + array[i].hcluster.hclusterNameAlias
	      + "</td>");
	  } else {
	    var td4 = $("<td class='hidden-480'> </td>");
	  }
	  var userName = '-';
	  if(array[i].createUserModel!=null && !array[i].createUserModel.userName!=null){
	    userName= array[i].createUserModel.userName
	  }
	  var td5 = $("<td>"
	    + userName
	    + "</td>");
	  var td6 = $("<td class='hidden-480'>"
	    + date('Y-m-d H:i:s',array[i].createTime)
	      + "</td>");
	  if(array[i].status == 4){
	    var td7 = $("<td>"
	      +"<a href=\"#\" name=\"dbRefuseStatus\" rel=\"popover\" data-container=\"body\" data-toggle=\"popover\" data-placement=\"top\" data-trigger='hover' data-content=\""+ array[i].auditInfo + "\" style=\"cursor:pointer; text-decoration:none;\">"
	      + translateStatus(array[i].status)
		+"</a>"
	      + "</td>");
	  }else if(array[i].status == 2){
	    var td7 = $("<td>"
	      +"<a name=\"buildStatusBoxLink\" data-toggle=\"modal\" data-target=\"#create-mcluster-status-modal\" style=\"cursor:pointer; text-decoration:none;\">"
	      +"<i class=\"ace-icon fa fa-spinner fa-spin green bigger-125\" />"
	      +"创建中...</a>"
	      + "</td>");
	  }else{
	    var td7 = $("<td> <a>"
	      + translateStatus(array[i].status)
		+ "</a></td>");
	  }

	  if(array[i].status == 0 ||array[i].status == 5||array[i].status == 13){
	    var tr = $("<tr class=\"warning\"></tr>");

	  }else if(array[i].status == 3 ||array[i].status == 4||array[i].status == 14){
	    var tr = $("<tr class=\"default-danger\"></tr>");

	  }else{
	    var tr = $("<tr></tr>");
	  }

	  tr.append(td0).append(td1).append(td2).append(td3).append(td4).append(td5).append(td6).append(td7);
	  tr.appendTo(tby);

	  $('[name = "dbRefuseStatus"]').popover();
	}//循环json中的数据

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

function pageControl() {
  // 首页
  $("#firstPage").bind("click", function() {
    currentPage = 1;
    queryByPage();
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
      queryByPage();
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
      queryByPage();
    }
  });

  // 末页
  $("#lastPage").bind("click", function() {
    currentPage = $("#totalPage_input").val();
    queryByPage();
  });
}
function queryHcluster(){
	var options = $('#dbPhyMcluster');
	getLoading();
	$.ajax({
		cache:false,
		url:'/hcluster',
		type:'get',
		dataType:'json',
		success:function(data){
			removeLoading();
			var array = data.data;
			for(var i = 0, len = array.length; i < len; i++){
				
				var option = $("<option value=\""+array[i].id+"\" data-hclsName='"+array[i].hclusterName+"'>"
								+array[i].hclusterNameAlias
								+"</option>");
				options.append(option);
			}
			initChosen();
		}
	});
}
function page_init(){
  queryByPage();
  pageControl();
  // 新加2015-7-7
  queryHcluster();
  queryUser();
}
