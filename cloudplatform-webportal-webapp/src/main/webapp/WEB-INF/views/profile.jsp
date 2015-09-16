<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="zh">
<head>
	<meta charset="utf-8"/>
	<meta http-equiv="X-UA-compatible" content="IE=edge,chrome=1"/>
	<meta name="viewport" content="width=device-width,initial-scale=1, maximum-scale=1, user-scalable=no"/>
    <link rel="shortcut icon" href="/static/images/favicon.ico">
	<link rel="stylesheet" href="/static/stylesheets/bootstrap.css">
    <link rel="stylesheet" href="/static/stylesheets/font-awesome.css">
    <link rel="stylesheet" href="/static/stylesheets/common.css">
    <link rel="stylesheet" href="/static/stylesheets/style.css">
    <link rel="stylesheet" type="text/css" href="/static/stylesheets/style-profile.css">
	<title>Le云控制台</title>
</head>
<style>
.main .side-bar{height:100%;margin-top:0;padding-top:10px;z-index:200;background:#ebebeb;}
.main .content-wrapper{width:1630px;width:100%;min-width:1630px;}
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
	<script type="text/javascript" src="/static/javascripts/require.js" data-main="/static/apps/dashboard/main.js"></script>
</body>
</html>
