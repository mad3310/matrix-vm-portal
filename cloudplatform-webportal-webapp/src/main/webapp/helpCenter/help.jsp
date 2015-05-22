<%@ page language="java" pageEncoding="UTF-8"%>
    <!-- content-right-login -->
    <!-- <div class="content-right" data-spm="2"> -->
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
                        <a href="#" title="gbalancer链接数据库" class="help-gbalancerDB" data-spm-click="help-gbalancerDB.jsp">gbalancer链接数据库</a>
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
<!-- </div> -->
<script>
$('.vnavbar').find('a').removeClass('current');
//新手向导tab切换逻辑
$('.wizard .y-tab li').on('click', function(){
    var ele =$(this), id = ele.data('id');
    $('.wizard .y-tab li').removeClass('y-current');
    ele.addClass('y-current');
    $('.wizard .guide-bd').hide();
    $('.wizard .guide-bd[data-id='+id+']').show();
});
var containers = ["helpDetail-login",
								"helpDetail-reg",
								"helpDetail-findpas",
								"help-createDb",
								"help-createUser",
								"help-connect",
								"help-datastructure",
								"help-gbalancerDB",
								"help-userLink",
								"help-OSSuse",
								"help-OSStool",
								"help-OCSuse"];
(function htmlLoad(containers){
$('.'+containers.join(",.")).unbind("click").click(function(event) {
	event.preventDefault();
	$('.GeneralQues').parent().removeClass('current');
	$('.serviceCenter').parent().removeClass('current');
	$('.vnavbar').find('.current').removeClass('current');
	var source=$(this).attr('data-spm-click');
	$('.content-right').load(source);
});
})(containers);
</script>