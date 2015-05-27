<%@ page language="java" pageEncoding="UTF-8"%>
<style>
.dp-highlighter {font-family: Monaco, DejaVu Sans Mono, Bitstream Vera Sans Mono, Consolas, Courier New, monospace;font-size: 12px;
background-color: transparent;width: 97%;overflow: auto;margin-left: 9px;padding: 1px;word-break: break-all;word-wrap: break-word;}
.dp-highlighter .tools {padding: 3px;text-align: left;margin: 0;color: black;font-weight: bold;}

.dp-highlighter ol {border: 1px solid #D1D7DC;list-style: decimal;background-color: #fff;margin: 0px 0px 1px 0px;padding: 2px 0;color: #2B91AF;font-size: 1.0em;line-height: 1.4em;}
.dp-highlighter ol li, .dp-highlighter .columns div {border-left: 1px solid #D1D7DC;background-color: #FAFAFA;padding-left: 10px;line-height: 18px;margin: 0 0 0 38px;}
.dp-highlighter ol li span {color: Black;}
.dp-xml .tag, .dp-xml .tag-name {color: #069;font-weight: bold;}
.dp-xml .tag, .dp-xml .tag-name {color: #069;font-weight: bold;}
</style>
<!-- <div class="content-right" data-spm="2"> -->
    <div class="bread-crumb">
        <span class="item"><a href="#" data-spm-anchor-id="5176.788314868.2.1">开放存储OSS</a></span>&nbsp;&gt;&nbsp;<span class="data-spm-click" data-id="3-1-2">OSS客户端工具</span>
    </div>
    <h2 style="text-align: center;color: #333;font-size: 18px;">OSS客户端工具</h2>
    <div class="article-content">
        <p><span style="text-transform:none;text-indent:0px;display:inline !important;font:14px/21px 微软雅黑,Microsoft YaHei;white-space:normal;float:none;letter-spacing:normal;color:rgb(0,0,0);word-spacing:0px;">可使用cyberduck来使用服务：</p>
        <p><span style="text-transform:none;text-indent:0px;display:inline !important;font:14px/21px 微软雅黑,Microsoft YaHei;white-space:normal;float:none;letter-spacing:normal;color:rgb(0,0,0);word-spacing:0px;">1.从http://cyberduck.io下载cyberduck客户端工具。</p>
        <p><span style="text-transform:none;text-indent:0px;display:inline !important;font:14px/21px 微软雅黑,Microsoft YaHei;white-space:normal;float:none;letter-spacing:normal;color:rgb(0,0,0);word-spacing:0px;">2. 安装cyberduck后，新建配置文件：Openstack Swift (le) .cyberduckprofile（记事本即可）内容为：</p>
        <div class="dp-highlighter" id="">
            <div class="bar">
                <div class="tools">
                    代码<div class="zero-clipboard">
                            <a id= "zclipCopy" class="btn-clipboard">复制</a>
                            <div id="zeroclipboardTooltip" class="hidden" style="position: absolute; right:0; top:-15px; z-index: 999999999;">
                                复制到粘贴板
                            </div>
                        </div>
                        
                </div>
            </div>
            <ol id="cyberduckprofile" start="1" class="dp-xml">
                <li><span class="tag">&lt;</span><span class="tag-name">?xml version="1.0" encoding="UTF-8"?</span><span class="tag">&gt;</span></li>
                <li><span><span class="tag">&lt;</span><span class="tag-name">!DOCTYPE plist PUBLIC "-//Apple//DTD PLIST 1.0//EN" "http://www.apple.com/DTDs/PropertyList-1.0.dtd"</span><span class="tag">&gt;</span></span></li>
                <li><span><span class="tag">&lt;</span><span class="tag-name">plist version="1.0"</span><span class="tag">&gt;</span></span></li>
                <li><span>&nbsp;&nbsp;&nbsp;&nbsp;<span class="tag">&lt;</span><span class="tag-name">dict</span><span class="tag">&gt;</span></span><span>&nbsp;&nbsp;</span></li>
                <li><span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="tag">&lt;</span><span class="tag-name">key</span><span class="tag">&gt;</span><span>Protocol</span><span class="tag">&lt;/</span><span class="tag-name">key</span><span class="tag">&gt;</span><span>&nbsp;&nbsp;</span></span></li>
                <li><span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="tag">&lt;</span><span class="tag-name">string</span><span class="tag">&gt;</span><span>swift</span><span class="tag">&lt;/</span><span class="tag-name">string</span><span class="tag">&gt;</span><span>&nbsp;&nbsp;</span></span></li>
                <li><span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="tag">&lt;</span><span class="tag-name">key</span><span class="tag">&gt;</span><span>Vendor</span><span class="tag">&lt;/</span><span class="tag-name">key</span><span class="tag">&gt;</span><span>&nbsp;&nbsp;</span></span></li>
                <li><span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="tag">&lt;</span><span class="tag-name">string</span><span class="tag">&gt;</span><span>cyberduck</span><span class="tag">&lt;/</span><span class="tag-name">string</span><span class="tag">&gt;</span><span>&nbsp;&nbsp;</span></span></li>
                <li><span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="tag">&lt;</span><span class="tag-name">key</span><span class="tag">&gt;</span><span>Description</span><span class="tag">&lt;/</span><span class="tag-name">key</span><span class="tag">&gt;</span><span>&nbsp;&nbsp;</span></span></li>
                <li><span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="tag">&lt;</span><span class="tag-name">string</span><span class="tag">&gt;</span><span>OpenStack Swift (le)</span><span class="tag">&lt;/</span><span class="tag-name">string</span><span class="tag">&gt;</span><span>&nbsp;&nbsp;</span></span></li>
                <li><span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="tag">&lt;</span><span class="tag-name">key</span><span class="tag">&gt;</span><span>Scheme</span><span class="tag">&lt;/</span><span class="tag-name">key</span><span class="tag">&gt;</span><span>&nbsp;&nbsp;</span></span></li>
                <li><span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="tag">&lt;</span><span class="tag-name">string</span><span class="tag">&gt;</span><span>https</span><span class="tag">&lt;/</span><span class="tag-name">string</span><span class="tag">&gt;</span><span>&nbsp;&nbsp;</span></span></li>
                <li><span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="tag">&lt;</span><span class="tag-name">key</span><span class="tag">&gt;</span><span>Default Port</span><span class="tag">&lt;/</span><span class="tag-name">key</span><span class="tag">&gt;</span><span>&nbsp;&nbsp;</span></span></li>
                <li><span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="tag">&lt;</span><span class="tag-name">string</span><span class="tag">&gt;</span><span>443</span><span class="tag">&lt;/</span><span class="tag-name">string</span><span class="tag">&gt;</span><span>&nbsp;&nbsp;</span></span></li>
                <li><span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="tag">&lt;</span><span class="tag-name">key</span><span class="tag">&gt;</span><span>Hostname Configurable</span><span class="tag">&lt;/</span><span class="tag-name">key</span><span class="tag">&gt;</span><span>&nbsp;&nbsp;</span></span></li>
                <li><span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="tag">&lt;</span><span class="tag-name">true/</span><span class="tag">&gt;</span><span>&nbsp;&nbsp;</span></span></li>
                <li><span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="tag">&lt;</span><span class="tag-name">key</span><span class="tag">&gt;</span><span>Port Configurable</span><span class="tag">&lt;/</span><span class="tag-name">key</span><span class="tag">&gt;</span><span>&nbsp;&nbsp;</span></span></li>
                <li><span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="tag">&lt;</span><span class="tag-name">true/</span><span class="tag">&gt;</span><span>&nbsp;&nbsp;</span></span></li>
                <li><span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="tag">&lt;</span><span class="tag-name">key</span><span class="tag">&gt;</span><span>Context</span><span class="tag">&lt;/</span><span class="tag-name">key</span><span class="tag">&gt;</span><span>&nbsp;&nbsp;</span></span></li>
                <li><span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="tag">&lt;</span><span class="tag-name">string</span><span class="tag">&gt;</span><span>/auth/v1.0</span><span class="tag">&lt;/</span><span class="tag-name">string</span><span class="tag">&gt;</span><span>&nbsp;&nbsp;</span></span></li>
                <li><span>&nbsp;&nbsp;&nbsp;&nbsp;<span class="tag">&lt;/</span><span class="tag-name">dict</span><span class="tag">&gt;</span><span>&nbsp;&nbsp;</span></span></li>
                <li><span><span class="tag">&lt;</span><span class="tag-name">/plist</span><span class="tag">&gt;</span><span>&nbsp;&nbsp;</span></span></li>
            </ol>
        </div>
    </div>
    <p><span style="text-transform:none;text-indent:0px;display:inline !important;font:14px/21px 微软雅黑,Microsoft YaHei;white-space:normal;float:none;letter-spacing:normal;color:rgb(0,0,0);word-spacing:0px;">保存完成后，文件图标变成:<img src="${ctx}/static/img/help/27.jpg">
双击该文件导入cyberduck。
    </p>
    <p><span style="text-transform:none;text-indent:0px;display:inline !important;font:14px/21px 微软雅黑,Microsoft YaHei;white-space:normal;float:none;letter-spacing:normal;color:rgb(0,0,0);word-spacing:0px;">3. 导入完成后，打开cbyerduck，新建连接，选择openstack swift (le)，如下图：
    </p>
    <p><img src="${ctx}/static/img/help/28.jpg"></p>
    <p><span style="text-transform:none;text-indent:0px;display:inline !important;font:14px/21px 微软雅黑,Microsoft YaHei;white-space:normal;float:none;letter-spacing:normal;color:rgb(0,0,0);word-spacing:0px;">4. 在服务器文本框中输入OSS->“基本信息” 中显示的访问地址，及登录matrix使用的用户名与密码，点击连接即可,如下图：
    </p>
    <p><img src="${ctx}/static/img/help/29.jpg"></p>
    <p><span style="text-transform:none;text-indent:0px;display:inline !important;font:14px/21px 微软雅黑,Microsoft YaHei;white-space:normal;float:none;letter-spacing:normal;color:rgb(0,0,0);word-spacing:0px;">5. 5） 连接成功后即可查看、上传/下载文件，如下图：
    </p>
    <p><img src="${ctx}/static/img/help/30.jpg"></p>
<!-- </div> -->
<script type="text/javascript" src="${ctx}/static/modules/jquery/zclip/jquery.zclip.min.js"></script>

<script>
    var target=$('.data-spm-click').attr('data-id');
    $('.vnavbar').find('a').removeClass('current');
    $('.vnavbar').find('li[data-id='+target+']').children('a').addClass('current');
    $('li[data-id='+target+']').closest('ul').removeClass('hidden');
    $('li[data-id='+target+']').closest('ul').prev().find('span').removeClass('arrow-down').addClass('arrow-up');
    // /*初始化tooltip*/
    $('#zclipCopy').hover(function(){
        $("#zeroclipboardTooltip").fadeIn('fast').removeClass('hidden');
    },function(){
        $("#zeroclipboardTooltip").fadeOut('fast').addClass('hidden').text('复制到剪贴板');
    })
    /*初始化复制功能按钮*/           
    $('#zclipCopy').zclip({
            path: '/static/modules/jquery/zclip/ZeroClipboard.swf',
            copy: function(){
                return $('#cyberduckprofile').text();
            },
            afterCopy:function(){
                $("#zeroclipboardTooltip").removeClass('hidden').text('复制成功');
            }
    });
</script>