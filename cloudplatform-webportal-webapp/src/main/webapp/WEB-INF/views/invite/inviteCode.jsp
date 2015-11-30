<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="renderer" content="webkit">
	<meta name="Keywords" content="乐视云计算，云计算，VaaS，视频存储，免费空间，企业视频，云主机，开放平台">
	<title>乐视云计算-最专业的VaaS云平台</title>
	<link rel="shortcut icon" href="/static/staticPage/img/favicon.ico">
	<link rel="stylesheet" href="/static/staticPage/css/common.css">
	<link rel="stylesheet" href="/static/staticPage/css/style.css">
</head>
<body class="invitebg">
	<div class="inviteBlock">
		<div class="blockInputs">
			<span class="blockTitle">乐视云邀请码</span>
			<form class="blockForm">
				<input type="text" class="formInput inviteCode" placeholder="请输入邀请码" onchange="inviteVali($(this))" onpropertychange="inviteVali($(this))"/>
				<div class="error-msg hide"></div>
				<div class="valicode hide" id="valicode">
					<input type="text" class="valicode-input formInput" id="input_idcode" placeholder="点击图片刷新验证码" onchange="codeVali($(this))" onpropertychange="codeVali($(this))"/>
					<img src="../kaptcha" class="vali-codeimg formInput">
					<div class="error-msg hide">验证码错误，请重新输入</div>
				</div>
				<div class="formBtn"><div class="btn btn-le-blue invitebtn">验证</div></div>
			</form>
		</div>
		<div class="blockSuccess hide">
			<div class="blockTitle"><span>恭喜</span></div>
			<div class="blockDesc"><span>邀请码验证通过，可继续访问乐视云平台</span></div>
			<div><a><span>5s后自动跳转~</span></a></div>
		</div>
	</div>
</body>
</html>
<script src="/static/javascripts/jquery-1.11.3.js"></script>
<script>
	function inviteVali(obj){
		var flag=false;
		var reg=/^[a-zA-z0-9]{12}$/;
		if(reg.test(obj.val())){
			flag=true;
			obj.removeClass('has-error')
				.next('.error-msg').addClass('hide');
		}else{
			obj.addClass('has-error')
				.next('.error-msg').removeClass('hide').text('邀请码格式错误');
		}
		return flag;
	}
	function codeVali(obj){
		var flag=false;
		var code=obj.val();
		$.ajax({
			url: '${ctx}/kaptcha',
			async: false,
			type: 'post',
			data: {'kaptcha':code},
			success:function(data){
				if(data.data){
					flag=true;
					obj.removeClass('has-error')
						.next().next('.error-msg').addClass('hide');
				}else{
					flag=false;
					obj.addClass('has-error')
						.next().next('.error-msg').removeClass('hide').text('验证码错误，请重新输入');
				}
				return flag;
			}
		});
		
	}
	function totalValify(){
		var flag=true;
		var inviteresult=inviteVali($('.inviteCode'));
		var coderesult=codeVali($('.valicode-input'));
		if(inviteresult&&coderesult){
		}else{
			flag=false;
			return flag;
		}
	}
	function ifneedcode(){
		return $.ajax({
			url: '${ctx}/kaptcha/isNeed',
			type: 'get',
			success:function(data){
				var _target=$('#valicode');
				if(data.data){//需要
					_target.removeClass('hide');
				}else{
					_target.addClass('hide')
				}
			}
		});
	}
	function inviteBtnClick(){
		var _target=$('.invitebtn').unbind('click').click(function(event){
			var invitecode=$('.inviteCode').val(),code=$('.valicode-input'),inputdata='';
			var ifneedcodeResult=ifneedcode();
			ifneedcodeResult.done(function(data){
				if(data.data){//需要验证码
					if(totalValify()){
						inputdata={
							'inviteCode':invitecode,
							'kaptcha':code
						}
					}
				}else{
					if(inviteVali($('.inviteCode'))){//只需要验证邀请码
						inputdata={
							'inviteCode':invitecode
						}
					}
				}
				console.log(inputdata)
				$.ajax({
					url:'${ctx}/inviteCode/verify',
					type:'post',
					data:inputdata,
					success:function(data){
						if(data.data==0){
							$('.blockInputs').removeClass('hide');
							$('.blockSuccess').addClass('hide');
							$('.inviteCode').addClass('has-error').next('.error-msg').removeClass('hide').text(data.msgs);
						}else if(data.data==1){//验证通过
							$('.inviteCode').removeClass('has-error').next('.error-msg').addClass('hide');
							$('.blockInputs').addClass('hide');
							$('.blockSuccess').removeClass('hide');
						}else{
							$('.blockInputs').removeClass('hide');
							$('.blockSuccess').addClass('hide');
							$('.inviteCode').addClass('has-error').next('.error-msg').removeClass('hide').text(data.msgs);
						}
					}
				})
			});
		});
	}
	inviteBtnClick();
</script>