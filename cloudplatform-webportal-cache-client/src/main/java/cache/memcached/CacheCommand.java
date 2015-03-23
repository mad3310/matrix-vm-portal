/*
 * @Title: CacheCommand.java
 * @Package com.letv.mms.cache.client.memcached
 * @Description: TODO
 * @author chenguang 
 * @date 2012-12-18 下午6:21:15
 * @version V1.0
 *
 * Modification History:  
 * Date         Author      Version     Description  
 * -------------------------------------------------------------- 
 * 2012-12-18                          
 */
package cache.memcached;

/**
 * @author wenchu.cenwc
 *
 */
public enum CacheCommand 
{
	PUT("put"),
	RECOVER("recover"),
	STORECOUNTER("storeCounter"),
	RECOVERCOUNTER("recoverCounter"),
	ADDORDECR("addOrDecr"),
	ADDORINCR("addOrIncr"),
	DECR("decr"),
	INCR("incr"),
	ADD("add"),
	REPLACE("replace"),
	ANSYPUT("ansyPut"),
	ANSYSTORECOUNTER("ansystoreCounter"),
	ANSYADDORDECR("ansyAddOrDecr"),
	ANSYADDORINCR("ansyAddOrIncr"),
	ANSYDECR("ansyDecr"),
	ANSYINCR("ansyIncr"),
	ANSYADD("ansyAdd"),
	ANSYREPLACE("ansyReplace");
	
	private String v;
	
	CacheCommand(String value)
	{
		v = value;
	}
	
	@Override
	public String toString() {
		return v;
	}
	
}

