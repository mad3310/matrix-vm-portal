package com.letv.portal.service.openstack.internal;

import java.io.InputStream;
import java.io.StringWriter;

import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.letv.portal.service.openstack.exception.OpenStackException;
import com.letv.portal.service.openstack.util.Params;

@SuppressWarnings("deprecation")
public class UserRegister {

	private static final Logger logger = LoggerFactory
			.getLogger(UserRegister.class);

	private final String endpoint;
	private final String userName;
	private final String password;

	public UserRegister(String endpoint, String userName, String password) {
		this.endpoint = endpoint;
		this.userName = userName;
		this.password = password;
	}

	@SuppressWarnings("resource")
	public void run() throws OpenStackException {
		HttpClient client = new DefaultHttpClient();
		try {
			HttpPost req = new HttpPost(this.endpoint
					+ "OS-KSREG/register/project/user");

			req.addHeader("User-Agent", "python-keystoneclient");
			req.addHeader("Content-Type", "application/json");
			req.addHeader("Accept", "application/json");
			req.addHeader("X-Auth-Token", "REGISTER");

			Params body = new Params();
			Params bodyUser = new Params();
			body.p("user", bodyUser);
			bodyUser.p("enabled", true).p("name", this.userName)
					.p("password", password).p("description", null);
			Params bodyTenant = new Params();
			body.p("tenant", bodyTenant);
			bodyTenant.p("enabled", true).p("name", this.userName)
					.p("description", null);

			ObjectMapper objectMapper = new ObjectMapper();
			StringWriter stringWriter = new StringWriter();
			JsonGenerator jsonGenerator = objectMapper.getJsonFactory()
					.createJsonGenerator(stringWriter);
			objectMapper.writeValue(jsonGenerator, body);
			req.setEntity(new StringEntity(stringWriter.toString(), HTTP.UTF_8));

			HttpResponse resp = client.execute(req);
			int statusCode = resp.getStatusLine().getStatusCode();
			if (statusCode != 200) {
				try {
					HttpEntity httpEntity = resp.getEntity();
					if (httpEntity != null) {
						String contentEncoding = "UTF-8";
						Header contentEncodingHeader = httpEntity
								.getContentEncoding();
						if (contentEncodingHeader != null) {
							String contentEncodingHeaderValue = contentEncodingHeader
									.getValue();
							if (contentEncodingHeaderValue != null) {
								contentEncoding = contentEncodingHeaderValue;
							}
						}

						InputStream rcis = httpEntity.getContent();
						try {
							logger.error(IOUtils
									.toString(rcis, contentEncoding));
						} finally {
							rcis.close();
						}
					}
				} catch (Exception ex) {
					logger.error(ex.getMessage(), ex);
				}

				throw new OpenStackException("OpenStack response status code:"
						+ Integer.toString(statusCode));
			}
		} catch (OpenStackException ose) {
			throw ose;
		} catch (Exception ex) {
			throw new OpenStackException(ex);
		} finally {
			client.getConnectionManager().shutdown();
		}
	}
}
