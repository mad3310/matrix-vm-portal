package com.letv.mms.cache.retry;

import java.util.concurrent.Callable;

public class Retry {
   public static <K, R> R retry(Callable<R> callable, IRetryHandler<K> retryHandler, K key) {
      Throwable caught = null;
      for (int tries = 1; ; tries++) {
         try {
            R retval = callable.call();
            // If no retry happened, don't involve the retry handlers anymore.
            if (tries > 1) {
               retryHandler.success(key, tries, caught);
            }
            return retval;
         }
         catch (Throwable t) {
            if (caught == null) {
               caught = t;
            }
            if (!retryHandler.beforeRetry(key, tries, t)) {
               try {
                  if (tries > 1) { // If no retry happened, don't involve the retry handlers anymore.
                     retryHandler.failure(key, tries, caught);
                  }
                  throw caught;
               }
               catch (Throwable e) {
                  if (e instanceof Error) {
                     throw (Error)e;
                  }
                  else if (e instanceof RuntimeException) {
                     throw (RuntimeException)e;
                  }
                  else {
                     throw new RuntimeException(e);
                  }
               }
            }
         }
      }
   }
}
