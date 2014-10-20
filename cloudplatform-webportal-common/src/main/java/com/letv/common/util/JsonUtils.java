/**
 * 
 */
package com.letv.common.util;

import java.io.ByteArrayOutputStream;

import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.http.converter.HttpMessageNotWritableException;

/**
 * @author <a href="mailto:mengdawei@letv.com">Dawei Meng</a>
 *
 */
public  abstract class JsonUtils {
    public static String writeObject(Object o) throws Exception{
    	ObjectMapper objectMapper = new ObjectMapper();
    	ByteArrayOutputStream out = new ByteArrayOutputStream();
    	JsonEncoding encoding = JsonEncoding.UTF8;
		JsonGenerator jsonGenerator =
				objectMapper.getJsonFactory().createJsonGenerator(System.out, encoding);

		JsonGenerator jsonGenerator2 =
				objectMapper.getJsonFactory().createJsonGenerator(out, encoding);
		
		try {
//			if (true) {
//				jsonGenerator.writeRaw("{} && ");
//			}
			objectMapper.writeValue(jsonGenerator, o);
			objectMapper.writeValue(jsonGenerator2, o);
			
		}
		catch (JsonGenerationException ex) {
			throw new HttpMessageNotWritableException("Could not write JSON: " + ex.getMessage(), ex);
		}
		return out.toString();
    }
}
