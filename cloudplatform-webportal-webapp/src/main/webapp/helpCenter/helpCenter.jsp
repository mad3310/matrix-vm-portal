<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE>
<meta charset="utf-8">
<meta name="keyword" content="letv,帮助中心"/>
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-compatible" content="IE=edge,chrome=1"/>
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<link type="text/css" rel="stylesheet" href="${ctx}/static/css/ui-css/help-common.css"/>
<link type="text/css" rel="stylesheet" href="${ctx}/static/css/font-awesome.min.css"/>
<link rel="shortcut icon" href=" ${ctx}/static/img/favicon2.ico">
<script type="text/javascript" src="${ctx}/static/modules/jquery/2.0.3/jquery.min.js"></script>
<title>帮助中心</title>
<div class="header y-row">
    <h2 class="slogo"><a href="#">帮助中心</a></h2>
    <div class="search-wrap">
        <input id="search-tip-target" type="hidden" value="">
        <input id="search-target" type="hidden" value="">
        <div class="search-frame">
            <input class="search-input ac_input" placeholder="（暂不可用）请简单输入您的关键词，如&quot;云服务器&quot;" autocomplete="off">
        </div>
        <span class="fa fa-search search-btn"></span>
        <!--TMS:1667817-->
        <div class="hot-keyword">
            <span>搜索热词：</span><br>
            <span class="word"><a href="#"  data-spm-anchor-id="5176.750001.500.2" class="helpDetail-login" data-spm-click="helpDetail-login.jsp">矩阵系统登录</a></span>
            <span class="word"><a href="#"  data-spm-anchor-id="5176.750001.500.3" class="help-createDb" data-spm-click="help-createDb.jsp">创建数据库集群</a></span>
            <span class="word"><a href="#"  data-spm-anchor-id="5176.750001.500.4" class="help-createUser" data-spm-click="help-createUser.jsp">创建数据库用户</a></span>
            <span class="word"><a href="#"  data-spm-anchor-id="5176.750001.500.5" class="help-datastructure" data-spm-click="help-datastructure.jsp">数据库架构</a></span>                          
        </div>
    </div>
</div>
<div class="main-navbar-wrap" data-spm="501">
    <div class="main-navbar y-row">
        <h3 class="nav-left "><label>全部分类</label></h3>
        <ul class="nav-content">
            <li class="current fl"><a class="GeneralQues" href="#">常见问题</a></li>
            <li class="fl"><a class="serviceCenter" href="">联系客服</a></li>
        </ul>
    </div>
</div>
<div class="content y-row">
    <div class="content-left vnavbar-show" data-spm="502">
    <!--hnavbar-->
        <!-- <div class="hnavbar hidden">
            <ul data-level="0" class="nav-level0">
                <li>
                    <h3 class="title">云产品</h3>
                    <ul data-level="1" class="nav-level1 dashed">
                        <li data-haschild="true" data-id="8314827">
                            <span class="cate-txt" data-spm-click="gostr=/aliyun;locaid=d8314827">云服务器ECS</span>
                            <span class="arrow-right"></span>
                        </li>
                        <li data-haschild="true" data-id="8314871">
                            <span class="cate-txt" data-spm-click="gostr=/aliyun;locaid=d8314871">负载均衡SLB</span>
                            <span class="arrow-right"></span>
                        </li>
                        <li data-haschild="true" data-id="8314883">
                            <span class="cate-txt" data-spm-click="gostr=/aliyun;locaid=d8314883">云数据库RDS</span>
                            <span class="arrow-right"></span>
                        </li>
                        <li data-haschild="true" data-id="8314910">
                            <span class="cate-txt" data-spm-click="gostr=/aliyun;locaid=d8314910">开放存储服务OSS</span>
                            <span class="arrow-right"></span>
                        </li>
                        <li data-haschild="true" data-id="8314936">
                            <span class="cate-txt" data-spm-click="gostr=/aliyun;locaid=d8314936">内容分发网络CDN</span>
                            <span class="arrow-right"></span>
                        </li>
                        <li data-haschild="true" data-id="8314941">
                            <span class="cate-txt" data-spm-click="gostr=/aliyun;locaid=d8314941">云盾</span>
                            <span class="arrow-right"></span>
                        </li>
                        <li data-haschild="true" data-id="8314972">
                            <span class="cate-txt" data-spm-click="gostr=/aliyun;locaid=d8314972">云监控</span>
                            <span class="arrow-right"></span>
                        </li>
                        <li data-haschild="true" data-id="8315107">
                            <span class="cate-txt" data-spm-click="gostr=/aliyun;locaid=d8315107">更多云产品</span>
                            <span class="arrow-right"></span>
                        </li>
                    </ul>
                </li>
            </ul>
            <ul data-level="2" class="nav-level2 hidden"></ul>
            <ul data-level="3" class="nav-level3 hidden"></ul>
        </div> -->
        <div class="vnavbar" style="display: block;">
            <h3 class="title">用户中心</h3>
            <ul class="main-level">
                <li data-haschild="false" data-id="1-1" class="helpDetail-login" data-spm-click="helpDetail-login.jsp"><a>矩阵系统登录</a></li>
                <li data-haschild="false" data-id="1-2"  class="helpDetail-reg" data-spm-click="helpDetail-reg.jsp" ><a href="#">矩阵系统注册</a></li>
                <li data-haschild="false" data-id="1-3" class="helpDetail-findpas" data-spm-click="helpDetail-findpas.jsp" ><a href="#">找回密码</a></li>
            </ul>
        </div>
        <div class="vnavbar RDS" style="display:block;">
            <h3 class="title">云数据库RDS</h3>
            <ul class="main-level">
                <li data-haschild="true" data-id="2-1">
                    <h3 data-spm-click="gostr=/aliyun;locaid=d8314861" data-spm-anchor-id="">运维技术分享<span class="arrow-up"></span></h3>
                    <ul class="sub-level">
                        <li data-haschild="false" data-id="2-1-1" class="help-createDb" data-spm-click="help-createDb.jsp">
                            <a href="#">创建数据库实例</a></li>
                        <li data-haschild="false" data-id="2-1-2" class="help-createUser" data-spm-click="help-createUser.jsp" >
                            <a href="#">创建数据库用户</a></li>
                        <li data-haschild="false" data-id="2-1-3" class="help-connect" data-spm-click="help-connect.jsp">
                            <a href="#">个人办公电脑怎么连接数据库</a></li>
                        <li data-haschild="false" data-id="2-1-4" class="help-datastructure" data-spm-click="help-datastructure.jsp" >
                            <a href="#">mcluster数据库架构</a></li>
                        <li data-haschild="false" data-id="2-1-5">
                            <a class="help-gbalancerDB" data-spm-click="help-gbalancerDB.jsp" href="#">gbalancer连接数据库</a></li>
                        <li data-haschild="false" data-id="2-1-6"  class="help-userLink" data-spm-click="help-userLink.jsp">
                            <a href="#">用户连接不上数据库</a></li>
                    </ul>
                </li>
            </ul>
        </div>
        <div class="vnavbar OSS" style="display:block;">
            <h3 class="title">开放存储OSS</h3>
            <ul class="main-level">
                <li data-haschild="true" data-id="3-1">
                    <h3 data-spm-click="gostr=/aliyun;locaid=d8314861" data-spm-anchor-id="">运维技术分享<span class="arrow-up"></span></h3>
                    <ul class="sub-level">
                        <li data-haschild="false" data-id="3-1-1" class="help-OSSuse" data-spm-click="help-OSSuse.jsp">
                            <a href="#">OSS使用方法</a></li>
                        <li data-haschild="false" data-id="3-1-2" class="help-Osstool" data-spm-click="help-Osstool.jsp" >
                            <a href="#">OSS客户端工具</a></li>
                    </ul>
                </li>
            </ul>
        </div>
        <div class="vnavbar OCS" style="display:block;">
            <h3 class="title">开放缓存OCS</h3>
            <ul class="main-level">
                <li data-haschild="false" data-id="4-1" class="help-OCSuse" data-spm-click="help-OCSuse.jsp"><a>OCS服务使用</a></li>
            </ul>
        </div>
        <!-- <input type="hidden" id="parentCategoryId" value="">
        <input type="hidden" id="currentCategoryId" value=""> -->
    </div>
    <!-- content-right-login -->
    <div class="content-right" data-spm="2">
                    <!-- 已登录状态 -->
        <div class="logined" data-spm="3">
            <div class="logined-left">
                <div class="portrait">
                    <img src="${ctx}/static/img/help/header.png">         
                </div>
                <div class="userinfo">
                    <!-- <h3 class="username">${sessionScope.userSession.userName}</h3> -->
                    <!-- <span class="user-account">账号：${sessionScope.userSession.email}</span> -->
                    <h3 class="username">hi,亲爱的用户</h3>
                    <span class="user-account">欢迎你来到帮助中心，若帮助中心找不到您遇到的问题，请与我们联系。</span>
                </div>
                <div style="clear:both;"></div>
            </div>
            <div class="logined-right"> 
                <h3>您可能会遇到的问题</h3>
                <span><a href="#" class="helpDetail-reg" data-spm-click="helpDetail-reg.jsp">矩阵系统注册</a></span>
                <span><a href="#" class="helpDetail-findpas" data-spm-click="helpDetail-findpas.jsp">忘记登录密码怎么办</a></span>
                <span><a href="#" class="help-connect" data-spm-click="help-connect.jsp">个人办公电脑怎么连接数据库？</a></span>
                <span><a href="#" class="help-userLink" data-spm-click="help-userLink.jsp">用户连接不上数据库？</a></span>
                <span><a href="#" class="help-OSStool" data-spm-click="help-OSStool.jsp">OSS客户端工具</a></span>
                <span><a href="#" class="help-OCSuse" data-spm-click="help-OCSuse.jsp">OCS服务使用</a></span>
            </div>
        </div>      
        <!-- start of 热点问题TMS区块-->
        <div class="section hot-problem">
            <div class="section-header">
                <h3 class="section-title">热点问题</h3> 
            </div>
            <div class="section-content">
                <div class="sub-section first">
                    <h4 class="sub-section-title">
                        云产品及服务              
                    </h4>
                    <div class="sub-section-content" style="margin-right:2%;*margin-right:1%;">
                        <a href="#" title="用户连接不上数据库" class="help-userLink" data-spm-click="help-userLink.jsp">用户连接不上数据库</a>
                        <a href="#" title="gbalancer链接数据库" class="help-gbalancerDB" data-spm-click="help-gbalancerDB.jsp">gbalancer连接数据库</a>
                        <a href="#" title="mcluster数据库架构" class="help-datastructure" data-spm-click="help-datastructure.jsp">mcluster数据库架构</a>
                        <a href="#" title="个人办公电脑怎么连接数据库" class="help-connect" data-spm-click="help-connect.jsp">个人办公电脑怎么连接数据库</a>
                        <a href="#" title="创建数据库用户" class="help-userLink" data-spm-click="help-userLink.jsp">创建数据库用户</a>
                    </div>
                    <div class="sub-section-content">
                        <a href="#" class="help-OSStool" data-spm-click="help-OSStool.jsp">OSS客户端工具</a>
                        <a href="#" class="help-OCSuse" data-spm-click="help-OCSuse.jsp">OCS服务使用</a>
                        <a href="#" class="help-OSSuse" data-spm-click="help-OSSuse.jsp">OSS使用方法</a>
                        <!-- <a href="#" title="云盾端口安全检测端口列表" >云盾端口安全检测端口列表</a>
                        <a href="#" title="乐视云CDN带宽处理能力" >阿里云CDN带宽处理能力</a>      -->  
                    </div>                            
                </div>
                <div class="sub-section">
                    <h4 class="sub-section-title">
                        会员帐号            
                    </h4>
                    <div class="sub-section-content">
                        <a href="#" class="helpDetail-findpas" data-spm-click="helpDetail-findpas.jsp">忘记密码怎么办？</a>
                        <a href="#" class="helpDetail-login" data-spm-click="helpDetail-login.jsp">矩阵系统登录</a>
                        <a href="#" class="helpDetail-reg" data-spm-click="helpDetail-reg.jsp" >矩阵系统注册</a>
                        <a href="#" title="忘记会员登录名怎么办？" class="text-unable">忘记会员登录名怎么办？</a>
                        <a href="#" title="企业帐号操作说明" class="text-unable">企业帐号操作说明</a>
                    </div>
                </div>
            </div>
        </div>
<!-- end of 热点问题TMS区块-->
        <!-- start of 新手向导TMS区块-->
        <div class="section wizard">
            <div class="section-header">
                <h3 class="section-title">新手向导</h3> 
            </div>
            <div class="section-content">
            <div class="y-tab-box guide-box">
              <div class="y-box">
                <div class="y-hd">
                  <div class="y-tab y-tab-s y-tab-4">
                    <ul>
                        <li class="y-first y-current" data-id="guide1"><span class="y-item">产品购买</span></li>
                        <li data-id="guide2"><span class="y-item">RDS配置</span></li>
                        <li data-id="guide3"><span class="y-item">OSS配置</span></li>
                        <li data-id="guide4"><span class="y-item">备案</span></li>
                    </ul>
                  </div>
                </div>
                <div class="y-bd guide-bd" data-id="guide1">
                    <ul>
                        <li>
                            <img src="${ctx}/static/img/help/help-user.png" width="100px" height="100px">
                            <h3 class="title">购买选型</h3>
                            <ul class="step-link-list">
                                <li><a href="#" class="text-unable">云服务器价格总览</a></li>
                                <li><a href="#" class="text-unable">如何选择适合我的云服务器？</a></li>
                            </ul>
                            <div class="dotted"></div>
                        </li>
                        <li>
                            <img src="${ctx}/static/img/help/help-server.png" width="100px" height="100px">
                            <h3 class="title">登录云服务器</h3>
                            <ul class="step-link-list">
                                <li><a href="#" class="text-unable">远程连接服务器For Windows 2003 &amp; 2008</a></li>
                                <li><a href="#" class="text-unable">远程连接服务器for Linux</a></li>
                            </ul>
                            <div class="dotted"></div>
                        </li>
                        <li>
                            <img src="${ctx}/static/img/help/help-computer.png" width="100px" height="100px">
                            <h3 class="title">分区格式化</h3>
                            <ul class="step-link-list">
                                <li><a href="#" class="text-unable">硬盘分区和格式化For Windows 2008</a></li>
                                <li><a href="#" class="text-unable">Linux 系统挂载数据盘</a></li>
                            </ul>
                            <div class="dotted"></div>
                        </li>
                        <li>
                            <img src="${ctx}/static/img/help/help-cloud.png" width="100px" height="100px">
                            <h3 class="title">配置环境</h3>
                            <ul class="step-link-list">
                                <li><a href="#" class="text-unable">windows2008一键安装web环境全攻略</a></li>
                                <li><a href="#" class="text-unable">Linux一键安装web环境全攻略</a></li>             
                            </ul>
                            <div class="dotted"></div>
                        </li>
                        <li>
                            <img src="${ctx}/static/img/help/help-soft.png" width="100px" height="100px">
                            <h3 class="title">安装软件</h3>              
                            <ul class="step-link-list">
                                <li><a href="#" class="text-unable">Windows2008 FTP配置指南</a></li>
                                <li><a href="#" class="text-unable">安装和使用FTP/ Windows2003</a></li>   
                            </ul>
                        </li>
                    </ul>
                </div>
                <div class="y-bd guide-bd hidden" data-id="guide2">
                    <ul>
                        <li>
                            <img src="${ctx}/static/img/help/help-buy.png" width="100px" height="100px">
                            <h3 class="title">购买选型</h3>
                            <ul class="step-link-list">
                                <li><a href="#" class="text-unable">云数据库RDS与自建数据库对比</a></li>
                                <li><a href="#" class="text-unable">如何选择适合我的云服务器？</a></li>
                            </ul>
                            <div class="dotted"></div>
                        </li>
                        <li>
                            <img src="${ctx}/static/img/help/help-create.png" width="100px" height="100px">
                            <h3 class="title">创建</h3>
                            <ul class="step-link-list">
                                <li><a href="#" class="text-unable">创建账号</a></li>
                                <li><a href="#" class="text-unable">创建数据库</a></li>
                            </ul>
                            <div class="dotted"></div>
                        </li>
                        <li>
                            <img src="${ctx}/static/img/help/help-leadin.png" width="100px" height="100px">
                            <h3 class="title">数据导入</h3>
                            <ul class="step-link-list">
                                <li><a href="#" class="text-unable">SQL Server 2008 客户端导入</a></li>
                                <li><a href="#" class="text-unable">采用MySQLdump工具迁移</a></li>
                            </ul>
                            <div class="dotted"></div>
                        </li>
                        <li>
                            <img src="${ctx}/static/img/help/help-login.png" width="100px" height="100px">
                            <h3 class="title">登录链接</h3>
                            <ul class="step-link-list">
                                <li><a href="#" class="text-unable">如何连接RDS数据库？</a></li>
                                <li><a href="#" class="text-unable">登录数据库（iDB Cloud） 操作宝典</a></li>             
                            </ul>
                            <div class="dotted"></div>
                        </li>
                        <li>
                            <img src="${ctx}/static/img/help/help-improve.png" width="100px" height="100px">
                            <h3 class="title">优化顾问</h3>              
                            <ul class="step-link-list">
                                <li><a href="#" class="text-unable">如何提升RDS响应速度</a></li>
                                <!--<li><a href="#" >安装和使用FTP/ Windows2003</a></li>  -->  
                            </ul>
                        </li>
                    </ul>
                </div>
                <div class="y-bd guide-bd hidden" data-id="guide4">
                    <ul>
                        <li>
                            <img src="${ctx}/static/img/help/help-hand.png" width="100px" height="100px">
                            <h3 class="title">备案准备</h3>
                            <ul class="step-link-list">
                                <li><a href="#" class="text-unable">有备案号的还要不要做备案</a></li>
                            </ul>
                            <div class="dotted"></div>
                        </li>
                        <li>
                            <img src="${ctx}/static/img/help/help-folder.png" width="100px" height="100px">
                            <h3 class="title">提交信息</h3>
                            <ul class="step-link-list">
                                <li><a href="#" class="text-unable">提交备案信息，完成初审</a></li>
                                <!-- <li><a href="#" >创建数据库</a></li> -->
                            </ul>
                            <div class="dotted"></div>
                        </li>
                        <li>
                            <img src="${ctx}/static/img/help/help-phone.png" width="100px" height="100px">
                            <h3 class="title">拍照说明</h3>
                            <ul class="step-link-list">
                                <li><a href="#" class="text-unable">办理拍照流程说明</a></li>
                            </ul>
                            <div class="dotted"></div>
                        </li>
                        <li>
                            <img src="${ctx}/static/img/help/help-operate.png" width="100px" height="100px">
                            <h3 class="title">提交管局</h3>
                            <ul class="step-link-list">
                                <li><a href="#" class="text-unable">提交管局</a></li>
                                <!-- <li><a href="#" >关于Bucket的操作</a></li>              -->
                            </ul>
                            <div class="dotted"></div>
                        </li>
                        <li>
                            <img src="${ctx}/static/img/help/help-bucket.png" width="100px" height="100px">
                            <h3 class="title">管局下号</h3>              
                            <ul class="step-link-list">
                                <li><a href="#" class="text-unable">管局下号</a></li>
                                <!--<li><a href="#" >安装和使用FTP/ Windows2003</a></li>  -->  
                            </ul>
                        </li>
                    </ul>
                </div>
                <div class="y-bd guide-bd hidden" data-id="guide3">
                    <ul>
                        <li>
                            <img src="${ctx}/static/img/help/help-message.png" width="100px" height="100px">
                            <h3 class="title">OSS优势</h3>
                            <ul class="step-link-list">
                                <li><a href="#" class="text-unable">OSS与自建服务器存储对比</a></li>
                                <li><a href="#" class="text-unable">OSS价格总览</a></li>
                            </ul>
                            <div class="dotted"></div>
                        </li>
                        <li>
                            <img src="${ctx}/static/img/help/help-submessage.png" width="100px" height="100px">
                            <h3 class="title">使用帮助</h3>
                            <ul class="step-link-list">
                                <li><a href="#" class="text-unable">OSS控制台使用帮助</a></li>
                                <!-- <li><a href="#" >创建数据库</a></li> -->
                            </ul>
                            <div class="dotted"></div>
                        </li>
                        <li>
                            <img src="${ctx}/static/img/help/help-photo.png" width="100px" height="100px">
                            <h3 class="title">域名绑定</h3>
                            <ul class="step-link-list">
                                <li><a href="#" class="text-unable">OSS域名绑定教程</a></li>
                            </ul>
                            <div class="dotted"></div>
                        </li>
                        <li>
                            <img src="${ctx}/static/img/help/help-submit.png" width="100px" height="100px">
                            <h3 class="title">Object操作</h3>
                            <ul class="step-link-list">
                                <li><a href="#" class="text-unable">关于Object操作</a></li>
                                <li><a href="#" class="text-unable">关于Bucket的操作</a></li>             
                            </ul>
                            <div class="dotted"></div>
                        </li>
                        <li>
                            <img src="${ctx}/static/img/help/help-check.png" width="100px" height="100px">
                            <h3 class="title">Bucket 操作</h3>              
                            <ul class="step-link-list">
                                <li><a href="#" class="text-unable">API文档及部分开发包下载</a></li>
                                <!--<li><a href="#" >安装和使用FTP/ Windows2003</a></li>  -->  
                            </ul>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>       
</div>
<script>
// 全局变量

// 下拉菜单toggle
var _target=$('.main-level').children();
_target.click(function(event) {
    /* Act on the event */
    var _this=$(this);
    var _thisAttr=_this.attr('data-haschild');
    if(_thisAttr){//有孩子
        var _sublevel=_this.find('.sub-level');
        if(_sublevel.hasClass('hidden')){//下拉展示、箭头向上
            _sublevel.removeClass('hidden');
            _sublevel.prev().children('span').removeClass('arrow-down').addClass('arrow-up');
        }else{//下拉隐藏、箭头向下
            _sublevel.addClass('hidden');
            _sublevel.prev().children('span').removeClass('arrow-up').addClass('arrow-down');
        }
    }
});
//新手向导tab切换逻辑
$('.wizard .y-tab li').on('click', function(){
    var ele =$(this), id = ele.data('id');
    $('.wizard .y-tab li').removeClass('y-current');
    ele.addClass('y-current');
    $('.wizard .guide-bd').hide();
    $('.wizard .guide-bd[data-id='+id+']').show();
});
$('.nav-content').find('a').click(function(event) {
    event.preventDefault();
    $(this).parent().siblings().removeClass('current');
    $(this).parent().addClass('current');
    $('.content-right').html('');
    if($(this).hasClass('serviceCenter')){
        $('.content-right').load('connectService.jsp');  
    }else{
        $('.content-right').load('help.jsp');  
    }
});
htmlLoad('helpDetail-login');
htmlLoad('helpDetail-reg');
htmlLoad('helpDetail-findpas');
htmlLoad('help-createDb');
htmlLoad('help-createUser');
htmlLoad('help-connect');
htmlLoad('help-datastructure');
htmlLoad('help-gbalancerDB');
htmlLoad('help-userLink');
htmlLoad('help-OSSuse');
htmlLoad('help-OSStool');
htmlLoad('help-OCSuse');

function htmlLoad(container){
    $('.'+container).click(function(event) {
        event.preventDefault();
        $('.GeneralQues').parent().removeClass('current');
        $('.serviceCenter').parent().removeClass('current');
        $('.vnavbar').find('.current').removeClass('current');
        //$(this).addClass('current');
        // $(this).addClass('current')
        //         .parent().siblings().find('a').removeClass('current');
        // var _sublevel=$(this).closest('.sub-level');
        // if(_sublevel.length>0){//存在二级目录
        //     console.log(_sublevel.attr('class'))
        //     _sublevel.removeClass('hidden');
        //     _sublevel.prev().children('span').removeClass('arrow-down').addClass('arrow-up');
        // }
        var source=$(this).attr('data-spm-click');
        $('.content-right').html('');
        $('.content-right').load(source);
        
    });
}
</script>