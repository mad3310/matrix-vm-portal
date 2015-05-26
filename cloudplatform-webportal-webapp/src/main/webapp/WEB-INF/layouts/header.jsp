<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-- top bar begin -->
	<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation" style="min-height:40px;height:40px;">
      <div class="container-fluid">
        <div class="navbar-header">
          <a class="navbar-brand color" href="${ctx}/dashboard" style="padding-top:2px;height:40px !important;"><img src="${ctx}/static/img/logo.png"/></a>
		  <a class="navbar-brand color top-bar-btn" href="${ctx}/dashboard" style="white-space:nowrap; font-size:13px"><i class="fa fa-home text-20"></i></a>
        </div>
        <div id="navbar" class="navbar-collapse collapse pull-right">
            <ul class="nav navbar-nav">
	            <li><a href="javascript:void(0)" class="hlight"><span class="glyphicon glyphicon-bell"></span></a></li>
	            <li class="dropdown">
	              <a href="javascript:void(0)" class="dropdown-toggle hlight" data-toggle="dropdown">${sessionScope.userSession.userName}<span class="caret"></span></a>
	              <ul class="dropdown-menu" role="menu">
	                <li><a href="javascript:void(0)">用户中心</a></li>
	                <li><a href="javascript:void(0)">我的订单</a></li>
	                <li><a href="javascript:void(0)">账户管理</a></li>
	                <li class="divider"></li>
	                <li><a href="${ctx}/account/logout">退出</a></li>
	              </ul>
	            </li>
	            <li><a href="javascript:void(0)" class="hlight"><span class="glyphicon glyphicon-lock"></span></a></li>
	            <li><a href="javascript:void(0)" class="hlight"><span class="glyphicon glyphicon-pencil"></span></a></li>
          </ul>
        </div><!--/.nav-collapse -->
      </div>
    </nav>
<!-- top bar end -->

<!-- navbar begin -->
<div class="navbar navbar-default mt40" style="margin-bottom: 0px !important;"> 
  <div class="container-fluid">
    <div class="navbar-header">
      <a class="navbar-brand" href="${ctx}/dashboard">Le云控制台首页</a>
    </div>
  </div>
</div>
    <!-- navbar end -->