<%@ page language="java" pageEncoding="UTF-8"%>
<%@include file="/common/common.jsp"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>数据库审核</title>

</head>
<body>
	<div class="container">
		<%@include file="/common/header.jsp"%>
		<div id="wrap">
			<div class="row">
				<div class="col-md-12">
					<h3 class="text-left">DB审核</h3>
				</div>
				<hr style="FILTER: alpha(opacity = 0, finishopacity = 100, style = 1)" width="100%" color=#987cb9 SIZE=3></hr>
			</div>

			<div class="row clearfix">
				<div class="col-md-5 column">
					<div class="col-sm-12">
					<table class="table table-bordered" id="db_detail_table">	
						<caption>用户申请单</caption>
					</table>
					</div>
				</div>
				<div class="col-md-7 column">
					<ul id="myTab" class="nav nav-tabs">
					   <li class="active">
					      <a href="#agree" data-toggle="tab">同意</a>
					   </li>
					   <li>
					   	  <a href="#disagree" data-toggle="tab">不同意</a>
					   </li>
					</ul>					
					<div id="myTabContent" class="tab-content">
						<div class="tab-pane fade in active" id="agree">
						<form id="surveyForm" method="post" role="form" action="${ctx}/db/toMgrAudit/save">
						<input type="text" class="form-control hide" id="clusterId" name="clusterId"/>
						<input type="text" class="form-control hide" id="dbId" name="dbId"/>
						<input type="text" class="form-control hide" id="dbApplyStandardId" name="dbApplyStandardId"/>
						<div class="form-group" id="template" >
						<hr class="hide" style="FILTER: alpha(opacity = 0, finishopacity = 100, style = 1)" width="100%" color=#987cb9 SIZE=3></hr>
						    <div class="form-group  col-md-4">
						        <label>container名称</label>
						        <input type="text" class="form-control" id="containerName" name="containerName" placeholder="输入container名称" />
						    </div>
						    <div class="form-group  col-md-4">
						        <label>挂载目录</label>
						        <input type="text" class="form-control" id="mountDir" name="mountDir" placeholder="输入挂载目录" />
						    </div>
						    <div class="form-group  col-md-4">
						        <label>Zookeeper编号</label>
						        <input type="text" class="form-control" id="zookeeperId" name="zookeeperId" placeholder="输入Zookeeper编号" />
						    </div>
						    <div class="form-group  col-md-4">
						        <label>主机IP</label>
						        <select class="form-control" id="hostIpSelect" name="hostIpSelect">
								</select>
						    </div>
						    <div class="form-group  col-md-4">
						        <label>IP地址</label>
						        <input type="text" class="form-control" id="ipAddr" name="ipAddr" placeholder="输入container IP地址" />
						    </div>
						    <div class="form-group  col-md-4">
						        <label>网关</label>
						        <input type="text" class="form-control" id="gateAddr" name="gateAddr" placeholder="输入网关" />
						    </div>
						    <div class="form-group  col-md-4">
						        <label>子网掩码</label>
						        <input type="text" class="form-control" id="ipMask" name="ipMask" placeholder="输入container子网掩码" />
						    </div>
						    <div class="form-group  col-md-4">
						        <label>Cluster名称</label>
						        <input type="text" class="form-control" id="clusterNodeName" name="clusterNodeName" placeholder="输入Cluster名称" />
						    </div>
						    <div class="form-group  col-md-4">
						        <label>原名</label>
						        <input type="text" class="form-control" id="originName" name="originName" placeholder="输入原始名字" />
						    </div>
						    <div class="form-group  col-md-4">
						        <label>新名</label>
						        <input type="text" class="form-control" id="assignName" name="assignName" placeholder="输入新名字" />
						    </div>
						    <div class="form-group  col-md-4">
						        <label>类型</label>
						        <select class="form-control" id="type" name="type">
								  <option>VIP</option>
								  <option>normal</option>
								</select>
						    </div>
						    <div class="col-md-12">
					            <button type="button" class="btn btn-success addButton">
					                <i class="glyphicon glyphicon-plus"></i>
					            </button>
					            <button type="button" class="btn btn-danger removeButton hide" name="minusButton">
						                <i class="glyphicon glyphicon-minus"></i>
						        </button>	 
					        </div>
						</div>
						    <div id="submitBotton" class="form-group">
						        <div class="col-md-12">
						            <button type="submit" class="btn btn-primary  pull-right">确定</button>
						        </div>
						    </div>
						</form>
					   </div>
					   <div class="tab-pane fade" id="disagree">

					      <form role="form">
							  <div class="form-group form-group-lg">
							    <label class="control-label" for="formGroupInputLarge">原因</label>
							    <div>
							      <textarea id="disagreeForm" name="disagreeForm" class="form-control" rows="12" placeholder="请输入未通过原因"></textarea>
							    </div>
							  </div>
							  <button type="submit" class="btn btn-primary pull-right">确定</button>
						  </form>
					   </div>
					   </div>
				</div>
			</div>
		</div>
		<%@include file="/common/footer.jsp"%>
	</div>
</body>
<script type="text/javascript">
$(function(){
	queryByDbId("fb7241cc-5438-403b-a815-08c5c3ed67aa");
	dynamicField();
});
function queryByDbId(dbId) {
//	$("#db_detail_table tr").remove();
	$.ajax({ 
		type : "post",
		url : "${ctx}/db/list/dbApplyInfo?belongDb="
				+dbId,
				/* + "&dbName="
				+ $("#dbName").val() */
		dataType : "json", /*这句可用可不用，没有影响*/
		contentType : "application/json; charset=utf-8",
		success : function(data) {
			
			var value = data.data;
			var apply_table = $("#db_detail_table");
			$("#clusterId").val(value.clusterId);
			$("#dbId").val(value.belongDb);
			$("#dbApplyStandardId").val(value.id);
			apply_table.append("<tr><td>项目名称</td><td>"+value.applyName+"</td></tr>");
			apply_table.append("<tr><td>业务描述</td><td>"+value.descn+"</td></tr>");
			apply_table.append("<tr><td>链接类型</td><td>"+value.linkType+"</td></tr>");
			apply_table.append("<tr><td>最大访问量</td><td>"+value.maxConcurrency+"/s</td></tr>");
			apply_table.append("<tr><td>读写比例</td><td>"+value.readWriterRate+"</td></tr>");
			apply_table.append("<tr><td>开发语言</td><td>"+value.developLanguage+"</td></tr>");
			apply_table.append("<tr><td>IP访问列表</td><td>"+value.dataLimitIpList+"</td></tr>");
			apply_table.append("<tr><td>管理IP访问列表</td><td>"+value.mgrLimitIpList+"</td></tr>");
			apply_table.append("<tr><td>数据库引擎</td><td>"+value.engineType+"</td></tr>");
			apply_table.append("<tr><td>原数据库名</td><td>"+value.fromDbName+"</td></tr>");
			apply_table.append("<tr><td>原始数据库IP</td><td>"+value.fromDbIp+"</td></tr>");
			apply_table.append("<tr><td>原始数据库port</td><td>"+value.fromDbPort+"</td></tr>");
			apply_table.append("<tr><td>邮件通知</td><td>"+translateStatus(value.isEmailNotice)+"</td></tr>");
			apply_table.append("<tr><td>申请时间</td><td>"+value.createTime+"</td></tr>");
		},
		error : function(XMLHttpRequest,textStatus, errorThrown) {
			$('#pageMessage').html("<p class=\"bg-warning\" style=\"color:red;font-size:16px;\"><strong>警告!</strong>"+errorThrown+"</p>").show().fadeOut(3000);
		}
	});
}

function translateStatus(status){
	if(status = 1)
	{
		return "是";
	}else
	{
		return "否";
	}
	
}

function dynamicField(){
 var MAX_OPTIONS = 5;

    $('#surveyForm')
        .bootstrapValidator({
            feedbackIcons: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields: {
                question: {
                    validators: {
                        notEmpty: {
                            message: 'The question required and cannot be empty'
                        }
                    }
                },
                'option[]': {
                    validators: {
                        notEmpty: {
                            message: 'The option required and cannot be empty'
                        },
                        stringLength: {
                            max: 100,
                            message: 'The option must be less than 100 characters long'
                        }
                    }
                }
            }
        })

        // Add button click handler
        .on('click', '.addButton', function() {
            var $template = $('#template'),
                $clone    = $template
                                .clone()
                                .removeAttr('id')
                                .insertBefore('#submitBotton'),
                $option   = $clone.find('[name="option[]"]');
            	$clone.find('[name="minusButton"]').removeClass('hide');             
            	$clone.find("hr").removeClass('hide');
        		$clone.find("input").val('');
            // Add new field
            $('#surveyForm').bootstrapValidator('addField', $option);
        })

        // Remove button click handler
        .on('click', '.removeButton', function() {
            var $row    = $(this).parents('.form-group');

            // Remove element containing the option
            $row.remove();

            // Remove field
            $('#surveyForm').bootstrapValidator('removeField', $option);
        })

        // Called after adding new field
        .on('added.field.bv', function(e, data) {
            // data.field   --> The field name
            // data.element --> The new field element
            // data.options --> The new field options

            if (data.field === 'option[]') {
                if ($('#surveyForm').find(':visible[name="option[]"]').length >= MAX_OPTIONS) {
                    $('#surveyForm').find('.addButton').attr('disabled', 'disabled');
                }
            }
        })

        // Called after removing the field
        .on('removed.field.bv', function(e, data) {
           if (data.field === 'option[]') {
                if ($('#surveyForm').find(':visible[name="option[]"]').length < MAX_OPTIONS) {
                    $('#surveyForm').find('.addButton').removeAttr('disabled');
                }
            }
        });
}
</script>
</html>
