/*
 * @Title: MemcachedClientConfig.java
 * @Package com.letv.mms.cache.client.memcached
 * @Description: TODO
 * @author chenguang 
 * @date 2012-12-18 下午6:25:36
 * @version V1.0
 *
 * Modification History:  
 * Date         Author      Version     Description  
 * -------------------------------------------------------------- 
 * 2012-12-18                          
 */
package cache.memcached;

/**
 * Memcache的客户端配置
 * @author wenchu.cenwc<wenchu.cenwc@alibaba-inc.com>
 *
 */
public class MemcachedClientConfig
{
	private String name;
	/**
	 * 是否需要压缩
	 */
	private boolean compressEnable;
	/**
	 * 默认编码方式UTF-8
	 */
	private String defaultEncoding;
	/**
	 * 处理错误的类名，需要全路径
	 */
	private String errorHandler;
	/**
	 * 客户端对应的SocketIOPool
	 */
	private String socketPool;

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public boolean isCompressEnable()
	{
		return compressEnable;
	}

	public void setCompressEnable(boolean compressEnable)
	{
		this.compressEnable = compressEnable;
	}

	public String getDefaultEncoding()
	{
		return defaultEncoding;
	}

	public void setDefaultEncoding(String defaultEncoding)
	{
		this.defaultEncoding = defaultEncoding;
	}

	public String getErrorHandler()
	{
		return errorHandler;
	}

	public void setErrorHandler(String errorHandler)
	{
		this.errorHandler = errorHandler;
	}

	public String getSocketPool()
	{
		return socketPool;
	}

	public void setSocketPool(String socketPool)
	{
		this.socketPool = socketPool;
	}
	
}

