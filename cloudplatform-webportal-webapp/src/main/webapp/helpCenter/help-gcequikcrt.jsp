<%@ page language="java" pageEncoding="UTF-8"%>
<!-- <div class="content-right" data-spm="2"> -->
    <div class="bread-crumb">
        <span class="item"><a href="#" data-spm-anchor-id="5176.788314868.2.1">云引擎GCE</a></span>&nbsp;&gt;&nbsp;<span class="data-spm-click" data-id="5-2">快速创建</span>
    </div>
    <h2 style="text-align: center;color: #333;font-size: 18px;">快速创建</h2>
    <div class="article-content">
    	<p><span>1、联系我们创建应用image,我们会将image添加到您的matrix账号下。联系方式：</p>
    	<p><span style="line-height:1.5;font-family:微软雅黑,Microsoft YaHei;font-size:10.5pt;"> &nbsp; &nbsp; &nbsp;姓名:姚阔</p>
    	<p><span style="line-height:1.5;font-family:微软雅黑,Microsoft YaHei;font-size:10.5pt;">&nbsp; &nbsp; &nbsp;RTX:yaokuo</p>
    	<p><span style="line-height:1.5;font-family:微软雅黑,Microsoft YaHei;font-size:10.5pt;">&nbsp; &nbsp; &nbsp;QQ:875733121</p>
    	<p><span style="line-height:1.5;font-family:微软雅黑,Microsoft YaHei;font-size:10.5pt;">&nbsp; &nbsp; &nbsp;邮箱:yaokuo@letv.com</p>
    	<p><span style="line-height:1.5;font-family:微软雅黑,Microsoft YaHei;font-size:10.5pt;">&nbsp; &nbsp; &nbsp;手机:13121927657</p>
        <p><span>2、在<span style="line-height:1.5;font-family:微软雅黑,Microsoft YaHei;font-size:10.5pt;">【未开通的产品与服务:】</span>中，选择GCE服务，点击购买按钮，进入GCE购买页面，如下图：</p>
        <p><img src="/static/img/help/gce-quick1.jpg"></p>
        <p><span>3、购买云引擎GCE，必填字段，<span style="line-height:1.5;font-family:微软雅黑,Microsoft YaHei;font-size:10.5pt;">【云应用名称】【可用区】【服务类型】【镜像】【数量】</span>如下图，红框为必填处：</p>
        <p><img src="/static/img/help/gce-crt1.jpg"></p>
        <p><span>4、购买成功后，GCE实例列表会列表显示您已购买的GCE实例，如下图：</span></p>
        <p><img src="${ctx}/static/img/help/gce-quick2.jpg"></p>
        <p>5、点选进入一个应用实例，可查看或配置以下几项：<span style="line-height:1.5;font-family:微软雅黑,Microsoft YaHei;font-size:10.5pt;">【应用详情】【版本管理】【服务日志】【系统资源监控】</span>要说明的一点，这里的实例，分应用实例和ngix代理实例，请注意区分，下图为应用实例：</span></p>
        <p><img src="${ctx}/static/img/help/gce-quick3.jpg"></p>
        <p><span style="line-height:1.5;font-family:微软雅黑,Microsoft YaHei;font-size:10.5pt;">【实例详情】</span>您可以查看应用实例或nginx实例的基本信息和付费信息
        </p>
        <br>
        <p><span style="line-height:1.5;font-family:微软雅黑,Microsoft YaHei;font-size:10.5pt;">【版本管理】</span><span>会提供给您专业的代码管理，敬请期待</span></p>
        <br>
        <p><span style="line-height:1.5;font-family:微软雅黑,Microsoft YaHei;font-size:10.5pt;">【服务日志】</span><span>利用kibana组件，全方位的提供专业的日志服务，敬请期待</span></p>
        <br>
        <p><span style="line-height:1.5;font-family:微软雅黑,Microsoft YaHei;font-size:10.5pt;">【系统资源监控】</span><span>查看【cpu使用率】【磁盘】【内存】【网络】</span></p>
        <br>
        <p><span>除此之外，对于多处访问地址的理解，请参考&nbsp;<a href class="help-gceAccess" data-spm-click="help-gceAccess.jsp">GCE的多个访问地址含义</a></span></p>
    </div>
<!-- </div> -->
<script>
    var target=$('.data-spm-click').attr('data-id');
    $('.vnavbar').find('a').removeClass('current');
    $('.vnavbar').find('li[data-id='+target+']').children('a').addClass('current');
    $('li[data-id='+target+']').closest('ul').removeClass('hidden');
    $('li[data-id='+target+']').closest('ul').prev().find('span').removeClass('arrow-down').addClass('arrow-up');
    htmlLoad('help-gceAccess');
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