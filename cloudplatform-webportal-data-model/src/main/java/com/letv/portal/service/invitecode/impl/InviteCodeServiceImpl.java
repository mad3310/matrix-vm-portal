package com.letv.portal.service.invitecode.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.letv.common.dao.IBaseDao;
import com.letv.common.session.SessionServiceImpl;
import com.letv.common.util.PasswordRandom;
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
	public int verify(String inviteC) {
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
		}
		return 0;//验证失败
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