package com.letv.portal.service.invitecode;

import com.letv.portal.model.invitecode.InviteCode;
import com.letv.portal.service.IBaseService;


public interface IInviteCodeService extends IBaseService<InviteCode>  {
	
	/**
	  * @Title: create
	  * @Description: 生产邀请码
	  * @param amount 创建的个数   
	  * @throws 
	  * @author lisuxiao
	  * @date 2015年11月26日 上午10:46:30
	  */
	void create(Integer amount);
	
	/**
	  * @Title: verify
	  * @Description: 验证邀请码
	  * @param inviteCode 邀请码
	  * @param kaptcha 验证码
	  * @return int 0-验证失败，1-验证通过， 2-邀请码已使用   
	  * @throws 
	  * @author lisuxiao
	  * @date 2015年11月26日 上午11:06:11
	  */
	int verify(String inviteCode, String kaptcha);
	
	/**
	  * @Title: isInviteCodeUser
	  * @Description: 查询该用户是否是邀请码用户
	  * @param userId 用户id
	  * @return boolean  
	  * @throws 
	  * @author lisuxiao
	  * @date 2015年11月26日 下午2:05:44
	  */
	boolean isInviteCodeUser(Long userId);
}
