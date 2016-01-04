package com.letv.lcp.openstack.util.internal;

import java.io.StringWriter;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;

import com.letv.lcp.openstack.constants.Constants;
import com.letv.lcp.openstack.exception.OpenStackException;
import com.letv.lcp.openstack.util.HttpUtil;
import com.letv.lcp.openstack.util.Params;


@SuppressWarnings("deprecation")
public class UserRegister {

//    private static final Logger logger = LoggerFactory
//            .getLogger(UserRegister.class);

    private final String endpoint;
    private final String registerToken;
    private final String tenantName;
    private final String password;
    private final String email;

//    public UserRegister(String endpoint,String registerToken, OpenStackTenant tenant){
//        this(endpoint, registerToken, tenant.tenantName, tenant.password, tenant.email);
//    }

    public UserRegister(String endpoint, String registerToken, String tenantName, String password, String email) {
        this.endpoint = endpoint;
        this.registerToken = registerToken;
        this.tenantName = tenantName;
        this.password = password;
        this.email = email;
    }

    @SuppressWarnings("resource")
    public void run() throws OpenStackException {
        HttpClient client = new DefaultHttpClient();
        try {
            HttpPost req = new HttpPost(this.endpoint
                    + "OS-KSREG/register/project/user");

            req.addHeader("User-Agent", Constants.OPEN_STACK_USER_AGENT);
            req.addHeader("Content-Type", "application/json");
            req.addHeader("Accept", "application/json");
            req.addHeader("X-Auth-Token", this.registerToken);

            Params body = new Params();
            Params bodyUser = new Params();
            body.p("user", bodyUser);
            bodyUser.p("enabled", true).p("name", this.tenantName)
                    .p("password", this.password).p("description", "matrix "+this.tenantName).p("email",this.email);
            Params bodyTenant = new Params();
            body.p("tenant", bodyTenant);
            bodyTenant.p("enabled", true).p("name", this.tenantName)
                    .p("description", "matrix " + this.tenantName);

            ObjectMapper objectMapper = new ObjectMapper();
            StringWriter stringWriter = new StringWriter();
            JsonGenerator jsonGenerator = objectMapper.getJsonFactory()
                    .createJsonGenerator(stringWriter);
            objectMapper.writeValue(jsonGenerator, body);
            req.setEntity(new StringEntity(stringWriter.toString(), HTTP.UTF_8));

            HttpResponse resp = client.execute(req);
            int statusCode = resp.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                HttpUtil.throwExceptionOfResponse(resp);
            }
        } catch (OpenStackException ose) {
            throw ose;
        } catch (Exception ex) {
            throw new OpenStackException("后台服务异常",ex);
        } finally {
            client.getConnectionManager().shutdown();
        }
    }
}
