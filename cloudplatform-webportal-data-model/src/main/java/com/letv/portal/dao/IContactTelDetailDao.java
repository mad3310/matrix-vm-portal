/**
 * Created on Oct 24, 2011
 */
package com.letv.portal.dao;

import java.util.List;

import com.letv.portal.model.ContactTelDetail;

/**
 * @author liyunhui
 *
 */
public interface IContactTelDetailDao {

	public List<ContactTelDetail> selectTelDetailForContact(Long contactId);
	
	public void insertContactTelDetail(ContactTelDetail contactTelDetail);
	
	public void updateContactTelDetail(ContactTelDetail contactTelDetail);
	
	public void deleteOrphanContactTelDetail(List<Long> ids);
	
	public void deleteTelDetailForContact(Long contactId);	
	
}
