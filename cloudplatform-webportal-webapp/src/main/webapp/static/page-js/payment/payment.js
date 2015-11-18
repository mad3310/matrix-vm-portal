var redirects={
    '2':'/cvm/#/vm',
    '3':'/cvm/#/vm-disk',
    '4':'/cvm/#/vm-floatIP',
    '5':'/cvm/#/vm-router',
    'renew':'http://uc.letvcloud.com/uc/renew/getRenewView.do'
}
//余额选择
function remainChose(){
    $('.self-checkbox').unbind('click').click(function(event) {
        var _target=$(this);
        if(_target.hasClass('active')){
            // $('.remainInput').addClass('hide');
            $('.remainInput').css({
                transform:'translateY(-40px)',
                opacity:0,
                position:'absolute',
                transition:'all .5s ease-in',
            });
            _target.removeClass('active');
        }else{
            _target.addClass('active');
            // $('.remainInput').removeClass('hide')
            $('.remainInput').css({
                transform:'translateY(0px)',
                opacity:1,
                position:'relative',
                transition:'all .5s ease-in',
            });
        }
        alloptionsHandle();
    });
}
//余额支付金额校验，非负、不可大于订单金额，不可大于余额金额
function moneyInputVali(){
    var flag=false;
    var _target=$('.remainPay');
    var _paybtn=$('#pay');
    var _errordesc=$('.error-desc');
    var reg=/^-?\d+(\.\d{1,2})?$/;
    var remain=orderDetail();
    remain.done(function(){
        var orderPaynum=Number($('#orderpay').text().substring(1));//订单金额
        var remain=$('.remain').text().substring(1);
        var compare=(orderPaynum>remain)?remain:orderPaynum;
        _target.unbind('change').change(function(event){
            var money=_target.val();
            if(money){
                if(reg.test(money)){
                    if(money<0){
                        _target.addClass('has-error');
                        _errordesc.text('输入不合法数字！请输入两位小数');
                        _paybtn.attr('disabled', 'true');
                    }else{
                        if(money>compare){
                            _target.addClass('has-error');
                            _errordesc.text('支付金额有问题！');
                            _paybtn.attr('disabled', 'true');
                        }else{
                            // if(money==compare){
                            //     _paybtn.addAttr('disabled');
                            // }else{
                            //     _paybtn.removeAttr('disabled');
                            // }
                            $('.payoption:eq(0)').addClass('active');
                            _target.removeClass('has-error');
                            _errordesc.addClass('hide');
                            
                            flag=true;
                        }
                    }
                }else{//不是数字
                    _target.addClass('has-error');
                    _errordesc.text('输入不合法数字！请输入两位小数');
                    _paybtn.attr('disabled', 'true');
                }
            }else{
                if($('.self-checkbox').hasClass('active')){
                    _target.addClass('has-error');
                    _errordesc.text('输入不合法数字！请输入两位小数');
                    _paybtn.attr('disabled', 'true'); 
                }else{
                    flag=true;
                    _target.removeClass('has-error');
                    _errordesc.addClass('hide');
                    _paybtn.removeAttr('disabled')
                }
            } 
            return flag;
        });
    })
}
//支付方式选择
function payOptionChose(){
    $('.payoption').unbind('click').click(function(event) {
        var _target=$(this);
        if(_target.hasClass('active')){
            _target.removeClass('active');
        }else{
            _target.addClass('active').siblings().removeClass('active');
        }
        alloptionsHandle();
    });
}
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
        cache:false,
        type: 'get',
        success:function(data){
            var _data=data.data;
            if(data.result==0){//error
            }else{
                $('.account').text(_data.email);
            }
        }
    });
    return $.ajax({
        url:remainurl,
        cache:false,
        type: 'get',
        success:function(data){
            var _data=data.data;
            if(data.result==0){//error
            }else{
                $('.remain').text('¥'+_data);
            }
        }
    });
}
//订单详情
function orderDetail(){
    var orderNum=$('#orderNum').val();
    var orderurl='/order/'+orderNum
    return $.ajax({
    url:orderurl,
    cache:false,
    type: 'get',
    success:function(data){
         if(data.result==0){//error
             alert(data.msgs)
         }else{
             var _target=$('#order-tbody');
             _target.html('');
             var orderArray=data.data;
             var totalprice=0;
             var order,orderHtml='',params='',paramsArray='';
             var index='',title='',desc='';
             orderHtml=orderHtml+'<tr>'
                                 +'<td>'+orderArray[0].orderNumber+'</td>'
                                 +'<td><div style="width:50%;text-align:right;">云服务产品</div></td>'
                                 +'<td>'+orderArray.length+'</td>'
                                 +'<td>1个月</td>'
                                 +'<td class="price">¥'+orderArray[0].totalPrice+'</td>'
                             +'</tr>'
             for(var i in orderArray){
                var paramsHtml='';
                order=orderArray[i];
                totalprice=totalprice+order.price;
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
                                         +'<td></td>'
                                         +'<td><div style="width:50%;text-align:right;">'+order.productName+'</div></td>'
                                         +'<td>'+order.orderNum+'</td>'
                                         +'<td>1个月</td>'
                                         +'<td class="price">¥'+order.price+'</td>'
                                     +'</tr>'
                                    +'<tr>'
                                     +'<td></td>'
                                     +'<td>'
                                         +'<div class="payitems">'
                                            +paramsHtml
                                         +'</div>'
                                     +'</td>'
                                     +'<td></td>'
                                     +'<td></td>'
                                     +'<td></td>'
                                 +'</tr>';
            }
            $('#orderpay').text('¥'+totalprice);
            var userRemain=userInfo();
            userRemain.done(function(data){
                if(Number(data.data)>=Number(totalprice)){
                    $('.remainPay').val(totalprice);
                }else{
                    $('.remainPay').val(data.data);
                }
            });
            _target.append(orderHtml);
         }
     }
    });
}
//支付按钮状态
function alloptionsHandle(){
    var _paybtn=$('#pay');
    var orderPaynum=Number($('#orderpay').text().substring(1));
    var remainpay=$('.remainPay').val();
    // 检查当前的支付方式
    var _alloption=$('.alloption.active');
    if(_alloption.length>0){
        if(_alloption.length>1){
            _paybtn.removeAttr('disabled');
        }else{
            if(remainpay==orderPaynum){
                _paybtn.removeAttr('disabled'); 
            }else{
                _paybtn.attr('disabled','true');
            } 
        }
    }else{//未选择支付方式
        _paybtn.attr('disabled','true');
    }
}
//窗口&跳转支付
function goPay(){
    var width=document.body.scrollWidth;
    var orderNum=$('#orderNum').val();
    var redirect=$('#redirect').val();
    var remain=orderDetail();
    $('#pay').unbind('click').click(function(event){//窗口&跳转支付
        remain.done(function(){
            var orderPaynum=Number($('#orderpay').text().substring(1));//订单金额
            var remainPaynum=$('.remainPay').val();
            var money=orderPaynum-remainPaynum;
            // 检查当前的支付方式
            var _alloption=$('.alloption.active');
            var alloptions={
                '0':'/pay/'+orderNum+'?accountMoney='+remainPaynum,//余额支付
                '1':'/pay/'+orderNum+'?pattern=1',//支付宝
                '2':'/payment/wxpay?orderNum='+orderNum+'&money='+orderPaynum+'&accountMoney=0',//微信
                '01':'/pay/'+orderNum+'?pattern=1&accountMoney='+remainPaynum,//余额&支付宝
                '02':'/payment/wxpay?orderNum='+orderNum+'&money='+money.toFixed(2)+'&accountMoney='+remainPaynum//余额&微信
            }
            var option='';
            if(_alloption.length>0){
                $.ajax({
                    url: '/order/pay/'+orderNum,
                    cache:false,
                    type: 'get',
                    success:function(data){
                        var height=document.body.scrollHeight;
                        if(data.result==0){//error
                        }else{
                            if(data.data.status==1){//已失效
                                alert('订单已失效')
                            }else{
                                if(_alloption.length>1){//组合支付
                                    if(money==0){//默认支付宝全额付
                                        option='0';
                                    }else{
                                       option='0'+$('.payoption.active').attr('self-payoption'); 
                                    }
                                }else{//一种支付方式
                                    option=$('.alloption.active').attr('self-payoption');
                                }
                                var payoption=alloptions[option];
                                window.open(payoption);
                                $('.modal-container').css({
                                    width:width,
                                    height:height
                                }).removeClass('hide');
                            }
                        }
                    }
                });
            }else{//未选择支付方式
            }
        });   
    });
    $('.paybtn').unbind('click').click(function(event) {//隐藏窗口
        $.ajax({
            url: '/order/pay/'+orderNum,
            cache:false,
            type: 'get',
            success:function(data){
                var url='/cvm/#/vm';
                if(data.result==0){//error

                }else{
                    if(data.data.status==2){//已付款
                        if(redirects[redirect]){
                            url=redirects[redirect]
                        }
                        window.location.href=url;
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
        cache:false,
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