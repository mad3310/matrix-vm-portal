package com.letv.common.util;

import org.apache.commons.lang.StringUtils;

/**
 * Created by liuhao1 on 2015/12/7.
 */
public class SessionUtil {
    private static final String BARRIER = "|";

    public static String generateSessionId(String uuid) {
        if(StringUtils.isEmpty(uuid)){
            return "";
        }
        return new StringBuilder(uuid).append(BARRIER).append(System.nanoTime()).toString();
    }

    public static String generateSessionId(String uuid, String client_id,
                                           String client_secret) {
        if (StringUtils.isEmpty(uuid)) {
            return "";
        }
        String realContent = new StringBuilder(uuid).append(BARRIER).append(client_id)
                .append(BARRIER).append(client_secret).append(BARRIER)
                .append(System.nanoTime()).toString();

        return realContent;
    }

    public static final String getUuidBySessionId(String sessionId) {
        if(StringUtils.isEmpty(sessionId)){
            return "";
        }
        return sessionId.substring(0,sessionId.indexOf(BARRIER));
    }

    public static OauthClientIdAndSecret getClientIdAndClientSecretBySessionId(String sessionId) {
        if (StringUtils.isEmpty(sessionId)) {
            return null;
        }
        String[] contents = sessionId.split("\\"+BARRIER);
        return new OauthClientIdAndSecret(contents[1],contents[2]);
    }

    public static class OauthClientIdAndSecret {
        private String client_id;
        private String client_secret;

        public OauthClientIdAndSecret(String client_id, String client_secret) {
            super();
            this.client_id = client_id;
            this.client_secret = client_secret;
        }

        public String getClient_id() {
            return client_id;
        }

        public void setClient_id(String client_id) {
            this.client_id = client_id;
        }

        public String getClient_secret() {
            return client_secret;
        }

        public void setClient_secret(String client_secret) {
            this.client_secret = client_secret;
        }

        @Override
        public String toString() {
            return "OauthClientIdAndSecret [client_id=" + client_id
                    + ", client_secret=" + client_secret + "]";
        }

    }

}
