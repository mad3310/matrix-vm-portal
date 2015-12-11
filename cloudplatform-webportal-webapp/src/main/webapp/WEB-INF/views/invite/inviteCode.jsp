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
	<link rel="stylesheet" href="/static/staticPage/css/common-e7a2afd556.css">
	<link rel="stylesheet" href="/static/staticPage/css/style-e013a852a2.css">
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
					<img src="" class="vali-codeimg formInput" id="idcode">
					<div class="error-msg hide">验证码错误，请重新输入</div>
				</div>
				<div class="formBtn"><div class="btn btn-le-blue invitebtn">验证</div></div>
			</form>
		</div>
		<div class="blockSuccess hide">
			<div class="blockTitle"><span>恭喜</span></div>
			<div class="blockDesc"><span>邀请码验证通过，可继续访问乐视云平台</span></div>
			<div><a><span>2s后自动跳转~</span></a></div>
			<div><span class="text-muted">若没有自动跳转，请点击</span><a href="http://lcp.letvcloud.com/profile"><span>这里</span></a></div>
		</div>
	</div>
</body>
</html>
<script src="/static/javascripts/jquery-1.11.3.js"></script>
<script src="${ctx}/static/page-js/invite/inviteCode-937ff0c377.js"></script>
<script>
	inviteBtnClick();
	codeimgClick();
</script>