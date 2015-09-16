    <%@ page language="java" pageEncoding="UTF-8"%>
    <div class="header">
        <a href="/home/index.html" class="header-brand pull-left">
            <img src="/static/images/brand-logo.png" class="brand-logo" />
        </a>
        <a href="http://uc.letvcloud.com/userView/ucOverview.do" class="header-account pull-right">
            <img src="/static/images/nav-account.png" class="account-icon" />
            <span>${sessionScope.userSession.userName}</span>
        </a>
        <a href="/" class="header-help pull-right">
            <img src="/static/images/nav-help.png" class="help-icon" />
            <span>帮助中心</span>
        </a>
    </div>