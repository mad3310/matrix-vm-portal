package com.letv.lcp.openstack.util.internal;

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
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

import com.letv.lcp.openstack.constants.Constants;
import com.letv.lcp.openstack.exception.OpenStackException;
import com.letv.lcp.openstack.util.HttpUtil;
import com.letv.lcp.openstack.util.Params;


/**
 * Created by zhouxianguang on 2015/6/11.
 */
@SuppressWarnings("deprecation")
public class UserExists {

//	private static final Logger logger = LoggerFactory
//			.getLogger(UserExists.class);

	private final String endpoint;
	private final String tenantName;
	private final String password;

	private String tenantId;

//	public UserExists(String endpoint, OpenStackTenant tenant){
//		this(endpoint,tenant.tenantName,tenant.password);
//	}

	public UserExists(String endpoint, String tenantName, String password) {
		this.endpoint = endpoint;
		this.tenantName = tenantName;
		this.password = password;
	}

	@SuppressWarnings("resource")
	public boolean run() throws OpenStackException {
		boolean status = false;

		if (this.tenantName == null) {
			throw new OpenStackException("OpenStack user name is null.", "后台错误");
		} else if (this.tenantName.isEmpty()) {
			throw new OpenStackException(
					"OpenStack user name is an empty string.", "后台错误");
		}

		HttpClient client = new DefaultHttpClient();
		try {
			HttpPost req = new HttpPost(this.endpoint + "tokens");

			req.addHeader("User-Agent", Constants.OPEN_STACK_USER_AGENT);
			req.addHeader("Content-Type", "application/json");
			req.addHeader("Accept", "application/json");

			Params body = new Params();
			Params body_Auth = new Params();
			body.p("auth", body_Auth);
			Params body_Auth_PasswordCredentials = new Params();
			body_Auth.p("passwordCredentials", body_Auth_PasswordCredentials);
			body_Auth_PasswordCredentials.p("username", this.tenantName).p(
					"password", this.password);
			body_Auth.p("tenantName", this.tenantName);

			ObjectMapper objectMapper = new ObjectMapper();
			StringWriter stringWriter = new StringWriter();
			JsonGenerator jsonGenerator = objectMapper.getJsonFactory()
					.createJsonGenerator(stringWriter);
			objectMapper.writeValue(jsonGenerator, body);
			req.setEntity(new StringEntity(stringWriter.toString(), HTTP.UTF_8));

			HttpResponse resp = client.execute(req);
			int statusCode = resp.getStatusLine().getStatusCode();
			if (statusCode == 200) {
				status = true;

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
						String rcs = IOUtils.toString(rcis, contentEncoding);
						JsonNode rootJsonNode = objectMapper.readTree(rcs);
						this.tenantId = rootJsonNode.get("access").get("token")
								.get("tenant").get("id").getTextValue();
					} finally {
						rcis.close();
					}
				}
			} else if (statusCode == 401) {
				status = false;
			} else {
				HttpUtil.throwExceptionOfResponse(resp);
			}
		} catch (OpenStackException ose) {
			throw ose;
		} catch (Exception ex) {
			throw new OpenStackException("后台服务异常", ex);
		} finally {
			client.getConnectionManager().shutdown();
		}
		return status;
	}

	public String getTenantId() {
		return tenantId;
	}
}
