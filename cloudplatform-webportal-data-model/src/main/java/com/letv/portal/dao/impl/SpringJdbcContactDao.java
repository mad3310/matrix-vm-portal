///**
// * Created on Oct 7, 2011
// */
//package com.letv.portal.dao.impl;
//
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import javax.sql.DataSource;
//
//import org.springframework.beans.factory.BeanCreationException;
//import org.springframework.beans.factory.InitializingBean;
//import org.springframework.dao.DataAccessException;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.core.ResultSetExtractor;
//import org.springframework.jdbc.core.RowMapper;
//import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
//import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
//import org.springframework.jdbc.core.namedparam.SqlParameterSource;
//
//import com.letv.common.dao.QueryParam;
//import com.letv.portal.dao.ContactDao;
//import com.letv.portal.model.ContactModel;
//
///**
// * @author Clarence
// *
// */
//public class SpringJdbcContactDao implements ContactDao, InitializingBean {
//
//	private JdbcTemplate jdbcTemplate;
//	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
//	
//	private DataSource dataSource;
//
//	public DataSource getDataSource() {
//		return dataSource;
//	}
//
//	public void setDataSource(DataSource dataSource) {
//		this.dataSource = dataSource;
//		this.jdbcTemplate = new JdbcTemplate(dataSource);
//		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
//	}
//
//	public void afterPropertiesSet() throws Exception {
//		if (dataSource == null) {
//			throw new BeanCreationException("Must set dataSource on ContactDao");
//		}
//	}
//
//	public List<ContactModel> findAll() {
//		String sql = "select id, first_name, last_name, birth_date from CONTACT";
//		return jdbcTemplate.query(sql, new ContactMapper());	
//	}
//
//
//
//	public String findFirstNameById(Long id) {
//		String firstName = jdbcTemplate.queryForObject(
//				"select first_name from CONTACT where id = ?",
//				new Object[]{id}, String.class);
//		return firstName;
//	}
//
//	public String findLastNameById(Long id) {
//		String sql = "select last_name from CONTACT where id = :contactId";
//		
//		SqlParameterSource namedParameters = new MapSqlParameterSource("contactId", id);
//		//Map<String, Object> namedParameters = new HashMap<String, Object>();
//		//namedParameters.put("contactId", id);
//		return namedParameterJdbcTemplate.queryForObject(sql, namedParameters, String.class);
//	}
//
//	public List<ContactModel> findByFirstName(String firstName) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	public void insert(ContactModel contact) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	public void update(ContactModel contact) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	public void delete(Long contactId) {
//		// TODO Auto-generated method stub
//		
//	}	
//	
//	public void insertWithDetail(ContactModel contact) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	protected void retrieveIdentity(final ContactModel contact) {
//		JdbcTemplate jdbcTemplate = new JdbcTemplate(getDataSource()); 
//		contact.setId(jdbcTemplate.queryForLong("select last_insert_id()")); 
//	}
//	
//	private static final class ContactMapper implements RowMapper<ContactModel> {
//
//		public ContactModel mapRow(ResultSet rs, int rowNum) throws SQLException {
//
//			ContactModel contact = new ContactModel();
//			contact.setId(rs.getLong("id"));
//			contact.setFirstName(rs.getString("first_name"));
//			contact.setLastName(rs.getString("last_name"));
//			contact.setBirthDate(rs.getDate("birth_date"));
//			return contact;
//		}
//		
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
//
//}
