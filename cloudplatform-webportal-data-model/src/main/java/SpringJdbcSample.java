/**
 * Created on Oct 7, 2011
 */


import java.util.List;

import org.springframework.context.support.GenericXmlApplicationContext;

import com.letv.portal.dao.IContactDao;
import com.letv.portal.model.Contact;

/**
 * @author liyunhui
 *
 */
public class SpringJdbcSample {

	public static void main(String[] args) {
		
		GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
		ctx.load("classpath:cbase-context-xml.xml");
		ctx.refresh();
		
		IContactDao contactDao = ctx.getBean("contactDao", IContactDao.class);
		
		// Find first name by id
		//System.out.println("First name for contact id 1 is: " + contactDao.findFirstNameById(1l));
	
		// Find last name by id
		//System.out.println("Last name for contact id 1 is: " + contactDao.findLastNameById(1l));
				
		// Find and list all contacts
		List<Contact> contacts = contactDao.findAll();	
		for (Contact contact: contacts) {
			System.out.println(contact);
//			if (contact.getContactTelDetails() != null) {
//				for (ContactTelDetail contactTelDetail: contact.getContactTelDetails()) {
//					System.out.println("---" + contactTelDetail);
//				}
//			}
			System.out.println();
		}
		

	}
}
