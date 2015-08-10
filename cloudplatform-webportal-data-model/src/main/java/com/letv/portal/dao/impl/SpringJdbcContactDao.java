/**
 * Created on Oct 7, 2011
 */
package com.letv.portal.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import com.letv.common.dao.QueryParam;
import com.letv.portal.dao.IContactDao;
import com.letv.portal.model.Contact;
import com.letv.portal.model.SearchCriteria;

/**
 * @author Clarence
 *
 */
public class SpringJdbcContactDao implements IContactDao, InitializingBean {

	private JdbcTemplate jdbcTemplate;
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	private DataSource dataSource;

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	public void afterPropertiesSet() throws Exception {
		if (dataSource == null) {
			throw new BeanCreationException("Must set dataSource on ContactDao");
		}
	}

	public List<Contact> findAll() {
		String sql = "select id, first_name, last_name, birth_date from CONTACT";
		return jdbcTemplate.query(sql, new ContactMapper());	
	}



	public String findFirstNameById(Long id) {
		String firstName = jdbcTemplate.queryForObject(
				"select first_name from CONTACT where id = ?",
				new Object[]{id}, String.class);
		return firstName;
	}

	public String findLastNameById(Long id) {
		String sql = "select last_name from CONTACT where id = :contactId";
		
		SqlParameterSource namedParameters = new MapSqlParameterSource("contactId", id);
		//Map<String, Object> namedParameters = new HashMap<String, Object>();
		//namedParameters.put("contactId", id);
		return namedParameterJdbcTemplate.queryForObject(sql, namedParameters, String.class);
	}

	public List<Contact> findByFirstName(String firstName) {
		// TODO Auto-generated method stub
		return null;
	}

	public void insert(Contact contact) {
		// TODO Auto-generated method stub
		
	}

	public void update(Contact contact) {
		// TODO Auto-generated method stub
		
	}

	public void delete(Long contactId) {
		// TODO Auto-generated method stub
		
	}	
	
	public void insertWithDetail(Contact contact) {
		// TODO Auto-generated method stub
		
	}

	protected void retrieveIdentity(final Contact contact) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(getDataSource()); 
		contact.setId(jdbcTemplate.queryForLong("select last_insert_id()")); 
	}
	
	private static final class ContactMapper implements RowMapper<Contact> {

		public Contact mapRow(ResultSet rs, int rowNum) throws SQLException {

			Contact contact = new Contact();
			contact.setId(rs.getLong("id"));
			contact.setFirstName(rs.getString("first_name"));
			contact.setLastName(rs.getString("last_name"));
			contact.setBirthDate(rs.getDate("birth_date"));
			return contact;
		}
		
	}

	@Override
	public List<Contact> findAllWithDetail() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Contact findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Contact> findByFirstNameAndLastName(SearchCriteria criteria) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insertContact(Contact contact) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateContact(Contact contact) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteContact(Long id) {
		// TODO Auto-generated method stub
		
	}

	
	

}
