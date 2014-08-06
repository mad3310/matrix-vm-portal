package com.letv.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.zip.GZIPInputStream;
import java.util.zip.InflaterInputStream;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 * 从远程接口读取json数据或xml数据用于解析。
 * readContent();方法，从url地址读取字符串，返回。
 * 具备GZIP解析功能.<br>
 * @author zw
 * 
 */
public final class HttpUtil {

	private static final Log log = LogFactory.getLog(HttpUtil.class);

	public static final String readContent(String url) {
		
		String content="";
	    int count=0;
		while(true){
			count++;
			try{
			    content=readContend(url, 800, 800);
			}catch(Exception e){
				content=readContend(url, 800, 800);	//
			}
			if((StringUtils.isNotBlank(content)
					&&!content.equals("-1"))
					||count==5){
				break;
			}
		}
		return content;
	}

	public static final String readContend(String url, int contimeout, int readtimeout) {
		URL url1 = null;
		BufferedReader reader = null;
		HttpURLConnection connection = null;
		try {
			url1 = new URL(url);
			connection = (HttpURLConnection) url1.openConnection();
			connection.setConnectTimeout(contimeout);
			connection.setReadTimeout(readtimeout);
			connection.setRequestProperty("Accept-Encoding", "gzip, deflate");
			connection.connect();

			String contentEncoding = connection.getContentEncoding();// 编码
			InputStream stream;
			if (null != contentEncoding && -1 != contentEncoding.indexOf("gzip")) {
				stream = new GZIPInputStream(connection.getInputStream());
			} else if (null != contentEncoding && -1 != contentEncoding.indexOf("deflate")) {
				stream = new InflaterInputStream(connection.getInputStream());
			} else {
				stream = connection.getInputStream();
			}
			reader = new BufferedReader(new InputStreamReader(stream,"UTF-8"));
			StringBuffer sb = new StringBuffer();
			String line;
			while ((line = reader.readLine()) != null) {
				// System.out.println(line);
				sb.append(line).append("\n");
			}
			reader.close();
			connection.disconnect();
			return sb.toString();
		} catch (IOException e) {
			log.error("url: " + url + ",error:"+e.getMessage());
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
					log.error("读关闭错误", e1);
				}
			}

			return "-1";
		} finally {
			url1 = null;
			if (connection != null)
				connection.disconnect();
		}
	}
}
