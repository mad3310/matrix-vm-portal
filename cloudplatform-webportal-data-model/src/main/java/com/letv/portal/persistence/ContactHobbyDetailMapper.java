/**
 * Created on Oct 24, 2011
 */
package com.letv.portal.persistence;

import com.letv.portal.model.ContactHobbyDetail;

/**
 * @author Clarence
 *
 */
public interface ContactHobbyDetailMapper {

	public void insertContactHobbyDetail(ContactHobbyDetail contactHobbyDetail);
	
	public void deleteHobbyDetailForContact(Long contactId);	
	
}
