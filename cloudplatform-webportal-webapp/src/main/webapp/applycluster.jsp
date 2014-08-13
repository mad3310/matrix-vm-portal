<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<div class="modal fade" id="modal-container-22341" role="dialog"
	aria-labelledby="smallModal" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">×</button>
				<h4 class="modal-title" id="myModalLabel">添加Mcluster</h4>
			</div>
			<div class="modal-body row">
				<form class="form-horizontal">
					<div class="col-md-offset-1 col-md-5">
						<div class="control-group">
							<label class="control-label" for="input">Mcluster名字</label>
							<div class="controls">
								<input type="text" id="inputEmail" class="email"
									placeholder="">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="inputEmail">数据库引擎</label>
							<div class="controls">
								<input type="text" id="inputEmail" class="email"
									placeholder="1G">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="inputPassword"></label>
							<div class="controls">
								<input type="password" id="inputPassword" class="password"
									placeholder="10G">
							</div>
						</div>


					</div>
					<div class="col-md-offset-1 col-md-5">
						<div class="control-group">
							<label for="name">备份周期</label>
							<div class="controls">
								<select>
									<option>1小时</option>
									<option>3小时</option>
									<option>12小时</option>
									<option>一天</option>
									<option>一周</option>
								</select>
							</div>
						</div>
						<div class="control-group">
							<label for="name">邮件通知</label>
							<div>
								<input id="emailmessage" type="checkbox" checked />
							</div>
						</div>
					</div>
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
				<button type="button" class="btn btn-primary">申请添加</button>
			</div>
		</div>

	</div>

</div>