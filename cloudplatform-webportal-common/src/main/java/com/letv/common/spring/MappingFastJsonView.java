package com.letv.common.spring;

import java.io.PrintWriter;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.view.AbstractView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class MappingFastJsonView extends AbstractView {

	public static final String DEFAULT_CONTENT_TYPE = "application/json";

	private boolean prefixJson = false;

	private String encoding = "utf-8";

	private Set<String> modelKeys;

	private boolean extractValueFromSingleKeyModel = false;

	private boolean disableCaching = true;

	public MappingFastJsonView() {
		super();
		setContentType(DEFAULT_CONTENT_TYPE);
		// setExposePathVariables(false);
	}

	public void setPrefixJson(boolean prefixJson) {
		this.prefixJson = prefixJson;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	/**
	 * Set the attribute in the model that should be rendered by this view. When
	 * set, all other model attributes will be ignored.
	 */
	public void setModelKey(String modelKey) {
		this.modelKeys = Collections.singleton(modelKey);
	}

	/**
	 * Set the attributes in the model that should be rendered by this view.
	 * When set, all other model attributes will be ignored.
	 */
	public void setModelKeys(Set<String> modelKeys) {
		this.modelKeys = modelKeys;
	}

	/**
	 * Return the attributes in the model that should be rendered by this view.
	 */
	public Set<String> getModelKeys() {
		return this.modelKeys;
	}

	/**
	 * Set whether to serialize models containing a single attribute as a map or
	 * whether to extract the single value from the model and serialize it
	 * directly.
	 * <p>
	 * The effect of setting this flag is similar to using
	 * {@code MappingJacksonHttpMessageConverter} with an {@code @ResponseBody}
	 * request-handling method.
	 * <p>
	 * Default is {@code false}.
	 */
	public void setExtractValueFromSingleKeyModel(boolean extractValueFromSingleKeyModel) {
		this.extractValueFromSingleKeyModel = extractValueFromSingleKeyModel;
	}

	/**
	 * Disables caching of the generated JSON.
	 * <p>
	 * Default is {@code true}, which will prevent the client from caching the
	 * generated JSON.
	 */
	public void setDisableCaching(boolean disableCaching) {
		this.disableCaching = disableCaching;
	}



	@Override
	protected void prepareResponse(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType(getContentType());
		response.setCharacterEncoding(this.encoding);
		if (this.disableCaching) {
			response.addHeader("Pragma", "no-cache");
			response.addHeader("Cache-Control", "no-cache, no-store, max-age=0");
			response.addDateHeader("Expires", 1L);
		}
	}

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		response.setCharacterEncoding("utf-8");
		Object value = filterModel(model);
		value = filterValue(value);
		out.print(JSON.toJSONString(value, SerializerFeature.WriteMapNullValue));
		out.flush();
		out.close();
	}

	private Object filterValue(Object result) {
		if (!(result instanceof Map)) {
			return result;
		}

		Map map = (Map) result;
		if (map.size() == 1) {
			return map.values().toArray()[0];
		}
		return map;
	}
	/**
	 * Filters out undesired attributes from the given model. The return value
	 * can be either another {@link Map} or a single value object.
	 * <p>
	 * The default implementation removes {@link BindingResult} instances and
	 * entries not included in the {@link #setRenderedAttributes
	 * renderedAttributes} property.
	 * 
	 * @param model
	 *            the model, as passed on to {@link #renderMergedOutputModel}
	 * @return the object to be rendered
	 */
	protected Object filterModel(Map<String, Object> model) {
		Map<String, Object> result = new HashMap<String, Object>(model.size());
		Set<String> renderedAttributes = (!CollectionUtils.isEmpty(this.modelKeys) ? this.modelKeys : model.keySet());
		for (Map.Entry<String, Object> entry : model.entrySet()) {
			if (!(entry.getValue() instanceof BindingResult) && renderedAttributes.contains(entry.getKey())) {
				result.put(entry.getKey(), entry.getValue());
			}
		}
		return (this.extractValueFromSingleKeyModel && result.size() == 1 ? result.values().iterator().next() : result);
	}

}
