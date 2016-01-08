<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    <!DOCTYPE html>
    <html>
       <head>
            <meta http-equiv="X-UA-compatible" content="IE=edge,chrome=1"/>
            <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
            <title>控制台-乐视云平台</title>
            <link rel="shortcut icon" href="/images/favicon.ico">
            <link rel="stylesheet" href="/stylesheets/vendor/vendor-7184beb203.css">
            <link rel="stylesheet" href="/stylesheets/common-9a4679b888.css">
            <link rel="stylesheet" href="/stylesheets/style-cloudvm-86873e957c.css">
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
            <script type="text/javascript" src="/javascripts/dist/require.min.js" data-main="/javascripts/dist/cloudvm-main-build-1452242154636.js"></script>
        </body>
    </html>
