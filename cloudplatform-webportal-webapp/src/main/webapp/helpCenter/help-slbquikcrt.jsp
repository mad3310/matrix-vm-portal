<%@ page language="java" pageEncoding="UTF-8"%>
<!-- <div class="content-right" data-spm="2"> -->
    <div class="bread-crumb">
        <span class="item"><a href="#" data-spm-anchor-id="5176.788314868.2.1">负载均衡SLB</a></span>&nbsp;&gt;&nbsp;<span class="data-spm-click" data-id="6-2">快速创建</span>
    </div>
    <h2 style="text-align: center;color: #333;font-size: 18px;">快速创建</h2>
    <div class="article-content">
        <p><span>1、在<span style="line-height:1.5;font-family:微软雅黑,Microsoft YaHei;font-size:10.5pt;">【未开通的产品与服务:】</span>中，选择SLB服务，点击购买按钮，进入SLB购买页面，如下图：</p>
        <p><img src="${ctx}/static/img/help/slb-quick1.jpg"></p>
        <p><span>2、购买负载均衡SLB，必填字段，<span style="line-height:1.5;font-family:微软雅黑,Microsoft YaHei;font-size:10.5pt;">【负载均衡名称】【可用区】【数量】</span>，【可用区】是可选的物理集群，如下图，红框为必填处：</p>
        <p><img src="${ctx}/static/img/help/slb-crt1.jpg"></p>
        <p><span>3、购买成功后，SLB实例列表会列表显示您已购买的SLB实例，如下图：</span></p>
        <p><img src="${ctx}/static/img/help/slb-list1.jpg"></p>
        <p>4、点选进入一个实例，可查看或配置以下几项：<span style="line-height:1.5;font-family:微软雅黑,Microsoft YaHei;font-size:10.5pt;">【实例详情】【服务监听配置】【后端服务配置】【系统资源监控】</span></span></p>
        <p><img src="${ctx}/static/img/help/slb-quick2.jpg"></p>
        <p><span style="line-height:1.5;font-family:微软雅黑,Microsoft YaHei;font-size:10.5pt;">【实例详情】</span>您可以查看相应实例的基本信息和付费信息
        </p>
        <br>
        <p><span style="line-height:1.5;font-family:微软雅黑,Microsoft YaHei;font-size:10.5pt;">【服务监听配置】</span><span>修改服务的监听协议，具体的流程请您参考<a href class="help-slblisten" data-spm-click="help-slblisten.jsp">SLB服务监听配置设置</a></span></p>
        <br>
        <p><span style="line-height:1.5;font-family:微软雅黑,Microsoft YaHei;font-size:10.5pt;">【后端服务配置】</span><span>配置后端服务，具体的流程请您参考<a href class="help-slbadservice" data-spm-click="help-slbadservice.jsp">如何添加SLB后端服务</a></span></p>
        <br>
        <p><span style="line-height:1.5;font-family:微软雅黑,Microsoft YaHei;font-size:10.5pt;">【系统资源监控】</span><span>查看【cpu使用率】【磁盘】【内存】【网络】</span></p>
    </div>
<!-- </div> -->
<script>
    var target=$('.data-spm-click').attr('data-id');
    $('.vnavbar').find('a').removeClass('current');
    $('.vnavbar').find('li[data-id='+target+']').children('a').addClass('current');
    $('li[data-id='+target+']').closest('ul').removeClass('hidden');
    $('li[data-id='+target+']').closest('ul').prev().find('span').removeClass('arrow-down').addClass('arrow-up');
    htmlLoad('help-slblisten');
    htmlLoad('help-slbadservice');
function htmlLoad(container){
    $('.'+container).unbind("click").click(function(event) {
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