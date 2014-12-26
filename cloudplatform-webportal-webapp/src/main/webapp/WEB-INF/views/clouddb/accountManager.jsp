<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="zh">

<head>
	<meta charset="utf-8"/>
	<meta http-equiv="X-UA-compatible" content="IE=edge,chrome=1"/>
	<meta name="viewpoint" content="width=device-width,initial-scale=1"/>
	<!-- bootstrap css -->
	<link type="text/css" rel="stylesheet" href="${ctx}/static/css/bootstrap.min.css"/>
	<!-- ui-css -->
	<link type="text/css" rel="stylesheet" href="${ctx}/static/css/ui-css/common.css"/>
	<!-- bootstrapValidator-->
	<link type="text/css" rel="stylesheet" href="${ctx}/static/css/bootstrapValidator.css"/>

	<title>RDS账户管理</title>
</head>

<body>
	<!-- 全局参数 start -->
	<input class="hidden" value="${dbId}" name="dbId" id="dbId" type="text" />
	<!-- 全局参数 end -->
	<!-- 账号管理主界面div -->
	<div id="accountList" class="" role="tablist" aria-multiselectable="true">
		<div class="se-heading" id="headingOne">
			<div class="pull-left">
				<h4>账号管理</h4>
			</div>
			<div class="pull-right">
				<button id="refresh" class="btn btn-default">
					<span class="glyphicon glyphicon-refresh"></span>
					刷新
				</button>
				<button class="btn btn-primary toCreateAccount">创建帐号</button>
			</div>
		</div>
		<div class="table-responsive">
			<table class="table table-hover table-se">
				<thead>
					<tr>
						<th width="25%">帐号</th>
						<th width="15%">状态</th>
						<th width="15%">读写比例</th>
						<th>频次限制</th>
						<th class="text-right" width="30%">
							<span style="padding-left:8px">操作</span>
						</th>
					</tr>
				</thead>
				<tbody id="tby">
				</tbody>
			</table>
		</div>
	</div>
	<div id="ipListTab" class="mc-hide" role="tablist" aria-multiselectable="true">
		<!-- heading部分 -->
		<div class="se-heading" id="headingOne">
			<div class="pull-left">
				<h4>IP列表</h4>
			</div>
			<a class="pull-left toCreateAccount">返回创建新账号</a>
		</div>
		<div id="ipList" role="tabpanel" class="tab-pane fade active in"
			aria-labelledby="whitelist-tab">
			<table class="table table-hover">
				<thead>
					<tr>
						<th colspan="4">允许访问IP名单</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td width="25%">10.23.12.24/22</td>
						<td width="25%">0.0.0.0/0</td>
						<td width="25%">33.22.11.44</td>

						<td width="25%">10.23.12.24/21</td>

					</tr>
				</tbody>
			</table>
			<div class="has-warning help-block">
				您已添加<span class="">7</span>个IP，还能添加<span class="ng-binding">93</span>个。
			</div>
			<div class="" style="margin-bottom: 40px">
				<button id="modifyIpList" class="btn btn-primary">手动修改</button>
			</div>
		</div>
		<form id="ipForm" class="form-horizontal ng-pristine ng-valid ng-scope hide" role="form" novalidate="" name="security_form" security-list-man="">
			<div class="form-group">
				<label class="col-sm-2">允许访问IP名单：</label>
			</div>
			<div ng-hide="!loadingState" aliyun-loading="" size="48" style="margin-top: 10px" class="ng-hide">
			</div>
			<div class="form-group">
				<div class="col-sm-4">
					<textarea name="iplist" class="form-control" rows="4"></textarea>				
				</div>
				<div class="help-block ng-hide" style="padding-top: 30px"></div>
			</div>
			<div class="form-group">
				<div class="col-sm-10">
					<span class="help-block">请以逗号隔开，不可重复，最多100个。</span>
                    <span class="help-block">支持格式如：0.0.0.0/0，10.23.12.24（IP），10.23.12.24/24（CIDR模式，无类域间路由，/24表示了地址中前缀的长度，范围[1,32]）。</span>
					<span class="help-block">0.0.0.0/0和空代表不设IP访问的限制，数据库将会有高安全风险。建议仅将您的WEB服务器外网IP/IP段设为可访问权限。</span>
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-4">
					<button  class="btn btn-primary ipFromBotton">确定</button>
					<button  class="btn btn-default ipFromBotton">取消</button>
				</div>
			</div>
		</form>
	</div>
	<!-- 点击“创建账号”后加载的div 去掉mc-hide既可以显示此div-->
	<div id="newAccountTab" class="mc-hide" role="tablist" aria-multiselectable="true">
		<!-- heading部分 -->
		<div class="se-heading">
			<div class="pull-left">
				<h4>创建新账号</h4>
			</div>
			<a class="pull-left toAccountList">返回帐号管理</a>
		</div>
		<!-- 内容部分，由一个form承载 -->
		<div style="width:auto;height:auto;">
			<form id="db_user_create_form" role="form" class="form-horizontal" name="account_modify_form">
				<!-- 数据库账号模块 -->
				<div class="form-group">
					<label class="col-sm-2 control-label">
						<span class="text-danger">*</span>
						数据库帐号：
					</label>
					<div class="col-sm-8 row">
						<!-- ngIf: !newAccount -->
						<div class="col-sm-4">
							<!-- ngIf: newAccount && !isJst -->
								<input name="username" class="form-control input-radius-2" type="text">
						</div>
						<div class="col-sm-12 notice-block">
							<p class="text-correct">由小写字母，数字、下划线组成、字母开头，字母或数字结尾，最长16个字符</p>
						</div>
					</div>
				</div>
				<!-- 数据库账号模块end -->
				<!-- 授权数据库模块 -->
				<div class="form-group multi-select">
					<label class="col-sm-2 control-label" style="height:190px;margin-right:15px">授权IP：</label>
					<div class="inline-block mcluster-select" style="width:180px">
						<div class="select-head clearfix">
							<p class="pull-left">未授权IP</p>
                            <p class="pull-right">
                                <a class="toIpList">管理IP列表</a>
                            </p>
						</div>
						<div class="select">
							<ul class="select-list select-list-left">
							</ul>
							<!-- 没有数据记录时显示暂无数据 -->
							<div class="select-msg mc-hide">
								<span class="text-muted">暂无数据</span>
							</div>
						</div>
					</div>
					<div class="inline-block" style="width: 60px;text-align:center;height:183px;padding-top:60px">
						<div style="margin-bottom:5px">
							<a class="btn_db_add">授权&nbsp;&gt;</a>
						</div>
						<div>
							<a class="btn_db_remove">&lt;&nbsp;移除</a>
						</div>
					</div>
					<div class="inline-block mcluster-select" style="width:380px;height:100%">
						<div class="select-head clearfix">
							<p class="pull-left">已授权IP</p>
							<p class="pull-right">
								<span style="padding-right: 5px;color:#bbb">权限</span>
								<a class="select-all-rw">全部设读写</a>
							</p>
						</div>
						<div class="select">
							<div class="select-wrap">
								<ul class="select-list select-list-right">
								</ul>
								<!--无数据时显示暂无数据 -->
								<div class="select-msg mc-hide">
									<span class="text-muted">暂无数据</span>
								</div>
							</div>
						</div>
					</div>
				</div>
				<!-- 授权数据库模块end -->
				<!-- 密码输入模块 -->
				<div class="form-group">
					<label class="col-sm-2 control-label">
						<span class="text-danger">*</span>
						读写比例：
					</label>
					<div class="col-sm-8 row">
						<!-- 密码输入框 -->
						<div class="col-sm-4">
							<input name="readWriterRate" class="form-control input-radius-2" type="password">
					    </div>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">
						<span class="text-danger">*</span>
						最大并发量：
					</label>
					<div class="col-sm-8 row">
						<!-- 密码输入框 -->
						<div class="col-sm-4">
							<input name="maxConcurrency" class="form-control input-radius-2" type="password">
						</div>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">
						<span class="text-danger">*</span>
						密码：
					</label>
					<div class="col-sm-8 row">
						<!-- 密码输入框 -->
						<div class="col-sm-4">
							<input name="newPwd2" class="form-control input-radius-2" type="password">
						</div>
						<!-- 密码规则提示 -->
						<div class="notice-block col-sm-12">
							<p class="">由字母、数字、中划线或下划线组成，长度6~32位</p>
						</div>
					</div>
				</div>
				<!-- 密码输入模块end -->
				<!-- 确认密码模块 -->
				<div class="form-group">
					<label class="col-sm-2 control-label">
						<span class="text-danger">*</span>
						确认密码：
					</label>
					<div class="col-sm-8 row">
						<!-- 确认密码输入框 -->
						<div class="col-sm-4">
							<input name="newPwd2" class="form-control input-radius-2" type="password">
						</div>
					</div>
				</div>
				<!-- 密码确认模块end -->
				<!-- 备注说明模块 -->
				<div class="form-group">
					<label class="col-sm-2 control-label">备注说明：</label>
					<!-- 备注输入框 -->
					<div class="col-sm-10 row">
						<div class="col-sm-4">
							<textarea name="accountDesc" class="form-control" style="width:100%;height:90px"></textarea>
						</div>
						<!-- 备注输入超过长度限制提示 -->
						<div class="col-sm-8 help-info mc-hide">
							<small class="text-danger" >
								<span class="glyphicon glyphicon-remove-sign"></span>
								备注说明最多256个字符
							</small>
						</div>
						<!-- 备注信息规则静态提示 -->
						<div class="col-sm-12 notice-block">
							<p class="text-correct">请输入备注说明，最多256个字符(一个汉字等于3个字符)</p>
						</div>
					</div>
				</div>
				<!-- 备注说明模块end -->
				<!-- 按钮模块 -->
				<div class="form-group">
					<label class="col-sm-2 control-label"></label>
					<div class="col-sm-4">
						<button disabled="disabled" type="button" class="btn btn-success btn-disable">提交</button>
						<button type="button" class="btn btn-default">返回</button>
					</div>
				</div>
				<!-- 按钮模块end -->
			</form>
		</div>
	</div>

</body>
<!-- js -->
<script type="text/javascript" src="${ctx}/static/modules/seajs/2.3.0/sea.js"></script>
<script type="text/javascript">

// Set configuration
seajs.config({
	base: "${ctx}/static/modules/",
	alias: {
		"jquery": "jquery/2.0.3/jquery.min.js",
		"bootstrap": "bootstrap/bootstrap/3.3.0/bootstrap.js",
		"bootstrapValidator": "bootstrap/bootstrapValidator/0.5.3/bootstrapValidator.js"
	}
});

seajs.use("${ctx}/static/page-js/accountManager/main");
</script>

</html>
