var currentPage = 1; //第几页
var recordsPerPage = 15; //每页显示条数
var currentSelectedLineDbName = 1;

$(function(){
  //初始化
  page_init();
})

  function queryByPage() {
  	var dbName = $("#dbName").val()?$("#dbName").val():'';
	var mclusterName = $("#dbMcluster").val()?$("#dbMcluster").val():'';
	var hclusterName = $("#dbPhyMcluster").val()?$("#dbPhyMcluster").val():'';
	var userName = $("#dbuser").val()?$("#dbuser").val():'';
	/*var createTime = $("#PhyMechineDate").val()?$("#PhyMechineDate").val():'null';*/
	var status = $("#dbStatus").val()?$("#dbStatus").val():'';
	var queryCondition = {
			'currentPage':currentPage,
			'recordsPerPage':recordsPerPage
			//'dbName':dbName,
			//'mclusterName':mclusterName,
			//'hclusterName':hclusterName,
			//'userName':userName,
			/*'createTime':createTime,*/
			//'status':status
	}
    $("#tby tr").remove();
    getLoading();
    $.ajax({
      cache:false,
      type : "get",
      // url : '/slb',
      url : queryUrlBuilder("/gce",queryCondition),
      dataType : "json", /*这句可用可不用，没有影响*/
      contentType : "application/json; charset=utf-8",
      success : function(data) {
	removeLoading();
	error(data);
	var array = data.data.data;
	var tby = $("#tby");
	var totalPages = data.data.totalPages;
	for (var i = 0, len = array.length; i < len; i++) {
	  var td0 = $("<input class=\"hidden\" type=\"text\" value=\""+array[i].mclusterId+"\"\> ");
	  var td1 = $("<td class=\"center\">"
	    +"<label class=\"position-relative\">"
	    +"<input type=\"checkbox\" class=\"ace\"/>"
	    +"<span class=\"lbl\"></span>"
	    +"</label>"
	    +"</td>");
	  var td2;
	  if(array[i].status == 6){
	    td2 = $("<td>"
	      + "<a class=\"link\" href='#'>"+array[i].gceServerProxy.gceName+"</a>"
	      + "</td>");//href=\"/detail/db/"+array[i].id+"\"
	  }else if(array[i].status == 0 ||array[i].status == 3){
	    td2 = $("<td>"
	      + "<a class=\"link\" class=\"danger\" href='#'>"+array[i].gceServerProxy.gceName+"</a>"
	      + "</td>");//href=\"/audit/db/"+array[i].id+"\"
	  }else{
	    td2 = $("<td>"
	      + "<a class=\"link\" style=\"text-decoration:none;\">"+array[i].gceServerProxy.gceName+"</a>"
	      + "</td>");
	  }
	  if(array[i].gceCluster){
	    var td3 = $("<td class='hidden-480'>"
	      + "<a class=\"link\" href=\"/detail/gce/cluster/" + array[i].gceClusterId+"\">"+array[i].gceCluster.clusterName+"</a>"
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

function page_init(){
  queryByPage();
  pageControl();
}
