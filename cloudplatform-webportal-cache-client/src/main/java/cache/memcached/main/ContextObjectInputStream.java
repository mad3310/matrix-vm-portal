/*
 * @Title: ContextObjectInputStream.java
 * @Package com.letv.mms.cache.client.memcached.client
 * @Description: TODO
 * @author chenguang 
 * @date 2012-12-18 下午6:17:13
 * @version V1.0
 *
 * Modification History:  
 * Date         Author      Version     Description  
 * -------------------------------------------------------------- 
 * 2012-12-18                          
 */
package cache.memcached.main;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;

public class ContextObjectInputStream extends ObjectInputStream { 

	ClassLoader mLoader;
    
	public ContextObjectInputStream( InputStream in, ClassLoader loader ) throws IOException, SecurityException {
		super( in );
		mLoader = loader;
	}
	
	@SuppressWarnings("all")
	protected Class resolveClass( ObjectStreamClass v ) throws IOException, ClassNotFoundException {
		if ( mLoader == null )
			return super.resolveClass( v );
		else
			return Class.forName( v.getName(), true, mLoader );
	}
}
