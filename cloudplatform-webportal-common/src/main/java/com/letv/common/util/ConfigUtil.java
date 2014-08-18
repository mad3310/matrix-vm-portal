package com.letv.common.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Logger;

import org.slf4j.LoggerFactory;

/**Program Name: ConfigUtil <br>
 * Description:  配置文件读取类<br>
 * @author name: liuhao1 <br>
 * Written Date: 2014年8月18日 <br>
 * Modified By: <br>
 * Modified Date: <br>
 */
public class ConfigUtil {
	
	private static String FSP = System.getProperty("file.separator");
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ConfigUtil.class);
	
	private static Properties systemProperties;
	
	/**
	 * Methods Name: 通过key获取系统消息 <br>
	 * Description: 
	 * @author name: fxfeiyi
	 * @param key 国际化消息key
	 * @return 返回的消息value
	 */
	public static String getSysMessage(String key) {
		if (systemProperties == null) {
			loadSystemProperties();
		}
		return systemProperties.getProperty(key);
	}
	
	
	
	/**
	 * Methods Name: 通过key和默认值获取系统消息 <br>
	 * Description: 
	 * @author name: fxfeiyi
	 * @param key 国际化消息key
	 * @param defaultValue 默认值
	 * @return 返回的消息value
	 */
	public static String getSysMessage(String key, String defaultValue) {
		if (systemProperties == null) {
			loadSystemProperties();
		}
		return systemProperties.getProperty(key, defaultValue);
	}
	
	
	/**
	 * Methods Name: 通过文件路径，消息键值对集合，注释信息 追加或修改国际化信息 <br>
	 * Description: 该方法为平台内部使用
	 * @author name: fxfeiyi
	 * @param filePath 文件路径
	 * @param keyValueProps 消息键值对集合
	 * @param messageComment 注释信息 
	 * @return 返回的消息value
	 */
	public static String writeMessage(String filePath, Map<String,String[]> keyValueProps, Boolean isOverwrite) {

		String strResult = "";
		String sourceFile = filePath; // write file with path and name
		String tempFile = "";
		
		File file_jdbc = new File(sourceFile);
		
		String fileDir = file_jdbc.getParent();
		tempFile = fileDir + FSP + "temp.properties";
		
		File file_temp = new File(tempFile);
			
		try {
			
			BufferedReader buffReaderSource = null;
			
			try {
				buffReaderSource = new BufferedReader(new InputStreamReader(new FileInputStream(sourceFile), "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			Writer fw_temp = null;
			PrintWriter pw_temp = null;
			try {
				fw_temp =new BufferedWriter(new OutputStreamWriter(new FileOutputStream(tempFile),"UTF-8"));   
				//FileWriter fw_temp = new FileWriter(tempFile);
				pw_temp = new PrintWriter(fw_temp);
				
				String strLineSource = buffReaderSource.readLine();
				if(strLineSource!=null) 
					strLineSource = strLineSource.trim();
					
				while (strLineSource != null) {
					// identify if strLine have title,have change key
					Set<String> propsKey = keyValueProps.keySet();
					Iterator<String> propItera = propsKey.iterator();
					while(propItera.hasNext()){
						String msgKey = propItera.next();
						if(msgKey!=null){
							String[] msgValue = keyValueProps.get(msgKey);
							if (strLineSource.startsWith(msgKey)) { //如果原来存在这个key，用最新的value替换掉其旧的value
								strLineSource = msgKey + "=" + msgValue[0];
								propItera.remove();
							}
						}
					}
					pw_temp.write(strLineSource);
					pw_temp.println();
					//pw_temp.flush(); //为了效率这里注释，最后统一flush
					// read next line
					strLineSource = buffReaderSource.readLine();
				}
				//if not exits add one line message
//				if (!strTemp.equals("1")) {
//					if(messageComment!=null){
//						pw_temp.println();
//						pw_temp.write("#"+ messageComment +"表的元数据信息");
//						pw_temp.println();
//					} 
//					pw_temp.write(msgKey + "=" + msgValue);
//					pw_temp.println();
//					pw_temp.flush();
//				}
				
				//pw_temp.println();
				for(Map.Entry<String, String[]> entry : keyValueProps.entrySet()){
					String msgKey = entry.getKey();
					String[] msgValue = entry.getValue();
					//if(msgKey!=null&& !msgKey.startsWith("#")){
						
					if("br_and_comment".equals(msgValue[1])){
						pw_temp.println();
//						pw_temp.write("#"+ msgValue[0] +"表的元数据信息"); new add
//						pw_temp.println();new add
					}
					
					if(!msgKey.startsWith("#")){
						strLineSource = msgKey + "=" + msgValue[0];
					}else{
						strLineSource = "#" + msgValue[0];
					}
					
					pw_temp.write(strLineSource); 
					pw_temp.println();
//					}else{
//						pw_temp.println();
//						pw_temp.write("#"+ msgValue[0]);
//						pw_temp.println();
//					}
					
					
				}
				//end
				pw_temp.flush(); //上面注释代码一道此处
				
				// close BufferedReader object
				// close file
				//fileReader_jdbc.close();
				// delete properties file
				if (file_jdbc.exists()) {
					if (!file_jdbc.delete()) {
						return "error";
					}
				}
				// rename temp file to properties file
				if (!file_temp.exists()) {
					return "error";
				}
				
				file_temp.renameTo(file_jdbc);
//				if (!strTemp.equals("1")) { //注释掉
//					// there is no title prop exit so modify failed
//					strResult = "error";
//				}
				return strResult;
			} catch (IOException ex2) {
				ex2.printStackTrace();
				strResult = "error";
				return strResult;

			} finally{
				ConfigUtil.close(fw_temp, pw_temp, buffReaderSource);
			}
		} catch (FileNotFoundException ex1) {
			ex1.printStackTrace();

			strResult = "error";
			return strResult;
		}

	}
	/*
	//将中文转换Unicode编码
	public static String convertToUnicode(String s) {
		String unicode = "";
	    char[] charary = new char[s.length()];
	    for(int i=0; i<charary.length; i++) {
	      charary[i] = (char)s.charAt(i);
	       unicode+="\\u" + Integer.toString(charary[i], 16);
	     }
	    return unicode;
	  }
	  */
	
//	public static String format(String source,Object[] arg){
//		MessageFormat formatter = new MessageFormat("");
//		formatter.setLocale(Locale.CHINA);
//		String value = "";
//		try{
//			value = formatter.format(source,arg);
//		}catch(Exception e){
//			value = source;
//		}
//		
//		return value;
//	}
//	
	/*String message = "";
	for(String basename : propertyFileI18nPrefixs){
		ResourceBundle bundle = ResourceBundle.getBundle(basename, Locale.CHINA, MessageUtil.class.getClassLoader() );
		try {
			message = bundle.getString(key);
		} catch (Exception e) {
		}
		if(StringUtil.isNotNullOrEmpty(message)) break; 
	}
	return (message != null && !message.equals("") ? message: defaultValue);*/
	
	public static void close(Closeable... closeAble) {
		if (closeAble == null)
			return;
		List<Closeable> asList = Arrays.asList(closeAble);
		close(asList.iterator());
	}
	
	private static void close(Iterator<Closeable> iter) {
		if (iter.hasNext()) {
			try {
				Closeable closeable = iter.next();
				if (closeable != null)
					closeable.close();
			} catch (IOException e) {
			} finally {
				close(iter);
			}
		}
	}
	
	private static void loadSystemProperties() {
		InputStream is = null;
		try{
			systemProperties = new Properties();
			is = ContextHolder.getApplicationContext().getResource("classpath:properties/sysconfig.properties").getInputStream();
			systemProperties.load(is);
		} catch(IOException e){
			throw new RMPRuntimeException("加载classpath*:properties/sysconfig.properties文件错误：" , e);
		} finally {
			if(is != null) {
				try{
					is.close();
				} catch(IOException e) {
					LOGGER.info(e.getMessage());
				}
			}
		}
	}
}
