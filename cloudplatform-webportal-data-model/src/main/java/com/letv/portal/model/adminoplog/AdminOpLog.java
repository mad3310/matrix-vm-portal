package com.letv.portal.model.adminoplog;

import com.letv.common.exception.MatrixException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.annotate.JsonIgnore;

import com.letv.common.model.BaseModel;
import com.letv.portal.model.UserModel;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import java.util.Map;

public class AdminOpLog extends BaseModel {

	private static final long serialVersionUID = -3659064110880622705L;

	private String event;

	private AoLogType logType;

	private String module;

	private String description;

	private UserModel user;

	public AdminOpLog() {
	}

	public AdminOpLog(String event, Long createUser, AoLogType logType,
			String module, String description) {
		this.event = event;
		setCreateUser(createUser);
		this.logType = logType;
		this.module = module;
		this.description = description;
		// setCreateTime(new Timestamp(System.currentTimeMillis()));
	}

	public void setUser(UserModel user) {
		this.user = user;
	}

	public UserModel getUser() {
		return user;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public String getEvent() {
		return event;
	}

	@JsonIgnore
	public AoLogType getLogType() {
		return logType;
	}

	public String getType() {
		if (logType != null) {
			return logType.getName();
		} else {
			return "";
		}
	}

	public void setLogType(AoLogType logType) {
		this.logType = logType;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Map<String,Object> getEventMap(){
		if(this.event!=null&&!this.event.isEmpty()){
			try {
				return new ObjectMapper().readValue(this.event, new TypeReference<Map<String,Object>>() {
                });
			} catch (JsonParseException e) {
			} catch(JsonMappingException e) {
			} catch (Exception e){
                e.printStackTrace();
				throw new MatrixException("后台错误",e);
			}
		}
		return null;
	}
}
