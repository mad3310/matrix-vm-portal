/*
 * @Title: MemcachedException.java
 * @Package com.letv.mms.cache.client.memcached
 * @Description: TODO
 * @author chenguang 
 * @date 2012-12-18 下午6:28:33
 * @version V1.0
 *
 * Modification History:  
 * Date         Author      Version     Description  
 * -------------------------------------------------------------- 
 * 2012-12-18                          
 */
package cache.memcached;

/**
 * Memcached 内部错误定义
 * @author wenchu.cenwc
 *
 */
@SuppressWarnings("serial")
public class MemcachedException extends RuntimeException
{
	public MemcachedException() 
	{
		super();
	}

	public MemcachedException(String message) 
	{
		super(message);
	}


	public MemcachedException(String message, Throwable cause) 
	{
	   super(message, cause);
	}

	public MemcachedException(Throwable cause) 
	{
	   super(cause);
	}
}

