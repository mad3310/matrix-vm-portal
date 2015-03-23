/*
 * @Title: NestedIOException.java
 * @Package com.letv.mms.cache.client.memcached.client
 * @Description: TODO
 * @author chenguang 
 * @date 2012-12-18 下午6:20:06
 * @version V1.0
 *
 * Modification History:  
 * Date         Author      Version     Description  
 * -------------------------------------------------------------- 
 * 2012-12-18                          
 */
package cache.memcached.main;


import java.io.*;

/**
 * Bridge class to provide nested Exceptions with IOException which has
 * constructors that don't take Throwables.
 * 
 * @author <a href="mailto:burton@rojo.com">Kevin Burton</a>
 * @version 1.2
 */
public class NestedIOException extends IOException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Create a new <code>NestedIOException</code> instance.
     * @param cause object of type throwable
     */
    public NestedIOException( Throwable cause ) {
        super( cause.getMessage() );
        super.initCause( cause );
    }

    public NestedIOException( String message, Throwable cause ) {
        super( message );
        initCause( cause );
    }
}
