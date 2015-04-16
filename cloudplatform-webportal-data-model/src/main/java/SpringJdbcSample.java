///**
// * Created on Oct 7, 2011
// */
//
//
//import java.util.List;
//
//import org.springframework.context.support.GenericXmlApplicationContext;
//
//import com.letv.portal.dao.ContactDao;
//import com.letv.portal.model.ContactModel;
//
///**
// * @author liyunhui
// *
// */
//public class SpringJdbcSample {
//
//	public static void main(String[] args) {
//		
//		// 使用Spring JDBC支持， 在 app-context-xml.xml配置
//		GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
//		ctx.load("classpath:cbase-context-xml.xml");
//		ctx.refresh();
//		
//		// 从xml注入这个bean com.apress.prospring3.ch8.dao.jdbc.xml.JdbcContactDao
//		ContactDao contactDao = ctx.getBean("contactDao", ContactDao.class);
//		
//		// Find first name by id
//		System.out.println("First name for contact id 1 is: " + contactDao.findFirstNameById(1l));
//	
//		// Find last name by id
//		System.out.println("Last name for contact id 1 is: " + contactDao.findLastNameById(1l));
//				
//		// Find and list all contacts
//		List<ContactModel> contacts = contactDao.findAll();	
//		for (ContactModel contact: contacts) {
//			System.out.println(contact);
////			if (contact.getContactTelDetails() != null) {
////				for (ContactTelDetail contactTelDetail: contact.getContactTelDetails()) {
////					System.out.println("---" + contactTelDetail);
////				}
////			}
//			System.out.println();
//		}
//		
//
//	}
//}
