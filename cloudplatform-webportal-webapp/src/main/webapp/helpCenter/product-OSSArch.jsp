<%@ page language="java" pageEncoding="UTF-8"%>
<!-- <div class="content-right" data-spm="2"> -->
    <div class="bread-crumb">
        <span class="item"><a href="#">开放存储OSS</a></span>&nbsp;&gt;&nbsp;<span class="data-spm-click" data-id="3-1-2">OSS与自建存储对比</span>
    </div>
    <h2 style="text-align: center;color: #333;font-size: 18px;">OSS与自建存储对比</h2>
	<div class="article-content">
		<div class="markdown-body ng-scope" ui-view="doc"
			style="margin: 0 10px; margin-bottom: 25px;">
			<div style="overflow: auto;">
				<table>
					<thead>
						<tr>
						<th>对比项</th>
						<th>开放存储OSS</th>
						<th>自建服务器存储</th>
						</tr>
					</thead>
					<tbody>
						<tr>
						<td>服务可用性</td>
						<td>99.9%</td>
						<td>自建服务器可用性低</td>
						</tr>
						<tr>
						<td>数据可靠性</td>
						<td>三重备份，可靠性99.99999999%</td>
						<td>需自行研发备份策略，为达到同步可靠性需增加更多备份服务器成本；</td>
						</tr>
						<tr>
						<td>系统安全性</td>
						<td>提供对称加密用户验证，签名权限控制及防盗链功能；强大的安全防护系统，抵御落洪水攻击；</td>
						<td>自行部署，价格高昂；</td>
						</tr>
						<tr>
						<td>网络资源</td>
						<td>BGP多线（电国电信、联通、移动、教育网等）骨干网络接入，全国各地流畅访问；无带宽限制，按实际用量付费</td>
						<td>单线或双线接入速度慢，有带宽限制，峰值时期需人工扩容；</td>
						</tr>
						<tr>
						<td>存储能力</td>
						<td>文件存储容量无限</td>
						<td>服务器存储受硬盘容量限制，需人工扩容</td>
						</tr>
						<tr>
						<td>文件处理能力</td>
						<td>支持海量数据处理，1个与数十亿个文件处理能力无差别</td>
						<td>Windows系统一个目录下仅支持6万多个文件，Liunx系统目录下支持3万多个文件；当一个目录存储文件过多时，IO性能会直线下降</td>
						</tr>
						<tr>
						<td>维护成本</td>
						<td>无需运维</td>
						<td>需招聘专职运维人员，花费大量人力成本</td>
						</tr>
						<tr>
						<td>部署扩容</td>
						<td>无需规则，按实际使用自动扩容，弹性伸缩</td>
						<td>需硬件采购、机房托管、部署机器等工作，周期较长</td>
						</tr>
						<tr>
						<td>资源利用率</td>
						<td>按实际结算，100%利用率</td>
						<td>考虑峰值，资源利用率很低</td>
						</tr>
					</tbody>
					</table>
				</div>
		</div>
	</div>
<!-- </div> -->
<script>
var target=$('.data-spm-click').attr('data-id');
$('.vnavbar').find('a').removeClass('current');
$('.vnavbar').find('li[data-id='+target+']').children('a').addClass('current');
$('li[data-id='+target+']').closest('ul').removeClass('hidden');
$('li[data-id='+target+']').closest('ul').prev().find('span').removeClass('arrow-down').addClass('arrow-up');
</script>