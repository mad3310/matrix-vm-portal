package com.letv.mms.cache.retry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ThrowOnFailureRetryHandler<K> implements IRetryHandler<K> {

	private static final Log Logger = LogFactory
			.getLog(ThrowOnFailureRetryHandler.class);

	@Override
	public void success(K key, int numTries, Throwable cause) {
		Logger.error("this operation has been retry " + (numTries-1) + ",finished");
	}

	@Override
	public void failure(K key, int numTries, Throwable cause) throws Throwable {
		Logger.error("this operation has been retry " + (numTries-1) + ",but even failed,the reason is:", cause);
		throw cause;
	}

	@Override
	public boolean beforeRetry(K key, int numTries, Throwable cause) {
		if (numTries > 3)
			return false;

		return true;
	}

}
