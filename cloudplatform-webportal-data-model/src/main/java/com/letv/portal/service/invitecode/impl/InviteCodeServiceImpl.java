package com.letv.portal.service.invitecode.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.letv.common.dao.IBaseDao;
import com.letv.common.session.SessionServiceImpl;
import com.letv.common.util.PasswordRandom;
import com.letv.mms.cache.ICacheService;
import com.letv.mms.cache.factory.CacheFactory;
import com.letv.portal.constant.Constant;
import com.letv.portal.dao.invitecode.IInviteCodeDao;
import com.letv.portal.model.invitecode.InviteCode;
import com.letv.portal.service.impl.BaseServiceImpl;
import com.letv.portal.service.invitecode.IInviteCodeService;

/**
 * 
 * @author lisuxiao
 *
 */
@Service("inviteCodeService")
public class InviteCodeServiceImpl extends BaseServiceImpl<InviteCode>  implements IInviteCodeService {

    private static final Logger logger = LoggerFactory.getLogger(InviteCodeServiceImpl.class);

    @Resource
	private IInviteCodeDao inviteCodeDao;
    @Autowired
	private SessionServiceImpl sessionService;
    private ICacheService<?> cacheService = CacheFactory.getCache();
	
	public InviteCodeServiceImpl() {
		super(InviteCode.class);
	}

	@Override
	public IBaseDao<InviteCode> getDao() {
		return this.inviteCodeDao;
	}

	@Override
	public void create(Integer amount) {
		for(int i=0; i<amount; i++) {
			InviteCode invite = new InviteCode();
			invite.setInviteCode(PasswordRandom.genStr(12).toLowerCase());
			invite.setCreateUser(this.sessionService.getSession().getUserId());
			invite.setUsed("0");
			this.inviteCodeDao.insert(invite);
		}
	}

	@Override
	public int verify(String inviteC, String kaptcha) {
		if(!validate(kaptcha)) {
			return 0;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("inviteCode", inviteC);
		List<InviteCode> invites = this.inviteCodeDao.selectByMap(params);
		if(invites!=null && invites.size()>0) {
			for (InviteCode inviteCode : invites) {
				if("0".equals(inviteCode.getUsed())) {
					inviteCode.setUsed("1");
					inviteCode.setUserId(this.sessionService.getSession().getUserId());
					this.inviteCodeDao.update(inviteCode);
					return 1;//验证通过
				}
			}
			return 2;//邀请码已使用
		} else {
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.add(Calendar.DATE, 1);
			//验证后，验证次数放入缓存
			int i = 0;
			if(cacheService.get(Constant.KAPTCHA_VERIFY_COUNT + this.sessionService.getSession().getUserId(), null)!=null) {
				i = (Integer)cacheService.get(Constant.KAPTCHA_VERIFY_COUNT + this.sessionService.getSession().getUserId(), null);
				i++;
			} 
			cacheService.set(Constant.KAPTCHA_VERIFY_COUNT + this.sessionService.getSession().getUserId(), i, cal.getTime());
			
		}
		return 0;//验证失败
	}
	
	private boolean validate(String kaptcha) {
		//前三次不进行验证
		if(cacheService.get(Constant.KAPTCHA_VERIFY_COUNT + this.sessionService.getSession().getUserId(), null)==null || 
				(Integer)cacheService.get(Constant.KAPTCHA_VERIFY_COUNT + this.sessionService.getSession().getUserId(), null)<3) {
			return true;
		}
		if(StringUtils.isEmpty(kaptcha)) {
			return false;
		}
		String cacheKaptcha = (String) cacheService.get(Constant.KAPTCHA_COOKIE_NAME + this.sessionService.getSession().getUserId(), null);
		return kaptcha.equals(cacheKaptcha);
	}
	

	@Override
	public boolean isInviteCodeUser(Long userId) {
		if(userId != null) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("userId", userId);
			params.put("used", "1");//使用
			List<InviteCode> invites = this.inviteCodeDao.selectByMap(params);
			if(invites!=null && invites.size()>0) {
				return true;
			}
		}
		return false;
	}
	
}