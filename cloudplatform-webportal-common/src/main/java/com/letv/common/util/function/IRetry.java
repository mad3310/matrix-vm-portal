package com.letv.common.util.function;

@SuppressWarnings("hiding")
public interface IRetry<R, Boolean> {
    /**
      * @Title: execute
      * @Description: 方法执行
      * @return R   
      * @throws 
      * @author lisuxiao
      * @date 2015年12月29日 下午2:43:02
      */
    R execute();
    /**
      * @Title: analyzeResult
      * @Description: 分析执行结果
      * @param r
      * @return T   
      * @throws 
      * @author lisuxiao
      * @date 2015年12月29日 下午2:43:13
      */
    Boolean analyzeResult(R r);
}
