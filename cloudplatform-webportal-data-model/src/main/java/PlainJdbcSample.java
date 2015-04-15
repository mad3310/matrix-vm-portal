/**
 * Created on Oct 6, 2011
 */

import java.sql.Date;
import java.util.GregorianCalendar;
import java.util.List;

import com.letv.portal.dao.ContactDao;
import com.letv.portal.dao.impl.PlainJDBCContactDao;
import com.letv.portal.model.ContactModel;

/**
 * @author Clarence
 *
 */

public class PlainJdbcSample {

	// 使用纯 JDBC的方式 访问数据库。 每次查询建立连接，很多冗余代码
	private static ContactDao contactDao = new PlainJDBCContactDao();

	public static void main(String[] args) {

		// List all contacts
		System.out.println("Listing initial contact data:");
		listAllContacts();

		System.out.println();

		// Insert a new contact
		System.out.println("Insert a new contact");
		ContactModel contact = new ContactModel();
		contact.setFirstName("Jacky");
		contact.setLastName("Chan");
		contact.setBirthDate(new Date((new GregorianCalendar(2001, 10, 1))
				.getTime().getTime()));
		contactDao.insert(contact);
		System.out.println("Listing contact data after new contact created:");
		listAllContacts();

		System.out.println();

		// Delete the above newly created contact
		System.out.println("Deleting the previous created contact");
		contactDao.delete(contact.getId());
		System.out.println("Listing contact data after new contact deleted:");
		listAllContacts();

	}

	private static void listAllContacts() {

		List<ContactModel> contacts = contactDao.findAll();

		for (ContactModel contact : contacts) {
			System.out.println(contact);
		}
	}

}
