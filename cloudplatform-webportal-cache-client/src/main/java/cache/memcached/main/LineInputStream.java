/*
 * @Title: LineInputStream.java
 * @Package com.letv.mms.cache.client.memcached.client
 * @Description: TODO
 * @author chenguang 
 * @date 2012-12-18 下午6:18:28
 * @version V1.0
 *
 * Modification History:  
 * Date         Author      Version     Description  
 * -------------------------------------------------------------- 
 * 2012-12-18                          
 */
package cache.memcached.main;

import java.io.IOException;

public interface LineInputStream
{
	/**
	 * Read everything up to the next end-of-line.  Does
	 * not include the end of line, though it is consumed
	 * from the input.
	 * @return  All next up to the next end of line.
	 */
	public String readLine() throws IOException;
	
	/**
	 * Read everything up to and including the end of line.
	 */
	public void clearEOL() throws IOException;
	
	/**
	 * Read some bytes.
	 * @param buf   The buffer into which read. 
	 * @return      The number of bytes actually read, or -1 if none could be read.
	 */
	public int read( byte[] buf ) throws IOException;

}
