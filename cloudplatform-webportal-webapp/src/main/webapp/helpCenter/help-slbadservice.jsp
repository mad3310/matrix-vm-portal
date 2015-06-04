<%@ page language="java" pageEncoding="UTF-8"%>
<!-- <div class="content-right" data-spm="2"> -->
    <div class="bread-crumb">
        <span class="item"><a href="#" data-spm-anchor-id="5176.788314868.2.1">负载均衡SLB</a></span>&nbsp;&gt;&nbsp;<span class="data-spm-click" data-id="6-3-4">如何添加SLB后端服务</span>
    </div>
    <h2 style="text-align: center;color: #333;font-size: 18px;">如何添加SLB后端服务</h2>
    <div class="article-content">
        <p><span>点击<span style="line-height:1.5;font-family:微软雅黑,Microsoft YaHei;font-size:10.5pt;">【后端服务配置】</span>进入其页面，每个SLB实例只能创建添加一个后端服务，SLB未添加后端服务时，其界面展示如下：</p>
        <p><img src="${ctx}/static/img/help/slb-add1.jpg"></p>
        <p><span>可通过两种方式添加后端服务<span style="line-height:1.5;font-family:微软雅黑,Microsoft YaHei;font-size:10.5pt;">【添加自定义服务器】【添加GCE服务器】</span></p>
        <br>
        <p><span style="line-height:1.5;font-family:微软雅黑,Microsoft YaHei;font-size:10.5pt;">【添加GCE服务器】:</span><span>添加GCE服务器，首先要开通GCE服务，添加相应服务后，返回SLB服务，点击&nbsp;【添加GCE服务器】按钮，进入GCE服务器列表，可添加相应服务。如果您对于SLB和GCE如何配合服务不清楚，可参考&nbsp;<a href="/helpCenter/helpCenter.jsp?container=product-SLBArch">SLB架构设计</a></span></p>
        <p><span>举例说明添加GCE服务器的具体步骤,如下图所示：</span>
            <ul>
                <li>点击‘添加GCE服务器按钮’（若您已添加一个后端服务，那么添加服务按钮会消失）</li>
                <li>进入‘GCE服务列表’，选择‘test900’，点击‘添加’</li>
                <li>添加后台服务器‘test900’成功，返回‘已添加的服务器列表’</li>
                <li>相应添加按钮和添加服务模块消失</li>
                <li>若想重新添加，那么在‘已添加的服务器模块’，点击‘移除’，然后按照第一步开始重新操作即可</li>
            </ul>
        </p>
        <p><img src="${ctx}/static/img/help/slb-add2.jpg"></p>
        <br>
        <p><span style="line-height:1.5;font-family:微软雅黑,Microsoft YaHei;font-size:10.5pt;">【添加自定义服务器】:</span><span>添加自定义服务器，点击&nbsp;【添加自定义服务器】按钮，弹窗给出自定义需填写的表单</span></p>
        <p><span>举例说明添加GCE服务器的具体步骤,如下图所示：</span>
            <ul>
                <li>点击‘添加自定义服务器’</li>
                <li>进入‘添加服务弹窗’，填写‘服务名称’、‘服务器ip’、‘后端端口’、‘SLB协议端口’，点击确定</li>
                <li><img src="${ctx}/static/img/help/slb-add3.jpg"></li>
                <li>‘已添加的服务器’模块，列表显示添加成功的自定义服务器，可继续点击‘添加自定义服务器’，为服务添加节点</li>
                <li>若想重新添加，那么在‘已添加的服务器模块’，点击‘移除’，然后按照第一步开始重新操作即可</li>
                <li><img src="${ctx}/static/img/help/slb-add4.jpg"></li>
            </ul>
        </p>
        <p></p>
    </div>
<!-- </div> -->
<script>
    var target=$('.data-spm-click').attr('data-id');
    $('.vnavbar').find('a').removeClass('current');
    $('.vnavbar').find('li[data-id='+target+']').children('a').addClass('current');
    $('li[data-id='+target+']').closest('ul').removeClass('hidden');
    $('li[data-id='+target+']').closest('ul').prev().find('span').removeClass('arrow-down').addClass('arrow-up');
</script>