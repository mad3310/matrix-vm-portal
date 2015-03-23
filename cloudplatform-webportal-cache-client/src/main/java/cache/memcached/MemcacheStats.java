/*
 * @Title: MemcacheStats.java
 * @Package com.letv.mms.cache.client.memcached
 * @Description: TODO
 * @author chenguang 
 * @date 2012-12-18 下午6:29:56
 * @version V1.0
 *
 * Modification History:  
 * Date         Author      Version     Description  
 * -------------------------------------------------------------- 
 * 2012-12-18                          
 */
package cache.memcached;


/**
 * Memcache通过Stat获得的统计信息
 * @author wenchu.cenwc<wenchu.cenwc@alibaba-inc.com>
 *
 */
@SuppressWarnings("serial")
public class MemcacheStats implements java.io.Serializable
{
	private String serverHost;
	private String statInfo;
	
	public String getServerHost()
	{
		return serverHost;
	}
	public void setServerHost(String serverHost)
	{
		this.serverHost = serverHost;
	}
	public String getStatInfo()
	{
		return statInfo;
	}
	public void setStatInfo(String statInfo)
	{
		this.statInfo = statInfo;
	}

}

