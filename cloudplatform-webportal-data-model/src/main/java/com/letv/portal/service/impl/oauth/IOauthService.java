package com.letv.portal.service.impl.oauth;

import java.util.Map;


/**Program Name: OauthLoginApi <br>
 * Description:  提供基础的oauth api服务<br>
 * @author name: howie <br>
 * Written Date: 2015年7月15日 <br>
 * Modified By: <br>
 * Modified Date: <br>
 */
public interface IOauthService {
	
	public String getAuthorize(String clientId);
	public String getAccessToken(String clientId,String clientSecret,String code);
	public Map<String,Object> getUserdetailinfo(String accessToken);
	
}