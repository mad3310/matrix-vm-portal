//package com.letv.portal.dao.impl;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//import com.letv.common.dao.QueryParam;
//import com.letv.portal.dao.ContactDao;
//import com.letv.portal.model.ContactModel;
//
//public class PlainJDBCContactDao implements ContactDao {
//	static {
//		try {
//			Class.forName("com.mysql.jdbc.Driver");
//		} catch (ClassNotFoundException ex) {
//			// noop
//		}
//	}
//
//	private Connection getConnection() throws SQLException {
//		return DriverManager.getConnection(
//				"jdbc:mysql://10.154.28.164:3306/cbasetest?useUnicode=true&amp;characterEncoding=utf-8",
//				"webportal",
//				"webportal");
//	}
//
//	private void closeConnection(Connection connection) {
//		if (connection == null)
//			return;
//		try {
//			connection.close();
//		} catch (SQLException ex) {
//			// noop
//		}
//	}
//
//	public List<ContactModel> findAll() {
//		List<ContactModel> result = new ArrayList<ContactModel>();
//		Connection connection = null;
//
//		try {
//			connection = getConnection();
//			PreparedStatement statement = connection
//					.prepareStatement("select * from CONTACT");
//			ResultSet resultSet = statement.executeQuery();
//			while (resultSet.next()) {
//				ContactModel contact = new ContactModel();
//				contact.setId(resultSet.getLong("id"));
//				contact.setFirstName(resultSet.getString("first_name"));
//				contact.setLastName(resultSet.getString("last_name"));
//				contact.setBirthDate(resultSet.getDate("birth_date"));
//				result.add(contact);
//			}
//		} catch (SQLException ex) {
//			ex.printStackTrace();
//		} finally {
//			closeConnection(connection);
//		}
//		return result;
//	}
//
//	public List<ContactModel> findByFirstName(String firstName) {
//		return null;
//	}
//
//	public void insert(ContactModel contact) {
//		Connection connection = null;
//		try {
//			connection = getConnection();
//			PreparedStatement statement = connection
//					.prepareStatement(
//							"insert into CONTACT (first_name, last_name, birth_date) values (?, ?, ?)",
//							Statement.RETURN_GENERATED_KEYS);
//			statement.setString(1, contact.getFirstName());
//			statement.setString(2, contact.getLastName());
//			statement.setDate(3, contact.getBirthDate());
//			statement.execute();
//			ResultSet generatedKeys = statement.getGeneratedKeys();
//			if (generatedKeys.next()) {
//				contact.setId(generatedKeys.getLong(1));
//			}
//		} catch (SQLException ex) {
//			ex.printStackTrace();
//		} finally {
//			closeConnection(connection);
//		}
//	}
//
//	public void update(ContactModel contact) {
//	}
//
//	public void delete(Long contactId) {
//		Connection connection = null;
//
//		try {
//			connection = getConnection();
//			PreparedStatement statement = connection
//					.prepareStatement("delete from CONTACT where id=?");
//			statement.setLong(1, contactId);
//			statement.execute();
//		} catch (SQLException ex) {
//			ex.printStackTrace();
//		} finally {
//			closeConnection(connection);
//		}
//	}
//
//	@Override
//	public void updateBySelective(ContactModel t) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public void delete(ContactModel t) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public void deleteFlag(Long id) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public ContactModel selectById(Long id) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public Integer selectByModelCount(ContactModel t) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public <K, V> Integer selectByMapCount(Map<K, V> map) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public List<ContactModel> selectByModel(ContactModel t) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public <K, V> List<ContactModel> selectByMap(Map<K, V> map) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public List<ContactModel> selectPageByMap(QueryParam params) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public String findFirstNameById(Long id) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public String findLastNameById(Long id) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//}
