//点击刷新图片
	function codeimgClick(){
		$('.vali-codeimg').unbind('click').click(function(event) {
		  	$('.vali-codeimg').hide()
		                    .attr('src', '/kaptcha?random='+Math.random()*100)
		                    .fadeIn();
		});
	}
	function inviteVali(obj){
		var flag=false;
		var reg=/^[a-zA-z0-9]{12}$/;
		if(reg.test(obj.val())){
			flag=true;
			obj.removeClass('has-error')
				.next('.error-msg').addClass('hide');
		}else{
			obj.addClass('has-error')
				.next('.error-msg').removeClass('hide').text('邀请码格式错误');
		}
		return flag;
	}
	function codeVali(obj){
		var flag=false;
		var code=obj.val();
		$.ajax({
			url: '/kaptcha',
			async: false,
			type: 'post',
			data: {'kaptcha':code},
			success:function(data){
				if(data.data){
					flag=true;
					obj.removeClass('has-error')
						.next().next('.error-msg').addClass('hide');
				}else{
					flag=false;
					$('.vali-codeimg').attr('src', '/kaptcha?random='+Math.random()*100);
					obj.addClass('has-error')
						.next().next('.error-msg').removeClass('hide').text('验证码错误，请重新输入');
				}
			}
		});
		return flag;
	}
	function totalValify(){
		var flag=true;
		var inviteresult=inviteVali($('.inviteCode'));
		var coderesult=codeVali($('.valicode-input'));
		if(inviteresult&&coderesult){
		}else{
			flag=false;
		}
		return flag;
	}
	function ifneedcode(){
		return $.ajax({
			url: '/kaptcha/isNeed',
			type: 'get',
			success:function(data){
				var _target=$('#valicode');
				if(data.data){//需要
					_target.removeClass('hide');
				}else{
					_target.addClass('hide')
				}
			}
		});
	}
	function inviteBtnClick(){
		var html='';
		$('.invitebtn').unbind('click').click(function(event){
			var invitecode=$('.inviteCode').val(),code=$('.valicode-input').val(),inputdata='';
			var ifneedcodeResult=ifneedcode();
			ifneedcodeResult.done(function(data){
				if(data.data){//需要验证码
					if($('.vali-codeimg').length>0){//已加载
					}else{//加载验证码
						html='<input type="text" class="valicode-input formInput" id="input_idcode" placeholder="点击图片刷新验证码" onchange="codeVali($(this))" onpropertychange="codeVali($(this))"/>'
						+'<img src="../kaptcha" class="vali-codeimg formInput" id="idcode">'
						+'<div class="error-msg hide"></div>';
						$('#valicode').append(html)
					}
					if(totalValify()){
						inputdata={
							'inviteCode':invitecode,
							'kaptcha':code
						}
						inviteajax(inputdata)
					}
				}else{
					$('#valicode').html('');
					if(inviteVali($('.inviteCode'))){//只需要验证邀请码
						inputdata={
							'inviteCode':invitecode
						}
						inviteajax(inputdata)
					}
				}	
			});
		});
	}
	function inviteajax(inputdata){
		var time='';
		$.ajax({
			url:'/inviteCode/verify',
			type:'post',
			data:inputdata,
			success:function(data){
				if(data.data==0){
					if($('#idcode').length>0){//有验证码,刷新验证码
						$('#idcode').attr('src', '../kaptcha');
					}
					$('.blockInputs').removeClass('hide');
					$('.blockSuccess').addClass('hide');
					$('.inviteCode').addClass('has-error').next('.error-msg').removeClass('hide').text(data.msgs);
				}else if(data.data==1){//验证通过
					$('.inviteCode').removeClass('has-error').next('.error-msg').addClass('hide');
					$('.blockInputs').addClass('hide');
					$('.blockSuccess').removeClass('hide');
					//初始化定时器
					if(time){
						clearTimeout(time)
					}else{
						time=setTimeout(function(){
							window.location.href="http://matrix.letvcloud.com/profile";
						},5000);
					}
				}else{
					$('.blockInputs').removeClass('hide');
					$('.blockSuccess').addClass('hide');
					$('.inviteCode').addClass('has-error').next('.error-msg').removeClass('hide').text(data.msgs);
				}
			}
		})
	}