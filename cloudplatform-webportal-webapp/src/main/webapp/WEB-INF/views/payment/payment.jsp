<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="renderer" content="webkit">
	<title>乐视云计算-最专业的VaaS云平台</title>
	<meta name="Keywords" content="乐视云计算，云计算，VaaS，视频存储，免费空间，企业视频，云主机，开放平台">
	<link rel="shortcut icon" href="/static/staticPage/img/favicon.ico">
	<link rel="stylesheet" href="/static/staticPage/css/common.css">
	<link rel="stylesheet" href="/static/staticPage/css/style.css">
</head>
<body>
	<input id="userId" type="text" class="hide" value="${sessionScope.userSession.userId}">
	<input id="orderNum" type="text" class="hide" value="${orderNum}">
	<div class="main-body">
		<div class="order">
			<div class="order-title">订单支付</div>
			<div class="order-pay">
				<div class="pay-item">账号名称：<span class="item-desc account">letvcloud@letv.com</span></div>
				<div class="pay-item">
					<span>可用余额：</span><span class="item-desc remain">¥100</span>
					<button class="btn btn-le-red item-recharge">充值</buttom>
				</div>
				<div class="pay-item">本次需支付：<span class="text-red item-desc">¥1000</span></div>
				<div class="pay-item">
					<span>支付方式：</span>
					<button class="payoption active"><img src="/static/staticPage/img/zhifubao.png"></button>
					<!-- <button class="payoption"><img src="/static/staticPage/img/wechat.png"></button> -->
				</div>
				<div class="pay-item">
					<button class="btn btn-le-blue item-pay" id="pay">确认支付</button>
					<!-- <a class="btn btn-le-blue item-pay" id="pay">确认支付</a> -->
				</div>
			</div>
		</div>
		<div class="order">
			<div class="order-title">
				<span>订单详情</span>
				<span class="title-rollup">
					<span class="rollup-text">收起</span>
					<span class="iconfont icon-arrow01"></span>
					<span class="clearfix"></span>
				</span>
				<div class="clearfix"></div>
			</div>
			<div class="price-table ordertable opacity">
				<table class="col-md-12">
					<thead>
						<tr>
							<th width="12.5%">订单号</th>
							<th width="37.5%">配置</th>
							<th width="12.5%">数量</th>
							<th width="12.5%">单价</th>
							<th width="12.5%">使用时长</th>
							<th width="12.5%">支付费用</th>
						</tr>
					</thead>
					<tbody id="order-tbody">
						<tr>
							<td>0100002010010</td>
							<td>
								<div style="width:50%;text-align:right;">购买服务器</div>
							</td>
							<td>1</td>
							<td class="price">105.00元/月</td>
							<td>1个月</td>
							<td class="price">¥105</td>
						</tr>
						<tr>
							<td></td>
							<td>
								<div class="payitems">
									<div class="payitem clearfix">
										<div class="text-right">配置&nbsp;:</div><div class="payitem-desc">&nbsp;1核，2G内存，无数据盘</div>
									</div>
									<div class="payitem clearfix">
										<div class="text-right">带宽&nbsp;:</div><div class="payitem-desc">&nbsp;1Mbps</div>
									</div>
									<div class="payitem clearfix">
										<div class="text-right">操作系统&nbsp;:</div><div class="payitem-desc">&nbsp;CentOS 6.6 32位</div>
									</div>
									<div class="payitem clearfix">
										<div class="text-right">安全组件&nbsp;:</div><div class="payitem-desc">&nbsp;安全加固组件</div>
									</div>
									<div class="payitem clearfix">
										<div class="text-right">地域&nbsp;:</div><div class="payitem-desc">&nbsp;华东区-上海</div>
									</div>
									<div class="payitem clearfix">
										<div class="text-right">所属网络&nbsp;:</div><div class="payitem-desc">&nbsp;基础网络</div>
									</div>
									<div class="payitem clearfix">
										<div class="text-right">可用区&nbsp;:</div><div class="payitem-desc">&nbsp;上海一区</div>
									</div>
									<div class="payitem clearfix">
										<div class="text-right">作为网关&nbsp;:</div><div class="payitem-desc">&nbsp;否</div>
									</div>
								</div>
							</td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
	<div class="modal-container hide">
		<div class="modal">
			<div class="modal-top">
				<div class="modal-title"><span>充值完成前请不要关闭此窗口</span>
				<span class="iconfont icon-add"></span>
				<span class="clearfix"></span>
				</div>
			</div>
			<div class="modal-content">
				<div>请在新开充值页面上完成付款后，再继续支付。</div>
				<div class="buttons clearfix text-center">
					<div class="col-md-offset-2 col-md-4"><button class="btn btn-le-blue paybtn">充值完成</buttom></div>
					<div class="col-md-4"><button class="btn btn-le-red paybtn">充值遇到问题</buttom></div>
				</div>
			</div>
		</div>
	</div>
</body>
<script src="/static/javascripts/jquery-1.11.3.js"></script>
<script src="${ctx}/static/page-js/payment/payment.js"></script>
<script>
rollup();
var width=document.body.scrollWidth;
var height=document.body.scrollHeight;
var userid=$('#userId').val();
var orderNum=$('#orderNum').val();
var payurl="/user/"+userid;
var remainurl="/userAccount/balance/"+userid;
$.ajax({
	url:payurl,
	type: 'get',
	success:function(data){
		var _data=data.data;
		if(data.result==0){//error
		}else{
			$('.account').text(_data.email);
		}
	}
});
$.ajax({
	url:remainurl,
	type: 'get',
	success:function(data){
		var _data=data.data;
		if(data.result==0){//error
		}else{
			$('.remain').text(_data);
		}
	}
});
$('#pay').unbind('click').click(function(event){
	window.open('/pay/'+orderNum+'?pattern=1');
	$('.modal-container').css({
		width:width,
		height:height
	}).removeClass('hide');
});
// $.ajax({
// 	url: '/order/'+orderNum,
// 	type: 'get',
// 	success:function(data){
// 		if(data.result==0){//error

// 		}else{
// 			var _target=$('#order-tbody');
// 			var orderArray=data.data;
// 			var order,orderHtml='';
// 			for(var i in orderArray){
// 				order=orderArray[i];

// 				orderHtml=orderHtml+'<tr>'
// 										+'<td>'+order.orderNumber'</td>'
// 										+'<td><div style="width:50%;text-align:right;">购买服务器</div></td>'
// 										+'<td>'+order.params.count+'</td>'
// 										//+'<td class="price">105.00元/月</td>'
// 										+'<td>1个月</td>'
// 										+'<td class="price">¥'+order.totalPrice+'</td>'
// 									+'</tr>'
// 									+'<tr>'
// 										+'<td></td>'
// 										+'<td>'
// 											+'<div class="payitems">'
// 												+'<div class="payitem clearfix">'
// 													+'<div class="text-right">配置&nbsp;:</div><div class="payitem-desc">&nbsp;1核，2G内存，无数据盘</div>'
// 												+'</div>'
// 												+'<div class="payitem clearfix">'
// 													+'<div class="text-right">带宽&nbsp;:</div><div class="payitem-desc">&nbsp;'+order.bandWidth+'Mbps</div>'
// 												+'</div>'
// 												+'<div class="payitem clearfix">'
// 													+'<div class="text-right">操作系统&nbsp;:</div><div class="payitem-desc">&nbsp;CentOS 6.6 32位</div>'
// 												+'</div>'
// 												// +'<div class="payitem clearfix">'
// 												// 	+'<div class="text-right">安全组件&nbsp;:</div><div class="payitem-desc">&nbsp;安全加固组件</div>'
// 												// +'</div>'
// 												+'<div class="payitem clearfix">'
// 													+'<div class="text-right">地域&nbsp;:</div><div class="payitem-desc">&nbsp;华东区-上海</div>'
// 												+'</div>'
// 												+'<div class="payitem clearfix">'
// 													+'<div class="text-right">所属网络&nbsp;:</div><div class="payitem-desc">&nbsp;基础网络</div>'
// 												+'</div>'
// 												// +'<div class="payitem clearfix">'
// 												// 	+'<div class="text-right">可用区&nbsp;:</div><div class="payitem-desc">&nbsp;上海一区</div>'
// 												// +'</div>'
// 												// +'<div class="payitem clearfix">'
// 												// 	+'<div class="text-right">作为网关&nbsp;:</div><div class="payitem-desc">&nbsp;否</div>'
// 												// +'</div>'
// 											+'</div>'
// 										+'</td>'
// 										+'<td></td>'
// 										+'<td></td>'
// 										//+'<td></td>'
// 										+'<td></td>'
// 									+'</tr>';
// 			}
// 		}
// 	}
// })
// .done(function(data) {
// 	console.log(data);
// })
// .fail(function(data) {
// 	console.log(data);
// });
</script>
</html>