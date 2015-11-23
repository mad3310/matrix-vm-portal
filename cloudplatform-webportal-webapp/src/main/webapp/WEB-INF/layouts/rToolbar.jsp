<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<style>
.amz-sidebar-toggle {position: fixed;right: 10px;bottom: 10px;z-index: 1000;opacity: .5;-webkit-transition: .2s;transition: .2s;}
.m-top{position: fixed;right: 10px;bottom: 60px;z-index: 1000;opacity: .5;-webkit-transition: .2s;transition: .2s;}
.am-icon-btn {box-sizing: border-box;width: 48px;height: 48px;font-size: 24px;line-height: 48px;border-radius: 50%;background-color: #eee;color: #555;text-align:center;}
</style>
<a title="回到顶部" class="am-icon-btn m-top hidden-sm hidden-md hidden-lg" id="amz-go-top" style="display:none;"><i class="fa fa-arrow-up"></i></a>
<a class="am-icon-btn  amz-sidebar-toggle hidden-sm hidden-md hidden-lg hide"><i class="fa fa-list"></i></a>