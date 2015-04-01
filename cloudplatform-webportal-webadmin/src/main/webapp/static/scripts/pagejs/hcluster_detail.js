var numberOfHost = 0;
$(function(){
	//隐藏搜索框
	$('#nav-search').addClass("hidden");
	$('[name = "popoverHelp"]').popover();
	queryHost();
})

$('#add_host_form').bootstrapValidator({
    feedbackIcons: {
        valid: 'glyphicon glyphicon-ok',
        invalid: 'glyphicon glyphicon-remove',
        validating: 'glyphicon glyphicon-refresh'
    },
    fields: {
        hostName: {
            validators: {
                notEmpty: {
                    message: '主机名不能为空!'
                },
	       stringLength: {
		         max: 16,
		         message: '主机名过长!'
			}, 
			regexp: {
		         regexp: /^([a-zA-Z_]+[a-zA-Z_0-9]*)$/,
		         message: "请输入字母数字或'_',主机名不能以数字开头."
	        },
	        remote: {
                url: '/host/hostName/validate',
                message: '该主机名已存在!'
            }
	      }
        },
        hostIp: {
            validators: {
                notEmpty: {
                    message: '地址不能为空'
                },
            regexp: {
            regexp: /^(\d|\d\d|1\d\d|2[0-4]\d|25[0-5])(\.(\d|\d\d|1\d\d|2[0-4]\d|25[0-5])){3}$/,
            message: '请按提示格式输入'
        	}, 
          remote: {
                 url: '/host/hostIp/validate',
                 message: '此IP已存在!'
             }
            }
        }
    }
}).on('error.field.bv', function(e, data) {
	 $('#add_host_botton').addClass("disabled");
}).on('success.field.bv', function(e, data) {
 	$('#add_host_botton').removeClass("disabled");
});

function queryHost(){
	$("#tby tr").remove();
	getLoading();
	$.ajax({ 
		cache:false,
		type : "get",
		url : "/hcluster/"+$("#hclusterId").val(),
		dataType : "json", 
		success : function(data) {
			removeLoading();
			if(error(data)) return;
			var headerName = data.data.hcluster;
			/*if($('#headerHostName').html().indexOf(data.data.hcluster) < 0){
 				$("#headerHostName").append(data.data.hcluster);
			}*/
			if($('#hclusterTitle').html().indexOf(data.data.hcluster) < 0){
 				$("#hclusterTitle").prepend(data.data.hcluster);
			}
			var array = data.data.hclusterDetail;
			var tby = $("#tby");
			for (var i = 0, len = array.length; i < len; i++) {
				var td0 = $("<input name=\"host_id\" value= \""+array[i].hostId+"\" type=\"hidden\"/>");
				var td1 = $("<td>"
					    + array[i].hostNameAlias
				        + "</td>");
				var td2 = $("<td>"
					    + array[i].hostName
				        + "</td>");
				var td3;
				if(array[i].type == 0){
					td3 = $("<td>"
						+ "主机"
						+ "</td>");
				}else{
					td3 = $("<td>"
						+ "从机"
						+ "</td>");
				}
				var	td4 = $("<td>"
						+ array[i].hostIp
						+ "</td>");
				var	td5 = $("<td>"
						+ "正常"
						+ "</td>");
				var td6 = $("<td>"
						+"<div class=\"hidden-sm hidden-xs action-buttons\">"
						+"<a class=\"red\" href=\"#\" onclick=\"deleteHost(this)\" onfocus=\"this.blur();\" title=\"删除\" data-toggle=\"tooltip\" data-placement=\"right\">"
							+"<i class=\"ace-icon fa fa-trash-o bigger-120\"></i>"
						+"</a>"
						+"</div>"
						+ "</td>"
				);
				var tr = $("<tr></tr>");				
				tr.append(td0).append(td1).append(td2).append(td3).append(td4).append(td5).append(td6);
				tr.appendTo(tby);
				numberOfHost++;
			}
			$('[data-toggle = "tooltip"]').tooltip();
		}
	});
}

function deleteHost(obj){
	var tr = $(obj).parents("tr");
	var id =tr.find('[name="host_id"]').val();
	$.ajax({
		cache:false,
		url:'/host/isExistContainerOnHost/validate',
		type:'post',
		data:{ 'id' : id },
		success:function(data){
			if(data.valid){ //data.valid为true时可删除
				function deleteCmd(){
					var hostId =$(obj).parents("tr").find('[name="host_id"]').val();
					$.ajax({
						cache:false,
						url:'/host/'+hostId,
						type:'delete',
						success:function(data){
							if(error(data)) return;
							queryHost();
						}
					});
				}
				confirmframe("删除物理机","删除操作将丢失该物理机上的container!","您确定要删除?",deleteCmd);
			}else{
				warn("该物理机中含有container,删除完container后,才能执行此操作!",3000);
			}
		}
	});
}
function addHost(){
	$.ajax({
		cache:false,
		url: '/host',
        type: 'post',
        dataType: 'text',
        data: $("#add_host_form").serialize(),
        success: function (data) {
        	if(error(data)) return;
        	$("#add-host-form-modal").modal("hide");
        	queryHost();
			$('#add_host_form').find(":input").not(":button,:submit,:reset,:hidden").val("").removeAttr("checked").removeAttr("selected");
			$('#add_host_form').data('bootstrapValidator').resetForm();
			$('#type').val(1);
			$('#add_host_botton').addClass('disabled');
        }
	});
}
