package com.letv.portal.dao;

import java.util.List;

import com.letv.common.dao.IBaseDao;
import com.letv.portal.model.ContactModel;

public interface ContactDao extends IBaseDao<ContactModel>{
	
	public List<ContactModel> findAll();

	public List<ContactModel> findByFirstName(String firstName);

	public void insert(ContactModel contact);

	public void update(ContactModel contact);

	public void delete(Long contactId);
	
	
	public String findFirstNameById(Long id);
	
	public String findLastNameById(Long id);
	
	
	
	
}
