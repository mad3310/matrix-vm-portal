package com.letv.mms.cache.retry;

public interface IRetryHandler<K> {
   /**
    * Called whenever the operation has succeeded.
    * @param key Which operation is this? (the key is passed in when you start the op)
    * @param numTries How many tries did it take before success? Will always be > 1.
    * @param cause Why did it fail the first time? (we only keep track of the first failure)
    */
   void success(K key, int numTries, Throwable cause);
      
   /**
    * Called whenever the operation has failed and all retries are exhausted.
    * @param key Which operation is this? (the key is passed in when you start the op)
    * @param numTries How many tries did it take before success? Will always be > 1.
    * @param cause Why did it fail the first time? (we only keep track of the first failure)
    * @throws Throwable If you want the Retry call to throw. A non-RuntimeException will be wrapped in a RuntimeException
    */
   void failure(K key, int numTries, Throwable cause) throws Throwable;
   
   /**
    * Called before each retry.
    * @param key Which operation is this? (the key is passed in when you start the op)
    * @param numTries How many tries have there already been? The first time this is called it will be 1 (for the first try that failed).
    * @param cause Why did it fail this time? 
    * @return True if you want to retry and false if you don't.
    */
   boolean beforeRetry(K key, int numTries, Throwable cause);
}
