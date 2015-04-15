/**
 * Created on Oct 24, 2011
 */
package com.letv.portal.persistence;

import java.util.List;

import com.letv.portal.model.Contact;

/**
 * @author Clarence
 *
 */

// Mapper 是 MyBatis 的核心， 3个相关的部分
// Domain object， Mapper interface， SQL mapping configuration file
// Domain object 在 com.apress.prospring3.ch11.domain
// Mapper interface 在这里 com.apress.prospring3.ch11.persistence
// SQL mapping configuration file 在
// resources目录 com.apress.prospring3.ch11.persistence

public interface ContactMapper {

	// 这些操作的实际实现都在 ContactMapper.xml文件
	public List<Contact> findAll();
	
	public List<Contact> findAllWithDetail();
	
	public Contact findById(Long id);
	
	//public List<Contact> findByFirstNameAndLastName(SearchCriteria criteria);	
	
	public void insertContact(Contact contact);
	
	public void updateContact(Contact contact);
	
	public void deleteContact(Long id);
	
}
