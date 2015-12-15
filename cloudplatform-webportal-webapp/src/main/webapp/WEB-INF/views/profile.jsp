<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="zh">
<head>
	<meta charset="utf-8"/>
	<meta http-equiv="X-UA-compatible" content="IE=edge,chrome=1"/>
	<meta name="viewport" content="width=device-width,initial-scale=1, maximum-scale=1, user-scalable=no"/>
    <link rel="shortcut icon" href="/static/images/favicon.ico">
	<link rel="stylesheet" href="/static/stylesheets/bootstrap-f2d483c4e9.css">
    <link rel="stylesheet" href="/static/stylesheets/toaster-4f7c5e6480.css">
    <link rel="stylesheet" href="/static/stylesheets/common-06b30455c1.css">
    <link rel="stylesheet" type="text/css" href="/static/stylesheets/style-profile-d86d0b8502.css">
    <title>控制台-乐视云平台</title>
</head>
<style>
.main .side-bar{height:100%;margin-top:0;padding-top:10px;z-index:200;background:#ebebeb;}
.main .content-wrapper{width:1349px;width:100%;min-width:1349px;}
</style>
<body>
	<%@ include file="../includes/header.jsp"%>
    <div class="main">
        <%@ include file="../includes/sidebar.jsp"%>
        <div class="content-wrapper">
            <div ng-view="ng-view" class="content"></div>
        </div>
        <div class="clearfix"></div>
    </div>
    <script type="text/javascript" src="/static/javascripts/dist/require.min.js" data-main="/static/javascripts/dist/dashboard-main-1450162263403.js"></script>
</body>
</html>
