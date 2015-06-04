<%@ page language="java" pageEncoding="UTF-8"%>
<!-- <div class="content-right" data-spm="2"> -->
    <div class="bread-crumb">
        <span class="item"><a href="#">负载均衡SLB</a></span>&nbsp;&gt;&nbsp;<span class="data-spm-click" data-id="6-1-2">SLB使用限制</span>
    </div>
    <h2 style="text-align: center;color: #333;font-size: 18px;">SLB使用限制</h2>
	<div class="approve">
		<div class="product-title">
			<h1>SLB使用限制</h1>
		</div>
		<div class="approve-detail y-clear">
			<div class="dispaly-4div">
				<h3 class="">同地域</h3>
				<p class="">SLB不支持跨地域（Region）部署，也就是说一个SLB实例后端的GCE必须是属于同一地域（Region）的GCE实例。</p>
			</div>
			<div class="dispaly-4div">
				<h3 class="">依赖</h3>
				<p class="">
					在4层（TCP协议）服务中，当前不支持添加进后端云服务器池的GCE既作为Real Server，又作为客户端向所在的SLB实例发送请求。因为，返回的数据包只在云服务器内部转发，不经过SLB，所以通过配置在SLB内的GCE去访问的VIP是不通的。</p>
			</div>
			<div class="dispaly-4div">
				<h3 class="">端口</h3>
				<p class="">为了满足其安全合规的需求，目前其公网类型的SLB实例端口只能对外开放这些端口：80。</p>
			</div>
		</div>
	</div>
	<div class="approve">
		<div class="product-title">
			<h1>SLB使用注意事项</h1>
		</div>
		<ul style="color:#333;">
			<li>在通过SLB对外提供服务前，首先要确保已经完成并正确配置了所有SLB后端GCE上的应用服务，且能通过GCE的服务地址正确访问该服务.</li>
			<li>SLB不提供GCE间的数据同步服务，如果部署在SLB后端GCE上的应用服务是无状态的，那么可以通过独立的GCE或RDS服务来存储数据；如果部署在SLB后端GCE上的应用服务是有状态的，那么需要确保这些GCE上的数据是同步的。</li>
			<li>当SLB实例的服务地址（IP）已经解析到正常的域名进行对外服务时，请不要随意删除该SLB实例。删除SLB实例操作会将该SLB实例的服务地址（IP）一同释放掉，从而导致已经对外提供的服务中断。如果创建新的SLB实例，系统会重新分配一个服务地址（IP）。</li>
		</ul>
	</div>
<!-- </div> -->
<script>
var target=$('.data-spm-click').attr('data-id');
$('.vnavbar').find('a').removeClass('current');
$('.vnavbar').find('li[data-id='+target+']').children('a').addClass('current');
$('li[data-id='+target+']').closest('ul').removeClass('hidden');
$('li[data-id='+target+']').closest('ul').prev().find('span').removeClass('arrow-down').addClass('arrow-up');
</script>