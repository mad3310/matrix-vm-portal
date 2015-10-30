package com.letv.portal.util;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.letv.common.exception.CommonException;

import freemarker.template.Template;


/**
 * 消息格式化工具类
 * @author lisuxiao
 *
 */
@Service("messageFormatServiceUtil")
public class MessageFormatServiceUtil {
	
	@Autowired
	private FreeMarkerConfigurer freeMarkerConfigurer;
	
	
	public String format(String ftlName, Map<String, Object> model){
		String text;
  		try {
  			//通过指定模板名获取FreeMarker模板实例
  			Template tpl = freeMarkerConfigurer.getConfiguration().getTemplate(ftlName);
  			text = FreeMarkerTemplateUtils.processTemplateIntoString(tpl,model);
  		} catch (Exception e) {
  			throw new CommonException("值与模版转换发生错误！",e);
  		}
  		return text;
	}
	
}
