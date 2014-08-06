package com.letv.common.util;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Response处理类
 * 
 * @author Ben Liu
 */
public final class ResponseUtil {

    private static Log logger = LogFactory.getLog(ResponseUtil.class);

    /**
     * Constructor
     */
    private ResponseUtil() {

    }

    /**
     * 使用response.getWriter().write(content)向客户端写数据。
     * 
     * @param res HttpServletResponse
     * @param content content
     */
    public static void write(HttpServletResponse res, String content) {
        PrintWriter writer = null;
        try {
            res.setCharacterEncoding("utf-8");
            writer = res.getWriter();
            writer.write(content);
        } catch (Exception e) {
            logger.error("Exception when response.", e);
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

}
