$(function(){
	//隐藏搜索框
	$('#nav-search').addClass("hidden");
	
	pageinit();
	 var MAX_OPTIONS = 10;
	    $('#db_apply_form').bootstrapValidator({
	            feedbackIcons: {
	                valid: 'glyphicon glyphicon-ok',
	                invalid: 'glyphicon glyphicon-remove',
	                validating: 'glyphicon glyphicon-refresh'
	            },
	            fields: {
	            	userName: {
	                    validators: {
	                        notEmpty: {
	                            message: 'The question required and cannot be empty'
	                        },
	  			          stringLength: {
				              max: 40,
				              message: '用户名太长了!'
				          }
	                    }
	                },
	                password: {
	                    validators: {
	                        notEmpty: {
	                            message: 'The question required and cannot be empty'
	                        },
	  			          stringLength: {
				              max: 20,
				              message: '密码太长了!'
				          }
	                    }
	                },
	                readWriterRate: {
	                    validMessage: 'The business_description looks great',
	                    validators: {
	                        notEmpty: {
	                            message: '读写比例不能为空!'
	                        },
	                        regexp: {
	  		                  regexp: /^((\d|\d\d|\d\d\d)(\:(\d|\d\d|\d\d\d))){1}$/,
	  		                  message: '请按提示输入'
	  		              }
	                    }
	                },
	                maxConcurrency: {
	                    validMessage: 'The business_description looks great',
	                    validators: {
	                        notEmpty: {
	                            message: '最大并发量不能为空!'
	                        },integer: {
	                            message: '请输入数字'
	                        }
	                    }
	                },
	                'Ip[]': {
	                    validators: {
	                        notEmpty: {
	                            message: '地址不能为空'
	                        },
	  		              regexp: {
			                  regexp: /^(\d|1\d\d|2[0-4]\d|25[0-5])((\.(\d|1\d\d|2[0-4]\d|25[0-5]))|(\.\%)){3}$/,
			                  message: '请按提示格式输入'
			              }
	                    }
	                }
	            }
	        }).on('click', '.addButton', function() {
	            var $template = $('#optionTemplate'),
	                $clone    = $template
	                                .clone()
	                                .removeClass('hide')
	                                .removeAttr('id')
	                                .insertBefore($template),
	                $option   = $clone.find('[name="Ip[]"]');
	            $('#db_apply_form').bootstrapValidator('addField', $option);
	        }).on('click', '.removeButton', function() {
	            var $row    = $(this).parents('.form-group'),
	                $option = $row.find('[name="Ip[]"]');
	            $row.remove();
	            $('#db_apply_form').bootstrapValidator('removeField', $option);
	        }).on('added.field.bv', function(e, data) {
	            if (data.field === 'Ip[]') {
	                if ($('#db_apply_form').find(':visible[name="Ip[]"]').length >= MAX_OPTIONS) {
	                    $('#db_apply_form').find('.addButton').attr('disabled', 'disabled');
	                }
	            }
	        }).on('removed.field.bv', function(e, data) {
	           if (data.field === 'Ip[]') {
	                if ($('#db_apply_form').find(':visible[name="Ip[]"]').length < MAX_OPTIONS) {
	                    $('#db_apply_form').find('.addButton').removeAttr('disabled');
	                }
	            }
	        });
});

function checkboxControl(){
	$('th input:checkbox').click(function(){
		var that = this;
		$(this).closest('table').find('tr > td:first-child input:checkbox')
		.each(function(){
			this.checked = that.checked;
			$(this).closest('tr').toggleClass('selected');
		});
	});
}
function queryDbUser(){
	getLoading();
	$.ajax({ 
		cache:false,
		type : "get",
		url : "/dbUser/"+$("#dbId").val(),
		dataType : "json", /*这句可用可不用，没有影响*/
		success : function(data) {
			removeLoading();
			if(error(data)) return;
			var array = data.data;
			var tby = $("#tby");
			
			for (var i = 0, len = array.length; i < len; i++) {
				var td1 = $("<td class=\"center\">"
						    + "<label class=\"position-relative\">"
						    + "<input type=\"checkbox\" class=\"ace\"/>"
						    + "<span class=\"lbl\"></span>"
						    + "</label>"
					        + "</td>");
				var	td2 = $("<td>"
							+ array[i].username
							+ "</td>");
				var td3;
				if(array[i].type == 1){
					var td3 = $("<td>"
							    + "管理员"
							    + "</td>");
				}else if(array[i].type == 2){
					var td3 = $("<td>"
						    + "只读用户"
						    + "</td>");
				}else{
					var td3 = $("<td>"
							    + "读写用户"
							    + "</td>");
				}
				var td4 = $("<td>"
							+array[i].acceptIp
							+ "</td>");
				var td5 = $("<td>"
							+array[i].maxConcurrency
							+ "</td>");
				var td6 = $("<td>"
							+translateStatus(array[i].status)
							+ "</td>");
					
				if(array[i].status == 0 ||array[i].status == 5||array[i].status == 13){
					var tr = $("<tr class=\"warning\"></tr>");
				}else if(array[i].status == 3 ||array[i].status == 4||array[i].status == 14){
					var tr = $("<tr class=\"danger\"></tr>");
				}else{
					var tr = $("<tr></tr>");
				}
				
				tr.append(td1).append(td2).append(td3).append(td4).append(td5).append(td6);
				tr.appendTo(tby);
			}//循环json中的数据 
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
function queryDbInfo(){
	getLoading();
	$.ajax({ 
		cache:false,
		type : "get",
		url : "/db/"+$("#dbId").val(),
		dataType : "json", 
		success : function(data) {
			removeLoading();
			if(error(data)) return;
			var dbInfo = data.data;
			$("#headerDbName").append(dbInfo.dbName);
			$("#db_detail_table_dbname").text(dbInfo.dbName);
			$("#db_detail_table_belongUser").text(dbInfo.user.userName);
			$("#db_detail_table_createtime").text(date('Y-m-d H:i:s',dbInfo.createTime));
			for(var i=0,len = dbInfo.containers.length; i < len; i++)
			{
				var td1 = $("<th>"
						+ dbInfo.containers[i].containerName
						+"</th>");
				var td2 =$("<td>"
						+ dbInfo.containers[i].ipAddr
						+"</td>");
				var tr = $("<tr></tr>");
				tr.append(td1).append(td2);
				$("#db_detail_table").append(tr);
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

function pageinit(){
	checkboxControl();
	queryDbUser();
	queryDbInfo();
}
