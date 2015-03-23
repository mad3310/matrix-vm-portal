package cache;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import cache.util.DateTestUtil;

import com.letv.mms.cache.ICacheService;
import com.letv.mms.cache.factory.CacheFactory;

import org.junit.Assert;
import org.junit.Test;

public class CacheTest {
	private ICacheService<?> cacheService = CacheFactory.getCache();
	
	private ExecutorService exec = Executors.newFixedThreadPool(100);  
    private CompletionService<Map<String,String>> completionService = new ExecutorCompletionService<Map<String,String>>(exec);
    
	@Test
	public void testRemoveAll()
	{
		String key = "zbz_test_1111";
		String value = "zbz_test_value_1111";
		
		cacheService.delete(key);
		Object obj = cacheService.get(key, null);
		Assert.assertNull(obj);
		
		cacheService.set(key, value);
		obj = cacheService.get(key, null);
		Assert.assertEquals(value, obj);
		
		cacheService.removeAll();
		obj = cacheService.get(key, null);
		Assert.assertNull(obj);
	}
	
	@Test
	public void testSetAndGet()
	{
		final String key = "ptv_v_";
		final String value = "zbz_test_";
		
		final int workerNum = 10000;
		
		List<String> keyArray = new ArrayList<String>();
		for(int i = 0;i<workerNum;i++)
		{
			String realKey = key + String.valueOf(i);
			String realValue = value + String.valueOf(i);
			completionService.submit(new Worker(realKey,realValue));
			keyArray.add(realKey);
		}
		
		Map<String,String> resultMaps = new HashMap<String,String>();
		for(int i = 0;i<workerNum;i++)
		{
			try {
				resultMaps.putAll(completionService.take().get()) ;
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			} catch (ExecutionException e) {
				throw new RuntimeException(e);
			}
		}
		
		Assert.assertEquals(workerNum, resultMaps.size());
		
		for(String retrieveKey : keyArray)
		{
			String retrieveValue = resultMaps.get(retrieveKey);
			Assert.assertNotNull(retrieveValue);
			
			Object targetValue = cacheService.get(retrieveKey, null);
			Assert.assertEquals(targetValue, retrieveValue);
		}
	}
	
	private class Worker implements Callable<Map<String,String>>
	{
		private String key;
		private String value;
		
		public Worker(String key,String value)
		{
			this.key = key;
			this.value = value;
		}
		
		@Override
		public Map<String,String> call() {
			cacheService.delete(key);
			Object obj = CacheFactory.getCache().get(key, null);
			
			Map<String,String> resultMaps = new HashMap<String,String>();
			cacheService.set(key, value);
			obj = cacheService.get(key, null);
			
			resultMaps.put(key, String.valueOf(obj));
			return resultMaps;
		}
		
	}
	
	@Test
	public void testExpireSet()
	{
		final String key = "ptv_v_";
		final String value = "zbz_test_";
		
		Date expiredDate = DateTestUtil.getIndexDayRefCurr(Calendar.SECOND, 3);
		cacheService.set(key, value, expiredDate);
		
		String realValue = (String) cacheService.get(key, null);
		Assert.assertEquals(value, realValue);
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		realValue = (String) cacheService.get(key, null);
		Assert.assertNull(realValue);
	}
	
	@Test
	public void testExpireUpdate()
	{
		final String key = "ptv_v_";
		final String value = "zbz_test_";
		
		cacheService.set(key, value);
		
		String realValue = (String) cacheService.get(key, null);
		Assert.assertEquals(value, realValue);
		
		Date expiredDate = DateTestUtil.getIndexDayRefCurr(Calendar.SECOND, 3);
		cacheService.update(key, value, expiredDate);
		
		realValue = (String) cacheService.get(key, null);
		Assert.assertEquals(value, realValue);
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		realValue = (String) cacheService.get(key, null);
		Assert.assertNull(realValue);
	}

}
