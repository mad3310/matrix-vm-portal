/**
 * Created on Oct 24, 2011
 */
package com.letv.portal.dao;

import java.util.List;

import com.letv.portal.model.Contact;
import com.letv.portal.model.SearchCriteria;

/**
 * @author liyunhui
 *
 */

public interface IContactDao {

	public List<Contact> findAll();
	
	public List<Contact> findAllWithDetail();
	
	public Contact findById(Long id);
	
	public List<Contact> findByFirstNameAndLastName(SearchCriteria criteria);	
	
	public void insertContact(Contact contact);
	
	public void updateContact(Contact contact);
	
	public void deleteContact(Long id);
	
}
