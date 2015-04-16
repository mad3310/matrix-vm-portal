/**
 * Created on Oct 24, 2011
 */
package com.letv.portal.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.letv.portal.model.Contact;
import com.letv.portal.model.ContactHobbyDetail;
import com.letv.portal.model.ContactTelDetail;
import com.letv.portal.model.Hobby;
import com.letv.portal.model.SearchCriteria;
import com.letv.portal.dao.IContactHobbyDetailDao;
import com.letv.portal.dao.IContactDao;
import com.letv.portal.dao.IContactTelDetailDao;
import com.letv.portal.service.cbase.ContactService;

/**
 * @author liyunhui
 *
 */
@Service("contactService")
@Repository
@Transactional
public class ContactServiceImpl implements ContactService {

	private Log log = LogFactory.getLog(ContactServiceImpl.class);

	@Resource
	private IContactDao contactDao;

	@Resource
	private IContactTelDetailDao contactTelDetailDao;

	@Resource
	private IContactHobbyDetailDao contactHobbyDetailDao;

	@Transactional(readOnly = true)
	public List<Contact> findAll() {
		List<Contact> contacts = contactDao.findAll();
		return contacts;
	}

	@Transactional(readOnly = true)
	public List<Contact> findAllWithDetail() {
		List<Contact> contacts = contactDao.findAllWithDetail();
		for (Contact contact : contacts) {
			populateContactTelDetail(contact);
		}
		return contacts;
	}

	@Transactional(readOnly = true)
	public Contact findById(Long id) {
		Contact contact = contactDao.findById(id);
		populateContactTelDetail(contact);
		return contact;
	}

	public Contact save(Contact contact) {
		if (contact.getId() == null) {
			insert(contact);
		} else {
			update(contact);
		}
		return contact;
	}

	private Contact insert(Contact contact) {
		contactDao.insertContact(contact);
		Long contactId = contact.getId();
		ContactHobbyDetail contactHobbyDetail;
		if (contact.getContactTelDetails() != null) {
			for (ContactTelDetail contactTelDetail : contact
					.getContactTelDetails()) {
				contactTelDetail.setContact(contact);
				contactTelDetailDao.insertContactTelDetail(contactTelDetail);
			}
		}
		if (contact.getHobbies() != null) {
			for (Hobby hobby : contact.getHobbies()) {
				contactHobbyDetail = new ContactHobbyDetail();
				contactHobbyDetail.setContactId(contactId);
				contactHobbyDetail.setHobbyId(hobby.getHobbyId());
				contactHobbyDetailDao
						.insertContactHobbyDetail(contactHobbyDetail);
			}
		}
		return contact;
	}

	private Contact update(Contact contact) {
		contactDao.updateContact(contact);
		Long contactId = contact.getId();
		ContactHobbyDetail contactHobbyDetail;

		// List storing orphan ids of contact tel details
		List<Long> ids = new ArrayList<Long>();

		// Retrieve existing telephones for contact
		List<ContactTelDetail> oldContactTelDetails = contactTelDetailDao
				.selectTelDetailForContact(contactId);
		for (ContactTelDetail contactTelDetail : oldContactTelDetails) {
			ids.add(contactTelDetail.getId());
		}

		// Update telephone details
		if (contact.getContactTelDetails() != null) {
			for (ContactTelDetail contactTelDetail : contact
					.getContactTelDetails()) {
				if (contactTelDetail.getId() == null) {
					contactTelDetailDao
							.insertContactTelDetail(contactTelDetail);
				} else {
					contactTelDetailDao
							.updateContactTelDetail(contactTelDetail);
					ids.remove(contactTelDetail.getId());
				}
			}
			if (ids.size() > 0) {
				contactTelDetailDao.deleteOrphanContactTelDetail(ids);
			}
		}

		// Update hobby details
		contactHobbyDetailDao.deleteHobbyDetailForContact(contact.getId());
		if (contact.getHobbies() != null) {
			for (Hobby hobby : contact.getHobbies()) {
				contactHobbyDetail = new ContactHobbyDetail();
				contactHobbyDetail.setContactId(contactId);
				contactHobbyDetail.setHobbyId(hobby.getHobbyId());
				contactHobbyDetailDao
						.insertContactHobbyDetail(contactHobbyDetail);
			}
		}

		return contact;
	}

	public void delete(Contact contact) {
		Long contactId = contact.getId();
		contactTelDetailDao.deleteTelDetailForContact(contactId);
		contactHobbyDetailDao.deleteHobbyDetailForContact(contactId);
		contactDao.deleteContact(contactId);
	}

	@Transactional(readOnly = true)
	public List<Contact> findByFirstNameAndLastName(String firstName,
			String lastName) {
		log.info("Finding contact with first name: " + firstName
				+ " and last name: " + lastName);
		Contact contact = new Contact();
		contact.setFirstName(firstName);
		contact.setLastName(lastName);
		SearchCriteria criteria = new SearchCriteria();
		criteria.setFirstName(firstName);
		criteria.setLastName(lastName);

		List<Contact> contacts = contactDao
				.findByFirstNameAndLastName(criteria);
		for (Contact contactTemp : contacts) {
			populateContactTelDetail(contactTemp);
		}

		return contacts;
	}

	private void populateContactTelDetail(Contact contact) {
		if (contact.getContactTelDetails() != null) {
			for (ContactTelDetail contactTelDetail : contact
					.getContactTelDetails()) {
				contactTelDetail.setContact(contact);
			}
		}
	}

}
