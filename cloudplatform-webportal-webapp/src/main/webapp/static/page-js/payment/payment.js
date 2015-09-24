//展开&收起
function rollup(){
	$('.title-rollup').unbind('click').click(function(){
		var _target=$('.ordertable');
		var _targetI=$('.icon-arrow01');
		var _targetTxt=$('.rollup-text');
		if(_target.hasClass('opacity')){
			_target.removeClass('opacity')
			_target.css({
				opacity: '0',
				transition: 'opacity .2s ease-in'
			});
			_targetI.css({
				transform:'rotate(0deg)',
				transition:'transform .2s ease-in'
			});
			_targetTxt.text('展开');
		}else{
			_target.css({
				opacity: '1',
				transition: 'opacity .2s ease-in'
			});
			_target.addClass('opacity');
			_targetI.css({
				transform:'rotate(180deg)',
				transition:'transform .2s ease-in'
			});
			_targetTxt.text('收起');
		}
	});
}
//获取用户账号&余额
function userInfo(){
    var userid=$('#userId').val();
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
}
//订单详情
function orderDetail(){
    var orderNum=$('#orderNum').val();
    var orderurl='/order/'+orderNum
    $.ajax({
     url:orderurl,
     type: 'get',
     success:function(data){
         if(data.result==0){//error
             alert(data.msgs)
         }else{
             var _target=$('#order-tbody');
             _target.html('');
             var orderArray=data.data;
             var totalprice='';
             var order,orderHtml='',params='',paramsArray='',paramsHtml='';
             var index='',title='',desc='';
             for(var i in orderArray){
                order=orderArray[i];
                totalprice=totalprice+order.totalPrice;
                paramsArray=order.params.split("/;");
                for(var j in paramsArray){
                    params=paramsArray[j];
                    index=params.indexOf('/:');
                    title=params.substring(0,index);
                    desc=params.substring(index+2);
                    paramsHtml=paramsHtml+'<div class="payitem clearfix">'
                                                +'<div class="text-right">'+title+'&nbsp;:</div><div class="payitem-desc">&nbsp;'+desc+'</div>'
                                        +'</div>'
                }
                orderHtml=orderHtml+'<tr>'
                                         +'<td>'+order.orderNumber+'</td>'
                                         +'<td><div style="width:50%;text-align:right;">购买服务器</div></td>'
                                         +'<td>'+order.orderNum+'</td>'
                                         +'<td>1个月</td>'
                                         +'<td class="price">¥'+order.price+'</td>'
                                     +'</tr>'
                                     +'<tr>'
                                         +'<td></td>'
                                         +'<td>'
                                             +'<div class="payitems">'
                                                +paramsHtml
                                                 // +'<div class="payitem clearfix">'
                                                 //     +'<div class="text-right">配置&nbsp;:</div><div class="payitem-desc">&nbsp;1核，2G内存，无数据盘</div>'
                                                 // +'</div>'
                                             +'</div>'
                                         +'</td>'
                                         +'<td></td>'
                                         +'<td></td>'
                                         +'<td></td>'
                                     +'</tr>';
            }
            $('#orderpay').text('¥'+totalprice);
            _target.append(orderHtml);
         }
     }
    });
}
//窗口&跳转支付
function goPay(){
    var width=document.body.scrollWidth;
    var orderNum=$('#orderNum').val();
    $('#pay').unbind('click').click(function(event){//窗口&跳转支付
        $.ajax({
            url: '/order/pay/'+orderNum,
            type: 'get',
            success:function(data){
                var height=document.body.scrollHeight;
                if(data.result==0){//error
                }else{
                    if(data.data.status==1){//已失效
                        alert('订单已失效')
                    }else{
                        window.open('/pay/'+orderNum+'?pattern=1');
                        $('.modal-container').css({
                            width:width,
                            height:height
                        }).removeClass('hide');
                    }
                }
            }
        });
    });
    $('.paybtn').unbind('click').click(function(event) {//隐藏窗口
        $.ajax({
            url: '/order/pay/'+orderNum,
            type: 'get',
            success:function(data){
                if(data.result==0){//error

                }else{
                    if(data.data.status==2){//已付款
                        window.location.href="/cvm/#/vm";
                        $('.modal-container').addClass('hide');
                    }else{
                        $('.modal-container').addClass('hide');
                    }
                }
            }
        });
    });
    $('.icon-add').unbind('click').click(function(event) {
        $('.modal-container').addClass('hide');
    });

}
//订单支付状态api
function queryStatus(){
    var ordernum=$('#orderNum').val();
    var _tip=$('.desc-tip');
    var _paynum=$('#payNum');
    var _tiphtml='';
    return $.ajax({
        url: '/order/pay/'+ordernum,
        type: 'get',
        success:function(data){
            _tip.html('');
            if(data.result==0){//error
                alert(data.msgs)
            }else{
                var _target=data.data;
                if(_target.status==0){//支付不成功
                    _tiphtml='<div class="successicon fail"></div>'
                            +'<div  class="desc-success">支付处理中...</div>'
                            // +'<div class="desc-pay">待支付金额¥<span class="price">'+_target.totalPrice+'</span>元</div>';
                    // _paynum.text('-');
                }else{
                    _tiphtml='<div class="successicon"></div>'
                            +'<div  class="desc-success">支付成功</div>'
                            +'<div class="desc-pay">恭喜您成功支付¥<span class="price">'+_target.totalPrice+'</span>元</div>';
                    _paynum.text('支付订单号：'+_target.payNumber);
                }
                _tip.append(_tiphtml);
            }
        }
    })
}
//订单支付状态定时查询
function timeStatus(){
    var _tip=$('.desc-tip');
    var paystatus=queryStatus();       
    paystatus.done(function(data) {
        var timenum=0,time='';
        if(data.data.status==0){//支付不成功
            time=setInterval(function(){
                paystatus=queryStatus();
                paystatus.done(function(data){
                    timenum=timenum+4;
                    if(data.data.status==0){//支付不成功
                        if(timenum>=10){
                            _tip.children('div:eq(1)').text('支付不成功！');
                            clearInterval(time);
                        }
                    }else{
                        _tip.html('');
                        var _tiphtml='<div class="successicon"></div>'
                            +'<div  class="desc-success">支付成功</div>'
                            +'<div class="desc-pay">恭喜您成功支付¥<span class="price">'+data.data.totalPrice+'</span>元</div>';
                        clearInterval(time);
                        _tip.append(_tiphtml);
                    }  
                })
            },3000);
        }else{
        }
    });
}