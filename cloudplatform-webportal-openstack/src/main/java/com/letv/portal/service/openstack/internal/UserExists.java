package com.letv.portal.service.openstack.internal;

import com.letv.portal.service.openstack.exception.OpenStackException;
import com.letv.portal.service.openstack.util.Contants;
import com.letv.portal.service.openstack.util.Params;
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

import java.io.InputStream;
import java.io.StringWriter;

/**
 * Created by zhouxianguang on 2015/6/11.
 */
@SuppressWarnings("deprecation")
public class UserExists {

    private static final Logger logger = LoggerFactory.getLogger(UserExists.class);

    private final String endpoint;
    private final String userName;
    private final String password;

    public UserExists(String publicEndpoint, String userName, String password) {
        this.endpoint = publicEndpoint;
        this.userName = userName;
        this.password = password;
    }

    @SuppressWarnings("resource")
    public boolean run() throws OpenStackException {
        boolean status = false;
        HttpClient client = new DefaultHttpClient();
        try {
            HttpPost req = new HttpPost(this.endpoint
                    + "tokens");

            req.addHeader("User-Agent", Contants.OPEN_STACK_USER_AGENT);
            req.addHeader("Content-Type", "application/json");
            req.addHeader("Accept", "application/json");

            Params body = new Params();
            Params body_Auth = new Params();
            body.p("auth", body_Auth);
            Params body_Auth_PasswordCredentials = new Params();
            body_Auth.p("passwordCredentials", body_Auth_PasswordCredentials);
            body_Auth_PasswordCredentials.p("username", this.userName).p("password", this.password);
            body_Auth.p("tenantName", this.userName);

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
            } else if (statusCode == 401) {
                status = false;
            } else {
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
        return status;
    }
}
