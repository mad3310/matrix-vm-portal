package com.letv.portal.fixedPush.impl;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.letv.common.exception.TaskExecuteException;
import com.letv.common.util.HttpClient;
import com.letv.portal.fixedPush.IFixedPushService;
import com.letv.portal.model.ContainerModel;
import com.letv.portal.model.ContainerPush;
import com.letv.portal.model.fixed.FixedPushModel;



/**
 * Program Name: FixedPush <br>
 * Description:  与固资系统交互实现接口
 * @author name: wujun <br>
 * Written Date: 2014年10月14日 <br>
 * Modified By: <br>
 * Modified Date: <br>
 */
@Service("fixedPushService")
public class FixedPushServiceImpl implements IFixedPushService{
	
	
	private final static Logger logger = LoggerFactory.getLogger(FixedPushServiceImpl.class);
	
	@Value("${fixedpush.url}")
	private String FIXEDPUSH_GET;
	@Value("${fixedpush.url.ip}")
	private String FIXEDPUSH_SOCKET_IP;
	@Value("${fixedpush.url.port}")
	private int FIXEDPUSH_SOCKET_PORT;
	/**
	 * Methods Name: createMutilContainerPushFixedInfo <br>
	 * Description: 备案多个container<br>
	 * @author name: wujun
	 * @param fixedPushModel
	 */
	public Boolean createMutilContainerPushFixedInfo(List<ContainerModel> containers){
		Boolean flag = false;
		try {
			for(ContainerModel c:containers){
				String servertag = c.getHostIp();
				List<ContainerPush> list = new ArrayList<ContainerPush>();	
				FixedPushModel fixedPushModel = new FixedPushModel();
				ContainerPush containerMode = new ContainerPush();
				containerMode.setName(c.getContainerName());
				containerMode.setIp(c.getIpAddr());
				list.add(containerMode);
				fixedPushModel.setServertag(servertag);	
				fixedPushModel.setType("add");
				fixedPushModel.setIpaddress(list);
				createContainerPushFixedInfo(fixedPushModel);
			}
		} catch (Exception e) {
			throw new TaskExecuteException("push create fixed exception" + e.getMessage());
		}
		flag = true;
		
         return flag;
	}
	@Override
	public Boolean deleteMutilContainerPushFixedInfo(List<ContainerModel> containers){
		Boolean flag = true;
		try {
			for(ContainerModel c:containers){
				String servertag = c.getHostIp();
				List<ContainerPush> list = new ArrayList<ContainerPush>();	
				FixedPushModel fixedPushModel = new FixedPushModel();
				ContainerPush containerMode = new ContainerPush();
				containerMode.setName(c.getContainerName());
				containerMode.setIp(c.getIpAddr());
				list.add(containerMode);
				fixedPushModel.setServertag(servertag);	
				fixedPushModel.setType("delete");
				fixedPushModel.setIpaddress(list);
				createContainerPushFixedInfo(fixedPushModel);
			}
		} catch (Exception e) {
			throw new TaskExecuteException("push delete fixed exception" + e.getMessage());
		}
		return flag;
	}
	/**
	 * Methods Name: createContainerPushFixedInfo <br>
	 * Description: 创建container的相关系统<br>
	 * @author name: wujun
	 */
	public void createContainerPushFixedInfo(FixedPushModel fixedPushModel)throws Exception{
			String result = sendFixedInfo(fixedPushModel);		
	}
	
	/**
	 * Methods Name: deleteContainerPushFixedInfo <br>
	 * Description: 删除container的相关信息<br>
	 * @author name: wujun
	 */
	public Boolean deleteContainerPushFixedInfo(FixedPushModel fixedPushModel)throws Exception{
		String result = sendFixedInfo(fixedPushModel);
		return null;
	}	
	
	@Override
	public String sendFixedInfo(FixedPushModel fixedPushModel)throws Exception{	
	    String sn =	receviceFixedInfo(fixedPushModel);
	    fixedPushModel.setServertag(sn);
	    String pushString =  JSON.toJSONString(fixedPushModel);
	    sendSocket(pushString);
        return null;
	}
 
	@Override
	public String receviceFixedInfo(FixedPushModel fixedPushModel) throws Exception{
		if(fixedPushModel!=null){
			String hostIp = fixedPushModel.getServertag();
			String url = FIXEDPUSH_GET+hostIp;
			String sn=HttpClient.get(url);			
			return sn;
		}else {
			return null;
		}      
	}

	public void sendSocket(String pushString) throws IOException{
        Socket s1 = new Socket(FIXEDPUSH_SOCKET_IP, FIXEDPUSH_SOCKET_PORT);
	    InputStream is = s1.getInputStream();
	    DataInputStream dis = new DataInputStream(is);
	    OutputStream os = s1.getOutputStream();	
		try{
			if(null == pushString ||"".equals(pushString)){
			}else{
			os.write(int2byte(pushString.getBytes().length));
			os.write(pushString.getBytes());
			}
            os.flush();            
        } catch (Exception e) {
           logger.debug("socket发送出错");
        }finally{
        	 dis.close();
        	 s1.close();
        };
	}
    private static byte[] int2byte(int i) {
        return new byte[] { (byte) ((i >> 24) & 0xFF),
                (byte) ((i >> 16) & 0xFF), (byte) ((i >> 8) & 0xFF),
                (byte) (i & 0xFF) };
    }

}
