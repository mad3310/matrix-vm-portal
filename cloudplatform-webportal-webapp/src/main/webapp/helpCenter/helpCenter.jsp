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
                    <h3 data-spm-click="gostr=/aliyun;locaid=d8314861" data-spm-anchor-id="">RDS产品介绍<span class="arrow-down"></span></h3>
                    <ul class="sub-level hidden">
                        <li data-haschild="false" data-id="2-1-1" class="product-RDSIntro" data-spm-click="product-RDSIntro.jsp">
                            <a href="#">RDS简介</a></li>
                        <li data-haschild="false" data-id="2-1-2" class="product-RDSArch" data-spm-click="product-RDSArch.jsp">
                            <a href="#">RDS基础架构</a></li>
                        <li data-haschild="false" data-id="2-1-3" class="product-RDSAdvantage" data-spm-click="product-RDSAdvantage.jsp">
                            <a href="#">RDS优势</a></li>
                    </ul>
                </li>
                <li data-haschild="false" data-id="2-2" class="help-rdsquikcrt" data-spm-click="help-rdsquikcrt.jsp"><a>快速创建</a></li>
                <li data-haschild="true" data-id="2-3">
                    <h3 data-spm-click="gostr=/aliyun;locaid=d8314861" data-spm-anchor-id="">常见问题分享<span class="arrow-down"></span></h3>
                    <ul class="sub-level hidden">
                        <li data-haschild="false" data-id="2-3-1" class="help-createDb" data-spm-click="help-createDb.jsp">
                            <a href="#">创建数据库实例</a></li>
                        <li data-haschild="false" data-id="2-3-2" class="help-createUser" data-spm-click="help-createUser.jsp" >
                            <a href="#">创建数据库用户</a></li>
                        <li data-haschild="false" data-id="2-3-3" class="help-connect" data-spm-click="help-connect.jsp">
                            <a href="#">个人办公电脑怎么连接数据库</a></li>
                        <li data-haschild="false" data-id="2-3-4" class="help-datastructure" data-spm-click="help-datastructure.jsp" >
                            <a href="#">mcluster数据库架构</a></li>
                        <li data-haschild="false" data-id="2-3-5">
                            <a class="help-gbalancerDB" data-spm-click="help-gbalancerDB.jsp" href="#">gbalancer连接数据库</a></li>
                        <li data-haschild="false" data-id="2-3-6"  class="help-userLink" data-spm-click="help-userLink.jsp">
                            <a href="#">用户连接不上数据库</a></li>
                    </ul>
                </li>
            </ul>
        </div>
        <div class="vnavbar OSS" style="display:block;">
            <h3 class="title">开放存储OSS</h3>
            <ul class="main-level">
                <li data-haschild="true" data-id="3-1">
                    <h3 data-spm-click="gostr=/aliyun;locaid=d8314861" data-spm-anchor-id="">OSS产品介绍<span class="arrow-down"></span></h3>
                    <ul class="sub-level hidden">
                        <li data-haschild="false" data-id="3-1-1" class="product-OSSIntro" data-spm-click="product-OSSIntro.jsp">
                            <a href="#">OSS简介</a></li>
                        <li data-haschild="false" data-id="3-1-2" class="product-OSSArch" data-spm-click="product-OSSArch.jsp">
                            <a href="#">OSS与自建存储对比</a></li>
                        <li data-haschild="false" data-id="3-1-3" class="product-OSSAdvantage" data-spm-click="product-OSSAdvantage.jsp">
                            <a href="#">OSS优势</a></li>
                    </ul>
                </li>
                <li data-haschild="false" data-id="3-2" class="help-ossquikcrt" data-spm-click="help-ossquikcrt.jsp"><a>快速创建</a></li>
                <li data-haschild="true" data-id="3-3">
                    <h3 data-spm-click="gostr=/aliyun;locaid=d8314861" data-spm-anchor-id="">常见问题分享<span class="arrow-down"></span></h3>
                    <ul class="sub-level hidden">
                        <li data-haschild="false" data-id="3-3-1" class="help-OSSuse" data-spm-click="help-OSSuse.jsp">
                            <a href="#">OSS使用方法</a></li>
                        <li data-haschild="false" data-id="3-3-2" class="help-OSStool" data-spm-click="help-OSStool.jsp" >
                            <a href="#">OSS客户端工具</a></li>
                    </ul>
                </li>
            </ul>
        </div>
        <div class="vnavbar OCS" style="display:block;">
            <h3 class="title">开放缓存OCS</h3>
            <ul class="main-level">
                <li data-haschild="true" data-id="4-1">
                    <h3 data-spm-click="gostr=/aliyun;locaid=d8314861" data-spm-anchor-id="">OCS产品介绍<span class="arrow-down"></span></h3>
                    <ul class="sub-level hidden">
                        <li data-haschild="false" data-id="4-1-1" class="product-OCSIntro" data-spm-click="product-OCSIntro.jsp">
                            <a href="#">OCS简介</a></li>
                        <!-- <li data-haschild="false" data-id="4-1-2" class="product-OCSArch" data-spm-click="product-OCSArch.jsp">
                            <a href="#">OCS与自建存储对比</a></li>
                        <li data-haschild="false" data-id="4-1-3" class="product-OCSAdvantage" data-spm-click="product-OCSAdvantage.jsp">
                            <a href="#">OCS优势</a></li> -->
                    </ul>
                </li>
                <li data-haschild="false" data-id="4-2" class="help-OCSuse" data-spm-click="help-OCSuse.jsp"><a>快速创建</a></li>
            </ul>
        </div>
        <div class="vnavbar GCE" style="display:block;">
            <h3 class="title">云引擎GCE</h3>
            <ul class="main-level">
            	<li data-haschild="true" data-id="5-1">
                    <h3 data-spm-click="gostr=/aliyun;locaid=d8314861" data-spm-anchor-id="">GCE产品介绍<span class="arrow-down"></span></h3>
                    <ul class="sub-level hidden">
                        <li data-haschild="false" data-id="5-1-1" class="product-GCEIntro" data-spm-click="product-GCEIntro.jsp">
                            <a href="#">GCE简介</a></li>
                        <li data-haschild="false" data-id="5-1-2" class="product-GCEArch" data-spm-click="product-GCEArch.jsp" >
                            <a href="#">GCE基础架构</a></li>
                         <li data-haschild="false" data-id="5-1-3" class="product-GCEAdvantage" data-spm-click="product-GCEAdvantage.jsp" >
                            <a href="#">GCE优势</a></li>
                         <li data-haschild="false" data-id="5-1-4" class="product-GCExten" data-spm-click="product-GCExten.jsp" >
                            <a href="#">GCE扩展服务</a></li>
                    </ul>
                </li>
                <li data-haschild="false" data-id="5-2" class="help-gcequikcrt" data-spm-click="help-gcequikcrt.jsp"><a>快速创建</a></li>
                <li data-haschild="true" data-id="5-3">
                    <h3 data-spm-click="gostr=/aliyun;locaid=d8314861" data-spm-anchor-id="">常见问题分享<span class="arrow-down"></span></h3>
                    <ul class="sub-level hidden">
                        <li data-haschild="false" data-id="5-3-1" class="help-createGce" data-spm-click="help-createGce.jsp">
                            <a href="#">购买帮助</a></li>
                        <li data-haschild="false" data-id="5-3-2" class="help-gceAccess" data-spm-click="help-gceAccess.jsp" >
                            <a href="#">GCE的多个访问地址含义</a></li>
                    </ul>
                </li>
            </ul>
        </div>
        <div class="vnavbar SLB" style="display:block;">
            <h3 class="title">负载均衡 SLB</h3>
            <ul class="main-level">
                <li data-haschild="true" data-id="6-1" class="" data-spm-click="">
                    <h3 data-spm-anchor-id="">SLB产品介绍<span class="arrow-down"></span></h3>
                    <ul class="sub-level hidden">
                        <li data-haschild="false" data-id="6-1-1" class="product-SLBIntro" data-spm-click="product-SLBIntro.jsp">
                            <a href="#">SLB简介</a></li>
                        <li data-haschild="false" data-id="6-1-2" class="product-SLBArch" data-spm-click="product-SLBArch.jsp" >
                            <a href="#">SLB基础架构</a></li>
                        <li data-haschild="false" data-id="6-1-3" class="product-SLBAdvantage" data-spm-click="product-SLBAdvantage.jsp" >
                            <a href="#">SLB优势</a></li>
                    </ul>
                </li>
                <li data-haschild="false" data-id="6-2" class="help-slbquikcrt" data-spm-click="help-slbquikcrt.jsp"><a>快速创建</a></li>
                <li data-haschild="true" data-id="6-3">
                    <h3 data-spm-click="gostr=/aliyun;locaid=d8314861" data-spm-anchor-id="">常见问题分享<span class="arrow-down"></span></h3>
                    <ul class="sub-level hidden">
                        <li data-haschild="false" data-id="6-3-1" class="help-createSlb" data-spm-click="help-createSlb.jsp">
                            <a href="#">购买帮助</a></li>
                        <li data-haschild="false" data-id="6-3-2" class="help-slblist" data-spm-click="help-slblist.jsp" >
                            <a href="#">SLB实例列表字段说明</a></li>
                        <li data-haschild="false" data-id="6-3-3" class="help-slblisten" data-spm-click="help-slblisten.jsp" >
                            <a href="#">SLB服务监听配置设置</a></li>
                        <li data-haschild="false" data-id="6-3-4" class="help-slbadservice" data-spm-click="help-slbadservice.jsp" >
                            <a href="#">如何添加SLB后端服务</a></li>
                    </ul>
                </li>
            </ul>
        </div>
        <!-- <input type="hidden" id="parentCategoryId" value="">
        <input type="hidden" id="currentCategoryId" value=""> -->
    </div>
    <!-- content-right-login -->
    <div class="content-right" data-spm="2">
                    <!-- 已登录状态 -->
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
var containers = ["helpDetail-login","helpDetail-reg","helpDetail-findpas",
								"help-createDb","help-createUser","help-connect",
								"help-datastructure","help-gbalancerDB","help-userLink",
								"help-OSSuse","help-OSStool","help-OCSuse",
								'help-createGce','help-gceAccess','help-createSlb',
								'help-slblist','help-slblisten','help-slbadservice',
								'help-gcequikcrt','help-slbquikcrt',"product-GCEAdvantage",
								"product-GCEArch","product-GCEIntro","product-GCExten",
								"product-SLBAdvantage","product-SLBArch","product-SLBIntro",
								"product-SLBExten",'product-SLBLimit','product-RDSIntro','product-RDSArch','product-RDSAdvantage','help-rdsquikcrt',
                                'help-ossquikcrt','product-OSSIntro','product-OSSAdvantage','product-OSSArch','product-OCSIntro','product-OCSArch',
                                'product-OCSAdvantage'];
(function htmlLoad(containers){
	$('.'+containers.join(",.")).click(function(event) {
        event.preventDefault();
        $('.GeneralQues').parent().removeClass('current');
        $('.serviceCenter').parent().removeClass('current');
        $('.vnavbar').find('.current').removeClass('current');
        var source=$(this).attr('data-spm-click');
        $('.content-right').load(source);
    });
})(containers);
//获取链接中的参数
function GetQueryString(name) { 
	var reg = new RegExp("(^|&)" +name+ "=([^&]*)(&|$)","i"); 
	var r = window.location.search.substr(1).match(reg); 
	if (r!=null) return (r[2]); return null; 
} 
//判断加载哪个内部页面	 
var container = GetQueryString("container"); 
if(container == ""){
	$(".GeneralQues").click();
}else if($.inArray(container,containers) != -1){
	$("."+container).click();
}else{
	$(".GeneralQues").click();
}
</script>
