var currentPage = 1; //第几页
var recordsPerPage = 15; //每页显示条数
var timingTaskType="CRON";

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
  $("#dictSearch").click(function(){
    var iw=document.body.clientWidth;
    if(iw>767){//md&&lg
    }else{
      $('.queryOption').addClass('collapsed').find('.widget-body').attr('style', 'dispaly:none;');
      $('.queryOption').find('.widget-header').find('i').attr('class', 'ace-icon fa fa-chevron-down');
      var qryStr='';
      var qryStr1=$('#seDictName').val();var qryStr2=$('#seDictType').val();var qryStr3=$('#seDictDescn').val();
      if(qryStr1){
	qryStr+='<span class="label label-success arrowed">'+qryStr1+'<span class="queryBadge" data-rely-id="timingTaskName"><i class="ace-icon fa fa-times-circle"></i></span></span>&nbsp;'
      }
      if(qryStr2){
	qryStr+='<span class="label label-warning arrowed">'+qryStr2+'<span class="queryBadge" data-rely-id="timingTaskDescn"><i class="ace-icon fa fa-times-circle"></i></span></span>&nbsp;'
      }
      if(qryStr3){
	qryStr+='<span class="label label-warning arrowed">'+qryStr3+'<span class="queryBadge" data-rely-id="timingTaskDescn"><i class="ace-icon fa fa-times-circle"></i></span></span>&nbsp;'
      }
      if(qryStr){
	$('.queryOption').find('.widget-title').html(qryStr);
	$('.queryBadge').click(function(event) {
	  var id=$(this).attr('data-rely-id');
	  $('#'+id).val('');
	  $(this).parent().remove();
	  queryByPage();
	  if($('.queryBadge').length<=0){
	    $('.queryOption').find('.widget-title').html('字典查询条件');
	  }
	  return;
	});
      }else{
	$('.queryOption').find('.widget-title').html('字典查询条件');
      }
    }
    queryByPage();
  });
  $("#dictClearSearch").click(function(){
    var clearList = ["seDictName","seDictDescn","seDictType"];
    clearSearch(clearList);
  });
  enterKeydown($(".page-header > .input-group input"),queryByPage);
});
function queryByPage() {
  var name = $("#seDictName").val();
  var type=$('#seDictType').val();
  var  descn= $("#seDictDescn").val();
  var queryCondition = {
    'currentPage':currentPage,
    'recordsPerPage':recordsPerPage,
    'name':name?name:'',
    'type':type?type:'',
    'descn':descn?descn:''
  }
  $("#tby tr").remove();
  getLoading();
  $.ajax({
    cache:false,
    type : "get",
    url :queryUrlBuilder("/dictionary",queryCondition),
    dataType : "json",
    success : function(data) {
      removeLoading();
      if(error(data)) return;
      var array = data.data.data;
      var tby = $("#tby");
      var totalPages = data.data.totalPages;
      for (var i = 0, len = array.length; i < len; i++) {
	var td1 = $("<td class=\"center\">"
		    +"<label class=\"position-relative\">"
		    +"<input name=\"dict_id\" value= \""+array[i].id+"\" type=\"checkbox\" class=\"ace\"/>"
		    +"<span class=\"lbl\"></span>"
		    +"</label>"
		    +"</td>");
	var td2 = $("<td class='tdDictName'>"
		    +array[i].name
		    + "</td>");
	var td3 = $("<td class='hidden-480 tdDictType'>"
		    +array[i].type
		    +" </td>");
	var td4 = $("<td class='hidden-480 tdDictDescn'>"
		    + array[i].descn
		    + "</td>");
	var td5 = $("<td>"
		    +"<div class=\"action-buttons\">"
		    +"<a class=\"green action-modify\" href=\"#\" title=\"修改\" data-toggle=\"tooltip\" data-placement=\"right\">"
		    +"<i class=\"ace-icon fa fa-pencil bigger-130\"></i>"
		    +"</a>"
		    +"<a class=\"red action-delete\" href=\"#\" title=\"删除\" data-toggle=\"tooltip\" data-placement=\"right\">"
		    +"<i class=\"ace-icon fa fa-trash-o bigger-130\"></i>"
		    +"</a>"
		    +"</div>"
		    + "</td>"
		   );
	var tr = $("<tr></tr>");
	tr.append(td1).append(td2).append(td3).append(td4).append(td5);
	tr.appendTo(tby);
      }//循环json中的数据
      /*初始化tooltip*/
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
    error : function(XMLHttpRequest,textStatus, errorThrown) {
      error(XMLHttpRequest);
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
function formValidate(){
  $("#add-dict-form").bootstrapValidator({
    message: '无效的输入',
    feedbackIcons: {
    },
    fields: {
      dictName: {
        validMessage: '请按提示输入',

        validators: {
          notEmpty: {
            message: '名称不能为空!'
          },
	  stringLength: {
	    max: 40,
	    message: '字典名过长'
	  },regexp: {
	    regexp: /^([a-zA-Z_0-9]*)$/,
  	    message: "请输入字母数字或'_'"
          }
	}
      },
      dictType: {
        validMessage: '请按提示输入',
        validators: {
          notEmpty: {
            message: '类型不能为空!'
          },
	  regexp: {
	    regexp: /^([0-9]*)$/,
  	    message: "请输入数字"
          }
	}
      },
      dictDescn:{

      }
    }
  }).on('success.form.bv', function(e) {
    e.preventDefault();
    $("#add-dict-modal").modal("hide");
    addDict();
  });
}
function modifyFormValidate(){
  $("#modify-dict-form").bootstrapValidator({
    message: '无效的输入',
    feedbackIcons: {
    },
    fields: {
      modifyDictName: {
        validMessage: '请按提示输入',

        validators: {
          notEmpty: {
            message: '名称不能为空!'
          },
	  stringLength: {
	    max: 40,
	    message: '字典名过长'
	  },regexp: {
	    regexp: /^([a-zA-Z_0-9]*)$/,
  	    message: "请输入字母数字或'_'"
          }
	}
      },
      modifyDictType: {
        validMessage: '请按提示输入',
        validators: {
          notEmpty: {
            message: '类型不能为空!'
          },
	  regexp: {
	    regexp: /^([0-9]*)$/,
  	    message: "请输入数字"
          }
	}
      },
      modifyDictDescn:{

      }
    }
  }).on('success.form.bv', function(e) {
    e.preventDefault();
    $("#modify-dict-modal").modal("hide");
    modifyDict();
  });
}
function delDict(obj){
  function delCmd(){
    var dictId =$(obj).parents("tr").find('[name="dict_id"]').val();
    getLoading();
    $.ajax({
      cache:false,
      url:'/dictionary/'+dictId,
      type:'delete',
      success:function(data){
        removeLoading();
        if(error(data)) return;
        location.href = "/list/dictMgr";
      }
    });
  }
  confirmframe("删除","您确定要删除该字典？","如果不了解，请不要删除！",delCmd);
}

function modifyDict(){
  var data={
    id:$('#modifyDictId').val(),
    name:$('#modifyDictName').val(),
    type:$('#modifyDictType').val(),
    descn:$('#modifyDictDescn').val()
  }
  getLoading();
  $.ajax({
    cache:false,
    type:'post',
    url:'/dictionary/'+data.id,
    dataType:'json',
    data:data,
    success:function(){
      removeLoading();
      if(error(data)) return;
      location.href='/list/dictMgr';
    }
  });
}
function addDict(){
  var data={
    name:$('#dictName').val(),
    type:$('#dictType').val(),
    descn:$('#dictDescn').val()
  }
  getLoading();
  $.ajax({
    cache:false,
    type:'post',
    url:'/dictionary',
    dataType:'json',
    data:data,
    success:function(){
      removeLoading();
      if(error(data)) return;
      location.href='/list/dictMgr';
    }
  });
}
function operationBtnInit(){
  $("#tby").click(function(e){
    e = e? e:window.event;
    var _target = e.target || e.srcElement;
    var aTarget = $(_target).parent()[0];
    var aTargetClassList = aTarget.classList;
    if(aTargetClassList.contains("action-modify")){
      var rootNode = $(aTarget).parents("tr");
      var dictId = rootNode.find('[name="dict_id"]').val();
      var dictName = rootNode.find('.tdDictName').html();
      var dictType = rootNode.find('.tdDictType').html();
      var dictDescn = rootNode.find('.tdDictDescn').html();
      $('#modifyDictId').val(dictId);
      $('#modifyDictName').val(dictName);
      $('#modifyDictType').val(dictType);
      $('#modifyDictDescn').val(dictDescn);
      $("#modify-dict-modal").modal("show");
    }else if(aTargetClassList.contains("action-delete")){
      delDict(aTarget);
    }
  });
}

function page_init(){
  queryByPage();
  pageControl();
  formValidate();
  modifyFormValidate();
  operationBtnInit();
  $('[name = "popoverHelp"]').popover();
}
