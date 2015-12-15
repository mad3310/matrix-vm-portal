<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    <!DOCTYPE html>
    <html>
       <head>
            <meta http-equiv="X-UA-compatible" content="IE=edge,chrome=1"/>
            <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
            <title>控制台-乐视云平台</title>
            <link rel="shortcut icon" href="/static/images/favicon.ico">
            <link rel="stylesheet" href="/static/stylesheets/bootstrap-f2d483c4e9.css">
            <link rel="stylesheet" href="/static/stylesheets/font-awesome-81ce96f2e2.css">
            <link rel="stylesheet" href="/static/stylesheets/toaster-4f7c5e6480.css">
            <link rel="stylesheet" href="/static/stylesheets/rzslider-7c3df6f208.css">
            <link rel="stylesheet" href="/static/stylesheets/common-06b30455c1.css">
            <link rel="stylesheet" href="/static/stylesheets/style-cloudvm-86873e957c.css">
        </head>
        <style>
        .main .side-bar{height:100%;margin-top:0;padding-top:10px;z-index:200;background:#ebebeb;}
        .main .content-wrapper{width:1349px;width:100%;min-width:1349px;}
        </style>
        <body>
            <%@ include file="../../includes/header.jsp"%>
            <div class="main">
                <%@ include file="../../includes/sidebar.jsp"%>
                <div class="content-wrapper">
                    <div ng-view="ng-view" class="content"></div>
                </div>
                <div class="clearfix"></div>
            </div>
            <script type="text/javascript" src="/static/javascripts/dist/require.min.js" data-main="/static/javascripts/dist/cloudvm-main-1450074936172.js"></script>
        </body>
    </html>
